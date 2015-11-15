package nam.ui.userInterfaceType;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.ui.UserInterfaceType;


@FacesConverter(value = "userInterfaceTypeConverter", forClass = UserInterfaceType.class)
public class UserInterfaceTypeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		UserInterfaceType userInterfaceType = UserInterfaceType.valueOf(value.toUpperCase());
		return userInterfaceType;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		UserInterfaceType userInterfaceType = null;
		if (value instanceof String)
			userInterfaceType = UserInterfaceType.valueOf((String) value);
		else if (value instanceof UserInterfaceType)
			userInterfaceType = (UserInterfaceType) value;
		return userInterfaceType.value();
	}
	
}
