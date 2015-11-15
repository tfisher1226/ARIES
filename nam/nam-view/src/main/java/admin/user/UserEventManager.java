package admin.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.User;

import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("userEventManager")
public class UserEventManager extends AbstractEventManager<User> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public User getInstance() {
		return selectionContext.getSelection("user");
	}
	
	public void removeUser() {
		User user = getInstance();
		fireRemoveEvent(user);
	}
	
}
