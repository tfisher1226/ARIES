package org.aries.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.Capability;
import org.aries.util.BaseUtil;


public class CapabilityUtil extends BaseUtil {
	
	public static final Capability[] VALUES_ARRAY = new Capability[] {
		Capability.ALL,
		Capability.NONE,
		Capability.READ,
		Capability.CREATE,
		Capability.UPDATE,
		Capability.DELETE,
		Capability.EXPORT,
		Capability.EMAIL,
		Capability.PRINT
	};
	
	public static final List<Capability> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static Capability getCapability(int ordinal) {
		if (ordinal == Capability.ALL.ordinal())
			return Capability.ALL;
		if (ordinal == Capability.NONE.ordinal())
			return Capability.NONE;
		if (ordinal == Capability.READ.ordinal())
			return Capability.READ;
		if (ordinal == Capability.CREATE.ordinal())
			return Capability.CREATE;
		if (ordinal == Capability.UPDATE.ordinal())
			return Capability.UPDATE;
		if (ordinal == Capability.DELETE.ordinal())
			return Capability.DELETE;
		if (ordinal == Capability.EXPORT.ordinal())
			return Capability.EXPORT;
		if (ordinal == Capability.EMAIL.ordinal())
			return Capability.EMAIL;
		if (ordinal == Capability.PRINT.ordinal())
			return Capability.PRINT;
		return null;
	}
	
	public static String toString(Capability capability) {
		return capability.name();
	}
	
	public static String toString(Collection<Capability> capabilityList) {
		StringBuffer buf = new StringBuffer();
		Iterator<Capability> iterator = capabilityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Capability capability = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(capability);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<Capability> capabilityList) {
		Collections.sort(capabilityList, createCapabilityComparator());
	}
	
	public static Collection<Capability> sortRecords(Collection<Capability> capabilityCollection) {
		List<Capability> list = new ArrayList<Capability>(capabilityCollection);
		Collections.sort(list, createCapabilityComparator());
		return list;
	}
	
	public static Comparator<Capability> createCapabilityComparator() {
		return new Comparator<Capability>() {
			public int compare(Capability capability1, Capability capability2) {
				String text1 = capability1.value();
				String text2 = capability2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
