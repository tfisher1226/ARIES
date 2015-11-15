package nam.client.src.test.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Element;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds a simple, standalone Java application to launch a client request 
 * via the Service Proxy (i.e. client-side) Implementation {@link ModelClass} 
 * object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ClientProxyTestBuilder extends AbstractBeanBuilder {

	private ServiceProxyTestProvider serviceProxyTestProvider;
	
	
	public ClientProxyTestBuilder(GenerationContext context) {
		serviceProxyTestProvider = new ServiceProxyTestProvider(context);
		this.context = context;
	}

	public ModelClass build(Service service) throws Exception {
		String namespace = service.getNamespace();
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		String serviceName = NameUtil.capName(service.getName());
		String className = serviceName+"ProxyTest";
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, NameUtil.uncapName(className)));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(serviceName);
		initializeClass(modelClass, service);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Service service) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service);
		initializeImportedClasses(modelClass, service);
		initializeImplementedInterfaces(modelClass, service);
		initializeInstanceFields(modelClass, service);
		initializeInstanceOperations(modelClass, service);
	}

	protected void initializeClassAnnotations(ModelClass modelClass, Service service) throws Exception {
		modelClass.addClassAnnotation(AnnotationUtil.createMockitoJUnitRunnerAnnotation());
		modelClass.addImportedClass("org.mockito.runners.MockitoJUnitRunner");
		modelClass.addImportedClass("org.junit.runner.RunWith");
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
		modelClass.addImportedClass("org.junit.After");
		//modelClass.addImportedClass("org.junit.AfterClass");
		modelClass.addImportedClass("org.junit.Before");
		//modelClass.addImportedClass("org.junit.BeforeClass");
		modelClass.addImportedClass("org.junit.Test");
		if (modelClass.getParentClassName() != null)
			modelClass.addImportedClass(modelClass.getParentClassName());
		initializeImportedClasses(modelClass, ServiceUtil.getOperations(service));
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
		modelClass.addImportedClass("static org.mockito.Mockito.mock");
		modelClass.addImportedClass("static org.mockito.Mockito.when");
		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.TransportType");
		modelClass.addImportedClass("org.aries.message.Message");
		modelClass.addImportedClass("org.aries.service.ServiceProxy");
		modelClass.addImportedClass("org.aries.registry.ServiceLocator");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
	}
	
	protected void initializeImplementedInterfaces(ModelClass modelClass, Service service) throws Exception {
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		String proxyClassName = NameUtil.capName(service.getName())+"Proxy";
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(modelClass.getPackageName());
		attribute.setClassName(proxyClassName);
		attribute.setName("fixture");
		modelClass.addInstanceAttribute(attribute);
		
		//private ServiceProxy mockServiceProxy;
		ModelAttribute mockServiceProxyAttribute = new ModelAttribute();
		mockServiceProxyAttribute.setModifiers(Modifier.PRIVATE);
		mockServiceProxyAttribute.setPackageName("org.aries.service");
		mockServiceProxyAttribute.setClassName("ServiceProxy");
		mockServiceProxyAttribute.setName("mockServiceProxy");
		modelClass.addInstanceAttribute(mockServiceProxyAttribute);
		
		//private ServiceLocator mockServiceLocator;
		ModelAttribute mockServiceLocatorAttribute = new ModelAttribute();
		mockServiceLocatorAttribute.setModifiers(Modifier.PRIVATE);
		mockServiceLocatorAttribute.setPackageName("org.aries.service");
		mockServiceLocatorAttribute.setClassName("ServiceLocator");
		mockServiceLocatorAttribute.setName("mockServiceLocator");
		modelClass.addInstanceAttribute(mockServiceLocatorAttribute);
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceOperation(createSetupOperation());
		modelClass.addInstanceOperation(createTeardownOperation());
		modelClass.addInstanceOperation(createCreateFixtureOperation(service));

		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			ModelOperation modelOperation = createOperation(operation);
			modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
			modelOperation.addInitialSource(serviceProxyTestProvider.generate(modelClass, modelOperation, service, operation));
			modelClass.addInstanceOperation(modelOperation);
		}
	}

	protected ModelOperation createOperation(Operation operation) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName(operation.getName()+"Test");
		modelOperation.addException("Exception");
		return modelOperation; 
	}

	protected ModelOperation createSetupOperation() {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("setUp");
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("fixture = createFixture();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createTeardownOperation() {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("tearDown");
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("fixture = null;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createCreateFixtureOperation(Service service) {
		String fixtureClassName = NameUtil.capName(service.getName())+"Proxy";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(fixtureClassName);
		modelOperation.setName("createFixture");
		Buf buf = new Buf();
		buf.putLine2("mockServiceProxy = mock(ServiceProxy.class);");
		buf.putLine2("mockServiceLocator = mock(ServiceLocator.class);");
		buf.putLine2("BeanContext.set(\"org.aries.serviceLocator\", mockServiceLocator);");
		buf.putLine2("fixture = new "+fixtureClassName+"();");
		buf.putLine2("return fixture;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

}
