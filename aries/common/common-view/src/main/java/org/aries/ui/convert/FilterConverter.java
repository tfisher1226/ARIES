package org.aries.ui.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value="filterConverter", forClass=String.class)
public class FilterConverter extends AbstractConverter implements Converter, Serializable {
	
	private String pattern = "...";

	
	public FilterConverter() {
		// nothing for now
	}
	
	protected int getMaxColumnLength() {
		return 1024;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null)
			return null;
		String object = format(value);
		return object;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		if (value instanceof String == false)
			return value.toString();
		String text = format((String) value);
		//System.out.println(">>>"+text);
		return text;
	}
	
	protected String format(String value) {
		int maxColumnLength = getMaxColumnLength();
		if (value.length() <= maxColumnLength)
			return value;
		StringBuffer buf = new StringBuffer();
		String substring = value.substring(0, maxColumnLength - pattern.length());
		buf.append(substring);
		buf.append(pattern);
		return buf.toString();
	}
	
}
