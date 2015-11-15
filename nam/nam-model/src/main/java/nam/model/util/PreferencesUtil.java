package nam.model.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Preferences;
import nam.model.User;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.MapEntry;
import org.aries.util.BaseUtil;
import org.aries.util.ExceptionUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class PreferencesUtil extends BaseUtil {
	
	public static boolean isEmpty(Preferences preferences) {
		if (preferences == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(preferences.getUser());
		return status;
	}
	
	public static boolean isEmpty(Collection<Preferences> preferencesList) {
		if (preferencesList == null  || preferencesList.size() == 0)
			return true;
		Iterator<Preferences> iterator = preferencesList.iterator();
		while (iterator.hasNext()) {
			Preferences preferences = iterator.next();
			if (!isEmpty(preferences))
				return false;
		}
		return true;
	}
	
	public static String toString(Preferences preferences) {
		if (isEmpty(preferences))
			return "";
		String text = "";
		text += preferences.getUser();
		return text;
	}
	
	public static String toString(Collection<Preferences> preferencesList) {
		if (isEmpty(preferencesList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Preferences> iterator = preferencesList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Preferences preferences = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(preferences);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Preferences create() {
		Preferences preferences = new Preferences();
		initialize(preferences);
		return preferences;
	}
	
	public static Preferences create(User user) {
		Preferences preferences = create();
		preferences.setUser(user.getUserName());
		return preferences;
	}
	
	public static void initialize(Preferences preferences) {
		if (preferences.getEnableTooltips() == null)
			preferences.setEnableTooltips(false);
	}
	
	public static boolean validate(Preferences preferences) {
		if (preferences == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Preferences> preferencesList) {
		Validator validator = Validator.getValidator();
		Iterator<Preferences> iterator = preferencesList.iterator();
		while (iterator.hasNext()) {
			Preferences preferences = iterator.next();
			//TODO break or accumulate?
			validate(preferences);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
//	public static void sortRecords(List<Preferences> preferencesList) {
//		Collections.sort(preferencesList, createPreferencesComparator());
//	}
	
//	public static Collection<Preferences> sortRecords(Collection<Preferences> preferencesCollection) {
//		List<Preferences> list = new ArrayList<Preferences>(preferencesCollection);
//		Collections.sort(list, createPreferencesComparator());
//		return list;
//	}
	
//	public static void sortRecordsByUser(List<Preferences> preferencesList) {
//		Collections.sort(preferencesList, new Comparator<Preferences>() {
//			public int compare(Preferences preferences1, Preferences preferences2) {
//				int status = preferences1.compareTo(preferences2);
//				return status;
//			}
//		});
//	}
	
//	public static Comparator<Preferences> createPreferencesComparator() {
//		return new Comparator<Preferences>() {
//			public int compare(Preferences preferences1, Preferences preferences2) {
//				int status = preferences1.compareTo(preferences2);
//				return status;
//			}
//		};
//	}
	
	
	public static Preferences clone(Preferences preferences) {
		if (preferences == null)
			return null;
		Preferences clone = create();
		clone.setId(ObjectUtil.clone(preferences.getId()));
		clone.setUser(preferences.getUser());
		clone.setThemeId(ObjectUtil.clone(preferences.getThemeId()));
		clone.setOpenNodes(clone(preferences.getOpenNodes(), String.class, Boolean.class));
		clone.setSelectedNode(ObjectUtil.clone(preferences.getSelectedNode()));
		clone.setEnableTooltips(ObjectUtil.clone(preferences.getEnableTooltips()));
		return clone;
	}
	
	public static <K, V> org.aries.common.Map clone(org.aries.common.Map map, Class<K> keyClass, Class<V> valueClass) {
		org.aries.common.Map newMap = new org.aries.common.Map();
		List<MapEntry> entries = map.getEntries();
		Iterator<MapEntry> iterator = entries.iterator();
		while (iterator.hasNext()) {
			MapEntry mapEntry = iterator.next();
			
			try {
				@SuppressWarnings("unchecked") K key = (K) mapEntry.getKey();
				@SuppressWarnings("unchecked") V value = (V) mapEntry.getValue();
				K newKey = ObjectUtil.getCloneValue(key, keyClass);
				V newValue = ObjectUtil.getCloneValue(value, valueClass);
				MapEntry newEntry = new MapEntry();
				newEntry.setKey(newKey);
				newEntry.setValue(newValue);
				newMap.getEntries().add(newEntry);
			} catch (Exception e) {
				throw ExceptionUtil.rewrap(e);
			}
		}
		
		return newMap;
	}
}
