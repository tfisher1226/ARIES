package nam.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TransportType", namespace = "http://nam/model")
@XmlEnum
public enum TransportType {

	@XmlEnumValue("RMI")
	RMI("RMI"),

	@XmlEnumValue("EJB")
	EJB("EJB"),
	
	@XmlEnumValue("HTTP")
	HTTP("HTTP"),
	
	@XmlEnumValue("JMS")
	JMS("JMS");
	
	
	private final String value;
	
	
	private TransportType(String value) {
		this.value = value;
    }

	
	public String getValue() {
		return value;
	}
	
	public static TransportType fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
    }

}
