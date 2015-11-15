package nam.model.commandName;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.CommandName;


@FacesConverter(value = "commandNameConverter", forClass = CommandName.class)
public class CommandNameConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		CommandName commandName = CommandName.valueOf(value.toUpperCase());
		return commandName;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		CommandName commandName = null;
		if (value instanceof String)
			commandName = CommandName.valueOf((String) value);
		else if (value instanceof CommandName)
			commandName = (CommandName) value;
		return commandName.value();
	}
	
}
