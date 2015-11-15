package template1_package.ui;

import java.io.Serializable;

import org.aries.nam.model.TransportType;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import admin.Action;
import admin.RoleType;


@Startup
@AutoCreate
@Name("adminHelper")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class AdminHelper implements Serializable {

	@Factory(value = "roleTypeList")
	public RoleType[] getRoleTypeList() {
		return RoleType.values();
	}
	
	@Factory(value = "actionList")
	public Action[] getActionList() {
		return Action.values();
	}
	
	@Factory(value = "transportTypes")
	public TransportType[] getTransportTypes() {
		return TransportType.values();
	}
	
}
