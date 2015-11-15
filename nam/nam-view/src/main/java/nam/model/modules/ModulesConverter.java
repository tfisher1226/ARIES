package nam.model.modules;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import nam.model.Modules;
import nam.model.util.ModulesUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;


public class ModulesConverter extends AbstractConverter implements javax.faces.convert.Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ModulesListManager modulesListManager = BeanContext.getFromSession("mainModulesListManager");
		Modules modules = modulesListManager.getRecordByName(value);
		return modules;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Modules modules = (Modules) value;
		return ModulesUtil.toString(modules);
	}
	
}
