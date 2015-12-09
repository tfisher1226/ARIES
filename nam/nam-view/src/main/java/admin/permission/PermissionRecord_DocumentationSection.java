package admin.permission;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import admin.Permission;
import admin.util.PermissionUtil;


@SessionScoped
@Named("permissionDocumentationSection")
public class PermissionRecord_DocumentationSection extends AbstractWizardPage<Permission> implements Serializable {
	
	private Permission permission;

	
	public PermissionRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
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
