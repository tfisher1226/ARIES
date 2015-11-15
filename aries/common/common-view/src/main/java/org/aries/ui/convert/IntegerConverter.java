package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value="integerConverter", forClass=Integer.class)
public class IntegerConverter extends AbstractConverter implements Converter, Serializable {

	public IntegerConverter() {
		// nothing for now
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null)
			return null;
		try {
			int object = Integer.parseInt(value);
			return object;
		} catch (NumberFormatException ee) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		String text = value.toString();
		return text;
	}

}
