package org.aries.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.common.Country;
import org.aries.common.PhoneLocation;
import org.aries.common.entity.PhoneNumberEntity;


public class PhoneNumberEntityUtil {

	private static Log log = LogFactory.getLog(PhoneNumberEntityUtil.class);
	
	public static PhoneNumberEntity toPhoneNumber(String phoneNumberText, PhoneLocation phoneNumberType) {
		if (phoneNumberText == null || phoneNumberText.length() == 0)
			return null;
		phoneNumberText = phoneNumberText.replaceAll("\\(", "");
		phoneNumberText = phoneNumberText.replaceAll("\\)", "");
		phoneNumberText = phoneNumberText.replaceAll("-", "");
		phoneNumberText = phoneNumberText.replaceAll("x", "");
		phoneNumberText = phoneNumberText.replaceAll(" ", "");
		phoneNumberText = phoneNumberText.replaceAll("[a-z]", "");
		phoneNumberText = phoneNumberText.replaceAll("[A-Z]", "");
		if (phoneNumberText.length() < 10) {
			log.error("Unexpected value for PhoneNumber: "+phoneNumberText);
			return null;
		}
		PhoneNumberEntity phoneNumber = new PhoneNumberEntity();
		phoneNumber.setType(phoneNumberType);
		//TODO externalize this:
		phoneNumber.setCountry(Country.USA);
		if (phoneNumberText.length() >= 3)
			phoneNumber.setArea(phoneNumberText.substring(0, 3));
		if (phoneNumberText.length() >= 10)
			phoneNumber.setNumber(phoneNumberText.substring(3, 10));
		else if (phoneNumberText.length() >= 3)
			phoneNumber.setNumber(phoneNumberText.substring(3));
		if (phoneNumberText.length() > 10)
			phoneNumber.setExtension(phoneNumberText.substring(10));
		return phoneNumber;
	}

	public static String toString(PhoneNumberEntity phoneNumber) {
		StringBuffer buf = new StringBuffer();
		if (phoneNumber.getArea().length() != 3) {
			log.error("Unexpected value for PhoneNumber.area: "+phoneNumber.getArea());
			return null;
		}
		if (phoneNumber.getNumber().length() != 7) {
			log.error("Unexpected value for PhoneNumber.number: "+phoneNumber.getNumber());
			return null;
		}
		buf.append(phoneNumber.getArea()+" ");
		buf.append(phoneNumber.getNumber().substring(0, 3)+"-");
		buf.append(phoneNumber.getNumber().substring(3, 7));
		if (phoneNumber.getExtension() != null)
			buf.append(" "+phoneNumber.getExtension());
		return buf.toString();
	}
	
}
