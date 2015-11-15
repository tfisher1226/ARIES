package nam.model.persistence;

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

import nam.model.Persistence;
import nam.model.util.PersistenceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("persistenceListManager")
public class PersistenceListManager extends AbstractDomainListManager<Persistence, PersistenceListObject> implements Serializable {
	
	@Inject
	private PersistenceDataManager persistenceDataManager;
	
	@Inject
	private PersistenceEventManager persistenceEventManager;
	
	@Inject
	private PersistenceInfoManager persistenceInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "persistenceList";
	}
	
	@Override
	public String getTitle() {
		return "Persistence List";
	}
	
	@Override
	public Object getRecordKey(Persistence persistence) {
		return PersistenceUtil.getKey(persistence);
	}
	
	@Override
	public String getRecordName(Persistence persistence) {
		return PersistenceUtil.getLabel(persistence);
	}
	
	@Override
	protected Class<Persistence> getRecordClass() {
		return Persistence.class;
	}
	
	@Override
	protected Persistence getRecord(PersistenceListObject rowObject) {
		return rowObject.getPersistence();
	}
	
	@Override
	public Persistence getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? PersistenceUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Persistence persistence) {
		super.setSelectedRecord(persistence);
		fireSelectedEvent(persistence);
	}
	
	protected void fireSelectedEvent(Persistence persistence) {
		persistenceEventManager.fireSelectedEvent(persistence);
	}
	
	public boolean isSelected(Persistence persistence) {
		Persistence selection = selectionContext.getSelection("persistence");
		boolean selected = selection != null && selection.equals(persistence);
		return selected;
	}
	
	@Override
	protected PersistenceListObject createRowObject(Persistence persistence) {
		PersistenceListObject listObject = new PersistenceListObject(persistence);
		listObject.setSelected(isSelected(persistence));
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
	protected Collection<Persistence> createRecordList() {
		try {
			Collection<Persistence> persistenceList = persistenceDataManager.getPersistenceList();
			if (persistenceList != null)
				return persistenceList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewPersistence() {
		return viewPersistence(selectedRecordKey);
	}
	
	public String viewPersistence(Object recordKey) {
		Persistence persistence = recordByKeyMap.get(recordKey);
		return viewPersistence(persistence);
	}
	
	public String viewPersistence(Persistence persistence) {
		String url = persistenceInfoManager.viewPersistence(persistence);
		return url;
	}
	
	public String editPersistence() {
		return editPersistence(selectedRecordKey);
	}
	
	public String editPersistence(Object recordKey) {
		Persistence persistence = recordByKeyMap.get(recordKey);
		return editPersistence(persistence);
	}
	
	public String editPersistence(Persistence persistence) {
		String url = persistenceInfoManager.editPersistence(persistence);
		return url;
	}
	
	public void removePersistence() {
		removePersistence(selectedRecordKey);
	}
	
	public void removePersistence(Object recordKey) {
		Persistence persistence = recordByKeyMap.get(recordKey);
		removePersistence(persistence);
	}
	
	public void removePersistence(Persistence persistence) {
		try {
			if (persistenceDataManager.removePersistence(persistence))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelPersistence(@Observes @Cancelled Persistence persistence) {
		try {
			//Object key = PersistenceUtil.getKey(persistence);
			//recordByKeyMap.put(key, persistence);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("persistence");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validatePersistence(Collection<Persistence> persistenceList) {
		return PersistenceUtil.validate(persistenceList);
	}
	
	public void exportPersistenceList(@Observes @Export String tableId) {
		//String tableId = "pageForm:persistenceListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
