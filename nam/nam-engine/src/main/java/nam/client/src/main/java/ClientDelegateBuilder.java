package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Callback;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.Services;
import nam.model.util.ServiceUtil;
import nam.model.util.ServicesUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds a Service Delegate (i.e. client-side) Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ClientDelegateBuilder extends AbstractManagementBeanBuilder {

	private ClientDelegateProvider provider;
	
	
	public ClientDelegateBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		provider = new ClientDelegateProvider(context);
		initialize(provider);
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

		String suffix = "Client";
		if (service instanceof Callback)
			suffix = "To";
		
		String namespace = ClientLayerHelper.getNamespace(service);
		String packageName = ClientLayerHelper.getClientPackageName(service);
		//String interfaceName = ClientLayerHelper.getClientInterfaceName(service);
		String className = ClientLayerHelper.getClientInterfaceName(service) + suffix;
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
		modelClass.setParentClassName("AbstractDelegate");
		modelClass.addImportedClass("org.aries.client.AbstractDelegate");
		initializeClass(modelClass, service, element);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Service service, Element element) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, element);
		initializeImportedClasses(modelClass, service, element);
		initializeImplementedInterfaces(modelClass, service, element);
		initializeInstanceFields(modelClass, service, element);
		initializeInstanceOperations(modelClass, service, element);
	}

	protected void initializeClassAnnotations(ModelClass modelClass, Service service, Element element) throws Exception {
		switch (context.getClientLayerBeanType()) {
		case POJO: initializeClassAnnotations_POJO(modelClass, service, element); break;
		case EJB: initializeClassAnnotations_EJB(modelClass, service, element); break;
		case SEAM: initializeClassAnnotations_SEAM(modelClass, service, element); break;
		}
	}

	protected void initializeClassAnnotations_POJO(ModelClass modelClass, Service service, Element element) throws Exception {
		//List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		//classAnnotations.add(AnnotationUtil.createSuppressSerialWarning());
	}
	
	protected void initializeClassAnnotations_EJB(ModelClass modelClass, Service service, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createAnnotation("Stateless"));
		classAnnotations.add(AnnotationUtil.createAnnotation("Local", service.getInterfaceName()+".class"));
		modelClass.addImportedClass("javax.ejb.Stateless;");
		modelClass.addImportedClass("javax.ejb.Local;");
	}
	
	protected void initializeClassAnnotations_SEAM(ModelClass modelClass, Service service, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		//SEAM classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
		//SEAM classAnnotations.add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//SEAM classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		//SEAM String serviceContextName = ClientLayerHelper.getClientContextName(service);
		//SEAM classAnnotations.add(AnnotationUtil.createNameAnnotation(serviceContextName));
		//SEAM modelClass.addImportedClass("org.jboss.seam.ScopeType");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.Name");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
		//SEAM modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service, Element element) throws Exception {
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		if (element != null)
			modelClass.addImportedClass(ModelLayerHelper.getElementQualifiedName(element));
		initializeImportedClasses(modelClass, ServiceUtil.getOperations(service));
		addImportedClassesForFaults(modelClass, ServiceUtil.getFaults(service));
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
		if (!operation.getResults().isEmpty()) {
			Result result = operation.getResults().get(0);
			Element element = context.findElement(result);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, result.getType());
		}
	}
	
	protected void initializeImplementedInterfaces(ModelClass modelClass, Service service, Element element) throws Exception {
		modelClass.addImplementedInterface(ClientLayerHelper.getClientInterfaceName(service));
		//modelClass.addImplementedInterface("java.io.Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service, Element element) throws Exception {
		CodeUtil.addStaticLoggerField(modelClass, className);
		//CodeUtil.addSerialVersionUIDField(modelClass);
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Element element) throws Exception {
		createModelOperation_GetDomain(modelClass, service);
		createModelOperation_GetServiceId(modelClass, service);
		createModelOperation_GetProxy(modelClass, service);

		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			ModelOperation modelOperation = createModelOperation(service, operation);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			if (service instanceof Callback) {
				String operationName = modelOperation.getName();
				operationName = NameUtil.capName(operationName);
				modelOperation.setName("reply" + operationName);
			}

			//modelOperation.addAnnotation(AnnotationUtil.createWebMethodAnnotation());
			//modelClass.addImportedClass("javax.jws.WebMethod");
			provider.setService(service);
			String serviceName = provider.getServiceName(operation.getName());
			provider.generateSourceCode_DelegateInvocation(modelOperation, element, serviceName);
			modelClass.addInstanceOperation(modelOperation);
		}
		
		//become an element management bean?
		if (operations.size() == 0 && element != null) {
			provider.setService(service);
			super.initializeInstanceMethods(modelClass, element);
		}
	}

	protected void createModelOperation_GetDomain(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getDomain");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		provider.generateSourceCode_GetDomain(modelOperation, service);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createModelOperation_GetServiceId(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getServiceId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		provider.generateSourceCode_GetServiceId(modelOperation, service);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createModelOperation_GetProxy(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createSuppressWarning("unchecked"));
		modelOperation.setName("getProxy");
		modelOperation.setModifiers(Modifier.PUBLIC);
		String interfaceName = service.getInterfaceName();
		modelOperation.setResultType(interfaceName);
		modelOperation.addException("Exception");
		provider.generateSourceCode_GetProxy(modelOperation, service);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	
}
