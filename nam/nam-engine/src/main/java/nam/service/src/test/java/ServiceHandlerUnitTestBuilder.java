package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Attribute;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Element;
import nam.model.Execution;
import nam.model.Fault;
import nam.model.Field;
import nam.model.Invocation;
import nam.model.ModelLayerHelper;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Reference;
import nam.model.Result;
import nam.model.Service;
import nam.model.src.main.java.DummyValueFactory;
import nam.model.util.CacheUtil;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ProjectUtil;
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


public class ServiceHandlerUnitTestBuilder extends AbstractBeanBuilder {

	private ServiceClassTestProvider serviceProxyTestProvider;
	
	private DummyValueFactory dummyValueFactory;
	
	private boolean expectException;
	
	
	public ServiceHandlerUnitTestBuilder(GenerationContext context) {
		super(context);
		serviceProxyTestProvider = new ServiceClassTestProvider(context);
		dummyValueFactory = new DummyValueFactory(context);
	}

	public ModelClass build(Service service) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		//String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "HandlerUnitTest";
		String rootName = ServiceUtil.getRootName(service);
		//String serviceName = NameUtil.capName(interfaceName);
		String beanName = NameUtil.capName(className);

		Operation defaultOperation = ServiceUtil.getDefaultOperation(service);

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
		if (defaultOperation != null) {
			Parameter parameter = OperationUtil.getParameter(defaultOperation);
			if (parameter != null) {
				String parameterPackageName = TypeUtil.getPackageName(parameter.getType());
				String parameterClassName = TypeUtil.getClassName(parameter.getType());
				modelClass.addImportedClass(parameterPackageName+"."+parameterClassName);
			}
		}
		modelClass.setParentClassName("org.aries.tx.AbstractHandlerUnitTest");
		modelClass.addImportedClass("org.aries.tx.AbstractHandlerUnitTest");
		initializeClass(modelClass, service);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Service service) throws Exception {
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

		modelClass.addStaticImportedClass("org.mockito.Mockito.mock");
		//modelClass.addStaticImportedClass("org.mockito.Mockito.when");
		modelClass.addStaticImportedClass("org.mockito.Mockito.verify");
		//modelClass.addStaticImportedClass("org.mockito.Mockito.verifyNoMoreInteractions");

		//modelClass.addImportedClass("java.util.concurrent.Executors");
		//modelClass.addImportedClass("javax.management.MBeanServer");
		//modelClass.addImportedClass("javax.management.MBeanServerFactory");

		//modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.validate.util.CheckpointManager");
		//modelClass.addImportedClass("org.aries.launcher.TestUtil");
		//if (service.getProcess() != null)
		//	modelClass.addImportedClass("org.aries.registry.ProcessLocator");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		//modelClass.addImportedClass("org.aries.util.MBeanUtil");
		//modelClass.addImportedClass("common.tx.handler.MessageUtil");
		modelClass.addImportedClass("org.aries.util.FieldUtil");
		modelClass.addImportedClass("org.aries.tx.Transactional");

		Operation defaultOperation = ServiceUtil.getDefaultOperation(service);
		if (defaultOperation != null) {
			initializeImportedClasses(modelClass, service, defaultOperation);
			
		} else {
			List<Operation> operations = ServiceUtil.getOperations(service);
			Iterator<Operation> iterator = operations.iterator();
			while (iterator.hasNext()) {
				Operation operation = iterator.next();
				//TODO initializeImportedClasses(modelClass, service, operation);
			}
		}
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service, Operation operation) throws Exception {
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			Parameter parameter = parameterIterator.next();
			Element element = context.findElement(parameter);
			if (element == null) {
				addImportedClass(modelClass, parameter.getType());
			} else {
				String type = element.getType();
				addImportedClass(modelClass, type);
				String namespace = TypeUtil.getNamespace(type);
				//String fixturePackageName = ModelLayerHelper.getFixturePackageName(type);
				//String fixtureClassName = ModelLayerHelper.getFixtureClassName(type);
				String fixturePackageName = ModelLayerHelper.getModelFixturePackageName(namespace);
				String fixtureClassName = ModelLayerHelper.getModelFixtureClassName(namespace);
				modelClass.addImportedClass(fixturePackageName + "." + fixtureClassName);
			}
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
		
		Fault serviceFault = getFault(service);
		if (serviceFault != null) {
			String faultPackageName = TypeUtil.getPackageName(serviceFault.getType());
			String faultClassName = TypeUtil.getClassName(serviceFault.getType());
			modelClass.addImportedClass(faultPackageName + "." + faultClassName);
		}

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
		if (ServiceUtil.isStateful(service)) {
			String packageName = ServiceLayerHelper.getProcessPackageName(service.getProcess());
			String className = ServiceLayerHelper.getProcessClassName(service.getProcess());
			modelClass.addImportedClass(packageName+"."+className);
		}
	}

	protected void initializeImplementedInterfaces(ModelClass modelClass, Service service) throws Exception {
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		createFixtureField(modelClass, service);
		if (!ServiceUtil.isStateful(service) && service.getElement() != null) {
			createMockRepositoryField(modelClass, service);
		}
		if (ServiceUtil.isStateful(service)) {
			//createMockProcessLocatorField(modelClass, service);
			createMockContextField(modelClass, service);
			createMockProcessField(modelClass, service);
			//createMockStateManagerFields(modelClass, service);
		}
	}

	protected void createFixtureField(ModelClass modelClass, Service service) throws Exception {
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "HandlerImpl";
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(modelClass.getPackageName() + ".util");
		attribute.setClassName(className);
		attribute.setName("fixture");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelClass.addInstanceAttribute(attribute);
	}
	
	protected void createMockRepositoryField(ModelClass modelClass, Service service) {
		String serviceType = service.getElement();
		String elementName = TypeUtil.getLocalPart(serviceType);
		String className = NameUtil.capName(elementName) + "Repository";
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		modelClass.addImportedClass(packageName+"."+className);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("mock" + className);
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

	protected void createMockContextField(ModelClass modelClass, Service service) {
		Process process = service.getProcess();
		String contextClassName = ServiceLayerHelper.getProcessContextClassName(process);
		String processPackageName = ServiceLayerHelper.getProcessPackageName(process);
		modelClass.addImportedClass(processPackageName+"."+contextClassName);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(processPackageName);
		attribute.setClassName(contextClassName);
		attribute.setName("mock" + contextClassName);
		attribute.setGenerateGetter(false);;
		attribute.setGenerateSetter(false);;
		modelClass.addInstanceAttribute(attribute);
	}
	
	protected void createMockProcessField(ModelClass modelClass, Service service) {
		Process process = service.getProcess();
		String processClassName = ServiceLayerHelper.getProcessClassName(process);
		String processPackageName = ServiceLayerHelper.getProcessPackageName(process);
		modelClass.addImportedClass(processPackageName+"."+processClassName);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(processPackageName);
		attribute.setClassName(processClassName);
		attribute.setName("mock" + processClassName);
		attribute.setGenerateGetter(false);;
		attribute.setGenerateSetter(false);;
		modelClass.addInstanceAttribute(attribute);
	}

	protected void createMockStateManagerFields(ModelClass modelClass, Service service) {
		Process process = service.getProcess();
		List<Cache> cacheUnits = process.getCacheUnits();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			String packageName = CacheUtil.getPackageName(cache);
			String className = CacheUtil.getClassName(cache) + "Manager";
			ModelAttribute attribute = new ModelAttribute();
			attribute.setModifiers(Modifier.PRIVATE);
			attribute.setPackageName(packageName);
			attribute.setClassName(className);
			attribute.setName("mock" + className);
			modelClass.addImportedClass(packageName+"."+className);
			modelClass.addInstanceAttribute(attribute);
		}
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceOperation(createOperation_GetName(service));
		modelClass.addInstanceOperation(createOperation_GetDomain(service));
		modelClass.addInstanceOperation(createOperation_GetFixture(service));
		Process process = service.getProcess();
		if (process != null) {
			modelClass.addInstanceOperation(createOperation_GetMockServiceContext(service));
			modelClass.addInstanceOperation(createOperation_GetMockServiceProcess(service));
		}
		modelClass.addInstanceOperation(createOperation_Setup(service));
		modelClass.addInstanceOperation(createOperation_Teardown(service));
		
		if (process != null)
			modelClass.addInstanceOperation(createCreateFixtureOperation(process, service));
		else modelClass.addInstanceOperation(createCreateFixtureOperation(service));

		Operation defaultOperation = ServiceUtil.getDefaultOperation(service);
		if (defaultOperation != null) {
			initializeInstanceOperations(modelClass, service, defaultOperation);
			
		} else {
			List<Operation> operations = ServiceUtil.getOperations(service);
			Iterator<Operation> iterator = operations.iterator();
			while (iterator.hasNext()) {
				Operation operation = iterator.next();
				//TODO initializeInstanceOperations(modelClass, service, operation);
			}
		}
	}

	protected ModelOperation createOperation_GetName(Service service) {
		String serviceName = ServiceLayerHelper.getServiceNameCapped(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		modelOperation.setName("getName");
		String source = "return \""+serviceName+"\";";
		modelOperation.addInitialSource("\t\t"+source+"\n");
		return modelOperation;
	}
	
	protected ModelOperation createOperation_GetDomain(Service service) {
		String domainName = ServiceLayerHelper.getServiceDomainName(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		modelOperation.setName("getDomain");
		String source = "return \""+domainName+"\";";
		modelOperation.addInitialSource("\t\t"+source+"\n");
		return modelOperation;
	}
	
	protected ModelOperation createOperation_GetFixture(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Transactional");
		modelOperation.setName("getFixture");
		String source = "return fixture;";
		modelOperation.addInitialSource("\t\t"+source+"\n");
		return modelOperation;
	}

	protected ModelOperation createOperation_GetMockServiceContext(Service service) {
		String processContextClassName = ServiceLayerHelper.getProcessContextClassName(service.getProcess());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(processContextClassName);
		modelOperation.setName("getMockServiceContext");
		String source = "return mock"+processContextClassName+";";
		modelOperation.addInitialSource("\t\t"+source+"\n");
		return modelOperation;
	}

	protected ModelOperation createOperation_GetMockServiceProcess(Service service) {
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(processClassName);
		modelOperation.setName("getMockServiceProcess");
		String source = "return mock"+processClassName+";";
		modelOperation.addInitialSource("\t\t"+source+"\n");
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Setup(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("setUp");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (ServiceUtil.isStateful(service)) {
			String contextClassName = ServiceLayerHelper.getProcessContextClassName(service.getProcess());
			String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
			
			buf.putLine2("mock"+contextClassName+" = new "+contextClassName+"();");
			buf.putLine2("mock"+processClassName+" = mock("+processClassName+".class);");
		}
		
		String fileNamePrefix = ModuleUtil.getFileNamePrefix(context.getModule());
		String eventsFileName = fileNamePrefix + "-checks.xml";
		//buf.putLine2("CheckpointManager.setDomain(getDomain());");
		buf.putLine2("CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());");
		buf.putLine2("CheckpointManager.addConfiguration(\""+eventsFileName+"\");");
		buf.putLine2("super.setUp();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_SetupOLD(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("setUp");
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		if (ServiceUtil.isStateful(service)) {
			String processName = service.getProcess().getName();
			//String baseName = ProjectUtil.getBaseName(processName);
			//String baseNameCapped = NameUtil.capName(baseName);
			//String serviceNameCapped = NameUtil.capName(service.getName());
			String processClassName = NameUtil.capName(processName);
			buf.putLine2("mockProcessLocator = mock(ProcessLocator.class);");
			buf.putLine2("mock"+processClassName+" = mock("+processClassName+".class);");
			//buf.putLine2("mock"+baseNameCapped+"StateManager = mock("+baseNameCapped+"StateManager.class);");
			buf.putLine2("BeanContext.set(\"org.aries.processLocator\", mockProcessLocator);");
		}
		
		String serviceType = service.getElement();
		if (!ServiceUtil.isStateful(service)) {
			String repositoryName = TypeUtil.getLocalPart(serviceType) + "Repository";
			String repositoryClassName = NameUtil.capName(repositoryName);
			buf.putLine2("mock"+repositoryClassName+" = mock("+repositoryClassName+".class);");
		}
		
		String fileNamePrefix = ModuleUtil.getFileNamePrefix(context.getModule());
		String eventsFileName = fileNamePrefix + "-checks.xml";
		buf.putLine2("CheckpointManager.addConfiguration(\""+eventsFileName+"\");");
		buf.putLine2("MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();"); 
		buf.putLine2("MBeanUtil.setMBeanServer(mbeanServer);");
		//buf.putLine2("fixture = createFixture();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_Teardown(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("tearDown");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("BeanContext.clear();");
		if (service.getProcess() != null) {
			String contextClassName = ServiceLayerHelper.getProcessContextClassName(service.getProcess());
			String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
			buf.putLine2("mock"+contextClassName+" = null;");
			buf.putLine2("mock"+processClassName+" = null;");
		}
		buf.putLine2("fixture = null;");
		buf.putLine2("super.tearDown();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_TeardownOLD(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("tearDown");
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("MBeanUtil.unregisterMBeans();");
		if (service.getProcess() != null && service.getElement() == null) {
			String processName = service.getProcess().getName();
			String baseName = ProjectUtil.getBaseName(processName);
			String baseNameCapped = NameUtil.capName(baseName);
			String processClassName = NameUtil.capName(processName);
			//buf.putLine2("mock"+baseNameCapped+"StateManager = null;");
			buf.putLine2("mock"+processClassName+" = null;");
			buf.putLine2("mockProcessLocator = null;");
		}
		buf.putLine2("BeanContext.clear();");
		buf.putLine2("fixture = null;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createCreateFixtureOperation(Service service) {
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "HandlerImpl";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(className);
		modelOperation.setName("createFixture");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("fixture = new "+className+"();");
		buf.putLine2("initialize(fixture);");
		buf.putLine2("return fixture;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createCreateFixtureOperation(Process process, Service service) {
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "HandlerImpl";
		String contextClassName = ServiceLayerHelper.getProcessContextClassName(service.getProcess());
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String contextFieldName = NameUtil.uncapName(contextClassName);
		String processFieldName = NameUtil.uncapName(processClassName);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(className);
		modelOperation.setName("createFixture");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("fixture = new "+className+"();");
		buf.putLine2("FieldUtil.setFieldValue(fixture, \""+contextFieldName+"\", mock"+contextClassName+");");
		buf.putLine2("FieldUtil.setFieldValue(fixture, \""+processFieldName+"\", mock"+processClassName+");");
		buf.putLine2("initialize(fixture);");

		//buf.putLine2("fixture.setStateManager(mock"+baseNameCapped+"StateManager);");
		
		/*
		//assuming just one execution for now
		List<Execution> executions = service.getExecutions();
		if (executions.size() > 0) {
			Execution execution = executions.get(0);
			List<Action> actions = execution.getMethods();
			Iterator<Action> iterator = actions.iterator();
			while (iterator.hasNext()) {
				Action action = (Action) iterator.next();
				String actionName = NameUtil.capName(action.getName());
				String className = ProcessUtil.getClassName(actionName);
				buf.putLine2("mock"+className+" = mock("+className+".class);");
			}
		}
		*/

		/*
		Process process = context.getProcess();
		if (process != null) {
			String processClassName = NameUtil.capName(service.getName())+"Process";
			buf.putLine2("mockProcessLocator = mock(ProcessLocator.class);");
			buf.putLine2("mock"+processClassName+" = mock("+processClassName+".class);");
			buf.putLine2("BeanContext.set(\"org.aries.processLocator\", mockProcessLocator);");
		}
		*/
		
		buf.putLine2("return fixture;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	

	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Operation operation) throws Exception {
		createOperation_RunTest_success(modelClass, service, operation);
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterType = parameter.getType();
			String className = TypeUtil.getClassName(parameterType);
			if (!ClassUtil.isJavaLangType(className)) {
				createOperation_RunTest_nullParameter(modelClass, service, operation, parameter);
				createOperation_RunTest_emptyParameter(modelClass, service, operation, parameter);
				
				Element element = context.getElementByType(parameterType);
				if (element != null && ElementUtil.isUserDefined(element)) {
					List<Field> fields = ElementUtil.getFields(element);
					Iterator<Field> iterator2 = fields.iterator();
					while (iterator2.hasNext()) {
						Field field = iterator2.next();
						//String fieldType = field.getType();
						if (FieldUtil.isId(field))
							continue;
						createOperation_RunTest_nullParameter(modelClass, service, operation, parameter, field);
						if (!FieldUtil.isBaseType(field) || FieldUtil.isString(field))
							createOperation_RunTest_emptyParameter(modelClass, service, operation, parameter, field);
					}
				}
			}
		}
		if (context.isEnabled("useMessageOrientedInteraction")) {
			createOperation_RunTest_nullCorrelationId(modelClass, service, operation);
			createOperation_RunTest_emptyCorrelationId(modelClass, service, operation);
			if (ServiceUtil.hasProperty(context.getService(), "transaction")) {
				createOperation_RunTest_nullTransactionId(modelClass, service, operation);
				createOperation_RunTest_emptyTransactionId(modelClass, service, operation);
			}
		}
		createOperation_RunTest(modelClass, service, operation);
		createOperation_ValidateProcessInvocation(modelClass, service, operation);
		createOperation_ValidateProcessNotification(modelClass, service, operation);
		if (service instanceof Callback == false)
			createOperation_ValidateAfterExecution(modelClass, service, operation);
	}

	protected String getMessagePackageName(Operation operation) {
		Parameter parameter = OperationUtil.getParameter(operation);
		return TypeUtil.getPackageName(parameter.getType());
	}

	protected String getMessageClassName(Operation operation) {
		Parameter parameter = OperationUtil.getParameter(operation);
		return TypeUtil.getClassName(parameter.getType());
	}

	protected void createOperation_RunTest_success(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String argumentString = CodeUtil.getArgumentString(operation);
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_success");
		Buf buf = new Buf();
		buf.put(CodeUtil.getParameterCreationSource(operation));
		if (context.isEnabled("useMessageOrientedInteraction")) {
			//buf.putLine2("inputMessage = "+fixtureName+".create_"+messageClassName+"();");
			buf.putLine2("setupContext(\"dummyCorrelationId\", \"dummyTransactionId\");");
			if (operation.getParameters().size() == 1)
				buf.putLine2("setupMessage("+argumentString+");");
		}
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest_nullParameter(ModelClass modelClass, Service service, Operation operation, Parameter parameter) throws Exception {
		createOperation_RunTest_nullParameter(modelClass, service, operation, parameter, null);
	}
	
	protected void createOperation_RunTest_nullParameter(ModelClass modelClass, Service service, Operation operation, Parameter parameter, Field field) throws Exception {
		String parameterType = parameter.getType();
		String parameterName = parameter.getName();
		String parameterNameCapped = NameUtil.capName(parameterName);
		String parameterClassName = TypeUtil.getClassName(parameterType);
		String parameterFieldLabel = parameterNameCapped;
		String parameterFieldPath = parameterClassName;
		if (field != null) {
			String fieldName = field.getName();
			String fieldNameCapped = NameUtil.capName(fieldName);
			//String fieldClassName = TypeUtil.getClassName(field.getType());
			parameterFieldLabel = parameterNameCapped + fieldNameCapped;
			parameterFieldPath = parameterNameCapped + "/" + fieldName;
		}
		
		Buf buf = new Buf();
		String argumentString = null;
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_null"+parameterFieldLabel);
		
		Fault serviceFault = getFault(service);
		if (serviceFault != null) {
			String faultClassName = TypeUtil.getClassName(serviceFault.getType());
			buf.putLine2("addExpectedServiceAbortedException("+faultClassName+".class, \""+parameterFieldPath+"\");");
		//} else buf.putLine2("setupForExpectedIllegalArgument(\""+parameterClassName+"\");");
		} else buf.putLine2("addExpectedServiceAbortedException(\""+parameterFieldPath+" must be specified\");");
		
		if (field != null) {
			argumentString = CodeUtil.getArgumentString(operation);
			buf.put(CodeUtil.getParameterCreationSource(operation));
			if (context.isEnabled("useMessageOrientedInteraction")) {
				buf.putLine2("setupContext(\"dummyCorrelationId\", \"dummyTransactionId\");");
				if (operation.getParameters().size() == 1)
					buf.putLine2("setupMessage("+argumentString+");");
			}

			String fieldName = field.getName();
			String fieldNameCapped = NameUtil.capName(fieldName);
			buf.putLine2(parameterName+".set"+fieldNameCapped+"(null);");
			buf.putLine2("isAbortExpected = true;");
			buf.putLine2("runTestExecute_"+operation.getName()+"("+parameterName+");");
			
		} else {
			buf.putLine2("isAbortExpected = true;");
			buf.putLine2("runTestExecute_"+operation.getName()+"(null);");
			
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest_emptyParameter(ModelClass modelClass, Service service, Operation operation, Parameter parameter) throws Exception {
		createOperation_RunTest_emptyParameter(modelClass, service, operation, parameter, null);
	}
	
	protected void createOperation_RunTest_emptyParameter(ModelClass modelClass, Service service, Operation operation, Parameter parameter, Field field) throws Exception {
		String parameterType = parameter.getType();
		String parameterName = parameter.getName();
		String parameterNameCapped = NameUtil.capName(parameterName);
		String parameterClassName = TypeUtil.getClassName(parameterType);
		String parameterFieldLabel = parameterNameCapped;
		String parameterFieldPath = parameterClassName;
		Element element = context.getElementByType(parameterType);
		if (field != null) {
			String fieldName = field.getName();
			String fieldType = field.getType();
			String fieldNameCapped = NameUtil.capName(fieldName);
			//String fieldClassName = TypeUtil.getClassName(field.getType());
			parameterFieldLabel = parameterNameCapped + fieldNameCapped;
			parameterFieldPath = parameterNameCapped + "/" + fieldName;
			element = context.getElementByType(fieldType);
		}

		if (element != null) {
			String elementClassName = TypeUtil.getClassName(element.getType());
			parameterFieldPath = NameUtil.capName(getDepthFirstField(elementClassName));
		}
		
		Buf buf = new Buf();
		String argumentString = CodeUtil.getArgumentString(operation);
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_empty"+parameterFieldLabel);
		
		Fault serviceFault = getFault(service);
		if (serviceFault != null) {
			String faultClassName = TypeUtil.getClassName(serviceFault.getType());
			buf.putLine2("addExpectedServiceAbortedException("+faultClassName+".class, \""+parameterNameCapped+"\");");
		//} else buf.putLine2("setupForExpectedIllegalArgument(\""+parameterClassName+"\");");
		//} else buf.putLine2("addExpectedServiceAbortedException(\""+parameterClassName+" must not be empty\");");
		} else buf.putLine2("addExpectedServiceAbortedException(\""+parameterFieldPath+" must be specified\");");
		//buf.putLine2("inputMessage = "+fixtureName+".createEmpty_"+messageClassName+"();");
		
		if (field != null) {
			buf.put(CodeUtil.getParameterCreationSource(operation));
			if (context.isEnabled("useMessageOrientedInteraction")) {
				buf.putLine2("setupContext(\"dummyCorrelationId\", \"dummyTransactionId\");");
				if (operation.getParameters().size() == 1)
					buf.putLine2("setupMessage("+argumentString+");");
			}

			String fieldType = field.getType();
			String fieldName = field.getName();
			String fieldNameCapped = NameUtil.capName(fieldName);
			String fieldClassName = TypeUtil.getClassName(field.getType());

			if (FieldUtil.isString(field)) {
				buf.putLine2(parameterName+".set"+fieldNameCapped+"(\"\");");
			} else {
				String fixtureName = ModelLayerHelper.getModelFixtureBeanName(fieldType);
				buf.putLine2(parameterClassName + " " +parameterName + " = " + fixtureName+".createEmpty_"+fieldClassName+"();");
				buf.putLine2(parameterName+".set"+fieldNameCapped+"("+fieldName+");");
			}

		} else {
			buf.put(CodeUtil.getEmptyParameterCreationSource(operation));
			if (context.isEnabled("useMessageOrientedInteraction")) {
				buf.putLine2("setupContext(\"dummyCorrelationId\", \"dummyTransactionId\");");
				if (operation.getParameters().size() == 1)
					buf.putLine2("setupMessage("+argumentString+");");
			}
			
		}
		
		buf.putLine2("isAbortExpected = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//move this to CheckpointUtil (or something like that)
	protected String getDepthFirstField(String className) {
//		if (className.equals("OrderRequestMessage"))
//			System.out.println();
		Element element = context.getElementByName(className);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		return getDepthFirstField(element, elementNameUncapped);
	}

	//move this to CheckpointUtil (or something like that)
	protected String getDepthFirstField(Reference reference) {
		String className = TypeUtil.getClassName(reference.getType());
		Element element = context.getElementByName(className);
		return getDepthFirstField(element, reference.getName());
	}

	//move this to CheckpointUtil (or something like that)
	protected String getDepthFirstField(Element element, String name) {
		String fieldPath = getDepthFirstField(element);
		if (!fieldPath.isEmpty())
			return name + "/" + fieldPath;
		return name;
	}

	//move this to CheckpointUtil (or something like that)
	protected String getDepthFirstField(Element element) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(ElementUtil.getAttributes(element));
		fields.addAll(ElementUtil.getReferences(element));
		//ElementUtil.sortFieldsByType(fields);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String structure = field.getStructure();
			if (!structure.equals("item"))
				continue;
			String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
			if (field instanceof Reference)
				return getDepthFirstField((Reference) field);
			return fieldNameUncapped;
		}
		return "";
	}

	protected void createOperation_RunTest_nullCorrelationId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String argumentString = CodeUtil.getArgumentString(operation);
		//String messagePackageName = getMessagePackageName(operation);
		//String fixtureName = FixtureUtil.getFixtureNameFromPackageName(messagePackageName);
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_nullCorrelationId");
		Buf buf = new Buf();
		Fault serviceFault = getFault(service);
		if (serviceFault != null) {
			String faultClassName = TypeUtil.getClassName(serviceFault.getType());
			buf.putLine2("addExpectedServiceAbortedException("+faultClassName+".class, \"CorrelationId null\");");
		} else buf.putLine2("addExpectedServiceAbortedException(\"CorrelationId null\");");
		//buf.putLine2("inputMessage = "+fixtureName+".create_"+messageClassName+"();");
		buf.put(CodeUtil.getParameterCreationSource(operation));
		if (context.isEnabled("useMessageOrientedInteraction")) {
			buf.putLine2("setupContext(null, \"dummyTransactionId\");");
			if (operation.getParameters().size() == 1)
				buf.putLine2("setupMessage("+argumentString+");");
		}
		buf.putLine2("isAbortExpected = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest_emptyCorrelationId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String argumentString = CodeUtil.getArgumentString(operation);
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_emptyCorrelationId");
		Buf buf = new Buf();
		Fault serviceFault = getFault(service);
		if (serviceFault != null) {
			String faultClassName = TypeUtil.getClassName(serviceFault.getType());
			buf.putLine2("addExpectedServiceAbortedException("+faultClassName+".class, \"CorrelationId empty\");");
		} else buf.putLine2("addExpectedServiceAbortedException(\"CorrelationId empty\");");
		//buf.putLine2("inputMessage = "+fixtureName+".create_"+messageClassName+"();");
		buf.put(CodeUtil.getParameterCreationSource(operation));
		if (context.isEnabled("useMessageOrientedInteraction")) {
			buf.putLine2("setupContext(\"\", \"dummyTransactionId\");");
			if (operation.getParameters().size() == 1)
				buf.putLine2("setupMessage("+argumentString+");");
		}
		buf.putLine2("isAbortExpected = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createOperation_RunTest_nullTransactionId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String argumentString = CodeUtil.getArgumentString(operation);
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_nullTransactionId");
		Buf buf = new Buf();
		Fault serviceFault = getFault(service);
		if (serviceFault != null) {
			String faultClassName = TypeUtil.getClassName(serviceFault.getType());
			buf.putLine2("addExpectedServiceAbortedException("+faultClassName+".class, \"TransactionId null\");");
		} //else buf.putLine2("setupForExpectedAssertionError(\"TransactionId null\");");
		//buf.putLine2("inputMessage = "+fixtureName+".create_"+messageClassName+"();");
		buf.put(CodeUtil.getParameterCreationSource(operation));
		if (context.isEnabled("useMessageOrientedInteraction")) {
			buf.putLine2("setupContext(\"dummyCorrelationId\", null);");
			buf.putLine2("//setGlobalTransactionActive(true);");
			if (operation.getParameters().size() == 1)
				buf.putLine2("setupMessage("+argumentString+");");
		}
		//buf.putLine2("isAbortExpected = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest_emptyTransactionId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String argumentString = CodeUtil.getArgumentString(operation);
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_emptyTransactionId");
		Buf buf = new Buf();
		Fault serviceFault = getFault(service);
		if (serviceFault != null) {
			String faultClassName = TypeUtil.getClassName(serviceFault.getType());
			buf.putLine2("addExpectedServiceAbortedException("+faultClassName+".class, \"TransactionId empty\");");
		} //else buf.putLine2("setupForExpectedAssertionError(\"TransactionId empty\");");
		//buf.putLine2("inputMessage = "+fixtureName+".create_"+messageClassName+"();");
		buf.put(CodeUtil.getParameterCreationSource(operation));
		if (context.isEnabled("useMessageOrientedInteraction")) {
			buf.putLine2("setupContext(\"dummyCorrelationId\", \"\");");
			buf.putLine2("//setGlobalTransactionActive(true);");
			if (operation.getParameters().size() == 1)
				buf.putLine2("setupMessage("+argumentString+");");
		}
		//buf.putLine2("isAbortExpected = true;");
		buf.putLine2("runTestExecute_"+operation.getName()+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected ModelOperation createOperation_RunTest(ModelClass modelClass, Service service, Operation operation, String nameExtension) throws Exception {
		//List<String> parameterList = createParameterList(modelClass, service, operation, fixtureType, correlationId);
		//createOperation_RunTest(modelClass, service, operation, parameterList, nameExtension, expectedException);
		return createOperation_RunTest(modelClass, service, operation, nameExtension, null);
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
				String packageName = TypeUtil.getPackageName(parameter.getType());
				
				if (packageName.equals("java.lang")) {
					boolean isCollection = !parameter.getConstruct().equals("item");
					String dummyValue = dummyValueFactory.getDummyValue(parameterName, parameter.getType(), false, isCollection, false);
					parameterList.add(dummyValue);
					
				} else {
					String fixtureName = FixtureUtil.getFixtureNameFromType(parameter.getType());
					if (parameterName.equalsIgnoreCase("message"))
						parameterName = TypeUtil.getClassName(parameter.getType());
					parameterList.add(fixtureName + ".create_" + fixtureType + parameterName + "()");
					modelClass.addImportedClass(packageName + "." + fixtureName);
				}
			}
		}
		return parameterList;
	}

	protected ModelOperation createOperation_RunTest(ModelClass modelClass, Service service, Operation operation, String nameExtension, String expectedException) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("testExecute_" + operation.getName() + nameExtension);
		if (expectedException != null)
			modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation(expectedException));
		else modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addException("Exception");
		return modelOperation;
	}

	protected void createOperation_RunTest(ModelClass modelClass, Service service, Operation operation) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("runTestExecute_"+operation.getName());
		modelOperation.getParameters().addAll(util.createModelParameters(operation));
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	fixture = createFixture();");
		String argumentString = CodeUtil.getArgumentString(modelOperation);
		buf.putLine2("	fixture."+operation.getName()+"("+argumentString+");");

		if (ServiceUtil.hasProperty(context.getService(), "transaction")) {
			buf.putLine3("");
			buf.putLine2("	if (isGlobalTransactionActive())");
			buf.putLine2("		validateEnrollTransaction("+argumentString+");");
		}

		buf.putLine2("	validateProcessInvocation("+argumentString+");");
		buf.putLine3("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	validateAfterException(e);");
		//buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine3("");
		buf.putLine2("} finally {");
		buf.putLine2("	validateProcessNotification();");
		buf.putLine2("	validateAfterExecution();");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createOperation_RunTestOLD(ModelClass modelClass, Service service, Operation operation) throws Exception {
		//String serviceNameCapped = NameUtil.capName(service.getName());
		//String operationNameCapped = NameUtil.capName(operation.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("runTestExecute_"+operation.getName());
		modelOperation.addException("Exception");
		modelOperation.addParameter(CodeUtil.createParameter_CorrelationId());

		String processClassName = null;
		if (ServiceUtil.isStateful(service)) {
			processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
			//String baseName = GenerateUtil.getBaseName(processName);
			//String baseNameCapped = NameUtil.capName(baseName);
		}
		
		List<String> parameterList = new ArrayList<String>(); 
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			ModelParameter modelParameter = CodeUtil.createParameter(parameters, parameter);
			modelOperation.addParameter(modelParameter);
			parameterList.add(modelParameter.getName());
		}

		List<String> sourceLines = new ArrayList<String>();
		if (ServiceUtil.isStateful(service))
			sourceLines.add("when(mockProcessLocator.lookup("+processClassName+".class, correlationId)).thenReturn(mock"+processClassName+");");
		sourceLines.add("//$$$ Start fixture execution");
		sourceLines.add("fixture = createFixture(correlationId);");
		
		String serviceType = service.getElement();
		if (!ServiceUtil.isStateful(service)) {
			String repositoryName = TypeUtil.getLocalPart(serviceType) + "Repository";
			String repositoryClassName = NameUtil.capName(repositoryName);
			sourceLines.add("fixture."+repositoryName+" = mock"+repositoryClassName+";");
		}

		StringBuffer buf = new StringBuffer();
		//buf.append("fixture.execute(");
		buf.append("fixture."+operation.getName()+"(");
		Iterator<String> parameterIterator = parameterList.iterator();
		for (int i=0; parameterIterator.hasNext(); i++) {
			String parameterName = parameterIterator.next();
			if (i > 0)
				buf.append(", ");
			buf.append(parameterName);
		}
		buf.append(");");
		sourceLines.add(buf.toString());
		sourceLines.add("//$$$ Finish fixture execution");

		String operationName = null;
		if (ServiceUtil.isStateful(service)) {
			if (service instanceof Callback) 
				operationName = "response";
			else operationName = "request";
			operationName += NameUtil.capName(operation.getName());
		} else operationName = NameUtil.uncapName(operation.getName());
		
		buf = new StringBuffer();
		if (!ServiceUtil.isStateful(service)) {
			String repositoryName = TypeUtil.getLocalPart(serviceType) + "Repository";
			String repositoryClassName = NameUtil.capName(repositoryName);
			buf.append("verify(mock"+repositoryClassName+")."+operationName+"(");
			appendParameterList(buf, parameterList);
			buf.append(");");
			sourceLines.add(buf.toString());
			sourceLines.add("verifyNoMoreInteractions(mock"+repositoryClassName+");");
			
		}
		if (ServiceUtil.isStateful(service)) {
			//buf.append("verify(mock"+processClassName+").receive"+operationNameCapped+"(");
			buf.append("verify(mock"+processClassName+")."+operationName+"(");
			appendParameterList(buf, parameterList);
			buf.append(");");
			sourceLines.add(buf.toString());
			//sourceLines.add("verify(mock"+processClassName+").setStateManager(mock"+baseNameCapped+"StateManager);");
			sourceLines.add("verifyNoMoreInteractions(mock"+processClassName+");");

		} else {
			//System.out.println();
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_ValidateProcessInvocation(ModelClass modelClass, Service service, Operation operation) {
		String serviceName = ServiceLayerHelper.getServiceNameCapped(service);
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String methodSuffix = null;
		if (service instanceof Callback)
			methodSuffix = "_response";
		else methodSuffix = "_request";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("void");
		modelOperation.setName("validateProcessInvocation");
		modelOperation.getParameters().addAll(util.createModelParameters(operation));
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("if (!isAbortExpected)");
		String argumentString = CodeUtil.getArgumentString(operation);
		buf.putLine2("	verify(mock"+processClassName+").handle_"+serviceName+methodSuffix+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_ValidateProcessNotification(ModelClass modelClass, Service service, Operation operation) {
		String serviceName = ServiceLayerHelper.getServiceNameCapped(service);
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("void");
		modelOperation.setName("validateProcessNotification");
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("//verify(mock"+processClassName+").fire"+serviceName+"Done();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_ValidateAfterExecution(ModelClass modelClass, Service service, Operation operation) {
		String serviceName = ServiceLayerHelper.getServiceNameCapped(service);
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("void");
		modelOperation.setName("validateAfterExecution");
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("if (isAbortExpected)");
		buf.putLine2("	verify(mock"+processClassName+").handle_"+serviceName+"_request_exception(expectedException);");
		buf.putLine2("super.validateAfterExecution();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	public static void appendParameterList(StringBuffer buf, List<String> parameterList) {
		Iterator<String> parameterIterator;
		parameterIterator = parameterList.iterator();
		for (int i=0; parameterIterator.hasNext(); i++) {
			String parameterName = parameterIterator.next();
			if (i > 0)
				buf.append(", ");
			buf.append(parameterName);
		}
	}

	
//	protected boolean assureOperationName(String operationName) {
//		if (operationName.startsWith("import"))
//			return true;
//		if (assureOperation_GetAllElements(operation, element))
//			return true;
//		if (assureOperation_GetElementsByField(operation, element))
//			return true;
//		if (assureOperation_GetElementsByPage(operation, element))
//			return true;
//		if (assureOperation_GetElementsByCriteria(namespace, operation, element))
//			return true;
//		if (assureOperation_GetElementById(operation, element))
//			return true;
//		if (assureOperation_GetElementByField(operation, element))
//			return true;
//		if (assureOperation_AddElement(operation, element))
//			return true;
//		if (assureOperation_MoveElement(operation, element))
//			return true;
//		if (assureOperation_SaveElement(operation, element))
//			return true;
//		if (assureOperation_DeleteElement(operation, element))
//			return true;
//		return false;
//	}
	
}
