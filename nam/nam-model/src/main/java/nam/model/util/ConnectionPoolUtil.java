package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.ConnectionPool;


public class ConnectionPoolUtil extends BaseUtil {
	
	public static Object getKey(ConnectionPool connectionPool) {
		return connectionPool.getName();
	}
	
	public static String getLabel(ConnectionPool connectionPool) {
		return connectionPool.getName();
	}

	public static boolean getLabel(Collection<ConnectionPool> connectionPoolList) {
		if (connectionPoolList == null  || connectionPoolList.size() == 0)
			return true;
		Iterator<ConnectionPool> iterator = connectionPoolList.iterator();
		while (iterator.hasNext()) {
			ConnectionPool connectionPool = iterator.next();
			if (!isEmpty(connectionPool))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(ConnectionPool connectionPool) {
		if (connectionPool == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<ConnectionPool> connectionPoolList) {
		if (connectionPoolList == null  || connectionPoolList.size() == 0)
			return true;
		Iterator<ConnectionPool> iterator = connectionPoolList.iterator();
		while (iterator.hasNext()) {
			ConnectionPool connectionPool = iterator.next();
			if (!isEmpty(connectionPool))
				return false;
		}
		return true;
	}
	
	public static String toString(ConnectionPool connectionPool) {
		if (isEmpty(connectionPool))
			return "ConnectionPool: [uninitialized] "+connectionPool.toString();
		String text = connectionPool.toString();
		return text;
	}
	
	public static String toString(Collection<ConnectionPool> connectionPoolList) {
		if (isEmpty(connectionPoolList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<ConnectionPool> iterator = connectionPoolList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ConnectionPool connectionPool = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(connectionPool);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static ConnectionPool create() {
		ConnectionPool connectionPool = new ConnectionPool();
		initialize(connectionPool);
		return connectionPool;
	}
	
	public static void initialize(ConnectionPool connectionPool) {
		//nothing for now
	}
	
	public static boolean validate(ConnectionPool connectionPool) {
		if (connectionPool == null)
			return false;
		Validator validator = Validator.getValidator();
		PropertyUtil.validate(connectionPool.getProperties());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<ConnectionPool> connectionPoolList) {
		Validator validator = Validator.getValidator();
		Iterator<ConnectionPool> iterator = connectionPoolList.iterator();
		while (iterator.hasNext()) {
			ConnectionPool connectionPool = iterator.next();
			//TODO break or accumulate?
			validate(connectionPool);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<ConnectionPool> connectionPoolList) {
		Collections.sort(connectionPoolList, createConnectionPoolComparator());
	}
	
	public static Collection<ConnectionPool> sortRecords(Collection<ConnectionPool> connectionPoolCollection) {
		List<ConnectionPool> list = new ArrayList<ConnectionPool>(connectionPoolCollection);
		Collections.sort(list, createConnectionPoolComparator());
		return list;
	}
	
	public static Comparator<ConnectionPool> createConnectionPoolComparator() {
		return new Comparator<ConnectionPool>() {
			public int compare(ConnectionPool connectionPool1, ConnectionPool connectionPool2) {
				Object key1 = getKey(connectionPool1);
				Object key2 = getKey(connectionPool2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static ConnectionPool clone(ConnectionPool connectionPool) {
		if (connectionPool == null)
			return null;
		ConnectionPool clone = create();
		clone.setName(ObjectUtil.clone(connectionPool.getName()));
		clone.setInitialSize(ObjectUtil.clone(connectionPool.getInitialSize()));
		clone.setMinSize(ObjectUtil.clone(connectionPool.getMinSize()));
		clone.setMaxSize(ObjectUtil.clone(connectionPool.getMaxSize()));
		clone.setProperties(PropertyUtil.clone(connectionPool.getProperties()));
		return clone;
	}
	
}
