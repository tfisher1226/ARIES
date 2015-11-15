package nam.model.container;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Container;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("containerEventManager")
public class ContainerEventManager extends AbstractEventManager<Container> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Container getInstance() {
		return selectionContext.getSelection("container");
	}
	
	public void removeContainer() {
		Container container = getInstance();
		fireRemoveEvent(container);
	}
	
}
