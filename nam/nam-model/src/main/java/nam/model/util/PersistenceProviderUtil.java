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

import nam.model.PersistenceProvider;


public class PersistenceProviderUtil extends BaseUtil {
	
	public static Object getKey(PersistenceProvider persistenceProvider) {
		return persistenceProvider.getName();
	}
	
	public static String getLabel(PersistenceProvider persistenceProvider) {
		return persistenceProvider.getName();
	}
	
	public static boolean isEmpty(PersistenceProvider persistenceProvider) {
		if (persistenceProvider == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<PersistenceProvider> persistenceProviderList) {
		if (persistenceProviderList == null  || persistenceProviderList.size() == 0)
			return true;
		Iterator<PersistenceProvider> iterator = persistenceProviderList.iterator();
		while (iterator.hasNext()) {
			PersistenceProvider persistenceProvider = iterator.next();
			if (!isEmpty(persistenceProvider))
				return false;
		}
		return true;
	}
	
	public static String toString(PersistenceProvider persistenceProvider) {
		if (isEmpty(persistenceProvider))
			return "PersistenceProvider: [uninitialized] "+persistenceProvider.toString();
		String text = persistenceProvider.toString();
		return text;
	}
	
	public static String toString(Collection<PersistenceProvider> persistenceProviderList) {
		if (isEmpty(persistenceProviderList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<PersistenceProvider> iterator = persistenceProviderList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PersistenceProvider persistenceProvider = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(persistenceProvider);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static PersistenceProvider create() {
		PersistenceProvider persistenceProvider = new PersistenceProvider();
		initialize(persistenceProvider);
		return persistenceProvider;
	}
	
	public static void initialize(PersistenceProvider persistenceProvider) {
		//nothing for now
	}
	
	public static boolean validate(PersistenceProvider persistenceProvider) {
		if (persistenceProvider == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<PersistenceProvider> persistenceProviderList) {
		Validator validator = Validator.getValidator();
		Iterator<PersistenceProvider> iterator = persistenceProviderList.iterator();
		while (iterator.hasNext()) {
			PersistenceProvider persistenceProvider = iterator.next();
			//TODO break or accumulate?
			validate(persistenceProvider);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<PersistenceProvider> persistenceProviderList) {
		Collections.sort(persistenceProviderList, createPersistenceProviderComparator());
	}
	
	public static Collection<PersistenceProvider> sortRecords(Collection<PersistenceProvider> persistenceProviderCollection) {
		List<PersistenceProvider> list = new ArrayList<PersistenceProvider>(persistenceProviderCollection);
		Collections.sort(list, createPersistenceProviderComparator());
		return list;
	}
	
	public static Comparator<PersistenceProvider> createPersistenceProviderComparator() {
		return new Comparator<PersistenceProvider>() {
			public int compare(PersistenceProvider persistenceProvider1, PersistenceProvider persistenceProvider2) {
				Object key1 = getKey(persistenceProvider1);
				Object key2 = getKey(persistenceProvider2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static PersistenceProvider clone(PersistenceProvider persistenceProvider) {
		if (persistenceProvider == null)
			return null;
		PersistenceProvider clone = create();
		return clone;
	}
	
}
