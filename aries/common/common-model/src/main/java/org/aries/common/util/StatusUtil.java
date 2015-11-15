package org.aries.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.Status;
import org.aries.util.BaseUtil;


public class StatusUtil extends BaseUtil {
	
	public static final Status[] VALUES_ARRAY = new Status[] {
		Status.INFO,
		Status.PROMPT,
		Status.WARNING,
		Status.ERROR
	};
	
	public static final List<Status> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static Status getStatus(int ordinal) {
		if (ordinal == Status.INFO.ordinal())
			return Status.INFO;
		if (ordinal == Status.PROMPT.ordinal())
			return Status.PROMPT;
		if (ordinal == Status.WARNING.ordinal())
			return Status.WARNING;
		if (ordinal == Status.ERROR.ordinal())
			return Status.ERROR;
		return null;
	}
	
	public static String toString(Status status) {
		return status.name();
	}
	
	public static String toString(Collection<Status> statusList) {
		StringBuffer buf = new StringBuffer();
		Iterator<Status> iterator = statusList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Status status = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(status);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<Status> statusList) {
		Collections.sort(statusList, createStatusComparator());
	}
	
	public static Collection<Status> sortRecords(Collection<Status> statusCollection) {
		List<Status> list = new ArrayList<Status>(statusCollection);
		Collections.sort(list, createStatusComparator());
		return list;
	}
	
	public static Comparator<Status> createStatusComparator() {
		return new Comparator<Status>() {
			public int compare(Status status1, Status status2) {
				String text1 = status1.value();
				String text2 = status2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
