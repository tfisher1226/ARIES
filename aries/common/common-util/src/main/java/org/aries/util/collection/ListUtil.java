package org.aries.util.collection;

import java.util.Collection;


public class ListUtil {

	public static <T> void replaceElements(Collection<T> destinationList, Collection<T> sourceList) {
		if (destinationList != null && sourceList != null) {
			destinationList.clear();
			destinationList.addAll(sourceList);
		}
	}
	
}
