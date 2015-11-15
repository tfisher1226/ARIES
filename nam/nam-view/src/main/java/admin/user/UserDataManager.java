package admin.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.User;
import admin.util.UserUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("userDataManager")
public class UserDataManager implements Serializable {
	
	@Inject
	private UserEventManager userEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<User> getUserList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<User> getDefaultList() {
		return null;
	}
	
	public void saveUser(User user) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeUser(User user) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
