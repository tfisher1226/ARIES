package nam.query;

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
@XmlType(name = "ProjectCriteria", namespace = "http://nam/model", propOrder = {
	"name",
	"userName",
	"userName",
	"conditional"
})
@XmlRootElement(name = "projectCriteria", namespace = "http://nam/model")
public class ProjectCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "name", namespace = "http://nam/model", nillable = true)
	private String name;
	
	@XmlElement(name = "userName", namespace = "http://nam/model", nillable = true)
	private String userName;
	
	@XmlElement(name = "conditional", namespace = "http://nam/model", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean conditional;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
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
		ProjectCriteria other = (ProjectCriteria) object;
		return this == object;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
}
