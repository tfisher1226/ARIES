package nam.model.interactor;

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

import nam.model.Interactor;
import nam.model.Project;
import nam.model.util.InteractorUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("interactorInfoManager")
public class InteractorInfoManager extends AbstractNamRecordManager<Interactor> implements Serializable {
	
	@Inject
	private InteractorWizard interactorWizard;
	
	@Inject
	private InteractorDataManager interactorDataManager;
	
	@Inject
	private InteractorPageManager interactorPageManager;
	
	@Inject
	private InteractorEventManager interactorEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private InteractorHelper interactorHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public InteractorInfoManager() {
		setInstanceName("interactor");
	}
	
	
	public Interactor getInteractor() {
		return getRecord();
	}
	
	public Interactor getSelectedInteractor() {
		return selectionContext.getSelection("interactor");
	}
	
	@Override
	public Class<Interactor> getRecordClass() {
		return Interactor.class;
	}
	
	@Override
	public boolean isEmpty(Interactor interactor) {
		return interactorHelper.isEmpty(interactor);
	}
	
	@Override
	public String toString(Interactor interactor) {
		return interactorHelper.toString(interactor);
	}
	
	@Override
	public void initialize() {
		Interactor interactor = selectionContext.getSelection("interactor");
		if (interactor != null)
			initialize(interactor);
	}
	
	protected void initialize(Interactor interactor) {
		InteractorUtil.initialize(interactor);
		interactorWizard.initialize(interactor);
		setContext("interactor", interactor);
	}
	
	public void handleInteractorSelected(@Observes @Selected Interactor interactor) {
		selectionContext.setSelection("interactor",  interactor);
		interactorPageManager.updateState(interactor);
		interactorPageManager.refreshMembers();
		setRecord(interactor);
	}
	
	@Override
	public String newRecord() {
		return newInteractor();
	}
	
	public String newInteractor() {
		try {
			Interactor interactor = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("interactor",  interactor);
			String url = interactorPageManager.initializeInteractorCreationPage(interactor);
			interactorPageManager.pushContext(interactorWizard);
			initialize(interactor);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Interactor create() {
		Interactor interactor = InteractorUtil.create();
		return interactor;
	}
	
	@Override
	public Interactor clone(Interactor interactor) {
		interactor = InteractorUtil.clone(interactor);
		return interactor;
	}
	
	@Override
	public String viewRecord() {
		return viewInteractor();
	}
	
	public String viewInteractor() {
		Interactor interactor = selectionContext.getSelection("interactor");
		String url = viewInteractor(interactor);
		return url;
	}
	
	public String viewInteractor(Interactor interactor) {
		try {
			String url = interactorPageManager.initializeInteractorSummaryView(interactor);
			interactorPageManager.pushContext(interactorWizard);
			initialize(interactor);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editInteractor();
	}
	
	public String editInteractor() {
		Interactor interactor = selectionContext.getSelection("interactor");
		String url = editInteractor(interactor);
		return url;
	}
	
	public String editInteractor(Interactor interactor) {
		try {
			//interactor = clone(interactor);
			selectionContext.resetOrigin();
			selectionContext.setSelection("interactor",  interactor);
			String url = interactorPageManager.initializeInteractorUpdatePage(interactor);
			interactorPageManager.pushContext(interactorWizard);
			initialize(interactor);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveInteractor() {
		Interactor interactor = getInteractor();
		if (validateInteractor(interactor)) {
			saveInteractor(interactor);
		}
	}
	
	public void persistInteractor(Interactor interactor) {
		saveInteractor(interactor);
	}
	
	public void saveInteractor(Interactor interactor) {
		try {
			saveInteractorToSystem(interactor);
			interactorEventManager.fireAddedEvent(interactor);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveInteractorToSystem(Interactor interactor) {
		interactorDataManager.saveInteractor(interactor);
	}
	
	public void handleSaveInteractor(@Observes @Add Interactor interactor) {
		saveInteractor(interactor);
	}
	
	public void enrichInteractor(Interactor interactor) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Interactor interactor) {
		return validateInteractor(interactor);
	}
	
	public boolean validateInteractor(Interactor interactor) {
		Validator validator = getValidator();
		boolean isValid = InteractorUtil.validate(interactor);
		Display display = getFromSession("display");
		display.setModule("interactorInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveInteractor() {
		display = getFromSession("display");
		display.setModule("interactorInfo");
		Interactor interactor = selectionContext.getSelection("interactor");
		if (interactor == null) {
			display.error("Interactor record must be selected.");
		}
	}
	
	public String handleRemoveInteractor(@Observes @Remove Interactor interactor) {
		display = getFromSession("display");
		display.setModule("interactorInfo");
		try {
			display.info("Removing Interactor "+InteractorUtil.getLabel(interactor)+" from the system.");
			removeInteractorFromSystem(interactor);
			selectionContext.clearSelection("interactor");
			interactorEventManager.fireClearSelectionEvent();
			interactorEventManager.fireRemovedEvent(interactor);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeInteractorFromSystem(Interactor interactor) {
		if (interactorDataManager.removeInteractor(interactor))
			setRecord(null);
	}
	
	public void cancelInteractor() {
		BeanContext.removeFromSession("interactor");
		interactorPageManager.removeContext(interactorWizard);
	}
	
}
