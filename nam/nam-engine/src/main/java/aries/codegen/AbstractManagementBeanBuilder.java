package aries.codegen;

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
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Persistence;
import nam.model.Properties;
import nam.model.Query;
import nam.model.Result;
import nam.model.Service;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ParameterUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.QueryUtil;
import nam.model.util.ResultUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


/**
 * Builds an Abstract Management Bean from an Aries Element or Aries Namespace;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public abstract class AbstractManagementBeanBuilder extends AbstractBeanBuilder {

	private AbstractManagementBeanProvider provider;
	
	protected Namespace namespace;

	protected Persistence persistence;
	
	protected Set<String> classesCreated;
	
	
	public AbstractManagementBeanBuilder(GenerationContext context) {
		super(context);
	}

	protected void initialize(AbstractManagementBeanProvider provider) {
		this.provider = provider; 
	}

	
	public Collection<ModelClass> buildClasses(Persistence persistence) throws Exception {
		classesCreated = new HashSet<String>();
		context.buildParentElementMap(persistence);
		this.persistence = persistence;
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		Collection<Namespace> namespaces = PersistenceUtil.getNamespaces(persistence);
		if (units.size() > 0) {
			List<ModelClass> modelClasses = new ArrayList<ModelClass>(buildClassesFromUnits(units));
			Collections.sort(modelClasses);
			return modelClasses;
		} else {
			List<ModelClass> modelClasses = new ArrayList<ModelClass>(buildClassesFromNamespaces(namespaces));
			Collections.sort(modelClasses);
			return modelClasses;
		}
	}
	
	public Collection<ModelClass> buildClassesFromUnits(Collection<Unit> units) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			modelClasses.addAll(buildClasses(unit));
		}
		return modelClasses;
	}
	
	public Collection<ModelClass> buildClasses(Unit unit) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>(); 
		modelClasses.addAll(buildClasses(unit.getNamespace()));
		modelClasses.addAll(buildClasses(unit.getNamespace(), unit.getElements()));
		return modelClasses;
	}
	
	public Collection<ModelClass> buildClassesFromNamespaces(Collection<Namespace> namespaces) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			modelClasses.addAll(buildClasses(namespace));
		}
		return modelClasses;
	}
	
	public Collection<ModelClass> buildClasses(Namespace namespace) throws Exception {
		this.namespace = namespace;
		
		Collection<ModelClass> modelClasses = new ArrayList<ModelClass>();
		if (context.isEnabled("generate-imported-namespaces"))
			modelClasses.addAll(buildClassesFromNamespaces(namespace.getImports()));

		Properties properties = NamespaceUtil.getProperties(namespace);
		boolean isImported = NamespaceUtil.isImported(namespace);
		//boolean isImported = PropertyUtil.isEnabled(properties, "imported");
		if (!context.isEnabled("generate-imported-namespaces") && isImported)
			return modelClasses;
		
		List<Element> elements = NamespaceUtil.getElements(namespace);
		modelClasses.addAll(buildClassesFromElements(namespace, elements));
		return modelClasses;
	}
	
	public Collection<ModelClass> buildClasses(Namespace namespace, Elements elements) throws Exception {
		Collection<Element> list = ElementUtil.getElements(elements);
		return buildClassesFromElements(namespace, list);
	}
	
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
			ModelClass modelClass = buildClass(namespace, element);
			if (modelClass != null)
				modelClasses.add(modelClass);
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(Namespace namespace, Element element) throws Exception {
		return null;
	}

	
	protected void addImportedClassesForElements(ModelUnit modelUnit, Collection<Element> elements) {
		Iterator<Element> iterator2 = elements.iterator();
		while (iterator2.hasNext()) {
			Element element = iterator2.next();
			if (ElementUtil.isTransient(element))
				continue;
			addImportedClassForElement(modelUnit, element);
		}
	}

	protected void addImportedClassForElement(ModelUnit modelUnit, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		modelUnit.addImportedClass(elementPackageName + "." + elementClassName);
		String structure = element.getStructure();
		if (structure != null) {
			if (structure.equals("list")) {
				modelUnit.addImportedClass("java.util.List");
			} else if (structure.equals("set")) {
				modelUnit.addImportedClass("java.util.Set");
			} else if (structure.equals("map")) {
				//modelInterface.addImportedClass("java.util.Map");
			}
		}
		
		if (structure.equals("map"))
			addImportedClassForKey(modelUnit, element);
		addImportedClassForCriteria(modelUnit, element);
	}
	
	protected void addImportedClassForEntity(ModelUnit modelUnit, Element element) {
		String entityPackageName = DataLayerHelper.getEntityPackageName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		modelUnit.addImportedClass(entityPackageName + "." + entityClassName);
	}
	
	protected void addImportedClassForKey(ModelUnit modelUnit, Element element) {
		String elementType = element.getType();
		String elementKeyType = elementType + "Key";
		Element elementKey = context.getElementByType(elementKeyType);
		if (elementKey != null) {
			modelUnit.addImportedClass(elementKey);
		}
	}
	
	protected void addImportedClassForCriteria(ModelUnit modelUnit, Element element) {
		String elementType = element.getType();
		String elementCriteriaType = elementType + "Criteria";
		Element elementCriteria = context.getElementByType(elementCriteriaType);
		if (elementCriteria != null) {
			modelUnit.addImportedClass(elementCriteria);
		}
	}


	/*
	 * Management bean interface methods
	 * ---------------------------------
	 */
	
	protected void initializeInterfaceMethods(ModelInterface modelInterface, Element element) throws Exception {
		initializeInstanceMethods(modelInterface, element, true);
	}

	
	/*
	 * Management bean class creation
	 * ------------------------------
	 */
	
	protected void initializeInstanceMethods(ModelClass modelClass, Element element) throws Exception {
		initializeInstanceMethods(modelClass, element, false);
	}
	
	protected void initializeInstanceMethods(ModelUnit modelUnit, Element element, boolean isInterface) throws Exception {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		Element containingElement = getContainingElement(element);
		boolean hasKeyElement = CodeUtil.hasKeyElement(element);
		boolean hasCriteriaElement = CodeUtil.hasCriteriaElement(element);
		String structure = element.getStructure();

//		if (element.getName().toLowerCase().equals("user"))
//			System.out.println();

		if (structure.equals("item")) {
			modelUnit.addInstanceOperation(createInstanceOperation_GetAllElements(element, isInterface));
			modelUnit.addInstanceOperation(createInstanceOperation_GetElementById(element, isInterface));
			
			if (hasKeyElement) {
				modelUnit.addInstanceOperation(createInstanceOperation_GetElementByKey(element, isInterface));
				//modelUnit.addInstanceOperation(createInstanceOperation_GetElementsByKeys(element, isInterface));
			}
			modelUnit.addInstanceOperations(createInstanceOperations_GetElementByField(element, isInterface));
			modelUnit.addInstanceOperations(createInstanceOperations_GetElementListByField(element, isInterface));
			
		} else if (structure.equals("list") || structure.equals("set")) {
			modelUnit.addInstanceOperation(createInstanceOperation_GetAllElements(element, isInterface));
			modelUnit.addInstanceOperation(createInstanceOperation_GetElementById(element, isInterface));
			modelUnit.addInstanceOperations(createInstanceOperations_GetElementByField(element, isInterface));
			//modelUnit.addInstanceOperations(createInstanceOperations_GetElementByCriteria(element, isInterface));
			modelUnit.addInstanceOperations(createInstanceOperations_GetElementByQuery(element, isInterface));
			//modelUnit.addInstanceOperation(createInstanceOperation_GetElementListByIds(element, isInterface));
			modelUnit.addInstanceOperations(createInstanceOperations_GetElementListByField(element, isInterface));
			
		} else if (structure.equals("map")) {
			modelUnit.addInstanceOperation(createInstanceOperation_GetAllElements(element, isInterface));
			modelUnit.addInstanceOperation(createInstanceOperation_GetElementById(element, isInterface));
			modelUnit.addInstanceOperation(createInstanceOperation_GetElementByKey(element, isInterface));
			//modelUnit.addInstanceOperation(createInstanceOperation_GetElementsByKeys(element, isInterface));
			modelUnit.addInstanceOperations(createInstanceOperations_GetElementListByField(element, isInterface));
		}
		
		if (twoWaySelfReferencing == false)
			modelUnit.addInstanceOperation(createInstanceOperation_GetElementListByPage(element, isInterface));
		if (hasCriteriaElement)
			modelUnit.addInstanceOperations(createInstanceOperations_GetElementListByCriteria(element, isInterface));
		modelUnit.addInstanceOperations(createInstanceOperations_GetElementListByQueryCriteria(element, isInterface));
		modelUnit.addInstanceOperations(createInstanceOperations_GetElementListByQueryCondition(element, isInterface));
		if (ElementUtil.isRoot(element)) {
			modelUnit.addInstanceOperations(createInstanceOperations_AddElements(element, isInterface));
			if (twoWaySelfReferencing)
				modelUnit.addInstanceOperation(createInstanceOperation_MoveElement(element, isInterface));
			modelUnit.addInstanceOperations(createInstanceOperations_SaveElements(element, isInterface));
			modelUnit.addInstanceOperations(createInstanceOperations_RemoveElements(element, isInterface));
			if (hasCriteriaElement)
				modelUnit.addInstanceOperation(createInstanceOperation_RemoveElementListByCriteria(element, isInterface));
		}

		if (ElementUtil.isRoot(element))
			modelUnit.addInstanceOperation(createInstanceOperation_ImportElements(element, isInterface));
	}
	
	protected List<ModelOperation> createInstanceOperations_AddElements(Element element, boolean isInterface) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createInstanceOperation_AddElement(element, isInterface));
		String structure = element.getStructure().toLowerCase();
		if (structure.equals("list")) {
			modelOperations.add(createInstanceOperation_AddElementList(element, isInterface));
//		} else if (structure.equals("set")) {
//			modelOperations.add(createInstanceOperation_AddElementSet(element, isInterface));
		} else if (structure.equals("map")) {
			modelOperations.add(createInstanceOperation_AddElementList(element, isInterface));
			//modelOperations.add(createInstanceOperation_AddElementMap(element, isInterface));
		}
		return modelOperations;
	}

	protected List<ModelOperation> createInstanceOperations_SaveElements(Element element, boolean isInterface) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createInstanceOperation_SaveElement(element, isInterface));
		String structure = element.getStructure().toLowerCase();
		if (structure.equals("list"))
			modelOperations.add(createInstanceOperation_SaveElementList(element, isInterface));
//		else if (structure.equals("set"))
//			modelOperations.add(createInstanceOperation_SaveElementSet(element, isInterface));
		else if (structure.equals("map")) {
			modelOperations.add(createInstanceOperation_SaveElementList(element, isInterface));
			//modelOperations.add(createInstanceOperation_SaveElementMap(element, isInterface));
		}
		return modelOperations;
	}

	protected List<ModelOperation> createInstanceOperations_RemoveElements(Element element, boolean isInterface) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createInstanceOperation_RemoveAllElements(element, isInterface));
		modelOperations.add(createInstanceOperation_RemoveElement(element, isInterface));
		modelOperations.add(createInstanceOperation_RemoveElementById(element, isInterface));
		String structure = element.getStructure().toLowerCase();
		boolean hasCriteriaElement = CodeUtil.hasCriteriaElement(element);
		
		if (structure.equals("list")) {
			modelOperations.add(createInstanceOperation_RemoveElementList(element, isInterface));

//		} else if (structure.equals("set")) {
//			modelOperations.add(createInstanceOperation_RemoveElementSet(element, isInterface));
//			modelOperations.add(createInstanceOperation_RemoveElementList(element, isInterface));
//			modelOperations.add(createInstanceOperation_RemoveAllElements(element, isInterface));
			
		} else if (structure.equals("map")) {
			modelOperations.add(createInstanceOperation_RemoveElementByKey(element, isInterface));
			//modelOperations.add(createInstanceOperation_RemoveElementListByKeys(element, isInterface));
			modelOperations.add(createInstanceOperation_RemoveElementList(element, isInterface));
			//modelOperations.add(createInstanceOperation_RemoveElementMap(element, isInterface));
		}
		return modelOperations;
	}

	/*
	 * Operation creation methods
	 * --------------------------
	 */

	protected ModelOperation createModelOperation(Service service, Operation operation) {
		ModelOperation modelOperation = CodeUtil.createOperation(operation);
		modelOperation.setSynchronous(ServiceUtil.isSynchronous(service));
		//TODO modelOperation.addException("Exception");
		addImportClasses(modelOperation, operation);
//		List<ModelParameter> parameters = modelOperation.getParameters();
//		Iterator<ModelParameter> iterator = parameters.iterator();
//		while (iterator.hasNext()) {
//			ModelParameter modelParameter = iterator.next();
//			String parameterName = modelParameter.getName();
//		}
		if (!ServiceUtil.isStateful(service))
			modelOperation.setName(OperationUtil.getUniqueOperationName(operation));
		return modelOperation;
	}
	
	
	/**
	 * ImportElements operation
	 * ------------------------
	 */
	protected ModelOperation createInstanceOperation_ImportElements(Element element, boolean interfaceOnly) {
		String entityName = ModelLayerHelper.getElementNameCapped(element);
		//String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("import"+entityName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_ImportElements(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetAllElements operation
	 * ------------------------
	 */
	protected ModelOperation createInstanceOperation_GetAllElements(Element element, boolean interfaceOnly) {
		String entityName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getAll"+entityName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);

		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addImportedClass("java.util.List");

//		if (element.getStructure().equals("list")) {
//			modelOperation.setResultType("List<"+elementClassName+">");
//			modelOperation.addImportedClass("java.util.List");
//			
//		} else if (element.getStructure().equals("map")) {
//			String elementKeyClassName = ModelLayerHelper.getElementKeyClassName(element);
//			modelOperation.setResultType("Map<"+elementKeyClassName+", "+elementClassName+">");
//			modelOperation.addImportedClass("java.util.Map");
//		}
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetAllElements(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetElementListByField operations
	 * --------------------------------
	 */
	protected List<ModelOperation> createInstanceOperations_GetElementListByField(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		//System.out.println(">>>"+element.getName());
		
		List<Field> fields = ElementUtil.getIndexedFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field.getName().equals("id"))
				continue;
			if (FieldUtil.isUnique(field))
				continue;
			//if (field instanceof Reference && FieldUtil.isManyToOne((Reference) field)) {
				ModelOperation modelOperation = createInstanceOperation_GetElementListByField(element, field, interfaceOnly);
				modelOperations.add(modelOperation);
			//}
		}

		return modelOperations;
	}
	
	/**
	 * GetElementListByField operation
	 * -------------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementListByField(Element element, Field field, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		String fieldName = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldQualifiedName = ModelLayerHelper.getFieldQualifiedName(field);
		//Element fieldElement = context.getElementByType(field.getType());
		
		String parameterName = fieldName;
		String parameterClassName = fieldClassName;
		String fieldClauseName = fieldNameCapped;
		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName)) {
			if (fieldName.endsWith("List")) {
				fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "List");
				parameterName = NameUtil.assureEndsWith(parameterName, "List");
				//parameterClassName = "List<Long>";
				
			} else {
				//fieldClauseName = NameUtil.assureEndsWith(fieldClauseName, "Id");
				//parameterName = NameUtil.assureEndsWith(parameterName, "Id");
			}
		}

//		if (fieldClauseName.equals("PermissionIdList"))
//			System.out.println();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"RecordsBy"+fieldClauseName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addParameter(createParameter(parameterClassName, parameterName));
		//modelOperation.addParameter(createParameter(fieldEntityClassName, fieldEntityName));
		modelOperation.addImportedClass(fieldQualifiedName);
		modelOperation.addImportedClass("java.util.List");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			provider.setField(field);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementListByField(modelOperation, element, field);
		}
		return modelOperation;
	}
	
	/**
	 * GetElementByCriteria operations
	 * -----------------------------------
	 */
	protected List<ModelOperation> createInstanceOperations_GetElementByCriteria(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		ModelOperation modelOperation = createInstanceOperation_GetElementByCriteria(element, interfaceOnly);
		modelOperations.add(modelOperation);
		return modelOperations;
	}

	/**
	 * GetElementByCriteria operation
	 * ----------------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementByCriteria(Element element, boolean interfaceOnly) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String packageName = TypeUtil.getPackageName(element.getType());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(elementClassName);
		modelOperation.addParameter(createParameter(elementType+"Criteria", elementTypeUncapped+"Criteria"));
		modelOperation.addImportedClass(packageName+"."+elementType+"Criteria");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementByCriteria(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetElementListByCriteria operations
	 * -----------------------------------
	 */
	protected List<ModelOperation> createInstanceOperations_GetElementListByCriteria(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		ModelOperation modelOperation = createInstanceOperation_GetElementListByCriteria(element, interfaceOnly);
		modelOperations.add(modelOperation);
		return modelOperations;
	}

	/**
	 * GetElementListByCriteria operation
	 * ----------------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementListByCriteria(Element element, boolean interfaceOnly) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String packageName = TypeUtil.getPackageName(element.getType());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addParameter(createParameter(elementType+"Criteria", elementTypeUncapped+"Criteria"));
		modelOperation.addImportedClass(packageName+"."+elementType+"Criteria");
		modelOperation.addImportedClass("java.util.List");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementListByCriteria(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetElementListByQueryCriteria operations
	 * ----------------------------------------
	 */
	protected List<ModelOperation> createInstanceOperations_GetElementListByQueryCriteria(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		String namespaceUri = TypeUtil.getNamespace(element.getType());
		Namespace namespace = context.getNamespaceByUri(namespaceUri);
		Collection<Query> queriesForElement = NamespaceUtil.getQueriesForElement(namespace, element);
		Iterator<Query> iterator = queriesForElement.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			Criteria criteria = QueryUtil.getCriteria(query);
			if (criteria != null) {
				ModelOperation modelOperation = createInstanceOperation_GetElementListByQueryCriteria(element, query, interfaceOnly);
				//String queryQualifiedName = DataLayerHelper.getQueryQualifiedName(element);
				//modelOperation.addImportedClass(queryQualifiedName);
				modelOperations.add(modelOperation);
			}
		}
		return modelOperations;
	}
	
	/**
	 * GetElementListByQueryCriteria operation
	 * ---------------------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementListByQueryCriteria(Element element, Query query, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String packageName = TypeUtil.getPackageName(element.getType());
		String queryClassName = DataLayerHelper.getQueryClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"RecordsBy"+queryClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addParameter(createParameter(elementName+"Criteria", elementNameUncapped+"Criteria"));
		modelOperation.addImportedClass(packageName+"."+elementName+"Criteria");
		modelOperation.addImportedClass("java.util.List");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementListByQueryCriteria(modelOperation, element, query);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetElementListByQueryCondition operations
	 * -----------------------------------------
	 */
	protected List<ModelOperation> createInstanceOperations_GetElementListByQueryCondition(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		String namespaceUri = TypeUtil.getNamespace(element.getType());
		Namespace namespace = context.getNamespaceByUri(namespaceUri);
		Collection<Query> queriesForElement = NamespaceUtil.getQueriesForElement(namespace, element);
		Iterator<Query> iterator = queriesForElement.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			Condition condition = QueryUtil.getCondition(query);
			if (condition != null && query.getDistinct() == false) {
				ModelOperation modelOperation = createInstanceOperation_GetElementListByQueryCondition(element, query, condition, interfaceOnly);
				modelOperations.add(modelOperation);
			}
//			List<Condition> conditions = QueryUtil.getConditions(query);
//			if (conditions != null && conditions.size() > 0) {
//				ModelOperation modelOperation = createInstanceOperation_GetElementListByQuery(element, query, conditions, interfaceOnly);
//				modelOperations.add(modelOperation);
//			}
		}
		return modelOperations;
	}

	/**
	 * GetElementListByQueryCondition operation
	 * ----------------------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementListByQueryCondition(Element element, Query query, Condition condition, boolean interfaceOnly) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(query.getName());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addImportedClass("java.util.List");

		List<Serializable> insAndLikes = condition.getInsAndLikes();
		Iterator<Serializable> iterator = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Like) {
				Like likeObject = (Like) object;
				String fieldName = likeObject.getField();
				String typeName = likeObject.getType();
				String likeElementPackageName = TypeUtil.getPackageName(typeName);
				String likeElementClassName = TypeUtil.getClassName(typeName);
				
//				if (!typeName.contains(":")) {
//					likeElementType += namespace.getPrefix() + ":" + typeName;
//					likeElementPackageName = NameUtil.getPackageFromNamespace(namespace.getUri());
//					likeElementClassName = typeName;
//				} else {
//					int indexOf = typeName.indexOf(":");
//					typeName = typeName.substring(indexOf + 1);
//					//typeName = typeName.substring(indexOf + 1);
//					likeElementClassName = TypeUtil.getClassName(typeName);
//					if (!ClassUtil.isJavaDefaultType(likeElementClassName)) {
//						Element likeElement = context.getElementByType(typeName);
//						Assert.notNull(likeElement, "Element not found for 'like' clause of condition: "+likeElementName);
//						likeElementType = likeElement.getType();
//						likeElementPackageName = TypeUtil.getPackageName(likeElementType);
//						likeElementClassName = TypeUtil.getClassName(likeElementType);
//					}
//				}
					
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
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementListByQueryCondition(modelOperation, element, query);
		}
		
		return modelOperation;
	}
		
	/**
	 * GetElementListByPage operation
	 * ------------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementListByPage(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addParameter(createParameter("int", "pageIndex"));
		modelOperation.addParameter(createParameter("int", "pageSize"));
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementListByPage(modelOperation, element);
		}
		return modelOperation;
	}
	
	/**
	 * GetElementById operation
	 * ------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementById(Element element, boolean interfaceOnly) {
		String elementType = ModelLayerHelper.getElementType(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementTypeName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		Field field = context.findFieldByName(element, "id");
		if (field == null)
			System.out.println();
		Assert.notNull(field, "Field 'Id' not found for: "+elementType);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(fieldClassName, "id"));
		//modelOperation.addParameter(createParameter(fieldClassName, elementTypeName+"Id"));
		modelOperation.setResultType(elementClassName);
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementById(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetElementByKey operation
	 * -------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementByKey(Element element, boolean interfaceOnly) {
		String elementKey = ModelLayerHelper.getElementKey(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPart(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementKeyClassName = ModelLayerHelper.getElementKeyClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(CodeUtil.createParameter(elementKeyClassName, "key"));
		//modelOperation.addParameter(CodeUtil.createParameter(elementKeyClassName, elementType + "Key"));
		//modelOperation.addParameter(createParameter(elementKeyClassName, elementType+"Key"));
		modelOperation.setResultType(elementClassName);
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementByKey(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetElementListByIds operation
	 * -----------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementListByIds(Element element, boolean interfaceOnly) {
		String elementType = ModelLayerHelper.getElementType(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		Field field = context.findFieldByName(element, "id");
		Assert.notNull(field, "Field 'Id' not found for: "+elementType);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"RecordsByIds");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("List<Long>", "ids", "list"));
		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addImportedClass("java.util.List");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementById(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetElementsByKeys operation
	 * ---------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementsByKeys(Element element, boolean interfaceOnly) {
		String elementKey = ModelLayerHelper.getElementKey(element);
		String elementType = ModelLayerHelper.getElementTypeLocalPart(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementKeyClassName = ModelLayerHelper.getElementKeyClassName(element);
		String entityName = ModelLayerHelper.getElementNameCapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+entityName+"RecordsByKeys");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(CodeUtil.createParameter("List<"+elementKeyClassName+">", "keyList"));
		//modelOperation.addParameter(CodeUtil.createParameter("List<"+elementKeyClassName+">", elementType+"Keys"));
		modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addImportedClass("java.util.List");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementsByKeys(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetElementByField operations
	 * ---------------------------
	 */
	protected List<ModelOperation> createInstanceOperations_GetElementByField(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Field> uniqueFields = ElementUtil.getUniqueFields(element);
		Iterator<Field> iterator = uniqueFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			ModelOperation modelOperation = createInstanceOperation_GetElementByField(element, field, interfaceOnly);
			modelOperations.add(modelOperation);
		}
		return modelOperations;
	}

	/**
	 * GetElementByField operation
	 * --------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementByField(Element element, Field field, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldQualifiedName = ModelLayerHelper.getFieldQualifiedName(field);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+elementName+"RecordBy"+fieldNameCapped);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(fieldClassName, fieldNameUncapped));
		modelOperation.addImportedClass(fieldQualifiedName);
		modelOperation.setResultType(elementClassName);
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			provider.setField(field);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementByField(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * GetElementByQuery operations
	 * ----------------------------
	 */
	protected List<ModelOperation> createInstanceOperations_GetElementByQuery(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		String namespaceUri = TypeUtil.getNamespace(element.getType());
		Namespace namespace = context.getNamespaceByUri(namespaceUri);
		Collection<Query> queriesForElement = NamespaceUtil.getQueriesForElement(namespace, element);
		Iterator<Query> iterator = queriesForElement.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			Condition condition = QueryUtil.getCondition(query);
			if (query.getDistinct() && condition != null) {
				ModelOperation modelOperation = createInstanceOperation_GetElementByQuery(element, query, condition, interfaceOnly);
				modelOperations.add(modelOperation);
			}
		}
		return modelOperations;
	}
	
	/**
	 * GetElementByQuery operation
	 * ---------------------------
	 */
	protected ModelOperation createInstanceOperation_GetElementByQuery(Element element, Query query, Condition condition, boolean interfaceOnly) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(query.getName());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(elementClassName);

		List<Serializable> insAndLikes = condition.getInsAndLikes();
		Iterator<Serializable> iterator = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Like) {
				Like likeObject = (Like) object;
				String fieldName = likeObject.getField();
				String typeName = likeObject.getType();
				String likeElementPackageName = TypeUtil.getPackageName(typeName);
				String likeElementClassName = TypeUtil.getClassName(typeName);
					
				if (!ClassUtil.isJavaDefaultType(likeElementClassName))
					modelOperation.addImportedClass(likeElementPackageName+"."+likeElementClassName);
				modelOperation.addParameter(createParameter(likeElementClassName, fieldName));
				
			} else if (object instanceof In) {
				In inObject = (In) object;
				String fieldName = inObject.getField();
				String typeName = inObject.getType();
				String inElementPackageName = TypeUtil.getPackageName(typeName);
				String inElementClassName = TypeUtil.getClassName(typeName);
				
				//Element likeElement = context.getElementByType(typeName);
				//Assert.notNull(likeElement, "Element not found for 'like' clause of condition: "+fieldName);

				if (!ClassUtil.isJavaDefaultType(inElementClassName))
					modelOperation.addImportedClass(inElementPackageName+"."+inElementClassName);
				modelOperation.addImportedClass("java.util.List");

				String parameterName = fieldName + "List";
				String parameterClassName = "List<"+inElementClassName+">";
				modelOperation.addParameter(createParameter(parameterClassName, parameterName));
			}
		}
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_GetElementByQuery(modelOperation, element, query);
		}
		
		return modelOperation;
	}
	
	/**
	 * AddElement operation
	 * --------------------
	 */
	protected ModelOperation createInstanceOperation_AddElement(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("add"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (twoWaySelfReferencing)
			modelOperation.addParameter(createParameter("Long", "parentId"));
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		modelOperation.setResultType("Long");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_AddElement(modelOperation, element);
		}
		
		return modelOperation;
	}

	/**
	 * AddElementList operation
	 * ------------------------
	 */
	protected ModelOperation createInstanceOperation_AddElementList(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("add"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (twoWaySelfReferencing)
			modelOperation.addParameter(createParameter("Long", "parentId"));
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped+"List", "collection"));
		modelOperation.addImportedClass("java.util.Collection");
		modelOperation.setResultType("List<Long>");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_AddElementList(modelOperation, element);
		}
		
		return modelOperation;
	}

	/**
	 * AddElementMap operation
	 * -----------------------
	 */
	protected ModelOperation createInstanceOperation_AddElementMap(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("add"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (twoWaySelfReferencing)
			modelOperation.addParameter(createParameter("Long", "parentId"));
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped+"Map", "map"));
		modelOperation.setResultType("List<Long>");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_AddElementMap(modelOperation, element);
		}
		
		return modelOperation;
	}

	/**
	 * MoveElement operation
	 * ---------------------
	 */

	protected ModelOperation createInstanceOperation_MoveElement(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("move"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("Long", "parentId"));
		modelOperation.addParameter(createParameter("Long", elementNameUncapped+"Id"));

		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_MoveElement(modelOperation, element);
		}
		return modelOperation;
	}

	/**
	 * SaveElement operation
	 * ---------------------
	 */
	protected ModelOperation createInstanceOperation_SaveElement(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("save"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_SaveElement(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * SaveElementList operation
	 * -------------------------
	 */
	protected ModelOperation createInstanceOperation_SaveElementList(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("save"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped+"List", "collection"));
		modelOperation.addImportedClass("java.util.Collection");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_SaveElementList(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * SaveElementMap operation
	 * ------------------------
	 */
	protected ModelOperation createInstanceOperation_SaveElementMap(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("save"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped+"Map", "map"));
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_SaveElementMap(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * RemoveElement operation
	 * -----------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElement(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped));
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_RemoveElement(modelOperation, element);
		}
		
		return modelOperation;
	}

	/**
	 * RemoveElementById operation
	 * ----------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElementById(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String elementKeyClassName = ModelLayerHelper.getElementKeyClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("Long", "id"));
		//modelOperation.addParameter(createParameter("Long", elementNameUncapped+"Id"));
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_RemoveElementById(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * RemoveElementByKey operation
	 * ----------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElementByKey(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementKeyClassName = ModelLayerHelper.getElementKeyClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"Record");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementKeyClassName, "key"));
		//modelOperation.addParameter(createParameter(elementKeyClassName, elementNameUncapped+"Key"));
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_RemoveElementByKey(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * RemoveElementListByKeys operation
	 * ---------------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElementListByKeys(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementKeyClassName = ModelLayerHelper.getElementKeyClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"RecordsByKeys");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementKeyClassName, "keyList", "collection"));
		//modelOperation.addParameter(createParameter(elementKeyClassName, elementNameUncapped+"Keys", "collection"));
		modelOperation.addImportedClass("java.util.Collection");

		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_RemoveElementListByKeys(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * RemoveElementList operation
	 * ---------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElementList(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped+"List", "collection"));
		//modelOperation.setResultType("Collection<Long>");
		//modelOperation.setResultName("removedIds");
		modelOperation.addImportedClass("java.util.Collection");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_RemoveElementList(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * RemoveElementMap operation
	 * --------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElementMap(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter(elementClassName, elementNameUncapped+"Map", "map"));
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_RemoveElementMap(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * RemoveAllElements operation
	 * ---------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveAllElements(Element element, boolean interfaceOnly) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("removeAll"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_RemoveAllElements(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	/**
	 * RemoveElementListByCriteria operations
	 * -----------------------------------
	 */
	protected List<ModelOperation> createInstanceOperations_RemoveElementListByCriteria(Element element, boolean interfaceOnly) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		ModelOperation modelOperation = createInstanceOperation_RemoveElementListByCriteria(element, interfaceOnly);
		modelOperations.add(modelOperation);
		return modelOperations;
	}

	/**
	 * RemoveElementListByCriteria operation
	 * ----------------------------------
	 */
	protected ModelOperation createInstanceOperation_RemoveElementListByCriteria(Element element, boolean interfaceOnly) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String packageName = TypeUtil.getPackageName(element.getType());

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("remove"+elementName+"Records");
		modelOperation.setModifiers(Modifier.PUBLIC);
		//modelOperation.setResultType("List<"+elementClassName+">");
		modelOperation.addParameter(createParameter(elementType+"Criteria", elementTypeUncapped+"Criteria"));
		modelOperation.addImportedClass(packageName+"."+elementType+"Criteria");
		modelOperation.addImportedClass("java.util.List");
		
		if (interfaceOnly == false) {
			provider.setElement(element);
			modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
			provider.generateSourceCode_RemoveElementListByCriteria(modelOperation, element);
		}
		
		return modelOperation;
	}
	
	protected void addImportClasses(ModelOperation modelOperation, Operation operation) {
		List<Result> results = operation.getResults();
		Iterator<Result> iterator = results.iterator();
		while (iterator.hasNext()) {
			Result result = iterator.next();
			String construct = ResultUtil.getConstruct(result);
			if (construct.equals("list")) {
				modelOperation.addImportedClass("java.util.List");
			} else if (construct.equals("set")) {
				modelOperation.addImportedClass("java.util.Set");
			} else if (construct.equals("map")) {
				modelOperation.addImportedClass("java.util.Map");
			}
		}
		
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator2 = parameters.iterator();
		while (iterator2.hasNext()) {
			Parameter parameter = iterator2.next();
			String construct = ParameterUtil.getConstruct(parameter);
			if (construct.equals("list")) {
				modelOperation.addImportedClass("java.util.List");
			} else if (construct.equals("set")) {
				modelOperation.addImportedClass("java.util.Set");
			} else if (construct.equals("map")) {
				modelOperation.addImportedClass("java.util.Map");
			}
		}
	}
	
	
	/*
	 * Annotation creation methods
	 */
	
	protected ModelAnnotation createInstanceField_InjectionAnnotation(String packageName, String interfaceName, String contextName) {
		switch (context.getDataLayerBeanType()) {
		case EJB:
			return AnnotationUtil.createInjectAnnotation();
		case SEAM:
			//if (context.isEnabled("useQualifiedContextNames")) {
			//	String qualifiedName = packageName + "." + interfaceName;
			//	int segmentCount = NameUtil.getSegmentCount(qualifiedName);
			//	String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-1);
			//	contextName = contextPrefix + "." + contextName;
			//}
			//return AnnotationUtil.createInAnnotation(true, contextName);
		default:
			return null;
		}
	}

	protected ModelAnnotation createInstanceField_CreationAnnotation(Element element) {
		String className = ModelLayerHelper.getElementClassName(element);
		return AnnotationUtil.createNewAnnotation(className);
	}
	
	protected ModelAnnotation createInstanceField_CreationAnnotation(String className) {
		return AnnotationUtil.createNewAnnotation(className);
	}
	
}
