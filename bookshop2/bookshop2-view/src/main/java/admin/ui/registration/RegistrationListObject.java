package admin.ui.registration;

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
	public String getLabel() {
		return toString(registration);
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
		String thisText = toString(this.registration);
		String otherText = toString(other.registration);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		RegistrationListObject other = (RegistrationListObject) object;
		Long thisId = this.registration.getId();
		Long otherId = other.registration.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}
	
}
