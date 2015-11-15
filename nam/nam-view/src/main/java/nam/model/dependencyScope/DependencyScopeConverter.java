package nam.model.dependencyScope;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.convert.AbstractConverter;

import nam.model.DependencyScope;


@FacesConverter(value = "dependencyScopeConverter", forClass = DependencyScope.class)
public class DependencyScopeConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		DependencyScope dependencyScope = DependencyScope.valueOf(value.toUpperCase());
		return dependencyScope;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		DependencyScope dependencyScope = null;
		if (value instanceof String)
			dependencyScope = DependencyScope.valueOf((String) value);
		else if (value instanceof DependencyScope)
			dependencyScope = (DependencyScope) value;
		return dependencyScope.value();
	}
	
}
