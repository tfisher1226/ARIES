package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.EmailAccount;
import org.aries.common.EmailBox;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class EmailBoxUtil extends BaseUtil {
	
	public static boolean isEmpty(EmailBox emailBox) {
		if (emailBox == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<EmailBox> emailBoxList) {
		if (emailBoxList == null  || emailBoxList.size() == 0)
			return true;
		Iterator<EmailBox> iterator = emailBoxList.iterator();
		while (iterator.hasNext()) {
			EmailBox emailBox = iterator.next();
			if (!isEmpty(emailBox))
				return false;
		}
		return true;
	}
	
	public static String toString(EmailBox emailBox) {
		if (isEmpty(emailBox))
			return "EmailBox: [uninitialized] "+emailBox.toString();
		String text = emailBox.toString();
		return text;
	}
	
	public static String toString(Collection<EmailBox> emailBoxList) {
		if (isEmpty(emailBoxList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<EmailBox> iterator = emailBoxList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailBox emailBox = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(emailBox);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static EmailBox create() {
		EmailBox emailBox = new EmailBox();
		initialize(emailBox);
		return emailBox;
	}
	
	public static EmailBox create(EmailAccount emailAccount) {
		EmailBox emailBox = create();
		emailBox.setEmailAccount(emailAccount);
		return emailBox;
	}
	
	public static void initialize(EmailBox emailBox) {
		//nothing for now
	}
	
	public static boolean validate(EmailBox emailBox) {
		if (emailBox == null)
			return false;
		Validator validator = Validator.getValidator();
		EmailMessageUtil.validate(emailBox.getMessages());
		EmailBoxUtil.validate(emailBox.getParentBox());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<EmailBox> emailBoxList) {
		Validator validator = Validator.getValidator();
		Iterator<EmailBox> iterator = emailBoxList.iterator();
		while (iterator.hasNext()) {
			EmailBox emailBox = iterator.next();
			//TODO break or accumulate?
			validate(emailBox);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<EmailBox> emailBoxList) {
		Collections.sort(emailBoxList, createEmailBoxComparator());
	}
	
	public static Collection<EmailBox> sortRecords(Collection<EmailBox> emailBoxCollection) {
		List<EmailBox> list = new ArrayList<EmailBox>(emailBoxCollection);
		Collections.sort(list, createEmailBoxComparator());
		return list;
	}
	
	public static Comparator<EmailBox> createEmailBoxComparator() {
		return new Comparator<EmailBox>() {
			public int compare(EmailBox emailBox1, EmailBox emailBox2) {
				int status = emailBox1.compareTo(emailBox2);
				return status;
			}
		};
	}
	
	public static EmailBox clone(EmailBox emailBox) {
		if (emailBox == null)
			return null;
		EmailBox clone = create();
		clone.setId(ObjectUtil.clone(emailBox.getId()));
		clone.setType(ObjectUtil.clone(emailBox.getType()));
		clone.setName(ObjectUtil.clone(emailBox.getName()));
		clone.setEmailAccount(emailBox.getEmailAccount());
		clone.setParentBox(EmailBoxUtil.clone(emailBox.getParentBox()));
		clone.setMessages(EmailMessageUtil.clone(emailBox.getMessages()));
		clone.setCreationDate(ObjectUtil.clone(emailBox.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(emailBox.getLastUpdate()));
		return clone;
	}
	
	public static List<EmailBox> clone(List<EmailBox> emailBoxList) {
		if (emailBoxList == null)
			return null;
		List<EmailBox> newList = new ArrayList<EmailBox>();
		Iterator<EmailBox> iterator = emailBoxList.iterator();
		while (iterator.hasNext()) {
			EmailBox emailBox = iterator.next();
			EmailBox clone = clone(emailBox);
			newList.add(clone);
		}
		return newList;
	}
	
}
