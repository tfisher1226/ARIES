package admin.team;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import admin.Team;
import admin.User;
import admin.user.UserListManager;
import admin.user.UserListObject;
import admin.util.TeamUtil;


@SessionScoped
@Named("teamHelper")
public class TeamHelper extends AbstractElementHelper<Team> implements Serializable {
	
	@Override
	public boolean isEmpty(Team team) {
		return TeamUtil.isEmpty(team);
	}
	
	@Override
	public String toString(Team team) {
		return TeamUtil.toString(team);
	}
	
	@Override
	public String toString(Collection<Team> teamList) {
		return TeamUtil.toString(teamList);
	}
	
	@Override
	public boolean validate(Team team) {
		return TeamUtil.validate(team);
	}
	
	@Override
	public boolean validate(Collection<Team> teamList) {
		return TeamUtil.validate(teamList);
	}
	
	public DataModel<UserListObject> getMembers(Team team) {
		if (team == null)
			return null;
		return getMembers(team.getMembers());
	}
	
	public DataModel<UserListObject> getMembers(Collection<User> membersList) {
		UserListManager userListManager = BeanContext.getFromSession("userListManager");
		DataModel<UserListObject> dataModel = userListManager.getDataModel(membersList);
		return dataModel;
	}
	
}
