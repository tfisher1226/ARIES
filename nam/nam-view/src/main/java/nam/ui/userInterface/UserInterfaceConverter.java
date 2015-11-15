package nam.ui.userInterface;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.ui.UserInterface;
import nam.ui.util.UserInterfaceUtil;


@FacesConverter(value = "userInterfaceConverter", forClass = UserInterface.class)
public class UserInterfaceConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		UserInterfaceListManager userInterfaceListManager = BeanContext.getFromSession("userInterfaceListManager");
		UserInterface userInterface = userInterfaceListManager.getRecordByName(value);
		return userInterface;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		UserInterface userInterface = (UserInterface) value;
		return UserInterfaceUtil.toString(userInterface);
	}
	
}
