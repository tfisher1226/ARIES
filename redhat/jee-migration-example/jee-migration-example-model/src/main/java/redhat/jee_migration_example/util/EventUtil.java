package redhat.jee_migration_example.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import redhat.jee_migration_example.Event;


public class EventUtil extends BaseUtil {
	
	public static boolean isEmpty(Event event) {
		if (event == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(event.getMessage());
		return status;
	}
	
	public static boolean isEmpty(Collection<Event> eventList) {
		if (eventList == null  || eventList.size() == 0)
			return true;
		Iterator<Event> iterator = eventList.iterator();
		while (iterator.hasNext()) {
			Event event = iterator.next();
			if (!isEmpty(event))
				return false;
		}
		return true;
	}
	
	public static String toString(Event event) {
		if (isEmpty(event))
			return "Event: [uninitialized] "+event.toString();
		String text = event.toString();
		return text;
	}
	
	public static String toString(Collection<Event> eventList) {
		if (isEmpty(eventList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Event> iterator = eventList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Event event = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(event);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Event create() {
		Event event = new Event();
		initialize(event);
		return event;
	}
	
	public static void initialize(Event event) {
		//nothing for now
	}
	
	public static boolean validate(Event event) {
		if (event == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notNull(event.getDate(), "\"Date\" must be specified");
		validator.notEmpty(event.getMessage(), "\"Message\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Event> eventList) {
		Validator validator = Validator.getValidator();
		Iterator<Event> iterator = eventList.iterator();
		while (iterator.hasNext()) {
			Event event = iterator.next();
			//TODO break or accumulate?
			validate(event);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Event> eventList) {
		Collections.sort(eventList, createEventComparator());
	}
	
	public static Collection<Event> sortRecords(Collection<Event> eventCollection) {
		List<Event> list = new ArrayList<Event>(eventCollection);
		Collections.sort(list, createEventComparator());
		return list;
	}
	
	public static Comparator<Event> createEventComparator() {
		return new Comparator<Event>() {
			public int compare(Event event1, Event event2) {
				int status = event1.compareTo(event2);
				return status;
			}
		};
	}
	
	public static Event clone(Event event) {
		if (event == null)
			return null;
		Event clone = create();
		clone.setId(ObjectUtil.clone(event.getId()));
		clone.setDate(ObjectUtil.clone(event.getDate()));
		clone.setMessage(ObjectUtil.clone(event.getMessage()));
		return clone;
	}
	
}
