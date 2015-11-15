package bookshop2.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.Display;
import org.aries.ui.event.ResetEvent;
import org.aries.ui.tab.TabManager;

import admin.User;
import admin.ui.user.UserFieldListManager;
import admin.ui.user.UserListManager;


@SessionScoped
@Named("adminManager")
public class AdminManager implements Serializable {
	
	//@In(required = true)
	//protected Events events;
	
	@Inject
	protected Display display;
	
	//@In(required = false)
	//@Out(required = false)
	private User principal;
	
	//@Out(required = false)
	private String activeSection;
	
	//@Out(required = false)
	private String activeTab;

	@Inject
	private UserListManager userListManager;

	//@In(required = true)
	private UserFieldListManager userFieldListManager;

	private List<TabManager> tabManagersAsList;
	
	private Map<String, TabManager> tabManagers = new LinkedHashMap<String, TabManager>();
	
	@Inject
	private Event<ResetEvent> resetEvent;

	
	public AdminManager() {
		activeSection = "adminModelPane";
	}
	
	public void refresh() {
		//refreshOrganizations();
		refreshUsers();
	}
	
	public void reset() {
		//userListManager.refresh();
		//userFieldListManager.refresh();
		//postEvent("org.aries.ui.reset");
		resetEvent.fire(new ResetEvent());
	}
	
	public void postEvent(String event) {
//		events.raiseEvent(event);
	}

	//SEAM @Observer("org.aries.admin.userLoggedIn")
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
