package nam.model.provider;

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

import nam.model.Provider;
import nam.model.util.ProviderUtil;


@SessionScoped
@Named("providerSelectManager")
public class ProviderSelectManager extends AbstractSelectManager<Provider, ProviderListObject> implements Serializable {
	
	@Inject
	private ProviderDataManager providerDataManager;
	
	@Inject
	private ProviderHelper providerHelper;
	
	
	@Override
	public String getClientId() {
		return "providerSelect";
	}
	
	@Override
	public String getTitle() {
		return "Provider Selection";
	}
	
	@Override
	protected Class<Provider> getRecordClass() {
		return Provider.class;
	}
	
	@Override
	public boolean isEmpty(Provider provider) {
		return providerHelper.isEmpty(provider);
	}
	
	@Override
	public String toString(Provider provider) {
		return providerHelper.toString(provider);
	}
	
	protected ProviderHelper getProviderHelper() {
		return BeanContext.getFromSession("providerHelper");
	}
	
	protected ProviderListManager getProviderListManager() {
		return BeanContext.getFromSession("providerListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshProviderList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Provider> recordList) {
		ProviderListManager providerListManager = getProviderListManager();
		DataModel<ProviderListObject> dataModel = providerListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshProviderList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Provider> refreshRecords() {
		try {
			Collection<Provider> records = providerDataManager.getProviderList();
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
	public void sortRecords(List<Provider> providerList) {
		Collections.sort(providerList, new Comparator<Provider>() {
			public int compare(Provider provider1, Provider provider2) {
				String text1 = ProviderUtil.toString(provider1);
				String text2 = ProviderUtil.toString(provider2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
