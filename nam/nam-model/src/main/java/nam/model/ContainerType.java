package nam.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ContainerType", namespace = "http://nam/model")
@XmlEnum
public enum ContainerType {
	
	@XmlEnumValue("Plain")
	PLAIN("Plain"),
	
	@XmlEnumValue("Persistence")
	PERSISTENCE("Persistence");
	
	
	private final String value;
	
	
	private ContainerType(String value) {
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}
	
	public static ContainerType fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
