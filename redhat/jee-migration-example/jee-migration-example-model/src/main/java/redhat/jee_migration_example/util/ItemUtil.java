package redhat.jee_migration_example.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Messaging;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import redhat.jee_migration_example.Item;


public class ItemUtil extends BaseUtil {
	
	public static Object getKey(Item item) {
		return item.getName();
	}
	
	public static String getLabel(Item item) {
		return NameUtil.capName(item.getName());
	}
	
	public static boolean isEmpty(Item item) {
		if (item == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(item.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Item> itemList) {
		if (itemList == null  || itemList.size() == 0)
			return true;
		Iterator<Item> iterator = itemList.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			if (!isEmpty(item))
				return false;
		}
		return true;
	}
	
	public static String toString(Item item) {
		if (isEmpty(item))
			return "Item: [uninitialized] "+item.toString();
		String text = item.toString();
		return text;
	}
	
	public static String toString(Collection<Item> itemList) {
		if (isEmpty(itemList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Item> iterator = itemList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Item item = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(item);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Item create() {
		Item item = new Item();
		initialize(item);
		return item;
	}
	
	public static void initialize(Item item) {
		//nothing for now
	}
	
	public static boolean validate(Item item) {
		if (item == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(item.getName(), "\"Name\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Item> itemList) {
		Validator validator = Validator.getValidator();
		Iterator<Item> iterator = itemList.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			//TODO break or accumulate?
			validate(item);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Item> itemList) {
		Collections.sort(itemList, createItemComparator());
	}
	
	public static Collection<Item> sortRecords(Collection<Item> itemCollection) {
		List<Item> list = new ArrayList<Item>(itemCollection);
		Collections.sort(list, createItemComparator());
		return list;
	}
	
	public static void sortRecordsByName(List<Item> itemList) {
		Collections.sort(itemList, new Comparator<Item>() {
			public int compare(Item item1, Item item2) {
				String text1 = item1.getName();
				String text2 = item2.getName();
				return text1.compareTo(text2);
			}
		});
	}
	
	public static Comparator<Item> createItemComparator() {
		return new Comparator<Item>() {
			public int compare(Item item1, Item item2) {
				int status = item1.compareTo(item2);
				return status;
			}
		};
	}
	
	public static Item clone(Item item) {
		if (item == null)
			return null;
		Item clone = create();
		clone.setId(ObjectUtil.clone(item.getId()));
		clone.setName(ObjectUtil.clone(item.getName()));
		return clone;
	}
	
}
