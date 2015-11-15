package aries.codegen.util;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Command;
import nam.model.Element;
import nam.model.Fault;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Project;
import nam.model.Reference;
import nam.model.Result;
import nam.model.Service;
import nam.model.Type;
import nam.model.src.main.java.DummyValueFactory;
import nam.model.statement.DoneCommand;
import nam.model.util.CommandUtil;
import nam.model.util.ElementUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProcessUtil;
import nam.model.util.ResultUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;

import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


public class CodeUtil {

	public static GenerationContext context;
	
	public static DummyValueFactory dummyValueFactory;
	
	
	public static Map<String, Namespace> getImportedNamespaces(Module module) {
		//first try the module namespace
		Namespace namespace = context.getNamespaceByUri(module.getNamespace());
		//TODO do we need to enforce this to be notNull ?
		if (namespace == null)
			return new HashMap<String, Namespace>();
		Map<String, Namespace> importedNamespaces = NamespaceUtil.getImportedNamespaces(namespace);
		if (importedNamespaces.size() == 0) {
			//otherwise try the project namespace
			Project project = context.getProject();
			namespace = context.getNamespaceByUri(project.getNamespace());
			importedNamespaces = NamespaceUtil.getImportedNamespaces(namespace);
		}
		return importedNamespaces;
	}
	
	public static Collection<String> getImportedClasses(Service service) {
		List<String> classes = new ArrayList<String>();
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			classes.addAll(getImportedClasses(service, operation));
		}
		return classes;
	}

	public static Collection<String> getImportedClasses(Service service, Operation operation) {
		List<String> classes = new ArrayList<String>();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String qualifiedName = TypeUtil.getJavaName(parameter.getType());
			classes.add(qualifiedName);
			String structure = parameter.getConstruct();
			String collectionClass = getImportedClass(structure);
			if (collectionClass != null)
				classes.add(collectionClass);
		}
		List<Result> results = operation.getResults();
		Result result = null;
		if (results.size() > 0)
			result = results.get(0);
		if (result != null) {
			String structure = result.getConstruct();
			String qualifiedName = TypeUtil.getJavaName(result.getType());
			classes.add(qualifiedName);
			String collectionClass = getImportedClass(structure);
			if (collectionClass != null)
				classes.add(collectionClass);
		}
		return classes;
	}

	public static String getImportedClass(String structure) {
		if (structure.equalsIgnoreCase("list")) {
			return "java.util.List";
		} else if (structure.equalsIgnoreCase("set")) {
			return "java.util.Set";
		} else if (structure.equalsIgnoreCase("map")) {
			return "java.util.Map";
		}
		return null;
	}
	
	public static Collection<String> getImportedClasses(Command command) {
		List<String> classes = new ArrayList<String>();
		List<Parameter> parameters = command.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			classes.addAll(getImportedClasses(parameter));
		}
		return classes;
	}
	
	public static Collection<String> getImportedClasses(Parameter parameter) {
		List<String> classes = new ArrayList<String>();
		String type = parameter.getType();
		Element element = context.getElementByType(type);
		if (element != null) {
			Assert.notNull(element, "Element not found: "+type);
			classes.addAll(getImportedClasses(element));
		}
		return classes;
	}

	public static Collection<String> getImportedClasses(Element element) {
		List<String> classes = new ArrayList<String>();
		String type = element.getType();
		classes.add(TypeUtil.getJavaName(type));
		List<Reference> fields = ElementUtil.getReferences(element);
		classes.addAll(getImportedClasses(fields));
		return classes;
	}

	public static Collection<String> getImportedClasses(List<Reference> fields) {
		List<String> classes = new ArrayList<String>();
		Iterator<Reference> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Reference field = iterator.next();
			classes.addAll(getImportedClasses(field));
		}
		return classes;
	}

	public static Collection<String> getImportedClasses(Reference field) {
		List<String> classes = new ArrayList<String>();
		String type = field.getType();
		classes.add(TypeUtil.getJavaName(type));
		if (!TypeUtil.isDefaultType(type)) {
			Element element = context.getElementByType(type);
			Assert.notNull(element, "Element not found: "+type);
			classes.addAll(getImportedClasses(element));
		}
		return classes;
	}

	public static String getGetterName(String fieldName) {
		return "get"+NameUtil.capName(fieldName);
	}
	
	public static String getSetterName(String fieldName) {
		return "set"+NameUtil.capName(fieldName);
	}

	public static String generateConstructor(String className) {
		return generateConstructor(className, null);
	}
	
	//TODO add type checking of parameterValue into here
	public static String generateConstructor(String className, String parameterValue) {
		if (className.equals("Integer")) {
			if (parameterValue == null)
				return "Integer(0)";
			return "Integer("+parameterValue+")";
		}
		if (className.equals("Double")) {
			if (parameterValue == null)
				return "Double(0)";
			return "Double("+parameterValue+")";
		}
		if (className.equals("Float")) {
			if (parameterValue == null)
				return "Float(0)";
			return "Float("+parameterValue+")";
		}
		if (className.equals("Short")) {
			if (parameterValue == null)
				return "Short((short) 0)";
			return "Short("+parameterValue+")";
		}
		if (parameterValue == null)
			return className+"()";
		return className+"("+parameterValue+")";
	}

	public static String generateParameterString(Operation operation) {
		Buf buf = new Buf();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			if (i > 0)
				buf.put(", ");
			buf.put(parameterName);
		}
		return buf.get();
	}
	
	public static String getAssertionsForParameters(int indent, Operation operation) {
		Buf buf = new Buf();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String name = parameter.getName();
			String nameCapped = NameUtil.capName(name);
			buf.putLine(indent, "Assert.notNull("+name+", \""+nameCapped+" must be specified\");");
		}
		return buf.get();
	}
	
	
	public static String getVariableName(Link link) {
		String name = NameUtil.getFilteredName(link.getName());
		return name;
	}
	
	public static String getOperationName(Link link) {
		String name = NameUtil.toCamelCase(link.getName());
		name = "isSet"+NameUtil.capName(name);
		return name;
	}

	public static String getOperationName(Activity activity) {
		String activityName = activity.getName();
		if (activityName != null) {
			String name = NameUtil.uncapName(activityName);
			return name;
		
		} else if (activity instanceof Assign) {
			Assign assignActivity = (Assign) activity;
			assignActivity.getName();
			//TODO
			return null;
		
//		} else if (activity instanceof If) {
//			If ifActivity = (If) activity;
//			return ifActivity.getName();

		} else if (activity instanceof Invoke) {
			Invoke invokeActivity = (Invoke) activity;
			//QName qName = invokeActivity.getPortType().getQName();
			//String typeName = TypeUtil.getTypeFromQName(qName);
			//String localPart = TypeUtil.getLocalPart(typeName);
			//String name = "invoke"+NameUtil.capName(localPart);
			String operationName = invokeActivity.getOperation().getName();
			String name = "invoke"+NameUtil.capName(operationName);
			return name;
		
		} else if (activity instanceof Receive) {
			Receive receiveActivity = (Receive) activity;
			//QName qName = receiveActivity.getPortType().getQName();
			//String typeName = TypeUtil.getTypeFromQName(qName);
			//String localPart = TypeUtil.getLocalPart(typeName);
			//String name = "receive"+NameUtil.capName(localPart);
			//String name = "receive"+NameUtil.capName(localPart)+"Request";
			String name = "receive" + NameUtil.capName(receiveActivity.getOperation().getName());
			return name;
		
		} else if (activity instanceof Reply) {
			Reply replyActivity = (Reply) activity;
			//QName qName = replyActivity.getPortType().getQName();
			//String typeName = TypeUtil.getTypeFromQName(qName);
			//String localPart = TypeUtil.getLocalPart(typeName);
			//String name = "reply"+NameUtil.capName(localPart);
			String name = "reply" + NameUtil.capName(replyActivity.getOperation().getName());
			return name;
		
		} else if (activity instanceof Sequence) {
			Sequence sequenceActivity = (Sequence) activity;
			//we are assuming name for sequence is specified for now
			String name = NameUtil.capName(sequenceActivity.getName());
			return name;
		
		} else {
			//TODO others add as needed
			throw new RuntimeException("GetOperationName: unsupported action: "+activity.getClass().getSimpleName());
		}
	}

	public static String getVariableName(Variable variable) {
		return getVariableName(variable, true);
	}
	
	public static String getVariableName(Variable variable, boolean isGlobal) {
		String name = null;
		if (variable.getMessageType() != null) {
			Message message = variable.getMessageType();
			@SuppressWarnings("unchecked") List<Part> parts = message.getEParts();
			Assert.isTrue(parts.size() == 1, "Message should only have one part here");
			Part part = parts.get(0);
			//String messageName = TypeUtil.getNameFromMessage(message);
			//String name = messageName+"_"+part.getName();
			name = variable.getName()+"_"+part.getName();
			
		} else {
			name = variable.getName();
		}
		
		if (isGlobal)
			name = "this."+name;
		return name;
	}

	public static String getProcessServiceName(Process process) {
		return getProcessServiceName(process.getActivity());
	}

	public static String getProcessServiceName(Activity activityBlock) {
		Receive receiveActivity = getProcessReceiveActivity(activityBlock);
		String operationName = getOperationName(receiveActivity);
		return operationName;
	}

	public static String getProcessOperationName(Process process) {
		return getProcessOperationName(process.getActivity());
	}

	public static String getProcessOperationName(Activity activityBlock) {
		Receive receiveActivity = getProcessReceiveActivity(activityBlock);
		String operationName = getOperationName(receiveActivity);
		return operationName;
	}

	public static Receive getProcessReceiveActivity(Process process) {
		return getProcessReceiveActivity(process.getActivity());
	}
	
	public static Receive getProcessReceiveActivity(Activity activityBlock) {
		List<Activity> activities = getActivities(activityBlock);
		Assert.notNull(activities, "Activities must exist");
		//assuming if activities exist in block then 
		//also has at least one activity in block
		Activity firstActivity = activities.get(0);
		if (firstActivity instanceof Receive) {
			Receive receiveActivity = (Receive) firstActivity;
			return receiveActivity;
		}
		if (firstActivity instanceof Sequence || firstActivity instanceof Flow) {
			getProcessReceiveActivity(firstActivity);
		}
		return null;
	}

	public static List<Activity> getActivities(Activity activity) {
		List<Activity> activities = null;
		if (activity instanceof Sequence) {
			Sequence sequence = (Sequence) activity;
			activities = sequence.getActivities();
		} else if (activity instanceof Flow) {
			Flow flow = (Flow) activity;
			activities = flow.getActivities();
		}
		return activities;
	}


	public static void addSerialVersionUIDField(ModelClass modelClass) {
		modelClass.getImplementedInterfaces().contains("java.io.Serializable");
		modelClass.addStaticAttribute(createSerialVersionUIDAttribute());
	}

	public static ModelAttribute createSerialVersionUIDAttribute() {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.FINAL + Modifier.STATIC);
		//attribute.setPackageName(long.class.getPackage().getName());
		attribute.setClassName(long.class.getName());
		attribute.setName("serialVersionUID");
		attribute.setDefault("1L");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public static void addStaticLoggerField(ModelClass modelClass, String className) {
		modelClass.addImportedClass("org.apache.commons.logging.Log");
		modelClass.addImportedClass("org.apache.commons.logging.LogFactory");
		modelClass.addStaticAttribute(createStaticLoggerAttribute(className));
	}

	public static ModelAttribute createStaticLoggerAttribute(String className) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.FINAL + Modifier.STATIC);
		attribute.setPackageName("org.apache.commons.logging");
		attribute.setClassName("Log");
		attribute.setName("log");
		attribute.setDefault("LogFactory.getLog("+className+".class)");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	public static void addSessionContextField(ModelClass modelClass, Service service) {
		modelClass.addInstanceAttribute(createSessionContextAttribute(service));
		modelClass.addImportedClass("javax.annotation.Resource");
		modelClass.addImportedClass("javax.ejb.SessionContext");
	}
	
	public static ModelAttribute createSessionContextAttribute(Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.addAnnotation(AnnotationUtil.createAnnotation("Resource"));
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("javax.ejb");
		attribute.setClassName("SessionContext");
		attribute.setName("sessionContext");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	public static void addMessageDrivenContextField(ModelClass modelClass, Service service) {
		modelClass.addInstanceAttribute(createMessageDrivenContextAttribute(service));
		modelClass.addImportedClass("javax.annotation.Resource");
		modelClass.addImportedClass("javax.ejb.MessageDrivenContext");
	}
	
	public static ModelAttribute createMessageDrivenContextAttribute(Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.addAnnotation(AnnotationUtil.createAnnotation("Resource"));
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("javax.ejb");
		attribute.setClassName("MessageDrivenContext");
		attribute.setName("messageDrivenContext");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	public static void addWebServiceContextField(ModelClass modelClass, Service service) {
		modelClass.addInstanceAttribute(createWebServiceContextAttribute(service));
		modelClass.addImportedClass("javax.annotation.Resource");
		modelClass.addImportedClass("javax.xml.ws.WebServiceContext");
		//modelClass.addImportedClass("javax.xml.ws.handler.MessageContext");
	}

	public static ModelAttribute createWebServiceContextAttribute(Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.addAnnotation(AnnotationUtil.createAnnotation("Resource"));
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("javax.xml.ws");
		attribute.setClassName("WebServiceContext");
		attribute.setName("webServiceContext");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	public static ModelAttribute createAttribute_Mutex() {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("java.lang");
		attribute.setClassName("Object");
		attribute.setName("mutex");
		attribute.setDefault("new Object()");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public static ModelAttribute createAttribute_CorrelationId(Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("java.lang");
		attribute.setClassName("String");
		attribute.setName("correlationId");
		attribute.setGenerateGetter(true);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	public static void addTransactionSynchronizationRegistryField(ModelClass modelClass) {
		modelClass.addInstanceAttribute(createTransactionSynchronizationRegistryField());
		modelClass.addImportedClass("javax.annotation.Resource");
		modelClass.addImportedClass("javax.transaction.TransactionSynchronizationRegistry");
	}
	
	public static ModelAttribute createTransactionSynchronizationRegistryField() {
		ModelAttribute attribute = new ModelAttribute();
		attribute.addAnnotation(AnnotationUtil.createAnnotation("Resource"));
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("javax.transaction");
		attribute.setClassName("TransactionSynchronizationRegistry");
		attribute.setName("transactionSynchronizationRegistry");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public static void addCacheUnitManagerField(ModelClass modelClass, String namespace, Cache cache) {
		modelClass.addInstanceAttribute(createCacheUnitManagerField(namespace, cache));
	}

	public static ModelAttribute createCacheUnitManagerField(String namespace, Cache cache) {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cache);
		String className = DataLayerHelper.getCacheUnitInterfaceName(cache) + "Manager";
		String beanName = NameUtil.uncapName(className);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName(beanName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	

	public static void addConstructor(ModelClass modelClass, String code) {
		addConstructor(modelClass, (ModelParameter) null, new String[] {code});
	}

	public static void addConstructor(ModelClass modelClass, String parameterType, String parameterName, String code) {
		ModelParameter modelParameter = createParameter(parameterType, parameterName);
		addConstructor(modelClass, modelParameter, new String[] {code});
	}

	public static void addConstructor(ModelClass modelClass, ModelParameter modelParameter, String code) {
		ModelParameter[] parameters = new ModelParameter[] {modelParameter};
		addConstructor(modelClass, parameters, new String[] {code});
	}

	public static void addConstructor(ModelClass modelClass, ModelParameter[] modelParameters, String code) {
		addConstructor(modelClass, modelParameters, new String[] {code});
	}

	public static void addConstructor(ModelClass modelClass, ModelParameter modelParameter, String[] code) {
		if (modelParameter != null) {
			ModelParameter[] parameters = new ModelParameter[] {modelParameter};
			addConstructor(modelClass, parameters, code);
		} else {
			addConstructor(modelClass, (ModelParameter[]) null, code);
		}
	}
	
	public static void addConstructor(ModelClass modelClass, ModelParameter[] modelParameters, String[] code) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		if (modelParameters != null)
			for (int i = 0; i < modelParameters.length; i++) {
				modelConstructor.addParameter(modelParameters[i]);
				modelClass.addImportedClass(modelParameters[i]);
			}
		Buf buf = new Buf();
		for (int i = 0; i < code.length; i++)
			buf.putLine2(code[i]);
		modelConstructor.addInitialSource(buf.get());
		modelClass.addInstanceConstructor(modelConstructor);
	}

	public static ModelOperation createOperation(Operation operation) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		List<Result> results = operation.getResults();
		Result result = null;
		if (results.size() > 0)
			result = results.get(0);
		if (result != null) {
			modelOperation.setResultType(getResultType(result));
			modelOperation.setResultName(getResultName(result));
		} else modelOperation.setResultType("void");
		modelOperation.setName(operation.getName());
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			Parameter parameter = parameterIterator.next();
			//if (!parameter.getType().contains("{"))
			//	System.out.println();
			ModelParameter modelParameter = createParameter(parameter);
			//ModelParameter modelParameter = createParameterFromType(parameter.getConstruct(), parameter.getType(), parameter.getName());
			modelOperation.addParameter(modelParameter);
		}
		List<Fault> faults = operation.getFaults();
		Iterator<Fault> faultIterator = faults.iterator();
		while (faultIterator.hasNext()) {
			Fault fault = faultIterator.next();
			String faultPackageName = TypeUtil.getPackageName(fault.getType());
			String faultClassName = TypeUtil.getClassName(fault.getType());
			//modelClass.addImportedClass(faultPackageName+"."+faultClassName);
			modelOperation.addException(faultClassName);
		}
		//if (result != null)
		//	modelOperation.addSourceCode("\t\treturn null;\n");
		return modelOperation; 
	}

	public static ModelOperation createMethod_GetCorrelationId(ModelUnit modelUnit, boolean isInterface) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getCorrelationId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("java.lang.String");
		//if (isInterface)
			//TODO
		return modelOperation;
	}

	public static ModelOperation createMethod_SetCorrelationId(ModelUnit modelUnit, boolean isInterface) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("setCorrelationId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(CodeUtil.createParameter_CorrelationId());
		//if (isInterface)
			//TODO
		return modelOperation;
	}

	public static ModelOperation createMethod_GetTransactionId(ModelUnit modelUnit, boolean isInterface) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getTransactionId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("java.lang.String");
		//if (isInterface)
			//TODO
		return modelOperation;
	}

	public static ModelOperation createMethod_SetTransactionId(ModelUnit modelUnit, boolean isInterface) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("setTransactionId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(CodeUtil.createParameter_TransactionId());
		//if (isInterface)
			//TODO
		return modelOperation;
	}

	public static Collection<ModelOperation> sortOperations(Collection<ModelOperation> modelOperations) {
		List<ModelOperation> sortedOperations = new ArrayList<ModelOperation>(modelOperations);
		Collections.sort(sortedOperations);
		return sortedOperations;
	}


	public static String getResultType(Result result) {
		if (result == null)
			return null;
		String type = result.getType();
		String key = result.getKey();
		String className = TypeUtil.getClassName(type);
		String keyClassName = key != null ? TypeUtil.getClassName(key) : "Object";
		String construct = ResultUtil.getConstruct(result);
		if (construct.equals("item")) {
			return className;
		} else if (construct.equals("list")) {
			return "List<"+className+">";
		} else if (construct.equals("set")) {
			return "Set<"+className+">";
		} else if (construct.equals("map")) {
			return "Map<"+keyClassName+", "+className+">";
		}
		return null;
	}

	public static String getResultTypeName(Result result) {
		if (result == null)
			return null;
		String typeName = TypeUtil.getLocalPart(result.getType());
		if (CodeGenUtil.isJavaDefaultType(typeName))
			typeName = result.getName();
		//if (typeName == null)
		//	typeName = "response";
		String structure = result.getConstruct();
//		if (structure.equals("list"))
//			typeName += "List";
//		if (structure.equals("set"))
//			typeName += "Set";
//		if (structure.equals("map"))
//			typeName += "Map";
		return typeName;
	}
	
	public static String getStructuredName(String structure, String name) {
		if (structure.equals("list"))
			name += "List";
		if (structure.equals("set"))
			name += "Set";
		if (structure.equals("map"))
			name += "Map";
		return name;
	}
	
	
//	public static void assureResultType(Result result) {
//		String type = result.getType();
//
//		String className = TypeUtil.getClassName(type);
////		if (ClassUtil.isJavaPrimitiveType(className) || ClassUtil.isJavaDefaultType(className)) {
////			result.setType(TypeUtil.getJavaName(type));
////			return;
////		}
//
//		String construct = result.getConstruct();
//		if (construct.equals("item")) {
//			result.setType(className);
//		} else if (construct.equals("list")) {
//			result.setType("List<"+className+">");
//		} else if (construct.equals("set")) {
//			result.setType("Set<"+className+">");
//		} else if (construct.equals("map")) {
//			result.setType("Map<Object, "+className+">");
//		}
//	}
	
	
	public static String getResultName(Result result) {
		String name = result.getName();
		String type = result.getType();

		if (StringUtils.isEmpty(name)) {
			String className = TypeUtil.getClassName(type);
//			if (className == null)
//				System.out.println();
			if (ClassUtil.isJavaPrimitiveType(className) || ClassUtil.isJavaDefaultType(className)) {
				return "result";
			}

			name = NameUtil.uncapName(className);
			String construct = result.getConstruct();
			if (construct.equals("item")) {
				return name;
			} else if (construct.equals("list")) {
				return name+"List";
			} else if (construct.equals("set")) {
				return name+"Set";
			} else if (construct.equals("map")) {
				return name+"Map";
			}
		}
		
		return name;
	}
	

	public static List<String> getArgumentList(Command command) {
		List<String> arguments = new ArrayList<String>(command.getTokens());
		List<Parameter> parameters = command.getParameters();
		Iterator<Parameter> iterator2 = parameters.iterator();
		while (iterator2.hasNext()) {
			Parameter parameter = iterator2.next();
			arguments.add(parameter.getName());
		}
		return arguments;
	}
	
	public static List<String> getArgumentList(ModelOperation modelOperation) {
		List<ModelParameter> modelParameters = modelOperation.getParameters();
		List<String> arguments = new ArrayList<String>(modelParameters.size());
		Iterator<ModelParameter> iterator = modelParameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ModelParameter modelParameter = iterator.next();
			arguments.add(modelParameter.getName());
		}
		return arguments;
	}
	
	public static String getArgumentString(Command command) {
		return getArgumentString(command.getParameters());
	}
	
	public static String getArgumentString(List<Parameter> parameters) {
		Buf buf = new Buf();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(parameter.getName());
		}
		return buf.get();
	}

	public static String getArgumentString(Operation operation) {
		Buf buf = new Buf();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(parameter.getName());
		}
		return buf.get();
	}
	
	public static String getArgumentString(ModelOperation modelOperation) {
		Buf buf = new Buf();
		List<ModelParameter> modelParameters = modelOperation.getParameters();
		Iterator<ModelParameter> iterator = modelParameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ModelParameter modelParameter = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(modelParameter.getName());
		}
		return buf.get();
	}

	public static List<ModelParameter> createParameters(List<Parameter> parameters) {
		List<ModelParameter> modelParameters = new ArrayList<ModelParameter>();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			ModelParameter modelParameter = createParameter(parameter);
			modelParameters.add(modelParameter);
		}
		return modelParameters;
	}
	
	public static ModelParameter createParameter(Parameter parameter) {
		return createParameterFromType(parameter.getConstruct(), parameter.getType(), parameter.getKey(), parameter.getName());
	}
	
	public static ModelParameter createParameter(List<Parameter> parameters, Parameter parameter) {
		return createParameterFromType(parameter.getConstruct(), parameter.getType(), parameter.getKey(), parameter.getName());
	}

	public static ModelParameter createParameter(String parameterQualifiedName, String parameterName) {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setPackageName(NameUtil.getPackageName(parameterQualifiedName));
		modelParameter.setClassName(NameUtil.getClassName(parameterQualifiedName));
		modelParameter.setName(parameterName);
		return modelParameter;
	}

	public static ModelParameter createParameter(String packageName, String className, String name) {
		return createParameter("item", packageName, className, name);
	}
	
	public static ModelParameter createParameter(String structure, String packageName, String className, String name) {
		return createParameter(structure, packageName, null, className, name);
	}
	
	public static ModelParameter createParameter(String structure, String packageName, String keyClassName, String className, String name) {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setConstruct(structure);
		modelParameter.setPackageName(packageName);
		modelParameter.setKeyClassName(keyClassName);
		modelParameter.setClassName(className);
		modelParameter.setName(name);
		return modelParameter;
	}

	public static ModelParameter createParameterFromType(String type, String name) {
		return createParameterFromType("item", type, null, name);
	}

	public static ModelParameter createParameterFromType(String construct, String type, String key, String name) {
		String javaType = TypeUtil.getJavaName(type);
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setClassName(NameUtil.getClassName(javaType));
		modelParameter.setPackageName(NameUtil.getPackageName(javaType));
		if (key != null) {
			String javaKeyType = TypeUtil.getJavaName(key);
			modelParameter.setKeyClassName(NameUtil.getClassName(javaKeyType));
			modelParameter.setKeyPackageName(NameUtil.getPackageName(javaKeyType));
		}
		if (javaType.endsWith("Message") && !name.endsWith("Message"))
			name += "Message";
		modelParameter.setName(name);
		modelParameter.setConstruct(construct);
		return modelParameter;
	}
	
	public static ModelParameter createParameter_ServiceId() {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setPackageName("java.lang");
		modelParameter.setClassName("String");
		modelParameter.setName("serviceId");
		return modelParameter;
	}
	
	public static ModelParameter createParameter_HostName() {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setPackageName("java.lang");
		modelParameter.setClassName("String");
		modelParameter.setName("host");
		return modelParameter;
	}

	public static ModelParameter createParameter_PortNumber() {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setPackageName("java.lang");
		modelParameter.setClassName(int.class.getName());
		modelParameter.setName("port");
		return modelParameter;
	}

	public static ModelParameter createParameter_CorrelationId() {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setPackageName("java.lang");
		modelParameter.setClassName("String");
		modelParameter.setName("correlationId");
		return modelParameter;
	}

	public static ModelParameter createParameter_TransactionId() {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setPackageName("java.lang");
		modelParameter.setClassName("String");
		modelParameter.setName("transactionId");
		return modelParameter;
	}


	public static String getParameterCreationSource(Operation operation) {
		return getParameterCreationSource(operation, false);
	}
	
	public static String getEmptyParameterCreationSource(Operation operation) {
		return getParameterCreationSource(operation, true);
	}
	
	public static String getParameterCreationSource(Operation operation, boolean isEmpty) {
		Buf buf = new Buf();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterType = parameter.getType();
			String parameterBeanName = parameter.getName();
			String parameterClassName = TypeUtil.getClassName(parameterType);
			String structure = parameter.getConstruct();
			boolean isCollection = !structure.equals("item");
			
			if (ClassUtil.isJavaDefaultType(parameterClassName)) {
				String dummyValue = dummyValueFactory.getDummyValue(parameterBeanName, parameterType, false, isCollection, false);
				buf.putLine2(parameterClassName + " " +parameterBeanName + " = new " + parameterClassName + "(" + dummyValue+");");
			} else {
				//String parameterPackageName = TypeUtil.getPackageName(parameterType);
				String fixtureName = ModelLayerHelper.getModelFixtureBeanName(parameterType);
				//String fixtureName = FixtureUtil.getFixtureNameFromPackageName(parameterPackageName);
				if (isEmpty)
					buf.putLine2(parameterClassName + " " +parameterBeanName + " = " + fixtureName+".createEmpty_"+parameterClassName+"();");
				else buf.putLine2(parameterClassName + " " +parameterBeanName + " = " + fixtureName+".create_"+parameterClassName+"();");
			}
		}	
		return buf.get();
	}
	
	public static String createCheckpointsForParameters(Operation operation) {
		return createCheckpointsForParameters(2, operation);
	}
	
	public static String createCheckpointsForParameters(int indent, Operation operation) {
		Buf buf = new Buf();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String checkpointName = parameter.getName();
			//if (checkpointName.endsWith("Message"))
			//	checkpointName = checkpointName.substring(0, checkpointName.length()-7);
			buf.putLine(indent, "Check.isValid(\""+checkpointName+"\", "+parameterName+");");
		}
		return buf.get();
	}

	
	public static void addPrivateStaticField(ModelClass modelClass, String fieldType, String fieldName) {
		addField(modelClass, Modifier.PRIVATE + Modifier.STATIC, fieldType, fieldName, null);
	}
	
	public static void addField(ModelClass modelClass, int modifiers, String fieldType, String fieldName, String fieldValue) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(modifiers);
		attribute.setPackageName(NameUtil.getPackageName(fieldType));
		attribute.setClassName(NameUtil.getClassName(fieldType));
		attribute.setName(fieldName);
		attribute.setType(fieldType);
		attribute.setDefault(fieldValue);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelClass.addImportedClass(fieldType);
		modelClass.addInstanceAttribute(attribute);
	}

	public static String getShortName(List<Parameter> parameters, Parameter parameter) {
		String shortName = getShortName(parameter);
		Iterator<Parameter> iterator = parameters.iterator();
		int count = 0;
		while (iterator.hasNext()) {
			Parameter p = iterator.next();
			if (p == parameter)
				continue;
			if (shortName.equals(getShortName(p)))
				count++;
		}
		if (count > 0)
			return shortName+count;
		return shortName;
	}

	public static String getShortName(Parameter parameter) {
		return getShortName(parameter.getName());
	}
	
	public static String getShortName(String camelCaseName) {
		for (int i = camelCaseName.length()-1; i > 0; i--) {
			if (Character.isUpperCase(camelCaseName.charAt(i))) {
				return camelCaseName.substring(i).toLowerCase();
			}
		}
		return camelCaseName;
	}
	
	
	
//	public static void addMethod(ModelClass modelClass, String code) {
//		addConstructor(modelClass, (ModelParameter) null, new String[] {code});
//	}
//
//	public static void addMethod(ModelClass modelClass, String parameterType, String parameterName, String code) {
//		ModelParameter modelParameter = createParameter(parameterType, parameterName);
//		addMethod(modelClass, modelParameter, new String[] {code});
//	}
//
//	public static void addMethod(ModelClass modelClass, ModelParameter modelParameter, String code) {
//		ModelParameter[] parameters = new ModelParameter[] {modelParameter};
//		addMethod(modelClass, parameters, new String[] {code});
//	}
//
//	public static void addMethod(ModelClass modelClass, ModelParameter[] modelParameters, String code) {
//		addMethod(modelClass, modelParameters, new String[] {code});
//	}
//
//	public static void addMethod(ModelClass modelClass, ModelParameter modelParameter, String[] code) {
//		if (modelParameter != null) {
//			ModelParameter[] parameters = new ModelParameter[] {modelParameter};
//			addMethod(modelClass, parameters, code);
//		} else {
//			addMethod(modelClass, (ModelParameter[]) null, code);
//		}
//	}
//	
//	public static void addMethod(ModelClass modelClass, ModelParameter[] modelParameters, String[] code) {
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		if (modelParameters != null)
//			for (int i = 0; i < modelParameters.length; i++) {
//				modelOperation.addParameter(modelParameters[i]);
//				modelClass.addImportedClass(modelParameters[i]);
//			}
//		Buf buf = new Buf();
//		for (int i = 0; i < code.length; i++)
//			buf.putLine2(code[i]);
//		modelOperation.addInitialSource(buf.get());
//		modelClass.addInstanceOperation(modelOperation);
//	}

	public static String getSourceFromTokens(List<String> tokens) {
		Buf buf = new Buf();
		Iterator<String> iterator = tokens.iterator();
		while (iterator.hasNext()) {
			String token = iterator.next();
			buf.put(token);
			if (token.equals("new"))
				buf.put(" ");
		}
		return buf.get();
	}
	
	public static String createMethodSource(String code) {
		Buf buf = new Buf();
		if (code.length() > 0)
			buf.put2(code);
		return buf.get();
	}

	public static String createMethodSource(List<String> code) {
		String[] sourceArray = new String[code.size()];
		String[] sourceArrayRef = code.toArray(sourceArray);
		return createMethodSource(sourceArrayRef);
	}
	
	public static String createMethodSource(String[] code) {
		Buf buf = new Buf();
		for (int i = 0; i < code.length; i++)
			buf.putLine2(code[i]);
		return buf.get();
	}

	public static String getDefaultValueForType(String type) {
		String className = TypeUtil.getClassName(type);
		if (CodeGenUtil.isJavaDefaultType(className)) {
			className = className.toLowerCase();
			if (className.startsWith("int")) {
				return "0";
			} else if (className.equals("short")) {
				return "0";
			} else if (className.equals("long")) {
				return "0L";
			} else if (className.equals("double")) {
				return "0D";
			} else if (className.equals("float")) {
				return "0F";
			} else if (className.equals("boolean")) {
				return "false";
			} else if (className.equals("byte")) {
				return "0";
			} else if (className.startsWith("char")) {
				return "\'\0\'";
			}
		}
		return "null";
	}

	public static boolean isOneway(Operation operation) {
		List<Result> results = operation.getResults();
		if (results.size() == 0)
			return true;
		Result result = results.get(0);
		if (result.getType().equals("void"))
			return true;
		return false;
	}

	
	public static boolean isPhoneNumberField(Field field) {
		String fieldNameUncapped = NameUtil.uncapName(field.getName());
		if ((fieldNameUncapped.equals("homePhone") || 
			fieldNameUncapped.equals("workPhone") || 
			fieldNameUncapped.equals("cellPhone") ||
			fieldNameUncapped.equals("mobilePhone") ||
			fieldNameUncapped.equals("otherPhone") ||
			fieldNameUncapped.equals("faxPhone")))
				return true;
		return false;
	}

	public static boolean hasPhoneNumberField(Type type) {
		if (!ElementUtil.isElement(type))
			return false;
		Element element = (Element) type;
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			//Element fieldElement = context.getElementByType(field.getType());
			if (CodeUtil.isPhoneNumberField(field))
				return true;
		}
		return false;
	}

	public static boolean hasKeyElement(Type type) {
		Element keyElement = getKeyElement(type);
		return keyElement != null;
	}
	
	public static Element getKeyElement(Type type) {
		String keyType = type.getType()+"Key";
		Element keyElement = context.getElementByType(keyType);
		return keyElement;
	}
	
	public static boolean hasCriteriaElement(Type type) {
		Element criteriaElement = getCriteriaElement(type);
		return criteriaElement != null;
	}
	
	public static Element getCriteriaElement(Type type) {
		String criteriaType = type.getType()+"Criteria";
		Element criteriaElement = context.getElementByType(criteriaType);
		return criteriaElement;
	}

	public static boolean hasDoneCommand(Service service, Pathway pathway) {
		if (pathway != null && pathway.hasDoneCommand())
			return true;
		nam.model.Process process = service.getProcess();
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		Operation actualServiceOperation = ProcessUtil.getOperation(process, serviceOperationName);
		Collection<Command> commands = actualServiceOperation.getCommands();
		Collection<DoneCommand> doneCommands = CommandUtil.getCommands(commands, DoneCommand.class);
		return doneCommands.size() > 0;
	}
	

	//TODO merge this with ElementUtil
	
	public static boolean hasField(Element element, String fieldName) {
		return hasField(element, fieldName, false);
	}
	
	public static boolean hasField(Element element, String fieldName, boolean recurse) {
		Field field = getField(element, fieldName, recurse);
		return field != null;
	}

	public static Field getField(Element element, String fieldName) {
		return getField(element, fieldName, false);
	}
	
	public static Field getField(Element element, String fieldName, boolean recurse) {
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field.getName().equalsIgnoreCase(fieldName))
				return field;
		}
		if (recurse) {
			String superType = element.getExtends();
			if (superType != null) {
				Element superTypeElement = context.getElementByName(superType);
				if (superTypeElement != null) {
					return getField(superTypeElement, fieldName, recurse);
				}
			}
		}
		return null;
	}
	

	
}
