package admin.role;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Role;
import admin.util.RoleUtil;


@SessionScoped
@Named("roleOverviewSection")
public class RoleRecord_OverviewSection extends AbstractWizardPage<Role> implements Serializable {
	
	private Role role;
	
	
	public RoleRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
		setBackEnabled(false);
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
