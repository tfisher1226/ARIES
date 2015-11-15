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

import nam.ui.Control;


public class ControlUtil extends BaseUtil {
	
	public static Object getKey(Control control) {
		return control.getType();
	}
	
	public static String getLabel(Control control) {
		return control.getName();
	}
	
	public static boolean isEmpty(Control control) {
		if (control == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Control> controlList) {
		if (controlList == null  || controlList.size() == 0)
			return true;
		Iterator<Control> iterator = controlList.iterator();
		while (iterator.hasNext()) {
			Control control = iterator.next();
			if (!isEmpty(control))
				return false;
		}
		return true;
	}
	
	public static String toString(Control control) {
		if (isEmpty(control))
			return "Control: [uninitialized] "+control.toString();
		String text = control.toString();
		return text;
	}
	
	public static String toString(Collection<Control> controlList) {
		if (isEmpty(controlList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Control> iterator = controlList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Control control = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(control);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Control create() {
		Control control = new Control();
		initialize(control);
		return control;
	}
	
	public static void initialize(Control control) {
		//nothing for now
	}
	
	public static boolean validate(Control control) {
		if (control == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Control> controlList) {
		Validator validator = Validator.getValidator();
		Iterator<Control> iterator = controlList.iterator();
		while (iterator.hasNext()) {
			Control control = iterator.next();
			//TODO break or accumulate?
			validate(control);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Control> controlList) {
		Collections.sort(controlList, createControlComparator());
	}
	
	public static Collection<Control> sortRecords(Collection<Control> controlCollection) {
		List<Control> list = new ArrayList<Control>(controlCollection);
		Collections.sort(list, createControlComparator());
		return list;
	}
	
	public static Comparator<Control> createControlComparator() {
		return new Comparator<Control>() {
			public int compare(Control control1, Control control2) {
				Object key1 = getKey(control1);
				Object key2 = getKey(control2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Control clone(Control control) {
		if (control == null)
			return null;
		Control clone = create();
		return clone;
	}
	
}
