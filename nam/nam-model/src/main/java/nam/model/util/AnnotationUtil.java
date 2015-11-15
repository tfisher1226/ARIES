package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Annotation;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.Map;
import org.aries.common.MapEntry;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

public class AnnotationUtil extends BaseUtil {

	public static String getKey(Annotation annotation) {
		return annotation.getSource() + " " + annotation.getDetails(); 
	}
	
	public static String getLabel(Annotation annotation) {
		return annotation.getSource() + " " + annotation.getDetails(); 
	}
	
	public static boolean isEmpty(Annotation annotation) {
		if (annotation == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Annotation> annotationList) {
		if (annotationList == null  || annotationList.size() == 0)
			return true;
		Iterator<Annotation> iterator = annotationList.iterator();
		while (iterator.hasNext()) {
			Annotation annotation = iterator.next();
			if (!isEmpty(annotation))
				return false;
		}
		return true;
	}
	
	public static String toString(Annotation annotation) {
		if (isEmpty(annotation))
			return "Annotation: [uninitialized] "+annotation.toString();
		String text = annotation.toString();
		return text;
	}
	
	public static String toString(Collection<Annotation> annotationList) {
		if (isEmpty(annotationList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Annotation> iterator = annotationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Annotation annotation = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(annotation);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Annotation create() {
		Annotation annotation = new Annotation();
		initialize(annotation);
		return annotation;
	}
	
	public static void initialize(Annotation annotation) {
		//nothing for now
	}
	
	public static boolean validate(Annotation annotation) {
		if (annotation == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Annotation> annotationList) {
		Validator validator = Validator.getValidator();
		Iterator<Annotation> iterator = annotationList.iterator();
		while (iterator.hasNext()) {
			Annotation annotation = iterator.next();
			//TODO break or accumulate?
			validate(annotation);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Annotation> annotationList) {
		Collections.sort(annotationList, createAnnotationComparator());
	}
	
	public static Collection<Annotation> sortRecords(Collection<Annotation> annotationCollection) {
		List<Annotation> list = new ArrayList<Annotation>(annotationCollection);
		Collections.sort(list, createAnnotationComparator());
		return list;
	}
	
	public static Comparator<Annotation> createAnnotationComparator() {
		return new Comparator<Annotation>() {
			public int compare(Annotation annotation1, Annotation annotation2) {
				Object key1 = getKey(annotation1);
				Object key2 = getKey(annotation2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Annotation clone(Annotation annotation) {
		if (annotation == null)
			return null;
		Annotation clone = create();
		clone.setSource(ObjectUtil.clone(annotation.getSource()));
		//TODO clone.setDetails(ObjectUtil.clone(annotation.getDetails(), String.class, String.class));
		return clone;
	}
	
	public static List<Annotation> clone(List<Annotation> annotationList) {
		if (annotationList == null)
			return null;
		List<Annotation> newList = new ArrayList<Annotation>();
		Iterator<Annotation> iterator = annotationList.iterator();
		while (iterator.hasNext()) {
			Annotation annotation = iterator.next();
			Annotation clone = clone(annotation);
			newList.add(clone);
		}
		return newList;
	}


	public static Annotation cloneAnnotation(Annotation annotation) {
		Annotation newAnnotation = new Annotation();
		newAnnotation.setSource(annotation.getSource());
		Map newDetails = new Map();
		Map details = annotation.getDetails();
		List<MapEntry> entries = details.getEntries();
		List<MapEntry> newEntries = new ArrayList<MapEntry>();
		Iterator<MapEntry> iterator = entries.iterator();
		while (iterator.hasNext()) {
			MapEntry mapEntry = iterator.next();
			MapEntry newMapEntry = new MapEntry();
			newMapEntry.setKey(mapEntry.getKey());
			newMapEntry.setValue(mapEntry.getValue());
			newEntries.add(newMapEntry);
		}
		newDetails.setEntries(newEntries);
		newAnnotation.setDetails(newDetails);
		return newAnnotation;
	}

	public static List<Annotation> cloneAnnotations(List<Annotation> annotations) {
		List<Annotation> newAnnotations = new ArrayList<Annotation>();
		Iterator<Annotation> iterator = annotations.iterator();
		while (iterator.hasNext()) {
			Annotation annotation = iterator.next();
			Annotation newAnnotation = cloneAnnotation(annotation);
			newAnnotations.add(newAnnotation);
		}
		return newAnnotations;
	}

}
