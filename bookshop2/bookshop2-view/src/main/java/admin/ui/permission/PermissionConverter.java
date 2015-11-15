package admin.ui.permission;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import admin.Permission;
import admin.util.PermissionUtil;


@FacesConverter(value="permissionConverter", forClass=Permission.class)
public class PermissionConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		PermissionListManager permissionListManager = BeanContext.getFromSession("permissionListManager");
		Permission permission = permissionListManager.getRecordByName(value);
		return permission;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Permission permission = (Permission) value;
		return PermissionUtil.toString(permission);
	}
	
}
