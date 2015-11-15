package admin;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RoleTypeName", namespace = "http://admin")
@XmlEnum
public enum RoleTypeName {

	@XmlEnumValue("Manager")
	MANAGER("Manager"),

	@XmlEnumValue("User")
	USER("User"),

	@XmlEnumValue("Host")
	HOST("Host");


	private final String value;


	private RoleTypeName(String value) {
		this.value = value;
	}


	public String getValue() {
		return value;
	}

	public static RoleTypeName fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}
