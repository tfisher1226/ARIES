package nam.model.interactor;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Interactor;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("interactorEventManager")
public class InteractorEventManager extends AbstractEventManager<Interactor> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Interactor getInstance() {
		return selectionContext.getSelection("interactor");
	}
	
	public void removeInteractor() {
		Interactor interactor = getInstance();
		fireRemoveEvent(interactor);
	}
	
}
