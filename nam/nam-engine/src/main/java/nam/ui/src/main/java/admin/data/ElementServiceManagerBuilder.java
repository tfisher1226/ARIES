package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;


/**
 * Builds an Element Record Service Execution Manager Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementServiceManagerBuilder extends AbstractElementManagerBuilder {

	public ElementServiceManagerBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public ModelClass buildClass(Type type) throws Exception {
		if (!ElementUtil.isRoot(type))
			return null;
		
		Element element = (Element) type;
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "ServiceManager";

		//TODO use other ways to  find the name of a Service for Element
		Service service = context.getServiceByName(elementNameUncapped+"Service");
		if (service == null)
			return null;

		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		modelClass.setParentClassName("AbstractInvocationManager<"+elementName+">");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeClassAnnotations(modelClass, element);
		initializeClassConstructors(modelClass, element);
		//initializeInstanceFields(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Element element) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		
		if (ElementUtil.isRoot(element)) {
		} else if (ElementUtil.isElement(element)) {
		} else if (ElementUtil.isEnumeration(element)) {
		}
		
		//modelClass.addImportedClass("javax.faces.model.DataModel");

		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.ui.AbstractInvocationManager");
		modelClass.addImportedClass("org.aries.ui.InvocationValues");

		modelClass.addImportedClass("org.jboss.seam.ScopeType");
		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
		//modelClass.addImportedClass("org.jboss.seam.annotations.Create");
		//modelClass.addImportedClass("org.jboss.seam.annotations.Begin");
		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
		modelClass.addImportedClass("org.jboss.seam.annotations.Observer");
		//modelClass.addImportedClass("org.jboss.seam.annotations.Role");
		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
		modelClass.addImportedClass("org.jboss.seam.annotations.Startup");
		
		modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
		
		modelClass.addImportedClass("java.io.Serializable");
		modelClass.addImportedClass("java.util.Collection");
		//modelClass.addImportedClass("java.util.Comparator");
		modelClass.addImportedClass("java.util.List");
		//modelClass.addImportedClass("java.util.Map");
		super.initializeImportedClasses(modelClass);
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type element) throws Exception {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		//modelClass.getClassAnnotations().add(AnnotationUtil.createStartupAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createAnnotation("AutoCreate"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createNameAnnotation(elementNameUncapped+"ServiceManager"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createRoleAnnotation("main"+elementNameCapped+"SelectManager", ScopeType.SESSION));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createRestrictAnnotation("#{identity.loggedIn}"));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressSerialWarning());
	}

	
	/*
	 * Class Constructor(s)
	 */
	
	protected void initializeClassConstructors(ModelClass modelClass, Type element) throws Exception {
		modelClass.addInstanceConstructor(createConstructor(element));
	}
	
	protected ModelConstructor createConstructor(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		Service service = context.getServiceByName(elementNameUncapped+"Service");
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			String operationName = NameUtil.capName(operation.getName());
			buf.putLine2("initialize"+operationName+"();");
		}
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Element element) throws Exception {
		if (ElementUtil.isRoot(element))
			modelClass.addInstanceOperation(createOperation_getServiceMethod(element));
		modelClass.addInstanceOperation(createOperation_getInfoManagerMethod(element));
		modelClass.addInstanceOperation(createOperation_getListManagerMethod(element));
		modelClass.addInstanceOperations(createOperation_getSelectManagerMethods(element));
		modelClass.addInstanceOperations(createOperation_initializeMethods(element));
		modelClass.addInstanceOperations(createOperation_handlerMethods(element));
		modelClass.addInstanceOperations(createOperation_executeMethods(element));
	}

	protected ModelOperation createOperation_getServiceMethod(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		//TODO this will need adjustment(s)
		Service service = context.getServiceByName(elementNameUncapped);
		if (service == null)
			service = context.getServiceByName(elementNameUncapped+"Service");
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+serviceInterfaceName);
		modelOperation.setResultType(serviceInterfaceName);
		
		String clientBeanName = ClientLayerHelper.getClientName(service);
		String clientClassName = ClientLayerHelper.getClientClassName(service);
		String clientQualifiedInterfaceName = ClientLayerHelper.getClientQualifiedInterfaceName(service);
		String clientQualifiedClassName = ClientLayerHelper.getClientQualifiedClassName(service);
		modelOperation.addImportedClass(clientQualifiedInterfaceName);
		modelOperation.addImportedClass(clientQualifiedClassName);
		
		Buf buf = new Buf();
		//buf.putLine2("return BeanContext.getFromSession("+serviceInterfaceName+".ID);");
		buf.putLine2(clientClassName+" "+clientBeanName+" = BeanContext.getFromSession("+serviceInterfaceName+".ID);");
		buf.putLine2(clientBeanName+".setTransportType(getTransportType());");
		buf.putLine2("return "+clientBeanName+";");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getListManagerMethod(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementClassNameUncapped = NameUtil.uncapName(elementClassName);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"ListManager");
		modelOperation.setResultType(elementClassName+"ListManager");
		
		Buf buf = new Buf();
//		if (ElementUtil.isEnumeration(type)) {
//			buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"ListManager\");");
//			
//		} else if (ElementUtil.isElement(type)) {
//			buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"ListManager\");");
//		}
		
		buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"ListManager\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<ModelOperation> createOperation_getSelectManagerMethods(Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		Service service = context.getServiceByName(elementNameUncapped+"Service");
		Collection<Type> parameterTypes = getUniqueElementsFromParameter(service);
		parameterTypes = sortTypesByInstanceName(parameterTypes);
		Iterator<Type> iterator = parameterTypes.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			modelOperations.add(createOperation_getSelectManagerMethod(type));
		}
		
		return modelOperations;
	}
	
	protected ModelOperation createOperation_getSelectManagerMethod(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementClassNameUncapped = NameUtil.uncapName(elementClassName);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"SelectManager");
		modelOperation.setResultType(elementClassName+"SelectManager");
		
		Buf buf = new Buf();
//		if (ElementUtil.isEnumeration(type)) {
//			buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"ListManager\");");
//			
//		} else if (ElementUtil.isElement(type)) {
//			buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"ListManager\");");
//		}
		
		buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"SelectManager\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getInfoManagerMethod(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementClassNameUncapped = NameUtil.uncapName(elementClassName);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"InfoManager");
		modelOperation.setResultType(elementClassName+"InfoManager");
		
		Buf buf = new Buf();
//		if (ElementUtil.isEnumeration(type)) {
//			buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"InfoManager\");");
//			
//		} else if (ElementUtil.isElement(type)) {
//			buf.putLine2("return BeanContext.getFromSession(\"main"+elementClassName+"InfoManager\");");
//		}
		
		buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"InfoManager\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperation_initializeMethods(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		Service service = context.getServiceByName(elementNameUncapped+"Service");
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			modelOperations.add(createOperation_initializeMethod(element, service, operation));
		}
		
		return modelOperations;
	}
	
	protected ModelOperation createOperation_initializeMethod(Element element, Service service, Operation operation) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String operationNameCapped = NameUtil.capName(operation.getName());
		String operationNameUncapped = NameUtil.uncapName(operation.getName());
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+operationNameCapped);
		//modelOperation.addAnnotation(AnnotationUtil.createCreateAnnotation());
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());

		Buf buf = new Buf();
		buf.putLine2("InvocationValues invocationValues = getInvocationValues(\""+operationNameUncapped+"\");");
		
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterType = parameter.getType();
			String parameterClass = TypeUtil.getClassName(parameterType);
			String structure = NameUtil.capName(parameter.getConstruct());
			if (structure.equals("Item"))
				structure = "";
			
			buf.putLine2("invocationValues.addParameter"+structure+"Placeholder(\""+parameterClass+"\", \""+parameter.getName()+"\");");
			//buf.putLine2("invocationValues.addParameterPlaceholder(\""+parameterClass+"\", \""+parameter.getName()+"\", (Long) 0L);");
		}
		
		if (OperationUtil.hasResult(operation)) {
			Result result = OperationUtil.getFirstResult(operation);
			String resultType = result.getType();
			String resultClass = TypeUtil.getClassName(resultType);
			String structure = NameUtil.capName(result.getConstruct());
			if (structure.equals("Item"))
				structure = "";
			buf.putLine2("invocationValues.addResult"+structure+"Placeholder(\""+resultClass+"\", \""+result.getName()+"\");");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<ModelOperation> createOperation_handlerMethods(Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		Service service = context.getServiceByName(elementNameUncapped+"Service");
		Collection<Type> parameterTypes = getUniqueElementsFromParameter(service);
		parameterTypes = sortTypesByInstanceName(parameterTypes);
		Iterator<Type> iterator = parameterTypes.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (!ElementUtil.isEnumeration(type))
				modelOperations.add(createOperation_elementEnteredMethod(type));
			modelOperations.add(createOperation_elementSelectedMethod(type));
			modelOperations.add(createOperation_elementListSelectedMethod(type));
		}
		
		return modelOperations;
	}
	
	protected ModelOperation createOperation_elementEnteredMethod(Type type) throws Exception {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handle"+elementClassName+"Entered");
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation(elementNameUncapped+"Entered"));
		
		String namespace = context.getModule().getNamespace();
		String managerPackageName = elementPackageName + ".ui." + elementNameUncapped;
		modelOperation.addImportedClass(managerPackageName+"."+elementClassName+"InfoManager");
		modelOperation.addImportedClass(type);
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"InfoManager manager = get"+elementClassName+"InfoManager();");
		buf.putLine2("String serviceId = manager.getTargetService();");
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = manager.get"+elementClassName+"();");
		buf.putLine2("setParameterValue(serviceId, \""+elementNameUncapped+"\", "+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_elementSelectedMethod(Type type) throws Exception {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handle"+elementClassName+"Selected");
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation(elementNameUncapped+"Selected"));
		
		String namespace = context.getModule().getNamespace();
		String managerPackageName = elementPackageName + ".ui." + elementNameUncapped;
		modelOperation.addImportedClass(managerPackageName+"."+elementClassName+"SelectManager");
		modelOperation.addImportedClass(type);

		Buf buf = new Buf();
		buf.putLine2(elementClassName+"SelectManager manager = get"+elementClassName+"SelectManager();");
		buf.putLine2("String serviceId = manager.getTargetService();");
		buf.putLine2(elementClassName+" selected"+elementClassName+" = manager.getSelectedRecord();");
		buf.putLine2("setParameterValue(serviceId, \""+elementNameUncapped+"\", selected"+elementClassName+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_elementListSelectedMethod(Type type) throws Exception {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handle"+elementClassName+"ListSelected");
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation(elementNameUncapped+"ListSelected"));
		
		//String managerPackageName = elementPackageName + ".ui." + elementNameUncapped;
		String managerPackageName = elementPackageName + "." + elementNameUncapped;
		modelOperation.addImportedClass(managerPackageName+"."+elementClassName+"SelectManager");
		modelOperation.addImportedClass(type);
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"SelectManager manager = get"+elementClassName+"SelectManager();");
		buf.putLine2("String serviceId = manager.getTargetService();");
		buf.putLine2("Collection<"+elementClassName+"> selected"+elementClassName+"List = manager.getSelectedRecords();");
		buf.putLine2("setParameterValue(serviceId, \""+elementNameUncapped+"List\", selected"+elementClassName+"List);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperation_executeMethods(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		Service service = context.getServiceByName(elementNameUncapped+"Service");
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			modelOperations.add(createOperation_executeMethod(element, service, operation));
		}
		
		return modelOperations;
	}
	
	protected ModelOperation createOperation_executeMethod(Element element, Service service, Operation operation) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String operationNameCapped = NameUtil.capName(operation.getName());
		String operationNameUncapped = NameUtil.uncapName(operation.getName());
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("execute"+operationNameCapped);

		Buf buf = new Buf();
		buf.putLine2("try {");
		//buf.putLine2("	setModule(\""+operationNameUncapped+"\");");
		buf.putLine2("	InvocationValues invocationValues = getInvocationValues();");
		
		String parameterString = "";
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterType = parameter.getType();
			String parameterClass = TypeUtil.getClassName(parameterType);
			String parameterNameCapped = NameUtil.capName(parameterName);
			String structure = NameUtil.capName(parameter.getConstruct());
			if (structure.equals("Item"))
				structure = "";
			
			buf.putLine2("	"+parameterClass+" "+parameterName+" = invocationValues.getParameterValue(\""+parameterName+"\");");
			buf.putLine2("	Assert.notNull("+parameterName+", \""+parameterNameCapped+" parameter must be specified\");");

			if (i > 0)
				parameterString += ", ";
			parameterString += parameterName;
		}
		
		if (OperationUtil.hasResult(operation)) {
			Result result = OperationUtil.getFirstResult(operation);
			String resultName = result.getName();
			String resultType = result.getType();
			String resultClass = TypeUtil.getClassName(resultType);
			String structure = NameUtil.capName(result.getConstruct());
			String recordString = "result(s)";
			
			if (structure.equals("Item")) {
				resultType = resultClass;
				recordString = "result";
			} else if (structure.equals("List")) {
				resultType = "List<"+resultClass+">";
			} else if (structure.equals("Set")) {
				resultType = "Set<"+resultClass+">";
			} else if (structure.equals("Map")) {
				String resultKey = TypeUtil.getClassName(result.getKey());
				resultType = "Map<"+resultKey+", "+resultClass+">";
			}
			
			String resultNameCapped = NameUtil.capName(resultName);
			buf.putLine2("	"+resultType+" "+resultName+" = get"+elementClassName+"Service()."+operationNameUncapped+"("+parameterString+");");
			buf.putLine2("	Assert.notNull("+resultName+", \""+resultNameCapped+" "+recordString+" not found\");");
			//buf.putLine2("	Assert.notEmpty("+resultName+", \""+resultNameCapped+" record(s) not found\");");
			//buf.putLine2("	get"+resultClass+"ListManager().setRecordList("+resultName+");");
			buf.putLine2("	invocationValues.setResultValue("+resultName+");");
			buf.putLine2("	outject(\""+resultName+"\", "+resultName+");");

		} else {
			buf.putLine2("	get"+elementClassName+"Service()."+operationNameUncapped+"("+parameterString+");");
		}
		
		
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	handleException(e);");
		buf.putLine2("}");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
