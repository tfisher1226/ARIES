package nam.client.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.model.Application;
import nam.model.Information;
import nam.model.Messaging;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.InformationUtil;
import nam.model.util.MessagingUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;


/**
 * Builds a simple, standalone Java application to launch a client request 
 * via the Service Proxy (i.e. client-side) Implementation {@link ModelClass} 
 * object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class TestEARFactoryBuilder extends AbstractBeanBuilder {

	public TestEARFactoryBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelClass build(Application application) throws Exception {
		String namespace = application.getNamespace();
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		String applicationName = NameUtil.capName(application.getName());
		String className = applicationName+"TestEARBuilder";
		String beanName = NameUtil.uncapName(className);
		String beanType = TypeUtil.getTypeFromPackageAndClass(packageName, className);
		
		ModelClass modelClass = new ModelClass();
		modelClass.setType(beanType);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setParentClassName("AbstractArquillianEARBuilder");
		modelClass.setName(beanName);
		initializeClass(modelClass, application);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Application application) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeImportedClasses(modelClass, application);
		initializeInstanceConstructors(modelClass, application);
		initializeInstanceFields(modelClass, application);
		initializeInstanceOperations(modelClass, application);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Application application) throws Exception {
		modelClass.addImportedClass("org.aries.test.AbstractArquillianEARBuilder");
		modelClass.addImportedClass("org.aries.test.ApplicationXmlBuilder");
		modelClass.addImportedClass("org.aries.test.EnterpriseArchiveBuilder");
		modelClass.addImportedClass("org.jboss.shrinkwrap.api.asset.StringAsset");
		modelClass.addImportedClass("org.jboss.shrinkwrap.api.spec.EnterpriseArchive");
		modelClass.addImportedClass("org.jboss.shrinkwrap.api.spec.JavaArchive");
		modelClass.addImportedClass("org.jboss.shrinkwrap.api.spec.WebArchive");
	}

	protected void initializeInstanceConstructors(ModelClass modelClass, Application application) {
		modelClass.addInstanceConstructor(createConstructor1(modelClass, application));
		modelClass.addInstanceConstructor(createConstructor2(modelClass, application));
	}

	protected ModelConstructor createConstructor1(ModelClass modelClass, Application application) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.setName(modelClass.getClassName());
		
		Buf buf = new Buf();
		buf.putLine2("this(NAME);");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	protected ModelConstructor createConstructor2(ModelClass modelClass, Application application) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.setName(modelClass.getClassName());
		modelConstructor.addParameter(createParameter("String", "earName"));
		
		Buf buf = new Buf();
		buf.putLine2("earBuilder = createEnterpriseArchiveBuilder(earName);");
		buf.putLine2("appXmlBuilder = new ApplicationXmlBuilder();");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Application application) throws Exception {
		modelClass.addStaticAttribute(createStaticField_name(application));
		modelClass.addInstanceAttribute(createField_earBuilder(application));
		modelClass.addInstanceAttribute(createField_applicationXmlBuilder(application));
		modelClass.addInstanceAttribute(createField_includeWar(application));
	}

	protected ModelAttribute createStaticField_name(Application application) throws Exception {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setName("NAME");
		modelAttribute.setType("String");
		modelAttribute.setPackageName("java.lang");
		modelAttribute.setClassName("String");
		modelAttribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		modelAttribute.setDefault("\""+application.getContextRoot()+".ear\"");
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		//modelAttribute.setGenerateAddMethod(true);
		return modelAttribute;
	}

	protected ModelAttribute createField_earBuilder(Application application) throws Exception {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setName("earBuilder");
		modelAttribute.setType("boolean");
		modelAttribute.setPackageName("org.aries.test");
		modelAttribute.setClassName("EnterpriseArchiveBuilder");
		modelAttribute.setModifiers(Modifier.PRIVATE);
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		//modelAttribute.setGenerateAddMethod(true);
		return modelAttribute;
	}

	protected ModelAttribute createField_applicationXmlBuilder(Application application) throws Exception {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setName("appXmlBuilder");
		modelAttribute.setPackageName("org.aries.test");
		modelAttribute.setClassName("ApplicationXmlBuilder");
		modelAttribute.setModifiers(Modifier.PRIVATE);
		//modelAttribute.setType(TypeUtil.getTypeFromPackageAndClass("org.aries.test", "ApplicationXmlBuilder"));
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		//modelAttribute.setGenerateAddMethod(true);
		return modelAttribute;
	}
	
	protected ModelAttribute createField_includeWar(Application application) throws Exception {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setName("includeWar");
		modelAttribute.setType("boolean");
		modelAttribute.setPackageName("java.lang");
		modelAttribute.setClassName("boolean");
		modelAttribute.setModifiers(Modifier.PRIVATE);
		//modelAttribute.setGenerateGetter(false);
		//modelAttribute.setGenerateAddMethod(true);
		return modelAttribute;
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Application application) throws Exception {
		modelClass.addInstanceOperation(createOperation_addAsModule());
		modelClass.addInstanceOperation(createOperation_addAsModule2());
		modelClass.addInstanceOperation(createOperation_createEAR(application));
		modelClass.addInstanceOperation(createOperation_getApplicationXML(application));
	}

	protected ModelOperation createOperation_addAsModule() {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("addAsModule");
		modelOperation.addParameter(createParameter("WebArchive", "webArchive"));
		modelOperation.addParameter(createParameter("String", "contextRoot"));

		Buf buf = new Buf();
		buf.putLine2("appXmlBuilder.addWebModule(webArchive.getName(), contextRoot);");
		buf.putLine2("earBuilder.addAsModule(webArchive);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_addAsModule2() {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("addAsModule");
		modelOperation.addParameter(createParameter("JavaArchive", "javaArchive"));

		Buf buf = new Buf();
		buf.putLine2("earBuilder.addAsModule(javaArchive);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_createEAR(Application application) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("createEAR");
		modelOperation.setResultType("EnterpriseArchive");

		Buf buf = new Buf();
		buf.put(getSource_ProjectModelDependencies(application));
		buf.put(getSource_ApplicationModelDependencies(application, ModuleType.MODEL));
		buf.put(getSource_ApplicationModelDependencies(application, ModuleType.DATA));
		buf.put(getSource_ApplicationModelDependencies(application, ModuleType.CLIENT));
		buf.put(getSource_ApplicationModelDependencies(application, ModuleType.SERVICE));
		
		buf.put(getSource_ApplicationModelDependencies(application, ModuleType.VIEW));
		
		
		
		//buf.putLine2("earBuilder.addJavaModuleToEar(\"org.aries\", \"tx-manager-model\", \"0.0.1-SNAPSHOT\");");
		//buf.putLine2("earBuilder.addJavaModuleToEar(\"org.aries\", \"tx-manager-base\", \"0.0.1-SNAPSHOT\");");
		buf.putLine2("earBuilder.addEjbModuleToEar(\"org.aries\", \"tx-manager-client\", \"0.0.1-SNAPSHOT\");");
		//buf.putLine2("earBuilder.addJavaModuleToEar(\"org.aries\", \"tx-manager-registry\", \"0.0.1-SNAPSHOT\");");
		buf.putLine2("earBuilder.addJavaModuleToEar(\"admin\", \"admin-model\", \"0.0.1-SNAPSHOT\");");
		buf.putLine2("earBuilder.addEjbModuleToEar(\"admin\", \"admin-client\", \"0.0.1-SNAPSHOT\");");
		buf.putLine2("earBuilder.addEjbModuleToEar(\"admin\", \"admin-service\", \"0.0.1-SNAPSHOT\");");
		buf.putLine2("earBuilder.addEjbModuleToEar(\"admin\", \"admin-data\", \"0.0.1-SNAPSHOT\");");
		//buf.putLine2("earBuilder.addJavaModuleToEar(\"org.aries\", \"event-model\", \"0.0.1-SNAPSHOT\");");
		//buf.putLine2("earBuilder.addJavaModuleToEar(\"org.aries\", \"event-client\", \"0.0.1-SNAPSHOT\");");
		buf.putLine2("earBuilder.addEjbModuleToEar(\"org.aries\", \"common-client\", \"0.0.1-SNAPSHOT\");");
		buf.putLine2("earBuilder.addEjbModuleToEar(\"org.aries\", \"common-data\", \"0.0.1-SNAPSHOT\");");
		buf.putLine2("");
		buf.putLine2("earBuilder.buildApplicationXml(getApplicationXml());");
		buf.putLine2("earBuilder.buildDeploymentXml(getDeploymentXml());");
		
		Collection<String> dsDescriptorFileNames = getDSDescriptorFileNames(application);
		Collection<String> jmsDescriptorFileNames = getJMSDescriptorFileNames(application);

		if (!dsDescriptorFileNames.isEmpty()) {
			buf.putLine2("if (isDeployDs()) {");
			Iterator<String> iterator = dsDescriptorFileNames.iterator();
			while (iterator.hasNext()) {
				String fileName = iterator.next();
				buf.putLine2("	earBuilder.addAsResource(\""+fileName+"\");");
			}
			buf.putLine2("}");
		}
		
		if (!jmsDescriptorFileNames.isEmpty()) {
			buf.putLine2("if (isDeployJms()) {");
			Iterator<String> iterator = jmsDescriptorFileNames.iterator();
			while (iterator.hasNext()) {
				String fileName = iterator.next();
				buf.putLine2("	earBuilder.addAsResource(\""+fileName+"\");");
			}
			buf.putLine2("}");
		}
		
		buf.putLine2("");
		buf.putLine2("EnterpriseArchive archive = earBuilder.getArchive();");
		buf.putLine2("return archive;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected Collection<String> getDSDescriptorFileNames(Application application) {
		List<String> fileNames = new ArrayList<String>();
		Project project = context.getProject();
		Collection<Module> serviceModules = ApplicationUtil.getServiceModules(application);
		Iterator<Module> iterator = serviceModules.iterator();
		while (iterator.hasNext()) {
			Module serviceModule = iterator.next();
			Information information = serviceModule.getInformation();
			if (information == null)
				continue;
			
			Collection<Namespace> namespaces = InformationUtil.getAllNamespaces(information);
			Iterator<Namespace> iterator2 = namespaces.iterator();
			while (iterator2.hasNext()) {
				Namespace namespace = iterator2.next();
				Module moduleforNamespace = context.getDataModuleByNamespace(namespace);
				if (moduleforNamespace == null)
					continue;
				
				Persistence persistenceBlock = ProjectUtil.getPersistenceBlockByNamespace(project, namespace.getUri());
				String dataSourceFileName = PersistenceUtil.getDataSourceFileName(persistenceBlock);
				fileNames.add(dataSourceFileName);
			}
		}
		Collections.sort(fileNames);
		return fileNames;
	}
	
	protected Collection<String> getJMSDescriptorFileNames(Application application) {
		List<String> fileNames = new ArrayList<String>();
		List<Project> projects = context.getProjectList();

		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(projects);
		Iterator<Messaging> iterator = messagingBlocks.iterator();
		while (iterator.hasNext()) {
			Messaging messagingBlock = iterator.next();
			String fileName = MessagingUtil.getJMSDescriptorFileName(messagingBlock);
			fileNames.add(fileName);
		}
		
		Collections.sort(fileNames);
		return fileNames;
	}
	
	protected String getSource_ProjectModelDependencies(Application application) throws Exception {
		Buf buf = new Buf();
		Set<Module> modules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module module = moduleIterator.next();
			String groupId = module.getGroupId();
			String artifactId = module.getArtifactId();
			String version = module.getVersion();
			buf.putLine2("earBuilder.addJavaModuleToEar(\""+groupId+"\", \""+artifactId+"\", \""+version+"\");");
		}
		return buf.get();
	}
	
	protected String getSource_ApplicationModelDependencies(Application application, ModuleType moduleType) throws Exception {
		Buf buf = new Buf();
		Collection<Module> modules = ApplicationUtil.getModules(application, moduleType);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			String groupId = module.getGroupId();
			String artifactId = module.getArtifactId();
			String version = module.getVersion();
			switch (moduleType) {
			case MODEL:
				buf.putLine2("earBuilder.addJavaModuleToEar(\""+groupId+"\", \""+artifactId+"\", \""+version+"\");");
				break;
			case VIEW:
				buf.putLine2("if (includeWar)");
				buf.putLine2("	earBuilder.addWarModuleToEar(\""+groupId+"\", \""+artifactId+"\", \""+version+"\");");
				break;
			default:
				buf.putLine2("earBuilder.addEjbModuleToEar(\""+groupId+"\", \""+artifactId+"\", \""+version+"\");");
				break;
			}
		}
		return buf.get();
	}
	
	protected ModelOperation createOperation_getApplicationXML(Application application) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getApplicationXml");
		modelOperation.setResultType("StringAsset");
		
		Buf buf = new Buf();
		buf.put(getSource_DisplayName(application));
		buf.put(getSource_ProjectModelArchive(application));
		buf.put(getSource_ApplicationModelArchive(application, ModuleType.MODEL));
		buf.put(getSource_ApplicationModelArchive(application, ModuleType.DATA));
		buf.put(getSource_ApplicationModelArchive(application, ModuleType.CLIENT));
		buf.put(getSource_ApplicationModelArchive(application, ModuleType.SERVICE));
		buf.put(getSource_ApplicationModelArchive(application, ModuleType.VIEW));
		
		//buf.putLine2("appXmlBuilder.addJavaModule(\"tx-manager-model-0.0.1-SNAPSHOT.jar\");");
		//buf.putLine2("appXmlBuilder.addJavaModule(\"tx-manager-base-0.0.1-SNAPSHOT.jar\");");
		buf.putLine2("appXmlBuilder.addEjbModule(\"tx-manager-client-0.0.1-SNAPSHOT.jar\");");
		//buf.putLine2("appXmlBuilder.addJavaModule(\"tx-manager-registry-0.0.1-SNAPSHOT.jar\");");
		buf.putLine2("appXmlBuilder.addJavaModule(\"admin-model-0.0.1-SNAPSHOT.jar\");");
		buf.putLine2("appXmlBuilder.addEjbModule(\"admin-client-0.0.1-SNAPSHOT.jar\");");
		buf.putLine2("appXmlBuilder.addEjbModule(\"admin-service-0.0.1-SNAPSHOT.jar\");");
		buf.putLine2("appXmlBuilder.addEjbModule(\"admin-data-0.0.1-SNAPSHOT.jar\");");
		//buf.putLine2("appXmlBuilder.addJavaModule(\"event-model-0.0.1-SNAPSHOT.jar\");");
		//buf.putLine2("appXmlBuilder.addJavaModule(\"event-client-0.0.1-SNAPSHOT.jar\");");
		buf.putLine2("appXmlBuilder.addEjbModule(\"common-client-0.0.1-SNAPSHOT.jar\");");
		buf.putLine2("appXmlBuilder.addEjbModule(\"common-data-0.0.1-SNAPSHOT.jar\");");
		buf.putLine2("appXmlBuilder.addEjbModule(\"jboss-seam-2.3.1.Final.jar\");");
		buf.putLine2("String xml = appXmlBuilder.createApplicationXml();");
		buf.putLine2("StringAsset asset = new StringAsset(xml);");
		buf.putLine2("return asset;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected String getSource_DisplayName(Application application) {
		String contextRoot = application.getContextRoot();
		String displayName = NameUtil.normalize(contextRoot, " ");
		String[] displayNameSplit = StringUtils.split(displayName, " ");
		String displayNameWithCaps = NameUtil.capName(displayNameSplit);
		
		Buf buf = new Buf();
		buf.putLine2("appXmlBuilder.setDisplayName(\""+displayNameWithCaps+"\");");
		return buf.get();
	}

	protected String getSource_ProjectModelArchive(Application application) throws Exception {
		Buf buf = new Buf();
		Set<Module> modules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module module = moduleIterator.next();
			String groupId = module.getGroupId();
			String artifactId = module.getArtifactId();
			String version = module.getVersion();
			buf.putLine2("appXmlBuilder.addJavaModule(\""+artifactId+"-"+version+".jar\");");
		}
		return buf.get();
	}
	
	protected String getSource_ApplicationModelArchive(Application application, ModuleType moduleType) throws Exception {
		Buf buf = new Buf();
		Collection<Module> modules = ApplicationUtil.getModules(application, moduleType);
		Iterator<Module> iterator = modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			String groupId = module.getGroupId();
			String artifactId = module.getArtifactId();
			String version = module.getVersion();
			switch (moduleType) {
			case VIEW:
				buf.putLine2("if (includeWar)");
				String contextRoot = application.getContextRoot();
				buf.putLine2("	appXmlBuilder.addWebModule(\""+artifactId+"-"+version+".war\", \""+contextRoot+"\");");
				break;
			case MODEL:
				buf.putLine2("appXmlBuilder.addJavaModule(\""+artifactId+"-"+version+".jar\");");
				break;
			default:
				buf.putLine2("appXmlBuilder.addEjbModule(\""+artifactId+"-"+version+".jar\");");
				break;
			}
		}
		return buf.get();
	}
	
}
