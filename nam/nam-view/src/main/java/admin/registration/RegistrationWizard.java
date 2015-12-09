package admin.registration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.ExceptionUtil;
import org.aries.util.NameUtil;

import admin.Registration;
import admin.User;
import admin.util.RegistrationUtil;


@SessionScoped
@Named("registrationWizard")
@SuppressWarnings("serial")
public class RegistrationWizard extends AbstractDomainElementWizard<Registration> implements Serializable {
	
	@Inject
	private RegistrationDataManager registrationDataManager;
	
	@Inject
	private RegistrationPageManager registrationPageManager;
	
	@Inject
	private RegistrationEventManager registrationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Registration";
	}
	
	@Override
	public String getUrlContext() {
		return registrationPageManager.getRegistrationWizardPage();
	}
	
	@Override
	public void initialize(Registration registration) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(registrationPageManager.getSections());
		super.initialize(registration);
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
		registrationPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		registrationPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		registrationPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		registrationPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		if (super.finish() == null)
			return null;
		try {
			Registration registration = selectionContext.getSelection("registration");
			registrationDataManager.saveRegistration(registration);
			registrationEventManager.fireAddEvent(registration);
			getSecurityManager().login(registration.getUser());
			String url = getPageManager().getMainPage();
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String cancel() {
		Registration registration = getInstance();
		//TODO take this out soon
		if (registration == null)
			registration = new Registration();
		registrationEventManager.fireCancelledEvent(registration);
		//String url = selectionContext.popOrigin();
		String url = getPageManager().getMainPage();
		return url;
	}
	
	public String populateDefaultValues() {
		Registration registration = selectionContext.getSelection("registration");
		String name = RegistrationUtil.getLabel(registration);
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("registrationWizard");
			display.error("Registration name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
