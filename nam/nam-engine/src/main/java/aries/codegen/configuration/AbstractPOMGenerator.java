package aries.codegen.configuration;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Property;

import org.apache.commons.lang.StringUtils;

import aries.codegen.AbstractFileGenerator;
import aries.codegen.util.Buf;
import aries.generation.model.ModelDependency;
import aries.generation.model.ModelPom;


public abstract class AbstractPOMGenerator extends AbstractFileGenerator {

	public void generate(ModelPom modelPom) throws Exception {
		String projectName = context.getProjectName();
		String targetPath = context.getTargetPath(projectName);
		setTargetPath(targetPath);
		setTargetFile("pom.xml");

		Buf buf = new Buf();
		buf.put(generatePomFileOpen());
		buf.put(generatePomHeader(modelPom));
		buf.put(generatePomParent(modelPom));
		buf.put(generatePomModules(modelPom));
		buf.put(generatePomProperties(modelPom));
		buf.put(generatePomBuildSection(modelPom));
		buf.put(generatePomProfiles(modelPom));
		buf.put(generatePomDependencyManagement(modelPom));
		buf.put(generatePomDependencies(modelPom));
		buf.put(generatePomFileClose());
		generateFile(buf.get());
	}
	
	protected String generatePomFileOpen() {
		Buf buf = new Buf();
		buf.put("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buf.put(NL);
		buf.put("<project\n");
		buf.put1("xmlns=\"http://maven.apache.org/POM/4.0.0\"\n");
		buf.put1("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
		//buf.put1("xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n");
		buf.put1("xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n");
		return buf.get();
	}

	protected String generatePomParent(ModelPom modelPom) {
		ModelPom parentPom = modelPom.getParentPom();
		if (parentPom != null) {
			Buf buf = new Buf();
			buf.put(NL);
			buf.put1("<parent>\n");
			buf.put2("<groupId>"+parentPom.getGroupId()+"</groupId>\n");
			buf.put2("<artifactId>"+parentPom.getArtifactId()+"</artifactId>\n");
			buf.put2("<version>"+parentPom.getVersion()+"</version>\n");
			buf.put1("</parent>\n");
			return buf.get();
		}
		return "";
	}

	protected String generatePomHeader(ModelPom modelPom) {
		Buf buf = new Buf();
		buf.put(NL);
		buf.put1("<modelVersion>4.0.0</modelVersion>\n");
		buf.put1("<groupId>"+modelPom.getGroupId()+"</groupId>\n");
		buf.put1("<artifactId>"+modelPom.getArtifactId()+"</artifactId>\n");
		buf.put1("<name>"+modelPom.getName()+"</name>\n");
		String description = modelPom.getDescription();
		if (!StringUtils.isEmpty(description))
			buf.put1("<description>"+description+"</description>\n");
		else buf.put1("<description>"+modelPom.getName()+"</description>\n");
		buf.put1("<version>"+modelPom.getVersion()+"</version>\n");
		buf.put1("<packaging>"+modelPom.getPackaging()+"</packaging>\n");
		return buf.get();
	}

	protected String generatePomModules(ModelPom modelPom) {
		List<String> modules = modelPom.getModules();
		if (modules != null && modules.size() > 0) {
			Buf buf = new Buf();
			buf.put(NL);
			buf.put1("<modules>\n");
			Iterator<String> iterator = modules.iterator();
			while (iterator.hasNext()) {
				String module = iterator.next();
				buf.put2("<module>"+module+"</module>\n");
			}
			buf.put1("</modules>\n");
			return buf.get();
		}
		return "";
	}


	protected String generatePomProperties(ModelPom modelPom) {
		List<Property> properties = modelPom.getProperties();
		if (properties != null && properties.size() > 0) {
			Buf buf = new Buf();
			buf.put(NL);
			buf.put1("<properties>\n");
			Iterator<Property> iterator = properties.iterator();
			while (iterator.hasNext()) {
				Property property = iterator.next();
				String name = property.getName();
				String value = property.getValue();
				buf.put2("<"+name+">"+value+"</"+name+">\n");
			}
			buf.put1("</properties>\n");
			return buf.get();
		}
		return "";
	}

	protected String generatePomBuildSection(ModelPom modelPom) {
		String pomFilters = generatePomFilters(modelPom);
		String pomPlugins = generatePomPlugins(modelPom);
		String pomResources = generatePomResources(modelPom);
		String pomTestResources = generatePomTestResources(modelPom);

		Buf buf = new Buf();
		if (!pomFilters.isEmpty() || !pomPlugins.isEmpty()) {
			buf.put(NL);
			buf.putLine1("<build>");
			buf.put(pomFilters);
			buf.put(pomPlugins);
			buf.putLine1("</build>");
		}
		return buf.get();
	}

	protected String generatePomFilters(ModelPom modelPom) {
		List<String> filters = modelPom.getFilters();
		if (filters != null && filters.size() > 0) {
			Buf buf = new Buf();
			buf.putLine2("<filters>");
			Iterator<String> iterator = filters.iterator();
			while (iterator.hasNext()) {
				String filter = iterator.next();
				buf.put3("<filter>");
				buf.put(filter);
				buf.putLine("</filter>");
			}
			buf.putLine2("</filters>");
			buf.putLine2("");
			return buf.get();
		}
		return "";
	}
	
	protected String generatePomPlugins(ModelPom modelPom) {
		List<String> plugins = modelPom.getPlugins();
		if (plugins != null && plugins.size() > 0) {
			Buf buf = new Buf();
			buf.putLine2("<plugins>");
			Iterator<String> iterator = plugins.iterator();
			while (iterator.hasNext()) {
				String plugin = iterator.next();
				buf.put(plugin);
			}
			buf.putLine2("</plugins>");
			return buf.get();
		}
		return "";
	}
	
	protected String generatePomResources(ModelPom modelPom) {
		List<String> resources = modelPom.getResources();
		if (resources != null && resources.size() > 0) {
			Buf buf = new Buf();
			buf.putLine2("<resources>");
			Iterator<String> iterator = resources.iterator();
			while (iterator.hasNext()) {
				String resource = iterator.next();
				buf.put(resource);
			}
			buf.putLine2("</resources>");
			return buf.get();
		}
		return "";
	}
	
	protected String generatePomTestResources(ModelPom modelPom) {
		List<String> testResources = modelPom.getTestResources();
		if (testResources != null && testResources.size() > 0) {
			Buf buf = new Buf();
			buf.putLine2("<testResources>");
			Iterator<String> iterator = testResources.iterator();
			while (iterator.hasNext()) {
				String testResource = iterator.next();
				buf.put(testResource);
			}
			buf.putLine2("</testResources>");
			return buf.get();
		}
		return "";
	}
	
	
	protected String generatePomProfiles(ModelPom modelPom) {
		List<String> plugins = modelPom.getProfiles();
		if (plugins != null && plugins.size() > 0) {
			Buf buf = new Buf();
			buf.put(NL);
			buf.putLine1("<profiles>");
			Iterator<String> iterator = plugins.iterator();
			while (iterator.hasNext()) {
				String plugin = iterator.next();
				buf.put(plugin);
			}
			buf.putLine1("</profiles>");
			return buf.get();
		}
		return "";
	}
	
	protected String generatePomDependencyManagement(ModelPom modelPom) {
		Buf buf = new Buf();
		Set<ModelDependency> dependencies = modelPom.getManagedDependencies();
		if (dependencies != null && dependencies.size() > 0) {
			buf.put(NL);
			buf.putLine1("<dependencyManagement>");
			buf.putLine2("<dependencies>");
			Iterator<ModelDependency> iterator = dependencies.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				ModelDependency dependency = (ModelDependency) iterator.next();
				buf.put(generatePomManagedDependency(modelPom, dependency));
				if (i < dependencies.size()-1)
					buf.put(NL);
			}
			buf.putLine2("</dependencies>");
			buf.putLine1("</dependencyManagement>");
			return buf.get();
		}
		return "";
	}

	protected String generatePomManagedDependency(ModelPom modelPom, ModelDependency dependency) {
		Buf buf = new Buf();
		buf.putLine3("<dependency>");
		buf.putLine4("<groupId>"+dependency.getGroupId()+"</groupId>");
		buf.putLine4("<artifactId>"+dependency.getArtifactId()+"</artifactId>");
		if (dependency.getVersion() != null)
			buf.putLine4("<version>"+dependency.getVersion()+"</version>");
		buf.putLine4("<scope>"+dependency.getScope()+"</scope>");
		String type = dependency.getType();
		buf.putLine4("<type>"+type+"</type>");
		buf.putLine3("</dependency>");
		return buf.get();
	}

	protected String generatePomDependencies(ModelPom modelPom) {
		Buf buf = new Buf();
		Set<ModelDependency> dependencies = modelPom.getDependencies();
		if (dependencies != null && dependencies.size() > 0) {
			buf.put(NL);
			buf.putLine1("<dependencies>");
			Iterator<ModelDependency> iterator = dependencies.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				ModelDependency dependency = (ModelDependency) iterator.next();
				buf.put(generatePomDependency(modelPom, dependency));
				if (i < dependencies.size()-1)
					buf.put(NL);
			}
			buf.putLine1("</dependencies>");
			return buf.get();
		}
		return "";
	}

	protected String generatePomDependency(ModelPom modelPom, ModelDependency dependency) {
//		if (dependency.getArtifactId().startsWith("tx-manager-service"))
//			System.out.println();
		Buf buf = new Buf();
		buf.putLine2("<dependency>");
		buf.putLine3("<groupId>"+dependency.getGroupId()+"</groupId>");
		buf.putLine3("<artifactId>"+dependency.getArtifactId()+"</artifactId>");
		if (dependency.getVersion() != null)
			buf.putLine3("<version>"+dependency.getVersion()+"</version>");
		buf.putLine3("<scope>"+dependency.getScope()+"</scope>");
		String type = dependency.getType();
		if (!type.equals("jar"))
			buf.putLine3("<type>"+type+"</type>");
		buf.putLine2("</dependency>");
		return buf.get();
	}

	protected String generatePomFileClose() {
		Buf buf = new Buf();
		buf.put("</project>\n");
		return buf.get();
	}
	
}
