package org.aries.util.text;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CurrencyUtil {

	public static Double parseCurrency(String currency) throws Exception {
		BigDecimal bigDecimal = new BigDecimal(currencyToBigDecimalFormat(currency));
		double doubleValue = bigDecimal.doubleValue();
		return doubleValue;
	}
	
	public static String currencyToBigDecimalFormat(String currency) throws Exception {

		if (!doesMatch(currency, "^[+-]?[0-9]{1,3}(?:[0-9]*(?:[.,][0-9]{0,2})?|(?:,[0-9]{3})*(?:\\.[0-9]{0,2})?|(?:\\.[0-9]{3})*(?:,[0-9]{0,2})?)$"))
			throw new Exception("Currency in wrong format " + currency);

		// Replace all dots with commas
		currency = currency.replaceAll("\\.", ",");

		// If fractions exist, the separator must be a .
		if (currency.length()>=3) {
			char[] chars = currency.toCharArray();
			if (chars[chars.length-2] == ',') {
				chars[chars.length-2] = '.';
			} else if (chars[chars.length-3] == ',') {
				chars[chars.length-3] = '.';
			}
			currency = new String(chars);
		}

		// Remove all commas            
		return currency.replaceAll(",", "");                            
	}

	protected static boolean doesMatch(String token, String regex) {
		try {
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(token);
			return matcher.matches();
		} catch (RuntimeException e) {
			return false;
		}
	}

}
