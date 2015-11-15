package org.aries.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PhoneLocation", namespace = "http://www.aries.org/common")
@XmlEnum
public enum PhoneLocation {
	
	HOME,
	
	WORK,
	
	CELL,
	
	FAX,
	
	OTHER;
	
	
	public static PhoneLocation fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
