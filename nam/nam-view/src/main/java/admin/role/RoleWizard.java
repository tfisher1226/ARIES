package admin.role;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import admin.Role;


@SessionScoped
@Named("roleWizard")
@SuppressWarnings("serial")
public class RoleWizard extends AbstractDomainElementWizard<Role> implements Serializable {
	
	@Inject
	private RoleDataManager roleDataManager;
	
	@Inject
	private RolePageManager rolePageManager;
	
	@Inject
	private RoleEventManager roleEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Role";
	}
	
	@Override
	public String getUrlContext() {
		return rolePageManager.getRoleWizardPage();
	}
	
	@Override
	public void initialize(Role role) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(rolePageManager.getSections());
		super.initialize(role);
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
		rolePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		rolePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		rolePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		rolePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Role role = getInstance();
		roleDataManager.saveRole(role);
		roleEventManager.fireSavedEvent(role);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Role role = getInstance();
		//TODO take this out soon
		if (role == null)
			role = new Role();
		roleEventManager.fireCancelledEvent(role);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Role role = selectionContext.getSelection("role");
		String name = role.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("roleWizard");
			display.error("Role name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
