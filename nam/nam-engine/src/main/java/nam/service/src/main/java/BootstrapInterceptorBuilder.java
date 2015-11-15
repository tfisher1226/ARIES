package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.ProjectLevelHelper;
import nam.model.Application;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


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
public class BootstrapInterceptorBuilder extends AbstractManagementBeanBuilder {

	public BootstrapInterceptorBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public ModelClass buildClass(Module module) throws Exception {
		Application application = context.getApplication();
		String namespace = module.getNamespace();
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		String className = NameUtil.capName(application.getName()) + "ServiceInitializer";
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, NameUtil.uncapName(className)));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.setNamespace(namespace);
		modelClass.setParentClassName("Bootstrapper");
		modelClass.addImportedClass("org.aries.tx.module.Bootstrapper");
		modelClass.addImplementedInterface("Initializer");
		modelClass.addImportedClass("org.aries.runtime.Initializer");
		initializeClass(modelClass, module);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Module module) throws Exception {
		initializeClassAnnotations(modelClass, module);
		initializeInstanceOperations(modelClass, module);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Module module) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createStartupAnnotation());
		classAnnotations.add(AnnotationUtil.createSingletonAnnotation());
		classAnnotations.add(AnnotationUtil.createLocalBeanAnnotation());
		modelClass.addImportedClass("javax.ejb.Startup");
		modelClass.addImportedClass("javax.ejb.Singleton");
		modelClass.addImportedClass("javax.ejb.LocalBean");
		
//		String contextName = "service.initializer";
//		classAnnotations.add(AnnotationUtil.createStartupAnnotation());
//		//classAnnotations.add(AnnotationUtil.createSuppressSerialWarning());
//		//classAnnotations.add(AnnotationUtil.createBypassInterceptorsAnnotation());
//		classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.APPLICATION));
//		if (context.isEnabled("useQualifiedContextNames")) {
//			String contextPrefix = ProjectLevelHelper.getPackageName(module.getNamespace());
//			//String contextPrefix = NameUtil.getQualifiedContextNamePrefix(modelClass.getQualifiedName(), 2);
//			classAnnotations.add(AnnotationUtil.createNameAnnotation(contextPrefix + "." + contextName));
//		} else classAnnotations.add(AnnotationUtil.createNameAnnotation(contextName));
//		modelClass.addImportedClass("org.jboss.seam.annotations.Startup");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
//		modelClass.addImportedClass("org.jboss.seam.ScopeType");
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Module module) throws Exception {
		modelClass.addInstanceOperation(createOperation_getApplication(module));
		modelClass.addInstanceOperation(createOperation_getDomain(module));
		modelClass.addInstanceOperation(createOperation_getModule(module));
		modelClass.addInstanceOperation(createOperation_execute(module));
	}

	protected ModelOperation createOperation_getApplication(Module module) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getApplication");
		modelOperation.setResultType("String");
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		Buf buf = new Buf();
		buf.putLine2("return \""+context.getApplication().getName()+"\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getDomain(Module module) {
		String groupId = context.getApplication().getGroupId();
		String domain = groupId.replace("-", ".");
		domain = domain.replace("_", ".");

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getDomain");
		modelOperation.setResultType("String");
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		Buf buf = new Buf();
		//String packageNameFromNamespace = ProjectLevelHelper.getPackageName(module.getNamespace());
		//buf.putLine2("return \""+packageNameFromNamespace+"\";");
		buf.putLine2("return \""+domain+"\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getModule(Module module) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getModule");
		modelOperation.setResultType("String");
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		Buf buf = new Buf();
		String moduleName = context.getModule().getName();
		moduleName = moduleName.replace("-", ".");
		buf.putLine2("return \""+moduleName+"\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_execute(Module module) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("execute");
		modelOperation.addException("Exception");
		modelOperation.addAnnotation(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.addImportedClass("javax.annotation.PostConstruct");
		modelOperation.addImportedClass("org.aries.validate.util.CheckpointManager");
		
		Buf buf = new Buf();
		String filePrefix = module.getArtifactId().replace(".", "-");
		//buf.putLine2("CheckpointManager.setDomain(getDomain());");
		buf.putLine2("CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());");
		buf.putLine2("CheckpointManager.addConfiguration(\""+filePrefix+"-checks.xml\");");
		
		Map<String, Namespace> importedNamespaces = getImportedNamespaces(module);
		buf.put(createLoadSchemaSource(importedNamespaces));
		
		buf.putLine2("super.initializeAsServiceModule();");
		buf.putLine2("setInitialized(true);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected Map<String, Namespace> getImportedNamespaces(Module module) {
		//first try the module namespace
		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		//TODO do we need to enforce this to be notNull ?
		if (namespace == null)
			return new HashMap<String, Namespace>();
		Map<String, Namespace> importedNamespaces = NamespaceUtil.getImportedNamespaces(namespace);
		if (importedNamespaces.size() == 0) {
			//otherwise try the project namespace
			Project project = context.getProject();
			namespace = context.getNamespaceByUri(project.getNamespace());
			importedNamespaces = NamespaceUtil.getImportedNamespaces(namespace);
		}
		return importedNamespaces;
	}

	protected String createLoadSchemaSource(Map<String, Namespace> namespaceMap) {
		Buf buf = new Buf();
		Iterator<String> iterator = namespaceMap.keySet().iterator();
		while (iterator.hasNext()) {
			String uri = iterator.next();
			namespace = namespaceMap.get(uri);
			String sourceLine = createLoadSchemaSource(namespace);
			if (sourceLine != null)
				buf.put(sourceLine);
		}
		return buf.get();
	}

	protected String createLoadSchemaSource(Namespace namespace) {
		String filePath = namespace.getFilePath();
		if (filePath == null)
			return null;
		
		Buf buf = new Buf();
		String cacheLocation = context.getCacheLocation();
		filePath = filePath.replace(cacheLocation, "");
		filePath = filePath.replace(".aries", ".xsd");
		filePath = filePath.replace("\\", "/");
		
		String namespacePackageName = NamespaceUtil.getPackageName(namespace);
		buf.putLine2("loadSchema(\""+filePath+"\", "+namespacePackageName+".ObjectFactory.class);");
		return buf.get();
	}

	protected ModelOperation createOperation_execute_SEAM(Module module) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("execute");
		modelOperation.addException("Exception");
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.jboss.seam.postInitialization"));
		modelOperation.addImportedClass("org.jboss.seam.annotations.Observer");
		modelOperation.addImportedClass("org.aries.validate.util.CheckpointManager");
		
		Buf buf = new Buf();
		String filePrefix = module.getArtifactId().replace(".", "-");
		buf.putLine2("CheckpointManager.addConfiguration(\""+filePrefix+"-checks.xml\");");
		buf.putLine2("super.initializeAsServiceModule();");
		buf.putLine2("setInitialized(true);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

}
