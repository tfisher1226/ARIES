package admin.team;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Team;
import admin.util.TeamUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("teamDataManager")
public class TeamDataManager implements Serializable {
	
	@Inject
	private TeamEventManager teamEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Team> getTeamList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Team> getDefaultList() {
		return null;
	}
	
	public void saveTeam(Team team) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeTeam(Team team) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
