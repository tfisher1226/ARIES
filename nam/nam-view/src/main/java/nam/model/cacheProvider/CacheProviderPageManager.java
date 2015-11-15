package nam.model.cacheProvider;

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

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cacheProviderPageManager")
public class CacheProviderPageManager extends AbstractPageManager<CacheProvider> implements Serializable {
	
	@Inject
	private CacheProviderWizard cacheProviderWizard;
	
	@Inject
	private CacheProviderDataManager cacheProviderDataManager;
	
	@Inject
	private CacheProviderListManager cacheProviderListManager;
	
	@Inject
	private CacheProviderRecord_OverviewSection cacheProviderOverviewSection;
	
	@Inject
	private CacheProviderRecord_IdentificationSection cacheProviderIdentificationSection;
	
	@Inject
	private CacheProviderRecord_ConfigurationSection cacheProviderConfigurationSection;
	
	@Inject
	private CacheProviderRecord_DocumentationSection cacheProviderDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public CacheProviderPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("cacheProvider");
	}
	
	public void refreshLocal() {
		refreshLocal("cacheProvider");
	}
	
	public void refreshMembers() {
		refreshMembers("cacheProvider");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		cacheProviderDataManager.setScope(scope);
		cacheProviderListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		cacheProviderListManager.refresh();
	}
	
	public String getCacheProviderListPage() {
		return "/nam/model/cacheProvider/cacheProviderListPage.xhtml";
	}
	
	public String getCacheProviderTreePage() {
		return "/nam/model/cacheProvider/cacheProviderTreePage.xhtml";
	}
	
	public String getCacheProviderSummaryPage() {
		return "/nam/model/cacheProvider/cacheProviderSummaryPage.xhtml";
	}
	
	public String getCacheProviderRecordPage() {
		return "/nam/model/cacheProvider/cacheProviderRecordPage.xhtml";
	}
	
	public String getCacheProviderWizardPage() {
		return "/nam/model/cacheProvider/cacheProviderWizardPage.xhtml";
	}
	
	public String getCacheProviderManagementPage() {
		return "/nam/model/cacheProvider/cacheProviderManagementPage.xhtml";
	}
	
	public String initializeCacheProviderListPage() {
		String pageLevelKey = "cacheProviderList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "CacheProviders", "showCacheProviderManagementPage()");
		String url = getCacheProviderListPage();
		selectionContext.setCurrentArea("cacheProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeCacheProviderTreePage() {
		String pageLevelKey = "cacheProviderTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "CacheProviders", "showCacheProviderTreePage()");
		String url = getCacheProviderTreePage();
		selectionContext.setCurrentArea("cacheProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeCacheProviderSummaryPage(CacheProvider cacheProvider) {
		String pageLevelKey = "cacheProviderSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "CacheProviders", "showCacheProviderSummaryPage()");
		String url = getCacheProviderSummaryPage();
		selectionContext.setCurrentArea("cacheProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeCacheProviderRecordPage() {
		CacheProvider cacheProvider = selectionContext.getSelection("cacheProvider");
		String cacheProviderName = cacheProvider.getName();
		
		String pageLevelKey = "cacheProviderRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "CacheProviders", "showCacheProviderManagementPage()");
		addBreadcrumb(pageLevelKey, cacheProviderName, "showCacheProviderRecordPage()");
		String url = getCacheProviderRecordPage();
		selectionContext.setCurrentArea("cacheProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeCacheProviderCreationPage(CacheProvider cacheProvider) {
		setPageTitle("New "+getCacheProviderLabel(cacheProvider));
		setPageIcon("/icons/nam/NewCacheProvider16.gif");
		setSectionTitle("CacheProvider Identification");
		cacheProviderWizard.setNewMode(true);
		
		String pageLevelKey = "cacheProvider";
		String wizardLevelKey = "cacheProviderWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "CacheProviders", "showCacheProviderManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New CacheProvider", "showCacheProviderWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showCacheProviderWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showCacheProviderWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showCacheProviderWizardPage('Documentation')");
		
		cacheProviderIdentificationSection.setOwner("cacheProviderWizard");
		cacheProviderConfigurationSection.setOwner("cacheProviderWizard");
		cacheProviderDocumentationSection.setOwner("cacheProviderWizard");
		
		sections.clear();
		sections.add(cacheProviderIdentificationSection);
		sections.add(cacheProviderConfigurationSection);
		sections.add(cacheProviderDocumentationSection);
		
		String url = getCacheProviderWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("cacheProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeCacheProviderUpdatePage(CacheProvider cacheProvider) {
		setPageTitle(getCacheProviderLabel(cacheProvider));
		setPageIcon("/icons/nam/CacheProvider16.gif");
		setSectionTitle("CacheProvider Overview");
		String cacheProviderName = cacheProvider.getName();
		cacheProviderWizard.setNewMode(false);
		
		String pageLevelKey = "cacheProvider";
		String wizardLevelKey = "cacheProviderWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "CacheProviders", "showCacheProviderManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(cacheProviderName, "showCacheProviderWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showCacheProviderWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showCacheProviderWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showCacheProviderWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showCacheProviderWizardPage('Documentation')");
		
		cacheProviderOverviewSection.setOwner("cacheProviderWizard");
		cacheProviderIdentificationSection.setOwner("cacheProviderWizard");
		cacheProviderConfigurationSection.setOwner("cacheProviderWizard");
		cacheProviderDocumentationSection.setOwner("cacheProviderWizard");
		
		sections.clear();
		sections.add(cacheProviderOverviewSection);
		sections.add(cacheProviderIdentificationSection);
		sections.add(cacheProviderConfigurationSection);
		sections.add(cacheProviderDocumentationSection);
		
		String url = getCacheProviderWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("cacheProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeCacheProviderManagementPage() {
		setPageTitle("CacheProviders");
		setPageIcon("/icons/nam/CacheProvider16.gif");
		String pageLevelKey = "cacheProviderManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "CacheProviders", "showCacheProviderManagementPage()");
		String url = getCacheProviderManagementPage();
		selectionContext.setCurrentArea("cacheProvider");
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
		setSectionType("cacheProvider");
		setSectionName("Overview");
		setSectionTitle("Overview of CacheProviders");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeCacheProviderSummaryView(CacheProvider cacheProvider) {
		//String viewTitle = getCacheProviderLabel(cacheProvider);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("cacheProvider");
		setSectionName("Summary");
		setSectionTitle("Summary of CacheProvider Records");
		setSectionIcon("/icons/nam/CacheProvider16.gif");
		String viewLevelKey = "cacheProviderSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "CacheProviders", "showCacheProviderManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getCacheProviderLabel(CacheProvider cacheProvider) {
		String label = "CacheProvider";
		String name = CacheProviderUtil.getLabel(cacheProvider);
		if (name == null && cacheProvider.getName() != null)
			name = NameUtil.capName(cacheProvider.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<CacheProvider> page = cacheProviderWizard.getPage();
		if (page != null)
			setSectionTitle("CacheProvider " + page.getName());
	}
	
	protected void updateState(CacheProvider cacheProvider) {
		String cacheProviderName = NameUtil.capName(cacheProvider.getName());
		setSectionTitle(cacheProviderName + " CacheProvider");
	}
	
}
