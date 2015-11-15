package nam.ui.invocation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Invocation;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("invocationEventManager")
public class InvocationEventManager extends AbstractEventManager<Invocation> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Invocation getInstance() {
		return selectionContext.getSelection("invocation");
	}
	
	public void removeInvocation() {
		Invocation invocation = getInstance();
		fireRemoveEvent(invocation);
	}
	
}
