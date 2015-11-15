package nam.model.minion;

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

import nam.model.Minion;
import nam.model.util.MinionUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("minionListManager")
public class MinionListManager extends AbstractDomainListManager<Minion, MinionListObject> implements Serializable {
	
	@Inject
	private MinionDataManager minionDataManager;
	
	@Inject
	private MinionEventManager minionEventManager;
	
	@Inject
	private MinionInfoManager minionInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "minionList";
	}
	
	@Override
	public String getTitle() {
		return "Minion List";
	}
	
	@Override
	public Object getRecordKey(Minion minion) {
		return MinionUtil.getKey(minion);
	}
	
	@Override
	public String getRecordName(Minion minion) {
		return MinionUtil.getLabel(minion);
	}
	
	@Override
	protected Class<Minion> getRecordClass() {
		return Minion.class;
	}
	
	@Override
	protected Minion getRecord(MinionListObject rowObject) {
		return rowObject.getMinion();
	}
	
	@Override
	public Minion getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? MinionUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Minion minion) {
		super.setSelectedRecord(minion);
		fireSelectedEvent(minion);
	}
	
	protected void fireSelectedEvent(Minion minion) {
		minionEventManager.fireSelectedEvent(minion);
	}
	
	public boolean isSelected(Minion minion) {
		Minion selection = selectionContext.getSelection("minion");
		boolean selected = selection != null && selection.equals(minion);
		return selected;
	}
	
	@Override
	protected MinionListObject createRowObject(Minion minion) {
		MinionListObject listObject = new MinionListObject(minion);
		listObject.setSelected(isSelected(minion));
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
	protected Collection<Minion> createRecordList() {
		try {
			Collection<Minion> minionList = minionDataManager.getMinionList();
			if (minionList != null)
				return minionList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewMinion() {
		return viewMinion(selectedRecordKey);
	}
	
	public String viewMinion(Object recordKey) {
		Minion minion = recordByKeyMap.get(recordKey);
		return viewMinion(minion);
	}
	
	public String viewMinion(Minion minion) {
		String url = minionInfoManager.viewMinion(minion);
		return url;
	}
	
	public String editMinion() {
		return editMinion(selectedRecordKey);
	}
	
	public String editMinion(Object recordKey) {
		Minion minion = recordByKeyMap.get(recordKey);
		return editMinion(minion);
	}
	
	public String editMinion(Minion minion) {
		String url = minionInfoManager.editMinion(minion);
		return url;
	}
	
	public void removeMinion() {
		removeMinion(selectedRecordKey);
	}
	
	public void removeMinion(Object recordKey) {
		Minion minion = recordByKeyMap.get(recordKey);
		removeMinion(minion);
	}
	
	public void removeMinion(Minion minion) {
		try {
			if (minionDataManager.removeMinion(minion))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelMinion(@Observes @Cancelled Minion minion) {
		try {
			//Object key = MinionUtil.getKey(minion);
			//recordByKeyMap.put(key, minion);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("minion");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateMinion(Collection<Minion> minionList) {
		return MinionUtil.validate(minionList);
	}
	
	public void exportMinionList(@Observes @Export String tableId) {
		//String tableId = "pageForm:minionListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
