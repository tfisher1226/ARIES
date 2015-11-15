package admin.user;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import admin.User;
import admin.util.UserUtil;


@FacesConverter(value = "userConverter", forClass = User.class)
public class UserConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		UserListManager userListManager = BeanContext.getFromSession("userListManager");
		User user = userListManager.getRecordByName(value);
		return user;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		User user = (User) value;
		return UserUtil.toString(user);
	}
	
}
