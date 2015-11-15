package nam.model.dependency;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Dependency;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("dependencyEventManager")
public class DependencyEventManager extends AbstractEventManager<Dependency> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Dependency getInstance() {
		return selectionContext.getSelection("dependency");
	}
	
	public void removeDependency() {
		Dependency dependency = getInstance();
		fireRemoveEvent(dependency);
	}
	
}
