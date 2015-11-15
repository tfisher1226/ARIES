package admin.team;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import admin.Team;
import admin.util.TeamUtil;


@SessionScoped
@Named("teamSelectManager")
public class TeamSelectManager extends AbstractSelectManager<Team, TeamListObject> implements Serializable {
	
	@Inject
	private TeamDataManager teamDataManager;
	
	@Inject
	private TeamHelper teamHelper;
	
	
	@Override
	public String getClientId() {
		return "teamSelect";
	}
	
	@Override
	public String getTitle() {
		return "Team Selection";
	}
	
	@Override
	protected Class<Team> getRecordClass() {
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
	
	protected TeamHelper getTeamHelper() {
		return BeanContext.getFromSession("teamHelper");
	}
	
	protected TeamListManager getTeamListManager() {
		return BeanContext.getFromSession("teamListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshTeamList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Team> recordList) {
		TeamListManager teamListManager = getTeamListManager();
		DataModel<TeamListObject> dataModel = teamListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshTeamList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Team> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Team> teamList = BeanContext.getFromConversation(instanceId);
		return teamList;
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Team> teamList) {
		Collections.sort(teamList, new Comparator<Team>() {
			public int compare(Team team1, Team team2) {
				String text1 = TeamUtil.toString(team1);
				String text2 = TeamUtil.toString(team2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
