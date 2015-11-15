package template1.user;

import java.io.Serializable;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import template1.model.User;


@AutoCreate
@Name("userHelper")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class UserHelper implements Serializable {

	public static boolean nameExists(User user) {
		return user != null && 
			!StringUtils.isEmpty(user.getFirstName()) && 
			!StringUtils.isEmpty(user.getLastName()); 
	}
	
	public static String toNameString(User user) {
		if (user == null)
			return null;
		String name = user.getLastName()+", "+user.getFirstName();
		name = StringEscapeUtils.escapeJavaScript(name);
		return name;
	}
}
