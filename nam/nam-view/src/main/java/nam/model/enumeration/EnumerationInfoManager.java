package nam.model.enumeration;

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

import nam.model.Enumeration;
import nam.model.Project;
import nam.model.util.EnumerationUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("enumerationInfoManager")
public class EnumerationInfoManager extends AbstractNamRecordManager<Enumeration> implements Serializable {
	
	@Inject
	private EnumerationWizard enumerationWizard;
	
	@Inject
	private EnumerationDataManager enumerationDataManager;
	
	@Inject
	private EnumerationPageManager enumerationPageManager;
	
	@Inject
	private EnumerationEventManager enumerationEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private EnumerationHelper enumerationHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public EnumerationInfoManager() {
		setInstanceName("enumeration");
	}
	
	
	public Enumeration getEnumeration() {
		return getRecord();
	}
	
	public Enumeration getSelectedEnumeration() {
		return selectionContext.getSelection("enumeration");
	}
	
	@Override
	public Class<Enumeration> getRecordClass() {
		return Enumeration.class;
	}
	
	@Override
	public boolean isEmpty(Enumeration enumeration) {
		return enumerationHelper.isEmpty(enumeration);
	}
	
	@Override
	public String toString(Enumeration enumeration) {
		return enumerationHelper.toString(enumeration);
	}
	
	@Override
	public void initialize() {
		Enumeration enumeration = selectionContext.getSelection("enumeration");
		if (enumeration != null)
			initialize(enumeration);
	}
	
	protected void initialize(Enumeration enumeration) {
		EnumerationUtil.initialize(enumeration);
		enumerationWizard.initialize(enumeration);
		setContext("enumeration", enumeration);
	}
	
	public void handleEnumerationSelected(@Observes @Selected Enumeration enumeration) {
		selectionContext.setSelection("enumeration",  enumeration);
		enumerationPageManager.updateState(enumeration);
		enumerationPageManager.refreshMembers();
		setRecord(enumeration);
	}
	
	@Override
	public String newRecord() {
		return newEnumeration();
	}
	
	public String newEnumeration() {
		try {
			Enumeration enumeration = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("enumeration",  enumeration);
			String url = enumerationPageManager.initializeEnumerationCreationPage(enumeration);
			enumerationPageManager.pushContext(enumerationWizard);
			initialize(enumeration);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Enumeration create() {
		Enumeration enumeration = EnumerationUtil.create();
		return enumeration;
	}
	
	@Override
	public Enumeration clone(Enumeration enumeration) {
		enumeration = EnumerationUtil.clone(enumeration);
		return enumeration;
	}
	
	@Override
	public String viewRecord() {
		return viewEnumeration();
	}
	
	public String viewEnumeration() {
		Enumeration enumeration = selectionContext.getSelection("enumeration");
		String url = viewEnumeration(enumeration);
		return url;
	}
	
	public String viewEnumeration(Enumeration enumeration) {
		try {
			String url = enumerationPageManager.initializeEnumerationSummaryView(enumeration);
			enumerationPageManager.pushContext(enumerationWizard);
			initialize(enumeration);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editEnumeration();
	}
	
	public String editEnumeration() {
		Enumeration enumeration = selectionContext.getSelection("enumeration");
		String url = editEnumeration(enumeration);
		return url;
	}
	
	public String editEnumeration(Enumeration enumeration) {
		try {
			//enumeration = clone(enumeration);
			selectionContext.resetOrigin();
			selectionContext.setSelection("enumeration",  enumeration);
			String url = enumerationPageManager.initializeEnumerationUpdatePage(enumeration);
			enumerationPageManager.pushContext(enumerationWizard);
			initialize(enumeration);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveEnumeration() {
		Enumeration enumeration = getEnumeration();
		if (validateEnumeration(enumeration)) {
			saveEnumeration(enumeration);
		}
	}
	
	public void persistEnumeration(Enumeration enumeration) {
		saveEnumeration(enumeration);
	}
	
	public void saveEnumeration(Enumeration enumeration) {
		try {
			saveEnumerationToSystem(enumeration);
			enumerationEventManager.fireAddedEvent(enumeration);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveEnumerationToSystem(Enumeration enumeration) {
		enumerationDataManager.saveEnumeration(enumeration);
	}
	
	public void handleSaveEnumeration(@Observes @Add Enumeration enumeration) {
		saveEnumeration(enumeration);
	}
	
	public void enrichEnumeration(Enumeration enumeration) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Enumeration enumeration) {
		return validateEnumeration(enumeration);
	}
	
	public boolean validateEnumeration(Enumeration enumeration) {
		Validator validator = getValidator();
		boolean isValid = EnumerationUtil.validate(enumeration);
		Display display = getFromSession("display");
		display.setModule("enumerationInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveEnumeration() {
		display = getFromSession("display");
		display.setModule("enumerationInfo");
		Enumeration enumeration = selectionContext.getSelection("enumeration");
		if (enumeration == null) {
			display.error("Enumeration record must be selected.");
		}
	}
	
	public String handleRemoveEnumeration(@Observes @Remove Enumeration enumeration) {
		display = getFromSession("display");
		display.setModule("enumerationInfo");
		try {
			display.info("Removing Enumeration "+EnumerationUtil.getLabel(enumeration)+" from the system.");
			removeEnumerationFromSystem(enumeration);
			selectionContext.clearSelection("enumeration");
			enumerationEventManager.fireClearSelectionEvent();
			enumerationEventManager.fireRemovedEvent(enumeration);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeEnumerationFromSystem(Enumeration enumeration) {
		if (enumerationDataManager.removeEnumeration(enumeration))
			setRecord(null);
	}
	
	public void cancelEnumeration() {
		BeanContext.removeFromSession("enumeration");
		enumerationPageManager.removeContext(enumerationWizard);
	}
	
}
