package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.model.Application;
import nam.model.Attribute;
import nam.model.Callback;
import nam.model.Command;
import nam.model.Element;
import nam.model.Field;
import nam.model.Interactor;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Project;
import nam.model.Reference;
import nam.model.Service;
import nam.model.statement.IfStatement;
import nam.model.statement.InvokeCommand;
import nam.model.statement.ReplyCommand;
import nam.model.statement.SendCommand;
import nam.model.util.ApplicationUtil;
import nam.model.util.ElementUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ProcessUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.Assert;
import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.bpel.BPELUtil;
import aries.codegen.AbstractBeanBuilder;
import aries.codegen.ModelOperationFactory;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.JMSUtil;
import aries.codegen.util.JMSUtil.JMSDestination;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelReference;


/**
 * Builds an Process Context Implementation {@link ModelClass} object given a {@link Process} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ProcessContextBuilder extends AbstractBeanBuilder {

	//private ProcessClassProvider processClassProvider;
	
	private Process process;
	
	private ModelClass modelClass;

	private ModelOperationFactory modelOperationFactory;

	private Set<String> messageCreationOperationsDone;

	private Set<String> serviceCompletionOperationsDone;

	private Map<String, ModelAttribute> stateAttributeMap = new HashMap<String, ModelAttribute>();
	
	private Map<String, ModelReference> stateReferenceMap = new HashMap<String, ModelReference>();
	
	
	public ProcessContextBuilder(GenerationContext context) {
		//processClassProvider = new ProcessClassProvider(context);
		modelOperationFactory = new ModelOperationFactory(context);
		this.context = context;
	}
	
	public ModelClass buildClass(Process process) throws Exception {
		messageCreationOperationsDone = new HashSet<String>();
		serviceCompletionOperationsDone = new HashSet<String>();
		this.process = process;
		
		String processName = ServiceLayerHelper.getProcessContextName(process);
		String packageName = ServiceLayerHelper.getProcessContextPackageName(process);
		String className = ServiceLayerHelper.getProcessContextClassName(process);
		String baseName = ProjectUtil.getBaseName(processName);
		String beanName = NameUtil.uncapName(className);
		String typeName = ServiceLayerHelper.getProcessType(process);
//		if (processName.equalsIgnoreCase("SellerProcess"))
//			System.out.println();
		setBaseName(baseName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		modelClass = new ModelClass();
		modelClass.setType(typeName);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setParentClassName("org.aries.tx.ConversationContext");
		modelClass.addImportedClass("org.aries.tx.ConversationContext");

		org.eclipse.bpel.model.Process bpelProcess = BPELUtil.getBPELProcess(process);
		if (bpelProcess != null) {
			Object activityBlock = bpelProcess.getActivity();
			if (activityBlock != null) {
				if (activityBlock instanceof Sequence) {
					initializeClass(modelClass, process, (Sequence) activityBlock);
				} else if (activityBlock instanceof Flow) {
					initializeClass(modelClass, process, (Flow) activityBlock);
				}
			}
		} else {
			initializeClass(modelClass, process);
		}
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Process process) throws Exception {
		stateAttributeMap = new HashMap<String, ModelAttribute>();
		stateReferenceMap = new HashMap<String, ModelReference>();
		initializeImportedClasses(modelClass, process);
		initializeClassAnnotations(modelClass, process);
		initializeInstanceFields(modelClass, process);
		initializeInstanceOperations(modelClass, process);
	}

	protected void initializeClass(ModelClass modelClass, Process process, Sequence sequence) throws Exception {
		//TODO
	}

	protected void initializeClass(ModelClass modelClass, Process process, Flow flow) throws Exception {
		//TODO
	}

	
	protected void initializeImportedClasses(ModelClass modelClass, Process process) {
		modelClass.addImportedClass("java.util.Collection");
		//modelClass.addImportedClass("java.util.HashMap");
		//modelClass.addImportedClass("java.util.Iterator");
		//modelClass.addImportedClass("java.util.List");
		//modelClass.addImportedClass("java.util.Map");

		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		//modelClass.addImportedClass("org.aries.notification.NotificationDispatcher");
		//modelClass.addImportedClasses(CodeUtil.getImportedClasses(process));
		//modelClass.addImportedClass("org.aries.validate.util.Check");

		switch (context.getServiceLayerBeanType()) {
		case EJB:
			modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
			modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
			//modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRES_NEW");
			modelClass.addStaticImportedClass("javax.ejb.TransactionManagementType.CONTAINER");
			//modelClass.addImportedClass("javax.inject.Inject");
			modelClass.addImportedClass("javax.ejb.LocalBean");
			//modelClass.addImportedClass("javax.ejb.Remove");
			//modelClass.addImportedClass("javax.ejb.Stateful");
			modelClass.addImportedClass("javax.ejb.Singleton");
			//modelClass.addImportedClass("javax.ejb.Startup");
			//modelClass.addImportedClass("javax.ejb.Asynchronous");
			modelClass.addImportedClass("javax.ejb.AccessTimeout");
			modelClass.addImportedClass("javax.ejb.ConcurrencyManagement");
			modelClass.addImportedClass("javax.ejb.TransactionAttribute");
			modelClass.addImportedClass("javax.ejb.TransactionManagement");
			modelClass.addImportedClass("javax.annotation.PostConstruct");
			modelClass.addImportedClass("javax.annotation.PreDestroy");
			break;
			
		case SEAM:
			modelClass.addImportedClass("org.jboss.seam.ScopeType");
			modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
			modelClass.addImportedClass("org.jboss.seam.annotations.Name");
			modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
			modelClass.addImportedClass("org.jboss.seam.annotations.In");
			modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
			break;
			
		case POJO:
			modelClass.addImportedClass("org.aries.runtime.BeanContext");
			modelClass.addImportedClass("org.aries.registry.ProcessLocator");
			//modelClass.addImportedClass("org.aries.tx.Transactional");
			//modelClass.addImportedClass("common.tx.TransactionParticipantManager");
			break;
		}
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Process process) throws Exception {
		String className = modelClass.getClassName();
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createSingletonAnnotation());
			//classAnnotations.add(AnnotationUtil.createStatefulAnnotation());
			classAnnotations.add(AnnotationUtil.createLocalBeanAnnotation());
			classAnnotations.add(AnnotationUtil.createAccessTimeoutAnnotation(60000));
			classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
			classAnnotations.add(AnnotationUtil.createTransactionAttributeAnnotation());
			classAnnotations.add(AnnotationUtil.createTransactionManagementAnnotation());
			break;
			
		case SEAM:
			//classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
			////classAnnotations.add(AnnotationUtil.createSuppressSerialWarning());
			//classAnnotations.add(AnnotationUtil.createBypassInterceptorsAnnotation());
			//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
			
			//String contextName = NameUtil.uncapName(modelClass.getName());
			//if (context.isEnabled("generateServicePerOperation"))
			//	contextName = NameUtil.uncapName(process.getName()) + "." + contextName;
			//if (context.isEnabled("useQualifiedContextNames")) {
			//	String contextPrefix = NameUtil.getQualifiedContextNamePrefix(modelClass.getQualifiedName(), 2);
			//	classAnnotations.add(AnnotationUtil.createNameAnnotation(contextPrefix + "." + contextName));
			//} else classAnnotations.add(AnnotationUtil.createNameAnnotation(contextName));
			break;
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Process process) throws Exception {
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			modelClass.clearLocalVariables();
			Operation operation = iterator.next();
			context.setOperation(operation);

			initializeInstanceFields(modelClass, process, operation);

			String serviceId = operation.getName();
			String serviceInterface = NameUtil.capName(serviceId);
			Application application = context.getApplication();
			Service service = ApplicationUtil.getServiceByName(application, serviceInterface);
			if (service != null) {
				initializeInstanceFields(modelClass, process, service);
			}
		}
		
		Set<String> events = ProcessUtil.getAllEvents(process);
		Iterator<String> iterator2 = events.iterator();
		while (iterator2.hasNext()) {
			String eventName = iterator2.next();
			eventName = NameUtil.capName(eventName);
			if (!eventName.endsWith("Event"))
				eventName += "Event";
			Element element = context.getElementByName(eventName);
			initializeInstanceFields(modelClass, process, element);
		}
		
//		List<Operation> operations = process.getOperations();
//		Iterator<Operation> iterator = operations.iterator();
//		while (iterator.hasNext()) {
//			modelClass.clearLocalVariables();
//			Operation operation = iterator.next();
//			context.setOperation(operation);
//			initializeInstanceFields(modelClass, process, operation);
//		}
		
		modelClass.addInstanceReferences(modelOperationFactory.createReferences_ObjectFactory());
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Process process, Service service) throws Exception {
		initializeInstanceFields(modelClass, process, ServiceUtil.getOperations(service));
		List<Callback> outgoingCallbacks = ServiceUtil.getOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			initializeInstanceFields(modelClass, process, ServiceUtil.getOperations(callback));
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Process process, List<Operation> operations) throws Exception {
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			context.setOperation(operation);
			initializeInstanceFields(modelClass, process, operation);
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Process process, Operation operation) {
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterType = parameter.getType();

			Element parameterElement = context.getElementByType(parameterType);
			//Assert.notNull(parameterElement, "Element not found for parameter: "+parameterName);
			if (parameterElement != null)
				initializeInstanceFields(modelClass, process, parameterElement);
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Process process, Element element) {
		List<Field> fields = ElementUtil.getApplicationDefinedFields(element);
		Iterator<Field> fieldIterator = fields.iterator();
		while (fieldIterator.hasNext()) {
			Field field = fieldIterator.next();
			String beanName = field.getName();
			String fieldType = field.getType();
			String fieldName = NameUtil.capName(beanName);
			Element fieldByType = context.getElementByType(fieldType);
			String structure = field.getStructure();

			String className = TypeUtil.getClassName(fieldType);
			String packageName = TypeUtil.getPackageName(fieldType);
			modelClass.addImportedClass(packageName+"."+className);
			addImportedInterfaceForStructure(modelClass, field);

			if (field instanceof Attribute) {
				ModelAttribute modelAttribute = new ModelAttribute();
				modelAttribute.setModifiers(Modifier.PRIVATE);
				modelAttribute.setStructure(structure);
				modelAttribute.setName(beanName);
				modelAttribute.setType(fieldType);
				modelAttribute.setPackageName(packageName);
				modelAttribute.setClassName(className);
				//attribute.setValue("new "+className+"();");
				modelAttribute.setGenerateGetter(true);
				modelAttribute.setGenerateSetter(true);
				modelAttribute.setGenerateAddMethod(true);
				modelAttribute.setGenerateRemoveMethod(true);
				modelAttribute.setGenerateClearMethod(true);
				modelClass.addInstanceAttribute(modelAttribute);
				
				String qualifiedName = packageName + "." + className;
				stateAttributeMap.put(qualifiedName, modelAttribute);
				continue;
			}
			
			if (field instanceof Reference) {
				//if (beanName.equals("books"))
				//	System.out.println();
				ModelReference modelReference = new ModelReference();
				modelReference.setModifiers(Modifier.PRIVATE);
				modelReference.setStructure(structure);
				modelReference.setName(beanName);
				modelReference.setType(fieldType);
				modelReference.setPackageName(packageName);
				modelReference.setClassName(className);
				//attribute.setValue("new "+className+"();");
				modelReference.setGenerateGetter(true);
				modelReference.setGenerateSetter(true);
				modelReference.setGenerateAddMethod(true);
				modelReference.setGenerateRemoveMethod(true);
				modelReference.setGenerateClearMethod(true);
				modelClass.addInstanceReference(modelReference);
				
				String qualifiedName = packageName + "." + className;
				stateReferenceMap.put(qualifiedName, modelReference);
				continue;
			}
		}
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Process process) throws Exception {
		modelClass.addInstanceOperation(createOperation_GetDomainId(process));
		modelClass.addInstanceOperation(createOperation_GetConversationId(process));
		modelClass.addInstanceOperation(createOperation_Initialize(process));
		modelClass.addInstanceOperation(createOperation_Finalize(process));
		if (process.getTransacted() != null) {
			//modelClass.addInstanceOperations(createOperations_GetStateManager(process));
			//modelClass.addInstanceOperation(createOperation_UpdateState(process));
		}
		modelClass.addInstanceOperations(createOperations_GetDestination(modelClass, process));
		modelClass.addInstanceOperations(createOperations_AddDestination(modelClass, process));
		//modelClass.addInstanceOperations(createOperations_CreateState(modelClass, process));
		modelClass.addInstanceOperations(createOperations_CreateMessage(modelClass, process));
		modelClass.addInstanceOperations(createOperations_InitializeContext(modelClass, process));
		modelClass.addInstanceOperations(createOperations_ValidateMessage(modelClass, process));
		modelClass.addInstanceOperations(createOperations_ServiceCompletion(modelClass, process));

//		List<Operation> operations = process.getOperations();
//		Iterator<Operation> iterator = operations.iterator();
//		while (iterator.hasNext()) {
//			modelClass.clearLocalVariables();
//			Operation operation = iterator.next();
//			context.setOperation(operation);
//			modelOperationFactory.reset(modelClass);
//			//modelClass.getInstanceOperations().add(createServiceHandlerOperation(modelClass, operation));
//			context.setOperation(null);
//		}
//		
//		iterator = operations.iterator();
//		while (iterator.hasNext()) {
//			Operation operation = iterator.next();
//			modelClass.getInstanceOperations().addAll(createOperations_ServiceCompletion(modelClass, operation));
//		}
	}

	protected ModelOperation createOperation_GetDomainId(Process process) throws Exception {
		String domainName = ProjectLevelHelper.getPackageName(process.getNamespace());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getDomainId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("java.lang.String");
		modelOperation.addInitialSource(CodeUtil.createMethodSource("return \""+domainName+"\";\n"));
		return modelOperation;
	}
	
	protected ModelOperation createOperation_GetConversationId(Process process) throws Exception {
		String conversationId = ProjectLevelHelper.getPackageName(process.getNamespace()) + "." + process.getName();
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getConversationId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("java.lang.String");
		modelOperation.addInitialSource(CodeUtil.createMethodSource("return \""+conversationId+"\";\n"));
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Initialize(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("initialize");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("registerWithJMX();");

		Set<Module> modules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module projectModule = moduleIterator.next();
			String namespaceUri = projectModule.getNamespace();
			String localPart = NameUtil.getLocalPartFromNamespace(namespaceUri);
			String packageName = ProjectLevelHelper.getPackageName(namespaceUri);
			buf.putLine2(localPart+"ObjectFactory = new "+packageName+".ObjectFactory();");
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Finalize(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createPreDestroyAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("close");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("unregisterWithJMX();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperations_GetDestination(ModelClass modelClass, Process process) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			modelClass.clearLocalVariables();
			Operation operation = iterator.next();
			context.setOperation(operation);
//			if (operation.getName().contains("purchaseBooks"))
//				System.out.println();
			modelOperations.addAll(createOperations_GetDestination(modelClass, process, operation));
		}
		return modelOperations;
	}
	
	protected Collection<ModelOperation> createOperations_GetDestination(ModelClass modelClass, Process process, Operation operation) {
		Collection<Command> commands = OperationUtil.getCommands(operation);
		Collection<String> returnMessages = getReturnedMessagesFromCommands(commands);
		List<ModelOperation> modelOperations = createOperations_GetDestination(modelClass, process, operation.getName(), returnMessages);
		return modelOperations;
	}

	protected Collection<String> getReturnedMessagesFromCommands(Collection<Command> commands) {
		Set<String> returnedMessages = new HashSet<String>();
		
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			if (command instanceof SendCommand) {
				SendCommand sendCommand = (SendCommand) command;
				returnedMessages.addAll(sendCommand.getReturnMessages());
				
			} else if (command instanceof InvokeCommand) {
				InvokeCommand invokeCommand = (InvokeCommand) command;
				returnedMessages.addAll(invokeCommand.getReturnMessages());
				
			/*
			if (command instanceof ReplyCommand) {
				ReplyCommand replyCommand = (ReplyCommand) command;
				modelOperations.addAll(createOperations_GetDestination(modelClass, process, replyCommand));
			}
			*/
				
			} else if (command instanceof IfStatement) {
				IfStatement ifStatement = (IfStatement) command;
				returnedMessages.addAll(getReturnedMessagesFromCommands(ifStatement.getCommands()));
				returnedMessages.addAll(getReturnedMessagesFromCommands(ifStatement.getElseIfCommands()));
				returnedMessages.addAll(getReturnedMessagesFromCommands(ifStatement.getElseCommands()));
				
			} else if (command.getType().equals("unnamed")) {
				returnedMessages.addAll(getReturnedMessagesFromCommands(command.getCommands()));
			}
		}
		
		return returnedMessages;
	}
	
	protected List<ModelOperation> createOperations_GetDestination(ModelClass modelClass, Process process, String serviceName, Collection<String> messageNames) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();

		Application application = context.getApplication();
		Service serviceInstance = ApplicationUtil.getServiceByName(application, serviceName);
		if (serviceInstance == null)
			serviceInstance = ApplicationUtil.getIncomingCallbackByName(application, serviceName);
		
		Iterator<String> iterator = messageNames.iterator();
		while (iterator.hasNext()) {
			String messageName = iterator.next();
			String callbackName = messageName.substring(0, messageName.length()-7);
			
			//List<Project> projects = context.getProjects();
			//Service serviceInstance = ApplicationUtil.getServiceByName(application, serviceName);
			//Service serviceInstance = ProjectUtil.getServiceByName(projects, serviceName);
			//Callback callbackInstance = ApplicationUtil.getOutgoingCallbackByName(application, serviceName);
			Callback callbackInstance = ApplicationUtil.getIncomingCallbackByName(application, callbackName);
			//Callback callbackInstance = ProjectUtil.getIncomingCallbackByName(projects, messageName);
			
			modelOperations.addAll(createOperations_GetDestination(modelClass, serviceInstance, callbackInstance));
		}
		return modelOperations;
	}

	//NOTUSED
	protected List<ModelOperation> createOperations_GetDestination(ModelClass modelClass, Process process, ReplyCommand replyCommand) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Interactor interactor = (Interactor) replyCommand.getActor();
		String serviceName = NameUtil.capName(interactor.getService());
		String serviceNameCapped = NameUtil.capName(interactor.getService());
		String messageName = replyCommand.getMessageName();

		Application application = context.getApplication();
		//List<Project> projects = context.getProjects();
		Service serviceInstance = ApplicationUtil.getServiceByName(application, serviceName);
		//Service serviceInstance = ProjectUtil.getServiceByName(projects, serviceName);
		//Callback callbackInstance = ApplicationUtil.getOutgoingCallbackByName(application, messageName);
		Callback callbackInstance = ApplicationUtil.getIncomingCallbackByName(application, messageName);
		//Callback callbackInstance = ProjectUtil.getIncomingCallbackByName(projects, messageName);
		
		modelOperations.addAll(createOperations_GetDestination(modelClass, serviceInstance, callbackInstance));
		return modelOperations;
	}
	
	protected List<ModelOperation> createOperations_GetDestination(ModelClass modelClass, Service serviceForCaller, Callback serviceForMessage) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		String interfaceName = ServiceUtil.getExtendedDomainServiceName(serviceForMessage);
		
		Project project = context.getProject();
		Map<String, JMSDestination> jmsDestinations = JMSUtil.buildJMSDestinations(project, serviceForCaller, serviceForMessage);
		Iterator<JMSDestination> iterator = jmsDestinations.values().iterator();
		while (iterator.hasNext()) {
			JMSDestination jmsDestination = iterator.next();
			ModelOperation modelOperation = new ModelOperation();
			modelOperation.setModifiers(Modifier.PUBLIC);
			//
			// TODO 
			// Adjust destination name accordingly here - 
			// Do this when we start to have multiple JMS destinations become available per callback.
			// Now we just assume only one destination for each callback.
			//
			modelOperation.setName("getDestination_"+interfaceName+"");
			modelOperation.setResultType("String");
			
			Buf buf = new Buf();
			String destinationName = JMSUtil.getDestinationName(jmsDestination);
			buf.putLine2("return \""+destinationName+"_queue\";"); 
			modelOperation.addInitialSource(buf.get());
			modelOperations.add(modelOperation);
		}
		
		return modelOperations;
	}
	
	
	protected List<ModelOperation> createOperations_AddDestination(ModelClass modelClass, Process process) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			modelClass.clearLocalVariables();
			Operation operation = iterator.next();
			context.setOperation(operation);
			modelOperations.addAll(createOperations_AddDestination(modelClass, process, operation));
		}
		return modelOperations;
	}
	
	protected List<ModelOperation> createOperations_AddDestination(ModelClass modelClass, Process process, Operation operation) {
		List<Command> commands = operation.getCommands();
		Collection<String> returnMessages = getReturnedMessagesFromCommands(commands);
		List<ModelOperation> modelOperations = createOperations_AddDestination(modelClass, process, operation.getName(), returnMessages);
		return modelOperations;
	}

	private List<ModelOperation> createOperations_AddDestination(ModelClass modelClass, Process process, String serviceName, Collection<String> messageNames) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();

		Application application = context.getApplication();
		Service serviceInstance = ApplicationUtil.getServiceByName(application, serviceName);
		if (serviceInstance == null)
			serviceInstance = ApplicationUtil.getIncomingCallbackByName(application, serviceName);
		
		Iterator<String> iterator = messageNames.iterator();
		while (iterator.hasNext()) {
			String messageName = iterator.next();
			String callbackName = messageName.substring(0, messageName.length()-7);
			
			//List<Project> projects = context.getProjects();
			//Service serviceInstance = ApplicationUtil.getServiceByName(application, serviceName);
			//Service serviceInstance = ProjectUtil.getServiceByName(projects, serviceName);
			//Callback callbackInstance = ApplicationUtil.getOutgoingCallbackByName(application, serviceName);
			Callback callbackInstance = ApplicationUtil.getIncomingCallbackByName(application, callbackName);
			//Callback callbackInstance = ProjectUtil.getIncomingCallbackByName(projects, messageName);
			
			modelOperations.addAll(createOperations_AddDestination(modelClass, serviceInstance, callbackInstance));
		}
		return modelOperations;
	}

	//NOTUSED
	protected List<ModelOperation> createOperations_AddDestination(ModelClass modelClass, Process process, ReplyCommand replyCommand) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Interactor interactor = (Interactor) replyCommand.getActor();
		String serviceName = NameUtil.capName(interactor.getService());
		String serviceNameCapped = NameUtil.capName(interactor.getService());
		String messageName = replyCommand.getMessageName();

		Application application = context.getApplication();
		//List<Project> projects = context.getProjects();
		Service serviceInstance = ApplicationUtil.getServiceByName(application, serviceName);
		//Service serviceInstance = ProjectUtil.getServiceByName(projects, serviceName);
		Callback callbackInstance = ApplicationUtil.getIncomingCallbackByName(application, messageName);
		//Callback callbackInstance = ProjectUtil.getIncomingCallbackByName(projects, messageName);
		
		modelOperations.addAll(createOperations_AddDestination(modelClass, serviceInstance, callbackInstance));
		return modelOperations;
	}
	
	protected List<ModelOperation> createOperations_AddDestination(ModelClass modelClass, Service serviceForCaller, Callback serviceForMessage) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		String extendedName = ServiceUtil.getExtendedDomainServiceName(serviceForMessage);
		String interfaceName = serviceForMessage.getInterfaceName();
		
		Project project = context.getProject();
		Map<String, JMSDestination> jmsDestinations = JMSUtil.buildJMSDestinations(project, serviceForCaller, serviceForMessage);
		Iterator<JMSDestination> iterator = jmsDestinations.values().iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unused")
			JMSDestination jmsDestination = iterator.next();

			ModelOperation modelOperation = new ModelOperation();
			modelOperation.setModifiers(Modifier.PUBLIC);
			modelOperation.setName("addReplyTo_"+extendedName+"");
			modelOperation.setResultType("void");
			
			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setName("message");
			modelParameter.setClassName("AbstractMessage");
			modelParameter.setPackageName("org.aries.common");
			modelClass.addImportedClass("org.aries.common.AbstractMessage");
			modelOperation.addParameter(modelParameter);
			modelOperations.add(modelOperation);
			
			Buf buf = new Buf();
			String messageBeanName = "message";
			//buf.putLine2("message.addReplyToDestination("+interfaceName+".ID, get"+interfaceName+"Destination());");
			buf.putLine2(messageBeanName +".addToReplyToDestinations(\""+interfaceName+"\", getDestination_"+extendedName+"());");
			modelOperation.addInitialSource(buf.get());
			modelOperations.add(modelOperation);
		}
		
		return modelOperations;
	}
	

	//@SuppressWarnings("unchecked")
	protected List<ModelOperation> createOperations_CreateState(ModelClass modelClass, Process process) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Application application = context.getApplication();

		Iterator<ModelReference> iterator = stateReferenceMap.values().iterator();
		while (iterator.hasNext()) {
			ModelReference modelReference = iterator.next();
			modelOperations.add(createOperation_CreateState(modelClass, modelReference));
		}
		
		//Collections.sort(modelOperations);
		return modelOperations;
	}
	
	protected ModelOperation createOperation_CreateState(ModelClass modelClass, ModelReference modelReference) throws Exception {
		String referenceType = modelReference.getType();
		String className = TypeUtil.getClassName(referenceType);
		String packageName = TypeUtil.getPackageName(referenceType);
		String beanName = NameUtil.uncapName(className);
		String referenceName = NameUtil.capName(className);
		modelClass.addImportedClass(packageName+"."+className);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("create"+className);
		modelOperation.setResultType(className);

		Buf buf = new Buf();
		buf.putLine2(className+" "+beanName+" = new "+className+"();");
		
		Element element = context.getElementByType(referenceType);
		Assert.notNull(element, "Element not found: "+className);
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			
			buf.putLine2(beanName+".set"+fieldNameCapped+"(new "+fieldClassName+"());");
		}
		buf.putLine2("return "+beanName);
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	//@SuppressWarnings("unchecked")
	protected List<ModelOperation> createOperations_CreateMessage(ModelClass modelClass, Process process) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Application application = context.getApplication();

		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			modelClass.clearLocalVariables();
			Operation operation = iterator.next();
			context.setOperation(operation);

			String serviceId = operation.getName();
			//TODO exclude callback messages here is serviceId is for a callback
			modelOperations.addAll(createOperations_CreateMessage(modelClass, process, operation));
			
			String serviceInterface = NameUtil.capName(serviceId);
			Service service = ApplicationUtil.getServiceByName(application, serviceInterface);
			if (service != null) {
				modelOperations.addAll(createOperations_CreateMessage(modelClass, process, service));
			}
			
			modelOperations.addAll(createOperations_CreateMessage(modelClass, process, operation.getCommands()));
		}
		
		//Collections.sort(modelOperations);
		return modelOperations;
	}

	protected List<ModelOperation> createOperations_CreateMessage(ModelClass modelClass, Process process, Operation operation) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterType = parameter.getType();

			Element element = context.getElementByType(parameterType);
			if (element != null) {
				String messageClass = TypeUtil.getClassName(element.getType());
				if (!messageCreationOperationsDone.contains(messageClass)) {
					messageCreationOperationsDone.add(messageClass);
					modelOperations.add(createOperation_CreateMessage(modelClass, process, operation, element));
				}
			}
		}
		//get outgoing service request operations
		Set<Service> remoteServices = context.getRemoteServices(operation);
		Iterator<Service> remoteServiceIterator = remoteServices.iterator();
		while (remoteServiceIterator.hasNext()) {
			Service remoteService = remoteServiceIterator.next();
			List<Operation> operations = ServiceUtil.getOperations(remoteService);
			Iterator<Operation> iterator2 = operations.iterator();
			while (iterator2.hasNext()) {
				Operation operation2 = iterator2.next();
				modelOperations.addAll(createOperations_CreateMessage(modelClass, process, operation2));
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_CreateMessage(ModelClass modelClass, Process process, Operation operation, Element element) throws Exception {
		String elementName = NameUtil.capName(element.getName());
		String className = TypeUtil.getClassName(element.getType());
		String packageName = TypeUtil.getPackageName(element.getType());
		modelClass.addImportedClass(packageName+"."+className);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("create"+elementName);
		modelOperation.setResultType(className);

//		ModelParameter modelParameter = new ModelParameter();
//		modelParameter.setName("serviceId");
//		modelParameter.setClassName("String");
//		modelParameter.setPackageName("java.lang");
//		modelOperation.addParameter(modelParameter);
		
		String serviceName = NameUtil.capName(operation.getName());
		String sourceCode = getSourceForNewMessage(2, serviceName, element);
		modelOperation.addInitialSource(sourceCode);
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperations_CreateMessage(ModelClass modelClass, Process process, List<Command> commands) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();

			Interactor interactor = null;
			if (command instanceof InvokeCommand) {
				interactor = (Interactor) command.getActor();
			} else if (command instanceof SendCommand) {
				interactor = (Interactor) command.getActor();
			}
			
			if (interactor != null) {
				String serviceName = interactor.getService();
				Service service = context.getServiceByName(serviceName);
				Operation operation = ServiceUtil.getOperation(service, serviceName);
				modelOperations.addAll(createOperations_CreateMessage(modelClass, process, operation));
			}
		}
		return modelOperations;
	}

	protected List<ModelOperation> createOperations_CreateMessage(ModelClass modelClass, Process process, Service service) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Callback> outgoingCallbacks = ServiceUtil.getOutgoingCallbacks(service);
		Iterator<Callback> iterator = outgoingCallbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			modelOperations.addAll(createOperations_CreateMessage(modelClass, process, callback));
		}
		return modelOperations;
	}

	protected List<ModelOperation> createOperations_CreateMessage(ModelClass modelClass, Process process, Callback callback) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Operation> operations = ServiceUtil.getOperations(callback);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			context.setOperation(operation);
			modelOperations.addAll(createOperations_CreateMessage(modelClass, process, operation));
		}
		return modelOperations;
	}
	
	
	protected String getSourceForNewMessage(int indent, String serviceName, Element messageElement) {
		String elementName = messageElement.getName();
		String elementType = messageElement.getType();

		String messageBeanName = "message";
		//String messageBeanName = NameUtil.uncapName(elementName);
		String messageClassName = TypeUtil.getClassName(elementType);
		String messagePackageName = ModelLayerHelper.getElementPackageName(messageElement);
		String messageServiceName = messageClassName.replace("Message", "");
		String factoryPrefix = NameUtil.getSimpleName(messagePackageName);

		Buf buf = new Buf();
		buf.putLine(indent, messageClassName+" "+messageBeanName+" = "+factoryPrefix+"ObjectFactory.create"+messageClassName+"();");
		
		Application application = context.getApplication();
		Service serviceForMessage = ApplicationUtil.getIncomingCallbackByName(application, messageServiceName);
		Service serviceForCaller = ApplicationUtil.getServiceByName(application, serviceName);
		//Assert.notNull(serviceForCaller, "Service for caller not found: "+serviceName);

//		if (serviceForMessage != null)
//			buf.putLine(indent, "initializeMessage(\""+messageServiceName+"\", "+messageBeanName+");");
//		else buf.putLine(indent, "initializeMessage(getConversationId(), "+messageBeanName+");");
		
		buf.putLine(indent, "initializeMessage(getConversationId(), "+messageBeanName+");");

		/*
		//TODO
		if (serviceForMessage == null) {
			Assert.notNull(serviceForCaller, "Service for caller not found: "+serviceName);
			List<Callback> incomingCallbacks = ServiceUtil.getIncomingCallbacks(serviceForCaller);
			Iterator<Callback> callbackIterator = incomingCallbacks.iterator();
			while (callbackIterator.hasNext()) {
				Callback callback = callbackIterator.next();
				String callbackName = callback.getName();
				String callbackNameCapped = NameUtil.capName(callbackName);
				buf.putLine(indent, messageBeanName+".addReplyToDestinations(\""+callbackName+"\", get"+callbackNameCapped+"Destination());");
			}
		}
		*/
		
		List<Field> fields = ElementUtil.getApplicationDefinedFields(messageElement);
		Iterator<Field> fieldIterator = fields.iterator();
		while (fieldIterator.hasNext()) {
			Field field = fieldIterator.next();
			String beanName = field.getName();
			String fieldType = field.getType();
			String fieldName = NameUtil.capName(beanName);
			Element fieldByType = context.getElementByType(fieldType);

			buf.putLine2(""+messageBeanName+".set"+fieldName+"("+beanName+");");

			if (fieldByType != null) {
			} else if (CodeGenUtil.isJavaDefaultType(fieldType) || CodeGenUtil.isJavaLangType(fieldType) || CodeGenUtil.isJavaPrimitiveType(fieldType)) {
			}
		}
		
		buf.putLine2("return "+messageBeanName+";");
		return buf.get();
	}
	
	
	
	protected List<ModelOperation> createOperations_InitializeContext(ModelClass modelClass, Process process) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			modelClass.clearLocalVariables();
			Operation operation = iterator.next();
			context.setOperation(operation);
			modelOperations.addAll(createOperations_InitializeContext(modelClass, process, operation));
		}
		return modelOperations;
	}
	
	protected List<ModelOperation> createOperations_InitializeContext(ModelClass modelClass, Process process, Operation operation) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterType = parameter.getType();

			Element element = context.getElementByType(parameterType);
			if (element != null) {
				modelOperations.addAll(createOperations_InitializeContext(modelClass, process, operation, element));
			}
		}
		return modelOperations;
	}
	
	protected List<ModelOperation> createOperations_InitializeContext(ModelClass modelClass, Process process, Operation operation, Element element) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();

		String messageBeanName = NameUtil.uncapName(element.getName());
		String messageElementName = NameUtil.capName(element.getName());
		String messageClassName = TypeUtil.getClassName(element.getType());
		String messagePackageName = TypeUtil.getPackageName(element.getType());
		modelClass.addImportedClass(messagePackageName+"."+messageClassName);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initializeContext");
		modelOperation.setResultType("void");

//		ModelParameter modelParameter = new ModelParameter();
//		modelParameter.setName("serviceId");
//		modelParameter.setClassName("String");
//		modelParameter.setPackageName("java.lang");
//		modelOperation.addParameter(modelParameter);
		
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(messageBeanName);
		modelParameter.setClassName(messageClassName);
		modelParameter.setPackageName(messagePackageName);
		modelOperation.addParameter(modelParameter);

		Application application = context.getApplication();
		String serviceName = NameUtil.capName(operation.getName());
		Service serviceForMessage = ApplicationUtil.getIncomingCallbackByName(application, serviceName);
		Service serviceForCaller = ApplicationUtil.getServiceByName(application, serviceName);
		//Assert.notNull(serviceForCaller, "Service for caller not found: "+serviceName);
		
		Buf buf = new Buf();
		if (serviceForMessage != null)
			buf.putLine2("super.initializeContext(\""+serviceName+"\", "+messageBeanName+");");
		else buf.putLine2("super.initializeContext(getConversationId(), "+messageBeanName+");");
		
		//TODO - add validation at field level
		List<Field> fields = ElementUtil.getApplicationDefinedFields(element);
		Iterator<Field> fieldIterator = fields.iterator();
		while (fieldIterator.hasNext()) {
			Field field = fieldIterator.next();
			String beanName = field.getName();
			//String fieldType = field.getType();
			String fieldName = NameUtil.capName(beanName);
			//String className = TypeUtil.getClassName(fieldType);
			//String packageName = TypeUtil.getPackageName(fieldType);
			//Element fieldElement = context.getElementByType(fieldType);
			buf.putLine2("set"+fieldName+"("+messageBeanName+".get"+fieldName+"());");
		}
		
		modelOperation.addInitialSource(buf.get());
		modelOperations.add(modelOperation);
		return modelOperations;
	}

	
	
	protected List<ModelOperation> createOperations_ValidateMessage(ModelClass modelClass, Process process) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			modelClass.clearLocalVariables();
			Operation operation = iterator.next();
			context.setOperation(operation);
			modelOperations.addAll(createOperations_ValidateMessage(modelClass, process, operation));
		}
		return modelOperations;
	}
	
	protected List<ModelOperation> createOperations_ValidateMessage(ModelClass modelClass, Process process, Operation operation) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterType = parameter.getType();

			Element element = context.getElementByType(parameterType);
			if (element != null) {
				modelOperations.addAll(createOperation_Validation(modelClass, process, element));
			}
		}
		return modelOperations;
	}
	
	protected List<ModelOperation> createOperation_Validation(ModelClass modelClass, Process process, Element element) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();

		String beanName = NameUtil.uncapName(element.getName());
		String elementName = NameUtil.capName(element.getName());
		String className = TypeUtil.getClassName(element.getType());
		String packageName = TypeUtil.getPackageName(element.getType());
		modelClass.addImportedClass(packageName+"."+className);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("validate");
		modelOperation.setResultType("void");

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(beanName);
		modelParameter.setClassName(className);
		modelParameter.setPackageName(packageName);
		modelOperation.addParameter(modelParameter);
		modelOperations.add(modelOperation);

		Buf buf = new Buf();
		buf.putLine2("validate(\""+beanName+"\", "+beanName+");");
		buf.putLine2("validateMessage("+beanName+");");
		modelOperation.addInitialSource(buf.get());;
		
		//TODO - add validation at field level
		List<Field> fields = ElementUtil.getApplicationDefinedFields(element);
		Iterator<Field> fieldIterator = fields.iterator();
		while (fieldIterator.hasNext()) {
			Field field = fieldIterator.next();
			//String beanName = field.getName();
			//String fieldType = field.getType();
			//String fieldName = NameUtil.capName(beanName);
			//String className = TypeUtil.getClassName(fieldType);
			//String packageName = TypeUtil.getPackageName(fieldType);
			//Element fieldElement = context.getElementByType(fieldType);
		}
		
		return modelOperations;
	}

	

	protected List<ModelOperation> createOperations_ServiceCompletion(ModelClass modelClass, Process process) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();

		Application application = context.getApplication();
		String applicationName = application.getName();
		
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			modelClass.clearLocalVariables();
			Operation operation = iterator.next();
			context.setOperation(operation);

			//first get each incoming service
			String serviceId = operation.getName();
			Service service = context.getServiceByName(applicationName, serviceId);
			if (service != null) {
				String domainName = ServiceUtil.getNamespaceAlias(service);
				String serviceName = ServiceUtil.getServiceName(service);
				modelOperations.add(createOperation_FireNotification_IncomingRequestReceived(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestHandled(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestComplete(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestCancelDone(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestUndoDone(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestAborted_Reason(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestAborted_Cause(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_ErrorSent(domainName, serviceName));

				//get outgoing callback operations
				List<Callback> outgoingCallbacks = ServiceUtil.getOutgoingCallbacks(service);
				Iterator<Callback> callbackIterator = outgoingCallbacks.iterator();
				while (callbackIterator.hasNext()) {
					Callback callback = callbackIterator.next();
					//String callbackName = NameUtil.capName(callback.getName()); 
					String callbackDomain = ServiceUtil.getNamespaceAlias(callback);
					String callbackName = ServiceUtil.getServiceName(callback);
					String callbackKey = "outgoing" + callbackName;
					
					if (!serviceCompletionOperationsDone.contains(callbackKey)) {
						serviceCompletionOperationsDone.add(callbackKey);
						modelOperations.add(createOperation_FireNotification_OutgoingResponseSent(callbackDomain, callbackName));
						modelOperations.add(createOperation_FireNotification_OutgoingResponseAborted_Reason(callbackDomain, callbackName));
						modelOperations.add(createOperation_FireNotification_OutgoingResponseAborted_Cause(callbackDomain, callbackName));
					}
				}
			}
			
//			if (operation.getName().contains("purchaseBooks"))
//				System.out.println();
			
			//get outgoing service request operations
			Set<Service> remoteServices = context.getRemoteServices(operation);
			Iterator<Service> remoteServiceIterator = remoteServices.iterator();
			while (remoteServiceIterator.hasNext()) {
				Service remoteService = remoteServiceIterator.next();
				String remoteServiceDomain = ServiceUtil.getNamespaceAlias(remoteService);
				String remoteServiceName = ServiceUtil.getServiceName(remoteService);
				String remoteServiceKey = "outgoing" + remoteServiceName;
				
				if (!serviceCompletionOperationsDone.contains(remoteServiceKey)) {
					serviceCompletionOperationsDone.add(remoteServiceKey);
					modelOperations.add(createOperation_FireNotification_OutgoingRequestSent(remoteServiceDomain, remoteServiceName));
					modelOperations.add(createOperation_FireNotification_OutgoingRequestAborted_Reason(remoteServiceDomain, remoteServiceName));
					modelOperations.add(createOperation_FireNotification_OutgoingRequestAborted_Cause(remoteServiceDomain, remoteServiceName));
					modelOperations.add(createOperation_FireNotification_ErrorReceived(remoteServiceDomain, remoteServiceName));
				}
				
				//get incoming callback operations from this remote service
				List<Callback> outgoingCallbacks = ServiceUtil.getOutgoingCallbacks(remoteService);
				Iterator<Callback> callbackIterator = outgoingCallbacks.iterator();
				while (callbackIterator.hasNext()) {
					Callback callback = callbackIterator.next();
					String callbackDomain = ServiceUtil.getNamespaceAlias(callback);
					//String callbackName = NameUtil.capName(callback.getName()); 
					String callbackName = ServiceUtil.getServiceName(callback);
					String callbackKey = "incoming" + callbackName;
					
					if (!serviceCompletionOperationsDone.contains(callbackKey)) {
						serviceCompletionOperationsDone.add(callbackKey);
						modelOperations.add(createOperation_FireNotification_IncomingResponseReceived(callbackDomain, callbackName));
						modelOperations.add(createOperation_FireNotification_IncomingResponseHandled(callbackDomain, callbackName));
						modelOperations.add(createOperation_FireNotification_IncomingResponseComplete(callbackDomain, callbackName));
						modelOperations.add(createOperation_FireNotification_IncomingResponseAborted_Reason(callbackDomain, callbackName));
						modelOperations.add(createOperation_FireNotification_IncomingResponseAborted_Cause(callbackDomain, callbackName));
					}
				}
			}
			
//			if (service != null) {
//				//get incoming callback operations
//				List<Callback> incomingCallbacks = ServiceUtil.getIncomingCallbacks(service);
//				Iterator<Callback> callbackIterator = incomingCallbacks.iterator();
//				while (callbackIterator.hasNext()) {
//					Callback callback = callbackIterator.next();
//					String callbackName = NameUtil.capName(callback.getName()); 
//					String callbackKey = "incoming" + callbackName;
//					
//					if (!serviceCompletionOperationsDone.contains(callbackKey)) {
//						serviceCompletionOperationsDone.add(callbackKey);
//						modelOperations.add(createOperation_FireNotification_IncomingResponseReceived(callbackName));
//						modelOperations.add(createOperation_FireNotification_IncomingResponseHandled(callbackName));
//						modelOperations.add(createOperation_FireNotification_IncomingResponseComplete(callbackName));
//						modelOperations.add(createOperation_FireNotification_IncomingResponseAborted_Reason(callbackName));
//						modelOperations.add(createOperation_FireNotification_IncomingResponseAborted_Cause(callbackName));
//					}
//				}
//			}
		}

//		//get service completion operations
//		iterator = operations.iterator();
//		while (iterator.hasNext()) {
//			modelClass.clearLocalVariables();
//			Operation operation = iterator.next();
//			context.setOperation(operation);
//
//			String serviceId = operation.getName();
//			String serviceInterface = NameUtil.capName(serviceId);
//			Service service = ApplicationUtil.getServiceByName(application, serviceInterface);
//			if (service != null) {
//				modelOperations.addAll(createOperations_ServiceCompletion(modelClass, process, service));
//			}
//		}

		String domainName = ProcessUtil.getNamespaceAlias(process);
		Set<String> outgoingEvents = ProcessUtil.getOutgoingEvents(process);
		Iterator<String> iterator3 = outgoingEvents.iterator();
		while (iterator3.hasNext()) {
			String eventName = iterator3.next();
			eventName = NameUtil.capName(eventName);
			modelOperations.add(createOperation_FireNotification_EventPosted(domainName, eventName));
		}
		
		Set<String> incomingEvents = ProcessUtil.getIncomingEvents(process);
		Iterator<String> iterator2 = incomingEvents.iterator();
		while (iterator2.hasNext()) {
			String eventName = iterator2.next();
			eventName = NameUtil.capName(eventName);
			modelOperations.add(createOperation_FireNotification_EventReceived(domainName, eventName));
			modelOperations.add(createOperation_FireNotification_ProcessComplete(domainName, eventName));
			modelOperations.add(createOperation_FireNotification_ProcessAborted_Reason(domainName, eventName));
			modelOperations.add(createOperation_FireNotification_ProcessAborted_Cause(domainName, eventName));
		}

		return modelOperations;
	}

//	protected List<ModelOperation> createOperations_ServiceCompletion(ModelClass modelClass, Process process, Service service) throws Exception {
//		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
//		List<Callback> outgoingCallbacks = ServiceUtil.getOutgoingCallbacks(service);
//		Iterator<Callback> iterator = outgoingCallbacks.iterator();
//		while (iterator.hasNext()) {
//			Callback callback = iterator.next();
//			modelOperations.addAll(createOperations_ServiceCompletion(modelClass, process, callback));
//		}
//		return modelOperations;
//	}

//	protected List<ModelOperation> createOperations_ServiceCompletion(ModelClass modelClass, Process process, Callback callback) throws Exception {
//		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
//		List<Operation> operations = ServiceUtil.getOperations(callback);
//		Iterator<Operation> iterator = operations.iterator();
//		while (iterator.hasNext()) {
//			Operation operation = iterator.next();
//			
//			String callbackId = operation.getName();
//			if (!serviceCompletionOperationsDone.contains(callbackId)) {
//				serviceCompletionOperationsDone.add(callbackId);
//				context.setOperation(operation);
//
//				String serviceName = NameUtil.capName(operation.getName());
//				modelOperations.add(createOperation_FireNotification_IncomingResponseReceived(serviceName));
//				modelOperations.add(createOperation_FireNotification_IncomingResponseAborted_Reason(serviceName));
//				modelOperations.add(createOperation_FireNotification_IncomingResponseAborted_Cause(serviceName));
//			}
//		}
//		return modelOperations;
//	}
	
	protected ModelOperation createOperation_FireNotification_IncomingRequestReceived(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Request_Received");
	}

	protected ModelOperation createOperation_FireNotification_IncomingRequestHandled(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Request_Handled");
	}

	protected ModelOperation createOperation_FireNotification_IncomingRequestComplete(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Request_Done");
	}

	protected ModelOperation createOperation_FireNotification_IncomingRequestCancelDone(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Request_Cancel_Done");
	}

	protected ModelOperation createOperation_FireNotification_IncomingRequestUndoDone(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Request_Undo_Done");
	}

	protected ModelOperation createOperation_FireNotification_IncomingRequestAborted_Cause(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Incoming_Request_Aborted", "String", "reason");
	}

	protected ModelOperation createOperation_FireNotification_IncomingRequestAborted_Reason(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Incoming_Request_Aborted", "Throwable", "cause");
	}

	protected ModelOperation createOperation_FireNotification_OutgoingRequestSent(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Request_Sent");
	}

	protected ModelOperation createOperation_FireNotification_OutgoingRequestAborted_Cause(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Outgoing_Request_Aborted", "String", "reason");
	}

	protected ModelOperation createOperation_FireNotification_OutgoingRequestAborted_Reason(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Outgoing_Request_Aborted", "Throwable", "cause");
	}

	protected ModelOperation createOperation_FireNotification_IncomingResponseReceived(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Response_Received");
	}

	protected ModelOperation createOperation_FireNotification_IncomingResponseHandled(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Response_Handled");
	}

	protected ModelOperation createOperation_FireNotification_IncomingResponseComplete(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Response_Done");
	}

	protected ModelOperation createOperation_FireNotification_IncomingResponseAborted_Cause(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Incoming_Response_Aborted", "String", "reason");
	}

	protected ModelOperation createOperation_FireNotification_IncomingResponseAborted_Reason(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Incoming_Response_Aborted", "Throwable", "cause");
	}

	protected ModelOperation createOperation_FireNotification_OutgoingResponseSent(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Response_Sent");
	}

	protected ModelOperation createOperation_FireNotification_OutgoingResponseAborted_Cause(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Outgoing_Response_Aborted", "String", "reason");
	}

	protected ModelOperation createOperation_FireNotification_OutgoingResponseAborted_Reason(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Outgoing_Response_Aborted", "Throwable", "cause");
	}

	protected ModelOperation createOperation_FireNotification_EventPosted(String domainName, String processName) {
		return createOperation_FireNotification(domainName, processName, "Event_Posted");
	}

	protected ModelOperation createOperation_FireNotification_EventReceived(String domainName, String processName) {
		return createOperation_FireNotification(domainName, processName, "Event_Received");
	}

	protected ModelOperation createOperation_FireNotification_ProcessComplete(String domainName, String processName) {
		return createOperation_FireNotification(domainName, processName, "Process_Complete");
	}

	protected ModelOperation createOperation_FireNotification_ProcessAborted_Cause(String domainName, String processName) {
		return createOperation_FireNotification(domainName, processName, "Process_Aborted", "String", "reason");
	}

	protected ModelOperation createOperation_FireNotification_ProcessAborted_Reason(String domainName, String processName) {
		return createOperation_FireNotification(domainName, processName, "Process_Aborted", "Throwable", "cause");
	}
	
	protected ModelOperation createOperation_FireNotification_ErrorSent(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Error_Sent");
	}

	protected ModelOperation createOperation_FireNotification_ErrorReceived(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Error_Received");
	}

	protected ModelOperation createOperation_FireNotification(String domainName, String notificationName, String notificationGroup) {
		String operationName = "fire_" + notificationName + "_" + notificationGroup.toLowerCase();
		notificationGroup = NameUtil.capName(notificationGroup);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

//		String packageName = null;
//		Application application = context.getApplication();
//		Service service = ApplicationUtil.getServiceByName(application, notificationName);
//		Callback callback = ApplicationUtil.getOutgoingCallbackByName(application, notificationName);
//		if (service != null) packageName = service.getPackageName();
//		if (callback != null) packageName = callback.getPackageName();
//		if (packageName != null)
//			modelClass.addImportedClass(packageName+"."+notificationName);

		Application application = context.getApplication();
		String applicationName = application.getName();
		String objectName = notificationName;
		int indexOfDot = objectName.indexOf(".");
		if (indexOfDot != -1)
			objectName = notificationName.substring(0, indexOfDot);
		
		Buf buf = new Buf();
		//buf.putLine2("log.info(\"**** ["+applicationName+"]: "+objectName+" replied\");");
		buf.putLine2("fireServiceCompleted(\""+domainName+"_"+notificationName+"_"+notificationGroup+"\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_FireNotification(String domainName, String notificationName, String notificationGroup, String parameterType, String parameterName) {
		String operationName = "fire_"+notificationName+"_"+notificationGroup.toLowerCase();
		notificationGroup = NameUtil.capName(notificationGroup);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

//		String packageName = null;
//		Application application = context.getApplication();
//		Service service = ApplicationUtil.getServiceByName(application, notificationName);
//		Callback callback = ApplicationUtil.getOutgoingCallbackByName(application, notificationName);
//		if (service != null) packageName = service.getPackageName();
//		if (callback != null) packageName = callback.getPackageName();
//		if (packageName != null)
//			modelClass.addImportedClass(packageName+"."+notificationName);

		if (parameterType != null && parameterName != null) {
			ModelParameter modelParameter = createParameter(parameterType, parameterName);
			modelOperation.addParameter(modelParameter);
		}
		
		Buf buf = new Buf();
		if (parameterName != null)
			buf.putLine2("fireServiceAborted(\""+domainName+"_"+notificationName+"_"+notificationGroup+"\", "+parameterName+");");
		else buf.putLine2("fireServiceAborted(\""+domainName+"_"+notificationName+"_"+notificationGroup+"\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

}
