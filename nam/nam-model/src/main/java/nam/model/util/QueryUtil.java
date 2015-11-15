package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Condition;
import nam.model.Criteria;
import nam.model.In;
import nam.model.Like;
import nam.model.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class QueryUtil extends BaseUtil {
	
	public static Object getKey(Query query) {
		return query.getName();
	}
	
	public static String getLabel(Query query) {
		return query.getName();
	}
	
	public static boolean isEmpty(Query query) {
		if (query == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Query> queryList) {
		if (queryList == null  || queryList.size() == 0)
			return true;
		Iterator<Query> iterator = queryList.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			if (!isEmpty(query))
				return false;
		}
		return true;
	}
	
	public static String toString(Query query) {
		if (isEmpty(query))
			return "Query: [uninitialized] "+query.toString();
		String text = query.toString();
		return text;
	}
	
	public static String toString(Collection<Query> queryList) {
		if (isEmpty(queryList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Query> iterator = queryList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Query query = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(query);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Query create() {
		Query query = new Query();
		initialize(query);
		return query;
	}
	
	public static void initialize(Query query) {
		if (query.getDistinct() == null)
			query.setDistinct(false);
	}
	
	public static boolean validate(Query query) {
		if (query == null)
			return false;
		Validator validator = Validator.getValidator();
		//SerializableUtil.validate(query.getConditionsAndCriterias());
		//OrderByUtil.validate(query.getOrderBy());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Query> queryList) {
		Validator validator = Validator.getValidator();
		Iterator<Query> iterator = queryList.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			//TODO break or accumulate?
			validate(query);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Query> queryList) {
		Collections.sort(queryList, createQueryComparator());
	}
	
	public static Collection<Query> sortRecords(Collection<Query> queryCollection) {
		List<Query> list = new ArrayList<Query>(queryCollection);
		Collections.sort(list, createQueryComparator());
		return list;
	}
	
	public static Comparator<Query> createQueryComparator() {
		return new Comparator<Query>() {
			public int compare(Query query1, Query query2) {
				Object key1 = getKey(query1);
				Object key2 = getKey(query2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Query clone(Query query) {
		if (query == null)
			return null;
		Query clone = create();
		clone.setName(ObjectUtil.clone(query.getName()));
		clone.setFrom(ObjectUtil.clone(query.getFrom()));
		//clone.setOrderBy(OrderByUtil.clone(query.getOrderBy()));
		//clone.setConditionsAndCriterias(SerializableUtil.clone(query.getConditionsAndCriterias()));
		clone.setDistinct(ObjectUtil.clone(query.getDistinct()));
		return clone;
	}
	
	public static List<Query> clone(List<Query> queryList) {
		if (queryList == null)
			return null;
		List<Query> newList = new ArrayList<Query>();
		Iterator<Query> iterator = queryList.iterator();
		while (iterator.hasNext()) {
			Query query = iterator.next();
			Query clone = clone(query);
			newList.add(clone);
		}
		return newList;
	}


	public static List<Condition> getConditions(Query query) {
		return getObjectList(query, Condition.class);
	}

	public static Condition getCondition(Query query) {
		return getObject(query, Condition.class);
	}

	public static Criteria getCriteria(Query query) {
		return getObject(query, Criteria.class);
	}

	public static void addCriteria(Query query, Criteria criteria) {
		query.getConditionsAndCriterias().add(criteria);
	}

	public static <T> List<T> getObjectList(Query query, Class<?> objectClass) {
		List<Serializable> objects = query.getConditionsAndCriterias();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Query query, Class<?> objectClass) {
		List<Serializable> objects = query.getConditionsAndCriterias();
		return ListUtil.getObject(objects, objectClass);
	}
	
	public static List<String> getParameterNames(Query query) {
		Condition condition = QueryUtil.getCondition(query);
		Criteria criteria = QueryUtil.getCriteria(query);
		if (condition != null) {
			return getParameterNames(query, condition.getInsAndLikes());
		} else if (criteria != null) {
			return getParameterNames(query, criteria.getInsAndLikes());
		}
		
		return null;
	}

	public static List<String> getParameterNames(Query query, List<Serializable> insAndLikes) {
		List<String> parameterNames = new ArrayList<String>();
		Iterator<Serializable> iterator = insAndLikes.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Like) {
				Like likeObject = (Like) object;
				String fieldName = likeObject.getField();
				parameterNames.add(fieldName);
				
			} else if (object instanceof In) {
				In inObject = (In) object;
				String fieldName = inObject.getField();
				parameterNames.add(fieldName+"List");
			}
		}
		return parameterNames;
	}
	
}
