package nam.model.enumeration;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Enumeration;
import nam.model.util.EnumerationUtil;


@FacesConverter(value = "enumerationConverter", forClass = Enumeration.class)
public class EnumerationConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		EnumerationListManager enumerationListManager = BeanContext.getFromSession("enumerationListManager");
		Enumeration enumeration = enumerationListManager.getRecordByName(value);
		return enumeration;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Enumeration enumeration = (Enumeration) value;
		return EnumerationUtil.toString(enumeration);
	}
	
}
