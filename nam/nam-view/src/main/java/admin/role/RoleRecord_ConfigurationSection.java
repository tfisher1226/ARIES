package admin.role;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import admin.Role;
import admin.util.RoleUtil;


@SessionScoped
@Named("roleConfigurationSection")
public class RoleRecord_ConfigurationSection extends AbstractWizardPage<Role> implements Serializable {
	
	private Role role;
	
	
	public RoleRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public void initialize(Role role) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setRole(role);
	}
	
	@Override
	public void validate() {
		if (role == null) {
			validator.missing("Role");
		} else {
		}
	}
	
}
