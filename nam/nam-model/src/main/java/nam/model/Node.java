package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Node", namespace = "http://nam/model", propOrder = {
	"name",
	"nodeIP",
	"properties"
})
@XmlRootElement(name = "node", namespace = "http://nam/model")
public class Node implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlElement(name = "nodeIP", namespace = "http://nam/model")
	private String nodeIP;
	
	@XmlElement(name = "properties", namespace = "http://nam/model")
	private List<Property> properties;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Node() {
		properties = new ArrayList<Property>();
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNodeIP() {
		return nodeIP;
	}
	
	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}
	
	public List<Property> getProperties() {
		synchronized (properties) {
			return properties;
		}
	}
	
	public void setProperties(Collection<Property> properties) {
		if (properties == null) {
			this.properties = null;
		} else {
		synchronized (this.properties) {
				this.properties = new ArrayList<Property>();
				addToProperties(properties);
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
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Node other = (Node) object;
			int status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(nodeIP, other.nodeIP);
		if (status != 0)
			return status;
		}
		return 0;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Node other = (Node) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (nodeIP != null)
			hashCode += nodeIP.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Node: name="+name+", nodeIP="+nodeIP;
	}
	
}
