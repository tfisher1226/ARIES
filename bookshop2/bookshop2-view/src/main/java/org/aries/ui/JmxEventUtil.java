package org.aries.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class JmxEventUtil extends BaseUtil {
	
	public static boolean isEmpty(JmxEvent jmxEvent) {
		if (jmxEvent == null)
			return true;
		boolean status = false;
		status |= jmxEvent.getTimeStamp() == null;
		status |= jmxEvent.getSequenceNumber() == null;
		status |= StringUtils.isEmpty(jmxEvent.getMessage());
		return status;
	}
	
	public static boolean isEmpty(Collection<JmxEvent> jmxEventList) {
		if (jmxEventList == null  || jmxEventList.size() == 0)
			return true;
		Iterator<JmxEvent> iterator = jmxEventList.iterator();
		while (iterator.hasNext()) {
			JmxEvent jmxEvent = iterator.next();
			if (!isEmpty(jmxEvent))
				return false;
		}
		return true;
	}
	
	public static String toString(JmxEvent jmxEvent) {
		if (isEmpty(jmxEvent))
			return "JmxEvent: [uninitialized] "+jmxEvent.toString();
		String text = jmxEvent.toString();
		return text;
	}
	
	public static String toString(Collection<JmxEvent> jmxEventList) {
		if (isEmpty(jmxEventList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<JmxEvent> iterator = jmxEventList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			JmxEvent jmxEvent = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(jmxEvent);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static JmxEvent create() {
		JmxEvent jmxEvent = new JmxEvent();
		initialize(jmxEvent);
		return jmxEvent;
	}
	
	public static void initialize(JmxEvent jmxEvent) {
		//nothing for now
	}
	
	public static boolean validate(JmxEvent jmxEvent) {
		if (jmxEvent == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notNull(jmxEvent.getTimeStamp(), "\"Author\" must be specified");
		validator.notNull(jmxEvent.getSequenceNumber(), "\"SequenceNumber\" must be specified");
		validator.notEmpty(jmxEvent.getCorrelationId(), "\"CorrelationId\" must be specified");
		validator.notEmpty(jmxEvent.getEventId(), "\"EventId\" must be specified");
		validator.notEmpty(jmxEvent.getSourceId(), "\"SourceId\" must be specified");
		validator.notEmpty(jmxEvent.getMessage(), "\"Message\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<JmxEvent> jmxEventList) {
		Validator validator = Validator.getValidator();
		Iterator<JmxEvent> iterator = jmxEventList.iterator();
		while (iterator.hasNext()) {
			JmxEvent jmxEvent = iterator.next();
			//TODO break or accumulate?
			validate(jmxEvent);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<JmxEvent> jmxEventList) {
		Collections.sort(jmxEventList, createJmxEventComparator());
	}
	
	public static Collection<JmxEvent> sortRecords(Collection<JmxEvent> jmxEventCollection) {
		List<JmxEvent> list = new ArrayList<JmxEvent>(jmxEventCollection);
		Collections.sort(list, createJmxEventComparator());
		return list;
	}

	public static void sortRecordsByBarCode(List<JmxEvent> jmxEventList) {
		Collections.sort(jmxEventList, new Comparator<JmxEvent>() {
			public int compare(JmxEvent jmxEvent1, JmxEvent jmxEvent2) {
				Long sequenceNumber1 = jmxEvent1.getSequenceNumber();
				Long sequenceNumber2 = jmxEvent2.getSequenceNumber();
				if (sequenceNumber1 == null && sequenceNumber2 == null)
					return 0;
				if (sequenceNumber1 == null)
					return -1;
				if (sequenceNumber2 == null)
					return 1;
				return sequenceNumber1.compareTo(sequenceNumber2);
			}
		});
	}
	
	public static Comparator<JmxEvent> createJmxEventComparator() {
		return new Comparator<JmxEvent>() {
			public int compare(JmxEvent jmxEvent1, JmxEvent jmxEvent2) {
				int status = jmxEvent1.compareTo(jmxEvent2);
				return status;
			}
		};
	}
	
	public static JmxEvent clone(JmxEvent jmxEvent) {
		if (jmxEvent == null)
			return null;
		JmxEvent clone = create();
		clone.setSequenceNumber(ObjectUtil.clone(jmxEvent.getSequenceNumber()));
		clone.setTimeStamp(ObjectUtil.clone(jmxEvent.getTimeStamp()));
		clone.setCorrelationId(ObjectUtil.clone(jmxEvent.getCorrelationId()));
		clone.setEventId(ObjectUtil.clone(jmxEvent.getEventId()));
		clone.setSourceId(ObjectUtil.clone(jmxEvent.getSourceId()));
		clone.setMessage(ObjectUtil.clone(jmxEvent.getMessage()));
		clone.setData(ObjectUtil.clone(jmxEvent.getData()));
		return clone;
	}
	
	public static List<JmxEvent> clone(List<JmxEvent> jmxEventList) {
		if (jmxEventList == null)
			return null;
		List<JmxEvent> newList = new ArrayList<JmxEvent>();
		Iterator<JmxEvent> iterator = jmxEventList.iterator();
		while (iterator.hasNext()) {
			JmxEvent jmxEvent = iterator.next();
			JmxEvent clone = clone(jmxEvent);
			newList.add(clone);
		}
		return newList;
	}
	
	public static Set<JmxEvent> clone(Set<JmxEvent> jmxEventSet) {
		if (jmxEventSet == null)
			return null;
		Set<JmxEvent> newSet = new HashSet<JmxEvent>();
		Iterator<JmxEvent> iterator = jmxEventSet.iterator();
		while (iterator.hasNext()) {
			JmxEvent jmxEvent = iterator.next();
			JmxEvent clone = clone(jmxEvent);
			newSet.add(clone);
		}
		return newSet;
	}
	
}
