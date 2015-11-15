package admin;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Action", namespace = "http://admin")
@XmlEnum
public enum Action {
	
	ALL,
	
	NONE,
	
	READ,
	
	CREATE,
	
	UPDATE,
	
	DELETE,
	
	EXPORT,
	
	EMAIL,
	
	PRINT;
	
	
	public static Action fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
