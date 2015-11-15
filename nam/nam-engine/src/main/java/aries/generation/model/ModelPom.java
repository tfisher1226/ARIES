package aries.generation.model;

import java.util.List;
import java.util.Set;

import nam.model.Module;
import nam.model.Property;


public class ModelPom {

	private String groupId;

	private String artifactId;

	private String name;

	private String version;

	private String description;

	private String packaging;

	private ModelPom parentPom;

	private List<String> modules;

	private List<Property> properties;

	private List<String> filters;

	private List<String> resources;

	private List<String> testResources;

	private List<String> plugins;

	private List<String> profiles;

	private Set<ModelDependency> managedDependencies;

	private Set<ModelDependency> dependencies;

	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public ModelPom getParentPom() {
		return parentPom;
	}

	public void setParentPom(ModelPom parentPom) {
		this.parentPom = parentPom;
	}

	public List<String> getModules() {
		return modules;
	}

	public void addModule(Module module) {
		modules.add(module.getArtifactId());
	}

	public void setModules(List<String> modules) {
		this.modules = modules;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public List<String> getFilters() {
		return filters;
	}

	public void setFilters(List<String> filters) {
		this.filters = filters;
	}

	public List<String> getResources() {
		return resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}
	
	public List<String> getTestResources() {
		return testResources;
	}

	public void setTestResources(List<String> testResources) {
		this.testResources = testResources;
	}
	
	public List<String> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<String> plugins) {
		this.plugins = plugins;
	}

	public List<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<String> profiles) {
		this.profiles = profiles;
	}

	public Set<ModelDependency> getManagedDependencies() {
		return managedDependencies;
	}

	public void setManagedDependencies(Set<ModelDependency> managedDependencies) {
		this.managedDependencies = managedDependencies;
	}
	
	public Set<ModelDependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<ModelDependency> dependencies) {
		this.dependencies = dependencies;
	}

}
