package org.sgiusa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Generated by Nam.
 *
 */
@XmlType(name = "UserOperation")
@XmlEnum
public enum UserOperation {

	ALL,
	NONE,
	READ,
	CREATE,
	UPDATE,
	DELETE,
	PRINT,
	EMAIL;

	public UserOperation fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}