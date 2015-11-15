package org.nam.generator;

import nam.model.Project;

import org.aries.Assert;
import org.aries.util.properties.PropertyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class WSDLToAriesGeneratorTest extends AbstractGeneratorTest {

	private static String FILE_NAME = "bank-0.0.1.wsdl";
	//private static String FILE_NAME = "sgiusa-model-0.0.1.wsdl";
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
//		entityBeanBuilder = new EntityBeanBuilder(context);
//		entityBeanGenerator = new EntityBeanGenerator(context);
		PropertyManager.getInstance().addProperty("generateTableAnnotation", true);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
//		entityBeanBuilder = null;
//		entityBeanGenerator = null;
	}

	@Test
	public void testBuildFromWSDL() throws Exception {
		Project project = createProjectFromClasspath(FILE_NAME);
		Assert.notNull(project);
	}

}
