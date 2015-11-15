package org.nam.generator;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Application;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.service.ServiceModuleGenerator;

import org.apache.commons.io.FilenameUtils;
import org.aries.Assert;
import org.aries.util.FileUtil;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ServiceModuleGeneratorTest extends AbstractModuleGeneratorTest {

	//private static String FILE_NAME = "bank-0.0.1.wsdl";
	//private static String FILE_NAME = "HelloWorldWS.wsdl";
	private static String FILE_NAME = "/SimpleInvoke/SimpleInvoke.bpel";
	//private static String FILE_NAME = "AsyncEcho/async_echo.bpel";
	//private static String FILE_NAME = "OrderProcessing/OrderProcessing.bpel";
	//private static String FILE_NAME = "OrderManagement/OrderManagement.bpel";
	//private static String FILE_NAME = "LoanApproval/LoanApproval.bpel";
	
	private ServiceModuleGenerator serviceModuleGenerator;


	protected String getProjectName() {
		//return "simple_invoke";
		return FilenameUtils.getBaseName(FILE_NAME);
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		serviceModuleGenerator = new ServiceModuleGenerator(context);
	}

	@After
	public void tearDown() throws Exception {
		serviceModuleGenerator = null;
		super.tearDown();
	}

	@Test
	public void testGenerate() throws Exception {
		String fileName = FileUtil.normalizePath(FILE_NAME);
		Project project = getAriesModelForTest(fileName);
		//Project project = getAriesModelForTest(getFilePath(FILE_NAME));
		Assert.notNull(project, "Project must exist");
		Set<Module> allModules = ProjectUtil.getAllModules(project);
		Iterator<Module> iterator = allModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			serviceModuleGenerator.initialize(project, module);
			serviceModuleGenerator.generate(project, module);
		}
	}

	protected void createModulesAndServicesForTest(ResourceSet emfResourceSet, Application application) throws Exception {
		Module serviceModule = ModuleFactory.createModule(application, ModuleType.SERVICE);
		ApplicationUtil.getModules(application).add(serviceModule);
		List<Service> services = engine.getModelBuilder().buildServices(emfResourceSet);
		ModuleUtil.getServices(serviceModule).addAll(services);
	}

	
	
//	protected void getODEModelForTest(String fileName) throws Exception {
//		if (fileName.endsWith("bpel")) {
//			new XPath20ExpressionCompilerBPEL20();
//			BpelC compiler = BpelC.newBpelCompiler();
//			URL resource = ResourceUtil.getResource(fileName);
//			Process process = compile(resource);
////			fileName = resource.getFile();
////			File file = new File(fileName);
////			compiler.compile(file, 2L);
////			fileName = fileName.replace(".bpel", ".cbp");
////			file = new File(fileName);
////			InputStream stream = FileUtil.openFileAsStream(fileName);
////			String fileData = FileUtil.readStreamAsString(stream);
//			System.out.println(process);
//		}
//	}
	
//	protected Process compile(URL bpelResource) throws Exception {
//		byte[] bytes = StreamUtils.read(bpelResource);
//		InputSource inputSource = new InputSource(new ByteArrayInputStream(bytes));
//		String fileName = bpelResource.getFile();
//		File bpelFile = new File(fileName);
//		inputSource.setSystemId(bpelFile.getAbsolutePath());
//		Process process = BpelObjectFactory.getInstance().parse(inputSource, bpelFile.toURI());
//		return process;
//	}

}
