package nam.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.ui.Section;


public class SectionUtil extends BaseUtil {
	
	public static Object getKey(Section section) {
		return section.getName();
	}
	
	public static String getLabel(Section section) {
		return section.getName();
	}
	
	public static boolean isEmpty(Section section) {
		if (section == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(section.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Section> sectionList) {
		if (sectionList == null  || sectionList.size() == 0)
			return true;
		Iterator<Section> iterator = sectionList.iterator();
		while (iterator.hasNext()) {
			Section section = iterator.next();
			if (!isEmpty(section))
				return false;
		}
		return true;
	}
	
	public static String toString(Section section) {
		if (isEmpty(section))
			return "Section: [uninitialized] "+section.toString();
		String text = section.toString();
		return text;
	}
	
	public static String toString(Collection<Section> sectionList) {
		if (isEmpty(sectionList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Section> iterator = sectionList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Section section = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(section);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Section create() {
		Section section = new Section();
		initialize(section);
		return section;
	}
	
	public static void initialize(Section section) {
		//nothing for now
	}
	
	public static boolean validate(Section section) {
		if (section == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(section.getName(), "\"Name\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Section> sectionList) {
		Validator validator = Validator.getValidator();
		Iterator<Section> iterator = sectionList.iterator();
		while (iterator.hasNext()) {
			Section section = iterator.next();
			//TODO break or accumulate?
			validate(section);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Section> sectionList) {
		Collections.sort(sectionList, createSectionComparator());
	}
	
	public static Collection<Section> sortRecords(Collection<Section> sectionCollection) {
		List<Section> list = new ArrayList<Section>(sectionCollection);
		Collections.sort(list, createSectionComparator());
		return list;
	}
	
	public static Comparator<Section> createSectionComparator() {
		return new Comparator<Section>() {
			public int compare(Section section1, Section section2) {
				Object key1 = getKey(section1);
				Object key2 = getKey(section2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Section clone(Section section) {
		if (section == null)
			return null;
		Section clone = create();
		clone.setName(ObjectUtil.clone(section.getName()));
		return clone;
	}
	
	public static List<Section> clone(List<Section> sectionList) {
		if (sectionList == null)
			return null;
		List<Section> newList = new ArrayList<Section>();
		Iterator<Section> iterator = sectionList.iterator();
		while (iterator.hasNext()) {
			Section section = iterator.next();
			Section clone = clone(section);
			newList.add(clone);
		}
		return newList;
	}
	
}
