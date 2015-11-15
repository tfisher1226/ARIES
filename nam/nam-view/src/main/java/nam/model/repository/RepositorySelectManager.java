package nam.model.repository;

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

import nam.model.Repository;
import nam.model.util.RepositoryUtil;


@SessionScoped
@Named("repositorySelectManager")
public class RepositorySelectManager extends AbstractSelectManager<Repository, RepositoryListObject> implements Serializable {
	
	@Inject
	private RepositoryDataManager repositoryDataManager;
	
	@Inject
	private RepositoryHelper repositoryHelper;
	
	
	@Override
	public String getClientId() {
		return "repositorySelect";
	}
	
	@Override
	public String getTitle() {
		return "Repository Selection";
	}
	
	@Override
	protected Class<Repository> getRecordClass() {
		return Repository.class;
	}
	
	@Override
	public boolean isEmpty(Repository repository) {
		return repositoryHelper.isEmpty(repository);
	}
	
	@Override
	public String toString(Repository repository) {
		return repositoryHelper.toString(repository);
	}
	
	protected RepositoryHelper getRepositoryHelper() {
		return BeanContext.getFromSession("repositoryHelper");
	}
	
	protected RepositoryListManager getRepositoryListManager() {
		return BeanContext.getFromSession("repositoryListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshRepositoryList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Repository> recordList) {
		RepositoryListManager repositoryListManager = getRepositoryListManager();
		DataModel<RepositoryListObject> dataModel = repositoryListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshRepositoryList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Repository> refreshRecords() {
		try {
			Collection<Repository> records = repositoryDataManager.getRepositoryList();
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
	public void sortRecords(List<Repository> repositoryList) {
		Collections.sort(repositoryList, new Comparator<Repository>() {
			public int compare(Repository repository1, Repository repository2) {
				String text1 = RepositoryUtil.toString(repository1);
				String text2 = RepositoryUtil.toString(repository2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
