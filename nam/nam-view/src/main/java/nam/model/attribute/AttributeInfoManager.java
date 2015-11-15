package nam.model.attribute;

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

import nam.model.Attribute;
import nam.model.Project;
import nam.model.util.AttributeUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("attributeInfoManager")
public class AttributeInfoManager extends AbstractNamRecordManager<Attribute> implements Serializable {
	
	@Inject
	private AttributeWizard attributeWizard;
	
	@Inject
	private AttributeDataManager attributeDataManager;
	
	@Inject
	private AttributePageManager attributePageManager;
	
	@Inject
	private AttributeEventManager attributeEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private AttributeHelper attributeHelper;
	
	@Inject
	private SelectionContext selectionContext;

	
	public AttributeInfoManager() {
		setInstanceName("attribute");
	}
	
	
	public Attribute getAttribute() {
		return getRecord();
	}
	
	public Attribute getSelectedAttribute() {
		return selectionContext.getSelection("attribute");
	}
	
	@Override
	public Class<Attribute> getRecordClass() {
		return Attribute.class;
	}
	
	@Override
	public boolean isEmpty(Attribute attribute) {
		return attributeHelper.isEmpty(attribute);
	}
	
	@Override
	public String toString(Attribute attribute) {
		return attributeHelper.toString(attribute);
	}
	
	@Override
	public void initialize() {
		Attribute attribute = selectionContext.getSelection("attribute");
		if (attribute != null)
			initialize(attribute);
	}
	
	protected void initialize(Attribute attribute) {
		AttributeUtil.initialize(attribute);
		attributeWizard.initialize(attribute);
		setContext("attribute", attribute);
	}
	
	public void handleAttributeSelected(@Observes @Selected Attribute attribute) {
		selectionContext.setSelection("attribute",  attribute);
		attributePageManager.updateState(attribute);
		attributePageManager.refreshMembers();
		setRecord(attribute);
	}
	
	@Override
	public String newRecord() {
		return newAttribute();
	}
	
	public String newAttribute() {
		try {
			Attribute attribute = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("attribute",  attribute);
			String url = attributePageManager.initializeAttributeCreationPage(attribute);
			attributePageManager.pushContext(attributeWizard);
			initialize(attribute);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Attribute create() {
		Attribute attribute = AttributeUtil.create();
		return attribute;
	}
	
	@Override
	public Attribute clone(Attribute attribute) {
		attribute = AttributeUtil.clone(attribute);
		return attribute;
	}
	
	@Override
	public String viewRecord() {
		return viewAttribute();
	}
	
	public String viewAttribute() {
		Attribute attribute = selectionContext.getSelection("attribute");
		String url = viewAttribute(attribute);
		return url;
	}
	
	public String viewAttribute(Attribute attribute) {
		try {
			String url = attributePageManager.initializeAttributeSummaryView(attribute);
			attributePageManager.pushContext(attributeWizard);
			initialize(attribute);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
			
	@Override
	public String editRecord() {
		return editAttribute();
	}
	
	public String editAttribute() {
		Attribute attribute = selectionContext.getSelection("attribute");
		String url = editAttribute(attribute);
		return url;
	}
	
	public String editAttribute(Attribute attribute) {
		try {
			//attribute = clone(attribute);
			selectionContext.resetOrigin();
			selectionContext.setSelection("attribute",  attribute);
			String url = attributePageManager.initializeAttributeUpdatePage(attribute);
			attributePageManager.pushContext(attributeWizard);
			initialize(attribute);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveAttribute() {
		Attribute attribute = getAttribute();
		if (validateAttribute(attribute)) {
			saveAttribute(attribute);
		}
	}
	
	public void persistAttribute(Attribute attribute) {
			saveAttribute(attribute);
	}
	
	public void saveAttribute(Attribute attribute) {
		try {
			saveAttributeToSystem(attribute);
			attributeEventManager.fireAddedEvent(attribute);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveAttributeToSystem(Attribute attribute) {
		attributeDataManager.saveAttribute(attribute);
	}
	
	public void handleSaveAttribute(@Observes @Add Attribute attribute) {
		saveAttribute(attribute);
	}
	
	public void enrichAttribute(Attribute attribute) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Attribute attribute) {
		return validateAttribute(attribute);
	}
	
	public boolean validateAttribute(Attribute attribute) {
		Validator validator = getValidator();
		boolean isValid = AttributeUtil.validate(attribute);
		Display display = getFromSession("display");
		display.setModule("attributeInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveAttribute() {
		display = getFromSession("display");
		display.setModule("attributeInfo");
		Attribute attribute = selectionContext.getSelection("attribute");
		if (attribute == null) {
			display.error("Attribute record must be selected.");
		}
	}
	
	public String handleRemoveAttribute(@Observes @Remove Attribute attribute) {
		display = getFromSession("display");
		display.setModule("attributeInfo");
		try {
			display.info("Removing Attribute "+AttributeUtil.getLabel(attribute)+" from the system.");
			removeAttributeFromSystem(attribute);
			selectionContext.clearSelection("attribute");
			attributeEventManager.fireClearSelectionEvent();
			attributeEventManager.fireRemovedEvent(attribute);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeAttributeFromSystem(Attribute attribute) {
		if (attributeDataManager.removeAttribute(attribute))
			setRecord(null);
	}
	
	public void cancelAttribute() {
		BeanContext.removeFromSession("attribute");
		attributePageManager.removeContext(attributeWizard);
	}
	
}
