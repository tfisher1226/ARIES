package admin.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import admin.Registration;


public class RegistrationUtil {
	
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
			return "";
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
		return buf.toString();
	}
	
	public static Registration create() {
		Registration registration = new Registration();
		initialize(registration);
		return registration;
	}
	
	public static void initialize(Registration registration) {
		if (registration.getEnabled() == null)
			registration.setEnabled(true);
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
		Collections.sort(registrationList, new Comparator<Registration>() {
			public int compare(Registration registration1, Registration registration2) {
				String text1 = RegistrationUtil.toString(registration1);
				String text2 = RegistrationUtil.toString(registration2);
				return text1.compareTo(text2);
			}
		});
	}
	
	public static void sortRecordsByUser(List<Registration> registrationList) {
		Collections.sort(registrationList, new Comparator<Registration>() {
			public int compare(Registration registration1, Registration registration2) {
				String text1 = UserUtil.toString(registration1.getUser());
				String text2 = UserUtil.toString(registration2.getUser());
				return text1.compareTo(text2);
			}
		});
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
