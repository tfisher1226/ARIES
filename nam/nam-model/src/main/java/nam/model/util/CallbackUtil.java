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

import nam.model.Callback;


public class CallbackUtil extends BaseUtil {
	
	public static String getKey(Callback callback) {
		return ServiceUtil.getKey(callback);
	}
	
	public static String getLabel(Callback callback) {
		return ServiceUtil.getLabel(callback);
	}
	
	public static boolean getLabel(Collection<Callback> callbackList) {
		if (callbackList == null  || callbackList.size() == 0)
			return true;
		Iterator<Callback> iterator = callbackList.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			if (!isEmpty(callback))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Callback callback) {
		if (callback == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Callback> callbackList) {
		if (callbackList == null  || callbackList.size() == 0)
			return true;
		Iterator<Callback> iterator = callbackList.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			if (!isEmpty(callback))
				return false;
		}
		return true;
	}
	
	public static String toString(Callback callback) {
		if (isEmpty(callback))
			return "Callback: [uninitialized] "+callback.toString();
		String text = callback.toString();
		return text;
	}
	
	public static String toString(Collection<Callback> callbackList) {
		if (isEmpty(callbackList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Callback> iterator = callbackList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Callback callback = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(callback);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Callback create() {
		Callback callback = new Callback();
		initialize(callback);
		return callback;
	}
	
	public static void initialize(Callback callback) {
		//nothing for now
	}
	
	public static boolean validate(Callback callback) {
		if (callback == null)
			return false;
		Validator validator = Validator.getValidator();
		ServiceUtil.validate(callback.getReceivingService());
		ServiceUtil.validate(callback.getSendingService());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Callback> callbackList) {
		Validator validator = Validator.getValidator();
		Iterator<Callback> iterator = callbackList.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			//TODO break or accumulate?
			validate(callback);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Callback> callbackList) {
		Collections.sort(callbackList, createCallbackComparator());
	}
	
	public static Collection<Callback> sortRecords(Collection<Callback> callbackCollection) {
		List<Callback> list = new ArrayList<Callback>(callbackCollection);
		Collections.sort(list, createCallbackComparator());
		return list;
	}
	
	public static Comparator<Callback> createCallbackComparator() {
		return new Comparator<Callback>() {
			public int compare(Callback callback1, Callback callback2) {
				Object key1 = getKey(callback1);
				Object key2 = getKey(callback2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Callback clone(Callback callback) {
		if (callback == null)
			return null;
		Callback clone = create();
		clone.setDirection(callback.getDirection());
		clone.setSendingService(ServiceUtil.clone(callback.getSendingService()));
		clone.setReceivingService(ServiceUtil.clone(callback.getReceivingService()));
		return clone;
	}
	
}
