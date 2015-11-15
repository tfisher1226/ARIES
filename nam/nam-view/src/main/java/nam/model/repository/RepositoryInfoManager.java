package nam.model.repository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.Repository;
import nam.model.util.ProjectUtil;
import nam.model.util.RepositoryUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("repositoryInfoManager")
public class RepositoryInfoManager extends AbstractNamRecordManager<Repository> implements Serializable {
	
	@Inject
	private RepositoryWizard repositoryWizard;
	
	@Inject
	private RepositoryDataManager repositoryDataManager;
	
	@Inject
	private RepositoryPageManager repositoryPageManager;
	
	@Inject
	private RepositoryEventManager repositoryEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private RepositoryHelper repositoryHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public RepositoryInfoManager() {
		setInstanceName("repository");
	}
	
	
	public Repository getRepository() {
		return getRecord();
	}
	
	public Repository getSelectedRepository() {
		return selectionContext.getSelection("repository");
	}
	
	@Override
	public Class<Repository> getRecordClass() {
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
	
	@Override
	public void initialize() {
		Repository repository = selectionContext.getSelection("repository");
		if (repository != null)
			initialize(repository);
	}
	
	protected void initialize(Repository repository) {
		RepositoryUtil.initialize(repository);
		repositoryWizard.initialize(repository);
		setContext("repository", repository);
	}
	
	public void handleRepositorySelected(@Observes @Selected Repository repository) {
		selectionContext.setSelection("repository",  repository);
		repositoryPageManager.updateState(repository);
		repositoryPageManager.refreshMembers();
		setRecord(repository);
	}
	
	@Override
	public String newRecord() {
		return newRepository();
	}
	
	public String newRepository() {
		try {
			Repository repository = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("repository",  repository);
			String url = repositoryPageManager.initializeRepositoryCreationPage(repository);
			repositoryPageManager.pushContext(repositoryWizard);
			initialize(repository);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Repository create() {
		Repository repository = RepositoryUtil.create();
		return repository;
	}
	
	@Override
	public Repository clone(Repository repository) {
		repository = RepositoryUtil.clone(repository);
		return repository;
	}
	
	@Override
	public String viewRecord() {
		return viewRepository();
	}
	
	public String viewRepository() {
		Repository repository = selectionContext.getSelection("repository");
		String url = viewRepository(repository);
		return url;
	}
	
	public String viewRepository(Repository repository) {
		try {
			String url = repositoryPageManager.initializeRepositorySummaryView(repository);
			repositoryPageManager.pushContext(repositoryWizard);
			initialize(repository);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editRepository();
	}
	
	public String editRepository() {
		Repository repository = selectionContext.getSelection("repository");
		String url = editRepository(repository);
		return url;
	}
	
	public String editRepository(Repository repository) {
		try {
			//repository = clone(repository);
			selectionContext.resetOrigin();
			selectionContext.setSelection("repository",  repository);
			String url = repositoryPageManager.initializeRepositoryUpdatePage(repository);
			repositoryPageManager.pushContext(repositoryWizard);
			initialize(repository);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveRepository() {
		Repository repository = getRepository();
		if (validateRepository(repository)) {
			if (isImmediate())
				persistRepository(repository);
			outject("repository", repository);
		}
	}
	
	public void persistRepository(Repository repository) {
		saveRepository(repository);
	}
	
	public void saveRepository(Repository repository) {
		try {
			saveRepositoryToSystem(repository);
			repositoryEventManager.fireAddedEvent(repository);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveRepositoryToSystem(Repository repository) {
		repositoryDataManager.saveRepository(repository);
	}
	
	public void handleSaveRepository(@Observes @Add Repository repository) {
		saveRepository(repository);
	}
	
	public void addRepository(Repository repository) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichRepository(Repository repository) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Repository repository) {
		return validateRepository(repository);
	}
	
	public boolean validateRepository(Repository repository) {
		Validator validator = getValidator();
		boolean isValid = RepositoryUtil.validate(repository);
		Display display = getFromSession("display");
		display.setModule("repositoryInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveRepository() {
		display = getFromSession("display");
		display.setModule("repositoryInfo");
		Repository repository = selectionContext.getSelection("repository");
		if (repository == null) {
			display.error("Repository record must be selected.");
		}
	}
	
	public String handleRemoveRepository(@Observes @Remove Repository repository) {
		display = getFromSession("display");
		display.setModule("repositoryInfo");
		try {
			display.info("Removing Repository "+RepositoryUtil.getLabel(repository)+" from the system.");
			removeRepositoryFromSystem(repository);
			selectionContext.clearSelection("repository");
			repositoryEventManager.fireClearSelectionEvent();
			repositoryEventManager.fireRemovedEvent(repository);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeRepositoryFromSystem(Repository repository) {
		if (repositoryDataManager.removeRepository(repository))
			setRecord(null);
	}
	
	public void cancelRepository() {
		BeanContext.removeFromSession("repository");
		repositoryPageManager.removeContext(repositoryWizard);
	}
	
}
