package admin.ui.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Messages;
import org.aries.ui.wizard.AbstractWizard;

import admin.User;


@SessionScoped
@Named("userWizard")
public class UserWizard extends AbstractWizard implements Serializable {
	
	private User user;

	@Inject
	private UserInfoManager userInfoManager;
	
	//@Inject
	private UserInfoIdentityPanel userInfoIdentityPanel;

	//@Inject
	private UserInfoContactPanel userInfoContactPanel;

	
	public UserWizard() {
		userInfoIdentityPanel = new UserInfoIdentityPanel("userWizard");
		userInfoContactPanel = new UserInfoContactPanel("userWizard");
		addPage(userInfoIdentityPanel);
		addPage(userInfoContactPanel);
		//reset();
	}

	public void initialize(User user) {
		userInfoIdentityPanel.initialize(user);
		userInfoContactPanel.initialize(user);
		userInfoIdentityPanel.setVisible(true);
		userInfoContactPanel.setVisible(false);
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
		//userSetupPanel.refresh();
		//userConfigPanel.refresh();
		return null;
	}
	
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	//SEAM @Begin(join=true)
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
	
	//SEAM @Begin(join=true)
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

