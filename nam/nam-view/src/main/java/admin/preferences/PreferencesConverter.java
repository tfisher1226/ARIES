package admin.preferences;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import admin.Preferences;
import admin.util.PreferencesUtil;


@FacesConverter(value = "preferencesConverter", forClass = Preferences.class)
public class PreferencesConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		PreferencesListManager preferencesListManager = BeanContext.getFromSession("preferencesListManager");
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
