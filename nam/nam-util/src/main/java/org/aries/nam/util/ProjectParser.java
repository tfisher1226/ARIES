package org.aries.nam.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Import;
import nam.model.Imports;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;

import org.aries.Assert;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.runtime.BeanContext;

import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaLoader;
import com.sun.org.apache.xerces.internal.util.XMLCatalogResolver;
import com.sun.org.apache.xerces.internal.xs.XSModel;


public class ProjectParser {

	public ProjectParser() {
	}
	
	protected JAXBReader getJAXBReader() {
		String serviceId = "default";
		JAXBSessionCache jaxbSessionCache = BeanContext.get(serviceId + ".jaxbSessionCache");
		JAXBReader jaxbReader = jaxbSessionCache.getReader(serviceId);
		return jaxbReader;
	}
	
	public Project parseFromText(String fileData) {
		return parseFromText(fileData.getBytes());
	}
	
	public Project parseFromText(byte[] fileData) {
		try {
			JAXBReader jaxbReader = getJAXBReader();
			Project project = jaxbReader.unmarshal(fileData);
			processProjectImports(project);
			processSchemaImports(project);
			return project;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Project parseFromFileSystem(String filePath) {
		try {
			JAXBReader jaxbReader = getJAXBReader();
			Project project = jaxbReader.unmarshalFromFileSystem(filePath);
			processProjectImports(project);
			processSchemaImports(project);
			return project;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Project parseFromClasspath(String filePath) {
		Project project = unmarshalFromClasspath(filePath);
		processProjectImports(project);
		processSchemaImports(project);
		return project;
	}

	public <T> T unmarshalFromClasspath(String filePath) {
		try {
			JAXBReader jaxbReader = getJAXBReader();
			T object = jaxbReader.unmarshalFromClasspath(filePath); 
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void processProjectImports(Project project) {
		List<Project> importedModels = new ArrayList<Project>();
		Imports imports = project.getImports();
		if (imports != null) {
			List<Import> files = imports.getImports();
			Iterator<Import> iterator = files.iterator();
			while (iterator.hasNext()) {
				Import importFile = (Import) iterator.next();
				Project importedModel = unmarshalFromClasspath(importFile.getFile());
				importedModels.add(importedModel);
				processProjectImports(importedModel);
			}
			resolveReferences(project, importedModels);
		}
	}
	
	protected void resolveReferences(Project project, List<Project> importedModels) {
		resolveApplicationReferences(project, getApplications(importedModels));
		resolveModuleReferences(project, getModules(importedModels));
	}

	protected void processSchemaImports(Project project) {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		namespaces.addAll(ProjectUtil.getNamespaces(project));
		namespaces.addAll(ApplicationUtil.getNamespaces(project.getApplications()));
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = (Namespace) iterator.next();
			processSchemaImports(namespace);
		}
	}

	protected void processSchemaImports(Namespace namespace) {
		String schema = namespace.getSchema();
		if (schema != null) {
			//processSchemaImport(schema);
		}
	}
	
	@SuppressWarnings("restriction")
	protected void processSchemaImport(String schema) {
		if (schema != null) {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			//URL catalogResource = loader.getResource("schema/aries-common-0.0.1.xsd");
			URL catalogResource = loader.getResource("schema/model-0.0.1.cat");
			URL schemaResource = loader.getResource("schema/model-0.0.1.xsd");

			try {
				XMLSchemaLoader schemaLoader = new XMLSchemaLoader();
				
				String [] catalogs = { catalogResource.getPath() };
				XMLCatalogResolver resolver = new XMLCatalogResolver(catalogs);
				schemaLoader.setEntityResolver(resolver);
				
				XSModel model = schemaLoader.loadURI(schemaResource.getPath());
				//System.out.println(">>>"+model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Resolve Application references
	 */

	protected void resolveApplicationReferences(Project project, Map<String, Application> repository) {
		Collection<Application> references = removeApplicationReferences(project);
		Collection<Application> applications = ProjectUtil.getApplications(project);
		Iterator<Application> iterator = references.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			Application referenced = repository.get(application.getRef());
			Assert.notNull(referenced, "Referenced application not found");
			applications.add(referenced);
		}
	}

	protected Map<String, Application> getApplications(List<Project> importedModels) {
		Map<String, Application> referencedApplications = new HashMap<String, Application>();
		Iterator<Project> iterator = importedModels.iterator();
		while (iterator.hasNext()) {
			Project importedModel = (Project) iterator.next();
			Collection<Application> importedApplications = ApplicationUtil.getApplications(importedModel.getApplications());
			Iterator<Application> iterator2 = importedApplications.iterator();
			while (iterator2.hasNext()) {
				Application application = (Application) iterator2.next();
				//TODO check for ref here too, don't be blind here
				referencedApplications.put(application.getArtifactId(), application);
			}
		}
		return referencedApplications;
	}
	
	protected Collection<Application> removeApplicationReferences(Project project) {
		Collection<Application> applications = ApplicationUtil.getApplications(project.getApplications());
		//ListIterator<Application> listIterator = applications.listIterator();
		Collection<Application> references = getApplicationReferences(applications);
		Iterator<Application> iterator = references.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			applications.remove(application);
		}
		return references;
	}
	
	protected Collection<Application> getApplicationReferences(Collection<Application> applications) {
		Collection<Application> list = new ArrayList<Application>();
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			String ref = application.getRef();
			if (ref != null) {
				list.add(application);
			}
		}
		return list;
	}
	
	protected Map<String, Application> getApplicationReferenceMap(List<Application> applications) {
		Map<String, Application> map = new HashMap<String, Application>();
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			String ref = application.getRef();
			if (ref != null) {
				map.put(application.getArtifactId(), application);
			}
		}
		return map;
	}

	
	/*
	 * Resolve Module references
	 */
	
	protected void resolveModuleReferences(Project project, Map<String, Module> repository) {
		List<Module> references = removeModuleReferences(project);
		Set<Module> applications = ModuleUtil.getModules(project.getModules());
		Iterator<Module> iterator = references.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			Module referenced = repository.get(module.getRef());
			Assert.notNull(referenced, "Referenced application not found");
			applications.add(referenced);
		}
	}

	protected Map<String, Module> getModules(List<Project> importedModels) {
		Map<String, Module> referencedModules = new HashMap<String, Module>();
		Iterator<Project> iterator = importedModels.iterator();
		while (iterator.hasNext()) {
			Project importedProject = (Project) iterator.next();
			Set<Module> importedModules = ModuleUtil.getModules(importedProject.getModules());
			Iterator<Module> iterator2 = importedModules.iterator();
			while (iterator2.hasNext()) {
				Module module = (Module) iterator2.next();
				//TODO check for ref here too, don't be blind here
				referencedModules.put(module.getArtifactId(), module);
			}
		}
		return referencedModules;
	}
	
	protected List<Module> removeModuleReferences(Project project) {
		Set<Module> modules = ModuleUtil.getModules(project.getModules());
		List<Module> references = getModuleReferences(modules);
		Iterator<Module> iterator = references.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			modules.remove(module);
		}
		return references;
	}
	
	protected List<Module> getModuleReferences(Set<Module> modules) {
		List<Module> list = new ArrayList<Module>();
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = (Module) iterator.next();
			String ref = module.getRef();
			if (ref != null) {
				list.add(module);
			}
		}
		return list;
	}
	
}
