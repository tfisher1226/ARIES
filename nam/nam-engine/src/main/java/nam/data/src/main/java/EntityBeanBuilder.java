package nam.data.src.main.java;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.data.DataLayerHelper;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Elements;
import nam.model.Field;
import nam.model.Grouping;
import nam.model.Id;
import nam.model.IdSourceType;
import nam.model.Item;
import nam.model.ListItem;
import nam.model.MapItem;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Properties;
import nam.model.Reference;
import nam.model.SetItem;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.PropertyUtil;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.ecore.EEnum;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.MethodUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelField;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelPackage;
import aries.generation.model.ModelReference;


/**
 * Builds a JPA Entity Bean from an Aries Element or Aries Namespace;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * <li>overridePackageNameFromXSD</li>
 * <li>generateTableAnnotation</li>
 * <li>generateLowercaseTableAndColumnNames</li>
 * <li>addUnderscoresInTableAndColumnNames</li>
 * <li>generateFieldLevelJPAAnnotations</li>
 * <li>wrapEnumWithDataType</li>
 * </ul>
 * 
 */
public class EntityBeanBuilder extends AbstractBeanBuilder {

	private Persistence persistence;
	
	private Set<String> classesCreated;
	

	public EntityBeanBuilder(GenerationContext context) {
		super(context);
	}


	/*
	 * Helper methods
	 */
	
	protected Namespace getNamespace() {
		String uri = persistence.getNamespace();
		Namespace namespace = context.getNamespaceByUri(uri);
		return namespace;
	}

	protected boolean isExtendsAbstractParent(Element element) {
		//String classNameFromType = DataLayerHelper.getEntityClassName(element);
		//String classNameFromName = NameUtil.capName(element.getName()) + "Entity";
		//if (!classNameFromName.equals(classNameFromType)) {
			if (context.getParentElementByType(element.getType()) != null) {
				return true;
			}
		//}
		return false;
	}

	protected boolean isAbstractModelClass(ModelClass modelClass) {
		return modelClass.getClassName().startsWith("Abstract");
	}

	protected boolean isFieldElementTypeLocal(Field field) {
		Element element = context.getElementByType(field.getType());
		Assert.notNull(element, "Element not found for type: "+field.getType());
		Persistence persistence = context.getModule().getPersistence();
		List<Element> elements = PersistenceUtil.getFunctionalElements(persistence);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element localElement = iterator.next();
			if (ElementUtil.equals(localElement, element)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Public interface method
	 */
	
	public List<ModelPackage> buildPackages(Persistence persistence) throws Exception {
		classesCreated = new HashSet<String>();
		context.buildParentElementMap(persistence);
		this.persistence = persistence;
		//buildContext(persistence);

		ModelPackage modelPackage = new ModelPackage();
		modelPackage.getClasses().addAll(buildAbstractClasses());
		modelPackage.getClasses().addAll(buildClassesForFields(persistence));
		modelPackage.getClasses().addAll(buildClassesForElements(persistence));
		Collections.sort(modelPackage.getClasses());

		List<ModelPackage> modelPackages = new ArrayList<ModelPackage>();
		modelPackages.add(modelPackage);
		return modelPackages;
	}

	protected Collection<ModelClass> buildClassesForFields(Persistence persistence) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Element> elements = PersistenceUtil.getFunctionalElements(persistence);
		modelClasses.addAll(buildClassesForFields(elements));
		return modelClasses;
	}

	protected Collection<ModelClass> buildClassesForFields(List<Element> elements) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			modelClasses.addAll(buildClassesForFields(element));
		}
		return modelClasses;
	}
	
	protected Collection<ModelClass> buildClassesForFields(Element element) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			ModelClass modelClass = buildClass(getNamespace(), element, reference);
			if (modelClass != null)
				modelClasses.add(modelClass);
		}
		return modelClasses;
	}

	protected Collection<ModelClass> buildClassesForElements(Persistence persistence) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		
		if (units.size() == 0) {
			Collection<Namespace> namespaces = PersistenceUtil.getNamespaces(persistence);
			Iterator<Namespace> iterator = namespaces.iterator();
			while (iterator.hasNext()) {
				Namespace namespace = (Namespace) iterator.next();
				List<Element> elements = NamespaceUtil.getElements(namespace, false);
				modelClasses.addAll(buildClassesForElements(elements));
			}
			
		} else {
			Iterator<Unit> iterator = units.iterator();
			while (iterator.hasNext()) {
				Unit unit = iterator.next();
				if (unit.getRef() != null)
					continue;
				modelClasses.addAll(buildClasses(unit));
			}
		}
		return modelClasses;
	}


//	/*
//	 * Entity generation context creation methods
//	 * ------------------------------------------
//	 */
//	
//	protected void buildContext(Persistence persistence) throws Exception {
//		List<Unit> units = PersistenceUtil.getUnits(persistence);
//		Iterator<Unit> iterator = units.iterator();
//		while (iterator.hasNext()) {
//			Unit unit = iterator.next();
//			if (unit.getRef() != null)
//				continue;
//			buildContext(unit);
//		}
//	}
//
//	protected void buildContext(Unit unit) throws Exception {
//		List<Element> elements = ElementUtil.getElements(unit.getElements());
//		Iterator<Element> iterator = elements.iterator();
//		while (iterator.hasNext()) {
//			Element element = iterator.next();
//			buildContext(unit.getNamespace(), element);
//		}
//	}
//
//	protected void buildContext(Namespace namespace, Element element) throws Exception {
//		String classNameFromType = DataLayerHelper.getEntityClassName(element);
//		String classNameFromName = NameUtil.capName(element.getName()) + "Entity";
//		if (!classNameFromName.equals(classNameFromType)) {
//			parentElementMap.put(element.getType(), element);
//		}
//	}

	
	/*
	 * Abstract entity class build methods
	 * -----------------------------------
	 */

	protected List<ModelClass> buildAbstractClasses() throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>(); 
		Collection<Element> elements = context.getParentElementMap().values();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			ModelClass modelClass = buildAbstractClass(getNamespace(), element);
			modelClasses.add(modelClass);
		}
		return modelClasses;
	}

	protected ModelClass buildAbstractClass(Namespace namespace, Element element) throws Exception {
		String packageName = DataLayerHelper.getEntityPackageName(namespace);
		String className = getAbstractClassName(element);

		ModelClass modelClass = new ModelClass();
		modelClass.setType(element.getType());
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.addImplementedInterface("Serializable");
		modelClass.addImportedClass(packageName+"."+className);
		initializeClass(modelClass, element);
		return modelClass;
	}

	protected String getAbstractClassName(Element element) {
		return getAbstractClassName(element, false);
	}
	
	protected String getAbstractClassName(Element element, boolean isConcrete) {
		String elementName = NameUtil.capName(TypeUtil.getLocalPart(element.getType()));
		String className = "Abstract" + elementName + "Entity";

		Set<Reference> containedReferences = ElementUtil.getContainedReferences(element);
		Iterator<Reference> iterator = containedReferences.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Reference reference = iterator.next();
			if (i == 0)
				className += "<";
			if (isConcrete) {
				className += DataLayerHelper.getContainedEntityClassName(element, reference);
			} else {
				String genericVariable = "T";
				if (containedReferences.size() > 1)
					genericVariable += i;
				String containedClassName = TypeUtil.getClassName(reference.getType());
				String abstractEntityClassName = genericVariable+" extends Abstract" + containedClassName + "Entity";
				className += abstractEntityClassName;
			}
		}
		if (containedReferences.size() > 0)
			className += ">";
		return className;
	}
	
	/*
	 * Entity class build methods
	 * --------------------------
	 */

	public ModelPackage buildPackage(Unit unit) throws Exception {
		Collection<ModelClass> entityBeans = buildClasses(unit);
		ModelPackage modelPackage = new ModelPackage();
		modelPackage.getClasses().addAll(entityBeans);
		return modelPackage;
	}

	public Collection<ModelClass> buildClasses(Unit unit) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>(); 
		//modelClasses.addAll(buildClasses(unit.getNamespace()));
		modelClasses.addAll(buildClasses(unit.getNamespace(), unit.getElements()));
		return modelClasses;
	}
	
	protected Collection<ModelClass> buildClassesForElements(List<Element> elements) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		String uri = persistence.getNamespace();
		Namespace namespace = context.getNamespaceByUri(uri);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			ModelClass modelClass = buildClass(namespace, element);
			if (modelClass != null)
				modelClasses.add(modelClass);
		}
		return modelClasses;
	}

	public Collection<ModelClass> buildClasses(Namespace namespace) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		if (context.isEnabled("generate-imported-namespaces")) {
			List<Namespace> imports = namespace.getImports();
			Iterator<Namespace> iterator = imports.iterator();
			while (iterator.hasNext()) {
				Namespace importedNamespace = iterator.next();
				modelClasses.addAll(buildClasses(importedNamespace));
			}
		}

		Properties properties = NamespaceUtil.getProperties(namespace);
		boolean isImported = PropertyUtil.isEnabled(properties, "imported");
		if (!context.isEnabled("generate-imported-namespaces") && isImported)
			return modelClasses;
		
		List<Element> elements = NamespaceUtil.getElements(namespace, false);
		modelClasses.addAll(buildClasses(namespace, elements));
		return modelClasses;
	}

	protected Collection<ModelClass> buildClasses(Namespace namespace, Elements elements) throws Exception {
		List<Element> list = ElementUtil.getElements(elements);
		return buildClasses(namespace, list);
	}
	
	protected Collection<ModelClass> buildClasses(Namespace namespace, List<Element> elements) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isTransient(element))
				continue;
			ModelClass modelClass = buildClass(namespace, element);
			if (modelClass != null)
				modelClasses.add(modelClass);
		}
		return modelClasses;
	}
	
	protected ModelClass buildClass(Namespace namespace, Element element, Reference reference) throws Exception {
		Element referenceElement = context.getElementByType(reference.getType());
		String packageName = DataLayerHelper.getEntityPackageName(namespace);
		String packageNameFromType = TypeUtil.getPackageName(referenceElement.getType());
		String className = DataLayerHelper.getEntityClassName(referenceElement);
		//String classNameFromName = NameUtil.capName(element.getName()) + "Entity";
		
		//String elementName = NameUtil.capName(TypeUtil.getLocalPart(reference.getType()));
		//String className = elementName + "Entity";

		if (className.equalsIgnoreCase("GohonzonInfoEntity"))
			System.out.println();
		
		if (reference.getContained()) {
			String containedEntityPackageName = DataLayerHelper.getContainedEntityPackageName(namespace);
			String containedEntityClassName = DataLayerHelper.getContainedEntityClassName(element, reference);
			packageName = containedEntityPackageName;
			className = containedEntityClassName;
		}

		String qualifiedName = packageName + "." + className;
		if (classesCreated.contains(qualifiedName))
			return null;
		if (!packageName.startsWith(packageNameFromType))
			return null;

		ModelClass modelClass = new ModelClass();
		modelClass.setType(reference.getType());
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.addImplementedInterface("Serializable");
		if (isExtendsAbstractParent(referenceElement)) {
			String abstractParentClassName = getAbstractClassName(referenceElement, true);
			modelClass.setParentClassName(abstractParentClassName);
		}
		
		this.namespace = namespace;
		initializeClass(modelClass, element, referenceElement);
		classesCreated.add(qualifiedName);
		return modelClass;
	}

	protected ModelClass buildClass(Namespace namespace, Element element) throws Exception {
		String packageName = DataLayerHelper.getEntityPackageName(namespace);
		String classNameFromType = DataLayerHelper.getEntityClassName(element);
		String classNameFromName = NameUtil.capName(element.getName()) + "Entity";
		String qualifiedName = packageName + "." + classNameFromName;
		if (classesCreated.contains(qualifiedName))
			return null;
		
		//String elementName = ModelLayerHelper.getElementName(element);
		//String elementTypeLocalPart = ModelLayerHelper.getElementTypeLocalPart(element);
		//if (elementName.equalsIgnoreCase(elementTypeLocalPart))
		
		ModelClass modelClass = new ModelClass();
		modelClass.setType(element.getType());
		modelClass.setPackageName(packageName);
		modelClass.setClassName(classNameFromName);
		modelClass.setName(NameUtil.uncapName(classNameFromName));
		if (isExtendsAbstractParent(element)) {
			//modelClass.setAbstract(true);
			String abstractParentClassName = getAbstractClassName(element, true);
			modelClass.setParentClassName(abstractParentClassName);
		}
		
		this.namespace = namespace;
		modelClass.addImplementedInterface("Serializable");
		initializeClass(modelClass, element);
		classesCreated.add(qualifiedName);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		initializeImportedClasses(modelClass, element);
		
		if (isAbstractModelClass(modelClass) || !isExtendsAbstractParent(element)) {
			initializeInstanceFields(modelClass, element);
			initializeInstanceMethods(modelClass, element);
		}
		if (!isAbstractModelClass(modelClass) && element.getKey() != null) {
			initializeInstanceFieldForKey(modelClass, element);
		}
		
		//class annotations need to run after field initializations
		initializeClassAnnotations(modelClass, element);
	}
	
	public void initializeClass(ModelClass modelClass, Element owner, Element element) throws Exception {
		initializeImportedClasses(modelClass, element);
		
		if (!isAbstractModelClass(modelClass) && isExtendsAbstractParent(element)) {
			initializeInstanceFieldForContainingOwner(modelClass, owner, element);
		} else {
			initializeInstanceFields(modelClass, element);
			initializeInstanceMethods(modelClass, element);
		}
		
		initializeClassAnnotations(modelClass, element);
	}
	
	protected void initializeInstanceFieldForContainingOwner(ModelClass modelClass, Element owner, Element element) throws Exception {
		Reference reference = ModelLayerHelper.createReferenceToContainingOwner(namespace, owner);
		reference.setType(reference.getType() + "Entity");
		
		ModelReference modelReference = createInstanceField(element, reference);
		initializeReferenceAnnotations(modelClass, modelReference, element, reference);
		initializeImportedClasses(modelClass, modelReference, element, reference);
		modelClass.addInstanceReference(modelReference);
	}


	protected void initializeInstanceFieldForKey(ModelClass modelClass, Element element) throws Exception {
		String elementTypeLocalPart = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		Attribute attribute = new Attribute();
		attribute.setDerived(true);
		attribute.setName("key");
		//attribute.setName(elementTypeLocalPart+"Key");
		attribute.setType(TypeUtil.getDefaultType("String"));
		attribute.setUseForEquals(true);
		attribute.setUnique(true);
		attribute.setRequired(true);
		attribute.setNullable(true);
		attribute.setMinOccurs(1);
		attribute.setMaxOccurs(1);
		attribute.setStructure("item");
		initializeInstanceField(modelClass, element, attribute);
	}


	protected void initializeImportedClasses(ModelClass modelClass, Element element) throws Exception {
		String entityPackageName = DataLayerHelper.getEntityPackageName(getNamespace());
		
		modelClass.addImportedClass("java.io.Serializable");
		
		if (isAbstractModelClass(modelClass) || !isExtendsAbstractParent(element)) {
			Set<String> set = new HashSet<String>();
			List<Field> fields = ElementUtil.getFields(element);
			Iterator<Field> iterator = fields.iterator();
			while (iterator.hasNext()) {
				Field field = iterator.next();
				String fieldType = field.getType();
				if (!isImportRequiredForField(element, field) && !FieldUtil.isInverse(field))
					continue;
				
				String structure = field.getStructure();
				if (structure.equals("list")) {
					modelClass.addImportedClass("java.util.List");
					modelClass.addImportedClass("java.util.ArrayList");
					modelClass.addImportedClass("java.util.Collection");
				} else if (structure.equals("set")) {
					modelClass.addImportedClass("java.util.Set");
					modelClass.addImportedClass("java.util.HashSet");
					modelClass.addImportedClass("java.util.Collection");
				} else if (structure.equals("map")) {
					modelClass.addImportedClass("java.util.Map");
					modelClass.addImportedClass("java.util.HashMap");
				}
				
				if (element.getType().equals(fieldType))
					continue;
				if (set.contains(fieldType))
					continue;
	
				boolean fieldElementTypeLocal = isFieldElementTypeLocal(field);
				String fieldEntityPackageName = DataLayerHelper.getFieldEntityPackageName(field);
				String fieldEntityClassName = DataLayerHelper.getFieldEntityClassName(field);
				
//				if (fieldEntityClassName.equals("EmailAddressEntity"))
//					System.out.println();
	
				if (field instanceof Reference) {
					Reference reference = (Reference) field;
					if (reference.getContained()) {
						fieldElementTypeLocal = true;
						fieldEntityClassName = DataLayerHelper.getContainedEntityClassName(element, reference);
					}
				}
				
				if (fieldElementTypeLocal)
					modelClass.addImportedClass(entityPackageName + "." + fieldEntityClassName);
				else modelClass.addImportedClass(fieldEntityPackageName + "." + fieldEntityClassName);
				set.add(fieldType);
			}
		}
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		if (isAbstractModelClass(modelClass)) {
			classAnnotations.add(AnnotationUtil.createMappedSuperclassAnnotation());
			modelClass.addImportedClass("javax.persistence.MappedSuperclass");
		} else {
			classAnnotations.add(AnnotationUtil.createEntityAnnotation(modelClass));
			if (context.isEnabled("generateTableAnnotation"))
				classAnnotations.add(AnnotationUtil.createTableAnnotation(modelClass));
			classAnnotations.add(AnnotationUtil.createCacheConcurrencyAnnotation(modelClass));
		}
	}
	
//	protected void initializeInstanceField(ModelClass modelClass, Attribute attribute) throws Exception {
//		super.initializeInstanceField(modelClass, attribute);
//	}
//
//	protected void initializeInstanceField(ModelClass modelClass, Reference reference) throws Exception {
//		super.initializeInstanceField(modelClass, reference);
//	}

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		List<Serializable> objects = element.getAttributesAndReferencesAndGroups();
		Iterator<Serializable> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Attribute) {
				initializeInstanceField(modelClass, element, (Attribute) object);
			} else if (object instanceof Reference) {
				initializeInstanceField(modelClass, element, (Reference) object);
			} else if (object instanceof Grouping) {
				initializeInstanceFields(modelClass, element, (Grouping) object);
			}
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Element element, Grouping group) throws Exception {
		List<Field> objects = group.getFields();
		Iterator<Field> iterator = objects.iterator();
		while (iterator.hasNext()) {
			Field object = iterator.next();
			if (object instanceof Attribute) {
				initializeInstanceField(modelClass, element, (Attribute) object);
			} else if (object instanceof Reference) {
				initializeInstanceField(modelClass, element, (Reference) object);
			}
		}
	}
	
	protected ModelAttribute createInstanceField(Element element, Attribute attribute) throws Exception {
		ModelAttribute modelAttribute = super.createInstanceField(element, attribute);
		modelAttribute.setSynchronizationEnabled(false);
		modelAttribute.setGenerateAddMethod(false);
		modelAttribute.setGenerateRemoveMethod(false);
		modelAttribute.setGenerateClearMethod(false);
		return modelAttribute;
	}
	
	protected ModelReference createInstanceField(Element element, Reference reference) throws Exception {
		ModelReference modelReference = super.createInstanceField(element, reference);
		modelReference.setSynchronizationEnabled(false);
		modelReference.setGenerateAddMethod(false);
		modelReference.setGenerateRemoveMethod(false);
		modelReference.setGenerateClearMethod(false);
		return modelReference;
	}
	
	
//	protected void initializeInstanceField(ModelClass modelClass, Item2 item) throws Exception {
//		ModelAttribute modelAttribute = new ModelAttribute();
//		modelAttribute.setModifiers(Modifier.PRIVATE);
//		modelAttribute.setName(item.getName());
//		initializeInstanceFieldType(modelClass, modelAttribute, item);
//		initializeFieldAnnotations(modelClass, modelAttribute, item);
//		modelClass.addInstanceAttribute(modelAttribute);
//	}

//	protected void initializeInstanceField(ModelClass modelClass, ListItem item) throws Exception {
//		ModelReference modelReference = new ModelReference();
//		modelReference.setModifiers(Modifier.PRIVATE);
//		modelReference.setName(item.getName());
//		initializeInstanceFieldType(modelClass, modelReference, item);
//		initializeFieldAnnotations(modelClass, modelReference, item);
//		modelClass.addInstanceReference(modelReference);
//	}

//	protected void initializeInstanceField(ModelClass modelClass, SetItem item) throws Exception {
//		ModelReference modelReference = new ModelReference();
//		modelReference.setModifiers(Modifier.PRIVATE);
//		modelReference.setName(item.getName());
//		initializeInstanceFieldType(modelClass, modelReference, item);
//		initializeFieldAnnotations(modelClass, modelReference, item);
//		modelClass.addInstanceReference(modelReference);
//	}

	protected void initializeInstanceFieldType(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		String fieldType = field.getType();
		//String className = NameUtil.getClassName(itemType);
		//String packageName = NameUtil.getPackageName(itemType);
		String className = TypeUtil.getClassName(fieldType);
		String packageName = TypeUtil.getPackageName(fieldType);
		//String javaClass = ClassUtil.convertTypeToJavaClass(itemType);
		String javaType = packageName+"."+className;
		modelField.setType(fieldType);

		String elementClassName = className;
		String actualEntityClassName = className;
		String representativeEntityClassName = actualEntityClassName;
		
//		if (fieldType.contains("date"))
//			System.out.println();

		if (field instanceof Reference) {
			actualEntityClassName += "Entity";
			representativeEntityClassName += "Entity";
			
			Reference reference = (Reference) field;
			if (reference.getContained()) {
				int index = 0;
				Set<Reference> containedReferences = ElementUtil.getContainedReferences(element);
				Iterator<Reference> iterator = containedReferences.iterator();
				for (int i=0; iterator.hasNext(); i++) {
					Reference containedReference = iterator.next();
					if (containedReference.equals(reference)) {
						index = i;
						break;
					}
				}
				
				if (index == 0)
					representativeEntityClassName = "T";
				else representativeEntityClassName = "T"+index;
			}
		}
		
		String structure = field.getStructure();
		if (structure.equals("list")) {
			modelClass.addImportedClass("java.util.List");
			modelClass.addImportedClass("java.util.ArrayList");
		} else if (structure.equals("set")) {
			modelClass.addImportedClass("java.util.Set");
			modelClass.addImportedClass("java.util.HashSet");
		} else if (structure.equals("map")) {
			modelClass.addImportedClass("java.util.Map");
			modelClass.addImportedClass("java.util.HashMap");
		}
		
		if (field instanceof Attribute) {
			modelField.setPackageName(packageName);
			modelField.setClassName(representativeEntityClassName);
			modelClass.addImportedClass(javaType);

		} else if (field instanceof Reference) {
			if (context.getElementByType(fieldType) != null) {
				String entityPackageName = DataLayerHelper.getEntityPackageName(getNamespace());
				modelField.setPackageName(entityPackageName);
				modelField.setClassName(representativeEntityClassName);
				modelClass.addImportedClass(entityPackageName+"."+actualEntityClassName);
				
			} else {
				modelField.setPackageName(packageName);
				modelField.setClassName(elementClassName);
				modelClass.addImportedClass(javaType);
			}

		} else if (field instanceof Item) {
			if (CodeGenUtil.isJavaDefaultType(elementClassName)) {
				modelField.setPackageName(packageName);
				modelField.setClassName(elementClassName);
				modelClass.addImportedClass(javaType);
				
			} else if (context.isElementCachedByJavaType(javaType)) {
				if (context.getElementByType(fieldType) != null) {
					modelField.setPackageName(packageName+".entity");
					modelField.setClassName(representativeEntityClassName);
					modelClass.addImportedClass(packageName+".entity."+actualEntityClassName);

				} else {
					modelField.setPackageName(packageName);
					modelField.setClassName(elementClassName);
					modelClass.addImportedClass(javaType);
				}
			} else {
				modelField.setPackageName(packageName);
				modelField.setClassName(elementClassName);
				modelClass.addImportedClass(javaType);
			}
		
		} else if (field instanceof ListItem) {
			if (context.isElementCachedByJavaType(javaType)) {
				if (context.getElementByType(fieldType) != null) {
					modelField.setPackageName(packageName+".entity");
					modelField.setClassName("List<"+representativeEntityClassName+">");
					modelClass.addImportedClass(packageName+".entity."+actualEntityClassName);

				} else {
					modelField.setPackageName(packageName);
					modelField.setClassName("List<"+elementClassName+">");
					modelClass.addImportedClass(javaType);
				}
			} else {
				modelField.setPackageName(packageName);
				modelField.setClassName("List<"+elementClassName+">");
				modelClass.addImportedClass(javaType);
			}
		
		} else if (field instanceof SetItem) {
			if (context.isElementCachedByJavaType(javaType)) {
				if (context.getElementByType(fieldType) != null) {
					modelField.setPackageName(packageName+".entity");
					modelField.setClassName("Set<"+representativeEntityClassName+">");
					modelClass.addImportedClass(packageName+".entity."+actualEntityClassName);

				} else {
					modelField.setPackageName(packageName);
					modelField.setClassName("Set<"+elementClassName+">");
					modelClass.addImportedClass(javaType);
				}
			} else {
				modelField.setPackageName(packageName);
				modelField.setClassName("Set<"+elementClassName+">");
				modelClass.addImportedClass(javaType);
			}
			
		} else if (field instanceof MapItem) {
			//nothing for now
		}
	}
	
	protected void initializeFieldAnnotations(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		String elementClassName = TypeUtil.getClassName(element.getType());
		String fieldClassName = modelField.getClassName();
		//String className = fieldClassName;
		
//		if (field.getName().equals("id"))
//			System.out.println();
			
		//TODO make this identification come from property not name
		if (FieldUtil.isId(field)) {
			Id id = (Id) field;
			IdSourceType source = id.getSource();
			if (source != null && source == IdSourceType.TABLE) {
				String elementName = elementClassName;
				if (elementName.endsWith("Entity"))
					elementName = NameUtil.trimFromEnd(elementName, "Entity");
				annotations.add(AnnotationUtil.createIdAnnotation());
				annotations.add(AnnotationUtil.createTableGeneratorAnnotation(id, elementName));
				annotations.add(AnnotationUtil.createGeneratedValueAnnotation(id, elementName));
				modelClass.addImportedClass("javax.persistence.Id");
				modelClass.addImportedClass("javax.persistence.GeneratedValue");
				modelClass.addImportedClass("javax.persistence.GenerationType");
				modelClass.addImportedClass("javax.persistence.TableGenerator");
				
			} else {
				annotations.add(AnnotationUtil.createIdAnnotation());
				annotations.add(AnnotationUtil.createGeneratedValueAnnotation());
				modelClass.addImportedClass("javax.persistence.Id");
				modelClass.addImportedClass("javax.persistence.GeneratedValue");
				modelClass.addImportedClass("javax.persistence.GenerationType");
			}
		}

		//process type substitutions (just normalizing to a well-defined set)
		if (fieldClassName.equals("DateTime"))
			fieldClassName = "Date";
		
//		if (fieldClassName.equals("Division"))
//			System.out.println();
		
		//Process ATTRIBUTE 
		if (field instanceof Attribute) {
			Attribute attribute = (Attribute) field;
			
			//if (!StringUtils.isEmpty(field.getEnact())) {
			//	annotations.add(AnnotationUtil.createEnactAnnotation(modelClass, field));
			//}

			if (!field.getName().equals("id")) {
				annotations.add(AnnotationUtil.createColumnAnnotation(field));
				modelClass.addImportedClass("javax.persistence.Column");
			}
			
			String javaName = TypeUtil.getJavaName(field.getType());
			EEnum eEnum = context.getEnumerationCache().get(javaName);
			if (eEnum != null) {
				annotations.add(AnnotationUtil.createEnumeratedAnnotation("String"));
				modelClass.addImportedClass("javax.persistence.Enumerated");
				modelClass.addImportedClass("javax.persistence.EnumType");
			}

			if (field.getType().endsWith("date")) {
				annotations.add(AnnotationUtil.createTemporalDateAnnotation());
				modelClass.addImportedClass("javax.persistence.Temporal");
				modelClass.addImportedClass("javax.persistence.TemporalType");
			}
	
			if (field.getType().endsWith("dateTime") || field.getType().endsWith("time")) {
				annotations.add(AnnotationUtil.createTemporalTimestampAnnotation());
				modelClass.addImportedClass("javax.persistence.Temporal");
				modelClass.addImportedClass("javax.persistence.TemporalType");
			}

//			if (field.getName().toLowerCase().endsWith("zipcodes"))
//				System.out.println();
			
			if (field.getMaxOccurs() > 1 || field.getMaxOccurs() == -1) {
				annotations.add(AnnotationUtil.createElementCollectionAnnotation(modelClass, modelField));
				annotations.add(AnnotationUtil.createCollectionTableAnnotation(modelClass, modelField));
				annotations.add(AnnotationUtil.createCacheConcurrencyAnnotation(modelClass));
			}
		}
		
		//Process REFERENCE
		if (field instanceof Reference) {
			Reference reference = (Reference) field;
			
			//if (!StringUtils.isEmpty(field.getEnact())) {
			//	annotations.add(AnnotationUtil.createEnactAnnotation(modelClass, field));
			//}
			
			//item ???
			if (FieldUtil.isItem(field) && !FieldUtil.isTwoWay(field) && FieldUtil.isOneToOne(reference))
				initializeFieldAnnotations_OneToOne_Unidirectional(modelClass, modelField, element, reference);
			
			//item other-side=item (inverse=true)
			if (FieldUtil.isItem(field) && FieldUtil.isTwoWay(field) && FieldUtil.isOneToOne(reference))
				initializeFieldAnnotations_OneToOne_Bidirectional(modelClass, modelField, element, reference);

			//item other-side=item (inverse=true, in same class)
			//initializeFieldAnnotations_OneToOne_SelfReferencing(modelClass, modelField, element, item);
			
			//list ???
			if (FieldUtil.isCollection(field) && !FieldUtil.isTwoWay(field) && FieldUtil.isOneToMany(reference))
				initializeFieldAnnotations_OneToMany_Unidirectional(modelClass, modelField, element, reference);

//			if (elementClassName.startsWith("Organization") && fieldClassName.startsWith("Organization"))
//				System.out.println();
			
			//list other-side=item (inverse=true)
			if (FieldUtil.isCollection(field) && FieldUtil.isTwoWay(field) && FieldUtil.isOneToMany(reference) && !ElementUtil.isSelfReferencing(element))
				initializeFieldAnnotations_OneToMany_Bidirectional(modelClass, modelField, element, reference);
			
			//list other-side=item (inverse=true, in same class)
			if (FieldUtil.isCollection(field) && FieldUtil.isTwoWay(field) && FieldUtil.isOneToMany(reference) && ElementUtil.isSelfReferencing(element))
				initializeFieldAnnotations_OneToMany_SelfReferencing(modelClass, modelField, element, reference);

			//item ???
			if (FieldUtil.isItem(field) && !FieldUtil.isTwoWay(field) && FieldUtil.isManyToOne(reference))
				initializeFieldAnnotations_ManyToOne_Unidirectional(modelClass, modelField, element, reference);
			
			//item owning-side=list (inverse=false)
			if (FieldUtil.isItem(field) && FieldUtil.isTwoWay(field) && !FieldUtil.isInverse(field) && FieldUtil.isManyToOne(reference))
				initializeFieldAnnotations_ManyToOne_Bidirectional(modelClass, modelField, element, reference);
			
			//item other-side=list (inverse=true)
			if (FieldUtil.isItem(field) && FieldUtil.isTwoWay(field) && FieldUtil.isInverse(field) && FieldUtil.isManyToOne(reference))
				initializeFieldAnnotations_ManyToOne_Bidirectional_Inverse(modelClass, modelField, element, reference);
			
			//item other-side=list (inverse=true, in same class)
			//initializeFieldAnnotations_ManyToOne_SelfReferencing(modelClass, modelField, element, item);

			//list ???
			if (FieldUtil.isCollection(field) && !FieldUtil.isTwoWay(field) && FieldUtil.isManyToMany(reference))
				initializeFieldAnnotations_ManyToMany_Unidirectional(modelClass, modelField, element, reference);
			
			//list other-side=list (inverse=true)
			if (FieldUtil.isCollection(field) && FieldUtil.isTwoWay(field) && FieldUtil.isManyToMany(reference) && !ElementUtil.isSelfReferencing(element))
				initializeFieldAnnotations_ManyToMany_Bidirectional(modelClass, modelField, element, reference);
			
			//list other-side=list (inverse=true, in same class)
			if (FieldUtil.isCollection(field) && FieldUtil.isTwoWay(field) && FieldUtil.isManyToMany(reference) && ElementUtil.isSelfReferencing(element))
				initializeFieldAnnotations_ManyToMany_SelfReferencing(modelClass, modelField, element, reference);
			
			annotations.add(AnnotationUtil.createCacheConcurrencyAnnotation(modelClass));
		}
	}
	
	
    /**
     * @OneToOne(targetEntity="Shipping")
     * @JoinColumn(name="shipping_id", referencedColumnName="id")
     * 
     * A unidirectional one-to-one association on a join table is possible, but extremely unusual. 
     * 
     * A unidirectional one-to-one association on a primary key may use an id generator to 
     * adopt the referenced primary key as the local primary key.
     * 
     * A unidirectional one-to-one association on a foreign key is almost identical to a many-to-one 
     * unidirectional association. The only difference is the column unique constraint. 
     **/
	protected void initializeFieldAnnotations_OneToOne_Unidirectional(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
//		String className = modelClass.getClassName();
//		if (className.startsWith("Pref") && modelField.getName().toLowerCase().startsWith("user"))
//			System.out.println();

		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createOneToOneAnnotation(reference));
		modelClass.addImportedClass("javax.persistence.OneToOne");
		modelClass.addImportedClass("javax.persistence.CascadeType");
//		if (reference.getMappedBy() != null && reference.getMappedBy().equals("preferences"))
//			System.out.println();
		if (!FieldUtil.isInverse(reference) && StringUtils.isEmpty(reference.getMappedBy())) {
			annotations.add(AnnotationUtil.createJoinColumnAnnotation(element, reference));
			annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
			modelClass.addImportedClass("javax.persistence.JoinColumn");
			modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
		}
		
//		List<ModelAnnotation> annotations = modelField.getAnnotations();
//		String fieldClassName = modelField.getClassName();
//		String fieldType = field.getType();
//		String className = TypeUtil.getClassName(fieldType);
//		String packageName = TypeUtil.getPackageName(fieldType);
//		String javaType = packageName+"."+className;
//
//		if (!CodeGenUtil.isJavaDefaultType(className) && context.getElementByJavaTypeCache().containsKey(javaType)) {
//			annotations.add(AnnotationUtil.createOneToOneAnnotation(fieldClassName));
//			annotations.add(AnnotationUtil.createJoinColumnAnnotationCase2(field));
//			modelClass.addImportedClass("javax.persistence.OneToOne");
//			modelClass.addImportedClass("javax.persistence.CascadeType");
//			modelClass.addImportedClass("javax.persistence.JoinColumn");
//			annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, field));
//			modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
//		} else {
//			if (javaType.contains("Organization"))
//				System.out.println();
//			if (context.getEnumerationCache().containsKey(javaType)) {
//				annotations.add(AnnotationUtil.createEnumeratedAnnotation("String"));
//				modelClass.addImportedClass("javax.persistence.Enumerated");
//				modelClass.addImportedClass("javax.persistence.EnumType");
//			}
//			//annotations.add(AnnotationUtil.createColumnAnnotation(field));
//			//modelClass.addImportedClass("javax.persistence.Column");
//		}
	}

    /**
     * Owning-side:
     * @OneToOne(targetEntity="Customer", inversedBy="cart")
     * @JoinColumn(name="customer_id", referencedColumnName="id")
     * @JoinColumn(name="customer_id", referencedColumnName="id", insertable = false, updatable = false)
     * 
     * Non-owning-side:
     * @OneToOne(targetEntity="Cart", mappedBy="customer")
     * 
     * A bidirectional one-to-one association on a foreign key is common.
     * 
     * A bidirectional one-to-one association on a primary key uses the id generator on the 
     * non-owning-side.
     * 
     * A bidirectional one-to-one association on a join table is possible, but extremely unusual. 
     * 
     * An important note, in the @JoinColumn annotation, the name attribute refers to the primary key column of the table the class is tied.
     **/
	protected void initializeFieldAnnotations_OneToOne_Bidirectional(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createOneToOneAnnotation(reference));
		modelClass.addImportedClass("javax.persistence.OneToOne");
		modelClass.addImportedClass("javax.persistence.CascadeType");
//		if (reference.getMappedBy() != null && reference.getMappedBy().equals("preferences"))
//			System.out.println();
		if (!FieldUtil.isInverse(reference) && StringUtils.isEmpty(reference.getMappedBy())) {
			annotations.add(AnnotationUtil.createJoinColumnAnnotation(element, reference));
			annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
			modelClass.addImportedClass("javax.persistence.JoinColumn");
			modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
		}
	}

    /**
     * @OneToOne(targetEntity="Student")
     * @JoinColumn(name="mentor_id", referencedColumnName="id")
     * 
     * Almost identical to the unidirectional one-to-one association.
     **/
	protected void initializeFieldAnnotations_OneToOne_SelfReferencing(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createOneToOneAnnotation(reference));
		annotations.add(AnnotationUtil.createJoinColumnAnnotation(element, reference));
		modelClass.addImportedClass("javax.persistence.OneToOne");
		modelClass.addImportedClass("javax.persistence.CascadeType");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
		modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
	}
	

    /**
     * @OneToMany()
     * @JoinTable(name="users_phonenumbers",
     *      joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
     *      inverseJoinColumns={@JoinColumn(name="phonenumber_id", referencedColumnName="id", unique=true)})
     *
     * A unidirectional one-to-many association on a join table is the preferred option. 
     * Specifying unique="true", changes the multiplicity from many-to-many to one-to-many. 
     * 
     * A unidirectional one-to-many association can be mapped through a join table. From Doctrine’s 
     * point of view, it is simply mapped as a unidirectional many-to-many whereby a unique constraint 
     * on one of the join columns enforces the one-to-many cardinality.
     * 
     * A unidirectional one-to-many association on a foreign key is an unusual case, and is not recommended. 
     * One should instead use a join table for this kind of association.
     **/
	protected void initializeFieldAnnotations_OneToMany_Unidirectional(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createOneToManyAnnotation(reference));
		annotations.add(AnnotationUtil.createJoinTableAnnotationCase2(modelClass, element, reference));
		modelClass.addImportedClass("javax.persistence.OneToMany");
		modelClass.addImportedClass("javax.persistence.CascadeType");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		modelClass.addImportedClass("javax.persistence.JoinTable");
		annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
		modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
	}
	
    /**
     * Owning-side (Product):
     * @OneToMany(mappedBy="product")
     * @JoinTable(name="product_orders")
     * 
     * Non-owning-side (Order):
     * @ManyToOne(inverse="true")
     * 
     * When using a List, or other indexed collection, set the key column of the foreign key to not null. 
     * The association will be managed from the collections side to maintain the index of each element, 
     * making the other side virtually inverse by setting update="false" and insert="false": 
     * 
     * The bidirectional one-to-many association commonly uses a join table. The inverse="true" can go 
     * on either end of the association, on the collection, or on the join.
     **/
	protected void initializeFieldAnnotations_OneToMany_Bidirectional(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
//		List<ModelAnnotation> annotations = modelField.getAnnotations();
//		annotations.add(AnnotationUtil.createOneToManyAnnotation(reference));
//		modelClass.addImportedClass("javax.persistence.OneToMany");
//		modelClass.addImportedClass("javax.persistence.CascadeType");

		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createOneToManyAnnotation(reference));
		annotations.add(AnnotationUtil.createJoinTableAnnotationCase2(modelClass, element, reference));
		modelClass.addImportedClass("javax.persistence.OneToMany");
		modelClass.addImportedClass("javax.persistence.CascadeType");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		modelClass.addImportedClass("javax.persistence.JoinTable");
		annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
		modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
	}

    /**
     * @OneToMany(mappedBy="parent")
     **/
	protected void initializeFieldAnnotations_OneToMany_SelfReferencing(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createOneToManyAnnotation(reference));
		modelClass.addImportedClass("javax.persistence.OneToMany");
		modelClass.addImportedClass("javax.persistence.CascadeType");
	}
	
    /**
     * @ManyToOne(targetEntity="Address")
     * @JoinColumn(name="address_id", referencedColumnName="id")
     * 
     * A unidirectional many-to-one association is the most common kind of unidirectional association.
     * 
     * A unidirectional many-to-one association on a join table is common when the association is optional.
     * 
     * A many-to-one relationship is where one entity (typically a column or set of columns) contains values 
     * that refer to another entity (a column or set of columns) that has unique values. 
     * 
     * Many-to-one relationships are often enforced by foreign key/primary key relationships, and the relationships 
     * typically are between fact and dimension tables and between levels in a hierarchy. The relationship is often 
     * used to describe classifications or groupings. For example, in a geography schema having tables Region, State, and City, 
     * there are many states that are in a given region, but no states are in two regions. Similarly for cities, a city is 
     * in only one state (cities that have the same name but are in more than one state must be handled slightly differently). 
     * The key point is that each city exists in exactly one state, but a state may have many cities, hence the term “many-to-one.”
     **/
	protected void initializeFieldAnnotations_ManyToOne_Unidirectional(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createManyToOneAnnotation(reference));
		annotations.add(AnnotationUtil.createJoinColumnAnnotation(element, reference));
		modelClass.addImportedClass("javax.persistence.ManyToOne");
		modelClass.addImportedClass("javax.persistence.CascadeType");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
		modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
	}

    /**
     * @ManyToOne(targetEntity="Product", inversedBy="features")
     * @JoinColumn(name="product_id", referencedColumnName="id")
     * 
     * A bidirectional many-to-one association is the most common kind of bidirectional association.
     * This is for the owning side.
     **/
	protected void initializeFieldAnnotations_ManyToOne_Bidirectional(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createManyToOneAnnotation(reference));
		annotations.add(AnnotationUtil.createJoinColumnAnnotation(element, reference));
		modelClass.addImportedClass("javax.persistence.ManyToOne");
		modelClass.addImportedClass("javax.persistence.CascadeType");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
		modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
//		List<ModelAnnotation> annotations = modelField.getAnnotations();
//		annotations.add(AnnotationUtil.createManyToOneAnnotation(field));
//		annotations.add(AnnotationUtil.createJoinTableAnnotationCase2(modelClass, field));
//		modelClass.addImportedClass("javax.persistence.ManyToOne");
//		modelClass.addImportedClass("javax.persistence.CascadeType");
//		modelClass.addImportedClass("javax.persistence.JoinColumn");
//		modelClass.addImportedClass("javax.persistence.JoinTable");
	}

    /**
     * @ManyToOne(targetEntity="Product", inversedBy="features")
     * @JoinColumn(name="product_id", referencedColumnName="id")
     * 
     * A bidirectional many-to-one association is the most common kind of bidirectional association.
     * This is for the inverse side.
     **/
	protected void initializeFieldAnnotations_ManyToOne_Bidirectional_Inverse(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createManyToOneAnnotation(reference));
		annotations.add(AnnotationUtil.createJoinColumnAnnotation(element, reference));
		annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference));
		annotations.add(AnnotationUtil.createOnDeleteAnnotation(modelClass, element, reference));
		modelClass.addImportedClass("javax.persistence.ManyToOne");
		modelClass.addImportedClass("javax.persistence.CascadeType");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
		modelClass.addImportedClass("org.hibernate.annotations.OnDelete");
		modelClass.addStaticImportedClass("org.hibernate.annotations.OnDeleteAction.CASCADE");
//		List<ModelAnnotation> annotations = modelField.getAnnotations();
//		annotations.add(AnnotationUtil.createManyToOneAnnotation(field));
//		annotations.add(AnnotationUtil.createJoinTableAnnotationCase2(modelClass, field));
//		modelClass.addImportedClass("javax.persistence.ManyToOne");
//		modelClass.addImportedClass("javax.persistence.CascadeType");
//		modelClass.addImportedClass("javax.persistence.JoinColumn");
//		modelClass.addImportedClass("javax.persistence.JoinTable");
	}
	
    /**
     * @ManyToOne(targetEntity="Category", inversedBy="children")
     * @JoinColumn(name="parent_id", referencedColumnName="id")
     **/
	protected void initializeFieldAnnotations_ManyToOne_SelfReferencing(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		annotations.add(AnnotationUtil.createManyToOneAnnotation(reference));
		modelClass.addImportedClass("javax.persistence.ManyToOne");
		modelClass.addImportedClass("javax.persistence.CascadeType");
	}
	
	
    /**
     * @ManyToMany(targetEntity="Group")
     * @JoinTable(name="users_groups",
     *      joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
     *      inverseJoinColumns={@JoinColumn(name="group_id", referencedColumnName="id")}
     *      )
     **/
	protected void initializeFieldAnnotations_ManyToMany_Unidirectional(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		//annotations.add(AnnotationUtil.createColumnAnnotation(field));
		annotations.add(AnnotationUtil.createManyToManyAnnotation(reference));
		annotations.add(AnnotationUtil.createJoinTableAnnotationCase2(modelClass, element, reference));
		//modelClass.addImportedClass("javax.persistence.Column");
		modelClass.addImportedClass("javax.persistence.ManyToMany");
		modelClass.addImportedClass("javax.persistence.CascadeType");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		modelClass.addImportedClass("javax.persistence.JoinTable");
		annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
		modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
	}

    /**
     * Owning-side:
     * @ManyToMany(targetEntity="Group", inversedBy="users")
     * @JoinTable(name="users_groups")
     * 
     * Non-owning-side:
     * @ManyToMany(targetEntity="User", mappedBy="groups")
     **/
	protected void initializeFieldAnnotations_ManyToMany_Bidirectional(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		//annotations.add(AnnotationUtil.createColumnAnnotation(field));
		annotations.add(AnnotationUtil.createManyToManyAnnotation(reference));
		annotations.add(AnnotationUtil.createJoinTableAnnotationCase2(modelClass, element, reference));
		//modelClass.addImportedClass("javax.persistence.Column");
		modelClass.addImportedClass("javax.persistence.ManyToMany");
		modelClass.addImportedClass("javax.persistence.CascadeType");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		modelClass.addImportedClass("javax.persistence.JoinTable");
		annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
		modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
	}

    /**
     * Owning-side:
     * @ManyToMany(targetEntity="User", inversedBy="friendsWithMe")
     * @JoinTable(name="friends",
     *      joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
     *      inverseJoinColumns={@JoinColumn(name="friend_user_id", referencedColumnName="id")})
     *
     * Non-owning-side:
     * @ManyToMany(targetEntity="User", mappedBy="myFriends")
     **/
	protected void initializeFieldAnnotations_ManyToMany_SelfReferencing(ModelClass modelClass, ModelField modelField, Element element, Reference reference) throws Exception {
		List<ModelAnnotation> annotations = modelField.getAnnotations();
		//annotations.add(AnnotationUtil.createColumnAnnotation(field));
		annotations.add(AnnotationUtil.createAnnotation("ManyToMany"));
		//annotations.add(AnnotationUtil.createManyToManyAnnotation(reference));
		annotations.add(AnnotationUtil.createJoinTableAnnotationCase2(modelClass, element, reference));
		//modelClass.addImportedClass("javax.persistence.Column");
		modelClass.addImportedClass("javax.persistence.ManyToMany");
		modelClass.addImportedClass("javax.persistence.CascadeType");
		modelClass.addImportedClass("javax.persistence.JoinColumn");
		modelClass.addImportedClass("javax.persistence.JoinTable");
		annotations.add(AnnotationUtil.createForeignKeyAnnotation(modelClass, element, reference, true));
		modelClass.addImportedClass("org.hibernate.annotations.ForeignKey");
	}

	
	protected void initializeInstanceMethods(ModelClass modelClass, Element element) throws Exception {
//		modelClass.addInstanceOperation(createOperation_Equals(modelClass, element));
//		List<ModelField> idKey = context.getIdKey(modelClass);
//		if (idKey.size() > 0) {
//			modelClass.addInstanceOperation(createOperation_Hashcode(modelClass, element));
//			modelClass.addInstanceOperation(createOperation_ToString(modelClass, element));
//		}
		modelClass.addInstanceOperation(createOperation_ToString(modelClass, element));
	}
	
	protected ModelOperation createOperation_ToString(ModelClass modelClass, Element element) {
		String className = "getClass().getSimpleName()";
		ModelOperation operation = new ModelOperation();
		operation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		operation.setModifiers(Modifier.PUBLIC);
		operation.setResultType("String");
		operation.setName("toString");
		Buf buf = new Buf();
		
//		if (element.getName().toLowerCase().equals("user"))
//			System.out.println();
		
		Buf uniqueFieldText = new Buf();
		List<Field> uniqueFields = ElementUtil.getUniqueFields(element);
		//if (uniqueFields.size() > 0)
		//	uniqueFieldText.put(": ");
		Iterator<Field> iterator = uniqueFields.iterator();
		for (int i=0; iterator.hasNext();) {
			Field field = iterator.next();
			String fieldName = ModelLayerHelper.getFieldNameUncapped(field);
			if (i > 0)
				uniqueFieldText.put("+\", ");
			uniqueFieldText.put(fieldName+"=\"+"+MethodUtil.toGetMethod(field)+"()");
			i++;
		}
		
		buf.putLine2("if (getId() != null)");
		if (uniqueFieldText.length() > 0)
			buf.putLine2("	return "+className+"+\"[\"+getId()+\"] "+uniqueFieldText+";");
		else buf.putLine2("	return "+className+"+\"[\"+getId()+\"]\";");
		//buf.putLine2("return \""+className+": hashCode=\"+hashCode()"+uniqueFieldText+";");
		if (uniqueFieldText.length() > 0)
			buf.putLine2("return \""+className+": "+uniqueFieldText+";");
		else buf.putLine2("return super.toString();");
		operation.addInitialSource(buf.get());
		return operation;
	}

}
