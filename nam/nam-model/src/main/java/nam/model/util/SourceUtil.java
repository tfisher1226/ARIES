package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Source;
import nam.model.Sources;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class SourceUtil extends BaseUtil {
	
	public static Object getKey(Source source) {
		return source.getName();
	}
	
	public static String getLabel(Source source) {
		return NameUtil.capName(source.getName());
	}
	
	public static boolean isEmpty(Source source) {
		if (source == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Source> sourceList) {
		if (sourceList == null  || sourceList.size() == 0)
			return true;
		Iterator<Source> iterator = sourceList.iterator();
		while (iterator.hasNext()) {
			Source source = iterator.next();
			if (!isEmpty(source))
				return false;
		}
		return true;
	}
	
	public static String toString(Source source) {
		if (isEmpty(source))
			return "Source: [uninitialized] "+source.toString();
		String text = source.toString();
		return text;
	}
	
	public static String toString(Collection<Source> sourceList) {
		if (isEmpty(sourceList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Source> iterator = sourceList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Source source = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(source);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Source create() {
		Source source = new Source();
		initialize(source);
		return source;
	}
	
	public static void initialize(Source source) {
		//nothing for now
	}
	
	public static boolean validate(Source source) {
		if (source == null)
			return false;
		Validator validator = Validator.getValidator();
		AdapterUtil.validate(source.getAdapter());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Source> sourceList) {
		Validator validator = Validator.getValidator();
		Iterator<Source> iterator = sourceList.iterator();
		while (iterator.hasNext()) {
			Source source = iterator.next();
			//TODO break or accumulate?
			validate(source);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Source> sourceList) {
		Collections.sort(sourceList, createSourceComparator());
	}
	
	public static Collection<Source> sortRecords(Collection<Source> sourceCollection) {
		List<Source> list = new ArrayList<Source>(sourceCollection);
		Collections.sort(list, createSourceComparator());
		return list;
	}
	
	public static Comparator<Source> createSourceComparator() {
		return new Comparator<Source>() {
			public int compare(Source source1, Source source2) {
				Object key1 = getKey(source1);
				Object key2 = getKey(source2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Source clone(Source source) {
		if (source == null)
			return null;
		Source clone = create();
		clone.setDriverClass(ObjectUtil.clone(source.getDriverClass()));
		clone.setMaxActive(ObjectUtil.clone(source.getMaxActive()));
		clone.setMaxIdle(ObjectUtil.clone(source.getMaxIdle()));
		clone.setMaxWait(ObjectUtil.clone(source.getMaxWait()));
		clone.setAdapter(AdapterUtil.clone(source.getAdapter()));
		return clone;
	}


//	public static boolean isSourceExists(List<Source> sources, Source source) {
//		Iterator<Source> iterator = sources.iterator();
//		while (iterator.hasNext()) {
//			Source source1 = iterator.next();
//			if (equals(source1, source)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
//	public static void addSource(List<Source> sources, Source source) {
//		if (!isSourceExists(sources, source)) {
//			sources.add(source);
//		}
//	}
	
	public static Map<String, Source> createSourceMap(Sources sources) {
		if (sources == null)
			return new HashMap<String, Source>();
		return createSourceMap(sources.getSources());
	}

	public static Map<String, Source> createSourceMap(List<Source> sources) {
		Map<String, Source> map = new HashMap<String, Source>();
		Iterator<Source> iterator = sources.iterator();
		while (iterator.hasNext()) {
			Source source = (Source) iterator.next();
			String name = source.getName();
			if (name != null)
				map.put(name, source);
		}
		return map;
	}

	public static boolean equals(Source source1, Source source2) {
		Assert.notNull(source1, "First source must be specified");
		Assert.notNull(source2, "Second source must be specified");
		if (!source1.getName().equals(source2.getName()))
			return false;
		if (!source1.getStore().equals(source2.getStore()))
			return false;
		return true;
	}
	
}
