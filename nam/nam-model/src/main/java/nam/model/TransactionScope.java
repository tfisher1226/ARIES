package nam.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TransactionScope", namespace = "http://nam/model")
@XmlEnum
public enum TransactionScope {

    @XmlEnumValue("event")
    EVENT("event"),
	
    @XmlEnumValue("method")
    METHOD("method"),
	
    @XmlEnumValue("thread")
    THREAD("thread"),
	
    @XmlEnumValue("process")
    PROCESS("process"),
	
	@XmlEnumValue("conversation")
	CONVERSATION("conversation");
	
	
    private final String value;

	
	private TransactionScope(String value) {
		this.value = value;
    }

	
	public String getValue() {
        return value;
    }

	public static TransactionScope fromValue(String name) {
		return valueOf(name);
            }
	
	public String value() {
		return name();
    }

}
