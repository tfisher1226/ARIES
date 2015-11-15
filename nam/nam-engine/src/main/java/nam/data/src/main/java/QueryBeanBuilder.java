package nam.data.src.main.java;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Condition;
import nam.model.Criteria;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.In;
import nam.model.Item;
import nam.model.Like;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.OrderBy;
import nam.model.Query;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.QueryUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanBuilder;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


/**
 * Builds a <em>Query Bean</em> from an Aries <code>query</code> model element.
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class QueryBeanBuilder extends AbstractManagementBeanBuilder {

	private QueryBeanProvider provider;
	
	private boolean generateInterface;
	
	
	public QueryBeanBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		provider = new QueryBeanProvider(context);
	}

	protected String getBeanContextName(Namespace namespace, Field field) {
		return getBeanContextName(namespace, field, "Query");
	}


	/*
	 * Query bean interface creation
	 * -----------------------------
	 */
	
	public ModelInterface buildInterface(Namespace namespace, Element element, Query query) throws Exception {
		ModelInterface modelInterface = new ModelInterface();
		String packageName = DataLayerHelper.getQueryPackageName(namespace);
		String interfaceName = DataLayerHelper.getQueryInterfaceName(element);
		String parentInterfaceName = DataLayerHelper.getQueryParentInterfaceName(element);

		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		if (parentInterfaceName != null)
			modelInterface.addExtendedInterface(parentInterfaceName);
		modelInterface.setName(NameUtil.uncapName(interfaceName));
		initializeInterface(modelInterface, namespace, element, query);
		return modelInterface;
	}
	
	protected void initializeInterface(ModelInterface modelInterface, Namespace namespace, Element element, Query query) throws Exception {
		//initializeInterfaceAnnotations(modelInterface);
		initializeImportedClasses(modelInterface, namespace, element, query, true);
		initializeInterfaceMethods(modelInterface, namespace, element, query);
	}

	protected void initializeInterfaceMethods(ModelInterface modelInterface, Namespace namespace, Element element, Query query) throws Exception {
		initializeInstanceMethods(modelInterface, namespace, element, query, true);
	}
	
	
	/*
	 * Query bean class creation
	 * -------------------------
	 */
	
//	@Override
//	public Collection<ModelClass> buildClassesFromNamespaces(Collection<Namespace> namespaces) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		Iterator<Namespace> iterator = namespaces.iterator();
//		while (iterator.hasNext()) {
//			Namespace namespace = iterator.next();
//			modelClasses.addAll(buildClasses(namespace));
//			
//		}
//		return modelClasses;
//	}
	
//	@Override
//	public Collection<ModelClass> buildClasses(Namespace namespace) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		if (context.isEnabled("generate-imported-namespaces"))
//			modelClasses.addAll(buildClassesFromNamespaces(namespace.getImports()));
//
//		Properties properties = NamespaceUtil.getProperties(namespace);
//		boolean isImported = NamespaceUtil.isImported(namespace);
//		//boolean isImported = PropertyUtil.isEnabled(properties, "imported");
//		//if (!context.isEnabled("generate-imported-namespaces") && isImported)
//		//	return modelClasses;
//		
//		List<Query> queries = NamespaceUtil.getQueries(namespace);
//		modelClasses.addAll(buildClasses(namespace, queries));
//		return modelClasses;
//	}
	
//	public List<ModelClass> buildClasses(List<Unit> units) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		Iterator<Unit> iterator = units.iterator();
//		while (iterator.hasNext()) {
//			Unit unit = iterator.next();
//			modelClasses.addAll(buildClasses(unit));
//		}
//		return modelClasses;
//	}
	
//	public List<ModelClass> buildClasses(Unit unit) throws Exception {
//		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		List<Element> elements = ElementUtil.getElements(unit.getElements());
//		Iterator<Element> iterator = elements.iterator();
//		while (iterator.hasNext()) {
//			Element element = iterator.next();
//			if (!ElementUtil.isTransient(element)) {
//				ModelClass modelClass = buildClass(element);
//				modelClasses.add(modelClass);
//			}
//		}
//		return modelClasses;
//	}
	
	@Override
	public Collection<ModelClass> buildClassesFromElements(Namespace namespace, Collection<Element> elements) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			List<Query> queries = new ArrayList<Query>();
			if (CodeUtil.hasCriteriaElement(element))
				queries.add(createQueryForCriteriaElement(namespace, element));
			queries.addAll(NamespaceUtil.getQueriesForElement(namespace, element));
			
			modelClasses.addAll(buildClasses(namespace, element, queries));
			if (context.isEnabled("generate-imported-namespaces"))
				modelClasses.addAll(buildClassesFromNamespaces(namespace.getImports()));
		}
		return modelClasses;
	}
	
	public Query createQueryForCriteriaElement(Namespace namespace, Element element) {
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		Element criteriaElement = CodeUtil.getCriteriaElement(element);
		Collection<Item> items = ElementUtil.getItems(criteriaElement);

		Query query = new Query();
		query.setDistinct(false);
		query.setFrom(element.getType());
		
		Criteria criteria = new Criteria();
		QueryUtil.addCriteria(query, criteria);
		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			
			String structure = item.getStructure();
			if (structure.equals("item")) {
				Like likeOp = new Like();
				likeOp.setType(item.getType());
				likeOp.setField(item.getName());
				likeOp.setRequired(FieldUtil.isRequired(item));
				criteria.getInsAndLikes().add(likeOp);
				
			} else {
				In inOp = new In();
				inOp.setType(item.getType());
				inOp.setField(item.getName());
				inOp.setRequired(FieldUtil.isRequired(item));
				criteria.getInsAndLikes().add(inOp);
			}
		}
		
		OrderBy orderBy = new OrderBy();
		//orderBy.getItems().addAll(items);
		query.setOrderBy(orderBy);
		return query;
	}

	public List<ModelClass> buildClasses(Namespace namespace, Element element, Collection<Query> queries) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
//		ModelClass modelClass = buildClass(namespace, element, null);
//		modelClasses.add(modelClass);
		Iterator<Query> iterator = queries.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			ModelClass modelClass = buildClass(namespace, element, query);
			modelClasses.add(modelClass);
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(Namespace namespace, Query query) throws Exception {
		Element element = context.getElementByType(query.getFrom());
		return buildClass(namespace, element, query);
	}
	
	public ModelClass buildClass(Namespace namespace, Element element, Query query) throws Exception {
		String packageName = DataLayerHelper.getQueryPackageName(namespace);
		String interfaceName = DataLayerHelper.getQueryInterfaceName(element);
		String className = DataLayerHelper.getQueryClassName(element);
		String parentClassName = DataLayerHelper.getQueryParentClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		
		ModelClass modelClass = new ModelClass();
		modelClass.setType(element.getType());
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className+"<T extends "+entityClassName+">");
		if (parentClassName != null)
			modelClass.setParentClassName(parentClassName);
		modelClass.setName(NameUtil.uncapName(interfaceName));
		if (generateInterface)
			modelClass.addImplementedInterface(packageName + "." + interfaceName);
		initializeClass(modelClass, namespace, element, query);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Namespace namespace, Element element, Query query) throws Exception {
		initializeClassAnnotations(modelClass, element);
		initializeImportedClasses(modelClass, namespace, element, query, false);
		initializeInstanceFields(modelClass, element);
		initializeInstanceConstructor(modelClass, element, query);
		initializeInstanceMethods(modelClass, namespace, element, query);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
	}

	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
	}

	protected void initializeInstanceConstructor(ModelClass modelClass, Element element, Query query) {
		modelClass.getInstanceConstructors().add(createInstanceConstructor(modelClass, element, query, false));
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Namespace namespace, Element element, Query query) throws Exception {
		initializeInstanceMethods(modelClass, namespace, element, query, false);
	}
	
	
	/*
	 * Shared methods between Interface and Class initialization.
	 */
	
	protected void initializeInstanceMethods(ModelUnit modelUnit, Namespace namespace, Element element, Query query, boolean isInterface) throws Exception {
		modelUnit.addInstanceOperation(createInstanceOperations_IntegrateCriteria(modelUnit, element, query, false));
		if (query.getDistinct())
			modelUnit.addInstanceOperation(createInstanceOperations_GetSingleResult(modelUnit, namespace, element, query, false));
		else modelUnit.addInstanceOperation(createInstanceOperations_GetResultList(modelUnit, namespace, element, query, false));
		modelUnit.addInstanceOperation(createInstanceOperations_CreateCriteriaQuery(modelUnit, namespace, element, query, false));
		modelUnit.addInstanceOperation(createInstanceOperations_BuildPredicates(modelUnit, namespace, element, query, false));
		modelUnit.addInstanceOperations(createInstanceOperations_IntegratePredicates(modelUnit, namespace, element, query, false));
		modelUnit.addInstanceOperation(createInstanceOperations_ExecuteCriteriaQuery(modelUnit, namespace, element, query, false));
	}	
	
	protected void initializeImportedClasses(ModelUnit modelUnit, Namespace namespace, Element element, Query query, boolean isInterface) throws Exception {
		String parentClassName = DataLayerHelper.getQueryParentClassName(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String queryPackageName = DataLayerHelper.getQueryPackageName(namespace);
		String queryInterfaceName = DataLayerHelper.getQueryInterfaceName(element);

		if (parentClassName != null)
			modelUnit.addImportedClass(parentClassName);
		//modelUnit.addImportedClass(elementPackageName + "." + elementClassName);
		modelUnit.addImportedClass(entityPackageName + "." + entityClassName);
		modelUnit.addImportedClass(queryPackageName + "." + queryInterfaceName);
		modelUnit.addImportedClass("java.util.List");
		//modelClass.addImportedClass("org.aries.Assert");
		
		if (isInterface == false) {
			modelUnit.addImportedClass("java.util.ArrayList");
			modelUnit.addImportedClass("java.util.HashSet");
			modelUnit.addImportedClass("java.util.Iterator");
			modelUnit.addImportedClass("java.util.Set");
		}
	}
	
	
	
	/*
	 * Operation creation methods
	 * --------------------------
	 */

	protected ModelConstructor createInstanceConstructor(ModelClass modelClass, Element element, Query query, boolean interfaceOnly) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		modelConstructor.addParameter(createParameter("EntityManager", "entityManager"));
		modelConstructor.addInitialSource(provider.getSourceCode_Constructor(element));
		modelClass.addImportedClass("javax.persistence.EntityManager");
		return modelConstructor;
	}
	
	/*
	 * integrateCriteria() method
	 */
	
	protected ModelOperation createInstanceOperations_IntegrateCriteria(ModelUnit modelUnit, Element element, Query query, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("integrateCriteria");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementClassName+"Criteria", elementName+"Criteria"));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_IntegrateCriteria(element, query));
		modelUnit.addImportedClass(elementQualifiedName+"Criteria");
		return modelOperation;
	}

	/**
	 * getSingleResult() method
	 */
	protected ModelOperation createInstanceOperations_GetSingleResult(ModelUnit modelUnit, Namespace namespace, Element element, Query query, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getSingleResult");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(entityClassName);
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_GetResultList(element, query));
		modelUnit.addImportedClass(entityQualifiedName);
		return modelOperation;
	}
	
	/**
	 * getResultList() method
	 */
	protected ModelOperation createInstanceOperations_GetResultList(ModelUnit modelUnit, Namespace namespace, Element element, Query query, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		String entityClassName = "T";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getResultList");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+entityClassName+">");
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_GetResultList(element, query));
		modelUnit.addImportedClass(entityQualifiedName);
		return modelOperation;
	}
	
	/**
	 * createCriteriaQuery() method
	 */
	protected ModelOperation createInstanceOperations_CreateCriteriaQuery(ModelUnit modelUnit, Namespace namespace, Element element, Query query, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
		String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		String entityClassName = "T";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createCriteriaQuery");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addParameter(createParameter(elementClassName+"Criteria", elementName+"Criteria"));
		modelOperation.setResultType("CriteriaQuery<"+entityClassName+">");
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_CreateCriteriaQuery(element, query));
		modelUnit.addImportedClass("javax.persistence.criteria.CriteriaQuery");
		modelUnit.addImportedClass(entityQualifiedName);
		OrderBy orderBy = query.getOrderBy();
		if (orderBy != null && orderBy.getItems() != null)
			modelUnit.addImportedClass("javax.persistence.criteria.Order");
		return modelOperation;
	}
	
	/*
	 * buildPredicates() method
	 */
	
	protected ModelOperation createInstanceOperations_BuildPredicates(ModelUnit modelUnit, Namespace namespace, Element element, Query query, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("buildPredicates");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addParameter(createParameter(elementClassName+"Criteria", elementName+"Criteria"));
		modelOperation.setResultType("Predicate");
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_BuildPredicates(element, query));
		modelUnit.addImportedClass("javax.persistence.criteria.Predicate");
		modelUnit.addImportedClass(elementQualifiedName+"Criteria");
		return modelOperation;
	}
	
	/*
	 * integratePredicate() methods
	 */

	protected List<ModelOperation> createInstanceOperations_IntegratePredicates(ModelUnit modelUnit, Namespace namespace, Element element, Query query, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Condition condition = QueryUtil.getCondition(query);
		Criteria criteria = QueryUtil.getCriteria(query);

		if (condition != null) {
			modelOperations = createInstanceOperations_IntegratePredicates(modelUnit, namespace, element, query, condition.getInsAndLikes(), interfaceOnly);
		} else if (criteria != null) {
			modelOperations = createInstanceOperations_IntegratePredicates(modelUnit, namespace, element, query, criteria.getInsAndLikes(), interfaceOnly);
		}

		return modelOperations;
	}

	protected List<ModelOperation> createInstanceOperations_IntegratePredicates(ModelUnit modelUnit, Namespace namespace, Element element, Query query, List<Serializable> insAndLikes, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();

		Iterator<Serializable> iterator = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof In) {
				In inOp = (In) object;
				ModelOperation modelOperation = createInstanceOperations_IntegratePredicate(modelUnit, namespace, element, query, inOp, interfaceOnly);
				modelOperations.add(modelOperation);
				
			} else if (object instanceof Like) {
				Like likeOp = (Like) object;
				ModelOperation modelOperation = createInstanceOperations_IntegratePredicate(modelUnit, namespace, element, query, likeOp, interfaceOnly);
				modelOperations.add(modelOperation);

			}
		}
		
		return modelOperations;
	}
	
	/*
	 * integratePredicate() method for InOp
	 */
	
	protected ModelOperation createInstanceOperations_IntegratePredicate(ModelUnit modelUnit, Namespace namespace, Element element, Query query, In inOp, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);

		String fieldName = inOp.getField();
		String fieldNameCapped = NameUtil.capName(fieldName);
		Field targetField = ElementUtil.getField(element, inOp.getField());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addParameter(createParameter("Predicate", "predicate"));
		
		Element targetElement = context.getElementByType(inOp.getType());
		Enumeration targetEnumeration = context.getEnumerationByType(inOp.getType());
		
		//Element
		if (targetElement != null) {
			String targetElementClassName = ModelLayerHelper.getElementClassName(targetElement);
			String targetElementQualifiedName = ModelLayerHelper.getElementQualifiedName(targetElement);

			String targetFieldName = ElementUtil.getTargetFieldName(element, inOp.getField());
			String targetFieldNameCapped = NameUtil.capName(targetFieldName);
			if (targetFieldName.endsWith("Id")) {
				targetElementClassName = "Long";
				targetElementQualifiedName = null;
			}
			
			modelOperation.setName("integrate"+targetFieldNameCapped+"Predicate");
			modelOperation.addParameter(createParameter("Set<"+targetElementClassName+">", targetFieldName+"Set"));
			modelUnit.addImportedClass("javax.persistence.criteria.Path");
			if (targetElementQualifiedName != null)
				modelUnit.addImportedClass(targetElementQualifiedName);
			
		//Enumeration 
		} else if (targetEnumeration != null) {
			String targetEnumerationClassName = ModelLayerHelper.getEnumerationClassName(targetEnumeration);
			String targetEnumerationQualifiedName = ModelLayerHelper.getEnumerationQualifiedName(targetEnumeration);
			String collectionName = fieldName + "Set";

			modelOperation.setName("integrate"+fieldNameCapped+"Predicate");
			modelOperation.addParameter(createParameter("Set<"+targetEnumerationClassName+">", collectionName));
			modelUnit.addImportedClass("javax.persistence.criteria.Path");
			modelUnit.addImportedClass(targetEnumerationQualifiedName);
			
//			if (targetField == null)
//				System.out.println();
			
			if (FieldUtil.isCollection(targetField)) {
				modelUnit.addImportedClass("javax.persistence.criteria.Join");
			}
		}
		
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_IntegratePredicate(element, query, inOp));
		modelUnit.addImportedClass("javax.persistence.criteria.Predicate");
		modelUnit.addImportedClass(entityQualifiedName);
		return modelOperation;
	}
	
	/*
	 * integratePredicate() method for LikeOp
	 */
	
	protected ModelOperation createInstanceOperations_IntegratePredicate(ModelUnit modelUnit, Namespace namespace, Element element, Query query, Like likeOp, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);
		String fieldType = likeOp.getType();
		String fieldName = likeOp.getField();
		String fieldNameCapped = NameUtil.capName(fieldName);
		String fieldClassName = TypeUtil.getClassName(fieldType);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("integrate"+fieldNameCapped+"Predicate");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addParameter(createParameter("Predicate", "predicate"));
		modelOperation.addParameter(createParameter(fieldClassName, fieldName));
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_IntegratePredicate(element, query, likeOp));
		modelUnit.addImportedClass("javax.persistence.criteria.CriteriaQuery");
		modelUnit.addImportedClass(entityQualifiedName);
		OrderBy orderBy = query.getOrderBy();
		if (orderBy != null && orderBy.getItems() != null)
			modelUnit.addImportedClass("javax.persistence.criteria.Order");
		modelUnit.addImportedClass("javax.persistence.criteria.Path");
		return modelOperation;
	}
	
	
	/*
	 * executeCriteriaQuery() method
	 */
	
	protected ModelOperation createInstanceOperations_ExecuteCriteriaQuery(ModelUnit modelUnit, Namespace namespace, Element element, Query query, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
		String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		String entityClassName = "T";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("executeCriteriaQuery");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addParameter(createParameter("CriteriaQuery<"+entityClassName+">", "criteriaQuery"));
		if (query.getDistinct())
			modelOperation.setResultType(entityClassName);
		else modelOperation.setResultType("List<"+entityClassName+">");
		if (interfaceOnly == false)
			modelOperation.addInitialSource(provider.getSourceCode_ExecuteCriteriaQuery(element, query));
		modelUnit.addImportedClass("javax.persistence.criteria.CriteriaQuery");
		modelUnit.addImportedClass("javax.persistence.TypedQuery");
		modelUnit.addImportedClass(entityQualifiedName);
		return modelOperation;
	}

}
