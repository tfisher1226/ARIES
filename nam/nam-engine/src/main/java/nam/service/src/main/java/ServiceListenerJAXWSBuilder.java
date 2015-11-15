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
import nam.model.util.OperationUtil;
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
public class ServiceListenerJAXWSBuilder extends AbstractServiceListenerBuilder {

	private ServiceListenerJAXWSProvider serviceClassProvider;
	
	
	public ServiceListenerJAXWSBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected TransportType getTransportType() {
		return TransportType.HTTP;
	}
	
	protected String getClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service) + "ListenerForJAXWS";
	}
	
	protected void initialize() {
		serviceClassProvider = new ServiceListenerJAXWSProvider(context);
	}

//	public List<ModelClass> buildClasses(Services services) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		List<Service> list = ServicesUtil.getServices(services);
//		Iterator<Service> iterator = list.iterator();
//		while (iterator.hasNext()) {
//			Service service = iterator.next();
//			modelClasses.add(buildClass(service));
//		}
//		return modelClasses;
//	}
	
	public ModelClass buildClass(Service service, Channel channel) throws Exception {
		return buildClass(service, channel, TransportType.HTTP);
	}
	
	public ModelClass buildClass(Service service, Channel channel, TransportType transport) throws Exception {
		ModelClass modelClass = super.buildClass(service, channel, transport);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		modelClass.addImplementedInterface(interfaceName);
		Element element = context.getElement(service);
		initializeClass(modelClass, service, channel, element);
		return modelClass; 
	}
	
	public ModelClass buildClass(Service service, Channel channel, Operation operation, TransportType transport) throws Exception {
		ModelClass modelClass = super.buildClass(service, channel, operation, transport);
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
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
		modelClass.addClassAnnotation(AnnotationUtil.createRemoteAnnotation(service));
		modelClass.addClassAnnotation(AnnotationUtil.createStatelessAnnotation(service));
		modelClass.addClassAnnotation(AnnotationUtil.createWebServiceAnnotation(service));
		modelClass.addClassAnnotation(AnnotationUtil.createHandlerChainAnnotation("/jaxws-handlers-service-oneway.xml"));
		modelClass.addImportedClass("javax.ejb.Remote");
		modelClass.addImportedClass("javax.ejb.Stateless");
		modelClass.addImportedClass("javax.jws.WebService");
		modelClass.addImportedClass("javax.jws.HandlerChain");
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Service service, Element element) throws Exception {
		modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
		//modelClass.addStaticImportedClass("javax.ejb.TransactionManagementType.CONTAINER");
		initializeImportedClasses(modelClass, service, ServiceUtil.getOperations(service));
		//String contextPrefix = NameUtil.getQualifiedContextNamePrefix(modelClass.getQualifiedName(), 2);
		//modelClass.addImportedClass(contextPrefix+".exception.ExceptionFactory");
		//modelClass.addImportedClass("org.aries.Assert");
		//modelClass.addImportedClass("org.aries.runtime.Initializer");
		modelClass.addImportedClass("org.aries.tx.module.Bootstrapper");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		//if (service.getTransacted() != null) {
		//modelClass.addImportedClass("javax.ejb.TransactionManagement");
		modelClass.addImportedClass("javax.ejb.TransactionAttribute");
		//modelClass.addImportedClass("javax.ejb.TransactionManagementType");
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Service service, List<Operation> operations) throws Exception {
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			initializeImportedClasses(modelClass, service, operation);
		}
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Service service, Operation operation) throws Exception {
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
		if (ServiceUtil.hasResult(service, operation)) {
			modelClass.addImportedClass("org.aries.Assert");
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service, Element element) throws Exception {
		String className = NameUtil.capName(modelClass.getName());
		CodeUtil.addStaticLoggerField(modelClass, className);
		if (ServiceUtil.isStateful(service))
			ServiceLayerFactory.addReference_StatefulContext(modelClass, service);
		ServiceLayerFactory.addReference_ServiceHandler(modelClass, service);
		CodeUtil.addWebServiceContextField(modelClass, service);
		CodeUtil.addTransactionSynchronizationRegistryField(modelClass);
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Element element) throws Exception {
		//modelClass.addInstanceOperation(createActionOperation(service));
		//modelClass.addInstanceOperation(createValidateOperation());
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			initializeInstanceOperations(modelClass, service, element, operation);
		}
//		if (ServiceUtil.isStateful(service))
//			System.out.println();
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
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation());
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
		boolean isOneway = ServiceUtil.isSynchronous(service) == false;
		
		modelClass.addInstanceOperation(modelOperation);
		addImportedClasses(modelClass, operation);
		modelClass.addInstanceOperation(modelOperation);
		//modelClass.addImportedClass("org.aries.validate.util.Check");
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelOperation.addInitialSource(serviceClassProvider.generateSourceCode_ModelOperation_HTTPInvocation(operation, service));

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
	 * Operation creation method
	 * -------------------------
	 */
	protected ModelOperation createModelOperation(Service service, Operation operation) {
		ModelOperation modelOperation = super.createModelOperation(service, operation);
		boolean isStateless = ServiceUtil.isStateful(service) == false;
		boolean isOneway = ServiceUtil.isSynchronous(service) == false;

		if (isOneway) {
			modelOperation.addAnnotation(AnnotationUtil.createOnewayAnnotation());
			modelOperation.addImportedClass("javax.jws.Oneway");
		}

		//having this exist with oneway is too "cluttered"
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());

		String uniqueOperationName = null;
		if (isStateless)
			uniqueOperationName = OperationUtil.getUniqueOperationName(operation);
		modelOperation.addAnnotation(AnnotationUtil.createWebMethodAnnotation(uniqueOperationName));
		modelOperation.addImportedClass("javax.jws.WebMethod");
		
		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty()) {
			result = results.get(0);
			//String name = result.getName();
			String typeName = CodeUtil.getResultTypeName(result);
			typeName = CodeUtil.getStructuredName(result.getConstruct(), typeName);
			modelOperation.addAnnotation(AnnotationUtil.createWebResultAnnotation(typeName));
			modelOperation.addImportedClass("javax.jws.WebResult");
		}
		
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation());
		return modelOperation;
	}
	
}
