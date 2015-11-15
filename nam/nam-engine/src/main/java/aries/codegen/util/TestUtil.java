package aries.codegen.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.aries.Assert;
import org.aries.util.NameUtil;


public class TestUtil {

	public static String getRandomValue(String className) {
		if (className.equals("Integer"))
			return Integer.toString(getRandomInt());
		if (className.equals("Long"))
			return Long.toString(getRandomLong());
		if (className.equals("Double"))
			return Double.toString(getRandomDouble());
		if (className.equals("Float"))
			return Float.toString(getRandomFloat());
		if (className.equals("Short"))
			return Short.toString(getRandomShort());
		if (className.equals("String"))
			return "\"randomString_"+getRandomString()+"\"";
		//throw new RuntimeException("Unrecognized class: "+className);
		return null;
	}

	public static int getRandomInt() {
		return RandomUtils.nextInt();
	}

	public static short getRandomShort() {
		return (short) RandomUtils.nextInt();
	}

	public static long getRandomLong() {
		return RandomUtils.nextLong();
	}

	public static double getRandomDouble() {
		return RandomUtils.nextDouble();
	}

	public static float getRandomFloat() {
		return RandomUtils.nextFloat();
	}

	public static String getRandomString() {
		return RandomStringUtils.randomAlphabetic(1);
	}
	
	public static void assertFieldCorrect(String name, String value, long index) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals("dummy"+name+index), name+" not correct");
	}

	public static void assertFieldCorrect(String name, Double value, long index) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(1D + index), name+" not correct");
	}

	public static void assertFieldCorrect(String name, Integer value, long index) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(1 + index), name+" not correct");
	}

	public static void assertFieldEquals(String name, Object value1, Object value2, String message) {
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		Assert.equals(value1, value2, message+": "+nameCapped+" fields not equal: "+nameUncapped+"1="+value1+", "+nameUncapped+"2="+value2);
	}

}
