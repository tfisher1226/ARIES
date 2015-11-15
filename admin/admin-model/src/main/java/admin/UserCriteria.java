package admin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.common.AbstractCriteria;
import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.StreetAddress;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserCriteria", namespace = "http://admin", propOrder = {
	"enabled",
	"userName",
	"personName",
	"emailAddress",
	"streetAddress",
	"roles"
})
@XmlRootElement(name = "userCriteria", namespace = "http://admin")
public class UserCriteria extends AbstractCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "enabled", namespace = "http://admin", type = String.class, defaultValue = "true")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled = true;
	
	@XmlElement(name = "userName", namespace = "http://admin")
	private String userName;
	
	@XmlElement(name = "personName", namespace = "http://admin", nillable = true)
	private PersonName personName;
	
	@XmlElement(name = "emailAddress", namespace = "http://admin", nillable = true)
	private EmailAddress emailAddress;
	
	@XmlElement(name = "streetAddress", namespace = "http://admin", nillable = true)
	private StreetAddress streetAddress;
	
	@XmlElement(name = "roles", namespace = "http://admin", nillable = true)
	private Role roles;
	
	
	public UserCriteria() {
		//nothing for now
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
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public PersonName getPersonName() {
		return personName;
	}
	
	public void setPersonName(PersonName personName) {
		this.personName = personName;
	}
	
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public StreetAddress getStreetAddress() {
		return streetAddress;
	}
	
	public void setStreetAddress(StreetAddress streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public Role getRoles() {
		return roles;
	}
	
	public void setRoles(Role roles) {
		this.roles = roles;
	}
}
