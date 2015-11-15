package nam.ui.control;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.ui.Control;
import nam.ui.util.ControlUtil;


@FacesConverter(value = "controlConverter", forClass = Control.class)
public class ControlConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ControlListManager controlListManager = BeanContext.getFromSession("controlListManager");
		Control control = controlListManager.getRecordByName(value);
		return control;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Control control = (Control) value;
		return ControlUtil.toString(control);
	}
	
}
