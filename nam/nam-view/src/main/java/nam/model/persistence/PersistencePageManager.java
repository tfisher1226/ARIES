package nam.model.persistence;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Persistence;
import nam.model._import.ImportPageManager;
import nam.model.adapter.AdapterPageManager;
import nam.model.domain.DomainPageManager;
import nam.model.provider.ProviderPageManager;
import nam.model.query.QueryPageManager;
import nam.model.repository.RepositoryPageManager;
import nam.model.source.SourcePageManager;
import nam.model.unit.UnitPageManager;
import nam.model.util.PersistenceUtil;
import nam.ui.design.SelectionContext;

import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;


@SessionScoped
@Named("persistencePageManager")
public class PersistencePageManager extends AbstractPageManager<Persistence> implements Serializable {
	
	@Inject
	private PersistenceWizard persistenceWizard;
	
	@Inject
	private PersistenceDataManager persistenceDataManager;
	
	@Inject
	private PersistenceListManager persistenceListManager;
	
	@Inject
	private ImportPageManager importPageManager;
	
	@Inject
	private DomainPageManager domainPageManager;
	
	@Inject
	private UnitPageManager unitPageManager;
	
	@Inject
	private SourcePageManager sourcePageManager;
	
	@Inject
	private AdapterPageManager adapterPageManager;
	
	@Inject
	private QueryPageManager queryPageManager;

	@Inject
	private RepositoryPageManager repositoryPageManager;
	
	@Inject
	private ProviderPageManager providerPageManager;
	
	@Inject
	private PersistenceRecord_OverviewSection persistenceOverviewSection;
	
	@Inject
	private PersistenceRecord_IdentificationSection persistenceIdentificationSection;
	
	@Inject
	private PersistenceRecord_ConfigurationSection persistenceConfigurationSection;
	
	@Inject
	private PersistenceRecord_DocumentationSection persistenceDocumentationSection;
	
	@Inject
	private PersistenceRecord_ImportsSection persistenceImportsSection;
	
	@Inject
	private PersistenceRecord_DomainsSection persistenceDomainsSection;
	
	@Inject
	private PersistenceRecord_UnitsSection persistenceUnitsSection;
	
	@Inject
	private PersistenceRecord_SourcesSection persistenceSourcesSection;
	
	@Inject
	private PersistenceRecord_AdaptersSection persistenceAdaptersSection;
	
	@Inject
	private PersistenceRecord_EntitiesSection persistenceEntitiesSection;
	
	@Inject
	private PersistenceRecord_QueriesSection persistenceQueriesSection;
	
	@Inject
	private PersistenceRecord_RepositoriesSection persistenceRepositoriesSection;
	
	@Inject
	private PersistenceRecord_ProvidersSection persistenceProvidersSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PersistencePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("persistence");
	}
	
	public void refreshLocal() {
		refreshLocal("persistence");
	}
	
	public void refreshMembers() {
		refreshMembers("persistence");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		persistenceDataManager.setScope(scope);
		persistenceListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		importPageManager.refresh(scope);
		domainPageManager.refresh(scope);
		unitPageManager.refresh(scope);
		sourcePageManager.refresh(scope);
		adapterPageManager.refresh(scope);
		//entityPageManager.refresh(scope);
		queryPageManager.refresh(scope);
		repositoryPageManager.refresh(scope);
		providerPageManager.refresh(scope);
		persistenceListManager.refresh();
	}
	
	public String getPersistenceListPage() {
		return "/nam/model/persistence/persistenceListPage.xhtml";
	}
	
	public String getPersistenceTreePage() {
		return "/nam/model/persistence/persistenceTreePage.xhtml";
	}
	
	public String getPersistenceSummaryPage() {
		return "/nam/model/persistence/persistenceSummaryPage.xhtml";
	}
	
	public String getPersistenceRecordPage() {
		return "/nam/model/persistence/persistenceRecordPage.xhtml";
	}
	
	public String getPersistenceWizardPage() {
		return "/nam/model/persistence/persistenceWizardPage.xhtml";
	}
	
	public String getPersistenceManagementPage() {
		return "/nam/model/persistence/persistenceManagementPage.xhtml";
	}
	
	public String initializePersistenceListPage() {
		String pageLevelKey = "persistenceList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Persistence", "showPersistenceManagementPage()");
		String url = getPersistenceListPage();
		selectionContext.setCurrentArea("persistence");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePersistenceTreePage() {
		String pageLevelKey = "persistenceTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Persistence", "showPersistenceTreePage()");
		String url = getPersistenceTreePage();
		selectionContext.setCurrentArea("persistence");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePersistenceSummaryPage(Persistence persistence) {
		String pageLevelKey = "persistenceSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Persistence", "showPersistenceSummaryPage()");
		String url = getPersistenceSummaryPage();
		selectionContext.setCurrentArea("persistence");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePersistenceRecordPage() {
		Persistence persistence = selectionContext.getSelection("persistence");
		String persistenceName = PersistenceUtil.getLabel(persistence);
		
		String pageLevelKey = "persistenceRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Persistence", "showPersistenceManagementPage()");
		addBreadcrumb(pageLevelKey, persistenceName, "showPersistenceRecordPage()");
		String url = getPersistenceRecordPage();
		selectionContext.setCurrentArea("persistence");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializePersistenceCreationPage(Persistence persistence) {
		setPageTitle("New "+getPersistenceLabel(persistence));
		setPageIcon("/icons/nam/NewPersistence16.gif");
		setSectionTitle("Persistence Identification");
		persistenceWizard.setNewMode(true);
		
		String pageLevelKey = "persistence";
		String wizardLevelKey = "persistenceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Persistence", "showPersistenceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Persistence", "showPersistenceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Imports", "showPersistenceWizardPage('Imports')");
		addBreadcrumb(wizardLevelKey, "Domains", "showPersistenceWizardPage('Domains')");
		addBreadcrumb(wizardLevelKey, "Units", "showPersistenceWizardPage('Units')");
		addBreadcrumb(wizardLevelKey, "Sources", "showPersistenceWizardPage('Sources')");
		addBreadcrumb(wizardLevelKey, "Adapters", "showPersistenceWizardPage('Adapters')");
		addBreadcrumb(wizardLevelKey, "Entities", "showPersistenceWizardPage('Entities')");
		addBreadcrumb(wizardLevelKey, "Queries", "showPersistenceWizardPage('Queries')");
		addBreadcrumb(wizardLevelKey, "Repositories", "showPersistenceWizardPage('Repositories')");
		addBreadcrumb(wizardLevelKey, "Providers", "showPersistenceWizardPage('Providers')");
		
		persistenceIdentificationSection.setOwner("persistenceWizard");
		persistenceConfigurationSection.setOwner("persistenceWizard");
		persistenceDocumentationSection.setOwner("persistenceWizard");
		persistenceOverviewSection.setOwner("persistenceWizard");
		persistenceImportsSection.setOwner("persistenceWizard");
		persistenceDomainsSection.setOwner("persistenceWizard");
		persistenceUnitsSection.setOwner("persistenceWizard");
		persistenceSourcesSection.setOwner("persistenceWizard");
		persistenceAdaptersSection.setOwner("persistenceWizard");
		persistenceEntitiesSection.setOwner("persistenceWizard");
		persistenceQueriesSection.setOwner("persistenceWizard");
		persistenceRepositoriesSection.setOwner("persistenceWizard");
		persistenceProvidersSection.setOwner("persistenceWizard");
		
		sections.clear();
		sections.add(persistenceIdentificationSection);
		sections.add(persistenceConfigurationSection);
		sections.add(persistenceDocumentationSection);
		sections.add(persistenceOverviewSection);
		sections.add(persistenceImportsSection);
		sections.add(persistenceDomainsSection);
		sections.add(persistenceUnitsSection);
		sections.add(persistenceSourcesSection);
		sections.add(persistenceAdaptersSection);
		sections.add(persistenceEntitiesSection);
		sections.add(persistenceQueriesSection);
		sections.add(persistenceRepositoriesSection);
		sections.add(persistenceProvidersSection);
		
		String url = getPersistenceWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("persistence");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializePersistenceUpdatePage(Persistence persistence) {
		setPageTitle(getPersistenceLabel(persistence));
		setPageIcon("/icons/nam/Persistence16.gif");
		setSectionTitle("Persistence Overview");
		String persistenceName = PersistenceUtil.getLabel(persistence);
		persistenceWizard.setNewMode(false);
		
		String pageLevelKey = "persistence";
		String wizardLevelKey = "persistenceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Persistence", "showPersistenceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(persistenceName, "showPersistenceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overviews", "showPersistenceWizardPage('Overviews')");
		addBreadcrumb(wizardLevelKey, "Imports", "showPersistenceWizardPage('Imports')");
		addBreadcrumb(wizardLevelKey, "Domains", "showPersistenceWizardPage('Domains')");
		addBreadcrumb(wizardLevelKey, "Units", "showPersistenceWizardPage('Units')");
		addBreadcrumb(wizardLevelKey, "Sources", "showPersistenceWizardPage('Sources')");
		addBreadcrumb(wizardLevelKey, "Adapters", "showPersistenceWizardPage('Adapters')");
		addBreadcrumb(wizardLevelKey, "Entities", "showPersistenceWizardPage('Entities')");
		addBreadcrumb(wizardLevelKey, "Queries", "showPersistenceWizardPage('Queries')");
		addBreadcrumb(wizardLevelKey, "Repositories", "showPersistenceWizardPage('Repositories')");
		addBreadcrumb(wizardLevelKey, "Providers", "showPersistenceWizardPage('Providers')");
		
		persistenceOverviewSection.setOwner("persistenceWizard");
		persistenceIdentificationSection.setOwner("persistenceWizard");
		persistenceConfigurationSection.setOwner("persistenceWizard");
		persistenceDocumentationSection.setOwner("persistenceWizard");
		persistenceOverviewSection.setOwner("persistenceWizard");
		persistenceImportsSection.setOwner("persistenceWizard");
		persistenceDomainsSection.setOwner("persistenceWizard");
		persistenceUnitsSection.setOwner("persistenceWizard");
		persistenceSourcesSection.setOwner("persistenceWizard");
		persistenceAdaptersSection.setOwner("persistenceWizard");
		persistenceEntitiesSection.setOwner("persistenceWizard");
		persistenceQueriesSection.setOwner("persistenceWizard");
		persistenceRepositoriesSection.setOwner("persistenceWizard");
		persistenceProvidersSection.setOwner("persistenceWizard");
		
		sections.clear();
		sections.add(persistenceOverviewSection);
		sections.add(persistenceIdentificationSection);
		sections.add(persistenceConfigurationSection);
		sections.add(persistenceDocumentationSection);
		sections.add(persistenceOverviewSection);
		sections.add(persistenceImportsSection);
		sections.add(persistenceDomainsSection);
		sections.add(persistenceUnitsSection);
		sections.add(persistenceSourcesSection);
		sections.add(persistenceAdaptersSection);
		sections.add(persistenceEntitiesSection);
		sections.add(persistenceQueriesSection);
		sections.add(persistenceRepositoriesSection);
		sections.add(persistenceProvidersSection);
		
		String url = getPersistenceWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("persistence");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializePersistenceManagementPage() {
		setPageTitle("Persistence");
		setPageIcon("/icons/nam/Persistence16.gif");
		String pageLevelKey = "persistenceManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Persistence", "showPersistenceManagementPage()");
		String url = getPersistenceManagementPage();
		selectionContext.setCurrentArea("persistence");
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
		setSectionType("persistence");
		setSectionName("Overview");
		setSectionTitle("Overview of Persistence");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializePersistenceImportsView() {
		setSectionType("persistence");
		setSectionName("Imports");
		setSectionTitle("Persistence Imports");
		setSectionIcon("/icons/nam/Import16.gif");
		selectionContext.setMessageDomain("persistenceImports");
		importPageManager.refresh("persistence");
		persistenceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializePersistenceDomainsView() {
		setSectionType("persistence");
		setSectionName("Domains");
		setSectionTitle("Persistence Domains");
		setSectionIcon("/icons/nam/Domain16.gif");
		selectionContext.setMessageDomain("persistenceDomains");
		domainPageManager.refresh("persistence");
		persistenceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializePersistenceUnitsView() {
		setSectionType("persistence");
		setSectionName("Units");
		setSectionTitle("Persistence Units");
		setSectionIcon("/icons/nam/Unit16.gif");
		selectionContext.setMessageDomain("persistenceUnits");
		unitPageManager.refresh("persistence");
		persistenceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializePersistenceSourcesView() {
		setSectionType("persistence");
		setSectionName("Sources");
		setSectionTitle("Persistence Sources");
		setSectionIcon("/icons/nam/Source16.gif");
		selectionContext.setMessageDomain("persistenceSources");
		sourcePageManager.refresh("persistence");
		persistenceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializePersistenceAdaptersView() {
		setSectionType("persistence");
		setSectionName("Adapters");
		setSectionTitle("Persistence Adapters");
		setSectionIcon("/icons/nam/Adapter16.gif");
		selectionContext.setMessageDomain("persistenceAdapters");
		adapterPageManager.refresh("persistence");
		persistenceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializePersistenceEntitiesView() {
		setSectionType("persistence");
		setSectionName("Entities");
		setSectionTitle("Persistence Entities");
		setSectionIcon("/icons/nam/Entity16.gif");
		selectionContext.setMessageDomain("persistenceEntities");
		//entityPageManager.refresh("persistence");
		persistenceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializePersistenceQueriesView() {
		setSectionType("persistence");
		setSectionName("Queries");
		setSectionTitle("Persistence Queries");
		setSectionIcon("/icons/nam/Query16.gif");
		selectionContext.setMessageDomain("persistenceQueries");
		queryPageManager.refresh("persistence");
		persistenceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializePersistenceRepositoriesView() {
		setSectionType("persistence");
		setSectionName("Repositories");
		setSectionTitle("Persistence Repositories");
		setSectionIcon("/icons/nam/Repository16.gif");
		selectionContext.setMessageDomain("persistenceRepositories");
		repositoryPageManager.refresh("persistence");
		persistenceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializePersistenceProvidersView() {
		setSectionType("persistence");
		setSectionName("Providers");
		setSectionTitle("Persistence Providers");
		setSectionIcon("/icons/nam/Provider16.gif");
		selectionContext.setMessageDomain("persistenceProviders");
		providerPageManager.refresh("persistence");
		persistenceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializePersistenceSummaryView(Persistence persistence) {
		//String viewTitle = getPersistenceLabel(persistence);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("persistence");
		setSectionName("Summary");
		setSectionTitle("Summary of Persistence Records");
		setSectionIcon("/icons/nam/Persistence16.gif");
		String viewLevelKey = "persistenceSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Persistence", "showPersistenceManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getPersistenceLabel(Persistence persistence) {
		String label = "Persistence";
		String name = PersistenceUtil.getLabel(persistence);
		if (name == null && persistence.getName() != null)
			name = PersistenceUtil.getLabel(persistence);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Persistence> page = persistenceWizard.getPage();
		if (page != null)
			setSectionTitle("Persistence " + page.getName());
	}
	
	protected void updateState(Persistence persistence) {
		String persistenceName = PersistenceUtil.getLabel(persistence);
		setSectionTitle(persistenceName + " Persistence");
	}
	
}
