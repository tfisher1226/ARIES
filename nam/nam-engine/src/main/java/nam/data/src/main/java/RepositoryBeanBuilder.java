package nam.data.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


/**
 * Builds a Repository Bean from an Aries Element or Aries Namespace;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class RepositoryBeanBuilder extends AbstractManagementBeanBuilder {

	private RepositoryBeanProvider provider;
	
	
	public RepositoryBeanBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		provider = new RepositoryBeanProvider(context);
		initialize(provider);
	}

	/*
	 * Repository interface creation
	 * -----------------------------
	 */

	public ModelInterface buildInterface(Unit unit) throws Exception {
		ModelInterface modelInterface = new ModelInterface();
		String packageName = DataLayerHelper.getRepositoryPackageName(unit);
		String interfaceName = DataLayerHelper.getRepositoryInterfaceName(unit);
		String parentInterfaceName = DataLayerHelper.getRepositoryParentInterfaceName(unit);
		namespace = unit.getNamespace();

		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		if (parentInterfaceName != null)
			modelInterface.addExtendedInterface(parentInterfaceName);
		modelInterface.setName(NameUtil.uncapName(interfaceName));
		initializeInterface(modelInterface, unit);
		return modelInterface;
	}
	
//	public List<ModelInterface> buildInterfaces(Unit unit) throws Exception {
//		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>(); 
//		modelInterfaces.addAll(buildInterfaces(unit.getNamespace()));
//		modelInterfaces.addAll(buildInterfaces(unit.getNamespace(), unit.getElements()));
//		return modelInterfaces;
//	}
	
//	public List<ModelInterface> buildInterfaces(List<Namespace> namespaces) throws Exception {
//		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
//		Iterator<Namespace> iterator = namespaces.iterator();
//		while (iterator.hasNext()) {
//			Namespace namespace = iterator.next();
//			modelInterfaces.addAll(buildInterfaces(namespace));
//		}
//		return modelInterfaces;
//	}
	
//	public List<ModelInterface> buildInterfaces(Namespace namespace) throws Exception {
//		this.namespace = namespace;
//		List<Element> elements = NamespaceUtil.getElements(namespace);
//		List<ModelInterface> modelInterfaces = buildInterfaces(namespace, elements);
//		return modelInterfaces;
//	}
//	
//	public List<ModelInterface> buildInterfaces(Namespace namespace, Elements elements) throws Exception {
//		List<Element> list = ElementUtil.getElements(elements);
//		return buildInterfaces(namespace, list);
//	}
//	
//	public List<ModelInterface> buildInterfaces(Namespace namespace, List<Element> elements) throws Exception {
//		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
//		Iterator<Element> iterator = elements.iterator();
//		while (iterator.hasNext()) {
//			Element element = iterator.next();
//			if (ElementUtil.isAbstract(element))
//				continue;
//			if (ElementUtil.isTransient(element))
//				continue;
//			ModelInterface modelInterface = buildInterface(namespace, element);
//			modelInterfaces.add(modelInterface);
//		}
//		return modelInterfaces;
//	}
	
//	public ModelInterface buildInterface(Namespace namespace, Element element) throws Exception {
//		ModelInterface modelInterface = new ModelInterface();
//		String packageName = DataLayerHelper.getRepositoryPackageName(namespace);
//		String interfaceName = DataLayerHelper.getRepositoryInterfaceName(element);
//		String parentInterfaceName = DataLayerHelper.getRepositoryParentInterfaceName(element);
//
//		modelInterface.setPackageName(packageName);
//		modelInterface.setClassName(interfaceName);
//		if (parentInterfaceName != null)
//			modelInterface.addExtendedInterface(parentInterfaceName);
//		modelInterface.setName(NameUtil.uncapName(interfaceName));
//		initializeInterface(modelInterface, element);
//		return modelInterface;
//	}
	
	protected void initializeInterface(ModelInterface modelInterface, Unit unit) throws Exception {
		//initializeInterfaceAnnotations(modelInterface);
		initializeImportedClasses(modelInterface, unit);
		initializeInterfaceMethods(modelInterface, unit);
	}

	protected void initializeInterfaceMethods(ModelInterface modelInterface, Unit unit) throws Exception {
		modelInterface.addInstanceOperation(createInstanceOperation_ClearContext(unit, true));
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			Field field = context.findFieldByName(element, "id");
			if (field != null) {
				initializeInterfaceMethods(modelInterface, element);
			}
		}
	}

	protected void initializeImportedClasses(ModelInterface modelInterface, Unit unit) throws Exception {
		String parentClassName = DataLayerHelper.getRepositoryParentInterfaceName(unit);

		if (parentClassName != null)
			modelInterface.addImportedClass(parentClassName);
		modelInterface.addImportedClass("java.util.List");
		
		List<Element> elements = UnitUtil.getElements(unit);
		addImportedClassesForElements(modelInterface, elements);
	}


	/*
	 * Repository class creation
	 * -------------------------
	 */
	
	public ModelClass buildClass(Unit unit) throws Exception {
		String packageName = DataLayerHelper.getRepositoryPackageName(namespace);
		String interfaceName = DataLayerHelper.getRepositoryInterfaceName(unit);
		String className = DataLayerHelper.getRepositoryClassName(unit);
		String parentClassName = DataLayerHelper.getRepositoryParentClassName(unit);
		this.namespace = unit.getNamespace();
		
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
		initializeClass(modelClass, unit);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Unit unit) throws Exception {
		initializeClassAnnotations(modelClass, unit);
		initializeImportedClasses(modelClass, unit);
		initializeInstanceFields(modelClass, unit);
		initializeInstanceMethods(modelClass, unit);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Unit unit) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		switch (context.getDataLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createStatefulAnnotation());
			String interfaceName = DataLayerHelper.getRepositoryInterfaceName(unit);
			classAnnotations.add(AnnotationUtil.createLocalAnnotation(interfaceName));
			break;
		case SEAM:
			//classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
			//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
			//String contextName = DataLayerHelper.getRepositoryContextName(unit);
			//classAnnotations.add(AnnotationUtil.createNameAnnotation(contextName));
			break;
		}
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Unit unit) throws Exception {
//		String parentClassName = DataLayerHelper.getRepositoryParentClassName(element);
//		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
//		String elementClassName = ModelLayerHelper.getElementClassName(element);
//		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
//		String entityClassName = DataLayerHelper.getEntityClassName(element);
//		String daoPackageName = DataLayerHelper.getDAOPackageName(namespace);
//		String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
//		String mapperPackageName = DataLayerHelper.getMapperPackageName(namespace);
//		String mapperInterfaceName = DataLayerHelper.getMapperInterfaceName(element);
//		String importerPackageName = DataLayerHelper.getImporterPackageName(namespace);
//		String importerInterfaceName = DataLayerHelper.getImporterInterfaceName(element);

		String parentClassName = DataLayerHelper.getRepositoryParentClassName(unit);
		if (parentClassName != null)
			modelClass.addImportedClass(parentClassName);
		modelClass.addImportedClass("java.util.List");
		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");

		switch (context.getDataLayerBeanType()) {
		case EJB:
			modelClass.addImportedClass("javax.ejb.Local");
			modelClass.addImportedClass("javax.ejb.Stateful");
			modelClass.addImportedClass("javax.inject.Inject");
			break;
		case SEAM:
			modelClass.addImportedClass("org.jboss.seam.ScopeType");
			modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
			modelClass.addImportedClass("org.jboss.seam.annotations.In");
			modelClass.addImportedClass("org.jboss.seam.annotations.Name");
			modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
			//modelClass.addImportedClass("org.jboss.seam.annotations.Transactional");
			break;
		}
		
		List<Element> elements = UnitUtil.getElements(unit);
		addImportedClassesForElements(modelClass, elements);
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Unit unit) throws Exception {
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isTransient(element))
				continue;
			ModelReference modelReference = createInstanceField_ManagerBean(element);
			modelClass.addInstanceReference(modelReference);
			modelClass.addImportedClass(modelReference);
		}
	}

	protected ModelReference createInstanceField_ManagerBean(Element element) {
		String packageName = DataLayerHelper.getManagerPackageName(namespace);
		String interfaceName = DataLayerHelper.getManagerInterfaceName(element);
		String beanName = DataLayerHelper.getManagerNameUncapped(element);
		String beanType = org.aries.util.TypeUtil.getDerivedType(element.getType(), beanName);
		String contextName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);

		ModelReference modelReference = new ModelReference();
		modelReference.getAnnotations().add(createInstanceField_InjectionAnnotation(packageName, interfaceName, contextName));
		modelReference.setModifiers(Modifier.PROTECTED);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(interfaceName);
		modelReference.setName(beanName);
		modelReference.setType(beanType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(true);
		modelReference.setGenerateSetter(true);
		return modelReference;
	}

//	protected ModelReference createInstanceField_ImporterBean(Element element) {
//		String importerNameUncapped = DataLayerHelper.getImporterNameUncapped(element);
//		String importerPackageName = DataLayerHelper.getImporterPackageName(namespace);
//		String importerInterfaceName = DataLayerHelper.getImporterInterfaceName(element);
//		String importerType = TypeUtil.getDerivedType(element.getType(), importerNameUncapped);
//
//		ModelReference modelReference = new ModelReference();
//		switch (context.getDataLayerBeanType()) {
//		case EJB:
//			modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
//			break;
//		case SEAM:
//			String importerContextName = importerNameUncapped;
//			if (context.isEnabled("useQualifiedContextNames")) {
//				String qualifiedName = importerPackageName + "." + importerInterfaceName;
//				int segmentCount = NameUtil.getSegmentCount(qualifiedName);
//				String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-1);
//				importerContextName = contextPrefix + "." + importerNameUncapped;
//			}
//			modelReference.addAnnotation(AnnotationUtil.createInAnnotation(true, importerContextName));
//			break;
//		}
//		modelReference.setModifiers(Modifier.PROTECTED);
//		modelReference.setPackageName(importerPackageName);
//		modelReference.setClassName(importerInterfaceName);
//		modelReference.setName(importerNameUncapped);
//		modelReference.setType(importerType);
//		modelReference.setStructure("item");
//		modelReference.setGenerateGetter(false);
//		modelReference.setGenerateSetter(false);
//		return modelReference;
//	}

//	protected ModelReference createInstanceField_MapperBean(Element element) {
//		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
//		String mapperPackageName = DataLayerHelper.getMapperPackageName(namespace);
//		String mapperInterfaceName = DataLayerHelper.getMapperInterfaceName(element);
//		String mapperType = TypeUtil.getDerivedType(element.getType(), mapperNameUncapped);
//
//		ModelReference modelReference = new ModelReference();
//		switch (context.getDataLayerBeanType()) {
//		case EJB:
//			modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
//			break;
//		case SEAM:
//			String mapperContextName = mapperNameUncapped;
//			if (context.isEnabled("useQualifiedContextNames")) {
//				String qualifiedName = mapperPackageName + "." + mapperInterfaceName;
//				int segmentCount = NameUtil.getSegmentCount(qualifiedName);
//				String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-1);
//				mapperContextName = contextPrefix + "." + mapperNameUncapped;
//			}
//			modelReference.addAnnotation(AnnotationUtil.createInAnnotation(true, mapperContextName));
//			break;
//		}
//		modelReference.setModifiers(Modifier.PROTECTED);
//		modelReference.setPackageName(mapperPackageName);
//		modelReference.setClassName(mapperInterfaceName);
//		modelReference.setName(mapperNameUncapped);
//		modelReference.setType(mapperType);
//		modelReference.setStructure("item");
//		modelReference.setGenerateGetter(false);
//		modelReference.setGenerateSetter(false);
//		return modelReference;
//	}

//	protected ModelReference createInstanceField_DAOBean(Element element) {
//		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
//		String daoPackageName = DataLayerHelper.getDAOPackageName(namespace);
//		String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
//		String daoType = TypeUtil.getDerivedType(element.getType(), daoNameUncapped);
//		
//		ModelReference modelReference = new ModelReference();
//		switch (context.getDataLayerBeanType()) {
//		case EJB:
//			modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
//			break;
//		case SEAM:
//			String daoContextName = daoNameUncapped;
//			if (context.isEnabled("useQualifiedContextNames")) {
//				String qualifiedName = daoPackageName + "." + daoInterfaceName;
//				int segmentCount = NameUtil.getSegmentCount(qualifiedName);
//				String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-1);
//				daoContextName = contextPrefix + "." + daoNameUncapped;
//			}
//			modelReference.addAnnotation(AnnotationUtil.createInAnnotation(true, daoContextName));
//			break;
//		}
//		modelReference.setModifiers(Modifier.PROTECTED);
//		modelReference.setPackageName(daoPackageName);
//		modelReference.setClassName(daoInterfaceName);
//		modelReference.setName(daoNameUncapped);
//		modelReference.setType(daoType);
//		modelReference.setStructure("item");
//		modelReference.setGenerateGetter(false);
//		modelReference.setGenerateSetter(false);
//		return modelReference;
//	}

	protected void initializeInstanceMethods(ModelClass modelClass, Unit unit) throws Exception {
		modelClass.addInstanceOperation(createInstanceOperation_ClearContext(unit, false));
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			Field field = context.findFieldByName(element, "id");
			if (field != null) {
				provider.setElement(element);
				initializeInstanceMethods(modelClass, element, false);
			}
		}
	}
	
	/**
	 * ClearContext operation
	 * ----------------------
	 */
	protected ModelOperation createInstanceOperation_ClearContext(Unit unit, boolean interfaceOnly) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("clearContext");
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_ClearContext(modelOperation, unit);
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
	 * RemoveElementById operation
	 * ---------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElementById(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = super.createInstanceOperation_RemoveElementById(element, interfaceOnly);
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

}
