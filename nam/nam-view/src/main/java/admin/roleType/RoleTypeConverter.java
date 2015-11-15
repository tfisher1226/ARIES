package admin.roleType;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import admin.RoleType;


@FacesConverter(value = "roleTypeConverter", forClass = RoleType.class)
public class RoleTypeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		RoleType roleType = RoleType.valueOf(value.toUpperCase());
		return roleType;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		RoleType roleType = null;
		if (value instanceof String)
			roleType = RoleType.valueOf((String) value);
		else if (value instanceof RoleType)
			roleType = (RoleType) value;
		return roleType.value();
	}
	
}
