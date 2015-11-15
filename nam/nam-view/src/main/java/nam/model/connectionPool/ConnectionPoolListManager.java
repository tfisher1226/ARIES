package nam.model.connectionPool;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.ConnectionPool;
import nam.model.util.ConnectionPoolUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("connectionPoolListManager")
public class ConnectionPoolListManager extends AbstractDomainListManager<ConnectionPool, ConnectionPoolListObject> implements Serializable {
	
	@Inject
	private ConnectionPoolDataManager connectionPoolDataManager;
	
	@Inject
	private ConnectionPoolEventManager connectionPoolEventManager;
	
	@Inject
	private ConnectionPoolInfoManager connectionPoolInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "connectionPoolList";
	}
	
	@Override
	public String getTitle() {
		return "ConnectionPool List";
	}
	
	@Override
	public Object getRecordKey(ConnectionPool connectionPool) {
		return ConnectionPoolUtil.getKey(connectionPool);
	}
	
	@Override
	public String getRecordName(ConnectionPool connectionPool) {
		return ConnectionPoolUtil.getLabel(connectionPool);
	}
	
	@Override
	protected Class<ConnectionPool> getRecordClass() {
		return ConnectionPool.class;
	}
	
	@Override
	protected ConnectionPool getRecord(ConnectionPoolListObject rowObject) {
		return rowObject.getConnectionPool();
	}
	
	@Override
	public ConnectionPool getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ConnectionPoolUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(ConnectionPool connectionPool) {
		super.setSelectedRecord(connectionPool);
		fireSelectedEvent(connectionPool);
	}
	
	protected void fireSelectedEvent(ConnectionPool connectionPool) {
		connectionPoolEventManager.fireSelectedEvent(connectionPool);
	}
	
	public boolean isSelected(ConnectionPool connectionPool) {
		ConnectionPool selection = selectionContext.getSelection("connectionPool");
		boolean selected = selection != null && selection.equals(connectionPool);
		return selected;
	}
	
	@Override
	protected ConnectionPoolListObject createRowObject(ConnectionPool connectionPool) {
		ConnectionPoolListObject listObject = new ConnectionPoolListObject(connectionPool);
		listObject.setSelected(isSelected(connectionPool));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<ConnectionPool> createRecordList() {
		try {
			Collection<ConnectionPool> connectionPoolList = connectionPoolDataManager.getConnectionPoolList();
			if (connectionPoolList != null)
				return connectionPoolList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewConnectionPool() {
		return viewConnectionPool(selectedRecordKey);
	}
	
	public String viewConnectionPool(Object recordKey) {
		ConnectionPool connectionPool = recordByKeyMap.get(recordKey);
		return viewConnectionPool(connectionPool);
	}
	
	public String viewConnectionPool(ConnectionPool connectionPool) {
		String url = connectionPoolInfoManager.viewConnectionPool(connectionPool);
		return url;
	}
	
	public String editConnectionPool() {
		return editConnectionPool(selectedRecordKey);
	}
	
	public String editConnectionPool(Object recordKey) {
		ConnectionPool connectionPool = recordByKeyMap.get(recordKey);
		return editConnectionPool(connectionPool);
	}
	
	public String editConnectionPool(ConnectionPool connectionPool) {
		String url = connectionPoolInfoManager.editConnectionPool(connectionPool);
		return url;
	}
	
	public void removeConnectionPool() {
		removeConnectionPool(selectedRecordKey);
	}
	
	public void removeConnectionPool(Object recordKey) {
		ConnectionPool connectionPool = recordByKeyMap.get(recordKey);
		removeConnectionPool(connectionPool);
	}
	
	public void removeConnectionPool(ConnectionPool connectionPool) {
		try {
			if (connectionPoolDataManager.removeConnectionPool(connectionPool))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelConnectionPool(@Observes @Cancelled ConnectionPool connectionPool) {
		try {
			//Object key = ConnectionPoolUtil.getKey(connectionPool);
			//recordByKeyMap.put(key, connectionPool);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("connectionPool");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateConnectionPool(Collection<ConnectionPool> connectionPoolList) {
		return ConnectionPoolUtil.validate(connectionPoolList);
	}
	
	public void exportConnectionPoolList(@Observes @Export String tableId) {
		//String tableId = "pageForm:connectionPoolListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
