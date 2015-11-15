package nam.ui.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nam.ProjectLevelHelper;
import nam.model.Application;
import nam.model.Information;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.NamespaceUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds a Module Bootstrap Interceptor Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServletListenerBuilder extends AbstractBeanBuilder {

	public ServletListenerBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public ModelClass buildClass(Module module) throws Exception {
		String namespace = module.getNamespace();
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		String className = "ServletListener";
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, NameUtil.uncapName(className)));
		modelClass.setPackageName(packageName + ".ui");
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.setNamespace(namespace);
		modelClass.addImplementedInterface("ServletContextListener");
		modelClass.addImplementedInterface("HttpSessionListener");
		initializeClass(modelClass, module);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Module module) throws Exception {
		initializeImportedClasses(modelClass);
		initializeStaticFields(modelClass);
		initializeInstanceOperations(modelClass, module);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass) {
		modelClass.addImportedClass("javax.servlet.ServletContext");
		modelClass.addImportedClass("javax.servlet.ServletContextEvent");
		modelClass.addImportedClass("javax.servlet.ServletContextListener");
		modelClass.addImportedClass("javax.servlet.http.HttpSessionEvent");
		modelClass.addImportedClass("javax.servlet.http.HttpSessionListener");
		
		modelClass.addImportedClass("org.apache.commons.logging.Log");
		modelClass.addImportedClass("org.apache.commons.logging.LogFactory");
		
		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.RuntimeContext");
		modelClass.addImportedClass("org.aries.jaxb.JAXBSessionCache");
		modelClass.addImportedClass("org.aries.launcher.Bootstrap");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		modelClass.addImportedClass("org.aries.util.FileUtil");
	}

	protected void initializeStaticFields(ModelClass modelClass) throws Exception {
		modelClass.addStaticAttribute(createStaticLoggerAttribute(modelClass.getClassName()));
//		modelClass.addStaticAttribute(createStaticPropertyPrefixLoggerAttribute());
	}
	
	public static ModelAttribute createStaticLoggerAttribute(String className) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.FINAL + Modifier.STATIC);
		attribute.setPackageName("org.apache.commons.logging");
		attribute.setClassName("Log");
		attribute.setName("log");
		attribute.setDefault("LogFactory.getLog("+className+".class)");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
//	protected ModelAttribute createStaticPropertyPrefixLoggerAttribute() {
//		Application application = context.getApplication();
//		ModelAttribute attribute = new ModelAttribute();
//		attribute.setModifiers(Modifier.PRIVATE + Modifier.FINAL + Modifier.STATIC);
//		attribute.setPackageName("java.lang");
//		attribute.setClassName("String");
//		attribute.setName("PROPERTY_PREFIX");
//		attribute.setValue("\""+application.getArtifactId()+"\"");
//		attribute.setGenerateGetter(false);
//		attribute.setGenerateSetter(false);
//		return attribute;
//	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Module module) throws Exception {
		modelClass.addInstanceOperation(createOperation_getDomain());
		//modelClass.addInstanceOperation(createOperation_getApplication());
		modelClass.addInstanceOperation(createOperation_getModule());
		modelClass.addInstanceOperation(createOperation_contextInitialized(module));
		modelClass.addInstanceOperation(createOperation_contextDestroyed(module));
		modelClass.addInstanceOperation(createOperation_sessionCreated(module));
		modelClass.addInstanceOperation(createOperation_sessionDestroyed(module));
	}

	protected ModelOperation createOperation_getDomain() throws Exception {
		Project project = context.getProject();
		String domainName = project.getDomain();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getDomain");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return \""+domainName+"\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getApplication() throws Exception {
		Application application = context.getApplication();
		String applicationName = application.getName();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getApplication");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return \""+applicationName+"\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getModule() throws Exception {
		Module module = context.getModule();
		String moduleName = module.getName().replace("-", ".");
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getModule");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return \""+moduleName+"\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_contextInitialized(Module module) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		Buf javadoc = new Buf();
		javadoc.putLine2("Notification that the web application is ready to process requests.");
		javadoc.putLine2("Loads properties from potential/expected property sources.");
		javadoc.putLine2("@param servletContextEvent");
		modelOperation.addJavadoc(javadoc.get());
		modelOperation.setName("contextInitialized");
		ModelParameter modelParameter = createParameter("ServletContextEvent", "event");
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		buf.putLine2("ServletContext servletContext = event.getServletContext();");
		buf.putLine2("RuntimeContext.getInstance().setServletContext(servletContext);");
		buf.putLine2("String propertyName = getDomain() + \".application_runtime_home\";");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	String runtimeHome = System.getProperty(propertyName);");
		buf.putLine2("	Assert.notNull(runtimeHome, \"External property not found: \"+propertyName);");
		buf.putLine2("	Bootstrap bootstrapper = new Bootstrap();");
		buf.putLine2("	bootstrapper.setDomainName(getDomain());");
		buf.putLine2("	bootstrapper.setModuleName(getModule());");
		buf.putLine2("	BeanContext.set(getDomain() + \".bootstrapper\", bootstrapper);");
		buf.putLine2("	bootstrapper.initialize(runtimeHome);");
		buf.putLine2("	runtimeHome = FileUtil.normalizePath(runtimeHome);");
		buf.putLine2("	");
		
		buf.putLine2("	JAXBSessionCache jaxbSessionCache = BeanContext.get(getDomain() + \".jaxbSessionCache\");");
		buf.putLine2("	");
		
		Information information = module.getInformation();
		if (information != null) {
			addSchemaSourceIndex = 0;
			namespaceMap = new TreeMap<String, Namespace>();
			List<Namespace> namespaces = getNamespaces(information);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = iterator.next();
				String addSchemaSource = getAddSchemaSource(namespace);
				if (addSchemaSource != null)
					buf.put(addSchemaSource);
			}
		}

		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Throwable rootCause = ExceptionUtil.getRootCause(e);");
		buf.putLine2("	log.error(\"Error:\", e);");
		buf.putLine2("	log.error(\"Caused by:\", rootCause);");
		buf.putLine2("	throw new RuntimeException(rootCause);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<Namespace> getNamespaces(Information information) {
		List<Namespace> namespaces = information.getNamespaces();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			checkAddNamespacesToMap(namespace);
		}
		
		List<Namespace> values = new ArrayList<Namespace>();
		values.addAll(namespaceMap.values());
		return values;
	}

	protected void checkAddNamespacesToMap(Namespace namespace) {
		if (!namespaceMap.containsKey(namespace.getUri()))
			namespaceMap.put(namespace.getUri(), namespace);
		List<Namespace> imports = namespace.getImports();
		Iterator<Namespace> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Namespace importedNamespace = iterator.next();
			if (!NamespaceUtil.equals(importedNamespace, namespace))
				checkAddNamespacesToMap(importedNamespace);
		}
	}

	private int addSchemaSourceIndex;
	
	private Map<String, Namespace> namespaceMap;
	
	protected String getAddSchemaSource(Namespace namespace) {
		String filePath = namespace.getFilePath();
		if (filePath == null)
			return null;
		
		filePath = filePath.replace(".aries", ".xsd");
		filePath = filePath.replace(context.getCacheLocation(), "");
		String fileSeparator = System.getProperty("file.separator");
		filePath = filePath.replace(fileSeparator, "/");
		String packageName = ProjectLevelHelper.getPackageName(namespace.getUri());
		
		Buf buf = new Buf();
		addSchemaSourceIndex++;
		buf.putLine3("// Add schema for: "+namespace.getUri());
		buf.putLine3("String schemaFileName" + addSchemaSourceIndex + " = \"file://\" + runtimeHome + \"" + filePath + "\";");
		buf.putLine3("jaxbSessionCache.addSchema(schemaFileName" + addSchemaSourceIndex + ", "+packageName+".ObjectFactory.class);");
		buf.putLine3("");
		return buf.get();
	}

	protected ModelOperation createOperation_contextDestroyed(Module module) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		Buf javadoc = new Buf();
		javadoc.putLine2("Notification that the servlet context is about to be shut down.");
		javadoc.putLine2("Shuts down any cache managers known to {@link CacheManager#ALL_CACHE_MANAGERS}.");
		javadoc.putLine2("@param servletContextEvent");
		modelOperation.addJavadoc(javadoc.get());
		modelOperation.setName("contextDestroyed");
		ModelParameter modelParameter = createParameter("ServletContextEvent", "event");
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		//buf.putLine2("super.contextDestroyed(event);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_sessionCreated(Module module) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("sessionCreated");
		ModelParameter modelParameter = createParameter("HttpSessionEvent", "event");
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		//buf.putLine2("ServletLifecycle.beginSession(event.getSession());");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_sessionDestroyed(Module module) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("sessionDestroyed");
		ModelParameter modelParameter = createParameter("HttpSessionEvent", "event");
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		//buf.putLine2("JBossClusterMonitor monitor = JBossClusterMonitor.getInstance(event.getSession().getServletContext());");
		//buf.putLine2("	if (monitor != null && monitor.failover()) {");
		//buf.putLine2("	// If application is unfarmed or all nodes shutdown simultaneously, cluster cache may still fail to retrieve SFSBs to destroy");
		//buf.putLine2("	log.debug(\"Detected fail-over, not destroying session context\");");
		//buf.putLine2("} else {");
		//buf.putLine2("	ServletLifecycle.endSession(event.getSession());");
		//buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
