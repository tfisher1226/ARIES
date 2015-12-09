package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Reference;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;
import nam.model.util.ViewUtil;
import nam.ui.Relation;
import nam.ui.View;

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
 * Builds an Element Page Manager Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementPageManagerBuilder extends AbstractElementManagerBuilder {

	public ElementPageManagerBuilder(GenerationContext context) {
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
		String className = elementName + "PageManager";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		modelClass.setParentClassName("AbstractPageManager<"+elementName+">");
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
		
		//if (type.getType().endsWith("user"))
		//	System.out.println();
		
		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
		modelClass.addImportedClass("nam.ui.design.SelectionContext");
		//modelClass.addImportedClass("org.apache.commons.lang.StringUtils");
		modelClass.addImportedClass("org.aries.ui.Breadcrumb");
		modelClass.addImportedClass("org.aries.ui.AbstractWizardPage");
		//modelClass.addImportedClass("org.aries.util.NameUtil");
		
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

		modelClass.addImportedClass("javax.enterprise.context.SessionScoped");
		//modelClass.addImportedClass("javax.enterprise.event.Event");
		modelClass.addImportedClass("javax.enterprise.event.Observes");
		modelClass.addImportedClass("javax.inject.Inject");
		modelClass.addImportedClass("javax.inject.Named");

		modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.ui.AbstractPageManager");
		modelClass.addImportedClass("org.aries.ui.AbstractWizardPage");
		modelClass.addImportedClass("org.aries.ui.event.Selected");
		modelClass.addImportedClass("org.aries.ui.event.Unselected");
		
		//modelClass.addImportedClass("org.aries.ui.Display");
		//modelClass.addImportedClass("org.aries.util.Validator");
		//modelClass.addImportedClass("org.aries.util.CollectionUtil");
		//modelClass.addImportedClass("org.aries.ui.event.Add");
		//modelClass.addImportedClass("org.aries.ui.event.Remove");
		//modelClass.addImportedClass("org.aries.ui.event.Selected");
		//modelClass.addImportedClass("org.aries.ui.event.Updated");
		
		//modelClass.addImportedClass("java.util.List");
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
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"PageManager"));
	}

	
	/*
	 * Class Constructor(s)
	 */
	
	protected void initializeClassConstructors(ModelClass modelClass, Type element) throws Exception {
		modelClass.addInstanceConstructor(createConstructor(element));
	}
	
	protected ModelConstructor createConstructor(Type element) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);		
		
		Buf buf = new Buf();
		buf.putLine2("initializeSections();");
		//buf.putLine2("initializeDefaultView();");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	
	/*
	 * Instance fields
	 */

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceReference(createReference_Manager(element, "Wizard", ""));
		modelClass.addInstanceReference(createReference_Manager(element, "Data", "Manager"));
		modelClass.addInstanceReference(createReference_Manager(element, "Info", "Manager"));
		modelClass.addInstanceReference(createReference_Manager(element, "List", "Manager"));
		modelClass.addInstanceReferences(createReferences_ChildManagers(element));
		modelClass.addInstanceReference(createReference_Section(element, "OverviewSection"));
		modelClass.addInstanceReference(createReference_Section(element, "IdentificationSection"));
		modelClass.addInstanceReference(createReference_Section(element, "ConfigurationSection"));
		modelClass.addInstanceReference(createReference_Section(element, "DocumentationSection"));
		modelClass.addInstanceReferences(createReferences_ChildSections(element));
		modelClass.addInstanceReference(createReference_SelectionContext(element));
	}
	
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
	
	protected Collection<ModelReference> createReferences_ChildManagers(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		Collection<ModelReference> modelReferences = new ArrayList<ModelReference>();
		
//		if (elementClassName.equals("User"))
//			System.out.println();

		View view = context.getModule().getView();
		Relation relation = ViewUtil.getManagedByRelation(view, elementClassName);
		if (relation == null)
			relation = ViewUtil.getMemberOfRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				ModelReference modelReference = createReference_ChildManager(element, child);
				if (modelReference != null)
					modelReferences.add(modelReference);
			}
		}
		return modelReferences;
	}

	protected ModelReference createReference_ChildManager(Element element, String child) {
		Element childElement = context.getElementByName(child);
		if (childElement == null)
			return null;
		
		//Assert.notNull(childElement, "Element not found: "+child);
		String childPackageName = ModelLayerHelper.getElementPackageName(childElement);
		String childNameCapped = NameUtil.capName(child);
		String childNameUncapped = NameUtil.uncapName(child);

		String packageName = childPackageName + "." + childNameUncapped;
		String className = childNameCapped + "PageManager";
		String beanName = childNameUncapped + "PageManager";
		
		ModelReference modelReference = new ModelReference();
		modelReference.addImportedClass(packageName + "." + className);
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(beanName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	protected ModelReference createReference_Section(Element element, String sectionName) {
		//String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementClassName + "Record_" + sectionName;
		String beanName = elementNameUncapped + sectionName;
		
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
	
	protected Collection<ModelReference> createReferences_ChildSections(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		if (elementClassName.equals("User"))
			System.out.println();
		Collection<ModelReference> modelReferences = new ArrayList<ModelReference>();
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getMemberOfRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				modelReferences.add(createReference_ChildSection(element, child));
			}
		}
		return modelReferences;
	}

	protected ModelReference createReference_ChildSection(Element element, String child) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		Element childElement = context.getElementByName(child);
		if (childElement == null)
			childElement = element;
		
		String childPackageName = ModelLayerHelper.getElementPackageName(childElement);
		String childNameCapped = NameUtil.capName(child);
		String childNameUncapped = NameUtil.uncapName(child);
		String childNameCappedPlural = NameUtil.toPlural(childNameCapped);
		
//		Field field = ElementUtil.getField(element, child);
//		Assert.notNull(element, "Field not found: "+child);
//		boolean fieldExists = field != null;
//		if (fieldExists) 
//			childNameCappedPlural = childNameCapped;
		
		String packageName = childPackageName + "." + childNameUncapped;
		String className = elementClassName + "Record_"+childNameCappedPlural + "Section";
		String beanName = elementNameUncapped + childNameCappedPlural + "Section";
		
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
		modelClass.addInstanceOperation(createOperation_refreshAll(element));
		modelClass.addInstanceOperation(createOperation_refreshLocal(element));
		modelClass.addInstanceOperation(createOperation_refreshMembers(element));
		modelClass.addInstanceOperation(createOperation_refreshAll2(element));
		modelClass.addInstanceOperation(createOperation_refreshLocal2(element));
		modelClass.addInstanceOperation(createOperation_refreshMembers2(element));
		
		modelClass.addInstanceOperation(createOperation_getPage(element, "List"));
		modelClass.addInstanceOperation(createOperation_getPage(element, "Tree"));
		modelClass.addInstanceOperation(createOperation_getPage(element, "Summary"));
		modelClass.addInstanceOperation(createOperation_getPage(element, "Record"));
		modelClass.addInstanceOperation(createOperation_getPage(element, "Wizard"));
		modelClass.addInstanceOperation(createOperation_getPage(element, "Management"));

		modelClass.addInstanceOperations(createOperations_handleSelected(element));
		modelClass.addInstanceOperation(createOperation_handleUnselected(element));
		//modelClass.addInstanceOperations(createOperations_handleChecked(element));
		//modelClass.addInstanceOperation(createOperation_handleUnchecked(element));
		modelClass.addInstanceOperation(createOperation_handleChecked(element));

		modelClass.addInstanceOperation(createOperation_initializeListPage(element));
		modelClass.addInstanceOperation(createOperation_initializeTreePage(element));
		modelClass.addInstanceOperation(createOperation_initializeSummaryPage(element));
		modelClass.addInstanceOperation(createOperation_initializeRecordPage(element));
		modelClass.addInstanceOperation(createOperation_initializeCreationPage(element));
		modelClass.addInstanceOperation(createOperation_initializeUpdatePage(element));
		modelClass.addInstanceOperation(createOperation_initializeManagementPage(element));
		modelClass.addInstanceOperation(createOperation_initializeDefaultView(element));
		//modelClass.addInstanceOperation(createOperation_initializeView(element));
		//modelClass.addInstanceOperation(createOperation_initializeView2(element));
		modelClass.addInstanceOperations(createOperation_initializeManagementViews(element));
		modelClass.addInstanceOperation(createOperation_initializeSummaryView(element));
		modelClass.addInstanceOperation(createOperation_getElementLabel(element));
		modelClass.addInstanceOperation(createOperation_updateState(element));
		modelClass.addInstanceOperation(createOperation_updateState2(element));
	}

	protected ModelOperation createOperation_refreshAll(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refresh");
		
		Buf buf = new Buf();
		buf.putLine2("refresh(\"projectList\");");
		//buf.putLine2("refresh(\""+elementNameUncapped+"\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_refreshAll2(Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refresh");
		modelOperation.addParameter(createParameter("String", "scope"));
		
		Buf buf = new Buf();
		buf.putLine2("refreshLocal(scope);");
		buf.putLine2("//refreshMembers(scope);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_refreshLocal(Element element) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refreshLocal");
		
		Buf buf = new Buf();
		buf.putLine2("refreshLocal(\""+elementNameUncapped+"\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_refreshLocal2(Element element) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refreshLocal");
		modelOperation.addParameter(createParameter("String", "scope"));
		
		Buf buf = new Buf();
		buf.putLine2(elementNameUncapped+"DataManager.setScope(scope);");
		buf.putLine2(elementNameUncapped+"ListManager.refresh();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_refreshMembers(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refreshMembers");
		
		Buf buf = new Buf();
		buf.putLine2("refreshMembers(\""+elementNameUncapped+"\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_refreshMembers2(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("refreshMembers");
		modelOperation.addParameter(createParameter("String", "scope"));
		
		Buf buf = new Buf();
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getMemberOfRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				String childNameUncapped = NameUtil.uncapName(child);
				buf.putLine2(childNameUncapped+"PageManager.refreshLocal(scope);");
			}
		}
		//buf.putLine2(elementNameUncapped+"ListManager.refresh();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	
	protected ModelOperation createOperation_getPage(Element element, String pageTypeId) throws Exception {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String contextPath = "/" + elementPackageName.replace(".", "/");

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("get"+elementClassName+pageTypeId+"Page");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return \""+contextPath+"/"+elementNameUncapped+"/"+elementNameUncapped+pageTypeId+"Page.xhtml\";");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperations_handleSelected(Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createOperation_handleSelected(element, element));
//		List<ContainedBy> containedByList = element.getContainedBy();
//		Iterator<ContainedBy> iterator = containedByList.iterator();
//		while (iterator.hasNext()) {
//			ContainedBy containedBy = (ContainedBy) iterator.next();
//			Element parentElement = context.getElementByType(containedBy.getType());
//			Assert.notNull(parentElement, "ContainedBy element not found: "+containedBy.getType());
//			modelOperations.add(createOperation_handleSelected(parentElement, element));
//		}
		return modelOperations;
	}
	
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
		if (parent == element) {
			buf.putLine2("selectionContext.setSelection(\""+elementNameUncapped+"\",  "+elementNameUncapped+");");
			//buf.putLine2(elementNameUncapped+"PageManager.refreshMembers(\""+elementNameUncapped+"Selection\");");
			//buf.putLine2(elementNameUncapped+"PageManager.updateState("+elementNameUncapped+");");
			buf.putLine2(elementNameUncapped+"InfoManager.setRecord("+elementNameUncapped+");");
		} else {
			buf.putLine2(elementNameUncapped+"PageManager.updateState("+parentNameUncapped+");");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_handleUnselected(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handle"+elementClassName+"Unselected");
		ModelParameter modelParameter = createParameter(elementClassName, elementNameUncapped);
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Unselected"));
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		buf.putLine2("selectionContext.unsetSelection(\""+elementNameUncapped+"\",  "+elementNameUncapped+");");
		//buf.putLine2(elementNameUncapped+"PageManager.refreshMembers(\""+elementNameUncapped+"Selection\");");
		buf.putLine2(elementNameUncapped+"InfoManager.unsetRecord("+elementNameUncapped+");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	//NOTUSED
	protected List<ModelOperation> createOperations_handleCheckedOLD(Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createOperation_handleChecked(element));
		return modelOperations;
	}
	
	//NOTUSED
	protected ModelOperation createOperation_handleCheckedOLD(Element parent, Element element) {
		String parentClassName = ModelLayerHelper.getElementClassName(parent);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String parentNameUncapped = ModelLayerHelper.getElementNameUncapped(parent);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handle"+parentClassName+"Checked");
		ModelParameter modelParameter = createParameter(parentClassName, parentNameUncapped);
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Checked"));
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		if (parent == element) {
			buf.putLine2("selectionContext.setSelection(\""+elementNameUncapped+"Selection\",  "+elementNameUncapped+");");
			buf.putLine2(elementNameUncapped+"PageManager.refreshLocal(\""+elementNameUncapped+"Selection\");");
			//buf.putLine2(elementNameUncapped+"PageManager.updateState("+elementNameUncapped+");");
			buf.putLine2("setRecord("+elementNameUncapped+");");
		} else {
			//buf.putLine2(elementNameUncapped+"PageManager.updateState("+parentNameUncapped+");");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_handleChecked(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handle"+elementClassName+"Checked");
		
		Buf buf = new Buf();
		buf.putLine2("String scope = \""+elementNameUncapped+"Selection\";");
		buf.putLine2(elementClassName+"ListObject listObject = "+elementNameUncapped+"ListManager.getSelection();");
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = selectionContext.getSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("boolean checked = "+elementNameUncapped+"ListManager.getCheckedState();");
		buf.putLine2("listObject.setChecked(checked);");
		buf.putLine2("if (checked) {");
		buf.putLine2("	"+elementNameUncapped+"InfoManager.setRecord("+elementNameUncapped+");");
		buf.putLine2("	selectionContext.setSelection(scope,  "+elementNameUncapped+");");
		buf.putLine2("} else {");
		buf.putLine2("	"+elementNameUncapped+"InfoManager.unsetRecord("+elementNameUncapped+");");
		buf.putLine2("	selectionContext.unsetSelection(scope,  "+elementNameUncapped+");");
		buf.putLine2("}");
		
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getMemberOfRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getType();
			buf.putLine2("String target = selectionContext.getCurrentTarget();");
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				String childNameUncapped = NameUtil.uncapName(child);
				buf.putLine2("if (target.equals(\""+childNameUncapped+"\"))");
				buf.putLine2("	"+childNameUncapped+"PageManager.refreshLocal(scope);");
			}
		}
		
		buf.putLine2("refreshLocal(scope);");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_handleUnchecked(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("handle"+elementClassName+"Unchecked");
		ModelParameter modelParameter = createParameter(elementClassName, elementNameUncapped);
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Observes"));
		modelParameter.addAnnotation(AnnotationUtil.createAnnotation("Unchecked"));
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		buf.putLine2("selectionContext.unsetSelection(\""+elementNameUncapped+"Selection\",  "+elementNameUncapped+");");
		buf.putLine2(elementNameUncapped+"PageManager.refreshLocal(\""+elementNameUncapped+"Selection\");");
		buf.putLine2("unsetRecord("+elementNameUncapped+");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	
	protected ModelOperation createOperation_initializeListPage(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+elementClassName+"ListPage");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("String pageLevelKey = \""+elementNameUncapped+"List\";");
		buf.putLine2("clearBreadcrumbs(pageLevelKey);");
		buf.putLine2("addBreadcrumb(pageLevelKey, \"Top\", \"showMainPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, \""+elementClassName+"s\", \"show"+elementClassName+"ManagementPage()\");");
		buf.putLine2("String url = get"+elementClassName+"ListPage();");
		buf.putLine2("selectionContext.setCurrentArea(\""+elementNameUncapped+"\");");
		buf.putLine2("selectionContext.setSelectedArea(pageLevelKey);");
		buf.putLine2("selectionContext.setMessageDomain(pageLevelKey);");
		buf.putLine2("selectionContext.resetOrigin();");
		buf.putLine2("selectionContext.setUrl(url);");
		buf.putLine2("sections.clear();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_initializeTreePage(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+elementClassName+"TreePage");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("String pageLevelKey = \""+elementNameUncapped+"Tree\";");
		buf.putLine2("clearBreadcrumbs(pageLevelKey);");
		buf.putLine2("addBreadcrumb(pageLevelKey, \"Top\", \"showMainPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, \""+elementClassName+"s\", \"show"+elementClassName+"TreePage()\");");
		buf.putLine2("String url = get"+elementClassName+"TreePage();");
		buf.putLine2("selectionContext.setCurrentArea(\""+elementNameUncapped+"\");");
		buf.putLine2("selectionContext.setSelectedArea(pageLevelKey);");
		buf.putLine2("selectionContext.setMessageDomain(pageLevelKey);");
		buf.putLine2("selectionContext.resetOrigin();");
		buf.putLine2("selectionContext.setUrl(url);");
		buf.putLine2("sections.clear();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_initializeSummaryPage(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+elementClassName+"SummaryPage");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("String pageLevelKey = \""+elementNameUncapped+"Summary\";");
		buf.putLine2("clearBreadcrumbs(pageLevelKey);");
		buf.putLine2("addBreadcrumb(pageLevelKey, \"Top\", \"showMainPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, \""+elementClassName+"s\", \"show"+elementClassName+"SummaryPage()\");");
		buf.putLine2("String url = get"+elementClassName+"SummaryPage();");
		buf.putLine2("selectionContext.setCurrentArea(\""+elementNameUncapped+"\");");
		buf.putLine2("selectionContext.setSelectedArea(pageLevelKey);");
		buf.putLine2("selectionContext.setMessageDomain(pageLevelKey);");
		buf.putLine2("selectionContext.resetOrigin();");
		buf.putLine2("selectionContext.setUrl(url);");
		buf.putLine2("sections.clear();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_initializeRecordPage(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+elementClassName+"RecordPage");
		//modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = selectionContext.getSelection(\""+elementNameUncapped+"\");");
		buf.putLine2("String "+elementNameUncapped+"Name = "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
		buf.putLine2("");
		
		buf.putLine2("String pageLevelKey = \""+elementNameUncapped+"Record\";");
		buf.putLine2("clearBreadcrumbs(pageLevelKey);");
		
		//TODO need to add breadcrumb for "current owner"
		
		buf.putLine2("addBreadcrumb(pageLevelKey, \"Top\", \"showMainPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, \""+elementClassName+"s\", \"show"+elementClassName+"ManagementPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, "+elementNameUncapped+"Name, \"show"+elementClassName+"RecordPage()\");");
		
		buf.putLine2("String url = get"+elementClassName+"RecordPage();");
		buf.putLine2("selectionContext.setCurrentArea(\""+elementNameUncapped+"\");");
		buf.putLine2("selectionContext.setSelectedArea(pageLevelKey);");
		buf.putLine2("selectionContext.setMessageDomain(pageLevelKey);");
		buf.putLine2("selectionContext.resetOrigin();");
		buf.putLine2("selectionContext.setUrl(url);");
		buf.putLine2("initializeDefaultView();");
		buf.putLine2("sections.clear();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_initializeCreationPage(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+elementClassName+"CreationPage");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");

		Buf buf = new Buf();
		buf.putLine2("setPageTitle(\"New \"+get"+elementClassName+"Label("+elementNameUncapped+"));"); 
		buf.putLine2("setPageIcon(\"/icons/nam/New"+elementClassName+"16.gif\");");
		buf.putLine2("setSectionTitle(\""+elementClassName+" Identification\");"); 
		//buf.putLine2("String "+elementNameUncapped+"Name = selected"+elementClassName+".getName();");
		//buf.putLine2("String "+elementNameUncapped+"Label = "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
		buf.putLine2(elementNameUncapped+"Wizard.setNewMode(true);");
		buf.putLine2("");
		buf.putLine2("String pageLevelKey = \""+elementNameUncapped+"\";");
		buf.putLine2("String wizardLevelKey = \""+elementNameUncapped+"Wizard\";");
		buf.putLine2("clearBreadcrumbs(pageLevelKey);");
		buf.putLine2("clearBreadcrumbs(wizardLevelKey);");
		buf.putLine2("");
		buf.putLine2("addBreadcrumb(pageLevelKey, \"Top\", \"showMainPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, \""+elementClassName+"s\", \"show"+elementClassName+"ManagementPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, new Breadcrumb(\"New "+elementClassName+"\", \"show"+elementClassName+"WizardPage()\"));");
		//buf.putLine2("");
		//buf.putLine2("if (StringUtils.isEmpty("+elementNameUncapped+"Label))");
		//buf.putLine2("	addBreadcrumb(pageLevelKey, new Breadcrumb(getPageTitle(), \"show"+elementClassName+"WizardPage()\"));");
		//buf.putLine2("else addBreadcrumb(pageLevelKey, new Breadcrumb(getPageTitle()+\" [\"+"+elementNameUncapped+"Label+\"]\", \"show"+elementClassName+"WizardPage()\"));");
		
		buf.putLine2("");
		//buf.putLine2("addBreadcrumb(wizardLevelKey, \"Identification\", \"show"+elementClassName+"WizardPage('Identification')\");");
		//buf.putLine2("addBreadcrumb(wizardLevelKey, \"Configuration\", \"show"+elementClassName+"WizardPage('Configuration')\");");
		//buf.putLine2("addBreadcrumb(wizardLevelKey, \"Documentation\", \"show"+elementClassName+"WizardPage('Documentation')\");");
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getMemberOfRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				String childNameCapped = NameUtil.capName(child);
				String childNameCappedPlural = NameUtil.toPlural(childNameCapped);
				buf.putLine2("addBreadcrumb(wizardLevelKey, \""+childNameCappedPlural+"\", \"show"+elementClassName+"WizardPage('"+childNameCappedPlural+"')\");");
			}
		}
		
		buf.putLine2("");
		buf.putLine2(elementNameUncapped+"IdentificationSection.setOwner(\""+elementNameUncapped+"Wizard\");");
		buf.putLine2(elementNameUncapped+"ConfigurationSection.setOwner(\""+elementNameUncapped+"Wizard\");");
		buf.putLine2(elementNameUncapped+"DocumentationSection.setOwner(\""+elementNameUncapped+"Wizard\");");
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				String childNameCapped = NameUtil.capName(child);
				String childNameCappedPlural = NameUtil.toPlural(childNameCapped);
				buf.putLine2(elementNameUncapped+""+childNameCappedPlural+"Section.setOwner(\""+elementNameUncapped+"Wizard\");");
			}
		}
		buf.putLine2("");
		buf.putLine2("sections.clear();");
		buf.putLine2("sections.add("+elementNameUncapped+"IdentificationSection);");
		buf.putLine2("sections.add("+elementNameUncapped+"ConfigurationSection);");
		buf.putLine2("sections.add("+elementNameUncapped+"DocumentationSection);");
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				String childNameCapped = NameUtil.capName(child);
				String childNameCappedPlural = NameUtil.toPlural(childNameCapped);
				buf.putLine2("sections.add("+elementNameUncapped+childNameCappedPlural+"Section);");
			}
		}
		buf.putLine2("");
		buf.putLine2("String url = get"+elementClassName+"WizardPage() + \"?section=Identification\";");
		buf.putLine2("selectionContext.setCurrentArea(\""+elementNameUncapped+"\");");
		buf.putLine2("selectionContext.setSelectedArea(pageLevelKey);");
		buf.putLine2("selectionContext.setMessageDomain(pageLevelKey);");
		buf.putLine2("//selectionContext.resetOrigin();");
		buf.putLine2("selectionContext.setUrl(url);");
		//buf.putLine2("sections.clear();");
		buf.putLine2("refreshLocal();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_initializeUpdatePage(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+elementClassName+"UpdatePage");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("setPageTitle(get"+elementClassName+"Label("+elementNameUncapped+"));"); 
		buf.putLine2("setPageIcon(\"/icons/nam/"+elementClassName+"16.gif\");");
		buf.putLine2("setSectionTitle(\""+elementClassName+" Overview\");"); 
		buf.putLine2("String "+elementNameUncapped+"Name = "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
		//buf.putLine2("String "+elementNameUncapped+"Label = "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
		buf.putLine2(elementNameUncapped+"Wizard.setNewMode(false);");
		buf.putLine2("");
		buf.putLine2("String pageLevelKey = \""+elementNameUncapped+"\";");
		buf.putLine2("String wizardLevelKey = \""+elementNameUncapped+"Wizard\";");
		buf.putLine2("clearBreadcrumbs(pageLevelKey);");
		buf.putLine2("clearBreadcrumbs(wizardLevelKey);");
		buf.putLine2("");
		buf.putLine2("addBreadcrumb(pageLevelKey, \"Top\", \"showMainPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, \""+elementClassName+"s\", \"show"+elementClassName+"ManagementPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, new Breadcrumb("+elementNameUncapped+"Name, \"show"+elementClassName+"WizardPage()\"));");
		//buf.putLine2("");
		//buf.putLine2("String "+elementNameUncapped+"Label = "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
		//buf.putLine2("if (StringUtils.isEmpty("+elementNameUncapped+"Label))");
		//buf.putLine2("	addBreadcrumb(pageLevelKey, new Breadcrumb(getPageTitle(), \"show"+elementClassName+"WizardPage()\"));");
		//buf.putLine2("else addBreadcrumb(pageLevelKey, new Breadcrumb(getPageTitle()+\" [\"+"+elementNameUncapped+"Label+\"]\", \"show"+elementClassName+"WizardPage()\"));");
		
		buf.putLine2("");
		//buf.putLine2("addBreadcrumb(wizardLevelKey, \"Overview\", \"show"+elementClassName+"WizardPage('Overview')\");");
		//buf.putLine2("addBreadcrumb(wizardLevelKey, \"Identification\", \"show"+elementClassName+"WizardPage('Identification')\");");
		//buf.putLine2("addBreadcrumb(wizardLevelKey, \"Configuration\", \"show"+elementClassName+"WizardPage('Configuration')\");");
		//buf.putLine2("addBreadcrumb(wizardLevelKey, \"Documentation\", \"show"+elementClassName+"WizardPage('Documentation')\");");
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getMemberOfRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				String childNameCapped = NameUtil.capName(child);
				String childNameCappedPlural = NameUtil.toPlural(childNameCapped);
				buf.putLine2("addBreadcrumb(wizardLevelKey, \""+childNameCappedPlural+"\", \"show"+elementClassName+"WizardPage('"+childNameCappedPlural+"')\");");
			}
		}

		buf.putLine2("");
		buf.putLine2(elementNameUncapped+"OverviewSection.setOwner(\""+elementNameUncapped+"Wizard\");");
		buf.putLine2(elementNameUncapped+"IdentificationSection.setOwner(\""+elementNameUncapped+"Wizard\");");
		buf.putLine2(elementNameUncapped+"ConfigurationSection.setOwner(\""+elementNameUncapped+"Wizard\");");
		buf.putLine2(elementNameUncapped+"DocumentationSection.setOwner(\""+elementNameUncapped+"Wizard\");");
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				String childNameCapped = NameUtil.capName(child);
				String childNameCappedPlural = NameUtil.toPlural(childNameCapped);
				buf.putLine2(elementNameUncapped+""+childNameCappedPlural+"Section.setOwner(\""+elementNameUncapped+"Wizard\");");
			}
		}
		buf.putLine2("");
		buf.putLine2("sections.clear();");
		buf.putLine2("sections.add("+elementNameUncapped+"OverviewSection);");
		buf.putLine2("sections.add("+elementNameUncapped+"IdentificationSection);");
		buf.putLine2("sections.add("+elementNameUncapped+"ConfigurationSection);");
		buf.putLine2("sections.add("+elementNameUncapped+"DocumentationSection);");
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				String childNameCapped = NameUtil.capName(child);
				String childNameCappedPlural = NameUtil.toPlural(childNameCapped);
				buf.putLine2("sections.add("+elementNameUncapped+childNameCappedPlural+"Section);");
			}
		}
		buf.putLine2("");
		buf.putLine2("String url = get"+elementClassName+"WizardPage() + \"?section=Overview\";");
		buf.putLine2("selectionContext.setCurrentArea(\""+elementNameUncapped+"\");");
		buf.putLine2("selectionContext.setSelectedArea(pageLevelKey);");
		buf.putLine2("selectionContext.setMessageDomain(pageLevelKey);");
		buf.putLine2("//selectionContext.resetOrigin();");
		buf.putLine2("selectionContext.setUrl(url);");
		//buf.putLine2("sections.clear();");
		buf.putLine2("refreshLocal();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_initializeManagementPage(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementNameCappedPlural = NameUtil.toPlural(elementNameCapped);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+elementClassName+"ManagementPage");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("setPageTitle(\""+elementNameCappedPlural+"\");");
		buf.putLine2("setPageIcon(\"/icons/nam/"+elementClassName+"16.gif\");");
		buf.putLine2("String pageLevelKey = \""+elementNameUncapped+"Management\";");
		buf.putLine2("clearBreadcrumbs(pageLevelKey);");
		buf.putLine2("addBreadcrumb(pageLevelKey, \"Top\", \"showMainPage()\");");
		buf.putLine2("addBreadcrumb(pageLevelKey, \""+elementNameCappedPlural+"\", \"show"+elementClassName+"ManagementPage()\");");
		buf.putLine2("String url = get"+elementClassName+"ManagementPage();");
		buf.putLine2("selectionContext.setCurrentArea(\""+elementNameUncapped+"\");");
		buf.putLine2("selectionContext.setSelectedArea(pageLevelKey);");
		buf.putLine2("selectionContext.setMessageDomain(pageLevelKey);");
		buf.putLine2("selectionContext.resetOrigin();");
		buf.putLine2("selectionContext.setUrl(url);");
		buf.putLine2("initializeDefaultView();");
		buf.putLine2("sections.clear();");
		//buf.putLine2("refreshLocal();");
		buf.putLine2("return url;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_initializeDefaultView(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementNamePluralCapped = NameUtil.toPlural(elementClassName);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initializeDefaultView");
		
		Buf buf = new Buf();
		buf.putLine2("setPageTitle(\""+elementNamePluralCapped+"\");");
		buf.putLine2("setPageIcon(\"/icons/nam/"+elementClassName+"16.gif\");");
		buf.putLine2("setSectionType(\""+elementNameUncapped+"\");"); 
		buf.putLine2("setSectionName(\"Overview\");"); 
		buf.putLine2("setSectionTitle(\"Overview of "+elementNamePluralCapped+"\");");
		buf.putLine2("setSectionIcon(\"/icons/nam/Overview16.gif\");"); 
		buf.putLine2("String viewLevelKey = \""+elementNameUncapped+"Overview\";");
		buf.putLine2("clearBreadcrumbs(viewLevelKey);");
		buf.putLine2("addBreadcrumb(viewLevelKey, \"Top\", \"showMainPage()\");");
		buf.putLine2("addBreadcrumb(viewLevelKey, \""+elementNamePluralCapped+"\", \"show"+elementClassName+"ManagementPage()\");");
		buf.putLine2("String scope = \"projectList\";");
		//buf.putLine2("projectPageManager.refreshLocal(scope);");
		//buf.putLine2(elementNameUncapped+"DataManager.setScope(scope);");
		//buf.putLine2(elementNameUncapped+"ListManager.refresh();");
		buf.putLine2("refreshLocal(scope);");
		buf.putLine2("sections.clear();");
		
		//buf.putLine2("setSectionIcon(\"/icons/nam/"+elementClassName+"16.gif\");"); 
		//buf.putLine2("initializeView(this, \"Overview of "+elementNamePluralCapped+"\", \"Overview\") ;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	//NOTUSED
	protected ModelOperation createOperation_initializeView(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initializeView");
		modelOperation.addParameter(createParameter("String", "elementType"));
		modelOperation.addParameter(createParameter("String", "viewTitle"));
		modelOperation.addParameter(createParameter("String", "sectionName"));
		
		Buf buf = new Buf();
		buf.putLine2("String pageManagerName = NameUtil.uncapName(elementType) + \"PageManager\";");
		buf.putLine2("PageManager<?> pageManager = BeanContext.getFromSession(pageManagerName);");
		buf.putLine2("initializeView(pageManager, viewTitle, sectionName);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	//NOTUSED
	protected ModelOperation createOperation_initializeView2(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initializeView");
		modelOperation.addParameter(createParameter("PageManager<?>", "pageManager"));
		modelOperation.addParameter(createParameter("String", "viewTitle"));
		modelOperation.addParameter(createParameter("String", "sectionName"));
		
		Buf buf = new Buf();
		buf.putLine2("pageManager.setSectionName(sectionName);"); 
		buf.putLine2("pageManager.setSectionTitle(viewTitle);");
		buf.putLine2("pageManager.setSectionType(\""+elementNameUncapped+"\");"); 
		buf.putLine2("pageManager.setSectionIcon(\"/icons/nam/"+elementClassName+"16.gif\");"); 
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected Collection<ModelOperation> createOperation_initializeManagementViews(Element element) {
		Collection<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		View view = context.getModule().getView();
		Relation relation = ViewUtil.getManagedByRelation(view, elementClassName);
		//TODO take out MemberOf from here later...
		if (relation == null)
			relation = ViewUtil.getMemberOfRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getType();
			Iterator<String> iterator = children.iterator();
			while (iterator.hasNext()) {
				String child = iterator.next();
				modelOperations.add(createOperation_initializeSubView(element, child));
			}
		}
		return modelOperations;
	}

	protected ModelOperation createOperation_initializeSubView(Element element, String child) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String childNameCapped = NameUtil.capName(child);
		String childNameUncapped = NameUtil.uncapName(child);
		String childNameCappedPlural = NameUtil.toPlural(childNameCapped);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+elementClassName+childNameCappedPlural+"View");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("setSectionType(\""+elementNameUncapped+"\");"); 
		buf.putLine2("setSectionName(\""+childNameCappedPlural+"\");"); 
		buf.putLine2("setSectionTitle(\""+childNameCappedPlural+"\");");
		//buf.putLine2("setSectionTitle(\""+elementClassName+" "+childNameCappedPlural+"\");");
		buf.putLine2("setSectionIcon(\"/icons/nam/"+childNameCapped+"16.gif\");"); 
		//buf.putLine2("String sectionTitle = \""+elementClassName+" "+childNameCappedPlural+"\";");
		//buf.putLine2("initializeView(this, sectionTitle, \""+childNameCappedPlural+"\");");
		//buf.putLine2("String viewLevelKey = \""+elementNameUncapped+""+childNameCappedPlural+"\";");
		buf.putLine2("selectionContext.setMessageDomain(\""+elementNameUncapped+""+childNameCappedPlural+"\");");
		buf.putLine2(childNameUncapped+"PageManager.refreshLocal(\""+elementNameUncapped+"Selection\");");
		//buf.putLine2(elementNameUncapped+"ListManager.refreshLocal();");
		buf.putLine2("refreshLocal(\"projectList\");");
		buf.putLine2("sections.clear();");
		buf.putLine2("return null;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
		
	}

	protected ModelOperation createOperation_initializeSummaryView(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String sectionTitlePart = elementClassName;
		if (!element.getSuperTypes().isEmpty())
			System.out.println();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize"+elementClassName+"SummaryView");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("//String viewTitle = get"+elementClassName+"Label("+elementNameUncapped+");");
		buf.putLine2("//String currentArea = selectionContext.getCurrentArea();");
		
		buf.putLine2("setSectionType(\""+elementNameUncapped+"\");");
		buf.putLine2("setSectionName(\"Summary\");");
		buf.putLine2("setSectionTitle(\"Summary of "+sectionTitlePart+" Records\");");
		buf.putLine2("setSectionIcon(\"/icons/nam/"+elementClassName+"16.gif\");");
		
		//buf.putLine2("initializeView(currentArea, viewTitle, \"Summary\");");
		buf.putLine2("String viewLevelKey = \""+elementNameUncapped+"Summary\";");
		buf.putLine2("clearBreadcrumbs(viewLevelKey);");
		buf.putLine2("addBreadcrumb(viewLevelKey, \"Top\", \"showMainPage()\");");
		buf.putLine2("addBreadcrumb(viewLevelKey, \""+elementClassName+"s\", \"show"+elementClassName+"ManagementPage()\");");
		//buf.putLine2("String url = get"+elementClassName+"SummaryPage();");
		buf.putLine2("selectionContext.setMessageDomain(viewLevelKey);");
		//buf.putLine2("selectionContext.resetOrigin();");
		//buf.putLine2("selectionContext.setUrl(url);");
		buf.putLine2("sections.clear();");
		buf.putLine2("return null;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getElementLabel(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"Label");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
//		if (elementClassName.equals("Listener"))
//			System.out.println();
		
		Buf buf = new Buf();
		buf.putLine2("String label = \""+elementClassName+"\";");
		buf.putLine2("String name = "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
		if (hasField(element, "name", true)) {
			buf.putLine2("if (name == null && "+elementNameUncapped+".getName() != null)");
			buf.putLine2("	name = "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
			//buf.putLine2("	name = NameUtil.capName("+elementNameUncapped+".getName());");
		}
		buf.putLine2("if (name != null && !name.isEmpty())");
		buf.putLine2("	label = name + \" \" + label;");
		buf.putLine2("return label;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_updateState(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("updateState");
		
		Buf buf = new Buf();
		buf.putLine2("AbstractWizardPage<"+elementClassName+"> page = "+elementNameUncapped+"Wizard.getPage();");
		buf.putLine2("if (page != null)");
		buf.putLine2("	setSectionTitle(\""+elementClassName+" \" + page.getName());"); 
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_updateState2(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("updateState");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("String "+elementNameUncapped+"Name = "+elementClassName+"Util.getLabel("+elementNameUncapped+");");
		buf.putLine2("setSectionTitle("+elementNameUncapped+"Name + \" "+elementClassName+"\");"); 
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}


	
	public boolean hasField(Element element, String fieldName, boolean recurse) {
		Field field = getField(element, fieldName, recurse);
		return field != null;
	}

	public Field getField(Element element, String fieldName, boolean recurse) {
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
				Element parentElement = context.getElementByName(superType);
				if (parentElement != null) {
					Field field = getField(parentElement, fieldName, recurse);
					return field;
				}
			}
		}
		return null;
	}
	

}
