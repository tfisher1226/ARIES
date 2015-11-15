package nam.model.persistenceProvider;

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

import nam.model.PersistenceProvider;
import nam.model.util.PersistenceProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("persistenceProviderPageManager")
public class PersistenceProviderPageManager extends AbstractPageManager<PersistenceProvider> implements Serializable {
	
	@Inject
	private PersistenceProviderWizard persistenceProviderWizard;
	
	@Inject
	private PersistenceProviderDataManager persistenceProviderDataManager;
	
	@Inject
	private PersistenceProviderListManager persistenceProviderListManager;
	
	@Inject
	private PersistenceProviderRecord_OverviewSection persistenceProviderOverviewSection;
	
	@Inject
	private PersistenceProviderRecord_IdentificationSection persistenceProviderIdentificationSection;
	
	@Inject
	private PersistenceProviderRecord_ConfigurationSection persistenceProviderConfigurationSection;
	
	@Inject
	private PersistenceProviderRecord_DocumentationSection persistenceProviderDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PersistenceProviderPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("persistenceProvider");
	}
	
	public void refreshLocal() {
		refreshLocal("persistenceProvider");
	}
	
	public void refreshMembers() {
		refreshMembers("persistenceProvider");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		persistenceProviderDataManager.setScope(scope);
		persistenceProviderListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		persistenceProviderListManager.refresh();
	}
	
	public String getPersistenceProviderListPage() {
		return "/nam/model/persistenceProvider/persistenceProviderListPage.xhtml";
	}
	
	public String getPersistenceProviderTreePage() {
		return "/nam/model/persistenceProvider/persistenceProviderTreePage.xhtml";
	}
	
	public String getPersistenceProviderSummaryPage() {
		return "/nam/model/persistenceProvider/persistenceProviderSummaryPage.xhtml";
	}
	
	public String getPersistenceProviderRecordPage() {
		return "/nam/model/persistenceProvider/persistenceProviderRecordPage.xhtml";
	}
	
	public String getPersistenceProviderWizardPage() {
		return "/nam/model/persistenceProvider/persistenceProviderWizardPage.xhtml";
	}
	
	public String getPersistenceProviderManagementPage() {
		return "/nam/model/persistenceProvider/persistenceProviderManagementPage.xhtml";
	}
	
	public String initializePersistenceProviderListPage() {
		String pageLevelKey = "persistenceProviderList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "PersistenceProviders", "showPersistenceProviderManagementPage()");
		String url = getPersistenceProviderListPage();
		selectionContext.setCurrentArea("persistenceProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePersistenceProviderTreePage() {
		String pageLevelKey = "persistenceProviderTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "PersistenceProviders", "showPersistenceProviderTreePage()");
		String url = getPersistenceProviderTreePage();
		selectionContext.setCurrentArea("persistenceProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePersistenceProviderSummaryPage(PersistenceProvider persistenceProvider) {
		String pageLevelKey = "persistenceProviderSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "PersistenceProviders", "showPersistenceProviderSummaryPage()");
		String url = getPersistenceProviderSummaryPage();
		selectionContext.setCurrentArea("persistenceProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializePersistenceProviderRecordPage() {
		PersistenceProvider persistenceProvider = selectionContext.getSelection("persistenceProvider");
		String persistenceProviderName = persistenceProvider.getName();
		
		String pageLevelKey = "persistenceProviderRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "PersistenceProviders", "showPersistenceProviderManagementPage()");
		addBreadcrumb(pageLevelKey, persistenceProviderName, "showPersistenceProviderRecordPage()");
		String url = getPersistenceProviderRecordPage();
		selectionContext.setCurrentArea("persistenceProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializePersistenceProviderCreationPage(PersistenceProvider persistenceProvider) {
		setPageTitle("New "+getPersistenceProviderLabel(persistenceProvider));
		setPageIcon("/icons/nam/NewPersistenceProvider16.gif");
		setSectionTitle("PersistenceProvider Identification");
		persistenceProviderWizard.setNewMode(true);
		
		String pageLevelKey = "persistenceProvider";
		String wizardLevelKey = "persistenceProviderWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "PersistenceProviders", "showPersistenceProviderManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New PersistenceProvider", "showPersistenceProviderWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showPersistenceProviderWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showPersistenceProviderWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showPersistenceProviderWizardPage('Documentation')");
		
		persistenceProviderIdentificationSection.setOwner("persistenceProviderWizard");
		persistenceProviderConfigurationSection.setOwner("persistenceProviderWizard");
		persistenceProviderDocumentationSection.setOwner("persistenceProviderWizard");
		
		sections.clear();
		sections.add(persistenceProviderIdentificationSection);
		sections.add(persistenceProviderConfigurationSection);
		sections.add(persistenceProviderDocumentationSection);
		
		String url = getPersistenceProviderWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("persistenceProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializePersistenceProviderUpdatePage(PersistenceProvider persistenceProvider) {
		setPageTitle(getPersistenceProviderLabel(persistenceProvider));
		setPageIcon("/icons/nam/PersistenceProvider16.gif");
		setSectionTitle("PersistenceProvider Overview");
		String persistenceProviderName = persistenceProvider.getName();
		persistenceProviderWizard.setNewMode(false);
		
		String pageLevelKey = "persistenceProvider";
		String wizardLevelKey = "persistenceProviderWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "PersistenceProviders", "showPersistenceProviderManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(persistenceProviderName, "showPersistenceProviderWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showPersistenceProviderWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showPersistenceProviderWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showPersistenceProviderWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showPersistenceProviderWizardPage('Documentation')");
		
		persistenceProviderOverviewSection.setOwner("persistenceProviderWizard");
		persistenceProviderIdentificationSection.setOwner("persistenceProviderWizard");
		persistenceProviderConfigurationSection.setOwner("persistenceProviderWizard");
		persistenceProviderDocumentationSection.setOwner("persistenceProviderWizard");
		
		sections.clear();
		sections.add(persistenceProviderOverviewSection);
		sections.add(persistenceProviderIdentificationSection);
		sections.add(persistenceProviderConfigurationSection);
		sections.add(persistenceProviderDocumentationSection);
		
		String url = getPersistenceProviderWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("persistenceProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializePersistenceProviderManagementPage() {
		setPageTitle("PersistenceProviders");
		setPageIcon("/icons/nam/PersistenceProvider16.gif");
		String pageLevelKey = "persistenceProviderManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "PersistenceProviders", "showPersistenceProviderManagementPage()");
		String url = getPersistenceProviderManagementPage();
		selectionContext.setCurrentArea("persistenceProvider");
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
		setSectionType("persistenceProvider");
		setSectionName("Overview");
		setSectionTitle("Overview of PersistenceProviders");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializePersistenceProviderSummaryView(PersistenceProvider persistenceProvider) {
		//String viewTitle = getPersistenceProviderLabel(persistenceProvider);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("persistenceProvider");
		setSectionName("Summary");
		setSectionTitle("Summary of PersistenceProvider Records");
		setSectionIcon("/icons/nam/PersistenceProvider16.gif");
		String viewLevelKey = "persistenceProviderSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "PersistenceProviders", "showPersistenceProviderManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getPersistenceProviderLabel(PersistenceProvider persistenceProvider) {
		String label = "PersistenceProvider";
		String name = PersistenceProviderUtil.getLabel(persistenceProvider);
		if (name == null && persistenceProvider.getName() != null)
			name = NameUtil.capName(persistenceProvider.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<PersistenceProvider> page = persistenceProviderWizard.getPage();
		if (page != null)
			setSectionTitle("PersistenceProvider " + page.getName());
	}
	
	protected void updateState(PersistenceProvider persistenceProvider) {
		String persistenceProviderName = NameUtil.capName(persistenceProvider.getName());
		setSectionTitle(persistenceProviderName + " PersistenceProvider");
	}
	
}
