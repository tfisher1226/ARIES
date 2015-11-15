package nam.model.transportType;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.TransportType;


@FacesConverter(value = "transportTypeConverter", forClass = TransportType.class)
public class TransportTypeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		TransportType transportType = TransportType.valueOf(value.toUpperCase());
		return transportType;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		TransportType transportType = null;
		if (value instanceof String)
			transportType = TransportType.valueOf((String) value);
		else if (value instanceof TransportType)
			transportType = (TransportType) value;
		return transportType.value();
	}
	
}
