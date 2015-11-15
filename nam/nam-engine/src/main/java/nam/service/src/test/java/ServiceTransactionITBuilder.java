package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Element;
import nam.model.Execution;
import nam.model.Invocation;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.bpel.BPELRuntimeCache;
import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class ServiceTransactionITBuilder extends AbstractBeanBuilder {

	private ServiceClassTestProvider serviceProxyTestProvider;
	
	
	public ServiceTransactionITBuilder(GenerationContext context) {
		serviceProxyTestProvider = new ServiceClassTestProvider(context);
		this.context = context;
	}

	public ModelClass build(Service service) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "ImplTest";
		String rootName = ServiceUtil.getRootName(service);
		//String serviceName = NameUtil.capName(interfaceName);
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

		List<Operation> operations = ServiceUtil.getOperations(service);
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

		modelClass.addImportedClass("static org.mockito.Mockito.mock");
		modelClass.addImportedClass("static org.mockito.Mockito.when");

		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.registry.ProcessLocator");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		
		//assuming just one execution for now
		List<Execution> executions = ServiceUtil.getExecutions(service);
		if (executions.size() > 0) {
			initializeImportedClasses(modelClass, service, executions.get(0));
		}
		
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Service service, Execution execution) {
		List<Invocation> actions = execution.getInvocations();
		Iterator<Invocation> iterator = actions.iterator();
		while (iterator.hasNext()) {
			Invocation action = (Invocation) iterator.next();
			initializeImportedClasses(modelClass, service, action);
		}
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service, Invocation action) {
		//String packageName = NameUtil.getPackageFromNamespace(service.getNamespace())+".process";
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String actionName = NameUtil.capName(action.getName());
		String className = ServiceLayerHelper.getProcessClassName(actionName);
		modelClass.addImportedClass(packageName+"."+className);
	}

	protected void initializeImplementedInterfaces(ModelClass modelClass, Service service) throws Exception {
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		String proxyClassName = NameUtil.capName(service.getName()) + "Impl";
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(modelClass.getPackageName());
		attribute.setClassName(proxyClassName);
		attribute.setName("fixture");
		modelClass.addInstanceAttribute(attribute);
		
		if (BPELRuntimeCache.INSTANCE.hasProcess(service)) {
			attribute = new ModelAttribute();
			attribute.setModifiers(Modifier.PRIVATE);
			attribute.setPackageName(modelClass.getPackageName());
			attribute.setClassName("ProcessLocator");
			attribute.setName("mockProcessLocator");
			modelClass.addInstanceAttribute(attribute);
		}
	
		//assuming just one execution for now
		List<Execution> executions = ServiceUtil.getExecutions(service);
		if (executions.size() > 0) {
			initializeInstanceFields(modelClass, service, executions.get(0));
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Service service, Execution execution) throws Exception {
		List<Invocation> actions = execution.getInvocations();
		Iterator<Invocation> iterator = actions.iterator();
		while (iterator.hasNext()) {
			Invocation action = (Invocation) iterator.next();
			initializeInstanceFields(modelClass, service, action);
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Service service, Invocation action) throws Exception {
		//String packageName = NameUtil.getPackageFromNamespace(service.getNamespace())+".process";
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String actionName = NameUtil.capName(action.getName());
		String className = ServiceLayerHelper.getProcessClassName(actionName);

		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("mock"+className);
		modelClass.addInstanceAttribute(attribute);
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceOperation(createSetupOperation());
		modelClass.addInstanceOperation(createTeardownOperation(service));
		modelClass.addInstanceOperation(createCreateFixtureOperation(service));

		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			ModelOperation modelOperation = createOperation(operation);
			modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
			modelOperation.addInitialSource(serviceProxyTestProvider.generate(service, operation, modelClass, modelOperation));
			modelClass.addInstanceOperation(modelOperation);
		}
	}

	protected ModelOperation createOperation(Operation operation) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("test"+NameUtil.capName(operation.getName()));
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

	protected ModelOperation createTeardownOperation(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("tearDown");
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("fixture = null;");
		if (BPELRuntimeCache.INSTANCE.hasProcess(service)) {
			buf.putLine2("mockProcessLocator = null;");
			buf.putLine2("BeanContext.clear();");
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createCreateFixtureOperation(Service service) {
		String fixtureClassName = NameUtil.capName(service.getName())+"Impl";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(fixtureClassName);
		modelOperation.setName("createFixture");
		Buf buf = new Buf();
		buf.putLine2("fixture = new "+fixtureClassName+"();");

		//assuming just one execution for now
		List<Execution> executions = ServiceUtil.getExecutions(service);
		if (executions.size() > 0) {
			buf.putLine2("mockProcessLocator = mock(ProcessLocator.class);");
			buf.putLine2("BeanContext.set(\"org.aries.processLocator\", mockProcessLocator);");

			Execution execution = executions.get(0);
			List<Invocation> actions = execution.getInvocations();
			Iterator<Invocation> iterator = actions.iterator();
			while (iterator.hasNext()) {
				Invocation action = (Invocation) iterator.next();
				String actionName = NameUtil.capName(action.getName());
				String className = ServiceLayerHelper.getProcessClassName(actionName);
				buf.putLine2("mock"+className+" = mock("+className+".class);");
			}
		}

//		Process process = context.getProcess();
//		if (process != null) {
//			String processClassName = NameUtil.capName(service.getName())+"Process";
//			buf.putLine2("mockProcessLocator = mock(ProcessLocator.class);");
//			buf.putLine2("mock"+processClassName+" = mock("+processClassName+".class);");
//			buf.putLine2("BeanContext.set(\"org.aries.processLocator\", mockProcessLocator);");
//		}
		
		buf.putLine2("return fixture;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

}
