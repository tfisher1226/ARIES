package admin.team;

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

import admin.Team;
import admin.util.TeamUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("teamPageManager")
public class TeamPageManager extends AbstractPageManager<Team> implements Serializable {
	
	@Inject
	private TeamWizard teamWizard;
	
	@Inject
	private TeamDataManager teamDataManager;
	
	@Inject
	private TeamListManager teamListManager;
	
	@Inject
	private TeamRecord_OverviewSection teamOverviewSection;
	
	@Inject
	private TeamRecord_IdentificationSection teamIdentificationSection;
	
	@Inject
	private TeamRecord_ConfigurationSection teamConfigurationSection;
	
	@Inject
	private TeamRecord_DocumentationSection teamDocumentationSection;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TeamPageManager() {
		initializeSections();
		initializeDefaultView();
	}
	
	
	public void refresh() {
		refresh("team");
	}
	
	public void refreshLocal() {
		refreshLocal("team");
	}
	
	public void refreshMembers() {
		refreshMembers("team");
	}
	
	public void refresh(String scope) {
		refreshLocal(scope);
		refreshMembers(scope);
	}
	
	public void refreshLocal(String scope) {
		teamDataManager.setScope(scope);
		teamListManager.refresh();
	}
	
	public void refreshMembers(String scope) {
		teamListManager.refresh();
	}
	
	public String getTeamListPage() {
		return "/admin/team/teamListPage.xhtml";
	}
	
	public String getTeamTreePage() {
		return "/admin/team/teamTreePage.xhtml";
	}
	
	public String getTeamSummaryPage() {
		return "/admin/team/teamSummaryPage.xhtml";
	}
	
	public String getTeamRecordPage() {
		return "/admin/team/teamRecordPage.xhtml";
	}
	
	public String getTeamWizardPage() {
		return "/admin/team/teamWizardPage.xhtml";
	}
	
	public String getTeamManagementPage() {
		return "/admin/team/teamManagementPage.xhtml";
	}
	
	public String initializeTeamListPage() {
		String pageLevelKey = "teamList";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Teams", "showTeamManagementPage()");
		String url = getTeamListPage();
		selectionContext.setCurrentArea("team");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTeamTreePage() {
		String pageLevelKey = "teamTree";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Teams", "showTeamTreePage()");
		String url = getTeamTreePage();
		selectionContext.setCurrentArea("team");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTeamSummaryPage(Team team) {
		String pageLevelKey = "teamSummary";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Teams", "showTeamSummaryPage()");
		String url = getTeamSummaryPage();
		selectionContext.setCurrentArea("team");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		sections.clear();
		return url;
	}
	
	public String initializeTeamRecordPage() {
		Team team = selectionContext.getSelection("team");
		String teamName = TeamUtil.getLabel(team);
		
		String pageLevelKey = "teamRecord";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Teams", "showTeamManagementPage()");
		addBreadcrumb(pageLevelKey, teamName, "showTeamRecordPage()");
		String url = getTeamRecordPage();
		selectionContext.setCurrentArea("team");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		initializeDefaultView();
		sections.clear();
		return url;
	}
	
	public String initializeTeamCreationPage(Team team) {
		setPageTitle("New "+getTeamLabel(team));
		setPageIcon("/icons/nam/NewTeam16.gif");
		setSectionTitle("Team Identification");
		teamWizard.setNewMode(true);
		
		String pageLevelKey = "team";
		String wizardLevelKey = "teamWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Teams", "showTeamManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb("New Team", "showTeamWizardPage()"));
		
		
		teamIdentificationSection.setOwner("teamWizard");
		teamConfigurationSection.setOwner("teamWizard");
		teamDocumentationSection.setOwner("teamWizard");
		
		sections.clear();
		sections.add(teamIdentificationSection);
		sections.add(teamConfigurationSection);
		sections.add(teamDocumentationSection);
		
		String url = getTeamWizardPage() + "?section=Identification";
		selectionContext.setCurrentArea("team");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeTeamUpdatePage(Team team) {
		setPageTitle(getTeamLabel(team));
		setPageIcon("/icons/nam/Team16.gif");
		setSectionTitle("Team Overview");
		String teamName = TeamUtil.getLabel(team);
		teamWizard.setNewMode(false);
		
		String pageLevelKey = "team";
		String wizardLevelKey = "teamWizard";
		clearBreadcrumbs(pageLevelKey);
		clearBreadcrumbs(wizardLevelKey);
		
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Teams", "showTeamManagementPage()");
		addBreadcrumb(pageLevelKey, new Breadcrumb(teamName, "showTeamWizardPage()"));
		
		
		teamOverviewSection.setOwner("teamWizard");
		teamIdentificationSection.setOwner("teamWizard");
		teamConfigurationSection.setOwner("teamWizard");
		teamDocumentationSection.setOwner("teamWizard");
		
		sections.clear();
		sections.add(teamOverviewSection);
		sections.add(teamIdentificationSection);
		sections.add(teamConfigurationSection);
		sections.add(teamDocumentationSection);
		
		String url = getTeamWizardPage() + "?section=Overview";
		selectionContext.setCurrentArea("team");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		//selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh();
		return url;
	}
	
	public String initializeTeamManagementPage() {
		setPageTitle("Teams");
		setPageIcon("/icons/nam/Team16.gif");
		String pageLevelKey = "teamManagement";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Teams", "showTeamManagementPage()");
		String url = getTeamManagementPage();
		selectionContext.setCurrentArea("team");
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
		setSectionType("team");
		setSectionName("Overview");
		setSectionTitle("Overview of Teams");
		setSectionIcon("/icons/nam/Overview16.gif");
	}
	
	public String initializeTeamSummaryView(Team team) {
		//String viewTitle = getTeamLabel(team);
		//String currentArea = selectionContext.getCurrentArea();
		setSectionType("team");
		setSectionName("Summary");
		setSectionTitle("Summary of Team Records");
		setSectionIcon("/icons/nam/Team16.gif");
		String viewLevelKey = "teamSummary";
		clearBreadcrumbs(viewLevelKey);
		addBreadcrumb(viewLevelKey, "Top", "showMainPage()");
		addBreadcrumb(viewLevelKey, "Teams", "showTeamManagementPage()");
		selectionContext.setMessageDomain(viewLevelKey);
		sections.clear();
		return null;
	}
	
	protected String getTeamLabel(Team team) {
		String label = "Team";
		String name = TeamUtil.getLabel(team);
		if (name == null && team.getName() != null)
			name = TeamUtil.getLabel(team);
		if (name != null && !name.isEmpty())
			label = name + " " + label;
		return label;
	}
	
	protected void updateState() {
		AbstractWizardPage<Team> page = teamWizard.getPage();
		if (page != null)
			setSectionTitle("Team " + page.getName());
	}
	
	protected void updateState(Team team) {
		String teamName = TeamUtil.getLabel(team);
		setSectionTitle(teamName + " Team");
	}
	
}
