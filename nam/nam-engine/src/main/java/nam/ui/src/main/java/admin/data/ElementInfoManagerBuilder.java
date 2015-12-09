package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.client.ClientLayerHelper;
import nam.model.Attribute;
import nam.model.ContainedBy;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Reference;
import nam.model.Service;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelReference;


/**
 * Builds an Element Record Manager Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementInfoManagerBuilder extends AbstractElementManagerBuilder {

	public ElementInfoManagerBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public ModelClass buildClass(Type type) throws Exception {
		if (!ElementUtil.isElement(type))
			return null;

		Element element = (Element) type;
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "InfoManager";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		modelClass.setParentClassName("AbstractNamRecordManager<"+elementName+">");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeClassAnnotations(modelClass, element);
		initializeClassConstructors(modelClass, element);
		initializeInstanceFields(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Element type) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		
//		if (elementClassName.equals(""))
//			System.out.println();
		
		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		modelClass.addImportedClass(elementPackageName + ".Project");
		modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
		modelClass.addImportedClass(elementPackageName + ".util.ProjectUtil");

		modelClass.addImportedClass("nam.ui.design.SelectionContext");
		modelClass.addImportedClass("nam.ui.design.WorkspaceEventManager");

		List<Field> fields = ElementUtil.getFields(type);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field instanceof Reference == false)
				continue;
			
			String fieldPackageName = TypeUtil.getPackageName(field.getType());
			String fieldClassName = TypeUtil.getClassName(field.getType()) + "Util";
			//modelClass.addImportedClass(fieldPackageName + ".util." + fieldClassName);
		}

		if (ElementUtil.isRoot(type)) {
			//modelClass.addImportedClass("org.aries.Assert");

		} else if (ElementUtil.isElement(type)) {

		} else if (ElementUtil.isEnumeration(type)) {
		}
		
//		modelClass.addImportedClass("org.jboss.seam.ScopeType");
//		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Begin");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.In");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Observer");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.Out");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Startup");
//		modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
		
		modelClass.addImportedClass("javax.enterprise.context.SessionScoped");
		//modelClass.addImportedClass("javax.enterprise.event.Event");
		modelClass.addImportedClass("javax.enterprise.event.Observes");
		modelClass.addImportedClass("javax.inject.Inject");
		modelClass.addImportedClass("javax.inject.Named");
		
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.ui.Display");
		modelClass.addImportedClass("org.aries.util.Validator");
		//modelClass.addImportedClass("org.aries.util.CollectionUtil");
		modelClass.addImportedClass("org.aries.ui.event.Add");
		modelClass.addImportedClass("org.aries.ui.event.Remove");
		//modelClass.addImportedClass("org.aries.ui.event.Checked");
		//modelClass.addImportedClass("org.aries.ui.event.Selected");
		//modelClass.addImportedClass("org.aries.ui.event.Unselected");
		//modelClass.addImportedClass("org.aries.ui.event.Unchecked");
		//modelClass.addImportedClass("org.aries.ui.event.Updated");
		
		//modelClass.addImportedClass("org.aries.ui.AbstractRecordManager");
		modelClass.addImportedClass("nam.ui.design.AbstractNamRecordManager");
		
		modelClass.addImportedClass("java.util.List");
		//modelClass.addImportedClass("java.util.Map");
		super.initializeImportedClasses(modelClass);
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		//modelClass.getClassAnnotations().add(AnnotationUtil.createStartupAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createAnnotation("AutoCreate"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createNameAnnotation(elementNameUncapped+"InfoManager"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createRestrictAnnotation("#{identity.loggedIn}"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressWarning("serial", "unused"));
		
		modelClass.getClassAnnotations().add(AnnotationUtil.createSessionScopedAnnotation());
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"InfoManager"));
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
		buf.putLine2("setInstanceName(\""+elementNameUncapped+"\");");
		//buf.putLine2("setCancelEvent(\"org.aries.cancel"+elementClassName+"\");");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	
	/*
	 * Instance fields
	 */

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceReference(createReference_Manager(element, "Wizard", ""));
		modelClass.addInstanceReference(createReference_Manager(element, "Data", "Manager"));
		modelClass.addInstanceReference(createReference_Manager(element, "Page", "Manager"));
		modelClass.addInstanceReference(createReference_Manager(element, "Event", "Manager"));
		//modelClass.addInstanceReference(createReference_Element(element));
		//modelClass.addInstanceReferences(createReferences_Managers(element));
		//modelClass.addInstanceReference(createReference_Service(element));
		//modelClass.addInstanceReference(createReference_ElementAddedEvent(element));
		//modelClass.addInstanceReference(createReference_ElementRemovedEvent(element));
		modelClass.addInstanceReference(createReference_WorkspaceEventManager(element));
		modelClass.addInstanceReference(createReference_ElementHelper(element));
		modelClass.addInstanceReference(createReference_SelectionContext(element));
	}
	
	public ModelReference createReference_Element(Element element) {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String packageName = ModelLayerHelper.getElementPackageName(element);
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		String className = ModelLayerHelper.getElementClassName(element);
		
		ModelReference modelReference = new ModelReference();
		//modelReference.addAnnotation(AnnotationUtil.createOutAnnotation(false));
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(elementName);
		modelReference.setGenerateGetter(true);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	//NOTUSED not needed for now
	public List<ModelReference> createReferences_Managers(Element element) {
		List<ModelReference> modelReferences = new ArrayList<ModelReference>(); 
		List<ModelReference> modelReferences2 = new ArrayList<ModelReference>(); 
		
		modelReferences.add(createReference_Manager(element, "Wizard", ""));
		modelReferences.add(createReference_Manager(element, "Data", "Manager"));
		modelReferences.add(createReference_Manager(element, "Page", "Manager"));
		modelReferences.add(createReference_Manager(element, "Event", "Manager"));
		if (ElementUtil.isHierarchical(element))
			modelReferences.add(createReference_Manager(element, "Tree", "Manager"));

//		if (element.getName().equalsIgnoreCase("Service"))
//			System.out.println();
		
		List<Field> fields = ElementUtil.getFields(element);
		Map<Field, Element> map = createFieldTypeMap(fields);
		List<Field> fieldList = createSortedFieldListByClassName(map.keySet());
		Iterator<Field> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			Type fieldObject = map.get(field);
			String structure = field.getStructure();
			if (fieldObject == null)
				continue;

			if (field instanceof Reference || ElementUtil.isEnumeration(fieldObject)) {
				if (FieldUtil.isContained(field))
					modelReferences2.add(createReference_Manager(fieldObject, "Info", "Manager"));
				else modelReferences2.add(createReference_Manager(fieldObject, "Select", "Manager"));
				if (structure.equals("list") || structure.equals("set"))
					modelReferences2.add(createReference_Manager(fieldObject, "List", "Manager"));
			}
		}
		
		sortModelReferencesByClassName(modelReferences2);
		modelReferences.addAll(modelReferences2);
		return modelReferences;
	}
	
	//NOTUSED not needed for now
	public ModelReference createReference_Manager(Type fieldObject, String managerType, String suffix) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementName = ModelLayerHelper.getElementNameUncapped(fieldObject);
		String elementClass = ModelLayerHelper.getElementClassName(fieldObject);
		String packageName = ModelLayerHelper.getElementPackageName(fieldObject);
		String className = elementClass; 
		String instanceName = elementName; 

		//TODO handle special cases for "org.aries.common"
		if (!packageName.equals("org.aries.common")) { 
			className += managerType;
			instanceName += managerType;
			//modelUnit.addImportedClass(packageName+".ui."+elementName+"."+className+"Manager");
			modelUnit.addImportedClass(packageName+"."+elementName+"."+className+suffix);
		} else {
			modelUnit.addImportedClass("org.aries.ui.manager."+className+suffix);
		}
		className += suffix;
		instanceName += suffix;

		//boolean isListManager = managerType.equals("List");
		//addImportedClass(modelUnit, referenceType.getType());

		ModelReference modelReference = new ModelReference();
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		//modelReference.addAnnotation(AnnotationUtil.createInAnnotation(!isListManager));
		modelReference.setType(fieldObject.getType());
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(instanceName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	public ModelReference createReference_Service(Element element) {
		String namespace = context.getModule().getNamespace();
		String packageName = ModelLayerHelper.getElementPackageName(element);
		//String packageName = ProjectLevelHelper.getPackageName(namespace);
		String className = ModelLayerHelper.getElementClassName(element) + "Service";
		String elementName = ModelLayerHelper.getElementNameUncapped(element) + "Service";
		String serviceId = className + ".ID";
		
		ModelReference modelReference = new ModelReference();
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		//modelReference.addAnnotation(AnnotationUtil.createInAnnotationUnquoted(true, serviceId));
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(elementName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	public ModelReference createReference_ElementAddedEvent(Element element) {
		String namespace = context.getModule().getNamespace();
		String packageName = NameUtil.getPackageNameFromNamespace(namespace) + ".event";
		String className = ModelLayerHelper.getElementClassName(element) + "AddedEvent";
		String elementName = ModelLayerHelper.getElementNameUncapped(element) + "AddedEvent";
		
		ModelReference modelReference = new ModelReference();
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName("Event<"+className+">");
		modelReference.setName(elementName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	public ModelReference createReference_WorkspaceEventManager(Element element) {
		String packageName = "nam.ui.design";
		String className = "WorkspaceEventManager";
		String elementName = "workspaceEventManager";
		
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
	
	public ModelReference createReference_ElementHelper(Element element) {
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
	
	public ModelReference createReference_SelectionContext(Element element) {
		String packageName = "nam.ui.design";
		String className = "SelectionContext";
		String elementName = "selectionContext";
		
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

	protected void initializeInstanceOperations(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceOperation(createOperation_getRecord(element));
		modelClass.addInstanceOperation(createOperation_getSelectedRecord(element));
		modelClass.addInstanceOperation(createOperation_getRecordClass(element));
		modelClass.addInstanceOperation(createOperation_isEmpty(element));
		modelClass.addInstanceOperation(createOperation_toString(element));
		//modelClass.addInstanceOperation(createOperation_getHelper(element));
		if (ElementUtil.isRoot(element))
			modelClass.addInstanceOperation(createOperation_getService(element));
		//modelClass.addInstanceOperation(createOperation_setTitle(element));
		//modelClass.addInstanceOperation(createOperation_setHeader(element));
		//modelClass.addInstanceOperation(createOperation_setMessage(element));
		modelClass.addInstanceOperation(createOperation_initialize(element));
		modelClass.addInstanceOperation(createOperation_initialize2(element));
		//modelClass.addInstanceOperation(createOperation_initializeOutjectedState(element));
		//modelClass.addInstanceOperations(createOperations_initializeManagers(element));
		//modelClass.addInstanceOperations(createOperations_initializeHandlers(element));
		//modelClass.addInstanceOperation(createOperation_activate(element));
		//modelClass.addInstanceOperations(createOperations_handleSelected(element));
		//modelClass.addInstanceOperation(createOperation_handleUnselected(element));
		//modelClass.addInstanceOperations(createOperations_handleChecked(element));
		//modelClass.addInstanceOperation(createOperation_handleUnchecked(element));
		modelClass.addInstanceOperation(createOperation_newRecord(element));
		modelClass.addInstanceOperation(createOperation_newRecord2(element));
		modelClass.addInstanceOperation(createOperation_create(element));
		modelClass.addInstanceOperation(createOperation_clone(element));
		modelClass.addInstanceOperation(createOperation_viewRecord(element));
		modelClass.addInstanceOperation(createOperation_viewRecord2(element));
		modelClass.addInstanceOperation(createOperation_viewRecord3(element));
		modelClass.addInstanceOperation(createOperation_editRecord(element));
		modelClass.addInstanceOperation(createOperation_editRecord2(element));
		modelClass.addInstanceOperation(createOperation_editRecord3(element));
		modelClass.addInstanceOperation(createOperation_saveRecord(element));
		modelClass.addInstanceOperation(createOperation_persistRecord(element));
		modelClass.addInstanceOperation(createOperation_saveRecord2(element));
		modelClass.addInstanceOperation(createOperation_saveRecordToSystem(element));
		modelClass.addInstanceOperation(createOperation_handleSaveRecord(element));
		if (ElementUtil.isRoot(element))
			modelClass.addInstanceOperation(createOperation_addRecord(element));
		modelClass.addInstanceOperation(createOperation_saveRecordToSystem(element));
		//modelClass.addInstanceOperation(createOperation_assureRecord(element));
		modelClass.addInstanceOperation(createOperation_enrichRecord(element));
		modelClass.addInstanceOperation(createOperation_validateRecord(element));
		modelClass.addInstanceOperation(createOperation_validateRecord2(element));
		modelClass.addInstanceOperation(createOperation_promptRemoveRecord(element));
		modelClass.addInstanceOperation(createOperation_removeRecord(element));
		modelClass.addInstanceOperation(createOperation_removeRecordFromSystem(element));
		modelClass.addInstanceOperation(createOperation_cancelRecord(element));
	}

	protected ModelOperation createOperation_getRecordClass(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getRecordClass");
		modelOperation.setResultType("Class<"+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementClassName+".class;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_isEmpty(Element element) throws Exception {
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
	
	protected ModelOperation createOperation_toString(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("toString");
		modelOperation.setResultType("String");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementNameUncapped+"Helper.toString("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getRecord(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("get"+elementClassName);
		modelOperation.setResultType(elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2("return getRecord();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getSelectedRecord(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getSelected"+elementClassName);
		modelOperation.setResultType(elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2("return selectionContext.getSelection(\""+elementNameUncapped+"\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getHelper(Element element) throws Exception {
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
	
	protected ModelOperation createOperation_getService(Element element) throws Exception {
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
	
	protected ModelOperation createOperation_initialize(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize");

		Buf buf = new Buf();
		//buf.putLine2("selectionContext.resetOrigin();");
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = selectionContext.getSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("if ("+elementNameUncapped+" != null)");
		buf.putLine2("	initialize("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_initialize2(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("initialize");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		Set<String> fieldsAlreadyProcessed = new HashSet<String>();
		
		Buf buf = new Buf();
		//buf.putLine2(elementClassName+"Util.initialize("+elementNameUncapped+");");
		buf.putLine2(elementNameUncapped+"Wizard.initialize("+elementNameUncapped+");");

//		List<Field> fields = ElementUtil.getFields(element);
//		Map<Field, Element> map = createFieldTypeMap(fields);
//		List<Field> fieldList = createSortedFieldListByClassName(map.keySet());
//		Iterator<Field> iterator = fieldList.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			Type fieldObject = map.get(field);
//			String structure = field.getStructure();
//			if (fieldObject == null)
//				continue;
//
//			if (field instanceof Reference || ElementUtil.isEnumeration(fieldObject)) {
//				String fieldPackageName = ModelLayerHelper.getElementPackageName(fieldObject);
//				String fieldClassName = ModelLayerHelper.getElementClassName(fieldObject);
//				
//				if (!fieldsAlreadyProcessed.contains(fieldClassName)) {
//					fieldsAlreadyProcessed.add(fieldClassName);
//				
//					//TODO handle special case for "org.aries.common"
//					if (fieldPackageName.equals("org.aries.common")) { 
//						buf.putLine2("initialize"+fieldClassName+"Manager("+elementNameUncapped+");");
//						continue;
//					}
//					
//					if (FieldUtil.isContained(field))
//						buf.putLine2("initialize"+fieldClassName+"InfoManager("+elementNameUncapped+");");
//					if (structure.equals("list") || structure.equals("set"))
//						buf.putLine2("initialize"+fieldClassName+"ListManager("+elementNameUncapped+");");
//					if (!FieldUtil.isContained(field))
//						buf.putLine2("initialize"+fieldClassName+"SelectManager("+elementNameUncapped+");");
//				}
//			}
//		}
		
//		buf.putLine2("//initializeOutjectedState("+elementNameUncapped+");");
		buf.putLine2("setContext(\""+elementNameUncapped+"\", "+elementNameUncapped+");");
		//buf.putLine2("show();");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_initializeOutjectedState(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("initializeOutjectedState");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		Map<String, String> sourceLinesByInstanceName = new HashMap<String, String>();
		
		boolean phoneNumberMapHandled = false;
		List<Field> fields = ElementUtil.getFields(element);
		Map<Field, Element> map = createFieldTypeMap(fields);
		List<Field> fieldList = sortTypesByInstanceName(map.keySet());
		Iterator<Field> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			Type fieldObject = map.get(field);
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			//String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
			String fieldInstanceId = getInstanceId(element, field);

			if (field instanceof Attribute && !ElementUtil.isEnumeration(fieldObject))
				continue;
			if (sourceLinesByInstanceName.containsKey(fieldInstanceId))
				continue;
			if (CodeUtil.isPhoneNumberField(field) && phoneNumberMapHandled)
				continue;
			
			if (CodeUtil.isPhoneNumberField(field)) { 
				phoneNumberMapHandled = true;
				fieldInstanceId = elementNameUncapped + "PhoneNumbers";
				String sourceLine = "outjectTo(\""+elementNameUncapped+"PhoneNumbers\", get"+elementClassName+"Helper().getPhoneNumbers("+elementNameUncapped+"));";
				sourceLinesByInstanceName.put(fieldInstanceId, sourceLine);
				
			} else {
				String sourceLine = "outjectTo(\""+fieldInstanceId+"\", "+elementNameUncapped+".get"+fieldNameCapped+"());";
				sourceLinesByInstanceName.put(fieldInstanceId, sourceLine);
			}
		}
		
		Buf buf = new Buf();
		List<String> instanceNames = new ArrayList<String>(sourceLinesByInstanceName.keySet());
		Collections.sort(instanceNames);
		Iterator<String> iterator2 = instanceNames.iterator();
		while (iterator2.hasNext()) {
			String instanceName = iterator2.next();
			String sourceLine = sourceLinesByInstanceName.get(instanceName);
			buf.putLine2(sourceLine);
		}
		
		buf.putLine2("outject(\""+elementNameUncapped+"\", "+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperations_initializeManagers(Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<String> fieldsAlreadyProcessed = new HashSet<String>();

		List<Field> fields = ElementUtil.getFields(element);
		Map<Field, Element> map = createFieldTypeMap(fields);
		List<Field> fieldList = createSortedFieldListByClassName(map.keySet());
		Iterator<Field> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			Type fieldObject = map.get(field);
			if (fieldObject == null)
				continue;
			
			if (field instanceof Reference || ElementUtil.isEnumeration(fieldObject)) {
				String fieldElementName = ModelLayerHelper.getElementNameUncapped(fieldObject);
				if (!fieldsAlreadyProcessed.contains(fieldElementName)) {
					fieldsAlreadyProcessed.add(fieldElementName);
				
					String fieldPackageName = ModelLayerHelper.getElementPackageName(fieldObject);
					boolean isContained = FieldUtil.isContained(field);
					
					String structure = field.getStructure();
					if (fieldPackageName.equals("org.aries.common")) {
						modelOperations.add(createOperation_initializeManager(element, field, fieldObject, ""));
					} else {
						if (structure.equals("item")) {
							if (isContained)
								addInstanceOperation(modelOperations, createOperation_initializeManager(element, field, fieldObject, "Info"));
							else addInstanceOperation(modelOperations, createOperation_initializeManager(element, field, fieldObject, "Select"));
							//addInstanceOperation(modelOperations, createOperation_initializeManager(element, field, fieldElement, "List"));
						} else if (structure.equals("list") || structure.equals("set")) {
							if (isContained)
								addInstanceOperation(modelOperations, createOperation_initializeManager(element, field, fieldObject, "Info"));
							addInstanceOperation(modelOperations, createOperation_initializeManager(element, field, fieldObject, "List"));
							if (!isContained)
								addInstanceOperation(modelOperations, createOperation_initializeManager(element, field, fieldObject, "Select"));
						}
					}
				}
			}
		}
		
		return modelOperations;
	}

	protected ModelOperation createOperation_initializeManager(Element element, Field field, Type fieldElement, String extension) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		//String fieldPackageName = ModelLayerHelper.getElementPackageName(fieldElement);
		String fieldClassName = ModelLayerHelper.getElementClassName(fieldElement);
		String fieldElementNameCapped = ModelLayerHelper.getElementNameCapped(fieldElement);
		String fieldElementNameUncapped = ModelLayerHelper.getElementNameUncapped(fieldElement);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String dataManagerClass = fieldClassName + "DataManager";
		String dataManagerName = fieldElementNameUncapped + "DataManager";
		String managerName = fieldElementNameUncapped + extension + "Manager";
		String managerNameCapped = NameUtil.capName(managerName);
		String attributeType = fieldElementNameCapped + extension;
		String attributeName = fieldNameCapped;
		String structure = field.getStructure();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("initialize"+managerNameCapped);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));

//		boolean skipActionEvent = extension.equals("List");
		boolean skipAttribute = structure.equals("list") && (extension.equals("Info") || extension.equals(""));

		Buf buf = new Buf();
		//buf.putLine2(managerName+" = BeanContext.getFromSession(\""+managerName+"\");");
		buf.putLine2(dataManagerClass+" "+dataManagerName+" = BeanContext.getFromSession(\""+dataManagerName+"\");");
		//buf.putLine2(managerName+".setContext(\""+elementClassName+"\", toString("+elementNameUncapped+"));");
		buf.putLine2(dataManagerName+".setOwner("+elementNameUncapped+");");
		
		if (extension.equals("Select"))
			buf.putLine2(managerName+".setOwnerRecord("+elementNameUncapped+");");
		
//		if (!skipActionEvent)
//			buf.putLine2(managerName+".setActionEvent(\""+elementNameUncapped+fieldClassName+"Entered\");");
		
		if (!skipAttribute) {
//			if (fieldNameUncapped.equals("personName")) {
//				buf.putLine2(managerName+".setFirstName("+elementNameUncapped+".getFirstName());");
//				buf.putLine2(managerName+".setLastName("+elementNameUncapped+".getLastName());");
//				buf.putLine2(managerName+".setMiddleInitial("+elementNameUncapped+".getMiddleInitial());");
//				buf.putLine2(managerName+".setMiddleInitialEnabled(false);");
//			}
//			if ((fieldNameUncapped.equals("homePhone") || 
//				fieldNameUncapped.equals("workPhone") || 
//				fieldNameUncapped.equals("cellPhone") ||
//				fieldNameUncapped.equals("mobilePhone") ||
//				fieldNameUncapped.equals("otherPhone") ||
//				fieldNameUncapped.equals("faxPhone"))) {
//				if (!phoneNumberManagerHandled)
//					buf.putLine2(managerName+".setPhoneNumberMap("+elementClassName+"Helper.createPhoneNumberMap("+elementNameUncapped+"));");
//				else return null;
				
//			} else if (extension.equals("List")) {
//				buf.putLine2(managerName+".setRecordList(user.get"+attributeName+"());");
//			} else if (extension.equals("Select")) {
//				buf.putLine2(managerName+".setSelected"+fieldClassName+"List(user.get"+attributeName+"());");
//			} else buf.putLine2(managerName+".set"+attributeName+"(user.get"+attributeName+"());");
//			}
		}
		
		buf.putLine2(managerName+".initialize();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<ModelOperation> createOperations_initializeHandlers(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		boolean phoneNumberMapHandled = false;
		List<Field> fields = ElementUtil.getFields(element);
		Map<Field, Element> map = createFieldTypeMap(fields);
		List<Field> fieldList = createSortedFieldListByClassName(map.keySet());
		Iterator<Field> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			Type fieldObject = map.get(field);
			//String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			//String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
			if (fieldObject == null)
				continue;
			
			if (field instanceof Reference || ElementUtil.isEnumeration(fieldObject)) {
				String fieldPackageName = ModelLayerHelper.getElementPackageName(fieldObject);
				//String fieldPackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
				boolean isContained = FieldUtil.isContained(field);
				
				if (CodeUtil.isPhoneNumberField(field)) { 
					if (phoneNumberMapHandled)
						continue;
					phoneNumberMapHandled = true;
				}
				
				String structure = field.getStructure();
				if (fieldPackageName.equals("org.aries.common")) {
					modelOperations.add(createOperation_initializeHandler(element, field, fieldObject, ""));
				} else {
					if (structure.equals("item")) {
						if (isContained)
							addInstanceOperation(modelOperations, createOperation_initializeHandler(element, field, fieldObject, "Info"));
						else addInstanceOperation(modelOperations, createOperation_initializeHandler(element, field, fieldObject, "Select"));
					} else if (structure.equals("list") || structure.equals("set")) {
						if (isContained)
							addInstanceOperation(modelOperations, createOperation_initializeHandler(element, field, fieldObject, "List"));
						else addInstanceOperation(modelOperations, createOperation_initializeHandler(element, field, fieldObject, "Select"));
					}
				}
			}
		}
		
		sortModelOperationsByName(modelOperations);
		return modelOperations;
	}

	protected ModelOperation createOperation_initializeHandler(Element element, Field field, Type fieldObject, String extension) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String fieldPackageName = ModelLayerHelper.getElementPackageName(fieldObject);
		String fieldClassName = ModelLayerHelper.getElementClassName(fieldObject);
		String fieldKeyClassName = ModelLayerHelper.getElementKeyClassName(fieldObject);
		String fieldElementNameCapped = ModelLayerHelper.getElementNameCapped(fieldObject);
		String fieldElementNameUncapped = ModelLayerHelper.getElementNameUncapped(fieldObject);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String managerName = fieldElementNameUncapped + extension + "Manager";
		String managerNameCapped = NameUtil.capName(managerName);
		String attributeType = fieldElementNameCapped + extension;
		String attributeName = fieldNameCapped;
		String fieldInstanceIdUncapped = elementNameUncapped + fieldNameCapped;
		String fieldInstanceIdCapped = elementNameCapped + fieldNameCapped;
		boolean isContained = FieldUtil.isContained(field);
		String structure = field.getStructure();
		String structureCapped = NameUtil.capName(structure);

//		if (fieldElementNameUncapped.equals("permission"))
//			System.out.println();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addImportedClass(field);
		ModelParameter modelParameter = null;
		
		Buf buf = new Buf();
		
		if (CodeUtil.isPhoneNumberField(field)) {
			fieldInstanceIdUncapped = elementNameUncapped + "PhoneNumbers";
			fieldInstanceIdCapped = elementNameCapped + "PhoneNumbers";
			modelParameter = createParameter("Map<PhoneLocation, PhoneNumber>", "phoneNumbers");
			
			buf.putLine2(elementClassName+" "+elementNameUncapped+" = get"+elementClassName+"();");

			//buf.putLine2("Map<PhoneLocation, PhoneNumber> phoneNumbers = phoneNumberManager.getPhoneNumberMap();");
			//user.getPhoneNumbers().put(PhoneNumberType.HOME, phoneNumbers.get(PhoneNumberType.HOME));
			//user.getPhoneNumbers().put(PhoneNumberType.CELL, phoneNumbers.get(PhoneNumberType.CELL));
			
			if (ElementUtil.isFieldExists(element, "cellPhone"))
				buf.putLine2(elementNameUncapped+".setCellPhone(phoneNumbers.get(PhoneLocation.CELL));");
			if (ElementUtil.isFieldExists(element, "homePhone"))
				buf.putLine2(elementNameUncapped+".setHomePhone(phoneNumbers.get(PhoneLocation.HOME));");
			if (ElementUtil.isFieldExists(element, "workPhone"))
				buf.putLine2(elementNameUncapped+".setWorkPhone(phoneNumbers.get(PhoneLocation.WORK));");
			if (ElementUtil.isFieldExists(element, "faxPhone"))
				buf.putLine2(elementNameUncapped+".setFaxPhone(phoneNumbers.get(PhoneLocation.FAX));");
			if (ElementUtil.isFieldExists(element, "otherPhone"))
				buf.putLine2(elementNameUncapped+".setOtherPhone(phoneNumbers.get(PhoneLocation.OTHER));");
			
			modelUnit.addImportedClass("org.aries.common.PhoneNumber");
			modelUnit.addImportedClass("org.aries.common.PhoneLocation");
			modelUnit.addImportedClass("java.util.Map");
			
		} else if (structure.equals("item")) {
			if (extension.equals("Select"))
				buf.putLine2("get"+elementNameCapped+"().set"+fieldNameCapped+"("+fieldNameUncapped+");");
			else buf.putLine2("get"+elementNameCapped+"().set"+fieldNameCapped+"("+fieldNameUncapped+");");
			modelParameter = createParameter(fieldClassName, fieldNameUncapped);

			//if (extension.equals("Select"))
			//	buf.putLine2(elementNameUncapped+".set"+fieldNameCapped+"("+managerName+".getSelectedRecord());");
			//else buf.putLine2(elementNameUncapped+".set"+fieldNameCapped+"("+managerName+".get"+fieldClassName+"());");
			
		} else if (structure.equals("list") || structure.equals("set")) {
			modelParameter = createParameter(structureCapped+"<"+fieldClassName+">", fieldNameUncapped);

			if (isContained) {
				buf.putLine2("get"+elementNameCapped+"().set"+fieldNameCapped+"("+fieldNameUncapped+");");
				buf.putLine2(managerName+".initialize("+fieldNameUncapped+");");
				//buf.putLine2(fieldClassName+" "+fieldElementNameUncapped+" = "+fieldElementNameUncapped+"InfoManager.get"+fieldClassName+"();");
				//buf.putLine2("CollectionUtil.add("+elementNameUncapped+".get"+fieldNameCapped+"(), "+fieldElementNameUncapped+");");
				//buf.putLine2(managerName+".initialize("+elementNameUncapped+".get"+fieldNameCapped+"());");
				//buf.putLine2(elementNameUncapped+".get"+fieldNameCapped+"().addAll("+managerName+".getRecordList());");
				modelUnit.addImportedClass(fieldPackageName + "." + fieldClassName);
				//modelUnit.addImportedClass("org.aries.util.CollectionUtil");
			} else {
				buf.putLine2(elementClassName+" "+elementNameUncapped+" = get"+elementClassName+"();");
				buf.putLine2(elementNameUncapped+".get"+fieldNameCapped+"().clear();");
				buf.putLine2(elementNameUncapped+".get"+fieldNameCapped+"().addAll("+fieldNameUncapped+");");
				//buf.putLine2(elementNameUncapped+".get"+fieldNameCapped+"().addAll("+managerName+".getSelectedRecordList());");
			}
		} else if (structure.equals("map")) {
			modelParameter = createParameter("Map<"+fieldKeyClassName+", "+fieldClassName+">", fieldNameUncapped);
		}

		String action = "Updated";
		if (extension.equals("Select"))
			action = "Selected";
		modelOperation.setName("handle" + fieldInstanceIdCapped + action);
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation(action));
		modelOperation.addParameter(modelParameter);
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation(fieldInstanceIdUncapped + action));
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_activate(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		boolean joinConversation = !ElementUtil.isRoot(element);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("activate");
		
		Buf buf = new Buf();
		buf.putLine2("initializeContext();");
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = BeanContext.getFromSession(getInstanceId());");
		buf.putLine2("if ("+elementNameUncapped+" == null)");
		buf.putLine2("	new"+elementClassName+"();");
		buf.putLine2("else edit"+elementClassName+"("+elementNameUncapped+");");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_newRecord(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("newRecord");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return new"+elementClassName+"();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_newRecord2(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		boolean joinConversation = !ElementUtil.isRoot(element);
		//modelOperation.addAnnotation(AnnotationUtil.createBeginAnnotation(joinConversation));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("new" +elementClassName);
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = create();");
		buf.putLine2("	selectionContext.resetOrigin();");
		buf.putLine2("	selectionContext.setSelection(\""+elementNameUncapped+"\",  "+elementNameUncapped+");");
		buf.putLine2("	String url = "+elementNameUncapped+"PageManager.initialize"+elementClassName+"CreationPage("+elementNameUncapped+");");
		buf.putLine2("	"+elementNameUncapped+"PageManager.pushContext("+elementNameUncapped+"Wizard);");
		buf.putLine2("	initialize("+elementNameUncapped+");");
		buf.putLine2("	return url;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	handleException(e);");
		buf.putLine2("	return null;");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_create(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//boolean joinConversation = !ElementUtil.isRoot(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("create");
		modelOperation.setResultType(elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = "+elementClassName+"Util.create();");
		buf.putLine2("return "+elementNameUncapped+";");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_clone(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//boolean joinConversation = !ElementUtil.isRoot(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("clone");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType(elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2(elementNameUncapped+" = "+elementClassName+"Util.clone("+elementNameUncapped+");");
		buf.putLine2("return "+elementNameUncapped+";");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
//	protected ModelOperation createOperation_createRecord(Element element) {
//		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
//		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		//String fieldPackageName = ModelLayerHelper.getElementPackageName(fieldElement);
//
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setName("createRecord");
//		modelOperation.setResultType(elementClassName);
//		
//		Buf buf = new Buf();
//		buf.putLine2(elementClassName+" "+elementNameUncapped+" = new "+elementClassName+"();");
//		//buf.putLine2("assureRecord("+elementNameUncapped+");");
//		
//		//List<Attribute> attributes = ElementUtil.getAttributes(element);
//		//List<Reference> references = ElementUtil.getReferences(element);
//		//buf.put(createOperation_createRecord_sourceCode(element, attributes));
//		//buf.put(createOperation_createRecord_sourceCode(element, references));
//		
//		//buf.putLine2("	"+elementName+".setEmailAddress(new EmailAddress());");
//		buf.putLine2("return "+elementNameUncapped+";");
//		
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}

//	protected <F extends Field> String createOperation_createRecord_sourceCode(Element element, List<F> fields) {
//		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
//		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		
//		Buf buf = new Buf();
//		Map<F, Element> map = createFieldTypeMap(fields);
//		List<F> fieldList = sortTypesByInstanceName(map.keySet());
//		Iterator<F> iterator = fieldList.iterator();
//		while (iterator.hasNext()) {
//			F field = iterator.next();
//			Element fieldElement = map.get(field);
//
//			String fieldType = field.getType();
//			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
//			String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
//			String fieldInstanceIdUncapped = elementNameUncapped + fieldNameCapped;
//			String fieldInstanceIdCapped = elementNameCapped + fieldNameCapped;
//			boolean isContained = FieldUtil.isContained(field);
//			String structure = field.getStructure();
//			
//			if (field instanceof Attribute) {
//				Object defaultValue = FieldUtil.getDefaultValue(field);
//				if (defaultValue != null) {
//					Enumeration fieldEnumeration = context.getEnumerationByType(fieldType);
//					if (fieldEnumeration != null) {
//						String enumerationClassName = TypeUtil.getClassName(fieldType);
//						//TODO VALIDATE that DefaultValue is an actual member of Enumeration
//						buf.putLine2(elementNameUncapped+".set"+fieldNameCapped+"("+enumerationClassName+"."+defaultValue+");");
//					} else {
//						buf.putLine2(elementNameUncapped+".set"+fieldNameCapped+"("+defaultValue+");");
//					}
//				}
//				continue;
//			}
//
//			String fieldClassName = ModelLayerHelper.getElementClassName(fieldElement);
//			String fieldElementNameCapped = ModelLayerHelper.getElementNameCapped(fieldElement);
//			String fieldElementNameUncapped = ModelLayerHelper.getElementNameUncapped(fieldElement);
//
//			if (structure.equals("item")) {
//				String managerSuffix = "InfoManager";
//				String fieldPackageName = ModelLayerHelper.getElementPackageName(fieldElement);
//				if (fieldPackageName.equals("org.aries.common")) 
//					managerSuffix = "Manager";
//				String managerName = fieldElementNameUncapped + managerSuffix;
//				buf.putLine2(elementNameUncapped+".set"+fieldNameCapped+"("+managerName+".createRecord());");
//				
//			} else if (structure.equals("list")) {
//				//nothing for now
//			}
//		}
//		
//		return buf.get();
//	}

	protected ModelOperation createOperation_viewRecord(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("viewRecord");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return view"+elementClassName+"();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_viewRecord2(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		//boolean joinConversation = !ElementUtil.isRoot(element);
		//modelOperation.addAnnotation(AnnotationUtil.createBeginAnnotation(joinConversation));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("view" +elementClassName);
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		//buf.putLine2("SelectionContext selectionContext = getSelectionContext();");
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = selectionContext.getSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("String url = view"+elementClassName+"("+elementNameUncapped+");");
		buf.putLine2("return url;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_viewRecord3(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("view"+elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		//buf.putLine2("	"+elementNameUncapped+" = clone("+elementNameUncapped+");");
		buf.putLine2("	String url = "+elementNameUncapped+"PageManager.initialize"+elementClassName+"SummaryView("+elementNameUncapped+");");
		buf.putLine2("	"+elementNameUncapped+"PageManager.pushContext("+elementNameUncapped+"Wizard);");
		buf.putLine2("	initialize("+elementNameUncapped+");");
		buf.putLine2("	return url;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	handleException(e);");
		buf.putLine2("	return null;");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_editRecord(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("editRecord");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return edit"+elementClassName+"();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_editRecord2(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		//boolean joinConversation = !ElementUtil.isRoot(element);
		//modelOperation.addAnnotation(AnnotationUtil.createBeginAnnotation(joinConversation));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("edit" +elementClassName);
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		//buf.putLine2("SelectionContext selectionContext = getSelectionContext();");
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = selectionContext.getSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("String url = edit"+elementClassName+"("+elementNameUncapped+");");
		buf.putLine2("return url;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_editRecord3(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		//boolean joinConversation = !ElementUtil.isRoot(element);
		//modelOperation.addAnnotation(AnnotationUtil.createBeginAnnotation(joinConversation));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("edit" +elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	//"+elementNameUncapped+" = clone("+elementNameUncapped+");");
		buf.putLine2("	selectionContext.resetOrigin();");
		buf.putLine2("	selectionContext.setSelection(\""+elementNameUncapped+"\",  "+elementNameUncapped+");");
		buf.putLine2("	String url = "+elementNameUncapped+"PageManager.initialize"+elementClassName+"UpdatePage("+elementNameUncapped+");");
		buf.putLine2("	"+elementNameUncapped+"PageManager.pushContext("+elementNameUncapped+"Wizard);");
		buf.putLine2("	initialize("+elementNameUncapped+");");
		buf.putLine2("	return url;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	handleException(e);");
		buf.putLine2("	return null;");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_saveRecord(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("save" +elementClassName);
		
		Buf buf = new Buf();
		//buf.putLine2("setModule(\""+elementClassName+"\");");
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = get"+elementClassName+"();");
		//buf.putLine2("enrich"+elementClassName+"("+elementNameUncapped+");");
		buf.putLine2("if (validate"+elementClassName+"("+elementNameUncapped+")) {");
		
		if (ElementUtil.isRoot(element)) {
			buf.putLine2("	if (isImmediate())");
			buf.putLine2("		persist"+elementClassName+"("+elementNameUncapped+");");
			buf.putLine2("	outject(\""+elementNameUncapped+"\", "+elementNameUncapped+");");
			//buf.putLine2("	raiseEvent(actionEvent);");
			
		} else {
			buf.putLine2("	save"+elementClassName+"("+elementNameUncapped+");");
		}

		buf.putLine2("}");

		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.aries.save"+elementClassName));
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_persistRecord(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		boolean hasId = ElementUtil.hasField(element, "id");
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("persist" +elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		if (hasId == false) {
			buf.putLine2("save"+elementClassName+"("+elementNameUncapped+");");
		} else {
			buf.putLine2("Long id = "+elementNameUncapped+".getId();");
			buf.putLine2("if (id != null) {");
			buf.putLine2("	save"+elementClassName+"("+elementNameUncapped+");");
			if (ElementUtil.isRoot(element)) {
				buf.putLine2("} else {");
				buf.putLine2("	add"+elementClassName+"("+elementNameUncapped+");");
			}
			buf.putLine2("}");
		}

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_saveRecord2(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		boolean hasId = ElementUtil.hasField(element, "id");
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("save" +elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		if (ElementUtil.isRoot(element) && hasId) {
			buf.putLine2("	get"+elementClassName+"Service().save"+elementClassName+"("+elementNameUncapped+");");
			//buf.putLine2("	raiseEvent(\"org.aries.refresh"+elementClassName+"List\");");
			//buf.putLine2("	raiseEvent(actionEvent);");
			//buf.putLine2("	hide();");
		} else {
			//buf.putLine2("	//TODO add specific persitence call here");
			buf.putLine2("	save"+elementClassName+"ToSystem("+elementNameUncapped+");");
			buf.putLine2("	"+elementNameUncapped+"EventManager.fireAddedEvent("+elementNameUncapped+");");
			//buf.putLine2("	post"+elementClassName+"AddedEvent();");
		}
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	handleException(e);");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_saveRecordToSystem(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("save"+elementClassName+"ToSystem");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2(elementNameUncapped+"DataManager.save"+elementClassName+"("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_handleSaveRecord(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handleSave" +elementClassName);
		ModelParameter modelParameter = createParameter(elementClassName, elementNameUncapped);
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Add"));
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		//buf.putLine2(elementClassName+" "+elementNameUncapped+" = event.get"+elementClassName+"();");
		buf.putLine2("save"+elementClassName+"("+elementNameUncapped+");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_addRecord(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		boolean hasId = ElementUtil.hasField(element, "id");
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("add" +elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		if (hasId) {
			buf.putLine2("	Long id = get"+elementClassName+"Service().add"+elementClassName+"("+elementNameUncapped+");");
			buf.putLine2("	Assert.notNull(id, \""+elementClassName+" Id should not be null\");");
			buf.putLine2("	raiseEvent(\"org.aries.refresh"+elementClassName+"List\");");
			buf.putLine2("	raiseEvent(actionEvent);");
		} else {
			buf.putLine2("	//TODO");
		}
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	handleException(e);");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_enrichRecord(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("enrich" +elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
//	protected ModelOperation createOperation_assureRecord(Element element) {
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
//		
//		ModelOperation modelOperation = new ModelOperation();
//		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
//		modelOperation.setModifiers(Modifier.PROTECTED);
//		modelOperation.setName("assureRecord");
//		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
//
//		Buf buf = new Buf();
//		List<Field> fields = ElementUtil.getFields(element);
//		Map<Field, Element> map = createFieldTypeMap(fields);
//		List<Field> fieldList = sortTypesByInstanceName(map.keySet());
//		Iterator<Field> iterator = fieldList.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			Element fieldElement = map.get(field);
//			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
//			String structure = field.getStructure();
//
//			if (FieldUtil.isBoolean(field)) {
//				Object defaultValue = FieldUtil.getDefaultValue(field);
//				if (defaultValue != null)
//					defaultValue = Boolean.TRUE;
//				buf.putLine2("if ("+elementNameUncapped+".get"+fieldNameCapped+"() == null)");
//				buf.putLine2("	"+elementNameUncapped+".set"+fieldNameCapped+"("+defaultValue+");");
//				continue;
//			}
//			
//			if (field instanceof Reference == false)
//				continue;
//			if (!structure.equals("item"))
//				continue;
//
//			String fieldElementNameCapped = ModelLayerHelper.getElementNameCapped(fieldElement);
//			buf.putLine2("if ("+elementNameUncapped+".get"+fieldNameCapped+"() == null)");
//			buf.putLine2("	"+elementNameUncapped+".set"+fieldNameCapped+"("+fieldElementNameCapped+"Util.create());");
//		}
//		
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	
	protected ModelOperation createOperation_validateRecord(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("validate");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("boolean");

		Buf buf = new Buf();
		buf.putLine2("return validate"+elementClassName+"("+elementNameUncapped+");");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_validateRecord2(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("validate"+elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("boolean");

		Buf buf = new Buf();
		buf.putLine2("Validator validator = getValidator();");
		buf.putLine2("boolean isValid = "+elementClassName+"Util.validate("+elementNameUncapped+");");
		buf.putLine2("Display display = getFromSession(\"display\");");
		buf.putLine2("display.setModule(\""+elementNameUncapped+"Info\");");
		buf.putLine2("display.addErrors(validator.getMessages());");
		buf.putLine2("//FacesContext.getCurrentInstance().isValidationFailed()");
		buf.putLine2("setValidated(isValid);");
		buf.putLine2("return isValid;");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
//	protected ModelOperation createOperation_validateRecord(Element element) throws Exception {
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
//		
//		ModelOperation modelOperation = new ModelOperation();
//		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setName("validate");
//		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
//		modelOperation.setResultType("boolean");
//
//		Buf buf = new Buf();
//		buf.putLine2("Validator validator = Validator.getValidator();");
//
//		List<Field> fields = ElementUtil.getFields(element);
//		Map<Field, Element> map = createFieldTypeMap(fields);
//		List<Field> fieldList = sortTypesByInstanceName(map.keySet());
//		Iterator<Field> iterator = fieldList.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			Type fieldObject = map.get(field);
//			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
//			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
//			String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
//			String fieldInstanceId = getInstanceId(element, field);
//			String structure = field.getStructure();
//
//			if (FieldUtil.isId(field))
//				continue;
//			if (!FieldUtil.isRequired(field))
//				continue;
//
////			//TODO handle special case for now
////			if (fieldPackageName.equals("org.aries.common"))
////				modelOperation.addImportedClass("org.aries.common.util." + fieldClassName + "Util");
////			else modelOperation.addImportedClass(getNamespacePackageName() + ".util." + fieldClassName + "Util");
//			
//			if (field instanceof Attribute) {
//				if (FieldUtil.isString(field))
//					buf.putLine2("validator.notEmpty("+elementNameUncapped+".get"+fieldNameCapped+"(), \"\\\""+fieldNameCapped+"\\\" must be specified\");");
//				else if (structure.equals("item"))
//					buf.putLine2("validator.notNull("+elementNameUncapped+".get"+fieldNameCapped+"(), \"\\\""+fieldNameCapped+"\\\" must be specified\");");
//				else buf.putLine2("validator.notEmpty("+elementNameUncapped+".get"+fieldNameCapped+"(), \"At least one of \\\""+fieldNameCapped+"\\\" must be specified\");");
//				continue;
//			}
//			
//			if (structure.equals("item")) {
//				buf.putLine2("validator.isTrue("+fieldClassName+"Util.isEmpty("+elementNameUncapped+".get"+fieldNameCapped+"()), \"\\\""+fieldNameCapped+"\\\" must be specified\");");
//				modelOperation.addImportedClass(fieldPackageName + ".util." + fieldClassName + "Util");
//			} else {
//				buf.putLine2("validator.isTrue("+fieldClassName+"Util.isEmpty("+elementNameUncapped+".get"+fieldNameCapped+"()), \"At least one of \\\""+fieldNameCapped+"\\\" must be specified\");");
//				modelOperation.addImportedClass(fieldPackageName + ".util." + fieldClassName + "Util");
//			}
//		}
//
//		buf.putLine2("");
//		iterator = fieldList.iterator();
//		boolean lineBreakNeeded = false;
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			Type fieldObject = map.get(field);
//			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
//			//String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
//			String fieldInstanceId = getInstanceId(element, field);
//			String structure = field.getStructure();
//
//			if (field instanceof Reference == false)
//				continue;
//			//if (!FieldUtil.isRequired(field))
//			//	continue;
//			
//			if (isPhoneNumberField(field)) {
//				buf.putLine2("phoneNumberManager.validate("+elementNameUncapped+".get"+fieldNameCapped+"());");
//				modelOperation.addImportedClass(field);
//				continue;
//			}
//			
//			String managerName = "";
//			String fieldElementNameUncapped = ModelLayerHelper.getElementNameUncapped(fieldObject);
//			String fieldPackageName = ModelLayerHelper.getElementPackageName(fieldObject);
//			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
//			if (fieldPackageName.equals("org.aries.common")) {
//				managerName = fieldElementNameUncapped + "Manager";
//			} else if (structure.equals("item")) {
//				managerName = fieldElementNameUncapped + "InfoManager";
//			} else managerName = fieldElementNameUncapped + "ListManager";
//			
//			buf.putLine2(managerName+".validate("+elementNameUncapped+".get"+fieldNameCapped+"());");
//			lineBreakNeeded = true;
//		}
//		
//		if (lineBreakNeeded)
//			buf.putLine2("");
//		buf.putLine2("display.addErrors(validator.getMessages());");
//		buf.putLine2("boolean isValid = validator.isValid();");
//		buf.putLine2("return isValid;");
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	
	
//	protected ModelOperation createOperation_setTitle(Element element) throws Exception {
//		String className = ModelLayerHelper.getElementClassName(element);
//		String elementName = ModelLayerHelper.getElementNameUncapped(element);
//
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setName("setTitle");
//		modelOperation.addParameter(createParameter(className, elementName));
//		
//		Buf buf = new Buf();
//		buf.putLine2("if ("+className+"Helper.nameExists("+elementName+"))");
//		buf.putLine2("	setTitle("+className+"Helper.getName("+elementName+"));");
//		buf.putLine2("else setTitle(\""+className+" Information\");");
//		
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	
//	protected ModelOperation createOperation_setHeader(Element element) throws Exception {
//		String className = ModelLayerHelper.getElementClassName(element);
//		String elementName = ModelLayerHelper.getElementNameUncapped(element);
//
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setName("setHeader");
//		modelOperation.addParameter(createParameter(className, elementName));
//		
//		Buf buf = new Buf();
//		buf.putLine2("if ("+className+"Helper.nameExists("+elementName+"))");
//		buf.putLine2("	setHeader(\""+className+" Information for: \"+"+className+"Helper.getName("+elementName+"));");
//		buf.putLine2("else setHeader(\"New "+className+"\");");
//		
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	
//	protected ModelOperation createOperation_setMessage(Element element) throws Exception {
//		String className = ModelLayerHelper.getElementClassName(element);
//		String elementName = ModelLayerHelper.getElementNameUncapped(element);
//
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setName("setMessage");
//		modelOperation.addParameter(createParameter(className, elementName));
//		
//		Buf buf = new Buf();
//		buf.putLine2("display.setModule(\""+className+"\");");
//		buf.putLine2("if ("+className+"Helper.nameExists("+elementName+"))");
//		buf.putLine2("	display.info(\"Specify information for \"+"+className+"Helper.getName("+elementName+"));");
//		buf.putLine2("else display.info(\"Specify information for new "+className+" Record\");");
//
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	

	protected ModelOperation createOperation_promptRemoveRecord(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("promptRemove"+elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2("display = getFromSession(\"display\");");
		buf.putLine2("display.setModule(\""+elementNameUncapped+"Info\");");
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = selectionContext.getSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("if ("+elementNameUncapped+" == null) {");
		buf.putLine2("	display.error(\""+elementClassName+" record must be selected.\");");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_removeRecord(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handleRemove"+elementClassName);
		ModelParameter modelParameter = createParameter(elementClassName, elementNameUncapped);
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Remove"));
		modelOperation.addParameter(modelParameter);
		modelOperation.setResultType("String");

		Buf buf = new Buf();
		buf.putLine2("display = getFromSession(\"display\");");
		buf.putLine2("display.setModule(\""+elementNameUncapped+"Info\");");
		buf.putLine2("try {");
		//buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = getRecord();");
		buf.putLine2("	display.info(\"Removing "+elementClassName+" \"+"+elementClassName+"Util.getLabel("+elementNameUncapped+")+\" from the system.\");");
		buf.putLine2("	remove"+elementClassName+"FromSystem("+elementNameUncapped+");");
		//if (ElementUtil.isHierarchical(element))
		//	buf.putLine2("	"+elementNameUncapped+"TreeManager.clearSelection();");
		buf.putLine2("	selectionContext.clearSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("	"+elementNameUncapped+"EventManager.fireClearSelectionEvent();");
		buf.putLine2("	"+elementNameUncapped+"EventManager.fireRemovedEvent("+elementNameUncapped+");");
		buf.putLine2("	workspaceEventManager.fireRefreshEvent();");
		//buf.putLine2("	post"+elementClassName+"RemovedEvent();");
		buf.putLine2("	return null;");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	handleException(e);");
		buf.putLine2("	return null;");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_removeRecordFromSystem(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("remove"+elementClassName+"FromSystem");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+"DataManager.remove"+elementClassName+"("+elementNameUncapped+"))");
		buf.putLine2("	setRecord(null);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_cancelRecord(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.aries.cancel"+elementClassName));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("cancel"+elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2("BeanContext.removeFromSession(\""+elementNameUncapped+"\");");
		buf.putLine2(""+elementNameUncapped+"PageManager.removeContext("+elementNameUncapped+"Wizard);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
