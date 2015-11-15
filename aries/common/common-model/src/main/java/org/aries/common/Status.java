package org.aries.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Status", namespace = "http://www.aries.org/common")
@XmlEnum
public enum Status {
	
	INFO,
	
	PROMPT,
	
	WARNING,
	
	ERROR;
	
	
	public static Status fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
