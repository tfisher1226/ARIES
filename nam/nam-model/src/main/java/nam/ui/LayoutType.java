package nam.ui;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "LayoutType", namespace = "http://nam/ui")
@XmlEnum
public enum LayoutType {
	
	@XmlEnumValue("Type01")
	TYPE01("Type01");
	
	
	private final String value;
	
	
	private LayoutType(String value) {
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}
	
	public static LayoutType fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
