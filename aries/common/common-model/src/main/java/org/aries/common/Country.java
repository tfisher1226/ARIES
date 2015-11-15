package org.aries.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Country", namespace = "http://www.aries.org/common")
@XmlEnum
public enum Country {
	
	USA,
	
	CAN,
	
	MEX,
	
	PR;
	
	
	public static Country fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
