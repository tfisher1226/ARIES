package nam.model.persistence;

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

import nam.model.Persistence;
import nam.model.Project;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("persistenceInfoManager")
public class PersistenceInfoManager extends AbstractNamRecordManager<Persistence> implements Serializable {
	
	@Inject
	private PersistenceWizard persistenceWizard;
	
	@Inject
	private PersistenceDataManager persistenceDataManager;
	
	@Inject
	private PersistencePageManager persistencePageManager;
	
	@Inject
	private PersistenceEventManager persistenceEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private PersistenceHelper persistenceHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PersistenceInfoManager() {
		setInstanceName("persistence");
	}
	
	
	public Persistence getPersistence() {
		return getRecord();
	}
	
	public Persistence getSelectedPersistence() {
		return selectionContext.getSelection("persistence");
	}
	
	@Override
	public Class<Persistence> getRecordClass() {
		return Persistence.class;
	}
	
	@Override
	public boolean isEmpty(Persistence persistence) {
		return persistenceHelper.isEmpty(persistence);
	}
	
	@Override
	public String toString(Persistence persistence) {
		return persistenceHelper.toString(persistence);
	}
	
	@Override
	public void initialize() {
		Persistence persistence = selectionContext.getSelection("persistence");
		if (persistence != null)
			initialize(persistence);
	}
	
	protected void initialize(Persistence persistence) {
		PersistenceUtil.initialize(persistence);
		persistenceWizard.initialize(persistence);
		setContext("persistence", persistence);
	}
	
	public void handlePersistenceSelected(@Observes @Selected Persistence persistence) {
		selectionContext.setSelection("persistence",  persistence);
		persistencePageManager.updateState(persistence);
		persistencePageManager.refreshMembers();
		setRecord(persistence);
	}
	
	@Override
	public String newRecord() {
		return newPersistence();
	}
	
	public String newPersistence() {
		try {
			Persistence persistence = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("persistence",  persistence);
			String url = persistencePageManager.initializePersistenceCreationPage(persistence);
			persistencePageManager.pushContext(persistenceWizard);
			initialize(persistence);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Persistence create() {
		Persistence persistence = PersistenceUtil.create();
		return persistence;
	}
	
	@Override
	public Persistence clone(Persistence persistence) {
		persistence = PersistenceUtil.clone(persistence);
		return persistence;
	}
	
	@Override
	public String viewRecord() {
		return viewPersistence();
	}
	
	public String viewPersistence() {
		Persistence persistence = selectionContext.getSelection("persistence");
		String url = viewPersistence(persistence);
		return url;
	}
	
	public String viewPersistence(Persistence persistence) {
		try {
			String url = persistencePageManager.initializePersistenceSummaryView(persistence);
			persistencePageManager.pushContext(persistenceWizard);
			initialize(persistence);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editPersistence();
	}
	
	public String editPersistence() {
		Persistence persistence = selectionContext.getSelection("persistence");
		String url = editPersistence(persistence);
		return url;
	}
	
	public String editPersistence(Persistence persistence) {
		try {
			//persistence = clone(persistence);
			selectionContext.resetOrigin();
			selectionContext.setSelection("persistence",  persistence);
			String url = persistencePageManager.initializePersistenceUpdatePage(persistence);
			persistencePageManager.pushContext(persistenceWizard);
			initialize(persistence);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void savePersistence() {
		Persistence persistence = getPersistence();
		if (validatePersistence(persistence)) {
			if (isImmediate())
				persistPersistence(persistence);
			outject("persistence", persistence);
		}
	}
	
	public void persistPersistence(Persistence persistence) {
		savePersistence(persistence);
	}
	
	public void savePersistence(Persistence persistence) {
		try {
			savePersistenceToSystem(persistence);
			persistenceEventManager.fireAddedEvent(persistence);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void savePersistenceToSystem(Persistence persistence) {
		persistenceDataManager.savePersistence(persistence);
	}
	
	public void handleSavePersistence(@Observes @Add Persistence persistence) {
		savePersistence(persistence);
	}
	
	public void addPersistence(Persistence persistence) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichPersistence(Persistence persistence) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Persistence persistence) {
		return validatePersistence(persistence);
	}
	
	public boolean validatePersistence(Persistence persistence) {
		Validator validator = getValidator();
		boolean isValid = PersistenceUtil.validate(persistence);
		Display display = getFromSession("display");
		display.setModule("persistenceInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemovePersistence() {
		display = getFromSession("display");
		display.setModule("persistenceInfo");
		Persistence persistence = selectionContext.getSelection("persistence");
		if (persistence == null) {
			display.error("Persistence record must be selected.");
		}
	}
	
	public String handleRemovePersistence(@Observes @Remove Persistence persistence) {
		display = getFromSession("display");
		display.setModule("persistenceInfo");
		try {
			display.info("Removing Persistence "+PersistenceUtil.getLabel(persistence)+" from the system.");
			removePersistenceFromSystem(persistence);
			selectionContext.clearSelection("persistence");
			persistenceEventManager.fireClearSelectionEvent();
			persistenceEventManager.fireRemovedEvent(persistence);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removePersistenceFromSystem(Persistence persistence) {
		if (persistenceDataManager.removePersistence(persistence))
			setRecord(null);
	}
	
	public void cancelPersistence() {
		BeanContext.removeFromSession("persistence");
		persistencePageManager.removeContext(persistenceWizard);
	}
	
}
