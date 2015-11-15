package org.aries.util;

import java.util.Collection;


public class CollectionUtil {

	public static boolean isEmpty(Collection<String> messages) {
		return messages == null || messages.size() == 0;
	}

	public static <T> void add(Collection<T> collection, T object) {
		collection.remove(object);
		collection.add(object);
	}

}
