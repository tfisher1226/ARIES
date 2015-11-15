package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.aries.common.PhoneNumber;
import org.aries.common.util.PhoneNumberUtil;


@FacesConverter(value="phoneNumberConverter", forClass=PhoneNumber.class)
public class PhoneNumberConverter extends AbstractConverter implements Converter, Serializable {

	public PhoneNumberConverter() {
		// nothing for now
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		PhoneNumber phone = PhoneNumberUtil.toPhoneNumber(value);
		return phone;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		if (value instanceof PhoneNumber == false)
			throw new ConverterException("Unexpected value for PhoneNumber: "+value);
		PhoneNumber phone = (PhoneNumber) value;
		String text = PhoneNumberUtil.toString(phone);
		return text;
	}

}
