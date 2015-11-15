package template1_package.ui;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Action;
import admin.Permission;
import admin.Role;
import admin.User;


@AutoCreate
@BypassInterceptors
@Name("permissionResolver")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class PermissionResolver implements Serializable {

	public boolean check(User user, String domain, String element, Action action) {
		//check each role that user has - return true if any role has permission
		Set<Role> roles = user.getRoles();
		if (resolveRoles(roles, domain, element, action))
			return true;
		//check if any specific permission exists, and resolve using those permissions
		List<Permission> permissions = user.getPermissions();
		if (resolvePermissions(permissions, domain, element, action))
			return true;
		return false;
	}

	protected boolean resolveRoles(Collection<Role> roles, String domain, String element, Action action) {
		Iterator<Role> iterator = roles.iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			List<Permission> permissions = role.getPermissions();
			if (resolvePermissions(permissions, domain, element, action))
				return true;
		}
		
		return false;
	}

	protected boolean resolvePermissions(Collection<Permission> permissions, String domain, String element, Action action) {
		Iterator<Permission> iterator = permissions.iterator();
		while (iterator.hasNext()) {
			Permission permission = iterator.next();
			if (permission.getActions().contains(Action.ALL)) {
				return true;
			}
			if (hasPermission(permission, domain, element, action)) {
				return true;
			}
		}
		
		return false;
	}

	protected boolean hasPermission(Permission permission, String domain, String element, Action action) {
		if (!permission.getActions().contains(action)) {
			return false;
		}
		
		String restrictedTarget = permission.getTarget();
		if (restrictedTarget != null && !restrictedTarget.equals(element)) {
			return false;
		}
		
		String restrictedOrganization = permission.getOrganization();
		if (restrictedOrganization != null && !restrictedOrganization.equals(domain)) {
			return false;
		}
		
		return true;
	}
	
}
