package nam.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TransactionUsage", namespace = "http://nam/model")
@XmlEnum
public enum TransactionUsage {

    @XmlEnumValue("mandatory")
    MANDATORY("mandatory"),
	
    @XmlEnumValue("supported")
    SUPPORTED("supported"),
	
    @XmlEnumValue("required")
    REQUIRED("required"),
	
    @XmlEnumValue("requiresNew")
    REQUIRES_NEW("requiresNew");
	
	
    private final String value;

	
	private TransactionUsage(String value) {
		this.value = value;
    }

	
	public String getValue() {
        return value;
    }

	public static TransactionUsage fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
    }

}
