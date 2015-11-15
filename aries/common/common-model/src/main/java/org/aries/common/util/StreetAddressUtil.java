package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.Country;
import org.aries.common.PhoneNumber;
import org.aries.common.State;
import org.aries.common.StreetAddress;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class StreetAddressUtil extends BaseUtil {

	public static boolean isEmpty(StreetAddress streetAddress) {
		if (streetAddress == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(streetAddress.getStreet());
		status |= StringUtils.isEmpty(streetAddress.getCity());
		status |= streetAddress.getState() != State.CA;
		status |= ZipCodeUtil.isEmpty(streetAddress.getZipCode());
		status |= streetAddress.getCountry() != Country.USA;
		return status;
	}
	
	public static boolean isEmpty(Collection<StreetAddress> streetAddressList) {
		if (streetAddressList == null  || streetAddressList.size() == 0)
			return true;
		Iterator<StreetAddress> iterator = streetAddressList.iterator();
		while (iterator.hasNext()) {
			StreetAddress streetAddress = iterator.next();
			if (!isEmpty(streetAddress))
				return false;
		}
		return true;
	}
	
	public static String toString(StreetAddress streetAddress) {
		if (streetAddress == null)
			return "[uninitialized]";
		if (isEmpty(streetAddress))
			return "[uninitialized]: "+streetAddress.toString();
		String text = streetAddress.toString();
		return text;
	}

	public static String toStringLine1(StreetAddress streetAddress) {
		if (streetAddress == null)
			return " ";
		String street = streetAddress.getStreet();
		return street;
	}
	
	public static String toStringLine2(StreetAddress streetAddress) {
		if (streetAddress == null)
			return " ";
		StringBuffer buf = new StringBuffer();
		String city = streetAddress.getCity();
		State state = streetAddress.getState();
		String zip = ZipCodeUtil.toZipCodeString(streetAddress.getZipCode());
		
		if (!StringUtils.isEmpty(city) && state != null) {
			buf.append(city+", "+state);
		} else if (!StringUtils.isEmpty(city) && state == null) {
			buf.append(city);
		} else if (StringUtils.isEmpty(city) && state != null) {
			buf.append(state);
		}
		if (!StringUtils.isEmpty(zip)) {
			if (buf.length() > 0)
				buf.append(" ");
			buf.append(zip);
		}
		return buf.toString();
	}
	
	public static String toString(Collection<StreetAddress> streetAddressList) {
		if (isEmpty(streetAddressList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<StreetAddress> iterator = streetAddressList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			StreetAddress streetAddress = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(streetAddress);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}

	public static StreetAddress create() {
		StreetAddress streetAddress = new StreetAddress();
		initialize(streetAddress);
		return streetAddress;
	}
	
	public static void initialize(StreetAddress streetAddress) {
		if (streetAddress.getCountry() == null)
			streetAddress.setCountry(Country.USA);
		if (streetAddress.getState() == null)
			streetAddress.setState(State.CA);
	}
	
	public static boolean isValid(StreetAddress streetAddress) {
		if (streetAddress == null)
			return true;
		boolean isValid = validate(streetAddress);
		return isValid;
	}
	
	public static boolean validate(StreetAddress streetAddress) {
		if (streetAddress == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(streetAddress.getCity(), "\"City\" must be specified");
		validator.notNull(streetAddress.getCountry(), "\"Country\" must be specified");
		validator.notNull(streetAddress.getState(), "\"State\" must be specified");
		validator.notEmpty(streetAddress.getStreet(), "\"Street\" must be specified");
		boolean zipCodeIsEmpty = ZipCodeUtil.isEmpty(streetAddress.getZipCode());
		validator.isFalse(zipCodeIsEmpty, "\"ZipCode\" must be specified");
		if (!zipCodeIsEmpty)
			ZipCodeUtil.validate(streetAddress.getZipCode());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<StreetAddress> streetAddressList) {
		Validator validator = Validator.getValidator();
		Iterator<StreetAddress> iterator = streetAddressList.iterator();
		while (iterator.hasNext()) {
			StreetAddress streetAddress = iterator.next();
			//TODO break or accumulate?
			validate(streetAddress);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<StreetAddress> streetAddressList) {
		Collections.sort(streetAddressList, createStreetAddressComparator());
	}
	
	public static Collection<StreetAddress> sortRecords(Collection<StreetAddress> streetAddressCollection) {
		List<StreetAddress> list = new ArrayList<StreetAddress>(streetAddressCollection);
		Collections.sort(list, createStreetAddressComparator());
		return list;
	}
	
	public static Comparator<StreetAddress> createStreetAddressComparator() {
		return new Comparator<StreetAddress>() {
			public int compare(StreetAddress streetAddress1, StreetAddress streetAddress2) {
				int status = streetAddress1.compareTo(streetAddress2);
				return status;
			}
		};
	}
	
	public static StreetAddress clone(StreetAddress streetAddress) {
		if (streetAddress == null)
			return null;
		StreetAddress clone = create();
		clone.setId(ObjectUtil.clone(streetAddress.getId()));
		clone.setStreet(ObjectUtil.clone(streetAddress.getStreet()));
		clone.setCity(ObjectUtil.clone(streetAddress.getCity()));
		clone.setState(streetAddress.getState());
		clone.setZipCode(ZipCodeUtil.clone(streetAddress.getZipCode()));
		clone.setCountry(streetAddress.getCountry());
		clone.setLatitude(ObjectUtil.clone(streetAddress.getLatitude()));
		clone.setLongitude(ObjectUtil.clone(streetAddress.getLongitude()));
		return clone;
	}
	
}
