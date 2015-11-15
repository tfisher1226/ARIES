package nam.model.connectionPool;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.ConnectionPool;
import nam.model.util.ConnectionPoolUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("connectionPoolDataManager")
public class ConnectionPoolDataManager implements Serializable {
	
	@Inject
	private ConnectionPoolEventManager connectionPoolEventManager;
	
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
	
	public Collection<ConnectionPool> getConnectionPoolList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<ConnectionPool> getDefaultList() {
		return null;
	}
	
	public void saveConnectionPool(ConnectionPool connectionPool) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeConnectionPool(ConnectionPool connectionPool) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
