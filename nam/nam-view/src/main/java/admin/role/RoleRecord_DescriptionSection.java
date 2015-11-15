package admin.role;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Role;


@SessionScoped
@Named("roleDescriptionSection")
public class RoleRecord_DescriptionSection extends AbstractWizardPage<Role> {
	
	private Role role;

	
	public RoleRecord_DescriptionSection() {
		//setTitle("Specify role information.");
		setName("Documentation");
		setUrl("documentation");
		//setOwner(owner);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void initialize(Role role) {
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(true);
		setRole(role);
	}
	
	public void validate() {
		if (role == null) {
			validator.missing("Role");
		} else {

		}
	}

}
