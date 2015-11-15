package nam.ui.userInterface;

import java.io.Serializable;

import nam.model.Application;
import nam.ui.UserInterface;
import nam.ui.util.UserInterfaceUtil;

import org.aries.ui.AbstractListObject;


public class UserInterfaceListObject extends AbstractListObject<UserInterface> implements Comparable<UserInterfaceListObject>, Serializable {
	
	private Application application;
	
	private UserInterface userInterface;
	
	
	public UserInterfaceListObject(Application application, UserInterface userInterface) {
		this.application = application;
		this.userInterface = userInterface;
	}
	
	
	public Application getApplication() {
		return application;
	}
	
	public UserInterface getUserInterface() {
		return userInterface;
	}
	
	@Override
	public Object getKey() {
		return getKey(userInterface);
	}
	
	public Object getKey(UserInterface userInterface) {
		return UserInterfaceUtil.getKey(userInterface);
	}
	
	@Override
	public String getLabel() {
		return getLabel(userInterface);
	}
	
	public String getLabel(UserInterface userInterface) {
		return UserInterfaceUtil.getLabel(userInterface);
	}
	
	@Override
	public String toString() {
		return toString(userInterface);
	}
	
	@Override
	public String toString(UserInterface userInterface) {
		return UserInterfaceUtil.toString(userInterface);
	}
	
	@Override
	public int compareTo(UserInterfaceListObject other) {
		Object thisKey = getKey(this.userInterface);
		Object otherKey = getKey(other.userInterface);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		UserInterfaceListObject other = (UserInterfaceListObject) object;
		Object thisKey = UserInterfaceUtil.getKey(this.userInterface);
		Object otherKey = UserInterfaceUtil.getKey(other.userInterface);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
