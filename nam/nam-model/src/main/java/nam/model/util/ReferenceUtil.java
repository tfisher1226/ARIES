package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Attribute;
import nam.model.Project;
import nam.model.Reference;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ReferenceUtil extends BaseUtil {
	
	public static Object getKey(Reference reference) {
		return reference.getType() + ":" + reference.getName();
	}
	
	public static String getLabel(Reference reference) {
		return reference.getName();
	}
	
	public static boolean isEmpty(Reference reference) {
		if (reference == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Reference> referenceList) {
		if (referenceList == null  || referenceList.size() == 0)
			return true;
		Iterator<Reference> iterator = referenceList.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			if (!isEmpty(reference))
				return false;
		}
		return true;
	}
	
	public static String toString(Reference reference) {
		if (isEmpty(reference))
			return "Reference: [uninitialized] "+reference.toString();
		String text = reference.toString();
		return text;
	}
	
	public static String toString(Collection<Reference> referenceList) {
		if (isEmpty(referenceList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Reference> iterator = referenceList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Reference reference = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(reference);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Reference create() {
		Reference reference = new Reference();
		initialize(reference);
		return reference;
	}
	
	public static void initialize(Reference reference) {
		//nothing for now
	}
	
	public static boolean validate(Reference reference) {
		if (reference == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Reference> referenceList) {
		Validator validator = Validator.getValidator();
		Iterator<Reference> iterator = referenceList.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			//TODO break or accumulate?
			validate(reference);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Reference> referenceList) {
		Collections.sort(referenceList, createReferenceComparator());
	}
	
	public static Collection<Reference> sortRecords(Collection<Reference> referenceCollection) {
		List<Reference> list = new ArrayList<Reference>(referenceCollection);
		Collections.sort(list, createReferenceComparator());
		return list;
	}
	
	public static Comparator<Reference> createReferenceComparator() {
		return new Comparator<Reference>() {
			public int compare(Reference reference1, Reference reference2) {
				String name1 = reference1.getName();
				String name2 = reference2.getName();
				int status = name1.compareTo(name2);
				return status;
			}
		};
	}
	
	public static Reference clone(Reference reference) {
		if (reference == null)
			return null;
		Reference clone = create();
		return clone;
	}
	
	public static List<Reference> clone(List<Reference> referenceList) {
		if (referenceList == null)
			return null;
		List<Reference> newList = new ArrayList<Reference>();
		Iterator<Reference> iterator = referenceList.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			Reference clone = clone(reference);
			newList.add(clone);
		}
		return newList;
	}
	
}
