package org.aries.ui.convert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;


@FacesConverter(value="currencyConverter", forClass=Number.class)
public class CurrencyConverter extends AbstractConverter implements Converter, Serializable {
	
	private static final NumberFormat FORMAT = NumberFormat.getCurrencyInstance(Locale.US);

	
	public CurrencyConverter() {
		// nothing for now
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		
		ParsePosition parsePosition = new ParsePosition(0);
		Number number = FORMAT.parse(value, parsePosition);
		
		if (parsePosition.getIndex() != value.length()) {
			final String converterMessage = getConverterMessage(component);
			final String errorMessage = converterMessage != null ? converterMessage : String.format("Invalid currency %s", value);
			//TODO FacesMessages.instance().add(Severity.ERROR, errorMessage);
			throw new ConverterException(errorMessage);
		}
		
		return BigDecimal.valueOf(number.intValue());
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof Number)) {
			String errorMessage = String.format("Cannot convert %s. %s converts %s types", value, this.getClass().getName(), Number.class.getName());
			//TODO FacesMessages.instance().add(Severity.ERROR, errorMessage);
			throw new ConverterException(errorMessage);
		}
		
		Number number = (Number) value;
		return FORMAT.format(number.doubleValue());
	}
	
	private String getConverterMessage(UIComponent component) {
		Map<String, Object> attributes = component.getAttributes();
		Object tmp = attributes.get("converterMessage");
		if (tmp instanceof String)
			return (String) tmp;
		return null;
	}

}
