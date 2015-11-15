package admin.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.util.NameUtil;

import admin.User;
import admin.permission.PermissionPageManager;
import admin.preferences.PreferencesPageManager;
import admin.registration.RegistrationPageManager;
import admin.role.RolePageManager;
import admin.team.TeamPageManager;
import admin.util.UserUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("userPageManager")
public class UserPageManager extends AbstractPageManager<User> implements Serializable {

	@Inject
	private UserWizard userWizard;
	
	@Inject
	private UserDataManager userDataManager;
	
	@Inject
	private UserListManager userListManager;
	
	@Inject
	private TeamPageManager teamPageManager;
	
	@Inject
	private RolePageManager rolePageManager;
	
	@Inject
	private PermissionPageManager permissionPageManager;
	
	@Inject
	private PreferencesPageManager preferencesPageManager;
	
	@Inject
	private RegistrationPageManager registrationPageManager;
	
	@Inject
	private UserRecord_OverviewSection userOverviewSection;
	
	@Inject
	private UserRecord_IdentitySection userIdentitySection;
	
	@Inject
	private UserRecord_ContactSection userContactSection;
	@Inject
	private UserRecord_TeamsSection userTeamsSection;
	
	@Inject
	private UserRecord_RolesSection userRolesSection;
	
	@Inject
	private UserRecord_ActionsSection userActionsSection;
	
	@Inject
	private UserRecord_PermissionsSection userPermissionsSection;
	
	@Inject
	private UserRecord_PreferencesSection userPreferencesSection;
	
	@Inject
	private UserRecord_RegistrationSection userRegistrationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public UserPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("user");
	}
	
	public void refreshLocal() {
		refreshLocal("user");
	}
	
	public void refreshMembers() {
		refreshMembers("user");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		userDataManager.setScope(scope);
		userListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		teamPageManager.refresh(scope);
		rolePageManager.refresh(scope);
		//actionPageManager.refresh(scope);
		permissionPageManager.refresh(scope);
		preferencesPageManager.refresh(scope);
		registrationPageManager.refresh(scope);
		userListManager.refresh();
	}

	public String getUserListPage() {
		return "/admin/user/userListPage.xhtml";
	}
	
	public String getUserTreePage() {
		return "/admin/user/userTreePage.xhtml";
	}

	public String getUserSummaryPage() {
		return "/admin/user/userSummaryPage.xhtml";
	}

	public String getUserRecordPage() {
		return "/admin/user/userRecordPage.xhtml";
	}
	
	public String getUserWizardPage() {
		return "/admin/user/userWizardPage.xhtml";
	}

	public String getUserManagementPage() {
		return "/admin/user/userManagementPage.xhtml";
	}
	
	public String initializeUserListPage() {
		String pageLevelKey = "userList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Users", "showUserManagementPage()");
		String url = getUserListPage();
		selectionContext.setCurrentArea("user");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeUserTreePage() {
		String pageLevelKey = "userTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Users", "showUserTreePage()");
		String url = getUserTreePage();
		selectionContext.setCurrentArea("user");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeUserSummaryPage(User user) {
		String pageLevelKey = "userSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Users", "showUserSummaryPage()");
		String url = getUserSummaryPage();
		selectionContext.setCurrentArea("user");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeUserRecordPage() {
		User user = selectionContext.getSelection("user");
		String userName = UserUtil.getLabel(user);
		
		String pageLevelKey = "userRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Users", "showUserManagementPage()");
		addBreadcrumb(pageLevelKey, userName, "showUserRecordPage()");
		String url = getUserRecordPage();
		selectionContext.setCurrentArea("user");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeUserCreationPage(User user) {
		setPageTitle("New "+getUserLabel(user));
		setPageIcon("/icons/nam/NewUser16.gif");
		setSectionTitle("User Identification");
		userWizard.setNewMode(true);
		
		String pageLevelKey = "user";
		String wizardLevelKey = "userWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Users", "showUserManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New User", "showUserWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Teams", "showUserWizardPage('Teams')");
		addBreadcrumb(wizardLevelKey, "Roles", "showUserWizardPage('Roles')");
		addBreadcrumb(wizardLevelKey, "Actions", "showUserWizardPage('Actions')");
		addBreadcrumb(wizardLevelKey, "Permissions", "showUserWizardPage('Permissions')");
		addBreadcrumb(wizardLevelKey, "Preferences", "showUserWizardPage('Preferenceses')");
		addBreadcrumb(wizardLevelKey, "Registration", "showUserWizardPage('Registration')");

		userIdentitySection.setOwner("userWizard");
		userContactSection.setOwner("userWizard");
		userTeamsSection.setOwner("userWizard");
		userRolesSection.setOwner("userWizard");
		userActionsSection.setOwner("userWizard");
		userPermissionsSection.setOwner("userWizard");
		userPreferencesSection.setOwner("userWizard");
		userRegistrationSection.setOwner("userWizard");
		
		sections.clear();
		sections.add(userIdentitySection);
		sections.add(userContactSection);
		sections.add(userTeamsSection);
		sections.add(userRolesSection);
		sections.add(userActionsSection);
		sections.add(userPermissionsSection);
		sections.add(userPreferencesSection);
		sections.add(userRegistrationSection);
		
		String url = getUserWizardPage() + "?section=Identity";
		selectionContext.setCurrentArea("user");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeUserUpdatePage(User user) {
		setPageTitle(getUserLabel(user));
		setPageIcon("/icons/nam/User16.gif");
		setSectionTitle("User Overview");
		String userName = UserUtil.getLabel(user);
		userWizard.setNewMode(false);
		
		String pageLevelKey = "user";
		String wizardLevelKey = "userWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Users", "showUserManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(userName, "showUserWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Teams", "showUserWizardPage('Teams')");
		addBreadcrumb(wizardLevelKey, "Roles", "showUserWizardPage('Roles')");
		addBreadcrumb(wizardLevelKey, "Actions", "showUserWizardPage('Actions')");
		addBreadcrumb(wizardLevelKey, "Permissions", "showUserWizardPage('Permissions')");
		addBreadcrumb(wizardLevelKey, "Preferences", "showUserWizardPage('Preferences')");
		addBreadcrumb(wizardLevelKey, "Registration", "showUserWizardPage('Registration')");

		userOverviewSection.setOwner("userWizard");
		userIdentitySection.setOwner("userWizard");
		userContactSection.setOwner("userWizard");
		userTeamsSection.setOwner("userWizard");
		userRolesSection.setOwner("userWizard");
		userActionsSection.setOwner("userWizard");
		userPermissionsSection.setOwner("userWizard");
		userPreferencesSection.setOwner("userWizard");
		userRegistrationSection.setOwner("userWizard");
		
		sections.clear();
		sections.add(userOverviewSection);
		sections.add(userIdentitySection);
		sections.add(userContactSection);
		sections.add(userTeamsSection);
		sections.add(userRolesSection);
		sections.add(userActionsSection);
		sections.add(userPermissionsSection);
		sections.add(userPreferencesSection);
		sections.add(userRegistrationSection);
		
		String url = getUserWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("user");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}

	public String initializeUserManagementPage() {
		setPageTitle("Users");
		setPageIcon("/icons/nam/Users16.gif");
		String pageLevelKey = "userManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Users", "showUserManagementPage()");
		String url = getUserManagementPage();
		selectionContext.setCurrentArea("user");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		refresh();
		return url;
	}

	public void initializeDefaultView() {
		setSectionType("user");
		setSectionName("Overview");
		setSectionTitle("Overview of Users");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeUserTeamsView() {
		setSectionType("user");
		setSectionName("Teams");
		setSectionTitle("User Teams");
		setSectionIcon("/icons/nam/Team16.gif");
		selectionContext.setMessageDomain("userTeams");
		teamPageManager.refresh("user");
		userListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserRolesView() {
		setSectionType("user");
		setSectionName("Roles");
		setSectionTitle("User Roles");
		setSectionIcon("/icons/nam/Role16.gif");
		selectionContext.setMessageDomain("userRoles");
		rolePageManager.refresh("user");
		userListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserActionsView() {
		setSectionType("user");
		setSectionName("Actions");
		setSectionTitle("User Actions");
		setSectionIcon("/icons/nam/Action16.gif");
		selectionContext.setMessageDomain("userActions");
		//actionPageManager.refresh("user");
		userListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserPermissionsView() {
		setSectionType("user");
		setSectionName("Permissions");
		setSectionTitle("User Permissions");
		setSectionIcon("/icons/nam/Permission16.gif");
		selectionContext.setMessageDomain("userPermissions");
		permissionPageManager.refresh("user");
		userListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserPreferencesesView() {
		setSectionType("user");
		setSectionName("Preferenceses");
		setSectionTitle("User Preferenceses");
		setSectionIcon("/icons/nam/Preferences16.gif");
		selectionContext.setMessageDomain("userPreferenceses");
		preferencesPageManager.refresh("user");
		userListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserRegistrationsView() {
		setSectionType("user");
		setSectionName("Registrations");
		setSectionTitle("User Registrations");
		setSectionIcon("/icons/nam/Registration16.gif");
		selectionContext.setMessageDomain("userRegistrations");
		registrationPageManager.refresh("user");
		userListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserSummaryView(User user) {
		//String viewTitle = getUserLabel(user);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("user");
		setSectionName("Summary");
		setSectionTitle("Summary of User Records");
		setSectionIcon("/icons/nam/User16.gif");
		String viewLevelKey = "userSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Users", "showUserManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getUserLabel(User user) {
		String label = "User";
		String name = UserUtil.getLabel(user);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<User> page = userWizard.getPage();
		if (page != null)
			setSectionTitle("User " + page.getName());
	}
	
	protected void updateState(User user) {
		String userName = UserUtil.getLabel(user);
		setSectionTitle(userName + " User");
	}
	
}
