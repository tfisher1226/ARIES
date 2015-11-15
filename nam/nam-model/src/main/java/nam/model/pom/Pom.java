package nam.model.pom;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class Pom implements Serializable {

	private Long id;

	private String groupId;

	private String artifactId;

	private String name;

	private String description;

	private String version;

	private Boolean enabled;

	private Pom parent;

	private List<String> modules;

	private List<String> properties;

	private List<Dependency> dependencies;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Pom other = (Pom) object;
		if (this.getId() == null ||  other.getId() == null)
			return false;
		if (this.getId() != other.getId())
			return false;
		return true;
	}

	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return 0;
	}
}
