package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import nam.ProjectLevelHelper;
import nam.client.ClientLayerHelper;
import nam.data.DataLayerHelper;
import nam.model.Attribute;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Command;
import nam.model.Definition;
import nam.model.Element;
import nam.model.Interactor;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.Reference;
import nam.model.Service;
import nam.model.Type;
import nam.model.Unit;
import nam.model.statement.AbstractBlock;
import nam.model.statement.BlockStatement;
import nam.model.statement.Branch;
import nam.model.statement.ConditionStatement;
import nam.model.statement.DefinitionCommand;
import nam.model.statement.ExpressionStatement;
import nam.model.statement.IfStatement;
import nam.model.statement.InvokeCommand;
import nam.model.statement.ReplyCommand;
import nam.model.util.CommandUtil;
import nam.model.util.DefinitionUtil;
import nam.model.util.ElementUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ProcessUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerFactory;
import nam.service.ServiceLayerHelper;
import nam.service.src.main.java.CacheUnitManagerBuilder;

import org.aries.util.NameUtil;
import org.aries.util.NumberUtil;
import org.springframework.util.Assert;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.TestType;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.JMSUtil;
import aries.codegen.util.Pathway;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;
import aries.generation.model.ModelUnit;


/**
 * Builds a Service JMS Listener CIT {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * @author tfisher
 */
public abstract class AbstractServiceJMSListenerCITBuilder extends AbstractListenerTestBuilder {

	protected abstract AbstractServiceJMSListenerCITProvider createProvider(GenerationContext context);
	
	protected abstract String getBaseClassName(Service service);
	
	protected abstract String getFixtureClassName(Service service);

	protected abstract String getTestClassName(Service service);

	protected abstract String getTestParentClassName();

	
	protected AbstractServiceJMSListenerCITProvider provider;
	
	protected boolean includeTestWar;

	protected int testIterationCount;

	private boolean isNeedFixtureReset;

	
	public AbstractServiceJMSListenerCITBuilder(GenerationContext context) {
		super(context);
		provider = createProvider(context);
//		dummyValueFactory = new DummyValueFactory(context);
	}
	
//	public String getManagerClassName() {
//		return getBaseClassName() + "Manager";
//	}
//
//	public String getHelperClassName() {
//		return getBaseClassName() + "Helper";
//	}
	
	public ModelClass buildClass(Service service) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String className = getTestClassName(service);
		String beanName = NameUtil.capName(className);

//		if (service.getName().equalsIgnoreCase("purchaseBooks"))
//			System.out.println();
		
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setNamespace(namespace);
		modelClass.setParentClassName(getTestParentClassName());
		modelClass.addImportedClass(getTestParentClassName());
		initializeClass(modelClass, service);
		return modelClass; 
	}
	
	protected void initializeClass(ModelClass modelClass, Service service) throws Exception {
		initializeClassAnnotations(modelClass);
		initializeImportedClasses(modelClass, service);
		initializeInstanceFields(modelClass, service);
		initializeInstanceMethods(modelClass, service);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
		//modelClass.addImportedClass("java.util.ArrayList");
		//modelClass.addImportedClass("java.util.Set");
		//modelClass.addImportedClass("java.util.List");
		//modelClass.addImportedClass("java.util.Map");
		
//		Unit unit = context.getUnit();
//		if (unit != null) {
//			String namespace = context.getApplication().getNamespace();
//			String packageName = ProjectLevelHelper.getPackageName(namespace);
//			String unitName = DataLayerHelper.getPersistenceUnitNameCapped(unit);
//			modelClass.addImportedClass(packageName + ".helper." + unitName + "Helper");
//		}
		
		modelClass.addImportedClass("org.junit.Test");
		modelClass.addImportedClass("org.junit.After");
		modelClass.addImportedClass("org.junit.AfterClass");
		modelClass.addImportedClass("org.junit.Before");
		modelClass.addImportedClass("org.junit.BeforeClass");
		modelClass.addImportedClass("org.junit.Ignore");
		modelClass.addImportedClass("org.junit.runner.RunWith");
		
		modelClass.addImportedClass("org.jboss.arquillian.container.test.api.Deployment");
		modelClass.addImportedClass("org.jboss.arquillian.container.test.api.TargetsContainer");
		modelClass.addImportedClass("org.jboss.arquillian.junit.Arquillian");
		modelClass.addImportedClass("org.jboss.arquillian.junit.InSequence");
		modelClass.addImportedClass("org.jboss.arquillian.container.test.api.RunAsClient");
		modelClass.addImportedClass("org.jboss.shrinkwrap.api.spec.EnterpriseArchive");
		if (includeTestWar)
			modelClass.addImportedClass("org.jboss.shrinkwrap.api.spec.WebArchive");

		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.tx.AbstractArquillianTest");
		modelClass.addImportedClass("org.aries.tx.BytemanRule");
		//modelClass.addImportedClass("org.aries.tx.BytemanUtil");
		
		//modelClass.addImportedClass("org.aries.tx.TXManagerEARBuilder");
		//modelClass.addImportedClass("org.aries.util.concurrent.FutureResult");
		modelClass.addImportedClass("org.apache.commons.lang.exception.ExceptionUtils");

		//jms
		modelClass.addImportedClass("javax.jms.Message");
		modelClass.addImportedClass("javax.jms.MessageListener");
		modelClass.addImportedClass("org.aries.jms.util.MessageUtil");
		modelClass.addImportedClass("org.aries.jms.client.JmsClient");

		//jmx
		//modelClass.addImportedClass("javax.management.ObjectName");

		switch (context.getServiceLayerBeanType()) {
		case EJB:
			//modelClass.addImportedClass("javax.inject.Inject");
			//modelClass.addImportedClass("javax.ejb.Asynchronous");
			//modelClass.addImportedClass("javax.ejb.AccessTimeout");
			break;
			
		case SEAM:
			break;
		}
	}

	protected void initializeClassAnnotations(ModelClass modelClass) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createArquillianRunAsClientAnnotation());
		classAnnotations.add(AnnotationUtil.createJUnitSuiteRunWithAnnotation("Arquillian"));
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		Process process = service.getProcess();
		addReference_MainClient(modelClass, service);
		addReferences_CallbackHandlers(modelClass, service);
		addReferences_Messages(modelClass, service);
		//if (service.getName().equals("reserveBooks"))
		//	System.out.println();
		addReference_TransactionTestControl(modelClass, service);
		if (ServiceUtil.hasCacheUnitReference(service))
			addReferences_CacheUnitRelated(modelClass, service);
		if (ServiceUtil.hasDataUnitReference(service))
			addReferences_DataUnitRelated(modelClass, service);
		if (ProcessUtil.hasIncomingEvents(process))
			addReferences_IncomingEvents(modelClass, process);
		if (ProcessUtil.hasIncomingEvents(process))
			addReference_EventMulticasterProxy(modelClass, service);
		addReferences_StatusFlags(modelClass, service);
		addReferences_FailureReasons(modelClass, service);
	}

	public void addReference_TransactionTestControl(ModelClass modelClass, Service service) throws Exception {
		modelClass.addInstanceAttribute(createReference_TransactionTestControl());
		modelClass.addImportedClass("org.aries.tx.TransactionTestControl");
	}
	
	public ModelAttribute createReference_TransactionTestControl() throws Exception {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("org.aries.tx");
		attribute.setClassName("TransactionTestControl");
		attribute.setName("transactionTestControl");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public void addReference_EventMulticasterProxy(ModelClass modelClass, Service service) throws Exception {
		ModelAttribute modelAttribute = createReference_EventMulticasterProxy();
		modelClass.addInstanceAttribute(modelAttribute);
	}

	public ModelAttribute createReference_EventMulticasterProxy() throws Exception {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("org.aries.event.multicaster");
		attribute.setClassName("EventMulticasterProxy");
		attribute.setName("eventMulticasterProxy");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	/*
	 * Cache unit related
	 */

	public void addReferences_CacheUnitRelated(ModelClass modelClass, Service service) {
		modelClass.addImportedClass("org.aries.tx.CacheModuleTestControl");
		
		Module module = context.getModule();
		//Process process = service.getProcess();
		Set<Cache> cacheReferences = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheReferences.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			//Cache cacheUnit = ProcessUtil.getCacheUnit(process, cacheName);
			Assert.notNull(cacheUnit, "CacheUnit not found: "+cacheUnit.getName());
			String cacheUnitQualifiedName = DataLayerHelper.getCacheUnitQualifiedName(module.getNamespace(), cacheUnit);
			//modelClass.addInstanceAttribute(createReference_CacheTestControl(cacheUnit));
			//modelClass.addInstanceAttribute(createReference_CacheProxy(cacheUnit));
			modelClass.addInstanceAttribute(createReference_CacheHelper(cacheUnit));
			modelClass.addImportedClass(cacheUnitQualifiedName+"Proxy");
			modelClass.addImportedClass(cacheUnitQualifiedName+"Helper");
		}
	}
	
	public ModelAttribute createReference_CacheTestControl(Cache cacheUnit) {
		String cacheUnitNameUncapped = DataLayerHelper.getCacheUnitNameUncapped(cacheUnit);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("org.aries.tx");
		attribute.setClassName("CacheModuleTestControl");
		attribute.setName(cacheUnitNameUncapped+"Control");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public ModelAttribute createReference_CacheHelper(Cache cacheUnit) {
		String namespace = context.getModule().getNamespace();
		String cacheUnitPackageName = DataLayerHelper.getCacheUnitPackageName(namespace, cacheUnit);
		String cacheUnitNameCapped = DataLayerHelper.getCacheUnitNameCapped(cacheUnit);
		String cacheUnitNameUncapped = DataLayerHelper.getCacheUnitNameUncapped(cacheUnit);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(cacheUnitPackageName);
		attribute.setClassName(cacheUnitNameCapped+"Helper");
		attribute.setName(cacheUnitNameUncapped+"Helper");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public ModelAttribute createReference_CacheProxy(Cache cacheUnit) {
		String namespace = context.getModule().getNamespace();
		String cacheUnitPackageName = DataLayerHelper.getCacheUnitPackageName(namespace, cacheUnit);
		String cacheUnitNameCapped = DataLayerHelper.getCacheUnitNameCapped(cacheUnit);
		String cacheUnitNameUncapped = DataLayerHelper.getCacheUnitNameUncapped(cacheUnit);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(cacheUnitPackageName);
		attribute.setClassName(cacheUnitNameCapped+"Proxy");
		attribute.setName(cacheUnitNameUncapped+"Proxy");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	/*
	 * Data unit related
	 */
	
	public void addReferences_DataUnitRelated(ModelClass modelClass, Service service) {
		modelClass.addImportedClass("org.aries.tx.DataModuleTestControl");
		
		Module module = context.getModule();
		Process process = service.getProcess();
		Set<String> dataUnitReferences = ServiceUtil.getDataUnitReferences(service);
		Iterator<String> iterator = dataUnitReferences.iterator();
		while (iterator.hasNext()) {
			String dataName = iterator.next();
			Unit dataUnit = ProcessUtil.getDataUnit(process, dataName);
			Assert.notNull(dataUnit, "DataUnit not found: "+dataName);
			String dataUnitQualifiedName = DataLayerHelper.getPersistenceUnitQualifiedName(module.getNamespace(), dataUnit);
			//modelClass.addInstanceAttribute(createReference_CacheTestControl(cacheUnit));
			//modelClass.addInstanceAttribute(createReference_CacheProxy(cacheUnit));
			modelClass.addInstanceAttribute(createReference_DataUnitHelper(dataUnit));
			modelClass.addImportedClass(dataUnitQualifiedName+"Proxy");
			modelClass.addImportedClass(dataUnitQualifiedName+"Manager");
			modelClass.addImportedClass(dataUnitQualifiedName+"Helper");
		}
	}
	
	public ModelAttribute createReference_DataUnitTestControl(Unit dataUnit) {
		String dataUnitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(dataUnit);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("org.aries.tx");
		attribute.setClassName("DataModuleTestControl");
		attribute.setName(dataUnitNameUncapped+"Control");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public ModelAttribute createReference_DataUnitHelper(Unit dataUnit) {
		String namespace = context.getModule().getNamespace();
		String cacheUnitPackageName = DataLayerHelper.getPersistenceUnitPackageName(namespace, dataUnit);
		String cacheUnitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(dataUnit);
		String cacheUnitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(dataUnit);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(cacheUnitPackageName);
		attribute.setClassName(cacheUnitNameCapped+"Helper");
		attribute.setName(cacheUnitNameUncapped+"Helper");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public ModelAttribute createReference_DataUnitProxy(Unit dataUnit) {
		String namespace = context.getModule().getNamespace();
		String dataUnitPackageName = DataLayerHelper.getPersistenceUnitPackageName(namespace, dataUnit);
		String dataUnitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(dataUnit);
		String dataUnitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(dataUnit);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(dataUnitPackageName);
		attribute.setClassName(dataUnitNameCapped+"Proxy");
		attribute.setName(dataUnitNameUncapped+"Proxy");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	
	public void addReference_MainClient(ModelClass modelClass, Service service) throws Exception {
		String clientName = ClientLayerHelper.getClientNameUncapped(service);
		String serviceName = ServiceUtil.getDomainServiceName(service);
		ModelAttribute modelAttribute = createReference_JmsClient(clientName + "Client");
		modelAttribute.addJavadoc("Main "+serviceName+" request handler");
		modelClass.addInstanceAttribute(modelAttribute);
	}

	public void addReferences_CallbackHandlers(ModelClass modelClass, Service service) throws Exception {
		//provide one of these to serve as a live mock for each external service that is invoked
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service remoteService = iterator.next();
			String serviceName = ServiceUtil.getDomainServiceName(remoteService);
			ModelAttribute modelAttribute = createReference_JmsClient("remote" + serviceName + "Handler");
			modelAttribute.addJavadoc("Remote "+serviceName+" request handler");
			modelClass.addInstanceAttribute(modelAttribute);

			//provide one of these for each callback (response) from specified remote service
			List<Callback> remoteOutgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
			Iterator<Callback> iterator2 = remoteOutgoingCallbacks.iterator();
			while (iterator2.hasNext()) {
				Callback callback = iterator2.next();
				String clientName = ServiceUtil.getDomainServiceName(callback);
				modelAttribute = createReference_JmsClient("remote" + clientName + "Sender");
				modelAttribute.addJavadoc("Remote "+clientName+" response sender");
				modelClass.addInstanceAttribute(modelAttribute);
			}
		}

		//provide one of these for each callback (response) from the service being tested (invoked)
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator3 = outgoingCallbacks.iterator();
		while (iterator3.hasNext()) {
			Callback callback = iterator3.next();
			String clientName = ServiceUtil.getDomainServiceName(callback);
			ModelAttribute modelAttribute = createReference_JmsClient("local" + clientName + "Handler");
			modelAttribute.addJavadoc("Local "+clientName+" response handler");
			modelClass.addInstanceAttribute(modelAttribute);
		}
	}
	
	public ModelAttribute createReference_JmsClient(String beanName) throws Exception {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("org.aries.jms.client");
		attribute.setClassName("JmsClient");
		attribute.setName(beanName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	protected void addReferences_Messages(ModelClass modelClass, Service service) {
		modelClass.addInstanceAttribute(createReference_Message(modelClass, service, false));

		//provide one of these for each callback (response) from target service
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			modelClass.addInstanceAttribute(createReference_Message(modelClass, callback, false));
		}

		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service remoteService = iterator.next();
			modelClass.addInstanceAttribute(createReference_Message(modelClass, remoteService, true));

			//provide one of these for each callback (response) from specified remote service
			List<Callback> remoteOutgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
			Iterator<Callback> iterator3 = remoteOutgoingCallbacks.iterator();
			while (iterator3.hasNext()) {
				Callback callback = iterator3.next();
				modelClass.addInstanceAttribute(createReference_Message(modelClass, callback, true));
			}
		}
	}
	
	protected ModelAttribute createReference_Message(ModelClass modelClass, Service service, boolean locationQualified) {
		String serviceNamespace = NameUtil.uncapName(ServiceUtil.getNamespaceAlias(service));
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return null;
		
		String messageQualifiedName = getMessageQualifiedName(operation);
		String messagePackageName = getMessagePackageName(operation);
		String messageClassName = getMessageClassName(operation);
		String messageBeanName = getMessageBeanName(operation);
		modelClass.addImportedClass(messageQualifiedName);

//		if (messageBeanName.equals("shipmentFailedMessage"))
//			System.out.println();
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(messagePackageName);
		attribute.setClassName(messageClassName);
		if (locationQualified)
			attribute.setName(serviceNamespace + messageClassName);
		else attribute.setName(messageBeanName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	protected void addReferences_IncomingEvents(ModelClass modelClass, Process process) {
		Set<String> events = ProcessUtil.getIncomingEvents(process);
		Iterator<String> iterator = events.iterator();
		while (iterator.hasNext()) {
			String eventName = iterator.next();
			ModelReference modelReference = ServiceLayerFactory.createReference_Event(modelClass, process, eventName);
			modelClass.addInstanceReference(modelReference);
		}
	}
	
	protected void addReferences_OutgoingEvents(ModelClass modelClass, Process process) {
		Set<String> events = ProcessUtil.getOutgoingEvents(process);
		Iterator<String> iterator = events.iterator();
		while (iterator.hasNext()) {
			String eventName = iterator.next();
			ModelReference modelReference = ServiceLayerFactory.createReference_Event(modelClass, process, eventName);
			modelClass.addInstanceReference(modelReference);
		}
	}
	
	protected void addReferences_StatusFlags(ModelClass modelClass, Service service) {
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service remoteService = iterator.next();
			//modelClass.addInstanceAttribute(createReference_Message(modelClass, remoteService));
			modelClass.addInstanceAttribute(createReference_StatusFlag(modelClass, remoteService));
		}
		
		//provide one of these for each callback (response) from specified remote service
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			modelClass.addInstanceAttribute(createReference_StatusFlag(modelClass, callback));
		}
	}

	protected ModelAttribute createReference_StatusFlag(ModelClass modelClass, Service service) {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return null;
		
		String messageReceivedFlagName = getMessageReceivedFlagName(operation);

		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("java.lang");
		attribute.setClassName("boolean");
		attribute.setName(messageReceivedFlagName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	protected void addReferences_FailureReasons(ModelClass modelClass, Service service) {
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			Operation operation = ServiceUtil.getDefaultOperation(callback);
			if (operation.getParameters().isEmpty())
				continue;

			String messageClassName = getMessageClassName(operation);
			//TODO use a much better way to distinguish a "failure message"
			if (messageClassName.contains("Failed")) {
				modelClass.addInstanceAttribute(createReference_FailureReason(modelClass, callback));
			}
		}
	}
	
	protected ModelAttribute createReference_FailureReason(ModelClass modelClass, Service service) {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		String messageFailureMessageReason = getFailureMessageReason(operation);

		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("java.lang");
		attribute.setClassName("String");
		attribute.setName(messageFailureMessageReason);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public void addReferences_CallbackListeners_UNUSED(ModelClass modelClass, Service service) throws Exception {
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			String clientName = ClientLayerHelper.getClientNameUncapped(callback);
			modelClass.addInstanceAttribute(createReference_MessageListener_UNUSED(clientName + "Listener"));
		}
	}
	
	public ModelAttribute createReference_MessageListener_UNUSED(String beanName) throws Exception {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("javax.jms.MessageListener");
		attribute.setClassName("MessageListener");
		attribute.setName(beanName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	
//	public void addReference_Manager(ModelClass modelClass, String namespace) throws Exception {
//		ModelAttribute modelAttribute = createReference_Manager(namespace);
//		modelClass.addInstanceAttribute(modelAttribute);
//	}
//
//	public ModelAttribute createReference_Manager(String namespace) throws Exception {
//		String packageName = getBasePackageName(namespace);
//		String className = getBaseClassName() + "Manager";
//		String beanName = NameUtil.uncapName(className);
//		
//		ModelAttribute attribute = new ModelAttribute();
//		attribute.setModifiers(Modifier.PRIVATE);
//		attribute.setPackageName(packageName);
//		attribute.setClassName(className);
//		attribute.setName(beanName);
//		attribute.setGenerateGetter(false);
//		attribute.setGenerateSetter(false);
//		attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
//		return attribute;
//	}
	
	protected void initializeInstanceMethods(ModelClass modelClass, Service service) throws Exception {
		Process process = service.getProcess();

		createMethod_JUnitBeforeClass(modelClass);
		createMethod_JUnitAfterClass(modelClass);
		createMethod_GetTestClass(modelClass);
		createMethod_GetServerName(modelClass);
		createMethod_GetDomainId(modelClass, service);
		createMethod_GetServiceId(modelClass, service);
		createMethod_GetTargetArchive(modelClass, service);
		createMethod_GetTargetDestination(modelClass, service);
		createMethod_GetMessageDestination_Target(modelClass, service);
		createMethods_GetMessageDestinations_Remote(modelClass, service);
		createMethods_GetMessageDestinations_Local(modelClass, service);
		createMethod_JUnitSetup(modelClass, service);
		createMethod_CreateTransactionTestControl(modelClass);
		if (ProcessUtil.hasIncomingEvents(process))
			createMethod_CreateEventMulticasterProxy(modelClass, service);
		if (ServiceUtil.hasCacheUnitReference(service))
			createMethods_CacheUnitRelated(modelClass, service);
		if (ServiceUtil.hasDataUnitReference(service))
			createMethods_DataUnitRelated(modelClass, service);
		createMethods_CreateMessages(modelClass, service);
		createMethods_CreateEvents(modelClass, service);
		createMethod_RegisterNotificationListeners(modelClass, service);
		createMethod_JUnitTeardown(modelClass, service);
		createMethod_ClearStructures(modelClass, service);
		createMethod_ClearState(modelClass, service);
		createMethod_ClearMessages(modelClass, service);
		//createMethod_CreateTXManagerEAR(modelClass);
		createMethod_CreateTestEAR(modelClass);
		if (ProcessUtil.hasIncomingEvents(process)) {
			createMethod_EventsExist(modelClass, service, process);
			createMethod_FireEvents(modelClass, service, process);
			createMethod_FireEvent(modelClass, service);
		}
		if (includeTestWar) {
			createMethod_CreateTestWAR(modelClass);
		}
		createMethod_RunSendMessage(modelClass, service);
		createMethod_RunSendMessage_main(modelClass, service);
		createMethod_RunSendMessage_cancel(modelClass, service);
		createMethod_RunSendMessage_undo(modelClass, service);
		//if (ProcessUtil.hasIncomingEvents(process)) {
			createMethod_StartRunSendMessage(modelClass, service);
		//}
		createMethod_StartMainClient(modelClass, service);
		createMethods_StartServiceListeners(modelClass, service);
		createMethods_CreateServiceListeners(modelClass, service);
		createMethod_ValidateResult(modelClass, service);
		createMethods_ValidateMessages(modelClass, service);
		createMethods_RunTests(modelClass, service);
	}
	
	/*
	 * Cache unit related
	 */
	
	public void createMethods_CacheUnitRelated(ModelClass modelClass, Service service) {
		Process process = service.getProcess();
		Set<Cache> cacheReferences = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheReferences.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			//Cache cacheUnit = ProcessUtil.getCacheUnit(process, cacheName);
			Assert.notNull(cacheUnit, "CacheUnit not found: "+cacheUnit.getName());
			createMethod_CreateCacheUnitHelper(modelClass, cacheUnit);
			createMethod_CreateCacheUnitProxy(modelClass, cacheUnit);
			createMethod_CreateCacheUnitTestControl(modelClass, cacheUnit);
		}
	}

	public void createMethod_CreateCacheUnitHelper(ModelClass modelClass, Cache cacheUnit) {
		String cacheUnitNameCapped = DataLayerHelper.getCacheUnitNameCapped(cacheUnit);
		String cacheUnitNameUncapped = DataLayerHelper.getCacheUnitNameUncapped(cacheUnit);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+cacheUnitNameCapped+"Helper");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2(cacheUnitNameUncapped+"Helper = new "+cacheUnitNameCapped+"Helper();");
		buf.putLine2(cacheUnitNameUncapped+"Helper.setRunningAsClient(true);");
		buf.putLine2(cacheUnitNameUncapped+"Helper.setProxy(create"+cacheUnitNameCapped+"Proxy());");
		buf.putLine2(cacheUnitNameUncapped+"Helper.initializeAsClient(create"+cacheUnitNameCapped+"Control());");
		//buf.putLine2(cacheUnitNameUncapped+"Helper.setJmxManager(jmxManager);");
		//buf.putLine2(cacheUnitNameUncapped+"Helper.setCacheProxy("+cacheUnitNameUncapped+"Proxy);");
		//buf.putLine2(cacheUnitNameUncapped+"Helper.initialize("+cacheUnitNameUncapped+"Control);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	public void createMethod_CreateCacheUnitProxy(ModelClass modelClass, Cache cacheUnit) {
		String cacheUnitNameCapped = DataLayerHelper.getCacheUnitNameCapped(cacheUnit);
		String cacheUnitNameUncapped = DataLayerHelper.getCacheUnitNameUncapped(cacheUnit);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+cacheUnitNameCapped+"Proxy");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.setResultType(cacheUnitNameCapped+"Proxy");
		Buf buf = new Buf();
		buf.putLine2(cacheUnitNameCapped+"Proxy "+cacheUnitNameUncapped+"Proxy = new "+cacheUnitNameCapped+"Proxy();");
		buf.putLine2(cacheUnitNameUncapped+"Proxy.setJmxManager(jmxManager);");
		buf.putLine2("return "+cacheUnitNameUncapped+"Proxy;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	public void createMethod_CreateCacheUnitTestControl(ModelClass modelClass, Cache cacheUnit) {
		String cacheUnitNameCapped = DataLayerHelper.getCacheUnitNameCapped(cacheUnit);
		String cacheUnitNameUncapped = DataLayerHelper.getCacheUnitNameUncapped(cacheUnit);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+cacheUnitNameCapped+"Control");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.setResultType("CacheModuleTestControl");
		Buf buf = new Buf();
		buf.putLine2("CacheModuleTestControl "+cacheUnitNameUncapped+"Control = new CacheModuleTestControl(transactionTestControl);");
		buf.putLine2(cacheUnitNameUncapped+"Control.setupCacheLayer();");
		buf.putLine2("return "+cacheUnitNameUncapped+"Control;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	/*
	 * Data unit related
	 */
	
	public void createMethods_DataUnitRelated(ModelClass modelClass, Service service) {
		Process process = service.getProcess();
		Set<String> cacheReferences = ServiceUtil.getDataUnitReferences(service);
		Iterator<String> iterator = cacheReferences.iterator();
		while (iterator.hasNext()) {
			String cacheName = iterator.next();
			Unit dataUnit = ProcessUtil.getDataUnit(process, cacheName);
			Assert.notNull(dataUnit, "DataUnit not found: "+cacheName);
			createMethod_CreateDataUnitHelper(modelClass, dataUnit);
			//createMethod_CreateDataUnitProxy(modelClass, dataUnit);
			createMethod_CreateDataUnitControl(modelClass, dataUnit);
			createMethod_ClearDataUnitContext(modelClass, dataUnit);
		}
	}

	public void createMethod_CreateDataUnitHelper(ModelClass modelClass, Unit dtaaUnit) {
		String cacheUnitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(dtaaUnit);
		String cacheUnitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(dtaaUnit);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+cacheUnitNameCapped+"Helper");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2(cacheUnitNameUncapped+"Helper = new "+cacheUnitNameCapped+"Helper();");
		buf.putLine2(cacheUnitNameUncapped+"Helper.setJmxProxy(jmxProxy);");
		buf.putLine2(cacheUnitNameUncapped+"Helper.setJmxManager(jmxManager);");
		//buf.putLine2(cacheUnitNameUncapped+"Helper.setCacheProxy(create"+cacheUnitNameCapped+"Proxy());");
		buf.putLine2(cacheUnitNameUncapped+"Helper.initializeAsClient(create"+cacheUnitNameCapped+"Control());");
		//buf.putLine2(cacheUnitNameUncapped+"Helper.setCacheProxy("+cacheUnitNameUncapped+"Proxy);");
		//buf.putLine2(cacheUnitNameUncapped+"Helper.initialize("+cacheUnitNameUncapped+"Control);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	public void createMethod_CreateDataUnitProxy(ModelClass modelClass, Unit dataUnit) {
		String cacheUnitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(dataUnit);
		String cacheUnitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(dataUnit);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+cacheUnitNameCapped+"Proxy");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.setResultType(cacheUnitNameCapped+"Proxy");
		
		Buf buf = new Buf();
		buf.putLine2(cacheUnitNameCapped+"Proxy "+cacheUnitNameUncapped+"Proxy = new "+cacheUnitNameCapped+"Proxy();");
		buf.putLine2(cacheUnitNameUncapped+"Proxy.setJmxManager(jmxManager);");
		buf.putLine2("return "+cacheUnitNameUncapped+"Proxy;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	public void createMethod_CreateDataUnitControl(ModelClass modelClass, Unit dataUnit) {
		String dataUnitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(dataUnit);
		String dataUnitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(dataUnit);
		
		Namespace dataUnitNamespace = dataUnit.getNamespace();
		Persistence persistenceBlock = ProjectUtil.getPersistenceBlockByNamespace(context.getProject(), dataUnitNamespace.getUri());
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+dataUnitNameCapped+"Control");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.setResultType("DataModuleTestControl");
		
		String databaseName = DataLayerHelper.getDatabaseName(persistenceBlock);
		String dataSourceName = DataLayerHelper.getDataSourceName(persistenceBlock);
		String persistenceUnitName = DataLayerHelper.getPersistenceUnitName(dataUnit);
		
		Buf buf = new Buf();
		buf.putLine2("DataModuleTestControl "+dataUnitNameUncapped+"Control = new DataModuleTestControl(transactionTestControl);");
		buf.putLine2(dataUnitNameUncapped+"Control.setDatabaseName(\""+databaseName+"\");");
		buf.putLine2(dataUnitNameUncapped+"Control.setDataSourceName(\""+dataSourceName+"\");");
		buf.putLine2(dataUnitNameUncapped+"Control.setPersistenceUnitName(\""+persistenceUnitName+"\");");
		buf.putLine2(dataUnitNameUncapped+"Control.setupDataLayer();");
		buf.putLine2("return "+dataUnitNameUncapped+"Control;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	public void createMethod_ClearDataUnitContext(ModelClass modelClass, Unit dataUnit) {
		String dataUnitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(dataUnit);
		String dataUnitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(dataUnit);
		String managerName = dataUnitNameCapped + "Manager";
			
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("clear"+dataUnitNameCapped+"Context");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		//buf.putLine2("jmxProxy.call("+dataUnitNameCapped+"MBean.MBEAN_NAME, \"clearContext\");");
		buf.putLine2(dataUnitNameUncapped+"Helper.clearContext("+managerName+".MBEAN_NAME);");
		//buf.putLine2(dataUnitNameCapped+"Proxy.clearPersistenceContext(););");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	
	public void createMethod_JUnitBeforeClass(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeClassAnnotation());
		modelOperation.setName("beforeClass");
		modelOperation.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		//buf.putLine2("BytemanUtil.initialize();");
		buf.putLine2("AbstractArquillianTest.beforeClass();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	public void createMethod_JUnitAfterClass(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterClassAnnotation());
		modelOperation.setName("afterClass");
		modelOperation.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		Buf buf = new Buf();
		buf.putLine2("AbstractArquillianTest.afterClass();");
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_GetTestClass(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getTestClass");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Class<?>");
		Buf buf = new Buf();
		buf.putLine2("return "+modelClass.getClassName()+".class;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_GetServerName(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getServerName");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		Buf buf = new Buf();
		buf.putLine2("return \"hornetQ01_local\";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_GetDomainId(ModelClass modelClass, Service service) {
		String domainId = ServiceLayerHelper.getServiceDomainName(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getDomainId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		Buf buf = new Buf();
		buf.putLine2("return \""+domainId+"\";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_GetServiceId(ModelClass modelClass, Service service) {
		String serviceId = ServiceLayerHelper.getServiceId(service);
		
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getServiceId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		Buf buf = new Buf();
		buf.putLine2("return \""+serviceId+"\";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_GetTargetArchive(ModelClass modelClass, Service service) {
		String applicationName = NameUtil.capName(context.getApplication().getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getTargetArchive");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		Buf buf = new Buf();
		buf.putLine2("return "+applicationName+"TestEARBuilder.NAME;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_GetTargetDestination(ModelClass modelClass, Service service) {
		String serviceName = ServiceUtil.getExtendedDomainServiceName(service);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getTargetDestination");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		Buf buf = new Buf();
		buf.putLine2("return get_target_"+serviceName+"_destination();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_GetMessageDestination_Target(ModelClass modelClass, Service service) {
		String serviceName = ServiceUtil.getExtendedDomainServiceName(service);
		String channelName = ServiceUtil.getFirstIncomingChannelName(service);
		String destinationName = JMSUtil.getDestinationName(service, channelName);
		destinationName += "_queue";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get_target_"+serviceName+"_destination");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		Buf buf = new Buf();
		buf.putLine2("return getJNDINameForQueue(\""+destinationName+"\");");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_GetMessageDestinations_Remote(ModelClass modelClass, Service service) {
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service remoteService = iterator.next();
			String remoteServiceName = ServiceUtil.getExtendedDomainServiceLabel(remoteService);
			String channelName = ServiceUtil.getFirstIncomingChannelName(remoteService);
			String destinationName = JMSUtil.getDestinationName(remoteService, channelName);
			destinationName += "_queue";
			
			ModelOperation modelOperation = new ModelOperation();
			modelOperation.addJavadoc("Provides destination for outgoing call to remote service.");
			//modelOperation.setName("getRemote"+serviceInterfaceName+"MessageDestination");
			modelOperation.setName("get_"+remoteServiceName+"_destination");
			modelOperation.setModifiers(Modifier.PUBLIC);
			modelOperation.setResultType("String");
			
			Buf buf = new Buf();
			buf.putLine2("return getJNDINameForQueue(\""+destinationName+"\");");
			modelOperation.addInitialSource(buf.get());
			modelClass.addInstanceOperation(modelOperation);
		}	
		
		/*
		 * TODO - SOMETHING WRONG HERE - 
		 * we should be getting IncomingCallbacks not OutgoingCallbacks
		 * but somehow, because the callback "Listener" is being associated 
		 * with the "outgoing" callback as opposed to the "incoming" callback...
		 * so we use getDistinctOutgoingCallbacks() for now...
		 */
		//provide one of these for each callback (response) from specified remote service
		List<Callback> remoteIncomingCallbacks = ServiceUtil.getDistinctIncomingCallbacks(service);
		Iterator<Callback> iterator2 = remoteIncomingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			String callbackName = ServiceUtil.getExtendedDomainServiceName(callback);
			String channelName = ServiceUtil.getFirstIncomingChannelName(callback);
			String destinationName = JMSUtil.getDestinationName(callback, channelName);
			destinationName += "_queue";
			
			ModelOperation modelOperation = new ModelOperation();
			modelOperation.addJavadoc("Provides destination for incoming response back from remote service.");
			//modelOperation.setName("getRemote"+clientInterfaceName+"ResponseDestination");
			modelOperation.setName("get_remote_"+callbackName+"_destination");
			modelOperation.setModifiers(Modifier.PUBLIC);
			modelOperation.setResultType("String");
			
			Buf buf = new Buf();
			buf.putLine2("return getJNDINameForQueue(\""+destinationName+"\");");
			modelOperation.addInitialSource(buf.get());
			modelClass.addInstanceOperation(modelOperation);
		}
	}
	
	protected void createMethods_GetMessageDestinations_Local(ModelClass modelClass, Service service) {
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		for (char letter = 'a'; iterator.hasNext(); letter++) {
			Callback callback = iterator.next();
			Operation operation = ServiceUtil.getDefaultOperation(callback);
			String callbackName = ServiceUtil.getExtendedDomainServiceName(callback);
			//String messageBaseName = getMessageBaseName(operation);
			
			ModelOperation modelOperation = new ModelOperation();
			modelOperation.addJavadoc("Provides destination for outgoing response back to caller.");
			//modelOperation.setName("getLocal"+messageBaseName+"ResponseDestination");
			modelOperation.setName("get_local_"+callbackName+"_destination");
			modelOperation.setModifiers(Modifier.PUBLIC);
			modelOperation.setResultType("String");
			Buf buf = new Buf();
			buf.putLine2("return \"test_message_domain_test_destination_queue_"+letter+"\";");
			modelOperation.addInitialSource(buf.get());
			modelClass.addInstanceOperation(modelOperation);
		}
	}
	
	protected void createMethod_JUnitSetup(ModelClass modelClass, Service service) {
		Process process = service.getProcess();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeAnnotation());
		modelOperation.setName("setUp");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("super.setUp();");
		buf.putLine2("startServer();");
		buf.putLine2("createTransactionControl();");
		if (ProcessUtil.hasIncomingEvents(process))
			buf.putLine2("createEventMulticasterProxy();");
		
		Set<Cache> cacheReferences = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheReferences.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			//Cache cacheUnit = ProcessUtil.getCacheUnit(process, cacheName);
			Assert.notNull(cacheUnit, "CacheUnit not found: "+cacheUnit.getName());
			String cacheUnitNameCapped = DataLayerHelper.getCacheUnitNameCapped(cacheUnit);
			//buf.putLine2("create"+cacheUnitNameCapped+"Control();");
			buf.putLine2("create"+cacheUnitNameCapped+"Helper();");
		}
		
		Set<String> dataUnitReferences = ServiceUtil.getDataUnitReferences(service);
		Iterator<String> iterator2 = dataUnitReferences.iterator();
		while (iterator2.hasNext()) {
			String cacheName = iterator2.next();
			Unit dataUnit = ProcessUtil.getDataUnit(process, cacheName);
			Assert.notNull(dataUnit, "CacheUnit not found: "+cacheName);
			String dataUnitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(dataUnit);
			//buf.putLine2("create"+dataUnitNameCapped+"Control();");
			buf.putLine2("create"+dataUnitNameCapped+"Helper();");
			buf.putLine2("clear"+dataUnitNameCapped+"Context();");
		}
		
		buf.putLine2("removeMessagesFromDestinations();");
		
//		List<Callback> remoteIncomingCallbacks = ServiceUtil.getDistinctIncomingCallbacks(service);
//		Iterator<Callback> iterator3 = remoteIncomingCallbacks.iterator();
//		while (iterator3.hasNext()) {
//			Callback callback = iterator3.next();
//			String clientName = ServiceUtil.getDomainServiceName(callback);
//			String extendedName = ServiceUtil.getExtendedDomainServiceName(callback);
//			buf.putLine2("remote"+clientName+"Sender = createClient(get_remote_"+extendedName+"_destination());");
//			//buf.putLine2("remote"+clientInterfaceName+"Sender = createClient(getRemote"+clientInterfaceName+"ResponseDestination());");
//		}
		
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator3 = remoteServices.iterator();
		while (iterator3.hasNext()) {
			Service remoteService = iterator3.next();
			List<Callback> remoteOutgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
			Iterator<Callback> iterator4 = remoteOutgoingCallbacks.iterator();
			while (iterator4.hasNext()) {
				Callback callback = iterator4.next();
				String clientName = ServiceUtil.getDomainServiceName(callback);
				String clientName2 = ServiceUtil.getExtendedDomainServiceName(service, callback);
				buf.putLine2("remote"+clientName+"Sender = createClient(get_remote_"+clientName2+"_destination());");
				//buf.putLine2("remote"+clientInterfaceName+"Sender = createClient(getRemote"+clientInterfaceName+"ResponseDestination());");
			}
		}
		
//		Operation operation = ServiceUtil.getDefaultOperation(service);
//		String messagePackageName = getMessagePackageName(operation);
//		String messageClassName = getMessageClassName(operation);
//		//String messageBeanName = getMessageBeanName(operation);
//		modelClass.addImportedClass(messagePackageName + "." + messageClassName);
//		//buf.putLine2(messageBeanName+" = create"+messageClassName+"();");
//		buf.putLine2("create"+messageClassName+"();");

//		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
//		Iterator<Callback> iterator = outgoingCallbacks.iterator();
//		while (iterator.hasNext()) {
//			Callback callback = iterator.next();
//			String clientName = ClientLayerHelper.getClientNameUncapped(callback);
//			String clientNameCapped = ClientLayerHelper.getClientNameCapped(callback);
//			buf.putLine2(clientName+"Listener = create"+clientNameCapped+"ResponseListener();");
//		}

//		iterator = outgoingCallbacks.iterator();
//		while (iterator.hasNext()) {
//			Callback callback = iterator.next();
//			String clientName = ClientLayerHelper.getClientNameUncapped(callback);
//			Map<String, JMSDestination> jmsDestinations = JMSUtil.buildJMSDestinations(context.getProject(), service, callback);
//			Iterator<String> iterator2 = jmsDestinations.keySet().iterator();
//			while (iterator2.hasNext()) {
//				String destinationKey = iterator2.next();
//				JMSDestination jmsDestination = jmsDestinations.get(destinationKey);
//				String jmsDestinationName = JMSUtil.getDestinationName(service.getDomain(), callback.getName(), jmsDestination.channel);
//				buf.putLine2(clientName+"Sender = createClient(\"jms/queue/"+jmsDestinationName+"_queue\");");
//			}
//		}
		
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator4 = operations.iterator();
		while (iterator4.hasNext()) {
			Operation operation = iterator4.next();
			if (!operation.getParameters().isEmpty()) {
				String messageNamespace = getMessageNamespace(operation);
				//String fixturePackageName = ModelLayerHelper.getModelFixturePackageName(messageNamespace);
				String fixtureClassName = ModelLayerHelper.getModelFixtureClassName(messageNamespace);
				buf.putLine2(fixtureClassName+".reset();");
			}
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_CreateTransactionTestControl(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createTransactionControl");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("transactionTestControl = new TransactionTestControl();");
		buf.putLine2("transactionTestControl.setupTransactionManager();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	public void createMethod_CreateEventMulticasterProxy(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createEventMulticasterProxy");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		
		String applicationName = NameUtil.capName(context.getApplication().getName());
		modelClass.addImportedClass(service.getDomain()+".event."+applicationName+"EventReceiverMBean");
		
		Buf buf = new Buf();
		buf.putLine2("eventMulticasterProxy = new EventMulticasterProxy();");
		buf.putLine2("eventMulticasterProxy.setJmxManager(jmxManager);");
		buf.putLine2("eventMulticasterProxy.setMBeanName("+applicationName+"EventReceiverMBean.MBEAN_NAME);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethods_CreateMessages(ModelClass modelClass, Service service) {
		createMethod_CreateMessage_Input(modelClass, service);
		createMethod_CreateMessage_InputCancel(modelClass, service);
		createMethod_CreateMessage_InputUndo(modelClass, service);
		createMethod_CreateMessage_InputFull(modelClass, service);
		createMethods_CreateMessage_Responses(modelClass, service);
	}

	protected void createMethod_CreateMessage_Input(ModelClass modelClass, Service service) {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;

		String messageClassName = getMessageClassName(operation);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+messageClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(messageClassName);
		
		Buf buf = new Buf();
		buf.putLine2("return create"+messageClassName+"(false, false);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CreateMessage_InputCancel(ModelClass modelClass, Service service) {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;

		String messageClassName = getMessageClassName(operation);
		String messageBaseName = getMessageBaseName(operation);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+messageBaseName+"CancelMessage");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(messageClassName);
		
		Buf buf = new Buf();
		buf.putLine2("return create"+messageClassName+"(true, false);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CreateMessage_InputUndo(ModelClass modelClass, Service service) {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;

		String messageClassName = getMessageClassName(operation);
		String messageBaseName = getMessageBaseName(operation);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+messageBaseName+"UndoMessage");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(messageClassName);
		
		Buf buf = new Buf();
		buf.putLine2("return create"+messageClassName+"(false, true);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CreateMessage_InputFull(ModelClass modelClass, Service service) {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;

		String messagePackageName = getMessagePackageName(operation);
		String messageClassName = getMessageClassName(operation);
		String messageBeanName = getMessageBeanName(operation);
		String messageNamespace = getMessageNamespace(operation);
		messageBeanName = "message";
		
		String fixturePackageName = ModelLayerHelper.getModelFixturePackageName(messageNamespace);
		String fixtureClassName = ModelLayerHelper.getModelFixtureClassName(messageNamespace);
		modelClass.addImportedClass(messagePackageName + "." + messageClassName);
		modelClass.addImportedClass(fixturePackageName + "." + fixtureClassName);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+messageClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(CodeUtil.createParameter("java.lang", "boolean", "cancel"));
		modelOperation.addParameter(CodeUtil.createParameter("java.lang", "boolean", "undo"));
		modelOperation.setResultType(messageClassName);

		Buf buf = new Buf();
		buf.putLine2(messageClassName +" "+messageBeanName+" = "+fixtureClassName+".create_"+messageClassName+"(cancel, undo);");
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
			
			String messageBaseName = "messageBaseName";
			if (!ServiceUtil.isSynchronous(service)) {
				messageBaseName = getMessageBaseName(callbackOperation);
			}
			
			String callbackName = ServiceUtil.getExtendedDomainServiceName(callback);
			buf.putLine2(messageBeanName+".addToReplyToDestinations(\""+messageBaseName+"\", get_local_"+callbackName+"_destination());");
			//buf.putLine2(messageBeanName+".addToReplyToDestinations(\""+messageBaseName+"\", getLocal"+messageBaseName+"ResponseDestination());");
		}
		buf.putLine2("initializeMessage("+messageBeanName+");");
		buf.putLine2("return "+messageBeanName+";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_CreateMessage_Responses(ModelClass modelClass, Service service) {
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service remoteService = iterator.next();
			List<Callback> remoteOutgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
			Iterator<Callback> iterator2 = remoteOutgoingCallbacks.iterator();
			while (iterator2.hasNext()) {
				Callback callback = iterator2.next();
				createMethod_CreateMessage_Response(modelClass, callback);
			}
		}
	}

	protected void createMethod_CreateMessage_Response(ModelClass modelClass, Service service) {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		String messageClassName = getMessageClassName(operation);
		String messageNamespace = getMessageNamespace(operation);
		
		String fixturePackageName = ModelLayerHelper.getModelFixturePackageName(messageNamespace);
		String fixtureClassName = ModelLayerHelper.getModelFixtureClassName(messageNamespace);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+messageClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(messageClassName);
		
		Buf buf = new Buf();
		buf.putLine2(messageClassName+" message = "+fixtureClassName+".create_"+messageClassName+"();");
		buf.putLine2("initializeMessage(message);");
		buf.putLine2("return message;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_CreateEvents(ModelClass modelClass, Service service) {
		Process process = service.getProcess();
		Set<String> events = ProcessUtil.getIncomingEvents(process);
		Iterator<String> iterator = events.iterator();
		while (iterator.hasNext()) {
			String eventName = iterator.next();
			if (!eventName.endsWith("Event"))
				eventName += "Event";

			Element eventElement = context.getElementByName(eventName);
			Assert.notNull(eventElement, "Event element not found: "+eventName);
			modelClass.addImportedClass(eventElement);
			
			String eventBeanName = NameUtil.uncapName(eventName);
			String eventNamespace = TypeUtil.getNamespace(eventElement.getType());
			String fixturePackageName = ModelLayerHelper.getModelFixturePackageName(eventNamespace);
			String fixtureClassName = ModelLayerHelper.getModelFixtureClassName(eventNamespace);
			modelClass.addImportedClass(fixturePackageName + "." + fixtureClassName);
			
			ModelOperation modelOperation = new ModelOperation();
			modelOperation.setName("create"+eventName);
			modelOperation.setModifiers(Modifier.PUBLIC);
			
			Buf buf = new Buf();
			buf.putLine2(eventBeanName+" = "+fixtureClassName+".create_"+eventName+"();");
			buf.putLine2("initializeEvent("+eventBeanName+");");
			modelOperation.addInitialSource(buf.get());
			modelClass.addInstanceOperation(modelOperation);
		}
	}

	protected void createMethod_RegisterNotificationListeners(ModelClass modelClass, Service service) {
		String domain = ServiceLayerHelper.getServiceDomainName(service);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("registerNotificationListeners");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		String serviceNamespaceAlias = ServiceUtil.getNamespaceAlias(service);
		String domainServiceName = ServiceUtil.getExtendedDomainServiceName(service);
		//String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		buf.putLine2("addRequestNotificationListeners(\""+domainServiceName+"\");");
		
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			domain = callback.getDomain();
			String callbackNamespaceAlias = ServiceUtil.getNamespaceAlias(callback);
			String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
			buf.putLine2("addResponseNotificationListeners(\""+callbackNamespaceAlias+"_"+callbackInterfaceName+"\");");
		}

		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator3 = remoteServices.iterator();
		while (iterator3.hasNext()) {
			Service remoteService = iterator3.next();
			domain = remoteService.getDomain();
			String remoteNamespaceAlias = ServiceUtil.getNamespaceAlias(remoteService);
			String remoteServiceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(remoteService);
			buf.putLine2("addRequestNotificationListeners(\""+remoteNamespaceAlias+"_"+remoteServiceInterfaceName+"\");");
			
			List<Callback> remoteOutgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
			Iterator<Callback> iterator4 = remoteOutgoingCallbacks.iterator();
			while (iterator4.hasNext()) {
				Callback callback = iterator4.next();
				domain = callback.getDomain();
				String callbackNamespaceAlias = ServiceUtil.getNamespaceAlias(callback);
				String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
				buf.putLine2("addResponseNotificationListeners(\""+callbackNamespaceAlias+"_"+callbackInterfaceName+"\");");
			}
		}
		
		Process process = service.getProcess();
		Set<String> events = ProcessUtil.getIncomingEvents(process);
		Iterator<String> iterator2 = events.iterator();
		while (iterator2.hasNext()) {
			String eventName = iterator2.next();
			domain = ServiceLayerHelper.getServiceDomainName(service);
			buf.putLine2("addProcessNotificationListeners(\""+serviceNamespaceAlias+"_"+eventName+"\");");
		}
		
		buf.putLine2("super.registerNotificationListeners();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_JUnitTeardown(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setName("tearDown");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("clearStructures();");
		buf.putLine2("clearState();");
		buf.putLine2("super.tearDown();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_ClearStructures(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("clearStructures");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		String serviceNameUncapped = ServiceLayerHelper.getServiceNameUncapped(service);
		buf.putLine2(serviceNameUncapped+"Client.reset();");
		buf.putLine2(serviceNameUncapped+"Client = null;");

		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		if (!remoteServices.isEmpty()) {
			buf.putLine2("");
			buf.putLine2("//remote handler(s) for requests to remote (mocked) service(s)");
			Iterator<Service> iterator = remoteServices.iterator();
			while (iterator.hasNext()) {
				Service remoteService = iterator.next();
				String serviceName = ServiceUtil.getDomainServiceName(remoteService);
				buf.putLine2("remote"+serviceName+"Handler.reset();");
				buf.putLine2("remote"+serviceName+"Handler = null;");
			}
			
			iterator = remoteServices.iterator();
			while (iterator.hasNext()) {
				Service remoteService = iterator.next();
				//provide one of these for each callback (response) from specified remote service
				List<Callback> remoteOutgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
				if (remoteOutgoingCallbacks.size() > 0) {
					buf.putLine2("");
					buf.putLine2("//remote clients for sending responses back from remote (mocked) service(s)");
					Iterator<Callback> iterator2 = remoteOutgoingCallbacks.iterator();
					while (iterator2.hasNext()) {
						Callback callback = iterator2.next();
						String callbackName = ServiceUtil.getDomainServiceName(callback);
						buf.putLine2("remote"+callbackName+"Sender.reset();");
						buf.putLine2("remote"+callbackName+"Sender = null;");
					}
				}
			}
		}
		
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		if (!outgoingCallbacks.isEmpty()) {
			buf.putLine2("");
			buf.putLine2("//local handlers for responses from target service");
			Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
			while (iterator2.hasNext()) {
				Callback callback = iterator2.next();
				String callbackName = ServiceUtil.getDomainServiceName(callback);
				buf.putLine2("local"+callbackName+"Handler.reset();");
				buf.putLine2("local"+callbackName+"Handler = null;");
				//buf.putLine2(clientName+"Listener = null;");
			}
		}

		buf.putLine2("super.clearStructures();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_ClearState(ModelClass modelClass, Service service) {
		List<Callback> callbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Set<String> messageCreated = new HashSet<String>();

		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("clearState");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		
		Process process = service.getProcess();
//		Set<String> cacheReferences = ServiceUtil.getCacheReferences(service);
//		Iterator<String> iterator = cacheReferences.iterator();
//		while (iterator.hasNext()) {
//			String cacheName = iterator.next();
//			//Cache cacheUnit = ProcessUtil.getCacheUnit(process, cacheName);
//			buf.putLine2(cacheName+"Helper.assureRemoveAll();");
//		}
		
		//input message to target service
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (!operation.getParameters().isEmpty()) {
			String messageBeanName = getMessageBeanName(operation);
			buf.putLine2(messageBeanName+" = null;");
			messageCreated.add(messageBeanName);
		}
		
		//callback messages for target service
		//provide one of these for each callback (response) from target service
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
			
			String callbackMessageBeanName = "callbackMessageBeanName";
			if (!ServiceUtil.isSynchronous(service)) {
				callbackMessageBeanName = getMessageBeanName(callbackOperation);
			}
			
			if (!messageCreated.contains(callbackMessageBeanName)) {
				buf.putLine2(callbackMessageBeanName+" = null;");
				messageCreated.add(callbackMessageBeanName);
			}
		}

		//provide one of these for communication to each specified remote service
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator3 = remoteServices.iterator();
		while (iterator3.hasNext()) {
			Service remoteService = iterator3.next();
			operation = ServiceUtil.getDefaultOperation(remoteService);
			if (operation.getParameters().isEmpty())
				continue;

			String messageBeanName = getMessageBeanName(operation);
			if (!messageCreated.contains(messageBeanName)) {
				buf.putLine2(messageBeanName+" = null;");
				messageCreated.add(messageBeanName);
			}
			
//			//provide one of these for each callback (response) from specified remote service
//			List<Callback> remoteOutgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
//			Iterator<Callback> iterator4 = remoteOutgoingCallbacks.iterator();
//			while (iterator4.hasNext()) {
//				Callback callback = iterator4.next();
//				Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
//				String callbackMessageBeanName = getMessageBeanName(callbackOperation);
//				if (!messageCreated.contains(callbackMessageBeanName)) {
//					buf.putLine2(callbackMessageBeanName+" = null;");
//					messageCreated.add(callbackMessageBeanName);
//				}
//			}
		}
		
		//events
		Set<String> events = ProcessUtil.getIncomingEvents(process);
		Iterator<String> iterator5 = events.iterator();
		while (iterator5.hasNext()) {
			String eventName = iterator5.next();
			if (!eventName.endsWith("Event"))
				eventName += "Event";
			String eventBeanName = NameUtil.uncapName(eventName);
			buf.putLine2(eventBeanName+" = null;");
		}
		
		//flags for communication to each specified remote service
		iterator3 = remoteServices.iterator();
		while (iterator3.hasNext()) {
			Service remoteService = iterator3.next();
			operation = ServiceUtil.getDefaultOperation(remoteService);
			String messageReceivedFlagName = getMessageReceivedFlagName(operation);
			buf.putLine2(messageReceivedFlagName+" = false;");

//			//flags for each callback (response) from specified remote service
//			List<Callback> remoteOutgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
//			Iterator<Callback> iterator4 = remoteOutgoingCallbacks.iterator();
//			while (iterator4.hasNext()) {
//				Callback callback = iterator4.next();
//				Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
//				messageReceivedFlagName = getMessageReceivedFlagName(callbackOperation);
//				buf.putLine2(messageReceivedFlagName+" = false;");
//			}
		}
		
		//flags
		Iterator<Callback> iterator6 = callbacks.iterator();
		while (iterator6.hasNext()) {
			Callback callback = iterator6.next();
			Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
			if (callbackOperation.getParameters().isEmpty())
				continue;
			
			String messageReceivedFlagName = getMessageReceivedFlagName(callbackOperation);
			buf.putLine2(messageReceivedFlagName+" = false;");
		}
		
		buf.putLine2("super.clearState();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ClearMessages(ModelClass modelClass, Service service) {
		String serviceName = ServiceUtil.getExtendedDomainServiceName(service);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("removeMessagesFromDestinations");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("removeMessagesFromQueue(getTargetArchive(), get_target_"+serviceName+"_destination());");
		
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			String callbackName = ServiceUtil.getExtendedDomainServiceName(callback);
			buf.putLine2("removeMessagesFromQueue(getTargetArchive(), get_local_"+callbackName+"_destination());");
			//buf.putLine2("removeMessagesFromQueue(getTargetArchive(), getLocal"+serviceInterfaceName+"ResponseDestination());");
			//buf.putLine2("removeMessagesFromQueue(getTargetArchive(), getJNDINameForQueue(getLocal"+serviceInterfaceName+"Destination()));");
		}
		
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service remoteService = iterator.next();
			String remoteServiceName = ServiceUtil.getExtendedDomainServiceName(remoteService);
			buf.putLine2("removeMessagesFromQueue(getTargetArchive(), get_remote_"+remoteServiceName+"_destination());");
			//buf.putLine2("removeMessagesFromQueue(getTargetArchive(), getRemote"+serviceInterfaceName+"MessageDestination());");
			
			//provide one of these for each callback (response) from specified remote service
			List<Callback> remoteOutgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
			Iterator<Callback> iterator3 = remoteOutgoingCallbacks.iterator();
			while (iterator3.hasNext()) {
				Callback remoteCallback = iterator3.next();
				String remoteCallbackName = ServiceUtil.getExtendedDomainServiceName(service, remoteCallback);
				buf.putLine2("removeMessagesFromQueue(getTargetArchive(), get_remote_"+remoteCallbackName+"_destination());");
				//buf.putLine2("removeMessagesFromQueue(getTargetArchive(), getRemote"+clientInterfaceName+"ResponseDestination());");
			}
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CreateTXManagerEAR(ModelClass modelClass) {
		String applicationName = NameUtil.capName(context.getApplication().getName());
		String applicationNameUncapped = NameUtil.uncapName(applicationName);
		String containerName = "hornetQ01_local";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createArquillianDeploymentAnnotation("txManagerEAR", 1));
		modelOperation.addAnnotation(AnnotationUtil.createArquillianTargetsContainerAnnotation(containerName));
		modelOperation.setName("createTXManagerEAR");
		modelOperation.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		modelOperation.setResultType("EnterpriseArchive");
		Buf buf = new Buf();
		buf.putLine2("TXManagerEARBuilder builder = new TXManagerEARBuilder();");
		//if (includeTestWar)
		//	buf.putLine2("builder.addAsModule(createTestWAR(), \""+applicationNameUncapped+"\");");
		buf.putLine2("return builder.createEAR();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		String namespace = context.getApplication().getNamespace();
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		modelClass.addImportedClass(packageName + "." +applicationName + "EARBuilder");
		modelClass.addImportedClass(packageName + "." +applicationName + "TestWARBuilder");
	}
	
	protected void createMethod_CreateTestEAR(ModelClass modelClass) {
		String applicationName = NameUtil.capName(context.getApplication().getName());
		String applicationNameUncapped = NameUtil.uncapName(applicationName);
		String containerName = "hornetQ01_local";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createArquillianTargetsContainerAnnotation(containerName));
		modelOperation.addAnnotation(AnnotationUtil.createArquillianDeploymentAnnotation(applicationNameUncapped+"EAR", 2));
		modelOperation.setName("create"+applicationName+"EAR");
		modelOperation.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		modelOperation.setResultType("EnterpriseArchive");
		Buf buf = new Buf();
		buf.putLine2(applicationName+"TestEARBuilder builder = new "+applicationName+"TestEARBuilder();");
		buf.putLine2("builder.setRunningAsClient(true);");
		//TODO
		//if (includeTestWar)
		//	buf.putLine2("builder.setIncludeWar(true);");
		//else buf.putLine2("builder.addAsModule(createTestWAR(), \""+applicationNameUncapped+"\");");
		buf.putLine2("return builder.createEAR();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		String namespace = context.getApplication().getNamespace();
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		modelClass.addImportedClass(packageName + "." +applicationName + "TestEARBuilder");
		if (includeTestWar)
			modelClass.addImportedClass(packageName + "." +applicationName + "TestWARBuilder");
	}
	
	protected void createMethod_CreateTestWAR(ModelClass modelClass) {
		String applicationName = NameUtil.capName(context.getApplication().getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createTestWAR");
		modelOperation.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		modelOperation.setResultType("WebArchive");
		Buf buf = new Buf();
		buf.putLine2(applicationName+"TestWARBuilder builder = new "+applicationName+"TestWARBuilder(\"test.war\");");
		buf.putLine2("builder.addClass("+modelClass.getClassName()+".class);");
		buf.putLine2("return builder.create();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_EventsExist(ModelClass modelClass, Service service, Process process) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("eventsExist");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("boolean");
		
		Buf buf = new Buf();
		buf.put2("return ");
		Set<String> events = ProcessUtil.getAllEvents(process);
		Iterator<String> iterator = events.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String eventName = NameUtil.uncapName(iterator.next());
			if (!eventName.endsWith("Event"))
				eventName += "Event";
			if (i == 0)
				buf.put(eventName+" != null");
			else buf.put3(eventName+" != null");
			if (i < events.size() - 1)
				buf.putLine(" ||");
			else buf.putLine(";");
		}
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_FireEvents(ModelClass modelClass, Service service, Process process) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("fireEvents");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		Set<String> events = ProcessUtil.getAllEvents(process);
		Iterator<String> iterator = events.iterator();
		while (iterator.hasNext()) {
			String eventName = NameUtil.uncapName(iterator.next());
			if (!eventName.endsWith("Event"))
				eventName += "Event";
			buf.putLine2("if ("+eventName+" != null)");
			buf.putLine2("	fireEvent("+eventName+");");
		}
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_FireEvent(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("fireEvent");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter("AbstractEvent", "event"));
		
		modelClass.addImportedClass("org.aries.common.AbstractEvent");
		modelClass.addImportedClass("org.aries.event.multicaster.EventMulticasterProxy");

		Buf buf = new Buf();
		buf.putLine2("eventMulticasterProxy.dispatchEvent(event);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_StartMainClient(ModelClass modelClass, Service service) {
		String interfaceName = ClientLayerHelper.getClientInterfaceName(service);
		String clientPackageName = ClientLayerHelper.getClientPackageName(service);
		String serviceName = ServiceUtil.getExtendedDomainServiceName(service);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("start_" + interfaceName + "_client");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("JmsClient");
		modelOperation.addException("Exception");
		
		modelClass.addImportedClass(clientPackageName+"."+interfaceName);
		modelClass.addImportedClass(clientPackageName+"."+interfaceName+"ProxyForJMS");
		
		Buf buf = new Buf();
		buf.putLine2("JmsClient client = new "+interfaceName+"ProxyForJMS("+interfaceName+".ID);");
		//buf.putLine2("configureClient(client, get_target_"+serviceName+"_destination());");
		buf.putLine2("configureClient(client, getTargetDestination());");
		buf.putLine2("client.setCreateTemporaryQueue(true);");
		buf.putLine2("client.initialize();");
		buf.putLine2("return client;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_StartServiceListeners(ModelClass modelClass, Service service) {
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service remoteService = iterator.next();
			createMethod_StartServiceListener(modelClass, remoteService);
		}

		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			createMethod_StartServiceListener(modelClass, callback);
		}
	}

//	protected void createMethod_StartCallbackHandler(ModelClass modelClass, Service service, Callback callback) {
//		List<Channel> channels = ServiceUtil.getChannels(callback);
//		Iterator<Channel> iterator = channels.iterator();
//		while (iterator.hasNext()) {
//			Channel channel = iterator.next();
//			createMethod_StartCallbackHandler(modelClass, service, callback, channel);
//		}
//	}

	protected void createMethod_StartServiceListener(ModelClass modelClass, Service service) {
		String extendedName = ServiceUtil.getExtendedDomainServiceLabel(service);
		String extendedTypedName = ServiceUtil.getExtendedTypedDomainServiceLabel(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("start_" + extendedName + "_handler");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("JmsClient");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (service instanceof Callback)
			buf.putLine2("JmsClient client = createClient(getJNDINameForQueue(get_"+extendedName+"_destination()));");
		else buf.putLine2("JmsClient client = createClient(get_"+extendedName+"_destination());");
		buf.putLine2("client.setMessageListener(create_"+extendedTypedName+"_listener());");
		buf.putLine2("client.initialize();");
		buf.putLine2("return client;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}


	protected void createMethods_CreateServiceListeners(ModelClass modelClass, Service service) {
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service remoteService = iterator.next();
			createMethod_CreateServiceListener(modelClass, remoteService);
			List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
			if (outgoingCallbacks.size() > 0) {
				createMethod_ProcessMessage(modelClass, remoteService);
			}
		}

		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			createMethod_CreateServiceListener(modelClass, callback);
		}
	}

	protected void createMethod_CreateServiceListener(ModelClass modelClass, Service service) {
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		Operation operation = ServiceUtil.getDefaultOperation(service);
		
		boolean isCallback = service instanceof Callback;
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		String extendedName = ServiceUtil.getExtendedTypedDomainServiceLabel(service);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create_"+extendedName+"_listener");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("MessageListener");

		Buf buf = new Buf();
		if (operation.getParameters().isEmpty()) {
			buf.putLine2("return new MessageListener() {");
			buf.putLine2("	public void onMessage(Message message) {");
			buf.putLine2("		try {");
			buf.putLine2("			String jmsMessageID = message.getJMSMessageID();");
			buf.putLine2("			log.info(\"#### [test]: "+interfaceName+": received: \"+jmsMessageID);");
			buf.putLine2("			XXXXXX");
			buf.putLine2("			");
			buf.putLine2("		} catch (Throwable e) {");
			buf.putLine2("			errorMessage = ExceptionUtils.getRootCauseMessage(e);");
			buf.putLine2("			e.printStackTrace();");
			buf.putLine2("		} finally {");
			buf.putLine2("		}");
			buf.putLine2("	}");
			buf.putLine2("};");
			
		} else {
			String messageClassName = getMessageClassName(operation);
			String messageBeanName = getMessageBeanName(operation);
			String messageFlagName = getMessageReceivedFlagName(operation);
			String domainName = ServiceLayerHelper.getServiceDomainName(service);
	
			buf.putLine2("return new MessageListener() {");
			buf.putLine2("	public void onMessage(Message message) {");
			buf.putLine2("		try {");
			buf.putLine2("			String jmsMessageID = message.getJMSMessageID();");
			buf.putLine2("			log.info(\"#### [test]: "+interfaceName+": received: \"+jmsMessageID);");
			buf.putLine2("			Object object = MessageUtil.getPart(message, \""+messageBeanName+"\");");
			buf.putLine2("			Assert.isTrue(object instanceof "+messageClassName+", \"Payload type not correct\");");
			buf.putLine2("			"+messageClassName+" "+messageBeanName+" = ("+messageClassName+") object;");
			
			if (isCallback) {
				buf.putLine2("			validateMessage("+messageBeanName+");");
			} else {
				buf.putLine2("			switch (getActionFromMessage("+messageBeanName+")) {");
				buf.putLine2("			case CANCEL:");
				buf.putLine2("			case UNDO:");
				buf.putLine2("				break;");
				buf.putLine2("			default:");
				buf.putLine2("				validateMessage("+messageBeanName+");");
				if (outgoingCallbacks.size() > 0)
					buf.putLine2("				processMessage("+messageBeanName+");");
				buf.putLine2("				break;");
				buf.putLine2("			}");
			}
			
			buf.putLine2("		} catch (Throwable e) {");
			buf.putLine2("			errorMessage = ExceptionUtils.getRootCauseMessage(e);");
			buf.putLine2("			e.printStackTrace();");
			buf.putLine2("		} finally {");
			buf.putLine2("			if (expectedMessage != null && expectedMessage.equalsIgnoreCase(\""+domainName+"."+interfaceName+"\"))");
			buf.putLine2("				expectedMessageResult.set(true);");
			buf.putLine2("				"+messageFlagName+" = true;");
			buf.putLine2("		}");
			buf.putLine2("	}");
			buf.putLine2("};");
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ProcessMessage(ModelClass modelClass, Service service) {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		String messageClassName = getMessageClassName(operation);
		String messageBeanName = getMessageBeanName(operation);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("processMessage");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter(messageClassName, messageBeanName));

		Buf buf = new Buf();
		buf.putLine2("if (expectedCallback == null)");
		buf.putLine2("	return;");

		buf.putLine2("");
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Callback callback = iterator.next();
			String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
			String callbackName = ServiceUtil.getDomainServiceName(callback);
			Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
			String callbackMessageClassName = getMessageClassName(callbackOperation);
			String callbackMessageBeanName = getMessageBeanName(callbackOperation);			
			
			if (i == 0)
				buf.put2("");
			else buf.put2("} else ");
			buf.putLine("if (expectedCallback.equals(\""+callbackInterfaceName+"\")) {");
			buf.putLine2("	log.info(\"#### [test]: "+callbackInterfaceName+": sending\");");
			//buf.putLine2("	"+callbackMessageClassName+" "+callbackMessageBeanName+" = create"+callbackMessageClassName+"();");
			buf.putLine2("	initializeMessage("+callbackMessageBeanName+", "+messageBeanName+");");
			buf.putLine2("	remote"+callbackName+"Sender.sendResponse("+callbackMessageBeanName+");");
			if (i+1 < outgoingCallbacks.size())
				buf.putLine2("");
			else buf.putLine2("}");
		}

		//TODO use a much better way to distinguish a "failure" message...
		if (messageBeanName.contains("Failed") || messageBeanName.contains("Failure")) {
			String messageBaseName = NameUtil.uncapName(getMessageBaseName(operation));
			buf.putLine2("Assert.equals("+messageBeanName+".getReason(), "+messageBaseName+"Reason);");
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ValidateResult(ModelClass modelClass, Service service) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("validateResult");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(CodeUtil.createParameter("java.lang", "Object", "result"));
		
		Buf buf = new Buf();
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Callback callback = iterator.next();
			Operation operation = ServiceUtil.getDefaultOperation(callback);
			if (operation.getParameters().isEmpty())
				continue;
			
			String messageClassName = getMessageClassName(operation);
			
			if (i == 0)
				buf.putLine2("if (result instanceof "+messageClassName+") {");
			else buf.putLine2("} else if (result instanceof "+messageClassName+") {");
			buf.putLine2("	validateMessage(("+messageClassName+") result);");
		}
		
		buf.putLine2("} else if (result instanceof Throwable) {");
		buf.putLine2("	Throwable exception = (Throwable) result;");
		buf.putLine2("	if (exceptionMessage != null && !exception.getMessage().equals(exceptionMessage))");
		buf.putLine2("		errorMessage = \"Unexpected exception message: \"+exception.getMessage();");
		buf.putLine2("} else if (result instanceof String) {");
		buf.putLine2("	String resultString = result.toString();");
		buf.putLine2("	if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&");
		buf.putLine2("		(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))");
		buf.putLine2("			errorMessage = \"Unexpected message: \"+result;");
		buf.putLine2("} else {");
		buf.putLine2("	errorMessage = \"Unrecognized result: \"+result;");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_ValidateMessages(ModelClass modelClass, Service service) {
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		while (iterator.hasNext()) {
			Service remoteService = iterator.next();
			createMethod_ValidateMessage(modelClass, remoteService);
		}
		
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			createMethod_ValidateMessage(modelClass, callback);
		}
	}
	
	protected void createMethod_ValidateMessage(ModelClass modelClass, Service service) {
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;
		
		String messageClassName = getMessageClassName(operation);
		String messageBeanName = getMessageBeanName(operation);
		String messageType = getMessageType(operation);
		
		String helperName = ModelLayerHelper.getModelHelperBeanName(messageType);
		String helperQualifiedName = ModelLayerHelper.getModelHelperQualifiedName(messageType);
		modelClass.addImportedClass(helperQualifiedName);
		
		Element messageElement = context.getElementByType(messageType);
		Assert.notNull(messageElement, "Element not found for message: "+messageType);
		List<Attribute> attributes = ElementUtil.getAttributes(messageElement);
		List<Reference> references = ElementUtil.getReferences(messageElement);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("validateMessage");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter(messageClassName, messageBeanName));

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+messageBeanName+", \"Message must be specified\");");

		//buf.putLine2(helperName+".assertMessageEquals(this."+messageBeanName+", "+messageBeanName+");");

		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceNameCapped = NameUtil.capName(reference.getName());
			String referenceClassName = TypeUtil.getClassName(reference.getType());
			
			//helperName = ModelLayerHelper.getModelHelperBeanName(reference.getType());
			String fixtureClassName = ModelLayerHelper.getFixtureClassName(reference.getType());
			//helperQualifiedName = ModelLayerHelper.getHelperQualifiedName(reference.getType());
			String fixtureQualifiedName = ModelLayerHelper.getFixtureQualifiedName(reference.getType());
			modelClass.addImportedClass(fixtureQualifiedName);
			
			buf.putLine2("Assert.notNull("+messageBeanName+".get"+referenceNameCapped+"(), \""+referenceNameCapped+" not found\");");
			//buf.putLine2(fixtureClassName+".assert"+referenceClassName+"Correct("+messageBeanName+".get"+referenceNameCapped+"());");
		}
		
		buf.putLine2("if (this."+messageBeanName+" != null) {");
		Iterator<Attribute> iterator2 = attributes.iterator();
		while (iterator2.hasNext()) {
			Attribute attribute = iterator2.next();
			String attributeNameCapped = NameUtil.capName(attribute.getName());
			String attributeClassName = TypeUtil.getClassName(attribute.getType());
			
			buf.putLine3("Assert.equals(this."+messageBeanName+".get"+attributeNameCapped+"(), "+messageBeanName+".get"+attributeNameCapped+"(), \""+attributeNameCapped+" field is unequal\");");
		}

		iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceNameCapped = NameUtil.capName(reference.getName());
			String referenceClassName = TypeUtil.getClassName(reference.getType());
			
			//helperName = ModelLayerHelper.getModelHelperBeanName(reference.getType());
			String fixtureClassName = ModelLayerHelper.getFixtureClassName(reference.getType());
			//helperQualifiedName = ModelLayerHelper.getHelperQualifiedName(reference.getType());
			String fixtureQualifiedName = ModelLayerHelper.getFixtureQualifiedName(reference.getType());
			modelClass.addImportedClass(fixtureQualifiedName);
			
			buf.putLine3(fixtureClassName+".assertSame"+referenceClassName+"(this."+messageBeanName+".get"+referenceNameCapped+"(), "+messageBeanName+".get"+referenceNameCapped+"(), \""+referenceNameCapped+" field is unequal\");");
		}
		buf.putLine2("}");

//		//TODO use a much better way to distinguish a "failure" message...
//		if (messageBeanName.contains("Failed") || messageBeanName.contains("Failure")) {
//			String messageBaseName = NameUtil.uncapName(getMessageBaseName(operation));
//			buf.putLine2("Assert.equals("+messageBeanName+".getReason(), "+messageBaseName+"Reason);");
//		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	
	
	protected void createMethods_RunTests(ModelClass modelClass, Service service) {
		testIterationCount = 1;
		//cancel
		//undo
		
//		if (service.getName().equals("orderBooks"))
//			System.out.println();
		
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
		while (iterator2.hasNext()) {
			Callback callback = iterator2.next();
			createMethods_RunTests(modelClass, service, callback);
		}

		Process process = service.getProcess();
		Set<String> events = ProcessUtil.getIncomingEvents(process);
		Iterator<String> iterator = events.iterator();
		while (iterator.hasNext()) {
			String event = iterator.next();
			createMethods_RunTests_EventHandler(modelClass, service, event);
		}
		
		createMethod_RunTest_timeout(modelClass, service);
		createMethod_RunTest_exception(modelClass, service);
	}
	
	protected void createMethods_RunTests_EventHandler(ModelClass modelClass, Service service, String event) {
		String eventClassName = event;
		if (!event.endsWith("Event"))
			eventClassName += "Event";
		
		Element eventElement = context.getElementByName(eventClassName);
		Assert.notNull(eventElement, "Event element not found: "+eventClassName);
		
		createMethods_RunTests_EventHandler_Success(modelClass, service, event);
		createMethods_RunTests_EventHandler_Cancel(modelClass, service, event);
		createMethods_RunTests_EventHandler_Exception(modelClass, service, event);
		createMethods_RunTests_EventHandler_Timeout(modelClass, service, event);
	}

	protected void createMethods_RunTests_EventHandler_Success(ModelClass modelClass, Service service, String event) {
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String serviceNamespaceAlias = ServiceUtil.getNamespaceAlias(service);
		String domain = ServiceLayerHelper.getServiceDomainName(service);
		String eventBaseName = event;
		String eventClassName = event;
		if (!event.endsWith("Event"))
			eventClassName += "Event";

		String messageType = getMessageType(serviceOperation);
		String fixtureClassName = ModelLayerHelper.getFixtureClassName(messageType);
		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setEvent(event);
		helper.initializeForEvent();
		
		String testOperationName = "runTest_"+serviceOperationName+"_"+eventBaseName;
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		buf.putLine2("registerNotificationListeners();");
		buf.putLine2("create"+eventBaseName+"Event();");
		
		buf.putLine2("");
		buf.putLine2("expectedEvent = \""+serviceNamespaceAlias+"_"+eventBaseName+"_Process_Complete\";");
		buf.putLine2("expectedMessage = \""+domain+"."+helper.getSuccessCallbackName()+"\";");
		
		//buf.putLine2("//TODO if we wait too long then we need this:");
		//buf.putLine2("//TODO shipmentFailedReason = \"ShipmentConfirmed timed-out\";");
		
		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2(fixtureClassName+".reset();");
		buf.putLine2("runAction_send_"+serviceInterfaceName+"();");
		
		buf.put(helper.getValidateSuccessfulCallbackStateSource());
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateProcessCompletionStateSource());
		buf.put(helper.getValidateRequestCompletionStateSource());
		
		buf.putLine2("");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	
	protected void createMethods_RunTests_EventHandler_Cancel(ModelClass modelClass, Service service, String event) {
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String serviceInterfaceNameUncapped = NameUtil.uncapName(serviceInterfaceName);
		String serviceNamespaceAlias = ServiceUtil.getNamespaceAlias(service);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String messageClassName = getMessageClassName(serviceOperation);
		String cancelMessageName = messageClassName.replace("Message", "CancelMessage");
		
		String domain = ServiceLayerHelper.getServiceDomainName(service);
		String eventBaseName = event;
		String eventClassName = event;
		if (!event.endsWith("Event"))
			eventClassName += "Event";

		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setEvent(event);
		helper.initializeForEvent();
		
		String testOperationName = "runTest_"+serviceOperationName+"_"+eventBaseName+"_cancel";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		buf.putLine2("registerNotificationListeners();");
		
		buf.putLine2("");
		buf.putLine2("expectedEvent = \""+serviceNamespaceAlias+"_"+eventBaseName+"_Process_Aborted\";");
		buf.putLine2("expectedError = \""+eventBaseName+" execution cancelled\";");
		
		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2("Thread thread = start_runAction_send_"+serviceInterfaceName+"();");
		buf.putLine2(messageClassName+" message = create"+cancelMessageName+"();");
		buf.putLine2(serviceInterfaceNameUncapped+"Client.send(message, correlationId, null);");
		buf.putLine2("thread.join();");
		
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateProcessAbortedStateSource());
		buf.put(helper.getValidateProcessNonCompletionStateSource());
		buf.put(helper.getValidateRequestCancelledStateSource());
		
		buf.putLine2("");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	protected void createMethods_RunTests_EventHandler_Exception(ModelClass modelClass, Service service, String event) {
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String serviceInterfaceNameUncapped = NameUtil.uncapName(serviceInterfaceName);
		String serviceNamespaceAlias = ServiceUtil.getNamespaceAlias(service);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String messageClassName = getMessageClassName(serviceOperation);
		
		String domain = ServiceLayerHelper.getServiceDomainName(service);
		String eventBaseName = event;
		String eventClassName = event;
		if (!event.endsWith("Event"))
			eventClassName += "Event";

		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setEvent(event);
		helper.initializeForEvent();

		String failureCallbackName = helper.getFailureCallbackName();
		String failureCallbackNameUncapped = NameUtil.uncapName(failureCallbackName);
		
		String testOperationName = "runTest_"+serviceOperationName+"_"+eventBaseName+"_exception";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(processClassName, 
				"handle_"+eventBaseName+"_event", "AT ENTRY", 
				"throw new org.aries.ApplicationFailure(\\\"exception message\\\")", 
				testIterationCount));
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		buf.putLine2("setupByteman(testName);");
		buf.putLine2("registerNotificationListeners();");
		buf.putLine2("create"+eventBaseName+"Event();");
		
		buf.putLine2("");
		buf.putLine2("expectedMessage = \""+domain+"."+helper.getFailureCallbackName()+"\";");
		buf.putLine2(failureCallbackNameUncapped+"Reason = \"exception message\";");
		
		buf.putLine2("");
		buf.putLine2("//execute test");
		buf.putLine2("runAction_send_"+serviceInterfaceName+"();");
		
		buf.put(helper.getValidateFailureCallbackStateSource());
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateProcessAbortedStateSource());
		buf.put(helper.getValidateRequestCompletionStateSource());
		
		buf.putLine2("");
		buf.putLine2("//cleanup");
		buf.putLine2("tearDownByteman(testName);");
		
		buf.putLine2("");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	protected void createMethods_RunTests_EventHandler_Timeout(ModelClass modelClass, Service service, String event) {
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String serviceInterfaceNameUncapped = NameUtil.uncapName(serviceInterfaceName);
		String serviceNamespaceAlias = ServiceUtil.getNamespaceAlias(service);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String messageClassName = getMessageClassName(serviceOperation);
		
		String domain = ServiceLayerHelper.getServiceDomainName(service);
		String eventBaseName = event;
		String eventClassName = event;
		if (!event.endsWith("Event"))
			eventClassName += "Event";

		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setEvent(event);
		helper.initializeForEvent();
		
		String testOperationName = "runTest_"+serviceOperationName+"_"+eventBaseName+"_timeout";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(eventBaseName+"Executor", "register", "AT ENTRY", "$0.timeout = 0", testIterationCount));
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		buf.putLine2("setupByteman(testName);");
		buf.putLine2("registerNotificationListeners();");
		
		buf.putLine2("");
		buf.putLine2("expectedError = \""+eventBaseName+" execution timed-out\";");
		
		String failureCallbackName = helper.getFailureCallbackName();
		String failureCallbackNameUncapped = NameUtil.uncapName(failureCallbackName);
		buf.putLine2(failureCallbackNameUncapped+"Reason = \""+eventBaseName+" timed-out\";");
		
		buf.putLine2("");
		buf.putLine2("//execute test");
		buf.putLine2("runAction_send_"+serviceInterfaceName+"();");
		
		buf.put(helper.getValidateFailureCallbackStateSource());
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateProcessAbortedStateSource());
		buf.put(helper.getValidateRequestCompletionStateSource());
		
		buf.putLine2("");
		buf.putLine2("//cleanup");
		buf.putLine2("tearDownByteman(testName);");
		
		buf.putLine2("");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	protected void createMethods_RunTests(ModelClass modelClass, Service service, Callback callback) {
		Process process = service.getProcess();
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		Operation serviceProcessOperation = ProcessUtil.getOperation(process, serviceOperation.getName());
		Operation callbackProcessOperation = ProcessUtil.getOperation(process, callbackOperation.getName());
		
		List<Command> serviceCommands = serviceProcessOperation.getCommands();
		if (callbackProcessOperation != null) {
			List<Command> callbackCommands = callbackProcessOperation.getCommands();
			//createMethod_RunTest(modelClass, service, callback);
			//System.out.println();
		}
		
//		if (service.getName().equals("orderBooks"))
//			System.out.println();

//		List<Pathway> codePathways2 = getCodePathwaysForCallback(serviceCommands, callback);
//		Iterator<Pathway> iterator2 = codePathways2.iterator();
//		while (iterator2.hasNext()) {
//			Pathway pathway = iterator2.next();
//			createMethod_RunTest(modelClass, service, callback, pathway);
//		}

		List<Channel> channels = ServiceUtil.getChannels(callback);
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			//createMethod_RunTest_Success(modelClass, service, callback, channel);
			//createMethod_RunTest_All(modelClass, service, callback, channel);

			List<Pathway> codePathways = getCodePathwaysForCallback(serviceCommands, serviceCommands, callback);
			Iterator<Pathway> iterator3 = codePathways.iterator();
			while (iterator3.hasNext()) {
				Pathway pathway = iterator3.next();
				if (channels.size() == 1)
					channel = null;
				String pathwayType = pathway.getType();
				
				//boolean isTimeoutRequired = pathwayType.equals("timeout");
				//boolean isExceptionRequired = pathwayType.equals("exception");

				if (pathwayType.equals("response")) {
					createMethod_RunTest_success(modelClass, service, callback, channel, pathway);
					createMethod_RunTest_cancel(modelClass, service, callback, channel, pathway);
					createMethod_RunTest_undo(modelClass, service, callback, channel, pathway);

				} else if (pathwayType.equals("timeout")) {
					createMethod_RunTest_timeout(modelClass, service, callback, channel, pathway);
					
				} else if (pathwayType.equals("exception")) {
					createMethod_RunTest_exception(modelClass, service, callback, channel, pathway);
					
				} else if (pathwayType.equals("EQ")) {
					createMethod_RunTest_success(modelClass, service, callback, channel, pathway);
					
				} else if (pathwayType.equals("GT")) {
					createMethod_RunTest_success(modelClass, service, callback, channel, pathway);
					
				} else if (pathwayType.equals("LT")) {
					createMethod_RunTest_success(modelClass, service, callback, channel, pathway);
					
				} else {
					System.out.println();
				}
			}
		}
	}

	protected void createMethod_RunTest(ModelClass modelClass, Service service, Callback callback, Pathway pathway) {
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		String callbackBeanName = ServiceLayerHelper.getServiceNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		String messageClassName = getMessageClassName(callbackOperation);

		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setCallback(callback);
		//helper.setShouldResultInCompletion(false);
		helper.initializeForCallback(pathway);
		
		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String testOperationName = "runTest_"+serviceOperationName+"_"+pathway.getLabel();
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		
		Process process = service.getProcess();
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperationName);
		List<Command> commands = processOperation.getCommands();
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		buf.putLine2("registerNotificationListeners();");
		buf.putLine2("expectedCallback = \""+callbackInterfaceName+"\";");
		if (callbackInterfaceName.equalsIgnoreCase(helper.getFailureCallbackName()))
			buf.putLine2(callbackBeanName+"Reason = \"exception message\";");
		
		buf.putLine2("");
		buf.putLine2("//execute test");
		buf.putLine2("runAction_send_"+serviceInterfaceName+"();");
		
		buf.put(helper.getValidateSuccessfulCallbackStateSource());
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateRequestCompletionStateSource());
		
		buf.putLine2("");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}
	
	protected void createMethod_RunTest_success(ModelClass modelClass, Service service, Callback callback, Channel channel, Pathway pathway) {
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String domainServiceName = ServiceUtil.getExtendedDomainServiceName(service);
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		String callbackBeanName = ClientLayerHelper.getClientNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		String serviceDomain = ServiceLayerHelper.getServiceDomainName(service);
		
		String messageType = getMessageType(callbackOperation);
		String messageBaseName = getMessageBaseName(callbackOperation);
		String messageBeanName = getMessageBeanName(callbackOperation);
		String messageClassName = getMessageClassName(callbackOperation);
		String fixtureClassName = ModelLayerHelper.getFixtureClassName(messageType);

		Process process = service.getProcess();
		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String callbackOperationName = NameUtil.capName(callbackOperation.getName());
		Operation actualServiceOperation = ProcessUtil.getOperation(process, serviceOperationName);
		Operation actualCallbackOperation = ProcessUtil.getOperation(process, callbackOperationName);
		//List<Command> serviceCommands = actualServiceOperation.getCommands();
		//List<Command> callbackCommands = actualCallbackOperation.getCommands();

		Service remoteService = pathway.getRemoteService();
		String expectedIncomingCallbackName = pathway.getName();

		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setCallback(callback);
		helper.initializeForCallback(pathway);

		String testOperationName = "runTest_"+serviceOperationName+"_"+pathway.getLabel();
		if (channel != null)
			testOperationName += "_"+channel.getName();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		//buf.putLine2("setupByteman(testName);");
		buf.putLine2("registerNotificationListeners();");
		buf.putLine2("");
		buf.put(createSource_branchContextPreparation(service, callback, pathway));
		
		buf.putLine2("");
		buf.putLine2("// setup expectations");
		if (CodeUtil.hasDoneCommand(service, pathway)) {
			buf.putLine2("expectedEvent = \""+domainServiceName+"_Request_Done\";");
		} else if (remoteService != null) {
 			String remoteApplicationName = ServiceUtil.getNamespaceAlias(remoteService);
			buf.putLine2("expectedEvent = \""+remoteApplicationName+"_"+expectedIncomingCallbackName+"_Response_Done\";");
		}
		buf.putLine2("expectedMessage = \""+serviceDomain+"."+messageBaseName+"\";");

//		if (serviceOperationName.equals("QueryBooks"))
//			System.out.println();
		
		// If callback is expected, then set its expectations here 
		if (expectedIncomingCallbackName != null) {
			Service incomingCallback = context.getIncomingCallbackByName(expectedIncomingCallbackName);
			//String incomingCallbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(incomingCallback);
			Operation incomingCallbackOperation = ServiceUtil.getDefaultOperation(incomingCallback);
			String incomingCallbackMessageName = getMessageClassName(incomingCallbackOperation);
			String incomingCallbackNamespaceAlias = ServiceUtil.getNamespaceAlias(incomingCallback);
			String expectedCallbackUncapped = NameUtil.uncapName(incomingCallbackMessageName);
			
			buf.putLine2("");
			buf.putLine2("// setup expected callback response");
			//buf.putLine2("// setup expected \""+expectedIncomingCallbackName+"\" response");
			buf.putLine2("expectedCallback = \""+expectedIncomingCallbackName+"\";");
			buf.putLine2(expectedCallbackUncapped+" = create"+incomingCallbackMessageName+"();");
			
			Element incomingCallbackMessageElement = context.getElementByName(incomingCallbackMessageName);
			if (ElementUtil.isFieldExists(incomingCallbackMessageElement, "reason")) {
				String reason = NameUtil.splitStringUsingSpacesCapped(expectedIncomingCallbackName);
				buf.putLine2(expectedCallbackUncapped+".setReason(\""+reason+"\");");
			}
		}

		// For each remote service that is invoked, set its mock response here 
		//Collection<Service> remoteServices = pathway.getRemoteServices();
		if (remoteService != null) {
			String remoteServiceName = ServiceUtil.getDomainServiceName(remoteService);
			String remoteApplicationName = NameUtil.uncapName(ServiceUtil.getNamespaceAlias(remoteService));
	
			Callback outgoingCallback = ServiceUtil.getOutgoingCallbackByName(remoteService, expectedIncomingCallbackName);
			Operation incomingCallbackOperation = ServiceUtil.getDefaultOperation(callback);
			String incomingCallbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
			String callbackMessageType = getMessageType(incomingCallbackOperation);
			String callbackMessageBaseName = getMessageBaseName(incomingCallbackOperation);
			String callbackMessageBeanName = remoteApplicationName + getMessageClassName(incomingCallbackOperation);
			String callbackMessageClassName = getMessageClassName(incomingCallbackOperation);
	
			buf.putLine2("");
			buf.putLine2("// setup mock response \""+remoteApplicationName+"."+remoteService.getName()+"\"");
			buf.putLine2(callbackMessageBeanName+" = "+fixtureClassName+".create_"+callbackMessageClassName+"();");
	
			Element messageElement = context.getElementByName(callbackMessageClassName);
			ReplyCommand replyCommand = getReplyCommand(pathway.getCommands(), expectedIncomingCallbackName);
			boolean reasonFieldFoundInParameters = false;
			//if (replyCommand == null)
			//	System.out.println();
			if (replyCommand != null) {
				List<Parameter> parameters = replyCommand.getParameters();
				Iterator<Parameter> iterator = parameters.iterator();
				while (iterator.hasNext()) {
					Parameter parameter = iterator.next();
					String paramName = parameter.getName();
					String paramNameCapped = NameUtil.capName(paramName);
					if (paramName.equals("reason"))
						reasonFieldFoundInParameters = true;
	
					if (ElementUtil.isFieldExists(messageElement, paramName)) {
						Definition definition = pathway.getAvailableDefinition(paramName);
						Assert.notNull(definition, "Definition not found for: "+paramName);
						String valueAsString = DefinitionUtil.getValueAsString(definition);
						buf.putLine2(callbackMessageBeanName+".set"+paramNameCapped+"("+valueAsString+");");
					}
				}
			}
	
			boolean hasReasonField = ElementUtil.hasField(messageElement, "reason");
			if (hasReasonField && !reasonFieldFoundInParameters) {
				String reason = NameUtil.splitStringUsingSpacesCapped(expectedIncomingCallbackName);
				buf.putLine2(callbackMessageBeanName+".setReason(\""+reason+"\");");
			}
			
		} else {
			//check for any ReplyCommand 
			Collection<ReplyCommand> replyCommands = CommandUtil.getCommands(pathway.getCommands(), ReplyCommand.class);
			
			if (replyCommands.size() > 0) {
			}
			
			Iterator<ReplyCommand> iterator = replyCommands.iterator();
			while (iterator.hasNext()) {
				ReplyCommand replyCommand = iterator.next();
				Interactor interactor = (Interactor) replyCommand.getActor();
				String remoteApplicationName = interactor.getName();
				String remoteServiceName = interactor.getService();
				boolean reasonFieldFoundInParameters = false;
				
				String messageName = replyCommand.getMessageName();
				String callbackMessageClassName = messageName + "Message";
				String callbackMessageBeanName = NameUtil.uncapName(callbackMessageClassName);
				Element messageElement = context.getElementByName(callbackMessageClassName);
				
				buf.putLine2("");
				buf.putLine2("// setup response \""+messageName+"\"");
				buf.putLine2(callbackMessageBeanName+" = "+fixtureClassName+".create_"+callbackMessageClassName+"();");
				
				List<Parameter> parameters = replyCommand.getParameters();
				Iterator<Parameter> iterator3 = parameters.iterator();
				while (iterator3.hasNext()) {
					Parameter parameter = iterator3.next();
					String paramName = parameter.getName();
					String paramNameCapped = NameUtil.capName(paramName);
					if (paramName.equals("reason"))
						reasonFieldFoundInParameters = true;
	
					if (ElementUtil.isFieldExists(messageElement, paramName)) {
						Definition definition = pathway.getAvailableDefinition(paramName);
						Assert.notNull(definition, "Definition not found for: "+paramName);
						String valueAsString = DefinitionUtil.getValueAsString(definition);
						buf.putLine2(callbackMessageBeanName+".set"+paramNameCapped+"("+valueAsString+");");
					}
				}
				
				boolean hasReasonField = ElementUtil.hasField(messageElement, "reason");
				if (hasReasonField && !reasonFieldFoundInParameters) {
					String reason = NameUtil.splitStringUsingSpacesCapped(expectedIncomingCallbackName);
					buf.putLine2(callbackMessageBeanName+".setReason(\""+reason+"\");");
				}
			}
		}
		
//		boolean hasReasonField = ElementUtil.hasField(messageElement, "reason");
//		if (expectedIncomingCallbackName != null || hasReasonField) {
//			if (expectedIncomingCallbackName == null)
//				System.out.println();
//			if (ElementUtil.isFieldExists(messageElement, "reason")) {
//				String reason = null;
//				if (hasReasonField) {
//					reason = "";
//				}
//				else reason = NameUtil.splitStringUsingSpacesCapped(expectedIncomingCallbackName);
//				buf.putLine2(messageBeanName+".setReason(\""+reason+"\");");
//			}
//		}
		
//		//List<String> variableNames = new ArrayList<String>();
//		Stack<ConditionStatement> conditionStatements = pathway.getConditionStatements();
//		Iterator<ConditionStatement> iterator2 = conditionStatements.iterator();
//		while (iterator2.hasNext()) {
//			ConditionStatement conditionStatement = iterator2.next();
//			ExpressionStatement expressionStatement1 = conditionStatement.getExpressionStatement1();
//			String variableName = expressionStatement1.getTargetName();
//			String variableSource = findVariableSource(variableName, commands);
//			if (variableSource == null)
//				continue;
//			if (variableSource.endsWith("Manager"))
//				variableSource = variableSource.replace("Manager",  "");
//			String variableSourceUncapped = NameUtil.uncapName(variableSource);
//			String variableSourceCapped = NameUtil.capName(variableSource);
//			buf.putLine2(variableSourceUncapped+"Helper.clear"+variableSourceCapped+"();");
//			
//			String typeName = expressionStatement1.getTargetType().getType();
//			if (pathway.getType().equals("GT") || pathway.getType().equals("GE"))
//				buf.putLine2(variableSourceUncapped+"Helper.add"+typeName+"ListTo"+variableSourceCapped+"();");
//		}
		
		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2(fixtureClassName+".reset();");
		buf.putLine2("runAction_send_"+serviceInterfaceName+"();");

		buf.put(helper.getValidateRequestReceivedStateSource());
		buf.put(helper.getValidateRemoteInteractionsStateSource(pathway));
		buf.put(helper.getValidateSuccessfulCallbackStateSource());
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateRequestCompletionStateSource());
		
		if (pathway.getCommandBlock() != null) {
			List<Command> pathwayCommands = pathway.getCommandBlock().getCommands();
			Collection<ExpressionStatement> pathwayExpressions = CommandUtil.getCommands(pathwayCommands, ExpressionStatement.class);
			Iterator<ExpressionStatement> iterator3 = pathwayExpressions.iterator();
			while (iterator3.hasNext()) {
				ExpressionStatement expressionStatement = iterator3.next();
				String targetName = expressionStatement.getTargetName();
				String methodName = expressionStatement.getSelector();
				String targetNameCapped = NameUtil.capName(targetName);
				
				Cache cacheUnit = ServiceUtil.getCacheUnitReference(service, targetName);
				if (cacheUnit != null && methodName.startsWith("addTo")) {
					String fieldName = methodName.replace("addTo", "");
					
					List<Parameter> parameters = expressionStatement.getParameters();
					Iterator<Parameter> iterator4 = parameters.iterator();
					for (int i=0; iterator4.hasNext(); i++) {
						Parameter parameter = iterator4.next();
						String parameterType = parameter.getType();
						if (!parameterType.startsWith("{")) {
							Element element = context.getElementByName(parameterType);
							parameterType = element.getType();
						}
	
						String parameterName = parameter.getName();
						String parameterNameCapped = NameUtil.capName(parameterName);
						String parameterClassName = parameterType; 
						if (parameterType.startsWith("{"))
							parameterClassName = TypeUtil.getClassName(parameterType);
						
						String structure = parameter.getConstruct();
						String structuredType = TypeUtil.getStructuredType(parameter);
						String structuredName = TypeUtil.getStructuredParam(parameter);
						String responseMessageClassName = pathway.getName() + "Message";
						String responseMessageBeanName = NameUtil.uncapName(responseMessageClassName);
						String helperName = ModelLayerHelper.getModelHelperBeanName(parameterType);
						
						buf.putLine2("");
						buf.putLine2("// validate "+targetName+"."+methodName+" state");
						buf.putLine2(structuredType+" "+structuredName+" = "+targetName+"Helper.getAll"+fieldName+"();");
						buf.putLine2(structuredType+" "+structuredName+"ToAdd = "+responseMessageBeanName+".get"+parameterNameCapped+"();");
						buf.putLine2(fixtureClassName+".assertSame"+parameterClassName+"("+structuredName+"ToAdd, "+structuredName+");");
						buf.putLine2(targetName+"Helper.verify"+fieldName+"Count("+structuredName+"ToAdd.size());");
						
						if (structure.equals("list")) 
							modelClass.addImportedClass("java.util.List");
						else if (structure.equals("set")) 
							modelClass.addImportedClass("java.util.Set");
						else if (structure.equals("map")) 
							modelClass.addImportedClass("java.util.Map");
						else if (structure.equals("collection")) 
							modelClass.addImportedClass("java.util.Collection");
						Element element = context.getElementByType(parameterType);
						modelClass.addImportedClass(element);
					}
				}
			}
		}
		
		//buf.putLine2("");
		//buf.putLine2("// cleanup");
		//buf.putLine2("tearDownByteman(testName);");
		//buf.putLine2("removeMessagesFromDestinations();");
		
		buf.putLine2("");
		//buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	protected ReplyCommand getReplyCommand(Pathway pathway, String callbackInterfaceName) {
		Collection<Command> commands = pathway.getCommands();
		ReplyCommand replyCommand = getReplyCommand(commands, callbackInterfaceName);
		if (replyCommand != null)
			return replyCommand;
		AbstractBlock commandBlock = pathway.getCommandBlock();
		if (commandBlock != null) {
			commands = commandBlock.getCommands();
			replyCommand = getReplyCommand(commands, callbackInterfaceName);
			if (replyCommand != null)
				return replyCommand;
		}
		return null;
	}

	protected ReplyCommand getReplyCommand(Collection<Command> commands, String callbackInterfaceName) {
		Collection<ReplyCommand> replyCommands = CommandUtil.getCommands(commands, ReplyCommand.class);
		Iterator<ReplyCommand> iterator = replyCommands.iterator();
		while (iterator.hasNext()) {
			ReplyCommand replyCommand = iterator.next();
			if (replyCommand.getMessageName().equals(callbackInterfaceName)) {
				return replyCommand;
			}
		}
		return null;
	}
	
	protected void createMethod_RunTest_cancel(ModelClass modelClass, Service service, Callback callback, Channel channel, Pathway pathway) {
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String domainServiceName = ServiceUtil.getExtendedDomainServiceName(service);
		String domainServiceName2 = ServiceUtil.getExtendedDomainServiceName(pathway.getRemoteService(), callback);
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		String callbackBeanName = ClientLayerHelper.getClientNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		String serviceDomain = ServiceLayerHelper.getServiceDomainName(service);
		
		String messageType = getMessageType(callbackOperation);
		String messageBaseName = getMessageBaseName(callbackOperation);
		String messageBeanName = getMessageBeanName(callbackOperation);
		String messageClassName = getMessageClassName(callbackOperation);
		
		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setCallback(callback);
		helper.initializeForCallback(pathway);

		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String testOperationName = "runTest_"+serviceOperationName+"_"+pathway.getLabel();
		if (channel != null)
			testOperationName += "_"+channel.getName();
		testOperationName += "_cancel";
		
//		if (testOperationName.equals("runTest_ShipBooks_ShipmentScheduled_from_ShipBooks_ShipmentScheduled_cancel"))
//			System.out.println();

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
//		modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
//				processClassName, 
//				"handle_"+pathway.getName()+"_response",
//				"AT EXIT", 
//				"delay(60000)", 
//				testIterationCount));
		
		Process process = service.getProcess();
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperationName);
		List<Command> commands = processOperation.getCommands();
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		//buf.putLine2("setupByteman(testName);");
		buf.putLine2("registerNotificationListeners();");
		buf.putLine2("");
		buf.put(createSource_branchContextPreparation(service, callback, pathway));

		buf.putLine2("");
		buf.putLine2("// setup expectations");
		//buf.putLine2("expectedEvent = \""+domainServiceName+"_Request_Done\";");
		buf.putLine2("expectedEvent = \""+domainServiceName2+"_Response_Done\";");
		buf.putLine2("expectedMessage = \""+serviceDomain+"."+messageBaseName+"\";");

		buf.putLine2("");
		buf.putLine2("// setup mock response");
		String expectedIncomingCallbackName = pathway.getName();
		if (expectedIncomingCallbackName != null) {
			Service incomingCallback = context.getIncomingCallbackByName(expectedIncomingCallbackName);
			//String incomingCallbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(incomingCallback);
			Operation incomingCallbackOperation = ServiceUtil.getDefaultOperation(incomingCallback);
			String incomingCallbackMessageName = getMessageClassName(incomingCallbackOperation);
			String expectedCallbackUncapped = NameUtil.uncapName(incomingCallbackMessageName);
			
			buf.putLine2("expectedCallback = \""+expectedIncomingCallbackName+"\";");
			buf.putLine2(expectedCallbackUncapped+" = create"+incomingCallbackMessageName+"();");
			
			Element incomingCallbackMessageElement = context.getElementByName(incomingCallbackMessageName);
			if (ElementUtil.isFieldExists(incomingCallbackMessageElement, "reason")) {
				String reason = NameUtil.splitStringUsingSpacesCapped(expectedIncomingCallbackName);
				buf.putLine2(expectedCallbackUncapped+".setReason(\""+reason+"\");");
			}
		}
		
		String fixtureClassName = ModelLayerHelper.getFixtureClassName(messageType);
		buf.putLine2(messageBeanName+" = "+fixtureClassName+".create_"+messageClassName+"();");
		
		if (expectedIncomingCallbackName != null) {
			Element messageElement = context.getElementByName(messageClassName);
			if (ElementUtil.isFieldExists(messageElement, "reason")) {
				String reason = NameUtil.splitStringUsingSpacesCapped(expectedIncomingCallbackName);
				buf.putLine2(messageBeanName+".setReason(\""+reason+"\");");
			}
		}

//		if (service.getName().equals("orderBooks"))
//			System.out.println();
		
		//List<String> variableNames = new ArrayList<String>();
		Stack<ConditionStatement> conditionStatements = pathway.getConditionStatements();
		Iterator<ConditionStatement> iterator = conditionStatements.iterator();
		while (iterator.hasNext()) {
			ConditionStatement conditionStatement = iterator.next();
			ExpressionStatement expressionStatement1 = conditionStatement.getExpressionStatement1();
			String variableName = expressionStatement1.getTargetName();
			String variableSource = findVariableSource(variableName, commands);
			if (variableSource == null)
				continue;
			if (variableSource.endsWith("Manager"))
				variableSource = variableSource.replace("Manager",  "");
			String variableSourceUncapped = NameUtil.uncapName(variableSource);
			String variableSourceCapped = NameUtil.capName(variableSource);
			buf.putLine2(variableSourceUncapped+"Helper.clear"+variableSourceCapped+"();");
			
			String typeName = expressionStatement1.getTargetType().getType();
			if (pathway.getType().equals("GT") || pathway.getType().equals("GE"))
				buf.putLine2(variableSourceUncapped+"Helper.add"+typeName+"ListTo"+variableSourceCapped+"();");
		}
		
		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2(fixtureClassName+".reset();");
		buf.putLine2("runAction_send_"+serviceInterfaceName+"();");
		buf.putLine2("");
		buf.putLine2("// clear message queues");
		buf.putLine2("removeMessagesFromDestinations();");
		buf.putLine2("");
		buf.putLine2("// execute \"cancel\" of action");
		buf.putLine2(fixtureClassName+".reset();");
		buf.putLine2("runAction_send_"+serviceInterfaceName+"_cancel();");
		
		buf.put(helper.getValidateRequestReceivedStateSource());
		buf.put(helper.getValidateRemoteInteractionsStateSource(pathway));
		buf.put(helper.getValidateSuccessfulCallbackStateSource());
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateRequestCancelledStateSource());
		buf.put(helper.getValidateEmptyCacheStateSource(pathway));
		
		//buf.putLine2("");
		//buf.putLine2("// cleanup");
		//buf.putLine2("tearDownByteman(testName);");
		//buf.putLine2("removeMessagesFromDestinations();");
		
		buf.putLine2("");
		//buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	protected void createMethod_RunTest_undo(ModelClass modelClass, Service service, Callback callback, Channel channel, Pathway pathway) {
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String domainServiceName = ServiceUtil.getExtendedDomainServiceName(service);
		String domainServiceName2 = ServiceUtil.getExtendedDomainServiceName(pathway.getRemoteService(), callback);
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		String callbackBeanName = ClientLayerHelper.getClientNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		String messageBaseName = getMessageBaseName(callbackOperation);
		String messageBeanName = getMessageBeanName(callbackOperation);
		String messageClassName = getMessageClassName(callbackOperation);
		String messageType = getMessageType(callbackOperation);
		String serviceDomain = ServiceLayerHelper.getServiceDomainName(service);

//		Interactor interactor = (Interactor) pathway.getRootCommand().getActor();
//		String removeServiceName = NameUtil.capName(interactor.getService());
//		String removeServiceNameUncapped = NameUtil.uncapName(removeServiceName);
		
		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setCallback(callback);
		helper.initializeForCallback(pathway);

		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String testOperationName = "runTest_"+serviceOperationName+"_"+pathway.getLabel();
		if (channel != null)
			testOperationName += "_"+channel.getName();
		testOperationName += "_undo";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		
		Process process = service.getProcess();
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperationName);
		List<Command> commands = processOperation.getCommands();
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		//buf.putLine2("setupByteman(testName);");
		buf.putLine2("registerNotificationListeners();");
		buf.putLine2("");
		buf.put(createSource_branchContextPreparation(service, callback, pathway));

		buf.putLine2("");
		buf.putLine2("// setup expectations");
		//buf.putLine2("expectedEvent = \""+domainServiceName+"_Request_Done\";");
		buf.putLine2("expectedEvent = \""+domainServiceName2+"_Response_Done\";");
		buf.putLine2("expectedMessage = \""+serviceDomain+"."+messageBaseName+"\";");

		buf.putLine2("");
		buf.putLine2("// setup mock response");
		String expectedIncomingCallbackName = pathway.getName();
		if (expectedIncomingCallbackName != null) {
			Service incomingCallback = context.getIncomingCallbackByName(expectedIncomingCallbackName);
			//String incomingCallbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(incomingCallback);
			Operation incomingCallbackOperation = ServiceUtil.getDefaultOperation(incomingCallback);
			String incomingCallbackMessageName = getMessageClassName(incomingCallbackOperation);
			String expectedCallbackUncapped = NameUtil.uncapName(incomingCallbackMessageName);
			
			buf.putLine2("expectedCallback = \""+expectedIncomingCallbackName+"\";");
			buf.putLine2(expectedCallbackUncapped+" = create"+incomingCallbackMessageName+"();");
			
			Element incomingCallbackMessageElement = context.getElementByName(incomingCallbackMessageName);
			if (ElementUtil.isFieldExists(incomingCallbackMessageElement, "reason")) {
				String reason = NameUtil.splitStringUsingSpacesCapped(expectedIncomingCallbackName);
				buf.putLine2(expectedCallbackUncapped+".setReason(\""+reason+"\");");
			}
		}

		String fixtureClassName = ModelLayerHelper.getFixtureClassName(messageType);
		buf.putLine2(messageBeanName+" = "+fixtureClassName+".create_"+messageClassName+"();");
		
		if (expectedIncomingCallbackName != null) {
			Element messageElement = context.getElementByName(messageClassName);
			if (ElementUtil.isFieldExists(messageElement, "reason")) {
				String reason = NameUtil.splitStringUsingSpacesCapped(expectedIncomingCallbackName);
				buf.putLine2(messageBeanName+".setReason(\""+reason+"\");");
			}
		}

//		if (service.getName().equals("orderBooks"))
//			System.out.println();
		
		//List<String> variableNames = new ArrayList<String>();
		Stack<ConditionStatement> conditionStatements = pathway.getConditionStatements();
		Iterator<ConditionStatement> iterator = conditionStatements.iterator();
		while (iterator.hasNext()) {
			ConditionStatement conditionStatement = iterator.next();
			ExpressionStatement expressionStatement1 = conditionStatement.getExpressionStatement1();
			String variableName = expressionStatement1.getTargetName();
			String variableSource = findVariableSource(variableName, commands);
			if (variableSource == null)
				continue;
			if (variableSource.endsWith("Manager"))
				variableSource = variableSource.replace("Manager",  "");
			String variableSourceUncapped = NameUtil.uncapName(variableSource);
			String variableSourceCapped = NameUtil.capName(variableSource);
			buf.putLine2(variableSourceUncapped+"Helper.clear"+variableSourceCapped+"();");
			
			String typeName = expressionStatement1.getTargetType().getType();
			if (pathway.getType().equals("GT") || pathway.getType().equals("GE"))
				buf.putLine2(variableSourceUncapped+"Helper.add"+typeName+"ListTo"+variableSourceCapped+"();");
		}
		
		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2(fixtureClassName+".reset();");
		buf.putLine2("runAction_send_"+serviceInterfaceName+"();");
		buf.putLine2("");
		buf.putLine2("removeMessagesFromDestinations();");
		buf.putLine2("");
		buf.putLine2("// execute \"undo\" of action");
		buf.putLine2("runAction_send_"+serviceInterfaceName+"_undo();");
		
		buf.put(helper.getValidateRequestReceivedStateSource());
		buf.put(helper.getValidateRemoteInteractionsStateSource(pathway));
		buf.put(helper.getValidateSuccessfulCallbackStateSource());
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateRequestRolledBackStateSource());
		buf.put(helper.getValidateEmptyCacheStateSource(pathway));
		
		//buf.putLine2("");
		//buf.putLine2("// cleanup");
		//buf.putLine2("tearDownByteman(testName);");
		//buf.putLine2("removeMessagesFromDestinations();");
		
		buf.putLine2("");
		//buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	protected void createMethod_RunTest_timeout(ModelClass modelClass, Service service, Callback callback, Channel channel, Pathway pathway) {
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		String callbackBeanName = ClientLayerHelper.getClientNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		String serviceDomain = ServiceLayerHelper.getServiceDomainName(service);
		
		String messageType = getMessageType(callbackOperation);
		String messageBaseName = getMessageBaseName(callbackOperation);
		String messageBeanName = getMessageBeanName(callbackOperation);
		String messageClassName = getMessageClassName(callbackOperation);
		
		Interactor interactor = (Interactor) pathway.getRootCommand().getActor();
		String removeApplicationName = NameUtil.capName(interactor.getName());
		//TODO replace this with call to utils
		if (removeApplicationName.endsWith("Group"))
			removeApplicationName = removeApplicationName.replace("Group", "");
		String removeServiceName = NameUtil.capName(interactor.getService());
		String removeServiceNameUncapped = NameUtil.uncapName(removeServiceName);
		
		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setCallback(callback);
		helper.initializeForCallback(pathway);

		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String testOperationName = "runTest_"+serviceOperationName+"_"+pathway.getLabel();
		//String testOperationName = "runTest_"+serviceOperationName+"_timeout";
		if (channel != null)
			testOperationName += "_"+channel.getName();
		//testOperationName += "_from_timeout";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
				//serviceInterfaceName+"HandlerImpl", 
				//serviceOperation.getName(), 
				processClassName,
				"send_"+removeApplicationName+"_"+removeServiceName+"_request",
				"AT ENTRY", 
				"$0."+removeServiceNameUncapped+"Timeout = 0", 
				testIterationCount));
		
		Process process = service.getProcess();
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperationName);
		List<Command> commands = processOperation.getCommands();
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		buf.putLine2("setupByteman(testName);");
		buf.putLine2("registerNotificationListeners();");
		buf.putLine2("");
		buf.put(createSource_branchContextPreparation(service, callback, pathway));

		buf.putLine2("");
		buf.putLine2("// setup expectations");
		buf.putLine2("expectedEvent = \""+removeApplicationName+"_"+removeServiceName+"_Outgoing_Request_Aborted\";");
		buf.putLine2("expectedMessage = \""+serviceDomain+"."+messageBaseName+"\";");

		buf.putLine2("");
		buf.putLine2("// setup mock response");
		String fixtureClassName = ModelLayerHelper.getFixtureClassName(messageType);
		buf.putLine2(messageBeanName+" = "+fixtureClassName+".create_"+messageClassName+"();");
		Element messageElement = context.getElementByName(messageClassName);
		if (ElementUtil.isFieldExists(messageElement, "reason")) {
			buf.putLine2(messageBeanName+".setReason(\""+removeServiceName+" timed-out\");");
		}

//		buf.putLine2("");
//		String expectedIncomingCallbackName = pathway.getName();
//		if (expectedIncomingCallbackName != null) {
//			Service incomingCallback = context.getIncomingCallbackByName(expectedIncomingCallbackName);
//			//String incomingCallbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(incomingCallback);
//			Operation incomingCallbackOperation = ServiceUtil.getDefaultOperation(incomingCallback);
//			String incomingCallbackMessageName = getMessageClassName(incomingCallbackOperation);
//			String expectedCallbackUncapped = NameUtil.uncapName(incomingCallbackMessageName);
//			
//			buf.putLine2("// setup mock response");
//			buf.putLine2("expectedCallback = \""+expectedIncomingCallbackName+"\";");
//			buf.putLine2(expectedCallbackUncapped+" = create"+incomingCallbackMessageName+"();");
//		}
		
//		if (service.getName().equals("orderBooks"))
//			System.out.println();
		
		//List<String> variableNames = new ArrayList<String>();
		Stack<ConditionStatement> conditionStatements = pathway.getConditionStatements();
		Iterator<ConditionStatement> iterator = conditionStatements.iterator();
		while (iterator.hasNext()) {
			ConditionStatement conditionStatement = iterator.next();
			ExpressionStatement expressionStatement1 = conditionStatement.getExpressionStatement1();
			String variableName = expressionStatement1.getTargetName();
			String variableSource = findVariableSource(variableName, commands);
			if (variableSource == null)
				continue;
			if (variableSource.endsWith("Manager"))
				variableSource = variableSource.replace("Manager",  "");
			String variableSourceUncapped = NameUtil.uncapName(variableSource);
			String variableSourceCapped = NameUtil.capName(variableSource);
			buf.putLine2(variableSourceUncapped+"Helper.clear"+variableSourceCapped+"();");
			
			String typeName = expressionStatement1.getTargetType().getType();
			if (pathway.getType().equals("GT") || pathway.getType().equals("GE"))
				buf.putLine2(variableSourceUncapped+"Helper.add"+typeName+"ListTo"+variableSourceCapped+"();");
		}
		
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// execute action");
		buf.putLine2("	"+fixtureClassName+".reset();");
		buf.putLine2("	runAction_send_"+serviceInterfaceName+"();");

		helper.setIndent(3);
		buf.put(helper.getValidateRequestReceivedStateSource());
		buf.put(helper.getValidateRemoteInteractionsStateSource(pathway));
		buf.put(helper.getValidateSuccessfulCallbackStateSource());
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateOutgoingRequestAbortedStateSource(removeApplicationName, removeServiceName));
		buf.put(helper.getValidateRequestCompletionStateSource(true));
		buf.put(helper.getValidateEmptyCacheStateSource(pathway));
		
		buf.putLine2("	");
		//buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("	if (errorMessage != null)");
		buf.putLine2("		fail(errorMessage);");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	tearDownByteman(testName);");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	protected void createMethod_RunTest_exception(ModelClass modelClass, Service service, Callback callback, Channel channel, Pathway pathway) {
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		String callbackBeanName = ClientLayerHelper.getClientNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		String serviceDomain = ServiceLayerHelper.getServiceDomainName(service);
		
		String messageType = getMessageType(callbackOperation);
		String messageBaseName = getMessageBaseName(callbackOperation);
		String messageBeanName = getMessageBeanName(callbackOperation);
		String messageClassName = getMessageClassName(callbackOperation);

		Interactor interactor = (Interactor) pathway.getRootCommand().getActor();
		String removeApplicationName = NameUtil.capName(interactor.getName());
		//TODO replace this with call to utils
		if (removeApplicationName.endsWith("Group"))
			removeApplicationName = removeApplicationName.replace("Group", "");
		String removeServiceName = NameUtil.capName(interactor.getService());
		String removeServiceNameUncapped = NameUtil.uncapName(removeServiceName);
		
		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.setCallback(callback);
		helper.initializeForCallback(pathway);

		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String testOperationName = "runTest_"+serviceOperationName+"_"+pathway.getLabel();
		//String testOperationName = "runTest_"+serviceOperationName+"_exception";
		if (channel != null)
			testOperationName += "_"+channel.getName();
		//testOperationName += "_from_exception";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
				//removeServiceName+"Client", 
				processClassName,
				//removeServiceNameUncapped,
				"fire_"+removeServiceName+"_request_sent",
				//"fire_"+removeApplicationName+"_"+removeServiceName+"_request_sent",
				//"handle_"+serviceInterfaceName+"_request", 
				"AT EXIT", 
				"throw new org.aries.ApplicationFailure(\\\"exception message\\\")", 
				testIterationCount));
		
		Process process = service.getProcess();
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperationName);
		List<Command> commands = processOperation.getCommands();
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		buf.putLine2("setupByteman(testName);");
		buf.putLine2("registerNotificationListeners();");
		buf.putLine2("");
		buf.put(createSource_branchContextPreparation(service, callback, pathway));

		buf.putLine2("");
		buf.putLine2("// setup expectations");
		//buf.putLine2("expectedEvent = \""+serviceInterfaceName+"_Request_Done\";");
		buf.putLine2("expectedEvent = \""+removeApplicationName+"_"+removeServiceName+"_Outgoing_Request_Aborted\";");
		buf.putLine2("expectedMessage = \""+serviceDomain+"."+messageBaseName+"\";");
		buf.putLine2("exceptionMessage = \"exception message\";");

		buf.putLine2("");
		buf.putLine2("// setup mock response");
		String fixtureClassName = ModelLayerHelper.getFixtureClassName(messageType);
		buf.putLine2(messageBeanName+" = "+fixtureClassName+".create_"+messageClassName+"();");
		Element messageElement = context.getElementByName(messageClassName);
		if (ElementUtil.isFieldExists(messageElement, "reason")) {
			buf.putLine2(messageBeanName+".setReason(\"exception message\");");
		}
		
//		buf.putLine2("");
//		String expectedIncomingCallbackName = pathway.getName();
//		if (expectedIncomingCallbackName != null) {
//			Service incomingCallback = context.getIncomingCallbackByName(expectedIncomingCallbackName);
//			//String incomingCallbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(incomingCallback);
//			Operation incomingCallbackOperation = ServiceUtil.getDefaultOperation(incomingCallback);
//			String incomingCallbackMessageName = getMessageClassName(incomingCallbackOperation);
//			String expectedCallbackUncapped = NameUtil.uncapName(incomingCallbackMessageName);
//			
//			buf.putLine2("// setup mock response");
//			buf.putLine2("expectedCallback = \""+expectedIncomingCallbackName+"\";");
//			buf.putLine2(expectedCallbackUncapped+" = create"+incomingCallbackMessageName+"();");
//		}
		
//		if (service.getName().equals("orderBooks"))
//			System.out.println();
		
		//List<String> variableNames = new ArrayList<String>();
		Stack<ConditionStatement> conditionStatements = pathway.getConditionStatements();
		Iterator<ConditionStatement> iterator = conditionStatements.iterator();
		while (iterator.hasNext()) {
			ConditionStatement conditionStatement = iterator.next();
			ExpressionStatement expressionStatement1 = conditionStatement.getExpressionStatement1();
			String variableName = expressionStatement1.getTargetName();
			String variableSource = findVariableSource(variableName, commands);
			if (variableSource == null)
				continue;
			if (variableSource.endsWith("Manager"))
				variableSource = variableSource.replace("Manager",  "");
			String variableSourceUncapped = NameUtil.uncapName(variableSource);
			String variableSourceCapped = NameUtil.capName(variableSource);
			buf.putLine2(variableSourceUncapped+"Helper.clear"+variableSourceCapped+"();");
			
			String typeName = expressionStatement1.getTargetType().getType();
			if (pathway.getType().equals("GT") || pathway.getType().equals("GE"))
				buf.putLine2(variableSourceUncapped+"Helper.add"+typeName+"ListTo"+variableSourceCapped+"();");
		}
		
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// execute action");
		buf.putLine2("	"+fixtureClassName+".reset();");
		buf.putLine2("	runAction_send_"+serviceInterfaceName+"();");

		helper.setIndent(3);
		buf.put(helper.getValidateRequestReceivedStateSource());
		buf.put(helper.getValidateRemoteInteractionsStateSource(pathway));
		buf.put(helper.getValidateSuccessfulCallbackStateSource());
		buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateOutgoingRequestAbortedStateSource(removeApplicationName, removeServiceName));
		buf.put(helper.getValidateRequestCompletionStateSource(true));
		buf.put(helper.getValidateEmptyCacheStateSource(pathway));
		
		//buf.putLine2("");
		//buf.putLine2("// cleanup");
		//buf.putLine2("tearDownByteman(testName);");
		//buf.putLine2("removeMessagesFromDestinations();");
		
		buf.putLine2("	");
		//buf.putLine2("	log.info(testName+\": done\");");
		buf.putLine2("	if (errorMessage != null)");
		buf.putLine2("		fail(errorMessage);");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	tearDownByteman(testName);");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	protected void createMethod_RunTest_timeout(ModelClass modelClass, Service service) {
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String domainServiceName = ServiceUtil.getExtendedDomainServiceName(service);
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		//String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		//String callbackBeanName = ClientLayerHelper.getClientNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		//Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		if (serviceOperation.getParameters().isEmpty())
			return;
		
		String messageType = getMessageType(serviceOperation);
		String messageBaseName = getMessageBaseName(serviceOperation);
		String messageBeanName = getMessageBeanName(serviceOperation);
		String messageClassName = getMessageClassName(serviceOperation);
		String serviceDomain = ServiceLayerHelper.getServiceDomainName(service);

		String fixtureClassName = ModelLayerHelper.getFixtureClassName(messageType);
		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.initializeForService();

		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String testOperationName = "runTest_"+serviceOperationName;
		//String testOperationName = "runTest_"+serviceOperationName+"_timeout";
		testOperationName += "_timeout";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
				serviceInterfaceName+"HandlerImpl", 
				serviceOperation.getName(), 
				"AT ENTRY", 
				"$0.timeout = 0", 
				testIterationCount));
		
		Process process = service.getProcess();
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperationName);
		List<Command> commands = processOperation.getCommands();
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		buf.putLine2("setupByteman(testName);");
		buf.putLine2("registerNotificationListeners();");
		//buf.putLine2("");
		//buf.put(createSource_branchContextPreparation(service, pathway));

		buf.putLine2("");
		buf.putLine2("// setup expectations");
		buf.putLine2("expectedEvent = \""+domainServiceName+"_Incoming_Request_Aborted\";");
		buf.putLine2("//TODO expectedMessage = \""+serviceDomain+"."+messageBaseName+"\";");
		
//		if (service.getName().equals("orderBooks"))
//			System.out.println();
		
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// execute action");
		buf.putLine2("	"+fixtureClassName+".reset();");
		buf.putLine2("	runAction_send_"+serviceInterfaceName+"();");

		helper.setIndent(3);
		buf.put(helper.getValidateRequestReceivedStateSource());
		//buf.put(helper.getValidateRemoteInteractionsStateSource(pathway));
		//buf.put(helper.getValidateSuccessfulCallbackStateSource());
		//buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateRequestNonCompletionStateSource());
		buf.put(helper.getValidateIncomingRequestAbortedStateSource());
		//buf.put(helper.getValidateEmptyCacheStateSource(pathway));
		
		//buf.putLine2("");
		//buf.putLine2("// cleanup");
		//buf.putLine2("tearDownByteman(testName);");
		//buf.putLine2("removeMessagesFromDestinations();");
		
		buf.putLine2("	");
		//buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("	if (errorMessage != null)");
		buf.putLine2("		fail(errorMessage);");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	tearDownByteman(testName);");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	protected void createMethod_RunTest_exception(ModelClass modelClass, Service service) {
		String processClassName = ServiceLayerHelper.getProcessClassName(service.getProcess());
		String domainServiceName = ServiceUtil.getExtendedDomainServiceName(service);
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		//String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		//String callbackBeanName = ClientLayerHelper.getClientNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		//Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		if (serviceOperation.getParameters().isEmpty())
			return;
		
		String messageType = getMessageType(serviceOperation);
		String messageBaseName = getMessageBaseName(serviceOperation);
		String messageClassName = getMessageClassName(serviceOperation);
		String serviceDomain = ServiceLayerHelper.getServiceDomainName(service);
		
		String fixtureClassName = ModelLayerHelper.getFixtureClassName(messageType);
		ServiceListenerITBuilderHelper helper = new ServiceListenerITBuilderHelper(context);
		helper.setService(service);
		helper.initializeForService();

		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String testOperationName = "runTest_"+serviceOperationName;
		//String testOperationName = "runTest_"+serviceOperationName+"_exception";
		testOperationName += "_exception";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
				processClassName, 
				serviceOperation.getName(), 
				"AT EXIT", 
				"throw new org.aries.ApplicationFailure(\\\"exception message\\\")", 
				testIterationCount));
		
		Process process = service.getProcess();
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperationName);
		List<Command> commands = processOperation.getCommands();
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		
		buf.putLine2("");
		buf.putLine2("setupByteman(testName);");
		buf.putLine2("registerNotificationListeners();");
		//buf.putLine2("");
		//buf.put(createSource_branchContextPreparation(service, pathway));

		buf.putLine2("");
		buf.putLine2("// setup expectations");
		//buf.putLine2("expectedEvent = \""+serviceDomain+"."+serviceInterfaceName+"_Request_Done\";");
		buf.putLine2("expectedEvent = \""+domainServiceName+"_Incoming_Request_Aborted\";");
		buf.putLine2("expectedMessage = \""+serviceDomain+"."+messageBaseName+"\";");
		buf.putLine2("exceptionMessage = \"exception message\";");
		
//		if (service.getName().equals("orderBooks"))
//			System.out.println();
		
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// execute action");
		buf.putLine2("	"+fixtureClassName+".reset();");
		buf.putLine2("	runAction_send_"+serviceInterfaceName+"();");

		helper.setIndent(3);
		buf.put(helper.getValidateRequestReceivedStateSource());
		//buf.put(helper.getValidateRemoteInteractionsStateSource(pathway));
		//buf.put(helper.getValidateSuccessfulCallbackStateSource());
		//buf.put(helper.getValidateNonExistantCallbackStateSource());
		buf.put(helper.getValidateRequestNonCompletionStateSource());
		buf.put(helper.getValidateIncomingRequestAbortedStateSource());
		//buf.put(helper.getValidateEmptyCacheStateSource(pathway));
		
		//buf.putLine2("");
		//buf.putLine2("// cleanup");
		//buf.putLine2("tearDownByteman(testName);");
		//buf.putLine2("removeMessagesFromDestinations();");
		
		buf.putLine2("	");
		//buf.putLine2("	log.info(testName+\": done\");");
		buf.putLine2("	if (errorMessage != null)");
		buf.putLine2("		fail(errorMessage);");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	tearDownByteman(testName);");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		testIterationCount++;
	}

	
	protected String createSource_cacheUnits_PreparationOLD(Service service, Pathway pathway) {
		Buf buf = new Buf();
		
		Set<Cache> cacheReferences = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheReferences.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			String cacheName = cacheUnit.getName();
			Stack<ConditionStatement> conditionStatements = pathway.getConditionStatements();
			
			if (!conditionStatements.isEmpty()) {
				ConditionStatement conditionStatement = conditionStatements.peek();
				Definition definition1 = conditionStatement.getExpressionStatement1().getDefinition();
				Definition definition2 = conditionStatement.getExpressionStatement2().getDefinition();
					
				Process process = service.getProcess();
				if (definition1 != null && definition1.getTarget().equals(cacheName)) {
					//Cache cacheUnit = ProcessUtil.getCacheUnit(process, cacheName);
					Assert.notNull(cacheUnit, "CacheUnit not found: "+cacheName);

					String fieldNameCapped = NameUtil.capName(definition1.getName());
					Assert.isTrue(definition1.getSelectors().size() == 1, "CacheUnit should have only 1 selector");
					String selector = definition1.getSelectors().get(0);
					
					if (selector.startsWith("get")) {
						buf.putLine2(cacheName+"Helper.addTo"+fieldNameCapped+"();");
						
					} else if (selector.startsWith("set")) {
					} else if (selector.startsWith("add")) {
					} else if (selector.startsWith("remove")) {
					}
				}
				
				if (definition2 != null && definition2.getTarget().equals(cacheName)) {
					
				}
			}
			
			//buf.putLine2(cacheName+"Helper.assureRemoveAll();");
		}
		return buf.get();
	}
	
	protected String createSource_cacheUnits_RemoveAll(Service service) {
		Buf buf = new Buf();
		Set<Cache> cacheUnitReferences = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnitReferences.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			String unitName = cacheUnit.getName();
			buf.putLine2(unitName+"Helper.assureRemoveAll();");
		}
		return buf.get();
	}
	
	protected String createSource_dataUnits_RemoveAll(Service service) {
		Buf buf = new Buf();
		Set<String> dataUnitReferences = ServiceUtil.getDataUnitReferences(service);
		Iterator<String> iterator = dataUnitReferences.iterator();
		while (iterator.hasNext()) {
			String unitName = iterator.next();
			buf.putLine2(unitName+"Helper.assureRemoveAll();");
		}
		return buf.get();
	}

	protected String getTargetField(ExpressionStatement expressionStatement) {
		Definition definition = expressionStatement.getDefinition();
		String selector = null;
		if (definition != null) {
			List<String> selectorChain = definition.getSelectors();
			int selectorChainSize = selectorChain.size();
			if (selectorChainSize > 0) {
				selector = selectorChain.get(selectorChainSize - 1);
			}
		} else {
			selector = expressionStatement.getSelector();
		}
		
		if (selector == null)
			return null;
		String targetName = OperationUtil.getTargetTypeFromOperationName(selector);
		return targetName;
	}

	protected String createSource_branchContextPreparation(Service service, Callback callback, Pathway pathway) {
		Buf buf = new Buf();
		
		buf.put(createSource_cacheUnits_RemoveAll(service));
		buf.put(createSource_dataUnits_RemoveAll(service));
		
//		if (service.getName().equals("orderBooks"))
//		System.out.println();
	
		Collection<Branch> requiredBranches = pathway.getRequiredBranches();
		Iterator<Branch> iterator = requiredBranches.iterator();
		while (iterator.hasNext()) {
			Branch branch = iterator.next();
			boolean isSuccess = branch.isSuccess();
			ConditionStatement conditionStatement = branch.getConditionStatement();
			buf.put(createSource_branchContextPreparation(conditionStatement, isSuccess));
		}

		if (requiredBranches.isEmpty()) {
			Stack<ConditionStatement> conditionStatements = pathway.getConditionStatements();
			Iterator<ConditionStatement> iterator2 = conditionStatements.iterator();
			while (iterator2.hasNext()) {
				ConditionStatement conditionStatement = iterator2.next();
				boolean isSuccess = isSuccessPathwayForCondition(pathway, conditionStatement);
				buf.put(createSource_branchContextPreparation(conditionStatement, isSuccess));
			}
		}
		
		if (buf.length() > 0) {
			buf.insertLine2("// prepare test data");
			Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
			String callbackMessageNamespace = getMessageNamespace(callbackOperation);
			String fixtureClassName = ModelLayerHelper.getModelFixtureClassName(callbackMessageNamespace);
			if (isNeedFixtureReset)
				buf.putLine2(fixtureClassName+".reset();");
		}
		return buf.get();
	}

	protected boolean isSuccessPathwayForCondition(Pathway pathway, ConditionStatement conditionStatement) {
		String pathwayType = pathway.getType();
		String operator = conditionStatement.getOperator();
		
		boolean GT = operator.equals(">");
		boolean GE = operator.equals(">=");
		boolean EQ = operator.equals("==");
		boolean NE = operator.equals("!-");
		boolean LT = operator.equals("<");
		boolean LE = operator.equals("<=");

		if (EQ) {
			return pathwayType.equals("EQ");
		} else if (GT) {
			return pathwayType.equals("GT");
		} else if (GE) {
			return pathwayType.equals("GE");
		} else if (LT) {
			return pathwayType.equals("LT");
		} else if (LE) {
			return pathwayType.equals("LE");
		}

		return false;
	}

	protected String createSource_branchContextPreparation(ConditionStatement conditionStatement, boolean isSuccess) {
		isNeedFixtureReset = false;
		Buf buf = new Buf();
		
		ExpressionStatement expressionStatement1 = conditionStatement.getExpressionStatement1();
		ExpressionStatement expressionStatement2 = conditionStatement.getExpressionStatement2();
		
		Type targetType1 = expressionStatement1.getTargetType();
		Type targetType2 = expressionStatement2.getTargetType();

		String objectName = BranchUtil.getObjectName(expressionStatement1);
		String objectNameUncapped = NameUtil.uncapName(objectName);
		String objectNameCapped = NameUtil.capName(objectName);
		
		String targetField = getTargetField(expressionStatement1);
		String rhsValue = BranchUtil.getValue(expressionStatement2);
		String operator = conditionStatement.getOperator();
		
		boolean GT = operator.equals(">");
		boolean GE = operator.equals(">=");
		boolean EQ = operator.equals("==");
		boolean NE = operator.equals("!-");
		boolean LT = operator.equals("<");
		boolean LE = operator.equals("<=");
		
		if (isSuccess && EQ) {
			//validate type as numeric

		} else {
			//validate type as numeric
			int rhsValueAsInt = Integer.parseInt(rhsValue);
			
			if (!isSuccess && EQ) {
				//validate type as numeric
				rhsValueAsInt += 2;
				//if (targetType1 == null)
				//	System.out.println();
				String typeName = TypeUtil.getClassName(targetType1.getType());
				buf.putLine2(objectNameUncapped+"Helper.addTo"+targetField+"("+rhsValueAsInt+");");
				isNeedFixtureReset = true;
			
			} else if (isSuccess && (GT || GE)) {
				String typeName = TypeUtil.getClassName(targetType1.getType());
				buf.putLine2(objectNameUncapped+"Helper.clear"+objectNameCapped+"();");
				buf.putLine2(objectNameUncapped+"Helper.addTo"+targetField+"("+rhsValueAsInt+");");
				//buf.putLine2(variableSourceUncapped+"Helper.add"+typeName+"ListTo"+variableSourceCapped+"();");
				isNeedFixtureReset = true;
			
			} else if (!isSuccess && (GT || GE)) {
				//validate type as numeric
				String typeName = TypeUtil.getClassName(targetType1.getType());
				buf.putLine2(objectNameUncapped+"Helper.clear"+objectNameCapped+"();");
				buf.putLine2(objectNameUncapped+"Helper.addTo"+targetField+"("+rhsValueAsInt+");");
				isNeedFixtureReset = true;
			
			} else if (isSuccess && (LT || LE)) {
				//validate type as numeric
				rhsValueAsInt += 2;
				//if (targetType1 == null)
				//	System.out.println();
				String typeName = TypeUtil.getClassName(targetType1.getType());
				buf.putLine2(objectNameUncapped+"Helper.addTo"+targetField+"("+rhsValueAsInt+");");
				isNeedFixtureReset = true;

			} else if (!isSuccess && (LT || LE)) {
				//validate type as numeric
				rhsValueAsInt += 2;
				String typeName = TypeUtil.getClassName(targetType1.getType());
				buf.putLine2(objectNameUncapped+"Helper.addTo"+targetField+"("+rhsValueAsInt+");");
				isNeedFixtureReset = true;
			}
		}
		
		return buf.get();
	}
	
	
	protected List<Pathway> getCodePathwaysForCallback(Collection<Command> serviceCommands, List<Command> commands, Callback callback) {
		String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		List<Pathway> pathways = new ArrayList<Pathway>();
		
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();

			if (command instanceof InvokeCommand) {
				InvokeCommand invokeCommand = (InvokeCommand) command;
				pathways.addAll(getCodePathwaysFromCommandBlocks(serviceCommands, invokeCommand, invokeCommand.getResponseBlocks(), callback, "response"));
				pathways.addAll(getCodePathwaysFromCommandBlocks(serviceCommands, invokeCommand, invokeCommand.getTimeoutBlocks(), callback, "timeout"));
				pathways.addAll(getCodePathwaysFromCommandBlocks(serviceCommands, invokeCommand, invokeCommand.getExceptionBlocks(), callback, "exception"));
				
			} else if (command instanceof IfStatement) {
				IfStatement ifStatement = (IfStatement) command;

				ConditionStatement conditionStatement = ifStatement.getConditionStatement();
				ExpressionStatement expressionStatement1 = conditionStatement.getExpressionStatement1();
				ExpressionStatement expressionStatement2 = conditionStatement.getExpressionStatement2();

				String label1 = expressionStatement1.getLabel();
				String label2 = expressionStatement2.getLabel();
				//String operatorName = OperatorUtil.getOperatorName(conditionStatement.getOperator());
				String operatorName = conditionStatement.getOperator();
				
				if (callbackTriggered(ifStatement.getCommands(), callback)) {
					if (operatorName.equals("==")) {
						Pathway pathway = new Pathway();
						pathway.setType("EQ");
						pathway.setRootCommand(ifStatement);
						pathway.setCommands(ifStatement.getCommands());
						pathway.setLabel("from_" + callbackInterfaceName + "_" + label1 + "_EQ_" + label2);
						pathway.pushConditionStatement(conditionStatement);
						addDefinitionsToPathway(pathway, commands);
						addDefinitionsToPathway(pathway, ifStatement.getCommands());
						pathways.add(pathway);
					}
					
				} else if (callbackTriggered(ifStatement.getElseCommands(), callback)) {
					if (operatorName.equals("==")) {
						Pathway pathway = new Pathway();
						pathway.setType("GT");
						pathway.setRootCommand(ifStatement);
						pathway.setCommands(ifStatement.getElseCommands());
						pathway.setLabel("from_" + callbackInterfaceName + "_" + label1 + "_GT_" + label2);
						pathway.pushConditionStatement(conditionStatement);
						addDefinitionsToPathway(pathway, commands);
						addDefinitionsToPathway(pathway, ifStatement.getElseCommands());
						pathways.add(pathway);
						
						String objectName = expressionStatement2.getTargetName();
						if (!expressionStatement1.getSelector().equals("size") || NumberUtil.isNumber(objectName) && Integer.parseInt(objectName) > 0) {
							pathway = new Pathway();
							pathway.setType("LT");
							pathway.setRootCommand(ifStatement);
							pathway.setCommands(ifStatement.getElseCommands());
							pathway.setLabel("from_" + callbackInterfaceName + "_" + label1 + "_LT_" + label2);
							pathway.pushConditionStatement(conditionStatement);
							addDefinitionsToPathway(pathway, commands);
							addDefinitionsToPathway(pathway, ifStatement.getElseCommands());
							pathways.add(pathway);
						}
					}
				}
				
				pathways.addAll(getCodePathwaysForCallback(serviceCommands, ifStatement.getCommands(), callback));
				pathways.addAll(getCodePathwaysForCallback(serviceCommands, ifStatement.getElseCommands(), callback));
				
			} else {
				String type = command.getType();
				if (type.equals("unnamed")) {
					pathways.addAll(getCodePathwaysForCallback(serviceCommands, command.getCommands(), callback));
				}
			}
		}
		
		return pathways;
	}

	public <T extends AbstractBlock> Collection<Pathway> getCodePathwaysFromCommandBlocks(Collection<Command> serviceCommands, Command rootCommand, List<T> commandBlocks, Callback callback, String type) {
		List<Pathway> pathways = new ArrayList<Pathway>();
		Iterator<T> iterator2 = commandBlocks.iterator();
		while (iterator2.hasNext()) {
			AbstractBlock commandBlock = iterator2.next();
			pathways.addAll(getCodePathwaysFromCommandBlock(serviceCommands, rootCommand, commandBlock, callback, type));
		}
		return pathways;
	}
	
	public <T extends AbstractBlock> Collection<Pathway> getCodePathwaysFromCommandBlock(Collection<Command> serviceCommands, Command rootCommand, T commandBlock, Callback callback, String type) {
		String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		List<Pathway> pathways = new ArrayList<Pathway>();
		String pathwayName = commandBlock.getName();
		String blockName = commandBlock.getName();
		if (blockName == null)
			blockName = type;
		
		Interactor interactor = (Interactor) rootCommand.getActor();
		String removeApplicationName = NameUtil.capName(interactor.getName());
		String removeServiceName = NameUtil.capName(interactor.getService());
		
		List<Command> commands = commandBlock.getCommands();
		Collection<ReplyCommand> replyCommands = CommandUtil.getCommands(commands, ReplyCommand.class);
		Iterator<ReplyCommand> iterator3 = replyCommands.iterator();
		while (iterator3.hasNext()) {
			ReplyCommand replyCommand = iterator3.next();
			String messageName = replyCommand.getMessageName();
			if (messageName.equals(callbackInterfaceName)) {
				Pathway pathway = new Pathway();
				if (pathwayName != null)
					pathway.setName(pathwayName);
				else pathway.setName(messageName);
				pathway.setType(type);
				
				Service removeService = context.getServiceByName(removeApplicationName, removeServiceName);
				pathway.setRemoteService(removeService);
				
				pathway.setLabel(callbackInterfaceName + "_from_"+removeServiceName+"_"+blockName);
				pathway.setRootCommand(rootCommand);
				pathway.setCommandBlock(commandBlock);
				
				if (rootCommand instanceof BlockStatement) {
					BlockStatement blockStatement = (BlockStatement) rootCommand;
					pathway.addRequiredConditions(blockStatement.getRequiredConditions());
					String text = rootCommand.getText();
					//System.out.println(">>>> "+text);
					//if (text.equalsIgnoreCase("supplier.shipbooks"))
					//	System.out.println();
				}
				
				addDefinitionsToPathway(pathway, serviceCommands);
				addDefinitionsToPathway(pathway, commands);
				//if (callbackInterfaceName.equals("PurchaseRejected"))
				//	System.out.println();

				//Collection<DoneCommand> doneCommands = CommandUtil.getCommands(commands, DoneCommand.class);
				//pathway.setTerminatesExecution(doneCommands.size() > 0);
				pathways.add(pathway);
				//break;
			}
		}
		return pathways;
	}

	public static void addDefinitionsToPathway(Pathway pathway, Collection<Command> commands) {
		Collection<DefinitionCommand> definitionCommands = CommandUtil.getCommands(commands, DefinitionCommand.class);
		Iterator<DefinitionCommand> iterator = definitionCommands.iterator();
		while (iterator.hasNext()) {
			DefinitionCommand definitionCommand = iterator.next();
			Definition definition = definitionCommand.getDefinition();
			pathway.addAvailableDefinition(definition);
		}
	}

	public static boolean callbackTriggered(List<Command> commands, Callback callback) {
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			if (command instanceof ReplyCommand) {
				ReplyCommand replyCommand = (ReplyCommand) command;
				if (replyCommand.getMessageName().equals(callback.getInterfaceName())) {
					return true;
				}
			} else {
				if (command.getCommands() == null)
					throw new RuntimeException("CODE PROBLEM");
				if (callbackTriggered(command.getCommands(), callback))
					return true;
			}
		}
		return false;
	}

	protected String findVariableSource(String variableName, List<Command> commands) {
		String variableSource = null;
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			if (command instanceof DefinitionCommand) {
				DefinitionCommand definitionCommand = (DefinitionCommand) command;
				Definition definition = definitionCommand.getDefinition();
				if (definition.getName().equals(variableName)) {
					return definition.getTarget();
				}
			}
		}
		return variableSource;
	}

	protected void createMethod_RunTest_Success(ModelClass modelClass, Service service, Callback callback, Channel channel) {
		String callbackInterfaceName = ClientLayerHelper.getClientInterfaceName(callback);
		String callbackBeanName = ClientLayerHelper.getClientNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		String messageClassName = getMessageClassName(callbackOperation);

		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String testOperationName = "test"+serviceOperationName+"_"+callbackInterfaceName;
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		
		Process process = service.getProcess();
		Operation processOperation = ProcessUtil.getOperation(process, serviceOperationName);
		List<Command> commands = processOperation.getCommands();
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		buf.putLine2("clearSupplierOrderCache();");
		buf.putLine2("runTest("+callbackBeanName+"Listener);");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_RunTest_All(ModelClass modelClass, Service service, Callback callback, Channel channel) {
		String clientListenerName = ClientLayerHelper.getClientNameUncapped(callback);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		String messageClassName = getMessageClassName(callbackOperation);

		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		String testOperationName = "test"+serviceOperationName+"_"+messageClassName;
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(testOperationName);

		//provider.initialize(item);
		modelOperation.addException("Exception");
		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+testOperationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");
		buf.putLine2("clearSupplierOrderCache();");
		buf.putLine2("addBooksToSupplierOrderCache();");
		buf.putLine2("runTest("+clientListenerName+"Listener);");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_RunSendMessage(ModelClass modelClass, Service service) {
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("runAction_send_"+interfaceName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		String messageClassName = getMessageClassName(operation);
		buf.putLine2("runAction_send_"+interfaceName+"(create"+messageClassName+"());");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_RunSendMessage_main(ModelClass modelClass, Service service) {
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;
		
		String messagePackageName = getMessagePackageName(operation);
		String messageClassName = getMessageClassName(operation);
		String messageBeanName = getMessageBeanName(operation);
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("runAction_send_"+interfaceName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(CodeUtil.createParameter(messagePackageName, messageClassName, messageBeanName));
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(provider.getRunTest(modelClass, modelOperation, service));
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_RunSendMessage_cancel(ModelClass modelClass, Service service) {
		String serviceName = ServiceUtil.getExtendedDomainServiceName(service);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String interfaceNameUncapped = NameUtil.uncapName(interfaceName);
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;
		
		String messagePackageName = getMessagePackageName(operation);
		String messageClassName = getMessageClassName(operation);
		String messageBaseName = getMessageBaseName(operation);
		String messageBeanName = getMessageBeanName(operation);
		String domain = ServiceLayerHelper.getServiceDomainName(service);
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("runAction_send_"+interfaceName+"_cancel");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		//buf.putLine2("expectedEvent = \""+domain+"."+serviceName+"_Request_Cancel_Done\";");
		buf.putLine2("expectedEvent = \""+serviceName+"_Request_Cancel_Done\";");
		buf.putLine2("registerForResult(expectedEvent);");
		buf.putLine2("");
		buf.putLine2(messageBaseName+"Message message = create"+messageBaseName+"CancelMessage();");
		buf.putLine2(interfaceNameUncapped+"Client.send(message, correlationId, null);");
		buf.putLine2("");
		buf.putLine2("Object result = waitForCompletion();");
		buf.putLine2("validateResult(result);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_RunSendMessage_undo(ModelClass modelClass, Service service) {
		String serviceName = ServiceUtil.getExtendedDomainServiceName(service);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String interfaceNameUncapped = NameUtil.uncapName(interfaceName);
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;
		
		String messagePackageName = getMessagePackageName(operation);
		String messageClassName = getMessageClassName(operation);
		String messageBaseName = getMessageBaseName(operation);
		String messageBeanName = getMessageBeanName(operation);
		String domain = ServiceLayerHelper.getServiceDomainName(service);
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("runAction_send_"+interfaceName+"_undo");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		//buf.putLine2("expectedEvent = \""+domain+"."+serviceName+"_Request_Undo_Done\";");
		buf.putLine2("expectedEvent = \""+serviceName+"_Request_Undo_Done\";");
		buf.putLine2("registerForResult(expectedEvent);");
		buf.putLine2("");
		buf.putLine2(messageBaseName+"Message message = create"+messageBaseName+"UndoMessage();");
		buf.putLine2(interfaceNameUncapped+"Client.send(message, correlationId, null);");
		buf.putLine2("");
		buf.putLine2("Object result = waitForCompletion();");
		buf.putLine2("validateResult(result);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	
	protected void createMethod_StartRunSendMessage(ModelClass modelClass, Service service) {
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		Operation operation = ServiceUtil.getDefaultOperation(service);
		if (operation.getParameters().isEmpty())
			return;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("start_runAction_send_"+interfaceName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setResultType("Thread");
		
		Buf buf = new Buf();
		buf.putLine2("Thread thread = new Thread() {");
		buf.putLine2("	public void run() {");
		buf.putLine2("		try {");
		buf.putLine2("			runAction_send_"+interfaceName+"();");
		buf.putLine2("		} catch (Exception e) {");
		buf.putLine2("			errorMessage = e.getMessage();");
		buf.putLine2("		}");
		buf.putLine2("	}");
		buf.putLine2("};");
		buf.putLine2("thread.start();");
		buf.putLine2("Thread.sleep(4000);");
		buf.putLine2("return thread;");
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	
	protected <T extends Type> String getOperationName(SourceType sourceType, MethodType methodType, TestType testType, T dataItem) {
		String name = NameUtil.capName(dataItem.getName());
		String structure = dataItem.getStructure();
		String methodName = getDataAccessMethodPrefix(methodType, structure);
		String operationName = "test" + NameUtil.capName(methodName) + name;
		//operationName += getDataAccessMethodSuffix(methodType, structure);
		if (testType.isExecute())
			operationName = "runAction_" + operationName;
		else operationName = operationName + "_" + testType;
		return operationName;
	}

	protected boolean isParameterRequired(MethodType methodType, TestType testType) {
		return testType.isExecute();
	}
	
	protected <T extends Type> void createMethods_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
		String structure = dataItem.getStructure();
		if (structure.equals("item")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
			
		} else if (structure.equals("list")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));

		} else if (structure.equals("map")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Success));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Commit));
			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Rollback));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Manager + TestType.Null));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Repository + TestType.Null));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Execute));

			/*
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemByKey));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByKeys));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
			*/
		}
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, TestType testType, Type item) {
		provider.initialize(item);
		
		modelOperation.addException("Exception");
		if (!testType.isExecute()) {
			testIterationCount++;
			modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
			modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
			modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		}

		switch (methodType) {
		case GetAllAsList:
			return provider.getNothingForNow();
		case GetAllAsMap: 
			return provider.getNothingForNow();
		case GetAsItem: 
		case GetAsItemByKey: 
			return provider.getNothingForNow();
		case GetAsList: 
		case GetAsListByKeys: 
			return provider.getNothingForNow();
		case GetAsMapByKeys: 
			return provider.getNothingForNow();
		case GetMatchingAsList: 
			return provider.getNothingForNow();
		case Set: 
			return provider.getNothingForNow();
		case AddAsItem: 
			return provider.getNothingForNow();
		case AddAsList: 
			return provider.getTestAddAsList(modelUnit, modelOperation, testType);
		case RemoveAll: 
			return provider.getNothingForNow();
		case RemoveAsItem:
			return provider.getNothingForNow();
		case RemoveAsItemByKey:
			return provider.getNothingForNow();
		case RemoveAsListByKeys:
			return provider.getNothingForNow();
		case RemoveAsList:
			return provider.getNothingForNow();
		case RemoveMatchingAsList:
			return provider.getNothingForNow();
		default: 
			return "";
		}
	}
	
}
