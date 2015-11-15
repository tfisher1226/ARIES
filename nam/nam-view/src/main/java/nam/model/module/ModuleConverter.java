package nam.model.module;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Module;
import nam.model.util.ModuleUtil;


@FacesConverter(value = "moduleConverter", forClass = Module.class)
public class ModuleConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ModuleListManager moduleListManager = BeanContext.getFromSession("moduleListManager");
		Module module = moduleListManager.getRecordByName(value);
		return module;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Module module = (Module) value;
		return ModuleUtil.getLabel(module);
	}
	
}
