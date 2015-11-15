package nam.model.transport;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Transport;
import nam.model.util.TransportUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("transportDataManager")
public class TransportDataManager implements Serializable {
	
	@Inject
	private TransportEventManager transportEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Transport> getTransportList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Transport> getDefaultList() {
		return null;
	}
	
	public void saveTransport(Transport transport) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeTransport(Transport transport) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
