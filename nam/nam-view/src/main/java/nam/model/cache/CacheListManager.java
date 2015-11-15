package nam.model.cache;

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

import nam.model.Cache;
import nam.model.util.CacheUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cacheListManager")
public class CacheListManager extends AbstractDomainListManager<Cache, CacheListObject> implements Serializable {
	
	@Inject
	private CacheDataManager cacheDataManager;
	
	@Inject
	private CacheEventManager cacheEventManager;
	
	@Inject
	private CacheInfoManager cacheInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "cacheList";
	}
	
	@Override
	public String getTitle() {
		return "Cache List";
	}
	
	@Override
	public Object getRecordKey(Cache cache) {
		return CacheUtil.getKey(cache);
	}
	
	@Override
	public String getRecordName(Cache cache) {
		return CacheUtil.getLabel(cache);
	}
	
	@Override
	protected Class<Cache> getRecordClass() {
		return Cache.class;
	}
	
	@Override
	protected Cache getRecord(CacheListObject rowObject) {
		return rowObject.getCache();
	}
	
	@Override
	public Cache getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? CacheUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Cache cache) {
		super.setSelectedRecord(cache);
		fireSelectedEvent(cache);
	}
	
	protected void fireSelectedEvent(Cache cache) {
		cacheEventManager.fireSelectedEvent(cache);
	}
	
	public boolean isSelected(Cache cache) {
		Cache selection = selectionContext.getSelection("cache");
		boolean selected = selection != null && selection.equals(cache);
		return selected;
	}
	
	@Override
	protected CacheListObject createRowObject(Cache cache) {
		CacheListObject listObject = new CacheListObject(cache);
		listObject.setSelected(isSelected(cache));
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
	protected Collection<Cache> createRecordList() {
		try {
			Collection<Cache> cacheList = cacheDataManager.getCacheList();
			if (cacheList != null)
				return cacheList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewCache() {
		return viewCache(selectedRecordKey);
	}
	
	public String viewCache(Object recordKey) {
		Cache cache = recordByKeyMap.get(recordKey);
		return viewCache(cache);
	}
	
	public String viewCache(Cache cache) {
		String url = cacheInfoManager.viewCache(cache);
		return url;
	}
	
	public String editCache() {
		return editCache(selectedRecordKey);
	}
	
	public String editCache(Object recordKey) {
		Cache cache = recordByKeyMap.get(recordKey);
		return editCache(cache);
	}
	
	public String editCache(Cache cache) {
		String url = cacheInfoManager.editCache(cache);
		return url;
	}
	
	public void removeCache() {
		removeCache(selectedRecordKey);
	}
	
	public void removeCache(Object recordKey) {
		Cache cache = recordByKeyMap.get(recordKey);
		removeCache(cache);
	}
	
	public void removeCache(Cache cache) {
		try {
			if (cacheDataManager.removeCache(cache))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelCache(@Observes @Cancelled Cache cache) {
		try {
			//Object key = CacheUtil.getKey(cache);
			//recordByKeyMap.put(key, cache);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("cache");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateCache(Collection<Cache> cacheList) {
		return CacheUtil.validate(cacheList);
	}
	
	public void exportCacheList(@Observes @Export String tableId) {
		//String tableId = "pageForm:cacheListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
