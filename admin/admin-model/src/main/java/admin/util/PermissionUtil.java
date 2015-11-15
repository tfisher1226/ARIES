package admin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import admin.Action;
import admin.Permission;


public class PermissionUtil extends BaseUtil {
	
	public static String getKey(Permission permission) {
		return permission.getOrganization() + "." + permission.getTarget();
	}
	
	public static String getLabel(Permission permission) {
		return permission.getOrganization() + "." + permission.getTarget();
	}
	
	public static boolean isEmpty(Permission permission) {
		if (permission == null)
			return true;
		boolean status = false;
		List<Action> actions = permission.getActions();
		Iterator<Action> iterator = actions.iterator();
		while (iterator.hasNext()) {
			Action action = iterator.next();
			if (action == null)
				return false;
		}
		return status;
	}
	
	public static boolean isEmpty(Collection<Permission> permissionList) {
		if (permissionList == null  || permissionList.size() == 0)
			return true;
		Iterator<Permission> iterator = permissionList.iterator();
		while (iterator.hasNext()) {
			Permission permission = iterator.next();
			if (!isEmpty(permission))
				return false;
		}
		return true;
	}

	//TODO Implement this
	public static String toLabel(Permission permission) {
		StringBuffer buf = new StringBuffer();  
		List<Action> actions = permission.getActions();
		Iterator<Action> iterator = actions.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Action action = iterator.next();
			if (i > 0)
				buf.append(", ");
			buf.append(action.name());
		}
		return buf.toString();
	}
	
	public static String toString(Permission permission) {
		if (isEmpty(permission))
			return "Permission: [uninitialized] "+permission.toString();
		String text = permission.toString();
		return text;
	}
	
	public static String toString(Collection<Permission> permissionList) {
		if (isEmpty(permissionList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Permission> iterator = permissionList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Permission permission = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(permission);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Permission create() {
		Permission permission = new Permission();
		initialize(permission);
		return permission;
	}
	
	public static void initialize(Permission permission) {
		if (permission.getActions().size() == 0)
			permission.getActions().add(Action.ALL);
		if (permission.getEnabled() == null)
			permission.setEnabled(true);
	}
	
	public static boolean isValid(Permission permission) {
		boolean isValid = validate(permission);
		return isValid;
	}
	
	public static boolean validate(Permission permission) {
		if (permission == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(permission.getActions(), "At least one of \"Actions\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean isValid(Collection<Permission> permissionList) {
		boolean isValid = validate(permissionList);
		return isValid;
	}
	
	public static boolean validate(Collection<Permission> permissionList) {
		Validator validator = Validator.getValidator();
		Iterator<Permission> iterator = permissionList.iterator();
		while (iterator.hasNext()) {
			Permission permission = iterator.next();
			//TODO break or accumulate?
			validate(permission);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Permission> permissionList) {
		Collections.sort(permissionList, createPermissionComparator());
	}
	
	public static Collection<Permission> sortRecords(Collection<Permission> permissionCollection) {
		List<Permission> list = new ArrayList<Permission>(permissionCollection);
		Collections.sort(list, createPermissionComparator());
		return list;
	}
	
	public static Comparator<Permission> createPermissionComparator() {
		return new Comparator<Permission>() {
			public int compare(Permission permission1, Permission permission2) {
				Object key1 = getKey(permission1);
				Object key2 = getKey(permission2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Permission clone(Permission permission) {
		if (permission == null)
			return null;
		Permission clone = create();
		clone.setId(ObjectUtil.clone(permission.getId()));
		clone.setTarget(ObjectUtil.clone(permission.getTarget()));
		clone.setOrganization(ObjectUtil.clone(permission.getOrganization()));
		clone.setActions(ObjectUtil.clone(permission.getActions(), Action.class));
		clone.setEnabled(ObjectUtil.clone(permission.getEnabled()));
		return clone;
	}
	
	public static List<Permission> clone(List<Permission> permissionList) {
		if (permissionList == null)
			return null;
		List<Permission> newList = new ArrayList<Permission>();
		Iterator<Permission> iterator = permissionList.iterator();
		while (iterator.hasNext()) {
			Permission permission = iterator.next();
			Permission clone = clone(permission);
			newList.add(clone);
		}
		return newList;
	}
	
}
