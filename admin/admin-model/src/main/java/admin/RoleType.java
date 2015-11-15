package admin;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RoleType", namespace = "http://admin")
@XmlEnum
public enum RoleType {

	@XmlEnumValue("MANAGER")
	MANAGER("Manager"),

	@XmlEnumValue("USER")
	USER("User"),

	@XmlEnumValue("HOST")
	HOST("Host");


	private final String value;
	
	
	private RoleType(String value) {
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}
	
	public static RoleType fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}
