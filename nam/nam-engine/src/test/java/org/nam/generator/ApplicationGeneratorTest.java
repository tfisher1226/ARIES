package org.nam.generator;

import java.util.Iterator;
import java.util.Set;

import nam.PomModuleBuilder;
import nam.PomModuleGenerator;
import nam.data.DataModuleBuilder;
import nam.data.DataModuleGenerator;
import nam.model.ModelModuleBuilder;
import nam.model.ModelModuleGenerator;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ProjectUtil;

import org.aries.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class ApplicationGeneratorTest extends AbstractModuleGeneratorTest {

	private static String FILE_NAME = "bank-0.0.1.wsdl";
	//private static String FILE_NAME = "sgiusa-0.0.1.wsdl";

	private PomModuleBuilder pomModuleBuilder;
	private PomModuleGenerator pomModuleGenerator;
	private ModelModuleBuilder modelModuleBuilder;
	private ModelModuleGenerator modelModuleGenerator;
	private DataModuleBuilder dataModuleBuilder;
	private DataModuleGenerator dataModuleGenerator;
	//private ClientModuleGenerator clientModuleGenerator;
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		pomModuleBuilder = new PomModuleBuilder(context);
		pomModuleGenerator = new PomModuleGenerator(context);
		modelModuleBuilder = new ModelModuleBuilder(context);
		modelModuleGenerator = new ModelModuleGenerator(context);
		dataModuleBuilder = new DataModuleBuilder(context);
		dataModuleGenerator = new DataModuleGenerator(context);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		pomModuleBuilder = null;
		pomModuleGenerator = null;
		modelModuleBuilder = null;
		modelModuleGenerator = null;
		dataModuleBuilder = null;
		dataModuleGenerator = null;
	}

	@Test
	@Ignore
	public void testGenerate() throws Exception {
		Project project = createProjectFromClasspath(FILE_NAME);
		Assert.notNull(project, "Project must exist");
		Set<Module> allModules = ProjectUtil.getAllModules(project);
		Iterator<Module> iterator = allModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			pomModuleGenerator.execute(project, module);
			modelModuleGenerator.execute(project, module);
			dataModuleGenerator.execute(project, module);
		}
	}

}
