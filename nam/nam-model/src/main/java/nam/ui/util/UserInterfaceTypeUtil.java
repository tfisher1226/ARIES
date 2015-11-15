package nam.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.ui.UserInterfaceType;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;


public class UserInterfaceTypeUtil extends BaseUtil {
	
	public static final UserInterfaceType[] VALUES_ARRAY = new UserInterfaceType[] {
		UserInterfaceType.WEB,
		UserInterfaceType.MOBILE,
		UserInterfaceType.TABLET,
		UserInterfaceType.DESKTOP
	};
	
	public static final List<UserInterfaceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static UserInterfaceType getUserInterfaceType(int ordinal) {
		if (ordinal == UserInterfaceType.WEB.ordinal())
			return UserInterfaceType.WEB;
		if (ordinal == UserInterfaceType.MOBILE.ordinal())
			return UserInterfaceType.MOBILE;
		if (ordinal == UserInterfaceType.TABLET.ordinal())
			return UserInterfaceType.TABLET;
		if (ordinal == UserInterfaceType.DESKTOP.ordinal())
			return UserInterfaceType.DESKTOP;
		return null;
	}
	
	public static String toString(UserInterfaceType userInterfaceType) {
		return userInterfaceType.name();
	}
	
	public static String toString(Collection<UserInterfaceType> userInterfaceTypeList) {
		StringBuffer buf = new StringBuffer();
		Iterator<UserInterfaceType> iterator = userInterfaceTypeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			UserInterfaceType userInterfaceType = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(userInterfaceType);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<UserInterfaceType> userInterfaceTypeList) {
		Collections.sort(userInterfaceTypeList, createUserInterfaceTypeComparator());
	}
	
	public static Collection<UserInterfaceType> sortRecords(Collection<UserInterfaceType> userInterfaceTypeCollection) {
		List<UserInterfaceType> list = new ArrayList<UserInterfaceType>(userInterfaceTypeCollection);
		Collections.sort(list, createUserInterfaceTypeComparator());
		return list;
	}
	
	public static Comparator<UserInterfaceType> createUserInterfaceTypeComparator() {
		return new Comparator<UserInterfaceType>() {
			public int compare(UserInterfaceType userInterfaceType1, UserInterfaceType userInterfaceType2) {
				String text1 = userInterfaceType1.value();
				String text2 = userInterfaceType2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
