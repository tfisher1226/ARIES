package admin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.util.EmailAddressUtil;
import org.aries.common.util.PersonNameUtil;
import org.aries.common.util.StreetAddressUtil;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import admin.UserCriteria;


public class UserCriteriaUtil extends BaseUtil {
	
//	public static Object getKey(UserCriteria userCriteria) {
//		//nothing for now
//	}
//	
//	public static String getLabel(UserCriteria userCriteria) {
//		//nothing for now
//	}
	
	public static boolean isEmpty(UserCriteria userCriteria) {
		if (userCriteria == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<UserCriteria> userCriteriaList) {
		if (userCriteriaList == null  || userCriteriaList.size() == 0)
			return true;
		Iterator<UserCriteria> iterator = userCriteriaList.iterator();
		while (iterator.hasNext()) {
			UserCriteria userCriteria = iterator.next();
			if (!isEmpty(userCriteria))
				return false;
		}
		return true;
	}
	
	public static String toString(UserCriteria userCriteria) {
		if (isEmpty(userCriteria))
			return "UserCriteria: [uninitialized] "+userCriteria.toString();
		String text = userCriteria.toString();
		return text;
	}
	
	public static String toString(Collection<UserCriteria> userCriteriaList) {
		if (isEmpty(userCriteriaList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<UserCriteria> iterator = userCriteriaList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			UserCriteria userCriteria = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(userCriteria);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static UserCriteria create() {
		UserCriteria userCriteria = new UserCriteria();
		initialize(userCriteria);
		return userCriteria;
	}
	
	public static void initialize(UserCriteria userCriteria) {
		if (userCriteria.getEnabled() == null)
			userCriteria.setEnabled(true);
	}
	
	public static boolean validate(UserCriteria userCriteria) {
		if (userCriteria == null)
			return false;
		Validator validator = Validator.getValidator();
		EmailAddressUtil.validate(userCriteria.getEmailAddress());
		PersonNameUtil.validate(userCriteria.getPersonName());
		RoleUtil.validate(userCriteria.getRoles());
		StreetAddressUtil.validate(userCriteria.getStreetAddress());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<UserCriteria> userCriteriaList) {
		Validator validator = Validator.getValidator();
		Iterator<UserCriteria> iterator = userCriteriaList.iterator();
		while (iterator.hasNext()) {
			UserCriteria userCriteria = iterator.next();
			//TODO break or accumulate?
			validate(userCriteria);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static UserCriteria clone(UserCriteria userCriteria) {
		if (userCriteria == null)
			return null;
		UserCriteria clone = create();
		clone.setEnabled(ObjectUtil.clone(userCriteria.getEnabled()));
		clone.setUserName(ObjectUtil.clone(userCriteria.getUserName()));
		clone.setPersonName(PersonNameUtil.clone(userCriteria.getPersonName()));
		clone.setEmailAddress(EmailAddressUtil.clone(userCriteria.getEmailAddress()));
		clone.setStreetAddress(StreetAddressUtil.clone(userCriteria.getStreetAddress()));
		clone.setRoles(RoleUtil.clone(userCriteria.getRoles()));
		return clone;
	}
	
}
