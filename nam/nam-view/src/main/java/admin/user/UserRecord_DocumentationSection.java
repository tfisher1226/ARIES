package admin.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.User;
import admin.util.UserUtil;


@SessionScoped
@Named("userDocumentationSection")
public class UserRecord_DocumentationSection extends AbstractWizardPage<User> implements Serializable {
	
	private User user;
	
	
	public UserRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
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
