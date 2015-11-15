package admin.user;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import admin.User;
import admin.util.UserUtil;


public class UserListObject extends AbstractListObject<User> implements Comparable<UserListObject>, Serializable {
	
	private User user;
	
	
	public UserListObject(User user) {
		this.user = user;
	}

	
	public User getUser() {
		return user;
	}
	
	@Override
	public Object getKey() {
		return getKey(user);
	}
	
	public Object getKey(User user) {
		return UserUtil.getKey(user);
	}
	
	@Override
	public String getLabel() {
		return getLabel(user);
	}
	
	public String getLabel(User user) {
		return UserUtil.getLabel(user);
	}
	
	@Override
	public String toString() {
		return toString(user);
	}
	
	@Override
	public String toString(User user) {
		return UserUtil.toString(user);
	}
	
	@Override
	public int compareTo(UserListObject other) {
		Object thisKey = getKey(this.user);
		Object otherKey = getKey(other.user);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		UserListObject other = (UserListObject) object;
		Object thisKey = UserUtil.getKey(this.user);
		Object otherKey = UserUtil.getKey(other.user);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}

}
