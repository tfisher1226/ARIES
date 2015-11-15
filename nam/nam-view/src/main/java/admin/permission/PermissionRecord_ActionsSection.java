package admin.permission;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Permission;


@SessionScoped
@Named("preferencesActionsSection")
public class PermissionRecord_ActionsSection extends AbstractWizardPage<Permission> {

	private Permission permission;

	
	public PermissionRecord_ActionsSection() {
		//setTitle("Specify permission information.");
		setName("Elements");
		setUrl("elements");
		//setOwner(owner);
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public void initialize(Permission permission) {
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPermission(permission);
	}
	
	public void validate() {
		if (permission == null) {
			validator.missing("Permission");
		} else {
		}
	}

}
