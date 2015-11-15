package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.Attachment;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class AttachmentUtil extends BaseUtil {
	
	public static boolean isEmpty(Attachment attachment) {
		if (attachment == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Attachment> attachmentList) {
		if (attachmentList == null  || attachmentList.size() == 0)
			return true;
		Iterator<Attachment> iterator = attachmentList.iterator();
		while (iterator.hasNext()) {
			Attachment attachment = iterator.next();
			if (!isEmpty(attachment))
				return false;
		}
		return true;
	}
	
	public static String toString(Attachment attachment) {
		if (isEmpty(attachment))
			return "Attachment: [uninitialized] "+attachment.toString();
		String text = attachment.toString();
		return text;
	}
	
	public static String toString(Collection<Attachment> attachmentList) {
		if (isEmpty(attachmentList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Attachment> iterator = attachmentList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Attachment attachment = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(attachment);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Attachment create() {
		Attachment attachment = new Attachment();
		initialize(attachment);
		return attachment;
	}
	
	public static void initialize(Attachment attachment) {
		//nothing for now
	}
	
	public static boolean validate(Attachment attachment) {
		if (attachment == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Attachment> attachmentList) {
		Validator validator = Validator.getValidator();
		Iterator<Attachment> iterator = attachmentList.iterator();
		while (iterator.hasNext()) {
			Attachment attachment = iterator.next();
			//TODO break or accumulate?
			validate(attachment);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Attachment> attachmentList) {
		Collections.sort(attachmentList, createAttachmentComparator());
	}
	
	public static Collection<Attachment> sortRecords(Collection<Attachment> attachmentCollection) {
		List<Attachment> list = new ArrayList<Attachment>(attachmentCollection);
		Collections.sort(list, createAttachmentComparator());
		return list;
	}
	
	public static Comparator<Attachment> createAttachmentComparator() {
		return new Comparator<Attachment>() {
			public int compare(Attachment attachment1, Attachment attachment2) {
				int status = attachment1.compareTo(attachment2);
				return status;
			}
		};
	}
	
	public static Attachment clone(Attachment attachment) {
		if (attachment == null)
			return null;
		Attachment clone = create();
		clone.setId(ObjectUtil.clone(attachment.getId()));
		clone.setName(ObjectUtil.clone(attachment.getName()));
		clone.setSize(ObjectUtil.clone(attachment.getSize()));
		clone.setFileName(ObjectUtil.clone(attachment.getFileName()));
		clone.setFileData(ObjectUtil.clone(attachment.getFileData()));
		clone.setContentType(ObjectUtil.clone(attachment.getContentType()));
		return clone;
	}
	
	public static List<Attachment> clone(List<Attachment> attachmentList) {
		if (attachmentList == null)
			return null;
		List<Attachment> newList = new ArrayList<Attachment>();
		Iterator<Attachment> iterator = attachmentList.iterator();
		while (iterator.hasNext()) {
			Attachment attachment = iterator.next();
			Attachment clone = clone(attachment);
			newList.add(clone);
		}
		return newList;
	}
	
}
