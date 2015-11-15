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
import org.aries.common.ZipCode;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ZipCodeUtil extends BaseUtil {

	public static boolean isEmpty(ZipCode zipCode) {
		if (zipCode == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(zipCode.getNumber());
		status |= zipCode.getCountry() != Country.USA;
		return status;
	}
	
	public static boolean isEmpty(Collection<ZipCode> zipCodeList) {
		if (zipCodeList == null  || zipCodeList.size() == 0)
			return true;
		Iterator<ZipCode> iterator = zipCodeList.iterator();
		while (iterator.hasNext()) {
			ZipCode zipCode = iterator.next();
			if (!isEmpty(zipCode))
				return false;
		}
		return true;
	}
	
	public static String toString(ZipCode zipCode) {
		if (zipCode == null)
			return "ZipCode: [uninitialized]";
		if (isEmpty(zipCode))
			return "ZipCode: [uninitialized]: "+zipCode.toString();
		String text = zipCode.toString();
		return text;
	}
	
	public static String toString(Collection<ZipCode> zipCodeList) {
		if (isEmpty(zipCodeList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<ZipCode> iterator = zipCodeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ZipCode zipCode = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(zipCode);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static ZipCode create() {
		ZipCode zipCode = new ZipCode();
		initialize(zipCode);
		return zipCode;
	}
	
	public static void initialize(ZipCode zipCode) {
		if (zipCode.getCountry() == null)
			zipCode.setCountry(Country.USA);
	}
	
	public static boolean validate(ZipCode zipCode) {
		if (zipCode == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notNull(zipCode.getCountry(), "\"Country\" must be specified");
		validator.notEmpty(zipCode.getNumber(), "\"Number\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<ZipCode> zipCodeList) {
		Validator validator = Validator.getValidator();
		Iterator<ZipCode> iterator = zipCodeList.iterator();
		while (iterator.hasNext()) {
			ZipCode zipCode = iterator.next();
			//TODO break or accumulate?
			validate(zipCode);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<ZipCode> zipCodeList) {
		Collections.sort(zipCodeList, createZipCodeComparator());
	}
	
	public static Collection<ZipCode> sortRecords(Collection<ZipCode> zipCodeCollection) {
		List<ZipCode> list = new ArrayList<ZipCode>(zipCodeCollection);
		Collections.sort(list, createZipCodeComparator());
		return list;
	}
	
	public static Comparator<ZipCode> createZipCodeComparator() {
		return new Comparator<ZipCode>() {
			public int compare(ZipCode zipCode1, ZipCode zipCode2) {
				int status = zipCode1.compareTo(zipCode2);
				return status;
			}
		};
	}
	
	public static ZipCode clone(ZipCode zipCode) {
		if (zipCode == null)
			return null;
		ZipCode clone = create();
		clone.setId(ObjectUtil.clone(zipCode.getId()));
		clone.setNumber(ObjectUtil.clone(zipCode.getNumber()));
		clone.setExtension(ObjectUtil.clone(zipCode.getExtension()));
		clone.setCountry(zipCode.getCountry());
		return clone;
	}
	
	
	public static String toZipCodeString(ZipCode zipCode) {
		if (zipCode == null)
			return null;
		String number = zipCode.getNumber();
		String extension = zipCode.getExtension();
		StringBuffer buf = new StringBuffer();
		buf.append(number);
		if (!StringUtils.isEmpty(extension))
			buf.append("-"+extension);
		return buf.toString();
	}

	public static ZipCode toZipCode(String text) {
		if (text == null || text.length() == 0)
			return null;
		text = text.replaceAll("\\(", "");
		text = text.replaceAll("\\)", "");
		text = text.replaceAll("-", "");
		text = text.replaceAll("x", "");
		text = text.replaceAll(" ", "");
		text = text.replaceAll("[a-z]", "");
		text = text.replaceAll("[A-Z]", "");
		if (text.length() < 5) {
			throw new RuntimeException("Unexpected value for ZipCode: "+text);
		}
		if (text.length() > 5 && text.length() < 9) {
			throw new RuntimeException("Unexpected value for ZipCode: "+text);
		}
		ZipCode zipCode = new ZipCode();
		//TODO externalize this:
		zipCode.setCountry(Country.USA);
		zipCode.setNumber(text.substring(0, 5));
		if (text.length() > 5)
			zipCode.setExtension(text.substring(5, 9));
		return zipCode;
	}

}
