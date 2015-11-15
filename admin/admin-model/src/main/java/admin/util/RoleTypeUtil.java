package admin.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;

import admin.RoleType;


public class RoleTypeUtil extends BaseUtil {
	
	public static final RoleType[] VALUES_ARRAY = new RoleType[] {
		RoleType.MANAGER,
		RoleType.USER,
		RoleType.HOST
	};
	
	public static final List<RoleType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static RoleType getRoleType(int ordinal) {
		if (ordinal == RoleType.MANAGER.ordinal())
			return RoleType.MANAGER;
		if (ordinal == RoleType.USER.ordinal())
			return RoleType.USER;
		if (ordinal == RoleType.HOST.ordinal())
			return RoleType.HOST;
		return null;
	}
	
	public static String toString(RoleType roleType) {
		return roleType.name();
	}
	
	public static String toString(Collection<RoleType> roleTypeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<RoleType> iterator = roleTypeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			RoleType roleType = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(roleType);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<RoleType> roleTypeList) {
		Collections.sort(roleTypeList, createRoleTypeComparator());
	}
	
	public static Collection<RoleType> sortRecords(Collection<RoleType> roleTypeCollection) {
		List<RoleType> list = new ArrayList<RoleType>(roleTypeCollection);
		Collections.sort(list, createRoleTypeComparator());
		return list;
	}
	
	public static Comparator<RoleType> createRoleTypeComparator() {
		return new Comparator<RoleType>() {
			public int compare(RoleType roleType1, RoleType roleType2) {
				String text1 = roleType1.value();
				String text2 = roleType2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
