package nam.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TransferMode", namespace = "http://nam/model")
@XmlEnum
public enum TransferMode {

	@XmlEnumValue("Text")
	TEXT("Text"),

	@XmlEnumValue("Binary")
	BINARY("Binary");
	
	
	private final String value;
	
	
	private TransferMode(String value) {
		this.value = value;
    }

	
	public String getValue() {
		return value;
	}
	
	public static TransferMode fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
    }

}
