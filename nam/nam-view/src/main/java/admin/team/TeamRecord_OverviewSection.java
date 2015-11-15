package admin.team;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import admin.Team;
import admin.util.TeamUtil;


@SessionScoped
@Named("teamOverviewSection")
public class TeamRecord_OverviewSection extends AbstractWizardPage<Team> implements Serializable {
	
	private Team team;
	
	
	public TeamRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	@Override
	public void initialize(Team team) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setTeam(team);
	}
	
	@Override
	public void validate() {
		if (team == null) {
			validator.missing("Team");
		} else {
		}
	}
	
}
