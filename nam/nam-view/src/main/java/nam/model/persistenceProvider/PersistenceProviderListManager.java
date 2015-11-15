package nam.model.persistenceProvider;

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

import nam.model.PersistenceProvider;
import nam.model.util.PersistenceProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("persistenceProviderListManager")
public class PersistenceProviderListManager extends AbstractDomainListManager<PersistenceProvider, PersistenceProviderListObject> implements Serializable {
	
	@Inject
	private PersistenceProviderDataManager persistenceProviderDataManager;
	
	@Inject
	private PersistenceProviderEventManager persistenceProviderEventManager;
	
	@Inject
	private PersistenceProviderInfoManager persistenceProviderInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "persistenceProviderList";
	}
	
	@Override
	public String getTitle() {
		return "PersistenceProvider List";
	}
	
	@Override
	public Object getRecordKey(PersistenceProvider persistenceProvider) {
		return PersistenceProviderUtil.getKey(persistenceProvider);
	}
	
	@Override
	public String getRecordName(PersistenceProvider persistenceProvider) {
		return PersistenceProviderUtil.toString(persistenceProvider);
	}
	
	@Override
	protected Class<PersistenceProvider> getRecordClass() {
		return PersistenceProvider.class;
	}
	
	@Override
	protected PersistenceProvider getRecord(PersistenceProviderListObject rowObject) {
		return rowObject.getPersistenceProvider();
	}
	
	@Override
	public PersistenceProvider getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? PersistenceProviderUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(PersistenceProvider persistenceProvider) {
		super.setSelectedRecord(persistenceProvider);
		fireSelectedEvent(persistenceProvider);
	}
	
	protected void fireSelectedEvent(PersistenceProvider persistenceProvider) {
		persistenceProviderEventManager.fireSelectedEvent(persistenceProvider);
	}
	
	public boolean isSelected(PersistenceProvider persistenceProvider) {
		PersistenceProvider selection = selectionContext.getSelection("persistenceProvider");
		boolean selected = selection != null && selection.equals(persistenceProvider);
		return selected;
	}
	
	@Override
	protected PersistenceProviderListObject createRowObject(PersistenceProvider persistenceProvider) {
		PersistenceProviderListObject listObject = new PersistenceProviderListObject(persistenceProvider);
		listObject.setSelected(isSelected(persistenceProvider));
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
	protected Collection<PersistenceProvider> createRecordList() {
		try {
			Collection<PersistenceProvider> persistenceProviderList = persistenceProviderDataManager.getPersistenceProviderList();
			if (persistenceProviderList != null)
				return persistenceProviderList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewPersistenceProvider() {
		return viewPersistenceProvider(selectedRecordKey);
	}
	
	public String viewPersistenceProvider(Object recordKey) {
		PersistenceProvider persistenceProvider = recordByKeyMap.get(recordKey);
		return viewPersistenceProvider(persistenceProvider);
	}
	
	public String viewPersistenceProvider(PersistenceProvider persistenceProvider) {
		PersistenceProviderInfoManager persistenceProviderInfoManager = BeanContext.getFromSession("persistenceProviderInfoManager");
		String url = persistenceProviderInfoManager.viewPersistenceProvider(persistenceProvider);
		return url;
	}
	
	public String editPersistenceProvider() {
		return editPersistenceProvider(selectedRecordKey);
	}
	
	public String editPersistenceProvider(Object recordKey) {
		PersistenceProvider persistenceProvider = recordByKeyMap.get(recordKey);
		return editPersistenceProvider(persistenceProvider);
	}
	
	public String editPersistenceProvider(PersistenceProvider persistenceProvider) {
		PersistenceProviderInfoManager persistenceProviderInfoManager = BeanContext.getFromSession("persistenceProviderInfoManager");
		String url = persistenceProviderInfoManager.editPersistenceProvider(persistenceProvider);
		return url;
	}
	
	public void removePersistenceProvider() {
		removePersistenceProvider(selectedRecordKey);
	}
	
	public void removePersistenceProvider(Object recordKey) {
		PersistenceProvider persistenceProvider = recordByKeyMap.get(recordKey);
		removePersistenceProvider(persistenceProvider);
	}
	
	public void removePersistenceProvider(PersistenceProvider persistenceProvider) {
		try {
			if (persistenceProviderDataManager.removePersistenceProvider(persistenceProvider))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelPersistenceProvider(@Observes @Cancelled PersistenceProvider persistenceProvider) {
		try {
			//Object key = PersistenceProviderUtil.getKey(persistenceProvider);
			//recordByKeyMap.put(key, persistenceProvider);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("persistenceProvider");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validatePersistenceProvider(Collection<PersistenceProvider> persistenceProviderList) {
		return PersistenceProviderUtil.validate(persistenceProviderList);
	}
	
	public void exportPersistenceProviderList(@Observes @Export String tableId) {
		//String tableId = "pageForm:persistenceProviderListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
