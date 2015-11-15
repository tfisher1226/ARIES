package org.aries.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Properties", namespace = "http://www.aries.org/common", propOrder = {
	"properties"
})
@XmlRootElement(name = "properties", namespace = "http://www.aries.org/common")
public class Properties implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "properties", namespace = "http://www.aries.org/common")
	private List<Property> properties;
	
	
	public Properties() {
		properties = new ArrayList<Property>();
	}
	
	
	public List<Property> getProperties() {
		synchronized (properties) {
			return properties;
		}
	}
	
	public void setProperties(Collection<Property> propertyList) {
		if (propertyList == null) {
			this.properties = null;
		} else {
		synchronized (this.properties) {
			this.properties = new ArrayList<Property>();
				addToProperties(propertyList);
			}
		}
	}

	public void addToProperties(Property property) {
		if (property != null ) {
			synchronized (this.properties) {
				this.properties.add(property);
			}
		}
	}

	public void addToProperties(Collection<Property> propertyCollection) {
		if (propertyCollection != null && !propertyCollection.isEmpty()) {
			synchronized (this.properties) {
				this.properties.addAll(propertyCollection);
			}
		}
	}

	public void removeFromProperties(Property property) {
		if (property != null ) {
			synchronized (this.properties) {
				this.properties.remove(property);
			}
		}
	}

	public void removeFromProperties(Collection<Property> propertyCollection) {
		if (propertyCollection != null ) {
			synchronized (this.properties) {
				this.properties.removeAll(propertyCollection);
			}
		}
	}

	public void clearProperties() {
		synchronized (properties) {
			properties.clear();
		}
	}
}
