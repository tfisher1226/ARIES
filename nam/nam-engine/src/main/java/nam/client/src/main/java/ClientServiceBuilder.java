package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Callback;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Service;
import nam.model.Services;
import nam.model.util.ServiceUtil;
import nam.model.util.ServicesUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds a Service Service (i.e. client-side) Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ClientServiceBuilder extends AbstractBeanBuilder {

	private Namespace namespace;

	
	public ClientServiceBuilder(GenerationContext context) {
		this.context = context;
	}

	public List<ModelClass> buildClasses(Services services) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Service> list = ServicesUtil.getServices(services);
		Iterator<Service> iterator = list.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (context.isEnabled("generateServicePerOperation")) {
				modelClasses.addAll(buildClasses(service));
			} else {
				modelClasses.add(buildClass(service));
			}
		}
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Service service) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			modelClasses.add(buildClass(service, operation));
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(Service service) throws Exception {
		String namespace = ClientLayerHelper.getNamespace(service);
		String packageName = ClientLayerHelper.getClientPackageName(service);
		//String interfaceName = ClientLayerHelper.getClientInterfaceName(service);
		String className = ClientLayerHelper.getClientInterfaceName(service) + "Service";
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
		modelClass.setParentClassName("javax.xml.ws.Service");
		initializeClass(modelClass, service, null);
		return modelClass; 
	}

	public ModelClass buildClass(Service service, Operation operation) throws Exception {
		//Element element = context.getElement(service);
		String namespace = ClientLayerHelper.getNamespace(service);
		String packageName = ClientLayerHelper.getClientPackageName(service);
		//String interfaceName = ClientLayerHelper.getClientInterfaceName(service);
		//String className = NameUtil.capName(operation.getName()) + "Service";
		String className = ClientLayerHelper.getClientInterfaceName(service) + "Service";
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
		modelClass.addImplementedInterface("MessageListener");
		modelClass.addImportedClass("org.aries.MessageListener");
		modelClass.addImplementedInterface("Serializable");
		modelClass.addImportedClass("java.io.Serializable");
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		//modelClass.addImportedClass("org.aries.action.AbstractAction");
		initializeClass(modelClass, service, operation);
		return modelClass; 
	}
	
	protected void initializeClass(ModelClass modelClass, Service service, Operation operation) throws Exception {
		initializeImportedClasses(modelClass, service);
		initializeClassAnnotations(modelClass, service);
		initializeInstanceFields(modelClass, service);
		initializeInstanceConstructor(modelClass, service);
		initializeInstanceOperations(modelClass, service, operation);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Service service) throws Exception {
		modelClass.addClassAnnotation(AnnotationUtil.createWebServiceClientAnnotation(service));
		modelClass.addImportedClass("javax.xml.ws.WebServiceClient");
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		CodeUtil.addPrivateStaticField(modelClass, "java.net.URL", "WSDL_LOCATION");
		modelClass.addInstanceAttribute(createServiceAttribute(service));
		//modelClass.addImportedClass("");
	}

	public static ModelAttribute createServiceAttribute(Service service) {
		String packageName = ClientLayerHelper.getClientPackageName(service);
		String className = ClientLayerHelper.getClientInterfaceName(service) + "Service";
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("service");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	protected void initializeInstanceConstructor(ModelClass modelClass, Service service) {
		String className = ClientLayerHelper.getClientInterfaceName(service) + "Service";
		String beanName = NameUtil.uncapName(className);
		CodeUtil.addConstructor(modelClass, "this(WSDL_LOCATION);");
		CodeUtil.addConstructor(modelClass, "java.net.URL", "wsdlLocation", "this(wsdlLocation, new QName(\""+service.getNamespace()+"\", \""+beanName+"\"));");
		ModelParameter modelParameter1 = CodeUtil.createParameter("java.net.URL", "wsdlLocation");
		ModelParameter modelParameter2 = CodeUtil.createParameter("javax.xml.namespace.QName", "serviceName");
		ModelParameter[] modelParameters = new ModelParameter[] {modelParameter1, modelParameter2};
		CodeUtil.addConstructor(modelClass, modelParameters, "super(wsdlLocation, serviceName);");
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		//modelClass.addInstanceOperation(createActionOperation(service));
		//modelClass.addInstanceOperation(createValidateOperation());
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			initializeInstanceOperations(modelClass, service, operation);
		}		
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Operation operation) throws Exception {
		modelClass.addInstanceOperation(createGetPortMethod(modelClass, service, operation, false));
		modelClass.addInstanceOperation(createGetPortMethod(modelClass, service, operation, true));
		modelClass.addImportedClass("javax.xml.ws.WebEndpoint");
		modelClass.addImportedClass("javax.xml.ws.WebServiceFeature");
	}


	protected ModelOperation createGetPortMethod(ModelClass modelClass, Service service, Operation operation, boolean addFeatures) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createWebEndpointAnnotation(service.getName()));
		modelOperation.setName("getPort");
		modelOperation.setModifiers(Modifier.PUBLIC);
		String packageName = context.getPackageName(service);
		//String packageName = NameUtil.getPackageFromNamespace(service.getNamespace());
		String interfaceName = service.getInterfaceName();
		String className = NameUtil.capName(interfaceName);
		String serviceName = NameUtil.uncapName(interfaceName);
		
		modelOperation.setResultType(packageName+"."+className);
		if (addFeatures == false) {
			Buf buf = new Buf();
			buf.putLine2("return super.getPort(new QName(\""+service.getNamespace()+"\", \""+serviceName+"\"), "+interfaceName+".class);");
			modelOperation.addInitialSource(buf.get());
		} else {
			Buf buf = new Buf();
			buf.putLine2("return super.getPort(new QName(\""+service.getNamespace()+"\", \""+serviceName+"\"), "+interfaceName+".class, features);");
			modelOperation.addInitialSource(buf.get());
			ModelParameter modelParameter = CodeUtil.createParameter("WebServiceFeature...", "features");
			modelOperation.addParameter(modelParameter);
		}
		return modelOperation;
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
		if (modelClass.getParentClassName() != null)
			modelClass.addImportedClass(modelClass.getParentClassName());
	}
	
}
