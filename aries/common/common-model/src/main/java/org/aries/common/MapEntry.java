package org.aries.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MapEntry", namespace = "http://www.aries.org/common", propOrder = {
	"key",
	"value"
})
@XmlRootElement(name = "mapEntry", namespace = "http://www.aries.org/common")
public class MapEntry implements Comparable<MapEntry>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "key", namespace = "http://www.aries.org/common")
	private Object key;
	
	@XmlElement(name = "value", namespace = "http://www.aries.org/common")
	private Object value;
	
	
	public MapEntry() {
		//nothing for now
	}
	
	
	public Object getKey() {
		return key;
	}
	
	public void setKey(Object key) {
		this.key = key;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	protected int compare(Object value1, Object value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		String string1 = value2.toString();
		String string2 = value2.toString();
		int status = string1.compareTo(string2);
		return status;
	}
	
	@Override
	public int compareTo(MapEntry other) {
		int status = compare(key, other.key);
		if (status != 0)
			return status;
		status = compare(value, other.value);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		MapEntry other = (MapEntry) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (key != null)
			hashCode += key.hashCode();
		if (value != null)
			hashCode += value.hashCode();
		if (hashCode == 0)
		return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "MapEntry: key="+key+", value="+value;
	}
	
}
