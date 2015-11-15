package nam.model.connectionPool;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.ConnectionPool;
import nam.model.Project;
import nam.model.util.ConnectionPoolUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("connectionPoolInfoManager")
public class ConnectionPoolInfoManager extends AbstractNamRecordManager<ConnectionPool> implements Serializable {
	
	@Inject
	private ConnectionPoolWizard connectionPoolWizard;
	
	@Inject
	private ConnectionPoolDataManager connectionPoolDataManager;
	
	@Inject
	private ConnectionPoolPageManager connectionPoolPageManager;
	
	@Inject
	private ConnectionPoolEventManager connectionPoolEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ConnectionPoolHelper connectionPoolHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ConnectionPoolInfoManager() {
		setInstanceName("connectionPool");
	}
	
	
	public ConnectionPool getConnectionPool() {
		return getRecord();
	}
	
	public ConnectionPool getSelectedConnectionPool() {
		return selectionContext.getSelection("connectionPool");
	}
	
	@Override
	public Class<ConnectionPool> getRecordClass() {
		return ConnectionPool.class;
	}
	
	@Override
	public boolean isEmpty(ConnectionPool connectionPool) {
		return connectionPoolHelper.isEmpty(connectionPool);
	}
	
	@Override
	public String toString(ConnectionPool connectionPool) {
		return connectionPoolHelper.toString(connectionPool);
	}
	
	@Override
	public void initialize() {
		ConnectionPool connectionPool = selectionContext.getSelection("connectionPool");
		if (connectionPool != null)
			initialize(connectionPool);
	}
	
	protected void initialize(ConnectionPool connectionPool) {
		ConnectionPoolUtil.initialize(connectionPool);
		connectionPoolWizard.initialize(connectionPool);
		setContext("connectionPool", connectionPool);
	}
	
	public void handleConnectionPoolSelected(@Observes @Selected ConnectionPool connectionPool) {
		selectionContext.setSelection("connectionPool",  connectionPool);
		connectionPoolPageManager.updateState(connectionPool);
		connectionPoolPageManager.refreshMembers();
		setRecord(connectionPool);
	}
	
	@Override
	public String newRecord() {
		return newConnectionPool();
	}
	
	public String newConnectionPool() {
		try {
			ConnectionPool connectionPool = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("connectionPool",  connectionPool);
			String url = connectionPoolPageManager.initializeConnectionPoolCreationPage(connectionPool);
			connectionPoolPageManager.pushContext(connectionPoolWizard);
			initialize(connectionPool);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public ConnectionPool create() {
		ConnectionPool connectionPool = ConnectionPoolUtil.create();
		return connectionPool;
	}
	
	@Override
	public ConnectionPool clone(ConnectionPool connectionPool) {
		connectionPool = ConnectionPoolUtil.clone(connectionPool);
		return connectionPool;
	}
	
	@Override
	public String viewRecord() {
		return viewConnectionPool();
	}
	
	public String viewConnectionPool() {
		ConnectionPool connectionPool = selectionContext.getSelection("connectionPool");
		String url = viewConnectionPool(connectionPool);
		return url;
	}
	
	public String viewConnectionPool(ConnectionPool connectionPool) {
		try {
			String url = connectionPoolPageManager.initializeConnectionPoolSummaryView(connectionPool);
			connectionPoolPageManager.pushContext(connectionPoolWizard);
			initialize(connectionPool);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editConnectionPool();
	}
	
	public String editConnectionPool() {
		ConnectionPool connectionPool = selectionContext.getSelection("connectionPool");
		String url = editConnectionPool(connectionPool);
		return url;
	}
	
	public String editConnectionPool(ConnectionPool connectionPool) {
		try {
			//connectionPool = clone(connectionPool);
			selectionContext.resetOrigin();
			selectionContext.setSelection("connectionPool",  connectionPool);
			String url = connectionPoolPageManager.initializeConnectionPoolUpdatePage(connectionPool);
			connectionPoolPageManager.pushContext(connectionPoolWizard);
			initialize(connectionPool);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveConnectionPool() {
		ConnectionPool connectionPool = getConnectionPool();
		if (validateConnectionPool(connectionPool)) {
			if (isImmediate())
				persistConnectionPool(connectionPool);
			outject("connectionPool", connectionPool);
		}
	}
	
	public void persistConnectionPool(ConnectionPool connectionPool) {
		saveConnectionPool(connectionPool);
	}
	
	public void saveConnectionPool(ConnectionPool connectionPool) {
		try {
			saveConnectionPoolToSystem(connectionPool);
			connectionPoolEventManager.fireAddedEvent(connectionPool);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveConnectionPoolToSystem(ConnectionPool connectionPool) {
		connectionPoolDataManager.saveConnectionPool(connectionPool);
	}
	
	public void handleSaveConnectionPool(@Observes @Add ConnectionPool connectionPool) {
		saveConnectionPool(connectionPool);
	}
	
	public void addConnectionPool(ConnectionPool connectionPool) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichConnectionPool(ConnectionPool connectionPool) {
		//nothing for now
	}
	
	@Override
	public boolean validate(ConnectionPool connectionPool) {
		return validateConnectionPool(connectionPool);
	}
	
	public boolean validateConnectionPool(ConnectionPool connectionPool) {
		Validator validator = getValidator();
		boolean isValid = ConnectionPoolUtil.validate(connectionPool);
		Display display = getFromSession("display");
		display.setModule("connectionPoolInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveConnectionPool() {
		display = getFromSession("display");
		display.setModule("connectionPoolInfo");
		ConnectionPool connectionPool = selectionContext.getSelection("connectionPool");
		if (connectionPool == null) {
			display.error("ConnectionPool record must be selected.");
		}
	}
	
	public String handleRemoveConnectionPool(@Observes @Remove ConnectionPool connectionPool) {
		display = getFromSession("display");
		display.setModule("connectionPoolInfo");
		try {
			display.info("Removing ConnectionPool "+ConnectionPoolUtil.getLabel(connectionPool)+" from the system.");
			removeConnectionPoolFromSystem(connectionPool);
			selectionContext.clearSelection("connectionPool");
			connectionPoolEventManager.fireClearSelectionEvent();
			connectionPoolEventManager.fireRemovedEvent(connectionPool);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeConnectionPoolFromSystem(ConnectionPool connectionPool) {
		if (connectionPoolDataManager.removeConnectionPool(connectionPool))
			setRecord(null);
	}
	
	public void cancelConnectionPool() {
		BeanContext.removeFromSession("connectionPool");
		connectionPoolPageManager.removeContext(connectionPoolWizard);
	}
	
}
