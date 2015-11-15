package nam.model.module;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Module;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("moduleEventManager")
public class ModuleEventManager extends AbstractEventManager<Module> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Module getInstance() {
		return selectionContext.getSelection("module");
	}

	public void removeModule() {
		Module module = getInstance();
		fireRemoveEvent(module);
	}
	
}
