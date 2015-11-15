package nam.model.minion;

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

import nam.model.Minion;
import nam.model.Project;
import nam.model.util.MinionUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("minionInfoManager")
public class MinionInfoManager extends AbstractNamRecordManager<Minion> implements Serializable {
	
	@Inject
	private MinionWizard minionWizard;
	
	@Inject
	private MinionDataManager minionDataManager;
	
	@Inject
	private MinionPageManager minionPageManager;
	
	@Inject
	private MinionEventManager minionEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private MinionHelper minionHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public MinionInfoManager() {
		setInstanceName("minion");
	}
	
	
	public Minion getMinion() {
		return getRecord();
	}
	
	public Minion getSelectedMinion() {
		return selectionContext.getSelection("minion");
	}
	
	@Override
	public Class<Minion> getRecordClass() {
		return Minion.class;
	}
	
	@Override
	public boolean isEmpty(Minion minion) {
		return minionHelper.isEmpty(minion);
	}
	
	@Override
	public String toString(Minion minion) {
		return minionHelper.toString(minion);
	}
	
	@Override
	public void initialize() {
		Minion minion = selectionContext.getSelection("minion");
		if (minion != null)
			initialize(minion);
	}
	
	protected void initialize(Minion minion) {
		MinionUtil.initialize(minion);
		minionWizard.initialize(minion);
		setContext("minion", minion);
	}
	
	public void handleMinionSelected(@Observes @Selected Minion minion) {
		selectionContext.setSelection("minion",  minion);
		minionPageManager.updateState(minion);
		minionPageManager.refreshMembers();
		setRecord(minion);
	}
	
	@Override
	public String newRecord() {
		return newMinion();
	}
	
	public String newMinion() {
		try {
			Minion minion = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("minion",  minion);
			String url = minionPageManager.initializeMinionCreationPage(minion);
			minionPageManager.pushContext(minionWizard);
			initialize(minion);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Minion create() {
		Minion minion = MinionUtil.create();
		return minion;
	}
	
	@Override
	public Minion clone(Minion minion) {
		minion = MinionUtil.clone(minion);
		return minion;
	}
	
	@Override
	public String viewRecord() {
		return viewMinion();
	}
	
	public String viewMinion() {
		Minion minion = selectionContext.getSelection("minion");
		String url = viewMinion(minion);
		return url;
	}
	
	public String viewMinion(Minion minion) {
		try {
			String url = minionPageManager.initializeMinionSummaryView(minion);
			minionPageManager.pushContext(minionWizard);
			initialize(minion);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editMinion();
	}
	
	public String editMinion() {
		Minion minion = selectionContext.getSelection("minion");
		String url = editMinion(minion);
		return url;
	}
	
	public String editMinion(Minion minion) {
		try {
			//minion = clone(minion);
			selectionContext.resetOrigin();
			selectionContext.setSelection("minion",  minion);
			String url = minionPageManager.initializeMinionUpdatePage(minion);
			minionPageManager.pushContext(minionWizard);
			initialize(minion);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveMinion() {
		Minion minion = getMinion();
		if (validateMinion(minion)) {
			if (isImmediate())
				persistMinion(minion);
			outject("minion", minion);
		}
	}
	
	public void persistMinion(Minion minion) {
		saveMinion(minion);
	}
	
	public void saveMinion(Minion minion) {
		try {
			saveMinionToSystem(minion);
			minionEventManager.fireAddedEvent(minion);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveMinionToSystem(Minion minion) {
		minionDataManager.saveMinion(minion);
	}
	
	public void handleSaveMinion(@Observes @Add Minion minion) {
		saveMinion(minion);
	}
	
	public void addMinion(Minion minion) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichMinion(Minion minion) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Minion minion) {
		return validateMinion(minion);
	}
	
	public boolean validateMinion(Minion minion) {
		Validator validator = getValidator();
		boolean isValid = MinionUtil.validate(minion);
		Display display = getFromSession("display");
		display.setModule("minionInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveMinion() {
		display = getFromSession("display");
		display.setModule("minionInfo");
		Minion minion = selectionContext.getSelection("minion");
		if (minion == null) {
			display.error("Minion record must be selected.");
		}
	}
	
	public String handleRemoveMinion(@Observes @Remove Minion minion) {
		display = getFromSession("display");
		display.setModule("minionInfo");
		try {
			display.info("Removing Minion "+MinionUtil.getLabel(minion)+" from the system.");
			removeMinionFromSystem(minion);
			selectionContext.clearSelection("minion");
			minionEventManager.fireClearSelectionEvent();
			minionEventManager.fireRemovedEvent(minion);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeMinionFromSystem(Minion minion) {
		if (minionDataManager.removeMinion(minion))
			setRecord(null);
	}
	
	public void cancelMinion() {
		BeanContext.removeFromSession("minion");
		minionPageManager.removeContext(minionWizard);
	}
	
}
