package admin.registration;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import admin.Registration;
import admin.util.RegistrationUtil;


public class RegistrationListObject extends AbstractListObject<Registration> implements Comparable<RegistrationListObject>, Serializable {
	
	private Registration registration;
	
	
	public RegistrationListObject(Registration registration) {
		this.registration = registration;
	}
	
	
	public Registration getRegistration() {
		return registration;
	}
	
	@Override
	public Object getKey() {
		return getKey(registration);
	}
	
	public Object getKey(Registration registration) {
		return RegistrationUtil.getKey(registration);
	}
	
	@Override
	public String getLabel() {
		return getLabel(registration);
	}
	
	public String getLabel(Registration registration) {
		return RegistrationUtil.getLabel(registration);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Registration16.gif";
	}
	
	@Override
	public String toString() {
		return toString(registration);
	}
	
	@Override
	public String toString(Registration registration) {
		return RegistrationUtil.toString(registration);
	}
	
	@Override
	public int compareTo(RegistrationListObject other) {
		Object thisKey = getKey(this.registration);
		Object otherKey = getKey(other.registration);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		RegistrationListObject other = (RegistrationListObject) object;
		Object thisKey = RegistrationUtil.getKey(this.registration);
		Object otherKey = RegistrationUtil.getKey(other.registration);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
