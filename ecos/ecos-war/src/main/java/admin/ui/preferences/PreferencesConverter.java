package admin.ui.preferences;

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

import admin.Preferences;
import admin.util.PreferencesUtil;


@Converter
@AutoCreate
@BypassInterceptors
@Name("preferencesConverter")
@Scope(ScopeType.APPLICATION)
public class PreferencesConverter extends AbstractConverter implements javax.faces.convert.Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		PreferencesListManager preferencesListManager = BeanContext.getFromSession("mainPreferencesListManager");
		Preferences preferences = preferencesListManager.getRecordByName(value);
		return preferences;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Preferences preferences = (Preferences) value;
		return PreferencesUtil.toString(preferences);
	}
	
}
