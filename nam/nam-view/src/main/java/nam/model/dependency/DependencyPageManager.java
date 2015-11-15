package nam.model.dependency;

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

import nam.model.Dependency;
import nam.model.util.DependencyUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("dependencyPageManager")
public class DependencyPageManager extends AbstractPageManager<Dependency> implements Serializable {
	
	@Inject
	private DependencyWizard dependencyWizard;
	
	@Inject
	private DependencyDataManager dependencyDataManager;
	
	@Inject
	private DependencyListManager dependencyListManager;
	
	@Inject
	private DependencyRecord_OverviewSection dependencyOverviewSection;
	
	@Inject
	private DependencyRecord_IdentificationSection dependencyIdentificationSection;
	
	@Inject
	private DependencyRecord_ConfigurationSection dependencyConfigurationSection;
	
	@Inject
	private DependencyRecord_DocumentationSection dependencyDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public DependencyPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("dependency");
	}
	
	public void refreshLocal() {
		refreshLocal("dependency");
	}
	
	public void refreshMembers() {
		refreshMembers("dependency");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		dependencyDataManager.setScope(scope);
		dependencyListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		dependencyListManager.refresh();
	}
	
	public String getDependencyListPage() {
		return "/nam/model/dependency/dependencyListPage.xhtml";
	}
	
	public String getDependencyTreePage() {
		return "/nam/model/dependency/dependencyTreePage.xhtml";
	}
	
	public String getDependencySummaryPage() {
		return "/nam/model/dependency/dependencySummaryPage.xhtml";
	}
	
	public String getDependencyRecordPage() {
		return "/nam/model/dependency/dependencyRecordPage.xhtml";
	}
	
	public String getDependencyWizardPage() {
		return "/nam/model/dependency/dependencyWizardPage.xhtml";
	}
	
	public String getDependencyManagementPage() {
		return "/nam/model/dependency/dependencyManagementPage.xhtml";
	}
	
	public String initializeDependencyListPage() {
		String pageLevelKey = "dependencyList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Dependencys", "showDependencyManagementPage()");
		String url = getDependencyListPage();
		selectionContext.setCurrentArea("dependency");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeDependencyTreePage() {
		String pageLevelKey = "dependencyTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Dependencys", "showDependencyTreePage()");
		String url = getDependencyTreePage();
		selectionContext.setCurrentArea("dependency");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeDependencySummaryPage(Dependency dependency) {
		String pageLevelKey = "dependencySummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Dependencys", "showDependencySummaryPage()");
		String url = getDependencySummaryPage();
		selectionContext.setCurrentArea("dependency");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeDependencyRecordPage() {
		Dependency dependency = selectionContext.getSelection("dependency");
		String dependencyName = DependencyUtil.getLabel(dependency);
		
		String pageLevelKey = "dependencyRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Dependencys", "showDependencyManagementPage()");
		addBreadcrumb(pageLevelKey, dependencyName, "showDependencyRecordPage()");
		String url = getDependencyRecordPage();
		selectionContext.setCurrentArea("dependency");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeDependencyCreationPage(Dependency dependency) {
		setPageTitle("New "+getDependencyLabel(dependency));
		setPageIcon("/icons/nam/NewDependency16.gif");
		setSectionTitle("Dependency Identification");
		dependencyWizard.setNewMode(true);
		
		String pageLevelKey = "dependency";
		String wizardLevelKey = "dependencyWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Dependencys", "showDependencyManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Dependency", "showDependencyWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showDependencyWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showDependencyWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showDependencyWizardPage('Documentation')");
		
		dependencyIdentificationSection.setOwner("dependencyWizard");
		dependencyConfigurationSection.setOwner("dependencyWizard");
		dependencyDocumentationSection.setOwner("dependencyWizard");
		
		sections.clear();
		sections.add(dependencyIdentificationSection);
		sections.add(dependencyConfigurationSection);
		sections.add(dependencyDocumentationSection);
		
		String url = getDependencyWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("dependency");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeDependencyUpdatePage(Dependency dependency) {
		setPageTitle(getDependencyLabel(dependency));
		setPageIcon("/icons/nam/Dependency16.gif");
		setSectionTitle("Dependency Overview");
		String dependencyName = DependencyUtil.getLabel(dependency);
		dependencyWizard.setNewMode(false);
		
		String pageLevelKey = "dependency";
		String wizardLevelKey = "dependencyWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Dependencys", "showDependencyManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(dependencyName, "showDependencyWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showDependencyWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showDependencyWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showDependencyWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showDependencyWizardPage('Documentation')");
		
		dependencyOverviewSection.setOwner("dependencyWizard");
		dependencyIdentificationSection.setOwner("dependencyWizard");
		dependencyConfigurationSection.setOwner("dependencyWizard");
		dependencyDocumentationSection.setOwner("dependencyWizard");
		
		sections.clear();
		sections.add(dependencyOverviewSection);
		sections.add(dependencyIdentificationSection);
		sections.add(dependencyConfigurationSection);
		sections.add(dependencyDocumentationSection);
		
		String url = getDependencyWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("dependency");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeDependencyManagementPage() {
		setPageTitle("Dependencys");
		setPageIcon("/icons/nam/Dependency16.gif");
		String pageLevelKey = "dependencyManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Dependencys", "showDependencyManagementPage()");
		String url = getDependencyManagementPage();
		selectionContext.setCurrentArea("dependency");
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
		setSectionType("dependency");
		setSectionName("Overview");
		setSectionTitle("Overview of Dependencys");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeDependencySummaryView(Dependency dependency) {
		//String viewTitle = getDependencyLabel(dependency);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("dependency");
		setSectionName("Summary");
		setSectionTitle("Summary of Dependency Records");
		setSectionIcon("/icons/nam/Dependency16.gif");
		String viewLevelKey = "dependencySummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Dependencys", "showDependencyManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getDependencyLabel(Dependency dependency) {
		String label = "Dependency";
		String name = DependencyUtil.getLabel(dependency);
		if (name == null && dependency.getName() != null)
			name = DependencyUtil.getLabel(dependency);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Dependency> page = dependencyWizard.getPage();
		if (page != null)
			setSectionTitle("Dependency " + page.getName());
	}
	
	protected void updateState(Dependency dependency) {
		String dependencyName = DependencyUtil.getLabel(dependency);
		setSectionTitle(dependencyName + " Dependency");
	}
	
}
