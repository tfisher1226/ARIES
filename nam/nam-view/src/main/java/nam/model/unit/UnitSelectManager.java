package nam.model.unit;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Unit;
import nam.model.util.UnitUtil;


@SessionScoped
@Named("unitSelectManager")
public class UnitSelectManager extends AbstractSelectManager<Unit, UnitListObject> implements Serializable {
	
	@Inject
	private UnitDataManager unitDataManager;
	
	@Inject
	private UnitHelper unitHelper;
	
	
	@Override
	public String getClientId() {
		return "unitSelect";
	}
	
	@Override
	public String getTitle() {
		return "Unit Selection";
	}
	
	@Override
	protected Class<Unit> getRecordClass() {
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
	
	protected UnitHelper getUnitHelper() {
		return BeanContext.getFromSession("unitHelper");
	}
	
	protected UnitListManager getUnitListManager() {
		return BeanContext.getFromSession("unitListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshUnitList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Unit> recordList) {
		UnitListManager unitListManager = getUnitListManager();
		DataModel<UnitListObject> dataModel = unitListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshUnitList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Unit> refreshRecords() {
		try {
			Collection<Unit> records = unitDataManager.getUnitList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Unit> unitList) {
		Collections.sort(unitList, new Comparator<Unit>() {
			public int compare(Unit unit1, Unit unit2) {
				String text1 = UnitUtil.toString(unit1);
				String text2 = UnitUtil.toString(unit2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
