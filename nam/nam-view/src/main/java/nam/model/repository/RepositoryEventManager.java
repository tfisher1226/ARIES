package nam.model.repository;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Repository;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("repositoryEventManager")
public class RepositoryEventManager extends AbstractEventManager<Repository> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Repository getInstance() {
		return selectionContext.getSelection("repository");
	}
	
	public void removeRepository() {
		Repository repository = getInstance();
		fireRemoveEvent(repository);
	}
	
}
