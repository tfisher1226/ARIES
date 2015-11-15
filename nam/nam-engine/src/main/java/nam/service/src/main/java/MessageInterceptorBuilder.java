package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.Services;
import nam.model.TransportType;
import nam.model.util.OperationUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.ServicesUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds a Client Interceptor (i.e. client-side) Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class MessageInterceptorBuilder extends AbstractBeanBuilder {

	private MessageInterceptorProvider messageInterceptorProvider;
	
	
	public MessageInterceptorBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		messageInterceptorProvider = new MessageInterceptorProvider(context);
	}
	
	public List<ModelClass> buildClasses(Services services) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Service> list = ServicesUtil.getServices(services);
		Iterator<Service> iterator = list.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			ModelClass modelClass = buildClass(service);
			modelClasses.add(modelClass);
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(Service service) throws Exception {
		Element element = null;
		if (service.getElement() != null)
			element = context.getElementByType(service.getElement());
		
		String namespace = ClientLayerHelper.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "Interceptor";
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
		modelClass.setParentClassName("MessageInterceptor<"+interfaceName+">");
		modelClass.addImportedClass("org.aries.message.MessageInterceptor");
		//modelClass.addImplementedInterface(interfaceName);
		initializeClass(modelClass, service, element);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Service service, Element element) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, element);
		initializeImportedClasses(modelClass, service, element);
		initializeInstanceFields(modelClass, service, element);
		initializeInstanceOperations(modelClass, service, element);
	}

	protected void initializeClassAnnotations(ModelClass modelClass, Service service, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createStatelessAnnotation());
		classAnnotations.add(AnnotationUtil.createLocalBeanAnnotation());
		classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
		classAnnotations.add(AnnotationUtil.createTransactionManagementAnnotation());
		
//		switch (context.getClientLayerBeanType()) {
//		case POJO: initializeClassAnnotations_POJO(modelClass, service, element); break;
//		case EJB: initializeClassAnnotations_EJB(modelClass, service, element); break;
//		case SEAM: initializeClassAnnotations_SEAM(modelClass, service, element); break;
//		}
	}

	protected void initializeClassAnnotations_POJO(ModelClass modelClass, Service service, Element element) throws Exception {
		//nothing for now
	}
	
	protected void initializeClassAnnotations_EJB(ModelClass modelClass, Service service, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createAnnotation("Stateless"));
		classAnnotations.add(AnnotationUtil.createAnnotation("Local", service.getInterfaceName()+".class"));
		modelClass.addImportedClass("javax.ejb.Stateless");
		modelClass.addImportedClass("javax.ejb.Local");
	}
	
	protected void initializeClassAnnotations_SEAM(ModelClass modelClass, Service service, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		//classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
		//classAnnotations.add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		//String serviceContextName = ClientLayerHelper.getClientContextName(service);
		//classAnnotations.add(AnnotationUtil.createNameAnnotation(serviceContextName));
		//modelClass.addImportedClass("org.jboss.seam.ScopeType");
		//modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
		//modelClass.addImportedClass("org.jboss.seam.annotations.Name");
		//modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
		//modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service, Element element) throws Exception {
		modelClass.addImportedClasses(CodeUtil.getImportedClasses(service));
		
		modelClass.addImportedClass("org.aries.message.Message");
		modelClass.addImportedClass("org.aries.message.MessageInterceptor");
		//modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		//modelClass.addImportedClass("org.aries.Assert");
		
		modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
		modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
		modelClass.addStaticImportedClass("javax.ejb.TransactionManagementType.CONTAINER");

		modelClass.addImportedClass("javax.ejb.LocalBean");
		modelClass.addImportedClass("javax.ejb.Stateless");
		modelClass.addImportedClass("javax.ejb.ConcurrencyManagement");
		modelClass.addImportedClass("javax.ejb.TransactionAttribute");
		modelClass.addImportedClass("javax.ejb.TransactionManagement");
		modelClass.addImportedClass("javax.inject.Inject");
		
		if (element != null)
			modelClass.addImportedClass(ModelLayerHelper.getElementQualifiedName(element));
		//modelClass.addImportedClass("java.io.Serializable");
		//addImportedClassesForFaults(modelClass, ServiceUtil.getFaults(service));
		//initializeImportedClasses(modelClass, ServiceUtil.getOperations(service));
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
		CodeUtil.addStaticLoggerField(modelClass, className);
		//CodeUtil.addSerialVersionUIDField(modelClass);
		modelClass.addStaticAttribute(createHandlerAttribute(service));
	}

	public ModelAttribute createHandlerAttribute(Service service) {
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = interfaceName + "Handler";
		String beanName = NameUtil.uncapName(className);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
		attribute.setModifiers(Modifier.PROTECTED);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName(beanName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Element element) throws Exception {
		//add service location method
		//createModelOperation_CreateMessage(modelClass, service);
		//createModelOperation_GetProxy(modelClass, service);
		//createModelOperation_GetHandler(modelClass, service);

		//modelClass.addInstanceOperation(createActionOperation(service));
		//modelClass.addInstanceOperation(createValidateOperation());
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			ModelOperation modelOperation = new ModelOperation();
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation());
			modelOperation.setName(OperationUtil.getUniqueOperationName(operation));
			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setPackageName("org.aries.message");
			modelParameter.setClassName("Message");
			modelParameter.setName("message");
			modelOperation.addParameter(modelParameter);
			modelOperation.setResultType("Message");
			
			messageInterceptorProvider.setService(service);
			String serviceName = messageInterceptorProvider.getServiceName(operation.getName());
			messageInterceptorProvider.generateSourceCode_ProxyInvocation(modelOperation, operation);
			modelClass.addInstanceOperation(modelOperation);
		}
	}

	protected void createModelOperation_CreateMessage(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createMessage");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Message");
		messageInterceptorProvider.generateSourceCode_CreateMessage(modelOperation, service);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createModelOperation_GetProxy(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getProxy");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("ServiceProxy");
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setPackageName("java.lang");
		modelParameter.setClassName("String");
		modelParameter.setName("serviceId");
		modelOperation.addParameter(modelParameter);
		messageInterceptorProvider.generateSourceCode_GetProxy(modelOperation, TransportType.RMI);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createModelOperation_GetHandler(ModelClass modelClass, Service service) {
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getHandler");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(interfaceName + "Handler");
		messageInterceptorProvider.generateSourceCode_GetHandler(modelOperation, service);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	
}
