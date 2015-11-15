package nam.service.src.main.java;

import java.lang.reflect.Modifier;

import nam.model.Module;
import nam.model.Service;
import nam.model.util.ModuleUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds a Service Launcher {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceLauncherBuilder extends AbstractBeanBuilder {

	public ServiceLauncherBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelClass build(Service service) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "Launcher";
		String rootName = ServiceUtil.getRootName(service);
		String beanName = NameUtil.capName(className);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setNamespace(namespace);
		modelClass.setParentClassName("AbstractServiceInitializer");
		modelClass.addImportedClass("org.aries.tx.service.AbstractServiceInitializer");
		initializeClass(modelClass, service);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Service service) throws Exception {
		initializeImportedClasses(modelClass, service);
		initializeInstanceFields(modelClass, service);
		initializeInstanceMethods(modelClass, service);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceAttribute(createINSTANCEAttribute(service));
		CodeUtil.addStaticLoggerField(modelClass, className);
		modelClass.addInstanceAttribute(createLauncherAttribute(service));
		modelClass.addImportedClass("org.aries.launcher.ServiceLauncher");
	}

	public ModelAttribute createINSTANCEAttribute(Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("INSTANCE");
		attribute.setDefault("new "+className+"()");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	public static ModelAttribute createLauncherAttribute(Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("org.aries.launcher");
		attribute.setClassName("ServiceLauncher");
		attribute.setName("launcher");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceOperation(createStartupMethod(modelClass, service));
		modelClass.addInstanceOperation(createShutdownMethod(modelClass, service));
	}
	
	protected ModelOperation createStartupMethod(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.getParameters().add(CodeUtil.createParameter_HostName());
		modelOperation.getParameters().add(CodeUtil.createParameter_PortNumber());
		modelOperation.addInitialSource(createStartupMethodSource(service));
		modelClass.addImportedClass("org.aries.tx.util.EndpointDescriptor");
		return modelOperation;
	}

	protected String createStartupMethodSource(Service service) {
		Module module = ModuleUtil.getServiceModule(context.getProjectList(), service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForJAXWS";
		
		Buf buf = new Buf();
		buf.putLine2("EndpointDescriptor descriptor = new EndpointDescriptor();");
		buf.putLine2("descriptor.setNamespace(\""+service.getNamespace()+"\");");
		//buf.putLine2("descriptor.setContext(\""+service.getNamespace().replace("http:/", "")+"\");");
		buf.putLine2("descriptor.setContext(\"/"+NameUtil.getPackagePathFromNamespace(service.getNamespace())+"\");");
		buf.putLine2("descriptor.setServiceName(\""+service.getName()+"Service\");");
		buf.putLine2("descriptor.setPortTypeName(\""+service.getName()+"\");");
		buf.putLine2("descriptor.setDescription(\"Launcher component for the "+service.getName()+" service\");");
		buf.putLine2("descriptor.setPackageName("+NameUtil.getClassName(service.getInterfaceName())+".class.getPackage().getName());");
		buf.putLine2("descriptor.setInterfaceClass("+NameUtil.getClassName(service.getInterfaceName())+".class.getName());");
		buf.putLine2("descriptor.setImplementationClass("+className+".class.getName());");
		buf.putLine2("descriptor.setBindAddress(host);");
		buf.putLine2("descriptor.setBindPort(port);");
		buf.putLine2("launcher = launch(descriptor);");
		return buf.get();
	}

	protected ModelOperation createShutdownMethod(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("shutdown");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.addInitialSource(createShutdownMethodSource(service));
		return modelOperation;
	}

	protected String createShutdownMethodSource(Service service) {
		Buf buf = new Buf();
		buf.putLine2("if (launcher != null)");
		buf.putLine2("	launcher.shutdown();");
		buf.putLine2("launcher = null;");
		return buf.get();
	}

}
