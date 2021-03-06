package admin.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;

import admin.Action;


public class ActionUtil extends BaseUtil {
	
	public static final Action[] VALUES_ARRAY = new Action[] {
		Action.ALL,
		Action.NONE,
		Action.READ,
		Action.CREATE,
		Action.UPDATE,
		Action.DELETE,
		Action.EXPORT,
		Action.EMAIL,
		Action.PRINT
	};
	
	public static final List<Action> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static Action getAction(int ordinal) {
		if (ordinal == Action.ALL.ordinal())
			return Action.ALL;
		if (ordinal == Action.NONE.ordinal())
			return Action.NONE;
		if (ordinal == Action.READ.ordinal())
			return Action.READ;
		if (ordinal == Action.CREATE.ordinal())
			return Action.CREATE;
		if (ordinal == Action.UPDATE.ordinal())
			return Action.UPDATE;
		if (ordinal == Action.DELETE.ordinal())
			return Action.DELETE;
		if (ordinal == Action.EXPORT.ordinal())
			return Action.EXPORT;
		if (ordinal == Action.EMAIL.ordinal())
			return Action.EMAIL;
		if (ordinal == Action.PRINT.ordinal())
			return Action.PRINT;
		return null;
	}
	
	public static String toString(Action action) {
		return action.name();
	}
	
	public static String toString(Collection<Action> actionList) {
		StringBuffer buf = new StringBuffer();
		Iterator<Action> iterator = actionList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Action action = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(action);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<Action> actionList) {
		Collections.sort(actionList, createActionComparator());
	}
	
	public static Collection<Action> sortRecords(Collection<Action> actionCollection) {
		List<Action> list = new ArrayList<Action>(actionCollection);
		Collections.sort(list, createActionComparator());
		return list;
	}
	
	public static Comparator<Action> createActionComparator() {
		return new Comparator<Action>() {
			public int compare(Action action1, Action action2) {
				String text1 = action1.value();
				String text2 = action2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
