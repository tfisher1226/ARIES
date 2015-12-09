package admin.permission;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import admin.Permission;
import admin.util.PermissionUtil;


@SessionScoped
@Named("permissionOverviewSection")
public class PermissionRecord_OverviewSection extends AbstractWizardPage<Permission> implements Serializable {
	
	private Permission permission;
	
	
	public PermissionRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Permission getPermission() {
		return permission;
	}
	
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	@Override
	public void initialize(Permission permission) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPermission(permission);
	}
	
	@Override
	public void validate() {
		if (permission == null) {
			validator.missing("Permission");
		} else {
		}
	}
	
}
