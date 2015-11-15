package nam.ui.util;

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

import nam.ui.Table;


public class TableUtil extends BaseUtil {
	
	public static Object getKey(Table table) {
		return table.getName();
	}
	
	public static String getLabel(Table table) {
		return table.getName();
	}
	
	public static boolean isEmpty(Table table) {
		if (table == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(table.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Table> tableList) {
		if (tableList == null  || tableList.size() == 0)
			return true;
		Iterator<Table> iterator = tableList.iterator();
		while (iterator.hasNext()) {
			Table table = iterator.next();
			if (!isEmpty(table))
				return false;
		}
		return true;
	}
	
	public static String toString(Table table) {
		if (isEmpty(table))
			return "Table: [uninitialized] "+table.toString();
		String text = table.toString();
		return text;
	}
	
	public static String toString(Collection<Table> tableList) {
		if (isEmpty(tableList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Table> iterator = tableList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Table table = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(table);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Table create() {
		Table table = new Table();
		initialize(table);
		return table;
	}
	
	public static void initialize(Table table) {
		//nothing for now
	}
	
	public static boolean validate(Table table) {
		if (table == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(table.getName(), "\"Name\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Table> tableList) {
		Validator validator = Validator.getValidator();
		Iterator<Table> iterator = tableList.iterator();
		while (iterator.hasNext()) {
			Table table = iterator.next();
			//TODO break or accumulate?
			validate(table);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Table> tableList) {
		Collections.sort(tableList, createTableComparator());
	}
	
	public static Collection<Table> sortRecords(Collection<Table> tableCollection) {
		List<Table> list = new ArrayList<Table>(tableCollection);
		Collections.sort(list, createTableComparator());
		return list;
	}
	
	public static Comparator<Table> createTableComparator() {
		return new Comparator<Table>() {
			public int compare(Table table1, Table table2) {
				Object key1 = getKey(table1);
				Object key2 = getKey(table2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Table clone(Table table) {
		if (table == null)
			return null;
		Table clone = create();
		clone.setName(ObjectUtil.clone(table.getName()));
		return clone;
	}
	
	public static List<Table> clone(List<Table> tableList) {
		if (tableList == null)
			return null;
		List<Table> newList = new ArrayList<Table>();
		Iterator<Table> iterator = tableList.iterator();
		while (iterator.hasNext()) {
			Table table = iterator.next();
			Table clone = clone(table);
			newList.add(clone);
		}
		return newList;
	}
	
}
