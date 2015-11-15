package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Attribute;
import nam.model.Cache;
import nam.model.Field;
import nam.model.Item;
import nam.model.Process;
import nam.model.Reference;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class CacheUtil extends BaseUtil {
	
	public static String getKey(Cache cache) {
		return cache.getType();
	}
	
	public static String getLabel(Cache cache) {
		return cache.getName();
	}

	public static boolean getLabel(Collection<Cache> cacheList) {
		if (cacheList == null  || cacheList.size() == 0)
			return true;
		Iterator<Cache> iterator = cacheList.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			if (!isEmpty(cache))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Cache cache) {
		if (cache == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Cache> cacheList) {
		if (cacheList == null  || cacheList.size() == 0)
			return true;
		Iterator<Cache> iterator = cacheList.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			if (!isEmpty(cache))
				return false;
		}
		return true;
	}
	
	public static String toString(Cache cache) {
		if (isEmpty(cache))
			return "Cache: [uninitialized] "+cache.toString();
		String text = cache.toString();
		return text;
	}
	
	public static String toString(Collection<Cache> cacheList) {
		if (isEmpty(cacheList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Cache> iterator = cacheList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Cache cache = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(cache);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Cache create() {
		Cache cache = new Cache();
		initialize(cache);
		return cache;
	}
	
	public static void initialize(Cache cache) {
		//nothing for now
	}
	
	public static boolean validate(Cache cache) {
		if (cache == null)
			return false;
		Validator validator = Validator.getValidator();
		ItemsUtil.validate(cache.getItems());
		TransactedUtil.validate(cache.getTransacted());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Cache> cacheList) {
		Validator validator = Validator.getValidator();
		Iterator<Cache> iterator = cacheList.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			//TODO break or accumulate?
			validate(cache);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Cache> cacheList) {
		Collections.sort(cacheList, createCacheComparator());
	}
	
	public static Collection<Cache> sortRecords(Collection<Cache> cacheCollection) {
		List<Cache> list = new ArrayList<Cache>(cacheCollection);
		Collections.sort(list, createCacheComparator());
		return list;
	}
	
	public static Comparator<Cache> createCacheComparator() {
		return new Comparator<Cache>() {
			public int compare(Cache cache1, Cache cache2) {
				Object key1 = getKey(cache1);
				Object key2 = getKey(cache2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Cache clone(Cache cache) {
		if (cache == null)
			return null;
		Cache clone = create();
		clone.setScope(ObjectUtil.clone(cache.getScope()));
		clone.setLevel(ObjectUtil.clone(cache.getLevel()));
		clone.setTransacted(TransactedUtil.clone(cache.getTransacted()));
		clone.setItems(ItemsUtil.clone(cache.getItems()));
		return clone;
	}
	
	public static List<Cache> clone(List<Cache> cacheList) {
		if (cacheList == null)
			return null;
		List<Cache> newList = new ArrayList<Cache>();
		Iterator<Cache> iterator = cacheList.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			Cache clone = clone(cache);
			newList.add(clone);
		}
		return newList;
	}


	public static String getRootName(Cache cache) {
		String rootName = ProjectUtil.getBaseName(cache.getName(), "Cache");
		return rootName;
	}
	
	public static String getPackageName(Cache cache) {
		String packageName = TypeUtil.getPackageName(cache.getType());
		return packageName + ".cache";
	}

	public static String getClassName(Cache cache) {
		String className = TypeUtil.getClassName(cache.getType());
//		if (className == null)
//			System.out.println();
		if (!className.toLowerCase().endsWith("cache"))
			className += "Cache";
		return className;
	}

	public static String getQualifiedName(Cache cache) {
		String qualifiedName = getPackageName(cache) + "." + getClassName(cache);
		return qualifiedName;
	}
	
	public static String getType(Cache cache) {
		return cache.getType();
	}
	
	public static String getCacheName(Cache cache) {
		Assert.notNull(cache, "Cache must be specified");
		if (!StringUtils.isEmpty(cache.getName()))
			return cache.getName();
		if (!StringUtils.isEmpty(cache.getRef()))
			return cache.getRef();
		String cacheName = NameUtil.uncapName(getClassName(cache));
		return cacheName;
		//return null;
	}

	public static void addItems(Cache cache, List<Field> itemList) {
		Iterator<Field> iterator = itemList.iterator();
		while (iterator.hasNext()) {
			Field item = iterator.next();
			addItem(cache, item);
		}
	}
	
	public static void addItem(Cache cache, Field item) {
		if (!cache.getIdsAndItemsAndSecrets().contains(item))
			cache.getIdsAndItemsAndSecrets().add(item);
	}

	public static boolean removeItem(Cache cache, Field item) {
		return cache.getIdsAndItemsAndSecrets().remove(item);
	}
	
	public static List<Item> getItems(Cache cache) {
		return getObjectList(cache, Item.class);
	}
	
	public static List<Attribute> getAttributes(Cache cache) {
		return getObjectList(cache, Attribute.class);
	}
	
	public static List<Reference> getReferences(Cache cache) {
		return getObjectList(cache, Reference.class);
	}
	
	public static <T> List<T> getObjectList(Cache cache, Class<?> objectClass) {
		List<Serializable> objects = cache.getIdsAndItemsAndSecrets();
		return ListUtil.getObjectList(objects, objectClass);
	}

//	public static List<Element> getElements(Cache cache) {
//		return cache.get;
//	}
	
}
