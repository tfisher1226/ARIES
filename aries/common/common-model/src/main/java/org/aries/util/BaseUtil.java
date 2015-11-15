package org.aries.util;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.aries.Assert;


public class BaseUtil {

	public static boolean assertObjectEmpty(Object value) {
		return value != null;
	}

	public static boolean assertObjectEmpty(String value) {
		return value != null && value.isEmpty();
	}

	public static boolean assertObjectEmpty(int value) {
		return value != 0;
	}

	public static boolean assertObjectEmpty(Integer value) {
		return value != null && value != 0;
	}

	public static boolean assertObjectEmpty(long value) {
		return value != 0;
	}

	public static boolean assertObjectEmpty(Long value) {
		return value != null && value != 0;
	}

	public static boolean assertObjectEmpty(short value) {
		return value != 0;
	}

	public static boolean assertObjectEmpty(Short value) {
		return value != null && value != 0;
	}

	public static boolean assertObjectEmpty(double value) {
		return value != 0;
	}

	public static boolean assertObjectEmpty(Double value) {
		return value != null && value != 0;
	}

	public static void assertObjectExists(String name, Object value) {
		Assert.notNull(value, name+" must be specified");
	}

	public static void assertObjectCorrect(String name, Boolean value, Boolean target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static void assertObjectCorrect(String name, String value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, String value, long index) {
		assertObjectCorrect(name, value, "dummy"+name+index);
	}

	public static void assertObjectCorrect(String name, String value, String target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static void assertObjectCorrect(String name, Integer value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Integer value, long index) {
		assertObjectCorrect(name, value, 1 + (int) index);
	}

	public static void assertObjectCorrect(String name, Integer value, Integer target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static void assertObjectCorrect(String name, Short value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Short value, long index) {
		assertObjectCorrect(name, value, 1 + (short) index);
	}

	public static void assertObjectCorrect(String name, Short value, Short target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static void assertObjectCorrect(String name, Long value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Long value, long index) {
		assertObjectCorrect(name, value, 1L + (long) index);
	}

	public static void assertObjectCorrect(String name, Long value, Long target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static void assertObjectCorrect(String name, Double value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Double value, long index) {
		assertObjectCorrect(name, value, 1D + (double) index);
	}

	public static void assertObjectCorrect(String name, Double value, Double target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static void assertObjectCorrect(String name, Float value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Float value, long index) {
		assertObjectCorrect(name, value, 1F + (double) index);
	}

	public static void assertObjectCorrect(String name, Float value, Float target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static void assertObjectCorrect(String name, Date value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Date value, long index) {
		assertObjectCorrect(name, value, new Date(1000L + index));
	}

	public static void assertObjectCorrect(String name, Date value, Date target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static void assertObjectCorrect(String name, byte[] value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, byte[] value, long index) {
		assertObjectCorrect(name, value, "dummy" + name + index);
	}

	public static void assertObjectCorrect(String name, byte[] value, byte[] target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static <T> void assertObjectCorrect(String name, T value, T target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct");
	}

	public static <T> void assertObjectCorrect(String name, Collection<T> list, long index) {
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			T value = iterator.next();
			assertObjectCorrect(name, value, index);
		}
	}

	public static <K, T> void assertObjectCorrect(String name, Map<K, T> map, long index) {
		Iterator<K> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			K key = iterator.next();
			T value = map.get(key);
			assertObjectCorrect(name, key, index);
			assertObjectCorrect(name, value, index);
		}
	}

	public static void assertObjectEquals(String name, Object value1, Object value2, String message) {
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		Assert.equals(value1, value2, message+": "+nameCapped+" fields not equal: "+nameUncapped+"1="+value1+", "+nameUncapped+"2="+value2);
	}

}
