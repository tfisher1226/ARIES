package admin.ui.role;

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

import admin.Role;
import admin.util.RoleUtil;


@Converter
@AutoCreate
@BypassInterceptors
@Name("roleConverter")
@Scope(ScopeType.APPLICATION)
public class RoleConverter extends AbstractConverter implements javax.faces.convert.Converter, Serializable {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		RoleListManager roleListManager = BeanContext.getFromSession("mainRoleListManager");
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
