package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.common.EmailAccount;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class EmailAccountUtil extends BaseUtil {
	
	public static boolean isEmpty(EmailAccount emailAccount) {
		if (emailAccount == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(emailAccount.getUserId());
		status |= StringUtils.isEmpty(emailAccount.getPasswordHash());
		return status;
	}
	
	public static boolean isEmpty(Collection<EmailAccount> emailAccountList) {
		if (emailAccountList == null  || emailAccountList.size() == 0)
			return true;
		Iterator<EmailAccount> iterator = emailAccountList.iterator();
		while (iterator.hasNext()) {
			EmailAccount emailAccount = iterator.next();
			if (!isEmpty(emailAccount))
				return false;
		}
		return true;
	}
	
	public static String toString(EmailAccount emailAccount) {
		if (isEmpty(emailAccount))
			return "EmailAccount: [uninitialized] "+emailAccount.toString();
		String text = emailAccount.toString();
		return text;
	}
	
	public static String toString(Collection<EmailAccount> emailAccountList) {
		if (isEmpty(emailAccountList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<EmailAccount> iterator = emailAccountList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailAccount emailAccount = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(emailAccount);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static EmailAccount create() {
		EmailAccount emailAccount = new EmailAccount();
		initialize(emailAccount);
		return emailAccount;
	}
	
	public static void initialize(EmailAccount emailAccount) {
		if (emailAccount.getEnabled() == null)
			emailAccount.setEnabled(false);
	}
	
	public static boolean validate(EmailAccount emailAccount) {
		if (emailAccount == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(emailAccount.getPasswordHash(), "\"PasswordHash\" must be specified");
		validator.notEmpty(emailAccount.getUserId(), "\"UserId\" must be specified");
		EmailBoxUtil.validate(emailAccount.getEmailBoxes());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<EmailAccount> emailAccountList) {
		Validator validator = Validator.getValidator();
		Iterator<EmailAccount> iterator = emailAccountList.iterator();
		while (iterator.hasNext()) {
			EmailAccount emailAccount = iterator.next();
			//TODO break or accumulate?
			validate(emailAccount);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<EmailAccount> emailAccountList) {
		Collections.sort(emailAccountList, createEmailAccountComparator());
	}
	
	public static Collection<EmailAccount> sortRecords(Collection<EmailAccount> emailAccountCollection) {
		List<EmailAccount> list = new ArrayList<EmailAccount>(emailAccountCollection);
		Collections.sort(list, createEmailAccountComparator());
		return list;
	}
	
	public static void sortRecordsByUserId(List<EmailAccount> emailAccountList) {
		Collections.sort(emailAccountList, new Comparator<EmailAccount>() {
			public int compare(EmailAccount emailAccount1, EmailAccount emailAccount2) {
				String text1 = emailAccount1.getUserId();
				String text2 = emailAccount2.getUserId();
				return text1.compareTo(text2);
			}
		});
	}
	
	public static Comparator<EmailAccount> createEmailAccountComparator() {
		return new Comparator<EmailAccount>() {
			public int compare(EmailAccount emailAccount1, EmailAccount emailAccount2) {
				int status = emailAccount1.compareTo(emailAccount2);
				return status;
			}
		};
	}
	
	public static EmailAccount clone(EmailAccount emailAccount) {
		if (emailAccount == null)
			return null;
		EmailAccount clone = create();
		clone.setId(ObjectUtil.clone(emailAccount.getId()));
		clone.setUserId(ObjectUtil.clone(emailAccount.getUserId()));
		clone.setPasswordHash(ObjectUtil.clone(emailAccount.getPasswordHash()));
		clone.setPasswordSalt(ObjectUtil.clone(emailAccount.getPasswordSalt()));
		clone.setFirstName(ObjectUtil.clone(emailAccount.getFirstName()));
		clone.setLastName(ObjectUtil.clone(emailAccount.getLastName()));
		clone.setEmailBoxes(EmailBoxUtil.clone(emailAccount.getEmailBoxes()));
		clone.setEnabled(ObjectUtil.clone(emailAccount.getEnabled()));
		return clone;
	}
	
}
