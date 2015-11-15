package nam.data.src.main.java;

import java.io.Serializable;
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
import nam.model.OrderBy;
import nam.model.Query;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.QueryUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;


public class QueryBeanProvider extends AbstractBeanProvider {

	private boolean generateInterface;

	
	public QueryBeanProvider(GenerationContext context) {
		super(context);
	}
	
	/*
	 * Interface Javadoc generation
	 * ----------------------------
	 */

	public String getJavadoc_Interface(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		
		Buf buf = new Buf();
		buf.putLine2("/**");
		buf.putLine2(" * Provides a custom dynamic {@link "+elementClassName+"Query} object for looking up elements of type to {@link "+elementClassName+"}.");
		buf.putLine2(" *"); 
		buf.putLine2(" * @author "+context.getAuthor());
		buf.putLine2(" */");
		return buf.get();
	}

	/*
	 * Class Javadoc generation
	 * ------------------------
	 */
	
	protected String getJavadoc_Class(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);

		Buf buf = new Buf();
		buf.putLine(" * Provides a custom dynamic {@link "+elementClassName+"Query} object for looking up elements of type to {@link "+elementClassName+"}.");
		return buf.get();
	}
	
	
	/*
	 * toEntity() method
	 */

	public String getSourceCode_ToModel(Element element, Field owner) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementType = element.getType(); 

		Buf buf = new Buf();
		buf.putLine2("if ("+elementName+"Entity == null)");
		buf.putLine2("	return null;");
		buf.putLine2(elementClassName+" "+elementName+"Model = new "+elementClassName+"();");
		
		buf.putLine2("return "+elementName+"Model;");
		return buf.get();
	}

	public String getSourceCode_Constructor(Element element) {
		Buf buf = new Buf();
		buf.putLine2("super(entityManager);");
		return buf.get();
	}

	public String getSourceCode_IntegrateCriteria(Element element, Query query) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("query = createCriteriaQuery("+elementName+"Criteria);");
		return buf.get();
	}

	public String getSourceCode_GetResultList(Element element, Query query) {
		Buf buf = new Buf();
		buf.putLine2("return executeCriteriaQuery(query);");
		return buf.get();
	}

	public String getSourceCode_CreateCriteriaQuery(Element element, Query query) {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);

		Buf buf = new Buf();
		buf.putLine2("CriteriaQuery<"+entityClassName+"> criteriaQuery = criteriaBuilder.createQuery("+entityClassName+".class);");
		buf.putLine2("criteriaQuery.distinct("+query.getDistinct()+");");
		buf.putLine2("root = criteriaQuery.from(entityClass);");
		buf.putLine2("@SuppressWarnings(\"unchecked\") CriteriaQuery<T> select = (CriteriaQuery<T>) criteriaQuery.select(root);");
		
		OrderBy orderBy = query.getOrderBy();
		if (orderBy != null && orderBy.getItems() != null) {
			buf.putLine2("List<Order> orderByList = new ArrayList<Order>();");
			List<Item> items = orderBy.getItems();
			Iterator<Item> iterator = items.iterator();
			while (iterator.hasNext()) {
				Item item = iterator.next();
				String fieldName = item.getName();
				String direction = item.getAscend() ? "asc" : "desc";
				buf.putLine2("orderByList.add(criteriaBuilder."+direction+"(root.get(\""+fieldName+"\")));");
			}
			buf.putLine2("select = select.orderBy(orderByList);");
		}
		
		buf.putLine2("Predicate predicate = buildPredicates("+elementName+"Criteria);");
		buf.putLine2("select.where(predicate);");
		buf.putLine2("return select;");
		return buf.get();
	}

	public String getSourceCode_BuildPredicates(Element element, Query query) {
		Condition condition = QueryUtil.getCondition(query);
		Criteria criteria = QueryUtil.getCriteria(query);
		if (condition != null) {
			return getSourceCode_BuildPredicates(element, query, condition.getInsAndLikes());
		} else if (criteria != null) {
			return getSourceCode_BuildPredicates(element, query, criteria.getInsAndLikes());
		} else {
			return null;
		}
	}
	
	public String getSourceCode_BuildPredicates(Element element, Query query, List<Serializable> insAndLikes) {
		Buf buf = new Buf();
		buf.putLine2("Predicate predicate = criteriaBuilder.conjunction();");
		
		String fromType = query.getFrom();
		String fromElementName = TypeUtil.getLocalPart(fromType);
		
		Iterator<Serializable> iterator = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Like) {
				Like likeOp = (Like) object;
				String fieldName = ElementUtil.getTargetFieldName(element, likeOp.getField());
				String fieldNameCapped = NameUtil.capName(fieldName);
				buf.putLine2("integrate"+fieldNameCapped+"Predicate(predicate, "+fromElementName+"Criteria.get"+fieldNameCapped+"());");
				
			} else if (object instanceof In) {
				In inOp = (In) object;
				String fieldName = ElementUtil.getTargetFieldName(element, inOp.getField());
				String fieldNameCapped = NameUtil.capName(fieldName);
				buf.putLine2("integrate"+fieldNameCapped+"Predicate(predicate, "+fromElementName+"Criteria.get"+fieldNameCapped+"Set());");
			}
		}
		buf.putLine2("return predicate;");
		return buf.get();
	}

//	public String getSourceCode_BuildPredicates(Element element, Query query, Condition condition) {
//		Buf buf = new Buf();
//		buf.putLine2("Predicate predicate = criteriaBuilder.conjunction();");
//		
//		String fromType = query.getFrom();
//		String fromElementName = TypeUtil.getLocalPart(fromType);
//		
//		List<Serializable> insAndLikes = condition.getInsAndLikes();
//		Iterator<Serializable> iterator = insAndLikes.iterator();
//		while (iterator.hasNext()) {
//			Serializable object = iterator.next();
//			if (object instanceof Like) {
//				Like likeOp = (Like) object;
//				String fieldName = likeOp.getField();
//				String fieldNameCapped = NameUtil.capName(fieldName);
//				buf.putLine2("integrate"+fieldNameCapped+"Predicate(predicate, "+fromElementName+"Criteria.get"+fieldNameCapped+"());");
//				
//			} else if (object instanceof In) {
//				In inOp = (In) object;
//				String fieldName = inOp.getField();
//				String fieldNameCapped = NameUtil.capName(fieldName);
//				buf.putLine2("integrate"+fieldNameCapped+"Predicate(predicate, "+fromElementName+"Criteria.get"+fieldNameCapped+"Set());");
//			}
//		}
//		buf.putLine2("return predicate;");
//		return buf.get();
//	}

	public String getSourceCode_IntegratePredicate(Element element, Query query, Like likeOp) {
		String fieldName = likeOp.getField();
		
		Buf buf = new Buf();
		buf.putLine2("Path<Object> path = root.get(\""+fieldName+"\");");
		buf.putLine2("Predicate "+fieldName+"Predicate = path.in("+fieldName+");");
		buf.putLine2("predicate.getExpressions().add("+fieldName+"Predicate);");
		return buf.get();
	}
	
	public String getSourceCode_IntegratePredicate(Element element, Query query, In inOp) {
		String fieldName = inOp.getField();
		Element targetElement = context.getElementByType(inOp.getType());
		Enumeration targetEnumeration = context.getEnumerationByType(inOp.getType());
		Field targetField = ElementUtil.getTargetField(element, inOp.getField());
		
		Buf buf = new Buf();
		if (targetElement != null) {
			String elementName = ModelLayerHelper.getElementNameUncapped(targetElement);
			String elementClassName = ModelLayerHelper.getElementClassName(targetElement);
			String targetFieldName = ElementUtil.getTargetFieldName(element, fieldName);
			String targetFieldSetName = targetFieldName + "Set";
			if (targetFieldName.endsWith("Id")) {
				elementClassName = "Long";
			}
			
			buf.putLine2("Set<Long> ids = new HashSet<Long>();");
			buf.putLine2("Iterator<"+elementClassName+"> iterator = "+targetFieldSetName+".iterator();");
			buf.putLine2("while (iterator.hasNext()) {");
			buf.putLine2("	"+elementClassName+" "+targetFieldName+" = iterator.next();");
			if (targetFieldName.endsWith("Id"))
				buf.putLine2("	ids.add("+targetFieldName+");");
			else buf.putLine2("	ids.add("+targetFieldName+".getId());");
			buf.putLine2("}");
			buf.putLine2("if ("+targetFieldSetName+" != null && "+targetFieldSetName+".size() > 0) {");
			if (targetFieldName.endsWith("Id"))
				buf.putLine2("	Path<Object> path = root.get(\""+fieldName+"\");");
			else buf.putLine2("	Path<Object> path = root.get(\""+fieldName+"\").get(\"id\");");
			buf.putLine2("	Predicate "+targetFieldName+"Predicate = path.in(ids);");
			buf.putLine2("	predicate.getExpressions().add("+targetFieldName+"Predicate);");
			buf.putLine2("}");
			
		} else if (targetEnumeration != null) {
			String enumerationName = ModelLayerHelper.getEnumerationNameUncapped(targetEnumeration);
			String enumerationClassName = ModelLayerHelper.getEnumerationClassName(targetEnumeration);
			String entityClassName = DataLayerHelper.getEntityClassName(element);
			String collectionName = inOp.getField() + "Set";
			
			if (FieldUtil.isCollection(targetField)) {
				buf.putLine2("if ("+collectionName+" != null && "+collectionName+".size() > 0) {");
				buf.putLine2("	Join<"+enumerationClassName+", "+entityClassName+"> joins = root.join(\""+fieldName+"\");"); 
				buf.putLine2("	Predicate "+enumerationName+"Predicate = joins.in("+collectionName+");");
				buf.putLine2("	predicate.getExpressions().add("+enumerationName+"Predicate);");
				buf.putLine2("}");
				
			} else {
				buf.putLine2("if ("+collectionName+" != null && "+collectionName+".size() > 0) {");
				buf.putLine2("	Path<Object> path = root.get(\""+fieldName+"\");");
				buf.putLine2("	Predicate "+enumerationName+"Predicate = path.in("+collectionName+");");
				buf.putLine2("	predicate.getExpressions().add("+enumerationName+"Predicate);");
				buf.putLine2("}");
			}
		}
		
		return buf.get();
	}
	
	public String getSourceCode_ExecuteCriteriaQuery(Element element, Query query) {
		String entityClassName = "T"; //DataLayerHelper.getEntityClassName(element);

		Buf buf = new Buf();
		buf.putLine2("TypedQuery<"+entityClassName+"> typedQuery = entityManager.createQuery(criteriaQuery);"); 
		if (query.getDistinct()) {
			buf.putLine2(entityClassName+"T result = typedQuery.getSingleResult();");
			buf.putLine2("return result;");	
		} else {
			buf.putLine2("List<"+entityClassName+"> results = new ArrayList<"+entityClassName+">();"); 
			buf.putLine2("results.addAll(typedQuery.getResultList());");
			buf.putLine2("return results;");	
		}
		return buf.get();
	}

}
