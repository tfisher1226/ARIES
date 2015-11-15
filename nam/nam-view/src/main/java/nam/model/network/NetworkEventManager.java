package nam.model.network;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Network;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("networkEventManager")
public class NetworkEventManager extends AbstractEventManager<Network> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Network getInstance() {
		return selectionContext.getSelection("network");
	}
	
	public void removeNetwork() {
		Network network = getInstance();
		fireRemoveEvent(network);
	}
	
}
