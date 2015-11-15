package nam.model.dependency;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Dependency;
import nam.model.util.DependencyUtil;


@FacesConverter(value = "dependencyConverter", forClass = Dependency.class)
public class DependencyConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		DependencyListManager dependencyListManager = BeanContext.getFromSession("dependencyListManager");
		Dependency dependency = dependencyListManager.getRecordByName(value);
		return dependency;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Dependency dependency = (Dependency) value;
		return DependencyUtil.toString(dependency);
	}
	
}
