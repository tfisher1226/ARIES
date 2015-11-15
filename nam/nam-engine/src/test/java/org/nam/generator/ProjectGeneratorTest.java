package org.nam.generator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.PomModuleGenerator;
import nam.client.ClientModuleGenerator;
import nam.data.DataModuleGenerator;
import nam.model.Application;
import nam.model.ModelModuleGenerator;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ProjectUtil;
import nam.service.ServiceModuleGenerator;

import org.aries.Assert;
import org.aries.util.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ProjectGeneratorTest extends AbstractProjectGeneratorTest {

	//private static String FILE_NAME = "bank-0.0.1.wsdl";
	//private static String FILE_NAME = "HelloWorldWS.wsdl";
	//private static String FILE_NAME = "SimpleInvoke/SimpleInvoke.bpel";
	//private static String FILE_NAME = "SimpleInvoke" + File.separator + "SimpleInvoke.aries";
	//private static String FILE_NAME = "AsyncEcho" + File.separator + "async_echo.aries";
	private static String FILE_NAME = "/AsyncEcho/async_echo.aries";
	
	private PomModuleGenerator pomModuleGenerator;
	private ModelModuleGenerator modelModuleGenerator;
	private ClientModuleGenerator clientModuleGenerator;
	private ServiceModuleGenerator serviceModuleGenerator;
	private DataModuleGenerator dataModuleGenerator;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		pomModuleGenerator = new PomModuleGenerator(context);
		modelModuleGenerator = new ModelModuleGenerator(context);
		clientModuleGenerator = new ClientModuleGenerator(context);
		serviceModuleGenerator = new ServiceModuleGenerator(context);
		dataModuleGenerator = new DataModuleGenerator(context);
	}
	
	@After
	public void tearDown() throws Exception {
		pomModuleGenerator = null;
		modelModuleGenerator = null;
		clientModuleGenerator = null;
		serviceModuleGenerator = null;
		dataModuleGenerator = null;
		super.tearDown();
	}

	protected String getProjectName() {
		return "model2";
	}
	
	@Test
	public void testGenerate() throws Exception {
		String fileName = FileUtil.normalizePath(FILE_NAME);
		Project project = getAriesModelForTest(fileName);
		//Project project = getAriesModelForTest(getFilePath(FILE_NAME));
		Assert.notNull(project, "Project must exist");
		Application application = context.getApplication();
		Assert.notNull(application, "Application must exist");
		Collection<Module> modules = ApplicationUtil.getModules(application);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			context.setProject(project);
			context.setApplication(application);
			context.setTemplateHome(context.getTemplateName()+"-"+module.getType().name().toLowerCase());
			generate(project, module);
			//generate sub-models
			List<Project> subProjects = ProjectUtil.getSubProjects(project);
			Iterator<Project> subProjectIterator = subProjects.iterator();
			while (iterator.hasNext()) {
				Project subProject = subProjectIterator.next();
				context.setProject(subProject);
				generate(subProject, module);
			}
		}
	}

	protected void generate(Project project, Module module) throws Exception {
		switch (module.getType()) {
		case POM: pomModuleGenerator.execute(project, module); break;
		case MODEL: modelModuleGenerator.execute(project, module); break;
		case CLIENT: clientModuleGenerator.execute(project, module); break;
		case SERVICE: serviceModuleGenerator.execute(project, module); break;
		}
	}

}
