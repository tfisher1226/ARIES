package nam.ui.userInterface;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.UserInterface;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;


@SessionScoped
@Named("userInterfaceWizard")
@SuppressWarnings("serial")
public class UserInterfaceWizard extends AbstractDomainElementWizard<UserInterface> implements Serializable {
	
	@Inject
	private UserInterfaceDataManager userInterfaceDataManager;
	
	@Inject
	private UserInterfacePageManager userInterfacePageManager;
	
	@Inject
	private UserInterfaceEventManager userInterfaceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "UserInterface";
	}
	
	@Override
	public String getUrlContext() {
		return userInterfacePageManager.getUserInterfaceWizardPage();
	}
	
	@Override
	public void initialize(UserInterface userInterface) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(userInterfacePageManager.getSections());
		super.initialize(userInterface);
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
		userInterfacePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		userInterfacePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		userInterfacePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		userInterfacePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		UserInterface userInterface = getInstance();
		userInterfaceDataManager.saveUserInterface(userInterface);
		userInterfaceEventManager.fireSavedEvent(userInterface);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		UserInterface userInterface = getInstance();
		//TODO take this out soon
		if (userInterface == null)
			userInterface = new UserInterface();
		userInterfaceEventManager.fireCancelledEvent(userInterface);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		UserInterface userInterface = selectionContext.getSelection("userInterface");
		String name = userInterface.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("userInterfaceWizard");
			display.error("UserInterface name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
