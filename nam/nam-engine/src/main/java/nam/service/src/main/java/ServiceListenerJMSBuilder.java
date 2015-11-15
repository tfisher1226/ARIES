package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Element;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.TransportType;
import nam.model.util.ParameterUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerFactory;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Process;

import aries.bpel.BPELRuntimeCache;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
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
public class ServiceListenerJMSBuilder extends AbstractServiceListenerBuilder {

	private ServiceListenerJMSProvider serviceListenerProvider;
	
	
	public ServiceListenerJMSBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected TransportType getTransportType() {
		return TransportType.JMS;
	}

	protected String getClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForJMS";
	}
	
	protected void initialize() {
		serviceListenerProvider = new ServiceListenerJMSProvider(context);
	}

	public ModelClass buildClass(Service service, Channel channel, TransportType transport) throws Exception {
		ModelClass modelClass = super.buildClass(service, channel, transport);
		modelClass.addImplementedInterface("MessageListener");
		modelClass.addImportedClass("javax.jms.MessageListener");
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		modelClass.setParentClassName("AbstractJMSListener");
		modelClass.addImportedClass("org.aries.tx.service.jms.AbstractJMSListener");
		Element element = context.getElement(service);
		initializeClass(modelClass, service, channel, element);
		return modelClass; 
	}
	
	public ModelClass buildClass(Service service, Channel channel, Operation operation, TransportType transport) throws Exception {
		ModelClass modelClass = super.buildClass(service, channel, operation, transport);
		modelClass.addImplementedInterface("MessageListener");
		modelClass.addImportedClass("javax.jms.MessageListener");
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		//modelClass.addImportedClass("org.aries.action.AbstractAction");
		modelClass.setParentClassName("AbstractJMSListener");
		modelClass.addImportedClass("org.aries.tx.service.jms.AbstractJMSListener");
		Element element = context.getElement(service);
		initializeClass(modelClass, service, channel, element, operation);
		addImportedClasses(modelClass, operation);
		return modelClass; 
	}
	
	protected void initializeClass(ModelClass modelClass, Service service, Channel channel, Element element) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, channel, element);
		initializeImportedClasses(modelClass, service, element);
		initializeInstanceFields(modelClass, service, element);
		initializeInstanceOperations(modelClass, service, element);
	}

	protected void initializeClass(ModelClass modelClass, Service service, Channel channel, Element element, Operation operation) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, channel, element);
		initializeImportedClasses(modelClass, service, element);
		initializeInstanceFields(modelClass, service, element);
		initializeInstanceOperations(modelClass, service, element, operation);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Service service, Channel channel, Element element) throws Exception {
		String contextName = NameUtil.uncapName(modelClass.getName());
		if (context.isEnabled("generateServicePerOperation"))
			contextName = NameUtil.uncapName(service.getName()) + "." + contextName;
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createMessageDrivenQueueBasedAnnotation(service, channel));
		classAnnotations.add(AnnotationUtil.createTransactionManagementAnnotation());
		classAnnotations.add(AnnotationUtil.createInterceptorsAnnotation("JMSListenerInterceptor.class"));
		modelClass.addImportedClass("org.aries.tx.service.jms.JMSListenerInterceptor");
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Service service, Element element) throws Exception {
		//initializeImportedClasses(modelClass, ServiceUtil.getOperations(service));
		//String contextPrefix = NameUtil.getQualifiedContextNamePrefix(modelClass.getQualifiedName(), 2);
		//modelClass.addImportedClass(contextPrefix+".exception.ExceptionFactory");
		modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
		modelClass.addStaticImportedClass("javax.ejb.TransactionManagementType.CONTAINER");
		modelClass.addImportedClass("javax.ejb.ActivationConfigProperty");
		modelClass.addImportedClass("javax.ejb.MessageDriven");
		modelClass.addImportedClass("javax.ejb.MessageDrivenContext");
		modelClass.addImportedClass("javax.ejb.TransactionAttribute");
		modelClass.addImportedClass("javax.ejb.TransactionManagement");
		modelClass.addImportedClass("javax.interceptor.Interceptors");
		//modelClass.addImportedClass("javax.jms.Message");
		
		String operationName = service.getName();
		Operation operation = ServiceUtil.getOperation(service, operationName);
		if (operation == null || ServiceUtil.isSynchronous(service)) {
			modelClass.addImportedClass("javax.jms.Destination");
			modelClass.addImportedClass("java.io.Serializable");
			modelClass.addImportedClass("org.aries.message.Message");
			//modelClass.addImportedClass("org.aries.message.util.MessageConstants");
		}
		
		//modelClass.addImportedClass("org.aries.Assert");
		//modelClass.addImportedClass("org.aries.validate.util.Check");
		//modelClass.addImportedClass("org.aries.runtime.Initializer");
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.jms.util.MessageUtil");
		//modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		modelClass.addImportedClass(ServiceLayerHelper.getServiceQualifiedInterfaceName(service));
		
		if (operation != null) {
			List<Parameter> parameters = operation.getParameters();
			Iterator<Parameter> iterator = parameters.iterator();
			while (iterator.hasNext()) {
				Parameter parameter = iterator.next();
				String qualifiedName = TypeUtil.getQualifiedName(parameter.getType());
				modelClass.addImportedClass(qualifiedName);
			}
		}
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
		//String className = NameUtil.capName(modelClass.getName());
		//CodeUtil.addStaticLoggerField(modelClass, className);
		if (ServiceUtil.isStateful(service))
			ServiceLayerFactory.addReference_StatefulContext(modelClass, service);
		CodeUtil.addMessageDrivenContextField(modelClass, service);
		if (ServiceUtil.isSynchronous(service))
			ServiceLayerFactory.addReference_ServiceInterceptor(modelClass, service);
		else ServiceLayerFactory.addReference_ServiceHandler(modelClass, service);
		CodeUtil.addTransactionSynchronizationRegistryField(modelClass);
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Element element) throws Exception {
		modelClass.addInstanceOperation(createInstanceOperation_GetModuleId(service));
		modelClass.addInstanceOperation(createInstanceOperation_GetServiceId(service));
		modelClass.addInstanceOperation(createInstanceOperation_GetDelegate(service));
		modelClass.addInstanceOperation(createInstanceOperation_OnMessage(service));
		if (ServiceUtil.isSynchronous(service))
			modelClass.addInstanceOperation(createInstanceOperation_Invoke(service));
		//else modelClass.addInstanceOperation(createInstanceOperation_Send(service)); 
		
		/*
		if (context.isEnabled("generateMessageLevelTransport")) {
			List<Operation> operations = ServiceUtil.getOperations(service);
			Iterator<Operation> iterator = operations.iterator();
			while (iterator.hasNext()) {
				Operation operation = iterator.next();
				initializeInstanceOperations(modelClass, service, element, operation);
			}
			
		} else {
			List<Operation> operations = ServiceUtil.getOperations(service);
			Iterator<Operation> iterator = operations.iterator();
			while (iterator.hasNext()) {
				Operation operation = iterator.next();
				modelClass.addInstanceOperation(createInstanceOperation_OnMessage(service, operation));
				//modelClass.addInstanceOperation(createValidateOperation());
			}
		}
		*/
	}
	
	/**
	 * onMessage() method generation with delegation as EJB invocation
	 */
	protected ModelOperation createInstanceOperation_OnMessage(Service service) {
		nam.model.Process process = service.getProcess();

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("onMessage");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("javax.jms.Message", "jmsMessage"));
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation());
		
		Buf buf = new Buf();
		buf.putLine2("if (!isInitialized())");
		buf.putLine2("	return;");
		buf.putLine2("");
		
		//buf.putLine2("	String jmsMessageID = jmsMessage.getJMSMessageID();");
		//buf.putLine2("	String jmsCorrelationID = jmsMessage.getJMSCorrelationID();");
		//buf.putLine2("	boolean jmsRedelivered = jmsMessage.getJMSRedelivered();");
		//buf.putLine2("	long jmsExpiration = jmsMessage.getJMSExpiration();");
		//buf.putLine2("	long jmsTimestamp = jmsMessage.getJMSTimestamp();");
		//buf.putLine2("	long requestId = jmsMessage.getLongProperty(MessageConstants.PROPERTY_REQUEST_ID);");
		//buf.putLine2("	String transactionId = jmsMessage.getStringProperty(MessageConstants.PROPERTY_TRANSACTION_ID);");
		//buf.putLine2("	String correlationId = jmsMessage.getStringProperty(MessageConstants.PROPERTY_CORRELATION_ID);");
		
		if (ServiceUtil.isSynchronous(service)) {
			buf.putLine2("try {");
			buf.putLine2("	String correlationId = MessageUtil.getCorrelationIdFromMessage(jmsMessage);");
			buf.putLine2("	Serializable payload = MessageUtil.getPayloadFromMessage(jmsMessage);");
			buf.putLine2("	Serializable output = invoke(payload, correlationId, 0L);");
			buf.putLine2("	");
			buf.putLine2("	//send response (if any)");
			buf.putLine2("	if (output != null) {");
			buf.putLine2("		Destination jmsReplyTo = jmsMessage.getJMSReplyTo();");
			buf.putLine2("		sendResponse(jmsReplyTo, output, correlationId, transactionId);");
			buf.putLine2("	}");
			buf.putLine2("	");
			buf.putLine2("} catch (Throwable e) {");
			buf.putLine2("	log.error(e);");
			buf.putLine2("	//TODO send this exception to \"invalid\" queue");
			//buf.putLine2("	//String reason = e.getMessage();");
			//buf.putLine2("	//throw new JMSException(reason);");
			buf.putLine2("}");
			
		} else {
			buf.putLine2("try {");
			//buf.putLine2("	//logStarted();");
			//buf.putLine2("	String correlationId = getCorrelationIdFromMessage(message);");

			String operationName = service.getName();
			Operation operation = ServiceUtil.getOperation(service, operationName);
			
			if (operation != null) {
				//buf.putLine2("	//String correlationId = MessageUtil.getCorrelationIdFromMessage(jmsMessage);");

				//String operationName = operation .getName();
				List<Parameter> parameters = operation.getParameters();
				Iterator<Parameter> iterator = parameters.iterator();
				while (iterator.hasNext()) {
					Parameter parameter = iterator.next();
					String className = TypeUtil.getClassName(parameter.getType());
					String instanceName = NameUtil.uncapName(className);
					buf.putLine2("	"+className+" "+instanceName+" = MessageUtil.getPart(jmsMessage, \""+instanceName+"\");");
				}
	
				buf.putLine2("	");
				buf.putLine2("	//validate the message");
				process = service.getProcess();
				String processContextInstanceName = ServiceLayerHelper.getProcessContextInstanceName(process);
				buf.putLine2("	"+processContextInstanceName+".initializeContext(jmsMessage);");

				String parameterString = ParameterUtil.getArgumentString(operation);
				if (process != null) {
					buf.putLine2("	"+processContextInstanceName+".validate("+parameterString+");");
					//buf.putLine3("");
				}
				
				buf.putLine2("	");
				buf.putLine2("	//handle the message");
				String handlerInstanceName = ServiceLayerHelper.getHandlerInstanceName(service);
				String handlerClassName = ServiceLayerHelper.getHandlerClassName(service);
				//buf.putLine3(handlerInstanceName+".setCorrelationId(correlationId);");
				//buf.putLine3(handlerInstanceName+".setTransactionId(transactionId);");
				buf.putLine3(handlerInstanceName+"."+operationName+"("+parameterString+");");
				//buf.putLine3("log.info(\""+interfaceName+": received\");");
				buf.putLine3("");

			} else {
				buf.putLine2("	String correlationId = MessageUtil.getCorrelationIdFromMessage(jmsMessage);");
				buf.putLine2("	Serializable payload = MessageUtil.getPayloadFromMessage(jmsMessage);");
				buf.putLine2("	Serializable output = invoke(payload, correlationId, 0L);");
				buf.putLine2("	");
				buf.putLine2("	//send the response (if any)");
				buf.putLine2("	if (output != null) {");
				buf.putLine2("		Destination jmsReplyTo = jmsMessage.getJMSReplyTo();");
				buf.putLine2("		sendResponse(jmsReplyTo, output, correlationId, transactionId);");
				buf.putLine2("	}");
			}
			
			buf.putLine2("} catch (Throwable e) {");
			buf.putLine2("	log.error(e);");
			buf.putLine2("	//TODO send this exception to \"invalid\" queue");
			//buf.putLine2("	//String reason = e.getMessage();");
			//buf.putLine2("	//throw new JMSException(reason);");
			
			process = service.getProcess();
			if (process != null) {
				String mainAction = context.getService() instanceof Callback ? "response" : "request";
				String mainServiceName = ServiceLayerHelper.getServiceInterfaceName(context.getService());
				String processContextInstanceName = ServiceLayerHelper.getProcessContextInstanceName(process);
				buf.putLine2("	"+processContextInstanceName+".fire_"+mainServiceName+"_incoming_"+mainAction+"_aborted(e);");
				//buf.putLine2("	"+processContextInstanceName+".fire_"+mainServiceName+"_"+mainAction+"Complete();");
			}

			buf.putLine2("}");
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
	 * onMessage() method generation with delegation as EJB invocation
	 */
	protected ModelOperation createInstanceOperation_OnMessage(Service service, Operation operation) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("onMessage");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("javax.jms.Message", "message"));
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation());

		//TODO include this below if/when user design calls for overriding the class level spec.
		//modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation());
		String sourceCode = serviceListenerProvider.generateSourceCode_ModelOperation_EJBInvocation(operation, service);
		modelOperation.addInitialSource(sourceCode);
		return modelOperation;
	}
	
	/**
	 * process() method generation with delegation using MessageLevel API.
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
		String sourceCode = serviceListenerProvider.generateSourceCode_ProcessMessage(modelOperation, service, element, operation);
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
		modelOperation.addInitialSource(serviceListenerProvider.generate(modelClass, service, operation));
		addImportedClasses(modelClass, operation);
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass("org.aries.validate.util.Check");
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");

		Process bpelProcess = null;
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
			Process process = BPELRuntimeCache.INSTANCE.getProcessByName(processKey);
			if (process != null) {
				String processPackageName = ProjectLevelHelper.getPackageName(process.getTargetNamespace());
				String processClassName = NameUtil.toCamelCase(process.getName());
				modelClass.addImportedClass(processPackageName+"."+processClassName);
				modelClass.addImportedClass("org.aries.runtime.BeanContext");
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
	 * Operation creation method
	 * -------------------------
	 */
	protected ModelOperation createModelOperation(Service service, Operation operation) {
		ModelOperation modelOperation = super.createModelOperation(service, operation);
		//boolean isOneway = modelOperation.getResultType().equals("void");
		return modelOperation;
	}
	
}
