package nam.model.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Literal;
import nam.model.ModelLayerHelper;
import nam.model.Reference;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.TypeUtil;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds an Element Util Class Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementUtilBuilder extends AbstractElementClassBuilder {

	public ElementUtilBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}
	
	public ModelClass buildClass(Type type) throws Exception {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
		String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		//String packageName = namespacePackage + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + ".util";
		String className = elementName + "Util";
		
		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.setParentClassName("BaseUtil");
		modelClass.addImportedClass("org.aries.util.BaseUtil");
		//modelClass.addImplementedInterface("Serializable");
		initializeClass(modelClass, type);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Type type) throws Exception {
		initializeImportedClasses(modelClass, type);
		initializeClassAnnotations(modelClass, type);
		initializeStaticFields(modelClass, type);
		initializeStaticOperations(modelClass, type);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Type type) {
		addImportedClass(modelClass, type.getType());
		
		if (ElementUtil.isElement(type)) {
			Element element = (Element) type;
			List<Field> fields = ElementUtil.getFields(element);
			Iterator<Field> iterator = fields.iterator();
			while (iterator.hasNext()) {
				Field field = iterator.next();
				if (field instanceof Reference == false)
					continue;
				
				//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
				String fieldPackageName = TypeUtil.getPackageName(field.getType());
				String fieldClassName = TypeUtil.getClassName(field.getType()) + "Util";
				modelClass.addImportedClass(fieldPackageName + ".util." + fieldClassName);	
			}
			
			if (CodeUtil.hasKeyElement(element))
				modelClass.addImportedClass(CodeUtil.getKeyElement(element));
			
			modelClass.addImportedClass("org.aries.util.ObjectUtil");
			modelClass.addImportedClass("org.aries.util.Validator");
			//modelClass.addImportedClass("org.apache.commons.lang.StringUtils");
			//modelClass.addImportedClass("org.apache.commons.lang.StringEscapeUtils");
			//modelClass.addImportedClass("org.apache.commons.lang.SerializationUtils");
			
		} else if (ElementUtil.isEnumeration(type)) {
			modelClass.addImportedClass("java.util.Arrays");
		}
		
		modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.Collections");
		modelClass.addImportedClass("java.util.Comparator");
		modelClass.addImportedClass("java.util.Iterator");
		modelClass.addImportedClass("java.util.List");
	}

	
	/*
	 * Class Annotations
	 */
	
	protected void initializeClassAnnotations(ModelClass modelClass, Type element) throws Exception {
		//modelClass.getClassAnnotations().add(AnnotationUtil.createSuppressSerialWarning());
	}


	/*
	 * Static operations
	 */

	protected void initializeStaticFields(ModelClass modelClass, Type type) {
		if (ElementUtil.isEnumeration(type)) {
			modelClass.addStaticAttribute(createStaticField_valuesArray((Enumeration) type));
			modelClass.addStaticAttribute(createStaticField_valuesList((Enumeration) type));
		}
	}
	
	public static ModelAttribute createStaticField_valuesArray(Enumeration type) {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL);
		attribute.setClassName(elementClassName+"[]");
		attribute.setName("VALUES_ARRAY");
		Buf buf = new Buf();
		buf.putLine("new "+elementClassName+"[] {");
		
		List<Literal> literals = type.getLiterals();
		Iterator<Literal> iterator = literals.iterator();
		for (int i=1; iterator.hasNext(); i++) {
			Literal literal = (Literal) iterator.next();
			buf.put2(elementClassName + "." + literal.getName());
			if (i < literals.size())
				buf.putLine(",");
			else buf.putLine();
		}

		buf.put1("}");
		attribute.setDefault(buf.get());
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public static ModelAttribute createStaticField_valuesList(Enumeration type) {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL);
		attribute.setClassName("List<"+elementClassName+">");
		attribute.setName("VALUES");
		Buf buf = new Buf();
		buf.put("Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY))");
		attribute.setDefault(buf.get());
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	

	/*
	 * Static operations
	 */

	protected void initializeStaticOperations(ModelClass modelClass, Type type) throws Exception {
		if (ElementUtil.isElement(type)) {
			Element element = (Element) type;
			modelClass.addInstanceOperation(createOperation_getKey(element));
			modelClass.addInstanceOperation(createOperation_getLabel(element));
			modelClass.addInstanceOperation(createOperation_isEmpty(element));
			modelClass.addInstanceOperation(createOperation_isEmpty2(element));
		}
		if (ElementUtil.isEnumeration(type)) {
			modelClass.addInstanceOperation(createOperation_getValue((Enumeration) type));
		}
		modelClass.addInstanceOperation(createOperation_toString(type));
		modelClass.addInstanceOperation(createOperation_toString2(type));
		if (ElementUtil.isElement(type)) {
			Element element = (Element) type;
			modelClass.addInstanceOperation(createOperation_createRecord(element));
			modelClass.addInstanceOperations(createOperation_createRecords(element));
			modelClass.addInstanceOperation(createOperation_initializeRecord(element));
			modelClass.addInstanceOperation(createOperation_validateRecord(element));
			modelClass.addInstanceOperation(createOperation_validateRecord2(element));
		}
		if (!ElementUtil.isCriteriaElement(type)) {
			modelClass.addInstanceOperations(createOperations_sortRecords(type));
			modelClass.addInstanceOperation(createOperation_createComparator(type));
		}
		//modelClass.addInstanceOperation(createOperation_compareFields(type));
		if (ElementUtil.isElement(type)) {
			Element element = (Element) type;
			modelClass.addInstanceOperation(createOperation_cloneRecord(element));
			modelClass.addInstanceOperations(createOperation_cloneRecords(element));
			if (CodeUtil.hasKeyElement(element))
				modelClass.addInstanceOperation(createOperation_cloneRecordKey(element));
		}
	}

	protected ModelOperation createOperation_getKey(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("getKey");
		modelOperation.setResultType("Object");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		if (ElementUtil.hasField(element, "groupId") && ElementUtil.hasField(element, "artifactId")) {
			buf.putLine2("return "+elementNameUncapped+".getGroupId() + \".\" + "+elementNameUncapped+".getArtifactId();");
		} else if (ElementUtil.hasField(element, "domain") && ElementUtil.hasField(element, "name")) {
			buf.putLine2("return "+elementNameUncapped+".getDomain() + \".\" + "+elementNameUncapped+".getName();");
		} else if (ElementUtil.hasField(element, "name")) {
			buf.putLine2("return "+elementNameUncapped+".getName();");
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_getLabel(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("getLabel");
		modelOperation.setResultType("String");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		if (ElementUtil.hasField(element, "groupId") && ElementUtil.hasField(element, "artifactId")) {
			buf.putLine2("return "+elementNameUncapped+".getGroupId() + \".\" + "+elementNameUncapped+".getArtifactId();");
		} else if (ElementUtil.hasField(element, "domain") && ElementUtil.hasField(element, "name")) {
			buf.putLine2("return "+elementNameUncapped+".getDomain() + \".\" + "+elementNameUncapped+".getName();");
		} else if (ElementUtil.hasField(element, "name")) {
			buf.putLine2("return "+elementNameUncapped+".getName();");
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getLabel2(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("getLabel");
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+"List == null  || "+elementNameUncapped+"List.size() == 0)");
		buf.putLine2("	return true;");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	if (!isEmpty("+elementNameUncapped+"))");
		buf.putLine2("		return false;");
		buf.putLine2("}");
		buf.putLine2("return true;");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_isEmpty(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("isEmpty");
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+" == null)");
		buf.putLine2("	return true;");
		buf.putLine2("boolean status = false;");
		
		List<Field> requiredFields = ElementUtil.getRequiredFields(element);
		//List<Field> requiredFields = createSortedFieldListByName(unsortedFields);
		Iterator<Field> iterator = requiredFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldType = field.getType();
			Element fieldElement = context.getElementByType(fieldType);
			Enumeration fieldEnumeration = context.getEnumerationByType(fieldType);
			
//			if (fieldNameCapped.equals("State"))
//				System.out.println();
			
			if (field instanceof Attribute) {
				//TODO add others as needed
				if (fieldClassName.equals("String")) {
					modelUnit.addImportedClass("org.apache.commons.lang.StringUtils");
					buf.putLine2("status |= StringUtils.isEmpty("+elementNameUncapped+".get"+fieldNameCapped+"());");
					
				} else if (fieldEnumeration != null) {
					if (field.getDefault() != null)
						buf.putLine2("status |= "+elementNameUncapped+".get"+fieldNameCapped+"() != "+fieldClassName+"."+field.getDefault()+";");
					else buf.putLine2("status |= "+elementNameUncapped+".get"+fieldNameCapped+"() != null;");
					modelUnit.addImportedClass(ModelLayerHelper.getFieldQualifiedName(field));
					
				} else if (fieldElement != null) {
					modelUnit.addImportedClass(ModelLayerHelper.getFieldQualifiedName(field));
					buf.putLine2("status |= "+elementNameUncapped+".get"+fieldNameCapped+"() != null;");
				}
				
			} else if (field instanceof Reference) {
				buf.putLine2("status |= "+fieldClassName+"Util.isEmpty("+elementNameUncapped+".get"+fieldNameCapped+"());");
			}
		}
		
		buf.putLine2("return status;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_isEmpty2(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("isEmpty");
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+"List == null  || "+elementNameUncapped+"List.size() == 0)");
		buf.putLine2("	return true;");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	if (!isEmpty("+elementNameUncapped+"))");
		buf.putLine2("		return false;");
		buf.putLine2("}");
		buf.putLine2("return true;");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_getValue(Enumeration enumeration) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(enumeration);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("get" + elementClassName);
		modelOperation.setResultType(elementClassName);
		modelOperation.addParameter(createParameter("int", "ordinal"));
		
		Buf buf = new Buf();
		List<Literal> literals = enumeration.getLiterals();
		Iterator<Literal> iterator = literals.iterator();
		for (int i=1; iterator.hasNext(); i++) {
			Literal literal = (Literal) iterator.next();
			String literalName = literal.getName();

			buf.putLine2("if (ordinal == "+elementClassName+"."+literalName+".ordinal())"); 
			buf.putLine2("	return "+elementClassName+"."+literalName+";");
		}
		
		buf.putLine2("return null;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_toString(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("toString");
		modelOperation.setResultType("String");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		Buf buf = new Buf();
		if (ElementUtil.isElement(type)) {
			Element element = (Element) type;
			
			buf.putLine2("if (isEmpty("+elementNameUncapped+"))");
			buf.putLine2("	return \""+elementClassName+": [uninitialized] \"+"+elementNameUncapped+".toString();");
			
			List<Field> unsortedFields = ElementUtil.getLabelFields(element);
			List<Field> labelFields = createSortedFieldListByName(unsortedFields);
			
			if (labelFields.size() == 0) {
				//TODO make this default situation provide a more intelligent string
				buf.putLine2("String text = "+elementNameUncapped+".toString();");
				
			} else {
				buf.putLine2("String text = \"\";");
				Iterator<Field> iterator = labelFields.iterator();
				for (int i=0; iterator.hasNext(); i++) {
					Field field = iterator.next();
					String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
					String fieldClassName = ModelLayerHelper.getFieldClassName(field);
					String optionalComma = "";
					
					if (i > 0)
						optionalComma = "+ \", \"";
					if (field instanceof Attribute) {
						//TODO add others as needed
						if (fieldClassName.equals("String")) {
							buf.putLine2("text += "+elementNameUncapped+".get"+fieldNameCapped+"()"+optionalComma+";");
						}
					} else if (field instanceof Reference) {
						buf.putLine2("text += "+fieldNameCapped+"Util.toString("+elementNameUncapped+".get"+fieldNameCapped+"())"+optionalComma+";");
					}
				}
			}
			buf.putLine2("return text;");
			
		} else if (ElementUtil.isEnumeration(type)) {
			buf.putLine2("return "+elementNameUncapped+".name();");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_toString2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("toString");
		modelOperation.setResultType("String");
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		if (ElementUtil.isElement(element)) {
			buf.putLine2("if (isEmpty("+elementNameUncapped+"List))");
			buf.putLine2("	return \"\";");
		}
		buf.putLine2("StringBuffer buf = new StringBuffer();");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("for (int i=0; iterator.hasNext(); i++) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	if (i > 0)");
		buf.putLine2("		buf.append(\", \");");
		buf.putLine2("	String text = toString("+elementNameUncapped+");");
		buf.putLine2("	buf.append(text);");
		buf.putLine2("}");
		buf.putLine2("String text = StringEscapeUtils.escapeJavaScript(buf.toString());");
		buf.putLine2("return text;");
		
		modelUnit.addImportedClass("org.apache.commons.lang.StringEscapeUtils");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_createRecord(Element element) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create");
		modelOperation.setResultType(elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = new "+elementClassName+"();");
		buf.putLine2("initialize("+elementNameUncapped+");");
		buf.putLine2("return "+elementNameUncapped+";");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<ModelOperation> createOperation_createRecords(Element element) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		if (elementClassName.equals("Preferences"))
//			System.out.println();
		
		Reference inverseReference = ElementUtil.getInverseReference(element);
		if (inverseReference != null) {
			String referenceType = inverseReference.getType();
			Element parent = context.getElementByType(referenceType);
			modelOperations.add(createOperation_createRecord(parent, element));
		}
		
//		Collection<Element> allElements = context.getAllElements();
//		Set<Reference> references = ElementUtil.getContainingReferences(allElements, element);
//		Iterator<Reference> iterator = references.iterator();
//		while (iterator.hasNext()) {
//			Reference reference = iterator.next();
//			if (!FieldUtil.isContained(reference))
//				continue;
//			String referenceType = reference.getType();
//			Element parent = context.getElementByType(referenceType);
//			modelOperations.add(createOperation_createRecord(parent, element));
//		}

		return modelOperations;
	}
	
	protected ModelOperation createOperation_createRecord(Element parent, Element element) {
		String parentElementNameUncapped = ModelLayerHelper.getElementNameUncapped(parent);
		String parentElementClassName = ModelLayerHelper.getElementClassName(parent);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create");
		modelOperation.addParameter(createParameter(parentElementClassName, parentElementNameUncapped));
		modelOperation.setResultType(elementClassName);
		modelOperation.addImportedClass(parent);
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+" "+elementNameUncapped+" = create();");
		buf.putLine2(elementNameUncapped+".set"+parentElementClassName+"("+parentElementNameUncapped+");");
		buf.putLine2("return "+elementNameUncapped+";");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_initializeRecord(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("initialize");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));

		Buf buf = new Buf();
		//TODO add javadoc comment here
		List<Attribute> attributes = ElementUtil.getAttributes(element);
		//TODO add javadoc comment here
		List<Reference> references = ElementUtil.getReferences(element);
		buf.put(createOperation_initializeRecord_sourceCode(element, attributes)); 
		buf.put(createOperation_initializeRecord_sourceCode(element, references)); 
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected <F extends Field> String createOperation_initializeRecord_sourceCode(Element element, List<F> fields) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		Buf buf = new Buf();
		Map<F, Element> map = createFieldTypeMap(fields);
		List<F> fieldList = sortTypesByInstanceName(map.keySet());
		Iterator<F> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			F field = iterator.next();
			String fieldType = field.getType();
			Type fieldElement = map.get(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			String structure = field.getStructure();

//			if (elementNameUncapped.equalsIgnoreCase("preferences") && fieldNameCapped.equalsIgnoreCase("User"))
//				System.out.println();
			
			if (FieldUtil.isInverse(field))
				continue;
			
			if (field instanceof Attribute) {
				if (structure.equals("map"))
					continue;
				
				if (FieldUtil.isBoolean(field)) {
					Object defaultValue = FieldUtil.getDefaultValue(field);
					if (defaultValue == null)
						defaultValue = Boolean.FALSE;
					buf.putLine2("if ("+elementNameUncapped+".get"+fieldNameCapped+"() == null)");
					buf.putLine2("	"+elementNameUncapped+".set"+fieldNameCapped+"("+defaultValue+");");
					continue;
				}
				
				if (FieldUtil.isString(field)) {
					Object defaultValue = FieldUtil.getDefaultValue(field);
					if (defaultValue != null) {
						buf.putLine2("if ("+elementNameUncapped+".get"+fieldNameCapped+"() == null)");
						buf.putLine2("	"+elementNameUncapped+".set"+fieldNameCapped+"("+defaultValue+");");
					}
					continue;
				}

				Enumeration enumeration = context.getEnumerationByType(fieldType);
				if (enumeration != null) {
					String defaultValue = (String) FieldUtil.getDefaultValue(field);
					if (defaultValue != null) {
						if (!defaultValue.startsWith(fieldClassName))
							defaultValue = fieldClassName + "." + defaultValue;
						if (structure.equals("item")) {
							buf.putLine2("if ("+elementNameUncapped+".get"+fieldNameCapped+"() == null)");
							buf.putLine2("	"+elementNameUncapped+".set"+fieldNameCapped+"("+defaultValue+");");
						} else {
							buf.putLine2("if ("+elementNameUncapped+".get"+fieldNameCapped+"().size() == 0)");
							buf.putLine2("	"+elementNameUncapped+".get"+fieldNameCapped+"().add("+defaultValue+");");
						}
						modelUnit.addImportedClass(enumeration);
					}
					continue;
				}
			}
				
			if (field instanceof Reference == false)
				continue;
			
			if (structure.equals("map")) {
				//String fieldElementNameCapped = ModelLayerHelper.getElementNameCapped(fieldElement);
				//buf.putLine2("if ("+elementNameUncapped+".get"+fieldNameCapped+"() == null)");
				//buf.putLine2("	"+elementNameUncapped+".set"+fieldNameCapped+"("+fieldElementNameCapped+"Util.create"+fieldClassName+"());");
				continue;
			}

			//String fieldElementNameCapped = ModelLayerHelper.getElementNameCapped(fieldElement);
			//buf.putLine2("if ("+elementNameUncapped+".get"+fieldNameCapped+"() == null)");
			//buf.putLine2("	"+elementNameUncapped+".set"+fieldNameCapped+"("+fieldElementNameCapped+"Util.create"+fieldClassName+"());");
		}
		
		return buf.get();
	}
	
		
	protected ModelOperation createOperation_validateRecord(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("validate");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("boolean");

		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+" == null)");
		buf.putLine2("	return false;");
		buf.putLine2("Validator validator = Validator.getValidator();");

//		if (elementClassName.equals("Role"))
//			System.out.println();
		
		List<Field> fields = ElementUtil.getFields(element);
		Map<Field, Element> map = createFieldTypeMap(fields);
		List<Field> fieldList = sortTypesByInstanceName(map.keySet());
		Iterator<Field> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			//String fieldType = field.getType();
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String structure = field.getStructure();

			if (FieldUtil.isId(field))
				continue;
			if (!FieldUtil.isRequired(field))
				continue;
			if (FieldUtil.isInverse(field))
				continue;
						
			if (field instanceof Attribute) {
				if (FieldUtil.isString(field))
					buf.putLine2("validator.notEmpty("+elementNameUncapped+".get"+fieldNameCapped+"(), \"\\\""+fieldNameCapped+"\\\" must be specified\");");
				else if (structure.equals("item"))
					buf.putLine2("validator.notNull("+elementNameUncapped+".get"+fieldNameCapped+"(), \"\\\""+fieldNameCapped+"\\\" must be specified\");");
				else buf.putLine2("validator.notEmpty("+elementNameUncapped+".get"+fieldNameCapped+"(), \"At least one of \\\""+fieldNameCapped+"\\\" must be specified\");");
				continue;
			}
			
			if (structure.equals("item"))
				buf.putLine2("validator.isFalse("+fieldClassName+"Util.isEmpty("+elementNameUncapped+".get"+fieldNameCapped+"()), \"\\\""+fieldNameCapped+"\\\" must be specified\");");
			else buf.putLine2("validator.isFalse("+fieldClassName+"Util.isEmpty("+elementNameUncapped+".get"+fieldNameCapped+"()), \"At least one of \\\""+fieldNameCapped+"\\\" must be specified\");");
		}

		//buf.putLine2("");
		iterator = fieldList.iterator();
		//boolean lineBreakNeeded = false;
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);

			if (field instanceof Reference == false)
				continue;
			if (FieldUtil.isInverse(field))
				continue;
			
			if (CodeUtil.isPhoneNumberField(field)) {
				buf.putLine2("PhoneNumberUtil.validate("+elementNameUncapped+".get"+fieldNameCapped+"());");
				//modelOperation.addImportedClass(field);
				continue;
			}
			
			buf.putLine2(fieldClassName+"Util.validate("+elementNameUncapped+".get"+fieldNameCapped+"());");
			//lineBreakNeeded = true;
		}
		
		//if (lineBreakNeeded)
		//	buf.putLine2("");
		buf.putLine2("boolean isValid = validator.isValid();");
		buf.putLine2("return isValid;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_validateRecord2(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("validate");
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		buf.putLine2("Validator validator = Validator.getValidator();");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	//TODO break or accumulate?");
		buf.putLine2("	validate("+elementNameUncapped+");");
		buf.putLine2("}");
		buf.putLine2("boolean isValid = validator.isValid();");
		buf.putLine2("return isValid;");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperations_sortRecords(Type type) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createOperation_sortRecords(type));
		modelOperations.add(createOperation_sortRecords2(type));
		
		if (ElementUtil.isElement(type)) {
			Element element = (Element) type;
			Set<Field> fields = new HashSet<Field>();
			fields.addAll(ElementUtil.getUniqueFields(element));
			fields.addAll(ElementUtil.getIndexedFields(element));
			
			List<Field> sortedList = createSortedFieldListByName(fields);
			Iterator<Field> iterator = sortedList.iterator();
			while (iterator.hasNext()) {
				Field field = iterator.next();
				if (!FieldUtil.isRequired(field))
					continue;
				modelOperations.add(createOperation_sortRecords(element, field));
			}
		}
		
		return modelOperations;
	}

	protected ModelOperation createOperation_sortRecords(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("sortRecords");
		modelOperation.addParameter(createParameter("List<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		buf.putLine2("Collections.sort("+elementNameUncapped+"List, create"+elementClassName+"Comparator());");
		//buf.putLine2("Collections.sort("+elementNameUncapped+"List, new Comparator<"+elementClassName+">() {");
		//buf.putLine2("	public int compare("+elementClassName+" "+elementNameUncapped+"1, "+elementClassName+" "+elementNameUncapped+"2) {");
		//buf.putLine2("		String text1 = "+elementClassName+"Util.toString("+elementNameUncapped+"1);");
		//buf.putLine2("		String text2 = "+elementClassName+"Util.toString("+elementNameUncapped+"2);");
		//buf.putLine2("		return text1.compareTo(text2);");
		//buf.putLine2("	}");
		//buf.putLine2("});");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_sortRecords2(Type element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("sortRecords");
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementNameUncapped+"Collection"));
		modelOperation.setResultType("Collection<"+elementClassName+">");

		Buf buf = new Buf();
		buf.putLine2("List<"+elementClassName+"> list = new ArrayList<"+elementClassName+">("+elementNameUncapped+"Collection);");
		buf.putLine2("Collections.sort(list, create"+elementClassName+"Comparator());");
		buf.putLine2("return list;");

		//buf.putLine2("Collections.sort("+elementNameUncapped+"List, new Comparator<"+elementClassName+">() {");
		//buf.putLine2("	public int compare("+elementClassName+" "+elementNameUncapped+"1, "+elementClassName+" "+elementNameUncapped+"2) {");
		//buf.putLine2("		String text1 = "+elementClassName+"Util.toString("+elementNameUncapped+"1);");
		//buf.putLine2("		String text2 = "+elementClassName+"Util.toString("+elementNameUncapped+"2);");
		//buf.putLine2("		return text1.compareTo(text2);");
		//buf.putLine2("	}");
		//buf.putLine2("});");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_sortRecords(Type element, Field field) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("sortRecordsBy"+fieldNameCapped);
		modelOperation.addParameter(createParameter("List<"+elementClassName+">", elementNameUncapped+"List"));
		
		Buf buf = new Buf();
		buf.putLine2("Collections.sort("+elementNameUncapped+"List, new Comparator<"+elementClassName+">() {");
		buf.putLine2("	public int compare("+elementClassName+" "+elementNameUncapped+"1, "+elementClassName+" "+elementNameUncapped+"2) {");
		
		if (field instanceof Attribute) {
			//TODO add others as needed
			if (fieldClassName.equals("String")) {
				buf.putLine2("		String text1 = "+elementNameUncapped+"1.get"+fieldNameCapped+"();");
				buf.putLine2("		String text2 = "+elementNameUncapped+"2.get"+fieldNameCapped+"();");
				
			} else {
				Enumeration enumeration = context.getEnumerationByType(field.getType());
				if (enumeration != null) {
					buf.putLine2("		String text1 = "+elementNameUncapped+"1.get"+fieldNameCapped+"().name();");
					buf.putLine2("		String text2 = "+elementNameUncapped+"2.get"+fieldNameCapped+"().name();");
				}
			}
			buf.putLine2("		return text1.compareTo(text2);");
						
		} else if (field instanceof Reference) {
			//buf.putLine2("		String text1 = "+fieldClassName+"Util.toString("+elementNameUncapped+"1.get"+fieldNameCapped+"());");
			//buf.putLine2("		String text2 = "+fieldClassName+"Util.toString("+elementNameUncapped+"2.get"+fieldNameCapped+"());");
			//modelOperation.addImportedClass(field);
			buf.putLine2("		int status = "+elementNameUncapped+"1.compareTo("+elementNameUncapped+"2);");
			buf.putLine2("		return status;");
		}
		
		buf.putLine2("	}");
		buf.putLine2("});");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_createComparator(Type type) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"Comparator");
		modelOperation.setResultType("Comparator<"+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("return new Comparator<"+elementClassName+">() {");
		buf.putLine2("	public int compare("+elementClassName+" "+elementNameUncapped+"1, "+elementClassName+" "+elementNameUncapped+"2) {");
		
		if (ElementUtil.isEnumeration(type)) {
			buf.putLine2("		String text1 = "+elementNameUncapped+"1.value();");
			buf.putLine2("		String text2 = "+elementNameUncapped+"2.value();");
			buf.putLine2("		int status = text1.compareTo(text2);");
			buf.putLine2("		return status;");
			
		} else if (ElementUtil.isElement(type)) {
			buf.putLine2("		Object key1 = getKey("+elementNameUncapped+"1);");
			buf.putLine2("		Object key2 = getKey("+elementNameUncapped+"2);");
			buf.putLine2("		String text1 = key1.toString();");
			buf.putLine2("		String text2 = key2.toString();");
			buf.putLine2("		int status = text1.compareTo(text2);");
			buf.putLine2("		return status;");
			
//			Element element = (Element) type;
//			Collection<Field> keyFields = ElementUtil.getKeyFields(element);
//			Iterator<Field> iterator = keyFields.iterator();
//			for (int i=0; iterator.hasNext(); i++) {
//				Field field = iterator.next();
//				String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
//				//String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
//				if (i == 0)
//					buf.putLine2("		int status = compare("+elementNameUncapped+"1.get"+fieldNameCapped+"(), "+elementNameUncapped+"2.get"+fieldNameCapped+"());");
//				else buf.putLine2("		status = compare("+elementNameUncapped+"1.get"+fieldNameCapped+"(), "+elementNameUncapped+"2.get"+fieldNameCapped+"());");
//				buf.putLine2("		if (status != 0)");
//				buf.putLine2("			return status;");
//			}
		}
//		buf.putLine2("		return 0;");
		buf.putLine2("	}");
		buf.putLine2("};");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	
//	protected ModelOperation createOperation_compareFields(Type element) {
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PROTECTED);
//		modelOperation.setName("compare");
//		modelOperation.addParameter(CodeUtil.createParameter("T", "value1"));
//		modelOperation.addParameter(CodeUtil.createParameter("T", "value2"));
//		modelOperation.setResultType("<T extends Comparable<T>> int");
//
//		Buf buf = new Buf();
//		buf.putLine2("if (value1 == null && value2 == null) return 0;");
//		//buf.putLine2("	return 0;");
//		buf.putLine2("if (value1 != null && value2 == null) return 1;");
//		//buf.putLine2("	return 1;");
//		buf.putLine2("if (value1 == null && value2 != null) return -1;");
//		//buf.putLine2("	return -1;");
//		buf.putLine2("int status = value1.compareTo(value2);");
//		buf.putLine2("return status;");
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}

//	protected ModelOperation createOperation_compareCollections(Type element) {
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PROTECTED);
//		modelOperation.setName("compare");
//		modelOperation.addParameter(CodeUtil.createParameter("Collection<T>", "collecton1"));
//		modelOperation.addParameter(CodeUtil.createParameter("Collection<T>", "collecton2"));
//		modelOperation.setResultType("<T extends Comparable<T>> int");
//
//		Buf buf = new Buf();
//		buf.putLine2("if (collecton1 == null && collecton2 == null) return 0;");
//		buf.putLine2("if (collecton1 != null && collecton2 == null) return 1;");
//		buf.putLine2("if (collecton1 == null && collecton2 != null) return -1;");
//		buf.putLine2("int status = compare(collecton1.size(), collecton2.size());");
//		buf.putLine2("int status = compare(collecton1.size(), collecton2.size());");
//		buf.putLine2("if (status != 0)");
//		buf.putLine2("	return status;");
//		buf.putLine2("Iterator<T> iterator1 = collecton1.iterator();");
//		buf.putLine2("Iterator<T> iterator2 = collecton2.iterator();");
//		buf.putLine2("while (iterator2.hasNext() && iterator2.hasNext()) {");
//		buf.putLine2("	T value1 = iterator1.next();");
//		buf.putLine2("	T value2 = iterator2.next();");
//		buf.putLine2("	status = value1.compareTo(value2);");
//		buf.putLine2("	if (status != 0)");
//		buf.putLine2("		return status;");
//		buf.putLine2("}");
//		buf.putLine2("return 0;");
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}

	
	protected ModelOperation createOperation_cloneRecord(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("clone");
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType(elementClassName);
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+" == null)");
		buf.putLine2("	return null;");
		buf.putLine2(elementClassName+" clone = create();");
		//buf.putLine2(elementClassName+" clone = ("+elementClassName+") SerializationUtils.clone("+elementNameUncapped+");");
		
		List<Field> fields = ElementUtil.getFields(element);
		Map<Field, Element> map = createFieldTypeMap(fields);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldType = field.getType();
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String structure = field.getStructure();

			if (field instanceof Attribute) {
				Enumeration enumeration = context.getEnumerationByType(fieldType);
				
				if (structure.equals("item")) {
					if (enumeration != null)
						buf.putLine2("clone.set"+fieldNameCapped+"("+elementNameUncapped+".get"+fieldNameCapped+"());");
					else buf.putLine2("clone.set"+fieldNameCapped+"(ObjectUtil.clone("+elementNameUncapped+".get"+fieldNameCapped+"()));");
					
				} else if (structure.equals("list")) {
					buf.putLine2("clone.set"+fieldNameCapped+"(ObjectUtil.clone("+elementNameUncapped+".get"+fieldNameCapped+"(), "+fieldClassName+".class));");
				} else if (structure.equals("set")) {
					buf.putLine2("clone.set"+fieldNameCapped+"(ObjectUtil.clone("+elementNameUncapped+".get"+fieldNameCapped+"(), "+fieldClassName+".class));");
				} else if (structure.equals("map")) {
					String keyType = field.getKey();
					String keyClassName = TypeUtil.getClassName(keyType);
					buf.putLine2("clone.set"+fieldNameCapped+"(ObjectUtil.clone("+elementNameUncapped+".get"+fieldNameCapped+"(), "+keyClassName+".class, "+fieldClassName+".class));");
				}
				continue;
			}
			
			if (field instanceof Reference) {
				Element elementByType = context.getElementByType(fieldType);
				
				if (structure.equals("item")) {
				} else if (structure.equals("list")) {
				} else if (structure.equals("set")) {
				} else if (structure.equals("map")) {
				}

				if (FieldUtil.isInverse(field))
					buf.putLine2("clone.set"+fieldNameCapped+"("+elementNameUncapped+".get"+fieldNameCapped+"());");
				else buf.putLine2("clone.set"+fieldNameCapped+"("+fieldClassName+"Util.clone("+elementNameUncapped+".get"+fieldNameCapped+"()));");
			}
		}

		buf.putLine2("return clone;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<ModelOperation> createOperation_cloneRecords(Element element) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		List<Reference> listReferences = new ArrayList<Reference>();
		List<Reference> setReferences = new ArrayList<Reference>();
		Map<String, Reference> mapReferences = new HashMap<String, Reference>();
		Collection<Element> allElements = context.getAllElements();
		Set<Reference> referencesFor = ElementUtil.getNonTransientReferences(allElements, element);
		Iterator<Reference> iterator = referencesFor.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String structure = reference.getStructure();
			if (structure.equals("list")) {
				listReferences.add(reference);
			} else if (structure.equals("set")) {
				setReferences.add(reference);
			} else if (structure.equals("map")) {
				String keyType = reference.getKey();
				mapReferences.put(keyType, reference);
			}
		}
		
		if (!listReferences.isEmpty())
			modelOperations.add(createOperation_cloneRecordList(element));
		if (!setReferences.isEmpty())
			modelOperations.add(createOperation_cloneRecordSet(element));
		
		Iterator<String> iterator2 = mapReferences.keySet().iterator();
		while (iterator2.hasNext()) {
			String keyType = iterator2.next();
			//Reference reference = mapReferences.get(keyType);
			modelOperations.add(createOperation_cloneRecordMap(keyType, element));
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_cloneRecordList(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("clone");
		modelOperation.addParameter(createParameter("List<"+elementClassName+">", elementNameUncapped+"List"));
		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addImportedClass("java.util.List");
		modelOperation.addImportedClass("java.util.ArrayList");
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+"List == null)");
		buf.putLine2("	return null;");
		buf.putLine2("List<"+elementClassName+"> newList = new ArrayList<"+elementClassName+">();");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	"+elementClassName+" clone = clone("+elementNameUncapped+");");
		buf.putLine2("	newList.add(clone);");
		buf.putLine2("}");
		buf.putLine2("return newList;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_cloneRecordSet(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("clone");
		modelOperation.addParameter(createParameter("Set<"+elementClassName+">", elementNameUncapped+"Set"));
		modelOperation.setResultType("Set<"+elementClassName+">");
		modelOperation.addImportedClass("java.util.Set");
		modelOperation.addImportedClass("java.util.HashSet");
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+"Set == null)");
		buf.putLine2("	return null;");
		buf.putLine2("Set<"+elementClassName+"> newSet = new HashSet<"+elementClassName+">();");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"Set.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	"+elementClassName+" clone = clone("+elementNameUncapped+");");
		buf.putLine2("	newSet.add(clone);");
		buf.putLine2("}");
		buf.putLine2("return newSet;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_cloneRecordMap(String keyType, Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String keyElementClassName = TypeUtil.getClassName(keyType);
		Element keyElement = context.getElementByType(keyType);
		Enumeration keyEnumeration = context.getEnumerationByType(keyType);
		String keyPackageName = TypeUtil.getPackageName(keyType);
		String keyClassName = TypeUtil.getClassName(keyType);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("clone");
		modelOperation.addParameter(createParameter("Map<"+keyElementClassName+", "+elementClassName+">", elementNameUncapped+"Map"));
		modelOperation.setResultType("Map<"+keyElementClassName+", "+elementClassName+">");
		modelOperation.addImportedClass("java.util.Map");
		modelOperation.addImportedClass("java.util.HashMap");
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+"Map == null)");
		buf.putLine2("	return null;");
		buf.putLine2("Map<"+keyElementClassName+", "+elementClassName+"> newMap = new HashMap<"+keyElementClassName+", "+elementClassName+">();");
		buf.putLine2("Iterator<"+keyElementClassName+"> iterator = "+elementNameUncapped+"Map.keySet().iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+keyElementClassName+" key = iterator.next();");
		
		if (keyEnumeration != null) {
			modelOperation.addImportedClass(keyPackageName + "." + keyClassName);
			//buf.putLine2("	"+keyElementClassName+" keyClone = key;");
			
		} else if (keyElement != null) {
			modelOperation.addImportedClass(keyPackageName + "." + keyClassName);
			modelOperation.addImportedClass(keyPackageName + ".util." + keyClassName + "Util");	
			buf.putLine2("	"+keyElementClassName+" keyClone = clone(key);");
			//buf.putLine2("	"+keyElementClassName+" keyClone = "+keyElementClassName+"Util.clone(key);");
			
		} else {
			buf.putLine2("	"+keyElementClassName+" keyClone = ObjectUtil.clone(key);");
		}
				
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = "+elementNameUncapped+"Map.get(key);");
		buf.putLine2("	"+elementClassName+" clone = clone("+elementNameUncapped+");");
		if (keyEnumeration != null)
			buf.putLine2("	newMap.put(key, clone);");
		else buf.putLine2("	newMap.put(keyClone, clone);");
		buf.putLine2("}");
		buf.putLine2("return newMap;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_cloneRecordKey(Element element) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("clone");
		modelOperation.addParameter(createParameter(elementClassName+"Key", elementNameUncapped+"Key"));
		modelOperation.setResultType(elementClassName+"Key");
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementNameUncapped+"Key == null)");
		buf.putLine2("	return null;");
		buf.putLine2(elementClassName+"Key clone = new "+elementClassName+"Key();");
		//buf.putLine2(elementClassName+" clone = ("+elementClassName+") SerializationUtils.clone("+elementNameUncapped+");");
		
		String keyType = element.getType()+"Key";
		String keyElementClassName = TypeUtil.getClassName(keyType);
		Element keyElement = context.getElementByType(keyType);
		
		List<Field> fields = ElementUtil.getFields(keyElement);
		Map<Field, Element> map = createFieldTypeMap(fields);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldType = field.getType();
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String structure = field.getStructure();

			if (field instanceof Attribute) {
				Enumeration enumeration = context.getEnumerationByType(fieldType);
				
				if (structure.equals("item")) {
					if (enumeration != null)
						buf.putLine2("clone.set"+fieldNameCapped+"("+elementNameUncapped+"Key.get"+fieldNameCapped+"());");
					else buf.putLine2("clone.set"+fieldNameCapped+"(ObjectUtil.clone("+elementNameUncapped+"Key.get"+fieldNameCapped+"()));");
				}
				continue;
			}
			
			if (field instanceof Reference) {
				if (structure.equals("item")) {
					if (FieldUtil.isInverse(field))
						buf.putLine2("clone.set"+fieldNameCapped+"("+elementNameUncapped+"Key.get"+fieldNameCapped+"());");
					else buf.putLine2("clone.set"+fieldNameCapped+"("+fieldClassName+"Util.clone("+elementNameUncapped+"Key.get"+fieldNameCapped+"()));");
				}
			}
		}

		buf.putLine2("return clone;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
