package nam.model.listener;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Listener;
import nam.model.util.ListenerUtil;


public class ListenerListObject extends AbstractListObject<Listener> implements Comparable<ListenerListObject>, Serializable {
	
	private Listener listener;
	
	
	public ListenerListObject(Listener listener) {
		this.listener = listener;
	}
	
	
	public Listener getListener() {
		return listener;
	}
	
	@Override
	public Object getKey() {
		return getKey(listener);
	}
	
	public Object getKey(Listener listener) {
		return ListenerUtil.getKey(listener);
	}
	
	@Override
	public String getLabel() {
		return getLabel(listener);
	}
	
	public String getLabel(Listener listener) {
		return ListenerUtil.getLabel(listener);
	}
	
	@Override
	public String toString() {
		return toString(listener);
	}
	
	@Override
	public String toString(Listener listener) {
		return ListenerUtil.toString(listener);
	}
	
	@Override
	public int compareTo(ListenerListObject other) {
		Object thisKey = getKey(this.listener);
		Object otherKey = getKey(other.listener);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ListenerListObject other = (ListenerListObject) object;
		Object thisKey = ListenerUtil.getKey(this.listener);
		Object otherKey = ListenerUtil.getKey(other.listener);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
