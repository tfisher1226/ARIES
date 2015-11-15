package nam.model.moduleLevel;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.ModuleLevel;


@FacesConverter(value = "moduleLevelConverter", forClass = ModuleLevel.class)
public class ModuleLevelConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ModuleLevel moduleLevel = ModuleLevel.valueOf(value.toUpperCase());
		return moduleLevel;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		ModuleLevel moduleLevel = null;
		if (value instanceof String)
			moduleLevel = ModuleLevel.valueOf((String) value);
		else if (value instanceof ModuleLevel)
			moduleLevel = (ModuleLevel) value;
		return moduleLevel.value();
	}
	
}
