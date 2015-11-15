package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value="booleanConverter", forClass=Boolean.class)
public class BooleanConverter extends AbstractConverter implements Converter, Serializable {

	public BooleanConverter() {
		// nothing for now
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null)
			return null;
		Boolean object = Boolean.valueOf(value);
		return object;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		String text = value.toString();
		return text;
	}

}
