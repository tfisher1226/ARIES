package admin.ui.user;

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
	public String getLabel() {
		return toString(user);
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
		String thisText = toString(this.user);
		String otherText = toString(other.user);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		UserListObject other = (UserListObject) object;
		Long thisId = this.user.getId();
		Long otherId = other.user.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}

}
