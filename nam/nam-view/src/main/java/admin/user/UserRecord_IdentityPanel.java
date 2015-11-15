package admin.user;

import java.io.Serializable;

import org.aries.ui.wizard.AbstractWizardPanel;

import admin.User;


//@SessionScoped
//@Named("userInfoIdentityPanel")
public class UserRecord_IdentityPanel extends AbstractWizardPanel implements Serializable {
	
	private User user;

	
	public UserRecord_IdentityPanel(String module) {
		setMessage("Specify Contact Information");
		setModule(module);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void initialize(User user) {
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setUser(user);
	}
	
	public boolean validate() {
		if (user == null) {
			validator.missing("User");
		} else {
			//if (StringUtils.isEmpty(user.getGroupId()))
			//	validator.missing("Group ID");
			//if (StringUtils.isEmpty(user.getArtifactId()))
			//	validator.missing("Artifact ID");
			//if (StringUtils.isEmpty(user.getVersion()))
			//	validator.missing("Version");
			//if (user.getWebEnabled() != null && user.getWebEnabled() && StringUtils.isEmpty(user.getNamespace())) 
			//	validator.missing("Namespace");
		}
		//for now
		return true;
	}
	
}
