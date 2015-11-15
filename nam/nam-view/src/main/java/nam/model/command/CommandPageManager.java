package nam.model.command;

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

import nam.model.Command;
import nam.model.util.CommandUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("commandPageManager")
public class CommandPageManager extends AbstractPageManager<Command> implements Serializable {
	
	@Inject
	private CommandWizard commandWizard;
	
	@Inject
	private CommandDataManager commandDataManager;
	
	@Inject
	private CommandListManager commandListManager;
	
	@Inject
	private CommandRecord_OverviewSection commandOverviewSection;
	
	@Inject
	private CommandRecord_IdentificationSection commandIdentificationSection;
	
	@Inject
	private CommandRecord_ConfigurationSection commandConfigurationSection;
	
	@Inject
	private CommandRecord_DocumentationSection commandDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public CommandPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("command");
	}
	
	public void refreshLocal() {
		refreshLocal("command");
	}
	
	public void refreshMembers() {
		refreshMembers("command");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		commandDataManager.setScope(scope);
		commandListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		commandListManager.refresh();
	}
	
	public String getCommandListPage() {
		return "/nam/model/command/commandListPage.xhtml";
	}
	
	public String getCommandTreePage() {
		return "/nam/model/command/commandTreePage.xhtml";
	}
	
	public String getCommandSummaryPage() {
		return "/nam/model/command/commandSummaryPage.xhtml";
	}
	
	public String getCommandRecordPage() {
		return "/nam/model/command/commandRecordPage.xhtml";
	}
	
	public String getCommandWizardPage() {
		return "/nam/model/command/commandWizardPage.xhtml";
	}
	
	public String getCommandManagementPage() {
		return "/nam/model/command/commandManagementPage.xhtml";
	}
	
	public String initializeCommandListPage() {
		String pageLevelKey = "commandList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Commands", "showCommandManagementPage()");
		String url = getCommandListPage();
		selectionContext.setCurrentArea("command");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeCommandTreePage() {
		String pageLevelKey = "commandTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Commands", "showCommandTreePage()");
		String url = getCommandTreePage();
		selectionContext.setCurrentArea("command");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeCommandSummaryPage(Command command) {
		String pageLevelKey = "commandSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Commands", "showCommandSummaryPage()");
		String url = getCommandSummaryPage();
		selectionContext.setCurrentArea("command");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeCommandRecordPage() {
		Command command = selectionContext.getSelection("command");
		String commandName = CommandUtil.getLabel(command);
		
		String pageLevelKey = "commandRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Commands", "showCommandManagementPage()");
		addBreadcrumb(pageLevelKey, commandName, "showCommandRecordPage()");
		String url = getCommandRecordPage();
		selectionContext.setCurrentArea("command");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeCommandCreationPage(Command command) {
		setPageTitle("New "+getCommandLabel(command));
		setPageIcon("/icons/nam/NewCommand16.gif");
		setSectionTitle("Command Identification");
		commandWizard.setNewMode(true);
		
		String pageLevelKey = "command";
		String wizardLevelKey = "commandWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Commands", "showCommandManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Command", "showCommandWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Identification", "showCommandWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showCommandWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showCommandWizardPage('Documentation')");
		
		commandIdentificationSection.setOwner("commandWizard");
		commandConfigurationSection.setOwner("commandWizard");
		commandDocumentationSection.setOwner("commandWizard");
		
		sections.clear();
		sections.add(commandIdentificationSection);
		sections.add(commandConfigurationSection);
		sections.add(commandDocumentationSection);
		
		String url = getCommandWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("command");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeCommandUpdatePage(Command command) {
		setPageTitle(getCommandLabel(command));
		setPageIcon("/icons/nam/Command16.gif");
		setSectionTitle("Command Overview");
		String commandName = CommandUtil.getLabel(command);
		commandWizard.setNewMode(false);
		
		String pageLevelKey = "command";
		String wizardLevelKey = "commandWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Commands", "showCommandManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(commandName, "showCommandWizardPage()"));
		
		addBreadcrumb(wizardLevelKey, "Overview", "showCommandWizardPage('Overview')");
		addBreadcrumb(wizardLevelKey, "Identification", "showCommandWizardPage('Identification')");
		addBreadcrumb(wizardLevelKey, "Configuration", "showCommandWizardPage('Configuration')");
		addBreadcrumb(wizardLevelKey, "Documentation", "showCommandWizardPage('Documentation')");
		
		commandOverviewSection.setOwner("commandWizard");
		commandIdentificationSection.setOwner("commandWizard");
		commandConfigurationSection.setOwner("commandWizard");
		commandDocumentationSection.setOwner("commandWizard");
		
		sections.clear();
		sections.add(commandOverviewSection);
		sections.add(commandIdentificationSection);
		sections.add(commandConfigurationSection);
		sections.add(commandDocumentationSection);
		
		String url = getCommandWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("command");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeCommandManagementPage() {
		setPageTitle("Commands");
		setPageIcon("/icons/nam/Command16.gif");
		String pageLevelKey = "commandManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Commands", "showCommandManagementPage()");
		String url = getCommandManagementPage();
		selectionContext.setCurrentArea("command");
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
		setSectionType("command");
		setSectionName("Overview");
		setSectionTitle("Overview of Commands");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeCommandSummaryView(Command command) {
		//String viewTitle = getCommandLabel(command);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("command");
		setSectionName("Summary");
		setSectionTitle("Summary of Command Records");
		setSectionIcon("/icons/nam/Command16.gif");
		String viewLevelKey = "commandSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Commands", "showCommandManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getCommandLabel(Command command) {
		String label = "Command";
		String name = CommandUtil.getLabel(command);
		if (name == null && command.getName() != null)
			name = CommandUtil.getLabel(command);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Command> page = commandWizard.getPage();
		if (page != null)
			setSectionTitle("Command " + page.getName());
	}
	
	protected void updateState(Command command) {
		String commandName = CommandUtil.getLabel(command);
		setSectionTitle(commandName + " Command");
	}
	
}
