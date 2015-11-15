package nam.ui;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserInterface", namespace = "http://nam/ui", propOrder = {
	"type",
	"name",
	"label",
	"groupId",
	"artifactId",
	"version",
	"namespace",
	"description",
	"creator",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "userInterface", namespace = "http://nam/ui")
public class UserInterface implements Comparable<UserInterface>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "type", namespace = "http://nam/ui")
	private UserInterfaceType type;
	
	@XmlElement(name = "name", namespace = "http://nam/ui")
	private String name;
	
	@XmlElement(name = "label", namespace = "http://nam/ui")
	private String label;
	
	@XmlElement(name = "groupId", namespace = "http://nam/ui")
	private String groupId;
	
	@XmlElement(name = "artifactId", namespace = "http://nam/ui")
	private String artifactId;
	
	@XmlElement(name = "version", namespace = "http://nam/ui")
	private String version;
	
	@XmlElement(name = "namespace", namespace = "http://nam/ui")
	private String namespace;
	
	@XmlElement(name = "description", namespace = "http://nam/ui")
	private String description;
	
	@XmlElement(name = "creator", namespace = "http://nam/ui")
	private String creator;
	
	@XmlElement(name = "creationDate", namespace = "http://nam/ui", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://nam/ui", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	
	public UserInterface() {
		//nothing for now
	}
	
	
	public UserInterfaceType getType() {
		return type;
	}
	
	public void setType(UserInterfaceType type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getArtifactId() {
		return artifactId;
	}
	
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getNamespace() {
		return namespace;
	}
	
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(UserInterface other) {
		int status = compare(type, other.type);
		if (status != 0)
			return status;
		status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(label, other.label);
		if (status != 0)
			return status;
		status = compare(groupId, other.groupId);
		if (status != 0)
			return status;
		status = compare(artifactId, other.artifactId);
		if (status != 0)
			return status;
		status = compare(version, other.version);
		if (status != 0)
			return status;
		status = compare(namespace, other.namespace);
		if (status != 0)
			return status;
		status = compare(description, other.description);
		if (status != 0)
			return status;
		status = compare(creator, other.creator);
		if (status != 0)
			return status;
		status = compare(creationDate, other.creationDate);
		if (status != 0)
			return status;
		status = compare(lastUpdate, other.lastUpdate);
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
		UserInterface other = (UserInterface) object;
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
		return "UserInterface: type="+type+", name="+name+", label="+label+", groupId="+groupId+", artifactId="+artifactId+", version="+version+", namespace="+namespace+", description="+description+", creator="+creator+", creationDate="+creationDate+", lastUpdate="+lastUpdate;
	}
	
}
