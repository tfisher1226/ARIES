package nam.model.master;

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

import nam.model.Master;
import nam.model.util.MasterUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("masterListManager")
public class MasterListManager extends AbstractDomainListManager<Master, MasterListObject> implements Serializable {
	
	@Inject
	private MasterDataManager masterDataManager;
	
	@Inject
	private MasterEventManager masterEventManager;
	
	@Inject
	private MasterInfoManager masterInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "masterList";
	}
	
	@Override
	public String getTitle() {
		return "Master List";
	}
	
	@Override
	public Object getRecordKey(Master master) {
		return MasterUtil.getKey(master);
	}
	
	@Override
	public String getRecordName(Master master) {
		return MasterUtil.getLabel(master);
	}
	
	@Override
	protected Class<Master> getRecordClass() {
		return Master.class;
	}
	
	@Override
	protected Master getRecord(MasterListObject rowObject) {
		return rowObject.getMaster();
	}
	
	@Override
	public Master getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? MasterUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Master master) {
		super.setSelectedRecord(master);
		fireSelectedEvent(master);
	}
	
	protected void fireSelectedEvent(Master master) {
		masterEventManager.fireSelectedEvent(master);
	}
	
	public boolean isSelected(Master master) {
		Master selection = selectionContext.getSelection("master");
		boolean selected = selection != null && selection.equals(master);
		return selected;
	}
	
	@Override
	protected MasterListObject createRowObject(Master master) {
		MasterListObject listObject = new MasterListObject(master);
		listObject.setSelected(isSelected(master));
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
	protected Collection<Master> createRecordList() {
		try {
			Collection<Master> masterList = masterDataManager.getMasterList();
			if (masterList != null)
				return masterList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewMaster() {
		return viewMaster(selectedRecordKey);
	}
	
	public String viewMaster(Object recordKey) {
		Master master = recordByKeyMap.get(recordKey);
		return viewMaster(master);
	}
	
	public String viewMaster(Master master) {
		String url = masterInfoManager.viewMaster(master);
		return url;
	}
	
	public String editMaster() {
		return editMaster(selectedRecordKey);
	}
	
	public String editMaster(Object recordKey) {
		Master master = recordByKeyMap.get(recordKey);
		return editMaster(master);
	}
	
	public String editMaster(Master master) {
		String url = masterInfoManager.editMaster(master);
		return url;
	}
	
	public void removeMaster() {
		removeMaster(selectedRecordKey);
	}
	
	public void removeMaster(Object recordKey) {
		Master master = recordByKeyMap.get(recordKey);
		removeMaster(master);
	}
	
	public void removeMaster(Master master) {
		try {
			if (masterDataManager.removeMaster(master))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelMaster(@Observes @Cancelled Master master) {
		try {
			//Object key = MasterUtil.getKey(master);
			//recordByKeyMap.put(key, master);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("master");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateMaster(Collection<Master> masterList) {
		return MasterUtil.validate(masterList);
	}
	
	public void exportMasterList(@Observes @Export String tableId) {
		//String tableId = "pageForm:masterListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
