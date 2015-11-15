package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.client.ClientLayerHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Service;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


/**
 * Builds an Element Record Selection Manager Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementSelectManagerBuilder extends AbstractElementManagerBuilder {

	public ElementSelectManagerBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public ModelClass buildClass(Type element) throws Exception {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "SelectManager";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		modelClass.setParentClassName("AbstractSelectManager<"+elementName+", "+elementName+"ListObject>");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Type element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeClassAnnotations(modelClass, element);
		initializeClassConstructors(modelClass, element);
		initializeInstanceFields(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Type type) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);

		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		
		if (ElementUtil.isRoot(type)) {
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");

		} else if (ElementUtil.isElement(type)) {
			//modelClass.addImportedClass("org.aries.Assert");
			//modelClass.addImportedClass("java.util.Collections");
			//modelClass.addImportedClass("java.util.Comparator");
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");

		} else if (ElementUtil.isEnumeration(type)) {
			modelClass.addImportedClass("java.util.ArrayList");
			//modelClass.addImportedClass("java.util.Collections");
		}
		
//		modelClass.addImportedClass("org.jboss.seam.ScopeType");
//		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.Create");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.Begin");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Observer");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.Role");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Startup");
//		modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
		
		modelClass.addImportedClass("javax.enterprise.context.SessionScoped");
		//modelClass.addImportedClass("javax.enterprise.event.Observes");
		modelClass.addImportedClass("javax.faces.model.DataModel");
		modelClass.addImportedClass("javax.inject.Named");
		modelClass.addImportedClass("javax.inject.Inject");
		
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.ui.AbstractSelectManager");

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
		//modelClass.getClassAnnotations().add(AnnotationUtil.createNameAnnotation(elementNameUncapped+"SelectManager"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createRoleAnnotation("main"+elementNameCapped+"SelectManager", ScopeType.SESSION));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createRestrictAnnotation("#{identity.loggedIn}"));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressSerialWarning());
		
		modelClass.getClassAnnotations().add(AnnotationUtil.createSessionScopedAnnotation());
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"SelectManager"));
	}

	
	/*
	 * Class Constructor(s)
	 */
	
	protected void initializeClassConstructors(ModelClass modelClass, Type element) throws Exception {
		//modelClass.addInstanceConstructor(createConstructor(element));
	}
	
	protected ModelConstructor createConstructor(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	
	/*
	 * Instance fields
	 */

	protected void initializeInstanceFields(ModelClass modelClass, Type element) throws Exception {
		if (!ElementUtil.isEnumeration(element))
			modelClass.addInstanceReference(createReference_ElementManager(element, "Data"));
		modelClass.addInstanceReference(createReference_ElementHelper(element));
	}

	public ModelReference createReference_ElementManager(Type element, String type) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		String namespace = context.getProject().getNamespace();
		String packageName = NameUtil.getPackageNameFromNamespace(namespace) + "";
		String className = elementClassName + type + "Manager";
		String elementName = elementNameUncapped + type + "Manager";
		
		ModelReference modelReference = new ModelReference();
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(elementName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	public ModelReference createReference_ElementHelper(Type element) {
		String packageName = ModelLayerHelper.getElementPackageName(element);
		String className = ModelLayerHelper.getElementClassName(element) + "Helper";
		String elementName = ModelLayerHelper.getElementNameUncapped(element) + "Helper";

		ModelReference modelReference = new ModelReference();
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(elementName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Type type) throws Exception {
		modelClass.addInstanceOperation(createOperation_getClientId(type));
		modelClass.addInstanceOperation(createOperation_getTitle(type));
		modelClass.addInstanceOperation(createOperation_getRecordClass(type));
		if (ElementUtil.isElement(type))
			modelClass.addInstanceOperation(createOperation_isEmpty(type));
		modelClass.addInstanceOperation(createOperation_toString(type));
		modelClass.addInstanceOperation(createOperation_getHelper(type));
		if (ElementUtil.isRoot(type))
			modelClass.addInstanceOperation(createOperation_getService(type));
		modelClass.addInstanceOperation(createOperation_getListManager(type));
		//modelClass.addInstanceOperation(createOperation_reset(element));
		modelClass.addInstanceOperation(createOperation_initialize(type));
		//modelClass.addInstanceOperation(createOperation_initialize2(element));
		modelClass.addInstanceOperation(createOperation_populateItems(type));
		modelClass.addInstanceOperation(createOperation_refreshRecords(type));
		modelClass.addInstanceOperation(createOperation_refreshRecords2(type));
		modelClass.addInstanceOperation(createOperation_activate(type));
		modelClass.addInstanceOperation(createOperation_submit(type));
		modelClass.addInstanceOperation(createOperation_sortRecords(type));
		modelClass.addInstanceOperations(createOperations_sortRecords2(type));
	}

	protected ModelOperation createOperation_getClientId(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getClientId");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return \""+elementNameUncapped+"Select\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getTitle(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getTitle");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return \""+elementClassName+" Selection\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getRecordId(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getRecordId");
		modelOperation.setResultType("Object");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementNameUncapped+".getId();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_isEmpty(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("isEmpty");
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementNameUncapped+"Helper.isEmpty("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_toString(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("toString");
		modelOperation.setResultType("String");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(type)) {
			buf.putLine2("return "+elementNameUncapped+".name();");
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2("return "+elementNameUncapped+"Helper.toString("+elementNameUncapped+");");
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getRecordClass(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("getRecordClass");
		modelOperation.setResultType("Class<"+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementClassName+".class;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getRecord(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("getRecord");
		modelOperation.setResultType(elementClassName);
		modelOperation.addParameter(createParameter(elementClassName+"ListObject", "rowObject"));
		
		Buf buf = new Buf();
		buf.putLine2("return rowObject.get"+elementClassName+"();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_createRowItem(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("createRowObject");
		modelOperation.setResultType(elementClassName+"ListObject");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("return new "+elementClassName+"ListObject("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getHelper(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"Helper");
		modelOperation.setResultType(elementClassName+"Helper");
		
		Buf buf = new Buf();
		buf.putLine2("return BeanContext.getFromSession(\""+elementNameUncapped+"Helper\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getService(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"Service");
		modelOperation.setResultType(elementClassName+"Service");
		
		//TODO use other ways of finding the name of the Service for the Element
		Service service = context.getServiceByName(elementNameUncapped+"Service");
		if (service == null)
			return null;
		
		String qualifiedName = ClientLayerHelper.getClientQualifiedInterfaceName(service);
		modelOperation.addImportedClass(qualifiedName);
		
		Buf buf = new Buf();
		buf.putLine2("return BeanContext.getFromSession("+elementClassName+"Service.ID);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getListManager(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementClassNameUncapped = NameUtil.uncapName(elementClassName);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"ListManager");
		modelOperation.setResultType(elementClassName+"ListManager");
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(type)) {
			buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"ListManager\");");
			
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"ListManager\");");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_reset(Type element) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.aries.ui.reset"));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("reset");
		
		Buf buf = new Buf();
		buf.putLine2("refresh();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_initialize(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize");
		//modelOperation.addAnnotation(AnnotationUtil.createCreateAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.aries.ui.reset"));
		
		Buf buf = new Buf();
		buf.putLine2("initializeContext(); ");
		buf.putLine2("refresh"+elementClassName+"List();");
		buf.putLine2("populate(selectedRecords);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_initialize2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("this.ownerRecord = "+elementNameUncapped+";");
		buf.putLine2("populate(selectedRecords);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_populateItems(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("populateItems");
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", "recordList"));
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"ListManager "+elementNameUncapped+"ListManager = get"+elementClassName+"ListManager();");
		buf.putLine2("DataModel<"+elementClassName+"ListObject> dataModel = "+elementNameUncapped+"ListManager.getDataModel(recordList);");
		buf.putLine2("setSelectedItems(dataModel);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_refreshRecords(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.aries.refresh"+elementClassName+"List"));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refresh"+elementClassName+"List");
		
		Buf buf = new Buf();
		buf.putLine2("masterList = refreshRecords();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_refreshRecords2(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("Collection<"+elementClassName+">");
		modelOperation.setName("refreshRecords");
		
//		if (elementNameUncapped.equals("module"))
//			System.out.println();
		
		Buf buf = new Buf();
		if (ElementUtil.isRoot(type)) {
			buf.putLine2("try {");
			//buf.putLine2("	"+elementClassName+"Service "+elementNameUncapped+"Service = get"+elementClassName+"Service();");
			//buf.putLine2("	List<"+elementClassName+"> records = "+elementNameUncapped+"Service.get"+elementClassName+"List();");
			buf.putLine2("	Collection<"+elementClassName+"> records = "+elementNameUncapped+"DataManager.get"+elementClassName+"List();");
			buf.putLine2("	return records;");
			buf.putLine2("} catch (Exception e) {");
			buf.putLine2("	handleException(e);");
			buf.putLine2("	return null;");
			buf.putLine2("}");
			
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2("String instanceId = getInstanceId();");
			buf.putLine2("if (instanceId == null)");
			buf.putLine2("	return null;");
			//buf.putLine2("Assert.notEmpty(instanceId, \"Instance Id must be specified\");");
			buf.putLine2("List<"+elementClassName+"> "+elementNameUncapped+"List = BeanContext.getFromConversation(instanceId);");
			buf.putLine2("return "+elementNameUncapped+"List;");
			
		} else if (ElementUtil.isEnumeration(type)) {
			buf.putLine2(elementClassName+"[] values = "+elementClassName+".values();");
			buf.putLine2("List<"+elementClassName+"> masterList = new ArrayList<"+elementClassName+">();");
			buf.putLine2("for ("+elementClassName+" capability : values) {");
			buf.putLine2("	masterList.add(capability);");
			buf.putLine2("}");
			buf.putLine2("return masterList;");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_activate(Type element) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("activate");
		//modelOperation.addAnnotation(AnnotationUtil.createCreateAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		
		Buf buf = new Buf();
		buf.putLine2("initialize();");
		buf.putLine2("super.show();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_submit(Type element) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("submit");
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("setModule(targetField);");
		buf.putLine2("return super.submit();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_sortRecords(Type type) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("sortRecords");
		
		Buf buf = new Buf();
		buf.putLine2("sortRecords(recordList);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperations_sortRecords2(Type type) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		Field defaultSortField = null;
		Field firstFieldFound = null;
		Field firstUniqueFieldFound = null;
		
		if (ElementUtil.isEnumeration(type)) {
			modelOperations.add(createOperation_sortRecords2(type));
			return modelOperations;
		}
		
		Element element = (Element) type;
		Set<Field> fields = new HashSet<Field>();
		fields.addAll(ElementUtil.getUniqueFields(element));
		fields.addAll(ElementUtil.getIndexedFields(element));
		
		List<Field> sortedList = createSortedFieldListByName(fields);
		Iterator<Field> iterator = sortedList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Field field = iterator.next();
			if (!FieldUtil.isRequired(field))
				continue;
			if (i == 0)
				firstFieldFound = field;
			if (FieldUtil.isUnique(field))
				firstUniqueFieldFound = field;
			modelOperations.add(createOperation_sortRecords2(element, field, false));
		}
		
		if (firstUniqueFieldFound == null)
			defaultSortField = firstFieldFound;
		else defaultSortField = firstUniqueFieldFound;
			
		if (modelOperations.size() == 0)
			modelOperations.add(createOperation_sortRecords2(element));
		else modelOperations.add(0, createOperation_sortRecords2(element, defaultSortField, true));
		return modelOperations;
	}
	
	protected ModelOperation createOperation_sortRecords2(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("sortRecords");
		modelOperation.addParameter(createParameter("List<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(type)) {
			modelUnit.addImportedClass("java.util.Collections");
			buf.putLine2("Collections.sort("+elementNameUncapped+"List);");
			
		} else if (ElementUtil.isElement(type)) {
			modelUnit.addImportedClass("java.util.Collections");
			modelUnit.addImportedClass("java.util.Comparator");
			buf.putLine2("Collections.sort("+elementNameUncapped+"List, new Comparator<"+elementClassName+">() {");
			buf.putLine2("	public int compare("+elementClassName+" "+elementNameUncapped+"1, "+elementClassName+" "+elementNameUncapped+"2) {");
			buf.putLine2("		String text1 = "+elementClassName+"Util.toString("+elementNameUncapped+"1);");
			buf.putLine2("		String text2 = "+elementClassName+"Util.toString("+elementNameUncapped+"2);");
			buf.putLine2("		return text1.compareTo(text2);");
			buf.putLine2("	}");
			buf.putLine2("});");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_sortRecords2(Type element, Field field, boolean isInterfaceMethod) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);

		ModelOperation modelOperation = new ModelOperation();
		if (isInterfaceMethod)
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (isInterfaceMethod)
			modelOperation.setName("sortRecords");
		else modelOperation.setName("sortRecordsBy"+fieldNameCapped);
		modelOperation.addParameter(createParameter("List<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		if (isInterfaceMethod)
			buf.putLine2("sortRecordsBy"+fieldNameCapped+"("+elementNameUncapped+"List);");
		else buf.putLine2(elementClassName+"Util.sortRecordsBy"+fieldNameCapped+"("+elementNameUncapped+"List);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
