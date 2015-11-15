package nam.model.util;

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

import nam.model.Modules;


public class ModulesUtil extends BaseUtil {
	
	public static boolean isEmpty(Modules modules) {
		if (modules == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Modules> modulesList) {
		if (modulesList == null  || modulesList.size() == 0)
			return true;
		Iterator<Modules> iterator = modulesList.iterator();
		while (iterator.hasNext()) {
			Modules modules = iterator.next();
			if (!isEmpty(modules))
				return false;
		}
		return true;
	}
	
	public static String toString(Modules modules) {
		if (isEmpty(modules))
			return "Modules: [uninitialized] "+modules.toString();
		String text = modules.toString();
		return text;
	}
	
	public static String toString(Collection<Modules> modulesList) {
		if (isEmpty(modulesList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Modules> iterator = modulesList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Modules modules = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(modules);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Modules create() {
		Modules modules = new Modules();
		initialize(modules);
		return modules;
	}
	
	public static void initialize(Modules modules) {
		//nothing for now
	}
	
	public static boolean validate(Modules modules) {
		if (modules == null)
			return false;
		Validator validator = Validator.getValidator();
		ModuleUtil.validate(modules);
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Modules> modulesList) {
		Validator validator = Validator.getValidator();
		Iterator<Modules> iterator = modulesList.iterator();
		while (iterator.hasNext()) {
			Modules modules = iterator.next();
			//TODO break or accumulate?
			validate(modules);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static Modules clone(Modules modules) {
		if (modules == null)
			return null;
		Modules clone = ModuleUtil.clone(modules);
		return clone;
	}
	
}
