package nam.model.listener;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Listener;
import nam.model.util.ListenerUtil;


@FacesConverter(value = "listenerConverter", forClass = Listener.class)
public class ListenerConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ListenerListManager listenerListManager = BeanContext.getFromSession("listenerListManager");
		Listener listener = listenerListManager.getRecordByName(value);
		return listener;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Listener listener = (Listener) value;
		return ListenerUtil.toString(listener);
	}
	
}
