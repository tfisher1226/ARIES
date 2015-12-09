package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Provider;
import nam.model.Providers;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ProviderUtil extends BaseUtil {
	
	public static String getKey(Provider provider) {
		return provider.getName();
	}
	
	public static String getLabel(Provider provider) {
		return provider.getName();
	}
	
	public static boolean getLabel(Collection<Provider> providerList) {
		if (providerList == null  || providerList.size() == 0)
			return true;
		Iterator<Provider> iterator = providerList.iterator();
		while (iterator.hasNext()) {
			Provider provider = iterator.next();
			if (!isEmpty(provider))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Provider provider) {
		if (provider == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Provider> providerList) {
		if (providerList == null  || providerList.size() == 0)
			return true;
		Iterator<Provider> iterator = providerList.iterator();
		while (iterator.hasNext()) {
			Provider provider = iterator.next();
			if (!isEmpty(provider))
				return false;
		}
		return true;
	}
	
	public static String toString(Provider provider) {
		if (isEmpty(provider))
			return "Provider: [uninitialized] "+provider.toString();
		String text = provider.toString();
		return text;
	}
	
	public static String toString(Collection<Provider> providerList) {
		if (isEmpty(providerList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Provider> iterator = providerList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Provider provider = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(provider);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Provider create() {
		Provider provider = new Provider();
		initialize(provider);
		return provider;
	}
	
	public static void initialize(Provider provider) {
		if (provider.getTransacted() == null)
			provider.setTransacted(true);
	}
	
	public static boolean validate(Provider provider) {
		if (provider == null)
			return false;
		Validator validator = Validator.getValidator();
		JndiContextUtil.validate(provider.getJndiContext());
		PropertiesUtil.validate(provider.getProperties());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Provider> providerList) {
		Validator validator = Validator.getValidator();
		Iterator<Provider> iterator = providerList.iterator();
		while (iterator.hasNext()) {
			Provider provider = iterator.next();
			//TODO break or accumulate?
			validate(provider);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Provider> providerList) {
		Collections.sort(providerList, createProviderComparator());
	}
	
	public static Collection<Provider> sortRecords(Collection<Provider> providerCollection) {
		List<Provider> list = new ArrayList<Provider>(providerCollection);
		Collections.sort(list, createProviderComparator());
		return list;
	}
	
	public static Comparator<Provider> createProviderComparator() {
		return new Comparator<Provider>() {
			public int compare(Provider provider1, Provider provider2) {
				Object key1 = getKey(provider1);
				Object key2 = getKey(provider2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Provider clone(Provider provider) {
		if (provider == null)
			return null;
		Provider clone = create();
		clone.setName(ObjectUtil.clone(provider.getName()));
		clone.setType(ObjectUtil.clone(provider.getType()));
		clone.setStore(ObjectUtil.clone(provider.getStore()));
		clone.setUserName(ObjectUtil.clone(provider.getUserName()));
		clone.setPassword(ObjectUtil.clone(provider.getPassword()));
		clone.setJndiName(ObjectUtil.clone(provider.getJndiName()));
		clone.setJndiContext(JndiContextUtil.clone(provider.getJndiContext()));
		clone.setConnectionUrl(ObjectUtil.clone(provider.getConnectionUrl()));
		clone.setTransacted(ObjectUtil.clone(provider.getTransacted()));
		clone.setProperties(PropertiesUtil.clone(provider.getProperties()));
		return clone;
	}
	
	public static List<Provider> clone(List<Provider> providerList) {
		if (providerList == null)
			return null;
		List<Provider> newList = new ArrayList<Provider>();
		Iterator<Provider> iterator = providerList.iterator();
		while (iterator.hasNext()) {
			Provider provider = iterator.next();
			Provider clone = clone(provider);
			newList.add(clone);
		}
		return newList;
	}



	public static Map<String, Provider> createProviderMap(Providers providers) {
		if (providers == null)
			return new HashMap<String, Provider>();
		return createProviderMap(providers.getProviders());
	}

	public static Map<String, Provider> createProviderMap(Collection<Provider> providers) {
		Map<String, Provider> map = new HashMap<String, Provider>();
		Iterator<Provider> iterator = providers.iterator();
		while (iterator.hasNext()) {
			Provider provider = (Provider) iterator.next();
			String name = provider.getName();
			if (name != null)
				map.put(name, provider);
		}
		return map;
	}

}
