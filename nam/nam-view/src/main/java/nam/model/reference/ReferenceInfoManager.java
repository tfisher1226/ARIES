package nam.model.reference;

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
import nam.model.Reference;
import nam.model.util.ProjectUtil;
import nam.model.util.ReferenceUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("referenceInfoManager")
public class ReferenceInfoManager extends AbstractNamRecordManager<Reference> implements Serializable {
	
	@Inject
	private ReferenceWizard referenceWizard;
	
	@Inject
	private ReferenceDataManager referenceDataManager;
	
	@Inject
	private ReferencePageManager referencePageManager;
		
	@Inject
	private ReferenceEventManager referenceEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ReferenceHelper referenceHelper;
	
	@Inject
	private SelectionContext selectionContext;

	
	public ReferenceInfoManager() {
		setInstanceName("reference");
	}
	
	
	public Reference getReference() {
		return getRecord();
	}
	
	public Reference getSelectedReference() {
		return selectionContext.getSelection("reference");
	}
	
	@Override
	public Class<Reference> getRecordClass() {
		return Reference.class;
	}
	
	@Override
	public boolean isEmpty(Reference reference) {
		return referenceHelper.isEmpty(reference);
	}
	
	@Override
	public String toString(Reference reference) {
		return referenceHelper.toString(reference);
	}
	
	@Override
	public void initialize() {
		Reference reference = selectionContext.getSelection("reference");
		if (reference != null)
			initialize(reference);
	}
	
	protected void initialize(Reference reference) {
		ReferenceUtil.initialize(reference);
		referenceWizard.initialize(reference);
		setContext("reference", reference);
	}
	
	public void handleReferenceSelected(@Observes @Selected Reference reference) {
		selectionContext.setSelection("reference",  reference);
		referencePageManager.updateState(reference);
		referencePageManager.refreshMembers();
		setRecord(reference);
	}
	
	@Override
	public String newRecord() {
		return newReference();
	}
	
	public String newReference() {
		try {
			Reference reference = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("reference",  reference);
			String url = referencePageManager.initializeReferenceCreationPage(reference);
			referencePageManager.pushContext(referenceWizard);
			initialize(reference);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Reference create() {
		Reference reference = ReferenceUtil.create();
		return reference;
	}
	
	@Override
	public Reference clone(Reference reference) {
		reference = ReferenceUtil.clone(reference);
		return reference;
	}
	
	@Override
	public String viewRecord() {
		return viewReference();
	}
	
	public String viewReference() {
		Reference reference = selectionContext.getSelection("reference");
		String url = viewReference(reference);
		return url;
	}
	
	public String viewReference(Reference reference) {
		try {
			String url = referencePageManager.initializeReferenceSummaryView(reference);
			referencePageManager.pushContext(referenceWizard);
			initialize(reference);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
			
	@Override
	public String editRecord() {
		return editReference();
	}
	
	public String editReference() {
		Reference reference = selectionContext.getSelection("reference");
		String url = editReference(reference);
		return url;
	}
	
	public String editReference(Reference reference) {
		try {
			//reference = clone(reference);
			selectionContext.resetOrigin();
			selectionContext.setSelection("reference",  reference);
			String url = referencePageManager.initializeReferenceUpdatePage(reference);
			referencePageManager.pushContext(referenceWizard);
			initialize(reference);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveReference() {
		Reference reference = getReference();
		if (validateReference(reference)) {
			saveReference(reference);
		}
	}
	
	public void persistReference(Reference reference) {
			saveReference(reference);
	}
	
	public void saveReference(Reference reference) {
		try {
			saveReferenceToSystem(reference);
			referenceEventManager.fireAddedEvent(reference);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveReferenceToSystem(Reference reference) {
		referenceDataManager.saveReference(reference);
	}
	
	public void handleSaveReference(@Observes @Add Reference reference) {
		saveReference(reference);
	}
	
	public void enrichReference(Reference reference) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Reference reference) {
		return validateReference(reference);
	}
	
	public boolean validateReference(Reference reference) {
		Validator validator = getValidator();
		boolean isValid = ReferenceUtil.validate(reference);
		Display display = getFromSession("display");
		display.setModule("referenceInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveReference() {
		display = getFromSession("display");
		display.setModule("referenceInfo");
		Reference reference = selectionContext.getSelection("reference");
		if (reference == null) {
			display.error("Reference record must be selected.");
		}
	}
	
	public String handleRemoveReference(@Observes @Remove Reference reference) {
		display = getFromSession("display");
		display.setModule("referenceInfo");
		try {
			display.info("Removing Reference "+ReferenceUtil.getLabel(reference)+" from the system.");
			removeReferenceFromSystem(reference);
			selectionContext.clearSelection("reference");
			referenceEventManager.fireClearSelectionEvent();
			referenceEventManager.fireRemovedEvent(reference);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeReferenceFromSystem(Reference reference) {
		if (referenceDataManager.removeReference(reference))
			setRecord(null);
	}
	
	public void cancelReference() {
		BeanContext.removeFromSession("reference");
		referencePageManager.removeContext(referenceWizard);
	}
	
}
