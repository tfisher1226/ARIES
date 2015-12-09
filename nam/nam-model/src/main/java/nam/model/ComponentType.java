package nam.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ComponentType", namespace = "http://nam/model")
@XmlEnum
public enum ComponentType {
	
	@XmlEnumValue("Cache")
	Cache("Cache"),
	
	@XmlEnumValue("Process")
	Process("Process"),
	
	@XmlEnumValue("Handler")
	Handler("Handler"),
	
	@XmlEnumValue("Scheduler")
	Scheduler("Scheduler"),
	
	@XmlEnumValue("Persistence")
	Persistence("Persistence"),
	
	@XmlEnumValue("Custom")
	Custom("Custom");
	
	
	private final String value;
	
	
	private ComponentType(String value) {
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}
	
	public static ComponentType fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
