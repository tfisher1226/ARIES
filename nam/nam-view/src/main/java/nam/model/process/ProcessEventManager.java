package nam.model.process;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("processEventManager")
public class ProcessEventManager extends AbstractEventManager<Process> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Process getInstance() {
		return selectionContext.getSelection("process");
	}
	
	public void removeProcess() {
		Process process = getInstance();
		fireRemoveEvent(process);
	}
	
}
