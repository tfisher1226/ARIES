package aries.generation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import nam.model.Information;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Services;
import nam.model.util.InformationUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;

import org.eclipse.emf.ecore.resource.ResourceSet;

import aries.generation.engine.GenerationContext;
import aries.generation.engine.GenerationEngine;


public class AriesModelReader {

	private GenerationContext context;

	private GenerationEngine engine;

	private AriesModelParser parser;
	
	private AriesModelBuilder builder;

	
	public AriesModelReader(GenerationContext context) throws Exception {
		this.context = context;
		initialize();
	}

	public void setEngine(GenerationEngine engine) {
		this.engine = engine;
	}

	public void setParser(AriesModelParser parser) {
		this.parser = parser;
	}

	public void setBuilder(AriesModelBuilder builder) {
		this.builder = builder;
	}
	
	protected void initialize() throws Exception {
	}

	public <T extends Serializable> T readModelFromFileSystem(String fileName) throws Exception {
		T model = parser.unmarshalFromFileSystem(fileName);
		return model;
	}

	public <T extends Serializable> T readModelFromFileSystem(String fileName, boolean readFromCache) throws Exception {
		T model = parser.unmarshalFromFileSystem(fileName, readFromCache);
		return model;
	}

	public <T extends Serializable> T readModelFromClasspath(String fileName) throws Exception {
		T model = parser.unmarshalFromClasspath(fileName);
		return model;
	}

	public <T extends Serializable> T readModelFromClasspath(String fileName, boolean readFromCache) throws Exception {
		T model = parser.unmarshalFromClasspath(fileName, readFromCache);
		return model;
	}

	public Project readInformationProjectFromFileSystem(String fileName) throws Exception {
		Information model = readModelFromFileSystem(fileName);
		Project project = new Project();
		ProjectUtil.addInformationBlock(project, model);
		//project.setName();
		//project.setDomain(value);
		context.setProject(project);
		engine.initializeProject(project, fileName);
		return project;
	}

	public Information readInformationModelFromFileSystem(String fileName) throws Exception {
		return readInformationModelFromFileSystem(fileName, false);
	}
	
	public Information readInformationModelFromFileSystem(String fileName, boolean readFromCache) throws Exception {
		Information model = readModelFromFileSystem(fileName);
		engine.getEMFModel(fileName);
		List<Namespace> namespaces = context.getNamespaces();
		InformationUtil.addNamespaces(model, namespaces);
		return model;
	}
	
	public Information readInformationModelFromFileSystem2ORIG(String fileName) throws Exception {
		Information model = readModelFromFileSystem(fileName);
		ResourceSet emfResources = engine.getEMFModel(fileName);
		builder.buildNamespaces(emfResources.getPackageRegistry(), false);
		return model;
	}
	
	public Persistence readPersistenceModelFromFileSystem(String fileName) throws Exception {
		return readPersistenceModelFromFileSystem(fileName, false);
	}
	
	public Persistence readPersistenceModelFromFileSystem(String fileName, boolean readFromCache) throws Exception {
		Persistence model = readModelFromFileSystem(fileName);
		//engine.getEMFModel(fileName);
		//List<Namespace> namespaces = context.getNamespaces();
		//PersistenceUtil.addNamespaces(model, namespaces);
		return model;
	}
	
	public Project readPersistenceProjectFromFileSystem(String fileName) throws Exception {
		Persistence model = readModelFromFileSystem(fileName);
		Project project = new Project();
		//ProjectUtil.getImports(project).addAll(PersistenceUtil.getImports(model));
		ProjectUtil.addPersistenceBlock(project, model);
		context.setProject(project);
		engine.initializeProject(project, fileName);
		return project;
	}

	public Persistence readPersistenceModelFromFileSystem2(String fileName) throws Exception {
		Persistence persistence = readModelFromFileSystem(fileName);
		Map<String, Namespace> namespaces = context.getNamespacesByPrefix();
		ResourceSet emfResources = engine.getEMFModel(fileName);
		namespaces = context.getNamespacesByPrefix();
		List<Namespace> buildNamespaces = builder.buildNamespaces(emfResources.getPackageRegistry());
		namespaces = context.getNamespacesByPrefix();
		Collection<Namespace> values = namespaces.values();
		PersistenceUtil.addNamespaces(persistence, values);
		return persistence;
	}

//	public void processPersistenceModel(Persistence model) throws Exception {
//		Map<String, Namespace> namespaces = context.getNamespacesByPrefix();
//		ResourceSet emfResources = engine.getEMFModel(fileName);
//		namespaces = context.getNamespacesByPrefix();
//		List<Namespace> buildNamespaces = builder.buildNamespaces(emfResources.getPackageRegistry());
//		namespaces = context.getNamespacesByPrefix();
//		model.getNamespaces().addAll(namespaces.values());
//	}

	public Services readServiceModelFromFileSystem2(String fileName) throws Exception {
		Services model = readModelFromFileSystem(fileName);
		ResourceSet emfResources = engine.getEMFModel(fileName);
		List<Namespace> buildNamespaces = builder.buildNamespaces(emfResources.getPackageRegistry());
		Map<String, Namespace> namespaces = context.getNamespacesByPrefix();
		//model.getNamespaces().addAll(namespaces.values());
		return model;
	}

	public Project readProjectFromFileSystem(String fileName) throws Exception {
		return readProjectFromFileSystem(fileName, true);
	}
	
	public Project readProjectFromFileSystem(String fileName, boolean readFromCache) throws Exception {
		Project project = readModelFromFileSystem(fileName, readFromCache);
		engine.initializeProject(project, fileName);
		return project;
	}
	
	public Project readProjectFromClasspath(String fileName, boolean readFromCache) throws Exception {
		Project project = readModelFromClasspath(fileName, readFromCache);
		engine.initializeProject(project, fileName);
		return project;
	}
	
	
//	public void processImportedModel(Import importedFile) throws Exception {
//		String fileName = importedFile.getFile();
//		Serializable object = createModelFromFileSystem(fileName);
//		if (object instanceof Information) {
//			processInformationModel((Information) object);
//		} else if (object instanceof Persistence) {
//			processPersistenceModel((Persistence) object);
//		} else if (object instanceof Services) {
//			processServicesModel((Services) object);
//		}
//	}
//
//	public void processInformationModel(Information model) throws Exception {
//		List<Import> imports = InformationUtil.getImports(model);
//		assureNamespaceImports(model);
//		Iterator<Import> iterator = imports.iterator();
//		while (iterator.hasNext()) {
//			Import importedFile = iterator.next();
//			processImportedModel(importedFile);
//		}
//	}

//	public void assureNamespaceImports(Information model) throws Exception {
//		List<Import> importedFiles = InformationUtil.getImports(model);
//		List<Namespace> importedNamespaces = assureNamespaceImports(importedFiles);
//		//model.getImportsAndNamespaces().addAll(importedNamespaces);
//		List<Namespace> namespaces = InformationUtil.getNamespaces(model);
//		Iterator<Namespace> namespaceIterator = namespaces.iterator();
//		while (namespaceIterator.hasNext()) {
//			Namespace namespace = namespaceIterator.next();
//			namespace.getImports().addAll(importedNamespaces);
//			//model.getImportsAndNamespaces().addAll(importedNamespaces);
//			//model.getImportsAndNamespaces().add(namespace);
//		}
//	}
//
//	public List<Namespace> assureNamespaceImports(List<Import> importedFiles) throws Exception {
//		List<Namespace> namespaces = new ArrayList<Namespace>();
//		Iterator<Import> iterator = importedFiles.iterator();
//		while (iterator.hasNext()) {
//			Import importedFile = iterator.next();
//			String runtimeLocation = context.getRuntimeLocation();
//			String filePath = runtimeLocation + "/" + importedFile.getFile();
//			Namespace namespace = engine.createNamespace(filePath);
//			NamespaceUtil.setProperty(namespace, "imported");
//			namespaces.add(namespace);
//		}
//		return namespaces;
//	}
	
	
//	public void processPersistenceModel(Persistence model) throws Exception {
//	}
//	
//	public void processServicesModel(Services model) throws Exception {
//	}
	
}
