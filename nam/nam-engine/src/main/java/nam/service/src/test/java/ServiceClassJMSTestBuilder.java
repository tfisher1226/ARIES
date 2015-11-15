package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Element;
import nam.model.Execution;
import nam.model.Invocation;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Result;
import nam.model.Service;
import nam.model.src.main.java.DummyValueFactory;
import nam.model.util.ModuleUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.ClassUtil;
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


public class ServiceClassJMSTestBuilder extends AbstractBeanBuilder {

	private ServiceClassTestProvider serviceClassTestProvider;
	
	private DummyValueFactory dummyValueFactory;
	
	private boolean expectException;
	
	
	public ServiceClassJMSTestBuilder(GenerationContext context) {
		serviceClassTestProvider = new ServiceClassTestProvider(context);
		dummyValueFactory = new DummyValueFactory(context);
		this.context = context;
	}

	public ModelClass build(Service service) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		//String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "JMSListenerTest";
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

		modelClass.addImportedClass("static org.mockito.Mockito.mock");
		modelClass.addImportedClass("static org.mockito.Mockito.when");
		modelClass.addImportedClass("static org.mockito.Mockito.verify");

		modelClass.addImportedClass("java.util.concurrent.Executors");
		modelClass.addImportedClass("javax.management.MBeanServer");
		modelClass.addImportedClass("javax.management.MBeanServerFactory");

		modelClass.addImportedClass("org.aries.Assert");
		if (ServiceUtil.isStateful(service))
			modelClass.addImportedClass("org.aries.registry.ProcessLocator");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.validate.util.CheckpointManager");
		modelClass.addImportedClass("org.aries.launcher.TestUtil");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.util.MBeanUtil");
		modelClass.addImportedClass("common.tx.handler.MessageUtil");

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

		//assuming just one execution for now
		List<Execution> executions = ServiceUtil.getExecutions(service);
		if (executions.size() > 0) {
			initializeImportedClasses(modelClass, service, executions.get(0));
		}
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Service service, Execution execution) {
		modelClass.addImportedClass(ServiceLayerHelper.getServiceQualifiedName(service));
		modelClass.addImportedClass(ServiceLayerHelper.getProcessQualifiedName(service.getProcess()));
		List<Invocation> actions = execution.getInvocations();
		Iterator<Invocation> iterator = actions.iterator();
		while (iterator.hasNext()) {
			Invocation action = (Invocation) iterator.next();
			initializeImportedClasses(modelClass, service, action);
		}
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service, Invocation action) {
	}

	protected void initializeImplementedInterfaces(ModelClass modelClass, Service service) throws Exception {
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		createFixtureField(modelClass, service);
		String serviceType = service.getElement();
		if (serviceType != null) {
			createMockActionField(modelClass, service);
			createMockElementField(modelClass, service);
		}
		if (ServiceUtil.isStateful(service)) {
			createMockWebServiceContextField(modelClass, service);
			createMockMessageContextField(modelClass, service);
			createMockProcessLocatorField(modelClass, service);
			createMockProxyLocatorField(modelClass, service);
			createMockTaskExecutorField(modelClass, service);
			createMockTaskExecutorFactoryField(modelClass, service);
			createMockServiceTransactorField(modelClass, service);
		}
	}

	protected void createFixtureField(ModelClass modelClass, Service service) throws Exception {
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("fixture");
		modelClass.addInstanceAttribute(attribute);
		modelClass.addImportedClass(packageName+"."+className);

		//assuming just one execution for now
		List<Execution> executions = ServiceUtil.getExecutions(service);
		if (executions.size() > 0) {
			initializeInstanceFields(modelClass, service, executions.get(0));
		}
	}
	
	protected void createMockActionField(ModelClass modelClass, Service service) {
		//String elementName = TypeUtil.getLocalPart(service.getElement());
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "Action";
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("mockAction");
		modelClass.addImportedClass(packageName+"."+className);
		modelClass.addInstanceAttribute(attribute);
	}
	
	protected void createMockElementField(ModelClass modelClass, Service service) {
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service);

		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("mock"+className);
		modelClass.addImportedClass(packageName+"."+className);
		modelClass.addInstanceAttribute(attribute);
	}

	protected void createMockWebServiceContextField(ModelClass modelClass, Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("javax.xml.ws");
		attribute.setClassName("WebServiceContext");
		attribute.setName("mockWebServiceContext");
		modelClass.addImportedClass("javax.xml.ws.WebServiceContext");
		modelClass.addInstanceAttribute(attribute);
	}

	protected void createMockMessageContextField(ModelClass modelClass, Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("javax.xml.ws.handler");
		attribute.setClassName("MessageContext");
		attribute.setName("mockMessageContext");
		modelClass.addImportedClass("javax.xml.ws.handler.MessageContext");
		modelClass.addInstanceAttribute(attribute);
	}

	protected void createMockProcessLocatorField(ModelClass modelClass, Service service) {
		Process process = service.getProcess();
		if (process != null) {
			ModelAttribute attribute = new ModelAttribute();
			attribute.setModifiers(Modifier.PRIVATE);
			attribute.setPackageName("org.aries.registry");
			attribute.setClassName("ProcessLocator");
			attribute.setName("mockProcessLocator");
			modelClass.addImportedClass("org.aries.registry.ProcessLocator");
			modelClass.addInstanceAttribute(attribute);
		}
	}

	protected void createMockProxyLocatorField(ModelClass modelClass, Service service) {
		//TODO only if client links are used then provide this attribute 
		//if (process != null) {
			ModelAttribute attribute = new ModelAttribute();
			attribute.setModifiers(Modifier.PRIVATE);
			attribute.setPackageName("org.aries.process");
			attribute.setClassName("ProxyLocator");
			attribute.setName("mockProxyLocator");
			modelClass.addImportedClass("org.aries.process.ProxyLocator");
			modelClass.addInstanceAttribute(attribute);
		//}
	}

	protected void createMockTaskExecutorField(ModelClass modelClass, Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("org.aries.task");
		attribute.setClassName("TaskExecutor");
		attribute.setName("mockTaskExecutor");
		modelClass.addImportedClass("org.aries.task.TaskExecutor");
		modelClass.addInstanceAttribute(attribute);
	}

	protected void createMockTaskExecutorFactoryField(ModelClass modelClass, Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("org.aries.task");
		attribute.setClassName("TaskExecutorFactory");
		attribute.setName("mockTaskExecutorFactory");
		modelClass.addImportedClass("org.aries.task.TaskExecutorFactory");
		modelClass.addInstanceAttribute(attribute);
	}

	protected void createMockServiceTransactorField(ModelClass modelClass, Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("common.tx");
		attribute.setClassName("Transactional");
		attribute.setName("mockTransactional");
		modelClass.addImportedClass("org.aries.tx.Transactional");
		modelClass.addInstanceAttribute(attribute);
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
		String packageName = context.getPackageName(service);
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
		modelClass.addInstanceOperation(createSetupOperation(service));
		modelClass.addInstanceOperation(createTeardownOperation(service));
		modelClass.addInstanceOperation(createCreateFixtureOperation(service));

		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			initializeInstanceOperations(modelClass, service, operation);
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

	protected ModelOperation createSetupOperation(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("setUp");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		String serviceName = NameUtil.capName(service.getName());
		String serviceType = service.getElement();

		if (serviceType != null) {
			String elementClassName = TypeUtil.getClassName(serviceType);
			String actionClassName = service.getInterfaceName() + "Action";
			//buf.putLine2("mock"+elementClassName+" = mock("+elementClassName+".class);");
			buf.putLine2("mockAction = mock("+actionClassName+".class);");
		}
		
		if (ServiceUtil.isStateful(service)) {
			buf.putLine2("mockWebServiceContext = mock(WebServiceContext.class);");
			buf.putLine2("mockWebServiceContext = mock(WebServiceContext.class);");
			buf.putLine2("mockMessageContext = mock(MessageContext.class);");
			if (service.getProcess() != null)
				buf.putLine2("mockProcessLocator = mock(ProcessLocator.class);");
			buf.putLine2("mockProxyLocator = mock(ProxyLocator.class);");
			buf.putLine2("mockTaskExecutor = mock(TaskExecutor.class);");
			buf.putLine2("mockTaskExecutorFactory = mock(TaskExecutorFactory.class);");
			buf.putLine2("mockTransactional = mock("+serviceName+"Transactor.class);");
		}
		
		if (serviceType != null) {
			String actionPackageName = context.getPackageName(service);
			String actionClassName = service.getInterfaceName() + "Action";
			String actionQualifiedName = actionPackageName + "." + actionClassName;
			String actionName = NameUtil.uncapName(actionClassName);
			String elementPackageName = TypeUtil.getPackageName(serviceType);
			String elementClassName = TypeUtil.getClassName(serviceType);
			String elementQualifiedName = elementPackageName+"."+elementClassName;
			
			String contextName = NameUtil.uncapName(service.getName()) + "." + NameUtil.uncapName(actionClassName);
			String contextPrefix = NameUtil.getQualifiedContextNamePrefix(actionQualifiedName, 2);
			buf.putLine2("BeanContext.set(\""+contextPrefix+"."+contextName+"\", mockAction);");
		}
		
		if (ServiceUtil.isStateful(service)) {
			buf.putLine2("BeanContext.set(\"org.aries.proxyLocator\", mockProxyLocator);");
			if (service.getProcess() != null)
				buf.putLine2("BeanContext.set(\"org.aries.processLocator\", mockProcessLocator);");
			buf.putLine2("BeanContext.set(\"org.aries.executorFactory\", mockTaskExecutorFactory);");
			buf.putLine2("BeanContext.set(\"org.aries.executorService\", Executors.newFixedThreadPool(1));");
		}
		
		String fileNamePrefix = ModuleUtil.getFileNamePrefix(context.getModule());
		String eventsFileName = fileNamePrefix + "-checks.xml";
		buf.putLine2("CheckpointManager.addConfiguration(\""+eventsFileName+"\");");
		buf.putLine2("MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();"); 
		buf.putLine2("MBeanUtil.setMBeanServer(mbeanServer);");
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
		buf.putLine2("MBeanUtil.unregisterMBeans();");
		String serviceType = service.getElement();
		if (serviceType != null) {
			//String elementClassName = TypeUtil.getClassName(serviceType);
			//buf.putLine2("mock"+elementClassName+" = null;");
			buf.putLine2("mockAction = null;");
		}
		if (ServiceUtil.isStateful(service)) {
			buf.putLine2("mockWebServiceContext = null;");
			buf.putLine2("mockMessageContext = null;");
			if (service.getProcess() != null)
				buf.putLine2("mockProcessLocator = null;");
			buf.putLine2("mockProxyLocator = null;");
			buf.putLine2("mockTransactional = null;");
		}
		buf.putLine2("BeanContext.clear();");
		buf.putLine2("fixture = null;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createCreateFixtureOperation(Service service) {
		String fixtureInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String fixtureClassName = fixtureInterfaceName + "HTTPListener";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(fixtureInterfaceName);
		modelOperation.setName("createFixture");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("fixture = new "+fixtureClassName+"();");
		if (ServiceUtil.isStateful(service)) {
			buf.putLine2("TestUtil.setFieldValue(fixture, \"webServiceContext\", mockWebServiceContext);");
		}

		//assuming just one execution for now
		List<Execution> executions = ServiceUtil.getExecutions(service);
		if (executions.size() > 0) {
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
	
	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Operation operation) throws Exception {
		createOperation_RunTest_Success(modelClass, service, operation);
		if (operation.getParameters().size() > 0) {
			Parameter parameter = operation.getParameters().get(0);
			String className = TypeUtil.getClassName(parameter.getType());
			if (!ClassUtil.isJavaLangType(className)) {
				createOperation_RunTest_NullRequest(modelClass, service, operation);
				createOperation_RunTest_EmptyRequest(modelClass, service, operation);
			}
		}
		createOperation_RunTest_NullCorrelationId(modelClass, service, operation);
		createOperation_RunTest_EmptyCorrelationId(modelClass, service, operation);
		createOperation_RunTest(modelClass, service, operation);
	}

	protected void createOperation_RunTest_Success(ModelClass modelClass, Service service, Operation operation) throws Exception {
		createOperation_RunTest(modelClass, service, operation, "_Success", "", "\"dummyCorrelationId\"", null);
	}
	
	protected void createOperation_RunTest_NullRequest(ModelClass modelClass, Service service, Operation operation) throws Exception {
		createOperation_RunTest(modelClass, service, operation, "_NullRequest", null, "\"dummyCorrelationId\"", "IllegalArgumentException.class");
	}

	protected void createOperation_RunTest_EmptyRequest(ModelClass modelClass, Service service, Operation operation) throws Exception {
		createOperation_RunTest(modelClass, service, operation, "_EmptyRequest", "Empty", "\"dummyCorrelationId\"", "IllegalArgumentException.class");
	}

	protected void createOperation_RunTest_NullCorrelationId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		createOperation_RunTest(modelClass, service, operation, "_NullCorrelationId", "", null, null);
	}

	protected void createOperation_RunTest_EmptyCorrelationId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		createOperation_RunTest(modelClass, service, operation, "_EmptyCorrelationId", "", "\"\"", null);
	}

	protected void createOperation_RunTest(ModelClass modelClass, Service service, Operation operation, String nameExtension, String fixtureType, String correlationId, String expectedException) throws Exception {
		List<String> parameterList = createParameterList(modelClass, service, operation, fixtureType, correlationId);
		createOperation_RunTest(modelClass, service, operation, parameterList, nameExtension, expectedException);
	}

	protected List<String> createParameterList(ModelClass modelClass, Service service, Operation operation, String fixtureType, String correlationId) {
		List<String> parameterList = new ArrayList<String>();
		parameterList.add(correlationId);
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			if (fixtureType == null) {
				parameterList.add(null);
			} else {
				String parameterName = NameUtil.capName(parameter.getName());
//				if (parameter.getName().equalsIgnoreCase("message") && service.getInterfaceName().equals("ShipBooks"))
//					parameterName = TypeUtil.getClassName(parameter.getType();
				String packageName = TypeUtil.getPackageName(parameter.getType());
				
				if (packageName.equals("java.lang")) {
					boolean isCollection = !parameter.getConstruct().equals("item");
					String dummyValue = dummyValueFactory.getDummyValue(parameterName, parameter.getType(), false, isCollection, false);
					parameterList.add(dummyValue);
					
				} else {
					String fixtureName = FixtureUtil.getFixtureNameFromType(parameter.getType());
					if (parameterName.equalsIgnoreCase("message"))
						parameterName = TypeUtil.getClassName(parameter.getType());
					parameterList.add(fixtureName + ".create" + fixtureType + parameterName + "()");
					modelClass.addImportedClass(packageName + "." + fixtureName);
				}
			}
		}
		return parameterList;
	}

	protected void createOperation_RunTest(ModelClass modelClass, Service service, Operation operation, List<String> parameterList, String nameExtension, String expectedException) throws Exception {
		String operationNameCapped = NameUtil.capName(operation.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("test" + operationNameCapped + nameExtension);
		if (expectedException != null)
			modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation(expectedException));
		else modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addException("Exception");
		
		StringBuffer buf = new StringBuffer();
		buf.append("runTest"+operationNameCapped+"("+getParameterString(parameterList)+");");
		buf.append("\n");

		String sourceCode = CodeUtil.createMethodSource(buf.toString());
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String fixtureName = FixtureUtil.getFixtureNameFromPackageName(packageName);
		String serviceNameCapped = NameUtil.capName(service.getName());
		String operationNameCapped = NameUtil.capName(operation.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("runTest" + operationNameCapped);
		modelOperation.addException("Exception");
		modelOperation.addParameter(CodeUtil.createParameter_CorrelationId());

		List<String> sourceLines = new ArrayList<String>();
		List<String> parameterList = new ArrayList<String>(); 
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			ModelParameter modelParameter = CodeUtil.createParameter(parameters, parameter);
			modelOperation.addParameter(modelParameter);
			parameterList.add(modelParameter.getName());
		}

		String serviceType = service.getElement();
		if (!ServiceUtil.isStateful(service)) {
			String elementClassName = TypeUtil.getClassName(serviceType);
			String elementName = NameUtil.uncapName(elementClassName);
			String mockName = "mock" + elementClassName;
			//String actionClassName = service.getInterfaceName() + "Action";
			String operationInvocation = operation.getName()+"("+getParameterString(parameterList)+")";
			
			List<Result> results = operation.getResults();
			Result result = null;
			if (results.isEmpty()) {
				sourceLines.add("//$$$ Start fixture execution");
				sourceLines.add("fixture."+operationInvocation+";");

			} else {
				result = results.get(0);
				String resultType = result.getType();
				String resultClassName = TypeUtil.getClassName(resultType);
				String resultName = NameUtil.capName(result.getName());
				
				String construct = result.getConstruct();
				if (construct.equals("item")) {
					if (ClassUtil.isJavaDefaultType(resultClassName)) {
						String dummyValue = dummyValueFactory.getDummyValue(resultName, resultType, false, false, false);
						sourceLines.add(resultClassName+" expected"+resultName+" = "+dummyValue+";");
					} else {
						sourceLines.add(resultClassName+" expected"+resultName+" = "+fixtureName+".create"+resultClassName+"();");
					}
					sourceLines.add("when(mockAction."+operationInvocation+").thenReturn(expected"+resultName+");");
					sourceLines.add("//$$$ Start fixture execution");
					sourceLines.add(resultClassName+" actual"+resultName+" = fixture."+operationInvocation+";");
				
				} else if (construct.equals("list")) {
					sourceLines.add("List<"+resultClassName+"> expected"+resultName+" = new ArrayList<"+resultClassName+">();");
					sourceLines.add("expected"+resultName+".add("+fixtureName+".create"+resultClassName+"());");
					sourceLines.add("when(mockAction."+operationInvocation+").thenReturn(expected"+resultName+");");
					sourceLines.add("//$$$ Start fixture execution");
					sourceLines.add("List<"+resultClassName+"> actual"+resultName+" = fixture."+operationInvocation+";");
					modelClass.addImportedClass("java.util.List");
					modelClass.addImportedClass("java.util.ArrayList");
				
				} else if (construct.equals("set")) {
					sourceLines.add("Set<"+resultClassName+"> expected"+resultName+" = new HashSet<"+resultClassName+">();");
					sourceLines.add("expected"+resultName+".add("+mockName+");");
					sourceLines.add("when(mockAction."+operationInvocation+").thenReturn(expected"+resultName+");");
					sourceLines.add("//$$$ Start fixture execution");
					sourceLines.add("Set<"+resultClassName+"> "+resultName+" = fixture."+operationInvocation+";");
					modelClass.addImportedClass("java.util.Set");
					modelClass.addImportedClass("java.util.HashSet");

				} else if (construct.equals("map")) {
					//TODO
					//TODO
					sourceLines.add("//$$$ Start fixture execution");
					//String resultKeyClassName = TypeUtil.getClassName(result.getKey());
					sourceLines.add("Map<Object, "+resultClassName+"> "+resultName+" = fixture."+operationInvocation+";");
					modelClass.addImportedClass("java.util.Map");
					modelClass.addImportedClass("java.util.HashMap");
				}
			}
			
			sourceLines.add("//$$$ Finish fixture execution");
			sourceLines.add("verify(mockAction)."+operation.getName()+"("+getParameterString(parameterList)+");");
			
			if (result != null) {
				String resultName = NameUtil.capName(result.getName());
				sourceLines.add("Assert.isTrue(actual"+resultName+".equals(expected"+resultName+"));");
			}
		}

		//TODO
		if (ServiceUtil.isStateful(service)) {
			if (service.getTransacted() != null)
				sourceLines.add("BeanContext.set(\""+modelClass.getPackageName()+"."+serviceNameCapped+"Transactor\", correlationId, mockTransactional);");
			sourceLines.add("when(mockWebServiceContext.getMessageContext()).thenReturn(mockMessageContext);");
			sourceLines.add("when(mockMessageContext.get(MessageUtil.ARIES_PROPERTY_CORRELATION_ID)).thenReturn(correlationId);");
			if (service.getTransacted() != null)
				sourceLines.add("when(mockTaskExecutorFactory.createExecutor(mockTransactional)).thenReturn(mockTaskExecutor);");
			else sourceLines.add("when(mockTaskExecutorFactory.createExecutor(fixture)).thenReturn(mockTaskExecutor);");
			
			sourceLines.add("//$$$ Start fixture execution");
			StringBuffer buf = new StringBuffer();
			buf.append("fixture."+operation.getName()+"("+getParameterString(parameterList)+");");
			sourceLines.add(buf.toString());
			sourceLines.add("//$$$ Finish fixture execution");
			
			if (service.getTransacted() != null) {
				sourceLines.add("verify(mockTaskExecutorFactory).createExecutor(mockTransactional);");
				sourceLines.add("verify(mockTaskExecutor).setMethodName(\""+operation.getName()+"\");");
			} else {
				sourceLines.add("verify(mockTaskExecutorFactory).createExecutor(fixture);");
				sourceLines.add("verify(mockTaskExecutor).setMethodName(\""+operation.getName()+"\");");
			}
			Iterator<String> parameterIterator = parameterList.iterator();
			parameterIterator = parameterList.iterator();
			while (parameterIterator.hasNext()) {
				String parameterName = parameterIterator.next();
				sourceLines.add("verify(mockTaskExecutor).addParameter("+parameterName+");");
			}
			sourceLines.add("verify(mockTaskExecutor).execute();");
		}

		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected String getParameterString(List<String> parameterList) {
		StringBuffer buf = new StringBuffer();
		Iterator<String> parameterIterator = parameterList.iterator();
		for (int i=0; parameterIterator.hasNext(); i++) {
			String parameterName = parameterIterator.next();
			if (i > 0)
				buf.append(", ");
			buf.append(parameterName);
		}
		return buf.toString();
	}

}
