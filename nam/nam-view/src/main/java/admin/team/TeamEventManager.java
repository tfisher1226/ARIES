package admin.team;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Team;

import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("teamEventManager")
public class TeamEventManager extends AbstractEventManager<Team> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Team getInstance() {
		return selectionContext.getSelection("team");
	}
	
	public void removeTeam() {
		Team team = getInstance();
		fireRemoveEvent(team);
	}
	
}
