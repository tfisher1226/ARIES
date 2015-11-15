package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Application;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Element;
import nam.model.Operation;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.JMSUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;


/**
 * Builds a JMS Service Client (i.e. client-side) Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ClientClassJMSBuilder extends AbstractManagementBeanBuilder {

	private ClientClassJMSProvider provider;

	
	public ClientClassJMSBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		provider = new ClientClassJMSProvider(context);
		initialize(provider);
	}
	
	public ModelClass build(Service service) throws Exception {
		String namespace = ClientLayerHelper.getNamespace(service);
		String interfaceName = ClientLayerHelper.getClientInterfaceName(service);
		String packageName = ClientLayerHelper.getClientPackageName(service);
		String className = ClientLayerHelper.getClientInterfaceName(service) + "ProxyForJMS";
		String rootName = ClientLayerHelper.getRootName(service);
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
		modelClass.addImplementedInterface("Proxy<"+interfaceName+">");
		modelClass.addImportedClass("org.aries.bean.Proxy");
		modelClass.setParentClassName("JMSProxy");
		modelClass.addImportedClass("org.aries.tx.service.jms.JMSProxy");
		modelUnit = modelClass;

		initializeClass(modelClass, service);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Service service) throws Exception {
		initializeInstanceFields(modelClass, service);
		initializeInstanceConstructor(modelClass, service);
		initializeInstanceOperations(modelClass, service);
		initializeImportedClasses(modelClass, service);
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		//String className = ClientLayerHelper.getClientInterfaceName(service) + "ProxyForJMS";
		//CodeUtil.addStaticLoggerField(modelClass, className);
		modelClass.addInstanceAttribute(createStaticDestinationField(service));
		modelClass.addInstanceAttribute(createServiceAttribute_ServiceInterceptor(service));
	}

	protected ModelAttribute createStaticDestinationField(Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.FINAL + Modifier.STATIC);
		attribute.setPackageName("java.lang");
		attribute.setClassName("String");
		attribute.setName("DESTINATION");

		//TODO externalize this... 
		//TODO stop assuming the first channel only
		List<Channel> channels = ServiceUtil.getChannels(service);
		Channel channel = channels.get(0);
		String destinationName = JMSUtil.getDestinationName(service, channel);
		attribute.setDefault("\"/queue/"+destinationName+"_queue\"");

		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	public ModelAttribute createServiceAttribute_ServiceInterceptor(Service service) {
		String className = ClientLayerHelper.getClientInterfaceName(service) + "Interceptor";
		String beanName = NameUtil.uncapName(className);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName(beanName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	protected void initializeInstanceConstructor(ModelClass modelClass, Service service) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.addParameter(CodeUtil.createParameter_ServiceId());
		
		Buf buf = new Buf();
		buf.putLine2("super(serviceId);");
		buf.putLine2("createDelegate();");
		modelConstructor.addInitialSource(buf.get());
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		//if (provider.messageLayerRequested)
		createModelOperation_CreateDelegate(modelClass, service);
		createModelOperation_GetDelegate(modelClass, service);
		if (service instanceof Callback)
			modelClass.addInstanceOperation(createModelOperation_Send(service));
		createServiceMethods(modelClass, service);
	}

	protected void createModelOperation_CreateDelegate(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createDelegate");
		modelOperation.setModifiers(Modifier.PROTECTED);
		
		Buf buf = new Buf();
		String interceptorPrefix = NameUtil.uncapName(service.getInterfaceName());
		buf.putLine2(interceptorPrefix+"Interceptor = new "+service.getInterfaceName()+"Interceptor();");
		buf.putLine2(interceptorPrefix+"Interceptor.setProxy(this);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createModelOperation_GetDelegate(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getDelegate");
		modelOperation.setResultType(service.getInterfaceName());
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		String serviceName = NameUtil.uncapName(service.getName());
		buf.putLine2("return "+serviceName+"Interceptor;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected ModelOperation createModelOperation_Send(Service service) {
		Application application = context.getApplication();
		String applicationName = application.getName();
		String qualifiedName = ServiceLayerHelper.getServiceQualifiedName(service);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String serviceName = NameUtil.uncapName(interfaceName);
		String messageClassName = interfaceName + "Message";
		String messageBeanName = NameUtil.uncapName(messageClassName);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		//modelOperation.addAnnotation(AnnotationUtil.createSuppressWarning("unchecked"));
		modelOperation.setName("send");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("Serializable", "message"));
		//modelOperation.addParameter(createParameter("String", "correlationId"));
		//modelOperation.addParameter(createParameter("long", "timeout"));
		modelOperation.addException("NamingException");
		modelOperation.addException("JMSException");
		
		modelUnit.addImportedClass("java.io.Serializable");
		modelUnit.addImportedClass("javax.jms.JMSException");
		modelUnit.addImportedClass("javax.naming.NamingException");
		modelUnit.addImportedClass("org.aries.util.ExceptionUtil");
		modelUnit.addImportedClass("org.aries.jms.util.MessageUtil");
		//modelUnit.addImportedClass(qualifiedName);
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		//buf.putLine2("	"+messageClassName+" "+messageBeanName+" = ("+messageClassName+") message;");
		buf.putLine2("	"+messageClassName+" "+messageBeanName+" = MessageUtil.getPart(message, \""+messageBeanName+"\");");
		buf.putLine2("	String replyToDestination = getReplyToDestination("+messageBeanName+", \""+interfaceName+"\");");
		buf.putLine2("	send(replyToDestination, "+messageBeanName+");");
		buf.putLine2("	log.info(\"#### ["+applicationName+"]: "+interfaceName+" response sent\");");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	

	protected void createServiceMethods(ModelClass modelClass, Service service) throws Exception {
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			createServiceMethod(modelClass, service, operation);
		}
	}
	
	protected void addImportedClasses(ModelClass modelClass, Operation operation) {
		super.addImportedClasses(modelClass, operation);
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
	}
	
	protected void createServiceMethod(ModelClass modelClass, Service service, Operation operation) throws Exception {
		ModelOperation modelOperation = createModelOperation(service, operation);
		//ModelOperation modelOperation = CodeUtil.createOperation(operation);
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		addImportedClasses(modelClass, operation);
		
		Element element = null;
		if (service.getElement() != null)
			element = context.getElementByType(service.getElement());
		
		provider.setService(service);
		String serviceName = provider.getServiceName(operation.getName());
		provider.generateSourceCode_ServiceMethod(modelOperation, element, serviceName);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createGetServiceURIMethod(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getServiceURI");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("java.lang.String");
		modelOperation.getParameters().add(CodeUtil.createParameter_HostName());
		modelOperation.getParameters().add(CodeUtil.createParameter_PortNumber());
		modelOperation.getExceptions().add("MalformedURLException");
		modelClass.addImportedClass("java.net.MalformedURLException");
		
		Buf buf = new Buf();
		String context = service.getNamespace().replace("http://", "");
		String portType = NameUtil.uncapName(service.getName());
		buf.putLine2("return \"http://\"+host+\":\"+port+\"/"+context +"/"+portType+"?wsdl\";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
		//addImportedClassesForFaults(modelClass, ServiceUtil.getFaults(service));
		if (service instanceof Callback)
			modelClass.addImportedClasses(CodeUtil.getImportedClasses(service));
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
	}

//	protected void initializeImportedClasses(ModelClass modelClass, List<Operation> operations) throws Exception {
//		Iterator<Operation> iterator = operations.iterator();
//		while (iterator.hasNext()) {
//			Operation operation = iterator.next();
//			initializeImportedClasses(modelClass, operation);
//		}
//	}
//
//	protected void initializeImportedClasses(ModelClass modelClass, Operation operation) throws Exception {
//		List<Parameter> parameters = operation.getParameters();
//		addImportedClasses(modelClass, parameters);
//		if (operation.getResult() != null) {
//			Result result = operation.getResult();
//			addImportedClasses(modelClass, result);
//		}
//	}
//
//	protected void addImportedClasses(ModelClass modelClass, List<Parameter> parameters) {
//		Iterator<Parameter> parameterIterator = parameters.iterator();
//		while (parameterIterator.hasNext()) {
//			Parameter parameter = parameterIterator.next();
//			Element element = context.findElement(parameter);
//			if (element != null)
//				addImportedClass(modelClass, element.getType());
//			else addImportedClass(modelClass, parameter.getType());
//		}
//	}
//	
//	protected void addImportedClasses(ModelClass modelClass, Result result) {
//		Element element = context.findElement(result);
//		if (element != null)
//			addImportedClass(modelClass, element.getType());
//		else addImportedClass(modelClass, result.getType());
//		if (result.getConstruct().equals("list")) {
//			modelClass.addImportedClass("java.util.List");
//		} else if (result.getConstruct().equals("set")) {
//			modelClass.addImportedClass("java.util.Set");
//		} else if (result.getConstruct().equals("map")) {
//			modelClass.addImportedClass("java.util.Map");
//		}
//	}
	
}

//Buf methodCall = new Buf();
//methodCall.put("sendMessage(");
//List<Parameter> parameters = operation.getParameters();
//Iterator<Parameter> iterator = parameters.iterator();
//for (int i=0; iterator.hasNext(); i++) {
//	Parameter parameter = iterator.next();
//	if (i > 0)
//		methodCall.put(", ");
//	methodCall.put(parameter.getName());
//}
//methodCall.put(")");
//
//Buf buf = new Buf();
//iterator = parameters.iterator();
//for (int i=0; iterator.hasNext(); i++) {
//	Parameter parameter = iterator.next();
//	buf.putLine2("Check.isValid(\""+parameter.getName()+"\", "+parameter.getName()+");");
//}
//
//buf.putLine2("try {");
//buf.putLine2("	"+methodCall.get()+";");
//buf.putLine2("} catch (Exception e) {");
//buf.putLine2("	log.error(\"Error\", e);");
//buf.putLine2("	throw ExceptionUtil.rewrap(e);");
//buf.putLine2("}");
//
//modelOperation.addInitialSource(buf.get());
//modelClass.addInstanceOperation(modelOperation);

