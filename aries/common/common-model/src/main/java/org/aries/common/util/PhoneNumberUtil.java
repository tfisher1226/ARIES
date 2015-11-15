package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.Country;
import org.aries.common.PhoneLocation;
import org.aries.common.PhoneNumber;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class PhoneNumberUtil extends BaseUtil {

	public static boolean isEmpty(PhoneNumber phoneNumber) {
		if (phoneNumber == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(phoneNumber.getArea());
		status |= StringUtils.isEmpty(phoneNumber.getNumber());
		status |= phoneNumber.getCountry() != Country.USA;
		status |= phoneNumber.getType() != PhoneLocation.HOME;
		return status;
	}
	
	public static boolean isEmpty(Collection<PhoneNumber> phoneNumberList) {
		if (phoneNumberList == null  || phoneNumberList.size() == 0)
			return true;
		Iterator<PhoneNumber> iterator = phoneNumberList.iterator();
		while (iterator.hasNext()) {
			PhoneNumber phoneNumber = iterator.next();
			if (!isEmpty(phoneNumber))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Map<PhoneLocation, PhoneNumber> phoneNumbers) {
		if (phoneNumbers == null)
			return true;
		return isEmpty((Collection<PhoneNumber>) phoneNumbers.values());
	}
	
	public static String toString(PhoneNumber phoneNumber) {
		if (phoneNumber == null)
			return "";
		if (isEmpty(phoneNumber))
			return "";
		//String text = phoneNumber.toString();
		String area = phoneNumber.getArea();
		String number = phoneNumber.getNumber();
		String text = "(" + area + ") " + number.substring(0, 3) + "-" + number.substring(3);
		return text;
	}
	
	public static String toString(PhoneLocation phoneLocation) {
		if (phoneLocation != null) {
			String name = phoneLocation.name().toLowerCase();
			name = NameUtil.capName(name) + ":";
			return name;
		}
		return "";
	}
	
	public static String toString(Collection<PhoneNumber> phoneNumberList) {
		if (isEmpty(phoneNumberList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<PhoneNumber> iterator = phoneNumberList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PhoneNumber phoneNumber = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(phoneNumber);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static PhoneNumber create() {
		PhoneNumber phoneNumber = new PhoneNumber();
		initialize(phoneNumber);
		return phoneNumber;
	}
	
	public static PhoneNumber create(PhoneLocation phoneLocation) {
		PhoneNumber phoneNumber = create();
		phoneNumber.setType(phoneLocation);
		return phoneNumber;
	}	

	public static void initialize(PhoneNumber phoneNumber) {
		if (phoneNumber.getCountry() == null)
			phoneNumber.setCountry(Country.USA);
		if (phoneNumber.getType() == null)
			phoneNumber.setType(PhoneLocation.HOME);
	}
		
	public static boolean isValid(PhoneNumber phoneNumber) {
		if (phoneNumber == null)
			return true;
		boolean isValid = validate(phoneNumber);
		return isValid;
	}
	
	public static boolean validate(PhoneNumber phoneNumber) {
		if (phoneNumber == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(phoneNumber.getArea(), "\"Area\" must be specified");
		validator.notNull(phoneNumber.getCountry(), "\"Country\" must be specified");
		validator.notEmpty(phoneNumber.getNumber(), "\"Number\" must be specified");
		validator.notNull(phoneNumber.getType(), "\"Type\" must be specified");
		validator.notNull(phoneNumber.getArea().length() != 3, "\"Area\" must have length 3: "+phoneNumber.getArea());
		validator.notNull(phoneNumber.getNumber().length() != 7, "\"Number\" must have length 7: "+phoneNumber.getNumber());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean isValid(String phoneNumber) {
		if (phoneNumber == null)
			return false;
		Pattern pattern = Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}
	
	public static boolean validate(Collection<PhoneNumber> phoneNumberList) {
		Validator validator = Validator.getValidator();
		Iterator<PhoneNumber> iterator = phoneNumberList.iterator();
		while (iterator.hasNext()) {
			PhoneNumber phoneNumber = iterator.next();
			//TODO break or accumulate?
			validate(phoneNumber);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}

	public static void sortRecords(List<PhoneNumber> phoneNumberList) {
		Collections.sort(phoneNumberList, createPhoneNumberComparator());
	}

	public static Collection<PhoneNumber> sortRecords(Collection<PhoneNumber> phoneNumberCollection) {
		List<PhoneNumber> list = new ArrayList<PhoneNumber>(phoneNumberCollection);
		Collections.sort(list, createPhoneNumberComparator());
		return list;
	}

	public static Comparator<PhoneNumber> createPhoneNumberComparator() {
		return new Comparator<PhoneNumber>() {
			public int compare(PhoneNumber phoneNumber1, PhoneNumber phoneNumber2) {
				int status = phoneNumber1.compareTo(phoneNumber2);
				return status;
			}
		};
	}
	
	public static PhoneNumber clone(PhoneNumber phoneNumber) {
		if (phoneNumber == null)
			return null;
		PhoneNumber clone = create();
		clone.setId(ObjectUtil.clone(phoneNumber.getId()));
		clone.setArea(ObjectUtil.clone(phoneNumber.getArea()));
		clone.setNumber(ObjectUtil.clone(phoneNumber.getNumber()));
		clone.setExtension(ObjectUtil.clone(phoneNumber.getExtension()));
		clone.setCountry(phoneNumber.getCountry());
		clone.setType(phoneNumber.getType());
		clone.setValue(ObjectUtil.clone(phoneNumber.getValue()));
		return clone;
	}

	public static Map<PhoneLocation, PhoneNumber> clone(Map<PhoneLocation, PhoneNumber> phoneNumberMap) {
		Map<PhoneLocation, PhoneNumber> newMap = new HashMap<PhoneLocation, PhoneNumber>();
		Set<PhoneLocation> keySet = phoneNumberMap.keySet();
		Iterator<PhoneLocation> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			PhoneLocation key = iterator.next();
			PhoneNumber clone = phoneNumberMap.get(key);
			newMap.put(key, clone);
		}
		return newMap;
	}





	public static PhoneNumber getHomePhone(Map<PhoneLocation, PhoneNumber> phoneNumbers) {
		return phoneNumbers.get(PhoneLocation.HOME);
	}
	
		
	public static String toPhoneNumberString(PhoneNumber phoneNumber) {
		if (phoneNumber == null)
			return null;
		StringBuffer buf = new StringBuffer();
		if (phoneNumber.getArea() != null)
			buf.append("("+phoneNumber.getArea()+") ");
		if (phoneNumber.getNumber() != null) {
	 		buf.append(phoneNumber.getNumber().substring(0, 3)+"-");
			buf.append(phoneNumber.getNumber().substring(3, 7));
			if (phoneNumber.getExtension() != null)
				buf.append(" "+phoneNumber.getExtension());
		}
		return buf.toString();
	}
	
	public static PhoneNumber toPhoneNumber(String text) {
		if (text == null || text.length() == 0)
			return null;
		text = text.replaceAll("\\(", "");
		text = text.replaceAll("\\)", "");
		text = text.replaceAll("-", "");
		text = text.replaceAll("x", "");
		text = text.replaceAll(" ", "");
		text = text.replaceAll("[a-z]", "");
		text = text.replaceAll("[A-Z]", "");
		if (text.length() < 10) {
			throw new RuntimeException("Unexpected value for PhoneNumber: "+text);
		}
		PhoneNumber phoneNumber = new PhoneNumber();
		//TODO externalize this:
		phoneNumber.setCountry(Country.USA);
		if (text.length() >= 3)
			phoneNumber.setArea(text.substring(0, 3));
		if (text.length() >= 10)
			phoneNumber.setNumber(text.substring(3, 10));
		else if (text.length() >= 3)
			phoneNumber.setNumber(text.substring(3));
		if (text.length() > 10)
			phoneNumber.setExtension(text.substring(10));
		return phoneNumber;
	}

}
