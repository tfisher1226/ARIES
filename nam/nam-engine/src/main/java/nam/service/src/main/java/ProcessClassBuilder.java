package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.model.Application;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Command;
import nam.model.Component;
import nam.model.Element;
import nam.model.Input;
import nam.model.Interactor;
import nam.model.Invocation;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Output;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.ProcessState;
import nam.model.Processor;
import nam.model.Reference;
import nam.model.Service;
import nam.model.Unit;
import nam.model.statement.InvokeCommand;
import nam.model.statement.ReplyCommand;
import nam.model.statement.ResponseBlock;
import nam.model.statement.SendCommand;
import nam.model.util.ApplicationUtil;
import nam.model.util.CommandUtil;
import nam.model.util.ElementUtil;
import nam.model.util.ExtensionsUtil;
import nam.model.util.OperationUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProcessStateUtil;
import nam.model.util.ProcessUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerFactory;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.bpel.BPELUtil;
import aries.codegen.AbstractBeanBuilder;
import aries.codegen.ModelOperationFactory;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.ExpressionUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelReference;
import aries.generation.model.ModelVariable;


/**
 * Builds a Process Implementation {@link ModelClass} object given a {@link Process} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ProcessClassBuilder extends AbstractBeanBuilder {

	private ProcessClassProvider processClassProvider;
	
	private Process process;
	
	private ModelClass modelClass;

	private ModelOperationFactory modelOperationFactory;
	
	private Set<ModelOperation> localOperationsFromStack;
	
	private Set<String> serviceCompleteOperationsDone;
	
	//private String serviceInterface;

	
	public ProcessClassBuilder(GenerationContext context) {
		processClassProvider = new ProcessClassProvider(context);
		this.context = context;
	}
	
	public ModelClass buildClass(Process process) throws Exception {
		this.process = process;
		modelOperationFactory = new ModelOperationFactory(context);
		localOperationsFromStack = new LinkedHashSet<ModelOperation>();
		serviceCompleteOperationsDone = new HashSet<String>();
		
		String processName = ServiceLayerHelper.getProcessName(process);
		String packageName = ServiceLayerHelper.getProcessPackageName(process);
		String className = ServiceLayerHelper.getProcessClassName(process);
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
		modelClass.setParentClassName("org.aries.process.AbstractProcess");
		modelClass.addImportedClass("org.aries.process.AbstractProcess");
		modelClass.addImplementedInterface(className+"MBean");

		modelOperationFactory.reset(modelClass);
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
		initializeImportedClasses(modelClass, process);
		initializeClassAnnotations(modelClass, process);
		initializeInstanceOperations(modelClass, process);
		initializeInstanceFields(modelClass, process);
		initializeClassConstructors(modelClass, process);
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		//modelClass.addImportedClass("org.aries.process.ProxyLocator");
	}

	protected void initializeClass(ModelClass modelClass, Process process, Sequence sequence) throws Exception {
		initializeClass(modelClass, process);
		initializeVariables(modelClass, process);
		initializeClassConstructors(modelClass, process, sequence);
		Receive receiveActivity = BPELUtil.getInitialReceiveActivity(sequence);
		javax.wsdl.OperationType receiveOperationStyle = receiveActivity.getOperation().getStyle();
		if (receiveOperationStyle == javax.wsdl.OperationType.REQUEST_RESPONSE) {
			ModelAttribute attribute = new ModelAttribute();
			attribute.setModifiers(Modifier.PRIVATE);
			attribute.setName("futureResult");
			attribute.setPackageName("org.aries.util.concurrent");
			String resultClassName = BPELUtil.getResultClassName(sequence);
			attribute.setClassName("FutureResult<"+resultClassName+">");
			modelClass.addImportedClass("org.aries.util.concurrent.FutureResult");
			modelClass.addInstanceAttribute(attribute);
		}

		processClassProvider.setCurrentClass(modelClass);
		processClassProvider.generate(process, sequence);
	}

	protected void initializeClass(ModelClass modelClass, Process process, Flow flow) throws Exception {
		initializeVariables(modelClass, process);
		initializeLinks(modelClass, process);
		processClassProvider.setCurrentClass(modelClass);
		processClassProvider.generate(process, flow);
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

		modelClass.addImportedClass("org.aries.process.TimeoutHandler");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		
		modelClass.addImportedClass("common.jmx.MBeanUtil");
		modelClass.addImportedClass("common.tx.state.ActionState");

		switch (context.getServiceLayerBeanType()) {
		case EJB:
			modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
			modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
			if (ProcessUtil.hasIncomingEvents(process))
				modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRES_NEW");
			modelClass.addStaticImportedClass("javax.ejb.TransactionManagementType.CONTAINER");
			modelClass.addImportedClass("javax.inject.Inject");
			modelClass.addImportedClass("javax.ejb.LocalBean");
			//modelClass.addImportedClass("javax.ejb.Remove");
			modelClass.addImportedClass("javax.ejb.Singleton");
			modelClass.addImportedClass("javax.ejb.Startup");
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
			classAnnotations.add(AnnotationUtil.createStartupAnnotation());
			classAnnotations.add(AnnotationUtil.createSingletonAnnotation());
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
	
	protected void initializeClassConstructors(ModelClass modelClass, Process process) throws Exception {
		modelClass.addInstanceConstructor(createConstructor(process));
	}

	protected ModelConstructor createConstructor(Process process) throws Exception {
		Set<ModelReference> referenceStack = modelOperationFactory.getReferenceStack();
		if (referenceStack.size() == 0)
			return null;
		
		Namespace namespace = context.getNamespaceByUri(ProcessUtil.getNamespace(process));
		String packageName = ProjectLevelHelper.getPackageName(namespace.getUri());
		org.eclipse.wst.wsdl.Operation operation = null; //TODO initialReceiveActivity.getOperation();
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		Iterator<ModelReference> referenceIterator = referenceStack.iterator();
		while (referenceIterator.hasNext()) {
			ModelReference reference = referenceIterator.next();
			String referencePackageName = reference.getPackageName();
			String referenceClassName = reference.getClassName();
			String factoryPrefix = NameUtil.getSimpleName(referencePackageName);
			String objectFactoryName = factoryPrefix + "ObjectFactory";
			buf.putLine2(objectFactoryName+" = new "+referencePackageName+".ObjectFactory();");
			if (!reference.isFullyQualified())
				modelClass.addImportedClass(referencePackageName+"."+referenceClassName);
		}
		
//		List<Cache> cacheUnits = process.getCacheUnits();
//		Iterator<Cache> iterator = cacheUnits.iterator();
//		while (iterator.hasNext()) {
//			Cache cache = iterator.next();
//			String className = CacheUtil.getClassName(cache) + "Manager";
//			String beanName = NameUtil.uncapName(className);
//			buf.putLine2(beanName+" = new "+className+"();");
//		}
		
//		if (operation != null && operation.getStyle() == javax.wsdl.OperationType.REQUEST_RESPONSE) {
//			String resultClassName = "Object";
//			buf.putLine2("futureResult = new FutureResult<"+resultClassName+">();");
//		}
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}

	protected void initializeVariables(ModelClass modelClass, Process process) throws Exception {
		//Process process = context.getProcessByService(service.getPortType());
		ProcessState state = process.getState();
		
		//create fields from top-level variables
		org.eclipse.bpel.model.Process bpelProcess = BPELUtil.getBPELProcess(process);
		Variables variables = bpelProcess.getVariables();
		Iterator<Variable> iterator = variables.getChildren().iterator();
		while (iterator.hasNext()) {
			Variable variable = iterator.next();

			org.eclipse.wst.wsdl.Message message = variable.getMessageType();
			if (message != null) {
				@SuppressWarnings("unchecked") Collection<org.eclipse.wst.wsdl.Part> parts = message.getParts().values();
				Iterator<org.eclipse.wst.wsdl.Part> partIterator = parts.iterator();
				while (partIterator.hasNext()) {
					org.eclipse.wst.wsdl.Part part = (org.eclipse.wst.wsdl.Part) partIterator.next();
					nam.model.Variable processVariable = new nam.model.Variable();
					processVariable.setType(TypeUtil.getTypeFromMessagePart(part));
					//String messageName = TypeUtil.getNameFromMessage(message);
					processVariable.setName(variable.getName()+"_"+part.getName());
					//nam.model.Variable.setName(messageName+"_"+part.getName());
					processVariable.setIsMessage(true);
					state.getVariables().add(processVariable);
				}

			} else {
				nam.model.Variable processVariable = new nam.model.Variable();
				String variableType = TypeUtil.getTypeFromVariable(variable);
				String variableClass = TypeUtil.getClassName(variableType);
				processVariable.setType(variableType);
				processVariable.setName(variable.getName());
				processVariable.setIsMessage(false);
				/*
				 * TODO deal with collections in the right way for each one of them.  
				 * There are also certain options (yet to be implemented) for each of the 
				 * basic ones: list, set and map - we don't wish to support any others.
				 */
				if (!CodeGenUtil.isJavaDefaultType(variableClass) && !CodeGenUtil.isJavaReservedWord(variableClass)) {
					if (variableClass.startsWith("List")) {
						processVariable.setValue("new ArrayList<"+variableClass+">()");
					} else if (variableClass.startsWith("Set")) {
						processVariable.setValue("new HashSet<"+variableClass+">()");
					} else if (variableClass.startsWith("Map")) {
						processVariable.setValue("new HashMap<String, "+variableClass+">()");
					} else {
						processVariable.setValue("new "+variableClass+"()");
					}
				}
				state.getVariables().add(processVariable);
			}
		}
		
		List<nam.model.Variable> stateVariables = process.getState().getVariables();
		Iterator<nam.model.Variable> variableIterator = stateVariables.iterator();
		while (variableIterator.hasNext()) {
			nam.model.Variable variable = (nam.model.Variable) variableIterator.next();
			ModelReference modelReference = new ModelReference();
			modelReference.setModifiers(Modifier.PRIVATE);
			modelReference.setClassName(TypeUtil.getClassName(variable.getType()));
			modelReference.setPackageName(TypeUtil.getPackageName(variable.getType()));
			modelReference.setName(variable.getName());
			modelReference.setType(variable.getType());
			modelReference.setDefault(variable.getValue());
			modelReference.setGenerateSetter(false);
			modelClass.addInstanceReference(modelReference);
			modelClass.addImportedClass(modelReference);
		}
	}

//	protected void initializeVariables(ModelClass modelClass, Service service) {
//		ServiceState state = service.getState();
//		List<nam.model.Variable> variables = state.getVariables();
//		Iterator<nam.model.Variable> variableIterator = variables.iterator();
//		while (variableIterator.hasNext()) {
//			nam.model.Variable variable = (nam.model.Variable) variableIterator.next();
//			ModelReference modelReference = new ModelReference();
//			modelReference.setModifiers(Modifier.PRIVATE);
//			modelReference.setClassName(TypeUtil.getClassName(variable.getType()));
//			modelReference.setPackageName(TypeUtil.getPackageName(variable.getType()));
//			modelReference.setName(variable.getName());
//			modelReference.setValue(variable.getValue());
//			modelReference.setGenerateSetter(false);
//			modelClass.addInstanceReference(modelReference);
//			modelClass.addImportedClass(modelReference);
//		}
//	}
	
//	protected void addProcessVariableToProcessState(ProcessState state, Variable variable, Part part) {
//		ProcessVariable processVariable2 = new ProcessVariable();
//		processVariable2.setType(typeFromMessagePart);
//		//String messageName = TypeUtil.getNameFromMessage(message);
//		processVariable2.setName(variable.getName());
//		//ProcessVariable2.setName(messageName+"_"+part.getName());
//		processVariable2.setMessage(true);
//		state.getVariables().add(processVariable2);
//
//	}

	protected void initializeLinks(ModelClass modelClass, Process process) throws Exception {
		//Process process = context.getProcess();
		//ServiceState state = service.getState();
		
		org.eclipse.bpel.model.Process bpelProcess = BPELUtil.getBPELProcess(process);
		Activity initialBlock = bpelProcess.getActivity();
		if (initialBlock instanceof Flow) {
			Flow flow = (Flow) initialBlock;
			List<Link> links = flow.getLinks().getChildren();

			//create state variables
			Iterator<Link> linkIterator = links.iterator();
			while (linkIterator.hasNext()) {
				Link link = linkIterator.next();
				nam.model.Variable variable = new nam.model.Variable();
				variable.setType(org.aries.util.TypeUtil.getTypeFromDefaultType("boolean"));
				variable.setName(CodeUtil.getVariableName(link));
				process.getState().getVariables().add(variable);
			}
		
			//create instance fields
			linkIterator = links.iterator();
			while (linkIterator.hasNext()) {
				Link link = linkIterator.next();
				String name = CodeUtil.getVariableName(link);
				nam.model.Variable variable = ProcessStateUtil.getVariableByName(process.getState(), name);
				ModelReference modelReference = new ModelReference();
				modelReference.setModifiers(Modifier.PRIVATE);
				modelReference.setClassName(TypeUtil.getClassName(variable.getType()));
				modelReference.setPackageName(TypeUtil.getPackageName(variable.getType()));
				modelReference.setName(variable.getName());
				modelReference.setType(variable.getType());
				modelReference.setDefault(variable.getValue());
				modelReference.setGenerateGetter(false);
				modelReference.setGenerateSetter(false);
				modelClass.addInstanceReference(modelReference);
				modelClass.addImportedClass(modelReference);
			}
			
			//create instance operations
			linkIterator = links.iterator();
			while (linkIterator.hasNext()) {
				Link link = linkIterator.next();
				ModelOperation modelOperation = new ModelOperation();
				modelOperation.setName(CodeUtil.getOperationName(link));
				modelOperation.setModifiers(Modifier.PROTECTED);
				modelOperation.setResultType("boolean");
				Buf buf = new Buf();
				String linkVariable = CodeUtil.getVariableName(link);
				buf.putLine2("return "+linkVariable+" != null && "+linkVariable+" == true;");
				modelOperation.addInitialSource(buf.get());
				modelClass.addInstanceOperation(modelOperation);
			}
		}
	}
	
//	protected void initializeClass(ModelClass modelClass, Process process) throws Exception {
//		modelClass.setParentTypeName("org.aries.action.AbstractAction");
//		initializeClassAnnotations(modelClass, service, action);
//		//initializeClassConstructors(modelClass, service, action);
//		initializeImportedClasses(modelClass, service, action);
//		initializeInstanceFields(modelClass, service, action);
//		modelClass.addInstanceOperation(createActionOperation(action));
//		//modelClass.addInstanceOperation(createValidateOperation());
//	}

//	protected void initializeClassAnnotations(ModelClass modelClass, Process process) throws Exception {
//		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
//		classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
//		classAnnotations.add(AnnotationUtil.createNameAnnotation(action.getName()+"Action"));
//		classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.EVENT));
//	}


	protected void initializeClassConstructors(ModelClass modelClass, Process process, Sequence sequence) throws Exception {
		modelClass.addInstanceConstructor(createConstructor(sequence));
	}
	
	protected ModelConstructor createConstructor(Sequence sequence) throws Exception {
		Receive initialReceiveActivity = BPELUtil.getInitialReceiveActivity(sequence);
		org.eclipse.wst.wsdl.Operation operation = initialReceiveActivity.getOperation();
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		Buf buf = new Buf();
		buf.putLine2("stateManager = new "+baseName+"StateManager();");
		if (operation.getStyle() == javax.wsdl.OperationType.REQUEST_RESPONSE) {
			String resultClassName = BPELUtil.getResultClassName(sequence);
			buf.putLine2("futureResult = new FutureResult<"+resultClassName+">();");
		}
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}

//	protected void initializeImportedClasses(ModelClass modelClass, Process process) {
//		modelClass.addImplementedInterface("java.io.Serializable");
//		modelClass.addImportedClass("java.io.Serializable");
//		modelClass.addImportedClass("org.jboss.seam.ScopeType");
//		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
//		modelClass.addImportedClass("org.jboss.seam.annotations.In");
//		
//		if (modelClass.getParentTypeName() != null)
//			modelClass.addImportedClass(modelClass.getParentTypeName());
//		//String packageName = context.getModule().getGroupId()+".model";
//
//		List<Input> inputs = action.getInputs();
//		Iterator<Input> inputIterator = inputs.iterator();
//		while (inputIterator.hasNext()) {
//			Input input = inputIterator.next();
//			Element element = context.findElement(input);
//			if (element != null)
//				addImportedClass(modelClass, element.getType());
//			else addImportedClass(modelClass, input.getType());
//		}
//
//		List<Output> outputs = action.getOutputs();
//		Iterator<Output> outputIterator = outputs.iterator();
//		while (outputIterator.hasNext()) {
//			Output output = outputIterator.next();
//			Element element = context.findElement(output);
//			if (element != null)
//				addImportedClass(modelClass, element.getType());
//			else addImportedClass(modelClass, output.getType());
//		}
//	}

	
	protected void initializeInstanceFields(ModelClass modelClass, Process process) throws Exception {
		ServiceLayerFactory.addReference_StatefulContext(modelClass, process);
		//modelClass.addInstanceAttribute(createAttribute_StateManager(process));
		//modelClass.addInstanceAttribute(createAttribute_ObjectFactory(process));
		modelClass.addStaticAttribute(createAttribute_DefaultTimeout());
		
		String namespace = process.getNamespace();
		Persistence persistence = ExtensionsUtil.getPersistenceBlock(context.getProject(), namespace);
		//Persistence persistence = ModuleUtil.getPersistenceBlock(context.getModule());
		if (persistence != null) {
			List<Unit> units = PersistenceUtil.getUnits(persistence);
			Iterator<Unit> unitIterator = units.iterator();
			while (unitIterator.hasNext()) {
				Unit unit = unitIterator.next();
				modelClass.addInstanceReference(modelOperationFactory.createReference_UnitManager(unit, namespace));
			}
		}
		
		List<Cache> caches = process.getCacheUnits();
		Iterator<Cache> cacheIterator = caches.iterator();
		while (cacheIterator.hasNext()) {
			Cache cache = cacheIterator.next();
			modelClass.addInstanceReference(modelOperationFactory.createReference_CacheManager(cache, namespace));
		}
		
		//modelClass.addInstanceReferences(modelOperationFactory.createReferences_ObjectFactory());
		
		if (ProcessUtil.hasIncomingEvents(process))
			createReferences_EventHandlers(modelClass, process);
		
		if (ProcessUtil.hasOutgoingEvents(process))
			createReference_EventMulticaster(modelClass, process);

		if (modelOperationFactory.getReferenceStack().size() > 0)
			modelClass.addInstanceReferences(modelOperationFactory.createReferences_ObjectFactory());
		
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), process);
		List<Service> remoteServicesSorted = new ArrayList<Service>(remoteServices);
		ServiceUtil.sortServices(remoteServicesSorted);
		Iterator<Service> iterator3 = remoteServicesSorted.iterator();
		while (iterator3.hasNext()) {
			Service remoteService = iterator3.next();
			modelClass.addInstanceReference(createReference_ServiceTimeout(remoteService));
		}
		
		Iterator<ModelReference> iterator = modelOperationFactory.getReferenceStack().iterator();
		while (iterator.hasNext()) {
			ModelReference reference = iterator.next();
			modelClass.addInstanceReference(reference);
		}
	}

	protected ModelAttribute createAttribute_DefaultTimeout() {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		attribute.setPackageName("java.lang");
		attribute.setClassName("long");
		attribute.setName("DEFAULT_TIMEOUT");
		attribute.setDefault(60000L);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	protected ModelReference createReference_ServiceTimeout(Service service) {
		String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		String beanName = serviceName + "Timeout";
		
		ModelReference reference = new ModelReference();
		reference.setModifiers(Modifier.PRIVATE);
		reference.setPackageName("java.lang");
		reference.setClassName("long");
		reference.setName(beanName);
		reference.setDefault("DEFAULT_TIMEOUT");
		reference.setGenerateGetter(false);
		reference.setGenerateSetter(false);
		return reference;
	}

	protected void createReferences_EventHandlers(ModelClass modelClass, Process process) {
		Set<String> events = ProcessUtil.getIncomingEvents(process);
		Iterator<String> iterator = events.iterator();
		while (iterator.hasNext()) {
			String eventName = iterator.next();
			ServiceLayerFactory.addReference_EventHandler(modelClass, process, eventName);
		}
	}
	
	protected void createReference_EventMulticaster(ModelClass modelClass, Process process) {
		ServiceLayerFactory.addReference_EventMulticaster(modelClass, process);
	}

	
	
//	protected ModelAttribute createAttribute_Processor(Processor processor) throws Exception {
//		String processorName = findProcessorName(processor);
//		Component component = findComponentByName(processorName);
//		ModelAttribute attribute = new ModelAttribute();
//		attribute.setModifiers(Modifier.PROTECTED);
//		attribute.setName(processorName);
//		attribute.setType(component.getType());
//		attribute.setPackageName(NameUtil.getPackageName(component.getType()));
//		attribute.setClassName(NameUtil.getClassName(component.getType()));
//		attribute.addAnnotation(AnnotationUtil.createInAnnotation(true));
//		return attribute;
//	}

//	public ModelAttribute createAttribute_StateManager(Process process) throws Exception {
//		String packageName = NameUtil.getPackageFromNamespace(getNamespace(process));
//		String className = ProcessUtil.getRootName(process) + "StateManager";
//		ModelAttribute attribute = new ModelAttribute();
//		attribute.setModifiers(Modifier.PRIVATE);
//		attribute.setPackageName(packageName);
//		attribute.setClassName(className);
//		attribute.setName("stateManager");
//		//attribute.setValue("new "+className+"();");
//		attribute.setGenerateGetter(false);
//		attribute.setGenerateSetter(false);
//		return attribute;
//	}

	
	protected void initializeInstanceOperations(ModelClass modelClass, Process process) throws Exception {
		modelClass.addInstanceOperation(createOperation_GetDomainId(process));
		//modelClass.addInstanceOperation(createOperation_GetConversationId(process));
		modelClass.addInstanceOperation(createOperation_PostConstruct(process));
		modelClass.addInstanceOperation(createOperation_PreDestroy(process));
		modelClass.addInstanceOperation(createOperation_RegisterWithJMX(process));
		modelClass.addInstanceOperation(createOperation_UnregisterWithJMX(process));
		
		if (process.getTransacted() != null) {
			//modelClass.addInstanceOperations(createOperations_GetStateManager(process));
			modelClass.addInstanceOperation(createOperation_UpdateState(process));
		}
		
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			modelClass.clearLocalVariables();
			Operation operation = iterator.next();
			context.setOperation(operation);
			//modelOperationFactory.reset(modelClass);

//			if (operation.getName().equalsIgnoreCase("BooksAvailable"))
//				System.out.println();
			
			Application application = context.getApplication();
			String serviceInterface = NameUtil.capName(operation.getName());
			Service service = ApplicationUtil.getServiceByName(application, serviceInterface);
			Callback callback = ApplicationUtil.getIncomingCallbackByName(application, serviceInterface);
			
			boolean isCustomMethod = service == null && callback == null;
			if (isCustomMethod) {
				//do nothing for now
				continue;
			}
			
			if (service != null)
				context.setService(service);
			if (callback != null)
				context.setService(callback);
			modelOperations.add(modelOperationFactory.createOperation_ServiceHandler(modelClass, context.getService(), operation));
			
			if (service != null) {
				modelOperations.add(modelOperationFactory.createOperation_ServiceHandler_cancel(modelClass, service, operation));
				modelOperations.add(modelOperationFactory.createOperation_ServiceHandler_undo(modelClass, service, operation));
				modelOperations.add(modelOperationFactory.createOperation_ServiceHandler_exception(modelClass, service, operation));
				modelOperations.add(modelOperationFactory.createOperation_ServiceHandler_timeout(modelClass, service, operation));
			}
			modelOperations.add(modelOperationFactory.createOperation_ServiceHandler_Done(modelClass, operation));

			//provide the exception and timeout handlers for each remote service invocation
			Collection<InvokeCommand> invokeCommands = CommandUtil.getCommands(operation.getCommands(), InvokeCommand.class);
			Iterator<InvokeCommand> iterator3 = invokeCommands.iterator();
			while (iterator3.hasNext()) {
				InvokeCommand invokeCommand = iterator3.next();
				modelOperationFactory.resetCallbackState();
				
				List<ResponseBlock> responseBlocks = invokeCommand.getResponseBlocks();
				Iterator<ResponseBlock> iterator4 = responseBlocks.iterator();
				while (iterator4.hasNext()) {
					ResponseBlock responseBlock = iterator4.next();
					Collection<ReplyCommand> replyCommands = CommandUtil.getCommands(responseBlock.getCommands(), ReplyCommand.class);
					Iterator<ReplyCommand> iterator5 = replyCommands.iterator();
					while (iterator5.hasNext()) {
						ReplyCommand replyCommand = iterator5.next();
						modelOperationFactory.createReplyOperations(2, operation, replyCommand);
						//modelOperations.addAll(replyOperations);
					}
				}

				//modelOperations.addAll(modelOperationFactory.createOperations_HandleRequest_responses(2, operation, invokeCommand));
				modelOperations.addAll(modelOperationFactory.createOperations_HandleRequest_exceptions(2, operation, invokeCommand));
				modelOperations.addAll(modelOperationFactory.createOperations_HandleRequest_timeouts(2, operation, invokeCommand));
			}

			//provide the exception and timeout handlers for each remote service invocation
			Collection<SendCommand> sendCommands = CommandUtil.getCommands(operation.getCommands(), SendCommand.class);
			Iterator<SendCommand> iterator6 = sendCommands.iterator();
			while (iterator6.hasNext()) {
				SendCommand sendCommand = iterator6.next();
				modelOperationFactory.resetCallbackState();
				Interactor interactor = (Interactor) sendCommand.getActor();
				List<Command> commands = sendCommand.getCommands();
				modelOperations.add(modelOperationFactory.createOperation_HandleRequest_exception(2, operation, interactor, commands));
				modelOperations.add(modelOperationFactory.createOperation_HandleRequest_timeout(2, operation, interactor, commands));
			}

			if (callback != null) {
				Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
				Collection<ReplyCommand> replyCommands = CommandUtil.getCommands(callbackOperation.getCommands(), ReplyCommand.class);
				Iterator<ReplyCommand> iterator2 = replyCommands.iterator();
				while (iterator2.hasNext()) {
					ReplyCommand replyCommand = iterator2.next();
					//modelOperationFactory.resetCallbackState();
					
					//modelOperations.addAll(modelOperationFactory.createOperations_HandleRequest_responses(2, operation, replyCommand));
					modelOperationFactory.createReplyOperations(2, operation, replyCommand);
					//modelOperations.addAll(replyOperations);
					//modelOperations.addAll(modelOperationFactory.createOperations_HandleRequest_exceptions(2, operation, replyCommand));
					//modelOperations.addAll(modelOperationFactory.createOperations_HandleRequest_timeouts(2, operation, replyCommand));
				}
			}
			
			localOperationsFromStack.addAll(modelOperationFactory.getPendingLocalOperationsFromStack());
		}
		
		iterator = operations.iterator();
		while (iterator.hasNext()) {
			modelClass.clearLocalVariables();
			Operation operation = iterator.next();
			context.setOperation(operation);
			
			//provide the exception and timeout handlers for each remote service invocation
			Collection<InvokeCommand> invokeCommands = CommandUtil.getCommands(operation.getCommands(), InvokeCommand.class);
			Iterator<InvokeCommand> iterator3 = invokeCommands.iterator();
			while (iterator3.hasNext()) {
				InvokeCommand invokeCommand = iterator3.next();
				modelOperationFactory.resetCallbackState();
//				modelOperations.addAll(modelOperationFactory.createOperations_HandleRequest_responses(2, operation, invokeCommand));
//				modelOperations.addAll(modelOperationFactory.createOperations_HandleRequest_exceptions(2, operation, invokeCommand));
//				modelOperations.addAll(modelOperationFactory.createOperations_HandleRequest_timeouts(2, operation, invokeCommand));
			}
			
			//modelOperations.addAll(createOperationsForOptionChecks(modelClass, operation));
			//modelOperations.addAll(modelOperationFactory.getPendingCallbackOperationsFromStack());
			localOperationsFromStack.addAll(modelOperationFactory.getPendingLocalOperationsFromStack());
			//modelClass.addInstanceOperations(modelOperationFactory.getPendingLocalOperationsFromStack());
			//modelOperations.addAll(modelOperationFactory.getLocalOperationStack().values());
			//modelOperations.add(createServiceCompleteOperation(modelClass, operation));
			
//			Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
//			Iterator<Service> iterator3 = remoteServices.iterator();
//			while (iterator3.hasNext()) {
//				Service remoteService = iterator3.next();
//				Operation operation2 = ProcessUtil.getOperation(remoteService.getProcess(), remoteService.getName());
//				modelOperations.add(createOperation_ServiceHandler_exception(modelClass, remoteService, operation2));
//				modelOperations.add(createOperation_ServiceHandler_timeout(modelClass, remoteService, operation2));
//				messageCreationOperations.add(createOperation_RemoteService_CreateMessage(modelClass, remoteService));
//				loggedStateCancelOperations.add(createOperation_RemoteService_CancelRequest(modelClass, remoteService));
//				loggedStateUndoOperations.add(createOperation_RemoteService_UndoRequest(modelClass, remoteService));
//				logStateOperations.add(createOperation_RemoteService_LogState(modelClass, remoteService));
//			}
			
			context.setOperation(null);
		}
	
	
		Application application = context.getApplication();
		Collection<Service> services = ApplicationUtil.getServices(application);
		Iterator<Service> iterator2 = services.iterator();
		while (iterator2.hasNext()) {
			Service service = iterator2.next();
			
			List<Callback> incomingCallbacks = ServiceUtil.getIncomingCallbacks(service);
			Iterator<Callback> iterator3 = incomingCallbacks.iterator();
			while (iterator3.hasNext()) {
				Callback incomingCallback = iterator3.next();
				Operation callbackOperation = ServiceUtil.getDefaultOperation(incomingCallback);
				Operation processOperation = ProcessUtil.getOperation(process, callbackOperation.getName());
				
				Set<ModelOperation> undoOperations = modelOperationFactory.createOperation_UndoCommands(modelClass, incomingCallback, processOperation);
				modelOperationFactory.addLogStateOperations(undoOperations);
			}
		}
		
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), process);
		List<Service> remoteServicesSorted = new ArrayList<Service>(remoteServices);
		ServiceUtil.sortServices(remoteServicesSorted);
		Iterator<Service> iterator3 = remoteServicesSorted.iterator();
		while (iterator3.hasNext()) {
			Service remoteService = iterator3.next();
			
			//Operation operation = ProcessUtil.getOperation(remoteService.getProcess(), remoteService.getName());
			//modelOperations.add(modelOperationFactory.createOperation_ServiceHandler_exception(modelClass, remoteService, operation));
			//modelOperations.add(modelOperationFactory.createOperation_ServiceHandler_timeout(modelClass, remoteService, operation));
			modelOperationFactory.addMessageCreationOperations(createOperation_RemoteService_CreateMessage(modelClass, remoteService));
			modelOperationFactory.addLoggedStateCancelOperation(createOperation_RemoteService_CancelRequest(modelClass, remoteService));
			modelOperationFactory.addLoggedStateUndoOperation(createOperation_RemoteService_UndoRequest(modelClass, remoteService));
			modelOperationFactory.addLogStateOperation(createOperation_RemoteService_LogState(modelClass, remoteService));
		}
		
		Iterator<ModelOperation> iterator4 = localOperationsFromStack.iterator();
		while (iterator4.hasNext()) {
			ModelOperation modelOperation = iterator4.next();
			System.out.println("******** "+modelOperation);
		}
		
		//Collections.sort(modelOperations);
		modelClass.addInstanceOperations(modelOperations);
		modelClass.addInstanceOperations(modelOperationFactory.getMessageCreationOperations());
		modelClass.addInstanceOperations(localOperationsFromStack);
		//modelClass.addInstanceOperations(CodeUtil.sortOperations(localOperationsFromStack));
		modelClass.addInstanceOperations(modelOperationFactory.getLoggedStateCancelOperations());
		modelClass.addInstanceOperations(modelOperationFactory.getLoggedStateUndoOperations());
		modelClass.addInstanceOperations(modelOperationFactory.getLogStateOperations());
		
		iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			modelClass.addInstanceOperations(createServiceCompleteOperations(modelClass, operation));
		}
	}

	protected ModelOperation createOperation_RemoteService_CreateMessage(ModelClass modelClass, Service remoteService) {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(remoteService);
		//String serviceInterfaceNameUncapped = NameUtil.uncapName(serviceInterfaceName);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(remoteService);
		String messageBaseName = ServiceLayerHelper.getMessageBaseName(serviceOperation);
		String messageClassName = ServiceLayerHelper.getMessageClassName(serviceOperation);
		String messageBeanName = "message";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+messageClassName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(messageClassName);
		
		Buf buf = new Buf();
		buf.putLine2(messageClassName+" "+messageBeanName+" = "+processContextName+".create"+messageClassName+"();");
		
		Service service = context.getService();
		Service outgoingCallback = context.getOutgoingCallbackByName(messageBaseName);
		if (outgoingCallback == null) {
			//List<Callback> incomingCallbacks = ServiceUtil.getIncomingCallbacks(serviceForCaller);
			List<Callback> callbacks = ServiceUtil.getDistinctOutgoingCallbacks(remoteService);
			Iterator<Callback> callbackIterator = callbacks.iterator();
			while (callbackIterator.hasNext()) {
				Callback callback = callbackIterator.next();
				//String callbackName = callback.getName();
				//String callbackNameCapped = NameUtil.capName(callbackName);
				String callbackName = ServiceUtil.getExtendedDomainServiceName(service, callback);
				buf.putLine2(processContextName+".addReplyTo_"+callbackName+"(message);");
				//buf.putLine(indent, messageBeanName+".addReplyToDestinations(\""+callbackName+"\", "+processContextName+".get"+callbackNameCapped+"Destination());");
			}
		}
		
		buf.putLine2("return message;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_RemoteService_CancelRequest(ModelClass modelClass, Service remoteService) {
		String serviceName = ServiceUtil.getExtendedDomainServiceName(remoteService);
		//Operation serviceOperation = ServiceUtil.getDefaultOperation(remoteService);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("cancel_"+serviceName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		
		ModelParameter modelParameter = createParameter("String", "correlationId");
		modelOperation.addParameter(modelParameter);

		String sourceCode = modelOperationFactory.getSource_actionStateRestoration(remoteService, "cancel");
		modelOperation.addInitialSource(sourceCode);
		return modelOperation;
	}

	protected ModelOperation createOperation_RemoteService_UndoRequest(ModelClass modelClass, Service remoteService) {
		String serviceName = ServiceUtil.getExtendedDomainServiceName(remoteService);
		//Operation serviceOperation = ServiceUtil.getDefaultOperation(remoteService);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("undo_"+serviceName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		
		ModelParameter modelParameter = createParameter("String", "correlationId");
		modelOperation.addParameter(modelParameter);

		String sourceCode = modelOperationFactory.getSource_actionStateRestoration(remoteService, "undo");
		modelOperation.addInitialSource(sourceCode);
		return modelOperation;
	}

	protected ModelOperation createOperation_RemoteService_LogState(ModelClass modelClass, Service remoteService) {
		String serviceName = ServiceUtil.getExtendedDomainServiceName(remoteService);
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(remoteService);
		String serviceInterfaceNameUncapped = NameUtil.uncapName(serviceInterfaceName);
		
		Operation serviceOperation = ServiceUtil.getDefaultOperation(remoteService);
		String messageType = ServiceLayerHelper.getMessageType(serviceOperation);
		String messageClassName = ServiceLayerHelper.getMessageClassName(serviceOperation);
		//String messageBeanName = ServiceLayerHelper.getMessageBeanName(serviceOperation);
		Element messageElement = context.getElementByType(messageType);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("logState_"+serviceName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		
		ModelParameter modelParameter = createParameter(messageClassName, "message");
		modelOperation.addParameter(modelParameter);

		Buf buf = new Buf();
		buf.putLine2("ActionState action = new ActionState();");
		buf.putLine2("action.setActionId(\""+serviceInterfaceNameUncapped+"\");");
		
		List<Reference> references = ElementUtil.getReferences(messageElement);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = (Reference) iterator.next();
			String fieldName = NameUtil.capName(reference.getName());
			String localPart = TypeUtil.getLocalPart(reference.getType());
			String structure = reference.getStructure();
			if (structure.equals("list")) localPart += "List";
			else if (structure.equals("set")) localPart += "Set";
			else if (structure.equals("map")) localPart += "Map";
			buf.putLine2("action.addElement(\""+localPart+"\", message.get"+fieldName+"());");
		}
		
		Process process = context.getProcess();
		String processContextBeanName = ServiceLayerHelper.getProcessContextInstanceName(process);
		buf.putLine2(processContextBeanName+".logAction(action);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_GetDomainId(Process process) throws Exception {
		String domainId = ProjectLevelHelper.getPackageName(process.getNamespace());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getDomainId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("java.lang.String");
		modelOperation.addInitialSource(CodeUtil.createMethodSource("return \""+domainId+"\";\n"));
		return modelOperation;
	}
	
	protected ModelOperation createOperation_PostConstruct(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("postConstruct");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("registerWithJMX();");
//		List<Module> modules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
//		Iterator<Module> moduleIterator = modules.iterator();
//		while (moduleIterator.hasNext()) {
//			Module projectModule = moduleIterator.next();
//			String namespaceUri = projectModule.getNamespace();
//			String localPart = NameUtil.getLocalPartFromNamespace(namespaceUri);
//			String packageName = ProjectLevelHelper.getPackageName(namespaceUri);
//			buf.putLine2(localPart+"ObjectFactory = new "+packageName+".ObjectFactory();");
//		}

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_PreDestroy(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createPreDestroyAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("preDestroy");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("unregisterWithJMX();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_RegisterWithJMX(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("registerWithJMX");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("MBeanUtil.registerMBean(this, MBEAN_NAME);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_UnregisterWithJMX(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("unregisterWithJMX");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("MBeanUtil.unregisterMBean(MBEAN_NAME);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
	protected List<ModelOperation> createServiceCompleteOperations(ModelClass modelClass, Operation operation) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		Application application = context.getApplication();
		String applicationName = application.getName();

		String serviceId = operation.getName();
		//String serviceInterface = NameUtil.capName(serviceId);
		Service service = context.getServiceByName(applicationName, serviceId);
		if (service != null) {
			String domainName = ServiceUtil.getNamespaceAlias(service);
			String serviceName = ServiceUtil.getServiceName(service);
			String serviceKey = "incoming" + serviceName;

			if (!serviceCompleteOperationsDone.contains(serviceKey)) {
				serviceCompleteOperationsDone.add(serviceKey);
				modelOperations.add(createOperation_FireNotification_IncomingRequestReceived(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestHandled(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestComplete(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestCancelDone(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestUndoDone(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestAborted_Reason(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_IncomingRequestAborted_Cause(domainName, serviceName));
				modelOperations.add(createOperation_FireNotification_ErrorSent(domainName, serviceName));
			}
			
			//get outgoing callback operations for service
			List<Callback> outgoingCallbacks = ServiceUtil.getOutgoingCallbacks(service);
			Iterator<Callback> callbackIterator = outgoingCallbacks.iterator();
			while (callbackIterator.hasNext()) {
				Callback callback = callbackIterator.next();
				String callbackDomain = ServiceUtil.getNamespaceAlias(callback);
				String callbackName = ServiceUtil.getServiceName(callback);
				String callbackKey = "outgoing" + callbackName;
				
				if (!serviceCompleteOperationsDone.contains(callbackKey)) {
					serviceCompleteOperationsDone.add(callbackKey);
					modelOperations.add(createOperation_FireNotification_OutgoingResponseSent(callbackDomain, callbackName));
					modelOperations.add(createOperation_FireNotification_OutgoingResponseAborted_Reason(callbackDomain, callbackName));
					modelOperations.add(createOperation_FireNotification_OutgoingResponseAborted_Cause(callbackDomain, callbackName));
				}
			}
		}
		
		Set<Service> remoteServices = context.getRemoteServices(operation);
		Iterator<Service> remoteServiceIterator = remoteServices.iterator();
		while (remoteServiceIterator.hasNext()) {
			Service remoteService = remoteServiceIterator.next();
			//String remoteServiceName = NameUtil.capName(remoteService.getName()); 
			String remoteServiceDomain = ServiceUtil.getNamespaceAlias(remoteService);
			String remoteServiceName = ServiceUtil.getServiceName(remoteService);
			String remoteServiceKey = "outgoing" + remoteServiceName;

//			if (remoteServiceName.equals("Supplier_ShipBooks"))
//				System.out.println();

			if (!serviceCompleteOperationsDone.contains(remoteServiceKey)) {
				serviceCompleteOperationsDone.add(remoteServiceKey);
				modelOperations.add(createOperation_FireNotification_OutgoingRequestSent(remoteServiceDomain, remoteServiceName));
				modelOperations.add(createOperation_FireNotification_OutgoingRequestAborted_Reason(remoteServiceDomain, remoteServiceName));
				modelOperations.add(createOperation_FireNotification_OutgoingRequestAborted_Cause(remoteServiceDomain, remoteServiceName));
				modelOperations.add(createOperation_FireNotification_ErrorReceived(remoteServiceDomain, remoteServiceName));
			}
		}
		
		if (service != null) {
			//get incoming callback operations for service
			List<Callback> incomingCallbacks = ServiceUtil.getIncomingCallbacks(service);
			Iterator<Callback> callbackIterator = incomingCallbacks.iterator();
			while (callbackIterator.hasNext()) {
				Callback callback = callbackIterator.next();
				String callbackDomain = ServiceUtil.getNamespaceAlias(callback);
				String callbackName = ServiceUtil.getServiceName(callback);
				String callbackKey = "incoming" + callbackName;
				
				if (!serviceCompleteOperationsDone.contains(callbackKey)) {
					serviceCompleteOperationsDone.add(callbackKey);
					modelOperations.add(createOperation_FireNotification_IncomingResponseReceived(callbackDomain, callbackName));
					modelOperations.add(createOperation_FireNotification_IncomingResponseHandled(callbackDomain, callbackName));
					modelOperations.add(createOperation_FireNotification_IncomingResponseComplete(callbackDomain, callbackName));
					modelOperations.add(createOperation_FireNotification_IncomingResponseAborted_Reason(callbackDomain, callbackName));
					modelOperations.add(createOperation_FireNotification_IncomingResponseAborted_Cause(callbackDomain, callbackName));
				}
			}
		}
		
//		Application application = context.getApplication();
//		Callback outgoingCallback = ApplicationUtil.getOutgoingCallbackByName(application, serviceInterface);
//		Callback incomingCallback = ApplicationUtil.getIncomingCallbackByName(application, serviceInterface);
//		if (outgoingCallback == null && incomingCallback == null) {
//			modelOperations.add(createOperation_FireNotification_OutgoingRequestSent(serviceInterface));
//			modelOperations.add(createOperation_FireNotification_OutgoingRequestAborted_Reason(serviceInterface));
//			modelOperations.add(createOperation_FireNotification_OutgoingRequestAborted_Cause(serviceInterface));
//		}
//
//		Service service = ApplicationUtil.getServiceByName(application, serviceInterface);
//		if (service != null) {
//			List<Callback> callbacks = new ArrayList<Callback>();
//			callbacks.addAll(ServiceUtil.getOutgoingCallbacks(service));
//			callbacks.addAll(ServiceUtil.getIncomingCallbacks(service));
//			Iterator<Callback> iterator = callbacks.iterator();
//			while (iterator.hasNext()) {
//				Callback callback = iterator.next();
//				String callbackInterface = callback.getInterfaceName();
//				if (!serviceCompleteOperationsDone.contains(callbackInterface)) {
//					serviceCompleteOperationsDone.add(callbackInterface);
//					modelClass.addImportedClass(callback.getPackageName()+"."+callbackInterface);
//					modelOperations.add(createOperation_FireNotification_OutgoingResponseReplied(callbackInterface));
//					modelOperations.add(createOperation_FireNotification_OutgoingResponseAborted_Reason(callbackInterface));
//					modelOperations.add(createOperation_FireNotification_OutgoingResponseAborted_Cause(callbackInterface));
//				}
//			}
//		}

		String processDomainName = ProcessUtil.getNamespaceAlias(process);
		Set<String> outgoingEvents = OperationUtil.getOutgoingEvents(operation);
		Iterator<String> iterator2 = outgoingEvents.iterator();
		while (iterator2.hasNext()) {
			String eventName = iterator2.next();
			String processEventName = NameUtil.capName(eventName);
			modelOperations.add(createOperation_FireNotification_EventPosted(processDomainName, processEventName));
		}
		
		Set<String> incomingEvents = OperationUtil.getIncomingEvents(operation);
		Iterator<String> iterator = incomingEvents.iterator();
		while (iterator.hasNext()) {
			String eventName = iterator.next();
			String processEventName = NameUtil.capName(eventName);
			modelOperations.add(createOperation_FireNotification_EventReceived(processDomainName, processEventName));
			modelOperations.add(createOperation_FireNotification_ProcessComplete(processDomainName, processEventName));
			modelOperations.add(createOperation_FireNotification_ProcessAborted_Reason(processDomainName, processEventName));
			modelOperations.add(createOperation_FireNotification_ProcessAborted_Cause(processDomainName, processEventName));
		}

		return modelOperations;
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

	protected ModelOperation createOperation_FireNotification_ErrorSent(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Error_Sent");
	}

	protected ModelOperation createOperation_FireNotification_ErrorReceived(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Error_Received");
	}

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

	protected ModelOperation createOperation_FireNotification_OutgoingResponseSent(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Response_Sent");
	}

	protected ModelOperation createOperation_FireNotification_OutgoingResponseAborted_Cause(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Outgoing_Response_Aborted", "String", "reason");
	}

	protected ModelOperation createOperation_FireNotification_OutgoingResponseAborted_Reason(String domainName, String serviceName) {
		return createOperation_FireNotification(domainName, serviceName, "Outgoing_Response_Aborted", "Throwable", "cause");
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

	protected ModelOperation createOperation_FireNotification(String domainName, String eventName, String eventGroup) {
		return createOperation_FireNotification(domainName, eventName, eventGroup, null, null);
	}
	
	protected ModelOperation createOperation_FireNotification(String domainName, String eventName, String eventGroup, String parameterType, String parameterName) {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		String operationName = "fire_" + eventName + "_" + eventGroup.toLowerCase();

		ModelOperation modelOperation = new ModelOperation();	
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");
		
		if (parameterType != null && parameterName != null) {
			ModelParameter modelParameter = createParameter(parameterType, parameterName);
			modelOperation.addParameter(modelParameter);
		}
		
		Buf buf = new Buf();
		if (parameterName != null)
			buf.putLine2(processContextName+".fire_"+eventName+"_"+eventGroup.toLowerCase()+"("+parameterName+");");
		else buf.putLine2(processContextName+".fire_"+eventName+"_"+eventGroup.toLowerCase()+"();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}


	
	protected ModelOperation createServiceCompleteOperationOLD(ModelClass modelClass, Operation operation) throws Exception {
		String operationName = "fire_" + NameUtil.capName(operation.getName()) + "_complete";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		ModelParameter modelParameter = createParameter("Object", "userData");
		modelOperation.addParameter(modelParameter);
		modelClass.addImportedClass(modelParameter);

		String domain = modelClass.getPackageName();
		String serviceId = operation.getName();

		Buf buf = new Buf();
		buf.putLine2("NotificationDispatcher dispatcher = BeanContext.get(\""+domain+".notificationDispatcher\");");
		buf.putLine2("dispatcher.fireServiceCompletedNotification(\""+domain+"."+serviceId+"\", \"\", userData);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected String getQualifiedStringNOTUSED(ModelVariable modelVariable) {
		String name = modelVariable.getName();
		String type = modelVariable.getType();
		String owner = modelVariable.getOwner();
		String value = name;
		if (!name.contains(".")) {
			Element element = context.getElementByName(owner);
			if (ElementUtil.isFieldExists(element, name)) {
				String expression = owner+"."+name;
				value = ExpressionUtil.parseExpression(expression);
			}
		}
		return value;
	}
	
//	protected void initializeCommandArgumentsWithOperationParameters(org.aries.nam.model.old.Operation operation, Command command) throws Exception {
//		Parameter parameter = operation.getParameters().get(0);
//		String responseObjectName = NameUtil.uncapName(parameter.getName());
//		List<Command> commands = command.getCommands();
//		Iterator<Command> iterator = commands.iterator();
//		while (iterator.hasNext()) {
//			Command command2 = iterator.next();
//			List<Parameter> arguments2 = new ArrayList<Parameter>(command2.getParameters());
//			for (int i=0; i < arguments2.size(); i++) {
//				Parameter parameter2 = command2.getParameters().get(i);
//				String parameterName2 = parameter2.getName();
//				if (parameterName2.endsWith("Event")) {
//					continue;
//				}
//				if (!parameterName2.contains(".")) {
//					Element responseObject = context.getElementByName(responseObjectName);
//					if (ElementUtil.isFieldExists(responseObject, parameterName2)) {
//						String expression = responseObjectName+"."+parameterName2;
//						String newValue = ExpressionUtil.parseExpression(expression);
//						Parameter newParameter = new Parameter();
//						newParameter.setName(newValue);
//						newParameter.setType(value);
//						
//						command2.getParameters().set(i, newValue);
//					}
//				}
//			}
//		}
//	}



	
	
//	protected List<ModelOperation> createOperations_GetStateManager(Process process) throws Exception {
//		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
//		List<Cache> cacheUnits = process.getCacheUnits();
//		Iterator<Cache> iterator = cacheUnits.iterator();
//		while (iterator.hasNext()) {
//			Cache cache = iterator.next();
//			modelOperations.add(createOperation_GetStateManager(cache));
//		}
//		return modelOperations;
//	}
//	
//	protected ModelOperation createOperation_GetStateManager(Cache cache) throws Exception {
//		String className = CacheUtil.getClassName(cache) + "Manager";
//		String beanName = NameUtil.uncapName(className);
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setName("get"+className);
//		modelOperation.setResultType(className);
//		String generatedSource = CodeUtil.createMethodSource("return "+beanName+";\n");
//		modelOperation.addInitialSource(generatedSource);
//		return modelOperation;
//	}
	
	protected ModelOperation createOperation_UpdateState(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("updateState");
		modelOperation.setResultType("void");
		Buf buf = new Buf();
		List<Cache> cacheUnits = process.getCacheUnits();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			String cacheName = NameUtil.uncapName(cache.getName());
			buf.putLine2(cacheName+"Manager.updateState();");
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	

	//NOTUSED right now
	protected ModelOperation createActionOperation(Invocation action) throws Exception {
		ModelOperation operation = new ModelOperation();
		operation.setModifiers(Modifier.PUBLIC);
		operation.setName(NameUtil.uncapName(action.getName()));
		
		List<Input> inputs = action.getInputs();
		Iterator<Input> iterator = inputs.iterator();
		while (iterator.hasNext()) {
			Input input = iterator.next();
			String elementType = context.findElementType(input);
			String elementName = context.findElementName(input);
			operation.addParameter(CodeUtil.createParameterFromType(elementType, elementName));
		}

		//assume only one processor is specified for now
		List<Processor> processors = action.getProcessors();
		
		Buf buf = new Buf();
		buf.putLine2("try {");
        buf.putLine2("	logStarted();");
		if (processors.size() > 0) {
			Processor processor = processors.get(0);
			String processorName = findProcessorName(processor);
			String processorMethod = findProcessorMethod(processor);
	        buf.put2("	"+processorName+"."+processorMethod+"(");
			iterator = inputs.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Input input = iterator.next();
				//String elementType = findElementType(input);
				String elementName = context.findElementName(input);
				buf.put(elementName);
				if (i < inputs.size()-1)
					buf.put(", ");	
			}
	        buf.putLine(");");
		}
        buf.putLine2("	logComplete();");
        buf.putLine2("	return null;");
        //buf.putLine2("   return employee;");
        buf.putLine2("} catch (Exception e) {");
        buf.putLine2("	logAborted(e);");
        buf.putLine2("	throw e;");
		buf.putLine2("}");

		operation.addInitialSource(buf.get());
		operation.addException("Exception");

		List<Output> results = action.getOutputs();
		if (results.size() > 0) {
			//assume only one result (at most) is specified for now
			Output result = results.get(0);
			String resultType = context.findElementType(result);
			//add element (instead of type) into operation as result
			operation.setResultType(resultType);
		}
		
		return operation;
	}

	protected String findProcessorName(Processor processor) {
		String processorName = null;
		Component component = context.getComponentByName(processor.getName());
		if (component != null)
			processorName = component.getName();
		if (processorName == null)
			processorName = processor.getName();
		return processorName;
	}

	protected String findProcessorMethod(Processor processor) {
		if (processor.getMethod() != null)
			return processor.getMethod();
		return "process";
	}

}
