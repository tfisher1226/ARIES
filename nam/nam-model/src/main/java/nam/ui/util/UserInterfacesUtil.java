package nam.ui.util;

import java.util.Collection;
import java.util.Iterator;

import nam.ui.UserInterfaces;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.Validator;


public class UserInterfacesUtil extends BaseUtil {
	
	public static boolean isEmpty(UserInterfaces userInterfaces) {
		if (userInterfaces == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<UserInterfaces> userInterfacesList) {
		if (userInterfacesList == null  || userInterfacesList.size() == 0)
			return true;
		Iterator<UserInterfaces> iterator = userInterfacesList.iterator();
		while (iterator.hasNext()) {
			UserInterfaces userInterfaces = iterator.next();
			if (!isEmpty(userInterfaces))
				return false;
		}
		return true;
	}
	
	public static String toString(UserInterfaces userInterfaces) {
		if (isEmpty(userInterfaces))
			return "UserInterfaces: [uninitialized] "+userInterfaces.toString();
		String text = userInterfaces.toString();
		return text;
	}
	
	public static String toString(Collection<UserInterfaces> userInterfacesList) {
		if (isEmpty(userInterfacesList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<UserInterfaces> iterator = userInterfacesList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			UserInterfaces userInterfaces = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(userInterfaces);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static UserInterfaces create() {
		UserInterfaces userInterfaces = new UserInterfaces();
		initialize(userInterfaces);
		return userInterfaces;
	}
	
	public static void initialize(UserInterfaces userInterfaces) {
		//nothing for now
	}
	
	public static boolean validate(UserInterfaces userInterfaces) {
		if (userInterfaces == null)
			return false;
		Validator validator = Validator.getValidator();
		UserInterfaceUtil.validate(userInterfaces.getUserInterfaces());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<UserInterfaces> userInterfacesList) {
		Validator validator = Validator.getValidator();
		Iterator<UserInterfaces> iterator = userInterfacesList.iterator();
		while (iterator.hasNext()) {
			UserInterfaces userInterfaces = iterator.next();
			//TODO break or accumulate?
			validate(userInterfaces);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static UserInterfaces clone(UserInterfaces userInterfaces) {
		if (userInterfaces == null)
			return null;
		UserInterfaces clone = create();
		clone.setUserInterfaces(UserInterfaceUtil.clone(userInterfaces.getUserInterfaces()));
		return clone;
	}
	
}
