package nam.model.repository;

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

import nam.model.Repository;
import nam.model.util.RepositoryUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("repositoryListManager")
public class RepositoryListManager extends AbstractDomainListManager<Repository, RepositoryListObject> implements Serializable {
	
	@Inject
	private RepositoryDataManager repositoryDataManager;
	
	@Inject
	private RepositoryEventManager repositoryEventManager;
	
	@Inject
	private RepositoryInfoManager repositoryInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "repositoryList";
	}
	
	@Override
	public String getTitle() {
		return "Repository List";
	}
	
	@Override
	public Object getRecordKey(Repository repository) {
		return RepositoryUtil.getKey(repository);
	}
	
	@Override
	public String getRecordName(Repository repository) {
		return RepositoryUtil.getLabel(repository);
	}
	
	@Override
	protected Class<Repository> getRecordClass() {
		return Repository.class;
	}
	
	@Override
	protected Repository getRecord(RepositoryListObject rowObject) {
		return rowObject.getRepository();
	}
	
	@Override
	public Repository getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? RepositoryUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Repository repository) {
		super.setSelectedRecord(repository);
		fireSelectedEvent(repository);
	}
	
	protected void fireSelectedEvent(Repository repository) {
		repositoryEventManager.fireSelectedEvent(repository);
	}
	
	public boolean isSelected(Repository repository) {
		Repository selection = selectionContext.getSelection("repository");
		boolean selected = selection != null && selection.equals(repository);
		return selected;
	}
	
	@Override
	protected RepositoryListObject createRowObject(Repository repository) {
		RepositoryListObject listObject = new RepositoryListObject(repository);
		listObject.setSelected(isSelected(repository));
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
	protected Collection<Repository> createRecordList() {
		try {
			Collection<Repository> repositoryList = repositoryDataManager.getRepositoryList();
			if (repositoryList != null)
				return repositoryList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewRepository() {
		return viewRepository(selectedRecordKey);
	}
	
	public String viewRepository(Object recordKey) {
		Repository repository = recordByKeyMap.get(recordKey);
		return viewRepository(repository);
	}
	
	public String viewRepository(Repository repository) {
		String url = repositoryInfoManager.viewRepository(repository);
		return url;
	}
	
	public String editRepository() {
		return editRepository(selectedRecordKey);
	}
	
	public String editRepository(Object recordKey) {
		Repository repository = recordByKeyMap.get(recordKey);
		return editRepository(repository);
	}
	
	public String editRepository(Repository repository) {
		String url = repositoryInfoManager.editRepository(repository);
		return url;
	}
	
	public void removeRepository() {
		removeRepository(selectedRecordKey);
	}
	
	public void removeRepository(Object recordKey) {
		Repository repository = recordByKeyMap.get(recordKey);
		removeRepository(repository);
	}
	
	public void removeRepository(Repository repository) {
		try {
			if (repositoryDataManager.removeRepository(repository))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelRepository(@Observes @Cancelled Repository repository) {
		try {
			//Object key = RepositoryUtil.getKey(repository);
			//recordByKeyMap.put(key, repository);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("repository");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateRepository(Collection<Repository> repositoryList) {
		return RepositoryUtil.validate(repositoryList);
	}
	
	public void exportRepositoryList(@Observes @Export String tableId) {
		//String tableId = "pageForm:repositoryListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
