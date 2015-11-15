package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.EmailAddress;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class EmailAddressUtil extends BaseUtil {

	public static boolean isValid(EmailAddress emailAddress) {
		if (!isEmpty(emailAddress))
			return isValid(emailAddress.getUrl());
		return false;
	}
	
	public static boolean isValid(String emailAddress) {
		Pattern pattern = Pattern.compile("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}
	
	public static boolean isEmpty(EmailAddress emailAddress) {
		if (emailAddress == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(emailAddress.getUrl());
		return status;
	}
	
	public static boolean isEmpty(Collection<EmailAddress> emailAddressList) {
		if (emailAddressList == null  || emailAddressList.size() == 0)
			return true;
		Iterator<EmailAddress> iterator = emailAddressList.iterator();
		while (iterator.hasNext()) {
			EmailAddress emailAddress = iterator.next();
			if (!isEmpty(emailAddress))
		return false;
	}
		return true;
	}

	public static String toString(EmailAddress emailAddress) {
		if (emailAddress == null)
			return "[uninitialized]";
		if (isEmpty(emailAddress))
			return "[uninitialized]";
		String text = emailAddress.getUrl();
		return text;
	}
	
	public static String toString(Collection<EmailAddress> emailAddressList) {
		if (isEmpty(emailAddressList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<EmailAddress> iterator = emailAddressList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailAddress emailAddress = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(emailAddress);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static EmailAddress create() {
		EmailAddress emailAddress = new EmailAddress();
		initialize(emailAddress);
		return emailAddress;
	}
	
	public static void initialize(EmailAddress emailAddress) {
		if (emailAddress.getEnabled() == null)
			emailAddress.setEnabled(true);
	}
	
	public static boolean validate(EmailAddress emailAddress) {
		if (emailAddress == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean emailAddressIsEmpty = StringUtils.isEmpty(emailAddress.getUrl());
		validator.isFalse(emailAddressIsEmpty, "\"Url\" must be specified");
		if (!emailAddressIsEmpty)
			PhoneNumberUtil.validate(emailAddress.getPhoneNumber());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<EmailAddress> emailAddressList) {
		Validator validator = Validator.getValidator();
		Iterator<EmailAddress> iterator = emailAddressList.iterator();
		while (iterator.hasNext()) {
			EmailAddress emailAddress = iterator.next();
			//TODO break or accumulate?
			validate(emailAddress);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<EmailAddress> emailAddressList) {
		Collections.sort(emailAddressList, createEmailAddressComparator());
	}
	
	public static Collection<EmailAddress> sortRecords(Collection<EmailAddress> emailAddressCollection) {
		List<EmailAddress> list = new ArrayList<EmailAddress>(emailAddressCollection);
		Collections.sort(list, createEmailAddressComparator());
		return list;
	}
	
	public static Comparator<EmailAddress> createEmailAddressComparator() {
		return new Comparator<EmailAddress>() {
			public int compare(EmailAddress emailAddress1, EmailAddress emailAddress2) {
				int status = emailAddress1.compareTo(emailAddress2);
				return status;
			}
		};
	}
	
	public static EmailAddress clone(EmailAddress emailAddress) {
		if (emailAddress == null)
			return null;
		EmailAddress clone = create();
		clone.setId(ObjectUtil.clone(emailAddress.getId()));
		clone.setUrl(ObjectUtil.clone(emailAddress.getUrl()));
		clone.setUserId(ObjectUtil.clone(emailAddress.getUserId()));
		clone.setFirstName(ObjectUtil.clone(emailAddress.getFirstName()));
		clone.setLastName(ObjectUtil.clone(emailAddress.getLastName()));
		clone.setPhoneNumber(PhoneNumberUtil.clone(emailAddress.getPhoneNumber()));
		clone.setOrganization(ObjectUtil.clone(emailAddress.getOrganization()));
		clone.setCreationDate(ObjectUtil.clone(emailAddress.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(emailAddress.getLastUpdate()));
		clone.setEnabled(ObjectUtil.clone(emailAddress.getEnabled()));
		return clone;
	}
	
	public static List<EmailAddress> clone(List<EmailAddress> emailAddressList) {
		if (emailAddressList == null)
			return null;
		List<EmailAddress> newList = new ArrayList<EmailAddress>();
		Iterator<EmailAddress> iterator = emailAddressList.iterator();
		while (iterator.hasNext()) {
			EmailAddress emailAddress = iterator.next();
			EmailAddress clone = clone(emailAddress);
			newList.add(clone);
		}
		return newList;
	}
	
}
