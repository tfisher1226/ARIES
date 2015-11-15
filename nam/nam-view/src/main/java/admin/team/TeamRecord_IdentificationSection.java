package admin.team;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import admin.Team;
import admin.util.TeamUtil;


@SessionScoped
@Named("teamIdentificationSection")
public class TeamRecord_IdentificationSection extends AbstractWizardPage<Team> implements Serializable {
	
	private Team team;
	
	
	public TeamRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setPopulateVisible(true);
		setPopulateEnabled(true);
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
