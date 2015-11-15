package nam.ui.userInterface;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.interactor.InteractorPageManager;
import nam.model.language.LanguagePageManager;
import nam.ui.UserInterface;
import nam.ui.control.ControlPageManager;
import nam.ui.conversation.ConversationPageManager;
import nam.ui.design.SelectionContext;
import nam.ui.graphics.GraphicsPageManager;
import nam.ui.layout.LayoutPageManager;
import nam.ui.section.SectionPageManager;
import nam.ui.util.UserInterfaceUtil;

import org.aries.ui.AbstractPageManager;
import org.aries.ui.AbstractWizardPage;
import org.aries.ui.Breadcrumb;
import org.aries.util.NameUtil;


@SessionScoped
@Named("userInterfacePageManager")
public class UserInterfacePageManager extends AbstractPageManager<UserInterface> implements Serializable {
	
	@Inject
	private UserInterfaceWizard userInterfaceWizard;
	
	@Inject
	private UserInterfaceDataManager userInterfaceDataManager;
	
	@Inject
	private UserInterfaceListManager userInterfaceListManager;
	
	@Inject
	private LanguagePageManager languagePageManager;
	
	@Inject
	private GraphicsPageManager graphicsPageManager;
	
	@Inject
	private SectionPageManager sectionPageManager;
	
	@Inject
	private LayoutPageManager layoutPageManager;
	
	@Inject
	private ControlPageManager controlPageManager;
	
	@Inject
	private InteractorPageManager interactorPageManager;
	
	@Inject
	private ConversationPageManager conversationPageManager;
	
	@Inject
	private UserInterfaceRecord_OverviewSection userInterfaceOverviewSection;
	
	@Inject
	private UserInterfaceRecord_IdentificationSection userInterfaceIdentificationSection;
	
	@Inject
	private UserInterfaceRecord_ConfigurationSection userInterfaceConfigurationSection;
	
	@Inject
	private UserInterfaceRecord_DocumentationSection userInterfaceDocumentationSection;
	
	@Inject
	private UserInterfaceRecord_LanguagesSection userInterfaceLanguagesSection;
	
	@Inject
	private UserInterfaceRecord_GraphicsSection userInterfaceGraphicsSection;
	
	@Inject
	private UserInterfaceRecord_SectionsSection userInterfaceSectionsSection;
	
	@Inject
	private UserInterfaceRecord_LayoutsSection userInterfaceLayoutsSection;
	
	@Inject
	private UserInterfaceRecord_ControlsSection userInterfaceControlsSection;
	
	@Inject
	private UserInterfaceRecord_InteractorsSection userInterfaceInteractorsSection;
	
	@Inject
	private UserInterfaceRecord_ConversationsSection userInterfaceConversationsSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public UserInterfacePageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("userInterface");
	}
	
	public void refreshLocal() {
		refreshLocal("userInterface");
	}
	
	public void refreshMembers() {
		refreshMembers("userInterface");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		userInterfaceDataManager.setScope(scope);
		userInterfaceListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		languagePageManager.refresh(scope);
		graphicsPageManager.refresh(scope);
		sectionPageManager.refresh(scope);
		layoutPageManager.refresh(scope);
		controlPageManager.refresh(scope);
		interactorPageManager.refresh(scope);
		conversationPageManager.refresh(scope);
		userInterfaceListManager.refresh();
	}
	
	public String getUserInterfaceListPage() {
		return "/nam/ui/userInterface/userInterfaceListPage.xhtml";
	}
	
	public String getUserInterfaceTreePage() {
		return "/nam/ui/userInterface/userInterfaceTreePage.xhtml";
	}
	
	public String getUserInterfaceSummaryPage() {
		return "/nam/ui/userInterface/userInterfaceSummaryPage.xhtml";
	}
	
	public String getUserInterfaceRecordPage() {
		return "/nam/ui/userInterface/userInterfaceRecordPage.xhtml";
	}
	
	public String getUserInterfaceWizardPage() {
		return "/nam/ui/userInterface/userInterfaceWizardPage.xhtml";
	}
	
	public String getUserInterfaceManagementPage() {
		return "/nam/ui/userInterface/userInterfaceManagementPage.xhtml";
	}
	
	public String initializeUserInterfaceListPage() {
		String pageLevelKey = "userInterfaceList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "UserInterfaces", "showUserInterfaceManagementPage()");
		String url = getUserInterfaceListPage();
		selectionContext.setCurrentArea("userInterface");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeUserInterfaceTreePage() {
		String pageLevelKey = "userInterfaceTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "UserInterfaces", "showUserInterfaceTreePage()");
		String url = getUserInterfaceTreePage();
		selectionContext.setCurrentArea("userInterface");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeUserInterfaceSummaryPage(UserInterface userInterface) {
		String pageLevelKey = "userInterfaceSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "UserInterfaces", "showUserInterfaceSummaryPage()");
		String url = getUserInterfaceSummaryPage();
		selectionContext.setCurrentArea("userInterface");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeUserInterfaceRecordPage() {
		UserInterface userInterface = selectionContext.getSelection("userInterface");
		String userInterfaceName = userInterface.getName();
		
		String pageLevelKey = "userInterfaceRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "UserInterfaces", "showUserInterfaceManagementPage()");
		addBreadcrumb(pageLevelKey, userInterfaceName, "showUserInterfaceRecordPage()");
		String url = getUserInterfaceRecordPage();
		selectionContext.setCurrentArea("userInterface");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeUserInterfaceCreationPage(UserInterface userInterface) {
		setPageTitle("New "+getUserInterfaceLabel(userInterface));
		setPageIcon("/icons/nam/NewUserInterface16.gif");
		setSectionTitle("User-Interface Identification");
		userInterfaceWizard.setNewMode(true);
		
		String pageLevelKey = "userInterface";
		String wizardLevelKey = "userInterfaceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "UserInterfaces", "showUserInterfaceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New UserInterface", "showUserInterfaceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showUserInterfaceWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showUserInterfaceWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showUserInterfaceWizardPage('Documentation')");
		addBreadcrumb(wizardLevelKey, "Languages", "showUserInterfaceWizardPage('Languages')");
		addBreadcrumb(wizardLevelKey, "Graphics", "showUserInterfaceWizardPage('Graphics')");
		addBreadcrumb(wizardLevelKey, "Sections", "showUserInterfaceWizardPage('Sections')");
		addBreadcrumb(wizardLevelKey, "Layouts", "showUserInterfaceWizardPage('Layouts')");
		addBreadcrumb(wizardLevelKey, "Controls", "showUserInterfaceWizardPage('Controls')");
		addBreadcrumb(wizardLevelKey, "Interactors", "showUserInterfaceWizardPage('Interactors')");
		addBreadcrumb(wizardLevelKey, "Conversations", "showUserInterfaceWizardPage('Conversations')");
		
		userInterfaceIdentificationSection.setOwner("userInterfaceWizard");
		userInterfaceConfigurationSection.setOwner("userInterfaceWizard");
		userInterfaceDocumentationSection.setOwner("userInterfaceWizard");
		userInterfaceLanguagesSection.setOwner("userInterfaceWizard");
		userInterfaceGraphicsSection.setOwner("userInterfaceWizard");
		userInterfaceSectionsSection.setOwner("userInterfaceWizard");
		userInterfaceLayoutsSection.setOwner("userInterfaceWizard");
		userInterfaceControlsSection.setOwner("userInterfaceWizard");
		userInterfaceInteractorsSection.setOwner("userInterfaceWizard");
		userInterfaceConversationsSection.setOwner("userInterfaceWizard");
		
		sections.clear();
		sections.add(userInterfaceIdentificationSection);
		sections.add(userInterfaceConfigurationSection);
		sections.add(userInterfaceDocumentationSection);
		sections.add(userInterfaceLanguagesSection);
		sections.add(userInterfaceGraphicsSection);
		sections.add(userInterfaceSectionsSection);
		sections.add(userInterfaceLayoutsSection);
		sections.add(userInterfaceControlsSection);
		sections.add(userInterfaceInteractorsSection);
		sections.add(userInterfaceConversationsSection);
		
		String url = getUserInterfaceWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("userInterface");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeUserInterfaceUpdatePage(UserInterface userInterface) {
		setPageTitle(getUserInterfaceLabel(userInterface));
		setPageIcon("/icons/nam/UserInterface16.gif");
		setSectionTitle("User-Interface Overview");
		String userInterfaceName = userInterface.getName();
		userInterfaceWizard.setNewMode(false);
		
		String pageLevelKey = "userInterface";
		String wizardLevelKey = "userInterfaceWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "UserInterfaces", "showUserInterfaceManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(userInterfaceName, "showUserInterfaceWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showUserInterfaceWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showUserInterfaceWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showUserInterfaceWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showUserInterfaceWizardPage('Documentation')");
		addBreadcrumb(wizardLevelKey, "Languages", "showUserInterfaceWizardPage('Languages')");
		addBreadcrumb(wizardLevelKey, "Graphics", "showUserInterfaceWizardPage('Graphics')");
		addBreadcrumb(wizardLevelKey, "Sections", "showUserInterfaceWizardPage('Sections')");
		addBreadcrumb(wizardLevelKey, "Layouts", "showUserInterfaceWizardPage('Layouts')");
		addBreadcrumb(wizardLevelKey, "Controls", "showUserInterfaceWizardPage('Controls')");
		addBreadcrumb(wizardLevelKey, "Interactors", "showUserInterfaceWizardPage('Interactors')");
		addBreadcrumb(wizardLevelKey, "Conversations", "showUserInterfaceWizardPage('Conversations')");
		
		userInterfaceOverviewSection.setOwner("userInterfaceWizard");
		userInterfaceIdentificationSection.setOwner("userInterfaceWizard");
		userInterfaceConfigurationSection.setOwner("userInterfaceWizard");
		userInterfaceDocumentationSection.setOwner("userInterfaceWizard");
		userInterfaceLanguagesSection.setOwner("userInterfaceWizard");
		userInterfaceGraphicsSection.setOwner("userInterfaceWizard");
		userInterfaceSectionsSection.setOwner("userInterfaceWizard");
		userInterfaceLayoutsSection.setOwner("userInterfaceWizard");
		userInterfaceControlsSection.setOwner("userInterfaceWizard");
		userInterfaceInteractorsSection.setOwner("userInterfaceWizard");
		userInterfaceConversationsSection.setOwner("userInterfaceWizard");
		
		sections.clear();
		sections.add(userInterfaceOverviewSection);
		sections.add(userInterfaceIdentificationSection);
		sections.add(userInterfaceConfigurationSection);
		sections.add(userInterfaceDocumentationSection);
		sections.add(userInterfaceLanguagesSection);
		sections.add(userInterfaceGraphicsSection);
		sections.add(userInterfaceSectionsSection);
		sections.add(userInterfaceLayoutsSection);
		sections.add(userInterfaceControlsSection);
		sections.add(userInterfaceInteractorsSection);
		sections.add(userInterfaceConversationsSection);
		
		String url = getUserInterfaceWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("userInterface");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeUserInterfaceManagementPage() {
		setPageTitle("User-Interfaces");
		setPageIcon("/icons/nam/UserInterface16.gif");
		String pageLevelKey = "userInterfaceManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "UserInterfaces", "showUserInterfaceManagementPage()");
		String url = getUserInterfaceManagementPage();
		selectionContext.setCurrentArea("userInterface");
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
		setSectionType("userInterface");
		setSectionName("Overview");
		setSectionTitle("Overview of User-Interfaces");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeUserInterfaceLanguagesView() {
		setSectionType("userInterface");
		setSectionName("Languages");
		setSectionTitle("User-Interface Languages");
		setSectionIcon("/icons/nam/Language16.gif");
		String viewLevelKey = "userInterfaceLanguages";
		selectionContext.setMessageDomain(viewLevelKey);
		languagePageManager.refresh("userInterface");
		userInterfaceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserInterfaceGraphicsView() {
		setSectionType("userInterface");
		setSectionName("Graphics");
		setSectionTitle("User-Interface Graphics");
		setSectionIcon("/icons/nam/Graphics16.gif");
		String viewLevelKey = "userInterfaceGraphics";
		selectionContext.setMessageDomain(viewLevelKey);
		graphicsPageManager.refresh("userInterface");
		userInterfaceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserInterfaceSectionsView() {
		setSectionType("userInterface");
		setSectionName("Sections");
		setSectionTitle("User-Interface Sections");
		setSectionIcon("/icons/nam/Section16.gif");
		String viewLevelKey = "userInterfaceSections";
		selectionContext.setMessageDomain(viewLevelKey);
		sectionPageManager.refresh("userInterface");
		userInterfaceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserInterfaceLayoutsView() {
		setSectionType("userInterface");
		setSectionName("Layouts");
		setSectionTitle("User-Interface Layouts");
		setSectionIcon("/icons/nam/Layout16.gif");
		String viewLevelKey = "userInterfaceLayouts";
		selectionContext.setMessageDomain(viewLevelKey);
		layoutPageManager.refresh("userInterface");
		userInterfaceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserInterfaceControlsView() {
		setSectionType("userInterface");
		setSectionName("Controls");
		setSectionTitle("User-Interface Controls");
		setSectionIcon("/icons/nam/Control16.gif");
		String viewLevelKey = "userInterfaceControls";
		selectionContext.setMessageDomain(viewLevelKey);
		controlPageManager.refresh("userInterface");
		userInterfaceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserInterfaceInteractorsView() {
		setSectionType("userInterface");
		setSectionName("Interactors");
		setSectionTitle("User-Interface Interactors");
		setSectionIcon("/icons/nam/Interactor16.gif");
		String viewLevelKey = "userInterfaceInteractors";
		selectionContext.setMessageDomain(viewLevelKey);
		interactorPageManager.refresh("userInterface");
		userInterfaceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserInterfaceConversationsView() {
		setSectionType("userInterface");
		setSectionName("Conversations");
		setSectionTitle("User-Interface Conversations");
		setSectionIcon("/icons/nam/Conversation16.gif");
		String viewLevelKey = "userInterfaceConversations";
		selectionContext.setMessageDomain(viewLevelKey);
		conversationPageManager.refresh("userInterface");
		userInterfaceListManager.refresh();
		sections.clear();
		return null;
	}
	
	public String initializeUserInterfaceSummaryView(UserInterface userInterface) {
		//String viewTitle = getUserInterfaceLabel(userInterface);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("userInterface");
		setSectionName("Summary");
		setSectionTitle("Summary of User-Interface Records");
		setSectionIcon("/icons/nam/UserInterface16.gif");
		String viewLevelKey = "userInterfaceSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "UserInterfaces", "showUserInterfaceManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getUserInterfaceLabel(UserInterface userInterface) {
		String label = "User-Interface";
		String name = UserInterfaceUtil.getLabel(userInterface);
		if (name == null && userInterface.getName() != null)
			name = NameUtil.capName(userInterface.getName());
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<UserInterface> page = userInterfaceWizard.getPage();
		if (page != null)
			setSectionTitle("User-Interface " + page.getName());
	}
	
	protected void updateState(UserInterface userInterface) {
		String userInterfaceName = NameUtil.capName(userInterface.getName());
		setSectionTitle(userInterfaceName + " User-Interface");
	}
	
}
