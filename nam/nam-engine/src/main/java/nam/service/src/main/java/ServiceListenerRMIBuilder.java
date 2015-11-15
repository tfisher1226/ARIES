package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Channel;
import nam.model.Element;
import nam.model.Fault;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Result;
import nam.model.Service;
import nam.model.TransportType;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerFactory;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.bpel.BPELRuntimeCache;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds a Service Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceListenerRMIBuilder extends AbstractServiceListenerBuilder {

	private ServiceListenerRMIProvider serviceClassProvider;
	
	
	public ServiceListenerRMIBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected TransportType getTransportType() {
		return TransportType.RMI;
	}
	
	protected String getClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForRMI";
	}
	
	protected void initialize() {
		serviceClassProvider = new ServiceListenerRMIProvider(context);
	}

	protected boolean isMessageInterceptorEnabled(Service service) {
		return true;
	}
	
	public ModelClass buildClass(Service service, Channel channel, TransportType transport) throws Exception {
		ModelClass modelClass = super.buildClass(service, channel, transport);
		modelClass.setParentClassName("AbstractRMIListener");
		modelClass.addImportedClass("org.aries.tx.service.rmi.AbstractRMIListener");
		//modelClass.addImportedClass("org.aries.tx.service.rmi.RMIListener");
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		modelClass.addImplementedInterface("Remote");
		Element element = context.getElement(service);
		initializeClass(modelClass, service, channel, element);
		return modelClass; 
	}
	
	public ModelClass buildClass(Service service, Channel channel, Operation operation, TransportType transport) throws Exception {
		ModelClass modelClass = super.buildClass(service, channel, operation, transport);
		modelClass.setParentClassName("AbstractRMIListener");
		modelClass.addImportedClass("org.aries.tx.rmi.AbstractRMIListener");
		//modelClass.addImportedClass("org.aries.tx.rmi.RMIListener");
		//modelClass.addImplementedInterface("MessageListener");
		//modelClass.addImportedClass("org.aries.MessageListener");
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		modelClass.addImplementedInterface("Remote");
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		//modelClass.addImportedClass("org.aries.action.AbstractAction");
		Element element = context.getElement(service);
		initializeClass(modelClass, service, channel, element, operation);
		addImportedClasses(modelClass, operation);
		return modelClass; 
	}
	
	protected void initializeClass(ModelClass modelClass, Service service, Channel channel, Element element) throws Exception {
		this.modelUnit = modelClass;
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, element);
		initializeImportedClasses(modelClass, service, element);
		initializeInstanceFields(modelClass, service, element);
		initializeInstanceConstructor(modelClass, service);
		initializeInstanceOperations(modelClass, service, element);
	}

	protected void initializeClass(ModelClass modelClass, Service service, Channel channel, Element element, Operation operation) throws Exception {
		this.modelUnit = modelClass;
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, element);
		initializeImportedClasses(modelClass, service, element);
		initializeInstanceFields(modelClass, service, element);
		initializeInstanceOperations(modelClass, service, element, operation);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Service service, Element element) throws Exception {
		String contextName = NameUtil.uncapName(modelClass.getName());
		if (context.isEnabled("generateServicePerOperation"))
			contextName = NameUtil.uncapName(service.getName()) + "." + contextName;

		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		modelClass.addImportedClass("java.io.Serializable");
		modelClass.addImportedClass("java.rmi.Remote");
		modelClass.addImportedClass("java.rmi.RemoteException");
		
		switch (context.getServiceLayerBeanType()) {
		case EJB:
//			classAnnotations.add(AnnotationUtil.createStartupAnnotation());
//			classAnnotations.add(AnnotationUtil.createSingletonAnnotation());
//			classAnnotations.add(AnnotationUtil.createLocalAnnotation("RMIListener"));
//			classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
//			modelClass.addImportedClass("javax.ejb.Startup");
//			modelClass.addImportedClass("javax.ejb.Singleton");
//			modelClass.addImportedClass("javax.ejb.Local");
//			modelClass.addImportedClass("javax.ejb.ConcurrencyManagement");
//			modelClass.addImportedClass("javax.annotation.PostConstruct");
			break;

		case SEAM:
			//classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
			//classAnnotations.add(AnnotationUtil.createSuppressSerialWarning());
			//classAnnotations.add(AnnotationUtil.createBypassInterceptorsAnnotation());
			//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
			//if (context.isEnabled("useQualifiedContextNames")) {
			//	String contextPrefix = NameUtil.getQualifiedContextNamePrefix(modelClass.getQualifiedName(), 2);
			//	classAnnotations.add(AnnotationUtil.createNameAnnotation(contextPrefix + "." + contextName));
			//} else classAnnotations.add(AnnotationUtil.createNameAnnotation(contextName));
			//modelClass.addImportedClass("org.jboss.seam.ScopeType");
			//modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
			//modelClass.addImportedClass("org.jboss.seam.annotations.Name");
			//modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
			//modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
			break;
		}
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Service service, Element element) throws Exception {
		//initializeImportedClasses(modelClass, ServiceUtil.getOperations(service));
		//String contextPrefix = NameUtil.getQualifiedContextNamePrefix(modelClass.getQualifiedName(), 2);
		//modelClass.addImportedClass(contextPrefix+".exception.ExceptionFactory");
		//modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
		modelClass.addImportedClass("org.aries.message.Message");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		Fault fault = getFault(service);
		if (fault != null)
			modelClass.addImportedClass(ServiceLayerHelper.getFaultQualifiedName(fault));
	}

	protected void initializeImportedClasses(ModelClass modelClass, List<Operation> operations) throws Exception {
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			initializeImportedClasses(modelClass, operation);
		}
	}

	protected void initializeImportedClasses(ModelClass modelClass, Operation operation) throws Exception {
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			Parameter parameter = parameterIterator.next();
			Element element = context.findElement(parameter);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, parameter.getType());
		}
		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty()) {
			result = results.get(0);
			Element element = context.findElement(result);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, result.getType());
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service, Element element) throws Exception {
		Process process = service.getProcess();
		if (ServiceUtil.isStateful(service))
			modelClass.addInstanceReference(ServiceLayerFactory.createReference_StatefulContext(modelClass, process));
		modelClass.addInstanceReference(ServiceLayerFactory.createReference_ServiceInterceptor(modelClass, service));
	}
	
	protected void initializeInstanceConstructor(ModelClass modelClass, Service service) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.addException("RemoteException");
		
		Buf buf = new Buf();
		buf.putLine2("registerAsRMIService();");
		modelConstructor.addInitialSource(buf.get());
		modelClass.addInstanceConstructor(modelConstructor);
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Element element) throws Exception {
		modelClass.addInstanceOperation(createInstanceOperation_Initialize(service));
		modelClass.addInstanceOperation(createInstanceOperation_GetModuleId(service));
		modelClass.addInstanceOperation(createInstanceOperation_GetServiceId(service));
		if (ServiceUtil.isStateful(service))
			modelClass.addInstanceOperation(createInstanceOperation_GetContext(service));
		modelClass.addInstanceOperation(createInstanceOperation_GetDelegate(service));
		modelClass.addInstanceOperation(createInstanceOperation_Invoke(service));
		if (ServiceUtil.isStateful(service))
			modelClass.addInstanceOperation(createInstanceOperation_Validate(service));

		if (getFault(service) != null)
			modelClass.addInstanceOperation(createInstanceOperation_CreateException(service));

		/*
		//modelClass.addInstanceOperation(createActionOperation(service));
		//modelClass.addInstanceOperation(createValidateOperation());
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			initializeInstanceOperations(modelClass, service, element, operation);
		}
		*/		
	}

	/**
	 * initialize() method generation with delegation as EJB invocation
	 */
	protected ModelOperation createInstanceOperation_Initialize(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		//modelOperation.addAnnotation(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("registerAsRMIService();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	/**
	 * getContext() method generation with delegation as EJB invocation
	 */
	protected ModelOperation createInstanceOperation_GetContext(Service service) {
		nam.model.Process process = service.getProcess();
		String contextClassName = ServiceLayerHelper.getProcessContextClassName(process);
		String contextInstanceName = ServiceLayerHelper.getProcessContextInstanceName(process);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getContext");
		modelOperation.setResultType(contextClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		buf.putLine2("if ("+contextInstanceName+" == null || resetRequested)");
		buf.putLine2("	"+contextInstanceName+" = BeanContext.getFromJNDI("+contextClassName+".class, \""+contextClassName+"\");");
		buf.putLine2("return "+contextInstanceName+";");
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
			String serviceName = NameUtil.capName(service.getName());
			String interceptorClassName = serviceName + "Interceptor";
			String interceptorInstanceName = NameUtil.uncapName(interceptorClassName);
			
			buf.putLine2("if ("+interceptorInstanceName+" == null || resetRequested)");
			buf.putLine2("	"+interceptorInstanceName+" = BeanContext.getFromJNDI("+interceptorClassName+".class, \""+interceptorClassName+"\");");
			buf.putLine2("return "+interceptorInstanceName+";");
			
			//buf.putLine2("return "+serviceName+"Interceptor;");
			//buf.putLine2("return BeanContext.getFromJNDI("+serviceName+"Interceptor.class, \""+serviceName+"Interceptor\");");
	
		} else {
			String serviceName = NameUtil.uncapName(service.getName());
			buf.putLine2("return "+serviceName+"Handler;");
		}

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Element element, Operation operation) throws Exception {
		if (context.isEnabled("generateMessageLevelTransport")) {
			initializeInstanceOperations_MessageLevel(modelClass, service, element, operation);
		} else {
			initializeInstanceOperations_MethodLevel(modelClass, service, element, operation);
		}
	}

	/**
	 * MessageLevel service invocation
	 * ------------------------------
	 */
	protected void initializeInstanceOperations_MessageLevel(ModelClass modelClass, Service service, Element element, Operation operation) throws Exception {
		modelClass.addInstanceOperation(createInstanceOperation_ProcessMessage(service, element, operation));
	}
	
	/**
	 * ProcessMessage operation
	 * ------------------------
	 */
	protected ModelOperation createInstanceOperation_ProcessMessage(Service service, Element element, Operation operation) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("process");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Message");
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter("Message", "message"));
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		String sourceCode = serviceClassProvider.generateSourceCode_ProcessMessage(modelOperation, service, element, operation);
		modelOperation.addInitialSource(sourceCode);
		return modelOperation;
	}

	/**
	 * MethodLevel service invocation
	 * ------------------------------
	 */
	protected void initializeInstanceOperations_MethodLevel(ModelClass modelClass, Service service, Element element, Operation operation) throws Exception {
		ModelOperation modelOperation = createModelOperation(service, operation);
		//boolean isOneway = modelOperation.getResultType().equals("void");
		
		modelClass.addInstanceOperation(modelOperation);
		addImportedClasses(modelClass, operation);
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass("org.aries.validate.util.Check");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelOperation.addInitialSource(serviceClassProvider.generateSourceCode_ModelOperation_RMIInvocation(operation, service));

		org.eclipse.bpel.model.Process bpelProcess = null;
		if (service.getProcess() != null) {
			String processKey = service.getProcess().getName();
			if (processKey == null)
				processKey = service.getProcess().getRef();
			bpelProcess = BPELRuntimeCache.INSTANCE.getProcessByName(processKey);
		}

		//if (isOneway) {
		if (ServiceUtil.isStateful(service) && bpelProcess != null) {
			modelClass.addImportedClass("org.aries.registry.ProcessLocator");
			modelClass.addInstanceOperation(createProcessOperation(modelClass, service, operation));
		}
	}

	/**
	 * Process invocation
	 * ------------------
	 */
	protected ModelOperation createProcessOperation(ModelClass modelClass, Service service, Operation operation) {
		ModelOperation modelOperation = CodeUtil.createOperation(operation);
		modelOperation.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setPackageName("java.lang");
		modelParameter.setClassName("String");
		modelParameter.setName("correlationId");
		modelOperation.getParameters().add(0, modelParameter);
		Buf buf = new Buf();
		if (service.getProcess() != null) {
			String processKey = service.getProcess().getName();
			if (processKey == null)
				processKey = service.getProcess().getRef();
			org.eclipse.bpel.model.Process process = BPELRuntimeCache.INSTANCE.getProcessByName(processKey);
			if (process != null) {
				String processPackageName = ProjectLevelHelper.getPackageName(process.getTargetNamespace());
				String processClassName = NameUtil.toCamelCase(process.getName());
				modelClass.addImportedClass(processPackageName+"."+processClassName);
				String operationName = "receive"+NameUtil.capName(operation.getName());
				buf.putLine2("ProcessLocator processLocator = BeanContext.get(\"org.aries.processLocator\");");
				buf.putLine2(processClassName+" process = processLocator.lookup("+processClassName+".class, correlationId);");
				buf.put2("process."+operationName+"(");
				List<Parameter> parameters = operation.getParameters();
				Iterator<Parameter> iterator = parameters.iterator();
				for (int i=0; iterator.hasNext(); i++) {
					Parameter parameter = iterator.next();
					if (i > 0)
						buf.put(", ");
					buf.put(parameter.getName());
				}
				buf.putLine(");");
			}
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	/**
	 * Invoke invocation
	 * -----------------
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
		modelOperation.addException("RemoteException");
		
		Buf buf = new Buf();
		buf.putLine2("if (!isInitialized())");
		buf.putLine2("	return null;");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	if (request instanceof String)");
		buf.putLine2("		return processAsText((String) request);");
		buf.putLine2("	if (request instanceof Message)");
		buf.putLine2("		return processAsBinary((Message) request);");
		buf.putLine2("	throw new Exception(\"Unexpected payload type: \"+request.getClass());");
		buf.putLine2("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	Throwable cause = ExceptionUtil.getRootCause(e);");
		buf.putLine2("	throw new RemoteException(cause.getMessage(), cause);");
		//buf.putLine2("	throw new RemoteException(e.getMessage());");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	/**
	 * Validate invocation
	 * -------------------
	 */
	protected ModelOperation createInstanceOperation_Validate(Service service) {
		String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		Operation operation = ServiceUtil.getOperation(service, serviceName);
		
		nam.model.Process process = service.getProcess();
		String contextInstanceName = ServiceLayerHelper.getProcessContextInstanceName(process);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("validate");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addParameter(createParameter("Message", "message"));
		
		Buf buf = new Buf();
		//TODO add warning if null
		if (operation != null) {
			List<Parameter> parameters = operation.getParameters();
			Iterator<Parameter> iterator = parameters.iterator();
			while (iterator.hasNext()) {
				Parameter parameter = iterator.next();
				String parameterName = parameter.getName();
				String parameterClassName = TypeUtil.getClassName(parameter.getType());
				String parameterQualifiedName = TypeUtil.getQualifiedName(parameter.getType());
				//buf.putLine2(contextInstanceName+".validate(message.getPart(\""+parameterName+"\"));");
				buf.putLine2(parameterClassName+" "+parameterName+" = message.getPart(\""+parameterName+"\");");
				buf.putLine2(contextInstanceName+".validate("+parameterName+");");
				modelUnit.addImportedClass(parameterQualifiedName);
			}
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	/**
	 * CreateException method
	 * ----------------------
	 */
	protected ModelOperation createInstanceOperation_CreateException(Service service) {
		String faultClassName = ServiceLayerHelper.getFaultClassName(getFault(service));
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getExceptionToThrow");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addParameter(createParameter("Throwable", "exception"));
		modelOperation.setResultType("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("Exception cause = ExceptionUtil.getRootCause(exception);");
		buf.putLine2("return new "+faultClassName+"(cause.getMessage(), cause);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
	/**
	 * Operation creation method
	 * -------------------------
	 */
	protected ModelOperation createModelOperation(Service service, Operation operation) {
		ModelOperation modelOperation = super.createModelOperation(service, operation);
		//boolean isOneway = modelOperation.getResultType().equals("void");
		return modelOperation;
	}
	
}
