package org.aries.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Capability", namespace = "http://www.aries.org/common")
@XmlEnum
public enum Capability {
	
	ALL,
	
	NONE,
	
	READ,
	
	CREATE,
	
	UPDATE,
	
	DELETE,
	
	EXPORT,
	
	EMAIL,
	
	PRINT;
	
	
	public static Capability fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
