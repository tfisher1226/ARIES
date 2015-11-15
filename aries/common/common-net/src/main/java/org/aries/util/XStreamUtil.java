package org.aries.util;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class XStreamUtil {

	public static <T> String toXML(T object) {
		XStream xstream = new XStream();
		String xml = xstream.toXML(object);
		return xml;
	}

	public static <T> String toXML(List<T> list) {
		XStream xstream = new XStream();
		String xml = xstream.toXML(list);
		return xml;
	}

	public static <T> T toObject(String xml) {
		XStream xstream = new XStream(new DomDriver());
		@SuppressWarnings("unchecked") T object = (T) xstream.fromXML(xml);
		return object;
	}

	public static <T> List<T> toObjectList(String xml) {
		XStream xstream = new XStream(new DomDriver());
		@SuppressWarnings("unchecked") List<T> list = (List<T>) xstream.fromXML(xml);
		return list;
	}
	
}
