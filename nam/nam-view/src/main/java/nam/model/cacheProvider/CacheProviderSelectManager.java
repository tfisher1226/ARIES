package nam.model.cacheProvider;

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

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;


@SessionScoped
@Named("cacheProviderSelectManager")
public class CacheProviderSelectManager extends AbstractSelectManager<CacheProvider, CacheProviderListObject> implements Serializable {
	
	@Inject
	private CacheProviderDataManager cacheProviderDataManager;
	
	@Inject
	private CacheProviderHelper cacheProviderHelper;
	
	
	@Override
	public String getClientId() {
		return "cacheProviderSelect";
	}
	
	@Override
	public String getTitle() {
		return "CacheProvider Selection";
	}
	
	@Override
	protected Class<CacheProvider> getRecordClass() {
		return CacheProvider.class;
	}
	
	@Override
	public boolean isEmpty(CacheProvider cacheProvider) {
		return cacheProviderHelper.isEmpty(cacheProvider);
	}
	
	@Override
	public String toString(CacheProvider cacheProvider) {
		return cacheProviderHelper.toString(cacheProvider);
	}
	
	protected CacheProviderHelper getCacheProviderHelper() {
		return BeanContext.getFromSession("cacheProviderHelper");
	}
	
	protected CacheProviderListManager getCacheProviderListManager() {
		return BeanContext.getFromSession("cacheProviderListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshCacheProviderList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<CacheProvider> recordList) {
		CacheProviderListManager cacheProviderListManager = getCacheProviderListManager();
		DataModel<CacheProviderListObject> dataModel = cacheProviderListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshCacheProviderList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<CacheProvider> refreshRecords() {
		try {
			Collection<CacheProvider> records = cacheProviderDataManager.getCacheProviderList();
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
	public void sortRecords(List<CacheProvider> cacheProviderList) {
		Collections.sort(cacheProviderList, new Comparator<CacheProvider>() {
			public int compare(CacheProvider cacheProvider1, CacheProvider cacheProvider2) {
				String text1 = CacheProviderUtil.toString(cacheProvider1);
				String text2 = CacheProviderUtil.toString(cacheProvider2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
