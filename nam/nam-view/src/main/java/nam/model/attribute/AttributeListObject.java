package nam.model.attribute;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Attribute;
import nam.model.util.AttributeUtil;


public class AttributeListObject extends AbstractListObject<Attribute> implements Comparable<AttributeListObject>, Serializable {
	
	private Attribute attribute;
	
	
	public AttributeListObject(Attribute attribute) {
		this.attribute = attribute;
	}
	
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	@Override
	public Object getKey() {
		return getKey(attribute);
	}
	
	public Object getKey(Attribute attribute) {
		return AttributeUtil.getKey(attribute);
	}
	
	@Override
	public String getLabel() {
		return getLabel(attribute);
	}
	
	public String getLabel(Attribute attribute) {
		return AttributeUtil.getLabel(attribute);
	}
	
	@Override
	public String toString() {
		return toString(attribute);
	}
	
	@Override
	public String toString(Attribute attribute) {
		return AttributeUtil.toString(attribute);
	}
	
	@Override
	public int compareTo(AttributeListObject other) {
		Object thisKey = getKey(this.attribute);
		Object otherKey = getKey(other.attribute);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		AttributeListObject other = (AttributeListObject) object;
		Object thisKey = AttributeUtil.getKey(this.attribute);
		Object otherKey = AttributeUtil.getKey(other.attribute);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
