package nam.model.repository;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Repository;
import nam.model.util.RepositoryUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("repositoryWizard")
@SuppressWarnings("serial")
public class RepositoryWizard extends AbstractDomainElementWizard<Repository> implements Serializable {
	
	@Inject
	private RepositoryDataManager repositoryDataManager;
	
	@Inject
	private RepositoryPageManager repositoryPageManager;
	
	@Inject
	private RepositoryEventManager repositoryEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Repository";
	}
	
	@Override
	public String getUrlContext() {
		return repositoryPageManager.getRepositoryWizardPage();
	}
	
	@Override
	public void initialize(Repository repository) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(repositoryPageManager.getSections());
		super.initialize(repository);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		repositoryPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		repositoryPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		repositoryPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		repositoryPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Repository repository = getInstance();
		repositoryDataManager.saveRepository(repository);
		repositoryEventManager.fireSavedEvent(repository);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Repository repository = getInstance();
		//TODO take this out soon
		if (repository == null)
			repository = new Repository();
		repositoryEventManager.fireCancelledEvent(repository);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Repository repository = selectionContext.getSelection("repository");
		String name = repository.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("repositoryWizard");
			display.error("Repository name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
