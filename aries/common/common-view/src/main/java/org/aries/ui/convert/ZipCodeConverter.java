package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.aries.common.ZipCode;
import org.aries.common.util.ZipCodeUtil;


@FacesConverter(value="zipCodeConverter", forClass=ZipCode.class)
public class ZipCodeConverter extends AbstractConverter implements Converter, Serializable {

	public ZipCodeConverter() {
		// nothing for now
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ZipCode zipCode = ZipCodeUtil.toZipCode(value);
		return zipCode;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		if (value instanceof ZipCode == false)
			throw new ConverterException("Unexpected value for ZipCode: "+value);
		ZipCode zipCode = (ZipCode) value;
		String text = ZipCodeUtil.toZipCodeString(zipCode);
		return text;
	}

}
