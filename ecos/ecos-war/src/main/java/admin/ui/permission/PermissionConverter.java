package admin.ui.permission;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Permission;
import admin.util.PermissionUtil;


@Converter
@AutoCreate
@BypassInterceptors
@Name("permissionConverter")
@Scope(ScopeType.APPLICATION)
public class PermissionConverter extends AbstractConverter implements javax.faces.convert.Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		PermissionListManager permissionListManager = BeanContext.getFromSession("mainPermissionListManager");
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
