package nam.ui.userInterfaceType;

import java.io.Serializable;

import nam.ui.UserInterfaceType;
import nam.ui.util.UserInterfaceTypeUtil;

import org.aries.ui.AbstractListObject;


public class UserInterfaceTypeListObject extends AbstractListObject<UserInterfaceType> implements Comparable<UserInterfaceTypeListObject>, Serializable {
	
	private UserInterfaceType userInterfaceType;
	
	
	public UserInterfaceTypeListObject(UserInterfaceType userInterfaceType) {
		this.userInterfaceType = userInterfaceType;
	}
	
	
	public UserInterfaceType getUserInterfaceType() {
		return userInterfaceType;
	}
	
//	@Override
//	public Object getKey() {
//		return getKey(userInterfaceType);
//	}
	
//	public Object getKey(UserInterfaceType userInterfaceType) {
//		return UserInterfaceTypeUtil.getKey(userInterfaceType);
//	}
	
//	@Override
//	public String getLabel() {
//		return getLabel(userInterfaceType);
//	}
	
//	public String getLabel(UserInterfaceType userInterfaceType) {
//		return UserInterfaceTypeUtil.getLabel(userInterfaceType);
//	}
	
	@Override
	public String toString() {
		return toString(userInterfaceType);
	}
	
	@Override
	public String toString(UserInterfaceType userInterfaceType) {
		return userInterfaceType.name();
	}
	
	@Override
	public int compareTo(UserInterfaceTypeListObject other) {
		String thisText = this.userInterfaceType.name();
		String otherText = other.userInterfaceType.name();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		UserInterfaceTypeListObject other = (UserInterfaceTypeListObject) object;
		String thisText = toString(this.userInterfaceType);
		String otherText = toString(other.userInterfaceType);
		return thisText.equals(otherText);
	}
	
}
