package admin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkinCriteria", namespace = "http://admin", propOrder = {
	"name"
})
@XmlRootElement(name = "skinCriteria", namespace = "http://admin")
public class SkinCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://admin", nillable = true)
	private String name;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		SkinCriteria other = (SkinCriteria) object;
		return this == object;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
}
