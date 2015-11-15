package org.sgiusa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Generated by Nam.
 *
 */
@XmlType(name = "Division")
@XmlEnum
public enum Division {

	NONE,
	YWD,
	YMD,
	YD,
	WD,
	MD,
	ALL;

	public Division fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}