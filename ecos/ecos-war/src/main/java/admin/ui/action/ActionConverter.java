package admin.ui.action;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Action;


@Converter
@AutoCreate
@BypassInterceptors
@Name("actionConverter")
@Scope(ScopeType.APPLICATION)
public class ActionConverter extends AbstractConverter implements javax.faces.convert.Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		Action action = Action.fromValue(value);
		return action;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Action action = (Action) value;
		return action.value();
	}

}
