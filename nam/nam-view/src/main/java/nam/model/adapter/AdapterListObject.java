package nam.model.adapter;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Adapter;
import nam.model.util.AdapterUtil;


public class AdapterListObject extends AbstractListObject<Adapter> implements Comparable<AdapterListObject>, Serializable {
	
	private Adapter adapter;
	
	
	public AdapterListObject(Adapter adapter) {
		this.adapter = adapter;
	}
	
	
	public Adapter getAdapter() {
		return adapter;
	}
	
	@Override
	public Object getKey() {
		return getKey(adapter);
	}
	
	public Object getKey(Adapter adapter) {
		return AdapterUtil.getKey(adapter);
	}
	
	@Override
	public String getLabel() {
		return getLabel(adapter);
	}
	
	public String getLabel(Adapter adapter) {
		return AdapterUtil.getLabel(adapter);
	}
	
	@Override
	public String toString() {
		return toString(adapter);
	}
	
	@Override
	public String toString(Adapter adapter) {
		return AdapterUtil.toString(adapter);
	}
	
	@Override
	public int compareTo(AdapterListObject other) {
		Object thisKey = getKey(this.adapter);
		Object otherKey = getKey(other.adapter);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		AdapterListObject other = (AdapterListObject) object;
		Object thisKey = AdapterUtil.getKey(this.adapter);
		Object otherKey = AdapterUtil.getKey(other.adapter);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
