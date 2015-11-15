package nam.model.persistence;

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

import nam.model.Persistence;
import nam.model.util.PersistenceUtil;


@SessionScoped
@Named("persistenceSelectManager")
public class PersistenceSelectManager extends AbstractSelectManager<Persistence, PersistenceListObject> implements Serializable {
	
	@Inject
	private PersistenceDataManager persistenceDataManager;
	
	@Inject
	private PersistenceHelper persistenceHelper;
	
	
	@Override
	public String getClientId() {
		return "persistenceSelect";
	}
	
	@Override
	public String getTitle() {
		return "Persistence Selection";
	}
	
	@Override
	protected Class<Persistence> getRecordClass() {
		return Persistence.class;
	}
	
	@Override
	public boolean isEmpty(Persistence persistence) {
		return persistenceHelper.isEmpty(persistence);
	}
	
	@Override
	public String toString(Persistence persistence) {
		return persistenceHelper.toString(persistence);
	}
	
	protected PersistenceHelper getPersistenceHelper() {
		return BeanContext.getFromSession("persistenceHelper");
	}
	
	protected PersistenceListManager getPersistenceListManager() {
		return BeanContext.getFromSession("persistenceListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshPersistenceList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Persistence> recordList) {
		PersistenceListManager persistenceListManager = getPersistenceListManager();
		DataModel<PersistenceListObject> dataModel = persistenceListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshPersistenceList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Persistence> refreshRecords() {
		try {
			Collection<Persistence> records = persistenceDataManager.getPersistenceList();
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
	public void sortRecords(List<Persistence> persistenceList) {
		Collections.sort(persistenceList, new Comparator<Persistence>() {
			public int compare(Persistence persistence1, Persistence persistence2) {
				String text1 = PersistenceUtil.toString(persistence1);
				String text2 = PersistenceUtil.toString(persistence2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
