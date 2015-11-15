package org.aries.message.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aries.message.Fault;
import org.aries.message.Message;
import org.aries.message.Message.Parts;
import org.aries.message.Message.Parts.Entry;


public class MessageUtil {

	public static void addPart(Message message, String key, Object value) {
		Entry entry = new Entry();
		entry.setKey(key);
		entry.setValue(value);
		getParts(message).getEntries().add(entry);
	}
	
	public static Parts getParts(Message message) {
		Parts parts = message.getParts();
		if (parts == null) {
			parts = new Parts();
			message.setParts(parts);
		}
		return parts;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getPart(Message message, String key) {
		List<Entry> list = message.getParts().getEntries();
		Iterator<Entry> iterator = list.iterator();
		while (iterator.hasNext()) {
			Message.Parts.Entry entry = (Message.Parts.Entry) iterator.next();
			if (entry.getKey().equals(key))
				return (T) entry.getValue();
		}
		return null;
	}

	public static Map<String, Object> getPartMap(Message message) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Entry> list = getParts(message).getEntries();
		Iterator<Entry> iterator = list.iterator();
		while (iterator.hasNext()) {
			Message.Parts.Entry entry = (Message.Parts.Entry) iterator.next();
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	
	/*
	 * JAXB related helper methods
	 * ---------------------------
	 */
	
	public static List<Class<?>> getClassesFromMessage(Message message) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		List<Message.Parts.Entry> entries = message.getParts().getEntries();
		classes.add(Message.class);
		classes.add(Fault.class);
		Iterator<Message.Parts.Entry> iterator = entries.iterator();
		for (int i=1; iterator.hasNext(); i++) {
			Message.Parts.Entry entry = (Message.Parts.Entry) iterator.next();
			Class<?> classObject = entry.getValue().getClass();
			classes.add(classObject); 
		}
		return classes;
	}
	
	public static boolean equals(Message message0, Message message1) {
		if (message0 == null || message1 == null)
			return false;
		if (!message0.getClass().isAssignableFrom(message1.getClass()))
			return false;
		if (message0.getParts() == null || message1.getParts() == null)
			return false;
		if (!partsEqual(message0, message1))
			return false;
		return true;
	}

	protected static boolean partsEqual(Message message0, Message message1) {
		return partsEqual(message1.getParts(), message1.getParts());
	}
	
	protected static boolean partsEqual(Parts parts0, Parts parts1) {
		if (parts0.getEntries().size() == parts1.getEntries().size()) {
			int count = parts0.getEntries().size();
			for (int i=0; i < count; i++) {
				Entry entry0 = parts0.getEntries().get(i);
				Entry entry1 = parts1.getEntries().get(i);
				if (!entry0.getKey().equals(entry1.getKey()))
					return false;
				if (!entry0.getValue().equals(entry1.getValue()))
					return false;
			}
			return true;
		}
		return false;
	}
	
}
