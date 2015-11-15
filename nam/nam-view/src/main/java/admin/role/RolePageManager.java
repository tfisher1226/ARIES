package admin.role;

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

import admin.Role;
import admin.util.RoleUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("rolePageManager")
public class RolePageManager extends AbstractPageManager<Role> implements Serializable {

	@Inject
	private RoleWizard roleWizard;
	
	@Inject
	private RoleDataManager roleDataManager;
	
	@Inject
	private RoleListManager roleListManager;
	
	@Inject
	private RoleRecord_OverviewSection roleOverviewSection;
	
	@Inject
	private RoleRecord_IdentificationSection roleIdentificationSection;
	
	@Inject
	private RoleRecord_ConfigurationSection roleConfigurationSection;
	
	@Inject
	private RoleRecord_DocumentationSection roleDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public RolePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("role");
	}
	
	public void refreshLocal() {
		refreshLocal("role");
	}
	
	public void refreshMembers() {
		refreshMembers("role");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		roleDataManager.setScope(scope);
		roleListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		roleListManager.refresh();
	}

	public String getRoleListPage() {
		return "/admin/role/roleListPage.xhtml";
	}
	
	public String getRoleTreePage() {
		return "/admin/role/roleTreePage.xhtml";
	}

	public String getRoleSummaryPage() {
		return "/admin/role/roleSummaryPage.xhtml";
	}
	
	public String getRoleRecordPage() {
		return "/admin/role/roleRecordPage.xhtml";
	}

	public String getRoleWizardPage() {
		return "/admin/role/roleWizardPage.xhtml";
	}

	public String getRoleManagementPage() {
		return "/admin/role/roleManagementPage.xhtml";
	}
	
	public String initializeRoleListPage() {
		String pageLevelKey = "roleList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Roles", "showRoleManagementPage()");
		String url = getRoleListPage();
		selectionContext.setCurrentArea("role");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeRoleTreePage() {
		String pageLevelKey = "roleTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Roles", "showRoleTreePage()");
		String url = getRoleTreePage();
		selectionContext.setCurrentArea("role");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeRoleSummaryPage(Role role) {
		String pageLevelKey = "roleSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Roles", "showRoleSummaryPage()");
		String url = getRoleSummaryPage();
		selectionContext.setCurrentArea("role");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeRoleRecordPage() {
		Role role = selectionContext.getSelection("role");
		String roleName = role.getName();
		
		String pageLevelKey = "roleRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Roles", "showRoleManagementPage()");
		addBreadcrumb(pageLevelKey, roleName, "showRoleRecordPage()");
		String url = getRoleRecordPage();
		selectionContext.setCurrentArea("role");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeRoleCreationPage(Role role) {
		setPageTitle("New "+getRoleLabel(role));
		setPageIcon("/icons/nam/NewRole16.gif");
		setSectionTitle("Role Identification");
		roleWizard.setNewMode(true);
		
		String pageLevelKey = "role";
		String wizardLevelKey = "roleWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Roles", "showRoleManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Role", "showRoleWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showRoleWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showRoleWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showRoleWizardPage('Documentation')");
		
		roleIdentificationSection.setOwner("roleWizard");
		roleConfigurationSection.setOwner("roleWizard");
		roleDocumentationSection.setOwner("roleWizard");

		sections.clear();
		sections.add(roleIdentificationSection);
		sections.add(roleConfigurationSection);
		sections.add(roleDocumentationSection);
		
		String url = getRoleWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("role");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeRoleUpdatePage(Role role) {
		setPageTitle(getRoleLabel(role));
		setPageIcon("/icons/nam/Role16.gif");
		setSectionTitle("Role Overview");
		String roleName = role.getName();
		roleWizard.setNewMode(false);
		
		String pageLevelKey = "role";
		String wizardLevelKey = "roleWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Roles", "showRoleManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(roleName, "showRoleWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showRoleWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showRoleWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showRoleWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showRoleWizardPage('Documentation')");

		roleOverviewSection.setOwner("roleWizard");
		roleIdentificationSection.setOwner("roleWizard");
		roleConfigurationSection.setOwner("roleWizard");
		roleDocumentationSection.setOwner("roleWizard");
		
		sections.clear();
		sections.add(roleOverviewSection);
		sections.add(roleIdentificationSection);
		sections.add(roleConfigurationSection);
		sections.add(roleDocumentationSection);
		
		String url = getRoleWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("role");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}

	public String initializeRoleManagementPage() {
		setPageTitle("Roles");
		setPageIcon("/icons/nam/Role16.gif");
		String pageLevelKey = "roleManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Roles", "showRoleManagementPage()");
		String url = getRoleManagementPage();
		selectionContext.setCurrentArea("role");
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
		setSectionType("role");
		setSectionName("Overview");
		setSectionTitle("Overview of Roles");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeRoleSummaryView(Role role) {
		//String viewTitle = getRoleLabel(role);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("role");
		setSectionName("Summary");
		setSectionTitle("Summary of Role Records");
		setSectionIcon("/icons/nam/Role16.gif");
		String viewLevelKey = "roleSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Roles", "showRoleManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getRoleLabel(Role role) {
		String label = "Role";
		String name = RoleUtil.getLabel(role);
		if (name == null && role.getName() != null)
			name = NameUtil.capName(role.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Role> page = roleWizard.getPage();
		if (page != null)
			setSectionTitle("Role " + page.getName());
	}
	
	protected void updateState(Role role) {
		String roleName = NameUtil.capName(role.getName());
		setSectionTitle(roleName + " Role");
	}
	
}
