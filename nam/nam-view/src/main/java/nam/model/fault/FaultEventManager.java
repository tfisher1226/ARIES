package nam.model.fault;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Fault;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("faultEventManager")
public class FaultEventManager extends AbstractEventManager<Fault> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Fault getInstance() {
		return selectionContext.getSelection("fault");
	}
	
	public void removeFault() {
		Fault fault = getInstance();
		fireRemoveEvent(fault);
	}
	
}
