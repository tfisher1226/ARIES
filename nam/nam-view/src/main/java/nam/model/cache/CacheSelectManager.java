package nam.model.cache;

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

import nam.model.Cache;
import nam.model.util.CacheUtil;


@SessionScoped
@Named("cacheSelectManager")
public class CacheSelectManager extends AbstractSelectManager<Cache, CacheListObject> implements Serializable {
	
	@Inject
	private CacheDataManager cacheDataManager;
	
	@Inject
	private CacheHelper cacheHelper;
	
	
	@Override
	public String getClientId() {
		return "cacheSelect";
	}
	
	@Override
	public String getTitle() {
		return "Cache Selection";
	}
	
	@Override
	protected Class<Cache> getRecordClass() {
		return Cache.class;
	}
	
	@Override
	public boolean isEmpty(Cache cache) {
		return cacheHelper.isEmpty(cache);
	}
	
	@Override
	public String toString(Cache cache) {
		return cacheHelper.toString(cache);
	}
	
	protected CacheHelper getCacheHelper() {
		return BeanContext.getFromSession("cacheHelper");
	}
	
	protected CacheListManager getCacheListManager() {
		return BeanContext.getFromSession("cacheListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshCacheList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Cache> recordList) {
		CacheListManager cacheListManager = getCacheListManager();
		DataModel<CacheListObject> dataModel = cacheListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshCacheList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Cache> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Cache> cacheList = BeanContext.getFromConversation(instanceId);
		return cacheList;
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
	public void sortRecords(List<Cache> cacheList) {
		Collections.sort(cacheList, new Comparator<Cache>() {
			public int compare(Cache cache1, Cache cache2) {
				String text1 = CacheUtil.toString(cache1);
				String text2 = CacheUtil.toString(cache2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
