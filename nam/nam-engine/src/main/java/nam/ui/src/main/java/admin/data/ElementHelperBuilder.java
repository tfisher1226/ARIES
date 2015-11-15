package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;

import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds an Element Helper Object Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementHelperBuilder extends AbstractElementManagerBuilder {

	public ElementHelperBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}

	public ModelClass buildClass(Type type) throws Exception {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String packageName = getPackageName(type);
		String className = elementName + "Helper";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		if (ElementUtil.isEnumeration(type)) {
			modelClass.setParentClassName("AbstractEnumerationHelper<"+elementClassName+">");
		} else if (ElementUtil.isElement(type)) {
			modelClass.setParentClassName("AbstractElementHelper<"+elementClassName+">");
		}
		modelClass.addImplementedInterface("Serializable");
		initializeClass(modelClass, type);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Type type) throws Exception {
		initializeImportedClasses(modelClass, type);
		initializeClassAnnotations(modelClass, type);
		initializeInstanceOperations(modelClass, type);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Type type) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);

//		if (elementClassName.equals("User"))
//			System.out.println();
		
		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");

		if (CodeUtil.hasPhoneNumberField(type)) {
			modelClass.addImportedClass("org.aries.common.PhoneLocation");
			modelClass.addImportedClass("org.aries.common.PhoneNumber");
			modelClass.addImportedClass("java.util.HashMap");
			modelClass.addImportedClass("java.util.Map");
		}

		if (ElementUtil.isRoot(type)) {
			modelClass.addImportedClass("org.aries.ui.manager.AbstractElementHelper");
			modelClass.addImportedClass("org.aries.runtime.BeanContext");
			modelClass.addImportedClass("javax.faces.model.DataModel");

		} else if (ElementUtil.isElement(type)) {
			modelClass.addImportedClass("org.aries.ui.manager.AbstractElementHelper");
			modelClass.addImportedClass("javax.faces.model.DataModel");

		} else if (ElementUtil.isEnumeration(type)) {
			modelClass.addImportedClass("org.aries.ui.manager.AbstractEnumerationHelper");
		}
		
		//modelClass.addImportedClass("org.apache.commons.lang.StringUtils");
		//modelClass.addImportedClass("org.apache.commons.lang.StringEscapeUtils");
		
//		modelClass.addImportedClass("org.jboss.seam.ScopeType");
//		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
//		modelClass.addImportedClass("org.jboss.seam.annotations.Startup");
//		modelClass.addImportedClass("org.jboss.seam.annotations.intercept.BypassInterceptors");
		
		modelClass.addImportedClass("javax.enterprise.context.SessionScoped");
		//modelClass.addImportedClass("javax.enterprise.event.Observes");
		modelClass.addImportedClass("javax.inject.Named");
		
		modelClass.addImportedClass("java.io.Serializable");
		modelClass.addImportedClass("java.util.Collection");
		//modelClass.addImportedClass("java.util.List");
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type element) throws Exception {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		//modelClass.getClassAnnotations().add(AnnotationUtil.createStartupAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createAutoCreateAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createBypassInterceptorsAnnotation());
		//modelClass.getClassAnnotations().add(AnnotationUtil.createNameAnnotation(elementNameUncapped+"Helper"));
		//modelClass.getClassAnnotations().add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		////modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressSerialWarning());
		
		modelClass.getClassAnnotations().add(AnnotationUtil.createSessionScopedAnnotation());
		modelClass.getClassAnnotations().add(AnnotationUtil.createNamedAnnotation(elementNameUncapped+"Helper"));
	}
	
	
	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Type type) throws Exception {
		if (ElementUtil.isEnumeration(type))
			modelClass.addInstanceOperation(createOperation_factoryMethod(type));
		if (ElementUtil.isElement(type))
			modelClass.addInstanceOperation(createOperation_isEmpty(type));
		modelClass.addInstanceOperation(createOperation_toString(type));
		modelClass.addInstanceOperation(createOperation_toString2(type));
		if (ElementUtil.isElement(type)) {
			modelClass.addInstanceOperation(createOperation_validate(type));
			modelClass.addInstanceOperation(createOperation_validate2(type));
			modelClass.addInstanceOperations(createOperation_getFields((Element) type));
		}
	}

	protected ModelOperation createOperation_factoryMethod(Type element) throws Exception {
		modelUnit.addImportedClass("javax.enterprise.inject.Produces");
		//modelUnit.addImportedClass("org.jboss.seam.annotations.Factory");
		
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createFactoryAnnotation(elementNameUncapped + "List"));
		modelOperation.addAnnotation(AnnotationUtil.createProducesAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("get" + elementClassName + "Array");
		modelOperation.setResultType(elementClassName + "[]");
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementClassName+".values();");

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
		buf.putLine2("return "+elementClassName+"Util.isEmpty("+elementNameUncapped+");");

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
			buf.putLine2("return "+elementClassName+"Util.toString("+elementNameUncapped+");");
		}

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
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementClassName+"Util.toString("+elementNameUncapped+"List);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_validate(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("validate");
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementClassName+"Util.validate("+elementNameUncapped+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_validate2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("validate");
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		buf.putLine2("return "+elementClassName+"Util.validate("+elementNameUncapped+"List);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperation_getFields(Element element) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
//		if (elementClassName.equalsIgnoreCase("preferences"))
//			System.out.println();

		List<Field> fields = ElementUtil.getFields(element);
		Map<Field, Type> map = createFieldTypeMap(fields);
		List<Field> fieldList = createSortedFieldListByName(map.keySet());
		Iterator<Field> iterator = fieldList.iterator();
		boolean phoneNumbersHandled = false;
		while (iterator.hasNext()) {
			Field field = iterator.next();
			Type type = map.get(field);
			
			//String fieldType = field.getType();
//			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
//			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String structure = field.getStructure();
			
			if (!phoneNumbersHandled && CodeUtil.isPhoneNumberField(field)) {
				modelOperations.add(createOperation_getFields_phoneNumbers(element, field));
				modelUnit.addImportedClass(field);
				phoneNumbersHandled = true;
				continue;
			}
			
			if (structure.equals("list") || structure.equals("set")) {
				modelOperations.add(createOperation_getFields(element, field, type));
				modelOperations.add(createOperation_getFields2(element, field, type));
				modelUnit.addImportedClass(field);
				
			} else if (structure.equals("map")) {
				//TODO implement for map model construction...
				modelOperations.add(createOperation_getFields(element, field, type));
				modelOperations.add(createOperation_getFields2(element, field, type));
				modelUnit.addImportedClass(field);
			}
		}
		
		return modelOperations;
	}
	
	protected ModelOperation createOperation_getFields_phoneNumbers(Type element, Field field) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldKeyClassName = ModelLayerHelper.getFieldClassName(field);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getPhoneNumbers");
		modelOperation.setResultType("Map<PhoneLocation, "+fieldClassName+">");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+" == null)");
		buf.putLine2("	return null;");
		buf.putLine2("Map<PhoneLocation, PhoneNumber> map = new HashMap<PhoneLocation, PhoneNumber>();");
		buf.putLine2("PhoneNumber cellPhone = "+elementNameUncapped+".getCellPhone();");
		buf.putLine2("PhoneNumber homePhone = "+elementNameUncapped+".getHomePhone();");
		buf.putLine2("if (cellPhone != null)");
		buf.putLine2("	map.put(PhoneLocation.CELL, cellPhone);");
		buf.putLine2("if (homePhone != null)");
		buf.putLine2("	map.put(PhoneLocation.HOME, homePhone);");
		buf.putLine2("return map;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getFields(Type element, Field field, Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String objectClassName = fieldClassName; 
		
		if (ElementUtil.isElement(type)) {
			objectClassName = fieldClassName + "ListObject";
		} else if (ElementUtil.isEnumeration(type)) {
			if (ClassUtil.isJavaDefaultType(fieldClassName))
				objectClassName = fieldClassName;
			else objectClassName = fieldClassName + "ListObject";
		}

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("get"+fieldNameCapped);
		modelOperation.setResultType("DataModel<"+objectClassName+">");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		
		buf.putLine2("if ("+elementNameUncapped+" == null)");
			buf.putLine2("	return null;");
		buf.putLine2("return get"+fieldNameCapped+"("+elementNameUncapped+".get"+fieldNameCapped+"());");
		
		//buf.putLine2(fieldClassName+"ListManager "+fieldNameUncapped+"ListManager = BeanContext.getFromSession(\""+fieldNameUncapped+"ListManager\");");
		//buf.putLine2(fieldClassName+"ListManager "+fieldNameUncapped+"ListManager = BeanContext.getFromSession(\""+fieldNameUncapped+"ListManager\");");
		//buf.putLine2("DataModel<"+fieldClassName+"ListObject> dataModel = "+fieldNameUncapped+"ListManager.getDataModel("+fieldNameUncapped+"List);");
		//buf.putLine2("return dataModel;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getFields2(Type element, Field field, Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);	
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldClassNameUncapped = NameUtil.uncapName(fieldClassName);
		String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
		String structure = field.getStructure();
		String objectClassName = fieldClassName; 
		
		if (ElementUtil.isElement(type)) {
			objectClassName = fieldClassName + "ListObject";
		} else if (ElementUtil.isEnumeration(type)) {
			if (ClassUtil.isJavaDefaultType(fieldClassName))
				objectClassName = fieldClassName;
			else objectClassName = fieldClassName + "ListObject";
		}

		//if (objectClassName == null)
		//	System.out.println();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("get"+fieldNameCapped);
		modelOperation.setResultType("DataModel<"+objectClassName+">");
		
		if (structure.equals("map")) {
			modelUnit.addImportedClass("java.util.Map");
			//modelUnit.addImportedClass("java.util.HashMap");
			String fieldKeyClassName = TypeUtil.getClassName(field.getKey());
			modelOperation.addParameter(createParameter("Map<"+fieldKeyClassName+", "+fieldClassName+">", fieldNameUncapped+"Map"));
		} else modelOperation.addParameter(createParameter("Collection<"+fieldClassName+">", fieldNameUncapped+"List"));
		
		Buf buf = new Buf();
		if (ClassUtil.isJavaDefaultType(fieldClassName)) {
			if (structure.equals("map")) {
				buf.putLine2("List<"+fieldClassName+"> values = new ArrayList<"+fieldClassName+">("+fieldNameUncapped+"Map.values());");
			} else buf.putLine2("List<"+fieldClassName+"> values = new ArrayList<"+fieldClassName+">("+fieldNameUncapped+"List);");
			buf.putLine2("@SuppressWarnings(\"unchecked\") DataModel<"+fieldClassName+"> dataModel = new ListTableModel<"+fieldClassName+">(values);");
			modelUnit.addImportedClass("org.aries.ui.model.ListTableModel");
			modelUnit.addImportedClass("java.util.ArrayList");
			modelUnit.addImportedClass("java.util.List");
			
		} else {
			//modelOperation.addImportedClass(fieldPackageName + ".ui." + fieldClassNameUncapped + "." + fieldClassName + "ListManager");
			//modelOperation.addImportedClass(fieldPackageName + ".ui." + fieldClassNameUncapped + "." + fieldClassName + "ListObject");
			modelOperation.addImportedClass(fieldPackageName + "." + fieldClassNameUncapped + "." + fieldClassName + "ListManager");
			modelOperation.addImportedClass(fieldPackageName + "." + fieldClassNameUncapped + "." + fieldClassName + "ListObject");
			buf.putLine2(fieldClassName+"ListManager "+fieldClassNameUncapped+"ListManager = BeanContext.getFromSession(\""+fieldClassNameUncapped+"ListManager\");");
			buf.putLine2("DataModel<"+objectClassName+"> dataModel = "+fieldClassNameUncapped+"ListManager.getDataModel("+fieldNameUncapped+"List);");
			modelUnit.addImportedClass("org.aries.runtime.BeanContext");
		}
		
		buf.putLine2("return dataModel;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

}
