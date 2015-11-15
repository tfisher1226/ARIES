package nam.model.connectionPool;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.ConnectionPool;
import nam.model.Property;
import nam.model.property.PropertyListManager;
import nam.model.property.PropertyListObject;
import nam.model.util.ConnectionPoolUtil;


@SessionScoped
@Named("connectionPoolHelper")
public class ConnectionPoolHelper extends AbstractElementHelper<ConnectionPool> implements Serializable {
	
	@Override
	public boolean isEmpty(ConnectionPool connectionPool) {
		return ConnectionPoolUtil.isEmpty(connectionPool);
	}
	
	@Override
	public String toString(ConnectionPool connectionPool) {
		return ConnectionPoolUtil.toString(connectionPool);
	}
	
	@Override
	public String toString(Collection<ConnectionPool> connectionPoolList) {
		return ConnectionPoolUtil.toString(connectionPoolList);
	}
	
	@Override
	public boolean validate(ConnectionPool connectionPool) {
		return ConnectionPoolUtil.validate(connectionPool);
	}
	
	@Override
	public boolean validate(Collection<ConnectionPool> connectionPoolList) {
		return ConnectionPoolUtil.validate(connectionPoolList);
	}
	
	public DataModel<PropertyListObject> getProperties(ConnectionPool connectionPool) {
		if (connectionPool == null)
			return null;
		return getProperties(connectionPool.getProperties());
	}
	
	public DataModel<PropertyListObject> getProperties(Collection<Property> propertiesList) {
		PropertyListManager propertyListManager = BeanContext.getFromSession("propertyListManager");
		DataModel<PropertyListObject> dataModel = propertyListManager.getDataModel(propertiesList);
		return dataModel;
	}
	
}
