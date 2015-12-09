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

import nam.model.Component;
import nam.model.ComponentType;
import nam.model.Operation;


public class ComponentUtil extends BaseUtil {
	
	public static String getKey(Component component) {
		return component.getName();
	}
	
	public static String getLabel(Component component) {
		return component.getName();
	}
	
	public static boolean getLabel(Collection<Component> componentList) {
		if (componentList == null  || componentList.size() == 0)
			return true;
		Iterator<Component> iterator = componentList.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			if (!isEmpty(component))
				return false;
		}
		return true;
	}
	
	//TODO this needs combination with super class
	public static boolean isEmpty(Component component) {
		if (component == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Component> componentList) {
		if (componentList == null  || componentList.size() == 0)
			return true;
		Iterator<Component> iterator = componentList.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			if (!isEmpty(component))
				return false;
		}
		return true;
	}
	
	public static String toString(Component component) {
		if (isEmpty(component))
			return "Component: [uninitialized] "+component.toString();
		String text = component.toString();
		return text;
	}
	
	public static String toString(Collection<Component> componentList) {
		if (isEmpty(componentList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Component> iterator = componentList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Component component = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(component);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Component create() {
		Component component = new Component();
		initialize(component);
		return component;
	}
	
	public static void initialize(Component component) {
		if (component.getCached() == null)
			component.setCached(false);
		if (component.getPublished() == null)
			component.setPublished(false);
	}
	
	public static boolean validate(Component component) {
		if (component == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notNull(component.getComponentType(), "\"ComponentType\" must be specified");
		ComponentUtil.validate(component.getComponents());
		OperationUtil.validate(component.getOperations());
		TransactedUtil.validate(component.getTransacted());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Component> componentList) {
		Validator validator = Validator.getValidator();
		Iterator<Component> iterator = componentList.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			//TODO break or accumulate?
			validate(component);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Component> componentList) {
		Collections.sort(componentList, createComponentComparator());
	}
	
	public static Collection<Component> sortRecords(Collection<Component> componentCollection) {
		List<Component> list = new ArrayList<Component>(componentCollection);
		Collections.sort(list, createComponentComparator());
		return list;
	}
	
	public static Comparator<Component> createComponentComparator() {
		return new Comparator<Component>() {
			public int compare(Component component1, Component component2) {
				Object key1 = getKey(component1);
				Object key2 = getKey(component2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	//TODO this needs work
	public static Component clone(Component component) {
		if (component == null)
			return null;
		Component clone = create();
		clone.setName(ObjectUtil.clone(component.getName()));
		clone.setType(ObjectUtil.clone(component.getType()));
		clone.setComponentType(component.getComponentType());
		clone.setBaseType(ObjectUtil.clone(component.getBaseType()));
		clone.setVersion(ObjectUtil.clone(component.getVersion()));
		clone.setElement(ObjectUtil.clone(component.getElement()));
		clone.setPackageName(ObjectUtil.clone(component.getPackageName()));
		clone.setInterfaceName(ObjectUtil.clone(component.getInterfaceName()));
		clone.setClassName(ObjectUtil.clone(component.getClassName()));
		clone.setPublished(ObjectUtil.clone(component.getPublished()));
		clone.setCached(ObjectUtil.clone(component.getCached()));
		//clone.setAnnotations(ObjectUtil.clone(component.getAnnotations(), String.class));
		//clone.setFields(ObjectUtil.clone(component.getFields(), String.class));
		clone.setTransacted(TransactedUtil.clone(component.getTransacted()));
		clone.setComponents(ComponentUtil.clone(component.getComponents()));
		clone.setOperations(OperationUtil.clone(component.getOperations()));
		return clone;
	}
	
	public static List<Component> clone(List<Component> componentList) {
		if (componentList == null)
			return null;
		List<Component> newList = new ArrayList<Component>();
		Iterator<Component> iterator = componentList.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			Component clone = clone(component);
			newList.add(clone);
		}
		return newList;
	}

}
