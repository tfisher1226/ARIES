package template1.user;

import java.io.Serializable;

import template1.model.User;


@SuppressWarnings("serial")
public class UserListObject implements Serializable, Comparable<UserListObject> {
	
	private User user;

	
	public UserListObject(User user) {
		this.user = user;
	}

	public User getRecord() {
		return this.user;
	}

	@Override
	public int compareTo(UserListObject other) {
		String thisId = user.getUserId();
		String otherId = other.user.getUserId();
		if (thisId == null)
			return -1;
		if (otherId == null)
			return 1;
		return thisId.compareTo(otherId);
	}

}
