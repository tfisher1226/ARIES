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
import nam.model.Condition;
import nam.model.Criteria;
import nam.model.Element;
import nam.model.Elements;
import nam.model.Field;
import nam.model.In;
import nam.model.Like;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Properties;
import nam.model.Query;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.QueryUtil;
import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


/**
 * Builds a DAO Bean from an Aries Element or Aries Namespace;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class DAOBeanBuilder extends AbstractBeanBuilder {

	private DAOBeanProvider provider;
	
	private Persistence persistence;
	
	private Namespace namespace;
	
	private Set<String> classesCreated;
	
	
	public DAOBeanBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		provider = new DAOBeanProvider(context);
	}

	protected boolean isExtendsAbstractParent(Element element) {
		if (context.getParentElementByType(element.getType()) != null) {
			return true;
		}
		return false;
	}
	
	protected String getElementNameUncapped(Element element) {
		return getElementName(element, false);
	}
	
	protected String getElementNameCapped(Element element) {
		return getElementName(element, true);
	}
	
	protected String getElementName(Element element, boolean capped) {
		if (isExtendsAbstractParent(element))
			return ModelLayerHelper.getElementTypeLocalPart(element, capped);
		return ModelLayerHelper.getElementName(element, capped);
	}

	protected String getParameterClassName(Element element) {
		//if (isExtendsAbstractParent(element))
		//	return "T";
		//return DataLayerHelper.getEntityClassName(element);
		return "T";
	}
	
	
	/*
	 * DAO interface creation
	 * ----------------------
	 */

	public List<ModelInterface> buildInterfaces(Persistence persistence) throws Exception {
		classesCreated = new HashSet<String>();
		context.buildParentElementMap(persistence);
		this.persistence = persistence;
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		Collection<Namespace> namespaces = PersistenceUtil.getNamespaces(persistence);
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
	
	public List<ModelInterface> buildInterfacesFromUnits(Collection<Unit> units) throws Exception {
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
		modelInterfaces.addAll(buildInterfaces(unit.getNamespace()));
		modelInterfaces.addAll(buildInterfaces(unit.getNamespace(), unit.getElements()));
		return modelInterfaces;
	}
	
	public List<ModelInterface> buildInterfacesFromNamespaces(Collection<Namespace> namespaces) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			modelInterfaces.addAll(buildInterfaces(namespace));
		}
		return modelInterfaces;
	}

	public List<ModelInterface> buildInterfaces(Namespace namespace) throws Exception {
		this.provider.namespace = namespace;
		this.namespace = namespace;
		
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		if (context.isEnabled("generate-imported-namespaces")) {
			List<Namespace> importedNamespaces = namespace.getImports();
			modelInterfaces.addAll(buildInterfacesFromNamespaces(importedNamespaces));
		}

//		if (namespace == null)
//			System.out.println();
		
		//Properties properties = NamespaceUtil.getProperties(namespace);
		boolean isImported = NamespaceUtil.isImported(namespace);
		//boolean isImported = PropertyUtil.isEnabled(properties, "imported");
		if (!context.isEnabled("generate-imported-namespaces") && isImported)
			return modelInterfaces;
		
		List<Element> elements = NamespaceUtil.getElements(namespace);
		modelInterfaces.addAll(buildInterfacesFromElements(namespace, elements));
		return modelInterfaces;
	}

	public List<ModelInterface> buildInterfaces(Namespace namespace, Elements elements) throws Exception {
		List<Element> list = ElementUtil.getElements(elements);
		return buildInterfacesFromElements(namespace, list);
	}
	
	public List<ModelInterface> buildInterfacesFromElements(Namespace namespace, List<Element> elements) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!ElementUtil.isPersisted(element))
				continue;
			ModelInterface modelInterface = buildInterface(namespace, element);
			if (modelInterface != null)
				modelInterfaces.add(modelInterface);
		}
		return modelInterfaces;
	}

//	public List<ModelInterface> buildInterfaces(List<Unit> units) throws Exception {
//		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
//		Iterator<Unit> iterator = units.iterator();
//		while (iterator.hasNext()) {
//			Unit unit = iterator.next();
//			modelInterfaces.addAll(buildInterfaces(unit));
//		}
//		return modelInterfaces;
//	}
//	
//	public List<ModelInterface> buildInterfaces(Unit unit) throws Exception {
//		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
//		List<Element> elements = ElementUtil.getElements(unit.getElements());
//		Iterator<Element> iterator = elements.iterator();
//		while (iterator.hasNext()) {
//			Element element = iterator.next();
//			if (ElementUtil.isRoot(element)) {
//				modelInterfaces.add(buildInterface(element));
//			}
//		}
//		return modelInterfaces;
//	}
	
	public ModelInterface buildInterface(Namespace namespace, Element element) throws Exception {
		ModelInterface modelInterface = new ModelInterface();
		String packageName = DataLayerHelper.getDAOPackageName(namespace);
		String interfaceName = DataLayerHelper.getDAOInterfaceNameFromType(element.getType());
		String parentInterfaceName = DataLayerHelper.getDAOParentInterfaceName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String qualifiedName = packageName + "." + interfaceName;
		if (classesCreated.contains(qualifiedName))
			return null;
		if (isExtendsAbstractParent(element))
			interfaceName += "<T extends Abstract"+entityClassName+">";
		else interfaceName += "<T extends "+entityClassName+">";
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		modelInterface.addExtendedInterface(parentInterfaceName+"<T>");
		modelInterface.setName(NameUtil.uncapName(interfaceName));
		initializeInterface(modelInterface, namespace, element);
		return modelInterface;
	}
	
	protected void initializeInterface(ModelInterface modelInterface, Namespace namespace, Element element) throws Exception {
		//initializeInterfaceAnnotations(modelInterface);
		initializeImportedClasses(modelInterface, namespace, element);
		initializeInterfaceMethods(modelInterface, namespace, element);
	}

	protected void initializeInterfaceMethods(ModelInterface modelInterface, Namespace namespace, Element element) throws Exception {
		initializeInstanceMethods(modelInterface, namespace, element, true);
	}

	protected void initializeImportedClasses(ModelInterface modelInterface, Namespace namespace, Element element) throws Exception {
		String parentClassName = DataLayerHelper.getDAOParentInterfaceName(element);
		String packageName = DataLayerHelper.getEntityPackageName(namespace);
		String className = DataLayerHelper.getEntityClassName(element);

		if (isExtendsAbstractParent(element))
			modelInterface.addImportedClass(packageName + ".Abstract" + className);
		else modelInterface.addImportedClass(packageName + "." + className);
		modelInterface.addImportedClass(parentClassName);
		modelInterface.addImportedClass("java.util.Collection");
		modelInterface.addImportedClass("java.util.List");
		if (element.getStructure().equals("set"))
			modelInterface.addImportedClass("java.util.Set");
//		if (element.getStructure().equals("map"))
//			modelInterface.addImportedClass("java.util.Map");
		
		List<Field> fields = ElementUtil.getFields(element);
		initializeImportedClasses(modelInterface, namespace, element, fields, true);
	}


	/*
	 * DAO class creation
	 * ------------------
	 */

	public List<ModelClass> buildClasses(Persistence persistence) throws Exception {
		classesCreated = new HashSet<String>();
		context.buildParentElementMap(persistence);
		this.persistence = persistence;
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		Collection<Namespace> namespaces = PersistenceUtil.getNamespaces(persistence);
		if (units.size() > 0) {
			List<ModelClass> modelClasses = buildClassesFromUnits(units);
			Collections.sort(modelClasses);
			return modelClasses;
		} else {
			List<ModelClass> modelClasses = buildClassesFromNamespaces(namespaces);
			Collections.sort(modelClasses);
			return modelClasses;
		}
	}
	
	public List<ModelClass> buildClassesFromUnits(Collection<Unit> units) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			modelClasses.addAll(buildClasses(unit));
		}
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Unit unit) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>(); 
		modelClasses.addAll(buildClasses(unit.getNamespace()));
		modelClasses.addAll(buildClasses(unit.getNamespace(), unit.getElements()));
		return modelClasses;
	}

	public List<ModelClass> buildClassesFromNamespaces(Collection<Namespace> namespaces) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (namespace.getImported() == null || !namespace.getImported()) {
				modelClasses.addAll(buildClasses(namespace));
			}
		}
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Namespace namespace) throws Exception {
		this.namespace = namespace;
		
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		if (context.isEnabled("generate-imported-namespaces")) {
			List<Namespace> importedNamespaces = namespace.getImports();
			modelClasses.addAll(buildClassesFromNamespaces(importedNamespaces));
		}

		Properties properties = NamespaceUtil.getProperties(namespace);
		boolean isImported = NamespaceUtil.isImported(namespace);
		//boolean isImported = PropertyUtil.isEnabled(properties, "imported");
		if (!context.isEnabled("generate-imported-namespaces") && isImported)
			return modelClasses;
		
		List<Element> elements = NamespaceUtil.getElements(namespace);
		modelClasses.addAll(buildClassesFromElements(namespace, elements));
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
			if (!ElementUtil.isPersisted(element))
				continue;
			ModelClass modelClass = buildClass(namespace, element);
			if (modelClass != null)
				modelClasses.add(modelClass);
		}
		return modelClasses;
	}
	
//	public List<ModelClass> buildClasses(List<Unit> units) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		Iterator<Unit> iterator = units.iterator();
//		while (iterator.hasNext()) {
//			Unit unit = iterator.next();
//			modelClasses.addAll(buildClasses(unit));
//		}
//		return modelClasses;
//	}
//	
//	public List<ModelClass> buildClasses(Unit unit) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		List<Element> elements = ElementUtil.getElements(unit.getElements());
//		Iterator<Element> iterator = elements.iterator();
//		while (iterator.hasNext()) {
//			Element element = iterator.next();
//			if (ElementUtil.isRoot(element)) {
//				ModelClass modelClass = buildClass(element);
//				modelClasses.add(modelClass);
//			}
//		}
//		return modelClasses;
//	}
	
	public ModelClass buildClass(Namespace namespace, Element element) throws Exception {
		String packageName = DataLayerHelper.getDAOPackageName(namespace);
		String interfaceName = DataLayerHelper.getDAOInterfaceNameFromType(element.getType());
		String className = DataLayerHelper.getDAOClassNameFromType(element.getType());
		String parentClassName = DataLayerHelper.getDAOParentClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String qualifiedName = packageName + "." + className;
		String beanName = NameUtil.uncapName(interfaceName);
		if (classesCreated.contains(qualifiedName))
			return null;
		
		interfaceName += "<T>";
		if (isExtendsAbstractParent(element))
			className += "<T extends Abstract"+entityClassName+">";
		else className += "<T extends "+entityClassName+">";
		
		ModelClass modelClass = new ModelClass();
		modelClass.setName(beanName);
		modelClass.setType(element.getType());
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setParentClassName(parentClassName+"<T>");
		modelClass.addImplementedInterface(interfaceName);
		//modelClass.addImplementedInterface(packageName + "." + interfaceName);
		//modelClass.addImplementedInterface("Serializable");
		//modelClass.addImportedClass("java.io.Serializable");
		initializeClass(modelClass, namespace, element);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		initializeClassAnnotations(modelClass, element);
		initializeImportedClasses(modelClass, namespace, element);
		initializeInstanceFields(modelClass, element);
		initializeInstanceMethods(modelClass, namespace, element);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		switch (context.getDataLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createStatelessAnnotation());
			String interfaceName = DataLayerHelper.getDAOInterfaceNameFromType(element.getType());
			classAnnotations.add(AnnotationUtil.createLocalAnnotation(interfaceName));
			break;
		case SEAM:
			classAnnotations.add(AnnotationUtil.createAnnotation("AutoCreate"));
			break;
		}
	}

//	protected void initializeInterfaceMethods(ModelInterface modelInterface, Element element) throws Exception {
//		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
//		modelInterface.addInstanceOperation(createInstanceOperation_GetAllRecords(element, true));
//		modelInterface.addInstanceOperations(createInstanceOperations_GetRecordsByField(element, true));
//		if (twoWaySelfReferencing == false)
//			modelInterface.addInstanceOperation(createInstanceOperation_GetRecordsForPage(element, true));
//		if (ElementUtil.isRoot(element)) {
//			modelInterface.addInstanceOperation(createInstanceOperation_GetRecordsByCriterion(element, true));
//			modelInterface.addInstanceOperation(createInstanceOperation_GetRecordsByCriteria(element, true));
//		}
//		modelInterface.addInstanceOperation(createInstanceOperation_GetRecordById(element, true));
//		modelInterface.addInstanceOperations(createInstanceOperations_GetRecordByField(element, true));
//		modelInterface.addInstanceOperation(createInstanceOperation_SaveRecord(element, true));
//		modelInterface.addInstanceOperation(createInstanceOperation_DeleteRecord(element, true));
//		if (twoWaySelfReferencing)
//			modelInterface.addInstanceOperation(createInstanceOperation_MoveRecord(element, true));
//	}

	protected void initializeInstanceMethods(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		initializeInstanceMethods(modelClass, namespace, element, false);
	}


	protected void initializeImportedClasses(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		String parentClassName = DataLayerHelper.getDAOParentClassName(element);
		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String queryPackageName = DataLayerHelper.getQueryPackageName(namespace);
		String queryClassName = DataLayerHelper.getQueryClassName(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);

		boolean criteriaElementExists = context.getElementByName(elementName+"Criteria") != null;

		if (isExtendsAbstractParent(element))
			modelClass.addImportedClass(entityPackageName + ".Abstract" + entityClassName);
		else modelClass.addImportedClass(entityPackageName + "." + entityClassName);
		if (criteriaElementExists)
			modelClass.addImportedClass(queryPackageName + "." + queryClassName); 
		modelClass.addImportedClass(parentClassName);
		modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.List");
		modelClass.addImportedClass("java.util.Iterator");
		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		
		switch (context.getDataLayerBeanType()) {
		case EJB:
			modelClass.addImportedClass("javax.ejb.Local");
			modelClass.addImportedClass("javax.ejb.Stateless");
			//modelClass.addImportedClass("javax.inject.Inject");
			//modelClass.addImportedClass("javax.persistence.PersistenceContext");

			break;
		case SEAM:
			modelClass.addImportedClass("org.jboss.seam.ScopeType");
			modelClass.addImportedClass("org.jboss.seam.annotations.AutoCreate");
			modelClass.addImportedClass("org.jboss.seam.annotations.In");
			modelClass.addImportedClass("org.jboss.seam.annotations.Name");
			modelClass.addImportedClass("org.jboss.seam.annotations.Scope");
			modelClass.addImportedClass("org.jboss.seam.annotations.Transactional");
			break;
		}

		//modelClass.addImportedClass("javax.persistence.EntityManager");
		modelClass.addImportedClass("javax.persistence.NoResultException");
		modelClass.addImportedClass("javax.persistence.Query");
		modelClass.addImportedClass("org.aries.tx.TransactionHelper");
		
		List<Field> fields = ElementUtil.getFields(element);
		initializeImportedClasses(modelClass, namespace, element, fields, false);
	}

	protected void initializeImportedClasses(ModelUnit modelUnit, Namespace namespace, Element element, List<Field> fields, boolean isInterface) {
		Set<String> set = new HashSet<String>();
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldType = field.getType();
//			if (!provider.isMapperRequiredForField(element, field) && !FieldUtil.isInverse(field))
//				continue;
			
			if (isInterface == false) {
				String structure = field.getStructure();
				if (structure.equals("list")) {
					modelUnit.addImportedClass("java.util.List");
					modelUnit.addImportedClass("java.util.ArrayList");
				} else if (structure.equals("set")) {
					//modelUnit.addImportedClass("java.util.Set");
					//modelUnit.addImportedClass("java.util.HashSet");
//				} else if (structure.equals("map")) {
//					modelUnit.addImportedClass("java.util.Map");
//					modelUnit.addImportedClass("java.util.HashMap");
				}
			}
			
			if (element.getType().equals(fieldType))
				continue;
			if (set.contains(fieldType))
				continue;
			
//			String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
//			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
//			if (fieldClassName.equals("Date"))
//				continue;
//			if (ClassUtil.isJavaPrimitiveType(fieldClassName))
//				continue;
//			if (ClassUtil.isJavaDefaultType(fieldClassName))
//				continue;
//
//			if (!element.getType().equals(fieldType)) {
//				modelUnit.addImportedClass(fieldPackageName + "." + fieldClassName);
//				
//				if (field instanceof Reference) {
//					String fieldEntityPackageName = DataLayerHelper.getFieldEntityPackageName(field);
//					String fieldEntityClassName = DataLayerHelper.getFieldEntityClassName(field);
//					modelUnit.addImportedClass(fieldEntityPackageName + "." + fieldEntityClassName);
//
//					String daoFieldPackageName = DataLayerHelper.getDAOPackageName(field);
//					String daoFieldInterfaceName = DataLayerHelper.getDAOInterfaceName(field);
//					modelUnit.addImportedClass(daoFieldPackageName + "." + daoFieldInterfaceName);
//					set.add(fieldType);
//				}
//			}
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		//ModelReference entityManagerReference = DataLayerFactory.createEntityManagerReference(element);

		switch (context.getDataLayerBeanType()) {
		case EJB:
			//String unitName = NameUtil.uncapName(context.getUnit().getName());
			//entityManagerReference.addAnnotation(AnnotationUtil.createPersistenceContextAnnotation(unitName));
			break;
		case SEAM:
			String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
			String contextPrefix = NameUtil.getQualifiedContextNamePrefix(elementQualifiedName, 1).toLowerCase();
			//entityManagerReference.addAnnotation(AnnotationUtil.createInAnnotation(true, contextPrefix+".entityManager"));
			break;
		}

		//modelClass.addInstanceReference(entityManagerReference);
		//modelClass.addInstanceAttribute(createInstanceAttribute_EntityClass(element));
		//modelClass.addInstanceAttribute(createInstanceAttribute_Name(element));
	}

	protected ModelAttribute createInstanceAttribute_EntityClass(Element element) {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE);
		modelAttribute.setPackageName("java.lang");
		modelAttribute.setClassName("Class<T>");
		modelAttribute.setName("entityClass");
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		return modelAttribute;
	}
	
	protected ModelAttribute createInstanceAttribute_Name(Element element) {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE);
		modelAttribute.setPackageName("java.lang");
		modelAttribute.setClassName("String");
		modelAttribute.setName("name");
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		return modelAttribute;
	}

	protected void initializeInstanceMethods(ModelUnit modelUnit, Namespace namespace, Element element, boolean isInterface) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String structure = element.getStructure();
		
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		boolean criteriaElementExists = context.getElementByName(elementClassName+"Criteria") != null;
		
		//if (!isInterface)
		//	modelUnit.addInstanceOperation(createInstanceOperation_GetEntityManager());
		//modelUnit.addInstanceOperation(createInstanceOperation_SetEntityClass(isInterface));
		//modelUnit.addInstanceOperation(createInstanceOperation_Initialize(element, isInterface));
		modelUnit.addInstanceOperation(createInstanceOperation_GetRecordById(element, isInterface));
		if (structure.equals("map"))
			modelUnit.addInstanceOperation(createInstanceOperation_GetRecordByKey(element, isInterface));
		modelUnit.addInstanceOperations(createInstanceOperations_GetRecordByField(element, isInterface));
		if (criteriaElementExists) {
			modelUnit.addInstanceOperation(createInstanceOperation_GetRecordByCriteria(namespace, element, isInterface));
		}
		modelUnit.addInstanceOperations(createInstanceOperations_GetRecordByQuery(namespace, element, isInterface));
		modelUnit.addInstanceOperation(createInstanceOperation_GetRecordList(element, isInterface));
		modelUnit.addInstanceOperations(createInstanceOperations_GetRecordListByField(element, isInterface));
		if (twoWaySelfReferencing == false)
			modelUnit.addInstanceOperation(createInstanceOperation_GetRecordListByPage(element, isInterface));
		if (criteriaElementExists) {
			modelUnit.addInstanceOperation(createInstanceOperation_GetRecordListByCriteria(namespace, element, isInterface));
		}
		modelUnit.addInstanceOperations(createInstanceOperations_GetRecordListByQueryCriteria(namespace, element, isInterface));
		modelUnit.addInstanceOperations(createInstanceOperations_GetRecordListByQueryCondition(namespace, element, isInterface));
		modelUnit.addInstanceOperation(createInstanceOperation_AddRecord(element, isInterface));
		modelUnit.addInstanceOperation(createInstanceOperation_AddRecords(element, isInterface));
		if (twoWaySelfReferencing) {
			modelUnit.addInstanceOperation(createInstanceOperation_MoveRecord(element, isInterface));
			//TODO modelUnit.addInstanceOperation(createInstanceOperation_MoveRecords(element, isInterface));
		}
		modelUnit.addInstanceOperation(createInstanceOperation_SaveRecord(element, isInterface));
		modelUnit.addInstanceOperation(createInstanceOperation_SaveRecords(element, isInterface));
		modelUnit.addInstanceOperation(createInstanceOperation_RemoveAllRecords(element, isInterface));
		modelUnit.addInstanceOperation(createInstanceOperation_RemoveRecord(element, isInterface));
		modelUnit.addInstanceOperation(createInstanceOperation_RemoveRecords(element, isInterface));
		if (structure.equals("map"))
			modelUnit.addInstanceOperation(createInstanceOperation_RemoveRecordByKey(element, isInterface));
		if (criteriaElementExists)
			modelUnit.addInstanceOperation(createInstanceOperation_RemoveRecordListByCriteria(element, isInterface));
	}


	/*
	 * Operation creation methods
	 * --------------------------
	 */

	/*
	 * SetEntityClass operation
	 * ------------------------
	 */

	protected ModelOperation createInstanceOperation_SetEntityClass(boolean interfaceOnly) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("setEntityClass");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("Class<T>", "entityClass"));
		modelOperation.setResultType("Class<T>");
		if (interfaceOnly == false) {
			modelOperation.addInitialSource(provider.getSourceCode_SetEntityClass());
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		}
		return modelOperation;
	}
	
	/*
	 * Initialize operation
	 * --------------------
	 */

	protected ModelOperation createInstanceOperation_Initialize(Element element, boolean interfaceOnly) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("String", "name"));
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_Initialize(element));
		}
		return modelOperation;
	}

	/*
	 * GetEntityManager operation
	 * --------------------------
	 */

	protected ModelOperation createInstanceOperation_GetEntityManager() {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getEntityManager");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("EntityManager");
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.addInitialSource(provider.getSourceCode_GetEntityManager());
		return modelOperation;
	}
	
	/*
	 * GetRecordById operation
	 * -----------------------
	 */

	protected ModelOperation createInstanceOperation_GetRecordById(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementType = ModelLayerHelper.getElementType(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = entityClassName;
		resultClassName = "T";
		
		Field field = context.findFieldByName(element, "id");
		Assert.notNull(field, "Field 'Id' not found for: "+elementType);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"RecordById");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(fieldClassName, "id"));
		modelOperation.setResultType(resultClassName);
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordById(element));
		}
		return modelOperation;
	}
	
	
	/*
	 * GetRecordByKey operation
	 * -----------------------
	 */

	protected ModelOperation createInstanceOperation_GetRecordByKey(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementType = ModelLayerHelper.getElementType(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = entityClassName;
		resultClassName = "T";
		
		String fieldClassName = null;
		Field field = context.findFieldByName(element, "key");
		if (field != null) {
			//Assert.notNull(field, "Field 'Key' not found for: "+elementType);
			fieldClassName = ModelLayerHelper.getFieldClassName(field);
			
		} else {
			String keyType = element.getKey();
			Assert.notNull(keyType, "Field 'Key' type not found for: "+elementType);
			fieldClassName = TypeUtil.getClassName(keyType);
		}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"RecordByKey");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(fieldClassName, "key"));
		modelOperation.setResultType(resultClassName);
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordByKey(element));
		}
		return modelOperation;
	}
	

	/*
	 * GetRecordByField operations
	 * ---------------------------
	 */
	
	protected List<ModelOperation> createInstanceOperations_GetRecordByField(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Field> uniqueFields = ElementUtil.getUniqueFields(element);
		Iterator<Field> iterator = uniqueFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			ModelOperation modelOperation = createInstanceOperation_GetRecordByField(element, field, interfaceOnly);
			modelOperations.add(modelOperation);
		}
		return modelOperations;
	}

	/*
	 * GetRecordByField operation
	 * --------------------------
	 */
	
	protected ModelOperation createInstanceOperation_GetRecordByField(Element element, Field field, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String fieldName = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldQualifiedName = ModelLayerHelper.getFieldQualifiedName(field);
		entityClassName = "T";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addImportedClass(fieldQualifiedName);
		
		//Element fieldElement = context.getElementByType(field.getType());
		//Enumeration fieldEnumeration = context.getEnumerationByType(field.getType());
		//if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName) && fieldElement == null && fieldEnumeration == null) {
		
//		Enumeration enumeration = context.getEnumerationByType(field.getType());
//		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName) && enumeration == null) {
//			fieldName = NameUtil.assureEndsWith(fieldName, "Id");
//		}
		
		modelOperation.setName("get"+elementName+"RecordBy"+fieldName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(fieldClassName, fieldNameUncapped));
		modelOperation.setResultType(entityClassName);
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordByField(element, field));
		}
		return modelOperation;
	}
	
	/*
	 * GetRecordByCriteria operation
	 * --------------------------
	 */

	protected ModelOperation createInstanceOperation_GetRecordByCriteria(Namespace namespace, Element element, boolean interfaceOnly) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		String packageName = TypeUtil.getPackageName(element.getType());
		String operationName = "get"+elementType+"RecordByCriteria";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(operationName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementType+"Criteria", elementTypeUncapped+"Criteria"));
		modelOperation.addImportedClass(packageName+"."+elementType+"Criteria");
		modelOperation.setResultType("T");
		
		if (interfaceOnly == false) {
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordByCriteria(modelOperation, element));
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		}
		return modelOperation;
	}

	/*
	 * GetRecordByQuery operations
	 * ---------------------------
	 */
	
	protected List<ModelOperation> createInstanceOperations_GetRecordByQuery(Namespace namespace, Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Collection<Query> queriesForElement = NamespaceUtil.getQueriesForElement(namespace, element);
		Iterator<Query> iterator = queriesForElement.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			Condition condition = QueryUtil.getCondition(query);
			if (condition != null && query.getDistinct()) {
				ModelOperation modelOperation = createInstanceOperation_GetRecordByQuery(element, query, interfaceOnly);
				modelOperations.add(modelOperation);
				if (interfaceOnly == false) {
					String queryQualifiedName = DataLayerHelper.getQueryQualifiedName(namespace, element);
					modelOperation.addImportedClass(queryQualifiedName);
				}
			}
		}
		return modelOperations;
	}
	
	/*
	 * GetRecordByQuery operation
	 * --------------------------
	 */

	protected ModelOperation createInstanceOperation_GetRecordByQuery(Element element, Query query, boolean interfaceOnly) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		//String packageName = TypeUtil.getPackageName(element.getType());
		String operationName = NameUtil.uncapName(query.getName());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(operationName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(entityClassName);
		
		Condition condition = QueryUtil.getCondition(query);
		Criteria criteria = QueryUtil.getCriteria(query);
		if (condition != null)
			initializeInstanceOperation_GetRecordByQuery(modelOperation, element, condition.getInsAndLikes());
		if (criteria != null)
			initializeInstanceOperation_GetRecordByQuery(modelOperation, element, criteria.getInsAndLikes());
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordByQuery(modelOperation, element, query));
		}
		
		return modelOperation;
	}

	protected void initializeInstanceOperation_GetRecordByQuery(ModelOperation modelOperation, Element element, List<Serializable> insAndLikes) {
		Iterator<Serializable> iterator = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Like) {
				Like likeObject = (Like) object;
				String fieldName = likeObject.getField();
				String typeName = likeObject.getType();
				String likeElementPackageName = TypeUtil.getPackageName(typeName);
				String likeElementClassName = TypeUtil.getClassName(typeName);
				
				if (!ClassUtil.isJavaDefaultType(likeElementClassName)) {
					Field field = ElementUtil.getTargetField(element, fieldName);
					//Field field = ElementUtil.getField(element, fieldName);
					
					String structure = field.getStructure();
					if (structure.equals("item")) {
					} else if (structure.equals("listItem")) {
						likeElementClassName = "List<"+likeElementClassName+">";
						modelOperation.addImportedClass("java.util.List");
						
					} else if (structure.equals("setItem")) {
						likeElementClassName = "Set<"+likeElementClassName+">";
						modelOperation.addImportedClass("java.util.Set");
						
					} else if (structure.equals("mapItem")) {
						String likeElementKeyClassName = TypeUtil.getClassName(field.getKey());
						likeElementClassName = "Map<"+likeElementKeyClassName+", "+likeElementClassName+">";
						//modelOperation.addImportedClass("java.util.Map");
					}
				}
					
				if (!ClassUtil.isJavaDefaultType(likeElementClassName))
					modelOperation.addImportedClass(likeElementPackageName+"."+likeElementClassName);
				modelOperation.addParameter(createParameter(likeElementClassName, fieldName));
				
			} else if (object instanceof In) {
				In inObject = (In) object;
				String fieldName = inObject.getField();
				String typeName = inObject.getType();
				String inElementPackageName = TypeUtil.getPackageName(typeName);
				String inElementClassName = TypeUtil.getClassName(typeName);
				String inElementSetClassName = "List<"+inElementClassName+">";

				if (!ClassUtil.isJavaDefaultType(inElementClassName))
					modelOperation.addImportedClass(inElementPackageName+"."+inElementClassName);
				modelOperation.addParameter(createParameter(inElementSetClassName, fieldName+"List"));
			}
		}
	}

	/*
	 * GetAllRecords operation
	 * -----------------------
	 */
	
	protected ModelOperation createInstanceOperation_GetRecordList(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = entityClassName;
		resultClassName = "T";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getAll"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+resultClassName+">");
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addAnnotation(AnnotationUtil.createSuppressWarningsAnnotation("unchecked"));
			modelOperation.addInitialSource(provider.getSourceCode_GetAllRecords(element));
		}

		return modelOperation;
	}

	/*
	 * GetRecordListByField operations
	 * ----------------------------
	 */
	
	protected List<ModelOperation> createInstanceOperations_GetRecordListByField(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();

		List<Field> fields = ElementUtil.getIndexedFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field.getName().equals("id"))
				continue;
			if (FieldUtil.isUnique(field))
				continue;
			//if (field instanceof Reference && FieldUtil.isManyToOne((Reference) field)) {
				ModelOperation modelOperation = createInstanceOperation_GetRecordListByField(element, field, interfaceOnly);
				modelOperations.add(modelOperation);
			//}
		}

		return modelOperations;
	}
	
	/*
	 * GetRecordsByField operation
	 * ---------------------------
	 */
	
	protected ModelOperation createInstanceOperation_GetRecordListByField(Element element, Field field, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String resultClassName = entityClassName;
		resultClassName = "T";

		//Element fieldElement = context.getElementByType(field.getType());
		//String fieldNameUncapped = ModelLayerHelper.getElementNameUncapped(fieldElement);
		//String fieldEntityClassName = DataLayerHelper.getEntityClassName(fieldElement);
		//String fieldEntityName = DataLayerHelper.getEntityNameUncapped(fieldElement);
		
		ModelOperation modelOperation = new ModelOperation();
		//if (!fieldNameCapped.endsWith("Id"))
		//	fieldNameCapped += "Id";
		modelOperation.setName("get"+elementName+"RecordsBy"+fieldNameCapped);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+resultClassName+">");
		modelOperation.addParameter(createParameter(fieldClassName, fieldNameUncapped));
		//modelOperation.addParameter(createParameter("Long", fieldNameUncapped+"Id"));
		//modelOperation.addParameter(createParameter(fieldEntityClassName, fieldEntityName));
		modelOperation.addImportedClass(fieldPackageName+"."+fieldClassName);
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addAnnotation(AnnotationUtil.createSuppressWarningsAnnotation("unchecked"));
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordListByField(element, field));
		}
		return modelOperation;
	}

	/*
	 * GetRecordsForPage operation
	 * ---------------------------
	 */

	protected ModelOperation createInstanceOperation_GetRecordListByPage(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = entityClassName;
		resultClassName = "T";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"RecordsByPage");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+resultClassName+">");
		modelOperation.addParameter(createParameter("int", "pageIndex"));
		modelOperation.addParameter(createParameter("int", "pageSize"));
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addAnnotation(AnnotationUtil.createSuppressWarningsAnnotation("unchecked"));
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordListByPage(element));
		}
		return modelOperation;
	}

	/*
	 * GetRecordsByCriterion operation
	 * -------------------------------
	 */

	protected ModelOperation createInstanceOperation_GetRecordListByCriteriaSet(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String packageName = TypeUtil.getPackageName(element.getType());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"ListByCriteria");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+entityClassName+">");
		modelOperation.addParameter(createParameter("List<"+elementName+"Criteria>", elementNameUncapped+"CriteriaSet"));
		modelOperation.addImportedClass(packageName+"."+elementName+"Criteria");
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordListByCriteriaSet(element));
		}
		
		return modelOperation;
	}
	
	/*
	 * GetRecordsByCriteria operation
	 * ------------------------------
	 */

	protected ModelOperation createInstanceOperation_GetRecordListByCriteria(Namespace namespace, Element element, boolean interfaceOnly) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String packageName = TypeUtil.getPackageName(element.getType());
		String operationName = "get"+elementType+"RecordsByCriteria";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(operationName);
		//modelOperation.setName("get"+elementName+"ListByCriteria");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<T>");
		modelOperation.addParameter(createParameter(elementType+"Criteria", elementTypeUncapped+"Criteria"));
		modelOperation.addImportedClass(packageName+"."+elementType+"Criteria");
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordListByCriteria(modelOperation, element));
		}
		
		return modelOperation;
	}
	
	/*
	 * GetRecordListByQueryCriteria operations
	 * -------------------------------
	 */
	
	protected List<ModelOperation> createInstanceOperations_GetRecordListByQueryCriteria(Namespace namespace, Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Collection<Query> queriesForElement = NamespaceUtil.getQueriesForElement(namespace, element);
		Iterator<Query> iterator = queriesForElement.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			Condition condition = QueryUtil.getCondition(query);
			if (condition != null && query.getDistinct() == false) {
				ModelOperation modelOperation = createInstanceOperation_GetRecordListByQueryCriteria(element, query, interfaceOnly);
				modelOperations.add(modelOperation);
				if (interfaceOnly == false) {
					String queryQualifiedName = DataLayerHelper.getQueryQualifiedName(namespace, element);
					modelOperation.addImportedClass(queryQualifiedName);
				}
			}
		}
		return modelOperations;
	}
	
	/*
	 * GetRecordsByQueryCriteria operation
	 * ------------------------------
	 */

	protected ModelOperation createInstanceOperation_GetRecordListByQueryCriteria(Element element, Query query, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String packageName = TypeUtil.getPackageName(element.getType());
		String operationName = NameUtil.uncapName(query.getName());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(operationName);
		//modelOperation.setName("get"+elementName+"ListByCriteria");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+entityClassName+">");
		modelOperation.addParameter(createParameter(elementName+"Criteria", elementNameUncapped+"Criteria"));
		modelOperation.addImportedClass(packageName+"."+elementName+"Criteria");
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordListByQueryCriteria(modelOperation, element, query));
		}
		
		return modelOperation;
	}
	
	/*
	 * GetRecordListByQueryCondition operations
	 * ----------------------------------------
	 */
	
	protected List<ModelOperation> createInstanceOperations_GetRecordListByQueryCondition(Namespace namespace, Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Collection<Query> queriesForElement = NamespaceUtil.getQueriesForElement(namespace, element);
		Iterator<Query> iterator = queriesForElement.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			Condition condition = QueryUtil.getCondition(query);
			if (condition != null && query.getDistinct() == false) {
				ModelOperation modelOperation = createInstanceOperation_GetRecordListByQueryCondition(element, query, interfaceOnly);
				modelOperations.add(modelOperation);
				if (interfaceOnly == false) {
					String queryQualifiedName = DataLayerHelper.getQueryQualifiedName(namespace, element);
					modelOperation.addImportedClass(queryQualifiedName);
				}
			}
		}
		return modelOperations;
	}
	
	/*
	 * GetRecordListByQueryCondition operation
	 * ------------------------------
	 */

	protected ModelOperation createInstanceOperation_GetRecordListByQueryCondition(Element element, Query query, boolean interfaceOnly) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		//String packageName = TypeUtil.getPackageName(element.getType());
		String operationName = NameUtil.uncapName(query.getName());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(operationName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+entityClassName+">");
		
		Condition condition = QueryUtil.getCondition(query);
		Criteria criteria = QueryUtil.getCriteria(query);
		if (condition != null)
			initializeInstanceOperation_GetRecordListByQueryCondition(modelOperation, element, condition.getInsAndLikes());
		if (criteria != null)
			initializeInstanceOperation_GetRecordListByQueryCondition(modelOperation, element, criteria.getInsAndLikes());
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_GetRecordListByQueryCondition(modelOperation, element, query));
		}
		
		return modelOperation;
	}

	protected void initializeInstanceOperation_GetRecordListByQueryCondition(ModelOperation modelOperation, Element element, List<Serializable> insAndLikes) {
		Iterator<Serializable> iterator = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Like) {
				Like likeObject = (Like) object;
				String fieldName = likeObject.getField();
				String typeName = likeObject.getType();
				String likeElementPackageName = TypeUtil.getPackageName(typeName);
				String likeElementClassName = TypeUtil.getClassName(typeName);
				
				if (!ClassUtil.isJavaDefaultType(likeElementClassName)) {
					Field field = ElementUtil.getField(element, fieldName);
					
					String structure = field.getStructure();
					if (structure.equals("item")) {
					} else if (structure.equals("listItem")) {
						likeElementClassName = "List<"+likeElementClassName+">";
						modelOperation.addImportedClass("java.util.List");
						
					} else if (structure.equals("setItem")) {
						likeElementClassName = "Set<"+likeElementClassName+">";
						modelOperation.addImportedClass("java.util.Set");
						
					} else if (structure.equals("mapItem")) {
						String likeElementKeyClassName = TypeUtil.getClassName(field.getKey());
						likeElementClassName = "Map<"+likeElementKeyClassName+", "+likeElementClassName+">";
						//modelOperation.addImportedClass("java.util.Map");
					}
				}
					
				if (!ClassUtil.isJavaDefaultType(likeElementClassName))
					modelOperation.addImportedClass(likeElementPackageName+"."+likeElementClassName);
				modelOperation.addParameter(createParameter(likeElementClassName, fieldName));
				
			} else if (object instanceof In) {
				In inObject = (In) object;
				String fieldName = inObject.getField();
				String typeName = inObject.getType();
				String inElementPackageName = TypeUtil.getPackageName(typeName);
				String inElementClassName = TypeUtil.getClassName(typeName);

				if (!ClassUtil.isJavaDefaultType(inElementClassName))
					modelOperation.addImportedClass(inElementPackageName+"."+inElementClassName);
				modelOperation.addParameter(createParameter(inElementClassName, fieldName));
			}
		}
	}
	
	/*
	 * AddRecord operation
	 * -------------------
	 */
	
	protected ModelOperation createInstanceOperation_AddRecord(Element element, boolean interfaceOnly) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = getElementNameCapped(element);
		String parameterName = getElementNameUncapped(element) + "Record";
		String parameterClassName = getParameterClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("add"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (twoWaySelfReferencing)
			modelOperation.addParameter(createParameter("Long", "parentId"));
		modelOperation.addParameter(createParameter(parameterClassName, parameterName));
		modelOperation.setResultType("Long");
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_AddRecord(element));
		}
		return modelOperation;
	}
	
	/*
	 * AddRecords operation
	 * --------------------
	 */
	
	protected ModelOperation createInstanceOperation_AddRecords(Element element, boolean interfaceOnly) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = getElementNameCapped(element);
		String parameterName = getElementNameUncapped(element) + "Records";
		String parameterClassName = getParameterClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("add"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (twoWaySelfReferencing)
			modelOperation.addParameter(createParameter("Long", "parentId"));
		modelOperation.addParameter(createParameter("Collection<"+parameterClassName+">", parameterName));
		modelOperation.setResultType("List<Long>");
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_AddRecords(element));
		}
		return modelOperation;
	}
	
	/*
	 * MoveRecord operation
	 * --------------------
	 */

	protected ModelOperation createInstanceOperation_MoveRecord(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String elementNameUncapped = getElementNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("move"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("Long", "parentId"));
		modelOperation.addParameter(createParameter("Long", elementNameUncapped+"Id"));
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_MoveRecord(element));
		}
		return modelOperation;
	}
	
	/*
	 * SaveRecord operation
	 * --------------------
	 */
	
	protected ModelOperation createInstanceOperation_SaveRecord(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String parameterName = getElementNameUncapped(element) + "Record";
		String parameterClassName = getParameterClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("save"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(parameterClassName, parameterName));
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_SaveRecord(element));
		}
		return modelOperation;
	}
	
	/*
	 * SaveRecords operation
	 * ---------------------
	 */
	
	protected ModelOperation createInstanceOperation_SaveRecords(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String parameterName = getElementNameUncapped(element) + "Records";
		String parameterClassName = getParameterClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("save"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("Collection<"+parameterClassName+">", parameterName));
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_SaveRecords(element));
		}
		return modelOperation;
	}

	/*
	 * RemoveAllRecords operation
	 * --------------------------
	 */

	protected ModelOperation createInstanceOperation_RemoveAllRecords(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("removeAll"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_RemoveAllRecords(element));
		}
		return modelOperation;
	}
	
	/*
	 * RemoveRecord operation
	 * ----------------------
	 */

	protected ModelOperation createInstanceOperation_RemoveRecord(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String parameterName = getElementNameUncapped(element) + "Record";
		String parameterClassName = getParameterClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(parameterClassName, parameterName));
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_RemoveRecord(element));
		}
		return modelOperation;
	}

	/*
	 * RemoveRecordById operation
	 * --------------------------
	 */

	protected ModelOperation createInstanceOperation_RemoveRecordById(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String parameterName = "id";
		String parameterClassName = "Long";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"RecordById");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(parameterClassName, parameterName));
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_RemoveRecordById(element));
		}
		return modelOperation;
	}
	
	/*
	 * RemoveRecordByKey operation
	 * ---------------------------
	 */

	protected ModelOperation createInstanceOperation_RemoveRecordByKey(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String parameterName = "key";
		String parameterClassName = "String";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"RecordByKey");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(parameterClassName, parameterName));
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_RemoveRecordByKey(element));
		}
		return modelOperation;
	}
	
	/*
	 * RemoveRecords operation
	 * -----------------------
	 */

	protected ModelOperation createInstanceOperation_RemoveRecords(Element element, boolean interfaceOnly) {
		String elementName = getElementNameCapped(element);
		String parameterName = getElementNameUncapped(element) + "Records";
		String parameterClassName = getParameterClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("Collection<"+parameterClassName+">", parameterName));
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			//modelOperation.addAnnotation(AnnotationUtil.createTransactionalAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_RemoveRecords(element));
		}
		return modelOperation;
	}

	/*
	 * RemoveRecordsByCriteria operation
	 * ------------------------------
	 */

	protected ModelOperation createInstanceOperation_RemoveRecordListByCriteria(Element element, boolean interfaceOnly) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String packageName = TypeUtil.getPackageName(element.getType());
		String operationName = "remove"+elementType+"Records";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(operationName);
		//modelOperation.setName("get"+elementName+"ListByCriteria");
		modelOperation.setModifiers(Modifier.PUBLIC);
		//modelOperation.setResultType("List<T>");
		modelOperation.addParameter(createParameter(elementType+"Criteria", elementTypeUncapped+"Criteria"));
		modelOperation.addImportedClass(packageName+"."+elementType+"Criteria");
		
		if (interfaceOnly == false) {
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			modelOperation.addInitialSource(provider.getSourceCode_RemoveRecordListByCriteria(modelOperation, element));
		}
		
		return modelOperation;
	}
	
}
