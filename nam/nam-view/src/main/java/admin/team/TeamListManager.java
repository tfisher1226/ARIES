package admin.team;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import admin.Team;
import admin.util.TeamUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("teamListManager")
public class TeamListManager extends AbstractDomainListManager<Team, TeamListObject> implements Serializable {
	
	@Inject
	private TeamDataManager teamDataManager;
	
	@Inject
	private TeamEventManager teamEventManager;
	
	@Inject
	private TeamInfoManager teamInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "teamList";
	}
	
	@Override
	public String getTitle() {
		return "Team List";
	}
	
	public Object getRecordId(Team team) {
		return team.getId();
	}
	
	@Override
	public Object getRecordKey(Team team) {
		return TeamUtil.getKey(team);
	}
	
	@Override
	public String getRecordName(Team team) {
		return TeamUtil.getLabel(team);
	}
	
	@Override
	protected Class<Team> getRecordClass() {
		return Team.class;
	}
	
	@Override
	protected Team getRecord(TeamListObject rowObject) {
		return rowObject.getTeam();
	}
	
	@Override
	public Team getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? TeamUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Team team) {
		super.setSelectedRecord(team);
		fireSelectedEvent(team);
	}
	
	protected void fireSelectedEvent(Team team) {
		teamEventManager.fireSelectedEvent(team);
	}
	
	public boolean isSelected(Team team) {
		Team selection = selectionContext.getSelection("team");
		boolean selected = selection != null && selection.equals(team);
		return selected;
	}
	
	public boolean isChecked(Team team) {
		Collection<Team> selection = selectionContext.getSelection("teamSelection");
		boolean checked = selection != null && selection.contains(team);
		return checked;
	}
	
	@Override
	protected TeamListObject createRowObject(Team team) {
		TeamListObject listObject = new TeamListObject(team);
		listObject.setSelected(isSelected(team));
		listObject.setChecked(isChecked(team));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Team> createRecordList() {
		try {
			Collection<Team> teamList = teamDataManager.getTeamList();
			if (teamList != null)
				return teamList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewTeam() {
		return viewTeam(selectedRecordKey);
	}
	
	public String viewTeam(Object recordKey) {
		Team team = recordByKeyMap.get(recordKey);
		return viewTeam(team);
	}
	
	public String viewTeam(Team team) {
		String url = teamInfoManager.viewTeam(team);
		return url;
	}
	
	public String editTeam() {
		return editTeam(selectedRecordKey);
	}
	
	public String editTeam(Object recordKey) {
		Team team = recordByKeyMap.get(recordKey);
		return editTeam(team);
	}
	
	public String editTeam(Team team) {
		String url = teamInfoManager.editTeam(team);
		return url;
	}
	
	public void removeTeam() {
		removeTeam(selectedRecordKey);
	}
	
	public void removeTeam(Object recordKey) {
		Team team = recordByKeyMap.get(recordKey);
		removeTeam(team);
	}
	
	public void removeTeam(Team team) {
		try {
			if (teamDataManager.removeTeam(team))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelTeam(@Observes @Cancelled Team team) {
		try {
			//Object key = TeamUtil.getKey(team);
			//recordByKeyMap.put(key, team);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("team");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateTeam(Collection<Team> teamList) {
		return TeamUtil.validate(teamList);
	}
	
	public void exportTeamList(@Observes @Export String tableId) {
		//String tableId = "pageForm:teamListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
