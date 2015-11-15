package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Role;
import nam.model.RoleType;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class RoleUtil extends BaseUtil {
	
	public static boolean isEmpty(Role role) {
		if (role == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(role.getName());
		status |= role.getRoleType() != RoleType.USER;
		status |= PermissionUtil.isEmpty(role.getPermissions());
		return status;
	}
	
	public static boolean isEmpty(Collection<Role> roleList) {
		if (roleList == null  || roleList.size() == 0)
			return true;
		Iterator<Role> iterator = roleList.iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			if (!isEmpty(role))
				return false;
		}
		return true;
	}
	
	public static String toString(Role role) {
		if (isEmpty(role))
			return "";
		String text = "";
		text += role.getName();
		return text;
	}
	
	public static String toString(Collection<Role> roleList) {
		if (isEmpty(roleList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Role> iterator = roleList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Role role = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(role);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Role create() {
		Role role = new Role();
		initialize(role);
		return role;
	}
	
	public static void initialize(Role role) {
		if (role.getConditional() == null)
			role.setConditional(true);
		if (role.getEnabled() == null)
			role.setEnabled(true);
		if (role.getRoleType() == null)
			role.setRoleType(RoleType.USER);
	}
	
	public static boolean validate(Role role) {
		if (role == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(role.getName(), "\"Name\" must be specified");
		validator.isFalse(PermissionUtil.isEmpty(role.getPermissions()), "At least one of \"Permissions\" must be specified");
		validator.notNull(role.getRoleType(), "\"RoleType\" must be specified");
		RoleUtil.validate(role.getGroups());
		PermissionUtil.validate(role.getPermissions());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Role> roleList) {
		Validator validator = Validator.getValidator();
		Iterator<Role> iterator = roleList.iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			//TODO break or accumulate?
			validate(role);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Role> roleList) {
		Collections.sort(roleList, createRoleComparator());
	}
	
	public static Collection<Role> sortRecords(Collection<Role> roleCollection) {
		List<Role> list = new ArrayList<Role>(roleCollection);
		Collections.sort(list, createRoleComparator());
		return list;
	}
	
	public static void sortRecordsByName(List<Role> roleList) {
		Collections.sort(roleList, new Comparator<Role>() {
			public int compare(Role role1, Role role2) {
				String text1 = role1.getName();
				String text2 = role2.getName();
				return text1.compareTo(text2);
			}
		});
	}
	
	public static void sortRecordsByRoleType(List<Role> roleList) {
		Collections.sort(roleList, new Comparator<Role>() {
			public int compare(Role role1, Role role2) {
				String text1 = role1.getRoleType().name();
				String text2 = role2.getRoleType().name();
				return text1.compareTo(text2);
			}
		});
	}
	
	public static Comparator<Role> createRoleComparator() {
		return new Comparator<Role>() {
			public int compare(Role role1, Role role2) {
				int status = role1.getName().compareTo(role2.getName());
				return status;
			}
		};
	}
	
	public static Role clone(Role role) {
		if (role == null)
			return null;
		Role clone = create();
		clone.setId(ObjectUtil.clone(role.getId()));
		clone.setName(ObjectUtil.clone(role.getName()));
		clone.setRoleType(role.getRoleType());
		clone.getGroups().addAll(RoleUtil.clone(role.getGroups()));
		clone.getPermissions().addAll(PermissionUtil.clone(role.getPermissions()));
		clone.setConditional(ObjectUtil.clone(role.getConditional()));
		clone.setEnabled(ObjectUtil.clone(role.getEnabled()));
		return clone;
	}
	
	public static List<Role> clone(List<Role> roleSet) {
		if (roleSet == null)
			return null;
		List<Role> newSet = new ArrayList<Role>();
		Iterator<Role> iterator = roleSet.iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			Role clone = clone(role);
			newSet.add(clone);
		}
		return newSet;
	}
	
}
