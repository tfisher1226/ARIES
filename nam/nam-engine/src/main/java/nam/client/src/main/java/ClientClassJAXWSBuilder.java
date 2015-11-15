package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Element;
import nam.model.Module;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Project;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.ModuleUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds a JAXWS Service Client (i.e. client-side) Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ClientClassJAXWSBuilder extends AbstractBeanBuilder {

	
	public ClientClassJAXWSBuilder(GenerationContext context) {
		this.context = context;
	}

	public ModelClass build(Service service) throws Exception {
		String namespace = ClientLayerHelper.getNamespace(service);
		String interfaceName = ClientLayerHelper.getClientInterfaceName(service);
		String packageName = ClientLayerHelper.getClientPackageName(service);
		String className = ClientLayerHelper.getClientInterfaceName(service) + "ProxyForJAXWS";
		//String className = ClientLayerHelper.getClientInterfaceName(service) + "ClientHTTPImpl";
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
		modelClass.setParentClassName("JAXWSProxy");
		modelClass.addImportedClass("org.aries.tx.service.jaxws.JAXWSProxy");
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
		modelClass.addInstanceAttribute(createServiceAttribute(service));
		modelClass.addInstanceAttribute(CodeUtil.createAttribute_Mutex());
	}

	public ModelAttribute createServiceAttribute(Service service) {
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
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.addParameter(CodeUtil.createParameter_HostName());
		modelConstructor.addParameter(CodeUtil.createParameter_PortNumber());
		
		Buf buf = new Buf();
		buf.putLine2("super(host, port);");
		modelConstructor.addInitialSource(buf.get());
		//modelConstructor.addException("Exception");
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		createModelOperation_GetServiceUri(modelClass, service);
		createModelOperation_GetDelegate(modelClass, service);
		createModelOperation_GetPort(modelClass, service);
		createModelOperation_CreateService(modelClass, service);
		//createModelOperation_ServiceCalls(modelClass, service);
	}

	protected void createModelOperation_ServiceCalls(ModelClass modelClass, Service service) throws Exception {
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			createModelOperation_ServiceCall(modelClass, service, operation);
		}
	}
	
	protected void createModelOperation_ServiceCall(ModelClass modelClass, Service service, Operation operation) throws Exception {
		ModelOperation modelOperation = CodeUtil.createOperation(operation);
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		addImportedClasses(modelClass, operation);
		
		Buf methodCall = new Buf();
		methodCall.put(operation.getName()+"(");
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			if (i > 0)
				methodCall.put(", ");
			methodCall.put(parameter.getName());
		}
		methodCall.put(")");
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	"+service.getInterfaceName()+" port = getPort();");
		if (!operation.getResults().isEmpty()) {
			buf.putLine2("	return port."+methodCall.get()+";");
		} else {
			buf.putLine2("	port."+methodCall.get()+";");
		}
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	log.error(e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
	}

	protected void createModelOperation_GetServiceUri(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getServiceURI");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("java.lang.String");
		modelOperation.getParameters().add(CodeUtil.createParameter_HostName());
		modelOperation.getParameters().add(CodeUtil.createParameter_PortNumber());
		modelOperation.getExceptions().add("MalformedURLException");
		modelClass.addImportedClass("java.net.MalformedURLException");
		
		Buf buf = new Buf();
		List<Project> projects = context.getProjectList();
		Module serviceModule = ModuleUtil.getServiceModule(projects, service);
		String moduleName = serviceModule.getName();
		String serviceName = service.getName() + "Service";
		String context = service.getNamespace().replace("http://", "");
		String portType = NameUtil.uncapName(service.getName());
		buf.putLine2("return \"http://\"+host+\":\"+port+\"/"+moduleName+"/"+serviceName+"/"+portType+"?wsdl\";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createModelOperation_GetDelegate(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getDelegate");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(service.getInterfaceName());

		Buf buf = new Buf();
		buf.putLine2("return getPort();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createModelOperation_GetPort(ModelClass modelClass, Service service) throws Exception {
		String className = ClientLayerHelper.getClientInterfaceName(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getPort");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(className);
		
		Buf buf = new Buf();
		buf.putLine2("synchronized (mutex) {");
		buf.putLine2("	if (service == null)");
		buf.putLine2("		service = createService();");
		buf.putLine2("	"+className+" port = service.getPort();");
		buf.putLine2("	initializePort(port);");
		buf.putLine2("	return port;");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		//modelClass.addImportedClass(packageName+"."+className);
	}

	protected void createModelOperation_CreateService(ModelClass modelClass, Service service) throws Exception {
		String className = ClientLayerHelper.getClientInterfaceName(service) + "Service";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createService");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(className);
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	URL wsdlLocation = new URL(getServiceURI(host, port));");
		buf.putLine2("	"+className+" service = new "+className+"(wsdlLocation);");
		buf.putLine2("	return service;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	//log.error(e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		modelClass.addImportedClass("java.net.URL");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
		//modelClass.addImportedClass(context.getPackageName(service) + "." + service.getInterfaceName());
		//addImportedClassesForFaults(modelClass, ServiceUtil.getFaults(service));
		//initializeImportedClasses(modelClass, ServiceUtil.getOperations(service));
	}

//	protected void initializeImportedClasses(ModelClass modelClass, List<Operation> operations) throws Exception {
//		Iterator<Operation> iterator = operations.iterator();
//		while (iterator.hasNext()) {
//			Operation operation = iterator.next();
//			initializeImportedClasses(modelClass, operation);
//		}
//	}

//	protected void initializeImportedClasses(ModelClass modelClass, Operation operation) throws Exception {
//		List<Parameter> parameters = operation.getParameters();
//		addImportedClasses(modelClass, parameters);
//		if (operation.getResult() != null) {
//			Result result = operation.getResult();
//			addImportedClasses(modelClass, result);
//		}
//	}

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
