package org.nam.generator;

import java.net.URL;

import nam.model.Project;

import org.aries.Assert;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import aries.codegen.AbstractProjectGenerator;


public class ProjectGeneratorOLDTest extends AbstractGeneratorTest {

	private AbstractProjectGenerator projectGenerator;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		//projectGenerator = new AbstractProjectGenerator();
	}

	@After
	public void tearDown() throws Exception {
		projectGenerator = null;
		super.tearDown();
	}

	@Test
	@Ignore
	public void testBuildFromXSD() throws Exception {
		Project project = getAriesModelForTest();
		Assert.notNull(project, "Project must exist");
		//projectGenerator.generate(project);
	}

	protected Project getAriesModelForTest() throws Exception {
		ResourceSet emfResourceSet = getEMFModelForTest();
		Assert.notNull(emfResourceSet, "ResourceSet must exist");
		Project project = engine.getModelBuilder().buildProject(emfResourceSet);
		return project;
	}
	
	private ResourceSet getEMFModelForTest() throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL schemaResource = classLoader.getResource("bank-0.0.1.xsd");
		ResourceSet emfResourceSet = engine.getEmfModelBuilder().buildFromXSD(schemaResource);
		return emfResourceSet;
	}

}
