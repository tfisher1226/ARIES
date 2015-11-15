package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.CacheProvider;


public class CacheProviderUtil extends BaseUtil {
	
	public static Object getKey(CacheProvider cacheProvider) {
		return cacheProvider.getName();
	}
	
	public static String getLabel(CacheProvider cacheProvider) {
		return cacheProvider.getName();
	}
	
	public static boolean isEmpty(CacheProvider cacheProvider) {
		if (cacheProvider == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<CacheProvider> cacheProviderList) {
		if (cacheProviderList == null  || cacheProviderList.size() == 0)
			return true;
		Iterator<CacheProvider> iterator = cacheProviderList.iterator();
		while (iterator.hasNext()) {
			CacheProvider cacheProvider = iterator.next();
			if (!isEmpty(cacheProvider))
				return false;
		}
		return true;
	}
	
	public static String toString(CacheProvider cacheProvider) {
		if (isEmpty(cacheProvider))
			return "CacheProvider: [uninitialized] "+cacheProvider.toString();
		String text = cacheProvider.toString();
		return text;
	}
	
	public static String toString(Collection<CacheProvider> cacheProviderList) {
		if (isEmpty(cacheProviderList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<CacheProvider> iterator = cacheProviderList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			CacheProvider cacheProvider = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(cacheProvider);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static CacheProvider create() {
		CacheProvider cacheProvider = new CacheProvider();
		initialize(cacheProvider);
		return cacheProvider;
	}
	
	public static void initialize(CacheProvider cacheProvider) {
		//nothing for now
	}
	
	public static boolean validate(CacheProvider cacheProvider) {
		if (cacheProvider == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<CacheProvider> cacheProviderList) {
		Validator validator = Validator.getValidator();
		Iterator<CacheProvider> iterator = cacheProviderList.iterator();
		while (iterator.hasNext()) {
			CacheProvider cacheProvider = iterator.next();
			//TODO break or accumulate?
			validate(cacheProvider);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<CacheProvider> cacheProviderList) {
		Collections.sort(cacheProviderList, createCacheProviderComparator());
	}
	
	public static Collection<CacheProvider> sortRecords(Collection<CacheProvider> cacheProviderCollection) {
		List<CacheProvider> list = new ArrayList<CacheProvider>(cacheProviderCollection);
		Collections.sort(list, createCacheProviderComparator());
		return list;
	}
	
	public static Comparator<CacheProvider> createCacheProviderComparator() {
		return new Comparator<CacheProvider>() {
			public int compare(CacheProvider cacheProvider1, CacheProvider cacheProvider2) {
				Object key1 = getKey(cacheProvider1);
				Object key2 = getKey(cacheProvider2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static CacheProvider clone(CacheProvider cacheProvider) {
		if (cacheProvider == null)
			return null;
		CacheProvider clone = create();
		return clone;
	}
	
}
