package nam.query;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import nam.model.Project;

import org.aries.adapter.BooleanAdapter;
import org.aries.common.AbstractCriteria;
import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.StreetAddress;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkspaceCriteria", namespace = "http://nam/model", propOrder = {
	"enabled",
	"workspaceName",
	"personName",
	"emailAddress",
	"streetAddress"
})
@XmlRootElement(name = "workspaceCriteria", namespace = "http://nam/model")
public class WorkspaceCriteria extends AbstractCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "enabled", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled;
	
	@XmlElement(name = "workspaceName", namespace = "http://nam/model")
	private String workspaceName;
	
	@XmlElement(name = "personName", namespace = "http://nam/model", nillable = true)
	private PersonName personName;
	
	@XmlElement(name = "emailAddress", namespace = "http://nam/model", nillable = true)
	private EmailAddress emailAddress;
	
	@XmlElement(name = "streetAddress", namespace = "http://nam/model", nillable = true)
	private StreetAddress streetAddress;
	
	
	public WorkspaceCriteria() {
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
	
	public String getWorkspaceName() {
		return workspaceName;
	}
	
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
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
	
}
