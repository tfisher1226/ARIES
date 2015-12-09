package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.ReferencedBy;
import nam.model.Service;
import nam.model.Type;
import nam.model.util.ElementUtil;

import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelReference;


/**
 * Builds an Element Record List Manager Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementListManagerBuilder extends AbstractElementManagerBuilder {

	public ElementListManagerBuilder(GenerationContext context) {
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
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "ListManager";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		modelClass.setParentClassName("AbstractDomainListManager<"+elementName+", "+elementName+"ListObject>");
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
		if (!ElementUtil.isEnumeration(type))
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
		
		//modelClass.addImportedClass(elementPackageName + ".Project");
		//modelClass.addImportedClass(elementPackageName + ".util.ProjectUtil");
		
		modelClass.addImportedClass("nam.ui.design.SelectionContext");
		//modelClass.addImportedClass("nam.ui.design.WorkspaceManager");
		modelClass.addImportedClass("org.aries.ui.AbstractDomainListManager");
		
		if (ElementUtil.isRoot(type)) {
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
			modelClass.addImportedClass("org.aries.ui.manager.ExportManager");
			modelClass.addImportedClass("java.util.Collection");
			//modelClass.addImportedClass("java.util.List");

		} else if (ElementUtil.isElement(type)) {
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
			modelClass.addImportedClass("org.aries.ui.manager.ExportManager");
			modelClass.addImportedClass("java.util.Collection");
			
		} else if (ElementUtil.isEnumeration(type)) {
			modelClass.addImportedClass("java.util.Arrays");
			modelClass.addImportedClass("java.util.Collection");
		}
		
//		modelClass.addImportedClass("org.jboss.seam.ScopeType");
//		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
//		//modelClass.addImportedClass("org.jboss.seam.annotations.Begin");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Observer");
//		//if (!elementClassName.equals("Role") && ElementUtil.isElement(type))
//		//	modelClass.addImportedClass("org.jboss.seam.annotations.Role");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Startup");
//		modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
		
		modelClass.addImportedClass("javax.enterprise.context.SessionScoped");
		if (!ElementUtil.isEnumeration(type))
			modelClass.addImportedClass("javax.enterprise.event.Observes");
		modelClass.addImportedClass("javax.inject.Inject");
		modelClass.addImportedClass("javax.inject.Named");
		
		if (!ElementUtil.isEnumeration(type)) {
			modelClass.addImportedClass("org.aries.runtime.BeanContext");
			//modelClass.addImportedClass("org.aries.ui.Display");
			//modelClass.addImportedClass("org.aries.ui.AbstractTabListManager");
			//modelClass.addImportedClass("org.aries.ui.event.ResetEvent");
			modelClass.addImportedClass("org.aries.ui.event.Cancelled");
			modelClass.addImportedClass("org.aries.ui.event.Refresh");
			modelClass.addImportedClass("org.aries.ui.event.Export");
		}
		
		//modelClass.addImportedClass("java.util.Map");
		super.initializeImportedClasses(modelClass);
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type type) throws Exception {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		
		//modelClass.getClassAnnotations().add(AnnotationUtil.createStartupAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createAnnotation("AutoCreate"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createNameAnnotation(elementNameUncapped+"ListManager"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		//if (ElementUtil.isElement(type)) {
		//	if (elementClassName.equals("Role"))
		//		modelClass.getClassAnnotations().add(AnnotationUtil.createRoleAnnotation2("main"+elementNameCapped+"ListManager", ScopeType.SESSION));
		//	else modelClass.getClassAnnotations().add(AnnotationUtil.createRoleAnnotation("main"+elementNameCapped+"ListManager", ScopeType.SESSION));
		//}
		////modelClass.getClassAnnotations().add(AnnotationUtil.createRestrictAnnotation("#{identity.loggedIn}"));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressSerialWarning());
		
		modelClass.getClassAnnotations().add(AnnotationUtil.createSessionScopedAnnotation());
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"ListManager"));
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
		if (!ElementUtil.isEnumeration(element)) {
			modelClass.addInstanceReference(createReference_ElementManager(element, "DataManager"));
			modelClass.addInstanceReference(createReference_ElementManager(element, "EventManager"));
			modelClass.addInstanceReference(createReference_ElementManager(element, "InfoManager"));
		}
		modelClass.addInstanceReference(createReference_SelectionContext(element));
	}

	public ModelReference createReference_ElementManager(Type element, String manager) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String packageName = elementPackageName;
		String className = elementClassName + manager;
		String elementName = elementNameUncapped + manager;
		
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
	
	public ModelReference createReference_SelectionContext(Type element) {
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

	protected void initializeInstanceOperations(ModelClass modelClass, Type type) throws Exception {
		modelClass.addInstanceOperation(createOperation_getClientId(type));
		modelClass.addInstanceOperation(createOperation_getTitle(type));
		if (ElementUtil.hasIdField(type))
			modelClass.addInstanceOperation(createOperation_getRecordId(type));
		modelClass.addInstanceOperation(createOperation_getRecordKey(type));
		modelClass.addInstanceOperation(createOperation_getRecordName(type));
		modelClass.addInstanceOperation(createOperation_getRecordClass(type));
		modelClass.addInstanceOperation(createOperation_getRecord(type));
		modelClass.addInstanceOperation(createOperation_getSelectedRecord(type));
		modelClass.addInstanceOperation(createOperation_getSelectedRecordLabel(type));
		modelClass.addInstanceOperation(createOperation_setSelectedRecord(type));
		if (!ElementUtil.isEnumeration(type))
			modelClass.addInstanceOperation(createOperation_fireSelectedEvent(type));
		modelClass.addInstanceOperation(createOperation_isSelected(type));
		modelClass.addInstanceOperation(createOperation_isChecked(type));
		modelClass.addInstanceOperation(createOperation_createRowItem(type));
		//modelClass.addInstanceOperation(createOperation_getHelper(type));
		//if (ElementUtil.isRoot(type))
		//	modelClass.addInstanceOperation(createOperation_getService(type));
		//if (ElementUtil.isElement(type))
		//	modelClass.addInstanceOperation(createOperation_getInfoManager(type));
		modelClass.addInstanceOperation(createOperation_reset(type));
		modelClass.addInstanceOperation(createOperation_initialize(type));
		//modelClass.addInstanceOperation(createOperation_handleRefresh(type));
		//if (ElementUtil.isElement(type))
		//	modelClass.addInstanceOperations(createOperations_handleSelected((Element) type));
		modelClass.addInstanceOperation(createOperation_refreshModel(type));
		//if (ElementUtil.isRoot(type) || ElementUtil.isEnumeration(type))
			modelClass.addInstanceOperation(createOperation_createRecordList(type));	
		if (ElementUtil.isElement(type)) {
			modelClass.addInstanceOperation(createOperation_viewRecord(type));
			modelClass.addInstanceOperation(createOperation_viewRecord2(type));
			modelClass.addInstanceOperation(createOperation_viewRecord3(type));
			modelClass.addInstanceOperation(createOperation_editRecord(type));
			modelClass.addInstanceOperation(createOperation_editRecord2(type));
			modelClass.addInstanceOperation(createOperation_editRecord3(type));
			modelClass.addInstanceOperation(createOperation_removeRecord(type));
			modelClass.addInstanceOperation(createOperation_removeRecord2(type));
			modelClass.addInstanceOperation(createOperation_removeRecord3(type));
			modelClass.addInstanceOperation(createOperation_cancelRecord(type));
			modelClass.addInstanceOperation(createOperation_validateRecords(type));
			modelClass.addInstanceOperation(createOperation_exportRecordList(type));
		}
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
		buf.putLine2("return \""+elementNameUncapped+"List\";");
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
		buf.putLine2("return \""+elementClassName+" List\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getRecordId(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getRecordId");
		modelOperation.setResultType("Object");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(type)) {
			buf.putLine2("return "+elementNameUncapped+".ordinal();");
			
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2("return "+elementNameUncapped+".getId();");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getRecordKey(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getRecordKey");
		modelOperation.setResultType("Object");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(type))
			buf.putLine2("return "+elementNameUncapped+".name();");
		else buf.putLine2("return "+elementClassName+"Util.getKey("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getRecordName(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getRecordName");
		modelOperation.setResultType("String");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(type)) {
			buf.putLine2("return "+elementNameUncapped+".name();");
			
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2("return "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
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
	
	protected ModelOperation createOperation_getSelectedRecord(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getSelectedRecord");
		modelOperation.setResultType(elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2("return super.getSelectedRecord();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getSelectedRecordLabel(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getSelectedRecordLabel");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(element))
			buf.putLine2("return selectedRecord != null ? selectedRecord.name() : null;");
		else buf.putLine2("return selectedRecord != null ? "+elementClassName+"Util.getLabel(selectedRecord) : null;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_setSelectedRecord(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("setSelectedRecord");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("super.setSelectedRecord("+elementNameUncapped+");");
		if (!ElementUtil.isEnumeration(element))
			buf.putLine2("fireSelectedEvent("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_fireSelectedEvent(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("fireSelectedEvent");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2(elementNameUncapped+"EventManager.fireSelectedEvent("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_isSelected(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("isSelected");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("boolean");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" selection = selectionContext.getSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("boolean selected = selection != null && selection.equals("+elementNameUncapped+");");
		buf.putLine2("return selected;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_isChecked(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("isChecked");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("boolean");
		
		Buf buf = new Buf();
		buf.putLine2("Collection<"+elementClassName+"> selection = selectionContext.getSelection(\""+elementNameUncapped+"Selection\");");
		buf.putLine2("boolean checked = selection != null && selection.contains("+elementNameUncapped+");");
		buf.putLine2("return checked;");
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
		buf.putLine2(""+elementClassName+"ListObject listObject = new "+elementClassName+"ListObject("+elementNameUncapped+");");
		buf.putLine2("listObject.setSelected(isSelected("+elementNameUncapped+"));");
		buf.putLine2("listObject.setChecked(isChecked("+elementNameUncapped+"));");
		buf.putLine2("return listObject;");
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
	
	protected ModelOperation createOperation_getInfoManager(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassNameUncapped = NameUtil.uncapName(elementClassName);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"InfoManager");
		modelOperation.setResultType(elementClassName+"InfoManager");
		
		Buf buf = new Buf();
		buf.putLine2("return BeanContext.getFromSession(\""+elementClassNameUncapped+"InfoManager\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_reset(Type type) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		
		//if (ElementUtil.isRoot(type) || ElementUtil.isEnumeration(type))
		//	modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.aries.ui.reset"));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("reset");
		
		Buf buf = new Buf();
		buf.putLine2("refresh();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_initialize(Type element) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize");
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		
		Buf buf = new Buf();
		buf.putLine2("if (recordList != null)");
		buf.putLine2("	initialize(recordList);");
		buf.putLine2("else refreshModel();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_handleRefresh(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handleRefresh");
		//modelOperation.setName("handle"+elementClassName+"ListRefresh");
		ModelParameter modelParameter = createParameter("Object", "object");
		//ModelParameter modelParameter = createParameter(elementClassName, elementNameUncapped);
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Refresh"));
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		buf.putLine2("refreshModel();");
		//buf.putLine2("//TODO implement this");
		//buf.putLine2("List<Project> projectList = selectionContext.getSelection(\"projectList\");");
		//buf.putLine2("List<"+elementClassName+"> applicationList = ProjectUtil.getApplications(projectList);");
		//buf.putLine2("refreshModel("+elementNameUncapped+"List);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	//NOTUSED
	protected List<ModelOperation> createOperations_handleSelected(Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<ReferencedBy> containedByList = element.getReferencedBy();
		Iterator<ReferencedBy> iterator = containedByList.iterator();
		while (iterator.hasNext()) {
			ReferencedBy referencedBy = (ReferencedBy) iterator.next();
			Element parentElement = context.getElementByType(referencedBy.getType());
			Assert.notNull(parentElement, "ReferencedBy element not found: "+referencedBy.getType());
			modelOperations.add(createOperation_handleSelected(parentElement, element));
		}
		return modelOperations;
	}
	
	//NOTUSED
	protected ModelOperation createOperation_handleSelected(Element parent, Element element) {
		String parentClassName = ModelLayerHelper.getElementClassName(parent);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String parentNameUncapped = ModelLayerHelper.getElementNameUncapped(parent);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handle"+parentClassName+"Selected");
		ModelParameter modelParameter = createParameter(parentClassName, parentNameUncapped);
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Selected"));
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		if (parent == element)
			buf.putLine2("selectionContext.setSelection(\""+parentNameUncapped+"\",  "+parentNameUncapped+");");
		buf.putLine2(elementNameUncapped+"PageManager.updateState("+parentNameUncapped+");");
		if (parent == element)
			buf.putLine2("setRecord("+elementNameUncapped+");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_refreshModel(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		//if (ElementUtil.isRoot(type) || ElementUtil.isEnumeration(type))
		//	modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.aries.refresh"+elementClassName+"List"));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refreshModel");
		
		Buf buf = new Buf();
		buf.putLine2("refreshModel(createRecordList());");
//		if (ElementUtil.isRoot(type) || ElementUtil.isEnumeration(type)) {
//			buf.putLine2("refreshModel(createRecordList());");
//		} else {
//			buf.putLine2("refreshModel(recordList);");
//		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_createRecordList(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("createRecordList");
		modelOperation.setResultType("Collection<"+elementClassName+">");
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(type)) {
			buf.putLine2("try {");
			buf.putLine2("	Collection<"+elementClassName+"> "+elementNameUncapped+"List = Arrays.asList("+elementClassName+".values());");
			buf.putLine2("	return "+elementNameUncapped+"List;");
			buf.putLine2("} catch (Exception e) {");
			buf.putLine2("	handleException(e);");
			buf.putLine2("	return null;");
			buf.putLine2("}");
			
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2("try {");
			//buf.putLine2("	"+elementClassName+"DataManager "+elementNameUncapped+"DataManager = BeanContext.getFromSession(\""+elementNameUncapped+"DataManager\");");
			buf.putLine2("	Collection<"+elementClassName+"> "+elementNameUncapped+"List = "+elementNameUncapped+"DataManager.get"+elementClassName+"List();");
			//buf.putLine2("	List<"+elementClassName+"> "+elementNameUncapped+"List = get"+elementClassName+"Service().get"+elementClassName+"List();");
			buf.putLine2("	if ("+elementNameUncapped+"List != null)");
			buf.putLine2("		return "+elementNameUncapped+"List;");
			buf.putLine2("	return recordList;");
			buf.putLine2("} catch (Exception e) {");
			buf.putLine2("	handleException(e);");
			buf.putLine2("	return null;");
			buf.putLine2("}");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_viewRecord(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("view"+elementClassName);
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return view"+elementClassName+"(selectedRecordKey);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
//	protected ModelOperation createOperation_viewRecord2(Type element) throws Exception {
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
//
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setName("view"+elementClassName);
//		modelOperation.addParameter(createParameter("String", "recordId"));
//		modelOperation.setResultType("String");
//		
//		Buf buf = new Buf();
//		buf.putLine2("return view"+elementClassName+"(Long.parseLong(recordId));");
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	
	protected ModelOperation createOperation_viewRecord2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("view"+elementClassName);
		modelOperation.addParameter(createParameter("Object", "recordKey"));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = recordByKeyMap.get(recordKey);");
		buf.putLine2("return view"+elementClassName+"("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_viewRecord3(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("view"+elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		//buf.putLine2(elementClassName+"InfoManager "+elementNameUncapped+"InfoManager = BeanContext.getFromSession(\""+elementNameUncapped+"InfoManager\");");
		buf.putLine2("String url = "+elementNameUncapped+"InfoManager.view"+elementClassName+"("+elementNameUncapped+");");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_editRecord(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("edit"+elementClassName);
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return edit"+elementClassName+"(selectedRecordKey);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
//	protected ModelOperation createOperation_editRecord2(Type element) throws Exception {
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
//
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setName("edit"+elementClassName);
//		modelOperation.addParameter(createParameter("String", "recordId"));
//		modelOperation.setResultType("String");
//		
//		Buf buf = new Buf();
//		buf.putLine2("return edit"+elementClassName+"(Long.parseLong(recordId));");
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	
	protected ModelOperation createOperation_editRecord2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("edit"+elementClassName);
		modelOperation.addParameter(createParameter("Object", "recordKey"));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = recordByKeyMap.get(recordKey);");
		buf.putLine2("return edit"+elementClassName+"("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_editRecord3(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("edit"+elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		//buf.putLine2(elementClassName+"InfoManager "+elementNameUncapped+"InfoManager = BeanContext.getFromSession(\""+elementNameUncapped+"InfoManager\");");
		buf.putLine2("String url = "+elementNameUncapped+"InfoManager.edit"+elementClassName+"("+elementNameUncapped+");");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_removeRecord(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.aries.remove"+elementClassName));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("remove"+elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2("remove"+elementClassName+"(selectedRecordKey);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
//	protected ModelOperation createOperation_removeRecord2(Type element) throws Exception {
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
//
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setName("remove"+elementClassName);
//		modelOperation.addParameter(createParameter("String", "recordId"));
//		
//		Buf buf = new Buf();
//		buf.putLine2("remove"+elementClassName+"(Long.parseLong(recordId));");
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}
	
	protected ModelOperation createOperation_removeRecord2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("remove"+elementClassName);
		modelOperation.addParameter(createParameter("Object", "recordKey"));
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = recordByKeyMap.get(recordKey);");
		buf.putLine2("remove"+elementClassName+"("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_removeRecord3(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("remove"+elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	if ("+elementNameUncapped+"DataManager.remove"+elementClassName+"("+elementNameUncapped+"))");
		//buf.putLine2("	get"+elementClassName+"Service().delete"+elementClassName+"(selectedRecord);");
		buf.putLine2("		clearSelection();");
		buf.putLine2("	refresh();");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	handleException(e);");
		buf.putLine2("}");
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
		ModelParameter modelParameter = createParameter(elementClassName, elementNameUncapped);
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Cancelled"));
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		//buf.putLine2("	Long id = "+elementNameUncapped+".getId();");
		buf.putLine2("	//Object key = "+elementClassName+"Util.getKey("+elementNameUncapped+");");
		buf.putLine2("	//recordByKeyMap.put(key, "+elementNameUncapped+");");
		buf.putLine2("	initialize(recordByKeyMap.values());");
		buf.putLine2("	BeanContext.removeFromSession(\""+elementNameUncapped+"\");");			
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	handleException(e);");
		buf.putLine2("}");
		
		//buf.putLine2("BeanContext.removeFromSession(\""+elementNameUncapped+"\");");
		//buf.putLine2(""+elementNameUncapped+"PageManager.removeContext("+elementNameUncapped+"Wizard);");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_validateRecords(Type element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("validate" + elementClassName);
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementNameUncapped+"List"));
		modelOperation.setResultType("boolean");
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementClassName+"Util.validate("+elementNameUncapped+"List);");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_exportRecordList(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createObserverAnnotation("org.aries.export"+elementClassName+"List"));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("export"+elementClassName+"List");
		ModelParameter modelParameter = createParameter("String", "tableId");
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Export"));
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		//TODO put targetDomain instead of "page" for form
		buf.putLine2("//String tableId = \"pageForm:"+elementNameUncapped+"ListTable\";");
		buf.putLine2("ExportManager exportManager = BeanContext.getFromSession(\"org.aries.exportManager\");");
		buf.putLine2("exportManager.exportToXLS(tableId);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
