package nam.model.pom;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Dependency implements Serializable {

	private Long id;

	private String groupId;

	private String artifactId;

	private String description;

	private String version;

	private String scope;

	private Boolean enabled;

	
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

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
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
		Dependency other = (Dependency) object;
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
