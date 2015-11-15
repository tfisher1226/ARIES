package nam.model.persistence;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Persistence;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("persistenceEventManager")
public class PersistenceEventManager extends AbstractEventManager<Persistence> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Persistence getInstance() {
		return selectionContext.getSelection("persistence");
	}
	
	public void removePersistence() {
		Persistence persistence = getInstance();
		fireRemoveEvent(persistence);
	}
	
}
