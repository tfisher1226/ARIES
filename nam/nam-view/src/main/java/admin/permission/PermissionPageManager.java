package admin.permission;

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

import admin.Permission;
import admin.util.PermissionUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("permissionPageManager")
public class PermissionPageManager extends AbstractPageManager<Permission> implements Serializable {
	
	@Inject
	private PermissionWizard permissionWizard;
	
	@Inject
	private PermissionDataManager permissionDataManager;
	
	@Inject
	private PermissionListManager permissionListManager;
	
	@Inject
	private PermissionRecord_OverviewSection permissionOverviewSection;
	
	@Inject
	private PermissionRecord_IdentificationSection permissionIdentificationSection;
	
	@Inject
	private PermissionRecord_ConfigurationSection permissionConfigurationSection;
	
	@Inject
	private PermissionRecord_DocumentationSection permissionDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	

	public PermissionPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("permission");
	}
	
	public void refreshLocal() {
		refreshLocal("permission");
	}
	
	public void refreshMembers() {
		refreshMembers("permission");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		permissionDataManager.setScope(scope);
		permissionListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		permissionListManager.refresh();
	}

	public String getPermissionListPage() {
		return "/admin/permission/permissionListPage.xhtml";
	}
	
	public String getPermissionTreePage() {
		return "/admin/permission/permissionTreePage.xhtml";
	}

	public String getPermissionSummaryPage() {
		return "/admin/permission/permissionSummaryPage.xhtml";
	}
	
	public String getPermissionRecordPage() {
		return "/admin/permission/permissionRecordPage.xhtml";
	}

	public String getPermissionWizardPage() {
		return "/admin/permission/permissionWizardPage.xhtml";
	}

	public String getPermissionManagementPage() {
		return "/admin/permission/permissionManagementPage.xhtml";
	}
	
	public String initializePermissionListPage() {
		String pageLevelKey = "permissionList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Permissions", "showPermissionManagementPage()");
		String url = getPermissionListPage();
		selectionContext.setCurrentArea("permission");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePermissionTreePage() {
		String pageLevelKey = "permissionTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Permissions", "showPermissionTreePage()");
		String url = getPermissionTreePage();
		selectionContext.setCurrentArea("permission");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePermissionSummaryPage(Permission permission) {
		String pageLevelKey = "permissionSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Permissions", "showPermissionSummaryPage()");
		String url = getPermissionSummaryPage();
		selectionContext.setCurrentArea("permission");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePermissionRecordPage() {
		Permission permission = selectionContext.getSelection("permission");
		String permissionName = PermissionUtil.getLabel(permission);
		
		String pageLevelKey = "permissionRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Permissions", "showPermissionManagementPage()");
		addBreadcrumb(pageLevelKey, permissionName, "showPermissionRecordPage()");
		String url = getPermissionRecordPage();
		selectionContext.setCurrentArea("permission");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializePermissionCreationPage(Permission permission) {
		setPageTitle("New "+getPermissionLabel(permission));
		setPageIcon("/icons/nam/NewPermission16.gif");
		setSectionTitle("Permission Identification");
		permissionWizard.setNewMode(true);
		
		String pageLevelKey = "permission";
		String wizardLevelKey = "permissionWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Permissions", "showPermissionManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Permission", "showPermissionWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showPermissionWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showPermissionWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showPermissionWizardPage('Documentation')");
		
		permissionIdentificationSection.setOwner("permissionWizard");
		permissionConfigurationSection.setOwner("permissionWizard");
		permissionDocumentationSection.setOwner("permissionWizard");

		sections.clear();
		sections.add(permissionIdentificationSection);
		sections.add(permissionConfigurationSection);
		sections.add(permissionDocumentationSection);
		
		String url = getPermissionWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("permission");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializePermissionUpdatePage(Permission permission) {
		setPageTitle(getPermissionLabel(permission));
		setPageIcon("/icons/nam/Permission16.gif");
		setSectionTitle("Permission Overview");
		String permissionName = PermissionUtil.getLabel(permission);
		permissionWizard.setNewMode(false);
		
		String pageLevelKey = "permission";
		String wizardLevelKey = "permissionWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Permissions", "showPermissionManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(permissionName, "showPermissionWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showPermissionWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showPermissionWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showPermissionWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showPermissionWizardPage('Documentation')");
		
		permissionOverviewSection.setOwner("permissionWizard");
		permissionIdentificationSection.setOwner("permissionWizard");
		permissionConfigurationSection.setOwner("permissionWizard");
		permissionDocumentationSection.setOwner("permissionWizard");

		sections.clear();
		sections.add(permissionOverviewSection);
		sections.add(permissionIdentificationSection);
		sections.add(permissionConfigurationSection);
		sections.add(permissionDocumentationSection);
		
		String url = getPermissionWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("permission");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}

	public String initializePermissionManagementPage() {
		setPageTitle("Permissions");
		setPageIcon("/icons/nam/Permission16.gif");
		String pageLevelKey = "permissionManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Permissions", "showPermissionManagementPage()");
		String url = getPermissionManagementPage();
		selectionContext.setCurrentArea("permission");
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
		setSectionType("permission");
		setSectionName("Overview");
		setSectionTitle("Overview of Permissions");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializePermissionSummaryView(Permission permission) {
		//String viewTitle = getPermissionLabel(permission);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("permission");
		setSectionName("Summary");
		setSectionTitle("Summary of Permission Records");
		setSectionIcon("/icons/nam/Permission16.gif");
		String viewLevelKey = "permissionSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Permissions", "showPermissionManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getPermissionLabel(Permission permission) {
		String label = "Permission";
		String name = PermissionUtil.getLabel(permission);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Permission> page = permissionWizard.getPage();
		if (page != null)
			setSectionTitle("Permission " + page.getName());
	}
	
	protected void updateState(Permission permission) {
		String permissionName = PermissionUtil.getLabel(permission);
		setSectionTitle(permissionName + " Permission");
	}
	
}
