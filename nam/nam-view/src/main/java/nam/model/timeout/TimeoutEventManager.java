package nam.model.timeout;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Timeout;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("timeoutEventManager")
public class TimeoutEventManager extends AbstractEventManager<Timeout> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Timeout getInstance() {
		return selectionContext.getSelection("timeout");
	}
	
	public void removeTimeout() {
		Timeout timeout = getInstance();
		fireRemoveEvent(timeout);
	}
	
}
