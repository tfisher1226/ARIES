package nam.model.provider;

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

import nam.model.Provider;
import nam.model.cacheProvider.CacheProviderPageManager;
import nam.model.messagingProvider.MessagingProviderPageManager;
import nam.model.persistenceProvider.PersistenceProviderPageManager;
import nam.model.util.ProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("providerPageManager")
public class ProviderPageManager extends AbstractPageManager<Provider> implements Serializable {
	
	@Inject
	private ProviderWizard providerWizard;
	
	@Inject
	private ProviderDataManager providerDataManager;
	
	@Inject
	private ProviderInfoManager providerInfoManager;
	
	@Inject
	private ProviderListManager providerListManager;
	
	@Inject
	private CacheProviderPageManager cacheProviderPageManager;
	
	@Inject
	private MessagingProviderPageManager messagingProviderPageManager;
	
	@Inject
	private PersistenceProviderPageManager persistenceProviderPageManager;
	
	@Inject
	private ProviderRecord_OverviewSection providerOverviewSection;
	
	@Inject
	private ProviderRecord_IdentificationSection providerIdentificationSection;
	
	@Inject
	private ProviderRecord_ConfigurationSection providerConfigurationSection;
	
	@Inject
	private ProviderRecord_DocumentationSection providerDocumentationSection;
	
	@Inject
	private ProviderRecord_CacheProvidersSection providerCacheProvidersSection;
	
	@Inject
	private ProviderRecord_MessagingProvidersSection providerMessagingProvidersSection;
	
	@Inject
	private ProviderRecord_PersistenceProvidersSection providerPersistenceProvidersSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ProviderPageManager() {
		initializeSections();
	}
	
	
	public void refresh() {
		refresh("provider");
	}
	
	public void refreshLocal() {
		refreshLocal("provider");
	}
	
	public void refreshMembers() {
		refreshMembers("provider");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		providerDataManager.setScope(scope);
		providerListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		cacheProviderPageManager.refreshLocal(scope);
		messagingProviderPageManager.refreshLocal(scope);
		persistenceProviderPageManager.refreshLocal(scope);
	}
	
	public String getProviderListPage() {
		return "/nam/model/provider/providerListPage.xhtml";
	}
	
	public String getProviderTreePage() {
		return "/nam/model/provider/providerTreePage.xhtml";
	}
	
	public String getProviderSummaryPage() {
		return "/nam/model/provider/providerSummaryPage.xhtml";
	}
	
	public String getProviderRecordPage() {
		return "/nam/model/provider/providerRecordPage.xhtml";
	}
	
	public String getProviderWizardPage() {
		return "/nam/model/provider/providerWizardPage.xhtml";
	}
	
	public String getProviderManagementPage() {
		return "/nam/model/provider/providerManagementPage.xhtml";
	}
	
	public void handleProviderSelected(@Observes @Selected Provider provider) {
		selectionContext.setSelection("provider",  provider);
		providerInfoManager.setRecord(provider);
	}
	
	public void handleProviderUnselected(@Observes @Unselected Provider provider) {
		selectionContext.unsetSelection("provider",  provider);
		providerInfoManager.unsetRecord(provider);
	}
	
	public void handleProviderChecked() {
		String scope = "providerSelection";
		ProviderListObject listObject = providerListManager.getSelection();
		Provider provider = selectionContext.getSelection("provider");
		boolean checked = providerListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			providerInfoManager.setRecord(provider);
			selectionContext.setSelection(scope,  provider);
		} else {
			providerInfoManager.unsetRecord(provider);
			selectionContext.unsetSelection(scope,  provider);
		}
		String target = selectionContext.getCurrentTarget();
		if (target.equals("cacheProvider"))
			cacheProviderPageManager.refreshLocal(scope);
		if (target.equals("messagingProvider"))
			messagingProviderPageManager.refreshLocal(scope);
		if (target.equals("persistenceProvider"))
			persistenceProviderPageManager.refreshLocal(scope);
		refreshLocal(scope);
	}
	
	public String initializeProviderListPage() {
		String pageLevelKey = "providerList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Providers", "showProviderManagementPage()");
		String url = getProviderListPage();
		selectionContext.setCurrentArea("provider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeProviderTreePage() {
		String pageLevelKey = "providerTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Providers", "showProviderTreePage()");
		String url = getProviderTreePage();
		selectionContext.setCurrentArea("provider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeProviderSummaryPage(Provider provider) {
		String pageLevelKey = "providerSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Providers", "showProviderSummaryPage()");
		String url = getProviderSummaryPage();
		selectionContext.setCurrentArea("provider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeProviderRecordPage() {
		Provider provider = selectionContext.getSelection("provider");
		String providerName = ProviderUtil.getLabel(provider);
		
		String pageLevelKey = "providerRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Providers", "showProviderManagementPage()");
		addBreadcrumb(pageLevelKey, providerName, "showProviderRecordPage()");
		String url = getProviderRecordPage();
		selectionContext.setCurrentArea("provider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeProviderCreationPage(Provider provider) {
		setPageTitle("New "+getProviderLabel(provider));
		setPageIcon("/icons/nam/NewProvider16.gif");
		setSectionTitle("Provider Identification");
		providerWizard.setNewMode(true);
		
		String pageLevelKey = "provider";
		String wizardLevelKey = "providerWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Providers", "showProviderManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Provider", "showProviderWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "CacheProviders", "showProviderWizardPage('CacheProviders')");
		addBreadcrumb(wizardLevelKey, "MessagingProviders", "showProviderWizardPage('MessagingProviders')");
		addBreadcrumb(wizardLevelKey, "PersistenceProviders", "showProviderWizardPage('PersistenceProviders')");
		
		providerIdentificationSection.setOwner("providerWizard");
		providerConfigurationSection.setOwner("providerWizard");
		providerDocumentationSection.setOwner("providerWizard");
		providerCacheProvidersSection.setOwner("providerWizard");
		providerMessagingProvidersSection.setOwner("providerWizard");
		providerPersistenceProvidersSection.setOwner("providerWizard");
		
		sections.clear();
		sections.add(providerIdentificationSection);
		sections.add(providerConfigurationSection);
		sections.add(providerDocumentationSection);
		sections.add(providerCacheProvidersSection);
		sections.add(providerMessagingProvidersSection);
		sections.add(providerPersistenceProvidersSection);
		
		String url = getProviderWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("provider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeProviderUpdatePage(Provider provider) {
		setPageTitle(getProviderLabel(provider));
		setPageIcon("/icons/nam/Provider16.gif");
		setSectionTitle("Provider Overview");
		String providerName = ProviderUtil.getLabel(provider);
		providerWizard.setNewMode(false);
		
		String pageLevelKey = "provider";
		String wizardLevelKey = "providerWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Providers", "showProviderManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(providerName, "showProviderWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "CacheProviders", "showProviderWizardPage('CacheProviders')");
		addBreadcrumb(wizardLevelKey, "MessagingProviders", "showProviderWizardPage('MessagingProviders')");
		addBreadcrumb(wizardLevelKey, "PersistenceProviders", "showProviderWizardPage('PersistenceProviders')");
		
		providerOverviewSection.setOwner("providerWizard");
		providerIdentificationSection.setOwner("providerWizard");
		providerConfigurationSection.setOwner("providerWizard");
		providerDocumentationSection.setOwner("providerWizard");
		providerCacheProvidersSection.setOwner("providerWizard");
		providerMessagingProvidersSection.setOwner("providerWizard");
		providerPersistenceProvidersSection.setOwner("providerWizard");
		
		sections.clear();
		sections.add(providerOverviewSection);
		sections.add(providerIdentificationSection);
		sections.add(providerConfigurationSection);
		sections.add(providerDocumentationSection);
		sections.add(providerCacheProvidersSection);
		sections.add(providerMessagingProvidersSection);
		sections.add(providerPersistenceProvidersSection);
		
		String url = getProviderWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("provider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refreshLocal();
		return url;
	}
	
	public String initializeProviderManagementPage() {
		setPageTitle("Providers");
		setPageIcon("/icons/nam/Provider16.gif");
		String pageLevelKey = "providerManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Providers", "showProviderManagementPage()");
		String url = getProviderManagementPage();
		selectionContext.setCurrentArea("provider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Providers");
		setPageIcon("/icons/nam/Provider16.gif");
		setSectionType("provider");
		setSectionName("Overview");
		setSectionTitle("Overview of Providers");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "providerOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Providers", "showProviderManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializeProviderCacheProvidersView() {
		setSectionType("provider");
		setSectionName("CacheProviders");
		setSectionTitle("CacheProviders");
		setSectionIcon("/icons/nam/CacheProvider16.gif");
		selectionContext.setMessageDomain("providerCacheProviders");
		cacheProviderPageManager.refreshLocal("providerSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeProviderMessagingProvidersView() {
		setSectionType("provider");
		setSectionName("MessagingProviders");
		setSectionTitle("MessagingProviders");
		setSectionIcon("/icons/nam/MessagingProvider16.gif");
		selectionContext.setMessageDomain("providerMessagingProviders");
		messagingProviderPageManager.refreshLocal("providerSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeProviderPersistenceProvidersView() {
		setSectionType("provider");
		setSectionName("PersistenceProviders");
		setSectionTitle("PersistenceProviders");
		setSectionIcon("/icons/nam/PersistenceProvider16.gif");
		selectionContext.setMessageDomain("providerPersistenceProviders");
		persistenceProviderPageManager.refreshLocal("providerSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeProviderSummaryView(Provider provider) {
		//String viewTitle = getProviderLabel(provider);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("provider");
		setSectionName("Summary");
		setSectionTitle("Summary of Provider Records");
		setSectionIcon("/icons/nam/Provider16.gif");
		String viewLevelKey = "providerSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Providers", "showProviderManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getProviderLabel(Provider provider) {
		String label = "Provider";
		String name = ProviderUtil.getLabel(provider);
		if (name == null && provider.getName() != null)
			name = ProviderUtil.getLabel(provider);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Provider> page = providerWizard.getPage();
		if (page != null)
			setSectionTitle("Provider " + page.getName());
	}
	
	protected void updateState(Provider provider) {
		String providerName = ProviderUtil.getLabel(provider);
		setSectionTitle(providerName + " Provider");
	}
	
}
