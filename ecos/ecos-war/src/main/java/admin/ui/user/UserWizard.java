package admin.ui.user;

import java.io.Serializable;

import org.aries.runtime.BeanContext;
import org.aries.ui.Messages;
import org.aries.ui.wizard.AbstractWizard;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.User;


@Startup
@AutoCreate
@BypassInterceptors
@Name("userWizard")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class UserWizard extends AbstractWizard implements Serializable {
	
	//@Out(required=false)
	private User user;

	//@In(required=true)
	private UserInfoManager userInfoManager;
	
	//@Out(required=true)
	private UserInfoIdentityPanel userInfoIdentityPage;

	//@Out(required=true)
	private UserInfoContactPanel userInfoContactPage;

	
	public UserWizard() {
		userInfoIdentityPage = new UserInfoIdentityPanel("userWizard");
		userInfoContactPage = new UserInfoContactPanel("userWizard");
		addPage(userInfoIdentityPage);
		addPage(userInfoContactPage);
		//reset();
	}

	public void initialize(User user) {
		userInfoIdentityPage.initialize(user);
		userInfoContactPage.initialize(user);
		userInfoIdentityPage.setVisible(true);
		userInfoContactPage.setVisible(false);
	}

//	Handler<Object> createUserSelectListener() {
//		return new Handler<Object>() {
//			public void handle(Object artifact) {
//				if (artifact instanceof User) {
//					//Configuration configuration = (Configuration) artifact;
//					//user.setConfiguration(configuration);
//				}
//			}
//		};
//	}

	public String refresh() {
		return super.refresh();
	}

	
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	@Begin(join=true)
	public void newUser() {
		setTitle("New User");
		Messages messages = BeanContext.get("messages");
		messages.info("User", "Enter information for new User.");
		user = new User();
		//user.setUserId(value);
		userInfoManager.initialize(user);
		initialize(user);
	}

//	@Begin(join=true)
//	public void editUser() {
//		editUser(user);
//	}
	
	@Begin(join=true)
	public void editUser(User user) {
		//setTitle("User: "+user.getName()+"");
		//initialize(user);
	}
	
	public void saveUser() {
		//List<User> users = ProjectUtil.getUsers(project);
		//if (!users.contains(user))
		//	users.add(user);
		//saveProject();
	}
	
	@Override
	public void finish() {
		super.finish();
		userInfoManager.saveUser();
		refresh();
	}
	
}

