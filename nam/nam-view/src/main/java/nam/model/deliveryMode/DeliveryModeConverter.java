package nam.model.deliveryMode;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.DeliveryMode;


@FacesConverter(value = "deliveryModeConverter", forClass = DeliveryMode.class)
public class DeliveryModeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		DeliveryMode deliveryMode = DeliveryMode.valueOf(value.toUpperCase());
		return deliveryMode;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		DeliveryMode deliveryMode = null;
		if (value instanceof String)
			deliveryMode = DeliveryMode.valueOf((String) value);
		else if (value instanceof DeliveryMode)
			deliveryMode = (DeliveryMode) value;
		return deliveryMode.value();
	}
	
}
