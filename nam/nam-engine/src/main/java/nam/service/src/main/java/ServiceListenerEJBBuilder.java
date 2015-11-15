package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Channel;
import nam.model.Element;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.TransportType;
import nam.model.util.ServiceUtil;
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
public class ServiceListenerEJBBuilder extends AbstractServiceListenerBuilder {

	private ServiceListenerEJBProvider serviceClassProvider;
	
	
	public ServiceListenerEJBBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected TransportType getTransportType() {
		return TransportType.EJB;
	}
	
	protected String getClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForEJB";
	}
	
	protected void initialize() {
		serviceClassProvider = new ServiceListenerEJBProvider(context);
	}

	protected boolean isMessageInterceptorEnabled(Service service) {
		return true;
	}
	
	public ModelClass buildClass(Service service, Channel channel, TransportType transport) throws Exception {
		ModelClass modelClass = super.buildClass(service, channel, transport);
		modelClass.setParentClassName("AbstractEJBListener");
		modelClass.addImportedClass("org.aries.tx.service.ejb.AbstractEJBListener");
		modelClass.addImportedClass("org.aries.tx.service.ejb.EJBListener");
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		//modelClass.addImplementedInterface("Remote");
		Element element = context.getElement(service);
		initializeClass(modelClass, service, channel, element);
		return modelClass; 
	}
	
	public ModelClass buildClass(Service service, Channel channel, Operation operation, TransportType transport) throws Exception {
		ModelClass modelClass = super.buildClass(service, channel, operation, transport);
		modelClass.setParentClassName("AbstractEJBListener");
		modelClass.addImportedClass("org.aries.tx.service.ejb.AbstractEJBListener");
		modelClass.addImportedClass("org.aries.tx.service.ejb.EJBListener");
		//modelClass.addImplementedInterface("MessageListener");
		//modelClass.addImportedClass("org.aries.MessageListener");
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		//modelClass.addImplementedInterface("Remote");
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		//modelClass.addImportedClass("org.aries.action.AbstractAction");
		Element element = context.getElement(service);
		initializeClass(modelClass, service, channel, element, operation);
		addImportedClasses(modelClass, operation);
		return modelClass; 
	}
	
	protected void initializeClass(ModelClass modelClass, Service service, Channel channel, Element element) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, element);
		initializeImportedClasses(modelClass, service, element);
		initializeInstanceFields(modelClass, service, element);
		//initializeInstanceConstructor(modelClass, service);
		initializeInstanceOperations(modelClass, service, element);
	}

	protected void initializeClass(ModelClass modelClass, Service service, Channel channel, Element element, Operation operation) throws Exception {
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
		//modelClass.addImportedClass("java.rmi.Remote");
		//modelClass.addImportedClass("java.rmi.RemoteException");
		
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createStartupAnnotation());
			classAnnotations.add(AnnotationUtil.createSingletonAnnotation());
			classAnnotations.add(AnnotationUtil.createLocalAnnotation("EJBListener"));
			classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
			modelClass.addImportedClass("javax.ejb.Startup");
			modelClass.addImportedClass("javax.ejb.Singleton");
			modelClass.addImportedClass("javax.ejb.Local");
			modelClass.addImportedClass("javax.ejb.ConcurrencyManagement");
			//modelClass.addImportedClass("javax.annotation.PostConstruct");
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
		modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
		modelClass.addImportedClass("org.aries.message.Message");
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
		if (!results.isEmpty())
			result = results.get(0);
		if (result != null) {
			Element element = context.findElement(result);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, result.getType());
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service, Element element) throws Exception {
		//String className = NameUtil.capName(modelClass.getName());
		//CodeUtil.addStaticLoggerField(modelClass, className);
		ServiceLayerFactory.addReference_ServiceInterceptor(modelClass, service);
	}
	
	protected void initializeInstanceConstructor(ModelClass modelClass, Service service) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		//modelConstructor.addException("RemoteException");
		
		Buf buf = new Buf();
		//buf.putLine2("registerAsEJBService();");
		modelConstructor.addInitialSource(buf.get());
		modelClass.addInstanceConstructor(modelConstructor);
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Element element) throws Exception {
		//modelClass.addInstanceOperation(createInstanceOperation_Initialize(service));
		modelClass.addInstanceOperation(createInstanceOperation_GetModuleId(service));
		modelClass.addInstanceOperation(createInstanceOperation_GetServiceId(service));
		modelClass.addInstanceOperation(createInstanceOperation_GetDelegate(service));
		modelClass.addInstanceOperation(createInstanceOperation_Invoke(service));

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
		modelOperation.addAnnotation(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		//buf.putLine2("registerAsEJBService();");
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
		modelOperation.addInitialSource(serviceClassProvider.generateSourceCode_ModelOperation_EJBInvocation(operation, service));

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
		modelOperation.addParameter(createParameter("Serializable", "payload"));
		modelOperation.addParameter(createParameter("String", "correlationId"));
		modelOperation.addParameter(createParameter("long", "timeout"));
		modelOperation.setResultType("Serializable");
		//modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("if (!isInitialized())");
		buf.putLine2("	return null;");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	if (payload instanceof String)");
		buf.putLine2("		return processAsText((String) payload);");
		buf.putLine2("	if (payload instanceof Message)");
		buf.putLine2("		return processAsBinary((Message) payload);");
		buf.putLine2("	throw new Exception(\"Unexpected payload type: \"+payload.getClass());");
		buf.putLine2("	");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	throw new RuntimeException(e.getMessage());");
		buf.putLine2("}");
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
