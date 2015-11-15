package nam.data.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Elements;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;

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
public class RepositoryBeanBuilderOLD extends AbstractManagementBeanBuilder {

	private RepositoryBeanProvider provider;
	
	
	public RepositoryBeanBuilderOLD(GenerationContext context) {
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
	
	public List<ModelInterface> buildInterfaces(Unit unit) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>(); 
		modelInterfaces.addAll(buildInterfaces(unit.getNamespace()));
		modelInterfaces.addAll(buildInterfaces(unit.getNamespace(), unit.getElements()));
		return modelInterfaces;
	}
	
	public List<ModelInterface> buildInterfaces(List<Namespace> namespaces) throws Exception {
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
		List<Element> elements = NamespaceUtil.getElements(namespace);
		List<ModelInterface> modelInterfaces = buildInterfaces(namespace, elements);
		return modelInterfaces;
	}
	
	public List<ModelInterface> buildInterfaces(Namespace namespace, Elements elements) throws Exception {
		List<Element> list = ElementUtil.getElements(elements);
		return buildInterfaces(namespace, list);
	}
	
	public List<ModelInterface> buildInterfaces(Namespace namespace, List<Element> elements) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			ModelInterface modelInterface = buildInterface(namespace, element);
			modelInterfaces.add(modelInterface);
		}
		return modelInterfaces;
	}
	
	public ModelInterface buildInterface(Namespace namespace, Element element) throws Exception {
		ModelInterface modelInterface = new ModelInterface();
		String packageName = DataLayerHelper.getRepositoryPackageName(namespace);
		String interfaceName = DataLayerHelper.getRepositoryInterfaceName(element);
		String parentInterfaceName = DataLayerHelper.getRepositoryParentInterfaceName(element);

		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		if (parentInterfaceName != null)
			modelInterface.addExtendedInterface(parentInterfaceName);
		modelInterface.setName(NameUtil.uncapName(interfaceName));
		initializeInterface(modelInterface, element);
		return modelInterface;
	}
	
	protected void initializeInterface(ModelInterface modelInterface, Element element) throws Exception {
		//initializeInterfaceAnnotations(modelInterface);
		initializeImportedClasses(modelInterface, element);
		initializeInterfaceMethods(modelInterface, element);
	}

	protected void initializeImportedClasses(ModelInterface modelInterface, Element element) throws Exception {
		String parentClassName = DataLayerHelper.getRepositoryParentInterfaceName(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		if (parentClassName != null)
			modelInterface.addImportedClass(parentClassName);
		modelInterface.addImportedClass(elementPackageName + "." + elementClassName);
		modelInterface.addImportedClass("java.util.List");
	}


	/*
	 * Repository class creation
	 * -------------------------
	 */
	
	public List<ModelClass> buildClasses(Unit unit) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>(); 
		modelClasses.addAll(buildClasses(unit.getNamespace()));
		modelClasses.addAll(buildClasses(unit.getNamespace(), unit.getElements()));
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(List<Namespace> namespaces) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			modelClasses.addAll(buildClasses(namespace));
		}
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Namespace namespace) throws Exception {
		this.namespace = namespace;
		List<Element> elements = NamespaceUtil.getElements(namespace);
		List<ModelClass> modelClasses = buildClassesFromElements(namespace, elements);
		return modelClasses;
	}

	public List<ModelClass> buildClasses(Namespace namespace, Elements elements) throws Exception {
		List<Element> list = ElementUtil.getElements(elements);
		return buildClassesFromElements(namespace, list);
	}
	
	public List<ModelClass> buildClassesFromElements(Namespace namespace, List<Element> elements) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Element> iterator = elements.iterator();
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
		String packageName = DataLayerHelper.getRepositoryPackageName(namespace);
		String interfaceName = DataLayerHelper.getRepositoryInterfaceName(element);
		String className = DataLayerHelper.getRepositoryClassName(element);
		String parentClassName = DataLayerHelper.getRepositoryParentClassName(element);
		this.namespace = namespace;
		
		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		if (parentClassName != null)
			modelClass.setParentClassName(parentClassName);
		modelClass.setName(NameUtil.uncapName(interfaceName));
		modelClass.setNamespace(namespace.getUri());
		modelClass.addImplementedInterface(packageName + "." + interfaceName);
		modelClass.addImplementedInterface("Serializable");
		modelClass.addImportedClass("java.io.Serializable");
		initializeClass(modelClass, namespace, element);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		initializeClassAnnotations(modelClass, namespace, element);
		initializeImportedClasses(modelClass, namespace, element);
		initializeInstanceFields(modelClass, namespace, element);
		initializeInstanceMethods(modelClass, element);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
		//classAnnotations.add(AnnotationUtil.createScopeAnnotation(ScopeType.SESSION));
		//String contextName = DataLayerHelper.getRepositoryContextName(namespace, element);
		//classAnnotations.add(AnnotationUtil.createNameAnnotation(contextName));
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		String parentClassName = DataLayerHelper.getRepositoryParentClassName(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String daoPackageName = DataLayerHelper.getDAOPackageName(namespace);
		String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
		String mapperPackageName = DataLayerHelper.getMapperPackageName(namespace);
		String mapperInterfaceName = DataLayerHelper.getMapperInterfaceName(element);
		String importerPackageName = DataLayerHelper.getImporterPackageName(namespace);
		String importerInterfaceName = DataLayerHelper.getImporterInterfaceName(element);
		
		if (parentClassName != null)
			modelClass.addImportedClass(parentClassName);
		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
		modelClass.addImportedClass(entityPackageName + "." + entityClassName);
		modelClass.addImportedClass(daoPackageName + "." + daoInterfaceName);
		modelClass.addImportedClass(mapperPackageName + "." + mapperInterfaceName);
		modelClass.addImportedClass(importerPackageName + "." + importerInterfaceName);
		modelClass.addImportedClass("java.util.List");
		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.jboss.seam.ScopeType");
		modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
		modelClass.addImportedClass("org.jboss.seam.annotations.In");
		modelClass.addImportedClass("org.jboss.seam.annotations.Name");
		modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
		//modelClass.addImportedClass("org.jboss.seam.annotations.Transactional");
	}

	protected void initializeInstanceFields(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		modelClass.addInstanceReference(createInstanceField_ImporterBean(namespace, element));
		modelClass.addInstanceReference(createInstanceField_MapperBean(element));
		modelClass.addInstanceReference(createInstanceField_DAOBean(namespace, element));
	}
	
	protected ModelReference createInstanceField_ImporterBean(Namespace namespace, Element element) {
		String importerNameUncapped = DataLayerHelper.getImporterNameUncapped(element);
		String importerPackageName = DataLayerHelper.getImporterPackageName(namespace);
		String importerInterfaceName = DataLayerHelper.getImporterInterfaceName(element);
		String importerType = org.aries.util.TypeUtil.getDerivedType(element.getType(), importerNameUncapped);

		String importerContextName = importerNameUncapped;
		if (context.isEnabled("useQualifiedContextNames")) {
			String qualifiedName = importerPackageName + "." + importerInterfaceName;
			int segmentCount = NameUtil.getSegmentCount(qualifiedName);
			String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-1);
			importerContextName = contextPrefix + "." + importerNameUncapped;
		}
		
		ModelReference modelReference = new ModelReference();
		//modelReference.addAnnotation(AnnotationUtil.createInAnnotation(true, importerContextName));
		modelReference.setModifiers(Modifier.PROTECTED);
		modelReference.setPackageName(importerPackageName);
		modelReference.setClassName(importerInterfaceName);
		modelReference.setName(importerNameUncapped);
		modelReference.setType(importerType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	protected ModelReference createInstanceField_MapperBean(Element element) {
		String mapperNameUncapped = DataLayerHelper.getMapperNameUncapped(element);
		String mapperPackageName = DataLayerHelper.getMapperPackageName(namespace);
		String mapperInterfaceName = DataLayerHelper.getMapperInterfaceName(element);
		String mapperType = org.aries.util.TypeUtil.getDerivedType(element.getType(), mapperNameUncapped);

		String mapperContextName = mapperNameUncapped;
		if (context.isEnabled("useQualifiedContextNames")) {
			String qualifiedName = mapperPackageName + "." + mapperInterfaceName;
			int segmentCount = NameUtil.getSegmentCount(qualifiedName);
			String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-1);
			mapperContextName = contextPrefix + "." + mapperNameUncapped;
		}
		
		ModelReference modelReference = new ModelReference();
		//modelReference.addAnnotation(AnnotationUtil.createInAnnotation(true, mapperContextName));
		modelReference.setModifiers(Modifier.PROTECTED);
		modelReference.setPackageName(mapperPackageName);
		modelReference.setClassName(mapperInterfaceName);
		modelReference.setName(mapperNameUncapped);
		modelReference.setType(mapperType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	protected ModelReference createInstanceField_DAOBean(Namespace namespace, Element element) {
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String daoPackageName = DataLayerHelper.getDAOPackageName(namespace);
		String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
		String daoType = org.aries.util.TypeUtil.getDerivedType(element.getType(), daoNameUncapped);
		
		String daoContextName = daoNameUncapped;
		if (context.isEnabled("useQualifiedContextNames")) {
			String qualifiedName = daoPackageName + "." + daoInterfaceName;
			int segmentCount = NameUtil.getSegmentCount(qualifiedName);
			String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-1);
			daoContextName = contextPrefix + "." + daoNameUncapped;
		}
		
		ModelReference modelReference = new ModelReference();
		//modelReference.addAnnotation(AnnotationUtil.createInAnnotation(true, daoContextName));
		modelReference.setModifiers(Modifier.PROTECTED);
		modelReference.setPackageName(daoPackageName);
		modelReference.setClassName(daoInterfaceName);
		modelReference.setName(daoNameUncapped);
		modelReference.setType(daoType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Element element) throws Exception {
		provider.setElement(element);
		initializeInstanceMethods(modelClass, element, false);
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

}
