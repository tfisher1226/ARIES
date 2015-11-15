package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Timeout", namespace = "http://nam/model", propOrder = {
	"value",
	"name"
})
@XmlRootElement(name = "timeout", namespace = "http://nam/model")
public class Timeout implements Comparable<Timeout>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "value", namespace = "http://nam/model")
	private Long value;
	
	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;
	
	
	public Timeout() {
		//nothing for now
	}
	
	
	public Long getValue() {
		return value;
	}
	
	public void setValue(Long value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Timeout other) {
		int status = compare(value, other.value);
		if (status != 0)
			return status;
		status = compare(name, other.name);
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
		Timeout other = (Timeout) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Timeout: value="+value+", name="+name;
	}
	
}
