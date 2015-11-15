package org.aries.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.PhoneLocation;
import org.aries.util.BaseUtil;


public class PhoneLocationUtil extends BaseUtil {
	
	public static final PhoneLocation[] VALUES_ARRAY = new PhoneLocation[] {
		PhoneLocation.HOME,
		PhoneLocation.WORK,
		PhoneLocation.CELL,
		PhoneLocation.FAX,
		PhoneLocation.OTHER
	};
	
	public static final List<PhoneLocation> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static PhoneLocation getPhoneLocation(int ordinal) {
		if (ordinal == PhoneLocation.HOME.ordinal())
			return PhoneLocation.HOME;
		if (ordinal == PhoneLocation.WORK.ordinal())
			return PhoneLocation.WORK;
		if (ordinal == PhoneLocation.CELL.ordinal())
			return PhoneLocation.CELL;
		if (ordinal == PhoneLocation.FAX.ordinal())
			return PhoneLocation.FAX;
		if (ordinal == PhoneLocation.OTHER.ordinal())
			return PhoneLocation.OTHER;
		return null;
	}
	
	public static String toString(PhoneLocation phoneLocation) {
		return phoneLocation.name();
	}
	
	public static String toString(Collection<PhoneLocation> phoneLocationList) {
		StringBuffer buf = new StringBuffer();
		Iterator<PhoneLocation> iterator = phoneLocationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PhoneLocation phoneLocation = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(phoneLocation);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<PhoneLocation> phoneLocationList) {
		Collections.sort(phoneLocationList, createPhoneLocationComparator());
	}
	
	public static Collection<PhoneLocation> sortRecords(Collection<PhoneLocation> phoneLocationCollection) {
		List<PhoneLocation> list = new ArrayList<PhoneLocation>(phoneLocationCollection);
		Collections.sort(list, createPhoneLocationComparator());
		return list;
	}
	
	public static Comparator<PhoneLocation> createPhoneLocationComparator() {
		return new Comparator<PhoneLocation>() {
			public int compare(PhoneLocation phoneLocation1, PhoneLocation phoneLocation2) {
				String text1 = phoneLocation1.value();
				String text2 = phoneLocation2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
