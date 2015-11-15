package nam.model.messagingProvider;

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

import nam.model.MessagingProvider;
import nam.model.util.MessagingProviderUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingProviderPageManager")
public class MessagingProviderPageManager extends AbstractPageManager<MessagingProvider> implements Serializable {
	
	@Inject
	private MessagingProviderWizard messagingProviderWizard;
	
	@Inject
	private MessagingProviderDataManager messagingProviderDataManager;
	
	@Inject
	private MessagingProviderListManager messagingProviderListManager;
	
	@Inject
	private MessagingProviderRecord_OverviewSection messagingProviderOverviewSection;
	
	@Inject
	private MessagingProviderRecord_IdentificationSection messagingProviderIdentificationSection;
	
	@Inject
	private MessagingProviderRecord_ConfigurationSection messagingProviderConfigurationSection;
	
	@Inject
	private MessagingProviderRecord_DocumentationSection messagingProviderDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public MessagingProviderPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("messagingProvider");
	}
	
	public void refreshLocal() {
		refreshLocal("messagingProvider");
	}
	
	public void refreshMembers() {
		refreshMembers("messagingProvider");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		messagingProviderDataManager.setScope(scope);
		messagingProviderListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		messagingProviderListManager.refresh();
	}
	
	public String getMessagingProviderListPage() {
		return "/nam/model/messagingProvider/messagingProviderListPage.xhtml";
	}
	
	public String getMessagingProviderTreePage() {
		return "/nam/model/messagingProvider/messagingProviderTreePage.xhtml";
	}
	
	public String getMessagingProviderSummaryPage() {
		return "/nam/model/messagingProvider/messagingProviderSummaryPage.xhtml";
	}
	
	public String getMessagingProviderRecordPage() {
		return "/nam/model/messagingProvider/messagingProviderRecordPage.xhtml";
	}
	
	public String getMessagingProviderWizardPage() {
		return "/nam/model/messagingProvider/messagingProviderWizardPage.xhtml";
	}
	
	public String getMessagingProviderManagementPage() {
		return "/nam/model/messagingProvider/messagingProviderManagementPage.xhtml";
	}
	
	public String initializeMessagingProviderListPage() {
		String pageLevelKey = "messagingProviderList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "MessagingProviders", "showMessagingProviderManagementPage()");
		String url = getMessagingProviderListPage();
		selectionContext.setCurrentArea("messagingProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMessagingProviderTreePage() {
		String pageLevelKey = "messagingProviderTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "MessagingProviders", "showMessagingProviderTreePage()");
		String url = getMessagingProviderTreePage();
		selectionContext.setCurrentArea("messagingProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMessagingProviderSummaryPage(MessagingProvider messagingProvider) {
		String pageLevelKey = "messagingProviderSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "MessagingProviders", "showMessagingProviderSummaryPage()");
		String url = getMessagingProviderSummaryPage();
		selectionContext.setCurrentArea("messagingProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMessagingProviderRecordPage() {
		MessagingProvider messagingProvider = selectionContext.getSelection("messagingProvider");
		String messagingProviderName = messagingProvider.getName();
		
		String pageLevelKey = "messagingProviderRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "MessagingProviders", "showMessagingProviderManagementPage()");
		addBreadcrumb(pageLevelKey, messagingProviderName, "showMessagingProviderRecordPage()");
		String url = getMessagingProviderRecordPage();
		selectionContext.setCurrentArea("messagingProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeMessagingProviderCreationPage(MessagingProvider messagingProvider) {
		setPageTitle("New "+getMessagingProviderLabel(messagingProvider));
		setPageIcon("/icons/nam/NewMessagingProvider16.gif");
		setSectionTitle("MessagingProvider Identification");
		messagingProviderWizard.setNewMode(true);
		
		String pageLevelKey = "messagingProvider";
		String wizardLevelKey = "messagingProviderWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "MessagingProviders", "showMessagingProviderManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New MessagingProvider", "showMessagingProviderWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showMessagingProviderWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showMessagingProviderWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showMessagingProviderWizardPage('Documentation')");
		
		messagingProviderIdentificationSection.setOwner("messagingProviderWizard");
		messagingProviderConfigurationSection.setOwner("messagingProviderWizard");
		messagingProviderDocumentationSection.setOwner("messagingProviderWizard");
		
		sections.clear();
		sections.add(messagingProviderIdentificationSection);
		sections.add(messagingProviderConfigurationSection);
		sections.add(messagingProviderDocumentationSection);
		
		String url = getMessagingProviderWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("messagingProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeMessagingProviderUpdatePage(MessagingProvider messagingProvider) {
		setPageTitle(getMessagingProviderLabel(messagingProvider));
		setPageIcon("/icons/nam/MessagingProvider16.gif");
		setSectionTitle("MessagingProvider Overview");
		String messagingProviderName = messagingProvider.getName();
		messagingProviderWizard.setNewMode(false);
		
		String pageLevelKey = "messagingProvider";
		String wizardLevelKey = "messagingProviderWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "MessagingProviders", "showMessagingProviderManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(messagingProviderName, "showMessagingProviderWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showMessagingProviderWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showMessagingProviderWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showMessagingProviderWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showMessagingProviderWizardPage('Documentation')");
		
		messagingProviderOverviewSection.setOwner("messagingProviderWizard");
		messagingProviderIdentificationSection.setOwner("messagingProviderWizard");
		messagingProviderConfigurationSection.setOwner("messagingProviderWizard");
		messagingProviderDocumentationSection.setOwner("messagingProviderWizard");
		
		sections.clear();
		sections.add(messagingProviderOverviewSection);
		sections.add(messagingProviderIdentificationSection);
		sections.add(messagingProviderConfigurationSection);
		sections.add(messagingProviderDocumentationSection);
		
		String url = getMessagingProviderWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("messagingProvider");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeMessagingProviderManagementPage() {
		setPageTitle("MessagingProviders");
		setPageIcon("/icons/nam/MessagingProvider16.gif");
		String pageLevelKey = "messagingProviderManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "MessagingProviders", "showMessagingProviderManagementPage()");
		String url = getMessagingProviderManagementPage();
		selectionContext.setCurrentArea("messagingProvider");
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
		setSectionType("messagingProvider");
		setSectionName("Overview");
		setSectionTitle("Overview of MessagingProviders");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeMessagingProviderSummaryView(MessagingProvider messagingProvider) {
		//String viewTitle = getMessagingProviderLabel(messagingProvider);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("messagingProvider");
		setSectionName("Summary");
		setSectionTitle("Summary of MessagingProvider Records");
		setSectionIcon("/icons/nam/MessagingProvider16.gif");
		String viewLevelKey = "messagingProviderSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "MessagingProviders", "showMessagingProviderManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getMessagingProviderLabel(MessagingProvider messagingProvider) {
		String label = "MessagingProvider";
		String name = MessagingProviderUtil.getLabel(messagingProvider);
		if (name == null && messagingProvider.getName() != null)
			name = NameUtil.capName(messagingProvider.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<MessagingProvider> page = messagingProviderWizard.getPage();
		if (page != null)
			setSectionTitle("MessagingProvider " + page.getName());
	}
	
	protected void updateState(MessagingProvider messagingProvider) {
		String messagingProviderName = NameUtil.capName(messagingProvider.getName());
		setSectionTitle(messagingProviderName + " MessagingProvider");
	}
	
}
