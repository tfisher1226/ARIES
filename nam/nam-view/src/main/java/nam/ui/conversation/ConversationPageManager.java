package nam.ui.conversation;

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

import nam.ui.Conversation;
import nam.ui.design.SelectionContext;
import nam.ui.util.ConversationUtil;


@SessionScoped
@Named("conversationPageManager")
public class ConversationPageManager extends AbstractPageManager<Conversation> implements Serializable {
	
	@Inject
	private ConversationWizard conversationWizard;
	
	@Inject
	private ConversationDataManager conversationDataManager;
	
	@Inject
	private ConversationListManager conversationListManager;
	
	@Inject
	private ConversationRecord_OverviewSection conversationOverviewSection;
	
	@Inject
	private ConversationRecord_IdentificationSection conversationIdentificationSection;
	
	@Inject
	private ConversationRecord_ConfigurationSection conversationConfigurationSection;
	
	@Inject
	private ConversationRecord_DocumentationSection conversationDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ConversationPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("conversation");
	}
	
	public void refreshLocal() {
		refreshLocal("conversation");
	}
	
	public void refreshMembers() {
		refreshMembers("conversation");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		conversationDataManager.setScope(scope);
		conversationListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		conversationListManager.refresh();
	}
	
	public String getConversationListPage() {
		return "/nam/ui/conversation/conversationListPage.xhtml";
	}
	
	public String getConversationTreePage() {
		return "/nam/ui/conversation/conversationTreePage.xhtml";
	}
	
	public String getConversationSummaryPage() {
		return "/nam/ui/conversation/conversationSummaryPage.xhtml";
	}
	
	public String getConversationRecordPage() {
		return "/nam/ui/conversation/conversationRecordPage.xhtml";
	}
	
	public String getConversationWizardPage() {
		return "/nam/ui/conversation/conversationWizardPage.xhtml";
	}
	
	public String getConversationManagementPage() {
		return "/nam/ui/conversation/conversationManagementPage.xhtml";
	}
	
	public String initializeConversationListPage() {
		String pageLevelKey = "conversationList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Conversations", "showConversationManagementPage()");
		String url = getConversationListPage();
		selectionContext.setCurrentArea("conversation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeConversationTreePage() {
		String pageLevelKey = "conversationTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Conversations", "showConversationTreePage()");
		String url = getConversationTreePage();
		selectionContext.setCurrentArea("conversation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeConversationSummaryPage(Conversation conversation) {
		String pageLevelKey = "conversationSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Conversations", "showConversationSummaryPage()");
		String url = getConversationSummaryPage();
		selectionContext.setCurrentArea("conversation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeConversationRecordPage() {
		Conversation conversation = selectionContext.getSelection("conversation");
		String conversationName = conversation.getName();
		
		String pageLevelKey = "conversationRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Conversations", "showConversationManagementPage()");
		addBreadcrumb(pageLevelKey, conversationName, "showConversationRecordPage()");
		String url = getConversationRecordPage();
		selectionContext.setCurrentArea("conversation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeConversationCreationPage(Conversation conversation) {
		setPageTitle("New "+getConversationLabel(conversation));
		setPageIcon("/icons/nam/NewConversation16.gif");
		setSectionTitle("Conversation Identification");
		conversationWizard.setNewMode(true);
		
		String pageLevelKey = "conversation";
		String wizardLevelKey = "conversationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Conversations", "showConversationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Conversation", "showConversationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showConversationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showConversationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showConversationWizardPage('Documentation')");
		
		conversationIdentificationSection.setOwner("conversationWizard");
		conversationConfigurationSection.setOwner("conversationWizard");
		conversationDocumentationSection.setOwner("conversationWizard");
		
		sections.clear();
		sections.add(conversationIdentificationSection);
		sections.add(conversationConfigurationSection);
		sections.add(conversationDocumentationSection);
		
		String url = getConversationWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("conversation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeConversationUpdatePage(Conversation conversation) {
		setPageTitle(getConversationLabel(conversation));
		setPageIcon("/icons/nam/Conversation16.gif");
		setSectionTitle("Conversation Overview");
		String conversationName = conversation.getName();
		conversationWizard.setNewMode(false);
		
		String pageLevelKey = "conversation";
		String wizardLevelKey = "conversationWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Conversations", "showConversationManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(conversationName, "showConversationWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showConversationWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showConversationWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showConversationWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showConversationWizardPage('Documentation')");
		
		conversationOverviewSection.setOwner("conversationWizard");
		conversationIdentificationSection.setOwner("conversationWizard");
		conversationConfigurationSection.setOwner("conversationWizard");
		conversationDocumentationSection.setOwner("conversationWizard");
		
		sections.clear();
		sections.add(conversationOverviewSection);
		sections.add(conversationIdentificationSection);
		sections.add(conversationConfigurationSection);
		sections.add(conversationDocumentationSection);
		
		String url = getConversationWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("conversation");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeConversationManagementPage() {
		setPageTitle("Conversations");
		setPageIcon("/icons/nam/Conversation16.gif");
		String pageLevelKey = "conversationManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Conversations", "showConversationManagementPage()");
		String url = getConversationManagementPage();
		selectionContext.setCurrentArea("conversation");
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
		setSectionType("conversation");
		setSectionName("Overview");
		setSectionTitle("Overview of Conversations");
		setSectionIcon("/icons/nam/Conversation16.gif");
	}
	
	public String initializeConversationSummaryView(Conversation conversation) {
		//String viewTitle = getConversationLabel(conversation);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("conversation");
		setSectionName("Summary");
		setSectionTitle("Summary of Conversation Records");
		setSectionIcon("/icons/nam/Conversation16.gif");
		String viewLevelKey = "conversationSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Conversations", "showConversationManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getConversationLabel(Conversation conversation) {
		String label = "Conversation";
		String name = ConversationUtil.getLabel(conversation);
		if (name == null && conversation.getName() != null)
			name = NameUtil.capName(conversation.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Conversation> page = conversationWizard.getPage();
		if (page != null)
			setSectionTitle("Conversation " + page.getName());
	}
	
	protected void updateState(Conversation conversation) {
		String conversationName = NameUtil.capName(conversation.getName());
		setSectionTitle(conversationName + " Conversation");
	}
	
}
