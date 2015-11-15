package aries.generation.model;

import java.util.ArrayList;
import java.util.List;

import nam.model.Dependency;
import nam.model.ModuleType;
import nam.model.util.SourceToken;


public class ModuleTemplate {

	private Long id;

	private String name;

	private Boolean enabled;

	private ModuleType moduleType;

	private String templateFilePath;

	private String templateHome;

	private List<Dependency> dependencies = new ArrayList<Dependency>();

	private List<SourceToken> sourceTokens;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ModuleType getModuleType() {
		return moduleType;
	}

	public void setModuleType(ModuleType moduleType) {
		this.moduleType = moduleType;
	}
	
	public String getTemplateHome() {
		return templateHome;
	}
	
	public void setTemplateHome(String templateHome) {
		this.templateHome = templateHome;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void addDependency(Dependency dependency) {
		dependencies.add(dependency);
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ModuleTemplate other = (ModuleTemplate) object;
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
