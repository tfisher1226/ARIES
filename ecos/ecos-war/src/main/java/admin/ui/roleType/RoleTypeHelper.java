package admin.ui.roleType;

import java.io.Serializable;
import java.util.Collection;

import org.aries.ui.manager.AbstractEnumerationHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.RoleType;
import admin.util.RoleTypeUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("roleTypeHelper")
@Scope(ScopeType.SESSION)
public class RoleTypeHelper extends AbstractEnumerationHelper<RoleType> implements Serializable {
	
	@Factory(value = "roleTypeList")
	public RoleType[] getRoleTypeArray() {
		return RoleType.values();
	}
	
	@Override
	public String toString(RoleType roleType) {
		return roleType.name();
	}
	
	@Override
	public String toString(Collection<RoleType> roleTypeList) {
		return RoleTypeUtil.toString(roleTypeList);
	}
	
}
