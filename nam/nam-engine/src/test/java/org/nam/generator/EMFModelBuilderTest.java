package org.nam.generator;

import java.net.URL;

import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aries.generation.EMFModelBuilder;


public class EMFModelBuilderTest extends AbstractBuilderTest {

	private EMFModelBuilder fixture;
	
	
	@Before
	public void setUp() throws Exception {
		createEMFModelBuilder();
	}

	protected void createEMFModelBuilder() throws Exception {
		context = getGenerationContextForTest();
		fixture = new EMFModelBuilder(context);
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
	}

	@Test
	public void testBuildFromXSD() throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL schemaResource = classLoader.getResource("bank-0.0.1.xsd");
		ResourceSet emfResourceSet = fixture.buildFromXSD(schemaResource);
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
	}

	@Test
	public void testBuildFromWSDL() throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL schemaResource = classLoader.getResource("bank-0.0.1.wsdl");
		ResourceSet emfResourceSet = fixture.buildFromWSDL(schemaResource);
		Assert.notNull(emfResourceSet, "File not found");
	}	

}
