package nam.model.acknowledgeMode;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.AcknowledgeMode;


@FacesConverter(value = "acknowledgeModeConverter", forClass = AcknowledgeMode.class)
public class AcknowledgeModeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		AcknowledgeMode acknowledgeMode = AcknowledgeMode.valueOf(value.toUpperCase());
		return acknowledgeMode;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		AcknowledgeMode acknowledgeMode = null;
		if (value instanceof String)
			acknowledgeMode = AcknowledgeMode.valueOf((String) value);
		else if (value instanceof AcknowledgeMode)
			acknowledgeMode = (AcknowledgeMode) value;
		return acknowledgeMode.value();
	}
	
}
