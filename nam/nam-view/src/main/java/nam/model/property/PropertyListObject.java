package nam.model.property;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Property;
import nam.model.util.PropertyUtil;


public class PropertyListObject extends AbstractListObject<Property> implements Comparable<PropertyListObject>, Serializable {
	
	private Property property;
	
	
	public PropertyListObject(Property property) {
		this.property = property;
	}
	
	
	public Property getProperty() {
		return property;
	}
	
	@Override
	public Object getKey() {
		return getKey(property);
	}
	
	public Object getKey(Property property) {
		return PropertyUtil.getKey(property);
	}
	
	@Override
	public String getLabel() {
		return getLabel(property);
	}
	
	public String getLabel(Property property) {
		return PropertyUtil.getLabel(property);
	}
	
	@Override
	public String toString() {
		return toString(property);
	}
	
	@Override
	public String toString(Property property) {
		return PropertyUtil.toString(property);
	}
	
	@Override
	public int compareTo(PropertyListObject other) {
		Object thisKey = getKey(this.property);
		Object otherKey = getKey(other.property);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		PropertyListObject other = (PropertyListObject) object;
		Object thisKey = PropertyUtil.getKey(this.property);
		Object otherKey = PropertyUtil.getKey(other.property);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
