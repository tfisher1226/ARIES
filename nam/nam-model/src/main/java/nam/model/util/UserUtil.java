package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.User;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.util.EmailAddressUtil;
import org.aries.common.util.PersonNameUtil;
import org.aries.common.util.PhoneNumberUtil;
import org.aries.common.util.StreetAddressUtil;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class UserUtil extends BaseUtil {
	
	public static Object getKey(User user) {
		return user.getUserName();
	}
	
	public static boolean isEmpty(User user) {
		if (user == null)
			return true;
		boolean status = false;
		//status |= PersonNameUtil.isEmpty(user.getName());
		status |= PersonNameUtil.isEmpty(user.getPersonName());
		status |= StringUtils.isEmpty(user.getUserName());
		status |= StringUtils.isEmpty(user.getPasswordHash());
		status |= EmailAddressUtil.isEmpty(user.getEmailAddress());
		status |= RoleUtil.isEmpty(user.getRoles());
		return status;
	}
	
	public static boolean isEmpty(Collection<User> userList) {
		if (userList == null  || userList.size() == 0)
			return true;
		Iterator<User> iterator = userList.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (!isEmpty(user))
				return false;
		}
		return true;
	}
	
	public static String toString(User user) {
		if (isEmpty(user))
			return "";
		String text = "";
		text += PersonNameUtil.toString(user.getPersonName());
		return text;
	}
	
	public static String toString(Collection<User> userList) {
		if (isEmpty(userList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<User> iterator = userList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			User user = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(user);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static User create() {
		User user = new User();
		initialize(user);
		return user;
	}
	
	public static void initialize(User user) {
		if (user.getEnabled() == null)
			user.setEnabled(true);
	}
	
	public static boolean validate(User user) {
		if (user == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.isFalse(EmailAddressUtil.isEmpty(user.getEmailAddress()), "\"EmailAddress\" must be specified");
		//validator.isFalse(PersonNameUtil.isEmpty(user.getName()), "\"Name\" must be specified");
		validator.notEmpty(user.getPasswordHash(), "\"PasswordHash\" must be specified");
		validator.isFalse(PersonNameUtil.isEmpty(user.getPersonName()), "\"PersonName\" must be specified");
		validator.isFalse(RoleUtil.isEmpty(user.getRoles()), "At least one of \"Roles\" must be specified");
		validator.notEmpty(user.getUserName(), "\"UserName\" must be specified");
		PhoneNumberUtil.validate(user.getCellPhone());
		EmailAddressUtil.validate(user.getEmailAddress());
		PhoneNumberUtil.validate(user.getHomePhone());
		//PersonNameUtil.validate(user.getName());
		PermissionUtil.validate(user.getPermissions());
		PersonNameUtil.validate(user.getPersonName());
		PreferencesUtil.validate(user.getPreferences());
		RoleUtil.validate(user.getRoles());
		StreetAddressUtil.validate(user.getStreetAddress());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<User> userList) {
		Validator validator = Validator.getValidator();
		Iterator<User> iterator = userList.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			//TODO break or accumulate?
			validate(user);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<User> userList) {
		Collections.sort(userList, createUserComparator());
	}
	
	public static Collection<User> sortRecords(Collection<User> userCollection) {
		List<User> list = new ArrayList<User>(userCollection);
		Collections.sort(list, createUserComparator());
		return list;
	}
	
	public static void sortRecordsByName(List<User> userList) {
		Collections.sort(userList, new Comparator<User>() {
			public int compare(User user1, User user2) {
				int status = user1.getPersonName().compareTo(user2.getPersonName());
				return status;
			}
		});
	}
	
	public static void sortRecordsByPersonName(List<User> userList) {
		Collections.sort(userList, new Comparator<User>() {
			public int compare(User user1, User user2) {
				int status = user1.getPersonName().compareTo(user2.getPersonName());
				return status;
			}
		});
	}
	
	public static void sortRecordsByUserName(List<User> userList) {
		Collections.sort(userList, new Comparator<User>() {
			public int compare(User user1, User user2) {
				String text1 = user1.getUserName();
				String text2 = user2.getUserName();
				return text1.compareTo(text2);
			}
		});
	}
	
	public static Comparator<User> createUserComparator() {
		return new Comparator<User>() {
			public int compare(User user1, User user2) {
				int status = user1.getPersonName().compareTo(user2.getPersonName());
				return status;
			}
		};
	}
	
	public static User clone(User user) {
		if (user == null)
			return null;
		User clone = create();
		clone.setId(ObjectUtil.clone(user.getId()));
		//clone.setName(PersonNameUtil.clone(user.getName()));
		clone.setPersonName(PersonNameUtil.clone(user.getPersonName()));
		clone.setUserName(ObjectUtil.clone(user.getUserName()));
		clone.setPasswordHash(ObjectUtil.clone(user.getPasswordHash()));
		clone.setPasswordSalt(ObjectUtil.clone(user.getPasswordSalt()));
		clone.setEnabled(ObjectUtil.clone(user.getEnabled()));
		clone.setEmailAddress(EmailAddressUtil.clone(user.getEmailAddress()));
		clone.setStreetAddress(StreetAddressUtil.clone(user.getStreetAddress()));
		clone.setHomePhone(PhoneNumberUtil.clone(user.getHomePhone()));
		clone.setCellPhone(PhoneNumberUtil.clone(user.getCellPhone()));
		clone.getRoles().addAll(RoleUtil.clone(user.getRoles()));
		clone.getPermissions().addAll(PermissionUtil.clone(user.getPermissions()));
		clone.setPreferences(PreferencesUtil.clone(user.getPreferences()));
		clone.setCreationDate(ObjectUtil.clone(user.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(user.getLastUpdate()));
		return clone;
	}
	
}
