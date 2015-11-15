package admin.team;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;

import admin.Team;
import admin.util.TeamUtil;


@SessionScoped
@Named("teamInfoManager")
public class TeamInfoManager extends AbstractNamRecordManager<Team> implements Serializable {
	
	@Inject
	private TeamWizard teamWizard;
	
	@Inject
	private TeamDataManager teamDataManager;
	
	@Inject
	private TeamPageManager teamPageManager;
	
	@Inject
	private TeamEventManager teamEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private TeamHelper teamHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TeamInfoManager() {
		setInstanceName("team");
	}
	
	
	public Team getTeam() {
		return getRecord();
	}
	
	public Team getSelectedTeam() {
		return selectionContext.getSelection("team");
	}
	
	@Override
	public Class<Team> getRecordClass() {
		return Team.class;
	}
	
	@Override
	public boolean isEmpty(Team team) {
		return teamHelper.isEmpty(team);
	}
	
	@Override
	public String toString(Team team) {
		return teamHelper.toString(team);
	}
	
	@Override
	public void initialize() {
		Team team = selectionContext.getSelection("team");
		if (team != null)
			initialize(team);
	}
	
	protected void initialize(Team team) {
		TeamUtil.initialize(team);
		teamWizard.initialize(team);
		setContext("team", team);
	}
	
	public void handleTeamSelected(@Observes @Selected Team team) {
		selectionContext.setSelection("team",  team);
		teamPageManager.updateState(team);
		teamPageManager.refreshMembers();
		setRecord(team);
	}
	
	@Override
	public String newRecord() {
		return newTeam();
	}
	
	public String newTeam() {
		try {
			Team team = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("team",  team);
			String url = teamPageManager.initializeTeamCreationPage(team);
			teamPageManager.pushContext(teamWizard);
			initialize(team);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Team create() {
		Team team = TeamUtil.create();
		return team;
	}
	
	@Override
	public Team clone(Team team) {
		team = TeamUtil.clone(team);
		return team;
	}
	
	@Override
	public String viewRecord() {
		return viewTeam();
	}
	
	public String viewTeam() {
		Team team = selectionContext.getSelection("team");
		String url = viewTeam(team);
		return url;
	}
	
	public String viewTeam(Team team) {
		try {
			String url = teamPageManager.initializeTeamSummaryView(team);
			teamPageManager.pushContext(teamWizard);
			initialize(team);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editTeam();
	}
	
	public String editTeam() {
		Team team = selectionContext.getSelection("team");
		String url = editTeam(team);
		return url;
	}
	
	public String editTeam(Team team) {
		try {
			//team = clone(team);
			selectionContext.resetOrigin();
			selectionContext.setSelection("team",  team);
			String url = teamPageManager.initializeTeamUpdatePage(team);
			teamPageManager.pushContext(teamWizard);
			initialize(team);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveTeam() {
		Team team = getTeam();
		if (validateTeam(team)) {
			saveTeam(team);
		}
	}
	
	public void persistTeam(Team team) {
		Long id = team.getId();
		if (id != null) {
			saveTeam(team);
		}
	}
	
	public void saveTeam(Team team) {
		try {
			saveTeamToSystem(team);
			teamEventManager.fireAddedEvent(team);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveTeamToSystem(Team team) {
		teamDataManager.saveTeam(team);
	}
	
	public void handleSaveTeam(@Observes @Add Team team) {
		saveTeam(team);
	}
	
	public void enrichTeam(Team team) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Team team) {
		return validateTeam(team);
	}
	
	public boolean validateTeam(Team team) {
		Validator validator = getValidator();
		boolean isValid = TeamUtil.validate(team);
		Display display = getFromSession("display");
		display.setModule("teamInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveTeam() {
		display = getFromSession("display");
		display.setModule("teamInfo");
		Team team = selectionContext.getSelection("team");
		if (team == null) {
			display.error("Team record must be selected.");
		}
	}
	
	public String handleRemoveTeam(@Observes @Remove Team team) {
		display = getFromSession("display");
		display.setModule("teamInfo");
		try {
			display.info("Removing Team "+TeamUtil.getLabel(team)+" from the system.");
			removeTeamFromSystem(team);
			selectionContext.clearSelection("team");
			teamEventManager.fireClearSelectionEvent();
			teamEventManager.fireRemovedEvent(team);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeTeamFromSystem(Team team) {
		if (teamDataManager.removeTeam(team))
			setRecord(null);
	}
	
	public void cancelTeam() {
		BeanContext.removeFromSession("team");
		teamPageManager.removeContext(teamWizard);
	}
	
}
