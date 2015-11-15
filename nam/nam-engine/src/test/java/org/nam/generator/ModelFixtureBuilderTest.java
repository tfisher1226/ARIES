package org.nam.generator;

import java.util.ArrayList;
import java.util.List;

import nam.model.Namespace;
import nam.model.src.main.java.ModelFixtureClassBuilder;

import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ModelFixtureBuilderTest extends AbstractModuleGeneratorTest {

	private static String FILE_NAME = "Bookshop/Bookshop.xsd";

	private ModelFixtureClassBuilder modelFixtureBuilder;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		modelFixtureBuilder = new ModelFixtureClassBuilder(context);
	}

	@After
	public void tearDown() throws Exception {
		modelFixtureBuilder = null;
		super.tearDown();
	}
	
	@Test
	public void testGenerate() throws Exception {
		List<Namespace> namespaces = getAriesNamespacesForTest(FILE_NAME);
		Assert.notNull(namespaces, "Namespaces must exist");
		Assert.equals(namespaces.size(), 1, "Namespace must exist");
		Namespace namespace = namespaces.get(0);
		modelFixtureBuilder.buildClass(namespace);
	}
	
	protected List<Namespace> getAriesNamespacesForTest(String fileName) throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest(fileName);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		List<Namespace> namespaces = createNamespaces(emfResourceSet);
		return namespaces;
	}

	protected List<Namespace> createNamespaces(ResourceSet emfResourceSet) throws Exception {
		List<Namespace> namespaces = new ArrayList<Namespace>();
		namespaces.addAll(engine.getModelBuilder().buildNamespaces(emfResourceSet.getPackageRegistry()));
		namespaces.addAll(engine.getModelBuilder().buildNamespaces(emfResourceSet.getResources()));
		return namespaces;
	}
}
