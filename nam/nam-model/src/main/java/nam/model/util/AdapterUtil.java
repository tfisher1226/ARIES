package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Adapter;
import nam.model.Adapters;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class AdapterUtil extends BaseUtil {
	
	public static Object getKey(Adapter adapter) {
		return adapter.getName();
	}
	
	public static String getLabel(Adapter adapter) {
		return adapter.getName();
	}
	
	public static boolean isEmpty(Adapter adapter) {
		if (adapter == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Adapter> adapterList) {
		if (adapterList == null  || adapterList.size() == 0)
			return true;
		Iterator<Adapter> iterator = adapterList.iterator();
		while (iterator.hasNext()) {
			Adapter adapter = iterator.next();
			if (!isEmpty(adapter))
				return false;
		}
		return true;
	}
	
	public static String toString(Adapter adapter) {
		if (isEmpty(adapter))
			return "Adapter: [uninitialized] "+adapter.toString();
		String text = adapter.toString();
		return text;
	}
	
	public static String toString(Collection<Adapter> adapterList) {
		if (isEmpty(adapterList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Adapter> iterator = adapterList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Adapter adapter = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(adapter);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Adapter create() {
		Adapter adapter = new Adapter();
		initialize(adapter);
		return adapter;
	}
	
	public static void initialize(Adapter adapter) {
		if (adapter.getTransacted() == null)
			adapter.setTransacted(true);
	}
	
	public static boolean validate(Adapter adapter) {
		if (adapter == null)
			return false;
		Validator validator = Validator.getValidator();
		ConnectionPoolUtil.validate(adapter.getConnectionPool());
		PropertiesUtil.validate(adapter.getProperties());
		ProviderUtil.validate(adapter.getProvider());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Adapter> adapterList) {
		Validator validator = Validator.getValidator();
		Iterator<Adapter> iterator = adapterList.iterator();
		while (iterator.hasNext()) {
			Adapter adapter = iterator.next();
			//TODO break or accumulate?
			validate(adapter);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Adapter> adapterList) {
		Collections.sort(adapterList, createAdapterComparator());
	}
	
	public static Collection<Adapter> sortRecords(Collection<Adapter> adapterCollection) {
		List<Adapter> list = new ArrayList<Adapter>(adapterCollection);
		Collections.sort(list, createAdapterComparator());
		return list;
	}
	
	public static Comparator<Adapter> createAdapterComparator() {
		return new Comparator<Adapter>() {
			public int compare(Adapter adapter1, Adapter adapter2) {
				Object key1 = getKey(adapter1);
				Object key2 = getKey(adapter2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Adapter clone(Adapter adapter) {
		if (adapter == null)
			return null;
		Adapter clone = create();
		clone.setType(ObjectUtil.clone(adapter.getType()));
		clone.setName(ObjectUtil.clone(adapter.getName()));
		clone.setClassName(ObjectUtil.clone(adapter.getClassName()));
		clone.setTransacted(ObjectUtil.clone(adapter.getTransacted()));
		clone.setMaxThreads(ObjectUtil.clone(adapter.getMaxThreads()));
		clone.setConnectionPool(ConnectionPoolUtil.clone(adapter.getConnectionPool()));
		clone.setProvider(ProviderUtil.clone(adapter.getProvider()));
		clone.setProperties(PropertiesUtil.clone(adapter.getProperties()));
		return clone;
	}


//	public static boolean isAdapterExists(List<Adapter> adapters, Adapter adapter) {
//		Iterator<Adapter> iterator = adapters.iterator();
//		while (iterator.hasNext()) {
//			Adapter adapter1 = iterator.next();
//			if (equals(adapter1, adapter)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
//	public static void addAdapter(List<Adapter> adapters, Adapter adapter) {
//		if (!isAdapterExists(adapters, adapter)) {
//			adapters.add(adapter);
//		}
//	}
	
	public static Map<String, Adapter> createAdapterMap(Adapters adapters) {
		if (adapters == null)
			return new HashMap<String, Adapter>();
		return createAdapterMap(adapters.getAdapters());
	}

	public static Map<String, Adapter> createAdapterMap(List<Adapter> adapters) {
		Map<String, Adapter> map = new HashMap<String, Adapter>();
		Iterator<Adapter> iterator = adapters.iterator();
		while (iterator.hasNext()) {
			Adapter adapter = (Adapter) iterator.next();
			String name = adapter.getName();
			if (name != null)
				map.put(name, adapter);
		}
		return map;
	}

	public static boolean equals(Adapter adapter1, Adapter adapter2) {
		Assert.notNull(adapter1, "First adapter must be specified");
		Assert.notNull(adapter2, "Second adapter must be specified");
		if (!adapter1.getName().equals(adapter2.getName()))
			return false;
		if (!adapter1.getClassName().equals(adapter2.getClassName()))
			return false;
		return true;
	}
	
}
