package nam.model.connectionPool;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.ConnectionPool;
import nam.model.util.ConnectionPoolUtil;


@SessionScoped
@Named("connectionPoolSelectManager")
public class ConnectionPoolSelectManager extends AbstractSelectManager<ConnectionPool, ConnectionPoolListObject> implements Serializable {
	
	@Inject
	private ConnectionPoolDataManager connectionPoolDataManager;
	
	@Inject
	private ConnectionPoolHelper connectionPoolHelper;
	
	
	@Override
	public String getClientId() {
		return "connectionPoolSelect";
	}
	
	@Override
	public String getTitle() {
		return "ConnectionPool Selection";
	}
	
	@Override
	protected Class<ConnectionPool> getRecordClass() {
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
	
	protected ConnectionPoolHelper getConnectionPoolHelper() {
		return BeanContext.getFromSession("connectionPoolHelper");
	}
	
	protected ConnectionPoolListManager getConnectionPoolListManager() {
		return BeanContext.getFromSession("connectionPoolListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshConnectionPoolList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<ConnectionPool> recordList) {
		ConnectionPoolListManager connectionPoolListManager = getConnectionPoolListManager();
		DataModel<ConnectionPoolListObject> dataModel = connectionPoolListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshConnectionPoolList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<ConnectionPool> refreshRecords() {
		try {
			Collection<ConnectionPool> records = connectionPoolDataManager.getConnectionPoolList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<ConnectionPool> connectionPoolList) {
		Collections.sort(connectionPoolList, new Comparator<ConnectionPool>() {
			public int compare(ConnectionPool connectionPool1, ConnectionPool connectionPool2) {
				String text1 = ConnectionPoolUtil.toString(connectionPool1);
				String text2 = ConnectionPoolUtil.toString(connectionPool2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
