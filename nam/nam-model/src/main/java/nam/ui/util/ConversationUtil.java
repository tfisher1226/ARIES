package nam.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.util.ProcessUtil;
import nam.ui.Conversation;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ConversationUtil extends BaseUtil {
	
	public static Object getKey(Conversation conversation) {
		return conversation.getName();
	}
	
	public static String getLabel(Conversation conversation) {
		return conversation.getName();
	}
	
	public static boolean isEmpty(Conversation conversation) {
		if (conversation == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(conversation.getName());
		status |= conversation.getProcess() != null;
		return status;
	}
	
	public static boolean isEmpty(Collection<Conversation> conversationList) {
		if (conversationList == null  || conversationList.size() == 0)
			return true;
		Iterator<Conversation> iterator = conversationList.iterator();
		while (iterator.hasNext()) {
			Conversation conversation = iterator.next();
			if (!isEmpty(conversation))
				return false;
		}
		return true;
	}
	
	public static String toString(Conversation conversation) {
		if (isEmpty(conversation))
			return "Conversation: [uninitialized] "+conversation.toString();
		String text = conversation.toString();
		return text;
	}
	
	public static String toString(Collection<Conversation> conversationList) {
		if (isEmpty(conversationList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Conversation> iterator = conversationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Conversation conversation = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(conversation);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Conversation create() {
		Conversation conversation = new Conversation();
		initialize(conversation);
		return conversation;
	}
	
	public static void initialize(Conversation conversation) {
		//nothing for now
	}
	
	public static boolean validate(Conversation conversation) {
		if (conversation == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(conversation.getName(), "\"Name\" must be specified");
		validator.notNull(conversation.getProcess(), "\"Process\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Conversation> conversationList) {
		Validator validator = Validator.getValidator();
		Iterator<Conversation> iterator = conversationList.iterator();
		while (iterator.hasNext()) {
			Conversation conversation = iterator.next();
			//TODO break or accumulate?
			validate(conversation);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Conversation> conversationList) {
		Collections.sort(conversationList, createConversationComparator());
	}
	
	public static Collection<Conversation> sortRecords(Collection<Conversation> conversationCollection) {
		List<Conversation> list = new ArrayList<Conversation>(conversationCollection);
		Collections.sort(list, createConversationComparator());
		return list;
	}
	
	public static Comparator<Conversation> createConversationComparator() {
		return new Comparator<Conversation>() {
			public int compare(Conversation conversation1, Conversation conversation2) {
				Object key1 = getKey(conversation1);
				Object key2 = getKey(conversation2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Conversation clone(Conversation conversation) {
		if (conversation == null)
			return null;
		Conversation clone = create();
		clone.setName(ObjectUtil.clone(conversation.getName()));
		clone.setProcess(ProcessUtil.clone(conversation.getProcess()));
		return clone;
	}
	
	public static List<Conversation> clone(List<Conversation> conversationList) {
		if (conversationList == null)
			return null;
		List<Conversation> newList = new ArrayList<Conversation>();
		Iterator<Conversation> iterator = conversationList.iterator();
		while (iterator.hasNext()) {
			Conversation conversation = iterator.next();
			Conversation clone = clone(conversation);
			newList.add(clone);
		}
		return newList;
	}
	
}
