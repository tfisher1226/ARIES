package org.sgiusa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Generated by Nam.
 *
 */
@XmlType(name = "GohonzonType")
@XmlEnum
public enum GohonzonType {

	REGULAR,
	SMALL,
	LARGE,
	FAMILY,
	OMOMORI,
	OKATAGI;

	public GohonzonType fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}