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

import nam.ui.Controls;


public class ControlsUtil extends BaseUtil {

	public static boolean isEmpty(Controls controls) {
		if (controls == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Controls> controlsList) {
		if (controlsList == null  || controlsList.size() == 0)
			return true;
		Iterator<Controls> iterator = controlsList.iterator();
		while (iterator.hasNext()) {
			Controls controls = iterator.next();
			if (!isEmpty(controls))
				return false;
		}
		return true;
	}
	
	public static String toString(Controls controls) {
		if (isEmpty(controls))
			return "Controls: [uninitialized] "+controls.toString();
		String text = controls.toString();
		return text;
	}
	
	public static String toString(Collection<Controls> controlsList) {
		if (isEmpty(controlsList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Controls> iterator = controlsList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Controls controls = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(controls);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Controls create() {
		Controls controls = new Controls();
		initialize(controls);
		return controls;
	}
	
	public static void initialize(Controls controls) {
		//nothing for now
	}
	
	public static boolean validate(Controls controls) {
		if (controls == null)
			return false;
		Validator validator = Validator.getValidator();
		TableUtil.validate(controls.getTables());
		TreeUtil.validate(controls.getTrees());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Controls> controlsList) {
		Validator validator = Validator.getValidator();
		Iterator<Controls> iterator = controlsList.iterator();
		while (iterator.hasNext()) {
			Controls controls = iterator.next();
			//TODO break or accumulate?
			validate(controls);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}

	public static Controls clone(Controls controls) {
		if (controls == null)
			return null;
		Controls clone = create();
		clone.setTables(TableUtil.clone(controls.getTables()));
		clone.setTrees(TreeUtil.clone(controls.getTrees()));
		return clone;
	}
	
}
