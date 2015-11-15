package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;

import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Type;
import nam.model.util.ElementUtil;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelReference;


/**
 * Builds an Element Record Tree Manager Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementTreeManagerBuilder extends AbstractElementManagerBuilder {

	public ElementTreeManagerBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}

	public ModelClass buildClass(Type type) throws Exception {
		if (!ElementUtil.isHierarchical(type))
			return null;
		if (type instanceof Element) {
			Element element = (Element) type;
			return buildClass(element);
		}
		return null;
	}
	
	public ModelClass buildClass(Element element) throws Exception {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "TreeManager";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		modelClass.setParentClassName("AbstractDomainTreeManager");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeClassAnnotations(modelClass, element);
		initializeClassConstructors(modelClass, element);
		//initializeInstanceFields(modelClass, element);
		initializeInstanceFields(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Type type) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);

		modelClass.addImportedClass("nam.ui.design.AbstractDomainTreeManager");
		modelClass.addImportedClass("nam.ui.design.SelectionContext");
		modelClass.addImportedClass("nam.ui.tree.ModelTreeNode");
		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		modelClass.addImportedClass(elementPackageName + ".Project");
		
		if (ElementUtil.isRoot(type)) {
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
			//modelClass.addImportedClass("org.aries.ui.manager.ExportManager");
			//modelClass.addImportedClass("java.util.Collection");
			modelClass.addImportedClass("java.util.List");

		} else if (ElementUtil.isElement(type)) {
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
			//modelClass.addImportedClass("org.aries.ui.manager.ExportManager");
			//modelClass.addImportedClass("java.util.Collection");
			
		} else if (ElementUtil.isEnumeration(type)) {
			modelClass.addImportedClass("java.util.Arrays");
			modelClass.addImportedClass("java.util.List");
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
		modelClass.addImportedClass("javax.enterprise.event.Observes");
		modelClass.addImportedClass("javax.inject.Inject");
		modelClass.addImportedClass("javax.inject.Named");
		
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.ui.EventManager");
		modelClass.addImportedClass("org.aries.ui.event.Added");
		//modelClass.addImportedClass("org.aries.ui.event.Cancelled");
		modelClass.addImportedClass("org.aries.ui.event.Refresh");
		modelClass.addImportedClass("org.aries.ui.event.Removed");
		modelClass.addImportedClass("org.aries.util.NameUtil");

		modelClass.addImportedClass("org.richfaces.model.TreeNode");

		//modelClass.addImportedClass("java.util.Map");
		super.initializeImportedClasses(modelClass);
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type type) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		modelClass.getClassAnnotations().add(AnnotationUtil.createSessionScopedAnnotation());
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"TreeManager"));
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
		buf.putLine2(elementNameUncapped+"TreeBuilder = new "+elementClassName+"TreeBuilder();");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	
	/*
	 * Instance fields
	 */

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceReference(createReference_RootNode(element));
		modelClass.addInstanceReference(createReference_SelectedNode(element));
		modelClass.addInstanceReference(createReference_TreeBuilder(element));
		modelClass.addInstanceReference(createReference_SelectionContext(element));
	}
	
	public ModelReference createReference_RootNode(Element element) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = "ModelTreeNode";
		String beanName = "rootNode";
		
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(beanName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	public ModelReference createReference_SelectedNode(Element element) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = "ModelTreeNode";
		String beanName = "selectedNode";
		
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(beanName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	public ModelReference createReference_TreeBuilder(Element element) {
		//String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementClassName + "TreeBuilder";
		String beanName = elementNameUncapped + "TreeBuilder";
		
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(beanName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	public ModelReference createReference_SelectionContext(Element element) {
		String packageName = "nam.ui.design";
		String className = "SelectionContext";
		String beanName = "selectionContext";
		
		ModelReference modelReference = new ModelReference();
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(beanName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Element element) throws Exception {
		//modelClass.addInstanceOperation(createOperation_getTitle(type));
		modelClass.addInstanceOperation(createOperation_getTreeId(element));
		modelClass.addInstanceOperation(createOperation_getRootNode());
		modelClass.addInstanceOperation(createOperation_getSelectedNode());
		modelClass.addInstanceOperation(createOperation_setSelectedNode());
		modelClass.addInstanceOperation(createOperation_getSelectedNodeName());
		modelClass.addInstanceOperation(createOperation_getNodeById(element));
		modelClass.addInstanceOperation(createOperation_getEventManager());
		modelClass.addInstanceOperation(createOperation_fireSelectionEvent());
		modelClass.addInstanceOperation(createOperation_handleRefresh());
		modelClass.addInstanceOperation(createOperation_handleRefresh2());
		modelClass.addInstanceOperation(createOperation_handleRefresh3());
		modelClass.addInstanceOperation(createOperation_refreshModel());
		modelClass.addInstanceOperation(createOperation_refreshModel2(element));
		modelClass.addInstanceOperation(createOperation_handleAdded(element));
		modelClass.addInstanceOperation(createOperation_handleRemoved(element));
	}
	
	protected ModelOperation createOperation_getTreeId(Element element) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getTreeId");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return \""+elementNameUncapped+"Tree\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getRootNode() {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getRootNode");
		modelOperation.setResultType("ModelTreeNode");
		
		Buf buf = new Buf();
		buf.putLine2("return rootNode;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getSelectedNode() {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getSelectedNode");
		modelOperation.setResultType("ModelTreeNode");
		
		Buf buf = new Buf();
		buf.putLine2("return selectedNode;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getTitle(Element element) throws Exception {
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
	
	protected ModelOperation createOperation_setSelectedNode() throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("setSelectedNode");
		modelOperation.addParameter(createParameter("TreeNode", "treeNode"));
		
		Buf buf = new Buf();
		buf.putLine2("selectedNode = (ModelTreeNode) treeNode;");
		buf.putLine2("selectionContext.setSelectedType(selectedNode.getType());");
		buf.putLine2("Object object = selectedNode.getData().getObject();");
		buf.putLine2("super.setSelectedNode(selectedNode);");
		buf.putLine2("fireSelectionEvent(selectedNode);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getSelectedNodeName() throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getSelectedNodeName");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("if (selectedNode != null)");
		buf.putLine2("	return NameUtil.capName(selectedNode.getLabel() + \" \" + selectedNode.getType());");
		buf.putLine2("return null;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getNodeById(Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getNodeById");
		modelOperation.addParameter(createParameter("String", "nodeId"));
		modelOperation.setResultType("TreeNode");
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementNameUncapped+"TreeBuilder.getNodeById(nodeId);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getEventManager() throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getEventManager");
		modelOperation.addParameter(createParameter("String", "elementType"));
		modelOperation.setResultType("EventManager<?>");
		
		Buf buf = new Buf();
		buf.putLine2("String elementTypeUncapped = NameUtil.uncapName(elementType);");
		buf.putLine2("EventManager<?> eventManager = BeanContext.getFromSession(elementTypeUncapped + \"EventManager\");");
		buf.putLine2("return eventManager;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_fireSelectionEvent() throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("fireSelectionEvent");
		modelOperation.addParameter(createParameter("ModelTreeNode", "treeNode"));
		
		Buf buf = new Buf();
		buf.putLine2("Object object = treeNode.getData().getObject();");
		buf.putLine2("if (object != null) {");
		buf.putLine2("	Class<?> classObject = object.getClass();");
		buf.putLine2("	String type = classObject.getSimpleName();");
		buf.putLine2("	");
		buf.putLine2("	EventManager<?> eventManager = getEventManager(type);");
		buf.putLine2("	eventManager.fireSelectedEvent(object);");
		buf.putLine2("	");
		buf.putLine2("	ModelTreeNode parentNode = (ModelTreeNode) treeNode.getParent();");
		buf.putLine2("	if (parentNode != null) {");
		buf.putLine2("		//fire selection event");
		buf.putLine2("		fireSelectionEvent(parentNode);");
		buf.putLine2("	}");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_handleRefresh() throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handleRefresh");
		ModelParameter parameter = createParameter("Object", "object");
		parameter.addAnnotation(AnnotationUtil.createObservesAnnotation());
		parameter.addAnnotation(AnnotationUtil.createAnnotation("Refresh"));
		modelOperation.addParameter(parameter);
		
		Buf buf = new Buf();
		buf.putLine2("List<Project> projectList = selectionContext.getSelection(\"projectList\");");
		buf.putLine2("refreshModel(projectList);");
		buf.putLine2("clearSelection();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_handleRefresh2() throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handleRefresh");
		ModelParameter parameter = createParameter("List<Project>", "projectList");
		parameter.addAnnotation(AnnotationUtil.createObservesAnnotation());
		parameter.addAnnotation(AnnotationUtil.createAnnotation("Refresh"));
		modelOperation.addParameter(parameter);
		
		Buf buf = new Buf();
		buf.putLine2("refreshModel(projectList);");
		buf.putLine2("clearSelection();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_handleRefresh3() throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handleRefresh");
		ModelParameter parameter = createParameter("Project", "project");
		parameter.addAnnotation(AnnotationUtil.createObservesAnnotation());
		parameter.addAnnotation(AnnotationUtil.createAnnotation("Refresh"));
		modelOperation.addParameter(parameter);
		
		Buf buf = new Buf();
		buf.putLine2("List<Project> projectList = selectionContext.getSelection(\"projectList\");");
		buf.putLine2("refreshModel(projectList);");
		buf.putLine2("clearSelection();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_refreshModel() throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refreshModel");
		
		Buf buf = new Buf();
		buf.putLine2("List<Project> projectList = selectionContext.getSelection(\"projectList\");");
		buf.putLine2("refreshModel(projectList);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_refreshModel2(Element element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refreshModel");
		modelOperation.addParameter(createParameter("List<Project>", "projectList"));
		
		Buf buf = new Buf();
		buf.putLine2("rootNode = "+elementNameUncapped+"TreeBuilder.createTree(projectList);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_handleAdded(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handleAdded");
		ModelParameter parameter = createParameter(elementClassName, elementNameUncapped);
		parameter.addAnnotation(AnnotationUtil.createObservesAnnotation());
		parameter.addAnnotation(AnnotationUtil.createAnnotation("Added"));
		modelOperation.addParameter(parameter);
		
		Buf buf = new Buf();
		buf.putLine2("refreshModel();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_handleRemoved(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handleRemoved");
		ModelParameter parameter = createParameter(elementClassName, elementNameUncapped);
		parameter.addAnnotation(AnnotationUtil.createObservesAnnotation());
		parameter.addAnnotation(AnnotationUtil.createAnnotation("Removed"));
		modelOperation.addParameter(parameter);
		
		Buf buf = new Buf();
		buf.putLine2("selectionContext.clearSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("refreshModel();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
