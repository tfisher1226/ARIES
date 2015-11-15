package admin.ui.roleType;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.manager.AbstractEnumerationHelper;

import admin.RoleType;
import admin.util.RoleTypeUtil;


@SessionScoped
@Named("roleTypeHelper")
public class RoleTypeHelper extends AbstractEnumerationHelper<RoleType> implements Serializable {
	
	//SEAM @Factory(value = "roleTypeList")
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
