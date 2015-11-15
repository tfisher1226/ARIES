package nam.model.domain;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Domain;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("domainEventManager")
public class DomainEventManager extends AbstractEventManager<Domain> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Domain getInstance() {
		return selectionContext.getSelection("domain");
	}
	
	public void removeDomain() {
		Domain domain = getInstance();
		fireRemoveEvent(domain);
	}
	
}
