package nam.model.property;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.Property;
import nam.model.util.ProjectUtil;
import nam.model.util.PropertyUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("propertyInfoManager")
public class PropertyInfoManager extends AbstractNamRecordManager<Property> implements Serializable {
	
	@Inject
	private PropertyWizard propertyWizard;
	
	@Inject
	private PropertyDataManager propertyDataManager;
	
	@Inject
	private PropertyPageManager propertyPageManager;
	
	@Inject
	private PropertyEventManager propertyEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private PropertyHelper propertyHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PropertyInfoManager() {
		setInstanceName("property");
	}
	
	
	public Property getProperty() {
		return getRecord();
	}
	
	public Property getSelectedProperty() {
		return selectionContext.getSelection("property");
	}
	
	@Override
	public Class<Property> getRecordClass() {
		return Property.class;
	}
	
	@Override
	public boolean isEmpty(Property property) {
		return propertyHelper.isEmpty(property);
	}
	
	@Override
	public String toString(Property property) {
		return propertyHelper.toString(property);
	}
	
	@Override
	public void initialize() {
		Property property = selectionContext.getSelection("property");
		if (property != null)
			initialize(property);
	}
	
	protected void initialize(Property property) {
		PropertyUtil.initialize(property);
		propertyWizard.initialize(property);
		setContext("property", property);
	}
	
	public void handlePropertySelected(@Observes @Selected Property property) {
		selectionContext.setSelection("property",  property);
		propertyPageManager.updateState(property);
		propertyPageManager.refreshMembers();
		setRecord(property);
	}
	
	@Override
	public String newRecord() {
		return newProperty();
	}
	
	public String newProperty() {
		try {
			Property property = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("property",  property);
			String url = propertyPageManager.initializePropertyCreationPage(property);
			propertyPageManager.pushContext(propertyWizard);
			initialize(property);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Property create() {
		Property property = PropertyUtil.create();
		return property;
	}
	
	@Override
	public Property clone(Property property) {
		property = PropertyUtil.clone(property);
		return property;
	}
	
	@Override
	public String viewRecord() {
		return viewProperty();
	}
	
	public String viewProperty() {
		Property property = selectionContext.getSelection("property");
		String url = viewProperty(property);
		return url;
	}
	
	public String viewProperty(Property property) {
		try {
			String url = propertyPageManager.initializePropertySummaryView(property);
			propertyPageManager.pushContext(propertyWizard);
			initialize(property);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editProperty();
	}
	
	public String editProperty() {
		Property property = selectionContext.getSelection("property");
		String url = editProperty(property);
		return url;
	}
	
	public String editProperty(Property property) {
		try {
			//property = clone(property);
			selectionContext.resetOrigin();
			selectionContext.setSelection("property",  property);
			String url = propertyPageManager.initializePropertyUpdatePage(property);
			propertyPageManager.pushContext(propertyWizard);
			initialize(property);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveProperty() {
		Property property = getProperty();
		if (validateProperty(property)) {
			saveProperty(property);
		}
	}
	
	public void persistProperty(Property property) {
			saveProperty(property);
	}
	
	public void saveProperty(Property property) {
		try {
			savePropertyToSystem(property);
			propertyEventManager.fireAddedEvent(property);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void savePropertyToSystem(Property property) {
		propertyDataManager.saveProperty(property);
	}
	
	public void handleSaveProperty(@Observes @Add Property property) {
		saveProperty(property);
	}
	
	public void enrichProperty(Property property) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Property property) {
		return validateProperty(property);
	}
	
	public boolean validateProperty(Property property) {
		Validator validator = getValidator();
		boolean isValid = PropertyUtil.validate(property);
		Display display = getFromSession("display");
		display.setModule("propertyInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveProperty() {
		display = getFromSession("display");
		display.setModule("propertyInfo");
		Property property = selectionContext.getSelection("property");
		if (property == null) {
			display.error("Property record must be selected.");
		}
	}
	
	public String handleRemoveProperty(@Observes @Remove Property property) {
		display = getFromSession("display");
		display.setModule("propertyInfo");
		try {
			display.info("Removing Property "+PropertyUtil.getLabel(property)+" from the system.");
			removePropertyFromSystem(property);
			selectionContext.clearSelection("property");
			propertyEventManager.fireClearSelectionEvent();
			propertyEventManager.fireRemovedEvent(property);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removePropertyFromSystem(Property property) {
		if (propertyDataManager.removeProperty(property))
			setRecord(null);
	}
	
	public void cancelProperty() {
		BeanContext.removeFromSession("property");
		propertyPageManager.removeContext(propertyWizard);
	}
	
}
