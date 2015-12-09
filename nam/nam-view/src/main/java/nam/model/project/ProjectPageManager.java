package nam.model.project;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Unselected;

import nam.model.Project;
import nam.model.application.ApplicationPageManager;
import nam.model.module.ModulePageManager;
import nam.model.network.NetworkPageManager;
import nam.model.provider.ProviderPageManager;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("projectPageManager")
public class ProjectPageManager extends AbstractPageManager<Project> implements Serializable {
	
	@Inject
	private ProjectWizard projectWizard;
	
	@Inject
	private ProjectDataManager projectDataManager;

	@Inject
	private ProjectInfoManager projectInfoManager;

	@Inject
	private ProjectListManager projectListManager;

	@Inject
	private ApplicationPageManager applicationPageManager;

	@Inject
	private ModulePageManager modulePageManager;

	@Inject
	private NetworkPageManager networkPageManager;
	
	@Inject
	private ProviderPageManager providerPageManager;

	@Inject
	private ProjectRecord_OverviewSection projectOverviewSection;
	
	@Inject
	private ProjectRecord_IdentificationSection projectIdentificationSection;
	
	@Inject
	private ProjectRecord_ConfigurationSection projectConfigurationSection;
	
	@Inject
	private ProjectRecord_DocumentationSection projectDocumentationSection;

	@Inject
	private ProjectRecord_ApplicationsSection projectApplicationsSection;

	@Inject
	private ProjectRecord_ModulesSection projectModulesSection;

	@Inject
	private ProjectRecord_NetworksSection projectNetworksSection;
	
	@Inject
	private ProjectRecord_ProvidersSection projectProvidersSection;

	@Inject
	private SelectionContext selectionContext;
	

	public ProjectPageManager() {
		initializeSections();
	}
	
	
	public void refresh() {
		refresh("projectList");
	}
	
	public void refreshLocal() {
		refreshLocal("project");
	}
	
	public void refreshMembers() {
		refreshMembers("project");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		projectDataManager.setScope(scope);
		projectListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		applicationPageManager.refreshLocal(scope);
		modulePageManager.refreshLocal(scope);
		networkPageManager.refreshLocal(scope);
		providerPageManager.refreshLocal(scope);
	}
	
	public String getProjectListPage() {
		return "/nam/model/project/projectListPage.xhtml";
	}
	
	public String getProjectTreePage() {
		return "/nam/model/project/projectTreePage.xhtml";
	}

	public String getProjectSummaryPage() {
		return "/nam/model/project/projectSummaryPage.xhtml";
	}
	
	public String getProjectRecordPage() {
		return "/nam/model/project/projectRecordPage.xhtml";
	}

	public String getProjectWizardPage() {
		return "/nam/model/project/projectWizardPage.xhtml";
	}

	public String getProjectManagementPage() {
		return "/nam/model/project/projectManagementPage.xhtml";
	}
	
	public void handleProjectSelected(@Observes @Selected Project project) {
		selectionContext.setSelection("project",  project);
		projectInfoManager.setRecord(project);
	}
	
	public void handleProjectUnselected(@Observes @Unselected Project project) {
		selectionContext.unsetSelection("project",  project);
		projectInfoManager.unsetRecord(project);
	}
	
	public void handleProjectChecked() {
		String scope = "projectSelection";
		ProjectListObject listObject = projectListManager.getSelection();
		Project project = selectionContext.getSelection("project");
		boolean checked = projectListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			projectInfoManager.setRecord(project);
			selectionContext.setSelection(scope,  project);
		} else {
			projectInfoManager.unsetRecord(project);
			selectionContext.unsetSelection(scope,  project);
		}
		String target = selectionContext.getCurrentTarget();
		if (target.equals("application"))
			applicationPageManager.refreshLocal(scope);
		if (target.equals("module"))
			modulePageManager.refreshLocal(scope);
		if (target.equals("network"))
			networkPageManager.refreshLocal(scope);
		if (target.equals("provider"))
			providerPageManager.refreshLocal(scope);
		refreshLocal(scope);
	}
	
	public String initializeProjectListPage() {
		String pageLevelKey = "projectList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Projects", "showProjectManagementPage()");
		String url = getProjectListPage();
		selectionContext.setCurrentArea("project");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeProjectTreePage() {
		String pageLevelKey = "projectTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Projects", "showProjectTreePage()");
		String url = getProjectTreePage();
		selectionContext.setCurrentArea("project");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeProjectSummaryPage(Project project) {
		String pageLevelKey = "projectSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Projects", "showProjectSummaryPage()");
		String url = getProjectSummaryPage();
		selectionContext.setCurrentArea("project");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeProjectRecordPage() {
		Project project = selectionContext.getSelection("project");
		String projectName = ProjectUtil.getLabel(project);
		
		String pageLevelKey = "projectRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Projects", "showProjectManagementPage()");
		addBreadcrumb(pageLevelKey, projectName, "showProjectRecordPage()");
		String url = getProjectRecordPage();
		selectionContext.setCurrentArea("project");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeProjectCreationPage(Project project) {
		setPageTitle("New "+getProjectLabel(project));
		setPageIcon("/icons/nam/NewProject16.gif");
		setSectionTitle("Project Identification");
		projectWizard.setNewMode(true);
		
		String pageLevelKey = "project";
		String wizardLevelKey = "projectWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Projects", "showProjectManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Project", "showProjectWizardPage()"));
		
		//addBreadcrumb(wizardLevelKey, "Applications", "showProjectWizardPage('Applications')");
		//addBreadcrumb(wizardLevelKey, "Modules", "showProjectWizardPage('Modules')");
		//addBreadcrumb(wizardLevelKey, "Networks", "showProjectWizardPage('Networks')");
		//addBreadcrumb(wizardLevelKey, "Providers", "showProjectWizardPage('Providers')");

		projectIdentificationSection.setOwner("projectWizard");
		projectConfigurationSection.setOwner("projectWizard");
		projectDocumentationSection.setOwner("projectWizard");
		projectApplicationsSection.setOwner("projectWizard");
		projectModulesSection.setOwner("projectWizard");
		projectNetworksSection.setOwner("projectWizard");
		projectProvidersSection.setOwner("projectWizard");

		sections.clear();
		sections.add(projectIdentificationSection);
		sections.add(projectConfigurationSection);
		sections.add(projectDocumentationSection);
		sections.add(projectApplicationsSection);
		sections.add(projectModulesSection);
		sections.add(projectNetworksSection);
		sections.add(projectProvidersSection);
		
		String url = getProjectWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("project");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}

	public String initializeProjectUpdatePage(Project project) {
		setPageTitle(getProjectLabel(project));
		setPageIcon("/icons/nam/Project16.gif");
		setSectionTitle("Project Overview");
		String projectName = ProjectUtil.getLabel(project);
		projectWizard.setNewMode(false);
		
		String pageLevelKey = "project";
		String wizardLevelKey = "projectWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Projects", "showProjectManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(projectName, "showProjectWizardPage()"));
		
		//addBreadcrumb(wizardLevelKey, "Applications", "showProjectWizardPage('Applications')");
		//addBreadcrumb(wizardLevelKey, "Modules", "showProjectWizardPage('Modules')");
		//addBreadcrumb(wizardLevelKey, "Networks", "showProjectWizardPage('Networks')");
		//addBreadcrumb(wizardLevelKey, "Providers", "showProjectWizardPage('Providers')");
		
		projectOverviewSection.setOwner("projectWizard");
		projectIdentificationSection.setOwner("projectWizard");
		projectConfigurationSection.setOwner("projectWizard");
		projectDocumentationSection.setOwner("projectWizard");
		projectApplicationsSection.setOwner("projectWizard");
		projectModulesSection.setOwner("projectWizard");
		projectNetworksSection.setOwner("projectWizard");
		projectProvidersSection.setOwner("projectWizard");

		sections.clear();
		sections.add(projectOverviewSection);
		sections.add(projectIdentificationSection);
		sections.add(projectConfigurationSection);
		sections.add(projectDocumentationSection);
		sections.add(projectApplicationsSection);
		sections.add(projectModulesSection);
		sections.add(projectNetworksSection);
		sections.add(projectProvidersSection);
		
		String url = getProjectWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("project");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeProjectManagementPage() {
		setPageTitle("Projects");
		setPageIcon("/icons/nam/Project16.gif");
		String pageLevelKey = "projectManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Projects", "showProjectManagementPage()");
		String url = getProjectManagementPage();
		selectionContext.setCurrentArea("project");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Projects");
		setPageIcon("/icons/nam/Project16.gif");
		setSectionType("project");
		setSectionName("Overview");
		setSectionTitle("Overview of Projects");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "projectOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Projects", "showProjectManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializeProjectApplicationsView() {
		setSectionType("project");
		setSectionName("Applications");
		setSectionTitle("Applications");
		setSectionIcon("/icons/nam/Application16.gif");
		selectionContext.setMessageDomain("projectApplications");
		applicationPageManager.refreshLocal("projectSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeProjectModulesView() {
		setSectionType("project");
		setSectionName("Modules");
		setSectionTitle("Modules");
		setSectionIcon("/icons/nam/Module16.gif");
		selectionContext.setMessageDomain("projectModules");
		modulePageManager.refreshLocal("projectSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeProjectNetworksView() {
		setSectionType("project");
		setSectionName("Networks");
		setSectionTitle("Networks");
		setSectionIcon("/icons/nam/Network16.gif");
		selectionContext.setMessageDomain("projectNetworks");
		networkPageManager.refreshLocal("projectSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeProjectProvidersView() {
		setSectionType("project");
		setSectionName("Providers");
		setSectionTitle("Providers");
		setSectionIcon("/icons/nam/Provider16.gif");
		selectionContext.setMessageDomain("projectProviders");
		providerPageManager.refreshLocal("projectSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeProjectSummaryView(Project project) {
		//String viewTitle = getProjectLabel(project);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("project");
		setSectionName("Summary");
		setSectionTitle("Summary of Project Records");
		setSectionIcon("/icons/nam/Project16.gif");
		String viewLevelKey = "projectSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Projects", "showProjectManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getProjectLabel(Project project) {
		String label = "Project";
		String name = ProjectUtil.getLabel(project);
		if (name == null && project.getName() != null)
			name = ProjectUtil.getLabel(project);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Project> page = projectWizard.getPage();
		if (page != null)
			setSectionTitle("Project " + page.getName());
	}
	
	protected void updateState(Project project) {
		String projectName = ProjectUtil.getLabel(project);
		setSectionTitle(projectName + " Project");
	}

}
