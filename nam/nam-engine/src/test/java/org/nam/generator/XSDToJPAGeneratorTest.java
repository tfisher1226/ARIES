package org.nam.generator;

import java.util.Iterator;
import java.util.List;

import nam.data.src.main.java.EntityBeanBuilder;
import nam.data.src.main.java.EntityBeanGenerator;
import nam.model.Namespace;

import org.aries.util.FileUtil;
import org.aries.util.properties.PropertyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class XSDToJPAGeneratorTest extends AbstractModuleGeneratorTest {

	private static String FILE_NAME1 = "aries-common-1.0.xsd";

	private static String FILE_NAME2 = "sgiusa-0.0.1.xsd";
	
	private EntityBeanBuilder entityBeanBuilder;

	private EntityBeanGenerator entityBeanGenerator;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		initializeContextForTest(createDataModuleForTest());
		//emfModelBuilder = new EMFModelBuilder(context);
		//ariesModelBuilder = new AriesModelBuilder(context);
		entityBeanBuilder = new EntityBeanBuilder(context);
		entityBeanGenerator = new EntityBeanGenerator(context);
		//PropertyManager.getInstance().initialize();
		PropertyManager.getInstance().addProperty("generateTableAnnotation", true);
	}

	@After
	public void tearDown() throws Exception {
//		emfModelBuilder = null;
		entityBeanBuilder = null;
		entityBeanGenerator = null;
	}

	@Test
	public void testBuildFromXSD() throws Exception {
		String fileName1 = FileUtil.normalizePath(FILE_NAME1);
		String fileName2 = FileUtil.normalizePath(FILE_NAME2);
		String[] fileNames = new String[] {fileName1, fileName2};
		List<Namespace> namespaces = getAriesNamespacesForTest(fileNames);
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			//TODO convert this: ModelPackage modelPackage = entityBeanBuilder.buildModelPackage(namespace);
			//TODO convert this: entityBeanGenerator.generate(modelPackage);
		}
	}

}
