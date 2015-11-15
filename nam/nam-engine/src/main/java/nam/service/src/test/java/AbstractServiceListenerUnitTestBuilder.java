package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Element;
import nam.model.Execution;
import nam.model.Fault;
import nam.model.Field;
import nam.model.Invocation;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
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

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public abstract class AbstractServiceListenerUnitTestBuilder extends AbstractListenerTestBuilder {

	protected abstract AbstractServiceListenerUnitTestProvider createProvider(GenerationContext context);
	
	protected abstract String getFixtureClassName(Service service);

	protected abstract String getTestClassName(Service service);

	protected abstract String getTestParentClassName();

	
	protected AbstractServiceListenerUnitTestProvider provider;
	
	protected DummyValueFactory dummyValueFactory;
	
	private boolean expectException;
	
	
	public AbstractServiceListenerUnitTestBuilder(GenerationContext context) {
		super(context);
		provider = createProvider(context);
		dummyValueFactory = new DummyValueFactory(context);
		CodeUtil.dummyValueFactory = dummyValueFactory;
	}

	public ModelClass buildClass(Service service) throws Exception {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		//String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = getTestClassName(service);
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
		
		if (operation != null) {
			Parameter parameter = OperationUtil.getParameter(operation);
			if (parameter != null) {
				String parameterPackageName = TypeUtil.getPackageName(parameter.getType());
				String parameterClassName = TypeUtil.getClassName(parameter.getType());
				modelClass.addImportedClass(parameterPackageName+"."+parameterClassName);
			}
		}
		modelClass.setParentClassName(getTestParentClassName());
		modelClass.addImportedClass(getTestParentClassName());
		initializeClass(modelClass, service);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Service service) throws Exception {
		initializeClassAnnotations(modelClass, service);
		initializeImportedClasses(modelClass, service);
		initializeImplementedInterfaces(modelClass, service);
		initializeInstanceFields(modelClass, service);
		initializeInstanceMethods(modelClass, service);
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
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		//modelClass.addImportedClass("org.aries.util.MBeanUtil");
		//modelClass.addImportedClass("common.tx.handler.MessageUtil");
		modelClass.addImportedClass("org.aries.util.FieldUtil");
		//modelClass.addImportedClass("org.aries.tx.Transactional");

		Operation defaultOperation = ServiceUtil.getDefaultOperation(service);
		if (defaultOperation != null) {
			initializeImportedClasses(modelClass, service, defaultOperation);
			
		} else {
			List<Operation> operations = ServiceUtil.getOperations(service);
			Iterator<Operation> iterator = operations.iterator();
			while (iterator.hasNext()) {
				Operation operation = iterator.next();
				initializeImportedClasses(modelClass, service, operation);
			}
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
			
			if (element != null) {
				String namespace = TypeUtil.getNamespace(element.getType());
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
		
		Fault fault = getFault(service);
		if (fault != null) {
			String faultPackageName = ServiceLayerHelper.getFaultPackageName(fault);
			String faultClassName = ServiceLayerHelper.getFaultClassName(fault);
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
			createMockHandlerField(modelClass, service);
			createMockContextField(modelClass, service);
			//createMockStateManagerFields(modelClass, service);
		}
	}

	protected void createFixtureField(ModelClass modelClass, Service service) throws Exception {
		String className = getFixtureClassName(service);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(modelClass.getPackageName());
		attribute.setClassName(className);
		attribute.setName("fixture");
		attribute.setGenerateGetter(false);;
		attribute.setGenerateSetter(false);;
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
	
	protected void createMockHandlerField(ModelClass modelClass, Service service) {
		String handlerClassName = provider.getHandlerInterfaceName(service);
		String handlerPackageName = ServiceLayerHelper.getHandlerPackageName(service);
		modelClass.addImportedClass(handlerPackageName+"."+handlerClassName);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(handlerPackageName);
		attribute.setClassName(handlerClassName);
		attribute.setName("mock" + handlerClassName);
		attribute.setGenerateGetter(false);;
		attribute.setGenerateSetter(false);;
		modelClass.addInstanceAttribute(attribute);
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

	protected void initializeInstanceMethods(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceOperation(createOperation_GetServiceId(service));
		modelClass.addInstanceOperation(createOperation_GetDomain(service));
		modelClass.addInstanceOperation(createOperation_GetModule(service));
		if (service.getProcess() != null)
			modelClass.addInstanceOperation(createOperation_GetMockServiceContext(service));
		modelClass.addInstanceOperation(createOperation_Setup(service));
		modelClass.addInstanceOperation(createOperation_Teardown(service));
		modelClass.addInstanceOperation(createCreateFixtureOperation(service));

		Operation defaultOperation = ServiceUtil.getDefaultOperation(service);
		if (defaultOperation != null) {
			initializeInstanceMethods(modelClass, service, defaultOperation);
			
		} else {
			List<Operation> operations = ServiceUtil.getOperations(service);
			Iterator<Operation> iterator = operations.iterator();
			while (iterator.hasNext()) {
				Operation operation = iterator.next();
				//TODO initializeInstanceMethods(modelClass, service, operation);
			}
		}
	}

	protected ModelOperation createOperation_GetServiceId(Service service) {
		String serviceName = ServiceLayerHelper.getServiceNameCapped(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		modelOperation.setName("getServiceId");
		String source = "return "+serviceName+".ID;";
		modelOperation.addInitialSource("\t\t"+source+"\n");
		return modelOperation;
	}
	
	protected ModelOperation createOperation_GetDomain(Service service) {
		String domainName = ServiceLayerHelper.getServiceDomainName(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		modelOperation.setName("getDomain");
		String source = "return \""+domainName+"\";";
		modelOperation.addInitialSource("\t\t"+source+"\n");
		return modelOperation;
	}
	
	protected ModelOperation createOperation_GetModule(Service service) {
		//String domainName = ServiceLayerHelper.getServiceDomainName(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		modelOperation.setName("getModule");
		Module module = context.getModule();
		String source = "return \""+module.getName()+"\";";
		//String source = "return \""+domainName+".service\";";
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
		modelOperation.addInitialSource(provider.getMethodSource_Setup(service));
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
		//buf.putLine2("CheckpointManager.setDomain(getDomain());");
		buf.putLine2("CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());");
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
		modelOperation.addInitialSource(provider.getMethodSource_TearDown(service));
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
		String fixtureClassName = getFixtureClassName(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(fixtureClassName);
		modelOperation.setName("createFixture");
		modelOperation.addException("Exception");
		Process process = service.getProcess();
		
		if (process != null)
			modelOperation.addInitialSource(provider.getMethodSource_CreateFixture(process, service));
		else modelOperation.addInitialSource(provider.getMethodSource_CreateFixture(service));
		return modelOperation;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Service service, Operation operation) throws Exception {
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
		}
		if (ServiceUtil.hasProperty(context.getService(), "transaction")) {
			createOperation_RunTest_nullTransactionId(modelClass, service, operation);
			createOperation_RunTest_emptyTransactionId(modelClass, service, operation);
		}
		createOperation_RunTest(modelClass, service, operation);
	}

	protected void createOperation_RunTest_success(ModelClass modelClass, Service service, Operation operation) throws Exception {
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_success");
		modelOperation.addInitialSource(provider.getMethodSource_RunTest_success(service, operation));
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest_nullParameter(ModelClass modelClass, Service service, Operation operation, Parameter parameter) throws Exception {
		createOperation_RunTest_nullParameter(modelClass, service, operation, parameter, null);
	}
	
	protected void createOperation_RunTest_nullParameter(ModelClass modelClass, Service service, Operation operation, Parameter parameter, Field field) throws Exception {
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_null"+provider.getParameterFieldLabel(parameter, field));
		modelOperation.addInitialSource(provider.getMethodSource_RunTest_nullParameter(service, operation, parameter, field));
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest_emptyParameter(ModelClass modelClass, Service service, Operation operation, Parameter parameter) throws Exception {
		createOperation_RunTest_emptyParameter(modelClass, service, operation, parameter, null);
	}
	
	protected void createOperation_RunTest_emptyParameter(ModelClass modelClass, Service service, Operation operation, Parameter parameter, Field field) throws Exception {
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_empty"+provider.getParameterFieldLabel(parameter, field));
		modelOperation.addInitialSource(provider.getMethodSource_RunTest_emptyParameter(service, operation, parameter, field));
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest_nullCorrelationId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_nullCorrelationId");
		modelOperation.addInitialSource(provider.getMethodSource_RunTest_nullCorrelationId(service, operation));
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest_emptyCorrelationId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_emptyCorrelationId");
		modelOperation.addInitialSource(provider.getMethodSource_RunTest_emptyCorrelationId(service, operation));
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createOperation_RunTest_nullTransactionId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_nullTransactionId");
		modelOperation.addInitialSource(provider.getMethodSource_RunTest_nullTransactionId(service, operation));
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createOperation_RunTest_emptyTransactionId(ModelClass modelClass, Service service, Operation operation) throws Exception {
		ModelOperation modelOperation = createOperation_RunTest(modelClass, service, operation, "_emptyTransactionId");
		modelOperation.addInitialSource(provider.getMethodSource_RunTest_emptyTransactionId(service, operation));
		modelClass.addInstanceOperation(modelOperation);
	}

	protected ModelOperation createOperation_RunTest(ModelClass modelClass, Service service, Operation operation, String nameExtension) throws Exception {
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
		modelOperation.addException("Exception");
		modelOperation.getParameters().addAll(util.createModelParameters(operation));
		modelOperation.addInitialSource(provider.getMethodSource_RunTest(service, operation));
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

	protected void createOperation_ValidateHandlerInvocation(ModelClass modelClass, Service service, Operation operation) {
		String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		String handlerClassName = ServiceLayerHelper.getHandlerInterfaceName(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("validateHandlerInvocation");
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("verify(mock"+handlerClassName+")."+serviceName+"(inputMessage);");
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
