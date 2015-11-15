package nam.model.service;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Service;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("serviceEventManager")
public class ServiceEventManager extends AbstractEventManager<Service> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Service getInstance() {
		return selectionContext.getSelection("service");
	}
	
	public void removeService() {
		Service service = getInstance();
		fireRemoveEvent(service);
	}
	
}
