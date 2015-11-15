package admin.role;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Role;
import admin.util.RoleUtil;


@SessionScoped
@Named("roleGroupsSection")
public class RoleRecord_GroupsSection extends AbstractWizardPage<Role> {

	//private static Log log = LogFactory.getLog(RoleSetupPage.class);
	
	private Role role;

	
	public RoleRecord_GroupsSection() {
		//setTitle("Specify role information.");
		setName("Groups");
		setUrl("groups");
		//setOwner(owner);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void initialize(Role role) {
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setRole(role);
	}
	
	public void validate() {
		if (role == null) {
			validator.missing("Role");
		} else {
			if (RoleUtil.isEmpty(role.getGroups()))
				validator.missing("Groups");
		}
	}

}
