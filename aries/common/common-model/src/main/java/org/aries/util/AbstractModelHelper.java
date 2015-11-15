package org.aries.util;

import java.util.Collection;
import java.util.StringTokenizer;


public class AbstractModelHelper {

	/**
	 * Validates a matching value.
	 * If expected is non-existent, then default to "true".
	 */
	public static <T> boolean isMatch(T expected, T actual) {
		if (expected == null)
			return true;
		return expected.equals(actual);
	}

	/**
	 * Validates existence of a matching value.
	 * If expected is non-existent, then default to "true".
	 */
	public static <T> boolean isExists(Collection<T> expected, T actual) {
		if (expected == null)
			return true;
		if (expected.isEmpty())
			return true;
		return expected.contains(actual);
	}

	public static String getValueFromKey(String key, int index) {
		StringTokenizer tokenizer = new StringTokenizer(":");
		for (int i=0; tokenizer.hasMoreTokens(); i++) {
			String token = tokenizer.nextToken();
			if (index == 1)
				return token;
		}
		return null;
	}

}
