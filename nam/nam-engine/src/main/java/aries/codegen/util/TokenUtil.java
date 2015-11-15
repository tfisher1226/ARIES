package aries.codegen.util;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;


public class TokenUtil {

	public static String getSimpleName(Class<?> input) {
		return getSimpleName(input.getName());
	}

	public static String[] tokenize(String input) {
		return tokenize(input, ".");
	}
	
	public static String[] tokenize(String input, String delimiter) {
        StringTokenizer stringTokenizer = new StringTokenizer(input, delimiter);
        String[] output = new String[stringTokenizer.countTokens()];
        for (int i=0; stringTokenizer.hasMoreTokens(); i++)
        	output[i] = stringTokenizer.nextToken();
        return output;
	}

	public static String getSimpleName(String input) {
        StringTokenizer stringTokenizer = new StringTokenizer(input, ".");
		String output = "";
        while (stringTokenizer.hasMoreTokens())
        	output = stringTokenizer.nextToken();
        return output;
	}
	
	public static String capitalize(String text) {
		if (!StringUtils.isEmpty(text)) {
	        StringBuffer buf = new StringBuffer(text.length());
            buf.append(Character.toUpperCase(text.charAt(0)));
            buf.append(text.substring(1));
            return buf.toString();
		}
		return text;
	}
	
	public static String uncapitalize(String text) {
		if (!StringUtils.isEmpty(text)) {
	        StringBuffer buf = new StringBuffer(text.length());
            buf.append(Character.toLowerCase(text.charAt(0)));
            buf.append(text.substring(1));
            return buf.toString();
		}
		return text;
	}
	
}
