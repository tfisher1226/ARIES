package org.aries.message.util;

import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aries.message.Message;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class MessageFactory {

	private static final String DEFAULT_PART = "defaultPart";
	
	private static MessageFactory instance = new MessageFactory();
	
	public static MessageFactory getInstance() {
		return instance;
	}
	
	private MessageFactory() {
		//nothing for now
	}
	
	
	/*
	 * CREATING MESSAGES
	 * -----------------
	 */

	public Message createMessage() {
		Message message = new Message();
		return message;
	}

	//payload assumed to be serializable
	public <T> Message createXMLMessage(String key, T value) {
		Message message = createMessage(key, toXML(value));
		return message;
	}

	//payload assumed to be serializable
	public <T extends Serializable> Message createMessage(String key, T value) {
		Message message = createMessage();
		addToMessage(message, key, value);
		return message;
	}

	//payload assumed to be serializable
	public <T> Message createXMLMessage(Message incomingMessage, String key, T value) throws Exception {
		Message outgoingMessage = createMessage(incomingMessage, key, toXML(value));
		return outgoingMessage;
	}

	//payload assumed to be serializable
	public <T extends Serializable> Message createMessage(Message incomingMessage, String key, T value) throws Exception {
		Message outgoingMessage = createMessage(incomingMessage);
		addToMessage(outgoingMessage, key, value);
		return outgoingMessage;
	}

	//payload assumed to be serializable
	public <T> Message createXMLMessage(Message incomingMessage, String key, List<T> value) throws Exception {
		Message outgoingMessage = createMessage(incomingMessage, key, toXML(value));
		return outgoingMessage;
	}

	public Message createMessage(Message incomingMessage) throws Exception {
		URI messageID = new URI(incomingMessage.getHeader().getMessageId());
		URI correlationID = new URI(incomingMessage.getHeader().getCorrelationId());
		Message outgoingMessage = createMessage();
		outgoingMessage.getHeader().setMessageId(messageID.toASCIIString());
		outgoingMessage.getHeader().setCorrelationId(correlationID.toASCIIString());
		return outgoingMessage;
	}

		
	/*
	 * WRITING MESSAGES
	 * -------------------
	 */

	//payload assumed to be serializable
	public <T extends Serializable> void addPart(Message message, String key, T value) {
		String xml = toXML(value);
		addToMessage(message, key, xml);
	}

	//payload assumed to be serializable
	public <T extends Serializable> void addToMessage(Message message, String key, T value) {
		@SuppressWarnings("unchecked") HashMap<String, T> map = (HashMap<String, T>) MessageUtil.getPart(message, DEFAULT_PART);
		if (map == null) {
			map = new HashMap<String, T>();
			MessageUtil.addPart(message, DEFAULT_PART, value);
		}
		map.put(key, value);
	}

	
	/*
	 * READING MESSAGES
	 * -------------------
	 */
	
	public <T> T getPart(Message message, String key) {
		String xml = getFromMessage(message, key);
		@SuppressWarnings("unchecked") T object = (T) toObject(xml);
		return object;
	}

	public <T> T getFromMessage(Message message) {
		@SuppressWarnings("unchecked") T object = (T) MessageUtil.getPart(message, DEFAULT_PART);
		return object;
	}
	
	public <T> T getFromMessage(Message message, String key) {
		@SuppressWarnings("unchecked") T object = (T) MessageUtil.getPart(message, key);
		return object;
	}

	public <T> String toXML(T object) {
		XStream xstream = new XStream();
		String xml = xstream.toXML(object);
		return xml;
	}

	public <T> String toXML(List<T> list) {
		XStream xstream = new XStream();
		String xml = xstream.toXML(list);
		return xml;
	}

	public <T> T toObject(String xml) {
		XStream xstream = new XStream(new DomDriver());
		@SuppressWarnings("unchecked") T object = (T) xstream.fromXML(xml);
		return object;
	}

	public <T> List<T> toObjectList(String xml) {
		XStream xstream = new XStream(new DomDriver());
		@SuppressWarnings("unchecked") List<T> list = (List<T>) xstream.fromXML(xml);
		return list;
	}

	
	public <T> T getFromMessageOLD(Message message, String key) {
		String xml = getXMLFromMessageOLD(message, key);
		@SuppressWarnings("unchecked") T object = (T) toObject(xml);
		return object;
	}

	public String getXMLFromMessageOLD(Message message, String partName) {
		Object object = getFromMessage(message, DEFAULT_PART);
		@SuppressWarnings("unchecked") Map<String, String> map = (Map<String, String>) object;
		if (map == null)
			return null;
		String xml = map.get(partName);
		return xml;
	}

}
