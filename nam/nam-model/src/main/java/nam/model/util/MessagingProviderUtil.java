package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.MessagingProvider;
import nam.model.Module;
import nam.model.Service;


public class MessagingProviderUtil extends BaseUtil {
	
	public static Object getKey(MessagingProvider messagingProvider) {
		return messagingProvider.getName();
	}
	
	public static String getLabel(MessagingProvider messagingProvider) {
		return messagingProvider.getName();
	}
	
	public static boolean isEmpty(MessagingProvider messagingProvider) {
		if (messagingProvider == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<MessagingProvider> messagingProviderList) {
		if (messagingProviderList == null  || messagingProviderList.size() == 0)
			return true;
		Iterator<MessagingProvider> iterator = messagingProviderList.iterator();
		while (iterator.hasNext()) {
			MessagingProvider messagingProvider = iterator.next();
			if (!isEmpty(messagingProvider))
				return false;
		}
		return true;
	}
	
	public static String toString(MessagingProvider messagingProvider) {
		if (isEmpty(messagingProvider))
			return "MessagingProvider: [uninitialized] "+messagingProvider.toString();
		String text = messagingProvider.toString();
		return text;
	}
	
	public static String toString(Collection<MessagingProvider> messagingProviderList) {
		if (isEmpty(messagingProviderList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<MessagingProvider> iterator = messagingProviderList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			MessagingProvider messagingProvider = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(messagingProvider);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static MessagingProvider create() {
		MessagingProvider messagingProvider = new MessagingProvider();
		initialize(messagingProvider);
		return messagingProvider;
	}
	
	public static void initialize(MessagingProvider messagingProvider) {
		//nothing for now
	}
	
	public static boolean validate(MessagingProvider messagingProvider) {
		if (messagingProvider == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<MessagingProvider> messagingProviderList) {
		Validator validator = Validator.getValidator();
		Iterator<MessagingProvider> iterator = messagingProviderList.iterator();
		while (iterator.hasNext()) {
			MessagingProvider messagingProvider = iterator.next();
			//TODO break or accumulate?
			validate(messagingProvider);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}

	public static void sortRecords(List<MessagingProvider> messagingProviderList) {
		Collections.sort(messagingProviderList, createMessagingProviderComparator());
	}
	
	public static Collection<MessagingProvider> sortRecords(Collection<MessagingProvider> messagingProviderCollection) {
		List<MessagingProvider> list = new ArrayList<MessagingProvider>(messagingProviderCollection);
		Collections.sort(list, createMessagingProviderComparator());
		return list;
	}
	
	public static Comparator<MessagingProvider> createMessagingProviderComparator() {
		return new Comparator<MessagingProvider>() {
			public int compare(MessagingProvider messagingProvider1, MessagingProvider messagingProvider2) {
				Object key1 = getKey(messagingProvider1);
				Object key2 = getKey(messagingProvider2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static MessagingProvider clone(MessagingProvider messagingProvider) {
		if (messagingProvider == null)
			return null;
		MessagingProvider clone = create();
		return clone;
	}
	
}
