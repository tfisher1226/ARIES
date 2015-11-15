package org.nam.generator;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Namespace;
import nam.model.src.main.java.ModelBeanClassBuilder;
import nam.model.src.main.java.ModelBeanClassGenerator;

import org.aries.Assert;
import org.aries.util.properties.PropertyManager;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import aries.generation.AriesModelBuilder;
import aries.generation.EMFModelBuilder;
import aries.generation.model.ModelPackage;


public class ModelModuleGeneratorTestOLD extends AbstractModuleGeneratorTest {

	private static String FILE_NAME1 = "aries-common-0.0.1.xsd";

	private static String FILE_NAME2 = "sgiusa-model-0.0.1.xsd";
	
	private ModelBeanClassBuilder modelBeanBuilder;

	private ModelBeanClassGenerator modelBeanGenerator;

	
	@Before
	public void setUp() throws Exception {
		context = getGenerationContextForTest();
		//context.setModule(createModelModule());
//		emfModelBuilder = new EMFModelBuilder(context);
//		ariesModelBuilder = new AriesModelBuilder(context);
		modelBeanBuilder = new ModelBeanClassBuilder(context);
		modelBeanGenerator = new ModelBeanClassGenerator(context);
		PropertyManager.getInstance().initialize();
	}

	@After
	public void tearDown() throws Exception {
//		emfModelBuilder = null;
//		engine.getModelBuilder() = null;
		modelBeanBuilder = null;
		modelBeanGenerator = null;
	}

	@Test
	@Ignore
	public void testGenerate() throws Exception {
		List<Namespace> namespaces = getAriesModelForTest();
		List<ModelPackage> modelPackages = buildGenModel(namespaces);
		modelPackages = buildGenModel(namespaces);
		//generate main sources
		generateSources(modelPackages);
		//generate test sources
		generateTests(modelPackages);
	}

	protected List<ModelPackage> buildGenModel(List<Namespace> namespaces) throws Exception {
		List<ModelPackage> modelPackages = new ArrayList<ModelPackage>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			ModelPackage modelPackage = modelBeanBuilder.buildPackage(namespace);
			modelBeanGenerator.generatePackage(modelPackage);
			modelPackages.add(modelPackage);
		}
		return modelPackages;
	}

	protected void generateSources(List<ModelPackage> modelPackages) throws Exception {
		context.setOptionGenerateTests(false);
		Iterator<ModelPackage> iterator = modelPackages.iterator();
		while (iterator.hasNext()) {
			ModelPackage modelPackage = iterator.next();
			modelBeanGenerator.generatePackage(modelPackage);
		}
	}

	protected void generateTests(List<ModelPackage> modelPackages) throws Exception {
		context.setOptionGenerateTests(true);
		//nothing yet
	}

	protected List<Namespace> getAriesModelForTest() throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest();
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		List<Namespace> namespaces = engine.getModelBuilder().buildNamespaces(emfResourceSet);
		return namespaces;
	}
	
	private ResourceSet getEMFModelForTest() throws Exception {
		List<URL> schemaResources = new ArrayList<URL>(); 
		schemaResources.add(getResourceForTest(FILE_NAME1));
		schemaResources.add(getResourceForTest(FILE_NAME2));
		ResourceSet emfResourceSet = engine.getEmfModelBuilder().buildFromXSD(schemaResources);
		return emfResourceSet;
	}

}
