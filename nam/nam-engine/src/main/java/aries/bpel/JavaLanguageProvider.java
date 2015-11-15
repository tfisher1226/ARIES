package aries.bpel;

import java.util.StringTokenizer;

import org.aries.util.NameUtil;


public class JavaLanguageProvider {

	
	public static String generateGetter(String fieldSet) {
		StringBuffer buf = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(fieldSet, ".");
		for (int i=0; tokenizer.hasMoreTokens(); i++) {
			String token = tokenizer.nextToken();
			if (i > 0)
				buf.append(".");
			String s = makeGetter(token);
			buf.append(s);
		}
		return buf.toString();
	}

	protected static String makeGetter(String field) {
		String getter = "get"+NameUtil.capName(field)+"()";
		return getter;
	}

//	public static String generateGetter(String fieldName) {
//		return "get"+NameUtil.capName(fieldName)+"()";
//	}

	public static String generateSetter(String fieldName) {
		return "set"+NameUtil.capName(fieldName)+"(XXPlaceHolderXX)";
	}
	
}
