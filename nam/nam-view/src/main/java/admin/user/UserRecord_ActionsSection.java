package admin.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import admin.User;
import admin.util.UserUtil;


@SessionScoped
@Named("userActionsSection")
public class UserRecord_ActionsSection extends AbstractWizardPage<User> implements Serializable {
	
	private User user;
	
	
	public UserRecord_ActionsSection() {
		setName("Actions");
		setUrl("actions");
	}
	
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public void initialize(User user) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setUser(user);
	}
	
	@Override
	public void validate() {
		if (user == null) {
			validator.missing("User");
		} else {
		}
	}
	
}
