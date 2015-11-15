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

import nam.ui.Layout;


public class LayoutUtil extends BaseUtil {
	
	public static Object getKey(Layout layout) {
		return layout.getName();
	}
	
	public static String getLabel(Layout layout) {
		return layout.getName();
	}
	
	public static boolean isEmpty(Layout layout) {
		if (layout == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(layout.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Layout> layoutList) {
		if (layoutList == null  || layoutList.size() == 0)
			return true;
		Iterator<Layout> iterator = layoutList.iterator();
		while (iterator.hasNext()) {
			Layout layout = iterator.next();
			if (!isEmpty(layout))
				return false;
		}
		return true;
	}
	
	public static String toString(Layout layout) {
		if (isEmpty(layout))
			return "Layout: [uninitialized] "+layout.toString();
		String text = layout.toString();
		return text;
	}
	
	public static String toString(Collection<Layout> layoutList) {
		if (isEmpty(layoutList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Layout> iterator = layoutList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Layout layout = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(layout);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Layout create() {
		Layout layout = new Layout();
		initialize(layout);
		return layout;
	}
	
	public static void initialize(Layout layout) {
		//nothing for now
	}
	
	public static boolean validate(Layout layout) {
		if (layout == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(layout.getName(), "\"Name\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Layout> layoutList) {
		Validator validator = Validator.getValidator();
		Iterator<Layout> iterator = layoutList.iterator();
		while (iterator.hasNext()) {
			Layout layout = iterator.next();
			//TODO break or accumulate?
			validate(layout);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Layout> layoutList) {
		Collections.sort(layoutList, createLayoutComparator());
	}
	
	public static Collection<Layout> sortRecords(Collection<Layout> layoutCollection) {
		List<Layout> list = new ArrayList<Layout>(layoutCollection);
		Collections.sort(list, createLayoutComparator());
		return list;
	}
	
	public static Comparator<Layout> createLayoutComparator() {
		return new Comparator<Layout>() {
			public int compare(Layout layout1, Layout layout2) {
				Object key1 = getKey(layout1);
				Object key2 = getKey(layout2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Layout clone(Layout layout) {
		if (layout == null)
			return null;
		Layout clone = create();
		clone.setName(ObjectUtil.clone(layout.getName()));
		return clone;
	}
	
	public static List<Layout> clone(List<Layout> layoutList) {
		if (layoutList == null)
			return null;
		List<Layout> newList = new ArrayList<Layout>();
		Iterator<Layout> iterator = layoutList.iterator();
		while (iterator.hasNext()) {
			Layout layout = iterator.next();
			Layout clone = clone(layout);
			newList.add(clone);
		}
		return newList;
	}
	
}
