package ecos.ui;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Action;
import admin.Role;
import admin.RoleType;
import admin.User;


@AutoCreate
@BypassInterceptors
@Name("securityGuard")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class SecurityGuard implements Serializable {

	//@In(required = true)
	private Display display;

	private User selectedUserRecord;

	//@In(required = true)
	private PermissionResolver permissionResolver;


	public final User getUser() {
		User user = BeanContext.get("org.aries.user");
		return user;
	}

	public final String getDomain() {
		display = BeanContext.getFromSession("display");
		return display.getModule();
	}

	public boolean isCheckUser() {
		return checkRole(RoleType.USER);
	}

	public boolean isCheckHost() {
		return checkRole(RoleType.HOST);
	}

	public boolean isCheckManager() {
		return checkRole(RoleType.MANAGER);
	}

	public boolean isUser(String userId) {
		User user = getUser();
		return user != null && user.getUserName().equals(userId);
	}

	protected boolean checkRole(RoleType roleType) {
		User user = getUser();
		if (user == null)
			return false;
		boolean containsRole = containsRole(user.getRoles(), roleType);
		return containsRole;
	}
	
	protected boolean containsRole(Collection<Role> roles, RoleType roleType) {
		Iterator<Role> iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			if (role.getRoleType() == roleType)
				return true;
		}
		return false;
	}
	
	
	public boolean canRead(String element) {
		return resolve(element, Action.READ);
	}

	//TODO add OPEN to Actions
	public boolean canOpen(String element) {
		return resolve(element, Action.READ);
	}

	public boolean canCreate(String element) {
		return resolve(element, Action.CREATE);
	}

	public boolean canUpdate(String element) {
		return resolve(element, Action.UPDATE);
	}

	public boolean canDelete(String element) {
		return resolve(element, Action.DELETE);
	}

	public boolean canExport(String element) {
		return resolve(element, Action.EXPORT);
	}

	public boolean canPrint(String element) {
		return resolve(element, Action.PRINT);
	}


	protected boolean resolve(String element, Action action) {
		permissionResolver = BeanContext.getFromSession("permissionResolver");
		return permissionResolver.check(getUser(), getDomain(), element, action);
	}

}
