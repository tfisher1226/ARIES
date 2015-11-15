package admin.event;

import admin.User;


public abstract class AbstractUserEvent {

	private User user;

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
