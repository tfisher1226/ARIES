package admin.permission;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Permission;
import admin.util.PermissionUtil;


@SessionScoped
@Named("permissionConfigurationSection")
public class PermissionRecord_ConfigurationSection extends AbstractWizardPage<Permission> implements Serializable {

	private Permission permission;

	
	public PermissionRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
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
		setBackEnabled(true);
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
