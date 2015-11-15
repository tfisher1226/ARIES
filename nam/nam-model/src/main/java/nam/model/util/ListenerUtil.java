package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Listener;
import nam.model.Listeners;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ListenerUtil extends BaseUtil {

	public static Object getKey(Listener listener) {
		return listener.getName() + ":" + listener.getChannel();
	}
	
	public static String getLabel(Listener listener) {
		return listener.getName();
	}
	
	public static boolean isEmpty(Listener listener) {
		if (listener == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Listener> listenerList) {
		if (listenerList == null  || listenerList.size() == 0)
			return true;
		Iterator<Listener> iterator = listenerList.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			if (!isEmpty(listener))
				return false;
		}
		return true;
	}
	
	public static String toString(Listener listener) {
		if (isEmpty(listener))
			return "Listener: [uninitialized] "+listener.toString();
		String text = listener.toString();
		return text;
	}
	
	public static String toString(Collection<Listener> listenerList) {
		if (isEmpty(listenerList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Listener> iterator = listenerList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Listener listener = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(listener);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Listener create() {
		Listener listener = new Listener();
		initialize(listener);
		return listener;
	}
	
	public static void initialize(Listener listener) {
		if (listener.getNoLocalReceipt() == null)
			listener.setNoLocalReceipt(false);
	}
	
	public static boolean validate(Listener listener) {
		if (listener == null)
			return false;
		Validator validator = Validator.getValidator();
		ChannelUtil.validate(listener.getExpiredChannel());
		ChannelUtil.validate(listener.getInvalidChannel());
		ChannelUtil.validate(listener.getReceiveChannel());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Listener> listenerList) {
		Validator validator = Validator.getValidator();
		Iterator<Listener> iterator = listenerList.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			//TODO break or accumulate?
			validate(listener);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Listener> listenerList) {
		Collections.sort(listenerList, createListenerComparator());
	}
	
	public static Collection<Listener> sortRecords(Collection<Listener> listenerCollection) {
		List<Listener> list = new ArrayList<Listener>(listenerCollection);
		Collections.sort(list, createListenerComparator());
		return list;
	}
	
	public static Comparator<Listener> createListenerComparator() {
		return new Comparator<Listener>() {
			public int compare(Listener listener1, Listener listener2) {
				Object key1 = getKey(listener1);
				Object key2 = getKey(listener2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Listener clone(Listener listener) {
		if (listener == null)
			return null;
		Listener clone = create();
		clone.setReceiveChannel(ChannelUtil.clone(listener.getReceiveChannel()));
		clone.setInvalidChannel(ChannelUtil.clone(listener.getInvalidChannel()));
		clone.setExpiredChannel(ChannelUtil.clone(listener.getExpiredChannel()));
		clone.setNoLocalReceipt(ObjectUtil.clone(listener.getNoLocalReceipt()));
		return clone;
	}
	
	public static List<Listener> clone(List<Listener> listenerList) {
		if (listenerList == null)
			return null;
		List<Listener> newList = new ArrayList<Listener>();
		Iterator<Listener> iterator = listenerList.iterator();
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			Listener clone = clone(listener);
			newList.add(clone);
		}
		return newList;
	}


	public static Map<String, Listener> createListenerMap(Listeners listeners) {
		if (listeners == null)
			return new HashMap<String, Listener>();
		return createListenerMap(listeners.getListeners());
	}

	public static Map<String, Listener> createListenerMap(List<Listener> listeners) {
		Map<String, Listener> map = new HashMap<String, Listener>();
		Iterator<Listener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			Listener listener = (Listener) iterator.next();
			String name = listener.getLink();
			if (name != null)
				map.put(name, listener);
		}
		return map;
	}

	public static void mergeListeners(Listener listener1, Listener listener2) {
		listener1.setAsynchronous(listener2.getAsynchronous());
		listener1.setChannel(listener2.getChannel());
		listener1.setDeliveryMode(listener2.getDeliveryMode());
		listener1.setDurable(listener2.getDurable());
		listener1.setExpiredChannel(listener2.getExpiredChannel());
		listener1.setInteraction(listener2.getInteraction());
		listener1.setInvalid(listener2.getInvalid());
		listener1.setInvalidChannel(listener2.getInvalidChannel());
		listener1.setLink(listener2.getLink());
		listener1.setName(listener2.getName());
		listener1.setNoLocalReceipt(listener2.getNoLocalReceipt());
		listener1.setPriority(listener2.getPriority());
		listener1.setReceiveChannel(listener2.getReceiveChannel());
		listener1.setRole(listener2.getRole());
		listener1.setService(listener2.getService());
		listener1.setTarget(listener2.getTarget());
		listener1.setTimeout(listener2.getTimeout());
		listener1.setTimeToLive(listener2.getTimeToLive());
		listener1.setTransacted(listener2.getTransacted());
		listener1.setRef(null);
	}

}
