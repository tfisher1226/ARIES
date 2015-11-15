package admin.team;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import admin.Team;
import admin.util.TeamUtil;


public class TeamListObject extends AbstractListObject<Team> implements Comparable<TeamListObject>, Serializable {
	
	private Team team;
	
	
	public TeamListObject(Team team) {
		this.team = team;
	}
	
	
	public Team getTeam() {
		return team;
	}
	
	@Override
	public Object getKey() {
		return getKey(team);
	}
	
	public Object getKey(Team team) {
		return TeamUtil.getKey(team);
	}
	
	@Override
	public String getLabel() {
		return getLabel(team);
	}
	
	public String getLabel(Team team) {
		return TeamUtil.getLabel(team);
	}
	
	@Override
	public String toString() {
		return toString(team);
	}
	
	@Override
	public String toString(Team team) {
		return TeamUtil.toString(team);
	}
	
	@Override
	public int compareTo(TeamListObject other) {
		Object thisKey = getKey(this.team);
		Object otherKey = getKey(other.team);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		TeamListObject other = (TeamListObject) object;
		Object thisKey = TeamUtil.getKey(this.team);
		Object otherKey = TeamUtil.getKey(other.team);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
