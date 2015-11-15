package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EJBTransport", namespace = "http://nam/model", propOrder = {
	"jndiName"
})
@XmlRootElement(name = "ejb-transport", namespace = "http://nam/model")
public class EjbTransport extends Transport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "jndiName", namespace = "http://nam/model")
	private String jndiName;
	
	
	public EjbTransport() {
		//nothing for now
	}
	
	
    public String getJndiName() {
        return jndiName;
    }

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Transport other) {
		int status = super.compareTo(other);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Transport other = (Transport) object;
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
		return "EJBTransport: jndiName="+jndiName;
    }

}
