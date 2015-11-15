package org.aries.util;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.aries.Assert;


public class BaseFixture {

	public static void main(String[] args) {
		long seed = 4;
		long index = 5;
		Long id = createId(seed, index);
		Long seedFromId = getSeedFromId(id);
		Long indexFromId = getIndexFromId(id);
		Assert.equals(seed, seedFromId, "Seed not correct");
		Assert.equals(index, indexFromId, "Seed not correct");
	}

	public static long createId(long index, long seed) {
		long id = createValue(index, seed);
		return id;
	}

	public static long createValue(long index, long seed) {
		long value = ((seed * 100L) + index);
		return value;
	}

	public static long getSeedFromId(long id) {
		return id % 100L;
	}
	
	public static long getIndexFromId(long id) {
		return id / 100L;
	}
	
	public static long getUniqueValueFromId(long id) {
		return getIndexFromId(id) + getSeedFromId(id);
	}
	
	public static void assertObjectExists(String name, Object value) {
		Assert.notNull(value, name+" must be specified");
	}

	public static void assertObjectCorrect(String name, Boolean value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Boolean value, Boolean target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static void assertObjectCorrect(String name, String value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, String value, long target) {
		assertObjectCorrect(name, value, "dummy" + name + target);
	}

	public static void assertObjectCorrect(String name, String value, String target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static void assertObjectCorrect(String name, Integer value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Integer value, long target) {
		assertObjectCorrect(name, value, new Integer((int) target));
	}

	public static void assertObjectCorrect(String name, Integer value, Integer target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static void assertObjectCorrect(String name, Short value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Short value, long target) {
		assertObjectCorrect(name, value, new Short((short) target));
	}

	public static void assertObjectCorrect(String name, Short value, Short target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static void assertObjectCorrect(String name, Long value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Long value, long value2) {
		assertObjectCorrect(name, value, new Long(value2));
	}

	public static void assertObjectCorrect(String name, Long value, Long target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static void assertObjectCorrect(String name, Double value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Double value, long target) {
		assertObjectCorrect(name, value, new Double((double) target));
	}

	public static void assertObjectCorrect(String name, Double value, Double target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static void assertObjectCorrect(String name, Float value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Float value, long target) {
		assertObjectCorrect(name, value, new Float((float) target));
	}

	public static void assertObjectCorrect(String name, Float value, Float target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static void assertObjectCorrect(String name, Date value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, Date value, long target) {
		assertObjectCorrect(name, value, new Date(1000L + target));
	}

	public static void assertObjectCorrect(String name, Date value, Date target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static void assertObjectCorrect(String name, byte[] value) {
		assertObjectCorrect(name, value, 0);
	}
	
	public static void assertObjectCorrect(String name, byte[] value, long target) {
		assertObjectCorrect(name, value, "dummy" + name + target);
	}

	public static void assertObjectCorrect(String name, byte[] value, byte[] target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static <T> void assertObjectCorrect(String name, T value, T target) {
		Assert.notNull(value, name+" must be specified");
		Assert.isTrue(value.equals(target), name+" not correct: value="+value+", target="+target);
	}

	public static <T> void assertObjectCorrect(String name, Collection<T> list, long target) {
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			T value = iterator.next();
			assertObjectCorrect(name, value, target);
		}
	}

	public static <K, T> void assertObjectCorrect(String name, Map<K, T> map, long target) {
		Iterator<K> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			K key = iterator.next();
			T value = map.get(key);
			assertObjectCorrect(name, key, target);
			assertObjectCorrect(name, value, target);
		}
	}

	public static void assertObjectEquals(String name, Date value, Date value2, String message) {
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		Assert.equals(value, value2, message+": "+nameCapped+" fields not equal: "+nameUncapped+"1="+value+", "+nameUncapped+"2="+value2);
	}

	public static void assertObjectEquals(String name, Object value, Object value2, String message) {
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		Assert.equals(value, value2, message+": "+nameCapped+" fields not equal: "+nameUncapped+"1="+value+", "+nameUncapped+"2="+value2);
	}

}
