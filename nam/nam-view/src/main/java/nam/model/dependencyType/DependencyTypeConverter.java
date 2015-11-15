package nam.model.dependencyType;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.DependencyType;


@FacesConverter(value = "dependencyTypeConverter", forClass = DependencyType.class)
public class DependencyTypeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		DependencyType dependencyType = DependencyType.valueOf(value.toUpperCase());
		return dependencyType;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		DependencyType dependencyType = null;
		if (value instanceof String)
			dependencyType = DependencyType.valueOf((String) value);
		else if (value instanceof DependencyType)
			dependencyType = (DependencyType) value;
		return dependencyType.value();
	}
	
}
