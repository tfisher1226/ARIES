package nam.model.query;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Query;
import nam.model.util.QueryUtil;


public class QueryListObject extends AbstractListObject<Query> implements Comparable<QueryListObject>, Serializable {
	
	private Query query;
	
	
	public QueryListObject(Query query) {
		this.query = query;
	}
	
	
	public Query getQuery() {
		return query;
	}
	
	@Override
	public Object getKey() {
		return getKey(query);
	}
	
	public Object getKey(Query query) {
		return QueryUtil.getKey(query);
	}
	
	@Override
	public String getLabel() {
		return getLabel(query);
	}
	
	public String getLabel(Query query) {
		return QueryUtil.getLabel(query);
	}
	
	@Override
	public String toString() {
		return toString(query);
	}
	
	@Override
	public String toString(Query query) {
		return QueryUtil.toString(query);
	}
	
	@Override
	public int compareTo(QueryListObject other) {
		Object thisKey = getKey(this.query);
		Object otherKey = getKey(other.query);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		QueryListObject other = (QueryListObject) object;
		Object thisKey = QueryUtil.getKey(this.query);
		Object otherKey = QueryUtil.getKey(other.query);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
