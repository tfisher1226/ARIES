package nam.model.listener;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Listener;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("listenerEventManager")
public class ListenerEventManager extends AbstractEventManager<Listener> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Listener getInstance() {
		return selectionContext.getSelection("listener");
	}
	
	public void removeListener() {
		Listener listener = getInstance();
		fireRemoveEvent(listener);
	}
	
}
