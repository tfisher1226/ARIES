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

import nam.model.Container;
import nam.model.ContainerType;


public class ContainerUtil extends BaseUtil {
	
	public static Object getKey(Container container) {
		return container.getName();
	}
	
	public static String getLabel(Container container) {
		return container.getName();
	}
	
	public static boolean isEmpty(Container container) {
		if (container == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Container> containerList) {
		if (containerList == null  || containerList.size() == 0)
			return true;
		Iterator<Container> iterator = containerList.iterator();
		while (iterator.hasNext()) {
			Container container = iterator.next();
			if (!isEmpty(container))
				return false;
		}
		return true;
	}
	
	public static String toString(Container container) {
		if (isEmpty(container))
			return "Container: [uninitialized] "+container.toString();
		String text = container.toString();
		return text;
	}
	
	public static String toString(Collection<Container> containerList) {
		if (isEmpty(containerList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Container> iterator = containerList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Container container = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(container);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Container create() {
		Container container = new Container();
		initialize(container);
		return container;
	}
	
	public static void initialize(Container container) {
		if (container.getType() == null)
			container.setType(ContainerType.PLAIN);
	}
	
	public static boolean validate(Container container) {
		if (container == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Container> containerList) {
		Validator validator = Validator.getValidator();
		Iterator<Container> iterator = containerList.iterator();
		while (iterator.hasNext()) {
			Container container = iterator.next();
			//TODO break or accumulate?
			validate(container);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Container> containerList) {
		Collections.sort(containerList, createContainerComparator());
	}
	
	public static Collection<Container> sortRecords(Collection<Container> containerCollection) {
		List<Container> list = new ArrayList<Container>(containerCollection);
		Collections.sort(list, createContainerComparator());
		return list;
	}
	
	public static Comparator<Container> createContainerComparator() {
		return new Comparator<Container>() {
			public int compare(Container container1, Container container2) {
				Object key1 = getKey(container1);
				Object key2 = getKey(container2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Container clone(Container container) {
		if (container == null)
			return null;
		Container clone = create();
		clone.setType(container.getType());
		clone.setName(ObjectUtil.clone(container.getName()));
		return clone;
	}
	
	public static List<Container> clone(List<Container> containerList) {
		if (containerList == null)
			return null;
		List<Container> newList = new ArrayList<Container>();
		Iterator<Container> iterator = containerList.iterator();
		while (iterator.hasNext()) {
			Container container = iterator.next();
			Container clone = clone(container);
			newList.add(clone);
		}
		return newList;
	}
	
}
