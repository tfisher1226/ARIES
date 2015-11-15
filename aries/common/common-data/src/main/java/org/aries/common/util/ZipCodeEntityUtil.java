package org.aries.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.common.Country;
import org.aries.common.entity.ZipCodeEntity;


public class ZipCodeEntityUtil {

	private static Log log = LogFactory.getLog(ZipCodeEntityUtil.class);
	
	public static ZipCodeEntity toZipCode(String text) {
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
			log.error("Unexpected value for ZipCode: "+text);
			return null;
		}
		if (text.length() > 5 && text.length() < 9) {
			log.error("Unexpected value for ZipCode: "+text);
			return null;
		}
		ZipCodeEntity zipCode = new ZipCodeEntity();
		//TODO externalize this:
		zipCode.setCountry(Country.USA);
		zipCode.setNumber(text.substring(0, 5));
		if (text.length() > 5)
			zipCode.setExtension(text.substring(5, 9));
		return zipCode;
	}

	public static String toString(ZipCodeEntity zipCode) {
		String number = zipCode.getNumber();
		if (number.length() != 5) {
			log.error("Unexpected value for ZipCode.number: "+number);
			return null;
		}
		String extension = zipCode.getExtension();
		if (extension != null && extension.length() != 4) {
			log.error("Unexpected value for ZipCode.extension: "+extension);
			return null;
		}
		StringBuffer buf = new StringBuffer();
		buf.append(number);
		if (!StringUtils.isEmpty(extension))
			buf.append("-"+extension);
		return buf.toString();
	}
	
}
