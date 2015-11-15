package template1_package.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.aries.ui.Display;
import org.aries.ui.tab.TabManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;

import admin.User;
import admin.ui.user.UserFieldListManager;
import admin.ui.user.UserListManager;


@AutoCreate
@Name("adminManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class AdminManager implements Serializable {
	
	@In(required = true)
	protected Events events;
	
	@In(required = true)
	protected Display display;
	
	@In(required = false)
	@Out(required = false)
	private User principal;
	
	@Out(required = false)
	private String activeSection;
	
	@Out(required = false)
	private String activeTab;

	@In(required = true)
	private UserListManager userListManager;

	@In(required = true)
	private UserFieldListManager userFieldListManager;
	
	private List<TabManager> tabManagersAsList;
	
	private Map<String, TabManager> tabManagers = new LinkedHashMap<String, TabManager>();
	
	
	public AdminManager() {
		activeSection = "adminModelPane";
	}
	
	public void refresh() {
		//refreshOrganizations();
		refreshUsers();
	}
	
	public void reset() {
		//userListManager.refresh();
		userFieldListManager.refresh();
		postEvent("org.aries.ui.reset");
		//userSkin.reset();
	}
	
	public void postEvent(String event) {
		events.raiseEvent(event);
	}

	@Observer("org.aries.admin.userLoggedIn")
	public void handleUserLoggedIn() {
		refresh();
	}

//	@Observer("org.aries.admin.organizationTreeChanged")
//	public void handleDomainTreeChanged() {
//		refreshOrganizations();
//	}
//
//	@Observer("org.aries.admin.organizationSelected")
//	public void handleOrganizationSelected() {
//		//Organization organization = organizationTreeManager.getSelectedOrganization();
//		//refreshMembership(organization.getId());
//	}
	
	/*
	 * Context Management
	 * ------------------
	 */
	
	public String getActiveSection() {
		return activeSection;
	}

	public void setActiveSection(String activeSection) {
		this.activeSection = activeSection;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	/*
	 * User Management
	 * -----------------
	 */

	public void refreshUsers() {
		userListManager.refresh();
	}

	public void viewUsers() {
		userListManager.refresh();
		userListManager.setTabVisible(true);
	}

	
	public TabManager getTab(String tabId) {
		return tabManagers.get(tabId);
	}

	public Collection<TabManager> getTabs() {
		return tabManagersAsList;
	}

	public void addTab(TabManager tabManager) {
		tabManagers.put(tabManager.getTabId(), tabManager);
		resetTabList();
	}

	protected void resetTabList() {
		tabManagersAsList = new ArrayList<TabManager>(tabManagers.values());
	}
	
	public void refreshTab(String tabId) {
		TabManager tabManager = tabManagers.get(tabId);
		if (tabManager != null) {
			tabManager.refresh();
		}
	}
	
	public void closeTab(String tabId) {
		if (tabId != null) {
			if (tabId.equals("userListTab")) {
				userListManager.setTabVisible(false);
				return;
			}

			TabManager tabManager = tabManagers.get(tabId);
			if (tabManager != null) {
				//tabManager.close();
				//tabManager.setVisible(false);
			}
			
			tabManagers.remove(tabId);
			resetTabList();
		}
	}	
	
}
