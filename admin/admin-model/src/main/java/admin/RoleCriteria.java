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


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoleCriteria", namespace = "http://admin", propOrder = {
	"name",
	"roleType",
	"conditional"
})
@XmlRootElement(name = "roleCriteria", namespace = "http://admin")
public class RoleCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://admin", nillable = true)
	private String name;
	
	@XmlElement(name = "roleType", namespace = "http://admin", nillable = true)
	private RoleType roleType;
	
	@XmlElement(name = "conditional", namespace = "http://admin", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean conditional;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public RoleType getRoleType() {
		return roleType;
	}
	
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	
	public Boolean isConditional() {
		return conditional;
	}
	
	public Boolean getConditional() {
		return conditional;
	}
	
	public void setConditional(Boolean conditional) {
		this.conditional = conditional;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		RoleCriteria other = (RoleCriteria) object;
		return this == object;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
}
