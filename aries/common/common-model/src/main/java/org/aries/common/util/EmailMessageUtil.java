package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.EmailMessage;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class EmailMessageUtil extends BaseUtil {
	
	public static boolean isEmpty(EmailMessage emailMessage) {
		if (emailMessage == null)
			return true;
		boolean status = false;
		status |= EmailAddressUtil.isEmpty(emailMessage.getFromAddress());
		return status;
	}
	
	public static boolean isEmpty(Collection<EmailMessage> emailMessageList) {
		if (emailMessageList == null  || emailMessageList.size() == 0)
			return true;
		Iterator<EmailMessage> iterator = emailMessageList.iterator();
		while (iterator.hasNext()) {
			EmailMessage emailMessage = iterator.next();
			if (!isEmpty(emailMessage))
				return false;
		}
		return true;
	}
	
	public static String toString(EmailMessage emailMessage) {
		if (isEmpty(emailMessage))
			return "EmailMessage: [uninitialized] "+emailMessage.toString();
		String text = emailMessage.toString();
		return text;
	}
	
	public static String toString(Collection<EmailMessage> emailMessageList) {
		if (isEmpty(emailMessageList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<EmailMessage> iterator = emailMessageList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailMessage emailMessage = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(emailMessage);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static EmailMessage create() {
		EmailMessage emailMessage = new EmailMessage();
		initialize(emailMessage);
		return emailMessage;
	}
	
	public static void initialize(EmailMessage emailMessage) {
		if (emailMessage.getSendAsHtml() == null)
			emailMessage.setSendAsHtml(false);
	}
	
	public static boolean validate(EmailMessage emailMessage) {
		if (emailMessage == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.isFalse(EmailAddressUtil.isEmpty(emailMessage.getFromAddress()), "\"FromAddress\" must be specified");
		EmailAddressListUtil.validate(emailMessage.getAdminAddresses());
		AttachmentUtil.validate(emailMessage.getAttachments());
		EmailAddressListUtil.validate(emailMessage.getBccAddresses());
		EmailAddressListUtil.validate(emailMessage.getCcAddresses());
		EmailAddressUtil.validate(emailMessage.getFromAddress());
		EmailAddressListUtil.validate(emailMessage.getReplytoAddresses());
		EmailAddressListUtil.validate(emailMessage.getToAddresses());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<EmailMessage> emailMessageList) {
		Validator validator = Validator.getValidator();
		Iterator<EmailMessage> iterator = emailMessageList.iterator();
		while (iterator.hasNext()) {
			EmailMessage emailMessage = iterator.next();
			//TODO break or accumulate?
			validate(emailMessage);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<EmailMessage> emailMessageList) {
		Collections.sort(emailMessageList, createEmailMessageComparator());
	}
	
	public static Collection<EmailMessage> sortRecords(Collection<EmailMessage> emailMessageCollection) {
		List<EmailMessage> list = new ArrayList<EmailMessage>(emailMessageCollection);
		Collections.sort(list, createEmailMessageComparator());
		return list;
	}
	
	public static Comparator<EmailMessage> createEmailMessageComparator() {
		return new Comparator<EmailMessage>() {
			public int compare(EmailMessage emailMessage1, EmailMessage emailMessage2) {
				int status = emailMessage1.compareTo(emailMessage2);
				return status;
			}
		};
	}
	
	public static EmailMessage clone(EmailMessage emailMessage) {
		if (emailMessage == null)
			return null;
		EmailMessage clone = create();
		clone.setId(ObjectUtil.clone(emailMessage.getId()));
		clone.setContent(ObjectUtil.clone(emailMessage.getContent()));
		clone.setSubject(ObjectUtil.clone(emailMessage.getSubject()));
		clone.setTimestamp(ObjectUtil.clone(emailMessage.getTimestamp()));
		clone.setSourceId(ObjectUtil.clone(emailMessage.getSourceId()));
		clone.setSmtpHost(ObjectUtil.clone(emailMessage.getSmtpHost()));
		clone.setSmtpPort(ObjectUtil.clone(emailMessage.getSmtpPort()));
		clone.setFromAddress(EmailAddressUtil.clone(emailMessage.getFromAddress()));
		clone.setToAddresses(EmailAddressListUtil.clone(emailMessage.getToAddresses()));
		clone.setBccAddresses(EmailAddressListUtil.clone(emailMessage.getBccAddresses()));
		clone.setCcAddresses(EmailAddressListUtil.clone(emailMessage.getCcAddresses()));
		clone.setReplytoAddresses(EmailAddressListUtil.clone(emailMessage.getReplytoAddresses()));
		clone.setAdminAddresses(EmailAddressListUtil.clone(emailMessage.getAdminAddresses()));
		clone.setAttachments(AttachmentUtil.clone(emailMessage.getAttachments()));
		clone.setSendAsHtml(ObjectUtil.clone(emailMessage.getSendAsHtml()));
		return clone;
	}
	
	public static List<EmailMessage> clone(List<EmailMessage> emailMessageList) {
		if (emailMessageList == null)
			return null;
		List<EmailMessage> newList = new ArrayList<EmailMessage>();
		Iterator<EmailMessage> iterator = emailMessageList.iterator();
		while (iterator.hasNext()) {
			EmailMessage emailMessage = iterator.next();
			EmailMessage clone = clone(emailMessage);
			newList.add(clone);
		}
		return newList;
	}
	
}
