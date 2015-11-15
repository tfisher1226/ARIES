package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Callback;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.Services;
import nam.model.util.OperationUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.ServicesUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;


/**
 * Builds a Service Interface {@link ModelInterface} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceInterfaceBuilder extends AbstractManagementBeanBuilder {

	public ServiceInterfaceBuilder(GenerationContext context) {
		super(context);
	}

	protected String getNamespace(Service service) {
		return ServiceUtil.getNamespace(service);
	}

	protected String getPackageName(Service service) {
		return ServiceLayerHelper.getServicePackageName(service);
	}

	protected String getInterfaceName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service);
	}

	public List<ModelInterface> buildInterfaces(Services services) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		List<Service> list = ServicesUtil.getServices(services);
		Iterator<Service> iterator = list.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (context.isEnabled("generateServicePerOperation")) {
				modelInterfaces.addAll(buildInterfaces(service));
			} else {
				modelInterfaces.add(buildInterface(service));
			}
		}
		return modelInterfaces;
	}

	public List<ModelInterface> buildInterfaces(Service service) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			modelInterfaces.add(buildInterface(service, operation));
		}
		return modelInterfaces;
	}
	
	public ModelInterface buildInterface(Service service) throws Exception {
		Element element = context.getElement(service);
		String namespace = getNamespace(service);
		String packageName = getPackageName(service);
		String interfaceName = getInterfaceName(service);
		String rootName = ServiceUtil.getRootName(service);
		String beanName = NameUtil.uncapName(interfaceName);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(interfaceName);
		ModelInterface modelInterface = new ModelInterface();
		modelInterface.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		modelInterface.setName(beanName);
		modelInterface.setNamespace(namespace);
		initializeInterface(modelInterface, service, element);
		return modelInterface; 
	}

	public void initializeInterface(ModelInterface modelInterface, Service service, Element element) throws Exception {
		initializeInterfaceAnnotations(modelInterface, service, element);
		initializeImportedClasses(modelInterface, service, element);
		initializeInterfaceFields(modelInterface, service, element);
		initializeInterfaceMethods(modelInterface, service, element);
	}
	
	public ModelInterface buildInterface(Service service, Operation operation) throws Exception {
		Element element = context.getElement(service);
		String namespace = getNamespace(service);
		String packageName = getPackageName(service);
		String interfaceName = getInterfaceName(service);
		String rootName = ServiceUtil.getRootName(service);
		String beanName = NameUtil.capName(interfaceName);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(interfaceName);
		ModelInterface modelInterface = new ModelInterface();
		modelInterface.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		modelInterface.setName(beanName);
		modelInterface.setNamespace(namespace);
		initializeInterface(modelInterface, service, element, operation);
		return modelInterface; 
	}
	
	public void initializeInterface(ModelInterface modelInterface, Service service, Element element, Operation operation) throws Exception {
		initializeInterfaceAnnotations(modelInterface, service, element);
		initializeImportedClasses(modelInterface, service, element, operation);
		initializeInterfaceFields(modelInterface, service, element, operation);
		initializeInterfaceMethods(modelInterface, service, element, operation);
	}

	protected void initializeInterfaceAnnotations(ModelInterface modelInterface, Service service, Element element) throws Exception {
		String serviceTransport = context.getProperty("generateServiceTransport");
		//TODO make this default come from central place
		if (serviceTransport == null)
			serviceTransport = "JAXWS";

		if (serviceTransport.equals("JAXWS")) {
			modelInterface.addInterfaceAnnotation(AnnotationUtil.createWebServiceInterfaceAnnotation(service));
			modelInterface.addInterfaceAnnotation(AnnotationUtil.createSOAPBindingRPCStyleAnnotation());
			modelInterface.addImportedClass("javax.jws.WebService");
			modelInterface.addImportedClass("javax.jws.soap.SOAPBinding");
		}
	}

	protected void initializeImportedClasses(ModelInterface modelInterface, Service service, Element element) throws Exception {
		if (element != null) {
			String elementPackageName = ModelLayerHelper.getElementPackageName(element);
			String elementClassName = ModelLayerHelper.getElementClassName(element);
			modelInterface.addImportedClass(elementPackageName + "." + elementClassName);
		}
		if (modelInterface.getExtendedInterfaces().size() > 0) {
			Iterator<String> iterator = modelInterface.getExtendedInterfaces().iterator();
			while (iterator.hasNext()) {
				String extendedInterface = iterator.next();
				modelInterface.addExtendedInterface(extendedInterface);
				modelInterface.addImportedClass(extendedInterface);
			}
		}
	}
	
	protected void initializeImportedClasses(ModelInterface modelInterface, Service service, Element element, Operation operation) throws Exception {
		initializeImportedClasses(modelInterface, service, element);
		initializeImportedClasses(modelInterface, operation);
	}

	protected void initializeImportedClasses(ModelInterface modelInterface, Service service, Element element, List<Operation> operations) throws Exception {
		initializeImportedClasses(modelInterface, service, element);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			initializeImportedClasses(modelInterface, operation);
		}
	}
	
	protected void initializeImportedClasses(ModelInterface modelInterface, Operation operation) throws Exception {
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			Parameter parameter = parameterIterator.next();
			Element element = context.findElement(parameter);
			if (element != null)
				addImportedClass(modelInterface, element.getType());
			else addImportedClass(modelInterface, parameter.getType());
		}
		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty())
			result = results.get(0);
		if (result != null) {
			Element element = context.findElement(result);
			if (element != null)
				addImportedClass(modelInterface, element.getType());
			else addImportedClass(modelInterface, result.getType());
		}
	}

	protected void initializeInterfaceMethods(ModelInterface modelInterface, Service service, Element element) throws Exception {
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			initializeInterfaceMethods(modelInterface, service, element, operation);
		}
		if (element != null && operations.size() == 0) {
			initializeInterfaceMethods(modelInterface, element);
		}
	}

	protected void initializeInterfaceFields(ModelInterface modelInterface, Service service, Element element) throws Exception {
		initializeInterfaceFields(modelInterface, service, element, null);
	}
	
	protected void initializeInterfaceFields(ModelInterface modelInterface, Service service, Element element, Operation operation) throws Exception {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PUBLIC);
		modelAttribute.setClassName("String");
		modelAttribute.setName("ID");
		
		//TODO - Externalize this properly
		String serviceId = service.getDomain();
		if (!context.getPropertyAsBoolean("appendProjectToGroupId"))
			serviceId += "." + context.getProject().getName();
		serviceId += "." + NameUtil.uncapName(service.getInterfaceName());
		
		modelAttribute.setDefault("\""+serviceId+"\"");
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		modelInterface.addInstanceAttribute(modelAttribute);
	}

	protected void initializeInterfaceMethods(ModelInterface modelInterface, Service service, Element element, Operation operation) throws Exception {
		ModelOperation modelOperation = createModelOperation(service, operation);
		if (service instanceof Callback) {
			String operationName = modelOperation.getName();
			operationName = NameUtil.capName(operationName);
			modelOperation.setName("reply" + operationName);
		}
		modelInterface.addInstanceOperation(modelOperation);
		addImportedClasses(modelInterface, operation);
	}

}

//public List<Execution> buildExecutions(ModelService modelService) {
//List<Execution> executions = new ArrayList<Execution>();
//return executions;
//}
//
//public Execution buildExecution(ModelExecution modelExecution) {
//Execution execution = new Execution();
//return execution;
//}
//
//public List<Execution> buildExecutions(ModelService modelService) {
//List<Execution> executions = new ArrayList<Execution>();
//return executions;
//}
//
//public Execution buildExecution(ModelExecution modelExecution) {
//Execution execution = new Execution();
//return execution;
//}

