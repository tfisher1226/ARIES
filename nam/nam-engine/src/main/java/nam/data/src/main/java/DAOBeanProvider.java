package nam.data.src.main.java;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Condition;
import nam.model.Element;
import nam.model.Field;
import nam.model.In;
import nam.model.Like;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Query;
import nam.model.util.ElementUtil;
import nam.model.util.QueryUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;


public class DAOBeanProvider extends AbstractBeanProvider {

	protected Namespace namespace;
	
	
	public DAOBeanProvider(GenerationContext context) {
		super(context);
	}

	protected boolean isExtended() {
		return true;
	}
	

	/**
	 * SetEntityClass operation
	 * --------------------------
	 */

	public String getSourceCode_SetEntityClass() {
		Buf buf = new Buf();
		buf.putLine2("this.entityClass = entityClass;");
		return buf.get();
	}
	
	/**
	 * Initialize operation
	 * --------------------
	 */
	public String getSourceCode_Initialize(Element element) {
		Buf buf = new Buf();
		buf.putLine2("this.name = name;");
		return buf.get();
	}

	/**
	 * GetEntityManager operation
	 * --------------------------
	 */

	public String getSourceCode_GetEntityManager() {
		Buf buf = new Buf();
		buf.putLine2("return entityManager;");
		return buf.get();
	}
		
	/**
	 * GetRecordById operation
	 * -----------------------
	 */
	public String getSourceCode_GetRecordById(Element element) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = entityClassName;
		if (isExtended()) {
			resultClassName = "T";
		}
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(id, \"Id must be specified\");");
		buf.putLine2("Query query = entityManager.createNamedQuery(\"get\"+name+\"RecordById\");");
		//buf.putLine2("Query query = entityManager.createNamedQuery(\"get"+elementName+"RecordById\");");
		buf.putLine2("query.setParameter(\"id\", id);");
		buf.putLine2("try {");
		buf.putLine2("	Object object = query.getSingleResult();");
		buf.putLine2("	@SuppressWarnings(\"unchecked\") "+resultClassName+" result = ("+resultClassName+") object;");
		buf.putLine2("	return result;");
		buf.putLine2("} catch (NoResultException e) {");
		buf.putLine2("	return null;");
		buf.putLine2("}");
		return buf.get();
	}
	

	/**
	 * GetRecordByKey operation
	 * -----------------------
	 */
	public String getSourceCode_GetRecordByKey(Element element) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = entityClassName;
		if (isExtended()) {
			resultClassName = "T";
		}
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(key, \"Key must be specified\");");
		buf.putLine2("Query query = entityManager.createNamedQuery(\"get\"+name+\"RecordByKey\");");
		//buf.putLine2("Query query = entityManager.createNamedQuery(\"get"+elementName+"RecordByKey\");");
		buf.putLine2("query.setParameter(\"key\", key);");
		buf.putLine2("try {");
		buf.putLine2("	Object object = query.getSingleResult();");
		buf.putLine2("	@SuppressWarnings(\"unchecked\") "+resultClassName+" result = ("+resultClassName+") object;");
		buf.putLine2("	return result;");
		buf.putLine2("} catch (NoResultException e) {");
		buf.putLine2("	return null;");
		buf.putLine2("}");
		return buf.get();
	}
	
	
	/**
	 * GetRecordByField operation
	 * --------------------------
	 */
	public String getSourceCode_GetRecordByField(Element element, Field field) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		//String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		entityClassName = "T";
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+fieldNameUncapped+", \""+fieldNameCapped+" must be specified\");");
		buf.putLine2("Query query = entityManager.createNamedQuery(\"get\"+name+\"RecordBy"+fieldNameCapped+"\");");
		//buf.putLine2("Query query = entityManager.createNamedQuery(\"get"+elementClassName+"RecordBy"+fieldNameCapped+"\");");
		buf.putLine2("query.setParameter(\""+fieldNameUncapped+"\", "+fieldNameUncapped+");");
		buf.putLine2("try {");
		buf.putLine2("	Object object = query.getSingleResult();");
		buf.putLine2("	@SuppressWarnings(\"unchecked\") "+entityClassName+" result = ("+entityClassName+") object;");
		buf.putLine2("	return result;");
		buf.putLine2("} catch (NoResultException e) {");
		buf.putLine2("	return null;");
		buf.putLine2("}");
		return buf.get();
	}

	/**
	 * GetRecordByCriteria operation
	 * -----------------------------
	 */
	public String getSourceCode_GetRecordByCriteria(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		//String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		//String queryClassName = DataLayerHelper.getQueryClassName(element);
		
		String queryPackageName = DataLayerHelper.getQueryPackageName(namespace);
		String queryClassName = DataLayerHelper.getQueryClassName(element);
		String queryBeanName = DataLayerHelper.getQueryNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+" record criteria must be specified\");");
		buf.putLine2("Assert.notNull(entityClass, \"EntityClass must be specified\");");
		buf.putLine2(queryClassName+"<T> query = new "+queryClassName+"<T>(entityManager);");
		buf.putLine2("query.setEntityClass(entityClass);");
		buf.putLine2("query.integrateCriteria("+elementTypeUncapped+"Criteria);");
		buf.putLine2("T result = query.getSingleResult();");
		buf.putLine2("return result;");
		
		modelOperation.addImportedClass(queryPackageName+"."+queryClassName);
		modelOperation.addImportedClass(elementPackageName+"."+elementClassName+"Criteria");
		return buf.get();
	}
	
	/**
	 * GetRecordByQuery operation
	 * --------------------------
	 */
	public String getSourceCode_GetRecordByQuery(ModelOperation modelOperation, Element element, Query query) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String queryClassName = DataLayerHelper.getQueryClassName(element);

		Buf buf = new Buf();
		List<String> parameters = QueryUtil.getParameterNames(query);
		Iterator<String> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			String parameterName = iterator.next();
			String parameterNameCapped = NameUtil.capName(parameterName);
			buf.putLine2("Assert.notNull("+parameterName+", \""+parameterNameCapped+" parameter must be specified\");");
		}

		buf.putLine2(elementNameCapped+"Query query = new "+queryClassName+"(entityManager);");
		buf.putLine2(elementNameCapped+"Criteria criteria = new "+elementNameCapped+"Criteria();");
		Condition condition = QueryUtil.getCondition(query);
		List<Serializable> insAndLikes = condition.getInsAndLikes();
		Iterator<Serializable> iterator2 = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator2.next();
			if (object instanceof Like) {
				Like likeObject = (Like) object;
				String fieldName = likeObject.getField();
				String fieldNameCapped = NameUtil.capName(fieldName);
				buf.putLine2("criteria.set"+fieldNameCapped+"("+fieldName+");");
				
			} else if (object instanceof In) {
				In inObject = (In) object;
				String fieldName = inObject.getField();
				String fieldNameCapped = NameUtil.capName(fieldName);
				buf.putLine2("criteria.get"+fieldNameCapped+"().add("+fieldName+");");
			}
		}
		
		buf.putLine2("query.integrateCriteria(criteria);");
		buf.putLine2(entityClassName+" result = query.getSingleResult();");
		buf.putLine2("return result;");
		
		modelOperation.addImportedClass(elementPackageName+"."+elementClassName+"Criteria");
		return buf.get();
	}

	/**
	 * GetAllRecords operation
	 * -----------------------
	 */
	
	public String getSourceCode_GetAllRecords(Element element) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = entityClassName;
		if (isExtended())
			resultClassName = "T";
		
		Buf buf = new Buf();
		if (isExtended())
			buf.putLine2("Query query = entityManager.createNamedQuery(\"getAll\"+name+\"Records\");");
		else buf.putLine2("Query query = entityManager.createNamedQuery(\"getAll\"+name+\"Records\");");
		//else buf.putLine2("Query query = entityManager.createNamedQuery(\"getAll"+elementName+"Records\");");
		buf.putLine2("List<"+resultClassName+"> result = query.getResultList();");
		buf.putLine2("return result;");
		return buf.get();
	}

	/**
	 * GetRecordListByField operation
	 * ------------------------------
	 */
	public String getSourceCode_GetRecordListByField(Element element, Field field) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String resultClassName = entityClassName;
		if (isExtended())
			resultClassName = "T";
		
		//Element fieldElement = context.getElementByType(field.getType());
		//String fieldNameUncapped = ModelLayerHelper.getElementNameUncapped(fieldElement);
		//String fieldEntityClassName = DataLayerHelper.getEntityClassName(fieldElement);
		//String fieldEntityName = DataLayerHelper.getEntityNameUncapped(fieldElement);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+fieldNameUncapped+", \""+fieldNameCapped+" must be specified\");");
		if (isExtended())
			buf.putLine2("Query query = entityManager.createNamedQuery(\"get\"+name+\"RecordsBy"+fieldNameCapped+"\");");
		else buf.putLine2("Query query = entityManager.createNamedQuery(\"get\"+name+\"RecordsBy"+fieldNameCapped+"\");");
		//else buf.putLine2("Query query = entityManager.createNamedQuery(\"get"+elementName+"RecordsBy"+fieldNameCapped+"\");");
		buf.putLine2("query.setParameter(\""+fieldNameUncapped+"\", "+fieldNameUncapped+");");
		buf.putLine2("List<"+resultClassName+"> result = query.getResultList();");
		buf.putLine2("return result;");
		return buf.get();
	}
	
	/**
	 * GetRecordListForPage operation
	 * ------------------------------
	 */
	
	//TODO add sanity checks for pageIndex and pageSize params
	public String getSourceCode_GetRecordListByPage(Element element) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String resultClassName = entityClassName;
		if (isExtended())
			resultClassName = "T";
		
		Buf buf = new Buf();
		if (isExtended())
			buf.putLine2("Query query = entityManager.createNamedQuery(\"get\"+name+\"RecordsByPage\");");
		else buf.putLine2("Query query = entityManager.createNamedQuery(\"get\"+name+\"RecordsByPage\");");
		//else buf.putLine2("Query query = entityManager.createNamedQuery(\"get"+elementName+"RecordsByPage\");");
		buf.putLine2("query.setFirstResult(pageIndex * pageSize);");
		buf.putLine2("query.setMaxResults(pageSize);");
		buf.putLine2("List<"+resultClassName+"> result = query.getResultList();");
		buf.putLine2("return result;");
		return buf.get();
	}
	
	/**
	 * GetRecordListByCriteria operation
	 * ---------------------------------
	 */
	public String getSourceCode_GetRecordListByCriteria(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String queryClassName = DataLayerHelper.getQueryClassName(element);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+" record criteria must be specified\");");
		buf.putLine2("Assert.notNull(entityClass, \"EntityClass must be specified\");");
		buf.putLine2(elementType+"Query<T> query = new "+elementType+"Query<T>(entityManager);");
		buf.putLine2("query.setEntityClass(entityClass);");
		buf.putLine2("query.integrateCriteria("+elementTypeUncapped+"Criteria);");
		buf.putLine2("List<T> results = query.getResultList();");
		buf.putLine2("return results;");
		modelOperation.addImportedClass(elementPackageName+"."+elementType+"Criteria");
		return buf.get();
	}
	
	/**
	 * GetRecordListByCriteriaSet operation
	 * ---------------------------------
	 */
	public String getSourceCode_GetRecordListByCriteriaSet(Element element) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String criteriaClassName = elementClassName + "Criteria";

		Buf buf = new Buf();
		buf.putLine2("List<"+entityClassName+"> results = new ArrayList<"+entityClassName+">();"); 
		buf.putLine2("Iterator<"+elementClassName+"Criteria> iterator = "+elementNameUncapped+"CriteriaSet.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+criteriaClassName+" "+elementNameUncapped+"Criteria = iterator.next();");
		buf.putLine2("	List<"+entityClassName+"> incrementalResults = get"+elementClassName+"sByCriteria("+elementNameUncapped+"Criteria);");
		buf.putLine2("	results.addAll(incrementalResults);");
		buf.putLine2("}");
		buf.putLine2("return results;");
		return buf.get();
	}
	
	/**
	 * GetRecordListByQueryCriteria operation
	 * ---------------------------------
	 */
	public String getSourceCode_GetRecordListByQueryCriteria(ModelOperation modelOperation, Element element, Query query) {
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String queryClassName = DataLayerHelper.getQueryClassName(element);

		Buf buf = new Buf();
		buf.putLine2(elementClassName+"Query query = new "+queryClassName+"(entityManager);");
		buf.putLine2("query.integrateCriteria("+elementNameUncapped+"Criteria);");
		buf.putLine2("List<"+entityClassName+"> results = query.getResultList();");
		buf.putLine2("return results;");
		
		modelOperation.addImportedClass(elementPackageName+"."+elementClassName+"Criteria");
		return buf.get();
	}
	
	/**
	 * GetRecordListByQueryCondition operation
	 * ------------------------------
	 */
	public String getSourceCode_GetRecordListByQueryCondition(ModelOperation modelOperation, Element element, Query query) {
		//String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String queryClassName = DataLayerHelper.getQueryClassName(element);

		Buf buf = new Buf();
		List<String> parameters = QueryUtil.getParameterNames(query);
		Iterator<String> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			String parameterName = iterator.next();
			String parameterNameCapped = NameUtil.capName(parameterName);
			buf.putLine2("Assert.notNull("+parameterName+", \""+parameterNameCapped+" parameter must be specified\");");
		}

		buf.putLine2(elementNameCapped+"Query query = new "+queryClassName+"(entityManager);");
		buf.putLine2(elementNameCapped+"Criteria criteria = new "+elementNameCapped+"Criteria();");
		Condition condition = QueryUtil.getCondition(query);
		List<Serializable> insAndLikes = condition.getInsAndLikes();
		Iterator<Serializable> iterator2 = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator2.next();
			if (object instanceof Like) {
				Like likeObject = (Like) object;
				String fieldName = likeObject.getField();
				String fieldNameCapped = NameUtil.capName(fieldName);
				buf.putLine2("criteria.set"+fieldNameCapped+"("+fieldName+");");
				
			} else if (object instanceof In) {
				In inObject = (In) object;
				String fieldName = inObject.getField();
				String fieldNameCapped = NameUtil.capName(fieldName);
				buf.putLine2("criteria.get"+fieldNameCapped+"().add("+fieldName+");");
			}
		}
		
		buf.putLine2("query.integrateCriteria(criteria);");
		buf.putLine2("List<"+entityClassName+"> results = query.getResultList();");
		buf.putLine2("return results;");
		
		modelOperation.addImportedClass(elementPackageName+"."+elementClassName+"Criteria");
		return buf.get();
	}

	/**
	 * AddRecord operation
	 * -------------------
	 */
	
	//TODO Use SaveRecord provider method i.e. invoke it
	public String getSourceCode_AddRecord(Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String parameterName = elementName;

		Buf buf = new Buf();
		if (twoWaySelfReferencing) {
			buf.putLine2("Assert.notNull(parentId, \"ParentId must exist\");");
			buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
			buf.putLine2("");
			buf.putLine2("try {");
			buf.putLine2("	transactionHelper.open();");
			buf.putLine2("	entityManager.joinTransaction();");
			buf.putLine2("");
			buf.putLine2("	"+entityClassName+" parent = getOrganizationById(parentId);");
			buf.putLine2("	"+elementName+" = entityManager.merge("+elementName+");");
			buf.putLine2("	"+elementName+".setParent(parent);");
			buf.putLine2("	parent.getChildren().add("+elementName+");");
			buf.putLine2("	entityManager.persist(parent);");
			buf.putLine2("	transactionHelper.close();");
			buf.putLine2("");
			buf.putLine2("	Long id = "+elementName+".getId();");
			buf.putLine2("	return id;");
			buf.putLine2("");
			buf.putLine2("} catch (Throwable e) {");
			buf.putLine2("	log.error(e);");
			buf.putLine2("	transactionHelper.fault(e);");
			buf.putLine2("	throw ExceptionUtil.rewrap(e);");
			buf.putLine2("}");
			
		} else {
			buf.putLine2("Assert.notNull("+parameterName+"Record, \""+elementNameCapped+" record parameter must be specified\");");
			buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
			buf.putLine2("");
			buf.putLine2("try {");
			buf.putLine2("	transactionHelper.open();");
			buf.putLine2("	entityManager.joinTransaction();");
			buf.putLine2("");
			buf.putLine2("	T merged = entityManager.merge("+parameterName+"Record);");
			buf.putLine2("	entityManager.persist(merged);");
			buf.putLine2("	transactionHelper.close();");
			buf.putLine2("");
			buf.putLine2("	Long id = merged.getId();");
			buf.putLine2("	"+parameterName+"Record.setId(id);");
			buf.putLine2("	return id;");
			buf.putLine2("");
			buf.putLine2("} catch (Throwable e) {");
			buf.putLine2("	log.error(e);");
			buf.putLine2("	transactionHelper.fault(e);");
			buf.putLine2("	throw ExceptionUtil.rewrap(e);");
			buf.putLine2("}");
		}
		return buf.get();
	}
	
	/**
	 * AddRecords operation
	 * --------------------
	 */
	
	//TODO Use SaveRecord provider method i.e. invoke it
	public String getSourceCode_AddRecords(Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String parameterName = elementName + "Records";

		Buf buf = new Buf();
		if (twoWaySelfReferencing) {
			//TODO
			
		} else {
			buf.putLine2("Assert.notNull("+parameterName+", \""+elementNameCapped+" records parameter must be specified\");");
			buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
			buf.putLine2("");
			buf.putLine2("try {");
			buf.putLine2("	transactionHelper.open();");
			buf.putLine2("	entityManager.joinTransaction();");
			buf.putLine2("");
			buf.putLine2("	List<Long> idList = new ArrayList<Long>();");
			buf.putLine2("	Iterator<T> iterator = "+parameterName+".iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		T "+elementName+"Record = iterator.next();");
			buf.putLine2("		T merged = entityManager.merge("+elementName+"Record);");
			buf.putLine2("		entityManager.persist(merged);");
			buf.putLine2("		Long id = merged.getId();");
			buf.putLine2("		"+elementName+"Record.setId(id);");
			buf.putLine2("		idList.add(id);");
			buf.putLine2("	}");
			buf.putLine2("");
			buf.putLine2("	transactionHelper.close();");
			buf.putLine2("	return idList;");
			buf.putLine2("");
			buf.putLine2("} catch (Throwable e) {");
			buf.putLine2("	log.error(e);");
			buf.putLine2("	transactionHelper.fault(e);");
			buf.putLine2("	throw ExceptionUtil.rewrap(e);");
			buf.putLine2("}");
		}
		return buf.get();
	}
	
	/**
	 * MoveRecord operation
	 * --------------------
	 */
	public String getSourceCode_MoveRecord(Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(parentId, \"ParentId must exist\");");
		buf.putLine2("Assert.notNull("+elementName+"Id, \""+elementNameCapped+"Id must exist\");");
		buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	transactionHelper.open();");
		buf.putLine2("	entityManager.joinTransaction();");
		buf.putLine2("");
		buf.putLine2("	"+entityClassName+" "+elementName+" = get"+elementNameCapped+"ById("+elementName+"Id);");
		buf.putLine2("	"+entityClassName+" newParent = get"+elementNameCapped+"ById(parentId);");
		buf.putLine2("");
		buf.putLine2("	"+entityClassName+" currentParent = "+elementName+".getParent();");
		buf.putLine2("	currentParent.getChildren().remove("+elementName+");");
		buf.putLine2("");
		buf.putLine2("	"+elementName+".setParent(newParent);");
		buf.putLine2("	newParent.getChildren().add("+elementName+");");
		buf.putLine2("");
		buf.putLine2("	entityManager.persist(newParent);");
		buf.putLine2("	entityManager.persist(currentParent);");
		buf.putLine2("	transactionHelper.close();");
		buf.putLine2("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(e);");
		buf.putLine2("	transactionHelper.fault(e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		return buf.get();
	}
	
	/**
	 * SaveRecord operation
	 * --------------------
	 */
	
	public String getSourceCode_SaveRecord(Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String parameterName = elementName + "Record";

		Buf buf = new Buf();
		if (twoWaySelfReferencing) {
			buf.putLine2("Assert.notNull("+elementName+".getId(), \""+elementNameCapped+"Id must exist\");");
			buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
			buf.putLine2("");
			//buf.putLine2("//EntityManager entityManager = entityManager;");
			//buf.putLine2("//EntityTransaction transaction = entityManager.getTransaction();");
			buf.putLine2("Long id = "+elementName+".getId();");
			buf.putLine2("");
			buf.putLine2("try {");
			//buf.putLine2("	//transaction.begin();");
			buf.putLine2("	transactionHelper.open();");
			buf.putLine2("	entityManager.joinTransaction();");
			buf.putLine2("");
			buf.putLine2("	if (id != null) {");
			buf.putLine2("		"+entityClassName+" original = get"+elementNameCapped+"ById("+elementName+".getId());");
			buf.putLine2("		"+elementName+".setParent(original.getParent());");
			buf.putLine2("	}"); 
			buf.putLine2("");
			buf.putLine2("	"+elementName+" = entityManager.merge("+elementName+");");
			buf.putLine2("	entityManager.persist("+elementName+");");
			buf.putLine2("	transactionHelper.close();");
			buf.putLine2("");
			buf.putLine2("} catch (Throwable e) {");
			//buf.putLine2("	//transaction.rollback();");
			buf.putLine2("	log.error(e);");
			buf.putLine2("	transactionHelper.fault(e);");
			buf.putLine2("	throw ExceptionUtil.rewrap(e);");
			buf.putLine2("}");
			
		} else {
			buf.putLine2("Assert.notNull("+parameterName+", \""+elementNameCapped+" record parameter must be specified\");");
			buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
			buf.putLine2("");
			buf.putLine2("try {");
			buf.putLine2("	transactionHelper.open();");
			buf.putLine2("	entityManager.joinTransaction();");
			buf.putLine2("");
			buf.putLine2("	T merged = entityManager.merge("+parameterName+");");
			buf.putLine2("	entityManager.persist(merged);");
			buf.putLine2("	transactionHelper.close();");
			buf.putLine2("");
			buf.putLine2("} catch (Throwable e) {");
			buf.putLine2("	log.error(e);");
			buf.putLine2("	transactionHelper.fault(e);");
			buf.putLine2("	throw ExceptionUtil.rewrap(e);");
			buf.putLine2("}");
		}
		
//		//buf.putLine2("//EntityManager entityManager = entityManager;");
//		//buf.putLine2("//EntityTransaction transaction = entityManager.getTransaction();");
//		//buf.putLine("");
//		buf.putLine2("try {");
//		//buf.putLine2("	//transaction.begin();");
//		buf.putLine2("	"+elementName+" = merge("+elementName+");");
//		//buf.putLine2("	//saveLeadershipInfo(memberEntity);");
//		//buf.putLine2("	//saveNotes(memberEntity);");
//		buf.putLine2("	persist("+elementName+");");
//		buf.putLine("");
//		buf.putLine2("} catch (Throwable e) {");
//		//buf.putLine2("	//transaction.rollback();");
//		buf.putLine2("	log.error(e);");
//		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
//		buf.putLine2("}");
		
//		List<Field> fields = ElementUtil.getFields(element);
//		Iterator<Field> iterator = fields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			if (field instanceof Reference) {
//				Reference reference = (Reference) field;
//				if (FieldUtil.isOneToOne(reference) || FieldUtil.isOneToMany(reference)) {
//					String fieldName = DataLayerHelper.getFieldNameCapped(field);
//					buf.putLine2(entityName+".set"+fieldName+"(merge("+entityName+".get"+fieldName+"()));");
//				}
//			}
//		}
//		buf.putLine2(entityName+" = merge("+entityName+");");
//		buf.putLine2("persist("+entityName+");");
//		buf.putLine2("Long id = "+entityName+".getId();");
//		buf.putLine2("return id;");
		return buf.get();
	}

	/**
	 * SaveRecords operation
	 * ---------------------
	 */
	
	public String getSourceCode_SaveRecords(Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String parameterName = elementName + "Records";

		Buf buf = new Buf();
		if (twoWaySelfReferencing) {
			//TODO
			
		} else {
			buf.putLine2("Assert.notNull("+parameterName+", \""+elementNameCapped+"Records parameter must be specified\");");
			buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
			buf.putLine2("");
			buf.putLine2("try {");
			buf.putLine2("	transactionHelper.open();");
			buf.putLine2("	entityManager.joinTransaction();");
			buf.putLine2("");
			buf.putLine2("	Iterator<T> iterator = "+parameterName+".iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		T "+elementName+"Record = iterator.next();");
			buf.putLine2("		T merged = entityManager.merge("+elementName+"Record);");
			buf.putLine2("		entityManager.persist(merged);");
			buf.putLine2("	}");
			buf.putLine2("");
			buf.putLine2("	transactionHelper.close();");
			buf.putLine2("");
			buf.putLine2("} catch (Throwable e) {");
			buf.putLine2("	log.error(e);");
			buf.putLine2("	transactionHelper.fault(e);");
			buf.putLine2("	throw ExceptionUtil.rewrap(e);");
			buf.putLine2("}");
		}
		return buf.get();
	}

	/**
	 * RemoveAllRecords operation
	 * --------------------------
	 */
	public String getSourceCode_RemoveAllRecords(Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	transactionHelper.open();");
		buf.putLine2("	entityManager.joinTransaction();");
		//buf.putLine3("");
		//buf.putLine2("	Query query = entityManager.createNamedQuery(\"removeAll\"+name+\"Records\");");
		//buf.putLine2("	query.executeUpdate();");
		buf.putLine3("");
		buf.putLine2("	Query query = entityManager.createNamedQuery(\"getAll\"+name+\"Records\");");
		buf.putLine2("	@SuppressWarnings(\"unchecked\") List<T> records = query.getResultList();");
		buf.putLine3("");
		buf.putLine2("	Iterator<T> iterator = records.iterator();");
		buf.putLine2("	while (iterator.hasNext()) {");
		buf.putLine2("		T "+elementName+"Record = iterator.next();");
		buf.putLine2("		T merged = entityManager.merge("+elementName+"Record);");
		buf.putLine2("		entityManager.remove(merged);");
		buf.putLine2("	}");
		buf.putLine3("");
		buf.putLine2("	transactionHelper.close();");
		buf.putLine3("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(e);");
		buf.putLine2("	transactionHelper.fault(e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		return buf.get();
	}
	
	/**
	 * RemoveRecord operation
	 * ----------------------
	 */
	public String getSourceCode_RemoveRecord(Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String parameterName  = elementName + "Record";
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+parameterName+", \""+elementNameCapped+" record parameter must be specified\");");
		buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	transactionHelper.open();");
		buf.putLine2("	entityManager.joinTransaction();");
		buf.putLine3("");
		buf.putLine2("	T merged = entityManager.merge("+parameterName+");");
		buf.putLine2("	entityManager.remove(merged);");
		buf.putLine2("	transactionHelper.close();");
		buf.putLine3("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(e);");
		buf.putLine2("	transactionHelper.fault(e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");

//		buf.putLine2(entityName+" = merge("+entityName+");");
//		List<Field> fields = ElementUtil.getFields(element);
//		Iterator<Field> iterator = fields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			if (field instanceof Reference) {
//				Reference reference = (Reference) field;
//				if (FieldUtil.isOneToOne(reference) || FieldUtil.isOneToMany(reference)) {
//					String fieldName = DataLayerHelper.getFieldNameCapped(field);
//					buf.putLine2("delete("+entityName+".get"+fieldName+"());");
//				}
//			}
//		}
//		
//		buf.putLine2("delete("+entityName+");");
		return buf.get();
	}

	/**
	 * RemoveRecordById operation
	 * --------------------------
	 */
	public String getSourceCode_RemoveRecordById(Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String parameterName  = "id";
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(id, \"Record Id parameter must be specified\");");
		buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	transactionHelper.open();");
		buf.putLine2("	entityManager.joinTransaction();");
		buf.putLine3("");
		buf.putLine2("	T merged = entityManager.merge("+parameterName+");");
		buf.putLine2("	entityManager.remove(merged);");
		buf.putLine2("	transactionHelper.close();");
		buf.putLine3("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(e);");
		buf.putLine2("	transactionHelper.fault(e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		return buf.get();
	}
	
	/**
	 * RemoveRecordByKey operation
	 * ---------------------------
	 */
	public String getSourceCode_RemoveRecordByKey(Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String parameterName  = "key";
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(key, \"Record Key parameter must be specified\");");
		buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	transactionHelper.open();");
		buf.putLine2("	entityManager.joinTransaction();");
		buf.putLine3("");
		buf.putLine2("	T merged = entityManager.merge("+parameterName+");");
		buf.putLine2("	entityManager.remove(merged);");
		buf.putLine2("	transactionHelper.close();");
		buf.putLine3("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(e);");
		buf.putLine2("	transactionHelper.fault(e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		return buf.get();
	}

	/**
	 * RemoveRecords operation
	 * -----------------------
	 */
	
	public String getSourceCode_RemoveRecords(Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String parameterName  = elementName + "Records";
		
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+parameterName +", \""+elementNameCapped+"Records parameter must be specified\");");
		buf.putLine2("TransactionHelper transactionHelper = new TransactionHelper();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	transactionHelper.open();");
		buf.putLine2("	entityManager.joinTransaction();");
		buf.putLine3("");
		buf.putLine2("	Iterator<T> iterator = "+parameterName +".iterator();");
		buf.putLine2("	while (iterator.hasNext()) {");
		buf.putLine2("		T "+elementName+"Record = iterator.next();");
		buf.putLine2("		T merged = entityManager.merge("+elementName+"Record);");
		buf.putLine2("		entityManager.remove(merged);");
		buf.putLine2("	}");
		buf.putLine3("");
		buf.putLine2("	transactionHelper.close();");
		buf.putLine3("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(e);");
		buf.putLine2("	transactionHelper.fault(e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		return buf.get();
	}
	
	/**
	 * RemoveRecordListByCriteria operation
	 * ---------------------------------
	 */
	public String getSourceCode_RemoveRecordListByCriteria(ModelOperation modelOperation, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementTypeUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String queryClassName = DataLayerHelper.getQueryClassName(element);

		Buf buf = new Buf();
		buf.putLine2("Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+" record criteria must be specified\");");
		buf.putLine2("Assert.notNull(entityClass, \"Entity class must be specified\");");
		buf.putLine2(elementType+"Query<T> query = new "+elementType+"Query<T>(entityManager);");
		buf.putLine2("query.setEntityClass(entityClass);");
		buf.putLine2("query.integrateCriteria("+elementTypeUncapped+"Criteria);");
		buf.putLine2("query.executeUpdate();");
		
//		buf.putLine2("Assert.notNull("+elementTypeUncapped+"Criteria, \""+elementType+" record criteria must be specified\");");
//		buf.putLine2("Iterator<Map<String, Object>> iterator = "+elementTypeUncapped+"Criteria.getAllFieldSets().iterator();");
//		buf.putLine2("while (iterator.hasNext()) {");
//		buf.putLine2("	Map<String, Object> fields = iterator.next();");
//		buf.putLine2("	remove"+elementType+"Records(fields);");
//		buf.putLine2("}");
		modelOperation.addImportedClass(elementPackageName+"."+elementType+"Criteria");
		return buf.get();
	}
	

}
