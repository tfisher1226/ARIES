package admin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Registration", namespace = "http://admin", propOrder = {
	"id",
	"enabled",
	"user",
	"loginCount"
})
@XmlRootElement(name = "registration", namespace = "http://admin")
public class Registration implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://admin")
	private Long id;
	
	@XmlElement(name = "enabled", namespace = "http://admin", type = String.class, defaultValue = "true")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled = true;
	
	@XmlElement(name = "user", namespace = "http://admin", required = true)
	private User user;
	
	@XmlElement(name = "loginCount", namespace = "http://admin")
	private Long loginCount;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Registration() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean isEnabled() {
		return enabled != null && enabled;
	}
	
	public Boolean getEnabled() {
		return enabled != null && enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getLoginCount() {
		return loginCount;
	}
	
	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
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
			Registration other = (Registration) object;
			int status = compareObject(user, other.user);
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
	
	protected <T extends Comparable<Object>> int compareObject(T value1, T value2) {
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
		Registration other = (Registration) object;
		if (id != null)
			return id.equals(other.id);
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		int hashCode = 0;
		if (user != null)
			hashCode += user.hashCode();
		if (hashCode == 0)
		return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Registration: user="+user;
	}
	
}
