package template1.user;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.common.EmailAddress;
import org.aries.common.StreetAddress;
import org.aries.ui.AbstractViewManager;
import org.aries.ui.Display;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import template1.model.Member;
import template1.model.User;
import template1.service.UserService;



@Startup
@AutoCreate
@Name("userInfoManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class UserInfoManager extends AbstractViewManager<Member> implements Serializable {

	@In(required = true)
	private Display display;
	
	@In(required = false)
	@Out(required = false)
	private User user;
	
	@In(required=true)
	private UserService userService;

//	@In(required = true)
//	private PersonNameManager personNameManager;
//
//	@In(required = true)
//	private EmailAddressManager emailAddressManager;
//
//	@In(required = true)
//	private PhoneNumberManager phoneNumberManager;

	
	public UserInfoManager() {
	}
	
	public String getTitle() {
		if (user != null && !StringUtils.isEmpty(user.getFirstName()) && !StringUtils.isEmpty(user.getLastName()))
			return user.getFirstName()+" "+user.getLastName();
		return "User Account Information";
	}

	public User getUser() {
		return user;
	}

	public void setUser (User user) {
		this.user = user;
		//initialize(user);
	}
	
	public void initialize(User user) {
		display.setModule("userDialog");
		if (UserHelper.nameExists(user))
			display.info("Specify information for "+UserHelper.toNameString(user));
		else display.info("Specify information for new Membership Record");
	}
	
	public void newUser() {
		try {
			user = new User();
			user.setStreetAddress(new StreetAddress());
			user.setEmailAddress(new EmailAddress());
			initialize(user);
		} catch (Exception e) {
			display.error("Error: "+e.getMessage());
		}
	}
	
	public void openUser() {
		editUser(user);
	}
	
	public void editUser() {
		editUser(user);
	}
	
	public void editUser(User user) {
		display.setModule("userDialog");
		if (user == null) {
			display.warn("User record must be specified");
		}
		
		try {
			this.user = user;
			initialize(user);
		} catch (Exception e) {
			display.error("Error: "+e.getMessage());
		}
	}

	@Observer("org.sgiusa.saveUser")
	public void checkSaveUser() {
		enhanceUser(user);
		//validate always but only save here if record already exists
		if (validateUser(user) && user.getId() != null)
			saveUser(user);
	}

	public void saveUser() {
		enhanceUser(user);
		if (validateUser(user))
			saveUser(user);
	}

	public void saveUser(User user) {
		try {
			Long id = userService.saveUser(user);
			Assert.notNull(id, "User Id should not be null");
		} catch (Exception e) {
			Throwable cause = ExceptionUtils.getRootCause(e);
			display.error("Error: "+cause.getMessage());
		}
	}

	public void enhanceUser(User user) {
		//special case to add names to email for new record
		EmailAddress email = user.getEmailAddress();
		if (email != null) {
			if (StringUtils.isEmpty(email.getFirstName()))
				email.setFirstName(user.getFirstName());
			if (StringUtils.isEmpty(email.getLastName()))
				email.setLastName(user.getLastName());
		}
	}
	
	public boolean validateUser(User user) {
		display.setModule("userDialog");
		try {
			Assert.notNull(user, "User record should exist");
			if (StringUtils.isEmpty(user.getLastName()))
				display.error("Last name must be specified");
			if (StringUtils.isEmpty(user.getFirstName()))
				display.error("First name must be specified");
			if (StringUtils.isEmpty(user.getUserId()))
				display.error("User ID must be specified");
			if (StringUtils.isEmpty(user.getPassword()))
				display.error("Password must be specified");
			if (user.getEmailAddress() == null) {
				display.error("Email Address record must be specified");
			} else {
				if (StringUtils.isEmpty(user.getEmailAddress().getUrl()))
					display.error("Email Address must be specified");
				if (StringUtils.isEmpty(user.getEmailAddress().getFirstName()))
					display.error("First Name of Email Address must be specified");
				if (StringUtils.isEmpty(user.getEmailAddress().getLastName()))
					display.error("Last Name of Email Address must be specified");
			}
			return !display.messagesExist();
		} catch (Exception e) {
			display.warn(e.getMessage());
			return false;
		}
	}
	
	public String promptRemoveUser() {
		return null;
	}
	
	@Observer("removeUser")
	public void removeUser() {
		try {
			//TODO
			//userService.deleteUser(user);
		} catch (Exception e) {
			display.error("Error: "+e.getMessage());
		}
	}
	
}
