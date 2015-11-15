package org.sgiusa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Generated by Nam.
 *
 */
@XmlType(name = "OrganizationLevel")
@XmlEnum
public enum OrganizationLevel {

	SGIUSA,
	TEAM,
	ZONE,
	REGION,
	AREA,
	CHAPTER,
	DISTRICT,
	GROUP,
	UNIT;

	public OrganizationLevel fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}