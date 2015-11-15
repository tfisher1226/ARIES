package template1_package.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import template1.model.Organization;
import template1.model.util.OrganizationUtil;


@AutoCreate
@Name("helper")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class Helper implements Serializable {
	
	public static String toTimeString(Date dateTime) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		return dateTime != null ? dateFormat.format(dateTime) : "";
	}
	
//	public String toTimeString(DateTime dateTime) {
//		return dateTime != null ? dateTime.toString("yyyy-MM-dd [HH:mm:ss]") : "";
//	}
	
	public static String toSimpleDate(Date dateTime) {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
		return dateTime != null ? formatter.format(dateTime) : "";
	}
	
	public static String toDateString(Date dateTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		return dateTime != null ? formatter.format(dateTime) : "";
	}

//	public static String toDateString(DateTime dateTime) {
//		return dateTime != null ? dateTime.toString("yyyy-MM-dd") : "";
//	}

	public static String toActiveString(Boolean active) {
		return active ? "Active" : "Inactive";
	}
	
	public static Boolean toActiveInactiveBoolean(String active) {
		return active.equals("Inactive") ? Boolean.FALSE : Boolean.TRUE;
	}

	


	public static String toOrganizationName(Organization organization) {
		if (organization != null)
			return OrganizationUtil.getLabel(organization);
		return null;
	}
	
//	public String getActivityGroupsText() {
//		String text = "";
//		if (member != null) {
//			List<ActivityGroup> activityGroups = member.getActivityGroups();
//			if (activityGroups != null) {
//				Iterator<ActivityGroup> iterator = activityGroups.iterator();
//				while (iterator.hasNext()) {
//					ActivityGroup activityGroup = iterator.next();
//					text += activityGroup.getLabel();
//					text += "\n";
//				}
//			}
//		}
//		return text;
//	}
//	
//	public String getLeadershipRolesText() {
//		String text = "";
//		if (member != null) {
//			LeadershipInfo leadershipInfo = member.getLeadershipInfo();
//			if (leadershipInfo != null) {
//				List<LeadershipRole> leadershipRoles = leadershipInfo.getLeadershipRoles();
//				Iterator<LeadershipRole> iterator = leadershipRoles.iterator();
//				while (iterator.hasNext()) {
//					LeadershipRole leadershipRole = iterator.next();
//					text += LeadershipRoleUtil.toString(leadershipRole);
//					text += "\n";
//				}
//			}
//		}
//		return text;
//	}

}
