package nam.model.property;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Property;
import nam.model.util.PropertyUtil;


@FacesConverter(value = "propertyConverter", forClass = Property.class)
public class PropertyConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		PropertyListManager propertyListManager = BeanContext.getFromSession("propertyListManager");
		Property property = propertyListManager.getRecordByName(value);
		return property;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Property property = (Property) value;
		return PropertyUtil.toString(property);
	}
	
}
