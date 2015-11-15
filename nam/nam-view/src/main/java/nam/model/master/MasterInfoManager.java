package nam.model.master;

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

import nam.model.Master;
import nam.model.Project;
import nam.model.util.MasterUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("masterInfoManager")
public class MasterInfoManager extends AbstractNamRecordManager<Master> implements Serializable {
	
	@Inject
	private MasterWizard masterWizard;
	
	@Inject
	private MasterDataManager masterDataManager;
	
	@Inject
	private MasterPageManager masterPageManager;
	
	@Inject
	private MasterEventManager masterEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private MasterHelper masterHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public MasterInfoManager() {
		setInstanceName("master");
	}
	
	
	public Master getMaster() {
		return getRecord();
	}
	
	public Master getSelectedMaster() {
		return selectionContext.getSelection("master");
	}
	
	@Override
	public Class<Master> getRecordClass() {
		return Master.class;
	}
	
	@Override
	public boolean isEmpty(Master master) {
		return masterHelper.isEmpty(master);
	}
	
	@Override
	public String toString(Master master) {
		return masterHelper.toString(master);
	}
	
	@Override
	public void initialize() {
		Master master = selectionContext.getSelection("master");
		if (master != null)
			initialize(master);
	}
	
	protected void initialize(Master master) {
		MasterUtil.initialize(master);
		masterWizard.initialize(master);
		setContext("master", master);
	}
	
	public void handleMasterSelected(@Observes @Selected Master master) {
		selectionContext.setSelection("master",  master);
		masterPageManager.updateState(master);
		masterPageManager.refreshMembers();
		setRecord(master);
	}
	
	@Override
	public String newRecord() {
		return newMaster();
	}
	
	public String newMaster() {
		try {
			Master master = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("master",  master);
			String url = masterPageManager.initializeMasterCreationPage(master);
			masterPageManager.pushContext(masterWizard);
			initialize(master);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Master create() {
		Master master = MasterUtil.create();
		return master;
	}
	
	@Override
	public Master clone(Master master) {
		master = MasterUtil.clone(master);
		return master;
	}
	
	@Override
	public String viewRecord() {
		return viewMaster();
	}
	
	public String viewMaster() {
		Master master = selectionContext.getSelection("master");
		String url = viewMaster(master);
		return url;
	}
	
	public String viewMaster(Master master) {
		try {
			String url = masterPageManager.initializeMasterSummaryView(master);
			masterPageManager.pushContext(masterWizard);
			initialize(master);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editMaster();
	}
	
	public String editMaster() {
		Master master = selectionContext.getSelection("master");
		String url = editMaster(master);
		return url;
	}
	
	public String editMaster(Master master) {
		try {
			//master = clone(master);
			selectionContext.resetOrigin();
			selectionContext.setSelection("master",  master);
			String url = masterPageManager.initializeMasterUpdatePage(master);
			masterPageManager.pushContext(masterWizard);
			initialize(master);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveMaster() {
		Master master = getMaster();
		if (validateMaster(master)) {
			if (isImmediate())
				persistMaster(master);
			outject("master", master);
		}
	}
	
	public void persistMaster(Master master) {
		saveMaster(master);
	}
	
	public void saveMaster(Master master) {
		try {
			saveMasterToSystem(master);
			masterEventManager.fireAddedEvent(master);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveMasterToSystem(Master master) {
		masterDataManager.saveMaster(master);
	}
	
	public void handleSaveMaster(@Observes @Add Master master) {
		saveMaster(master);
	}
	
	public void addMaster(Master master) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichMaster(Master master) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Master master) {
		return validateMaster(master);
	}
	
	public boolean validateMaster(Master master) {
		Validator validator = getValidator();
		boolean isValid = MasterUtil.validate(master);
		Display display = getFromSession("display");
		display.setModule("masterInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveMaster() {
		display = getFromSession("display");
		display.setModule("masterInfo");
		Master master = selectionContext.getSelection("master");
		if (master == null) {
			display.error("Master record must be selected.");
		}
	}
	
	public String handleRemoveMaster(@Observes @Remove Master master) {
		display = getFromSession("display");
		display.setModule("masterInfo");
		try {
			display.info("Removing Master "+MasterUtil.getLabel(master)+" from the system.");
			removeMasterFromSystem(master);
			selectionContext.clearSelection("master");
			masterEventManager.fireClearSelectionEvent();
			masterEventManager.fireRemovedEvent(master);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeMasterFromSystem(Master master) {
		if (masterDataManager.removeMaster(master))
			setRecord(null);
	}
	
	public void cancelMaster() {
		BeanContext.removeFromSession("master");
		masterPageManager.removeContext(masterWizard);
	}
	
}
