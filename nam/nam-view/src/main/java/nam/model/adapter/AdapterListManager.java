package nam.model.adapter;

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

import nam.model.Adapter;
import nam.model.util.AdapterUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("adapterListManager")
public class AdapterListManager extends AbstractDomainListManager<Adapter, AdapterListObject> implements Serializable {
	
	@Inject
	private AdapterDataManager adapterDataManager;
	
	@Inject
	private AdapterEventManager adapterEventManager;
	
	@Inject
	private AdapterInfoManager adapterInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "adapterList";
	}
	
	@Override
	public String getTitle() {
		return "Adapter List";
	}
	
	@Override
	public Object getRecordKey(Adapter adapter) {
		return AdapterUtil.getKey(adapter);
	}
	
	@Override
	public String getRecordName(Adapter adapter) {
		return AdapterUtil.getLabel(adapter);
	}
	
	@Override
	protected Class<Adapter> getRecordClass() {
		return Adapter.class;
	}
	
	@Override
	protected Adapter getRecord(AdapterListObject rowObject) {
		return rowObject.getAdapter();
	}
	
	@Override
	public Adapter getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? AdapterUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Adapter adapter) {
		super.setSelectedRecord(adapter);
		fireSelectedEvent(adapter);
	}
	
	protected void fireSelectedEvent(Adapter adapter) {
		adapterEventManager.fireSelectedEvent(adapter);
	}
	
	public boolean isSelected(Adapter adapter) {
		Adapter selection = selectionContext.getSelection("adapter");
		boolean selected = selection != null && selection.equals(adapter);
		return selected;
	}
	
	@Override
	protected AdapterListObject createRowObject(Adapter adapter) {
		AdapterListObject listObject = new AdapterListObject(adapter);
		listObject.setSelected(isSelected(adapter));
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
	protected Collection<Adapter> createRecordList() {
		try {
			Collection<Adapter> adapterList = adapterDataManager.getAdapterList();
			if (adapterList != null)
				return adapterList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewAdapter() {
		return viewAdapter(selectedRecordKey);
	}
	
	public String viewAdapter(Object recordKey) {
		Adapter adapter = recordByKeyMap.get(recordKey);
		return viewAdapter(adapter);
	}
	
	public String viewAdapter(Adapter adapter) {
		String url = adapterInfoManager.viewAdapter(adapter);
		return url;
	}
	
	public String editAdapter() {
		return editAdapter(selectedRecordKey);
	}
	
	public String editAdapter(Object recordKey) {
		Adapter adapter = recordByKeyMap.get(recordKey);
		return editAdapter(adapter);
	}
	
	public String editAdapter(Adapter adapter) {
		String url = adapterInfoManager.editAdapter(adapter);
		return url;
	}
	
	public void removeAdapter() {
		removeAdapter(selectedRecordKey);
	}
	
	public void removeAdapter(Object recordKey) {
		Adapter adapter = recordByKeyMap.get(recordKey);
		removeAdapter(adapter);
	}
	
	public void removeAdapter(Adapter adapter) {
		try {
			if (adapterDataManager.removeAdapter(adapter))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelAdapter(@Observes @Cancelled Adapter adapter) {
		try {
			//Object key = AdapterUtil.getKey(adapter);
			//recordByKeyMap.put(key, adapter);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("adapter");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateAdapter(Collection<Adapter> adapterList) {
		return AdapterUtil.validate(adapterList);
	}
	
	public void exportAdapterList(@Observes @Export String tableId) {
		//String tableId = "pageForm:adapterListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
