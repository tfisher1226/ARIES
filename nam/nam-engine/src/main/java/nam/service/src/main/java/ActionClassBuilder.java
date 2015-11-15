package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.data.DataLayerFactory;
import nam.model.Component;
import nam.model.Element;
import nam.model.Input;
import nam.model.Invocation;
import nam.model.Operation;
import nam.model.Output;
import nam.model.Parameter;
import nam.model.Processor;
import nam.model.Result;
import nam.model.Service;
import nam.model.Services;
import nam.model.util.ServiceUtil;
import nam.model.util.ServicesUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds an Action Implementation {@link ModelClass} object given a {@link Invocation} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ActionClassBuilder extends AbstractManagementBeanBuilder {

	private ActionClassProvider actionClassProvider;

	//private ProcessClassProvider processClassProvider;
	
	
	public ActionClassBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
		actionClassProvider = new ActionClassProvider(context);
		//processClassProvider = new ProcessClassProvider(context);
	}
	
	public List<ModelClass> buildClasses(Services services) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Service> list = ServicesUtil.getServices(services);
		Iterator<Service> iterator = list.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (context.isEnabled("generateServicePerOperation")) {
				modelClasses.addAll(buildClasses(service));
			} else {
				modelClasses.add(buildClass(service));
			}
		}
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Service service) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		if (context.isEnabled("generateServicePerOperation")) {
			List<Operation> operations = ServiceUtil.getOperations(service);
			modelClasses.addAll(buildClasses(service, operations));
		} else {
			modelClasses.add(buildClass(service));
		}
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Service service, List<Operation> operations) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			modelClasses.add(buildClass(service, operation));
		}
		return modelClasses;
	}

	public ModelClass buildClass(Service service) throws Exception {
		Element element = context.getElement(service);
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "Action";
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
		modelClass.addImplementedInterface(interfaceName);
		modelClass.addImplementedInterface("Serializable");
		modelClass.addImportedClass("java.io.Serializable");
		modelClass.setParentClassName("org.aries.action.AbstractAction");
		modelClass.addImportedClass("org.aries.action.AbstractAction");
		initializeClass(modelClass, service, element);
		return modelClass; 
	}
	
	public ModelClass buildClass(Service service, Operation operation) throws Exception {
		Element element = context.getElement(service);
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "Action";
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
		modelClass.addImplementedInterface(interfaceName);
		modelClass.addImplementedInterface("Serializable");
		modelClass.addImportedClass("java.io.Serializable");
		modelClass.setParentClassName("org.aries.action.AbstractAction");
		modelClass.addImportedClass("org.aries.action.AbstractAction");
		initializeClass(modelClass, service, element, operation);
		addImportedClasses(modelClass, operation);
		return modelClass; 
	}
	
	public ModelClass buildClass(Service service, Invocation action) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		//String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		//String actionName = NameUtil.uncapName(action.getName());
		//String className = NameUtil.capName(action.getName()) + "Action";
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "Action";
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
		modelClass.setParentClassName("org.aries.process.AbstractProcess");
		modelClass.addImportedClass("org.aries.process.AbstractProcess");
		modelClass.addImplementedInterface("Serializable");
		modelClass.addImportedClass("java.io.Serializable");
		
		context.setAction(action);
		initializeClass(modelClass, service, action);
		return modelClass; 
	}

//	protected void initializeClass(ModelClass modelClass, Service service, Action action, Sequence sequence) throws Exception {
//		initializeVariables(modelClass, service);
//		initializeClassConstructors(modelClass, service, action, sequence);
//		//initializeImportedClasses(modelClass, service, action);
//		modelClass.addImportedClass("org.aries.runtime.BeanContext");
//		initializeInstanceFields(modelClass, service, action);
//
//		Receive receiveActivity = BPELUtil.getInitialReceiveActivity(sequence);
//		OperationType receiveOperationStyle = receiveActivity.getOperation().getStyle();
//		if (receiveOperationStyle == OperationType.REQUEST_RESPONSE) {
//			ModelAttribute attribute = new ModelAttribute();
//			attribute.setModifiers(Modifier.PRIVATE);
//			attribute.setName("futureResult");
//			attribute.setPackageName("org.aries.util.concurrent");
//			String resultClassName = BPELUtil.getResultClassName(sequence);
//			attribute.setClassName("FutureResult<"+resultClassName+">");
//			modelClass.addImportedClass("org.aries.util.concurrent.FutureResult");
//			modelClass.addInstanceAttribute(attribute);
//		}
//		
//		processClassProvider.setCurrentClass(modelClass);
//		processClassProvider.generate(sequence);
//	}

//	protected void initializeClass(ModelClass modelClass, Service service, Action action, Flow flow) throws Exception {
//		initializeVariables(modelClass, service);
//		initializeLinks(modelClass, service);
//		processClassProvider.setCurrentClass(modelClass);
//		processClassProvider.generate(flow);
//	}

//	protected void initializeVariables(ModelClass modelClass, Service service) {
//		Process process = context.getProcessByService(service.getPortType());
//		ServiceState state = service.getState();
//		
//		//create fields from top-level variables
//		Variables variables = process.getVariables();
//		Iterator<Variable> iterator = variables.getChildren().iterator();
//		while (iterator.hasNext()) {
//			Variable variable = iterator.next();
//
//			Message message = variable.getMessageType();
//			if (message != null) {
//				@SuppressWarnings("unchecked") Collection<Part> parts = message.getParts().values();
//				Iterator<Part> partIterator = parts.iterator();
//				while (partIterator.hasNext()) {
//					Part part = (Part) partIterator.next();
//					ServiceVariable serviceVariable = new ServiceVariable();
//					serviceVariable.setType(TypeUtil.getTypeFromMessagePart(part));
//					//String messageName = TypeUtil.getNameFromMessage(message);
//					serviceVariable.setName(variable.getName()+"_"+part.getName());
//					//serviceVariable.setName(messageName+"_"+part.getName());
//					state.getVariables().add(serviceVariable);
//				}
//			} else {
//				ServiceVariable serviceVariable = new ServiceVariable();
//				serviceVariable.setType(TypeUtil.getTypeFromVariable(variable));
//				serviceVariable.setName(variable.getName());
//				state.getVariables().add(serviceVariable);
//			}
//		}
//		
//		List<ServiceVariable> stateVariables = state.getVariables();
//		Iterator<ServiceVariable> variableIterator = stateVariables.iterator();
//		while (variableIterator.hasNext()) {
//			ServiceVariable variable = (ServiceVariable) variableIterator.next();
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

//	protected void initializeVariables(ModelClass modelClass, Service service) {
//		ServiceState state = service.getState();
//		List<ServiceVariable> variables = state.getVariables();
//		Iterator<ServiceVariable> variableIterator = variables.iterator();
//		while (variableIterator.hasNext()) {
//			ServiceVariable variable = (ServiceVariable) variableIterator.next();
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
	
//	protected void initializeLinks(ModelClass modelClass, Service service) {
//		Process process = context.getProcess();
//		ServiceState state = service.getState();
//		
//		Activity initialBlock = process.getActivity();
//		if (initialBlock instanceof Flow) {
//			Flow flow = (Flow) initialBlock;
//			List<Link> links = flow.getLinks().getChildren();
//
//			//create state variables
//			Iterator<Link> linkIterator = links.iterator();
//			while (linkIterator.hasNext()) {
//				Link link = linkIterator.next();
//				ServiceVariable serviceVariable = new ServiceVariable();
//				serviceVariable.setType(TypeUtil.getTypeFromDefaultType("boolean"));
//				serviceVariable.setName(CodeUtil.getVariableName(link));
//				state.getVariables().add(serviceVariable);
//			}
//		
//			//create instance fields
//			linkIterator = links.iterator();
//			while (linkIterator.hasNext()) {
//				Link link = linkIterator.next();
//				String name = CodeUtil.getVariableName(link);
//				ServiceVariable variable = ServiceStateUtil.getVariableByName(state, name);
//				ModelReference modelReference = new ModelReference();
//				modelReference.setModifiers(Modifier.PRIVATE);
//				modelReference.setClassName(TypeUtil.getClassName(variable.getType()));
//				modelReference.setPackageName(TypeUtil.getPackageName(variable.getType()));
//				modelReference.setName(variable.getName());
//				modelReference.setValue(variable.getValue());
//				modelReference.setGenerateGetter(false);
//				modelReference.setGenerateSetter(false);
//				modelClass.addInstanceReference(modelReference);
//				modelClass.addImportedClass(modelReference);
//			}
//			
//			//create instance operations
//			linkIterator = links.iterator();
//			while (linkIterator.hasNext()) {
//				Link link = linkIterator.next();
//				ModelOperation modelOperation = new ModelOperation();
//				modelOperation.setName(CodeUtil.getOperationName(link));
//				modelOperation.setModifiers(Modifier.PROTECTED);
//				modelOperation.setResultType("boolean");
//				Buf buf = new Buf();
//				String linkVariable = CodeUtil.getVariableName(link);
//				buf.putLine2("return "+linkVariable+" != null && "+linkVariable+" == true;");
//				modelOperation.addInitialSource(buf.get());
//				modelClass.addInstanceOperation(modelOperation);
//			}
//		}
//	}

	protected void initializeClass(ModelClass modelClass, Service service, Element element) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, element);
		initializeImportedClasses(modelClass, service);
		initializeInstanceFields(modelClass, service, element);
		initializeInstanceOperations(modelClass, service, element);
	}
	
	protected void initializeClass(ModelClass modelClass, Service service, Element element, Operation operation) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, element);
		initializeImportedClasses(modelClass, service, operation);
		initializeInstanceFields(modelClass, service, element);
		initializeInstanceOperations(modelClass, service, element, operation);
	}
	
	protected void initializeClass(ModelClass modelClass, Service service, Invocation action) throws Exception {
		modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeClassAnnotations(modelClass, service, action);
		//initializeClassConstructors(modelClass, service, action);
		initializeImportedClasses(modelClass, service, action);
		initializeInstanceFields(modelClass, service, action);
		initializeInstanceOperations(modelClass, service, action);
	}

	protected void initializeClassAnnotations(ModelClass modelClass, Service service, Element element) throws Exception {
		String contextName = NameUtil.uncapName(service.getName()) + "." + NameUtil.uncapName(modelClass.getClassName());
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
		classAnnotations.add(AnnotationUtil.createSuppressSerialWarning());
		//classAnnotations.add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		//if (context.isEnabled("useQualifiedContextNames")) {
		//	String contextPrefix = NameUtil.getQualifiedContextNamePrefix(modelClass.getQualifiedName(), 2);
		//	classAnnotations.add(AnnotationUtil.createNameAnnotation(contextPrefix + "." + contextName));
		//} else classAnnotations.add(AnnotationUtil.createNameAnnotation(contextName));
		//modelClass.addImportedClass("org.jboss.seam.ScopeType");
		//modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
		//modelClass.addImportedClass("org.jboss.seam.annotations.Name");
		//modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
		//modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
	}

	protected void initializeClassAnnotations(ModelClass modelClass, Service service, Invocation action) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		//classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
		//classAnnotations.add(AnnotationUtil.createSuppressSerialWarning());
		//classAnnotations.add(AnnotationUtil.createNameAnnotation(action.getName()+"Action"));
		//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.EVENT));
	}

//	protected void initializeClassConstructors(ModelClass modelClass, Service service, Action action, Sequence sequence) {
//		Receive initialReceiveActivity = BPELUtil.getInitialReceiveActivity(sequence);
//		Operation operation = initialReceiveActivity.getOperation();
//		if (operation.getStyle() == OperationType.REQUEST_RESPONSE) {
//			modelClass.addInstanceConstructor(createConstructor(sequence));
//		}
//	}
	
//	protected ModelConstructor createConstructor(Sequence sequence) {
//		String resultClassName = BPELUtil.getResultClassName(sequence);
//		ModelConstructor modelConstructor = new ModelConstructor();
//		modelConstructor.setModifiers(Modifier.PUBLIC);
//		Buf buf = new Buf();
//		buf.putLine2("futureResult = new FutureResult<"+resultClassName+">();");
//		modelConstructor.addInitialSource(buf.get());
//		return modelConstructor;
//	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) {
		modelClass.addImportedClass("org.jboss.seam.ScopeType");
		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
		modelClass.addImportedClass("org.jboss.seam.annotations.In");
		//String contextPrefix = NameUtil.getQualifiedContextNamePrefix(modelClass.getQualifiedName(), 2);
		//modelClass.addImportedClass(contextPrefix+".exception.InfosgiExceptionUtil");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service, Operation operation) throws Exception {
		initializeImportedClasses(modelClass, service);
		
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
		if (!results.isEmpty())
			result = results.get(0);
		if (result != null) {
			Element element = context.findElement(result);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, result.getType());
		}
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Service service, Invocation action) {
		initializeImportedClasses(modelClass, service);
		
		if (modelClass.getParentClassName() != null)
			modelClass.addImportedClass(modelClass.getParentClassName());
		//String packageName = context.getModule().getGroupId()+".model";

		List<Input> inputs = action.getInputs();
		Iterator<Input> inputIterator = inputs.iterator();
		while (inputIterator.hasNext()) {
			Input input = inputIterator.next();
			Element element = context.findElement(input);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, input.getType());
		}

		List<Output> outputs = action.getOutputs();
		Iterator<Output> outputIterator = outputs.iterator();
		while (outputIterator.hasNext()) {
			Output output = outputIterator.next();
			Element element = context.findElement(output);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, output.getType());
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		String className = NameUtil.capName(modelClass.getName());
		CodeUtil.addStaticLoggerField(modelClass, className);
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service, Element element) throws Exception {
		if (!ServiceUtil.isStateful(service) && element != null)
			modelClass.addInstanceAttribute(DataLayerFactory.createRepositoryAttribute(service, null));
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service, Invocation action) throws Exception {
		initializeInstanceFields(modelClass, service);

		List<Processor> processors = action.getProcessors();
		Iterator<Processor> iterator = processors.iterator();
		while (iterator.hasNext()) {
			Processor processor = iterator.next();
			ModelAttribute processorAttribute = createProcessorAttribute(processor);
			modelClass.addInstanceAttribute(processorAttribute);
			String packageName = processorAttribute.getPackageName();
			String className = processorAttribute.getClassName();
			if (TypeUtil.isImportedClassRequired(packageName, className)) {
				modelClass.addImportedClass(packageName+"."+className);
			}
		}
		
		modelClass.addInstanceAttribute(createManagerAttribute(service));
	}
	
	protected ModelAttribute createProcessorAttribute(Processor processor) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PROTECTED);
		attribute.setName(findProcessorName(processor));
		Component component = findComponentByName(attribute.getName());
		attribute.setPackageName(NameUtil.getPackageName(component.getType()));
		attribute.setClassName(NameUtil.getClassName(component.getType()));
		//attribute.addAnnotation(AnnotationUtil.createInAnnotation(true));
		return attribute;
	}

	public static ModelAttribute createManagerAttribute(Service service) {
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String className = NameUtil.capName(service.getName()) + "Manager";
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("stateManager");
		//attribute.setValue("new "+className+"();");
		attribute.setGenerateGetter(true);
		attribute.setGenerateSetter(true);
		return attribute;
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Element element) throws Exception {
		//modelClass.addInstanceOperation(createActionOperation(service));
		//modelClass.addInstanceOperation(createValidateOperation());
		modelClass.addInstanceOperation(createGetDescriptionOperation(service.getName()));
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			modelClass.addInstanceOperation(createInvocationOperation(modelClass, service, element, operation));
			addImportedClasses(modelClass, operation);
		}
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Element element, Operation operation) throws Exception {
		modelClass.addInstanceOperation(createGetDescriptionOperation(operation.getName()));
		modelClass.addInstanceOperation(createInvocationOperation(modelClass, service, element, operation));
		//modelClass.addImportedClass("org.aries.validate.util.Check");
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Service service, Invocation action) throws Exception {
		modelClass.addInstanceOperation(createActionOperation(action));
		//modelClass.addInstanceOperation(createValidateOperation());
	}

	/**
	 * GetDescription operation
	 * ------------------------
	 */
	protected ModelOperation createGetDescriptionOperation(String descriptor) {
		String description = NameUtil.splitStringUsingSpacesCapped(descriptor);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getDescription");
		modelOperation.setResultType("String");
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		Buf buf = new Buf();
		buf.putLine2("return \""+description+"\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	/**
	 * Invocation operation
	 * --------------------
	 */
	protected ModelOperation createInvocationOperation(ModelClass modelClass, Service service, Element element, Operation operation) throws Exception {
		ModelOperation modelOperation = createModelOperation(service, operation);
		//boolean isOneway = modelOperation.getResultType().equals("void");
		if (ServiceUtil.isStateful(service)) {
			modelOperation.addInitialSource(actionClassProvider.getSource_InvokeProcess(modelClass, service, operation));
		} else {
			modelOperation.addInitialSource(actionClassProvider.getSource_InvokeRepository(modelClass, service, operation));
		}
		return modelOperation;
	}
	
	protected ModelOperation createActionOperation(Invocation action) {
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
