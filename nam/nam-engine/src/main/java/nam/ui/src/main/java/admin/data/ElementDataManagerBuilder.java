package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Type;
import nam.model.util.ViewUtil;
import nam.ui.Relation;
import nam.ui.View;

import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


/**
 * Builds an Element Record Data Manager Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementDataManagerBuilder extends AbstractElementManagerBuilder {

	public ElementDataManagerBuilder(GenerationContext context) {
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
		String className = elementName + "DataManager";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		//modelClass.setParentClassName("AbstractDomainListManager<"+elementName+", "+elementName+"ListObject>");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Type element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeClassAnnotations(modelClass, element);
		initializeInstanceFields(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Type type) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);

		//modelClass.addImportedClass(elementPackageName + ".Project");
		//modelClass.addImportedClass(elementPackageName + ".util.ProjectUtil");

		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
		
		modelClass.addImportedClass("nam.ui.design.SelectionContext");
		
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.List");

		modelClass.addImportedClass("javax.enterprise.context.SessionScoped");
		//modelClass.addImportedClass("javax.enterprise.event.Observes");
		modelClass.addImportedClass("javax.inject.Inject");
		modelClass.addImportedClass("javax.inject.Named");
		
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getContainedByRelation(view, elementClassName);
		if (relation != null) {
			List<String> containers = relation.getContainer();
			Iterator<String> iterator = containers.iterator();
			while (iterator.hasNext()) {
				String container = iterator.next();
				Element containerElement = context.getElementByName(container);
				Assert.notNull(containerElement, "Element not found: "+container);
				String containerElementPackageName = ModelLayerHelper.getElementPackageName(containerElement);
				String containerElementClassName = ModelLayerHelper.getElementClassName(containerElement);
				modelClass.addImportedClass(containerElementPackageName + "." + containerElementClassName);
				modelClass.addImportedClass(containerElementPackageName + ".util." + containerElementClassName + "Util");
			}
		}
		
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		//modelClass.addImportedClass("org.aries.ui.event.Cancelled");
		//modelClass.addImportedClass("org.aries.ui.event.Refresh");
		//modelClass.addImportedClass("org.aries.ui.event.Export");
		//modelClass.addImportedClass("org.aries.Assert");
		
		//modelClass.addImportedClass("java.util.Map");
		super.initializeImportedClasses(modelClass);
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type type) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		modelClass.getClassAnnotations().add(AnnotationUtil.createSessionScopedAnnotation());
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"DataManager"));
	}

	
	/*
	 * Instance fields
	 */

	protected void initializeInstanceFields(ModelClass modelClass, Type element) throws Exception {
		modelClass.addInstanceReference(createReference_ElementEventManager(element));
		modelClass.addInstanceReference(createReference_SelectionContext(element));
		modelClass.addInstanceReference(createReference_Scope(element));
	}
	
	public ModelReference createReference_ElementEventManager(Type element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String packageName = elementPackageName;
		String className = elementClassName+"EventManager";
		String elementName = elementNameUncapped+"EventManager";
		
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
	
	public ModelReference createReference_Scope(Type element) {
		String packageName = "java.lang";
		String className = "String";
		String elementName = "scope";
		
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(elementName);
		modelReference.setGenerateGetter(true);
		modelReference.setGenerateSetter(true);
		return modelReference;
	}
	
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Type type) throws Exception {
		modelClass.addInstanceOperation(createOperation_getOwner(type));
		modelClass.addInstanceOperation(createOperation_getElementList(type));
		modelClass.addInstanceOperations(createOperations_getElementList(type));
		modelClass.addInstanceOperation(createOperation_getDefaultElementList(type));
		//modelClass.addInstanceOperation(createOperation_addElement(type));
		modelClass.addInstanceOperation(createOperation_saveElement(type));
		modelClass.addInstanceOperation(createOperation_removeElement(type));
	}
	
	protected ModelOperation createOperation_getOwner(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("getOwner");
		modelOperation.setResultType("<T> T");
		
		Buf buf = new Buf();
		buf.putLine2("T owner = selectionContext.getSelection(scope);");
		buf.putLine2("return owner;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getElementList(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createSuppressWarning("unchecked"));
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("get"+elementClassName+"List");
		//modelOperation.addParameter(createParameter("Object", "recordKey"));
		modelOperation.setResultType("Collection<"+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("if (scope == null)");
		buf.putLine2("	return null;");
		buf.putLine2("");
		
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getContainedByRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getContainer();
			Iterator<String> iterator = children.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				String container = iterator.next();
				String containerNameCapped = NameUtil.capName(container);
				String containerNameUncapped = NameUtil.uncapName(container);
				
				buf.putLine2("if (scope.equals(\""+containerNameUncapped+"Selection\")) {");
				buf.putLine2("	return get"+elementClassName+"List_For"+containerNameCapped+"Selection();");
				buf.putLine2("}");
				buf.putLine2("");
			}
		}

		buf.putLine2("Object owner = getOwner();");
		buf.putLine2("if (owner == null)");
		buf.putLine2("	return null;");

		if (relation != null) {
			List<String> children = relation.getContainer();
			Iterator<String> iterator = children.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				String container = iterator.next();
				String containerNameCapped = NameUtil.capName(container);
				String containerNameUncapped = NameUtil.uncapName(container);
				buf.putLine2("");
				if (i == 0)
					buf.put2("");
				else buf.put2("} else ");
				buf.putLine("if (scope.equals(\""+containerNameUncapped+"\")) {");
				buf.putLine2("	return get"+elementClassName+"List_For"+containerNameCapped+"(("+containerNameCapped+") owner);");
			}
			if (!children.isEmpty()) {
				buf.putLine2("");
				buf.putLine2("} else {");
				buf.putLine2("	return getDefaultList();");
				buf.putLine2("}");
			} else {
				buf.putLine2("");
				buf.putLine2("return getDefaultList();");
			}
		} else {
			buf.putLine2("");
			buf.putLine2("return getDefaultList();");
		}

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected Collection<ModelOperation> createOperations_getElementList(Type type) {
		Collection<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getContainedByRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getContainer();
			Iterator<String> iterator = children.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				String container = iterator.next();
				modelOperations.add(createOperation_getElementList(type, container));
				modelOperations.add(createOperation_getElementListForSelection(type, container));
			}
		}
		return modelOperations;
	}

	protected ModelOperation createOperation_getElementList(Type type, String container) {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		String containerNameCapped = NameUtil.capName(container);
		String containerNameUncapped = NameUtil.uncapName(container);
		String elementClassNamePlural = NameUtil.toPlural(elementClassName);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"List_For"+containerNameCapped);
		modelOperation.addParameter(createParameter(containerNameCapped, containerNameUncapped));
		modelOperation.setResultType("Collection<"+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("return "+containerNameCapped+"Util.get"+elementClassNamePlural+"("+containerNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getElementListForSelection(Type type, String container) {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		String containerNameCapped = NameUtil.capName(container);
		String containerNameUncapped = NameUtil.uncapName(container);
		String elementClassNamePlural = NameUtil.toPlural(elementClassName);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("get"+elementClassName+"List_For"+containerNameCapped+"Selection");
		modelOperation.setResultType("Collection<"+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("Collection<"+containerNameCapped+"> "+containerNameUncapped+"Selection = selectionContext.getSelection(\""+containerNameUncapped+"Selection\");");
		buf.putLine2("Collection<"+elementClassName+"> "+elementNameUncapped+"List = "+containerNameCapped+"Util.get"+elementClassNamePlural+"("+containerNameUncapped+"Selection);");
		buf.putLine2("return "+elementNameUncapped+"List;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getDefaultElementList(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getDefaultList");
		//modelOperation.addParameter(createParameter("Object", "recordKey"));
		modelOperation.setResultType("Collection<"+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("return null;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getElementList(Type owner, Type element) throws Exception {
		String ownerClassName = ModelLayerHelper.getElementClassName(owner);
		String ownerNameUncapped = ModelLayerHelper.getElementNameUncapped(owner);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("get"+elementClassName+"List");
		//modelOperation.addParameter(createParameter("Object", "recordKey"));
		modelOperation.setResultType("Collection<"+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("Collection<"+elementClassName+"> "+elementNameUncapped+" = recordByKeyMap.get(recordKey);");
		
		buf.putLine2("return view"+elementClassName+"("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_saveElement(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("save"+elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("if (scope != null) {");
		buf.putLine2("	Object owner = getOwner();");
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getContainedByRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getContainer();
			Iterator<String> iterator = children.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				String child = iterator.next();
				String childNameCapped = NameUtil.capName(child);
				String childNameUncapped = NameUtil.uncapName(child);
				buf.putLine2("");
				if (i == 0)
					buf.put3("");
				else buf.put3("} else ");
				buf.putLine("if (scope.equals(\""+childNameUncapped+"\")) {");
				buf.putLine2("		"+childNameCapped+"Util.add"+elementClassName+"(("+childNameCapped+") owner, "+elementNameUncapped+");");
			}
			if (!children.isEmpty()) {
				buf.putLine3("}");
			}
		}
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_removeElement(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("remove"+elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("boolean");
		
		Buf buf = new Buf();
		buf.putLine2("if (scope != null) {");
		buf.putLine2("	Object owner = getOwner();");
		View view = context.getModule().getView();
		Relation relation = ViewUtil.getContainedByRelation(view, elementClassName);
		if (relation != null) {
			List<String> children = relation.getContainer();
			Iterator<String> iterator = children.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				String child = iterator.next();
				String childNameCapped = NameUtil.capName(child);
				String childNameUncapped = NameUtil.uncapName(child);
				buf.putLine2("");
				if (i == 0)
					buf.put3("");
				else buf.put3("} else ");
				buf.putLine("if (scope.equals(\""+childNameUncapped+"\")) {");
				buf.putLine2("		return "+childNameCapped+"Util.remove"+elementClassName+"(("+childNameCapped+") owner, "+elementNameUncapped+");");
			}
			if (!children.isEmpty()) {
				buf.putLine3("}");
			}
		}
		buf.putLine2("}");
		buf.putLine2("return false;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
