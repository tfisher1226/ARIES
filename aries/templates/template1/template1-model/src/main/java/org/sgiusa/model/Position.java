package org.sgiusa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Generated by Nam.
 *
 */
@XmlType(name = "Position")
@XmlEnum
public enum Position {

	LEADER,
	VICELEADER,
	GUIDANCE,
	ADVISOR,
	SENIORVICELEADER,
	GENERALDIRECTOR,
	VICEGENERALDIRECTOR,
	SENIORVICEGENERALDIRECTOR,
	SOKASPIRITCOORDINATOR,
	CULTUREDEPTCOORDINATOR,
	MEMBERCAREADVISOR,
	MEMBERSHIPSTATISTICSADMINISTRATOR,
	MEMBERSHIPDATABASEADMINISTRATOR,
	PUBLICATIONSREPRESENTATIVE;

	public Position fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}