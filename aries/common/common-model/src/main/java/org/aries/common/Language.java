package org.aries.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Language", namespace = "http://www.aries.org/common")
@XmlEnum
public enum Language {
	
	ENGLISH,
	
	SPANISH,
	
	FRENCH,
	
	ITALIAN,
	
	GERMAN,
	
	PORTUGUESE,
	
	JAPANESE,
	
	CHINESE,
	
	KOREAN,
	
	VIETNAMESE,
	
	THAI,
	
	OTHER;
	
	
	public static Language fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
