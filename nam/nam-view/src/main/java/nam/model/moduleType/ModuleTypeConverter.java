package nam.model.moduleType;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.ModuleType;


@FacesConverter(value = "moduleTypeConverter", forClass = ModuleType.class)
public class ModuleTypeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ModuleType moduleType = ModuleType.valueOf(value.toUpperCase());
		return moduleType;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		ModuleType moduleType = null;
		if (value instanceof String)
			moduleType = ModuleType.valueOf((String) value);
		else if (value instanceof ModuleType)
			moduleType = (ModuleType) value;
		return moduleType.value();
	}
	
}
