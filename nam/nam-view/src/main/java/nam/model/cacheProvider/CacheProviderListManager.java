package nam.model.cacheProvider;

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

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cacheProviderListManager")
public class CacheProviderListManager extends AbstractDomainListManager<CacheProvider, CacheProviderListObject> implements Serializable {
	
	@Inject
	private CacheProviderDataManager cacheProviderDataManager;
	
	@Inject
	private CacheProviderEventManager cacheProviderEventManager;
	
	@Inject
	private CacheProviderInfoManager cacheProviderInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "cacheProviderList";
	}
	
	@Override
	public String getTitle() {
		return "CacheProvider List";
	}
	
	@Override
	public Object getRecordKey(CacheProvider cacheProvider) {
		return CacheProviderUtil.getKey(cacheProvider);
	}
	
	@Override
	public String getRecordName(CacheProvider cacheProvider) {
		return CacheProviderUtil.toString(cacheProvider);
	}
	
	@Override
	protected Class<CacheProvider> getRecordClass() {
		return CacheProvider.class;
	}
	
	@Override
	protected CacheProvider getRecord(CacheProviderListObject rowObject) {
		return rowObject.getCacheProvider();
	}
	
	@Override
	public CacheProvider getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? CacheProviderUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(CacheProvider cacheProvider) {
		super.setSelectedRecord(cacheProvider);
		fireSelectedEvent(cacheProvider);
	}
	
	protected void fireSelectedEvent(CacheProvider cacheProvider) {
		cacheProviderEventManager.fireSelectedEvent(cacheProvider);
	}
	
	public boolean isSelected(CacheProvider cacheProvider) {
		CacheProvider selection = selectionContext.getSelection("cacheProvider");
		boolean selected = selection != null && selection.equals(cacheProvider);
		return selected;
	}
	
	@Override
	protected CacheProviderListObject createRowObject(CacheProvider cacheProvider) {
		CacheProviderListObject listObject = new CacheProviderListObject(cacheProvider);
		listObject.setSelected(isSelected(cacheProvider));
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
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<CacheProvider> createRecordList() {
		try {
			Collection<CacheProvider> cacheProviderList = cacheProviderDataManager.getCacheProviderList();
			if (cacheProviderList != null)
				return cacheProviderList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewCacheProvider() {
		return viewCacheProvider(selectedRecordKey);
	}
	
	public String viewCacheProvider(Object recordKey) {
		CacheProvider cacheProvider = recordByKeyMap.get(recordKey);
		return viewCacheProvider(cacheProvider);
	}
	
	public String viewCacheProvider(CacheProvider cacheProvider) {
		String url = cacheProviderInfoManager.viewCacheProvider(cacheProvider);
		return url;
	}
	
	public String editCacheProvider() {
		return editCacheProvider(selectedRecordKey);
	}
	
	public String editCacheProvider(Object recordKey) {
		CacheProvider cacheProvider = recordByKeyMap.get(recordKey);
		return editCacheProvider(cacheProvider);
	}
	
	public String editCacheProvider(CacheProvider cacheProvider) {
		String url = cacheProviderInfoManager.editCacheProvider(cacheProvider);
		return url;
	}
	
	public void removeCacheProvider() {
		removeCacheProvider(selectedRecordKey);
	}
	
	public void removeCacheProvider(Object recordKey) {
		CacheProvider cacheProvider = recordByKeyMap.get(recordKey);
		removeCacheProvider(cacheProvider);
	}
	
	public void removeCacheProvider(CacheProvider cacheProvider) {
		try {
			if (cacheProviderDataManager.removeCacheProvider(cacheProvider))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelCacheProvider(@Observes @Cancelled CacheProvider cacheProvider) {
		try {
			//Object key = CacheProviderUtil.getKey(cacheProvider);
			//recordByKeyMap.put(key, cacheProvider);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("cacheProvider");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateCacheProvider(Collection<CacheProvider> cacheProviderList) {
		return CacheProviderUtil.validate(cacheProviderList);
	}
	
	public void exportCacheProviderList(@Observes @Export String tableId) {
		//String tableId = "pageForm:cacheProviderListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
