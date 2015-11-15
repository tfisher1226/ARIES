package nam.model.unit;

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
import nam.model.Unit;
import nam.model.util.ProjectUtil;
import nam.model.util.UnitUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("unitInfoManager")
public class UnitInfoManager extends AbstractNamRecordManager<Unit> implements Serializable {
	
	@Inject
	private UnitWizard unitWizard;
	
	@Inject
	private UnitDataManager unitDataManager;
	
	@Inject
	private UnitPageManager unitPageManager;
	
	@Inject
	private UnitEventManager unitEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private UnitHelper unitHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public UnitInfoManager() {
		setInstanceName("unit");
	}
	
	
	public Unit getUnit() {
		return getRecord();
	}
	
	public Unit getSelectedUnit() {
		return selectionContext.getSelection("unit");
	}
	
	@Override
	public Class<Unit> getRecordClass() {
		return Unit.class;
	}
	
	@Override
	public boolean isEmpty(Unit unit) {
		return unitHelper.isEmpty(unit);
	}
	
	@Override
	public String toString(Unit unit) {
		return unitHelper.toString(unit);
	}
	
	@Override
	public void initialize() {
		Unit unit = selectionContext.getSelection("unit");
		if (unit != null)
			initialize(unit);
	}
	
	protected void initialize(Unit unit) {
		UnitUtil.initialize(unit);
		unitWizard.initialize(unit);
		setContext("unit", unit);
	}
	
	public void handleUnitSelected(@Observes @Selected Unit unit) {
		selectionContext.setSelection("unit",  unit);
		unitPageManager.updateState(unit);
		unitPageManager.refreshMembers();
		setRecord(unit);
	}
	
	@Override
	public String newRecord() {
		return newUnit();
	}
	
	public String newUnit() {
		try {
			Unit unit = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("unit",  unit);
			String url = unitPageManager.initializeUnitCreationPage(unit);
			unitPageManager.pushContext(unitWizard);
			initialize(unit);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Unit create() {
		Unit unit = UnitUtil.create();
		return unit;
	}
	
	@Override
	public Unit clone(Unit unit) {
		unit = UnitUtil.clone(unit);
		return unit;
	}
	
	@Override
	public String viewRecord() {
		return viewUnit();
	}
	
	public String viewUnit() {
		Unit unit = selectionContext.getSelection("unit");
		String url = viewUnit(unit);
		return url;
	}
	
	public String viewUnit(Unit unit) {
		try {
			String url = unitPageManager.initializeUnitSummaryView(unit);
			unitPageManager.pushContext(unitWizard);
			initialize(unit);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editUnit();
	}
	
	public String editUnit() {
		Unit unit = selectionContext.getSelection("unit");
		String url = editUnit(unit);
		return url;
	}
	
	public String editUnit(Unit unit) {
		try {
			//unit = clone(unit);
			selectionContext.resetOrigin();
			selectionContext.setSelection("unit",  unit);
			String url = unitPageManager.initializeUnitUpdatePage(unit);
			unitPageManager.pushContext(unitWizard);
			initialize(unit);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveUnit() {
		Unit unit = getUnit();
		if (validateUnit(unit)) {
			if (isImmediate())
				persistUnit(unit);
			outject("unit", unit);
		}
	}
	
	public void persistUnit(Unit unit) {
		saveUnit(unit);
	}
	
	public void saveUnit(Unit unit) {
		try {
			saveUnitToSystem(unit);
			unitEventManager.fireAddedEvent(unit);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveUnitToSystem(Unit unit) {
		unitDataManager.saveUnit(unit);
	}
	
	public void handleSaveUnit(@Observes @Add Unit unit) {
		saveUnit(unit);
	}
	
	public void addUnit(Unit unit) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichUnit(Unit unit) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Unit unit) {
		return validateUnit(unit);
	}
	
	public boolean validateUnit(Unit unit) {
		Validator validator = getValidator();
		boolean isValid = UnitUtil.validate(unit);
		Display display = getFromSession("display");
		display.setModule("unitInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveUnit() {
		display = getFromSession("display");
		display.setModule("unitInfo");
		Unit unit = selectionContext.getSelection("unit");
		if (unit == null) {
			display.error("Unit record must be selected.");
		}
	}
	
	public String handleRemoveUnit(@Observes @Remove Unit unit) {
		display = getFromSession("display");
		display.setModule("unitInfo");
		try {
			display.info("Removing Unit "+UnitUtil.getLabel(unit)+" from the system.");
			removeUnitFromSystem(unit);
			selectionContext.clearSelection("unit");
			unitEventManager.fireClearSelectionEvent();
			unitEventManager.fireRemovedEvent(unit);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeUnitFromSystem(Unit unit) {
		if (unitDataManager.removeUnit(unit))
			setRecord(null);
	}
	
	public void cancelUnit() {
		BeanContext.removeFromSession("unit");
		unitPageManager.removeContext(unitWizard);
	}
	
}
