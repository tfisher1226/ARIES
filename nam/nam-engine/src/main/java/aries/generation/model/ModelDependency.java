package aries.generation.model;


public class ModelDependency {

	private String groupId;

	private String artifactId;

	private String version;

	private String scope = "compile";

	private String type = "jar";

	
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

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ModelDependency other = (ModelDependency) object;
		if (this.getGroupId() == null || other.getGroupId() == null)
			return this == other;
		if (this.getArtifactId() == null || other.getArtifactId() == null)
			return this == other;
		if (!this.getGroupId().equals(other.getGroupId()))
			return false;
		if (!this.getScope().equals(other.getScope()))
			return false;
		if (!this.getType().equals(other.getType()))
			return false;
		if (!this.getArtifactId().equals(other.getArtifactId()))
			return false;
		return true;
	}

	public int hashCode() {
		if (getArtifactId() != null)
			return getArtifactId().hashCode();
		return 0;
	}
	
}
