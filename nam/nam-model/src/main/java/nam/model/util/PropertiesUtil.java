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

import nam.model.Properties;


public class PropertiesUtil extends BaseUtil {
	
	public static boolean getLabel(Collection<Properties> propertiesList) {
		if (propertiesList == null  || propertiesList.size() == 0)
			return true;
		Iterator<Properties> iterator = propertiesList.iterator();
		while (iterator.hasNext()) {
			Properties properties = iterator.next();
			if (!isEmpty(properties))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Properties properties) {
		if (properties == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Properties> propertiesList) {
		if (propertiesList == null  || propertiesList.size() == 0)
			return true;
		Iterator<Properties> iterator = propertiesList.iterator();
		while (iterator.hasNext()) {
			Properties properties = iterator.next();
			if (!isEmpty(properties))
				return false;
		}
		return true;
	}
	
	public static String toString(Properties properties) {
		if (isEmpty(properties))
			return "Properties: [uninitialized] "+properties.toString();
		String text = properties.toString();
		return text;
	}
	
	public static String toString(Collection<Properties> propertiesList) {
		if (isEmpty(propertiesList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Properties> iterator = propertiesList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Properties properties = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(properties);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Properties create() {
		Properties properties = new Properties();
		initialize(properties);
		return properties;
	}
	
	public static void initialize(Properties properties) {
		//nothing for now
	}
	
	public static boolean validate(Properties properties) {
		if (properties == null)
			return false;
		Validator validator = Validator.getValidator();
		PropertyUtil.validate(properties.getProperties());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Properties> propertiesList) {
		Validator validator = Validator.getValidator();
		Iterator<Properties> iterator = propertiesList.iterator();
		while (iterator.hasNext()) {
			Properties properties = iterator.next();
			//TODO break or accumulate?
			validate(properties);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static Properties clone(Properties properties) {
		if (properties == null)
			return null;
		Properties clone = create();
		clone.getProperties().addAll(PropertyUtil.clone(properties.getProperties()));
		return clone;
	}
	
}
