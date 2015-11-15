package admin.permission;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import admin.Permission;
import admin.util.PermissionUtil;


@SessionScoped
@Named("permissionWizard")
@SuppressWarnings("serial")
public class PermissionWizard extends AbstractDomainElementWizard<Permission> implements Serializable {
	
	@Inject
	private PermissionDataManager permissionDataManager;
	
	@Inject
	private PermissionPageManager permissionPageManager;
	
	@Inject
	private PermissionEventManager permissionEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Permission";
	}
	
	@Override
	public String getUrlContext() {
		return permissionPageManager.getPermissionWizardPage();
	}
	
	@Override
	public void initialize(Permission permission) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(permissionPageManager.getSections());
		super.initialize(permission);
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
		permissionPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		permissionPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		permissionPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		permissionPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Permission permission = getInstance();
		permissionDataManager.savePermission(permission);
		permissionEventManager.fireSavedEvent(permission);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Permission permission = getInstance();
		//TODO take this out soon
		if (permission == null)
			permission = new Permission();
		permissionEventManager.fireCancelledEvent(permission);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Permission permission = selectionContext.getSelection("permission");
		String name = PermissionUtil.getLabel(permission);
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("permissionWizard");
			display.error("Permission name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
