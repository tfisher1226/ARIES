package admin.ui.role;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import admin.Role;
import admin.util.RoleUtil;


@FacesConverter(value="roleConverter", forClass=Role.class)
public class RoleConverter extends AbstractConverter implements Converter, Serializable {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		RoleListManager roleListManager = BeanContext.getFromSession("roleListManager");
		Role role = roleListManager.getRecordByName(value);
		return role;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Role role = (Role) value;
		return RoleUtil.toString(role);
	}

}
