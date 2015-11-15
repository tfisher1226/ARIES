package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Attribute;
import nam.model.Element;
import nam.model.Fault;
import nam.model.Field;
import nam.model.Grouping;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class FaultUtil extends BaseUtil {
	
	public static Object getKey(Fault fault) {
		return fault.getName() + " " + fault.getCode();
	}

	public static String getLabel(Fault fault) {
		return fault.getName();
	}

	public static String getLabel(Collection<Fault> faultList) {
		return null;
	}
	
	public static boolean isEmpty(Fault fault) {
		if (fault == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Fault> faultList) {
		if (faultList == null  || faultList.size() == 0)
			return true;
		Iterator<Fault> iterator = faultList.iterator();
		while (iterator.hasNext()) {
			Fault fault = iterator.next();
			if (!isEmpty(fault))
				return false;
		}
		return true;
	}
	
	public static String toString(Fault fault) {
		if (isEmpty(fault))
			return "";
		String text = fault.toString();
		return text;
	}
	
	public static String toString(Collection<Fault> faultList) {
		if (isEmpty(faultList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Fault> iterator = faultList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Fault fault = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(fault);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Fault create() {
		Fault fault = new Fault();
		initialize(fault);
		return fault;
	}
	
	public static void initialize(Fault fault) {
		//nothing for now
	}
	
	public static boolean validate(Fault fault) {
		if (fault == null)
			return false;
		Validator validator = Validator.getValidator();
		FaultUtil.validate(fault.getCause());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Fault> faultList) {
		Validator validator = Validator.getValidator();
		Iterator<Fault> iterator = faultList.iterator();
		while (iterator.hasNext()) {
			Fault fault = iterator.next();
			//TODO break or accumulate?
			validate(fault);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Fault> faultList) {
		Collections.sort(faultList, createFaultComparator());
	}
	
	public static Collection<Fault> sortRecords(Collection<Fault> faultCollection) {
		List<Fault> list = new ArrayList<Fault>(faultCollection);
		Collections.sort(list, createFaultComparator());
		return list;
	}
	
	public static Comparator<Fault> createFaultComparator() {
		return new Comparator<Fault>() {
			public int compare(Fault fault1, Fault fault2) {
				Object key1 = getKey(fault1);
				Object key2 = getKey(fault2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Fault clone(Fault fault) {
		if (fault == null)
			return null;
		Fault clone = create();
		clone.setCode(ObjectUtil.clone(fault.getCode()));
		clone.setMessage(ObjectUtil.clone(fault.getMessage()));
		clone.setCause(FaultUtil.clone(fault.getCause()));
		return clone;
	}
	
	public static List<Fault> clone(List<Fault> faultList) {
		if (faultList == null)
			return null;
		List<Fault> newList = new ArrayList<Fault>();
		Iterator<Fault> iterator = faultList.iterator();
		while (iterator.hasNext()) {
			Fault fault = iterator.next();
			Fault clone = clone(fault);
			newList.add(clone);
		}
		return newList;
	}

	public static boolean equals(Fault fault1, Fault fault2) {
		Assert.notNull(fault1, "Fault1 must be specified");
		Assert.notNull(fault2, "Fault2 must be specified");
		Assert.notNull(fault1.getName(), "Fault1 name must be specified");
		Assert.notNull(fault2.getName(), "Fault2 name must be specified");
		Assert.notNull(fault1.getType(), "Fault1 type must be specified");
		Assert.notNull(fault2.getType(), "Fault2 type must be specified");
		if (!fault1.getName().equals(fault2.getName()))
			return false;
		if (!fault1.getType().equals(fault2.getType()))
			return false;
		return true;
	}

	public static boolean equals(List<Fault> faults1, List<Fault> faults2) {
		Assert.notNull(faults1, "Fault1 list must be specified");
		Assert.notNull(faults2, "Fault2 list must be specified");
		if (faults1.size() != faults2.size())
			return false;
		for (int i=0; i < faults1.size(); i++) {
			Fault fault1 = faults1.get(i);
			Fault fault2 = faults1.get(i);
			if (!equals(fault1, fault2))
				return false;
		}
		return true;
	}
	
	
	public static List<Attribute> getAttributes(Fault fault) {
		return ElementUtil.getFields2(fault, Attribute.class);
	}
	
	public static void addAttribute(Fault fault, Attribute attribute) {
		fault.addToAttributesAndReferencesAndGroups(attribute);
	}
	
	public static boolean removeAttribute(Fault fault, Attribute attribute) {
		return fault.removeFromAttributesAndReferencesAndGroups(attribute);
	}

}
