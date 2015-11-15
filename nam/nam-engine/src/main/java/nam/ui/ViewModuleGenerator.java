package nam.ui;

import nam.client.src.main.resources.maven.MavenFilterPropertiesFileGenerator;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ModuleUtil;
import nam.ui.src.main.java.ServletListenerGenerator;
import nam.ui.src.main.java.UtilClassGenerator;
import nam.ui.src.main.java.admin.data.ElementConverterGenerator;
import nam.ui.src.main.java.admin.data.ElementDataManagerGenerator;
import nam.ui.src.main.java.admin.data.ElementEventManagerGenerator;
import nam.ui.src.main.java.admin.data.ElementHelperGenerator;
import nam.ui.src.main.java.admin.data.ElementInfoManagerGenerator;
import nam.ui.src.main.java.admin.data.ElementListManagerGenerator;
import nam.ui.src.main.java.admin.data.ElementListObjectGenerator;
import nam.ui.src.main.java.admin.data.ElementPageManagerGenerator;
import nam.ui.src.main.java.admin.data.ElementRecordSectionGenerator;
import nam.ui.src.main.java.admin.data.ElementSelectManagerGenerator;
import nam.ui.src.main.java.admin.data.ElementServiceManagerGenerator;
import nam.ui.src.main.java.admin.data.ElementTreeBuilderGenerator;
import nam.ui.src.main.java.admin.data.ElementTreeManagerGenerator;
import nam.ui.src.main.java.admin.data.ElementValidatorGenerator;
import nam.ui.src.main.java.admin.data.ElementWizardManagerGenerator;
import nam.ui.src.main.resources.ComponentsPropertiesGenerator;
import nam.ui.src.main.resources.LoggingPropertiesGenerator;
import nam.ui.src.main.resources.SeamPropertiesGenerator;
import nam.ui.src.main.webapp.ActionsXHTMLGenerator;
import nam.ui.src.main.webapp.ErrorXHTMLGenerator;
import nam.ui.src.main.webapp.IndexXHTMLGenerator;
import nam.ui.src.main.webapp.LoginXHTMLGenerator;
import nam.ui.src.main.webapp.MainXHTMLGenerator;
import nam.ui.src.main.webapp.MenubarXHTMLGenerator;
import nam.ui.src.main.webapp.NavigatorXHTMLGenerator;
import nam.ui.src.main.webapp.ViewerXHTMLGenerator;
import nam.ui.src.main.webapp.WEBINF.BeansXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.ComponentsXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.FacesConfigXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.PagesXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.WebXMLGenerator;
import nam.ui.src.main.webapp.WEBINF.tags.TaglibXMLGenerator;
import nam.ui.src.main.webapp.admin.DomainModelXhtmlGenerator;
import nam.ui.src.main.webapp.admin.DomainServicesXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementActionsXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementInfoDialogXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementInfoPaneXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementListActionsXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementListDialogXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementListMenuXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementListPageXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementListPaneXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementListTableXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementListToolbarXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementManagementNavigatorXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementManagementPageXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementManagementSectionXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementManagementViewerXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementRecordMenuXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementRecordNavigatorXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementRecordPageXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementRecordSectionXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementRecordViewerXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementSelectDialogXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementSelectPaneXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementTreeActionsXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementTreeMenuXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementTreePageXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementTreeViewerXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementTreeXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementWizardControlXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementWizardMenuXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementWizardNavigatorXhtmlGenerator;
import nam.ui.src.main.webapp.admin.ElementWizardPageXhtmlGenerator;
import nam.ui.src.main.webapp.pages.DataModelXHTMLGenerator;

import org.aries.util.properties.PropertyManager;

import aries.codegen.application.AbstractModuleGenerator;
import aries.generation.engine.GenerationContext;


public class ViewModuleGenerator extends AbstractModuleGenerator {

	private ViewModuleBuilder viewModuleBuilder; 
	private ViewProjectGenerator viewProjectGenerator;

	//main resources generators:
	//private ApplicationPropertiesGenerator applicationPropertiesGenerator; 
	private ComponentsPropertiesGenerator componentsPropertiesGenerator; 
	private LoggingPropertiesGenerator loggingPropertiesGenerator; 
	private SeamPropertiesGenerator seamPropertiesGenerator; 

	//main resources/maven generators:
	private MavenFilterPropertiesFileGenerator mavenFilterPropertiesFileGenerator;

	//test resources generators:

	//test resources/maven generators:
	private MavenFilterPropertiesFileGenerator testMavenFilterPropertiesFileGenerator;
	
	//webapp generators:
	private IndexXHTMLGenerator indexXHTMLGenerator; 
	private ErrorXHTMLGenerator errorXHTMLGenerator; 
	//private HeaderXHTMLGenerator headerXHTMLGenerator; 
	private MenubarXHTMLGenerator menubarXHTMLGenerator; 
	//private PageSupportXHTMLGenerator supportXHTMLGenerator; 
	//private PageTemplateXHTMLGenerator templateXHTMLGenerator; 
	private LoginXHTMLGenerator loginXHTMLGenerator; 
	private MainXHTMLGenerator mainXHTMLGenerator; 
	private ActionsXHTMLGenerator actionsXHTMLGenerator; 
	private ViewerXHTMLGenerator viewerXHTMLGenerator; 
	private NavigatorXHTMLGenerator navigatorXHTMLGenerator; 

	//webapp/WEB-INF generators:
	private WebXMLGenerator webXMLGenerator; 
	private BeansXMLGenerator beansXMLGenerator; 
	//private JBossWebXMLGenerator jbossWebXMLGenerator; 
	private FacesConfigXMLGenerator facesConfigXMLGenerator; 
	private ComponentsXMLGenerator componentsXMLGenerator; 
	private PagesXMLGenerator pagesXMLGenerator; 

	//webapp/META-INF generators:
	private TaglibXMLGenerator taglibXMLGenerator; 

	//webapp/WEB-INF UI component generators:
	private DataModelXHTMLGenerator dataModelXHTMLGenerator;
	private DomainModelXhtmlGenerator domainModelXhtmlGenerator; 
	private DomainServicesXhtmlGenerator domainServicesXhtmlGenerator; 
	private ElementActionsXhtmlGenerator elementActionsXhtmlGenerator; 
	private ElementInfoDialogXhtmlGenerator elementInfoDialogXhtmlGenerator; 
	private ElementInfoPaneXhtmlGenerator elementInfoPaneXhtmlGenerator; 
	private ElementListActionsXhtmlGenerator elementListActionsXhtmlGenerator; 
	private ElementListDialogXhtmlGenerator elementListDialogXhtmlGenerator; 
	private ElementListPaneXhtmlGenerator elementListPaneXhtmlGenerator; 
	private ElementListMenuXhtmlGenerator elementListMenuXhtmlGenerator; 
	private ElementListPageXhtmlGenerator elementListPageXhtmlGenerator; 
	private ElementListTableXhtmlGenerator elementListTableXhtmlGenerator; 
	private ElementListToolbarXhtmlGenerator elementListToolbarXhtmlGenerator; 
	private ElementManagementPageXhtmlGenerator elementManagementPageXhtmlGenerator; 
	private ElementManagementNavigatorXhtmlGenerator elementManagementNavigatorXhtmlGenerator; 
	private ElementManagementSectionXhtmlGenerator elementManagementSectionXhtmlGenerator; 
	private ElementManagementViewerXhtmlGenerator elementManagementViewerXhtmlGenerator; 
	private ElementRecordPageXhtmlGenerator elementRecordPageXhtmlGenerator; 
	private ElementRecordMenuXhtmlGenerator elementRecordMenuXhtmlGenerator; 
	private ElementRecordNavigatorXhtmlGenerator elementRecordNavigatorXhtmlGenerator; 
	private ElementRecordSectionXhtmlGenerator elementRecordSectionXhtmlGenerator; 
	private ElementRecordViewerXhtmlGenerator elementRecordViewerXhtmlGenerator; 
	private ElementSelectDialogXhtmlGenerator elementSelectDialogXhtmlGenerator; 
	private ElementSelectPaneXhtmlGenerator elementSelectPaneXhtmlGenerator; 
	private ElementTreeXhtmlGenerator elementTreeXhtmlGenerator; 
	private ElementTreeActionsXhtmlGenerator elementTreeActionsXhtmlGenerator; 
	private ElementTreeMenuXhtmlGenerator elementTreeMenuXhtmlGenerator; 
	private ElementTreePageXhtmlGenerator elementTreePageXhtmlGenerator; 
	private ElementTreeViewerXhtmlGenerator elementTreeViewerXhtmlGenerator; 
	private ElementWizardPageXhtmlGenerator elementWizardPageXhtmlGenerator; 
	private ElementWizardMenuXhtmlGenerator elementWizardMenuXhtmlGenerator; 
	private ElementWizardControlXhtmlGenerator elementWizardControlXhtmlGenerator; 
	private ElementWizardNavigatorXhtmlGenerator elementWizardNavigatorXhtmlGenerator; 
	//private ElementPanelXHTMLGenerator elementPanelXHTMLGenerator; 
	//private ElementListPanelXHTMLGenerator elementListPanelXHTMLGenerator; 

	//java class generators:
	private ServletListenerGenerator servletListenerGenerator;
	//private ElementUtilGenerator elementUtilGenerator;
	private ElementHelperGenerator elementHelperGenerator;
	private ElementConverterGenerator elementConverterGenerator;
	private ElementValidatorGenerator elementValidatorGenerator;
	private ElementDataManagerGenerator elementDataManagerGenerator;
	private ElementEventManagerGenerator elementEventManagerGenerator;
	private ElementPageManagerGenerator elementPageManagerGenerator;
	private ElementRecordSectionGenerator elementRecordSectionGenerator;
	private ElementSelectManagerGenerator elementSelectManagerGenerator;
	private ElementServiceManagerGenerator elementServiceManagerGenerator;
	private ElementInfoManagerGenerator elementInfoManagerGenerator;
	private ElementListManagerGenerator elementListManagerGenerator;
	private ElementListObjectGenerator elementListObjectGenerator;
	private ElementTreeManagerGenerator elementTreeManagerGenerator;
	private ElementTreeBuilderGenerator elementTreeBuilderGenerator;
	private ElementWizardManagerGenerator elementWizardManagerGenerator;
	private UtilClassGenerator utilClassGenerator;

	
	public ViewModuleGenerator(GenerationContext context) {
		viewModuleBuilder = new ViewModuleBuilder(context);
		viewProjectGenerator = new ViewProjectGenerator(context);

		//main resources generators:
		//applicationPropertiesGenerator = new ApplicationPropertiesGenerator(context);
		componentsPropertiesGenerator = new ComponentsPropertiesGenerator(context);
		loggingPropertiesGenerator = new LoggingPropertiesGenerator(context);
		seamPropertiesGenerator = new SeamPropertiesGenerator(context);
		
		//test resources generators:
		
		//main resources/maven generators:
		mavenFilterPropertiesFileGenerator = new MavenFilterPropertiesFileGenerator(context);

		//test resources/maven generators:
		testMavenFilterPropertiesFileGenerator = new MavenFilterPropertiesFileGenerator(context);
		
		//webapp generators:
		indexXHTMLGenerator = new IndexXHTMLGenerator(context);
		errorXHTMLGenerator = new ErrorXHTMLGenerator(context);
		//headerXHTMLGenerator = new HeaderXHTMLGenerator(context);
		menubarXHTMLGenerator = new MenubarXHTMLGenerator(context);
		//supportXHTMLGenerator = new PageSupportXHTMLGenerator(context);
		//templateXHTMLGenerator = new PageTemplateXHTMLGenerator(context);
		loginXHTMLGenerator = new LoginXHTMLGenerator(context);
		mainXHTMLGenerator = new MainXHTMLGenerator(context);
		actionsXHTMLGenerator = new ActionsXHTMLGenerator(context);
		navigatorXHTMLGenerator = new NavigatorXHTMLGenerator(context);
		viewerXHTMLGenerator = new ViewerXHTMLGenerator(context);
		
		dataModelXHTMLGenerator = new DataModelXHTMLGenerator(context);
		domainModelXhtmlGenerator = new DomainModelXhtmlGenerator(context);
		domainServicesXhtmlGenerator = new DomainServicesXhtmlGenerator(context);
		elementActionsXhtmlGenerator = new ElementActionsXhtmlGenerator(context);
		elementInfoDialogXhtmlGenerator = new ElementInfoDialogXhtmlGenerator(context);
		elementInfoPaneXhtmlGenerator = new ElementInfoPaneXhtmlGenerator(context);
		elementListActionsXhtmlGenerator = new ElementListActionsXhtmlGenerator(context);
		elementListDialogXhtmlGenerator = new ElementListDialogXhtmlGenerator(context);
		elementListPaneXhtmlGenerator = new ElementListPaneXhtmlGenerator(context);
		elementListMenuXhtmlGenerator = new ElementListMenuXhtmlGenerator(context);
		elementListPageXhtmlGenerator = new ElementListPageXhtmlGenerator(context);
		elementListTableXhtmlGenerator = new ElementListTableXhtmlGenerator(context);
		elementListToolbarXhtmlGenerator = new ElementListToolbarXhtmlGenerator(context);
		elementManagementPageXhtmlGenerator = new ElementManagementPageXhtmlGenerator(context);
		elementManagementNavigatorXhtmlGenerator = new ElementManagementNavigatorXhtmlGenerator(context);
		elementManagementSectionXhtmlGenerator = new ElementManagementSectionXhtmlGenerator(context);
		elementManagementViewerXhtmlGenerator = new ElementManagementViewerXhtmlGenerator(context);
		elementRecordPageXhtmlGenerator = new ElementRecordPageXhtmlGenerator(context);
		elementRecordMenuXhtmlGenerator = new ElementRecordMenuXhtmlGenerator(context);
		elementRecordNavigatorXhtmlGenerator = new ElementRecordNavigatorXhtmlGenerator(context);
		elementRecordSectionXhtmlGenerator = new ElementRecordSectionXhtmlGenerator(context);
		elementRecordViewerXhtmlGenerator = new ElementRecordViewerXhtmlGenerator(context);
		elementSelectDialogXhtmlGenerator = new ElementSelectDialogXhtmlGenerator(context);
		elementSelectPaneXhtmlGenerator = new ElementSelectPaneXhtmlGenerator(context);
		elementTreeXhtmlGenerator = new ElementTreeXhtmlGenerator(context);
		elementTreeActionsXhtmlGenerator = new ElementTreeActionsXhtmlGenerator(context);
		elementTreeMenuXhtmlGenerator = new ElementTreeMenuXhtmlGenerator(context);
		elementTreePageXhtmlGenerator = new ElementTreePageXhtmlGenerator(context);
		elementTreeViewerXhtmlGenerator = new ElementTreeViewerXhtmlGenerator(context);
		elementWizardPageXhtmlGenerator = new ElementWizardPageXhtmlGenerator(context);
		elementWizardMenuXhtmlGenerator = new ElementWizardMenuXhtmlGenerator(context);
		elementWizardControlXhtmlGenerator = new ElementWizardControlXhtmlGenerator(context);
		elementWizardNavigatorXhtmlGenerator = new ElementWizardNavigatorXhtmlGenerator(context);
		//elementPanelXHTMLGenerator = new ElementPanelXHTMLGenerator(context);
		//elementListPanelXHTMLGenerator = new ElementListPanelXHTMLGenerator(context);
		
		//webapp/WEB-INF generators:
		webXMLGenerator = new WebXMLGenerator(context);
		beansXMLGenerator = new BeansXMLGenerator(context);
		//jbossWebXMLGenerator = new JBossWebXMLGenerator(context);
		facesConfigXMLGenerator = new FacesConfigXMLGenerator(context);
		componentsXMLGenerator = new ComponentsXMLGenerator(context);
		pagesXMLGenerator = new PagesXMLGenerator(context);
		
		//webapp/META-INF generators:
		taglibXMLGenerator = new TaglibXMLGenerator(context);
		
		//java class generators:
		servletListenerGenerator = new ServletListenerGenerator(context);
		//elementUtilGenerator = new ElementUtilGenerator(context);
		elementHelperGenerator = new ElementHelperGenerator(context);
		elementConverterGenerator = new ElementConverterGenerator(context);
		elementValidatorGenerator = new ElementValidatorGenerator(context);
		elementDataManagerGenerator = new ElementDataManagerGenerator(context);
		elementEventManagerGenerator = new ElementEventManagerGenerator(context);
		elementPageManagerGenerator = new ElementPageManagerGenerator(context);
		elementRecordSectionGenerator = new ElementRecordSectionGenerator(context);
		elementSelectManagerGenerator = new ElementSelectManagerGenerator(context);
		elementServiceManagerGenerator = new ElementServiceManagerGenerator(context);
		elementInfoManagerGenerator = new ElementInfoManagerGenerator(context);
		elementListManagerGenerator = new ElementListManagerGenerator(context);
		elementListObjectGenerator = new ElementListObjectGenerator(context);
		elementTreeManagerGenerator = new ElementTreeManagerGenerator(context);
		elementTreeBuilderGenerator = new ElementTreeBuilderGenerator(context);
		elementWizardManagerGenerator = new ElementWizardManagerGenerator(context);
		utilClassGenerator = new UtilClassGenerator(context);
		PropertyManager.getInstance().initialize();
		this.context = context;
	}

	public void initialize(Project project, Module module) throws Exception {
		viewModuleBuilder.initialize(project, module);
		domainModelXhtmlGenerator.initialize(project, module);
		domainServicesXhtmlGenerator.initialize(project, module);
		elementActionsXhtmlGenerator.initialize(project, module);
		elementInfoDialogXhtmlGenerator.initialize(project, module);
		elementInfoPaneXhtmlGenerator.initialize(project, module);
		elementListActionsXhtmlGenerator.initialize(project, module);
		elementListDialogXhtmlGenerator.initialize(project, module);
		elementListPaneXhtmlGenerator.initialize(project, module);
		elementListMenuXhtmlGenerator.initialize(project, module);
		elementListPageXhtmlGenerator.initialize(project, module);
		elementListTableXhtmlGenerator.initialize(project, module);
		elementListToolbarXhtmlGenerator.initialize(project, module);
		elementManagementPageXhtmlGenerator.initialize(project, module);
		elementManagementNavigatorXhtmlGenerator.initialize(project, module);
		elementManagementSectionXhtmlGenerator.initialize(project, module);
		elementManagementViewerXhtmlGenerator.initialize(project, module);
		elementRecordPageXhtmlGenerator.initialize(project, module);
		elementRecordMenuXhtmlGenerator.initialize(project, module);
		elementRecordNavigatorXhtmlGenerator.initialize(project, module);
		elementRecordSectionXhtmlGenerator.initialize(project, module);
		elementRecordViewerXhtmlGenerator.initialize(project, module);
		elementSelectDialogXhtmlGenerator.initialize(project, module);
		elementSelectPaneXhtmlGenerator.initialize(project, module);
		elementTreeXhtmlGenerator.initialize(project, module);
		elementTreeActionsXhtmlGenerator.initialize(project, module);
		elementTreeMenuXhtmlGenerator.initialize(project, module);
		elementTreePageXhtmlGenerator.initialize(project, module);
		elementTreeViewerXhtmlGenerator.initialize(project, module);
		elementWizardPageXhtmlGenerator.initialize(project, module);
		elementWizardMenuXhtmlGenerator.initialize(project, module);
		elementWizardControlXhtmlGenerator.initialize(project, module);
		elementWizardNavigatorXhtmlGenerator.initialize(project, module);
		//elementPanelXHTMLGenerator.initialize(project, module);
		//elementListPanelXHTMLGenerator.initialize(project, module);
	}
	
	public void generate(Project project, Module module) throws Exception {
		if (context.canGenerateWAR()) {
			System.out.println("\nGenerating UI-module: "+ModuleUtil.getModuleId(module));
			if (context.canGenerate("project"))
				viewProjectGenerator.generate(project, module);
			if (context.canGenerate("sources"))
				generateSources(project, module);
			if (context.canGenerate("tests"))
				generateTests(project, module);
		}
	}
	
	protected void generateSourcesOLD(Project project, Module module) throws Exception {
		context.setOptionGenerateTests(false);
		viewModuleBuilder.build(project, module);
		//elementPanelXHTMLGenerator.generate(project, module);
		//elementListPanelXHTMLGenerator.generate(project, module);
	}
	
	protected void generateSources(Project project, Module module) throws Exception {
		context.setOptionGenerateTests(false);

		//main resources generators:
		//applicationPropertiesGenerator.generate();
		componentsPropertiesGenerator.generate();
		loggingPropertiesGenerator.generate();
		seamPropertiesGenerator.generate();

		//test resources generators:
		
		//main resources/maven generators:
		mavenFilterPropertiesFileGenerator.generate();

		//test resources/maven generators:
		testMavenFilterPropertiesFileGenerator.generateForTest();
		
		//webapp/WEB-INF generators:
		webXMLGenerator.generate();
		beansXMLGenerator.generate();
		//jbossWebXMLGenerator.generate();
		facesConfigXMLGenerator.generate();
		componentsXMLGenerator.generate();
		pagesXMLGenerator.generate();

		//webapp/META-INF generators:
		taglibXMLGenerator.generate();

		//webapp generators:
		indexXHTMLGenerator.generate();
		errorXHTMLGenerator.generate();
		//headerXHTMLGenerator.generate();
		menubarXHTMLGenerator.generate();
		//supportXHTMLGenerator.generate();
		//templateXHTMLGenerator.generate();
		loginXHTMLGenerator.generate();
		mainXHTMLGenerator.generate();
		actionsXHTMLGenerator.generate();
		navigatorXHTMLGenerator.generate();
		viewerXHTMLGenerator.generate();

		domainModelXhtmlGenerator.generate(project, module);
		domainServicesXhtmlGenerator.generate(project, module);
		elementActionsXhtmlGenerator.generate(project, module);
		elementInfoDialogXhtmlGenerator.generate(project, module);
		elementInfoPaneXhtmlGenerator.generate(project, module);
		elementListActionsXhtmlGenerator.generate(project, module);
		elementListDialogXhtmlGenerator.generate(project, module);
		elementListPaneXhtmlGenerator.generate(project, module);
		elementListMenuXhtmlGenerator.generate(project, module);
		elementListPageXhtmlGenerator.generate(project, module);
		elementListTableXhtmlGenerator.generate(project, module);
		elementListToolbarXhtmlGenerator.generate(project, module);
		elementManagementPageXhtmlGenerator.generate(project, module);
		elementManagementNavigatorXhtmlGenerator.generate(project, module);
		elementManagementSectionXhtmlGenerator.generate(project, module);
		elementManagementViewerXhtmlGenerator.generate(project, module);
		elementRecordPageXhtmlGenerator.generate(project, module);
		elementRecordMenuXhtmlGenerator.generate(project, module);
		elementRecordNavigatorXhtmlGenerator.generate(project, module);
		elementRecordSectionXhtmlGenerator.generate(project, module);
		elementRecordViewerXhtmlGenerator.generate(project, module);
		elementSelectDialogXhtmlGenerator.generate(project, module);
		elementSelectPaneXhtmlGenerator.generate(project, module);
		elementTreeXhtmlGenerator.generate(project, module);
		elementTreeActionsXhtmlGenerator.generate(project, module);
		elementTreeMenuXhtmlGenerator.generate(project, module);
		elementTreePageXhtmlGenerator.generate(project, module);
		elementTreeViewerXhtmlGenerator.generate(project, module);
		elementWizardPageXhtmlGenerator.generate(project, module);
		elementWizardMenuXhtmlGenerator.generate(project, module);
		elementWizardControlXhtmlGenerator.generate(project, module);
		elementWizardNavigatorXhtmlGenerator.generate(project, module);
		//elementListPanelXHTMLGenerator.generate(project, module);
		//elementPanelXHTMLGenerator.generate(project, module);
		//elementPanelXHTMLGenerator.generate(viewModuleBuilder.buildElementPanelXhtmlFile());
		
		//java class generators:
		servletListenerGenerator.generateClass(viewModuleBuilder.buildServletListenerClass(project, module));
		//elementUtilGenerator.generateClasses(viewModuleBuilder.buildElementUtilClasses(project, module));
		elementHelperGenerator.generateClasses(viewModuleBuilder.buildElementHelperClasses(project, module));
		elementConverterGenerator.generateClasses(viewModuleBuilder.buildElementConverterClasses(project, module));
		elementValidatorGenerator.generateClasses(viewModuleBuilder.buildElementValidatorClasses(project, module));
		elementDataManagerGenerator.generateClasses(viewModuleBuilder.buildElementDataManagerClasses(project, module));
		elementEventManagerGenerator.generateClasses(viewModuleBuilder.buildElementEventManagerClasses(project, module));
		elementPageManagerGenerator.generateClasses(viewModuleBuilder.buildElementPageManagerClasses(project, module));
		elementRecordSectionGenerator.generateClasses(viewModuleBuilder.buildElementRecordSectionClasses(project, module));
		elementSelectManagerGenerator.generateClasses(viewModuleBuilder.buildElementSelectManagerClasses(project, module));
		elementServiceManagerGenerator.generateClasses(viewModuleBuilder.buildElementServiceManagerClasses(project, module));
		elementInfoManagerGenerator.generateClasses(viewModuleBuilder.buildElementInfoManagerClasses(project, module));
		elementListManagerGenerator.generateClasses(viewModuleBuilder.buildElementListManagerClasses(project, module));
		elementListObjectGenerator.generateClasses(viewModuleBuilder.buildElementListObjectClasses(project, module));
		elementTreeManagerGenerator.generateClasses(viewModuleBuilder.buildElementTreeManagerClasses(project, module));
		elementTreeBuilderGenerator.generateClasses(viewModuleBuilder.buildElementTreeBuilderClasses(project, module));
		elementWizardManagerGenerator.generateClasses(viewModuleBuilder.buildElementWizardManagerClasses(project, module));
		utilClassGenerator.generate();
	}

	
	protected void generateTests(Project project, Module module) throws Exception {
		context.setOptionGenerateTests(true);
	}

}
