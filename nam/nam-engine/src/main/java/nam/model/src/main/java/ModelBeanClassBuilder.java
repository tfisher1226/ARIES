package nam.model.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Attribute;
import nam.model.Cache;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Fault;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateAdapter;
import org.aries.adapter.DateTimeAdapter;
import org.aries.adapter.MapAdapter;
import org.aries.adapter.TimeAdapter;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.ModelFieldUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelEnum;
import aries.generation.model.ModelField;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelPackage;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelReference;


/*
 * TODO make clear note regarding special case for automatically creating "ref" attribute
 */
public class ModelBeanClassBuilder extends AbstractBeanBuilder {

	protected boolean needCompareObjectMethod;
	
	
	public ModelBeanClassBuilder(GenerationContext context) {
		super(context);
	}
	
	public List<ModelPackage> buildPackages(List<Namespace> namespaces) throws Exception {
		List<ModelPackage> modelPackages = new ArrayList<ModelPackage>();
		Iterator<Namespace> iterator = namespaces.iterator();
		Module module = context.getModule();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			boolean isLocalNamespace = namespace.getUri().equals(module.getNamespace());
			if (!isLocalNamespace && NamespaceUtil.isImported(namespace))
				continue;
			
			ModelPackage modelPackage = buildPackage(namespace);
			modelPackages.add(modelPackage);
		}
		return modelPackages;
	}

	public ModelPackage buildPackage(Namespace namespace) throws Exception {
		ModelPackage modelPackage = new ModelPackage();
		modelPackage.setName(ProjectLevelHelper.getPackageName(namespace.getUri()));
		//buildTypes(modelPackage, namespace, NamespaceUtil.getTypes(namespace));
		buildEnumerations(modelPackage, namespace, NamespaceUtil.getEnumerations(namespace));
		buildElements(modelPackage, namespace, NamespaceUtil.getElements(namespace));
		buildCaches(modelPackage, namespace, NamespaceUtil.getCaches(namespace));
		return modelPackage;
	}

	public void buildTypes(ModelPackage modelPackage, Namespace namespace, List<Type> dataTypes) throws Exception {
		Iterator<Type> iterator = dataTypes.iterator();
		while (iterator.hasNext()) {
			Type type = iterator.next();
			if (type instanceof Enumeration) {
				ModelEnum modelEnum = buildEnumeration(namespace, (Enumeration) type, false);
				modelPackage.getEnums().add(modelEnum);
			} else {
				ModelClass modelClass = buildType(type);
				modelPackage.getClasses().add(modelClass);
			}
		}
	}

	public ModelClass buildType(Type type) throws Exception {
		ModelClass modelClass = new ModelClass();
		modelClass.setType(type.getType());
		modelClass.setName(NameUtil.getSimpleName(type.getType()));
		modelClass.setClassName(TypeUtil.getClassName(type.getType()));
		modelClass.setPackageName(TypeUtil.getPackageName(type.getType()));
		//modelClass.addImplementedInterface(" Comparable<"+className+">");
		modelClass.addImplementedInterface("Serializable");
		//modelClass.setPackageName(context.getModule().getGroupId()+".model");
		//if (modelClass.getClassName().equals("Member"))
		//	System.out.println();
		if (type instanceof Element)
			initializeClass(modelClass, (Element) type);
		return modelClass;
	}

	public void buildEnumerations(ModelPackage modelPackage, Namespace namespace, List<Enumeration> enumerations) throws Exception {
		Iterator<Enumeration> iterator = enumerations.iterator();
		while (iterator.hasNext()) {
			Enumeration enumeration = iterator.next();
			modelPackage.getEnums().add(buildEnumeration(namespace, enumeration, true));
			//modelPackage.getEnums().add(buildEnumeration(namespace, enumeration, false));
			//if (ElementUtil.isEnumerationLabelsExist(enumeration))
			//	modelPackage.getEnums().add(buildEnumeration(namespace, enumeration, true));
		}
	}

	public ModelEnum buildEnumeration(Namespace namespace, Enumeration enumeration, boolean generateLabelVersion) throws Exception {
		//context.setEnumeration(enumeration);
		ModelEnum modelEnum = new ModelEnum();
		modelEnum.setName(NameUtil.getSimpleName(enumeration.getType()));
		modelEnum.setClassName(TypeUtil.getClassName(enumeration.getType()));
		//if (generateLabelVersion)
		//	modelEnum.setClassName(TypeUtil.getClassName(enumeration.getType()) + "Name");
		//else modelEnum.setClassName(TypeUtil.getClassName(enumeration.getType()));
		modelEnum.setPackageName(TypeUtil.getPackageName(enumeration.getType()));
		//modelClass.setPackageName(context.getModule().getGroupId()+".model");
		modelEnum.setNamespace(namespace.getUri());
		initializeEnum(modelEnum, enumeration, generateLabelVersion);
		return modelEnum;
	}

	public void buildElements(ModelPackage modelPackage, Namespace namespace, List<Element> elements) throws Exception {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String superType = element.getExtends();
			if (superType != null) {
				//Skip exceptions - they are built separately
				String superClassName = TypeUtil.getClassName(superType);
				if (superClassName.equals("Exception")) {
					continue;
				}
			}
			ModelClass modelClass = buildElement(namespace, element);
			modelPackage.getClasses().add(modelClass);
		}
	}

	public ModelClass buildElement(Namespace namespace, Element element) throws Exception {
		String elementType = element.getType();
		String packageName = TypeUtil.getPackageName(elementType);
		String className = TypeUtil.getClassName(elementType);

		if (element.getName().equalsIgnoreCase("Node"))
			System.out.println();

		ModelClass modelClass = new ModelClass();
		if (className.startsWith("Abstract"))
			modelClass.setAbstract(true);
		modelClass.setType(element.getType());
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.setNamespace(namespace.getUri());
		if (ElementUtil.isComparableElement(element))
			modelClass.addImplementedInterface("Comparable<Object>");
			//modelClass.addImplementedInterface("Comparable<"+className+">");
		modelClass.addImplementedInterface("Serializable");
		
		//modelClass.setName(element.getName());
		//modelClass.setPackageName(context.getModule().getGroupId()+".model");
		//if (modelClass.getClassName().equals("EmailBox"))
		//	System.out.println();
		String superType = element.getExtends();
		if (superType == null && className.endsWith("Criteria"))
			superType = "{http://www.aries.org/common}abstractCriteria";
		if (superType != null) {
			String superTypePackageName = null;
			String superTypeClassName = null;
			if (!TypeUtil.isValidType(superType)) {
				superTypePackageName = NameUtil.getPackageName(superType);
				superTypeClassName = NameUtil.getClassName(superType);
				if (superTypePackageName == null)
					superTypePackageName = packageName;
			} else {
				superTypePackageName = TypeUtil.getPackageName(superType);
				superTypeClassName = TypeUtil.getClassName(superType);
			}
			String superTypeName = superTypePackageName+"."+superTypeClassName;
			modelClass.setParentClassName(superTypeName);
			if (!superTypePackageName.equals("java.lang"))
				modelClass.addImportedClass(superTypeName);
		}

		initializeClass(modelClass, element);
		return modelClass;
	}

	public void buildCaches(ModelPackage modelPackage, Namespace namespace, List<Cache> caches) throws Exception {
		Iterator<Cache> iterator = caches.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			ModelClass modelClass = buildCache(namespace, cache);
			modelPackage.getClasses().add(modelClass);
		}
	}

	public ModelClass buildCache(Namespace namespace, Cache cache) throws Exception {
		ModelClass modelClass = buildElement(namespace, cache);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
//		if (element.getName().equals("ReservationAbortedException"))
//			System.out.println();

		initializeStaticFields(modelClass);
		initializeStaticMethods(modelClass);
		initializeConstructor(modelClass, element);
		initializeInstanceFields(modelClass, element);
		String superType = element.getExtends();
		String localPart = null;
		if (superType != null) {
			if (!TypeUtil.isValidType(superType)) {
				String className = NameUtil.getClassName(superType);
				localPart = NameUtil.uncapName(className);
			} else 
				localPart = TypeUtil.getLocalPart(superType);
			if (localPart.equals("exception")) {
				initializeClassConstructors(modelClass);
			}
		}
		initializeInstanceMethods(modelClass, element);
		initializeClassAnnotations(modelClass);
		initializeImportedClasses(modelClass, element);
	}

	public void initializeConstructor(ModelClass modelClass, Element element) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.setName(modelClass.getClassName());
		
		Buf buf = new Buf();
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			//String fieldType = field.getType();
			String fieldKeyType = field.getKey();
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
			String fieldKeyClassName = TypeUtil.getClassName(fieldKeyType);
			if (field.getStructure().equals("list"))
				buf.putLine2(fieldNameUncapped+" = new ArrayList<"+fieldClassName+">();");
			if (field.getStructure().equals("set"))
				buf.putLine2(fieldNameUncapped+" = new HashSet<"+fieldClassName+">();");
			if (field.getStructure().equals("map"))
				buf.putLine2(fieldNameUncapped+" = new HashMap<"+fieldKeyClassName+", "+fieldClassName+">();");
		}
		
		modelConstructor.addInitialSource(buf.get());
		modelClass.addInstanceConstructor(modelConstructor);
	}

	public void initializeEnum(ModelEnum modelEnum, Enumeration enumeration, boolean generateLabelVersion) throws Exception {
		initializeStaticFields(modelEnum);
		initializeStaticMethods(modelEnum);
		initializeClassAnnotations(modelEnum);
		initializeImportedClasses(modelEnum, enumeration);
		initializeInstanceFields(modelEnum, enumeration, generateLabelVersion);
		if (generateLabelVersion && ElementUtil.isEnumerationLabelsExist(enumeration)) {
			modelEnum.addImportedClass("javax.xml.bind.annotation.XmlEnumValue");
			modelEnum.addInstanceConstructor(createConstructor(modelEnum));
			modelEnum.addInstanceAttribute(createAttribute_EnumValue());
		}
		modelEnum.addInstanceOperation(createOperation_FromValue(modelEnum));
		modelEnum.addInstanceOperation(createOperation_Value(modelEnum));
		//initializeInstanceMethods(modelEnum, enumeration);
	}

	protected ModelConstructor createConstructor(ModelEnum modelEnum) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PRIVATE);
		modelConstructor.addParameter(createParameter("String", "value", "item"));
		modelConstructor.addInitialSource("\t\tthis.value = value;\n");
		modelConstructor.setName("value");
		return modelConstructor;
	}

	protected ModelAttribute createAttribute_EnumValue() {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE+Modifier.FINAL);
		modelAttribute.setClassName("String");
		modelAttribute.setName("value");
		modelAttribute.setStructure("item");
		modelAttribute.setGenerateSetter(false);
		return modelAttribute;
	}

	protected void initializeImportedClasses(ModelClass modelClass, Element element) throws Exception {
//		if (element.getName().equalsIgnoreCase("invoice"))
//			System.out.println();
//		if (ElementUtil.isComparableElement(element))
//			modelClass.addImportedClass("java.lang.Comparable");
		modelClass.addImportedClass("javax.xml.bind.annotation.XmlAccessType");
		modelClass.addImportedClass("javax.xml.bind.annotation.XmlAccessorType");
		modelClass.addImportedClass("javax.xml.bind.annotation.XmlType");
		modelClass.addImportedClass("javax.xml.bind.annotation.XmlRootElement");
		modelClass.addImportedClass("java.io.Serializable");
		if (ElementUtil.hasListOrSetFields(element))
			modelClass.addImportedClass("java.util.Collection");
		if (ElementUtil.hasMapFields(element)) {
			modelClass.addImportedClass("java.util.Iterator");
			modelClass.addImportedClass("java.util.Set");
		}
	}
	
	protected void initializeImportedClasses(ModelEnum modelEnum, Enumeration enumeration) throws Exception {
		modelEnum.addImportedClass("javax.xml.bind.annotation.XmlType");
		modelEnum.addImportedClass("javax.xml.bind.annotation.XmlEnum");
	}

	protected void initializeClassAnnotations(ModelClass modelClass) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		//TODO control this with external property
		//classAnnotations.add(AnnotationUtil.createSuppressSerialWarning());
		classAnnotations.add(AnnotationUtil.createXmlFieldAccessorType());
		classAnnotations.add(AnnotationUtil.createXmlType(modelClass));
		classAnnotations.add(AnnotationUtil.createXmlRootElement(modelClass));
	}

	protected void initializeClassAnnotations(ModelEnum modelEnum) throws Exception {
		//if (modelEnum.getClassName().equals("Division"))
		//	System.out.println();
		List<ModelAnnotation> classAnnotations = modelEnum.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createXmlType(modelEnum));
		classAnnotations.add(AnnotationUtil.createXmlEnum(modelEnum));
	}

	protected void initializeAttributeAnnotations(ModelClass modelClass, ModelAttribute modelAttribute, Element element, Attribute attribute) throws Exception {
		List<ModelAnnotation> annotations = modelAttribute.getAnnotations();
		if (attribute.getName().equals("ref")) {
//			if (element.getName().equalsIgnoreCase("EJBTransport"))
//				System.out.println();
			//if (!CodeUtil.hasField(element, "ref", true)) {
			if (element.getExtends() == null) {
				//only add this for base level superTypes
				annotations.add(AnnotationUtil.createXmlAttribute(modelClass, modelAttribute));
				modelClass.addImportedClass("javax.xml.bind.annotation.XmlAttribute");
			}
		} else {
			annotations.add(AnnotationUtil.createXmlElement(modelClass, modelAttribute));
			modelClass.addImportedClass("javax.xml.bind.annotation.XmlElement");
		}
		initializeXmlAdapterAnnotations(modelClass, modelAttribute, element, attribute);
	}

	protected void initializeReferenceAnnotations(ModelClass modelClass, ModelReference modelReference, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelReference.getAnnotations();
		String referenceType = modelReference.getType();
		Element fieldAsElement = context.getElementByType(referenceType);
		//Enumeration fieldAsEnumeration = context.getEnumerationByType(modelReference.getType());
		//if (fieldAsEnumeration != null) {

//		if (reference.getName().equalsIgnoreCase("informationsAndPersistencesAndServices"))
//			System.out.println();
		
		List<String> acceptedTypes = modelReference.getAcceptedTypes();
		if (acceptedTypes != null && !acceptedTypes.isEmpty()) {
			annotations.add(AnnotationUtil.createXmlElements(modelClass, modelReference));
			modelClass.addImportedClass("javax.xml.bind.annotation.XmlElement");
			modelClass.addImportedClass("javax.xml.bind.annotation.XmlElements");
			
		} else {
			//if (fieldAsElement != null) {
			//boolean isTwoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(fieldAsElement);
			boolean isInverse = ModelFieldUtil.isInverse(modelReference);
			//if (isInverse && isTwoWaySelfReferencing == false) {
			if (isInverse) {
				annotations.add(AnnotationUtil.createXmlTransient());
				modelClass.addImportedClass("javax.xml.bind.annotation.XmlTransient");
	
			} else {
				annotations.add(AnnotationUtil.createXmlElement(modelClass, modelReference));
				initializeXmlAdapterAnnotations(modelClass, modelReference, element, reference);
				modelClass.addImportedClass("javax.xml.bind.annotation.XmlElement");
			}
		//}
		}
	}

	protected void initializeXmlAdapterAnnotations(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		if (modelField.getMultiplicity() == -1) {
			if (modelField.getStructure().equals("list")) {
			} else if (modelField.getStructure().equals("set")) {
			} else if (modelField.getStructure().equals("map")) {
				annotations.add(AnnotationUtil.createXmlJavaTypeAdapter("MapAdapter.class"));
				modelClass.addImportedClass(MapAdapter.class.getCanonicalName());
				modelClass.addImportedClass("javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter");
			}

		} else if (modelField.getQualifiedName().equals("java.lang.Boolean")) {
			annotations.add(AnnotationUtil.createXmlJavaTypeAdapter("BooleanAdapter.class"));
			annotations.add(AnnotationUtil.createXmlSchemaType("boolean"));
			modelClass.addImportedClass(BooleanAdapter.class.getCanonicalName());
			modelClass.addImportedClass("javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter");
			modelClass.addImportedClass("javax.xml.bind.annotation.XmlSchemaType");

		} else if (field.getType().endsWith("date")) {
			annotations.add(AnnotationUtil.createXmlJavaTypeAdapter("DateAdapter.class"));
			annotations.add(AnnotationUtil.createXmlSchemaType("date"));
			modelClass.addImportedClass(DateAdapter.class.getCanonicalName());
			modelClass.addImportedClass("javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter");
			modelClass.addImportedClass("javax.xml.bind.annotation.XmlSchemaType");
		
		} else if (field.getType().endsWith("time")) {
			annotations.add(AnnotationUtil.createXmlJavaTypeAdapter("TimeAdapter.class"));
			annotations.add(AnnotationUtil.createXmlSchemaType("time"));
			modelClass.addImportedClass(TimeAdapter.class.getCanonicalName());
			modelClass.addImportedClass("javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter");
			modelClass.addImportedClass("javax.xml.bind.annotation.XmlSchemaType");
			
		//} else if (modelField.getQualifiedName().equals("java.util.Date")) {
		} else if (field.getType().endsWith("dateTime")) {
			annotations.add(AnnotationUtil.createXmlJavaTypeAdapter("DateTimeAdapter.class"));
			annotations.add(AnnotationUtil.createXmlSchemaType("dateTime"));
			modelClass.addImportedClass(DateTimeAdapter.class.getCanonicalName());
			modelClass.addImportedClass("javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter");
			modelClass.addImportedClass("javax.xml.bind.annotation.XmlSchemaType");
		}
		//@XmlElement(name = "MyMap", required = true)
		//@XmlJavaTypeAdapter(MapAdaptor.class)
	}

	protected void initializeInstanceFieldTypeOLD(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		String fieldType = field.getType();
		//String packageName = context.getModule().getGroupId();
		String packageName = TypeUtil.getPackageName(fieldType);
		String className = TypeUtil.getClassName(fieldType);

		if (fieldType != null) {
			if (CodeGenUtil.isJavaDefaultType(className)) {
				modelField.setPackageName(TypeUtil.getPackageName(fieldType));
				modelField.setClassName(TypeUtil.getClassName(fieldType));
			} else if (context.isElementCachedByJavaType(fieldType)) {
				modelField.setPackageName(packageName);
				modelField.setClassName(className);
			} else {
				modelField.setPackageName(TypeUtil.getPackageName(fieldType));
				modelField.setClassName(TypeUtil.getClassName(fieldType));
			}
		}

		if (modelField.getMultiplicity() == -1) {
			modelClass.addImportedClass("java.util.List");
			modelClass.addImportedClass("java.util.ArrayList");
		}
	}

	protected void initializeInstanceFieldType(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		String itemType = field.getType();
		String className = TypeUtil.getClassName(itemType);
		String packageName = TypeUtil.getPackageName(itemType);
		String javaType = packageName+"."+className;
		String structure = field.getStructure();
		modelClass.addImportedClass(javaType);
		
//		if (className.equals("StreetAddress"))
//			System.out.println();

		if (field instanceof Attribute) {
			if (structure == null) {
				modelField.setPackageName(packageName);
				modelField.setClassName(className);

			} else if (structure.equals("list")) {
				modelField.setPackageName(packageName);
				//modelField.setClassName("List<"+className+">");
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass("java.util.ArrayList");

			} else if (structure.equals("set")) {
				modelField.setPackageName(packageName);
				//modelField.setClassName("Set<"+className+">");
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.HashSet");

			} else if (structure.equals("map")) {
				modelField.setPackageName(packageName);
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.Map");
				modelClass.addImportedClass("java.util.HashMap");
				//key
				String keyType = field.getKey();
				String keyPackageName = TypeUtil.getPackageName(keyType);
				String keyClassName = TypeUtil.getClassName(keyType);
				modelField.setKeyPackageName(keyPackageName);
				modelField.setKeyClassName(keyClassName);
			}

		} else if (field instanceof Reference) {
			if (structure == null) {
				modelField.setPackageName(packageName);
				modelField.setClassName(className);

			} else if (structure.equals("list")) {
				modelField.setPackageName(packageName);
				//modelField.setClassName("List<"+className+">");
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass("java.util.ArrayList");

			} else if (structure.equals("set")) {
				modelField.setPackageName(packageName);
				//modelField.setClassName("Set<"+className+">");
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.HashSet");

			} else if (structure.equals("map")) {
				modelField.setPackageName(packageName);
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.Map");
				modelClass.addImportedClass("java.util.HashMap");
				//key
				String keyType = field.getKey();
				String keyPackageName = TypeUtil.getPackageName(keyType);
				String keyClassName = TypeUtil.getClassName(keyType);
				modelClass.addImportedClass(keyPackageName+"."+keyClassName);
				modelField.setKeyPackageName(keyPackageName);
				modelField.setKeyClassName(keyClassName);
			}
		}
	}

	protected void initializeFieldAnnotations(ModelClass modelClass, ModelField modelField, Field field) throws Exception {
		List<ModelAnnotation> fieldAnnotations = AnnotationUtil.createModelAnnotations(field.getAnnotations());
		modelField.setAnnotations(fieldAnnotations);
	}

	protected void initializeClassConstructors(ModelClass modelClass) {
		modelClass.addInstanceConstructor(createConstructor());
		modelClass.addInstanceConstructor(createConstructorWithMessage());
		modelClass.addInstanceConstructor(createConstructorWithCause());
		modelClass.addInstanceConstructor(createConstructorWithMessageAndCause());
		//modelClass.addInstanceConstructor(createConstructorWithMessageAndFields(fault));
		//modelClass.addInstanceConstructor(createConstructorWithMessageAndFieldsAndCause(fault));
	}
	
	protected ModelConstructor createConstructor() {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithMessage() {
		ModelConstructor modelConstructor = createConstructor();
		ModelParameter modelParameter = new ModelParameter();
		modelConstructor.getParameters().add(modelParameter);
		modelParameter.setName("message");
		modelParameter.setClassName("String");
		Buf buf = new Buf();
		buf.putLine2("super(message);");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithCause() {
		ModelConstructor modelConstructor = createConstructor();
		ModelParameter modelParameter = new ModelParameter();
		modelConstructor.getParameters().add(modelParameter);
		modelParameter.setName("cause");
		modelParameter.setClassName("Throwable");
		Buf buf = new Buf();
		buf.putLine2("super(cause.getMessage(), cause);");
		modelConstructor.getInitialSource().clear();
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithMessageAndCause() {
		ModelConstructor modelConstructor = createConstructorWithMessage();
		ModelParameter modelParameter = new ModelParameter();
		modelConstructor.getParameters().add(modelParameter);
		modelParameter.setName("cause");
		modelParameter.setClassName("Throwable");
		Buf buf = new Buf();
		buf.putLine2("super(message, cause);");
		modelConstructor.getInitialSource().clear();
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithMessageAndFields(Fault fault) {
		ModelConstructor modelConstructor = createConstructorWithMessage();
		addFieldParametersToConstructor(modelConstructor, fault.getAttributes());
		return modelConstructor;
	}

	protected ModelConstructor createConstructorWithMessageAndFieldsAndCause(Fault fault) {
		ModelConstructor modelConstructor = createConstructorWithMessageAndFields(fault);
		Buf buf = new Buf();
		buf.putLine2("super(message, cause);");
		List<String> initialSource = modelConstructor.getInitialSource();
		initialSource.set(0, buf.get());
		ModelParameter modelParameter = new ModelParameter();
		modelConstructor.getParameters().add(modelParameter);
		modelParameter.setName("cause");
		modelParameter.setClassName("Throwable");
		return modelConstructor;
	}

	protected void addFieldParametersToConstructor(ModelConstructor modelConstructor, List<Attribute> attributes) {
		Buf buf = new Buf();
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			ModelParameter modelParameter = new ModelParameter();
			modelConstructor.getParameters().add(modelParameter);
			modelParameter.setName(attribute.getName());
			modelParameter.setClassName(TypeUtil.getClassName(attribute.getType()));
			buf.putLine2("this."+attribute.getName()+" = "+attribute.getName()+";");
		}
		modelConstructor.addInitialSource(buf.get());
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Element element) throws Exception {
		Iterator<ModelField> iterator = modelClass.getInstanceFields().iterator();
		while (iterator.hasNext()) {
			ModelField modelField = iterator.next();
			needCompareObjectMethod = false;
			
			Element fieldElement = context.getElementByType(modelClass.getType());
			boolean isTwoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(fieldElement);
			boolean isInverse = ModelFieldUtil.isInverse(modelField);
			if (isInverse && isTwoWaySelfReferencing == false) {
				ModelReference modelReference = (ModelReference) modelField;
				modelClass.addInstanceOperation(createAfterUnmarshalOpertion(modelClass, modelReference));
			}
		}
		if (ElementUtil.isComparableElement(element)) {
			modelClass.addInstanceOperation(createOperation_compareTo(modelClass, element));
			modelClass.addInstanceOperation(createOperation_compare1(modelClass, element));
			//modelClass.addInstanceOperation(createOperation_compare2(modelClass, element));
			if (needCompareObjectMethod)
				modelClass.addInstanceOperation(createOperation_compareObject(modelClass, element));
			modelClass.addInstanceOperation(createOperation_Equals(modelClass, element));
			modelClass.addInstanceOperation(createOperation_Hashcode(modelClass, element));
			modelClass.addInstanceOperation(createOperation_toString(modelClass, element));
		}
	}

	protected void initializeInstanceMethods(ModelEnum modelEnum, Enumeration enumeration) throws Exception {
		modelEnum.addInstanceOperation(createOperation_Value(modelEnum));
		//TODO modelClass.addInstanceOperation(createToStringOperation());
	}

	protected ModelOperation createAfterUnmarshalOpertion(ModelClass modelClass, ModelReference modelReference) {
		String fieldName = modelReference.getName();
		String className = modelReference.getClassName();
		
		ModelOperation operation = new ModelOperation();
		operation.setModifiers(Modifier.PUBLIC);
		operation.setName("afterUnmarshal");
		operation.addParameter(CodeUtil.createParameter("javax.xml.bind.Unmarshaller", "unmarshaller"));
		operation.addParameter(CodeUtil.createParameter("java.lang.Object", "parent"));
		
		Buf buf = new Buf();
		buf.putLine2("if (parent instanceof "+className+") {");
		buf.putLine2("	this."+fieldName+" = ("+className+") parent;");
		buf.putLine2("}");
		operation.addInitialSource(buf.get());
		modelClass.addImportedClass("javax.xml.bind.Unmarshaller");
		return operation;
	}

	protected ModelOperation createOperation_compareTo(ModelClass modelClass, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		//String keyElementType = element.getType()+"Key";
		//Element keyElement = context.getElementByType(keyElementType);
		
		List<ModelField> key = context.getIdKey(modelClass);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("compareTo");
		modelOperation.addParameter(CodeUtil.createParameter("Object", "object"));
		//modelOperation.addParameter(CodeUtil.createParameter(elementClassName, "other"));
		modelOperation.setResultType("int");
		
		Buf buf = new Buf();
//		if (ElementUtil.idFieldExists(element)) {
//			buf.putLine2("int idStatus = compare(id, other.id);");
//			buf.putLine2("if (idStatus != 0)");
//			buf.putLine2("	return idStatus;");	
//		}
		
//		if (element.getName().equalsIgnoreCase("module"))
//			System.out.println();
		
//		if (element.getName().equalsIgnoreCase("Fault"))
//			System.out.println();

		boolean hasCollectionField = false;
		Collection<Field> keyFields = ElementUtil.getKeyFields(element);
		if (keyFields.isEmpty())
			keyFields.addAll(ElementUtil.getRequiredFields(element));
		//if (keyFields.isEmpty())
		//	keyFields.addAll(ElementUtil.getSingleValueAttributes(element));

		if (!keyFields.isEmpty()) {
			buf.putLine2("if (object.getClass().isAssignableFrom(this.getClass())) {");
			buf.putLine2("	"+elementClassName+" other = ("+elementClassName+") object;");
			
			Iterator<Field> iterator = keyFields.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Field field = iterator.next();
				if (FieldUtil.isCollection(field))
					hasCollectionField = true;
				
				String methodName = "compare";
				if (field instanceof Reference) {
					methodName += "Object";
					needCompareObjectMethod = true;
				}
					
				//String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
				String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
				if (i == 0)
					buf.putLine2("	int status = "+methodName+"("+fieldNameUncapped+", other."+fieldNameUncapped+");");
				else buf.putLine2("	status = "+methodName+"("+fieldNameUncapped+", other."+fieldNameUncapped+");");
				buf.putLine2("	if (status != 0)");
				buf.putLine2("		return status;");
			}
			buf.putLine2("}");
		}
		
		if (element.getExtends() == null) {
			buf.putLine2("return 0;");
		} else {
			buf.putLine2("int status = super.compareTo(object);");
			buf.putLine2("return status;");
		}
		
		modelOperation.addInitialSource(buf.get());
		
		if (hasCollectionField)
			modelClass.addInstanceOperation(createOperation_compare2(modelClass, element));
		return modelOperation;
	}
	
	protected ModelOperation createOperation_compare1(ModelClass modelClass, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String keyElementType = element.getType()+"Key";
		Element keyElement = context.getElementByType(keyElementType);
		
		List<ModelField> key = context.getIdKey(modelClass);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("compare");
		modelOperation.addParameter(CodeUtil.createParameter("T", "value1"));
		modelOperation.addParameter(CodeUtil.createParameter("T", "value2"));
		modelOperation.setResultType("<T extends Comparable<T>> int");

		Buf buf = new Buf();
		buf.putLine2("if (value1 == null && value2 == null) return 0;");
		//buf.putLine2("	return 0;");
		buf.putLine2("if (value1 != null && value2 == null) return 1;");
		//buf.putLine2("	return 1;");
		buf.putLine2("if (value1 == null && value2 != null) return -1;");
		//buf.putLine2("	return -1;");
		buf.putLine2("int status = value1.compareTo(value2);");
		buf.putLine2("return status;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_compare2(ModelClass modelClass, Element element) {
		modelClass.addImportedClass("java.util.Iterator");

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("compare");
		modelOperation.addParameter(CodeUtil.createParameter("Collection<T>", "collecton1"));
		modelOperation.addParameter(CodeUtil.createParameter("Collection<T>", "collecton2"));
		modelOperation.setResultType("<T extends Comparable<T>> int");

		Buf buf = new Buf();
		buf.putLine2("if (collecton1 == null && collecton2 == null) return 0;");
		buf.putLine2("if (collecton1 != null && collecton2 == null) return 1;");
		buf.putLine2("if (collecton1 == null && collecton2 != null) return -1;");
		buf.putLine2("int status = compare(collecton1.size(), collecton2.size());");
		buf.putLine2("if (status != 0)");
		buf.putLine2("	return status;");
		buf.putLine2("Iterator<T> iterator1 = collecton1.iterator();");
		buf.putLine2("Iterator<T> iterator2 = collecton2.iterator();");
		buf.putLine2("while (iterator2.hasNext() && iterator2.hasNext()) {");
		buf.putLine2("	T value1 = iterator1.next();");
		buf.putLine2("	T value2 = iterator2.next();");
		buf.putLine2("	status = value1.compareTo(value2);");
		buf.putLine2("	if (status != 0)");
		buf.putLine2("		return status;");
		buf.putLine2("}");
		buf.putLine2("return 0;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_compareObject(ModelClass modelClass, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String keyElementType = element.getType()+"Key";
		Element keyElement = context.getElementByType(keyElementType);
		
		List<ModelField> key = context.getIdKey(modelClass);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("compareObject");
		modelOperation.addParameter(CodeUtil.createParameter("T", "value1"));
		modelOperation.addParameter(CodeUtil.createParameter("T", "value2"));
		modelOperation.setResultType("<T extends Comparable<Object>> int");

		Buf buf = new Buf();
		buf.putLine2("if (value1 == null && value2 == null) return 0;");
		//buf.putLine2("	return 0;");
		buf.putLine2("if (value1 != null && value2 == null) return 1;");
		//buf.putLine2("	return 1;");
		buf.putLine2("if (value1 == null && value2 != null) return -1;");
		//buf.putLine2("	return -1;");
		buf.putLine2("int status = value1.compareTo(value2);");
		buf.putLine2("return status;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_toString(ModelClass modelClass, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		//String keyElementType = element.getType()+"Key";
		//Element keyElement = context.getElementByType(keyElementType);
		
		List<ModelField> key = context.getIdKey(modelClass);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("toString");
		modelOperation.setResultType("String");
		
		Buf buf = new Buf();
		if (elementClassName.startsWith("Abstract")) {
			buf.putLine2("StringBuffer buf = new StringBuffer();");
			buf.putLine2("buf.append(getClass().getName()+\": \");");
			buf.putLine2("buf.append(\"hashCode=\"+hashCode());");
			
			Collection<Field> toStringFields = ElementUtil.getFieldsForToString(element);
			Iterator<Field> iterator = toStringFields.iterator();
			for (int i=0; iterator.hasNext();) {
				Field field = iterator.next();
				if (FieldUtil.isId(field))
					continue;
				String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
				buf.putLine2("if ("+fieldNameUncapped+" != null)");
				buf.putLine2("	buf.append(\", "+fieldNameUncapped+"=\"+"+fieldNameUncapped+");");
			}
			buf.putLine2("return buf.toString();");
			
		}  else {
			buf.put2("return \""+elementClassName+":");
			boolean isEmpty = true;
			Collection<Field> toStringFields = ElementUtil.getFieldsForToString(element);
			Iterator<Field> iterator = toStringFields.iterator();
			for (int i=0; iterator.hasNext();) {
				Field field = iterator.next();
				if (FieldUtil.isId(field))
					continue;
				//String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
				String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
				if (i++ == 0)
					buf.put(" ");
				else buf.put("+\", ");
				buf.put(fieldNameUncapped+"=\"+"+fieldNameUncapped);
				isEmpty = false;
			}
			
			if (isEmpty)
				buf.putLine(" \"+super.toString();");
			else buf.putLine(";");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
