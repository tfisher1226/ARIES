package org.nam.generator;

import java.net.URL;

import nam.model.Project;

import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import aries.generation.AriesModelBuilder;
import aries.generation.EMFModelBuilder;


public class AriesModelBuilderTest extends AbstractBuilderTest {

	private EMFModelBuilder emfModelBuilder;
	
	private AriesModelBuilder fixture;

	
	@Before
	public void setUp() throws Exception {
		createEMFModelBuilder();
	}

	protected void createEMFModelBuilder() throws Exception {
		context = getGenerationContextForTest();
		emfModelBuilder = new EMFModelBuilder(context);
	}

	@After
	public void tearDown() throws Exception {
		emfModelBuilder = null;
	}

	@Test
	@Ignore
	public void testBuildFromXSD() throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest();
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		Project project = fixture.buildProject(emfResourceSet);
		Assert.notNull(project, "Project must exist");
	}

	private ResourceSet getEMFModelForTest() throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL schemaResource = classLoader.getResource("bank-0.0.1.xsd");
		ResourceSet emfResourceSet = emfModelBuilder.buildFromXSD(schemaResource);
		return emfResourceSet;
	}

}
