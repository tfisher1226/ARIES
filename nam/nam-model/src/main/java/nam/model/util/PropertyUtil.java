package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Module;
import nam.model.Properties;
import nam.model.Property;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class PropertyUtil extends BaseUtil {
	
	public static Object getKey(Property property) {
		return property.getName();
	}
	
	public static String getLabel(Property property) {
		return property.getName();
	}
	
	public static boolean isEmpty(Property property) {
		if (property == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(property.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Property> propertyList) {
		if (propertyList == null  || propertyList.size() == 0)
			return true;
		Iterator<Property> iterator = propertyList.iterator();
		while (iterator.hasNext()) {
			Property property = iterator.next();
			if (!isEmpty(property))
				return false;
		}
		return true;
	}
	
	public static String toString(Property property) {
		if (isEmpty(property))
			return "Property: [uninitialized] "+property.toString();
		String text = property.toString();
		return text;
	}
	
	public static String toString(Collection<Property> propertyList) {
		if (isEmpty(propertyList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Property> iterator = propertyList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Property property = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(property);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Property create() {
		Property property = new Property();
		initialize(property);
		return property;
	}
	
	public static void initialize(Property property) {
		//nothing for now
	}
	
	public static boolean validate(Property property) {
		if (property == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(property.getName(), "\"Name\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Property> propertyList) {
		Validator validator = Validator.getValidator();
		Iterator<Property> iterator = propertyList.iterator();
		while (iterator.hasNext()) {
			Property property = iterator.next();
			//TODO break or accumulate?
			validate(property);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Property> propertyList) {
		Collections.sort(propertyList, createPropertyComparator());
	}
	
	public static Collection<Property> sortRecords(Collection<Property> propertyCollection) {
		List<Property> list = new ArrayList<Property>(propertyCollection);
		Collections.sort(list, createPropertyComparator());
		return list;
	}
	
	public static Comparator<Property> createPropertyComparator() {
		return new Comparator<Property>() {
			public int compare(Property property1, Property property2) {
				Object key1 = getKey(property1);
				Object key2 = getKey(property2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Property clone(Property property) {
		if (property == null)
			return null;
		Property clone = create();
		clone.setName(ObjectUtil.clone(property.getName()));
		clone.setValue(ObjectUtil.clone(property.getValue()));
		return clone;
	}
	
	public static List<Property> clone(List<Property> propertyList) {
		if (propertyList == null)
			return null;
		List<Property> newList = new ArrayList<Property>();
		Iterator<Property> iterator = propertyList.iterator();
		while (iterator.hasNext()) {
			Property property = iterator.next();
			Property clone = clone(property);
			newList.add(clone);
		}
		return newList;
	}



	public static boolean isEnabled(Properties properties, String name) {
		Object value = getValue(properties, name);
		return value != null && value.equals("true"); 
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Properties properties, String name) {
		Property property = getProperty(properties, name);
		if (property != null && property.getName().equals(name)) {
			return (T) property.getValue(); 
		}
		return null;
	}
	
	public static Property getProperty(Properties properties, String name) {
		return getProperty(properties.getProperties(), name);
	}
	
	public static Property getProperty(List<Property> properties, String name) {
		Iterator<Property> iterator = properties.iterator();
		while (iterator.hasNext()) {
			Property property = iterator.next();
			if (property.getName().equals(name)) {
				return property; 
			}
		}
		return null;
	}

	public static void setProperty(Properties properties, String name) {
		setProperty(properties, name, true);
	}

	public static void unsetProperty(Properties properties, String name) {
		setProperty(properties, name, false);
	}

	public static void setProperty(Properties properties, String name, Object value) {
		setProperty(properties.getProperties(), name, value);
	}

	public static void setProperty(List<Property> properties, String name) {
		setProperty(properties, name, true);
	}
	
	public static void unsetProperty(List<Property> properties, String name) {
		setProperty(properties, name, false);
	}
	
	public static void setProperty(List<Property> properties, String name, Object value) {
		Property property = getProperty(properties, name);
		if (property == null) {
			property = new Property();
			property.setName(name);
			property.setValue(value.toString());
			properties.add(property);
		} else {
			if (value == null)
				property.setValue(null);
			else property.setValue(value.toString());
		}
	}
	
	public static void applyDefault(List<Property> properties, String name, boolean value) {
		if (getProperty(properties, name) == null) {
			setProperty(properties, name, value);
		}
	}
	
	public static void applyDefault(Properties properties, String name, boolean value) {
		if (getProperty(properties, name) == null) {
			setProperty(properties, name, value);
		}
	}

	public static void mergeProperties(Properties properties1, Properties properties2) {
		mergeProperties(properties1.getProperties(), properties2.getProperties());
	}
	
	public static void mergeProperties(List<Property> properties1, List<Property> properties2) {
		Iterator<Property> iterator2 = properties2.iterator();
		while (iterator2.hasNext()) {
			Property property2 = iterator2.next();
			if (!properties1.contains(property2)) {
				properties1.add(property2);
			}
		}
	}

}
