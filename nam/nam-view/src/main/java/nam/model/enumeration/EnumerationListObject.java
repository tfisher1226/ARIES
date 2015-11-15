package nam.model.enumeration;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Enumeration;
import nam.model.util.EnumerationUtil;


public class EnumerationListObject extends AbstractListObject<Enumeration> implements Comparable<EnumerationListObject>, Serializable {
	
	private Enumeration enumeration;
	
	
	public EnumerationListObject(Enumeration enumeration) {
		this.enumeration = enumeration;
	}
	
	
	public Enumeration getEnumeration() {
		return enumeration;
	}
	
	@Override
	public Object getKey() {
		return getKey(enumeration);
	}
	
	public Object getKey(Enumeration enumeration) {
		return EnumerationUtil.getKey(enumeration);
	}
	
	@Override
	public String getLabel() {
		return getLabel(enumeration);
	}
	
	public String getLabel(Enumeration enumeration) {
		return EnumerationUtil.getLabel(enumeration);
	}
	
	@Override
	public String toString() {
		return toString(enumeration);
	}
	
	@Override
	public String toString(Enumeration enumeration) {
		return EnumerationUtil.toString(enumeration);
	}
	
	@Override
	public int compareTo(EnumerationListObject other) {
		Object thisKey = getKey(this.enumeration);
		Object otherKey = getKey(other.enumeration);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		EnumerationListObject other = (EnumerationListObject) object;
		Object thisKey = EnumerationUtil.getKey(this.enumeration);
		Object otherKey = EnumerationUtil.getKey(other.enumeration);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
