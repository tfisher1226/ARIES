package nam.model.transport;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Transport;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("transportEventManager")
public class TransportEventManager extends AbstractEventManager<Transport> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Transport getInstance() {
		return selectionContext.getSelection("transport");
	}
	
	public void removeTransport() {
		Transport transport = getInstance();
		fireRemoveEvent(transport);
	}
	
}
