package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Callback;
import nam.model.Channel;
import nam.model.Element;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Service;
import nam.model.TransportType;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Abstract class to build a Service Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public abstract class AbstractServiceListenerBuilder extends AbstractManagementBeanBuilder {

	protected abstract void initializeClass(ModelClass modelClass, Service service, Channel channel, Element element) throws Exception;

	protected abstract void initializeClass(ModelClass modelClass, Service service, Channel channel, Element element, Operation operation) throws Exception;

	protected abstract TransportType getTransportType();
	
	protected abstract String getClassName(Service service);
	
	protected Namespace namespace;

	
	public AbstractServiceListenerBuilder(GenerationContext context) {
		super(context);
	}
	
//	public List<ModelClass> buildClasses(Services services) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		List<Service> list = ServicesUtil.getServices(services);
//		Iterator<Service> iterator = list.iterator();
//		while (iterator.hasNext()) {
//			Service service = iterator.next();
//			if (context.isEnabled("generateServicePerOperation")) {
//				modelClasses.addAll(buildClassesByOperation(service));
//			} else {
//				modelClasses.addAll(buildClasses(service));
//			}
//		}
//		return modelClasses;
//	}

//	public List<ModelClass> buildClasses(Service service) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		modelClasses.addAll(buildClassesByOperation(service, TransportType.RMI));
//		//modelClasses.addAll(buildClassesByOperation(service, TransportType.EJB));
//		modelClasses.addAll(buildClassesByOperation(service, TransportType.HTTP));
//		modelClasses.addAll(buildClassesByOperation(service, TransportType.JMS));
//		return modelClasses;
//	}


	public List<ModelClass> buildClasses(Service service, Channel channel) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		if (context.isEnabled("generateServicePerOperation"))
			modelClasses.addAll(buildClasses(service, channel, getTransportType()));
		else modelClasses.add(buildClass(service, channel, getTransportType()));
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Service service, Channel channel, TransportType transport) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			modelClasses.add(buildClass(service, channel, operation, transport));
		}
		return modelClasses;
	}

	public ModelClass buildClass(Service service, Channel channel, TransportType transport) throws Exception {
		Element element = context.getElement(service);
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = null;
		if (service instanceof Callback)
			packageName = ServiceLayerHelper.getServicePackageName(context.getService());
		else packageName = ServiceLayerHelper.getServicePackageName(service);
		String className = getClassName(service);
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
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		//modelClass.addImportedClass("org.aries.action.AbstractAction");
		return modelClass; 
	}

	public ModelClass buildClass(Service service, Channel channel, Operation operation, TransportType transport) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		//String packageName = ServiceLayerHelper.getServicePackageName(service, channel);
		String className = getClassName(service);
		String rootName = ServiceUtil.getRootName(service);
		String beanName = NameUtil.capName(operation.getName());

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
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		//modelClass.addImportedClass("org.aries.action.AbstractAction");
		return modelClass; 
	}

	
	/**
	 * getModuleId() method generation with delegation as EJB invocation
	 */
	protected ModelOperation createInstanceOperation_GetModuleId(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getModuleId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());

		Buf buf = new Buf();
		buf.putLine2("return \""+getModuleId()+"\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected String getModuleId() {
		Module module = context.getModule();
		String moduleId = module.getName();
		//moduleId = moduleId.replace("-",  ".");
		//moduleId = moduleId.replace("_",  ".");
		return moduleId;
	}
	
	/**
	 * getServiceId() method generation with delegation as EJB invocation
	 */
	protected ModelOperation createInstanceOperation_GetServiceId(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getServiceId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());

		Buf buf = new Buf();
		buf.putLine2("return "+service.getInterfaceName()+".ID;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	/**
	 * getDelegate() method generation with delegation as EJB invocation
	 */
	protected ModelOperation createInstanceOperation_GetDelegate(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getDelegate");
		modelOperation.setResultType("Object");
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		if (isMessageInterceptorEnabled(service)) {
			String serviceName = NameUtil.uncapName(service.getName());
			buf.putLine2("return "+serviceName+"Interceptor;");
			//buf.putLine2("return BeanContext.getFromJNDI("+serviceName+"Interceptor.class, \""+serviceName+"Interceptor\");");
	
		} else {
			String serviceName = NameUtil.uncapName(service.getName());
			buf.putLine2("return "+serviceName+"Handler;");
		}

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected boolean isMessageInterceptorEnabled(Service service) {
		return ServiceUtil.isSynchronous(service);
	}
	
	/**
	 * invoke() method generation with delegation as EJB invocation
	 */
	protected ModelOperation createInstanceOperation_Invoke(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createSuppressWarning("unchecked"));
		modelOperation.setName("invoke");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("Serializable", "request"));
		modelOperation.addParameter(createParameter("String", "correlationId"));
		modelOperation.addParameter(createParameter("long", "timeout"));
		modelOperation.setResultType("Serializable");
		
		Buf buf = new Buf();
		buf.putLine2("if (!isInitialized())");
		buf.putLine2("	return null;");
		buf.putLine2("if (request instanceof String)");
		buf.putLine2("	return processAsText((String) request);");
		buf.putLine2("if (request instanceof Message)");
		buf.putLine2("	return processAsBinary((Message) request);");
		buf.putLine2("throw new RuntimeException(\"Unexpected payload type: \"+request.getClass());");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}

