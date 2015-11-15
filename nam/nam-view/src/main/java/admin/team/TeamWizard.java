package admin.team;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import admin.Team;


@SessionScoped
@Named("teamWizard")
@SuppressWarnings("serial")
public class TeamWizard extends AbstractDomainElementWizard<Team> implements Serializable {
	
	@Inject
	private TeamDataManager teamDataManager;
	
	@Inject
	private TeamPageManager teamPageManager;
	
	@Inject
	private TeamEventManager teamEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Team";
	}
	
	@Override
	public String getUrlContext() {
		return teamPageManager.getTeamWizardPage();
	}
	
	@Override
	public void initialize(Team team) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(teamPageManager.getSections());
		super.initialize(team);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		teamPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		teamPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		teamPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		teamPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Team team = getInstance();
		teamDataManager.saveTeam(team);
		teamEventManager.fireSavedEvent(team);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Team team = getInstance();
		//TODO take this out soon
		if (team == null)
			team = new Team();
		teamEventManager.fireCancelledEvent(team);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Team team = selectionContext.getSelection("team");
		String name = team.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("teamWizard");
			display.error("Team name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
