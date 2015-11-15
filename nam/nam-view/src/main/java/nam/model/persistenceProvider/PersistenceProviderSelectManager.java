package nam.model.persistenceProvider;

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

import nam.model.PersistenceProvider;
import nam.model.util.PersistenceProviderUtil;


@SessionScoped
@Named("persistenceProviderSelectManager")
public class PersistenceProviderSelectManager extends AbstractSelectManager<PersistenceProvider, PersistenceProviderListObject> implements Serializable {
	
	@Inject
	private PersistenceProviderDataManager persistenceProviderDataManager;
	
	@Inject
	private PersistenceProviderHelper persistenceProviderHelper;
	
	
	@Override
	public String getClientId() {
		return "persistenceProviderSelect";
	}
	
	@Override
	public String getTitle() {
		return "PersistenceProvider Selection";
	}
	
	@Override
	protected Class<PersistenceProvider> getRecordClass() {
		return PersistenceProvider.class;
	}
	
	@Override
	public boolean isEmpty(PersistenceProvider persistenceProvider) {
		return persistenceProviderHelper.isEmpty(persistenceProvider);
	}
	
	@Override
	public String toString(PersistenceProvider persistenceProvider) {
		return persistenceProviderHelper.toString(persistenceProvider);
	}
	
	protected PersistenceProviderHelper getPersistenceProviderHelper() {
		return BeanContext.getFromSession("persistenceProviderHelper");
	}
	
	protected PersistenceProviderListManager getPersistenceProviderListManager() {
		return BeanContext.getFromSession("persistenceProviderListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshPersistenceProviderList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<PersistenceProvider> recordList) {
		PersistenceProviderListManager persistenceProviderListManager = getPersistenceProviderListManager();
		DataModel<PersistenceProviderListObject> dataModel = persistenceProviderListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshPersistenceProviderList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<PersistenceProvider> refreshRecords() {
		try {
			Collection<PersistenceProvider> records = persistenceProviderDataManager.getPersistenceProviderList();
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
	public void sortRecords(List<PersistenceProvider> persistenceProviderList) {
		Collections.sort(persistenceProviderList, new Comparator<PersistenceProvider>() {
			public int compare(PersistenceProvider persistenceProvider1, PersistenceProvider persistenceProvider2) {
				String text1 = PersistenceProviderUtil.toString(persistenceProvider1);
				String text2 = PersistenceProviderUtil.toString(persistenceProvider2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
