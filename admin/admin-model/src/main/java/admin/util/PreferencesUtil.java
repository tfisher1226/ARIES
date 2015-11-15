package admin.util;

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

import admin.Preferences;
import admin.Registration;
import admin.User;


public class PreferencesUtil extends BaseUtil {
	
	public static Object getKey(Preferences preferences) {
		return preferences.getUser().getUserName();
	}
	
	public static String getLabel(Preferences preferences) {
		return toString(preferences);
	}
	
	public static boolean isEmpty(Preferences preferences) {
		if (preferences == null)
			return true;
		boolean status = false;
		status |= UserUtil.isEmpty(preferences.getUser());
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
		text += UserUtil.toString(preferences.getUser());
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
		preferences.setUser(user);
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
	
	public static void sortRecords(List<Preferences> preferencesList) {
		Collections.sort(preferencesList, createPreferencesComparator());
	}
	
	public static Collection<Preferences> sortRecords(Collection<Preferences> preferencesCollection) {
		List<Preferences> list = new ArrayList<Preferences>(preferencesCollection);
		Collections.sort(list, createPreferencesComparator());
		return list;
	}
	
	public static void sortRecordsByUser(List<Preferences> preferencesList) {
		Collections.sort(preferencesList, new Comparator<Preferences>() {
			public int compare(Preferences preferences1, Preferences preferences2) {
				int status = preferences1.compareTo(preferences2);
				return status;
			}
		});
	}
	
	public static Comparator<Preferences> createPreferencesComparator() {
		return new Comparator<Preferences>() {
			public int compare(Preferences preferences1, Preferences preferences2) {
				Object key1 = getKey(preferences1);
				Object key2 = getKey(preferences2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Preferences clone(Preferences preferences) {
		if (preferences == null)
			return null;
		Preferences clone = create();
		clone.setId(ObjectUtil.clone(preferences.getId()));
		clone.setUser(preferences.getUser());
		clone.setThemeId(ObjectUtil.clone(preferences.getThemeId()));
		clone.setWorkState(ObjectUtil.clone(preferences.getWorkState(), String.class, String.class));
		clone.setOpenNodes(ObjectUtil.clone(preferences.getOpenNodes(), String.class, Boolean.class));
		clone.setSelectedNode(ObjectUtil.clone(preferences.getSelectedNode()));
		clone.setEnableTooltips(ObjectUtil.clone(preferences.getEnableTooltips()));
		return clone;
	}
	
}
