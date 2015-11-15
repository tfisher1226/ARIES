package aries.codegen;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.data.DataLayerHelper;
import nam.model.Attribute;
import nam.model.Cache;
import nam.model.Component;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Fault;
import nam.model.Field;
import nam.model.Information;
import nam.model.Input;
import nam.model.Literal;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Output;
import nam.model.Parameter;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Reference;
import nam.model.Result;
import nam.model.Service;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.AriesModelBuilder;
import aries.generation.AriesModelUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelEnum;
import aries.generation.model.ModelField;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelLiteral;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelReference;
import aries.generation.model.ModelUnit;


public abstract class AbstractBeanBuilder implements BeanBuilder {
	
	protected Log log = LogFactory.getLog(getClass());
	
	protected GenerationContext context;

	protected AriesModelBuilder builder;
	
	public AriesModelUtil util;
	
	protected String packageName;

	protected String className;

	protected String typeName;

	protected String beanName;

	protected String rootName;

	protected String baseName;

	protected Namespace namespace;

	protected ModelUnit modelUnit;

	
	public AbstractBeanBuilder() {
	}
	
	public AbstractBeanBuilder(GenerationContext context) {
		this.context = context;
		initialize();
	}

	private void initialize() {
		builder = new AriesModelBuilder(context);
		util = new AriesModelUtil(context);
		Class<?> thisClass = this.getClass();
		boolean isCacheRelated = DataLayerHelper.isCacheUnitRelated(thisClass);
		boolean isStateRelated = DataLayerHelper.isStateRelated(thisClass);
		util.setCacheUnitRelated(isCacheRelated);
		util.setStateRelated(isStateRelated);

	}

//	public GenerationContext getContext() {
//		return context;
//	}

//	public void setContext(GenerationContext context) {
//		this.context = context;
//	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public Element getContainingElement(Element element) {
		return ModelLayerHelper.getContainer(context, element);
	}
	
	protected String getBeanContextName(ModelClass modelClass) {
		return getBeanContextName(modelClass, "");
	}
	
	protected String getBeanContextName(ModelClass modelClass, String postfix) {
		String elementPackageName = modelClass.getPackageName();
		String elementName = NameUtil.uncapName(modelClass.getName()) + postfix;
		if (context.isEnabled("useQualifiedContextNames")) {
			String contextNamePrefix = NameUtil.getQualifiedContextNamePrefix(elementPackageName, 3);
			return contextNamePrefix + "." + elementName;
		}
		return elementName;
	}

	protected String getBeanContextName(Namespace namespace, Field field) {
		return getBeanContextName(namespace, field, "");
	}
	
	protected String getBeanContextName(Namespace namespace, Field field, String postfix) {
		String fieldPackageName = DataLayerHelper.getMapperPackageName(field);
		String fieldType = NameUtil.uncapName(TypeUtil.getClassName(field.getType())) + "Mapper";
		if (context.isEnabled("useQualifiedContextNames")) {
			String contextNamePrefix = NameUtil.getQualifiedContextNamePrefix(fieldPackageName, 3);
			return contextNamePrefix + "." + fieldType;
		}
		return fieldType;
	}


	public boolean isImportRequiredForField(Element element, Field field) {
		if (field instanceof Reference == false)
			return false;
		if (FieldUtil.isInverse(field))
			return false;
		String fieldType = field.getType();
		String className = TypeUtil.getClassName(fieldType);
		if (ClassUtil.isJavaPrimitiveType(className))
			return false;
		return true;
	}

	

	
	/*
	 * Class creation
	 * --------------
	 */

	public Collection<ModelClass> buildClasses(List<Namespace> namespaces) throws Exception {
		return buildClasses(context.getProject(), namespaces); 
	}
	
	public Collection<ModelClass> buildClasses(Project project, Module module) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Information information = module.getInformation();
		if (information != null)
			modelClasses.addAll(buildClasses(project, information));
		return modelClasses;
	}

	public Collection<ModelClass> buildClasses(Project project, Information information) throws Exception {
		return buildClasses(project, information.getNamespaces());
	}

	public Collection<ModelClass> buildClasses(Project project, Persistence persistence) throws Exception {
		return buildClasses(project, context.getNamespaceByUri(persistence.getNamespace()));
	}
	
	public Collection<ModelClass> buildClasses(Project project, List<Namespace> namespaces) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = (Namespace) iterator.next();
			modelClasses.addAll(buildClasses(project, namespace));
		}
		return modelClasses;
	}

	public Collection<ModelClass> buildClasses(Project project, Namespace namespace) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		if (namespace == null)
			return modelClasses;
		List<Type> types = new ArrayList<Type>();
		types.addAll(NamespaceUtil.getElements(namespace));
		types.addAll(NamespaceUtil.getEnumerations(namespace));
		Iterator<Type> iterator = types.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (ElementUtil.isAbstract(type))
				continue;
			if (ElementUtil.isTransient(type))
				continue;
			String className = TypeUtil.getClassName(type.getType());
			if (className.endsWith("Message") && !className.equals("EmailMessage"))
				continue;
			//if (className.endsWith("Event"))
			//	continue;
			if (className.endsWith("Exception"))
				continue;
			
//			if (!ElementUtil.isElement(type))
//				continue;
			
//			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(type);
//			Service service = context.getServiceByName(elementNameUncapped+"Service");
//			if (service == null)
//				continue;
			
			Element keyElement = context.getElementByType(type.getType()+"Key");
			Element criteriaElement = context.getElementByType(type.getType()+"Criteria");
			
			addClasses(modelClasses, buildClasses(type));
			if (keyElement != null)
				addClass(modelClasses, buildClass(keyElement));
			if (criteriaElement != null)
				addClass(modelClasses, buildClass(criteriaElement));
		}
		return modelClasses;
	}
	
	public Collection<ModelClass> buildClasses(Type type) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		ModelClass modelClass = buildClass(type);
		if (modelClass != null)
			modelClasses.add(modelClass);
		return modelClasses;
	}
	
	protected void addClass(Collection<ModelClass> modelClasses, ModelClass modelClass) {
		if (modelClass != null)
			modelClasses.add(modelClass);
	}

	protected void addClasses(Collection<ModelClass> modelClasses, Collection<ModelClass> modelClassesToAdd) {
		if (modelClassesToAdd != null)
			modelClasses.addAll(modelClassesToAdd);
	}

	protected ModelClass buildClass(Type type) throws Exception {
		//nothing by default
		return null;
	}


	public ModelClass createModelClass(String namespace, String packageName, String className) {
		String classNameUncapped = NameUtil.uncapName(className);
		String classType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, classNameUncapped);

		ModelClass modelClass = new ModelClass();
		modelClass.setType(classType);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(classNameUncapped);
		modelClass.setNamespace(namespace);
		this.modelUnit = modelClass;
		return modelClass; 
	}


	/*
	 * Class initialization
	 * --------------------
	 */
	
	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		//createClassAnnotations(modelClass);
		initializeStaticFields(modelClass);
		initializeStaticMethods(modelClass);
		initializeInstanceFields(modelClass, element);
		initializeInstanceMethods(modelClass);
		initializeClassAnnotations(modelClass);
		initializeClassConstructors(modelClass);
	}

//	protected void createClassAnnotations(ModelClass modelClass) {
//	}

	protected void initializeClassAnnotations(ModelClass modelClass) throws Exception {
		List<ModelAnnotation> classAnnotations = AnnotationUtil.createModelAnnotations(context.getElement().getAnnotations());
		modelClass.setClassAnnotations(classAnnotations);
	}

	protected void initializeClassConstructors(ModelClass modelClass) {
		modelClass.addInstanceConstructor(createConstructor());
	}
	
	protected ModelConstructor createConstructor() {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		return modelConstructor;
	}

	protected void initializeStaticFields(ModelClass modelClass) throws Exception {
		boolean isEnum = modelClass instanceof ModelEnum;
		if (!isEnum) {
			CodeUtil.addSerialVersionUIDField(modelClass);
		}
	}
	
	protected void initializeStaticMethods(ModelClass modelClass) throws Exception {
		//none yet
	}

	protected void initializeInstanceFields(ModelEnum modelEnum, Enumeration enumeration, boolean generateLabelVersion) throws Exception {
		List<Literal> literals = ElementUtil.getLiterals(enumeration);
		Iterator<Literal> iterator = literals.iterator();
		while (iterator.hasNext()) {
			Literal literal = iterator.next();
			initializeInstanceField(modelEnum, literal, generateLabelVersion);
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, List<Element> elements) throws Exception {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
				initializeInstanceFields(modelClass, element);
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
//			if (field.getType().equals("{http://www.w3.org/2001/XMLSchema}dateTime"))
//				System.out.println();
			if (field instanceof Attribute)
				initializeInstanceField(modelClass, element, (Attribute) field);
			else if (field instanceof Reference)
				initializeInstanceField(modelClass, element, (Reference) field);
		}
		//only add this for base level superTypes that are roots
		if (element.getExtends() == null && ElementUtil.isRoot(element)) {
			initializeInstanceAttribute_ref(modelClass, element);
		}
	}

	protected void initializeInstanceAttribute_ref(ModelClass modelClass, Element element) throws Exception {
		Attribute attribute = new Attribute();
		attribute.setName("ref");
		attribute.setLabel("Ref");
		attribute.setStructure("item");
		attribute.setType(TypeUtil.getDefaultType("String"));
		attribute.setNamespace(element.getNamespace());
		ModelAttribute modelAttribute = createInstanceField(element, attribute);
		initializeInstanceFieldName(modelClass, modelAttribute, element, attribute);
		initializeInstanceFieldType(modelClass, modelAttribute, element, attribute);
		//initializeFieldAnnotations(modelClass, modelAttribute, element, attribute);
		initializeAttributeAnnotations(modelClass, modelAttribute, element, attribute);
		initializeImportedClasses(modelClass, modelAttribute, element, attribute);
		modelClass.addInstanceAttribute(modelAttribute);
	}
	
	protected void initializeInstanceField(ModelClass modelClass, Element element, Attribute attribute) throws Exception {
		ModelAttribute modelAttribute = createInstanceField(element, attribute);
		initializeInstanceFieldName(modelClass, modelAttribute, element, attribute);
		initializeInstanceFieldType(modelClass, modelAttribute, element, attribute);
		//initializeFieldAnnotations(modelClass, modelAttribute, element, attribute);
		initializeAttributeAnnotations(modelClass, modelAttribute, element, attribute);
		initializeImportedClasses(modelClass, modelAttribute, element, attribute);
		modelClass.addInstanceAttribute(modelAttribute);
	}
	
	protected ModelAttribute createInstanceField(Element element, Attribute attribute) throws Exception {
		String attributeType = attribute.getType();
		String structure = attribute.getStructure();

		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setName(attribute.getName());
		modelAttribute.setType(attribute.getType());
		modelAttribute.setPackageName(TypeUtil.getPackageName(attributeType));
		modelAttribute.setClassName(TypeUtil.getClassName(attributeType));
		modelAttribute.setKeyEnabled(FieldUtil.isUseForEquals(attribute));
		modelAttribute.setModifiers(Modifier.PRIVATE);
		modelAttribute.setGenerateAddMethod(true);
		modelAttribute.setGenerateRemoveMethod(true);
		modelAttribute.setGenerateClearMethod(true);
		modelAttribute.setUnique(attribute.getUnique());
		modelAttribute.setRequired(attribute.getRequired());
		modelAttribute.setNullable(attribute.getNullable());
		modelAttribute.setStructure(attribute.getStructure());
		modelAttribute.setDefault(attribute.getDefault());
		modelAttribute.setMultiplicity(attribute.getMaxOccurs());
		//we currently using this flag for additional boolean field generation
		modelAttribute.setSynchronizationEnabled(!structure.equals("item") || modelAttribute.getClassName().equalsIgnoreCase("boolean"));
		if (structure.equals("map")) {
			String key = attribute.getKey();
			modelAttribute.setKeyType(key);
			modelAttribute.setKeyClassName(TypeUtil.getClassName(key));
			modelAttribute.setKeyPackageName(TypeUtil.getPackageName(key));
		}
//		if (!CodeGenUtil.isJavaDefaultType(attribute.getType()))
//			modelClass.addImportedClass(modelAttribute.getPackageName()+"."+modelAttribute.getClassName());
//		if (attribute.getType().endsWith("ActivityGroup"))
//			System.out.println();
		return modelAttribute;
	}

	protected void initializeInstanceField(ModelClass modelClass, Element element, Reference reference) throws Exception {
		ModelReference modelReference = createInstanceField(element, reference);
		initializeInstanceFieldName(modelClass, modelReference, element, reference);
		initializeInstanceFieldType(modelClass, modelReference, element, reference);
		//initializeFieldAnnotations(modelClass, modelReference, element, reference);
		initializeReferenceAnnotations(modelClass, modelReference, element, reference);
		initializeImportedClasses(modelClass, modelReference, element, reference);
		modelClass.addInstanceReference(modelReference);
	}
	
	protected ModelReference createInstanceField(Element element, Reference reference) throws Exception {
		String referenceType = reference.getType();
		String structure = reference.getStructure();
		
//		if (TypeUtil.getClassName(referenceType).contains("byte[]"))
//			System.out.println();

//		if (reference.getName().equalsIgnoreCase("informationsAndPersistencesAndServices"))
//			System.out.println();
		
		ModelReference modelReference = new ModelReference();
		modelReference.setPackageName(TypeUtil.getPackageName(referenceType));
		modelReference.setClassName(TypeUtil.getClassName(referenceType));
		modelReference.setName(reference.getName());
		modelReference.setType(referenceType);
		modelReference.setKeyEnabled(FieldUtil.isUseForEquals(reference));
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setGenerateAddMethod(true);
		modelReference.setGenerateRemoveMethod(true);
		modelReference.setGenerateClearMethod(true);
		modelReference.setUnique(reference.getUnique());
		modelReference.setRequired(reference.getRequired());
		modelReference.setNullable(reference.getNullable());
		modelReference.setContained(reference.getContained());
		modelReference.setInverse(reference.getInverse());
		modelReference.setStructure(reference.getStructure());
		modelReference.setDefault(reference.getDefault());
		modelReference.setMultiplicity(reference.getMaxOccurs());
		modelReference.setSynchronizationEnabled(!structure.equals("map"));
		if (structure.equals("map")) {
			String key = reference.getKey();
			modelReference.setKeyType(key);
			modelReference.setKeyClassName(TypeUtil.getClassName(key));
			modelReference.setKeyPackageName(TypeUtil.getPackageName(key));
		}
		List<String> itemTypes = reference.getItemTypes();
		Iterator<String> iterator = itemTypes.iterator();
		while (iterator.hasNext()) {
			String itemType = iterator.next();
			modelReference.addAcceptedType(itemType);
		}
//		if (!CodeGenUtil.isJavaDefaultType(reference.getType()))
//			modelClass.addImportedClass(modelReference.getPackageName()+"."+modelReference.getClassName());
		return modelReference;
	}

	protected void initializeInstanceField(ModelEnum modelEnum, Literal literal, boolean generateLabelVersion) throws Exception {
		//if (modelEnum.getName().equals("PositionName"))
		//	System.out.println();
		ModelLiteral modelLiteral = new ModelLiteral();
		modelLiteral.setName(literal.getName());
		if (generateLabelVersion) {
			modelLiteral.setText(literal.getLabel());
			modelLiteral.addAnnotation(AnnotationUtil.createXmlEnumValue(modelLiteral));
		}
		modelLiteral.setValue(literal.getValue());
		modelEnum.addLiteral(modelLiteral);
	}
	
	protected void initializeInstanceFieldName(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		String fieldName = field.getName();
		modelField.setName(fieldName);
	}

	protected void initializeInstanceFieldType(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		String fieldType = field.getType();
		modelField.setType(fieldType);
		modelField.setPackageName(TypeUtil.getPackageName(fieldType));
		modelField.setClassName(TypeUtil.getClassName(fieldType));
		String keyType = field.getKey();
		if (keyType == null) {
			modelField.setKeyPackageName(TypeUtil.getPackageName(keyType));
			modelField.setKeyClassName(TypeUtil.getClassName(keyType));
		}
	}
	
//	protected void initializeInstanceFieldType(ModelClass modelClass, ModelField modelField, String typeName) throws Exception {
//		//String javaClass = ClassUtil.convertTypeToJavaClass(fieldType);
//		modelField.setPackageName(TypeUtil.getPackageName(typeName));
//		modelField.setClassName(TypeUtil.getClassName(typeName));
//	}
	
	protected void initializeImportedClasses(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		String packageName = modelField.getPackageName();
		String className = modelField.getClassName();
		if (StringUtils.isEmpty(packageName))
			return;
		if (context.getModule() != null && packageName.startsWith(context.getModule().getGroupId()))
			return;
		if (!StringUtils.isEmpty(field.getStructure()))
			return;
		if (TypeUtil.isImportedClassRequired(packageName, className))
			modelClass.addImportedClass(packageName+"."+className);
		modelClass.addImportedClass(packageName+"."+className+"Entity");
	}

	protected void addImportedClassForHelper(ModelUnit modelUnit, Type type) {
		String uri = TypeUtil.getNamespace(type.getType());
		Namespace namespace = context.getNamespaceByUri(uri);
		String helperQualifiedName = ModelLayerHelper.getModelHelperQualifiedName(namespace);
		//if (helperQualifiedName == null)
		//	System.out.println();
		modelUnit.addImportedClass(helperQualifiedName);
	}
	
	protected void addImportedClassForFixture(ModelUnit modelUnit, Type type) {
		String uri = TypeUtil.getNamespace(type.getType());
		Namespace namespace = context.getNamespaceByUri(uri);
		String fixtureQualifiedName = ModelLayerHelper.getFixtureQualifiedName(namespace);
		//if (fixtureQualifiedName == null)
		//	System.out.println();
		modelUnit.addImportedClass(fixtureQualifiedName);
	}
	

	protected void initializeInstanceMethods(ModelClass modelClass) throws Exception {
		//nothing by default
	}

	protected void initializeFieldAnnotations(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		//nothing by default
	}
	
	protected void initializeAttributeAnnotations(ModelClass modelClass, ModelAttribute modelAttribute, Element element, Attribute attribute) throws Exception {
		initializeFieldAnnotations(modelClass, modelAttribute, element, attribute);
	}

	protected void initializeReferenceAnnotations(ModelClass modelClass, ModelReference modelReference, Element element, Reference reference) throws Exception {
		initializeFieldAnnotations(modelClass, modelReference, element, reference);
	}

	protected ModelAttribute createAttribute_Id() {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE);
		modelAttribute.setPackageName("java.lang");
		modelAttribute.setClassName("Long");
		modelAttribute.setName("id");
		return modelAttribute;
	}

	protected ModelAttribute createAttribute_Ref() {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE);
		modelAttribute.setPackageName("java.lang");
		modelAttribute.setClassName("Long");
		modelAttribute.setName("id");
		return modelAttribute;
	}
	
	protected ModelAttribute createAttribute_Reason() {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE);
		modelAttribute.setPackageName("java.lang");
		modelAttribute.setClassName("String");
		modelAttribute.setName("reason");
		return modelAttribute;
	}
	

	/*
	 * Operation factory methods
	 * -------------------------
	 */

	protected ModelOperation createMainOperation() {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC+Modifier.STATIC);
		modelOperation.setName("main");
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setClassName("String[]");
		modelParameter.setName("args");
		modelOperation.addParameter(modelParameter);
		modelOperation.addException("Exception");
		return modelOperation;
	}

	protected ModelOperation createOperation_Equals(ModelClass modelClass, Element element) {
		//TODO include references as keys (or parts of keys)?
		//String typeName = NameUtil.getClassName(element.getType());
		String typeName = modelClass.getClassName();
		ModelOperation operation = new ModelOperation();
		operation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		operation.setModifiers(Modifier.PUBLIC);
		operation.setResultType("boolean");
		operation.setName("equals");
		operation.addParameter(CodeUtil.createParameter("Object", "object"));
		
		Buf buf = new Buf();
		buf.putLine2("if (object == null)");
		buf.putLine2("	return false;");
		buf.putLine2("if (!object.getClass().isAssignableFrom(this.getClass()))");
		buf.putLine2("	return false;");
		buf.putLine2(typeName+" other = ("+typeName+") object;");
		
		if (ElementUtil.hasIdField(element)) {
			buf.putLine2("if (id != null)");
			buf.putLine2("	return id.equals(other.id);");
		}
		
		buf.putLine2("int status = compareTo(other);");
		buf.putLine2("return status == 0;");
		
//		List<ModelField> equalsKey = context.getEqualsKey(modelClass);
//		if (equalsKey.size() > 0) {
//			buf.put2("if (");
//			Iterator<ModelField> iterator = equalsKey.iterator();
//			for (int i=0; iterator.hasNext(); i++) {
//				ModelField field = iterator.next();
//				String name = NameUtil.capName(field.getName());
//				if (i > 0)
//					buf.put3("");
//				buf.put("this.get"+name+"() == null || other.get"+name+"() == null");
//				if (i+1 < equalsKey.size())
//					buf.putLine(" || ");
//				else buf.putLine(")");
//			}
//			buf.putLine3("return this == other;");
//			//buf.putLine2("}");
//
//			buf.put2("if (");
//			iterator = equalsKey.iterator();
//			for (int i=0; iterator.hasNext(); i++) {
//				ModelField field = iterator.next();
//				String name = NameUtil.capName(field.getName());
//				if (i > 0)
//					buf.put3("");
//				if (CodeGenUtil.isJavaPrimitiveType(field.getClassName()))
//					buf.put("this.get"+name+"() == other.get"+name+"())");
//				else buf.put("this.get"+name+"().equals(other.get"+name+"())");
//				if (i+1 < equalsKey.size())
//					buf.putLine(" && ");
//				else buf.putLine(")");
//			}
//			if (equalsKey.size() > 1)
//				buf.put1("");
//			buf.putLine3("return true;");
//			//buf.putLine2("}");
//		}
//		buf.putLine2("return this == object;");
		
		operation.addInitialSource(buf.get());
		return operation;
	}

	protected ModelOperation createOperation_Hashcode(ModelClass modelClass, Element element) {
		List<ModelField> key = context.getIdKey(modelClass);
		ModelOperation operation = new ModelOperation();
		operation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		operation.setModifiers(Modifier.PUBLIC);
		operation.setResultType("int");
		operation.setName("hashCode");
		
		Buf buf = new Buf();
		if (ElementUtil.hasIdField(element)) {
			buf.putLine2("if (id != null)");
			buf.putLine2("	return id.hashCode();");
		}
		
		buf.putLine2("int hashCode = 0;");
		List<ModelField> equalsKey = context.getEqualityFields(modelClass);
		Iterator<ModelField> iterator = equalsKey.iterator();
		while (iterator.hasNext()) {
			ModelField modelField = iterator.next();
			String name = NameUtil.uncapName(modelField.getName());
			buf.putLine2("if ("+name+" != null)");
			buf.putLine2("	hashCode += "+name+".hashCode();");
		}
		
		buf.putLine2("if (hashCode == 0)");
		buf.putLine2("	return super.hashCode();");
		buf.putLine2("return hashCode;");
		
		operation.addInitialSource(buf.get());
		return operation;
	}
	
//	protected ModelOperation createOperation_Hashcode(ModelClass modelClass, Element element) {
//		List<ModelField> key = context.getIdKey(modelClass);
//		ModelOperation operation = new ModelOperation();
//		operation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
//		operation.setModifiers(Modifier.PUBLIC);
//		operation.setResultType("int");
//		operation.setName("hashCode");
//		
//		Buf buf = new Buf();
//		if (ElementUtil.idFieldExists(element)) {
//			buf.putLine2("if (getId() != null)");
//			buf.putLine2("	return getId().hashCode();");
//		}
//		
//		Collection<Field> keyFields = ElementUtil.getKeyFields(element);
//		Iterator<Field> iterator = keyFields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
//			buf.putLine2("if (get"+fieldNameCapped+"() != null)");
//			buf.putLine2("	return get"+fieldNameCapped+"().hashCode();");
//		}
//		
//		if (key.size() > 0) {
//			ModelField field = key.get(0);
//			String name = NameUtil.capName(field.getName());
//			buf.putLine2("if (get"+name+"() != null)");
//			buf.putLine2("	return get"+name+"().hashCode();");
//		}
//		buf.putLine2("return super.hashCode();");
//		
//		operation.addInitialSource(buf.get());
//		return operation;
//	}
	

//	protected ModelOperation createOperation_ToString() {
//		ModelOperation operation = new ModelOperation();
//		operation.setModifiers(Modifier.PUBLIC);
//		operation.setResultType("String");
//		operation.setName("toString");
//		operation.addInitialSource("if (getId() != null)");
//		operation.addInitialSource("	return getId();");
//		operation.addInitialSource("return \"[no-name]\";");
//		return operation;
//	}


	/*
	 * Operation factory methods (for enums)
	 * -------------------------------------
	 */
	
	protected ModelOperation createOperation_Value(ModelEnum modelEnum) {
		ModelOperation operation = new ModelOperation();
		operation.setModifiers(Modifier.PUBLIC);
		operation.setResultType("String");
		operation.setName("value");
		Buf buf = new Buf();
		buf.putLine2("return name();");
		operation.addInitialSource(buf.get());
		return operation;
	}
	
	protected ModelOperation createOperation_FromValue(ModelEnum modelEnum) {
		ModelOperation operation = new ModelOperation();
		operation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		operation.setResultType(modelEnum.getClassName());
		operation.setName("fromValue");
		operation.addParameter(CodeUtil.createParameter("java.lang.String", "name"));
		Buf buf = new Buf();
		buf.putLine2("return valueOf(name);");
		operation.addInitialSource(buf.get());
		return operation;
	}

	
	/*
	 * Parameter factory methods
	 * -------------------------
	 */
	
	protected ModelParameter createParameter(Parameter parameter) {
		return createParameter(parameter.getType(), parameter.getName());
	}
	
	protected ModelParameter createParameter(String type, String name) {
		return createParameter(type, name, "item");
	}

	protected ModelParameter createParameter(String type, String name, String construct) {
		return createParameter(NameUtil.getPackageName(type), NameUtil.getClassName(type), name, construct);
	}

	protected ModelParameter createParameter(String packageName, String className, String name, String construct) {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(name);
		modelParameter.setPackageName(packageName);
		if (StringUtils.isEmpty(packageName))
			modelParameter.setClassName(className);
		else modelParameter.setClassName(packageName+"."+className);
		modelParameter.setConstruct(construct);
		if (construct.equalsIgnoreCase("map")) {
			modelParameter.setKeyPackageName("java.lang");
			modelParameter.setKeyClassName("Object");
		}
//		if (construct == null)
//			System.out.println();
//		if (construct.startsWith("List<"))
//			System.out.println();
//		if (construct.startsWith("Set<"))
//			System.out.println();
//		if (construct.startsWith("Map<"))
//			System.out.println();
		return modelParameter;
	}
	
	
	/*
	 * Component Helper methods
	 * ------------------------
	 */

	public Component findComponent(Input input) {
		return findComponent(input.getType(), input.getName(), false);
	}

	public Component findComponent(Input input, boolean required) {
		return findComponent(input.getType(), input.getName(), required);
	}

	public Component findComponent(Output output) {
		return findComponent(output.getType(), output.getName(), false);
	}

	public Component findComponent(Output result, boolean required) {
		return findComponent(result.getType(), result.getName(), required);
	}

	public Component findComponent(String type, String name, boolean required) {
		Component element = null;
		if (!StringUtils.isEmpty(name)) {
			element = context.getComponentByName(name);
			if (element == null)
				element = context.getComponentByType(type);
			if (required) 
				Assert.notNull(element, "Component not found for name: "+name);
		} else {
			element = context.getComponentByType(type);
			if (required) 
				Assert.notNull(element, "Component not found for type: "+type);
		}
		return element;
	}

	public Component findComponentByName(String name) {
		return findComponentByName(name, true);
	}
	
	public Component findComponentByName(String name, boolean required) {
		Assert.notEmpty(name, "Name must be specified");
		Component element = context.getComponentByName(name);
		if (required) 
			Assert.notNull(element, "Component not found for name: "+name);
		return element;
	}

	public Component findComponentByType(String type, boolean required) {
		Assert.notEmpty(type, "Type must be specified");
		Component element = context.getComponentByType(type);
		if (required) 
			Assert.notNull(element, "Component not found for type: "+type);
		return element;
	}

	public String findComponentType(Input input) {
		String elementType = null;
		Component element = findComponent(input);
		if (element != null)
			elementType = element.getType();
		if (elementType == null)
			elementType = input.getType();
		elementType = NameUtil.getClassName(elementType);
		return elementType;
	}

	public String findComponentName(Input input) {
		String elementName = null;
		Component element = findComponent(input);
		if (element != null)
			elementName = element.getName();
		if (elementName == null)
			elementName = input.getName();
		if (elementName == null)
			elementName = NameUtil.uncapName(input.getType());
		return elementName;
	}

	public String findComponentType(Output output) {
		String elementType = null;
		Component element = findComponent(output);
		if (element != null)
			elementType = element.getType();
		if (elementType == null)
			elementType = output.getType();
		elementType = NameUtil.getClassName(elementType);
		return elementType;
	}

	

	protected void addImportedClasses(ModelClass modelClass, Operation operation) {
		addImportedClassesForParameters(modelClass, operation.getParameters());
		addImportedClassesForFaults(modelClass, operation.getFaults());
		addImportedClassesForResults(modelClass, operation.getResults());
	}

	protected void addImportedClasses(ModelInterface modelInterface, Operation operation) {
		addImportedClassesForParameters(modelInterface, operation.getParameters());
		addImportedClassesForFaults(modelInterface, operation.getFaults());
		addImportedClassesForResults(modelInterface, operation.getResults());
	}
	
	protected void addImportedClassesForParameters(ModelUnit modelUnit, List<Parameter> parameters) {
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			addImportedClass(modelUnit, parameter.getType());
		}
	}

	protected void addImportedClassesForFaults(ModelUnit modelUnit, List<Fault> faults) {
		Iterator<Fault> iterator = faults.iterator();
		while (iterator.hasNext()) {
			Fault fault = iterator.next();
			addImportedClass(modelUnit, fault.getType());
		}
	}
	
	protected void addImportedClassesForResults(ModelUnit modelUnit, List<Result> results) {
		Iterator<Result> iterator = results.iterator();
		while (iterator.hasNext()) {
			Result result = iterator.next();
			addImportedClass(modelUnit, result.getType());
		}
	}

	protected void addImportedClass(ModelUnit modelUnit, String typeName) {
//		if (typeName.endsWith("Key"))
//			System.out.println();
		Assert.notNull(typeName, "TypeName must be specified");
		String qualifiedName = TypeUtil.getQualifiedName(typeName);
		if (TypeUtil.isImportedClassRequired(qualifiedName))
			modelUnit.addImportedClass(qualifiedName);
	}

	protected void addImportedInterfaceForStructure(ModelUnit modelUnit, Field field) {
		addImportedInterfaceForStructure(modelUnit, field.getStructure());
	}
	
	protected void addImportedInterfaceForStructure(ModelUnit modelUnit, ModelField modelField) {
		addImportedInterfaceForStructure(modelUnit, modelField.getStructure());
	}
	
	protected void addImportedInterfaceForStructure(ModelUnit modelUnit, String structure) {
		if (structure.equalsIgnoreCase("list")) {
			modelUnit.addImportedClass("java.util.List");
		} else if (structure.equalsIgnoreCase("set")) {
			modelUnit.addImportedClass("java.util.Set");
		} else if (structure.equalsIgnoreCase("map")) {
			modelUnit.addImportedClass("java.util.Map");
		}
	}


	protected void addInstanceOperation(List<ModelOperation> modelOperationList, ModelOperation modelOperation) {
		if (modelOperation != null)
			modelOperationList.add(modelOperation);
	}
	
	protected void addInstanceOperation(ModelUnit modelUnit, ModelOperation modelOperation) {
		if (modelOperation != null)
			modelUnit.addInstanceOperation(modelOperation);
	}
	

//	protected List<Field> getItemsFromCache(Process process) throws Exception {
//		return getItemsFromCache(process.getCache(), false);
//	}
//
//	protected List<Field> getItemsFromCacheSorted(Process process) throws Exception {
//		return getItemsFromCache(process.getCache(), true);
//	}
//
//	protected List<Field> getItemsFromCache(Service service) throws Exception {
//		return getItemsFromCache(service.getCache(), false);
//	}
//
//	protected List<Field> getItemsFromCacheSorted(Service service) throws Exception {
//		return getItemsFromCache(service.getCache(), true);
//	}

	protected List<Field> getItemsFromCache(Cache cache) throws Exception {
		return getItemsFromCache(cache, false);
	}

	protected List<Field> getItemsFromCacheSorted(Cache cache) throws Exception {
		return getItemsFromCache(cache, true);
	}

	protected List<Field> getItemsFromCache(Cache cache, boolean sorted) throws Exception {
		List<Field> list = new ArrayList<Field>();
		if (cache == null)
			return list;
		if (!StringUtils.isEmpty(cache.getRef()))
			cache = ModuleUtil.getCache(context.getModule(), cache.getRef());
		List<Field> fields = ElementUtil.getFields(cache);
		//List<Item> items = CacheUtil.getItems(cache);
		if (fields == null || fields.size() == 0) {
			//warn if no items exist?
			log.debug("No items found in cache: "+cache.getName());
		} else {
			list.addAll(fields);
			if (sorted) {
				Collections.sort(list, new Comparator<Field>() {
					public int compare(Field item1, Field item2) {
						return item1.getName().compareTo(item2.getName());
					}
				});
			}
		}
		return list;
	}

	
	public Fault getFault(Service service) {
		List<Fault> faults = ServiceUtil.getFaults(service);
		boolean hasServiceFault = faults.size() > 0;
		if (hasServiceFault)
			return faults.get(0);
		return null;
	}
	
	protected List<Fault> getFaults(Service service) {
		return ServiceUtil.getFaults(service);
	}

	
//	protected void createFields_DataItems(ModelUnit modelUnit, Service service) throws Exception {
//		Cache cache = service.getCache();
//		if (cache != null) {
//			if (!StringUtils.isEmpty(cache.getRef()))
//				cache = ModuleUtil.getCache(context.getModule(), cache.getRef());
//			Items items = cache.getItems();
//			if (items == null) {
//				//warn if no items exist?
//				log.debug("No items found in cache: "+cache.getName());
//			} else {
//				List<ListItem> listItems = items.getListItems();
//				Iterator<ListItem> iterator = listItems.iterator();
//				while (iterator.hasNext()) {
//					ListItem listItem = (ListItem) iterator.next();
//					createField_DataItem(modelUnit, service, listItem);
//				}
//			}
//		}
//	}
	
//	protected <T extends Item> void createField_DataItemUNUSED(ModelUnit modelUnit, Service service, T item) throws Exception {
//		String type = item.getType();
//		String name = item.getName();
//		String packageName = getPackageNameFromXSDType(type);
//		String className = getClassNameFromXSDType(type);
//
//		ModelAttribute modelAttribute = new ModelAttribute();
//		modelUnit.addInstanceAttribute(modelAttribute);
//		modelAttribute.setModifiers(Modifier.PRIVATE);
//		modelAttribute.setName(name);
//
//		if (structure.equals("map")) {
//			MapItem mapItem = (MapItem) item;
//			String key = mapItem.getKey();
//			String keyPackageName = getPackageNameFromXSDType(key);
//			String keyClassName = getClassNameFromXSDType(key);
//			modelUnit.addImportedClass("java.util.Map");
//			modelUnit.addImportedClass(packageName+"."+className);
//			modelUnit.addImportedClass(keyPackageName+"."+keyClassName);
//			modelAttribute.setClassName("Map<"+keyClassName+", "+className+">");
//			modelAttribute.setPackageName("java.util");
//			
//		} else if (structure.equals("list")) {
//			modelUnit.addImportedClass("java.util.List");
//			modelUnit.addImportedClass(packageName+"."+className);
//			modelAttribute.setClassName("List<"+className+">");
//			modelAttribute.setPackageName("java.util");
//
//		} else if (structure.equals("set")) {
//			modelUnit.addImportedClass("java.util.Set");
//			modelUnit.addImportedClass(packageName+"."+className);
//			modelAttribute.setClassName("Set<"+className+">");
//			modelAttribute.setPackageName("java.util");
//			
//		} else if (structure.equals("item")2) {
//			modelUnit.addImportedClass(packageName+"."+className);
//			modelAttribute.setClassName(className);
//			modelAttribute.setPackageName(packageName);
//		}
//	}
	
	
	
	public <T extends Type> List<String> createSourceForFactoryMethod(ModelClass modelClass, List<T> items) {
		return createSourceForFactoryMethod(modelClass, items, false);
	}
	
	public <T extends Type> List<String> createSourceForFactoryMethod(ModelClass modelClass, List<T> items, boolean initializeUsingParent) {
		List<String> sourceCode = new ArrayList<String>();
		Iterator<T> iterator = items.iterator();
		while (iterator.hasNext()) {
			T item = iterator.next();
			String type = item.getType();
			String name = NameUtil.capName(item.getName());
			String packageName = util.getPackageNameFromType(type);
			String className = util.getClassNameFromType(type);
			String structure = item.getStructure();
			
			if (structure.equals("map")) {
				String key = item.getKey();
				String keyPackageName = util.getPackageNameFromType(key);
				String keyClassName = util.getClassNameFromType(key);
				modelClass.addImportedClass("java.util.Map");
				modelClass.addImportedClass("java.util.HashMap");
				modelClass.addImportedClass(packageName+"."+className);
				modelClass.addImportedClass(keyPackageName+"."+keyClassName);
				if (initializeUsingParent)
					sourceCode.add("this."+item.getName()+" = new LinkedHashMap<"+keyClassName+", "+className+">(parent.getAll"+name+"AsMap());");
				else sourceCode.add("this."+item.getName()+" = new LinkedHashMap<"+keyClassName+", "+className+">();");

			} else if (structure.equals("list")) {
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass("java.util.ArrayList");
				modelClass.addImportedClass(packageName+"."+className);
				if (initializeUsingParent)
					sourceCode.add("this."+item.getName()+" = new ArrayList<"+className+">(parent.getAll"+name+"());");
				else sourceCode.add("this."+item.getName()+" = new ArrayList<"+className+">();");
				
			} else if (structure.equals("set")) {
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.HashSet");
				modelClass.addImportedClass(packageName+"."+className);
				if (initializeUsingParent)
					sourceCode.add("this."+item.getName()+" = new HashSet<"+className+">(parent.getAll"+name+"());");
				else sourceCode.add("this."+item.getName()+" = new HashSet<"+className+">();");
				
			} else if (structure.equals("item")) {
				modelClass.addImportedClass(packageName+"."+className);
				if (initializeUsingParent)
					sourceCode.add("this."+item.getName()+" = parent.get"+name+"();");
				else sourceCode.add("this."+item.getName()+" = new "+className+"();");
			}
		}
		return sourceCode;
	}

	protected <T extends Type> void createMethods_DataAccess(ModelUnit modelUnit, List<T> items, SourceType sourceType) throws Exception {
		Iterator<T> iterator = items.iterator();
		while (iterator.hasNext()) {
			T item = iterator.next();
			createMethods_DataAccess_Wrapper(modelUnit, item, sourceType);
		}
	}

	protected <T extends Type> void createMethods_DataAccess_Wrapper(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
//		boolean isCurrentStateWritable = sourceType == SourceType.CurrentState && false;
//		boolean isPendingStateWritable = sourceType == SourceType.PendingState;
//		boolean isPreparedStateWritable = sourceType == SourceType.PreparedState && false;
//		boolean isStateWritable = isCurrentStateWritable || isPendingStateWritable || isPreparedStateWritable;
//		boolean isJMXManageable = sourceType == SourceType.JMXInvocation;
//		boolean isCollection = FieldUtil.isCollection(field);
//		boolean isRemovable = true;
		
		createMethods_DataAccess(modelUnit, dataItem, sourceType);
		
		String elementName = NameUtil.uncapName(modelUnit.getClassName());
		List<Operation> operationsToBeCreated = new ArrayList<Operation>();
		List<Operation> referencedOperations = context.getReferencedOperations(elementName);
		Iterator<Operation> iterator = referencedOperations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			String operationName = operation.getName();
			switch (sourceType) {
			case SharedCache:
			case CurrentState:
				if (!operationName.startsWith("get"))
					continue;
				break;
			case PendingState:
				if (operationName.startsWith("get"))
					continue;
				break;
			case PreparedState:
				continue;
			}
			
			String operationNameLowerCase = operation.getName().toLowerCase();
			String fieldNameLowerCase = dataItem.getName().toLowerCase();
			if (operationNameLowerCase.contains(fieldNameLowerCase)) {
				ModelOperation instanceOperation = findInstanceOperation(modelUnit, operation.getName());
				if (instanceOperation == null) {
					operationsToBeCreated.add(operation);
				}
			}
		}
		
		if (operationsToBeCreated.size() > 0) {
			iterator = operationsToBeCreated.iterator();
			while (iterator.hasNext()) {
				Operation operation = iterator.next();
				throw new UnsupportedOperationException();
//				MethodType methodType = getMethodType(operation);
//				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, operation, field, sourceType, methodType));
			}
		}
	}

	protected <T extends Type> void createMethods_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
		boolean hasCriteriaElement = CodeUtil.hasCriteriaElement(dataItem);
		boolean isStateWritable = util.isStateWritable(sourceType);
		boolean isJMXManageable = util.isJMXManageable(sourceType);
//		if (dataItem.getStructure() == null)
//			System.out.println();
		boolean isCollection = FieldUtil.isCollection(dataItem);
		boolean isRemovable = util.isRemovable(sourceType);
		String structure = dataItem.getStructure();
		
		if (structure.equals("item")) {
			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItem));
			
		} else if (structure.equals("list") || structure.equals("set")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItemById));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByIds));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByCriteria));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
		
		//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsListByIndex));
		//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsListByKey));
		} else if (structure.equals("map")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItemById));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItemByKey));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByIds));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsMapByKeys));
			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByKeys));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsListByCriteria));
			if (hasCriteriaElement) {
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsMap));
			}
		}
		if ((isStateWritable || isJMXManageable) && DataLayerHelper.isCacheUnitRelated(this.getClass()))
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
		//if (isStateWritable)
		//	modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, field, sourceType, MethodType.Unset));
		if ((isStateWritable || isJMXManageable) && isCollection) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			if (structure.equals("map"))
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsMap));
		}
		if ((isStateWritable || isJMXManageable) && isCollection && isRemovable) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemById));
			if (structure.equals("map") && hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemByKey));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByKeys));
			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveMatchingAsList));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByCriteria));
		}
//		if (structure.equals("map"))
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, field, sourceType, MethodType.RemoveAsMap));
	}
	
//	protected MethodType getMethodType(Operation operation) {
//		if (operation.getName().startsWith("get"))
//			return MethodType.GetAsList;
//		if (operation.getName().startsWith("set"))
//			return MethodType.Set;
//		if (operation.getName().startsWith("unset"))
//			return MethodType.Unset;
//		if (operation.getName().startsWith("add"))
//			return MethodType.AddAsItem;
//		if (operation.getName().startsWith("remove"))
//			return MethodType.RemoveAsItem;
//		if (operation.getName().startsWith("removeAll"))
//			return MethodType.RemoveAll;
//		return null;
//	}

	protected ModelOperation findInstanceOperation(ModelUnit modelUnit, String operationName) {
		Collection<ModelOperation> instanceOperations = modelUnit.getInstanceOperations();
		Iterator<ModelOperation> iterator = instanceOperations.iterator();
		while (iterator.hasNext()) {
			ModelOperation modelOperation = iterator.next();
			if (modelOperation.getName().equalsIgnoreCase(operationName)) {
				return modelOperation;
			}
		}
		return null;
	}

	protected String getDataAccessMethodPrefix(MethodType methodType, String structure) {
		return util.getDataAccessMethodPrefix(methodType, structure);
	}

	protected String getDataAccessMethodSuffix(MethodType methodType, String structure) {
		return util.getDataAccessMethodSuffix(methodType, structure);
	}
	
//	protected boolean isMethodWritable(MethodType methodType) {
//		return methodType != MethodType.GetAsList;
//	}
	

	protected <T extends Field> ModelOperation createOperation_DataAccess(ModelUnit modelUnit, Operation operation, T field, SourceType sourceType, MethodType methodType) throws Exception {
		String type = field.getType();
		String name = NameUtil.capName(field.getName());
		String nameUncapped = NameUtil.uncapName(field.getName());
		String packageName = util.getPackageNameFromType(type);
		String className = util.getClassNameFromType(type);
		String fieldStructure = field.getStructure();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(util.getModifiers(sourceType));
		modelOperation.setName(operation.getName());
		
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			modelOperation.addParameter(CodeUtil.createParameter(parameter));
		}
		
		Buf buf = new Buf();
		if (isResultRequired(methodType)) {
			List<Result> results = operation.getResults();
			Result result = results.get(0);
			String xsdType = result.getType();
			int indexOfColon = xsdType.indexOf(":");
			String namespacePrefix = xsdType.substring(0, indexOfColon);
			Namespace namespace = context.getNamespaceByPrefix(namespacePrefix);
			String resultPackageName = ProjectLevelHelper.getPackageName(namespace.getUri());
			String resultClassName = xsdType.substring(indexOfColon+1);
			String resultStructure = result.getConstruct();
			
			String resultType = getResultType(methodType, modelUnit, resultPackageName, resultClassName, result.getKey(), resultStructure);
			modelOperation.setResultType(resultType);
			
			if (resultStructure.equals(fieldStructure)) {
				//TODO buf.putLine2("return currentState.get"+name+"();");
				buf.putLine2("return currentState.get"+name+"();");
				
			} else if (resultStructure.equals("map")) {
				
			} else if (resultStructure.equals("list")) {
				modelUnit.addImportedClass("java.util.ArrayList");
				buf.putLine2("List<"+resultClassName+"> list = new ArrayList<"+resultClassName+">();");
				if (fieldStructure.equals("set")) {
					buf.putLine2("list.addAll(currentState.get"+name+"());");
				} else if (fieldStructure.equals("map")) {
					buf.putLine2("list.addAll(currentState.get"+name+"());");
				} else if (fieldStructure.equals("item")) {
					buf.putLine2("list.add(currentState.get"+name+"());");
				}
				buf.putLine2("return list;");

			} else if (resultStructure.equals("set")) {
				
			} else if (resultStructure.equals("item")) {
			}
		}
		
		modelOperation.addInitialSource(buf.get());
		modelUnit.addImportedClass(packageName+"."+className);
		return modelOperation;
	}
	
	protected boolean isResultRequired(MethodType methodType) {
		boolean isCacheRelated = DataLayerHelper.isCacheUnitRelated(this.getClass()); 
		return util.isResultRequired(methodType, isCacheRelated);
	}
	
	protected boolean isParameterRequired(MethodType methodType, TestType testType) {
		return util.isParameterRequired(methodType, testType);
	}
	
	protected boolean isParameterGeneric(MethodType methodType) {
		return util.isParameterGeneric(methodType);
	}

	protected <T extends Type> ModelOperation createOperation_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType) throws Exception {
		return createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, null);
	}

	protected <T extends Type> ModelOperation createOperation_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType, int testTypes) throws Exception {
		return createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, new TestType(testTypes));
	}
	
	protected <T extends Type> ModelOperation createOperation_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType, TestType testType) throws Exception {
		String type = dataItem.getType();
		String name = NameUtil.capName(dataItem.getName());
		String nameUncapped = NameUtil.uncapName(dataItem.getName());
		String packageName = util.getPackageNameFromType(type);
		String className = util.getClassNameFromType(type);
		String structure = dataItem.getStructure();

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(util.getModifiers(sourceType));
		modelOperation.setName(getOperationName(sourceType, methodType, dataItem));
		modelUnit.addImportedClass(packageName+"."+className);
		
//		if (dataItem.getName().equals("reservedBooks") && methodType == MethodType.GetAsItemByKey)
//			System.out.println();
		
		if (isParameterRequired(methodType, testType)) {
			util.setCacheUnitRelated(DataLayerHelper.isCacheUnitRelated(this.getClass()));
			List<ModelParameter> modelParameters = util.createModelParameters(methodType, modelUnit, dataItem, packageName, className, structure);
			modelOperation.setParameters(modelParameters);
//			parameterType = getParameterType(methodType, modelUnit, field, packageName, className, structure);
//			modelOperation.addParameter(CodeUtil.createParameter(parameterType, typeName));
		}
		
		String resultType = null;
		if (isResultRequired(methodType)) {
			resultType = getResultType(methodType, modelUnit, packageName, className, dataItem.getKey(), structure);
			modelOperation.setResultType(resultType);
		}
		
		if (modelUnit instanceof ModelInterface)
			return modelOperation;
		
		Buf buf = new Buf();
		switch (sourceType) {
		case PendingState:
			switch (methodType) {
			case GetAsList: buf.putLine2("return stateProcessor.getPending"+name+"();"); break;
			case Set: buf.putLine2("stateProcessor.setPending"+name+"("+nameUncapped+");"); break;
			case Unset: buf.putLine2("stateProcessor.setPending"+name+"(null);"); break;
			case AddAsItem: buf.putLine2("stateProcessor.addPending"+name+"("+nameUncapped+");"); break;
			case RemoveAsItem: buf.putLine2("stateProcessor.removePending"+name+"("+nameUncapped+");"); break;
			case RemoveAll: buf.putLine2("stateProcessor.removeAllPending"+name+"();"); break;
			}
			break;
			
		case PreparedState:
			switch (methodType) {
			case GetAsList: buf.putLine2("return isLocked() ? getPreparedState().get"+name+"() : null;"); break;
			case Set: buf.putLine2("if (isLocked()) getPreparedState().set"+name+"("+nameUncapped+");"); break;
			case Unset: buf.putLine2("if (isLocked()) getPreparedState().set"+name+"(null);"); break;
			case AddAsItem: buf.putLine2("if (isLocked()) getPreparedState().add"+name+"("+nameUncapped+");"); break;
			case RemoveAsItem: buf.putLine2("if (isLocked()) getPreparedState().remove"+name+"("+nameUncapped+");"); break;
			case RemoveAll: buf.putLine2("if (isLocked()) getPreparedState().removeAll"+name+"();"); break;
			}
			break;

		case CurrentState:
			switch (methodType) {
			case GetAsList: buf.putLine2("return currentState.get"+name+"();"); break;
			case Set: buf.putLine2("currentState.set"+name+"("+nameUncapped+");"); break;
			case Unset: buf.putLine2("currentState.set"+name+"(null);"); break;
			case AddAsItem: buf.putLine2("currentState.add"+name+"("+nameUncapped+");"); break;
			case RemoveAsItem: buf.putLine2("currentState.remove"+name+"("+nameUncapped+");"); break;
			case RemoveAll: buf.putLine2("currentState.removeAll"+name+"();"); break;
			}
			break;
			
		case SharedCache:
			buf.put(getSharedStateSource(modelUnit, modelOperation, methodType, testType, dataItem)); 
			break;

		case JMXInvocation:
			switch (methodType) {
			case GetAsList: buf.putLine2("return invokeMBean(\"get"+name+"\");"); break;
			case Set: buf.putLine2("invokeMBean(\"set"+name+"\");"); break;
			case Unset: buf.putLine2("invokeMBean(\"set"+name+"\");"); break;
			case AddAsItem: buf.putLine2("invokeMBean(\"addTo"+name+"\");"); break;
			case RemoveAsItem: buf.putLine2("invokeMBean(\"removeFrom"+name+"\");"); break;
			case RemoveAll: buf.putLine2("invokeMBean(\"removeAll"+name+"\");"); break;
			}

			break;
		}

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	public <T extends Type> String getOperationName(SourceType sourceType, MethodType methodType, T dataItem) {
		String name = NameUtil.capName(dataItem.getName());
		String structure = dataItem.getStructure();
		
		String operationName = getDataAccessMethodPrefix(methodType, structure);
		switch (sourceType) {
		case PendingState:
			//if (!isMethodWritable(methodType))
				operationName += "Pending";
			operationName += name;
			break;
		case PreparedState:
			operationName += "Prepared" + name;
			break;
		case CurrentState:
		case SharedCache:
		case JMXInvocation:
			operationName += name;
			break;
		}
		operationName += getDataAccessMethodSuffix(methodType, structure);
		return operationName;
	}
	
	public <T extends Field> String getResultType(MethodType methodType, ModelUnit modelUnit, String packageName, String className, String keyType, String structure) {
		return util.getResultType(methodType, modelUnit, packageName, className, keyType, structure);
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, TestType testType, Type dataItem) {
		if (testType != null)
			return util.getSharedStateSource(modelUnit, modelOperation, methodType, testType, dataItem); 
		return getSharedStateSource(modelUnit, modelOperation, methodType, dataItem); 
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		return util.getSharedStateSource(modelUnit, modelOperation, methodType, dataItem); 
	}
	
	
//	protected String getNamespace(Process process) {
//		String namespace = ProcessUtil.getNamespace(process);
//		if (namespace == null)
//			namespace = context.getApplication().getNamespace();
//		return namespace;
//	}

//	protected String getClassNameForTypeName(String typeName) {
//		String className = typeName;
//		int indexOfDot = typeName.indexOf(".");
//		if (indexOfDot != -1)
//			className = typeName.substring(indexOfDot+1);
//		className = NameUtil.capName(className);
//		return className;
//	}
//
//	protected String getPackageNameForTypeName(String typeName) {
//		Namespace namespace = null;
//		int indexOfDot = typeName.indexOf(".");
//		if (indexOfDot != -1) {
//			String prefix = typeName.substring(0, indexOfDot);
//			typeName = typeName.substring(indexOfDot+1);
//			namespace = context.getNamespaceByPrefix(prefix);
//		} else namespace = context.getNamespace();
//		String packageName = NameUtil.getPackageFromNamespace(namespace.getUri());
//		return packageName;
//	}


}
