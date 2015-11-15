package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.aries.common.EmailAddress;


@FacesConverter(value="emailAddressConverter", forClass=EmailAddress.class)
public class EmailAddressConverter extends AbstractConverter implements Converter, Serializable {

	public EmailAddressConverter() {
		// nothing for now
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null)
			return null;
		EmailAddress email = new EmailAddress();
		email.setUrl(value);
		return email;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		EmailAddress emailAddress = (EmailAddress) value;
		return emailAddress.getUrl();
	}

}
