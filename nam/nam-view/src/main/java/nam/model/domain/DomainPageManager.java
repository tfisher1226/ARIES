package nam.model.domain;

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

import nam.model.Domain;
import nam.model.application.ApplicationPageManager;
import nam.model.module.ModulePageManager;
import nam.model.network.NetworkPageManager;
import nam.model.provider.ProviderPageManager;
import nam.model.service.ServicePageManager;
import nam.model.util.DomainUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("domainPageManager")
public class DomainPageManager extends AbstractPageManager<Domain> implements Serializable {
	
	@Inject
	private DomainWizard domainWizard;
	
	@Inject
	private DomainDataManager domainDataManager;

	@Inject
	private DomainInfoManager domainInfoManager;
	
	@Inject
	private DomainListManager domainListManager;
	
	@Inject
	private ApplicationPageManager applicationPageManager;
	
	@Inject
	private ModulePageManager modulePageManager;
	
	@Inject
	private NetworkPageManager networkPageManager;
	
	@Inject
	private ProviderPageManager providerPageManager;
	
	@Inject
	private ServicePageManager servicePageManager;
	
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
	}
	
	
	public void refresh() {
		refresh("projectList");
	}
	
	public void refreshLocal() {
		refreshLocal("domain");
	}
	
	public void refreshMembers() {
		refreshMembers("domain");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		//refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		domainDataManager.setScope(scope);
		domainListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		applicationPageManager.refreshLocal(scope);
		modulePageManager.refreshLocal(scope);
		networkPageManager.refreshLocal(scope);
		providerPageManager.refreshLocal(scope);
		servicePageManager.refreshLocal(scope);
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
	
	public void handleDomainSelected(@Observes @Selected Domain domain) {
		selectionContext.setSelection("domain",  domain);
		domainInfoManager.setRecord(domain);
	}
	
	public void handleDomainUnselected(@Observes @Unselected Domain domain) {
		selectionContext.unsetSelection("domain",  domain);
		domainInfoManager.unsetRecord(domain);
	}
	
	public void handleDomainChecked() {
		String scope = "domainSelection";
		DomainListObject listObject = domainListManager.getSelection();
		Domain domain = selectionContext.getSelection("domain");
		boolean checked = domainListManager.getCheckedState();
		listObject.setChecked(checked);
		if (checked) {
			domainInfoManager.setRecord(domain);
			selectionContext.setSelection(scope,  domain);
		} else {
			domainInfoManager.unsetRecord(domain);
			selectionContext.unsetSelection(scope,  domain);
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
		if (target.equals("service"))
			servicePageManager.refreshLocal(scope);
		refreshLocal(scope);
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
		refreshLocal();
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
		refreshLocal();
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
		return url;
	}
	
	public void initializeDefaultView() {
		setPageTitle("Domains");
		setPageIcon("/icons/nam/Domain16.gif");
		setSectionType("domain");
		setSectionName("Overview");
		setSectionTitle("Overview of Domains");
		setSectionIcon("/icons/nam/Overview16.gif");
		String viewLevelKey = "domainOverview";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Domains", "showDomainManagementPage()");
		String scope = "projectList";
		refreshLocal(scope);
		sections.clear();
	}
	
	public String initializeDomainApplicationsView() {
		setSectionType("domain");
		setSectionName("Applications");
		setSectionTitle("Applications");
		setSectionIcon("/icons/nam/Application16.gif");
		selectionContext.setMessageDomain("domainApplications");
		applicationPageManager.refreshLocal("domainSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeDomainModulesView() {
		setSectionType("domain");
		setSectionName("Modules");
		setSectionTitle("Modules");
		setSectionIcon("/icons/nam/Module16.gif");
		selectionContext.setMessageDomain("domainModules");
		modulePageManager.refreshLocal("domainSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeDomainNetworksView() {
		setSectionType("domain");
		setSectionName("Networks");
		setSectionTitle("Networks");
		setSectionIcon("/icons/nam/Network16.gif");
		selectionContext.setMessageDomain("domainNetworks");
		networkPageManager.refreshLocal("domainSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeDomainProvidersView() {
		setSectionType("domain");
		setSectionName("Providers");
		setSectionTitle("Providers");
		setSectionIcon("/icons/nam/Provider16.gif");
		selectionContext.setMessageDomain("domainProviders");
		providerPageManager.refreshLocal("domainSelection");
		refreshLocal("projectList");
		sections.clear();
		return null;
	}
	
	public String initializeDomainServicesView() {
		setSectionType("domain");
		setSectionName("Services");
		setSectionTitle("Services");
		setSectionIcon("/icons/nam/Service16.gif");
		selectionContext.setMessageDomain("domainServices");
		servicePageManager.refreshLocal("domainSelection");
		refreshLocal("projectList");
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
