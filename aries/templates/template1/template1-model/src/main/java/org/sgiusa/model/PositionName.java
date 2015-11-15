package org.sgiusa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Generated by Nam.
 *
 */
@XmlType(name = "PositionName")
@XmlEnum
public enum PositionName {

	Leader,
	ViceLeader,
	Guidance,
	Advisor,
	SeniorViceLeader,
	GeneralDirector,
	ViceGeneralDirector,
	SeniorViceGeneralDirector,
	SokaSpiritCoordinator,
	CultureDeptCoordinator,
	MemberCareAdvisor,
	MembershipStatisticsAdministrator,
	MembershipDatabaseAdministrator,
	PublicationsRepresentative;

	public PositionName fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}