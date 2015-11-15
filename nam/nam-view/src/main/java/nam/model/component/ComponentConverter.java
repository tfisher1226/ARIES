package nam.model.component;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Component;
import nam.model.util.ComponentUtil;


@FacesConverter(value = "componentConverter", forClass = Component.class)
public class ComponentConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ComponentListManager componentListManager = BeanContext.getFromSession("componentListManager");
		Component component = componentListManager.getRecordByName(value);
		return component;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Component component = (Component) value;
		return ComponentUtil.toString(component);
	}
	
}
