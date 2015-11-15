package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Container", namespace = "http://nam/model", propOrder = {
	"type",
	"name"
})
@XmlRootElement(name = "container", namespace = "http://nam/model")
public class Container implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "type", namespace = "http://nam/model", defaultValue = "PLAIN")
	private ContainerType type = ContainerType.PLAIN;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Container() {
		//nothing for now
	}
	
	
	public ContainerType getType() {
		return type;
	}
	
	public void setType(ContainerType type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
			Container other = (Container) object;
			int status = compare(type, other.type);
			if (status != 0)
				return status;
			status = compare(name, other.name);
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
		Container other = (Container) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (type != null)
			hashCode += type.hashCode();
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Container: type="+type+", name="+name;
	}
	
}
