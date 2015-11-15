package nam.model.component;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Component;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("componentEventManager")
public class ComponentEventManager extends AbstractEventManager<Component> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Component getInstance() {
		return selectionContext.getSelection("component");
	}
	
	public void removeComponent() {
		Component component = getInstance();
		fireRemoveEvent(component);
	}
	
}
