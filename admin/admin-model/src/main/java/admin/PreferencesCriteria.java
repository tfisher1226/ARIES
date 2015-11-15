package admin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PreferencesCriteria", namespace = "http://admin", propOrder = {
	"userName",
	"themeId"
})
@XmlRootElement(name = "preferencesCriteria", namespace = "http://admin")
public class PreferencesCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "userName", namespace = "http://admin", nillable = true)
	private String userName;
	
	@XmlElement(name = "themeId", namespace = "http://admin", nillable = true)
	private String themeId;
	
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getThemeId() {
		return themeId;
	}
	
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		PreferencesCriteria other = (PreferencesCriteria) object;
		return this == object;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
}
