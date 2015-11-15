package nam.model.connectionPool;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.ConnectionPool;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("connectionPoolEventManager")
public class ConnectionPoolEventManager extends AbstractEventManager<ConnectionPool> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public ConnectionPool getInstance() {
		return selectionContext.getSelection("connectionPool");
	}
	
	public void removeConnectionPool() {
		ConnectionPool connectionPool = getInstance();
		fireRemoveEvent(connectionPool);
	}
	
}
