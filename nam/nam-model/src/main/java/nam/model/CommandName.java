package nam.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CommandName", namespace = "http://nam/model")
@XmlEnum
public enum CommandName {

	@XmlEnumValue("New")
	NEW("New"),
	
	@XmlEnumValue("Call")
	CALL("Call"),
	
	@XmlEnumValue("Send")
	SEND("Send"),
	
	@XmlEnumValue("Invoke")
	INVOKE("Invoke"),
	
	@XmlEnumValue("Listen")
	LISTEN("Listen"),
	
	@XmlEnumValue("Receive")
	RECEIVE("Receive"),
	
	@XmlEnumValue("Reply")
	REPLY("Reply"),
	
	@XmlEnumValue("Post")
	POST("Post"),
	
	@XmlEnumValue("Throw")
	THROW("Throw"),
	
	@XmlEnumValue("Execute")
	EXECUTE("Execute"),
	
	@XmlEnumValue("Expression")
	EXPRESSION("Expression"),
	
	@XmlEnumValue("Option")
	OPTION("Option"),
	
	@XmlEnumValue("Branch")
	BRANCH("Branch"),
	
	@XmlEnumValue("Call")
	DONE("Call");
	
	
    private final String value;

	
	private CommandName(String value) {
		this.value = value;
    }

	
	public String getValue() {
        return value;
    }

	public static CommandName fromValue(String name) {
		return valueOf(name);
            }
	
	public String value() {
		return name();
    }

}
