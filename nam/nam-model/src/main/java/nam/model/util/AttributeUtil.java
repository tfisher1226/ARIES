package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.Application;
import nam.model.Attribute;
import nam.model.Parameter;


public class AttributeUtil extends BaseUtil {
	
	public static Object getKey(Attribute attribute) {
		return attribute.getType() + ":" + attribute.getName();
	}
	
	public static String getLabel(Attribute attribute) {
		return NameUtil.capName(attribute.getName());
	}
	
	public static boolean isEmpty(Attribute attribute) {
		if (attribute == null)
			return true;
		boolean status = false;
		status |= assertObjectEmpty(attribute.getType());
		status |= assertObjectEmpty(attribute.getName());
		status |= assertObjectEmpty(attribute.getNamespace());
		status |= assertObjectEmpty(attribute.getStructure());
		status |= assertObjectEmpty(attribute.getRef());
		return status;
	}
	
	public static boolean isEmpty(Collection<Attribute> attributeList) {
		if (attributeList == null  || attributeList.size() == 0)
			return true;
		Iterator<Attribute> iterator = attributeList.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			if (!isEmpty(attribute))
				return false;
		}
		return true;
	}
	
	public static String toString(Attribute attribute) {
		if (isEmpty(attribute))
			return "";
		String text = attribute.getType();
		return text;
	}
	
	public static String toString(Collection<Attribute> attributeList) {
		if (isEmpty(attributeList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Attribute> iterator = attributeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Attribute attribute = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(attribute);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Attribute create() {
		Attribute attribute = new Attribute();
		initialize(attribute);
		return attribute;
	}
	
	public static void initialize(Attribute attribute) {
		//nothing for now
	}
	
	public static boolean validate(Attribute attribute) {
		if (attribute == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Attribute> attributeList) {
		Validator validator = Validator.getValidator();
		Iterator<Attribute> iterator = attributeList.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			//TODO break or accumulate?
			validate(attribute);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Attribute> attributeList) {
		Collections.sort(attributeList, createAttributeComparator());
	}
	
	public static Collection<Attribute> sortRecords(Collection<Attribute> attributeCollection) {
		List<Attribute> list = new ArrayList<Attribute>(attributeCollection);
		Collections.sort(list, createAttributeComparator());
		return list;
	}
	
	public static Comparator<Attribute> createAttributeComparator() {
		return new Comparator<Attribute>() {
			public int compare(Attribute attribute1, Attribute attribute2) {
				String name1 = attribute1.getName();
				String name2 = attribute2.getName();
				int status = name1.compareTo(name2);
				return status;
			}
		};
	}
	
	public static Attribute clone(Attribute attribute) {
		if (attribute == null)
			return null;
		Attribute clone = create();
		return clone;
	}

	public static List<Attribute> clone(List<Attribute> attributeList) {
		if (attributeList == null)
			return null;
		List<Attribute> newList = new ArrayList<Attribute>();
		Iterator<Attribute> iterator = attributeList.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			Attribute clone = clone(attribute);
			newList.add(clone);
		}
		return newList;
	}

}
