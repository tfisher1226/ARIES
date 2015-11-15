package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dependency", namespace = "http://nam/model", propOrder = {
	"name",
	"artifactId",
	"groupId",
	"version",
	"description",
	"scope",
	"type"
})
@XmlRootElement(name = "dependency", namespace = "http://nam/model")
public class Dependency implements Comparable<Object>, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "name", namespace = "http://nam/model")
	private String name;

	@XmlElement(name = "artifactId", namespace = "http://nam/model")
	private String artifactId;

	@XmlElement(name = "groupId", namespace = "http://nam/model")
	private String groupId;

	@XmlElement(name = "version", namespace = "http://nam/model")
	private String version;

	@XmlElement(name = "description", namespace = "http://nam/model")
	private String description;

	@XmlElement(name = "scope", namespace = "http://nam/model")
	private DependencyScope scope;

	@XmlElement(name = "type", namespace = "http://nam/model")
	private DependencyType type;

	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Dependency() {
		//nothing for now
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public DependencyScope getScope() {
		return scope;
	}

	public void setScope(DependencyScope scope) {
		this.scope = scope;
	}
	
	public DependencyType getType() {
		return type;
	}

	public void setType(DependencyType type) {
		this.type = type;
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
			Dependency other = (Dependency) object;
			int status = compare(artifactId, other.artifactId);
			if (status != 0)
				return status;
			status = compare(groupId, other.groupId);
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
		Dependency other = (Dependency) object;
		int status = compareTo(other);
		return status == 0;
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		if (artifactId != null)
			hashCode += artifactId.hashCode();
		if (groupId != null)
			hashCode += groupId.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}

	@Override
	public String toString() {
		return "Dependency: artifactId="+artifactId+", groupId="+groupId;
	}

}
