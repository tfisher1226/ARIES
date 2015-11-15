package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.EmailAddressList;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class EmailAddressListUtil extends BaseUtil {
	
	public static boolean isEmpty(EmailAddressList emailAddressList) {
		if (emailAddressList == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(emailAddressList.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<EmailAddressList> emailAddressListList) {
		if (emailAddressListList == null  || emailAddressListList.size() == 0)
			return true;
		Iterator<EmailAddressList> iterator = emailAddressListList.iterator();
		while (iterator.hasNext()) {
			EmailAddressList emailAddressList = iterator.next();
			if (!isEmpty(emailAddressList))
				return false;
		}
		return true;
	}
	
	public static String toString(EmailAddressList emailAddressList) {
		if (isEmpty(emailAddressList))
			return "EmailAddressList: [uninitialized] "+emailAddressList.toString();
		String text = emailAddressList.toString();
		return text;
	}
	
	public static String toString(Collection<EmailAddressList> emailAddressListList) {
		if (isEmpty(emailAddressListList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<EmailAddressList> iterator = emailAddressListList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailAddressList emailAddressList = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(emailAddressList);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static EmailAddressList create() {
		EmailAddressList emailAddressList = new EmailAddressList();
		initialize(emailAddressList);
		return emailAddressList;
	}
	
	public static void initialize(EmailAddressList emailAddressList) {
		//nothing for now
	}
	
	public static boolean validate(EmailAddressList emailAddressList) {
		if (emailAddressList == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(emailAddressList.getName(), "\"Name\" must be specified");
		EmailAddressUtil.validate(emailAddressList.getAddresses());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<EmailAddressList> emailAddressListList) {
		Validator validator = Validator.getValidator();
		Iterator<EmailAddressList> iterator = emailAddressListList.iterator();
		while (iterator.hasNext()) {
			EmailAddressList emailAddressList = iterator.next();
			//TODO break or accumulate?
			validate(emailAddressList);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<EmailAddressList> emailAddressListList) {
		Collections.sort(emailAddressListList, createEmailAddressListComparator());
	}
	
	public static Collection<EmailAddressList> sortRecords(Collection<EmailAddressList> emailAddressListCollection) {
		List<EmailAddressList> list = new ArrayList<EmailAddressList>(emailAddressListCollection);
		Collections.sort(list, createEmailAddressListComparator());
		return list;
	}
	
	public static Comparator<EmailAddressList> createEmailAddressListComparator() {
		return new Comparator<EmailAddressList>() {
			public int compare(EmailAddressList emailAddressList1, EmailAddressList emailAddressList2) {
				int status = emailAddressList1.compareTo(emailAddressList2);
				return status;
			}
		};
	}
	
	public static EmailAddressList clone(EmailAddressList emailAddressList) {
		if (emailAddressList == null)
			return null;
		EmailAddressList clone = create();
		clone.setId(ObjectUtil.clone(emailAddressList.getId()));
		clone.setName(ObjectUtil.clone(emailAddressList.getName()));
		clone.setAddresses(EmailAddressUtil.clone(emailAddressList.getAddresses()));
		return clone;
	}
	
	public static List<EmailAddressList> clone(List<EmailAddressList> emailAddressListList) {
		if (emailAddressListList == null)
			return null;
		List<EmailAddressList> newList = new ArrayList<EmailAddressList>();
		Iterator<EmailAddressList> iterator = emailAddressListList.iterator();
		while (iterator.hasNext()) {
			EmailAddressList emailAddressList = iterator.next();
			EmailAddressList clone = clone(emailAddressList);
			newList.add(clone);
		}
		return newList;
	}
	
}
