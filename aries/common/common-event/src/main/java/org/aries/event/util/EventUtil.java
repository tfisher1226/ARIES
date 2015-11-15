package org.aries.event.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.aries.common.AbstractEvent;


public class EventUtil {
	
	public static <M extends AbstractEvent> void sortRecords(List<M> eventList) {
		Collections.sort(eventList, createEventComparator());
	}
	
	public static <M extends AbstractEvent> Collection<M> sortRecords(Collection<M> eventCollection) {
		List<M> list = new ArrayList<M>(eventCollection);
		Collections.sort(list, createEventComparator());
		return list;
	}

	public static <M extends AbstractEvent> Comparator<M> createEventComparator() {
		return new Comparator<M>() {
			public int compare(M event1, M event2) {
				int status = event1.compareTo(event2);
				return status;
			}
		};
	}
	
}
