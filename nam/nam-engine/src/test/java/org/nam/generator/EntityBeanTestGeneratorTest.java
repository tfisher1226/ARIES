package org.nam.generator;

import nam.data.src.test.java.EntityBeanTestGenerator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aries.generation.model.ModelClass;


public class EntityBeanTestGeneratorTest extends AbstractModuleGeneratorTest {

	private EntityBeanTestGenerator entityBeanTestGenerator;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		initializeContextForTest(createDataModuleForTest());
		entityBeanTestGenerator = new EntityBeanTestGenerator(context);
	}

	@After
	public void tearDown() throws Exception {
		entityBeanTestGenerator = null;
		super.tearDown();
	}

	protected ModelClass createModelClassForTest(String projectName, String className) {
		ModelClass modelClass = new ModelClass();
		modelClass.setClassName(className+"EntityTest");
		modelClass.setPackageName(projectName+".entity");
		modelClass.setFileName(className+"EntityTest.java");
		return modelClass;
	}
	
	@Test
	public void testGenerate() throws Exception {
		ModelClass modelClass = createModelClassForTest("model1", "Organization");
		entityBeanTestGenerator.generateClass(modelClass);
	}
	
}
