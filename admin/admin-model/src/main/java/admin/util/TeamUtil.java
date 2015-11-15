package admin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import admin.Team;


public class TeamUtil extends BaseUtil {
	
	public static Object getKey(Team team) {
		return team.getName();
	}
	
	public static String getLabel(Team team) {
		return team.getName();
	}
	
	public static boolean isEmpty(Team team) {
		if (team == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(team.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Team> teamList) {
		if (teamList == null  || teamList.size() == 0)
			return true;
		Iterator<Team> iterator = teamList.iterator();
		while (iterator.hasNext()) {
			Team team = iterator.next();
			if (!isEmpty(team))
				return false;
		}
		return true;
	}
	
	public static String toString(Team team) {
		if (isEmpty(team))
			return "Team: [uninitialized] "+team.toString();
		String text = team.toString();
		return text;
	}
	
	public static String toString(Collection<Team> teamList) {
		if (isEmpty(teamList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Team> iterator = teamList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Team team = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(team);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Team create() {
		Team team = new Team();
		initialize(team);
		return team;
	}
	
	public static void initialize(Team team) {
		//nothing for now
	}
	
	public static boolean validate(Team team) {
		if (team == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(team.getName(), "\"Name\" must be specified");
		UserUtil.validate(team.getMembers());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Team> teamList) {
		Validator validator = Validator.getValidator();
		Iterator<Team> iterator = teamList.iterator();
		while (iterator.hasNext()) {
			Team team = iterator.next();
			//TODO break or accumulate?
			validate(team);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Team> teamList) {
		Collections.sort(teamList, createTeamComparator());
	}
	
	public static Collection<Team> sortRecords(Collection<Team> teamCollection) {
		List<Team> list = new ArrayList<Team>(teamCollection);
		Collections.sort(list, createTeamComparator());
		return list;
	}
	
	public static Comparator<Team> createTeamComparator() {
		return new Comparator<Team>() {
			public int compare(Team team1, Team team2) {
				Object key1 = getKey(team1);
				Object key2 = getKey(team2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Team clone(Team team) {
		if (team == null)
			return null;
		Team clone = create();
		clone.setId(ObjectUtil.clone(team.getId()));
		clone.setName(ObjectUtil.clone(team.getName()));
		clone.setOrganization(ObjectUtil.clone(team.getOrganization()));
		clone.setDescription(ObjectUtil.clone(team.getDescription()));
		clone.setMembers(UserUtil.clone(team.getMembers()));
		clone.setCreationDate(ObjectUtil.clone(team.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(team.getLastUpdate()));
		return clone;
	}
	
}
