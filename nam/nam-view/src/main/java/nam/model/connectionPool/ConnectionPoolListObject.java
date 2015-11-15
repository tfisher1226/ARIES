package nam.model.connectionPool;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.ConnectionPool;
import nam.model.util.ConnectionPoolUtil;


public class ConnectionPoolListObject extends AbstractListObject<ConnectionPool> implements Comparable<ConnectionPoolListObject>, Serializable {
	
	private ConnectionPool connectionPool;
	
	
	public ConnectionPoolListObject(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	
	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}
	
	@Override
	public Object getKey() {
		return getKey(connectionPool);
	}
	
	public Object getKey(ConnectionPool connectionPool) {
		return ConnectionPoolUtil.getKey(connectionPool);
	}
	
	@Override
	public String getLabel() {
		return getLabel(connectionPool);
	}
	
	public String getLabel(ConnectionPool connectionPool) {
		return ConnectionPoolUtil.getLabel(connectionPool);
	}
	
	@Override
	public String toString() {
		return toString(connectionPool);
	}
	
	@Override
	public String toString(ConnectionPool connectionPool) {
		return ConnectionPoolUtil.toString(connectionPool);
	}
	
	@Override
	public int compareTo(ConnectionPoolListObject other) {
		Object thisKey = getKey(this.connectionPool);
		Object otherKey = getKey(other.connectionPool);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ConnectionPoolListObject other = (ConnectionPoolListObject) object;
		Object thisKey = ConnectionPoolUtil.getKey(this.connectionPool);
		Object otherKey = ConnectionPoolUtil.getKey(other.connectionPool);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
