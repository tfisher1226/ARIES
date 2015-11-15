package org.nam.generator;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Namespace;
import nam.model.src.main.java.ModelBeanClassBuilder;
import nam.model.src.main.java.ModelBeanClassGenerator;

import org.aries.Assert;
import org.aries.util.FileUtil;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aries.generation.model.ModelPackage;


public class XSDToJAXBGeneratorTest extends AbstractModuleGeneratorTest {

	private static String FILE_NAME1 = "aries-common-1.0.xsd";

	private static String FILE_NAME2 = "sgiusa-0.0.1.xsd";
	
	private ModelBeanClassBuilder modelBeanBuilder;

	private ModelBeanClassGenerator modelBeanGenerator;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		//context = getGenerationContextForTest();
		initializeContextForTest(createDataModuleForTest());
		//emfModelBuilder = new EMFModelBuilder(context);
		//ariesModelBuilder = new AriesModelBuilder(context);
		modelBeanBuilder = new ModelBeanClassBuilder(context);
		modelBeanGenerator = new ModelBeanClassGenerator(context);
		//PropertyManager.getInstance().initialize();
		//PropertyManager.getInstance().addProperty("generateTableAnnotation", true);
	}

	@After
	public void tearDown() throws Exception {
		//emfModelBuilder = null;
		//ariesModelBuilder = null;
		modelBeanBuilder = null;
		modelBeanGenerator = null;
		super.tearDown();
	}

	@Test
	public void testBuildFromXSD() throws Exception {
		List<Namespace> namespaces = getAriesModelForTest();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			ModelPackage modelPackage = modelBeanBuilder.buildPackage(namespace);
			modelBeanGenerator.generatePackage(modelPackage);
		}
	}

	protected List<Namespace> getAriesModelForTest() throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest();
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		List<Namespace> namespaces = engine.getModelBuilder().buildNamespaces(emfResourceSet);
		//ariesModelBuilder.getElementCache
		return namespaces;
	}
	
	private ResourceSet getEMFModelForTest() throws Exception {
		String fileName1 = FileUtil.normalizePath(FILE_NAME1);
		String fileName2 = FileUtil.normalizePath(FILE_NAME2);
		List<URL> schemaResources = new ArrayList<URL>(); 
		schemaResources.add(getResourceForTest(fileName1));
		schemaResources.add(getResourceForTest(fileName2));
		ResourceSet emfResourceSet = engine.getEmfModelBuilder().buildFromXSD(schemaResources);
		return emfResourceSet;
	}
	
}
