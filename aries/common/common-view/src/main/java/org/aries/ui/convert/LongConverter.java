package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.aries.util.Validator;


@FacesConverter(value="longConverter", forClass=Long.class)
public class LongConverter extends AbstractConverter implements Converter, Serializable {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Validator validator = Validator.getValidator();
 		validator.notEmpty(value, "Long value not specified");
		if (value == null || value.isEmpty())
			return null;
		try {
			Long object = Long.parseLong(value);
			return object;
		} catch (NumberFormatException e) {
			if (!value.isEmpty()) {
				validator.getMessages().add("Invalid format for \"Long\" value: "+value);
			}
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
