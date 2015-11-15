package org.aries.util.collection;

import java.util.Map;


public class MapUtil {

	public static <K, V> void replaceElements(Map<K, V> destinationMap, Map<K, V> sourceMap) {
		if (destinationMap != null && sourceMap != null) {
			destinationMap.clear();
			destinationMap.putAll(sourceMap);
		}
	}
	
}
