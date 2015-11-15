package nam.model.domain;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Domain;
import nam.model.service.ServicePageManager;
import nam.model.util.DomainUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.ui.PageManager;
import org.aries.util.NameUtil;


@SessionScoped
@Named("domainPageManager")
public class DomainPageManager extends AbstractPageManager<Domain> implements Serializable {
	
	@Inject
	private DomainWizard domainWizard;
	
	@Inject
	private DomainDataManager domainDataManager;

	@Inject
	private DomainListManager domainListManager;
	
	@Inject
	private DomainRecord_OverviewSection domainOverviewSection;
	
	@Inject
	private DomainRecord_IdentificationSection domainIdentificationSection;
	
	@Inject
	private DomainRecord_ConfigurationSection domainConfigurationSection;
	
	@Inject
	private DomainRecord_DocumentationSection domainDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public DomainPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("domain");
	}
	
	public void refreshLocal() {
		refreshLocal("domain");
	}
	
	public void refreshMembers() {
		refreshMembers("domain");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		domainDataManager.setScope(scope);
		domainListManager.refresh();
		}
	
	public void refreshMembers(String scope) {
		domainListManager.refresh();
	}
	
	public String getDomainListPage() {
		return "/nam/model/domain/domainListPage.xhtml";
	}
	
	public String getDomainTreePage() {
		return "/nam/model/domain/domainTreePage.xhtml";
	}
	
	public String getDomainSummaryPage() {
		return "/nam/model/domain/domainSummaryPage.xhtml";
	}
	
	public String getDomainRecordPage() {
		return "/nam/model/domain/domainRecordPage.xhtml";
	}
	
	public String getDomainWizardPage() {
		return "/nam/model/domain/domainWizardPage.xhtml";
	}
	
	public String getDomainManagementPage() {
		return "/nam/model/domain/domainManagementPage.xhtml";
	}
	
	public String initializeDomainListPage() {
		String pageLevelKey = "domainList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Domains", "showDomainManagementPage()");
		String url = getDomainListPage();
		selectionContext.setCurrentArea("domain");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeDomainTreePage() {
		String pageLevelKey = "domainTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Domains", "showDomainTreePage()");
		String url = getDomainTreePage();
		selectionContext.setCurrentArea("domain");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeDomainSummaryPage(Domain domain) {
		String pageLevelKey = "domainSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Domains", "showDomainSummaryPage()");
		String url = getDomainSummaryPage();
		selectionContext.setCurrentArea("domain");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeDomainRecordPage() {
		Domain domain = selectionContext.getSelection("domain");
		String domainName = DomainUtil.getLabel(domain);
		
		String pageLevelKey = "domainRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Domains", "showDomainManagementPage()");
		addBreadcrumb(pageLevelKey, domainName, "showDomainRecordPage()");
		String url = getDomainRecordPage();
		selectionContext.setCurrentArea("domain");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeDomainCreationPage(Domain domain) {
		setPageTitle("New "+getDomainLabel(domain));
		setPageIcon("/icons/nam/ApplicationDomain16.gif");
		setSectionTitle("Domain Identification");
		domainWizard.setNewMode(true);
		
		String pageLevelKey = "domain";
		String wizardLevelKey = "domainWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Domains", "showDomainManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Domain", "showDomainWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showDomainWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showDomainWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showDomainWizardPage('Documentation')");
		
		domainIdentificationSection.setOwner("domainWizard");
		domainConfigurationSection.setOwner("domainWizard");
		domainDocumentationSection.setOwner("domainWizard");
		
		sections.clear();
		sections.add(domainIdentificationSection);
		sections.add(domainConfigurationSection);
		sections.add(domainDocumentationSection);
		
		String url = getDomainWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("domain");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeDomainUpdatePage(Domain domain) {
		setPageTitle(getDomainLabel(domain));
		setPageIcon("/icons/nam/Domain16.gif");
		setSectionTitle("Domain Overview");
		String domainName = DomainUtil.getLabel(domain);
		domainWizard.setNewMode(false);
		
		String pageLevelKey = "domain";
		String wizardLevelKey = "domainWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Domains", "showDomainManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(domainName, "showDomainWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showDomainWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showDomainWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showDomainWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showDomainWizardPage('Documentation')");
		
		domainOverviewSection.setOwner("domainWizard");
		domainIdentificationSection.setOwner("domainWizard");
		domainConfigurationSection.setOwner("domainWizard");
		domainDocumentationSection.setOwner("domainWizard");
		
		sections.clear();
		sections.add(domainOverviewSection);
		sections.add(domainIdentificationSection);
		sections.add(domainConfigurationSection);
		sections.add(domainDocumentationSection);
		
		String url = getDomainWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("domain");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeDomainManagementPage() {
		setPageTitle("Domains");
		setPageIcon("/icons/nam/Domain16.gif");
		String pageLevelKey = "domainManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Domains", "showDomainManagementPage()");
		String url = getDomainManagementPage();
		selectionContext.setCurrentArea("domain");
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
		setSectionType("domain");
		setSectionName("Overview");
		setSectionTitle("Overview of Domains");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public void initializeView(String elementType, String viewTitle, String sectionName) {
		String pageManagerName = NameUtil.uncapName(elementType) + "PageManager";
		PageManager<?> pageManager = BeanContext.getFromSession(pageManagerName);
		initializeView(pageManager, viewTitle, sectionName);
	}
	
	public void initializeView(PageManager<?> pageManager, String viewTitle, String sectionName) {
		pageManager.setSectionName(sectionName);
		pageManager.setSectionTitle(viewTitle);
		pageManager.setSectionType("domain");
		pageManager.setSectionIcon("/icons/nam/ApplicationDomain16.gif");
	}
	
	public String initializeModuleDomainsView() {
		String sectionTitle = "Module Domains";
		//String viewTitle = getServiceLabel(service);
		String currentArea = selectionContext.getCurrentArea();
		initializeView(currentArea, sectionTitle, "ModuleDomains");
		String viewLevelKey = "moduleDomains";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Modules", "showModuleManagementPage()");
		//String url = getServiceSummaryPage();
		selectionContext.setMessageDomain(viewLevelKey);
		//selectionContext.resetOrigin();
		//selectionContext.setUrl(url);
		sections.clear();
		return null;
	}
	
	public String initializeServiceDomainsView() {
		String sectionTitle = "Service Domains";
		//String viewTitle = getServiceLabel(service);
		String currentArea = selectionContext.getCurrentArea();
		initializeView(currentArea, sectionTitle, "ServiceDomains");
		String viewLevelKey = "serviceDomains";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Services", "showServiceManagementPage()");
		//String url = getServiceSummaryPage();
		selectionContext.setMessageDomain(viewLevelKey);
		//selectionContext.resetOrigin();
		//selectionContext.setUrl(url);
		sections.clear();
		return null;
	}
	
	
	public String initializeDomainSummaryView(Domain domain) {
		//String viewTitle = getDomainLabel(domain);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("domain");
		setSectionName("Summary");
		setSectionTitle("Summary of Domain Records");
		setSectionIcon("/icons/nam/Domain16.gif");
		String viewLevelKey = "domainSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Domains", "showDomainManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getDomainLabel(Domain domain) {
		String label = "Domain";
		String name = DomainUtil.getLabel(domain);
		if (name == null && domain.getName() != null)
			name = DomainUtil.getLabel(domain);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}

	protected void updateState() {
		AbstractWizardPage<Domain> page = domainWizard.getPage();
		if (page != null)
			setSectionTitle("Domain " + page.getName());
	}

	protected void updateState(Domain domain) {
		String domainName = DomainUtil.getLabel(domain);
		setSectionTitle(domainName + " Domain");
	}
	
}
