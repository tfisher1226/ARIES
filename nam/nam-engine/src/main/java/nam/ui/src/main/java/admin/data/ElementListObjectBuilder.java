package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;

import nam.ProjectLevelHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Type;
import nam.model.util.ElementUtil;
import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


/**
 * Builds an Element Record List Object Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementListObjectBuilder extends AbstractBeanBuilder {

	public ElementListObjectBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public ModelClass buildClass(Type element) throws Exception {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "ListObject";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.setParentClassName("AbstractListObject<"+elementClassName+">");
		modelClass.addImplementedInterface("Comparable<"+className+">");
		modelClass.addImplementedInterface("Serializable");
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
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);

		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		
		if (ElementUtil.isEnumeration(type)) {
			//nothing for now
			
		} else if (ElementUtil.isElement(type)) {
			modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");
		}

		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		modelClass.addImportedClass("org.aries.ui.AbstractListObject");
		modelClass.addImportedClass("java.io.Serializable");
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type element) throws Exception {
		//modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressSerialWarning());
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
		modelConstructor.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("this."+elementNameUncapped+" = "+elementNameUncapped+";");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	
	/*
	 * Instance fields
	 */

	protected void initializeInstanceFields(ModelClass modelClass, Type element) throws Exception {
		modelClass.addInstanceReference(createReference_Element(element));
	}
	
	public ModelReference createReference_Element(Type element) {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		String className = ModelLayerHelper.getElementClassName(element);
		
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(elementName);
		modelReference.setGenerateGetter(true);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Type element) throws Exception {
		modelClass.addInstanceOperation(createOperation_getKey(element));
		if (!ElementUtil.isEnumeration(element))
			modelClass.addInstanceOperation(createOperation_getKey2(element));
		modelClass.addInstanceOperation(createOperation_getLabel(element));
		if (!ElementUtil.isEnumeration(element))
			modelClass.addInstanceOperation(createOperation_getLabel2(element));
		modelClass.addInstanceOperation(createOperation_setChecked(element));
		//modelClass.addInstanceOperation(createOperation_setChecked2(element));
		//modelClass.addInstanceOperation(createOperation_fireChangeEvent(element));
		modelClass.addInstanceOperation(createOperation_getIcon(element));
		modelClass.addInstanceOperation(createOperation_toString(element));
		modelClass.addInstanceOperation(createOperation_toString2(element));
		modelClass.addInstanceOperation(createOperation_compareTo(element));
		modelClass.addInstanceOperation(createOperation_equals(element));
	}
	
	protected ModelOperation createOperation_getKey(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getKey");
		modelOperation.setResultType("Object");
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(element))
			buf.putLine2("return "+elementNameUncapped+".name();");
		else buf.putLine2("return getKey("+elementNameUncapped+");");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getKey2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getKey");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("Object");
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementClassName+"Util.getKey("+elementNameUncapped+");");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getLabel(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getLabel");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(element))
			buf.putLine2("return "+elementNameUncapped+".name();");
		else buf.putLine2("return getLabel("+elementNameUncapped+");");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getLabel2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getLabel");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementClassName+"Util.getLabel("+elementNameUncapped+");");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_toString(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("toString");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return toString("+elementNameUncapped+");");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_setChecked(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("setChecked");
		modelOperation.addParameter(createParameter("boolean", "checked"));
		
		Buf buf = new Buf();
		buf.putLine2("super.setChecked(checked);");
		//buf.putLine2("fireChangeEvent("+elementNameUncapped+", checked);");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_setChecked2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("setChecked2");
		modelOperation.addParameter(createParameter("boolean", "checked"));
		
		Buf buf = new Buf();
		buf.putLine2("super.setChecked(checked);");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_fireChangeEvent(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED + Modifier.STATIC);
		modelOperation.setName("fireChangeEvent");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.addParameter(createParameter("boolean", "checked"));

		Buf buf = new Buf();
		buf.putLine2(elementClassName+"EventManager eventManager = BeanContext.getFromSession(\""+elementNameUncapped+"EventManager\");");
		buf.putLine2("if (checked)");
		buf.putLine2("	eventManager.fireCheckedEvent("+elementNameUncapped+");");
		buf.putLine2("else eventManager.fireUncheckedEvent("+elementNameUncapped+");");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getIcon(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getIcon");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		buf.putLine2("return \"/icons/nam/"+elementClassName+"16.gif\";");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_toString2(Type type) throws Exception {
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
			buf.putLine2("return "+elementClassName+"Util.toString("+elementNameUncapped+");");
		}

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_compareTo(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("compareTo");
		modelOperation.setResultType("int");
		modelOperation.addParameter(createParameter(elementClassName+"ListObject", "other"));
		
		Buf buf = new Buf();
		if (ElementUtil.isEnumeration(element)) {
			buf.putLine2("String thisText = this."+elementNameUncapped+".name();");
			buf.putLine2("String otherText = other."+elementNameUncapped+".name();");

		} else {
			buf.putLine2("Object thisKey = getKey(this."+elementNameUncapped+");");
			buf.putLine2("Object otherKey = getKey(other."+elementNameUncapped+");");
			buf.putLine2("String thisText = thisKey.toString();");
			buf.putLine2("String otherText = otherKey.toString();");
		}
		//buf.putLine2("if (thisText == null)");
		//buf.putLine2("	return -1;");
		//buf.putLine2("if (otherText == null)");
		//buf.putLine2("	return 1;");
		buf.putLine2("return thisText.compareTo(otherText);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_equals(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("equals");
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(createParameter("Object", "object"));
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"ListObject other = ("+elementClassName+"ListObject) object;");
		if (ElementUtil.isEnumeration(type)) {
			buf.putLine2("String thisText = toString(this."+elementNameUncapped+");");
			buf.putLine2("String otherText = toString(other."+elementNameUncapped+");");
			buf.putLine2("return thisText.equals(otherText);");
			
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2("Object thisKey = "+elementClassName+"Util.getKey(this."+elementNameUncapped+");");
			buf.putLine2("Object otherKey = "+elementClassName+"Util.getKey(other."+elementNameUncapped+");");
			//buf.putLine2("String thisText = toString(this."+elementNameUncapped+");");
			//buf.putLine2("String otherText = toString(other."+elementNameUncapped+");");
			buf.putLine2("if (thisKey == null)");
			buf.putLine2("	return false;");
			buf.putLine2("if (otherKey == null)");
			buf.putLine2("	return false;");
			buf.putLine2("return thisKey.equals(otherKey);");
			
		//TODO take this out?
		} else if (ElementUtil.isElement(type) && true) {
			buf.putLine2("Long thisId = this."+elementNameUncapped+".getId();");
			buf.putLine2("Long otherId = other."+elementNameUncapped+".getId();");
			buf.putLine2("if (thisId == null)");
			buf.putLine2("	return false;");
			buf.putLine2("if (otherId == null)");
			buf.putLine2("	return false;");
			buf.putLine2("return thisId.equals(otherId);");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
