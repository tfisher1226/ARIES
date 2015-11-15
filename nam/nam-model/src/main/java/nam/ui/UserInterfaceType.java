package nam.ui;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "UserInterfaceType", namespace = "http://nam/ui")
@XmlEnum
public enum UserInterfaceType {
	
	WEB,
	
	MOBILE,
	
	TABLET,
	
	DESKTOP;
	
	
	public static UserInterfaceType fromValue(String name) {
		return valueOf(name);
	}
	
	public String value() {
		return name();
	}
	
}
