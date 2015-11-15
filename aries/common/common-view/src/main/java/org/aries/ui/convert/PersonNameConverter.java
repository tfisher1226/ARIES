package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.util.PersonNameUtil;


@FacesConverter(value="personNameConverter", forClass=EmailAddress.class)
public class PersonNameConverter extends AbstractConverter implements Converter, Serializable {

	public PersonNameConverter() {
		// nothing for now
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null)
			return null;
		PersonName personName = PersonNameUtil.convert(value);
		return personName;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		PersonName personName = (PersonName) value;
		String text = PersonNameUtil.toString(personName);
		return text;
	}

}
