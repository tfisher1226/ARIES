package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Cache;
import nam.model.Element;
import nam.model.Field;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Project;
import nam.model.Result;
import nam.model.Service;
import nam.model.src.main.java.DummyValueFactory;
import nam.model.util.CacheUtil;
import nam.model.util.ElementUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ParameterUtil;
import nam.model.util.ProcessUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class ServiceTXLocalITBuilder extends AbstractBeanBuilder {

	private ServiceTXLocalITProvider serviceLocalTxITProvider;
	
	private DummyValueFactory dummyValueFactory;
	
	private boolean expectException;
	
	
	public ServiceTXLocalITBuilder(GenerationContext context) {
		serviceLocalTxITProvider = new ServiceTXLocalITProvider(context);
		dummyValueFactory = new DummyValueFactory(context);
		this.context = context;
	}

	public ModelClass build(Service service) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		//String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "TXLocalIT";
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
		modelClass.setParentClassName("AbstractTransactionalServiceIT");
		initializeClass(modelClass, service);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Service service) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service);
		initializeImportedClasses(modelClass, service);
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

		modelClass.addImportedClass("static org.mockito.Matchers.anyList");
		modelClass.addImportedClass("static org.mockito.Mockito.doThrow");
		modelClass.addImportedClass("static org.mockito.Mockito.mock");
		modelClass.addImportedClass("static org.mockito.Mockito.verify");
		modelClass.addImportedClass("static org.mockito.Mockito.verifyNoMoreInteractions");
		modelClass.addImportedClass("static org.mockito.Mockito.when");

		modelClass.addImportedClass("java.util.concurrent.Executors");
		modelClass.addImportedClass("javax.management.MBeanServer");
		modelClass.addImportedClass("javax.management.MBeanServerFactory");

		modelClass.addImportedClass("org.aries.Assert");
		if (ServiceUtil.isStateful(service))
			modelClass.addImportedClass("org.aries.registry.ProcessLocator");
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.validate.util.CheckpointManager");
		modelClass.addImportedClass("org.aries.process.Process");
		modelClass.addImportedClass("org.aries.launcher.TestUtil");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.util.FieldUtil");
		modelClass.addImportedClass("org.aries.util.MBeanUtil");
		//modelClass.addImportedClass("common.tx.handler.MessageUtil");

		modelClass.addImportedClass("common.tx.UserTransaction");
		modelClass.addImportedClass("common.tx.UserTransactionFactory");
		modelClass.addImportedClass("common.tx.manager.AbstractTransactionalServiceIT");
		
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
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		//createDataFields(modelClass, service);
		createCacheFields(modelClass, service, false);
		createCacheFields(modelClass, service, true);
		//createCacheManagerFields(modelClass, service, false);
		createCacheManagerFields(modelClass, service, true);
		createObjectFactoryFields(modelClass, service, true);
		createClientFields(modelClass, service, true);
		createProcessField(modelClass, service);
		if (ServiceUtil.isStateful(service)) {
		}
	}
	
	protected void createProcessField(ModelClass modelClass, Service service) throws Exception {
		if (ServiceUtil.isStateful(service)) {
			Process process = service.getProcess();
			String processName = ServiceLayerHelper.getProcessName(process);
			String processBeanName = NameUtil.uncapName(processName);
			String processClassName = ServiceLayerHelper.getProcessClassName(process);
			String processPackageName = ServiceLayerHelper.getProcessPackageName(process);
			modelClass.addImportedClass(processPackageName + "." + processClassName);
			ModelAttribute attribute = new ModelAttribute();
			attribute.setModifiers(Modifier.PRIVATE);
			attribute.setPackageName(processPackageName);
			attribute.setClassName(processClassName);
			attribute.setName(processBeanName);
			modelClass.addInstanceAttribute(attribute);
		}
	}
	
	protected void createDataFields(ModelClass modelClass, Service service) throws Exception {
		Process process = service.getProcess();
		Iterator<Cache> iterator = process.getCacheUnits().iterator();
		while (iterator.hasNext()) {
			Cache cache = (Cache) iterator.next();
			createDataFields(modelClass, service, cache);
		}
	}
		
	protected void createDataFields(ModelClass modelClass, Service service, Cache cache) {
		List<Field> fields = ElementUtil.getFields(cache);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			createDataField(modelClass, service, cache, field);
		}
	}

	protected void createDataField(ModelClass modelClass, Service service, Cache cache, Field field) {
		String fieldType = field.getType();
		String fieldName = NameUtil.uncapName(field.getName()); 
		String className = TypeUtil.getClassName(fieldType);
		String packageName = TypeUtil.getPackageName(fieldType);
		String structure = field.getStructure();
		modelClass.addImportedClass(packageName+"."+className);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName(fieldName);
		attribute.setStructure(structure);
		modelClass.addInstanceAttribute(attribute);
		if (structure.equals("list"))
			modelClass.addImportedClass("java.util.List");
		else if (structure.equals("set"))
			modelClass.addImportedClass("java.util.Set");
		else if (structure.equals("map"))
			modelClass.addImportedClass("java.util.Map");
	}

	protected void createCacheFields(ModelClass modelClass, Service service, boolean isMock) throws Exception {
		if (ServiceUtil.isStateful(service)) {
			Process process = service.getProcess();
			Iterator<Cache> iterator = process.getCacheUnits().iterator();
			while (iterator.hasNext()) {
				Cache cache = (Cache) iterator.next();
				createCacheField(modelClass, service, cache, isMock);
			}
		}
	}
	
	protected void createCacheField(ModelClass modelClass, Service service, Cache cache, boolean isMock) {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache);
		String beanName = NameUtil.uncapName(className);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		if (isMock)
			attribute.setName("mock"+className);
		else attribute.setName(beanName);
		modelClass.addInstanceAttribute(attribute);
		modelClass.addImportedClass(packageName+"."+className);
	}
	
	protected void createCacheManagerFields(ModelClass modelClass, Service service, boolean isMock) throws Exception {
		if (ServiceUtil.isStateful(service)) {
			Process process = service.getProcess();
			Iterator<Cache> iterator = process.getCacheUnits().iterator();
			while (iterator.hasNext()) {
				Cache cache = (Cache) iterator.next();
				createCacheManagerField(modelClass, service, cache, isMock);
			}
		}
	}
	
	protected void createCacheManagerField(ModelClass modelClass, Service service, Cache cache, boolean isMock) {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache) + "Manager";
		String beanName = NameUtil.uncapName(className);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		if (isMock)
			attribute.setName("mock"+className);
		else attribute.setName(beanName);
		modelClass.addInstanceAttribute(attribute);
		modelClass.addImportedClass(packageName+"."+className);
	}

	protected void createObjectFactoryFields(ModelClass modelClass, Service service, boolean isMock) throws Exception {
		Set<String> objectFactoryPackages = new HashSet<String>();
		Process process = service.getProcess();
		String operationName = service.getName();
		
		Operation operation = null;
		List<Operation > operations = new ArrayList<Operation>();
		if (ServiceUtil.isStateful(service))
			operation = ProcessUtil.getOperation(process, operationName);
		if (operation != null)
			operations.add(operation);
		if (operation == null) {
			operations.addAll(ServiceUtil.getOperations(service));
		}
		
		List<Parameter> parameters = new ArrayList<Parameter>();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			operation = iterator.next();
			parameters.addAll(operation.getParameters());
		}
		
		Iterator<Parameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			Parameter parameter = parameterIterator.next();

			String parameterType = parameter.getType();
			//String parameterName = NameUtil.uncapName(parameter.getName());
			//String parameterClassName = TypeUtil.getClassName(parameterType);
			String parameterPackageName = TypeUtil.getPackageName(parameterType);
			String parameterNamespaceUri = TypeUtil.getNamespace(parameterType);
			//Element parameterElement = context.getElementByName(parameterClassName);
			Namespace parameterNamespace = context.getNamespaceByUri(parameterNamespaceUri);
			if (parameterNamespace == null)
				continue;
			
			if (!objectFactoryPackages.contains(parameterPackageName)) {
				objectFactoryPackages.add(parameterPackageName);
				createAttribute_ObjectFactory(modelClass, parameterPackageName, isMock);
			}
		}
	}

	protected void createAttribute_ObjectFactory(ModelClass modelClass, String packageName, boolean isMock) throws Exception {
		String factoryPrefix = NameUtil.getSimpleName(packageName);
		factoryPrefix = factoryPrefix.replace(".", "_");
		factoryPrefix = NameUtil.toCamelCase(factoryPrefix);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setFullyQualified(true);
		attribute.setPackageName(packageName);
		attribute.setClassName(packageName+".ObjectFactory");
		if (isMock)
			attribute.setName("mock" + factoryPrefix + "ObjectFactory");
		else attribute.setName(NameUtil.uncapName(factoryPrefix) + "ObjectFactory");
		//attribute.setValue("new "+className+"();");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelClass.addInstanceAttribute(attribute);
	}
	
	protected void createClientFields(ModelClass modelClass, Service service, boolean isMock) {
		if (ServiceUtil.isStateful(service)) {
			Process process = service.getProcess();
			String operationName = service.getName();
			Operation operation = ProcessUtil.getOperation(process, operationName);
			//Assert.notNull(operation, "Process: "+process.getName()+": operation not found: "+operationName);
			
//			if (service.getClassName().equals("OrderRejectedListener"))
//				System.out.println();
			if (operation != null) {
				Project project = context.getProject();
				List<Service> remoteServices = ProjectUtil.getRemoteEndpoints(project, service, operation);
				Iterator<Service> iterator = remoteServices.iterator();
				while (iterator.hasNext()) {
					Service remoteService = iterator.next();
//					if (remoteService == null)
//						System.out.println();
					createClientField(modelClass, service, remoteService, isMock);
				}
				
				//List<Link> links = ServiceUtil.getLinks(service);
				//List<Invoker> invokers = ServiceUtil.getInvokers(service);
				//List<Interactor> interactors = ServiceUtil.getInteractors(service);
				//List<Sender> senders = ServiceUtil.getSenders(service);
			}
		}
	}

	protected void createClientField(ModelClass modelClass, Service service, Service remoteService, boolean isMock) {
//		if (remoteService == null)
//			System.out.println();
		String packageName = ServiceLayerHelper.getServicePackageName(remoteService);
//		String packageName = context.getPackageName(remoteService);
		String className = remoteService.getInterfaceName() + "Client";
		String clientName = NameUtil.uncapName(className) + "Client";
//		String cacheName = NameUtil.capName(cache.getName()) + "Manager"; 
//		String className = TypeUtil.getClassName(cacheType) + "Manager";
//		String packageName = TypeUtil.getPackageName(cacheType);
		
		modelClass.addImportedClass(packageName+"."+className);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("mock"+className);
		if (isMock)
			attribute.setName("mock"+className);
		else attribute.setName(clientName);
		modelClass.addInstanceAttribute(attribute);
		modelClass.addImportedClass(packageName+"."+className);
	}


	/*
	 * Operations
	 * ----------
	 */
	
	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceOperation(createOperation_Setup(service));
		modelClass.addInstanceOperation(createOperation_Teardown(service));
		
		if (ServiceUtil.isStateful(service)) {
			modelClass.addInstanceOperation(createProcessFactoryOperation(service));
			modelClass.addInstanceOperations(createCacheFactoryOperations(service));
		}
		
		modelClass.addInstanceOperation(createStartServiceOperation(service));
		modelClass.addInstanceOperation(createStopServiceOperation(service));

		modelClass.addInstanceOperations(createOperations_RunTest_Commit(modelClass, service));
//		modelClass.addInstanceOperation(createOperation_RunTest_Rollback(modelClass, service));
//		modelClass.addInstanceOperation(createOperation_RunTest_Rollback_EmptyInput(modelClass, service));
//		modelClass.addInstanceOperations(createOperations_RunTest_Rollback_ServiceUnreachable(modelClass, service));
//		modelClass.addInstanceOperations(createOperations_RunTest_Rollback_ManagerUnreachable(modelClass, service));
//		modelClass.addInstanceOperation(createOperation_RunTest_Rollback_RandomNullPointer(modelClass, service));
	}

	protected ModelOperation createOperation_Setup(Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("setUp");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("super.setUp();");
		
		if (ServiceUtil.isStateful(service)) {
			buf.put(createOperation_Setup_CacheFields(service, false));
			buf.put(createOperation_Setup_CacheFields(service, true));
			//buf.putLine("");
			//buf.put(createSetup_ConditionsForFields(service, false));
			buf.put(createOperation_Setup_ConditionsForFields(service, true));
			//buf.putLine("");
			buf.put(createOperation_Setup_ClientFields(service));
			buf.put(createOperation_Setup_ObjectFactoryFields(service));
			//buf.putLine("");
			buf.put(createOperation_Setup_AddProcessInstance(service));
			buf.put(createOperation_Setup_AddProxyInstances(service));
		}
		
		//mockBookshopObjectFactory = mock(bookshop.ObjectFactory.class);

		String fileNamePrefix = ModuleUtil.getFileNamePrefix(context.getModule());
		String eventsFileName = fileNamePrefix + "-checks.xml";
		buf.putLine2("CheckpointManager.addConfiguration(\""+eventsFileName+"\");");
		//buf.putLine2("MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();"); 
		//buf.putLine2("MBeanUtil.setMBeanServer(mbeanServer);");
		buf.putLine2("startService();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected String createSetup_CacheFields(Service service) throws Exception {
		Buf buf = new Buf();
		Process process = service.getProcess();
		Iterator<Cache> iterator = process.getCacheUnits().iterator();
		while (iterator.hasNext()) {
			Cache cache = (Cache) iterator.next();
			String cacheType = cache.getType();
			String cacheName = NameUtil.uncapName(cache.getName());
			String cacheClassName = TypeUtil.getClassName(cacheType);
			buf.putLine2("mock"+cacheClassName+" = mock("+cacheClassName+".class);");
		}
		return buf.get();
	}

	protected String createOperation_Setup_CacheFields(Service service, boolean isManager) throws Exception {
		Buf buf = new Buf();
		Process process = service.getProcess();
		Iterator<Cache> iterator = process.getCacheUnits().iterator();
		while (iterator.hasNext()) {
			Cache cache = (Cache) iterator.next();
			String cacheType = cache.getType();
			String className = TypeUtil.getClassName(cacheType);
			String cacheName = className;
			if (isManager)
				className += "Manager";
			buf.putLine2("mock"+className+" = mock("+className+".class);");
		}
		return buf.get();
	}

	protected String createOperation_Setup_ConditionsForFields(Service service, boolean isManager) throws Exception {
		Buf buf = new Buf();
		Process process = service.getProcess();
		Iterator<Cache> iterator = process.getCacheUnits().iterator();
		while (iterator.hasNext()) {
			Cache cache = (Cache) iterator.next();
			String cacheType = cache.getType();
			String className = TypeUtil.getClassName(cacheType);
			String cacheName = className;
			if (isManager)
				className += "Manager";
			if (isManager)
				buf.putLine2("when(mock"+className+".get"+cacheName+"()).thenReturn(mock"+cacheName+");");
		}
		return buf.get();
	}
	
	protected String createOperation_Setup_ClientFields(Service service) {
		Buf buf = new Buf();
		Process process = service.getProcess();
		String operationName = service.getName();
		Operation operation = ProcessUtil.getOperation(process, operationName);
	
		if (operation != null) {
			Project project = context.getProject();
			List<Service> remoteServices = ProjectUtil.getRemoteEndpoints(project, service, operation);
			Iterator<Service> iterator = remoteServices.iterator();
			while (iterator.hasNext()) {
				Service remoteService = iterator.next();
				String packageName = context.getPackageName(remoteService);
				String interfaceName = remoteService.getInterfaceName();
				String className = interfaceName + "Client";
				buf.putLine2("mock"+className+" = mock("+packageName+"."+className+".class);");

			}
		}
		return buf.get();
	}

	protected String createOperation_Setup_ObjectFactoryFields(Service service) {
		Buf buf = new Buf();
		Process process = service.getProcess();
		String operationName = service.getName();
		Operation operation = ProcessUtil.getOperation(process, operationName);
		if (operation != null) {
			List<Parameter> parameters = operation.getParameters();
			Iterator<Parameter> parameterIterator = parameters.iterator();
			while (parameterIterator.hasNext()) {
				Parameter parameter = parameterIterator.next();
	
				String parameterType = parameter.getType();
				String parameterName = NameUtil.uncapName(parameter.getName());
				String parameterClassName = TypeUtil.getClassName(parameterType);
				String parameterPackageName = TypeUtil.getPackageName(parameter.getType());
				String parameterFixtureName = FixtureUtil.getFixtureNameFromPackageName(parameterPackageName);
				Element parameterElement = context.getElementByName(parameterClassName);
				String mockObjectFactoryName = "mock" + NameUtil.capName(parameterPackageName) + "ObjectFactory";
	
				buf.putLine2(mockObjectFactoryName+" = mock("+parameterPackageName+".ObjectFactory.class);");
			}
		}
		return buf.get();
	}
	
	protected String createOperation_Setup_AddProcessInstance(Service service) {
		Buf buf = new Buf();
		Process process = service.getProcess();
		String processName = ServiceLayerHelper.getProcessName(process);
		String processClassName = NameUtil.capName(processName);
		buf.putLine2("addProcessInstance(create"+processClassName+"());");
		return buf.get();
	}

	protected String createOperation_Setup_AddProxyInstances(Service service) {
		Buf buf = new Buf();
		Process process = service.getProcess();
		String operationName = service.getName();
		Operation operation = ProcessUtil.getOperation(process, operationName);
	
		if (operation != null) {
			Project project = context.getProject();
			List<Service> remoteServices = ProjectUtil.getRemoteEndpoints(project, service, operation);
			Iterator<Service> iterator = remoteServices.iterator();
			while (iterator.hasNext()) {
				Service remoteService = iterator.next();
				String interfaceName = remoteService.getInterfaceName();
				String className = interfaceName + "Client";
				buf.putLine2("addProxyInstance(\""+interfaceName+"\", mock"+className+");");
			}
		}
		return buf.get();
	}


	protected ModelOperation createOperation_Teardown(Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("tearDown");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("MBeanUtil.unregisterMBeans();");
		//buf.putLine2("mockWebServiceContext = null;");
		buf.putLine2("BeanContext.clear();");
		buf.putLine2("super.tearDown();");
		buf.putLine2("stopService();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	
	protected ModelOperation createProcessFactoryOperation(Service service) {
		Process process = service.getProcess();
		String processName = NameUtil.uncapName(process.getName());
		String processClassName = NameUtil.capName(process.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(processClassName);
		modelOperation.setName("create"+processClassName);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2(""+processName+" = new "+processClassName+"();");
		List<Cache> cacheUnits = process.getCacheUnits();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			String cacheType = cache.getType();
			String cacheName = NameUtil.uncapName(cache.getName());
			String cacheClassName = TypeUtil.getClassName(cacheType);
			buf.putLine2(""+processName+".get"+cacheClassName+"Manager().set"+cacheClassName+"(create"+cacheClassName+"());");
			buf.putLine2("FieldUtil.setFieldValue("+processName+", \""+cacheName+"Manager\", mock"+cacheClassName+"Manager);");
		}
		
		String serviceOperationName = service.getName();
		Operation serviceOperation = ProcessUtil.getOperation(process, serviceOperationName);
		if (serviceOperation != null) {
			List<Parameter> parameters = serviceOperation.getParameters();
			Iterator<Parameter> parameterIterator = parameters.iterator();
			while (parameterIterator.hasNext()) {
				Parameter parameter = parameterIterator.next();

				String parameterType = parameter.getType();
				//String parameterName = NameUtil.uncapName(parameter.getName());
				//String parameterClassName = TypeUtil.getClassName(parameterType);
				String parameterPackageName = TypeUtil.getPackageName(parameter.getType());
				//String parameterFixtureName = FixtureUtil.getFixtureNameFromPackageName(parameterPackageName);
				//Element parameterElement = context.getElementByName(parameterClassName);

				String objectFactoryName = NameUtil.uncapName(parameterPackageName) + "ObjectFactory";
				String mockObjectFactoryName = "mock" + NameUtil.capName(objectFactoryName);
				
				buf.putLine2("FieldUtil.setFieldValue("+processName+", \""+objectFactoryName+"\", "+mockObjectFactoryName+");");
			}
		}
		
		buf.putLine2("return "+processName+";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createCacheFactoryOperations(Service service) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Process process = service.getProcess();
		List<Cache> cacheUnits = process.getCacheUnits();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			ModelOperation cacheFactoryOperation = createCacheFactoryOperation(service, cache);
			modelOperations.add(cacheFactoryOperation);
		}
		return modelOperations;
	}
	
	protected ModelOperation createCacheFactoryOperation(Service service, Cache cache) {
		String cacheType = cache.getType();
		String cacheName = NameUtil.uncapName(cache.getName());
		String cacheClassName = TypeUtil.getClassName(cacheType);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(cacheClassName);
		modelOperation.setName("create"+cacheClassName);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2(cacheName+" = new "+cacheClassName+"();");
		buf.putLine2("return "+cacheName+";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createStartServiceOperation(Service service) {
		String launcherPackageName = ServiceLayerHelper.getServicePackageName(service);
		String launcherClassName = service.getInterfaceName() + "Launcher";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("startService");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2(launcherPackageName+"."+launcherClassName+".INSTANCE.startup(hostName, httpPort);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createStopServiceOperation(Service service) {
		String launcherPackageName = ServiceLayerHelper.getServicePackageName(service);
		String launcherClassName = service.getInterfaceName() + "Launcher";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("stopService");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2(launcherPackageName+"."+launcherClassName+".INSTANCE.shutdown();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
	protected List<ModelOperation> createOperations_RunTest_Commit(ModelClass modelClass, Service service) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			//TODO NEED to have clear way to determine 
			//transactionality on a per-operation basis
			if (operation.getName().startsWith("get"))
				continue;
			modelOperations.add(createOperation_RunTest_Commit(modelClass, service, operation));
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_RunTest_Commit(ModelClass modelClass, Service service, Operation operation) {
		String packageName = context.getPackageName(service);
		String className = service.getInterfaceName();
		String serviceName = NameUtil.uncapName(className);
		
//		if (serviceName.equalsIgnoreCase("OrderBooks"))
//			System.out.println();
//		if (serviceName.equalsIgnoreCase("OrderRejected"))
//			System.out.println();

		String operationName = NameUtil.uncapName(operation.getName());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("testInvoke_Commit_"+operationName);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		Project project = context.getProject();
		//Map<String, Operation> operations = new LinkedHashMap<String, Operation>();
		Map<String, SOPInstance> parametersForServiceOperation = new LinkedHashMap<String, SOPInstance>();
		Map<String, SOPInstance> parametersForRemoteOperations = new LinkedHashMap<String, SOPInstance>();
		Map<String, Parameter> distinctParameters = new LinkedHashMap<String, Parameter>();
		//Operation operation = ProcessUtil.getOperation(process, operationName);
		//operations.put(operation.getName(), operation);
		
		parametersForServiceOperation.putAll(getParametersForOperation(service, operation));
		parametersForRemoteOperations.putAll(getParametersForService(service));
		distinctParameters.putAll(getDistinctParameters(parametersForServiceOperation));
		distinctParameters.putAll(getDistinctParameters(parametersForRemoteOperations));
		
		Set<String> parameterKeys = distinctParameters.keySet();
		Iterator<String> sopInstanceIterator = parameterKeys.iterator();
		while (sopInstanceIterator.hasNext()) {
			String parameterKey = sopInstanceIterator.next();
			Parameter parameter = distinctParameters.get(parameterKey);

			String parameterType = parameter.getType();
			String parameterName = NameUtil.uncapName(parameter.getName());
			String parameterClassName = TypeUtil.getClassName(parameterType);
			String parameterPackageName = TypeUtil.getPackageName(parameter.getType());
			String parameterFixtureName = FixtureUtil.getFixtureNameFromPackageName(parameterPackageName);
			//Element parameterElement = context.getElementByName(parameterClassName);
			//String parameterString = ParameterUtil.getArgumentString(operation);

			buf.putLine2(parameterClassName+" "+parameterName+" = "+parameterFixtureName+".create"+parameterClassName+"();");
			modelClass.addImportedClass(parameterPackageName + "." + parameterClassName);
			modelClass.addImportedClass(parameterPackageName + "." + parameterFixtureName);
		}
		
		//buf.putLine("");
		parameterKeys = distinctParameters.keySet();
		sopInstanceIterator = parameterKeys.iterator();
		while (sopInstanceIterator.hasNext()) {
			String parameterKey = sopInstanceIterator.next();
			Parameter parameter = distinctParameters.get(parameterKey);
	
			String parameterType = parameter.getType();
			String parameterName = NameUtil.uncapName(parameter.getName());
			String parameterClassName = TypeUtil.getClassName(parameterType);
			String parameterPackageName = TypeUtil.getPackageName(parameter.getType());
			String parameterFixtureName = FixtureUtil.getFixtureNameFromPackageName(parameterPackageName);
			Element parameterElement = context.getElementByName(parameterClassName);

			String factoryPrefix = NameUtil.getSimpleName(parameterPackageName);
			factoryPrefix = factoryPrefix.replace(".", "_");
			factoryPrefix = NameUtil.toCamelCase(factoryPrefix);
			
			String mockObjectFactoryName = "mock" + factoryPrefix + "ObjectFactory";
			buf.putLine2("when("+mockObjectFactoryName+".create"+parameterClassName+"()).thenReturn("+parameterName+");");
		}
		
//		endpointIterator = remoteEndpoints.iterator();
//		while (endpointIterator.hasNext()) {
//			Service endpoint = endpointIterator.next();
//			//String endpointPackageName = context.getPackageName(endpoint);
//			String endpointClassName = endpoint.getInterfaceName();
//			//String endpointBeanName = NameUtil.uncapName(endpointClassName);
//
//			Process endpointProcess = endpoint.getProcess();
//			String endpointOperationName = endpoint.getName();
//			operation = ProcessUtil.getOperation(endpointProcess, endpointOperationName);
//			if (operation == null)
//				continue;
//			
//			parameters = operation.getParameters();
//			parameterIterator = parameters.iterator();
//			while (parameterIterator.hasNext()) {
//				Parameter parameter = parameterIterator.next();
//
//				String parameterType = parameter.getType();
//				String parameterName = NameUtil.uncapName(parameter.getName());
//				String parameterClassName = TypeUtil.getClassName(parameterType);
//				String parameterPackageName = TypeUtil.getPackageName(parameter.getType());
//				String parameterFixtureName = FixtureUtil.getFixtureNameFromPackageName(parameterPackageName);
//				Element parameterElement = context.getElementByName(parameterClassName);
//
//				String mockObjectFactoryName = "mock" + NameUtil.capName(parameterPackageName) + "ObjectFactory";
//				buf.putLine2("when("+mockObjectFactoryName+".create"+parameterClassName+"()).thenReturn("+parameterName+");");
//			}
//		}

//			buf.putLine2("int expectedSize = 2;");
//			buf.putLine2("Set<Book> books = new HashSet<Book>(BookshopFixture.createBook_List(expectedSize));");
//			buf.putLine2("//when(mockBookCache.getAvailableBooks()).thenReturn(books);");
//			buf.putLine2("OrderAcceptedMessage orderAcceptedMessage = BookshopFixture.createOrderAcceptedMessage();");
//			buf.putLine2("ReservationRequestMessage reservationRequestMessage = BookshopFixture.createReservationRequestMessage();");
//			buf.putLine2("when(mockBookshopObjectFactory.createOrderAcceptedMessage()).thenReturn(orderAcceptedMessage);");
//			buf.putLine2("when(mockBookshopObjectFactory.createReservationRequestMessage()).thenReturn(reservationRequestMessage);");
		
		
		if (ServiceUtil.isStateful(service)) {
			String clientClassName = className + "Client";
			String clientOperationName = service.getName();
			Process process = service.getProcess();
			Operation clientOperation = ProcessUtil.getOperation(process, clientOperationName);
//			if (clientOperation == null)
//				System.out.println();
			if (clientOperation == null)
				clientOperation = ServiceUtil.getOperation(service, clientOperationName);
			
			List<Parameter> clientParameters = clientOperation.getParameters();
			String parameterString = ParameterUtil.getArgumentString(clientOperation);

			buf.putLine2("");
			buf.putLine2("UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();");
			buf.putLine2("userTransaction.begin();");
			buf.putLine2("");
			buf.putLine2("try {");
			
			Iterator<Parameter> iterator = clientParameters.iterator();
			while (iterator.hasNext()) {
				Parameter parameter = iterator.next();
				String parameterType = parameter.getType();
				String parameterName = NameUtil.uncapName(parameter.getName());
				String parameterClassName = TypeUtil.getClassName(parameterType);
				String parameterPackageName = TypeUtil.getPackageName(parameterType);
				String parameterFixtureName = FixtureUtil.getFixtureNameFromPackageName(parameterPackageName);
				//Element clientParameterElement = context.getElementByName(messageClassName);
				//buf.putLine2("	"+parameterClassName+" "+parameterName+" = "+parameterFixtureName+".create"+parameterClassName+"();");
				modelClass.addImportedClass(parameterPackageName + "." + parameterFixtureName);
			}
			
			buf.putLine2("	"+clientClassName+" client = new "+clientClassName+"(hostName, httpPort);");
			buf.putLine2("	client.setCorrelationId(correlationId);");
			buf.putLine2("	client."+operationName+"("+parameterString+");");
			buf.putLine2("");
			buf.putLine2("	boolean status = taskExecutorService.waitForTermination();");
			buf.putLine2("	Assert.isTrue(status, \"Unexpected result status\");");
			buf.putLine2("");
			buf.putLine2("	//TODO verify conditions for commit");
			buf.putLine2("	userTransaction.commit();");
			buf.putLine2("");
			buf.putLine2("} finally {");
			buf.putLine2("	if (userTransaction.getTransactionId() != null)");
			buf.putLine2("		userTransaction.rollback();");
			buf.putLine2("}");
			
			Set<String> sopInstanceKeys = parametersForRemoteOperations.keySet();
			sopInstanceIterator = sopInstanceKeys.iterator();
			while (sopInstanceIterator.hasNext()) {
				String sopInstanceKey = sopInstanceIterator.next();
				SOPInstance sopInstance = parametersForRemoteOperations.get(sopInstanceKey);

				Service endpoint = sopInstance.getService();
				String endpointClassName = endpoint.getInterfaceName();
				
				Operation endpointOperation = sopInstance.getOperation();
				operationName = endpointOperation.getName();
		
				Parameter parameter = sopInstance.getParameter();
				String parameterType = parameter.getType();
				String parameterName = NameUtil.uncapName(parameter.getName());
				String parameterClassName = TypeUtil.getClassName(parameterType);
				String parameterPackageName = TypeUtil.getPackageName(parameter.getType());
				String parameterFixtureName = FixtureUtil.getFixtureNameFromPackageName(parameterPackageName);
				Element parameterElement = context.getElementByName(parameterClassName);
				String mockClientClassName = "mock" + endpointClassName + "Client";

				buf.putLine2("");
				buf.putLine2("verify("+mockClientClassName+")."+operationName+"("+parameterName+");");
				buf.putLine2("verify("+mockClientClassName+").setCorrelationId(correlationId);");
				buf.putLine2("verifyNoMoreInteractions("+mockClientClassName+");");
			}
		
		} else {
			
		}
		
//		remoteEndpoints = ProjectUtil.getRemoteEndpoints(project, service);
//		endpointIterator = remoteEndpoints.iterator();
//		while (endpointIterator.hasNext()) {
//			Service endpoint = endpointIterator.next();
//			//String endpointPackageName = context.getPackageName(endpoint);
//			String endpointClassName = endpoint.getInterfaceName();
//			//String endpointBeanName = NameUtil.uncapName(endpointClassName);
//
//			Process endpointProcess = endpoint.getProcess();
//			String endpointOperationName = endpoint.getName();
//			operation = ProcessUtil.getOperation(endpointProcess, endpointOperationName);
//			if (operation == null)
//				continue;
//
//			operationName = operation.getName();
//			List<Parameter> parameters = operation.getParameters();
//			Iterator<Parameter> parameterIterator = parameters.iterator();
//			while (parameterIterator.hasNext()) {
//				Parameter parameter = parameterIterator.next();
//
//				//String parameterType = parameter.getType();
//				String parameterName = NameUtil.uncapName(parameter.getName());
//				//String parameterClassName = TypeUtil.getClassName(parameterType);
//				//String parameterPackageName = TypeUtil.getPackageName(parameterType);
//				//String parameterFixtureName = FixtureUtil.getFixtureNameFromPackageName(parameterPackageName);
//				//Element parameterElement = context.getElementByName(parameterClassName);
//				//String parameterString = ParameterUtil.getArgumentString(operation);
//				String mockClientClassName = "mock" + endpointClassName + "Client";
//
//				buf.putLine2("");
//				buf.putLine2("verify("+mockClientClassName+")."+operationName+"("+parameterName+");");
//				buf.putLine2("verify("+mockClientClassName+").setCorrelationId(correlationId);");
//				buf.putLine2("verifyNoMoreInteractions("+mockClientClassName+");");
//			}
//		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected Map<String, Parameter> getDistinctParameters(Map<String, SOPInstance> sopInstances) {
		Map<String, Parameter> map = new LinkedHashMap<String, Parameter>();
		Iterator<String> iterator = sopInstances.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			SOPInstance sopInstance = sopInstances.get(key);
			Parameter parameter = sopInstance.getParameter();
			map.put(parameter.getName(), parameter);
		}
		return map;
	}

	protected Map<String, SOPInstance> getParametersForService(Service service) {
		Map<String, SOPInstance> map = new LinkedHashMap<String, SOPInstance>();

		Project project = context.getProject();
		//Process process = service.getProcess();
		//String processName = NameUtil.uncapName(process.getName());
		//String processClassName = NameUtil.capName(process.getName());
		//String operationName = NameUtil.uncapName(service.getName());
		
		//Map<String, Operation> operations = new LinkedHashMap<String, Operation>();
		//Operation operation = ProcessUtil.getOperation(process, operationName);
		//operations.put(operation.getName(), operation);
		
		List<Service> remoteEndpoints = ProjectUtil.getRemoteEndpoints(project, service);
		Iterator<Service> endpointIterator = remoteEndpoints.iterator();
		while (endpointIterator.hasNext()) {
			Service endpoint = endpointIterator.next();
			//String endpointPackageName = context.getPackageName(endpoint);
			//String endpointClassName = endpoint.getInterfaceName();
			//String endpointBeanName = NameUtil.uncapName(endpointClassName);

			Process endpointProcess = endpoint.getProcess();
			String endpointOperationName = endpoint.getName();
			Operation operation = ProcessUtil.getOperation(endpointProcess, endpointOperationName);
			if (operation != null)
				map.putAll(getParametersForOperation(endpoint, operation));
		}
		return map;
	}

	protected Map<String, SOPInstance> getParametersForOperation(Service service, Operation operation) {
		Map<String, SOPInstance> map = new LinkedHashMap<String, SOPInstance>();
		
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			Parameter parameter = parameterIterator.next();
			String parameterName = NameUtil.uncapName(parameter.getName());
			SOPInstance tuple = new SOPInstance();
			tuple.setService(service);
			tuple.setOperation(operation);
			tuple.setParameter(parameter);
			map.put(parameterName, tuple);
		}
		
		return map;
	}
	
	
	protected class SOPInstance {
		
		private Service service;

		private Operation operation;

		private Parameter parameter;

		
		public Service getService() {
			return service;
		}

		public void setService(Service service) {
			this.service = service;
		}

		public Operation getOperation() {
			return operation;
		}

		public void setOperation(Operation operation) {
			this.operation = operation;
		}

		public Parameter getParameter() {
			return parameter;
		}

		public void setParameter(Parameter parameter) {
			this.parameter = parameter;
		}
	}
}
