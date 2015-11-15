package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import nam.model.TransportType;

import org.apache.commons.lang.StringUtils;


@FacesConverter(value="transportTypeConverter", forClass=TransportType.class)
public class TransportTypeConverter extends AbstractConverter implements Converter, Serializable {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		TransportType action = TransportType.fromValue(value);
		return action;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		TransportType action = (TransportType) value;
		return action.value();
	}

}
