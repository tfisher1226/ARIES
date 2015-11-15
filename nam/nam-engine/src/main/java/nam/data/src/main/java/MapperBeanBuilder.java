package nam.data.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.data.DataLayerHelper;
import nam.data.src.test.java.EntityClassHelper;
import nam.model.Element;
import nam.model.Elements;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Properties;
import nam.model.Reference;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


/**
 * Builds a Mapper Bean from an Aries Element or Aries Namespace;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * <li>generateInterface</li>
 * <li>useQualifiedContextNames</li>
 * </ul>
 * 
 * @author tfisher
 */
public class MapperBeanBuilder extends AbstractManagementBeanBuilder {

	private MapperBeanProvider provider;
	
	private Reference reference;
	
	protected boolean generateInterface;

	protected Element mainElement;

	protected Element mapperElement;
	
	
	public MapperBeanBuilder(GenerationContext context) {
		super(context);
		provider = new MapperBeanProvider(context);
	}

	protected boolean isExtendsAbstractParent(Type type) {
		String typeName = type.getType();
		if (context.getParentElementByType(typeName) != null)
			return true;
		return false;
	}
	
	protected String getBeanContextName(Namespace namespace, Field field) {
		return getBeanContextName(namespace, field, "Mapper");
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
	 * Mapper interface creation
	 * -------------------------
	 */
	
	public List<ModelInterface> buildInterfaces(Persistence persistence) throws Exception {
		classesCreated = new HashSet<String>();
		context.buildParentElementMap(persistence);
		this.persistence = persistence;
		List<Unit> units = PersistenceUtil.getUnits(persistence);
		List<Namespace> namespaces = PersistenceUtil.getNamespaces(persistence);
		if (units.size() > 0) {
			List<ModelInterface> modelInterfaces = buildInterfacesFromUnits(units);
			Collections.sort(modelInterfaces);
			return modelInterfaces;
		} else {
			List<ModelInterface> modelInterfaces = buildInterfacesFromNamespaces(namespaces);
			Collections.sort(modelInterfaces);
			return modelInterfaces;
		}
	}
	
	public List<ModelInterface> buildInterfacesFromUnits(List<Unit> units) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			modelInterfaces.addAll(buildInterfaces(unit));
		}
		return modelInterfaces;
	}
	
	public List<ModelInterface> buildInterfaces(Unit unit) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		List<Element> elements = ElementUtil.getElements(unit.getElements());
		Iterator<Element> iterator = elements.iterator();
		this.namespace = unit.getNamespace();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!ElementUtil.isTransient(element)) {
				ModelInterface modelInterface = buildInterface(namespace, element);
				if (modelInterface != null)
					modelInterfaces.add(modelInterface);
			}
		}
		return modelInterfaces;
	}
	
	public List<ModelInterface> buildInterfacesFromNamespaces(List<Namespace> namespaces) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			modelInterfaces.addAll(buildInterfaces(namespace));
		}
		return modelInterfaces;
	}
	
	public List<ModelInterface> buildInterfaces(Namespace namespace) throws Exception {
		this.namespace = namespace;
		
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		if (context.isEnabled("generate-imported-namespaces"))
			modelInterfaces.addAll(buildInterfacesFromNamespaces(namespace.getImports()));

		Properties properties = NamespaceUtil.getProperties(namespace);
		boolean isImported = NamespaceUtil.isImported(namespace);
		//boolean isImported = PropertyUtil.isEnabled(properties, "imported");
		if (!context.isEnabled("generate-imported-namespaces") && isImported)
			return modelInterfaces;
		
		List<Element> elements = NamespaceUtil.getElements(namespace);
		modelInterfaces.addAll(buildInterfaces(namespace, elements));
		return modelInterfaces;
	}
	
	public List<ModelInterface> buildInterfaces(Namespace namespace, Elements elements) throws Exception {
		List<Element> list = ElementUtil.getElements(elements);
		return buildInterfaces(namespace, list);
	}
	
	public List<ModelInterface> buildInterfaces(Namespace namespace, List<Element> elements) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Element> iterator = elements.iterator();
		this.namespace = namespace;
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			ModelInterface modelInterface = buildInterface(namespace, element);
			if (modelInterface != null)
				modelInterfaces.add(modelInterface);
		}
		return modelInterfaces;
	}
	
	public ModelInterface buildInterface(Namespace namespace, Element element) throws Exception {
		ModelInterface modelInterface = new ModelInterface();
		String packageName = DataLayerHelper.getMapperPackageName(namespace);
		String interfaceName = DataLayerHelper.getMapperInterfaceName(element);
		String parentInterfaceName = DataLayerHelper.getMapperParentInterfaceName(element);
		String qualifiedName = packageName + "." + interfaceName;
		if (classesCreated.contains(qualifiedName))
			return null;
		
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		if (parentInterfaceName != null)
			modelInterface.addExtendedInterface(parentInterfaceName);
		modelInterface.setName(NameUtil.uncapName(interfaceName));
		initializeInterface(modelInterface, namespace, element);
		return modelInterface;
	}
	
	protected void initializeInterface(ModelInterface modelInterface, Namespace namespace, Element element) throws Exception {
		//initializeInterfaceAnnotations(modelInterface);
		initializeImportedClasses(modelInterface, namespace, element);
		initializeInterfaceMethods(modelInterface, namespace, element);
	}

	protected void initializeImportedClasses(ModelInterface modelInterface, Namespace namespace, Element element) throws Exception {
		String parentClassName = DataLayerHelper.getMapperParentInterfaceName(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String mapperPackageName = DataLayerHelper.getMapperPackageName(namespace);

		if (parentClassName != null)
			modelInterface.addImportedClass(parentClassName);
		modelInterface.addImportedClass(elementPackageName + "." + elementClassName);
		modelInterface.addImportedClass(entityPackageName + "." + entityClassName);
		modelInterface.addImportedClass("java.util.Collection");
		modelInterface.addImportedClass("java.util.List");
		
		Set<String> set = new HashSet<String>();
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldType = field.getType();
			if (!provider.isImportRequiredForField(element, field) && !FieldUtil.isInverse(field))
				continue;
			
			String structure = field.getStructure();
			if (structure.equals("list")) {
				modelInterface.addImportedClass("java.util.List");
				modelInterface.addImportedClass("java.util.ArrayList");
			} else if (structure.equals("set")) {
				modelInterface.addImportedClass("java.util.Set");
				modelInterface.addImportedClass("java.util.HashSet");
			} else if (structure.equals("map")) {
				modelInterface.addImportedClass("java.util.Map");
				modelInterface.addImportedClass("java.util.HashMap");
			}
			
			if (element.getType().equals(fieldType))
				continue;
			if (set.contains(fieldType))
				continue;

			boolean fieldElementTypeLocal = isFieldElementTypeLocal(field);
			String mapperFieldPackageName = DataLayerHelper.getMapperPackageName(field);
			String mapperFieldInterfaceName = DataLayerHelper.getMapperInterfaceName(field);
			if (fieldElementTypeLocal)
				modelInterface.addImportedClass(mapperPackageName + "." + mapperFieldInterfaceName);
			else modelInterface.addImportedClass(mapperFieldPackageName + "." + mapperFieldInterfaceName);
			set.add(fieldType);
			
			String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldEntityPackageName = DataLayerHelper.getFieldEntityPackageName(field);
			String fieldEntityClassName = DataLayerHelper.getFieldEntityClassName(field);
			
			modelInterface.addImportedClass(fieldPackageName + "." + fieldClassName);
			if (fieldElementTypeLocal)
				modelInterface.addImportedClass(entityPackageName + "." + fieldEntityClassName);
			else modelInterface.addImportedClass(fieldEntityPackageName + "." + fieldEntityClassName);
		}
	}

	protected void initializeInterfaceMethods(ModelInterface modelInterface, Namespace namespace, Element element) throws Exception {
		if (ElementUtil.isTwoWaySelfReferencing(element)) {
			modelInterface.addInstanceOperation(createInstanceOperation_ToModel_2WaySelfReferencing(element, true));
			modelInterface.addInstanceOperation(createInstanceOperation_ToModel_2WaySelfReferencing_Flat(element, true));
			modelInterface.addInstanceOperation(createInstanceOperation_ToModelCollection_Entity_2WaySelfReferencing(element, true));
			modelInterface.addInstanceOperation(createInstanceOperation_ToEntity_2WaySelfReferencing(element, true));
			modelInterface.addInstanceOperation(createInstanceOperation_ToEntity_2WaySelfReferencing_Flat(namespace, element, true));
			modelInterface.addInstanceOperation(createInstanceOperation_ToEntityCollection_2WaySelfReferencing(namespace, element, true));

		} else {
			//modelInterface.addInstanceOperationAll(createInstanceOperations_CreateModel(element, true));
			//modelInterface.addInstanceOperationAll(createInstanceOperations_CreateEntity(element, true));
			modelInterface.addInstanceOperations(createInstanceOperations_ToModel(element, true));
			modelInterface.addInstanceOperations(createInstanceOperations_ToModelCollection(element, true));
			modelInterface.addInstanceOperations(createInstanceOperations_ToEntity(element, true));
			modelInterface.addInstanceOperations(createInstanceOperations_ToEntity2(namespace, element, true));
			modelInterface.addInstanceOperations(createInstanceOperations_ToEntityCollection(element, true));
		}
	}
	
	
	/*
	 * Mapper class creation
	 * -------------------------
	 */
	
	public Collection<ModelClass> buildClassesFromElements(Namespace namespace, Collection<Element> elements) throws Exception {
		Collection<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Element> iterator = elements.iterator();
		this.namespace = namespace;
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			ModelClass modelClass = buildClass(namespace, element, null);
			if (modelClass != null) {
				modelClasses.add(modelClass);
				modelClasses.addAll(buildClassesFromFields(namespace, element));
			}
		}
		return modelClasses;
	}

	public List<ModelClass> buildClassesFromFields(Namespace namespace, Element element) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Reference> references = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (reference.getContained()) {
				//Element referenceElement = context.getElementByType(reference.getType());
				modelClasses.add(buildClass(namespace, element, reference));
			}
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(Namespace namespace, Element element, Reference reference) throws Exception {
		this.namespace = namespace;
		this.mainElement = element;
		this.mapperElement = element;
		this.reference = reference;
		this.provider.namespace = namespace;
		this.provider.mainElement = element;
		this.provider.mapperElement = element;
		this.provider.reference = reference;

		String packageName = provider.getMapperPackageName();
		String className = provider.getMapperClassName();
		String typeName = provider.getMapperTypeName();
		String beanName = provider.getMapperBeanName();
		String interfaceName = className;
		
		if (reference != null && reference.getContained()) {
			this.mapperElement = context.getElementByType(reference.getType());
			this.provider.mapperElement = mapperElement;
		}

		String qualifiedName = packageName + "." + className;
		if (classesCreated.contains(qualifiedName))
			return null;
		
		ModelClass modelClass = new ModelClass();
		this.provider.modelClass = modelClass;
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setType(typeName);
		modelClass.setName(beanName);
		
		//if (isExtendsAbstractParent(element))
		//	modelClass.setClassName(className+"<E extends Abstract"+entityClassName+", M extends "+elementClassName+">");
		//else modelClass.setClassName(className+"<E extends "+entityClassName+", M extends "+elementClassName+">");

		String parentClassName = provider.getParentClassName();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();

		if (parentClassName != null) {
			parentClassName += "<"+elementClassName+", "+entityClassName+">";
			modelClass.setParentClassName(parentClassName);
		}
		
		if (generateInterface)
			modelClass.addImplementedInterface(packageName + "." + interfaceName);
		initializeClass(modelClass, namespace, mapperElement);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		initializeClassAnnotations(modelClass, element);
		initializeClassConstructor(modelClass, element);
		initializeImportedClasses(modelClass, namespace, element);
		initializeInstanceFields(modelClass, namespace, element);
		initializeInstanceMethods(modelClass, namespace, element);
	}
	
	protected void initializeClassConstructor(ModelClass modelClass, Element element) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		String sourceCode = provider.getSourceCode_Initialize(namespace, element);
		modelConstructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(modelConstructor);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		switch (context.getDataLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createStatelessAnnotation());
			classAnnotations.add(AnnotationUtil.createLocalAnnotation(provider.getMapperClassName()));
			break;
		case SEAM:
			//classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
			//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.APPLICATION));
			//classAnnotations.add(AnnotationUtil.createNameAnnotation(getBeanContextName(modelClass)));
			break;
		}
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
//		String parentClassName = DataLayerHelper.getMapperParentClassName(element);
//		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
//		//String entityClassName = DataLayerHelper.getEntityClassName(element);
//		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
//		String mapperPackageName = DataLayerHelper.getMapperPackageName(namespace);
//		String mapperInterfaceName = DataLayerHelper.getMapperInterfaceName(element);

		String parentClassName = provider.getParentClassName();
		String mapperPackageName = provider.getMapperPackageName();
		String mapperClassName = provider.getMapperClassName();
		String elementPackageName = provider.getElementPackageName();
		String elementClassName = provider.getElementClassName();
		String entityPackageName = provider.getEntityPackageName();
		String entityClassName = provider.getEntityClassName();
		
		if (parentClassName != null)
			modelClass.addImportedClass(parentClassName);
		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		//if (isExtendsAbstractParent(element))
		//	modelClass.addImportedClass(entityPackageName + ".Abstract" + entityClassName);
		//else modelClass.addImportedClass(entityPackageName + "." + entityClassName);
		modelClass.addImportedClass(entityPackageName + "." + entityClassName);
		modelClass.addImportedClass(mapperPackageName + "." + mapperClassName);
		modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.List");
		
		switch (context.getDataLayerBeanType()) {
		case EJB:
			modelClass.addImportedClass("javax.ejb.Local");
			modelClass.addImportedClass("javax.ejb.Stateless");
			//modelClass.addImportedClass("javax.annotation.PostConstruct");
			break;
		case SEAM:
			modelClass.addImportedClass("org.jboss.seam.ScopeType");
			modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
			modelClass.addImportedClass("org.jboss.seam.annotations.In");
			modelClass.addImportedClass("org.jboss.seam.annotations.Name");
			modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
			break;
		}
		
		//modelClass.addImportedClass("org.aries.Assert");
		//modelClass.addImportedClass("org.aries.util.collection.ListUtil");

		Set<String> set = new HashSet<String>();
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldType = field.getType();
			if (!provider.isImportRequiredForField(element, field) && !FieldUtil.isInverse(field))
				continue;
			
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
			
			if (element.getType().equals(fieldType))
				continue;
			if (set.contains(fieldType))
				continue;

			EntityClassHelper entityClassHelper = new EntityClassHelper(context);
			entityClassHelper.initialize(namespace, element, field);
			String entityClassName2 = entityClassHelper.getEntityClassName();
			String entityPackageName2 = entityClassHelper.getEntityPackageName();
			Element targetElement = entityClassHelper.getTargetElement();

//			if (!ElementUtil.equals(targetElement, element))
//				modelClass.addImportedClass(entityPackageName2 + "." + entityClassName2);
//			else System.out.println();
			
			String entityNamespace = TypeUtil.getNamespace(targetElement.getType());
			String mapperQualifiedName = DataLayerHelper.getMapperQualifiedName(entityNamespace, targetElement);
			modelClass.addImportedClass(mapperQualifiedName);
			
			if (!entityNamespace.equals(namespace.getUri())) {
				String mapperFixtureQualifiedName = DataLayerHelper.getMapperFixtureQualifiedName(entityNamespace);
				modelClass.addImportedClass(mapperFixtureQualifiedName);
			}

			
//			boolean fieldElementTypeLocal = isFieldElementTypeLocal(field);
//			String mapperFieldPackageName = DataLayerHelper.getMapperPackageName(field);
//			String mapperFieldInterfaceName = DataLayerHelper.getMapperInterfaceName(field);
//			if (fieldElementTypeLocal)
//				modelClass.addImportedClass(mapperPackageName + "." + mapperFieldInterfaceName);
//			else modelClass.addImportedClass(mapperFieldPackageName + "." + mapperFieldInterfaceName);
//			set.add(fieldType);
//			
//			String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
//			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
//			String fieldEntityPackageName = DataLayerHelper.getFieldEntityPackageName(field);
//			String fieldEntityClassName = DataLayerHelper.getFieldEntityClassName(field);
//			
//			if (fieldEntityClassName.equals("BookEntity"))
//				System.out.println();
//
//			modelClass.addImportedClass(fieldPackageName + "." + fieldClassName);
//			if (fieldElementTypeLocal)
//				modelClass.addImportedClass(entityPackageName + "." + fieldEntityClassName);
//			else modelClass.addImportedClass(fieldEntityPackageName + "." + fieldEntityClassName);
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		modelClass.getInstanceFields().addAll(createInstanceFields_MapperBeans(modelClass, namespace, element));
	}
	
	protected List<ModelReference> createInstanceFields_MapperBeans(ModelClass modelClass, Namespace namespace, Element element) {
		Map<String, ModelReference> map = new HashMap<String, ModelReference>();
		List<ModelReference> list = new ArrayList<ModelReference>();
		List<Reference> fields = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			if (!provider.isImportRequiredForField(element, reference))
				continue;
//			if (field instanceof Reference && !FieldUtil.isCascadeDelete((Reference) field))
//				continue;
			if (element.getType().equals(referenceType))
				continue;
			if (map.containsKey(referenceType))
				continue;
			ModelReference modelReference = createInstanceField_MapperBean(namespace, element, reference);
			map.put(referenceType, modelReference);
			list.add(modelReference);
		}
		if (list.size() > 0)
			modelClass.addImportedClass("javax.inject.Inject");
		return list;
	}

	protected ModelReference createInstanceField_MapperBean(Namespace namespace, Element element, Reference reference) {
		Element referenceElement = context.getElementByType(reference.getType());
		
		//String packageName = DataLayerHelper.getMapperPackageName(field);
		//String interfaceName = DataLayerHelper.getMapperInterfaceName(field);
		//String className = DataLayerHelper.getMapperInterfaceName(field);
		
		String packageName = DataLayerHelper.getInferredMapperPackageName(namespace, referenceElement);
		String className = DataLayerHelper.getInferredMapperClassName(namespace, referenceElement);
		String beanType = DataLayerHelper.getInferredMapperTypeName(namespace, referenceElement);
		String beanName = NameUtil.uncapName(className);
		
		//String contextName = getBeanContextName(namespace, field);
		//String fieldElementClassName = ModelLayerHelper.getFieldTypeLocalPartCapped(field);
		//String fieldEntityClassName = DataLayerHelper.getFieldEntityClassName(field);
		//if (isExtendsAbstractParent(field)) {
		//	className += "<"+fieldEntityClassName+", "+fieldElementClassName+">";
		//}

		if (reference.getContained()) {
			packageName = DataLayerHelper.getContainedMapperPackageName(referenceElement);
			className = DataLayerHelper.getContainedMapperClassName(element, reference);
			beanType = DataLayerHelper.getContainedMapperTypeName(namespace, element, reference);
			beanName = NameUtil.uncapName(className);
		}
		
		ModelReference modelReference = new ModelReference();
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		//modelReference.addAnnotation(createInstanceField_InjectionAnnotation(packageName, interfaceName, contextName));
		modelReference.setModifiers(Modifier.PROTECTED);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(beanName);
		modelReference.setType(beanType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	protected void initializeInstanceMethods(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		//modelClass.addInstanceOperation(createInstanceOperation_Initialize(namespace, element));
		
//		if (element.getName().equals("Role"))
//			System.out.println();
		
		if (ElementUtil.isTwoWaySelfReferencing(element)) {
			modelClass.addInstanceOperation(createInstanceOperation_ToModel_2WaySelfReferencing(element, false));
			modelClass.addInstanceOperation(createInstanceOperation_ToModel_2WaySelfReferencing_Flat(element, false));
			modelClass.addInstanceOperation(createInstanceOperation_ToModelCollection_Entity_2WaySelfReferencing(element, false));
			modelClass.addInstanceOperation(createInstanceOperation_ToEntity_2WaySelfReferencing(element, false));
			modelClass.addInstanceOperation(createInstanceOperation_ToEntity_2WaySelfReferencing_Flat(namespace, element, false));
			modelClass.addInstanceOperation(createInstanceOperation_ToEntityCollection_2WaySelfReferencing(namespace, element, false));

		} else if (ElementUtil.isSelfReferencing(element)) {
			modelClass.addInstanceOperation(createInstanceOperation_ToModel_SelfReferencing(element, false));
			modelClass.addInstanceOperation(createInstanceOperation_ToModel_SelfReferencing2(element, false));
			modelClass.addInstanceOperation(createInstanceOperation_ToModel_SelfReferencing_Flat(element, false));
			modelClass.addInstanceOperation(createInstanceOperation_ToModelCollection_Entity_SelfReferencing(element, false));
			modelClass.addInstanceOperation(createInstanceOperation_ToModelCollection_Entity_SelfReferencing2(element, false));
			modelClass.addInstanceOperations(createInstanceOperations_ToEntity(element, false));
			modelClass.addInstanceOperations(createInstanceOperations_ToEntity2(namespace, element, false));
			modelClass.addInstanceOperations(createInstanceOperations_ToEntityCollection(element, false));

		} else {
			modelClass.addInstanceOperations(createInstanceOperations_ToModel(element, false));
			modelClass.addInstanceOperations(createInstanceOperations_ToModelCollection(element, false));
			modelClass.addInstanceOperations(createInstanceOperations_ToEntity(element, false));
			modelClass.addInstanceOperations(createInstanceOperations_ToEntity2(namespace, element, false));
			modelClass.addInstanceOperations(createInstanceOperations_ToEntityCollection(element, false));
		}
	}	

	/*
	 * Operation creation methods
	 * --------------------------
	 */
	
	/**
	 * Initialize operation
	 * --------------------
	 */
	protected ModelOperation createInstanceOperation_Initialize(Namespace namespace, Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addAnnotation(AnnotationUtil.createPostConstructAnnotation());
		String sourceCode = provider.getSourceCode_Initialize(namespace, element);
		modelOperation.addInitialSource(sourceCode);
		return modelOperation;
	}
	
	/*
	 * createModel() method
	 */
	
	protected ModelOperation createInstanceOperations_CreateModel(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createModel");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(elementClassName);
		modelOperation.addParameter(createParameter(entityClassName, elementName+"Entity"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModel(namespace, element, null));
		return modelOperation;
	}

	
	/*
	 * toModel() methods
	 */
	
	protected List<ModelOperation> createInstanceOperations_ToModel(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		boolean inverseRelationFound = false;

		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isInverse(field)) {
				ModelOperation modelOperation = createInstanceOperation_ToModel(element, field, interfaceOnly);
				modelOperations.add(modelOperation);
				inverseRelationFound = true;
			}
		}
		
		if (inverseRelationFound == false) {
			ModelOperation modelOperation = createInstanceOperation_ToModel(element, interfaceOnly);
			modelOperations.add(modelOperation);
		}

		return modelOperations;
	}
	
	protected ModelOperation createInstanceOperation_ToModel(Element element, Field field, boolean interfaceOnly) {
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldName = ModelLayerHelper.getFieldNameUncapped(field);
		String structure = field.getStructure();
		if (structure.equals("list"))
			fieldClassName = "List<"+fieldClassName+">";

		ModelOperation modelOperation = createInstanceOperation_ToModel(element, true);
		modelOperation.getParameters().add(0, createParameter(fieldClassName, fieldName));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModel(namespace, element, field));
		return modelOperation;
	}

	protected ModelOperation createInstanceOperation_ToModel(Element element, boolean interfaceOnly) {
		//String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = provider.getElementClassName();
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		String entityClassName = provider.getEntityClassName();
		String entityBeanName = provider.getEntityBeanName();
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	resultClassName = "M";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toModel");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(resultClassName);
		modelOperation.addParameter(createParameter(entityClassName, entityBeanName));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModel(namespace, element, null));
		return modelOperation;
	}

	
	/*
	 * toModelList() methods
	 */
	
	protected List<ModelOperation> createInstanceOperations_ToModelCollection(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		boolean inverseRelationFound = false;
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isInverse(field)) {
				ModelOperation modelOperation = createInstanceOperation_ToModelList(element, field, interfaceOnly);
				modelOperations.add(modelOperation);
				inverseRelationFound = true;
			}
		}

		if (inverseRelationFound == false) {
			ModelOperation modelOperation = createInstanceOperation_ToModelList(element, interfaceOnly);
			modelOperations.add(modelOperation);
		}
		
		return modelOperations;
	}
	
	protected ModelOperation createInstanceOperation_ToModelList(Element element, Field field, boolean interfaceOnly) {
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldName = ModelLayerHelper.getFieldNameUncapped(field);
		String structure = field.getStructure();
		if (structure.equals("list"))
			fieldClassName = "List<"+fieldClassName+">";
		else if (structure.equals("set"))
			fieldClassName = "Set<"+fieldClassName+">";
		else if (structure.equals("map"))
			fieldClassName = "Map<"+field.getKey()+", "+fieldClassName+">";

		ModelOperation modelOperation = createInstanceOperation_ToModelList(element, true);
		modelOperation.getParameters().add(0, createParameter(fieldClassName, fieldName+"Model"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModelList(namespace, element, field));
		return modelOperation;
	}
	
	protected ModelOperation createInstanceOperation_ToModelList(Element element, boolean interfaceOnly) {
		String entityClassName = provider.getEntityClassName();
		String entityBeanName = provider.getEntityBeanName();
		
		//String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = provider.getElementClassName();
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	resultClassName = "M";
		//}

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toModelList");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+resultClassName+">");
		modelOperation.addParameter(createParameter("Collection<"+entityClassName+">", entityBeanName+"List"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModelList(namespace, element, null));
		return modelOperation;
	}
	
	
	/*
	 * toEntity() methods
	 */

	protected List<ModelOperation> createInstanceOperations_ToEntity(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		boolean inverseRelationFound = false;
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isInverse(field)) {
				ModelOperation modelOperation = createInstanceOperation_ToEntity(element, field, interfaceOnly);
				modelOperations.add(modelOperation);
				inverseRelationFound = true;
			}
		}

		if (inverseRelationFound == false) {
			ModelOperation modelOperation = createInstanceOperation_ToEntity(element, interfaceOnly);
			modelOperations.add(modelOperation);
		}

		return modelOperations;
	}
		
	protected ModelOperation createInstanceOperation_ToEntity(Element element, Field field, boolean interfaceOnly) {
		String fieldClassName = DataLayerHelper.getFieldEntityClassName(field);
		String fieldName = ModelLayerHelper.getFieldNameUncapped(field) + "Entity";
		String structure = field.getStructure();
		if (structure.equals("list"))
			fieldClassName = "List<"+fieldClassName+">";

		ModelOperation modelOperation = createInstanceOperation_ToEntity(element, true);
		modelOperation.getParameters().add(0, createParameter(fieldClassName, fieldName));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToEntity(namespace, element, field));
		return modelOperation;
	}
	
	protected ModelOperation createInstanceOperation_ToEntity(Element element, boolean interfaceOnly) {
		String entityClassName = provider.getEntityClassName();
		String elementClassName = provider.getElementClassName();
		String elementBeanName = provider.getElementBeanName();
		
		//String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = entityClassName;
		//if (isExtendsAbstractParent(element)) {
		//	elementClassName = "M";
		//	resultClassName = "E";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toEntity");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(resultClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementBeanName+"Model"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToEntity(namespace, element, null));
		return modelOperation;
	}
	
	
	/*
	 * toEntity2() methods
	 */
	
	protected List<ModelOperation> createInstanceOperations_ToEntity2(Namespace namespace, Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		boolean inverseRelationFound = false;
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isInverse(field) && !FieldUtil.isContained(field)) {
				ModelOperation modelOperation = createInstanceOperation_ToEntity2(namespace, element, field, interfaceOnly);
				modelOperations.add(modelOperation);
				inverseRelationFound = true;
			}
		}
		
		if (inverseRelationFound == false) {
			ModelOperation modelOperation = createInstanceOperation_ToEntity2(namespace, element, interfaceOnly);
			modelOperations.add(modelOperation);
		}
		
		return modelOperations;
	}
	
	protected ModelOperation createInstanceOperation_ToEntity2(Namespace namespace, Element element, Field field, boolean interfaceOnly) {
		String fieldClassName = DataLayerHelper.getFieldEntityClassName(field);
		String fieldName = ModelLayerHelper.getFieldNameUncapped(field) + "Entity";
		String structure = field.getStructure();
		if (structure.equals("list"))
			fieldClassName = "List<"+fieldClassName+">";

		ModelOperation modelOperation = createInstanceOperation_ToEntity2(namespace, element, true);
		modelOperation.getParameters().add(0, createParameter(fieldClassName, fieldName));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToEntity2(namespace, element, field));
		return modelOperation;
	}
	
	protected ModelOperation createInstanceOperation_ToEntity2(Namespace namespace, Element element, boolean interfaceOnly) {
		String entityClassName = provider.getEntityClassName();
		String entityBeanName = provider.getEntityBeanName();
		String elementClassName = provider.getElementClassName();
		String elementBeanName = provider.getElementBeanName();

		//String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		//if (isExtendsAbstractParent(element)) {
		//	elementClassName = "M";
		//	entityClassName = "E";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toEntity");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(entityClassName, entityBeanName));
		modelOperation.addParameter(createParameter(elementClassName, elementBeanName+"Model"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToEntity2(namespace, element, null));
		return modelOperation;
	}
	
	
	/*
	 * toEntityCollection() methods
	 */
	
//	protected List<ModelOperation> createInstanceOperations_ToEntityList(Element element, boolean interfaceOnly) {
//		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
//		ModelOperation modelOperation = createInstanceOperation_ToEntityList(element, interfaceOnly);
//		modelOperations.add(modelOperation);
//
//		List<Field> fields = ElementUtil.getFields(element);
//		Iterator<Field> iterator = fields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			if (FieldUtil.isInverse(field)) {
//				modelOperation = createInstanceOperation_ToModel(element, field, interfaceOnly);
//				modelOperations.add(modelOperation);
//			}
//		}
//		return modelOperations;
//	}
//	
//	protected List<ModelOperation> createInstanceOperation_ToEntityList(Element element, boolean interfaceOnly) {
//		ModelOperation modelOperation = createInterfaceOperation_ToEntityList(element);
//		modelOperation.addInitialSource(provider.getSourceCode_ToEntityList(element));
//		return modelOperation;
//	}
	
	protected List<ModelOperation> createInstanceOperations_ToEntityCollection(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		boolean inverseRelationFound = false;
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isInverse(field)) {
				ModelOperation modelOperation = createInstanceOperation_ToEntityList(element, field, interfaceOnly);
				modelOperations.add(modelOperation);
				inverseRelationFound = true;
			}
		}
		
		if (inverseRelationFound == false) {
			ModelOperation modelOperation = createInstanceOperation_ToEntityList(element, interfaceOnly);
			modelOperations.add(modelOperation);
		}
		
		return modelOperations;
	}
	
	protected ModelOperation createInstanceOperation_ToEntityList(Element element, Field field, boolean interfaceOnly) {
		String fieldClassName = DataLayerHelper.getFieldEntityClassName(field);
		String fieldName = ModelLayerHelper.getFieldNameUncapped(field) + "Entity";
		String structure = field.getStructure();
		if (structure.equals("list"))
			fieldClassName = "List<"+fieldClassName+">";
		else if (structure.equals("set"))
			fieldClassName = "Set<"+fieldClassName+">";
		else if (structure.equals("map"))
			fieldClassName = "Map<"+field.getKey()+", "+fieldClassName+">";

		ModelOperation modelOperation = createInstanceOperation_ToEntityList(element, true);
		modelOperation.getParameters().add(0, createParameter(fieldClassName, fieldName));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToEntityList(namespace, element, field));
		return modelOperation;
	}
	
	protected ModelOperation createInstanceOperation_ToEntityList(Element element, boolean interfaceOnly) {
		String entityClassName = provider.getEntityClassName();
		String elementClassName = provider.getElementClassName();
		String elementBeanName = provider.getElementBeanName();
		String resultClassName = entityClassName;
		//if (isExtendsAbstractParent(element)) {
		//	elementClassName = "M";
		//	resultClassName = "E";
		//}
		
//		String structure = element.getStructure();
//		if (structure.equals("list"))
//			fieldClassName = "List<"+fieldClassName+">";
//		else if (structure.equals("set"))
//			fieldClassName = "Set<"+fieldClassName+">";
//		else if (structure.equals("map"))
//			fieldClassName = "Map<"+field.getKey()+", "+fieldClassName+">";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toEntityList");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+resultClassName+">");
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementBeanName+"ModelList"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToEntityList(namespace, element, null));
		return modelOperation;
	}

	/*
	 * toEntity_SelfReferencing() methods
	 */

	protected ModelOperation createInstanceOperation_ToModel_SelfReferencing(Element element, boolean interfaceOnly) {
		String entityClassName = provider.getEntityClassName();
		String elementClassName = provider.getElementClassName();
		String elementBeanName = provider.getElementBeanName();
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	resultClassName = "M";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toModel");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(resultClassName);
		modelOperation.addParameter(createParameter(entityClassName, elementBeanName+"Entity"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModel_SelfReferencing(element));
		return modelOperation;
	}
	
	protected ModelOperation createInstanceOperation_ToModel_SelfReferencing2(Element element, boolean interfaceOnly) {
		String entityClassName = provider.getEntityClassName();
		String elementClassName = provider.getElementClassName();
		String elementBeanName = provider.getElementBeanName();
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		//String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	elementClassName = "M";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createSuppressWarning("unchecked"));
		modelOperation.setName("toModel");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, "parent"+elementNameCapped));
		modelOperation.addParameter(createParameter(entityClassName, elementBeanName+"Entity"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModel_SelfReferencing2(element));
		return modelOperation;
	}
	
	protected ModelOperation createInstanceOperation_ToModel_SelfReferencing_Flat(Element element, boolean interfaceOnly) {
		String elementBeanName = provider.getElementBeanName();
		String elementNameCapped = provider.getElementNameCapped();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();
		//String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	elementClassName = "M";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toModelFlat");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(elementClassName);
		modelOperation.addParameter(createParameter(elementClassName, "parent"+elementNameCapped));
		modelOperation.addParameter(createParameter(entityClassName, elementBeanName+"Entity"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModel_SelfReferencing_Flat(namespace, element));
		return modelOperation;
	}
	
	protected ModelOperation createInstanceOperation_ToModelCollection_Entity_SelfReferencing(Element element, boolean interfaceOnly) {
		String elementBeanName = provider.getElementBeanName();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();
		//String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	elementClassName = "M";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toModelList");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addParameter(createParameter("Collection<"+entityClassName+">", elementBeanName+"EntityList"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModelList_SelfReferencing(namespace, element));
		return modelOperation;
	}

	protected ModelOperation createInstanceOperation_ToModelCollection_Entity_SelfReferencing2(Element element, boolean interfaceOnly) {
		String elementBeanName = provider.getElementBeanName();
		String elementNameCapped = provider.getElementNameCapped();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	resultClassName = "M";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toModelList");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+resultClassName+">");
		modelOperation.addParameter(createParameter(elementClassName, "parent"+elementNameCapped));
		modelOperation.addParameter(createParameter("Collection<"+entityClassName+">", elementBeanName+"EntityList"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModelList_SelfReferencing2(namespace, element));
		return modelOperation;
	}

	
	/*
	 * toEntity_2WaySelfReferencing() methods
	 */

	protected ModelOperation createInstanceOperation_ToModel_2WaySelfReferencing(Element element, boolean interfaceOnly) {
		String elementBeanName = provider.getElementBeanName();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	resultClassName = "M";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toModel");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(resultClassName);
		modelOperation.addParameter(createParameter(entityClassName, elementBeanName+"Entity"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModel_2WaySelfReferencing(element));
		return modelOperation;
	}

	protected ModelOperation createInstanceOperation_ToModel_2WaySelfReferencing_Flat(Element element, boolean interfaceOnly) {
		String elementBeanName = provider.getElementBeanName();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	resultClassName = "M";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toModelFlat");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(resultClassName);
		modelOperation.addParameter(createParameter(entityClassName, elementBeanName+"Entity"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModel_2WaySelfReferencing_Flat(namespace, element));
		return modelOperation;
	}

	protected ModelOperation createInstanceOperation_ToModelCollection_Entity_2WaySelfReferencing(Element element, boolean interfaceOnly) {
		String elementBeanName = provider.getElementBeanName();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	entityClassName = "E";
		//	resultClassName = "M";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toModelList");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+resultClassName+">");
		modelOperation.addParameter(createParameter("Collection<"+entityClassName+">", elementBeanName+"EntityList"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToModelList_2WaySelfReferencing(namespace, element));
		return modelOperation;
	}

	protected ModelOperation createInstanceOperation_ToEntity_2WaySelfReferencing(Element element, boolean interfaceOnly) {
		String elementBeanName = provider.getElementBeanName();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	elementClassName = "M";
		//	resultClassName = "E";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toEntity");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(resultClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementBeanName+"Model"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToEntity_2WaySelfReferencing(namespace, element));
		return modelOperation;
	}

	protected ModelOperation createInstanceOperation_ToEntity_2WaySelfReferencing_Flat(Namespace namespace, Element element, boolean interfaceOnly) {
		String elementBeanName = provider.getElementBeanName();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	elementClassName = "M";
		//	resultClassName = "E";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toEntityFlat");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(resultClassName);
		modelOperation.addParameter(createParameter(elementClassName, elementBeanName+"Model"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToEntity_2WaySelfReferencing_Flat(namespace, element));
		return modelOperation;
	}

	protected ModelOperation createInstanceOperation_ToEntityCollection_2WaySelfReferencing(Namespace namespace, Element element, boolean interfaceOnly) {
		String elementBeanName = provider.getElementBeanName();
		String elementClassName = provider.getElementClassName();
		String entityClassName = provider.getEntityClassName();
		String resultClassName = elementClassName;
		//if (isExtendsAbstractParent(element)) {
		//	elementClassName = "M";
		//	resultClassName = "E";
		//}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("toEntityList");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+resultClassName+">");
		modelOperation.addParameter(createParameter("Collection<"+elementClassName+">", elementBeanName+"ModelList"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ToEntityList_2WaySelfReferencing(namespace, element));
		return modelOperation;
	}


}
