package nam.model.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.component.ComponentPageManager;
import nam.model.domain.DomainPageManager;
import nam.model.element.ElementPageManager;
import nam.model.module.ModulePageManager;
import nam.model.namespace.NamespacePageManager;
import nam.model.project.ProjectPageManager;
import nam.model.provider.ProviderPageManager;
import nam.model.service.ServicePageManager;
import nam.model.type.TypePageManager;
import nam.model.util.ApplicationUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.event.Checked;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Unchecked;
import org.aries.ui.event.Unselected;


@SessionScoped
@Named("applicationPageManager")
public class ApplicationPageManager extends AbstractPageManager<Application> implements Serializable {

	@Inject
	private ApplicationWizard applicationWizard;
	
	@Inject
	private ApplicationDataManager applicationDataManager;

	@Inject
	private ApplicationInfoManager applicationInfoManager;
	
	@Inject
	private ApplicationListManager applicationListManager;
	
	@Inject
	private ProjectPageManager projectPageManager;
	
	@Inject
	private DomainPageManager domainPageManager;
	
	@Inject
	private ModulePageManager modulePageManager;
	
	@Inject
	private ServicePageManager servicePageManager;
	
	@Inject
	private NamespacePageManager namespacePageManager;
	
	@Inject
	private ElementPageManager elementPageManager;
	
	@Inject
	private TypePageManager typePageManager;
	
	@Inject
	private ComponentPageManager componentPageManager;
	
	@Inject
	private ProviderPageManager providerPageManager;
	
	@Inject
	private ApplicationRecord_OverviewSection applicationOverviewSection;
	
	@Inject
	private ApplicationRecord_IdentificationSection applicationIdentificationSection;
	
	@Inject
	private ApplicationRecord_ConfigurationSection applicationConfigurationSection;
	
	@Inject
	private ApplicationRecord_DocumentationSection applicationDocumentationSection;
	
	@Inject
	private ApplicationRecord_ModulesSection applicationModulesSection;
	
	@Inject
	private ApplicationRecord_ServicesSection applicationServicesSection;

	@Inject
	private ApplicationRecord_ElementsSection applicationElementsSection;
	
	@Inject
	private ApplicationRecord_ComponentsSection applicationComponentsSection;
	
	@Inject
	private ApplicationRecord_ProvidersSection applicationProvidersSection;
	
	@Inject
	private ApplicationRecord_ProjectSelectionSection applicationProjectSelectionSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ApplicationPageManager() {
		initializeSections();
	}
	
	
	public void refresh() {
		refresh("application");
	}

	public void refreshLocal() {
		refreshLocal("projectList");
	}
	
	public void refreshMembers() {
		refreshMembers("application");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		applicationDataManager.setScope(scope);
		applicationListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		modulePageManager.refreshLocal(scope);
		servicePageManager.refreshLocal(scope);
		elementPageManager.refreshLocal(scope);
		typePageManager.refreshLocal(scope);
		namespacePageManager.refreshLocal(scope);
		componentPageManager.refreshLocal(scope);
		providerPageManager.refreshLocal(scope);
	}
	
	public String getApplicationListPage() {
		return "/nam/model/application/applicationListPage.xhtml";
	}

	public String getApplicationTreePage() {
		return "/nam/model/application/applicationTreePage.xhtml";
	}

	public String getApplicationSummaryPage() {
		return "/nam/model/application/applicationSummaryPage.xhtml";
	}

	public String getApplicationRecordPage() {
		return "/nam/model/application/applicationRecordPage.xhtml";
	}
	
	public String getApplicationWizardPage() {
		return "/nam/model/application/applicationWizardPage.xhtml";
	}
	
	public String getApplicationManagementPage() {
		return "/nam/model/application/applicationManagementPage.xhtml";
	}
	
	public String getProjectSelectionPage() {
		return getApplicationWizardPage() + "?section=projectSelection";
	}

	public void handleApplicationSelected(@Observes @Selected Application application) {
		selectionContext.setSelection("application",  application);
		applicationInfoManager.setRecord(application);
	}
	
	public void handleApplicationUnselected(@Observes @Unselected Application application) {
		selectionContext.unsetSelection("application",  application);
		applicationInfoManager.unsetRecord(application);
	}
	
	public void handleApplicationChecked() {
		String scope = "applicationSelection";
		ApplicationListObject listObject = applicationListManager.getSelection();
		Application application = selectionContext.getSelection("application");
		boolean checked = applicationListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			applicationInfoManager.setRecord(application);
			selectionContext.setSelection(scope,  application);
		} else {
			applicationInfoManager.unsetRecord(application);
			selectionContext.unsetSelection(scope,  application);
		}
		String target = selectionContext.getCurrentTarget();
		if (target.equals("module"))
			modulePageManager.refreshLocal(scope);
		if (target.equals("service"))
			servicePageManager.refreshLocal(scope);
		if (target.equals("element"))
			elementPageManager.refreshLocal(scope);
		if (target.equals("component"))
			componentPageManager.refreshLocal(scope);
		if (target.equals("provider"))
			providerPageManager.refreshLocal(scope);
		refreshLocal(scope);
	}
	
	public String initializeApplicationListPage() {
		String pageLevelKey = "applicationList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Applications", "showApplicationManagementPage()");
		String url = getApplicationListPage();
		selectionContext.setCurrentArea("application");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
		return url;
	}
	
	public String initializeApplicationTreePage() {
		setPageTitle("Applications");
		setPageIcon("/icons/nam/Application16.gif");
		setSectionTitle("Bookshop2 Applications"); 
		String pageLevelKey = "applicationTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Bookshop2", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Applications", "showApplicationTreePage()");
		String url = getApplicationTreePage();
		selectionContext.setCurrentArea("application");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		ApplicationTreeManager applicationTreeManager = BeanContext.getFromSession("applicationTreeManager");
		applicationTreeManager.refreshModel();
		return url;
	}

	public String initializeApplicationSummaryPage(Application application) {
		String pageLevelKey = "applicationSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Applications", "showApplicationSummaryPage()");
		String url = getApplicationSummaryPage();
		selectionContext.setCurrentArea("application");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeApplicationRecordPage() {
		Application application = selectionContext.getSelection("application");
		String applicationName = ApplicationUtil.getLabel(application);
		
		String pageLevelKey = "applicationRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Applications", "showApplicationManagementPage()");
		addBreadcrumb(pageLevelKey, applicationName, "showApplicationRecordPage()");
		String url = getApplicationRecordPage();
		selectionContext.setCurrentArea("application");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeApplicationCreationPage(Application application) {
		setPageTitle("New "+getApplicationLabel(application));
		setPageIcon("/icons/nam/NewApplication16.gif");
		setSectionTitle("Application Identification"); 
		applicationWizard.setNewMode(true);
		
		String pageLevelKey = "application";
		String wizardLevelKey = "applicationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Applications", "showApplicationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Application", "showApplicationWizardPage()"));
		
//		addBreadcrumb(wizardLevelKey, "Modules", "showApplicationWizardPage('Modules')");
//		addBreadcrumb(wizardLevelKey, "Services", "showApplicationWizardPage('Services')");
//		addBreadcrumb(wizardLevelKey, "Elements", "showApplicationWizardPage('Elements')");
//		addBreadcrumb(wizardLevelKey, "Components", "showApplicationWizardPage('Components')");
//		addBreadcrumb(wizardLevelKey, "Providers", "showApplicationWizardPage('Providers')");

		applicationIdentificationSection.setOwner("applicationWizard");
		applicationConfigurationSection.setOwner("applicationWizard");
		applicationDocumentationSection.setOwner("applicationWizard");
		applicationModulesSection.setOwner("applicationWizard");
		applicationServicesSection.setOwner("applicationWizard");
		applicationElementsSection.setOwner("applicationWizard");
		applicationComponentsSection.setOwner("applicationWizard");
		applicationProvidersSection.setOwner("applicationWizard");

		sections.clear();
		sections.add(applicationIdentificationSection);
		sections.add(applicationConfigurationSection);
		sections.add(applicationDocumentationSection);
		sections.add(applicationModulesSection);
		sections.add(applicationServicesSection);
		sections.add(applicationElementsSection);
		sections.add(applicationComponentsSection);
		sections.add(applicationProvidersSection);
		
		String url = getApplicationWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("application");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeApplicationUpdatePage(Application application) {
		setPageTitle(getApplicationLabel(application));
		setPageIcon("/icons/nam/Application16.gif");
		setSectionTitle("Application Overview");
		String applicationName = ApplicationUtil.getLabel(application);
		applicationWizard.setNewMode(false);

		String pageLevelKey = "application";
		String wizardLevelKey = "applicationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);

		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Applications", "showApplicationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(applicationName, "showApplicationWizardPage()"));
		
//		addBreadcrumb(wizardLevelKey, "Modules", "showApplicationWizardPage('Modules')");
//		addBreadcrumb(wizardLevelKey, "Services", "showApplicationWizardPage('Services')");
//		addBreadcrumb(wizardLevelKey, "Elements", "showApplicationWizardPage('Elements')");
//		addBreadcrumb(wizardLevelKey, "Components", "showApplicationWizardPage('Components')");
//		addBreadcrumb(wizardLevelKey, "Providers", "showApplicationWizardPage('Providers')");
		
		applicationOverviewSection.setOwner("applicationWizard");
		applicationIdentificationSection.setOwner("applicationWizard");
		applicationConfigurationSection.setOwner("applicationWizard");
		applicationDocumentationSection.setOwner("applicationWizard");
		applicationModulesSection.setOwner("applicationWizard");
		applicationServicesSection.setOwner("applicationWizard");
		applicationElementsSection.setOwner("applicationWizard");
		applicationComponentsSection.setOwner("applicationWizard");
		applicationProvidersSection.setOwner("applicationWizard");

		sections.clear();
		sections.add(applicationOverviewSection);
		sections.add(applicationIdentificationSection);
		sections.add(applicationConfigurationSection);
		sections.add(applicationDocumentationSection);
		sections.add(applicationModulesSection);
		sections.add(applicationServicesSection);
		sections.add(applicationElementsSection);
		sections.add(applicationComponentsSection);
		sections.add(applicationProvidersSection);
		
		String url = getApplicationWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("application");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeApplicationManagementPage() {
		setPageTitle("Applications");
		setPageIcon("/icons/nam/Application16.gif");
		String pageLevelKey = "applicationManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Applications", "showApplicationManagementPage()");
		String url = getApplicationManagementPage();
		selectionContext.setCurrentArea("application");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		//refreshLocal();
		return url;
	}
	
	public String initializeProjectSelectionPage() {
		setPageTitle("New Application");
		setPageIcon("/icons/nam/Application16.gif");
		setSectionTitle("Project Selection"); 
		String pageLevelKey = "applicationWizard";
		clearBreadcrumbs(pageLevelKey);
		sections.clear();
		sections.add(applicationProjectSelectionSection);
		applicationProjectSelectionSection.setOwner("applicationWizard");
		//applicationWizard.setModuleType(null);
		String url = getProjectSelectionPage();
		selectionContext.setCurrentArea("application");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		applicationWizard.assignPages(sections);
		applicationWizard.initialize();
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Applications");
		setPageIcon("/icons/nam/Application16.gif");
		setSectionType("application"); 
		setSectionName("Overview"); 
		setSectionTitle("Overview of Applications");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "applicationOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Applications", "showApplicationManagementPage()");
		String scope = "projectList";
		projectPageManager.refreshLocal(scope);
		applicationDataManager.setScope(scope);
		applicationListManager.refresh();
		sections.clear();
	}
	
	public String initializeApplicationDomainsView() {
		setSectionType("application"); 
		setSectionName("Domains"); 
		setSectionTitle("Domains");
		setSectionIcon("/icons/nam/ApplicationDomain16.gif"); 
		String url = getApplicationManagementPage();
		selectionContext.setMessageDomain("applicationDomains");
		domainPageManager.refreshLocal("projectList");
		refreshLocal("domainSelection");
		sections.clear();
		return url;
	}
	
	public String initializeApplicationModulesView() {
		setSectionType("application"); 
		setSectionName("Modules"); 
		setSectionTitle("Modules");
		setSectionIcon("/icons/nam/Module16.gif"); 
		String url = getApplicationManagementPage();
		selectionContext.setMessageDomain("applicationModules");
		modulePageManager.refreshLocal("applicationSelection");
		refreshLocal("projectList");
		sections.clear();
		return url;
	}
	
	public String initializeApplicationServicesView() {
		setSectionType("application"); 
		setSectionName("Services"); 
		setSectionTitle("Services");
		setSectionIcon("/icons/nam/Service16.gif"); 
		String url = getApplicationManagementPage();
		selectionContext.setMessageDomain("applicationServices");
		servicePageManager.refreshLocal("applicationSelection");
		refreshLocal("projectList");
		sections.clear();
		return url;
	}

	public String initializeApplicationElementsView() {
		setSectionType("application"); 
		setSectionName("Elements"); 
		setSectionTitle("Elements");
		setSectionIcon("/icons/nam/Element16.gif"); 
		String url = getApplicationManagementPage();
		selectionContext.setMessageDomain("applicationElements");
		namespacePageManager.refreshLocal("applicationSelection");
		elementPageManager.refreshLocal("namespaceSelection");
		refreshLocal("projectList");
		sections.clear();
		return url;
	}
	
	public String initializeApplicationComponentsView() {
		setSectionType("application"); 
		setSectionName("Components"); 
		setSectionTitle("Components");
		setSectionIcon("/icons/nam/Component16.gif"); 
		String url = getApplicationManagementPage();
		selectionContext.setMessageDomain("applicationComponents");
		componentPageManager.refreshLocal("applicationSelection");
		refreshLocal("projectList");
		sections.clear();
		return url;
	}
	
	public String initializeApplicationProvidersView() {
		setSectionType("application"); 
		setSectionName("Providers"); 
		setSectionTitle("Providers");
		setSectionIcon("/icons/nam/Provider16.gif"); 
		String url = getApplicationManagementPage();
		selectionContext.setMessageDomain("applicationProviders");
		providerPageManager.refreshLocal("applicationSelection");
		refreshLocal("projectList");
		sections.clear();
		return url;
	}
	
	public String initializeApplicationSummaryView(Application application) {
		//String viewTitle = getApplicationLabel(application);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("application"); 
		setSectionName("Summary"); 
		setSectionTitle("Summary of Application Records");
		setSectionIcon("/icons/nam/Application16.gif");
		String url = getApplicationManagementPage();
		String viewLevelKey = "applicationSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Applications", "showApplicationManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return url;
	}
	
	protected String getApplicationLabel(Application application) {
		String label = "Application";
		String name = ApplicationUtil.getLabel(application);
		if (name == null && application.getName() != null)
			name = ApplicationUtil.getLabel(application);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Application> page = applicationWizard.getPage();
		if (page != null)
			setSectionTitle("Application " + page.getName()); 
	}

	protected void updateState(Application application) {
		String applicationName = ApplicationUtil.getLabel(application);
		setSectionTitle(applicationName + " Application");
	}

}
