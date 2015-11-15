package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Items;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.Validator;


public class ItemsUtil extends BaseUtil {
	
	public static String getKey(Items items) {
		return items.toString();
	}
	
	public static boolean getLabel(Collection<Items> itemsList) {
		if (itemsList == null  || itemsList.size() == 0)
			return true;
		Iterator<Items> iterator = itemsList.iterator();
		while (iterator.hasNext()) {
			Items items = iterator.next();
			if (!isEmpty(items))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Items items) {
		if (items == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Items> itemsList) {
		if (itemsList == null  || itemsList.size() == 0)
			return true;
		Iterator<Items> iterator = itemsList.iterator();
		while (iterator.hasNext()) {
			Items items = iterator.next();
			if (!isEmpty(items))
				return false;
		}
		return true;
	}
	
	public static String toString(Items items) {
		if (isEmpty(items))
			return "Items: [uninitialized] "+items.toString();
		String text = items.toString();
		return text;
	}
	
	public static String toString(Collection<Items> itemsList) {
		if (isEmpty(itemsList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Items> iterator = itemsList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Items items = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(items);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Items create() {
		Items items = new Items();
		initialize(items);
		return items;
	}
	
	public static void initialize(Items items) {
		//nothing for now
	}
	
	public static boolean validate(Items items) {
		if (items == null)
			return false;
		Validator validator = Validator.getValidator();
		//TODO SerializableUtil.validate(items.getIdsAndItemsAndSecrets());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Items> itemsList) {
		Validator validator = Validator.getValidator();
		Iterator<Items> iterator = itemsList.iterator();
		while (iterator.hasNext()) {
			Items items = iterator.next();
			//TODO break or accumulate?
			validate(items);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Items> itemsList) {
		Collections.sort(itemsList, createItemsComparator());
	}
	
	public static Collection<Items> sortRecords(Collection<Items> itemsCollection) {
		List<Items> list = new ArrayList<Items>(itemsCollection);
		Collections.sort(list, createItemsComparator());
		return list;
	}
	
	public static Comparator<Items> createItemsComparator() {
		return new Comparator<Items>() {
			public int compare(Items items1, Items items2) {
				Object key1 = getKey(items1);
				Object key2 = getKey(items2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Items clone(Items items) {
		if (items == null)
			return null;
		Items clone = create();
		//TODO clone.setIdsAndItemsAndSecrets(SerializableUtil.clone(items.getIdsAndItemsAndSecrets()));
		return clone;
	}
	
}
