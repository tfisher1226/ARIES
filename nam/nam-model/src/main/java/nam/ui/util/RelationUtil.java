package nam.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.ui.Relation;


public class RelationUtil extends BaseUtil {
	
	public static Object getKey(Relation relation) {
		return relation.getName();
	}
	
	public static String getLabel(Relation relation) {
		return relation.getName();
	}
	
	public static boolean isEmpty(Relation relation) {
		if (relation == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Relation> relationList) {
		if (relationList == null  || relationList.size() == 0)
			return true;
		Iterator<Relation> iterator = relationList.iterator();
		while (iterator.hasNext()) {
			Relation relation = iterator.next();
			if (!isEmpty(relation))
				return false;
		}
		return true;
	}
	
	public static String toString(Relation relation) {
		if (isEmpty(relation))
			return "Relation: [uninitialized] "+relation.toString();
		String text = relation.toString();
		return text;
	}
	
	public static String toString(Collection<Relation> relationList) {
		if (isEmpty(relationList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Relation> iterator = relationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Relation relation = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(relation);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Relation create() {
		Relation relation = new Relation();
		initialize(relation);
		return relation;
	}
	
	public static void initialize(Relation relation) {
		//nothing for now
	}
	
	public static boolean validate(Relation relation) {
		if (relation == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Relation> relationList) {
		Validator validator = Validator.getValidator();
		Iterator<Relation> iterator = relationList.iterator();
		while (iterator.hasNext()) {
			Relation relation = iterator.next();
			//TODO break or accumulate?
			validate(relation);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Relation> relationList) {
		Collections.sort(relationList, createRelationComparator());
	}
	
	public static Collection<Relation> sortRecords(Collection<Relation> relationCollection) {
		List<Relation> list = new ArrayList<Relation>(relationCollection);
		Collections.sort(list, createRelationComparator());
		return list;
	}
	
	public static Comparator<Relation> createRelationComparator() {
		return new Comparator<Relation>() {
			public int compare(Relation relation1, Relation relation2) {
				Object key1 = getKey(relation1);
				Object key2 = getKey(relation2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Relation clone(Relation relation) {
		if (relation == null)
			return null;
		Relation clone = create();
		clone.setName(ObjectUtil.clone(relation.getName()));
		clone.setPattern(ObjectUtil.clone(relation.getPattern()));
		clone.setContainer(ObjectUtil.clone(relation.getContainer(), String.class));
		clone.setType(ObjectUtil.clone(relation.getType(), String.class));
		return clone;
	}
	
	public static List<Relation> clone(List<Relation> relationList) {
		if (relationList == null)
			return null;
		List<Relation> newList = new ArrayList<Relation>();
		Iterator<Relation> iterator = relationList.iterator();
		while (iterator.hasNext()) {
			Relation relation = iterator.next();
			Relation clone = clone(relation);
			newList.add(clone);
		}
		return newList;
	}
	
}
