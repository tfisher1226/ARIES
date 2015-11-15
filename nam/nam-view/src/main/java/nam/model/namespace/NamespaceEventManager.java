package nam.model.namespace;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Namespace;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("namespaceEventManager")
public class NamespaceEventManager extends AbstractEventManager<Namespace> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Namespace getInstance() {
		return selectionContext.getSelection("namespace");
	}
	
	public void removeNamespace() {
		Namespace namespace = getInstance();
		fireRemoveEvent(namespace);
	}
	
}
