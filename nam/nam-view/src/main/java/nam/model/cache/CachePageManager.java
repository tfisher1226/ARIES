package nam.model.cache;

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

import nam.model.Cache;
import nam.model.util.CacheUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("cachePageManager")
public class CachePageManager extends AbstractPageManager<Cache> implements Serializable {
	
	@Inject
	private CacheWizard cacheWizard;
	
	@Inject
	private CacheDataManager cacheDataManager;
	
	@Inject
	private CacheListManager cacheListManager;
	
	@Inject
	private CacheRecord_OverviewSection cacheOverviewSection;
	
	@Inject
	private CacheRecord_IdentificationSection cacheIdentificationSection;
	
	@Inject
	private CacheRecord_ConfigurationSection cacheConfigurationSection;
	
	@Inject
	private CacheRecord_DocumentationSection cacheDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public CachePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("cache");
	}
	
	public void refreshLocal() {
		refreshLocal("cache");
	}
	
	public void refreshMembers() {
		refreshMembers("cache");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		cacheDataManager.setScope(scope);
		cacheListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		cacheListManager.refresh();
	}
	
	public String getCacheListPage() {
		return "/nam/model/cache/cacheListPage.xhtml";
	}
	
	public String getCacheTreePage() {
		return "/nam/model/cache/cacheTreePage.xhtml";
	}
	
	public String getCacheSummaryPage() {
		return "/nam/model/cache/cacheSummaryPage.xhtml";
	}
	
	public String getCacheRecordPage() {
		return "/nam/model/cache/cacheRecordPage.xhtml";
	}
	
	public String getCacheWizardPage() {
		return "/nam/model/cache/cacheWizardPage.xhtml";
	}
	
	public String getCacheManagementPage() {
		return "/nam/model/cache/cacheManagementPage.xhtml";
	}
	
	public String initializeCacheListPage() {
		String pageLevelKey = "cacheList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Caches", "showCacheManagementPage()");
		String url = getCacheListPage();
		selectionContext.setCurrentArea("cache");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeCacheTreePage() {
		String pageLevelKey = "cacheTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Caches", "showCacheTreePage()");
		String url = getCacheTreePage();
		selectionContext.setCurrentArea("cache");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeCacheSummaryPage(Cache cache) {
		String pageLevelKey = "cacheSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Caches", "showCacheSummaryPage()");
		String url = getCacheSummaryPage();
		selectionContext.setCurrentArea("cache");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeCacheRecordPage() {
		Cache cache = selectionContext.getSelection("cache");
		String cacheName = cache.getName();
		
		String pageLevelKey = "cacheRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Caches", "showCacheManagementPage()");
		addBreadcrumb(pageLevelKey, cacheName, "showCacheRecordPage()");
		String url = getCacheRecordPage();
		selectionContext.setCurrentArea("cache");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeCacheCreationPage(Cache cache) {
		setPageTitle("New "+getCacheLabel(cache));
		setPageIcon("/icons/nam/NewCache16.gif");
		setSectionTitle("Cache Identification");
		cacheWizard.setNewMode(true);
		
		String pageLevelKey = "cache";
		String wizardLevelKey = "cacheWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Caches", "showCacheManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Cache", "showCacheWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showCacheWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showCacheWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showCacheWizardPage('Documentation')");
		
		cacheIdentificationSection.setOwner("cacheWizard");
		cacheConfigurationSection.setOwner("cacheWizard");
		cacheDocumentationSection.setOwner("cacheWizard");
		
		sections.clear();
		sections.add(cacheIdentificationSection);
		sections.add(cacheConfigurationSection);
		sections.add(cacheDocumentationSection);
		
		String url = getCacheWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("cache");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeCacheUpdatePage(Cache cache) {
		setPageTitle(getCacheLabel(cache));
		setPageIcon("/icons/nam/Cache16.gif");
		setSectionTitle("Cache Overview");
		String cacheName = cache.getName();
		cacheWizard.setNewMode(false);
		
		String pageLevelKey = "cache";
		String wizardLevelKey = "cacheWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Caches", "showCacheManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(cacheName, "showCacheWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showCacheWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showCacheWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showCacheWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showCacheWizardPage('Documentation')");
		
		cacheOverviewSection.setOwner("cacheWizard");
		cacheIdentificationSection.setOwner("cacheWizard");
		cacheConfigurationSection.setOwner("cacheWizard");
		cacheDocumentationSection.setOwner("cacheWizard");
		
		sections.clear();
		sections.add(cacheOverviewSection);
		sections.add(cacheIdentificationSection);
		sections.add(cacheConfigurationSection);
		sections.add(cacheDocumentationSection);
		
		String url = getCacheWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("cache");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeCacheManagementPage() {
		setPageTitle("Caches");
		setPageIcon("/icons/nam/Cache16.gif");
		String pageLevelKey = "cacheManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Caches", "showCacheManagementPage()");
		String url = getCacheManagementPage();
		selectionContext.setCurrentArea("cache");
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
		setSectionType("cache");
		setSectionName("Overview");
		setSectionTitle("Overview of Caches");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeCacheSummaryView(Cache cache) {
		//String viewTitle = getCacheLabel(cache);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("cache");
		setSectionName("Summary");
		setSectionTitle("Summary of Cache Records");
		setSectionIcon("/icons/nam/Cache16.gif");
		String viewLevelKey = "cacheSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Caches", "showCacheManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getCacheLabel(Cache cache) {
		String label = "Cache";
		String name = CacheUtil.getLabel(cache);
		if (name == null && cache.getName() != null)
			name = NameUtil.capName(cache.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Cache> page = cacheWizard.getPage();
		if (page != null)
			setSectionTitle("Cache " + page.getName());
	}
	
	protected void updateState(Cache cache) {
		String cacheName = NameUtil.capName(cache.getName());
		setSectionTitle(cacheName + " Cache");
	}
	
}
