package aries.generation.engine;

import java.util.List;

import nam.model.Import;
import nam.model.Namespace;
import nam.model.Project;

import org.eclipse.emf.ecore.resource.ResourceSet;


public interface GenerationEngine {

	public ResourceSet getEMFModel(String fileName) throws Exception;

	public List<Namespace> createNamespaces(String filePath) throws Exception;

	//public void assureNamespaces(Import importObject, String filePath) throws Exception;

	public void initializeProject(Project project, String fileName) throws Exception;

}
