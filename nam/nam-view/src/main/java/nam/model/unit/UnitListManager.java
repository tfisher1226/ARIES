package nam.model.unit;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Unit;
import nam.model.util.UnitUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("unitListManager")
public class UnitListManager extends AbstractDomainListManager<Unit, UnitListObject> implements Serializable {
	
	@Inject
	private UnitDataManager unitDataManager;
	
	@Inject
	private UnitEventManager unitEventManager;
	
	@Inject
	private UnitInfoManager unitInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "unitList";
	}
	
	@Override
	public String getTitle() {
		return "Unit List";
	}
	
	@Override
	public Object getRecordKey(Unit unit) {
		return UnitUtil.getKey(unit);
	}
	
	@Override
	public String getRecordName(Unit unit) {
		return UnitUtil.getLabel(unit);
	}
	
	@Override
	protected Class<Unit> getRecordClass() {
		return Unit.class;
	}
	
	@Override
	protected Unit getRecord(UnitListObject rowObject) {
		return rowObject.getUnit();
	}
	
	@Override
	public Unit getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? UnitUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Unit unit) {
		super.setSelectedRecord(unit);
		fireSelectedEvent(unit);
	}
	
	protected void fireSelectedEvent(Unit unit) {
		unitEventManager.fireSelectedEvent(unit);
	}
	
	public boolean isSelected(Unit unit) {
		Unit selection = selectionContext.getSelection("unit");
		boolean selected = selection != null && selection.equals(unit);
		return selected;
	}
	
	@Override
	protected UnitListObject createRowObject(Unit unit) {
		UnitListObject listObject = new UnitListObject(unit);
		listObject.setSelected(isSelected(unit));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Unit> createRecordList() {
		try {
			Collection<Unit> unitList = unitDataManager.getUnitList();
			if (unitList != null)
				return unitList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewUnit() {
		return viewUnit(selectedRecordKey);
	}
	
	public String viewUnit(Object recordKey) {
		Unit unit = recordByKeyMap.get(recordKey);
		return viewUnit(unit);
	}
	
	public String viewUnit(Unit unit) {
		String url = unitInfoManager.viewUnit(unit);
		return url;
	}
	
	public String editUnit() {
		return editUnit(selectedRecordKey);
	}
	
	public String editUnit(Object recordKey) {
		Unit unit = recordByKeyMap.get(recordKey);
		return editUnit(unit);
	}
	
	public String editUnit(Unit unit) {
		String url = unitInfoManager.editUnit(unit);
		return url;
	}
	
	public void removeUnit() {
		removeUnit(selectedRecordKey);
	}
	
	public void removeUnit(Object recordKey) {
		Unit unit = recordByKeyMap.get(recordKey);
		removeUnit(unit);
	}
	
	public void removeUnit(Unit unit) {
		try {
			if (unitDataManager.removeUnit(unit))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelUnit(@Observes @Cancelled Unit unit) {
		try {
			//Object key = UnitUtil.getKey(unit);
			//recordByKeyMap.put(key, unit);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("unit");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateUnit(Collection<Unit> unitList) {
		return UnitUtil.validate(unitList);
	}
	
	public void exportUnitList(@Observes @Export String tableId) {
		//String tableId = "pageForm:unitListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
