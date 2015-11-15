package nam.model.command;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Command;
import nam.model.util.CommandUtil;


@FacesConverter(value = "commandConverter", forClass = Command.class)
public class CommandConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		CommandListManager commandListManager = BeanContext.getFromSession("commandListManager");
		Command command = commandListManager.getRecordByName(value);
		return command;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Command command = (Command) value;
		return CommandUtil.toString(command);
	}
	
}
