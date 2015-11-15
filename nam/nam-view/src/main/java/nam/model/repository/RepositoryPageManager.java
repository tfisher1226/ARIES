package nam.model.repository;

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

import nam.model.Repository;
import nam.model.util.RepositoryUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("repositoryPageManager")
public class RepositoryPageManager extends AbstractPageManager<Repository> implements Serializable {
	
	@Inject
	private RepositoryWizard repositoryWizard;
	
	@Inject
	private RepositoryDataManager repositoryDataManager;
	
	@Inject
	private RepositoryListManager repositoryListManager;
	
	@Inject
	private RepositoryRecord_OverviewSection repositoryOverviewSection;
	
	@Inject
	private RepositoryRecord_IdentificationSection repositoryIdentificationSection;
	
	@Inject
	private RepositoryRecord_ConfigurationSection repositoryConfigurationSection;
	
	@Inject
	private RepositoryRecord_DocumentationSection repositoryDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public RepositoryPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("repository");
	}
	
	public void refreshLocal() {
		refreshLocal("repository");
	}
	
	public void refreshMembers() {
		refreshMembers("repository");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		repositoryDataManager.setScope(scope);
		repositoryListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		repositoryListManager.refresh();
	}
	
	public String getRepositoryListPage() {
		return "/nam/model/repository/repositoryListPage.xhtml";
	}
	
	public String getRepositoryTreePage() {
		return "/nam/model/repository/repositoryTreePage.xhtml";
	}
	
	public String getRepositorySummaryPage() {
		return "/nam/model/repository/repositorySummaryPage.xhtml";
	}
	
	public String getRepositoryRecordPage() {
		return "/nam/model/repository/repositoryRecordPage.xhtml";
	}
	
	public String getRepositoryWizardPage() {
		return "/nam/model/repository/repositoryWizardPage.xhtml";
	}
	
	public String getRepositoryManagementPage() {
		return "/nam/model/repository/repositoryManagementPage.xhtml";
	}
	
	public String initializeRepositoryListPage() {
		String pageLevelKey = "repositoryList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Repositorys", "showRepositoryManagementPage()");
		String url = getRepositoryListPage();
		selectionContext.setCurrentArea("repository");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeRepositoryTreePage() {
		String pageLevelKey = "repositoryTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Repositorys", "showRepositoryTreePage()");
		String url = getRepositoryTreePage();
		selectionContext.setCurrentArea("repository");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeRepositorySummaryPage(Repository repository) {
		String pageLevelKey = "repositorySummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Repositorys", "showRepositorySummaryPage()");
		String url = getRepositorySummaryPage();
		selectionContext.setCurrentArea("repository");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeRepositoryRecordPage() {
		Repository repository = selectionContext.getSelection("repository");
		String repositoryName = RepositoryUtil.getLabel(repository);
		
		String pageLevelKey = "repositoryRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Repositorys", "showRepositoryManagementPage()");
		addBreadcrumb(pageLevelKey, repositoryName, "showRepositoryRecordPage()");
		String url = getRepositoryRecordPage();
		selectionContext.setCurrentArea("repository");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeRepositoryCreationPage(Repository repository) {
		setPageTitle("New "+getRepositoryLabel(repository));
		setPageIcon("/icons/nam/NewRepository16.gif");
		setSectionTitle("Repository Identification");
		repositoryWizard.setNewMode(true);
		
		String pageLevelKey = "repository";
		String wizardLevelKey = "repositoryWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Repositorys", "showRepositoryManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Repository", "showRepositoryWizardPage()"));
		
		
		repositoryIdentificationSection.setOwner("repositoryWizard");
		repositoryConfigurationSection.setOwner("repositoryWizard");
		repositoryDocumentationSection.setOwner("repositoryWizard");
		
		sections.clear();
		sections.add(repositoryIdentificationSection);
		sections.add(repositoryConfigurationSection);
		sections.add(repositoryDocumentationSection);
		
		String url = getRepositoryWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("repository");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeRepositoryUpdatePage(Repository repository) {
		setPageTitle(getRepositoryLabel(repository));
		setPageIcon("/icons/nam/Repository16.gif");
		setSectionTitle("Repository Overview");
		String repositoryName = RepositoryUtil.getLabel(repository);
		repositoryWizard.setNewMode(false);
		
		String pageLevelKey = "repository";
		String wizardLevelKey = "repositoryWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Repositorys", "showRepositoryManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(repositoryName, "showRepositoryWizardPage()"));
		
		
		repositoryOverviewSection.setOwner("repositoryWizard");
		repositoryIdentificationSection.setOwner("repositoryWizard");
		repositoryConfigurationSection.setOwner("repositoryWizard");
		repositoryDocumentationSection.setOwner("repositoryWizard");
		
		sections.clear();
		sections.add(repositoryOverviewSection);
		sections.add(repositoryIdentificationSection);
		sections.add(repositoryConfigurationSection);
		sections.add(repositoryDocumentationSection);
		
		String url = getRepositoryWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("repository");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeRepositoryManagementPage() {
		setPageTitle("Repositorys");
		setPageIcon("/icons/nam/Repository16.gif");
		String pageLevelKey = "repositoryManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Repositorys", "showRepositoryManagementPage()");
		String url = getRepositoryManagementPage();
		selectionContext.setCurrentArea("repository");
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
		setSectionType("repository");
		setSectionName("Overview");
		setSectionTitle("Overview of Repositorys");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeRepositorySummaryView(Repository repository) {
		//String viewTitle = getRepositoryLabel(repository);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("repository");
		setSectionName("Summary");
		setSectionTitle("Summary of Repository Records");
		setSectionIcon("/icons/nam/Repository16.gif");
		String viewLevelKey = "repositorySummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Repositorys", "showRepositoryManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getRepositoryLabel(Repository repository) {
		String label = "Repository";
		String name = RepositoryUtil.getLabel(repository);
		if (name == null && repository.getName() != null)
			name = RepositoryUtil.getLabel(repository);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Repository> page = repositoryWizard.getPage();
		if (page != null)
			setSectionTitle("Repository " + page.getName());
	}
	
	protected void updateState(Repository repository) {
		String repositoryName = RepositoryUtil.getLabel(repository);
		setSectionTitle(repositoryName + " Repository");
	}
	
}
