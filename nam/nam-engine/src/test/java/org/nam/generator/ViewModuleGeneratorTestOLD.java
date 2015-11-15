package org.nam.generator;

import nam.ui.src.main.java.UtilClassGenerator;
import nam.ui.src.main.resources.ApplicationPropertiesGenerator;
import nam.ui.src.main.resources.ComponentsPropertiesGenerator;
import nam.ui.src.main.resources.LoggingPropertiesGenerator;
import nam.ui.src.main.resources.SeamPropertiesGenerator;
import nam.ui.src.main.webapp.ErrorXHTMLGenerator;
import nam.ui.src.main.webapp.HeaderXHTMLGenerator;
import nam.ui.src.main.webapp.IndexXHTMLGenerator;
import nam.ui.src.main.webapp.LoginXHTMLGenerator;
import nam.ui.src.main.webapp.MainXHTMLGenerator;
import nam.ui.src.main.webapp.MenubarXHTMLGenerator;
import nam.ui.src.main.webapp.PageTemplateXHTMLGenerator;
import nam.ui.src.main.webapp.WEBINF.ComponentsXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.FacesConfigXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.JBossWebXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.PagesXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.WebXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.tags.TaglibXMLGenerator;

import org.aries.util.properties.PropertyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import aries.generation.AriesModelBuilder;
import aries.generation.EMFModelBuilder;
import aries.generation.engine.GenerationContext;


public class ViewModuleGeneratorTestOLD extends AbstractModuleGeneratorTest {

	//runtime context
	private GenerationContext context;
	
	//model construction
	private EMFModelBuilder emfModelBuilder;
	private AriesModelBuilder ariesModelBuilder;

	//resources generators:
	private ApplicationPropertiesGenerator applicationPropertiesGenerator; 
	private ComponentsPropertiesGenerator componentsPropertiesGenerator; 
	private LoggingPropertiesGenerator loggingPropertiesGenerator; 
	private SeamPropertiesGenerator seamPropertiesGenerator; 

	//webapp generators:
	private IndexXHTMLGenerator indexXHTMLGenerator; 
	private ErrorXHTMLGenerator errorXHTMLGenerator; 
	private HeaderXHTMLGenerator headerXHTMLGenerator; 
	private MenubarXHTMLGenerator menubarXHTMLGenerator; 
	private PageTemplateXHTMLGenerator templateXHTMLGenerator; 
	private LoginXHTMLGenerator loginXHTMLGenerator; 
	private MainXHTMLGenerator mainXHTMLGenerator; 

	//webapp/WEB-INF generators:
	private WebXMLGenerator webXMLGenerator; 
	private JBossWebXMLGenerator jbossWebXMLGenerator; 
	private FacesConfigXMLGenerator facesConfigXMLGenerator; 
	private ComponentsXMLGenerator componentsXMLGenerator; 
	private PagesXMLGenerator pagesXMLGenerator; 

	//webapp/META-INF generators:
	private TaglibXMLGenerator taglibXMLGenerator; 

	//java class generators:
	private UtilClassGenerator utilClassGenerator; 

	
	@Before
	public void setUp() throws Exception {
		context = getGenerationContextForTest();
		//context.setModule(createWarModule());
		
		emfModelBuilder = new EMFModelBuilder(context);
		ariesModelBuilder = new AriesModelBuilder(context);
		
		//resources generators:
		applicationPropertiesGenerator = new ApplicationPropertiesGenerator(context);
		componentsPropertiesGenerator = new ComponentsPropertiesGenerator(context);
		loggingPropertiesGenerator = new LoggingPropertiesGenerator(context);
		seamPropertiesGenerator = new SeamPropertiesGenerator(context);
		
		//webapp generators:
		indexXHTMLGenerator = new IndexXHTMLGenerator(context);
		errorXHTMLGenerator = new ErrorXHTMLGenerator(context);
		headerXHTMLGenerator = new HeaderXHTMLGenerator(context);
		menubarXHTMLGenerator = new MenubarXHTMLGenerator(context);
		templateXHTMLGenerator = new PageTemplateXHTMLGenerator(context);
		loginXHTMLGenerator = new LoginXHTMLGenerator(context);
		mainXHTMLGenerator = new MainXHTMLGenerator(context);
		
		//webapp/WEB-INF generators:
		webXMLGenerator = new WebXMLGenerator(context);
		jbossWebXMLGenerator = new JBossWebXMLGenerator(context);
		facesConfigXMLGenerator = new FacesConfigXMLGenerator(context);
		componentsXMLGenerator = new ComponentsXMLGenerator(context);
		pagesXMLGenerator = new PagesXMLGenerator(context);
		
		//webapp/META-INF generators:
		taglibXMLGenerator = new TaglibXMLGenerator(context);
		
		//java class generators:
		utilClassGenerator = new UtilClassGenerator(context);
		PropertyManager.getInstance().initialize();
	}

	@After
	public void tearDown() throws Exception {
		emfModelBuilder = null;
		ariesModelBuilder = null;
		applicationPropertiesGenerator = null;
		componentsPropertiesGenerator = null;
		loggingPropertiesGenerator = null;
		seamPropertiesGenerator = null;
		indexXHTMLGenerator = null;
		errorXHTMLGenerator = null;
		headerXHTMLGenerator = null;
		menubarXHTMLGenerator = null;
		templateXHTMLGenerator = null;
		loginXHTMLGenerator = null;
		mainXHTMLGenerator = null;
		webXMLGenerator = null;
		jbossWebXMLGenerator = null;
		facesConfigXMLGenerator = null;
		componentsXMLGenerator = null;
		pagesXMLGenerator = null;
		taglibXMLGenerator = null;
		utilClassGenerator = null;
	}

//	protected List<Namespace> getAriesModelForTest() throws Exception {
//		ResourceSet emfResourceSet = getEMFModelForTest();
//		Assert.notNull(emfResourceSet, "ResourceSet must exist");
//		List<Namespace> namespaces = ariesModelBuilder.buildNamespaces(emfResourceSet);
//		return namespaces;
//	}
//
//	private ResourceSet getEMFModelForTest() throws Exception {
//		List<URL> schemaResources = new ArrayList<URL>(); 
//		schemaResources.add(getResourceForTest(FILE_NAME1));
//		schemaResources.add(getResourceForTest(FILE_NAME2));
//		ResourceSet emfResourceSet = emfModelBuilder.buildFromXSD(schemaResources);
//		return emfResourceSet;
//	}

//	private URL getResourceForTest(String fileName) throws Exception {
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		URL resource = classLoader.getResource(fileName);
//		return resource; 
//	}
	
	@Test
	@Ignore
	public void testGenerate() throws Exception {
		applicationPropertiesGenerator.generate();
		componentsPropertiesGenerator.generate();
		loggingPropertiesGenerator.generate();
		seamPropertiesGenerator.generate();
		indexXHTMLGenerator.generate();
		errorXHTMLGenerator.generate();
		headerXHTMLGenerator.generate();
		menubarXHTMLGenerator.generate();
		templateXHTMLGenerator.generate();
		loginXHTMLGenerator.generate();
		mainXHTMLGenerator.generate();
		webXMLGenerator.generate();
		jbossWebXMLGenerator.generate();
		facesConfigXMLGenerator.generate();
		componentsXMLGenerator.generate();
		pagesXMLGenerator.generate();
		taglibXMLGenerator.generate();
		utilClassGenerator.generate();
	}

}
