package nam.data.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.data.DataLayerFactory;
import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Elements;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Properties;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;
import aries.generation.model.ModelUnit;


/**
 * Builds a Manager Bean from an Aries Element or Aries Namespace;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ManagerBeanBuilder extends AbstractManagementBeanBuilder {

	private ManagerBeanProvider provider;
	
	
	public ManagerBeanBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		provider = new ManagerBeanProvider(context);
		initialize(provider);
	}

	/*
	 * Manager interface creation
	 * --------------------------
	 */

	public List<ModelInterface> buildInterfaces(List<Unit> units) throws Exception {
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
		modelInterfaces.addAll(buildInterfaces(unit, elements));
		return modelInterfaces;
	}
	
	protected Collection<? extends ModelInterface> buildInterfaces(Unit unit, List<Element> elements) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Element> iterator = elements.iterator();
		this.namespace = unit.getNamespace();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!ElementUtil.isTransient(element)) {
				ModelInterface modelInterface = buildInterface(namespace, element);
				modelInterfaces.add(modelInterface);
			}
		}
		return modelInterfaces;
	}

	public ModelInterface buildInterface(Namespace namespace, Element element) throws Exception {
		ModelInterface modelInterface = new ModelInterface();
		String packageName = DataLayerHelper.getManagerPackageName(namespace);
		String interfaceName = DataLayerHelper.getManagerInterfaceName(element);
		String parentInterfaceName = DataLayerHelper.getManagerParentInterfaceName(element);
		this.namespace = namespace;

		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		if (parentInterfaceName != null)
			modelInterface.addExtendedInterface(parentInterfaceName);
		modelInterface.setName(NameUtil.uncapName(interfaceName));

		provider.modelUnit = modelInterface;
		initializeInterface(modelInterface, element);
		return modelInterface;
	}
	
	protected void initializeInterface(ModelInterface modelInterface, Element element) throws Exception {
		initializeImportedClasses(modelInterface, element);
		initializeInterfaceMethods(modelInterface, element);
	}

	protected void initializeImportedClasses(ModelInterface modelInterface, Element element) throws Exception {
		super.addImportedClassForElement(modelInterface, element);
		String parentClassName = DataLayerHelper.getManagerParentInterfaceName(element);
		if (parentClassName != null) {
			modelInterface.addImportedClass(parentClassName);
		}
	}
	
	protected void initializeInterfaceMethods(ModelInterface modelInterface, Element element) throws Exception {
		modelInterface.addInstanceOperation(createInstanceOperation_Initialize(element, true));
		modelInterface.addInstanceOperation(createInstanceOperation_ClearContext(element, true));
		initializeInstanceMethods(modelInterface, element, true);
	}
	
	
	/*
	 * Manager class creation
	 * ----------------------
	 */
	
	public Collection<ModelClass> buildClasses(Unit unit) throws Exception {
		Set<ModelClass> modelClasses = new HashSet<ModelClass>(); 
		//modelClasses.addAll(buildClasses(unit.getNamespace()));
		modelClasses.addAll(buildClasses(unit.getNamespace(), unit.getElements()));
		return modelClasses;
	}
	
	public Collection<ModelClass> buildClasses(List<Namespace> namespaces) throws Exception {
		Set<ModelClass> modelClasses = new HashSet<ModelClass>(); 
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			modelClasses.addAll(buildClasses(namespace));
		}
		return modelClasses;
	}
	
	public Set<ModelClass> buildClasses(Namespace namespace) throws Exception {
		this.namespace = namespace;
		
		Set<ModelClass> modelClasses = new HashSet<ModelClass>(); 
		if (context.isEnabled("generate-imported-namespaces"))
			modelClasses.addAll(buildClasses(namespace.getImports()));

		Properties properties = NamespaceUtil.getProperties(namespace);
		boolean isImported = NamespaceUtil.isImported(namespace);
		//boolean isImported = PropertyUtil.isEnabled(properties, "imported");
		if (!context.isEnabled("generate-imported-namespaces") && isImported)
			return modelClasses;
		
		List<Element> elements = NamespaceUtil.getElements(namespace);
		modelClasses.addAll(buildClasses(namespace, elements));
		return modelClasses;
	}
	
	public Collection<ModelClass> buildClasses(Namespace namespace, Elements elements) throws Exception {
		List<Element> list = ElementUtil.getElements(elements);
		return buildClasses(namespace, list);
	}
	
	public Set<ModelClass> buildClasses(Namespace namespace, List<Element> elements) throws Exception {
		Set<ModelClass> modelClasses = new HashSet<ModelClass>(); 
		Iterator<Element> iterator = elements.iterator();
		this.namespace = namespace;
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			ModelClass modelClass = buildClass(namespace, element);
			modelClasses.add(modelClass);
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(Namespace namespace, Element element) throws Exception {
		String packageName = DataLayerHelper.getManagerPackageName(namespace);
		String interfaceName = DataLayerHelper.getManagerInterfaceName(element);
		String className = DataLayerHelper.getManagerClassName(element);
		String parentClassName = DataLayerHelper.getManagerParentClassName(element);
		this.namespace = namespace;
		
		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		if (parentClassName != null)
			modelClass.setParentClassName(parentClassName);
		modelClass.setName(NameUtil.uncapName(interfaceName));
		modelClass.setNamespace(namespace.getUri());
		modelClass.addImplementedInterface(interfaceName);
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		
		provider.modelUnit = modelClass;
		initializeClass(modelClass, element);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		initializeClassAnnotations(modelClass, element);
		initializeImportedClasses(modelClass, element);
		initializeInstanceFields(modelClass, element);
		initializeInstanceMethods(modelClass, element);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		switch (context.getDataLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createStatefulAnnotation());
			String interfaceName = DataLayerHelper.getManagerInterfaceName(element);
			classAnnotations.add(AnnotationUtil.createLocalAnnotation(interfaceName));
			classAnnotations.add(AnnotationUtil.createConcurrencyManagementAnnotation());
			break;
		case SEAM:
			//classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
			//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
			//String contextName = DataLayerHelper.getManagerContextName(namespace, element);
			//classAnnotations.add(AnnotationUtil.createNameAnnotation(contextName));
			break;
		}
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Element element) throws Exception {
		addImportedClassForElement(modelClass, element);
		
		modelClass.addImportedClass("java.util.List");
		modelClass.addImportedClass("org.aries.Assert");

		switch (context.getDataLayerBeanType()) {
		case EJB:
			modelClass.addImportedClass("javax.annotation.PostConstruct");
			modelClass.addImportedClass("javax.ejb.ConcurrencyManagement");
			modelClass.addImportedClass("javax.ejb.Local");
			modelClass.addImportedClass("javax.ejb.Stateful");
			modelClass.addImportedClass("javax.enterprise.inject.New");
			modelClass.addImportedClass("javax.inject.Inject");
			modelClass.addImportedClass("javax.persistence.PersistenceContext");
			modelClass.addImportedClass("javax.persistence.EntityManager");
			modelClass.addStaticImportedClass("javax.ejb.ConcurrencyManagementType.BEAN");
			modelClass.addStaticImportedClass("javax.persistence.PersistenceContextType.EXTENDED");
			break;
		case SEAM:
			//modelClass.addImportedClass("org.jboss.seam.ScopeType");
			//modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
			//modelClass.addImportedClass("org.jboss.seam.annotations.In");
			//modelClass.addImportedClass("org.jboss.seam.annotations.Name");
			//modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
			//modelClass.addImportedClass("org.jboss.seam.annotations.Transactional");
			break;
		}
		
//		List<Field> fields = ElementUtil.getFields(element);
//		Iterator<Field> iterator = fields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
//			String fieldClassName = ModelLayerHelper.getFieldTypeLocalPartCapped(field);
//			modelClass.addImportedClass(fieldPackageName + "." + fieldClassName);
//		}
		
//		List<Element> types = UnitUtil.getElementsFromNamespace(namespace, true);
//		Iterator<Element> iterator = types.iterator();
//		while (iterator.hasNext()) {
//			Element element = iterator.next();
//			String mapperPackageName = DataLayerHelper.getMapperPackageName(namespace);
//			String mapperInterfaceName = DataLayerHelper.getMapperInterfaceName(element);
//			String importerPackageName = DataLayerHelper.getImporterPackageName(namespace);
//			String importerInterfaceName = DataLayerHelper.getImporterInterfaceName(element);
//			modelClass.addImportedClass(mapperPackageName + "." + mapperInterfaceName);
//			modelClass.addImportedClass(importerPackageName + "." + importerInterfaceName);
//		}
//		
//		List<Element> elements = UnitUtil.getElements(unit);
//		Iterator<Element> iterator2 = elements.iterator();
//		while (iterator2.hasNext()) {
//			Element element = iterator2.next();
//			String elementPackageName = ModelLayerHelper.getElementPackageName(element);
//			String elementClassName = ModelLayerHelper.getElementClassName(element);
//			String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
//			String entityClassName = DataLayerHelper.getEntityClassName(element);
//			String daoPackageName = DataLayerHelper.getDAOPackageName(namespace);
//			String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
//			modelClass.addImportedClass(elementPackageName + "." + elementClassName);
//			modelClass.addImportedClass(entityPackageName + "." + entityClassName);
//			modelClass.addImportedClass(daoPackageName + "." + daoInterfaceName);
//		}
	}

	protected void addImportedClassForElement(ModelUnit modelUnit, Element element) {
		super.addImportedClassForElement(modelUnit, element);
		String parentClassName = DataLayerHelper.getManagerParentInterfaceName(element);
		if (parentClassName != null) {
			modelUnit.addImportedClass(parentClassName);
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		ModelReference entityManagerReference = DataLayerFactory.createEntityManagerReference(element);
		modelClass.addInstanceReference(entityManagerReference);

		switch (context.getDataLayerBeanType()) {
		case EJB:
			String unitName = NameUtil.uncapName(context.getUnit().getName());
			entityManagerReference.addAnnotation(AnnotationUtil.createPersistenceContextAnnotation(unitName, "EXTENDED"));
			break;
		case SEAM:
			//String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
			//String contextPrefix = NameUtil.getQualifiedContextNamePrefix(elementQualifiedName, 1).toLowerCase();
			//entityManagerReference.addAnnotation(AnnotationUtil.createInAnnotation(true, contextPrefix+".entityManager"));
			break;
		}
		
		if (ElementUtil.isRoot(element))
			modelClass.addInstanceReference(createInstanceField_ImporterBean(element));
		modelClass.addInstanceReference(createInstanceField_MapperBean(element));
		modelClass.addInstanceReference(createInstanceField_DAOBean(element));
		CodeUtil.addTransactionSynchronizationRegistryField(modelClass);
	}

	protected ModelReference createInstanceField_ImporterBean(Element element) {
		String packageName = DataLayerHelper.getImporterPackageName(namespace);
		String interfaceName = DataLayerHelper.getImporterInterfaceName(element);
		String beanName = DataLayerHelper.getImporterNameUncapped(element);
		String beanType = org.aries.util.TypeUtil.getDerivedType(element.getType(), beanName);
		String contextName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);

		ModelReference modelReference = new ModelReference();
		modelReference.addImportedClass(packageName + "." + interfaceName);
		modelReference.getAnnotations().add(createInstanceField_InjectionAnnotation(packageName, interfaceName, contextName));
		modelReference.setModifiers(Modifier.PROTECTED);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(interfaceName);
		modelReference.setName(beanName);
		modelReference.setType(beanType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	protected ModelReference createInstanceField_MapperBean(Element element) {
		String packageName = DataLayerHelper.getMapperPackageName(namespace);
		//String interfaceName = DataLayerHelper.getMapperInterfaceName(element);
		String className = DataLayerHelper.getInferredMapperClassName(namespace, element);
		String beanName = DataLayerHelper.getInferredMapperBeanName(namespace, element);
		String beanType = org.aries.util.TypeUtil.getDerivedType(element.getType(), beanName);
		String contextName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		
		ModelReference modelReference = new ModelReference();
		modelReference.addImportedClass(packageName + "." + className);
		modelReference.addImportedClass(elementPackageName + "." + elementClassName);
		modelReference.addImportedClass(entityPackageName + "." + entityClassName);
		modelReference.getAnnotations().add(createInstanceField_InjectionAnnotation(packageName, className, contextName));
		modelReference.setModifiers(Modifier.PROTECTED);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		//modelReference.setClassName(interfaceName+"<"+entityClassName+", "+elementClassName+">");
		modelReference.setName(beanName);
		modelReference.setType(beanType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	protected ModelReference createInstanceField_DAOBean(Element element) {
		String packageName = DataLayerHelper.getDAOPackageName(namespace);
		String className = DataLayerHelper.getDAOClassName(element);
		String interfaceName = DataLayerHelper.getDAOInterfaceName(element);
		String beanName = DataLayerHelper.getDAONameUncapped(element);
		String beanType = org.aries.util.TypeUtil.getDerivedType(element.getType(), beanName);
		String contextName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
		String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		
		ModelReference modelReference = new ModelReference();
		modelReference.addImportedClass(packageName + "." + className);
		modelReference.addImportedClass(packageName + "." + interfaceName);
		modelReference.addImportedClass(entityPackageName + "." + entityClassName);
		modelReference.getAnnotations().add(createInstanceField_InjectionAnnotation(packageName, interfaceName, contextName));
		modelReference.getAnnotations().add(createInstanceField_CreationAnnotation(className));
		modelReference.setModifiers(Modifier.PROTECTED);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(interfaceName+"<"+entityClassName+">");
		modelReference.setName(beanName);
		modelReference.setType(beanType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Unit unit) throws Exception {
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			provider.setElement(element);
			initializeInstanceMethods(modelClass, element);
		}
	}
	
	protected void initializeInstanceMethods(ModelClass modelClass, Element element) throws Exception {
		modelClass.addInstanceOperation(createInstanceOperation_ClearContext(element, false));
		modelClass.addInstanceOperation(createInstanceOperation_Initialize(element, false));
		initializeInstanceMethods(modelClass, element, false);
	}

	
	/**
	 * ClearContext operation
	 * ----------------------
	 */
	protected ModelOperation createInstanceOperation_ClearContext(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("clearContext");
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_ClearContext(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * Initialize operation
	 * --------------------
	 */
	protected ModelOperation createInstanceOperation_Initialize(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize");
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createPostConstructAnnotation());
			provider.generateSourceCode_Initialize(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * AddElement operation
	 * --------------------
	 */
	protected ModelOperation createInstanceOperation_AddElement(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_AddElement(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}
	
	/**
	 * AddElementList operation
	 * ------------------------
	 */
	protected ModelOperation createInstanceOperation_AddElementList(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_AddElementList(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}
	
	/**
	 * AddElementMap operation
	 * ------------------------
	 */
	protected ModelOperation createInstanceOperation_AddElementMap(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_AddElementMap(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}
	
	/**
	 * MoveElement operation
	 * ---------------------
	 */
	protected ModelOperation createInstanceOperation_MoveElement(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_MoveElement(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}
	
	/**
	 * SaveElement operation
	 * ---------------------
	 */
	protected ModelOperation createInstanceOperation_SaveElement(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_SaveElement(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}
	
	/**
	 * SaveElementList operation
	 * -------------------------
	 */
	protected ModelOperation createInstanceOperation_SaveElementList(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_SaveElementList(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}
	
	/**
	 * SaveElementMap operation
	 * ------------------------
	 */
	protected ModelOperation createInstanceOperation_SaveElementMap(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_SaveElementMap(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}
	
	/**
	 * RemoveElement operation
	 * -----------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElement(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_RemoveElement(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}

	/**
	 * RemoveElementList operation
	 * ---------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElementList(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_RemoveElementList(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}
	
	/**
	 * RemoveElementMap operation
	 * ---------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElementMap(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_RemoveElementMap(element, interfaceOnly);
		if (interfaceOnly == false) {
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
		}
		return modelOperation;
	}

}
