package nam.model.messaging;

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

import nam.model.Messaging;
import nam.model.channel.ChannelPageManager;
import nam.model.domain.DomainPageManager;
import nam.model._import.ImportPageManager;
import nam.model.interactor.InteractorPageManager;
import nam.model.listener.ListenerPageManager;
import nam.model.transport.TransportPageManager;
import nam.model.util.MessagingUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingPageManager")
public class MessagingPageManager extends AbstractPageManager<Messaging> implements Serializable {
	
	@Inject
	private MessagingWizard messagingWizard;
	
	@Inject
	private MessagingDataManager messagingDataManager;
	
	@Inject
	private MessagingListManager messagingListManager;
	
	@Inject
	private ImportPageManager importPageManager;
	
	@Inject
	private DomainPageManager domainPageManager;
	
	@Inject
	private ChannelPageManager channelPageManager;
	
	@Inject
	private ListenerPageManager listenerPageManager;
	
	@Inject
	private TransportPageManager transportPageManager;
	
	@Inject
	private InteractorPageManager interactorPageManager;
	
	@Inject
	private MessagingRecord_OverviewSection messagingOverviewSection;
	
	@Inject
	private MessagingRecord_IdentificationSection messagingIdentificationSection;
	
	@Inject
	private MessagingRecord_ConfigurationSection messagingConfigurationSection;
	
	@Inject
	private MessagingRecord_DocumentationSection messagingDocumentationSection;
	
	@Inject
	private MessagingRecord_OverviewsSection messagingOverviewsSection;
	
	@Inject
	private MessagingRecord_ImportsSection messagingImportsSection;
	
	@Inject
	private MessagingRecord_DomainsSection messagingDomainsSection;
	
	@Inject
	private MessagingRecord_ChannelsSection messagingChannelsSection;
	
	@Inject
	private MessagingRecord_ListenersSection messagingListenersSection;
	
	@Inject
	private MessagingRecord_TransportsSection messagingTransportsSection;
	
	@Inject
	private MessagingRecord_InteractorsSection messagingInteractorsSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public MessagingPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("messaging");
	}
	
	public void refreshLocal() {
		refreshLocal("messaging");
	}
	
	public void refreshMembers() {
		refreshMembers("messaging");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		messagingDataManager.setScope(scope);
		messagingListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		importPageManager.refresh(scope);
		domainPageManager.refresh(scope);
		channelPageManager.refresh(scope);
		listenerPageManager.refresh(scope);
		transportPageManager.refresh(scope);
		interactorPageManager.refresh(scope);
		messagingListManager.refresh();
	}
	
	public String getMessagingListPage() {
		return "/nam/model/messaging/messagingListPage.xhtml";
	}
	
	public String getMessagingTreePage() {
		return "/nam/model/messaging/messagingTreePage.xhtml";
	}
	
	public String getMessagingSummaryPage() {
		return "/nam/model/messaging/messagingSummaryPage.xhtml";
	}
	
	public String getMessagingRecordPage() {
		return "/nam/model/messaging/messagingRecordPage.xhtml";
	}
	
	public String getMessagingWizardPage() {
		return "/nam/model/messaging/messagingWizardPage.xhtml";
	}
	
	public String getMessagingManagementPage() {
		return "/nam/model/messaging/messagingManagementPage.xhtml";
	}
	
	public String initializeMessagingListPage() {
		String pageLevelKey = "messagingList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Messaging", "showMessagingManagementPage()");
		String url = getMessagingListPage();
		selectionContext.setCurrentArea("messaging");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMessagingTreePage() {
		String pageLevelKey = "messagingTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Messaging", "showMessagingTreePage()");
		String url = getMessagingTreePage();
		selectionContext.setCurrentArea("messaging");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMessagingSummaryPage(Messaging messaging) {
		String pageLevelKey = "messagingSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Messaging", "showMessagingSummaryPage()");
		String url = getMessagingSummaryPage();
		selectionContext.setCurrentArea("messaging");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeMessagingRecordPage() {
		Messaging messaging = selectionContext.getSelection("messaging");
		String messagingName = MessagingUtil.getLabel(messaging);
		
		String pageLevelKey = "messagingRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Messaging", "showMessagingManagementPage()");
		addBreadcrumb(pageLevelKey, messagingName, "showMessagingRecordPage()");
		String url = getMessagingRecordPage();
		selectionContext.setCurrentArea("messaging");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeMessagingCreationPage(Messaging messaging) {
		setPageTitle("New "+getMessagingLabel(messaging));
		setPageIcon("/icons/nam/NewMessaging16.gif");
		setSectionTitle("Messaging Identification");
		messagingWizard.setNewMode(true);
		
		String pageLevelKey = "messaging";
		String wizardLevelKey = "messagingWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Messaging", "showMessagingManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Messaging", "showMessagingWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overviews", "showMessagingWizardPage('Overviews')");
		addBreadcrumb(wizardLevelKey, "Imports", "showMessagingWizardPage('Imports')");
		addBreadcrumb(wizardLevelKey, "Domains", "showMessagingWizardPage('Domains')");
		addBreadcrumb(wizardLevelKey, "Channels", "showMessagingWizardPage('Channels')");
		addBreadcrumb(wizardLevelKey, "Listeners", "showMessagingWizardPage('Listeners')");
		addBreadcrumb(wizardLevelKey, "Transports", "showMessagingWizardPage('Transports')");
		addBreadcrumb(wizardLevelKey, "Interactors", "showMessagingWizardPage('Interactors')");
		
		messagingIdentificationSection.setOwner("messagingWizard");
		messagingConfigurationSection.setOwner("messagingWizard");
		messagingDocumentationSection.setOwner("messagingWizard");
		messagingOverviewsSection.setOwner("messagingWizard");
		messagingImportsSection.setOwner("messagingWizard");
		messagingDomainsSection.setOwner("messagingWizard");
		messagingChannelsSection.setOwner("messagingWizard");
		messagingListenersSection.setOwner("messagingWizard");
		messagingTransportsSection.setOwner("messagingWizard");
		messagingInteractorsSection.setOwner("messagingWizard");
		
		sections.clear();
		sections.add(messagingIdentificationSection);
		sections.add(messagingConfigurationSection);
		sections.add(messagingDocumentationSection);
		sections.add(messagingOverviewsSection);
		sections.add(messagingImportsSection);
		sections.add(messagingDomainsSection);
		sections.add(messagingChannelsSection);
		sections.add(messagingListenersSection);
		sections.add(messagingTransportsSection);
		sections.add(messagingInteractorsSection);
		
		String url = getMessagingWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("messaging");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeMessagingUpdatePage(Messaging messaging) {
		setPageTitle(getMessagingLabel(messaging));
		setPageIcon("/icons/nam/Messaging16.gif");
		setSectionTitle("Messaging Overview");
		String messagingName = MessagingUtil.getLabel(messaging);
		messagingWizard.setNewMode(false);
		
		String pageLevelKey = "messaging";
		String wizardLevelKey = "messagingWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Messaging", "showMessagingManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(messagingName, "showMessagingWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overviews", "showMessagingWizardPage('Overviews')");
		addBreadcrumb(wizardLevelKey, "Imports", "showMessagingWizardPage('Imports')");
		addBreadcrumb(wizardLevelKey, "Domains", "showMessagingWizardPage('Domains')");
		addBreadcrumb(wizardLevelKey, "Channels", "showMessagingWizardPage('Channels')");
		addBreadcrumb(wizardLevelKey, "Listeners", "showMessagingWizardPage('Listeners')");
		addBreadcrumb(wizardLevelKey, "Transports", "showMessagingWizardPage('Transports')");
		addBreadcrumb(wizardLevelKey, "Interactors", "showMessagingWizardPage('Interactors')");
		
		messagingOverviewSection.setOwner("messagingWizard");
		messagingIdentificationSection.setOwner("messagingWizard");
		messagingConfigurationSection.setOwner("messagingWizard");
		messagingDocumentationSection.setOwner("messagingWizard");
		messagingOverviewsSection.setOwner("messagingWizard");
		messagingImportsSection.setOwner("messagingWizard");
		messagingDomainsSection.setOwner("messagingWizard");
		messagingChannelsSection.setOwner("messagingWizard");
		messagingListenersSection.setOwner("messagingWizard");
		messagingTransportsSection.setOwner("messagingWizard");
		messagingInteractorsSection.setOwner("messagingWizard");
		
		sections.clear();
		sections.add(messagingOverviewSection);
		sections.add(messagingIdentificationSection);
		sections.add(messagingConfigurationSection);
		sections.add(messagingDocumentationSection);
		sections.add(messagingOverviewsSection);
		sections.add(messagingImportsSection);
		sections.add(messagingDomainsSection);
		sections.add(messagingChannelsSection);
		sections.add(messagingListenersSection);
		sections.add(messagingTransportsSection);
		sections.add(messagingInteractorsSection);
		
		String url = getMessagingWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("messaging");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeMessagingManagementPage() {
		setPageTitle("Messaging");
		setPageIcon("/icons/nam/Messaging16.gif");
		String pageLevelKey = "messagingManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Messaging", "showMessagingManagementPage()");
		String url = getMessagingManagementPage();
		selectionContext.setCurrentArea("messaging");
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
		setSectionType("messaging");
		setSectionName("Overview");
		setSectionTitle("Overview of Messaging");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeMessagingOverviewsView() {
		setSectionType("messaging");
		setSectionName("Overviews");
		setSectionTitle("Messaging Overviews");
		setSectionIcon("/icons/nam/Overview16.gif");
		selectionContext.setMessageDomain("messagingOverviews");
		//overviewPageManager.refresh("messaging");
		messagingListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeMessagingImportsView() {
		setSectionType("messaging");
		setSectionName("Imports");
		setSectionTitle("Messaging Imports");
		setSectionIcon("/icons/nam/Import16.gif");
		selectionContext.setMessageDomain("messagingImports");
		importPageManager.refresh("messaging");
		messagingListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeMessagingDomainsView() {
		setSectionType("messaging");
		setSectionName("Domains");
		setSectionTitle("Messaging Domains");
		setSectionIcon("/icons/nam/Domain16.gif");
		selectionContext.setMessageDomain("messagingDomains");
		domainPageManager.refresh("messaging");
		messagingListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeMessagingChannelsView() {
		setSectionType("messaging");
		setSectionName("Channels");
		setSectionTitle("Messaging Channels");
		setSectionIcon("/icons/nam/Channel16.gif");
		selectionContext.setMessageDomain("messagingChannels");
		channelPageManager.refresh("messaging");
		messagingListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeMessagingListenersView() {
		setSectionType("messaging");
		setSectionName("Listeners");
		setSectionTitle("Messaging Listeners");
		setSectionIcon("/icons/nam/Listener16.gif");
		selectionContext.setMessageDomain("messagingListeners");
		listenerPageManager.refresh("messaging");
		messagingListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeMessagingTransportsView() {
		setSectionType("messaging");
		setSectionName("Transports");
		setSectionTitle("Messaging Transports");
		setSectionIcon("/icons/nam/Transport16.gif");
		selectionContext.setMessageDomain("messagingTransports");
		transportPageManager.refresh("messaging");
		messagingListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeMessagingInteractorsView() {
		setSectionType("messaging");
		setSectionName("Interactors");
		setSectionTitle("Messaging Interactors");
		setSectionIcon("/icons/nam/Interactor16.gif");
		selectionContext.setMessageDomain("messagingInteractors");
		interactorPageManager.refresh("messaging");
		messagingListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeMessagingSummaryView(Messaging messaging) {
		//String viewTitle = getMessagingLabel(messaging);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("messaging");
		setSectionName("Summary");
		setSectionTitle("Summary of Messaging Records");
		setSectionIcon("/icons/nam/Messaging16.gif");
		String viewLevelKey = "messagingSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Messaging", "showMessagingManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getMessagingLabel(Messaging messaging) {
		String label = "Messaging";
		String name = MessagingUtil.getLabel(messaging);
		if (name == null && messaging.getName() != null)
			name = MessagingUtil.getLabel(messaging);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Messaging> page = messagingWizard.getPage();
		if (page != null)
			setSectionTitle("Messaging " + page.getName());
	}
	
	protected void updateState(Messaging messaging) {
		String messagingName = MessagingUtil.getLabel(messaging);
		setSectionTitle(messagingName + " Messaging");
	}
	
}
