package admin.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.PhoneNumber;
import org.aries.common.util.EmailAddressUtil;
import org.aries.common.util.PersonNameUtil;
import org.aries.common.util.PhoneNumberUtil;

import admin.Role;
import admin.User;
import admin.util.RoleUtil;


@SessionScoped
@Named("userWizard")
@SuppressWarnings("serial")
public class UserWizard extends AbstractDomainElementWizard<User> implements Serializable {
	
	@Inject
	private UserDataManager userDataManager;
	
	@Inject
	private UserPageManager userPageManager;

	@Inject
	private UserEventManager userEventManager;

	@Inject
	private SelectionContext selectionContext;

	
	@Override
	public String getName() {
		return "User";
	}

	@Override
	public String getUrlContext() {
		return userPageManager.getUserWizardPage();
	}

	@Override
	public void initialize(User user) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(userPageManager.getSections());
		super.initialize(user);
	}

	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}

	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}

	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}

	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		userPageManager.updateState();
		return url;
	}

	@Override
	public String first() {
		String url = super.first();
		userPageManager.updateState();
		return url;
	}

	@Override
	public String back() {
		String url = super.back();
		userPageManager.updateState();
		return url;
	}

	@Override
	public String next() {
		String url = super.next();
		userPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}

	@Override
	public String finish() {
		User user = getInstance();
		userDataManager.saveUser(user);
		userEventManager.fireSavedEvent(user);
		String url = selectionContext.popOrigin();
		return url;
	}

	@Override
	public String cancel() {
		User user = getInstance();
		//TODO take this out soon
		if (user == null)
			user = new User();
		userEventManager.fireCancelledEvent(user);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		User user = getInstance();
		PersonName personName = PersonNameUtil.create();
		personName.setFirstName("firstName1");
		personName.setLastName("lastName1");
		user.setPersonName(personName);
		user.setUserName("userName1");
		user.setPassword("password1");
		user.setPassword2("password2");
		
		EmailAddress emailAddress = EmailAddressUtil.create();
		emailAddress.setUrl("emailAddress1@sample.com");
		user.setEmailAddress(emailAddress );

		PhoneNumber cellPhone = PhoneNumberUtil.create();
		PhoneNumber homePhone = PhoneNumberUtil.create();
		cellPhone.setArea("213");
		cellPhone.setNumber("3345678");
		homePhone.setArea("626");
		homePhone.setNumber("2622299");
		user.setCellPhone(cellPhone);
		user.setHomePhone(homePhone);
		
		Role role = RoleUtil.create();
		role.setName("Test4");
		user.addToRoles(role);
		return getUrl();
	}
	
}
