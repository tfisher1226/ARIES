package org.aries.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "State", namespace = "http://www.aries.org/common")
@XmlEnum
public enum State {
	
	AL,
	
	AK,
	
	AZ,
	
	AR,
	
	CA,
	
	CO,
	
	CT,
	
	DE,
	
	FL,
	
	GA,
	
	HI,
	
	ID,
	
	IL,
	
	IN,
	
	IA,
	
	KS,
	
	KY,
	
	LA,
	
	ME,
	
	MD,
	
	MA,
	
	MI,
	
	MN,
	
	MS,
	
	MO,
	
	MT,
	
	NE,
	
	NV,
	
	NH,
	
	NJ,
	
	NM,
	
	NY,
	
	NC,
	
	ND,
	
	OH,
	
	OK,
	
	OR,
	
	PA,
	
	RI,
	
	SC,
	
	SD,
	
	TN,
	
	TX,
	
	UT,
	
	VT,
	
	VA,
	
	WA,
	
	WV,
	
	WI,
	
	WY;
	
	
	public static State fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
