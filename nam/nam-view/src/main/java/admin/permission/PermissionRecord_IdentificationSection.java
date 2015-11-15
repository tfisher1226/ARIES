package admin.permission;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import admin.Permission;


@SessionScoped
@Named("permissionIdentificationSection")
public class PermissionRecord_IdentificationSection extends AbstractWizardPage<Permission> implements Serializable {
	
	private Permission permission;

	
	public PermissionRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setPermission(permission);
	}
	
	@Override
	public void validate() {
		if (permission == null) {
			validator.missing("Permission");
		} else {
			if (StringUtils.isEmpty(permission.getTarget()))
				validator.missing("Target");
			if (StringUtils.isEmpty(permission.getOrganization()))
				validator.missing("Organization");
		}
	}

}
