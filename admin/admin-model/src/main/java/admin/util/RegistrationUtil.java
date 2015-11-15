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


public class RegistrationUtil extends BaseUtil {
	
	public static Object getKey(Registration registration) {
		return registration.getUser().getUserName();
	}
	
	public static String getLabel(Registration registration) {
		return registration.getUser().getUserName();
	}
	
	public static boolean isEmpty(Registration registration) {
		if (registration == null)
			return true;
		boolean status = false;
		status |= UserUtil.isEmpty(registration.getUser());
		return status;
	}
	
	public static boolean isEmpty(Collection<Registration> registrationList) {
		if (registrationList == null  || registrationList.size() == 0)
			return true;
		Iterator<Registration> iterator = registrationList.iterator();
		while (iterator.hasNext()) {
			Registration registration = iterator.next();
			if (!isEmpty(registration))
				return false;
		}
		return true;
	}
	
	public static String toString(Registration registration) {
		if (isEmpty(registration))
			return "Registration: [uninitialized] "+registration.toString();
		String text = "";
		text += UserUtil.toString(registration.getUser());
		return text;
	}
	
	public static String toString(Collection<Registration> registrationList) {
		if (isEmpty(registrationList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Registration> iterator = registrationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Registration registration = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(registration);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Registration create() {
		Registration registration = new Registration();
		initialize(registration);
		return registration;
	}
	
	public static void initialize(Registration registration) {
		if (registration.getEnabled() == null)
			registration.setEnabled(true);
		if (registration.getUser() == null)
			registration.setUser(UserUtil.create());
	}
	
	public static boolean validate(Registration registration) {
		if (registration == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.isFalse(UserUtil.isEmpty(registration.getUser()), "\"User\" must be specified");
		UserUtil.validate(registration.getUser());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Registration> registrationList) {
		Validator validator = Validator.getValidator();
		Iterator<Registration> iterator = registrationList.iterator();
		while (iterator.hasNext()) {
			Registration registration = iterator.next();
			//TODO break or accumulate?
			validate(registration);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Registration> registrationList) {
		Collections.sort(registrationList, createRegistrationComparator());
	}
	
	public static Collection<Registration> sortRecords(Collection<Registration> registrationCollection) {
		List<Registration> list = new ArrayList<Registration>(registrationCollection);
		Collections.sort(list, createRegistrationComparator());
		return list;
	}
	
	public static void sortRecordsByUser(List<Registration> registrationList) {
		Collections.sort(registrationList, new Comparator<Registration>() {
			public int compare(Registration registration1, Registration registration2) {
				int status = registration1.compareTo(registration2);
				return status;
			}
		});
	}
	
	public static Comparator<Registration> createRegistrationComparator() {
		return new Comparator<Registration>() {
			public int compare(Registration registration1, Registration registration2) {
				Object key1 = getKey(registration1);
				Object key2 = getKey(registration2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Registration clone(Registration registration) {
		if (registration == null)
			return null;
		Registration clone = create();
		clone.setId(ObjectUtil.clone(registration.getId()));
		clone.setEnabled(ObjectUtil.clone(registration.getEnabled()));
		clone.setUser(UserUtil.clone(registration.getUser()));
		clone.setLoginCount(ObjectUtil.clone(registration.getLoginCount()));
		return clone;
	}
	
}
