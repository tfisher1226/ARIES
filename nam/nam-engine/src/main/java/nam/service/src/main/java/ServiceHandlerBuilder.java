package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.data.DataLayerFactory;
import nam.data.DataLayerHelper;
import nam.model.Application;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Element;
import nam.model.Fault;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.Project;
import nam.model.Result;
import nam.model.Service;
import nam.model.Unit;
import nam.model.util.ApplicationUtil;
import nam.model.util.CacheUtil;
import nam.model.util.ElementUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ParameterUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;
import nam.service.ServiceLayerFactory;
import nam.service.ServiceLayerHelper;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Builds a Service Transaction Participant {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceHandlerBuilder extends AbstractBeanBuilder {

	public ServiceHandlerBuilder(GenerationContext context) {
		super(context);
	}


	/*
	 * Interface creation
	 */

	public ModelInterface buildInterface(Service service) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String interfaceName = ServiceLayerHelper.getHandlerInterfaceName(service);
		String instanceName = ServiceLayerHelper.getHandlerInstanceName(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		
		ModelInterface modelInterface = new ModelInterface();
		modelInterface.setNamespace(namespace);
		modelInterface.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, instanceName));
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		modelInterface.addExtendedInterface("Transactional");
		modelInterface.addImportedClass("org.aries.tx.Transactional");
		Fault fault = getFault(service);
		initializeInterface(modelInterface, service);
		return modelInterface;
	}
	
	public void initializeInterface(ModelInterface modelInterface, Service service) throws Exception {
		modelUnit = modelInterface;
		initializeImportedClasses(modelInterface, service);
		initializeInterfaceAnnotations(modelInterface, service);
		initializeInterfaceMethods(modelInterface, service);
	}
	
	protected void initializeImportedClasses(ModelInterface modelInterface, Service service) throws Exception {
		//modelInterface.addImportedClass("javax.ejb.Local");
		modelInterface.addImportedClasses(CodeUtil.getImportedClasses(service));
		modelInterface.addImportedClass("org.aries.tx.Transactional");
	}

	protected void initializeInterfaceAnnotations(ModelInterface modelInterface, Service service) throws Exception {
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			//modelInterface.addInterfaceAnnotation(AnnotationUtil.createLocalAnnotation());
			break;
		}
	}

	protected void initializeInterfaceMethods(ModelInterface modelInterface, Service service) throws Exception {
		//modelInterface.addInstanceOperation(CodeUtil.createMethod_SetCorrelationId(modelInterface, true));
		//modelInterface.addInstanceOperation(CodeUtil.createMethod_SetTransactionId(modelInterface, true));
		List<Operation> operations = ServiceUtil.getOperations(service);
		if (operations.size() == 1) {
			modelInterface.addInstanceOperation(createMethod_Execute(modelInterface, service, operations.get(0), true));
		} else if (operations.size() > 0) {
			modelInterface.addInstanceOperations(createMethods_Execute(modelInterface, service, true));
		}
	}

	
	/*
	 * Class creation
	 */
	
	public ModelClass buildClass(Service service) throws Exception {
//		if (service.getProcess() != null) {
//			String processName = ProcessUtil.getProcessName(service.getProcess());
//			setBaseName(ProjectUtil.getBaseName(processName));
//		} else {
//			setBaseName(ProjectUtil.getBaseName(service.getName()));
//		}
		
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String interfaceName = ServiceLayerHelper.getHandlerInterfaceName(service);
		String className = ServiceLayerHelper.getHandlerClassName(service);
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
		modelClass.setParentClassName("AbstractServiceHandler");
		modelClass.addImportedClass("org.aries.tx.service.AbstractServiceHandler");
		modelClass.addImplementedInterface(interfaceName);
		//modelClass.addImplementedInterface("Transactional");
		//modelClass.addImportedClass("org.aries.tx.Transactional");
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		initializeClass(modelClass, service);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Service service) throws Exception {
		modelUnit = modelClass;
		initializeImportedClasses(modelClass, service);
		initializeClassAnnotations(modelClass, service);
		//initializeClassConstructors(modelClass, service);
		initializeInstanceFields(modelClass, service);
		initializeInstanceMethods(modelClass, service);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
		modelClass.addImportedClasses(CodeUtil.getImportedClasses(service));
		//modelClass.addImportedClass("org.aries.checkpoint.util.Check");
		if (!ServiceUtil.isStateful(service))
			modelClass.addImportedClass("org.aries.Assert");
		
		List<Fault> faults = ServiceUtil.getFaults(service);
		boolean hasApplicationFault = faults.size() > 0;
		if (!hasApplicationFault) {
			//modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		}
		
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
			modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
			modelClass.addStaticImportedClass("javax.ejb.TransactionManagementType.CONTAINER");

			modelClass.addImportedClass("javax.ejb.Local");
			//modelClass.addImportedClass("javax.ejb.Remove");
			//modelClass.addImportedClass("javax.ejb.SessionContext");
			modelClass.addImportedClass("javax.ejb.Stateful");
			modelClass.addImportedClass("javax.ejb.ConcurrencyManagement");
			modelClass.addImportedClass("javax.ejb.TransactionAttribute");
			modelClass.addImportedClass("javax.ejb.TransactionManagement");
			modelClass.addImportedClass("javax.inject.Inject");
			if (!ServiceUtil.isSynchronous(service)) {
				modelClass.addImportedClass("javax.ejb.Asynchronous");
				modelClass.addImportedClass("javax.ejb.AccessTimeout");
			}
			modelClass.addImportedClass("javax.interceptor.Interceptors");
			modelClass.addImportedClass("org.aries.tx.service.ServiceHandlerInterceptor");
			break;
			
		case POJO:
			modelClass.addImportedClass("org.aries.runtime.BeanContext");
			modelClass.addImportedClass("org.aries.registry.ProcessLocator");
			modelClass.addImportedClass("org.aries.tx.Transactional");
			modelClass.addImportedClass("common.tx.TransactionParticipantManager");
			break;
		}
		
		boolean hasTimeout = ServiceUtil.hasTimeout(service);
		if (service instanceof Callback == false && hasTimeout)
			modelClass.addImportedClass("org.aries.process.TimeoutHandler");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");

//		if (ServiceUtil.isSynchronous(service)) {
//			modelClass.addImportedClass("org.aries.util.ExceptionUtil");
//		}
		
		//cache units
		//if (service.getProcess() != null) {
		if (!ServiceUtil.isSynchronous(service)) {
			Process process = service.getProcess();
			if (process != null) {
				String namespace = ServiceUtil.getNamespace(service);
				List<Cache> cacheUnits = process.getCacheUnits();
				Iterator<Cache> iterator = cacheUnits.iterator();
				while (iterator.hasNext()) {
					Cache cache = iterator.next();
					String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cache);
					String className = CacheUtil.getClassName(cache) + "Manager";
					modelClass.addImportedClass(packageName+"."+className);
				}
			}
		}
		
//		//persistence units
//		if (!ServiceUtil.isStateful(service)) {
//			String serviceType = service.getElement();
//			String elementName = NameUtil.capName(TypeUtil.getLocalPart(serviceType));
//			String repositoryPackageName = ProjectLevelHelper.getPackageName(service.getNamespace());
//			modelClass.addImportedClass(repositoryPackageName+"."+elementName+"Repository");
//		}
	}

	protected void initializeClassAnnotations(ModelClass modelClass, Service service) throws Exception {
		String handlerInterfaceName = ServiceLayerHelper.getHandlerInterfaceName(service);
		String contextName = NameUtil.uncapName(modelClass.getName());
		if (context.isEnabled("generateServicePerOperation"))
			contextName = NameUtil.uncapName(service.getName()) + "." + contextName;
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();

		List<Fault> faults = ServiceUtil.getFaults(service);
		Iterator<Fault> iterator = faults.iterator();
		while (iterator.hasNext()) {
			Fault fault = iterator.next();
			String faultPackageName = TypeUtil.getPackageName(fault.getType());
			String faultClassName = TypeUtil.getClassName(fault.getType());
			modelClass.addImportedClass(faultPackageName + "." + faultClassName);
		}

		switch (context.getServiceLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createStatefulAnnotation());
			classAnnotations.add(AnnotationUtil.createLocalAnnotation(handlerInterfaceName));
			classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
			classAnnotations.add(AnnotationUtil.createTransactionManagementAnnotation());
			classAnnotations.add(AnnotationUtil.createInterceptorsAnnotation("ServiceHandlerInterceptor.class"));
			break;
			
		case SEAM:
			//modelClass.addImportedClass("org.jboss.seam.ScopeType");
			//modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
			//modelClass.addImportedClass("org.jboss.seam.annotations.Name");
			//modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
			//modelClass.addImportedClass("org.jboss.seam.annotations.In");
			//modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");

			//classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
			////classAnnotations.add(AnnotationUtil.createSuppressSerialWarning());
			//classAnnotations.add(AnnotationUtil.createBypassInterceptorsAnnotation());
			//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
			//if (context.isEnabled("useQualifiedContextNames")) {
			//	String contextPrefix = NameUtil.getQualifiedContextNamePrefix(modelClass.getQualifiedName(), 2);
			//	classAnnotations.add(AnnotationUtil.createNameAnnotation(contextPrefix + "." + contextName));
			//} else classAnnotations.add(AnnotationUtil.createNameAnnotation(contextName));
			break;
		}
	}
	
	//NOTTUSED
	protected void initializeClassConstructors(ModelClass modelClass, Service service) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		constructor.addParameter(CodeUtil.createParameter_CorrelationId());
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"this.correlationId = correlationId;"
		});
		constructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(constructor);
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		//CodeUtil.addSessionContextField(modelClass, service);

		if (ServiceUtil.isStateful(service)) {
			ServiceLayerFactory.addReference_StatefulContext(modelClass, service);
			ServiceLayerFactory.addReference_StatefulProcess(modelClass, service);
		}
		
		String className = ServiceLayerHelper.getHandlerClassName(service);
		CodeUtil.addStaticLoggerField(modelClass, className);

		String serviceType = service.getElement();
		ModelAttribute repositoryAttribute = null;
		if (!ServiceUtil.isStateful(service) && serviceType != null) {
			Application application = context.getApplication();
			Collection<Module> dataModules = ApplicationUtil.getDataModules(application);
			Iterator<Module> iterator = dataModules.iterator();
			while (iterator.hasNext()) {
				Module module = iterator.next();
				Persistence persistence = module.getPersistence();
				List<Unit> units = PersistenceUtil.getUnits(persistence);
				Iterator<Unit> iterator2 = units.iterator();
				while (iterator2.hasNext()) {
					Unit unit = iterator2.next();
					String localPart = TypeUtil.getLocalPart(serviceType);
					if (UnitUtil.containsElementByName(unit, localPart)) {
						repositoryAttribute = DataLayerFactory.createRepositoryAttribute(service, unit);
						break;
					}
				}
			}
			
			//TODO check if repositoryAttribute was NOT created
			Assert.notNull(repositoryAttribute, "Repository not found for: "+serviceType);
			modelClass.addInstanceAttribute(repositoryAttribute);
		
//		if (!ServiceUtil.isStateful(service) && serviceType != null) {
//			Element element = getElementForServiceType(serviceType);
//			Application application = context.getApplication();
//			Set<Module> dataModules = ApplicationUtil.getDataModules(application);
//			Iterator<Module> iterator = dataModules.iterator();
//			while (iterator.hasNext()) {
//				Module module = iterator.next();
//				Persistence persistence = module.getPersistence();
//				List<Unit> units = PersistenceUtil.getUnits(persistence);
//				Iterator<Unit> iterator2 = units.iterator();
//				while (iterator2.hasNext()) {
//					Unit unit = iterator2.next();
//					if (UnitUtil.containsElement(unit, element)) {
//						modelClass.addInstanceAttribute(DataLayerFactory.createRepositoryAttribute(service, unit));
//						break;
//					}
//				}
//			}
			
		} else if (ServiceUtil.isStateful(service)) {
			Process process = service.getProcess();
			String namespace = process.getNamespace();
			List<Cache> cacheUnits = process.getCacheUnits();
			Iterator<Cache> iterator = cacheUnits.iterator();
			while (iterator.hasNext()) {
				Cache cache = iterator.next();
				CodeUtil.addCacheUnitManagerField(modelClass, namespace, cache);
			}
		}
		
//		if (service.getProcess() != null)
//			modelClass.addInstanceAttribute(createManagerAttribute(service));
		CodeUtil.addTransactionSynchronizationRegistryField(modelClass);
	}
	
//	public static ModelAttribute createManagerAttribute(Service service) {
//		Process process = service.getProcess();
//		String processName = ProcessUtil.getProcessName(process);
//		String packageName = NameUtil.getPackageFromNamespace(getNamespace(process));
//		String className = GenerateUtil.getBaseName(processName) + "StateManager";
//		ModelAttribute attribute = new ModelAttribute();
//		attribute.setModifiers(Modifier.PRIVATE);
//		attribute.setPackageName(packageName);
//		attribute.setClassName(className);
//		attribute.setName("stateManager");
//		attribute.setValue("new "+className+"()");
//		attribute.setGenerateGetter(true);
//		attribute.setGenerateSetter(true);
//		return attribute;
//	}

	private Element getElementForServiceType(String serviceType) {
		Element element = null;
		Application application = context.getApplication();
		Collection<Module> dataModules = ApplicationUtil.getDataModules(application);
		Iterator<Module> iterator = dataModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Persistence persistence = module.getPersistence();
			List<Unit> units = PersistenceUtil.getUnits(persistence);
			Iterator<Unit> iterator2 = units.iterator();
			while (iterator2.hasNext()) {
				Unit unit = iterator2.next();
				String localPart = TypeUtil.getLocalPart(serviceType);
				element = UnitUtil.getElementByName(unit, localPart);
				if (element != null) {
					break;
				}
			}
		}
		
		if (element == null) {
			String localPart = TypeUtil.getLocalPart(serviceType);
			element = context.getElementByName(localPart);
		}
		Assert.notNull(element, "Element not found for service: "+serviceType);
		return element;
	}


	protected void initializeInstanceMethods(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceOperation(createMethod_GetName(modelClass, service));
		modelClass.addInstanceOperation(createMethod_GetDomain(modelClass, service));
		if (ServiceUtil.isStateful(service)) {
			modelClass.addInstanceOperation(createMethod_Prepare(modelClass, service));
			modelClass.addInstanceOperation(createMethod_Commit(modelClass, service));
			modelClass.addInstanceOperation(createMethod_Rollback(modelClass, service));
			//modelClass.addInstanceOperations(createMethods_GetManagers(modelClass, service));
		}
		
		switch (context.getServiceLayerBeanType()) {
		case POJO:
			if (ServiceUtil.isStateful(service))
				modelClass.addInstanceOperation(createMethod_GetProcess(modelClass, service));
			break;
		}

		boolean hasTimeout = ServiceUtil.hasTimeout(service);
		List<Operation> operations = ServiceUtil.getOperations(service);
		if (operations.size() == 1) {
			Operation operation = operations.get(0);
			modelClass.addInstanceOperation(createMethod_Execute(modelClass, service, operation, false));
			if (service instanceof Callback == false && hasTimeout)
				modelClass.addInstanceOperation(createMethod_TimeoutHandler(modelUnit, service, operation, false));
		} else if (operations.size() > 0) {
			modelClass.addInstanceOperations(createMethods_Execute(modelClass, service, false));
		}
	}
	
	protected ModelOperation createMethod_GetName(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getName");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("java.lang.String");
		modelOperation.addInitialSource(CodeUtil.createMethodSource("return \""+NameUtil.capName(service.getName())+"\";\n"));
		return modelOperation;
	}

	protected ModelOperation createMethod_GetDomain(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getDomain");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("java.lang.String");
		modelOperation.addInitialSource(CodeUtil.createMethodSource("return \""+service.getDomain()+"\";\n"));
		return modelOperation;
	}

	//TODO handle case when more than one cache is present
	protected ModelOperation createMethod_Prepare(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("prepare");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("boolean");
		modelOperation.getParameters().add(CodeUtil.createParameter_TransactionId());
		
		Buf buf = new Buf();
		if (ServiceUtil.isStateful(service)) {
			List<Cache> cacheUnits = service.getProcess().getCacheUnits();
			if (cacheUnits.size() == 0) {
				buf.putLine2("return true;");
				
			} else if (cacheUnits.size() > 1) {
				//TODO do this right...
				buf.putLine2("boolean status = true;");
				//buf.putLine2("return true;");
			}
		
			Iterator<Cache> iterator = cacheUnits.iterator();
			while (iterator.hasNext()) {
				Cache cache = iterator.next();
				String cacheType = cache.getType();
				String cacheName = NameUtil.uncapName(cache.getName());
				String className = TypeUtil.getClassName(cacheType);
				String managerName = NameUtil.uncapName(className) + "Manager";
				if (cacheUnits.size() == 1) {
					buf.putLine2("return "+managerName+".prepare(transactionId);");
					//buf.putLine2("return "+cacheName+"Status;");

				} else {
					buf.putLine2("boolean "+cacheName+"Status = "+managerName+".prepare(transactionId);");
					buf.putLine2("status &= "+cacheName+"Status;");
				}
			}
			
			if (cacheUnits.size() > 1)
				buf.putLine2("return status;");
			
		} else {
			buf.putLine2("return true;");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createMethod_Commit(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("commit");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.getParameters().add(CodeUtil.createParameter_TransactionId());
		
		Buf buf = new Buf();
		if (ServiceUtil.isStateful(service)) {
			List<Cache> cacheUnits = service.getProcess().getCacheUnits();
			Iterator<Cache> iterator = cacheUnits.iterator();
			while (iterator.hasNext()) {
				Cache cache = iterator.next();
				String cacheType = cache.getType();
				String className = TypeUtil.getClassName(cacheType);
				String managerName = NameUtil.uncapName(className) + "Manager";
				buf.putLine2(managerName+".commit(transactionId);");
			}
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createMethod_Rollback(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("rollback");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.getParameters().add(CodeUtil.createParameter_TransactionId());
		
		Buf buf = new Buf();
		if (ServiceUtil.isStateful(service)) {
			List<Cache> cacheUnits = service.getProcess().getCacheUnits();
			Iterator<Cache> iterator = cacheUnits.iterator();
			while (iterator.hasNext()) {
				Cache cache = iterator.next();
				String cacheType = cache.getType();
				String className = TypeUtil.getClassName(cacheType);
				String managerName = NameUtil.uncapName(className) + "Manager";
				buf.putLine2(managerName+".rollback(transactionId);");
			}
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<ModelOperation> createMethods_GetManagers(ModelClass modelClass, Service service) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		if (ServiceUtil.isStateful(service)) {
			List<Cache> cacheUnits = service.getProcess().getCacheUnits();
			Iterator<Cache> iterator = cacheUnits.iterator();
			while (iterator.hasNext()) {
				Cache cache = iterator.next();
				modelOperations.add(createMethod_GetManager(modelClass, service, cache));
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createMethod_GetManager(ModelClass modelClass, Service service, Cache cache) throws Exception {
		String cacheType = cache.getType();
		//String cacheName = NameUtil.uncapName(cache.getName());
		String className = TypeUtil.getClassName(cacheType);
		//String packageName = TypeUtil.getPackageName(cacheType);

		Process process = service.getProcess();
		String processInstanceName = ServiceLayerHelper.getProcessInstanceName(process);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+className+"Manager");
		modelOperation.setResultType(className + "Manager");
		modelOperation.addInitialSource(CodeUtil.createMethodSource("return "+processInstanceName+".get"+className+"Manager();\n"));
		return modelOperation;
	}

	protected ModelOperation createMethod_GetProcess(ModelClass modelClass, Service service) throws Exception {
		String className = ServiceLayerHelper.getProcessClassName(service.getProcess());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("getProcess");
		modelOperation.setResultType(className);
		List<String> sourceLines = new ArrayList<String>();
		sourceLines.add("ProcessLocator processLocator = BeanContext.get(\"org.aries.processLocator\");");
		sourceLines.add(className + " process = processLocator.lookup("+className+".class, correlationId);");
		sourceLines.add("return process;");
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		return modelOperation;
	}

	protected List<ModelOperation> createMethods_Execute(ModelUnit modelUnit, Service service, boolean isInterface) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			modelOperations.add(createMethod_Execute(modelUnit, service, operation, isInterface));
//			if (!isInterface && service instanceof Callback == false)
//				modelOperations.add(createMethod_TimeoutHandler(modelUnit, service, operation, isInterface));
		}
		return modelOperations;
	}
	
	//TODO provide for additional operations - now just handling the first
	protected ModelOperation createMethod_Execute(ModelUnit modelUnit, Service service, Operation operation, boolean isInterface) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		if (!isInterface) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			if (!ServiceUtil.isSynchronous(service)) {
				modelOperation.addAnnotation(AnnotationUtil.createAsynchronousAnnotation());
				modelOperation.addAnnotation(AnnotationUtil.createAccessTimeoutAnnotation(60000));
			}
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation());
		}
		modelOperation.setName(operation.getName());
		modelOperation.setModifiers(Modifier.PUBLIC);
		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty())
			result = results.get(0);
		if (result != null && ServiceUtil.isSynchronous(service)) {
			String resultType = CodeUtil.getResultType(result);
			modelOperation.setResultType(resultType);
		} else modelOperation.setResultType("void");
		
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			ModelParameter modelParameter = CodeUtil.createParameter(parameters, parameter);
//			if (parameters.size() == 1 && !ServiceUtil.isSynchronous(service))
//				modelParameter.setName("message");
			modelOperation.getParameters().add(modelParameter);
			modelUnit.addImportedClass(TypeUtil.getJavaName(parameter.getType()));
		}
		Fault fault = getFault(service);
		if (fault != null) {
			modelOperation.addException(ServiceLayerHelper.getFaultClassName(fault));
			modelOperation.addImportedClass(ServiceLayerHelper.getFaultQualifiedName(fault));
		}
		if (!isInterface)
			modelOperation.addInitialSource(createMethodSource_Execute(modelUnit, service, operation));
		return modelOperation;
	}

	protected String createMethodSource_Execute(ModelUnit modelUnit, Service service, Operation operation) {
		String applicationName = context.getApplication().getName();
		String serviceInterface = ServiceLayerHelper.getServiceInterfaceName(service);
		boolean isManager = serviceInterface.endsWith("Manager");
		boolean isCallback = service instanceof Callback;
		boolean hasTimeout = ServiceUtil.hasTimeout(service);

		List<Fault> faults = ServiceUtil.getFaults(service);
		boolean hasApplicationFault = faults.size() > 0;
		int indent = 2;
		
		//buf.putLine2("Map<String, Serializable> conversationState = ConversationRegistry.getInstance().getConversationState(correlationId);");
		//buf.putLine2("supplierContext.validate(transactionSynchronizationRegistry);");
		//buf.putLine2("supplierContext.setCorrelationId(correlationId);");

		Buf buf = new Buf();
		if (isCallback)
			buf.putLine2("log.info(\"#### ["+applicationName+"]: "+serviceInterface+" response received\");");
		else buf.putLine2("log.info(\"#### ["+applicationName+"]: "+serviceInterface+" request received\");");
		
		if (!isCallback && hasTimeout)
			buf.putLine2("TimeoutHandler timeoutHandler = null;");
		
		buf.putLine2("");
		buf.putLine2("try {");
		indent++;
		
		String parameterString = ParameterUtil.getArgumentString(operation);
		//String parameterString = CodeUtil.getParameterString(operation.getParameters());
		
//		if (operation.getParameters().size() == 1 && !ServiceUtil.isSynchronous(service))
//			parameterString = "message";
		
		Process process = service.getProcess();
		if (process != null && !isManager) {
			String action = service instanceof Callback ? "response" : "request";
			String processContextName = ServiceLayerHelper.getProcessContextInstanceName(process);
			buf.putLine3(processContextName+".validate("+parameterString+");");
			buf.putLine3(processContextName+".fire_"+serviceInterface+"_"+action+"_received();");
		}
		
		//List<Parameter> parameters = operation.getParameters();
		//String parameterString = CodeUtil.getParameterString(operation.getParameters());
		//List<String> parameterNames = new ArrayList<String>();
		//Iterator<Parameter> iterator = parameters.iterator();
		//while (iterator.hasNext()) {
		//	Parameter parameter = iterator.next();
		//	String parameterName = parameter.getName();
		//	String checkpointName = parameter.getName();
		//	//if (checkpointName.endsWith("Message"))
		//	//	checkpointName = checkpointName.substring(0, checkpointName.length()-7);
		//	buf.putLine(indent, "Check.isValid(\""+checkpointName+"\", "+parameterName+");");
		//	parameterNames.add(parameterName);
		//}

		//buf.putLine(indent, "");
		//buf.putLine(indent, "Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();");
		//buf.putLine(indent, "String transactionId = transactionKey.toString();");
		//buf.putLine(indent, "setTransactionId(transactionId);");
		//buf.putLine(indent, "");
		
		if (ServiceUtil.isStateful(service)) {
			modelUnit.addImportedClass(ServiceLayerHelper.getProcessQualifiedName(process));
			modelUnit.addImportedClass(ServiceLayerHelper.getProcessContextQualifiedName(process));
		}
		
		if (ServiceUtil.isStateful(service)) {
			buf.put(createMethodSource_Execute_Stateful(service, operation, indent));
			
		} else if (service.getElement() != null) {
			buf.put(createMethodSource_Execute_Stateless(service, operation, indent));
		}
		
		//buf.putLine2(serviceName+"Processor processor = new "+serviceName+"Processor();");
		//buf.put2("processor.processRequest(correlationId, ");

		//Callback incomingCallback = context.getIncomingCallbackByName(serviceName);
		//Callback outgoingCallback = context.getOutgoingCallbackByName(serviceName);
		
		buf.putLine2("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(\"Error\", e);");
		if (!isCallback && hasTimeout) {
			buf.putLine2("	if (timeoutHandler != null)");
			buf.putLine2("		timeoutHandler.reset();");
		}
		buf.putLine2("	e = ExceptionUtil.getRootCause(e);");
		buf.putLine2("	transactionSynchronizationRegistry.setRollbackOnly();");
		
		if (process != null && !isManager) {
			String action = isCallback ? "response" : "request";
			if (!isCallback) {
				String processName = ServiceLayerHelper.getProcessInstanceName(process);
				buf.putLine2("	"+processName+".handle_"+serviceInterface+"_"+action+"_exception(e);");
			} else {
				String processContextName = ServiceLayerHelper.getProcessContextInstanceName(process);
				buf.putLine2("	"+processContextName+".fire_"+serviceInterface+"_incoming_"+action+"_aborted(e);");
			}
		}
		
		if (hasApplicationFault) {
			Fault fault = faults.get(0);
			String faultClassName = TypeUtil.getClassName(fault.getType());
			buf.putLine2("	//throw new "+faultClassName+"(e);");
			//buf.putLine2("	fault.setMessage(e.getMessage());");
			//buf.putLine2("	throw fault;");
			
		} else if (ServiceUtil.isSynchronous(service)) {
			buf.putLine2("	throw ExceptionUtil.rewrap(e);");
			//buf.putLine2("	RuntimeException exception = ExceptionUtil.rewrap(e);");
			//buf.putLine2("	throw exception;");
			
		} else {
			//buf.putLine2("	//throw ExceptionUtil.rewrap(e);");
		}

		if (!isCallback && hasTimeout) {
			buf.putLine2("	");	
			buf.putLine2("} finally {");
			buf.putLine2("	timeout = DEFAULT_TIMEOUT;");
			//buf.putLine2("	fireServiceInvokedNotification();");
			//buf.putLine2("	fireServiceInvokedNotification("+parameterString+");");
		}
		
		if (ServiceUtil.isStateful(context.getService())) {
			//buf.putLine2("	"+processName+".fire"+interfaceName+"Completed();");
			//buf.putLine2("	"+processName+".fire"+interfaceName+"Completed("+parameterString+");");
		}
		buf.putLine2("}");
		return buf.get();
	}

	protected String createMethodSource_Execute_Stateful(Service service, Operation operation, int indent) {
		String serviceInterface = ServiceLayerHelper.getServiceInterfaceName(service);
		boolean isManager = serviceInterface.endsWith("Manager");
		boolean isCallback = service instanceof Callback;
		boolean hasTimeout = ServiceUtil.hasTimeout(service);
		
		String operationNameBase = null;
		operationNameBase = "handle_" + NameUtil.capName(operation.getName());
		//String operationName = "receive"+NameUtil.capName(operation.getName());
		String action = isCallback ? "response" : "request";
		String operationName = operationNameBase + "_" + action;
		String cancelOperationName = operationNameBase + "_" + action + "_cancel";
		String undoOperationName = operationNameBase + "_" + action + "_undo";
		
		Process process = service.getProcess();
		String processName = ServiceLayerHelper.getProcessInstanceName(process);
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(process);
		
		String parameterString = ParameterUtil.getArgumentString(operation);
		//String parameterString = CodeUtil.getParameterString(operation.getParameters());
		String messageName = parameterString;
		if (messageName.contains(",")) {
			int indexOf = messageName.indexOf(",");
			messageName = messageName.substring(indexOf);
		}		
		
		Buf buf = new Buf();
		//TODO use name in enrollment process
		if (ServiceUtil.hasProperty(context.getService(), "transaction")) {
			//buf.putLine3("");
			//buf.putLine(indent, "");
			//buf.putLine(indent, "// Enroll in global transaction");
			//buf.putLine(indent, "// Enroll in global transaction \""+transactionName+"\"");
			
			//buf.putLine(indent, "");
			String transactionName = ServiceUtil.getProperty(context.getService(), "transaction");
			buf.putLine(indent, "if ("+processContextName+".isGlobalTransactionActive())");
			buf.putLine(indent, "	enrollTransaction(\""+transactionName+"\", "+processContextName+");");
			//buf.putLine(indent, "enrollTransaction(\""+transactionName+"\", "+processContextInstanceName+");");
			//buf.putLine(indent, "setTransactionId(orderRequestMessage.getTransactionId());");
			//buf.putLine(indent, "enrollTransaction("order_books");");
			//buf.putLine3("");
			//buf.putLine(indent, "");
		}

		if (isCallback) {
			buf.putLine(indent, processName+"."+operationName+"("+parameterString+");");
			
		} else {
			if (context.isEnabled("generateCancelAndUndoSupport")) {
				buf.putLine(indent, "");
				buf.putLine(indent, "switch (getAction("+messageName+")) {");
				buf.putLine(indent, "case CANCEL:");
				buf.putLine(indent, "	"+processName+"."+cancelOperationName+"("+parameterString+");");
				buf.putLine(indent, "	break;");
				buf.putLine(indent, "case UNDO:");
				buf.putLine(indent, "	"+processName+"."+undoOperationName+"("+parameterString+");");
				buf.putLine(indent, "	break;");
				buf.putLine(indent, "default:");
				if (hasTimeout) {
					buf.putLine(indent, "	timeoutHandler = startTimeoutHandler(createTimeoutHandler());");
					buf.putLine(indent, "	"+processName+"."+operationName+"("+parameterString+");");
					buf.putLine(indent, "	timeoutHandler.reset();");
				} else 
					buf.putLine(indent, processName+"."+operationName+"("+parameterString+");");
				buf.putLine(indent, "	break;");
				buf.putLine(indent, "}");
				
			} else {
				if (hasTimeout) {
					buf.putLine(indent, "	timeoutHandler = startTimeoutHandler(createTimeoutHandler());");
					buf.putLine(indent, "	"+processName+"."+operationName+"("+parameterString+");");
					buf.putLine(indent, "	timeoutHandler.reset();");
				} else
					buf.putLine(indent, processName+"."+operationName+"("+parameterString+");");
			}
		}
		
		//buf.putLine(indent, "if ("+messageName+".isCancelRequest()) {");
		//buf.putLine(indent, "	"+processName+"."+cancelOperationName+"("+parameterString+");");
		//buf.putLine(indent, "} else {");
		
		//buf.putLine(indent+1, processName+"."+operationName+"("+parameterString+");");
		//buf.putLine(indent, "}");
	
		//buf.putLine2("ProcessLocator processLocator = BeanContext.get(\"org.aries.processLocator\");");
		//buf.putLine2(className+" process = processLocator.lookup("+className+".class, correlationId);");
		//buf.putLine2("process.setStateManager(stateManager);");
		return buf.get();
	}
	
	protected String createMethodSource_Execute_Stateless(Service service, Operation operation, int indent) {
		String serviceType = service.getElement();
		Element element = getElementForServiceType(serviceType);
		String elementStructure = element.getStructure();
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementTypeCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementKeyTypeCapped = ModelLayerHelper.getElementKeyLocalPartCapped(element);
		String elementKeyTypeUncapped = ModelLayerHelper.getElementKeyLocalPartUncapped(element);
		String helperName = ModelLayerHelper.getModelHelperBeanName(element);
		
		Buf buf = new Buf();
		String operationName = operation.getName();
		//String name = TypeUtil.getLocalPart(serviceType);
		String uniqueOperationName = OperationUtil.getUniqueOperationName(operation);
		String dataLayerOperationName = getDataLayerOperationName(service, operation);

		String resultType = null;
		String resultName = null;
		String resultStructure = null;
		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty())
			result = results.get(0);
		if (result != null) {
			resultStructure = result.getConstruct();
			resultType = CodeUtil.getResultType(result);
			//resultName = CodeUtil.getResultName(result);
			resultName = CodeUtil.getStructuredName(resultStructure, "result");
		}
			
		String criteriaBeanName = elementTypeUncapped + "Criteria";
		String criteriaClassName = elementTypeCapped + "Criteria";

		Unit unit = findUnitForService(service);
		Assert.notNull(unit, "Unit not found for type: "+service.getElement());
		String unitName = NameUtil.uncapName(unit.getName());

		String parameterString = ParameterUtil.getArgumentString(operation);
		buf.put(CodeUtil.getAssertionsForParameters(indent, operation));
		List<Parameter> parameters = operation.getParameters();
		String parameterStructure = null;
		if (parameters.size() == 1) {
			Parameter parameter = parameters.get(0);
			parameterStructure = parameter.getConstruct();
		}
		
		if (!ServiceUtil.isSynchronous(service)) {
			buf.putLine(indent, unitName+"Repository."+dataLayerOperationName+"("+parameterString+");");
			
		} else {
			if (operationName.equals("getAll"+elementNameCapped+"AsMap")) {
				buf.putLine(indent, "List<"+elementTypeCapped+"> resultList = "+unitName+"Repository.getAll"+elementNameCapped+"Records();");
				buf.putLine(indent, "Map<"+elementKeyTypeCapped+", "+elementTypeCapped+"> resultMap = "+helperName+".create"+elementTypeCapped+"Map(resultList);");
				buf.putLine(indent, "return resultMap;");
				addImportedClassForHelper(modelUnit, element);
				
			} else if (operationName.startsWith("getFrom"+elementNameCapped)) {
				if (uniqueOperationName.endsWith("Id")) {
					buf.putLine(indent, elementTypeCapped+" result = "+unitName+"Repository.get"+elementNameCapped+"Record("+elementTypeUncapped+"Id);");
					buf.putLine(indent, "return result;");
					
				} else if (uniqueOperationName.endsWith("Ids")) {
					//buf.putLine(indent, "Assert.notNull("+elementTypeUncapped+"Ids, \""+elementTypeCapped+" Ids must be specified\");");
					buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = "+helperName+".create"+elementTypeCapped+"CriteriaFromIds("+elementTypeUncapped+"Ids);");
					buf.putLine(indent, "List<"+elementTypeCapped+"> resultList = "+unitName+"Repository.get"+elementNameCapped+"Records("+criteriaBeanName+");");
					//buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = new "+criteriaClassName+"();");
					//buf.putLine(indent, criteriaBeanName+".getIdList().addAll("+elementTypeUncapped+"Ids);");
					//buf.putLine(indent, "List<"+elementTypeCapped+"> results = "+unitName+"Repository.get"+elementNameCapped+"Records("+criteriaBeanName+");");
					buf.putLine(indent, "Assert.notNull(resultList, \"Results not found for "+elementTypeCapped+" Ids: \"+"+elementTypeUncapped+"Ids);");
					buf.putLine(indent, "return resultList;");
					
				} else if (uniqueOperationName.endsWith("Key")) {
					buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = "+helperName+".create"+elementTypeCapped+"CriteriaFromKey("+elementTypeUncapped+"Key);");
					buf.putLine(indent, "List<"+elementTypeCapped+"> resultList = "+unitName+"Repository.get"+elementNameCapped+"Records("+criteriaBeanName+");");
					buf.putLine(indent, "Assert.isTrue(resultList.size() <= 1, \"Multiple "+elementNameCapped+" records found for key: \"+"+elementTypeUncapped+"Key);");
					buf.putLine(indent, "if (resultList.size() == 1) {");
					buf.putLine(indent, "	"+elementTypeCapped+" result = resultList.get(0);");
					buf.putLine(indent, "	return result;");
					buf.putLine(indent, "}");
					buf.putLine(indent, "return null;");
					
				} else if (uniqueOperationName.endsWith("Keys")) {
					buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = "+helperName+".create"+elementTypeCapped+"CriteriaFromKeys("+elementTypeUncapped+"Keys);");
					buf.putLine(indent, "List<"+elementTypeCapped+"> resultList = "+unitName+"Repository.get"+elementNameCapped+"Records("+criteriaBeanName+");");
					buf.putLine(indent, "Assert.notNull(resultList, \"Results not found for "+elementTypeCapped+" keys: \"+"+elementTypeUncapped+"Keys);");
					buf.putLine(indent, "Map<"+elementKeyTypeCapped+", "+elementTypeCapped+"> resultMap = "+helperName+".create"+elementTypeCapped+"Map(resultList);");
					buf.putLine(indent, "return resultMap;");
					addImportedClassForHelper(modelUnit, element);
					
				} else if (uniqueOperationName.endsWith("Criteria")) {
					buf.putLine(indent, resultType+" "+resultName+" = "+unitName+"Repository."+dataLayerOperationName+"("+parameterString+");");
					buf.putLine(indent, "Assert.notNull(resultList, \"Results not found for "+elementTypeCapped+" criteria: \"+"+criteriaBeanName+");");
					buf.putLine(indent, "return "+resultName+";");
					
				} else {
					buf.putLine(indent, resultType+" "+resultName+" = "+unitName+"Repository."+dataLayerOperationName+"("+parameterString+");");
					buf.putLine(indent, "return "+resultName+";");
				}

			} else if (operationName.equals("getFrom"+elementNameCapped+"AsMap")) {
				buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = "+helperName+".create"+elementTypeCapped+"CriteriaFromKeys("+elementTypeUncapped+"Keys);");
				buf.putLine(indent, "Map<"+elementKeyTypeCapped+", "+elementTypeCapped+"> resultMap = "+unitName+"Repository.get"+elementNameCapped+"Records("+criteriaBeanName+");");
				buf.putLine(indent, "Assert.notNull(resultMap, \"Results not found for "+elementTypeCapped+" keys: \"+"+elementTypeUncapped+"Keys);");
				buf.putLine(indent, "return resultMap;");
				addImportedClassForHelper(modelUnit, element);
				
			} else if (operationName.equals("getMatching"+elementNameCapped)) {
				//buf.putLine(indent, "Assert.notNull("+elementTypeUncapped+"List, \""+elementTypeCapped+" list must be specified\");");
				buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = "+helperName+".create"+elementTypeCapped+"Criteria("+elementTypeUncapped+"List);");
				buf.putLine(indent, "List<"+elementTypeCapped+"> resultList = "+unitName+"Repository.get"+elementNameCapped+"Records("+criteriaBeanName+");");
				//buf.putLine(indent, "List<"+elementKeyTypeCapped+"> "+elementTypeUncapped+"Keys = "+helperName+".create"+elementTypeCapped+"Keys("+elementTypeUncapped+"List);");
				//buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = new "+criteriaClassName+"();");
				//buf.putLine(indent, criteriaBeanName+".getKeys().addAll("+elementTypeUncapped+"Keys);");
				//buf.putLine(indent, "List<"+elementTypeCapped+"> resultList = "+unitName+"Repository.get"+elementNameCapped+"Records("+criteriaBeanName+");");
				buf.putLine(indent, "Assert.notNull(resultList, \"No matching results found for "+elementTypeCapped+" list: \"+"+elementTypeUncapped+"List);");
				buf.putLine(indent, "return resultList;");
				addImportedClassForHelper(modelUnit, element);

			} else if (operationName.equals("getMatching"+elementNameCapped+"AsMap")) {
				//buf.putLine(indent, "Assert.notNull("+elementTypeUncapped+"List, \""+elementTypeCapped+" list must be specified\");");
				buf.putLine(indent, "List<"+elementTypeCapped+"> resultList = getMatching"+elementNameCapped+"("+elementTypeUncapped+"List);");
				buf.putLine(indent, "Assert.notNull(resultList, \"No matching results found for "+elementTypeCapped+" list: \"+"+elementTypeUncapped+"List);");
				buf.putLine(indent, "Map<"+elementKeyTypeCapped+", "+elementTypeCapped+"> resultMap = "+helperName+".create"+elementTypeCapped+"Map(resultList);");
				buf.putLine(indent, "return resultMap;");
				addImportedClassForHelper(modelUnit, element);
			
			} else if (operationName.startsWith("addTo"+elementNameCapped) && parameterStructure.equals("map")) {
				buf.putLine(indent, resultType+" "+resultName+" = "+unitName+"Repository."+dataLayerOperationName+"("+elementTypeUncapped+"Map.values());");
				buf.putLine(indent, "return "+resultName+";");
				
			} else if (operationName.startsWith("removeFrom"+elementNameCapped)) {
				if (uniqueOperationName.endsWith("Id")) {
					buf.putLine(indent, unitName+"Repository.remove"+elementNameCapped+"Record("+elementTypeUncapped+"Id);");
					
				} else if (uniqueOperationName.endsWith("Ids")) {
					buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = "+helperName+".create"+elementTypeCapped+"CriteriaFromIds("+elementTypeUncapped+"Ids);");
					buf.putLine(indent, unitName+"Repository.remove"+elementNameCapped+"Records("+criteriaBeanName+");");
					
				} else if (uniqueOperationName.endsWith("Key")) {
					buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = "+helperName+".create"+elementTypeCapped+"CriteriaFromKey("+elementTypeUncapped+"Key);");
					buf.putLine(indent, unitName+"Repository.remove"+elementNameCapped+"Records("+criteriaBeanName+");");
					
				} else if (uniqueOperationName.endsWith("Keys")) {
					buf.putLine(indent, criteriaClassName+" "+criteriaBeanName+" = "+helperName+".create"+elementTypeCapped+"CriteriaFromKeys("+elementTypeUncapped+"Keys);");
					buf.putLine(indent, unitName+"Repository.remove"+elementNameCapped+"Records("+criteriaBeanName+");");
					
				} else if (uniqueOperationName.endsWith("Criteria")) {
					buf.putLine(indent, unitName+"Repository.remove"+elementNameCapped+"Records("+criteriaBeanName+");");
					
				} else {
					buf.putLine(indent, unitName+"Repository."+dataLayerOperationName+"("+parameterString+");");
				}
				
			} else {
				if (result == null) {
					buf.putLine(indent, unitName+"Repository."+dataLayerOperationName+"("+parameterString+");");
				} else {
					buf.putLine(indent, resultType+" "+resultName+" = "+unitName+"Repository."+dataLayerOperationName+"("+parameterString+");");
					buf.putLine(indent, "return "+resultName+";");
				}
			}
		}

		return buf.get();
	}

	protected ModelOperation createMethod_TimeoutHandler(ModelUnit modelUnit, Service service, Operation operation, boolean isInterface) throws Exception {
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String processName = ServiceLayerHelper.getProcessInstanceName(service.getProcess());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createTimeoutHandler");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("Runnable");

		Buf buf = new Buf();
		buf.putLine2("return new Runnable() {");
		buf.putLine2("	public void run() {");
		buf.putLine2("		String reason = \""+serviceInterfaceName+" timed-out\";");
		buf.putLine2("		"+processName+".handle_"+serviceInterfaceName+"_request_timeout(reason);");
		buf.putLine2("	}");
		buf.putLine2("};");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	
	protected String getDataLayerOperationName(Service service, Operation operation) {
		String serviceType = service.getElement();
		Element element = getElementForServiceType(serviceType);
		
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationName = operation.getName();
		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty())
			result = results.get(0);
		
//		if (operationName.equals("getFromUserByUserName"))
//			System.out.println();
		
		if (operationName.equals("getAll"+elementNameCapped))
			return "getAll"+elementNameCapped+"Records";
		else if (operationName.equals("getAll"+elementNameCapped+"AsMap"))
			return "getAll"+elementNameCapped+"RecordsAsMap";
		else if (operationName.equals("getFrom"+elementNameCapped+"ById"))
			return "get"+elementNameCapped+"RecordById";
		else if (operationName.equals("getFrom"+elementNameCapped+"ByIds"))
			return "get"+elementNameCapped+"RecordsByIds";
		else if (operationName.equals("getFrom"+elementNameCapped+"ByKey"))
			return "get"+elementNameCapped+"RecordByKey";
		else if (operationName.equals("getFrom"+elementNameCapped+"ByKeys"))
			return "get"+elementNameCapped+"RecordsByKeys";
//		else if (operationName.equals("getFrom"+elementNameCapped+"ByCriteria"))
//			return "get"+elementNameCapped+"Records";
		else if (operationName.equals("getFrom"+elementNameCapped)) {
			Parameter parameter = operation.getParameters().get(0);
			String parameterStructure = parameter.getConstruct();
			if (parameterStructure.equals("item") && !parameter.getName().endsWith("Criteria"))
				return "get"+elementNameCapped+"Record";
			else return "get"+elementNameCapped+"Records";
		}
		else if (operationName.equals("getMatching"+elementNameCapped))
			return "getMatching"+elementNameCapped+"Records";
		else if (operationName.equals("getMatching"+elementNameCapped+"AsMap"))
			return "getMatching"+elementNameCapped+"RecordsAsMap";
		else if (operationName.equals("addTo"+elementNameCapped) && result.getConstruct().equals("item"))
			return "add"+elementNameCapped+"Record";
		else if (operationName.equals("addTo"+elementNameCapped) && !result.getConstruct().equals("item"))
			return "add"+elementNameCapped+"Records";
		else if (operationName.equals("addTo"+elementNameCapped+"AsMap"))
			return "add"+elementNameCapped+"Records";
		else if (operationName.equals("save"+elementNameCapped))
			return "save"+elementNameCapped+"Record";
		else if (operationName.equals("removeAll"+elementNameCapped))
			return "removeAll"+elementNameCapped+"Records";
		else if (operationName.equals("removeFrom"+elementNameCapped+"ById"))
			return "remove"+elementNameCapped+"RecordsById";
		else if (operationName.equals("removeFrom"+elementNameCapped+"ByIds"))
			return "remove"+elementNameCapped+"RecordsByIds";
		else if (operationName.equals("removeFrom"+elementNameCapped+"ByKey"))
			return "remove"+elementNameCapped+"RecordsByKey";
		else if (operationName.equals("removeFrom"+elementNameCapped+"ByKeys"))
			return "remove"+elementNameCapped+"RecordsByKeys";
		else if (operationName.equals("removeFrom"+elementNameCapped)) {
			Parameter parameter = operation.getParameters().get(0);
			String parameterStructure = parameter.getConstruct();
			if (parameterStructure.equals("item"))
				return "remove"+elementNameCapped+"Record";
			else return "remove"+elementNameCapped+"Records";
		}
		else if (operationName.equals("move"+elementNameCapped))
			return "move"+elementNameCapped+"Record";
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldName = ModelLayerHelper.getFieldNameCapped(field);
			fieldName = NameUtil.trimFromEnd(fieldName, "List");
			fieldName = NameUtil.trimFromEnd(fieldName, "Id");
			if (StringUtils.isEmpty(fieldName))
				continue;
			if (operationName.startsWith("getFrom"+elementNameCapped+"By"+fieldName))
				return "get"+elementNameCapped+"RecordBy"+fieldName;
			if (operationName.startsWith("getFrom"+elementNameCapped+"By"+fieldName))
				return "get"+elementNameCapped+"RecordsBy"+fieldName;
		}
		
		return null;
	}


	protected Unit findUnitForService(Service service) {
		String serviceType = service.getElement();
		Element element = getElementForServiceType(serviceType);
		String namespace = TypeUtil.getNamespace(element.getType());
		
		Project project = context.getProject();
		//Namespace namespace = context.getNamespaceByUri(namespaceUri);
		Persistence persistenceBlock = ProjectUtil.getPersistenceBlockByNamespace(project, namespace);
		
		Set<Module> dataModules = ProjectUtil.getDataModules(project);
		
		//Application application = context.getApplication();
		//Set<Module> dataModules = ApplicationUtil.getDataModules(application);
		Iterator<Module> iterator = dataModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			Persistence persistence = module.getPersistence();
			if (!persistence.getNamespace().equals(service.getNamespace()))
				continue;
			List<Unit> units = PersistenceUtil.getUnits(persistence);
			Iterator<Unit> iterator2 = units.iterator();
			while (iterator2.hasNext()) {
				Unit unit = iterator2.next();
				if (UnitUtil.containsElement(unit, element)) {
					return unit;
				}
			}
		}
		return null;
	}

}
