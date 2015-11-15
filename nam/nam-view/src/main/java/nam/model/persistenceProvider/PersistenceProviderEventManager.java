package nam.model.persistenceProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.PersistenceProvider;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("persistenceProviderEventManager")
public class PersistenceProviderEventManager extends AbstractEventManager<PersistenceProvider> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public PersistenceProvider getInstance() {
		return selectionContext.getSelection("persistenceProvider");
	}
	
	public void removePersistenceProvider() {
		PersistenceProvider persistenceProvider = getInstance();
		fireRemoveEvent(persistenceProvider);
	}
	
}
