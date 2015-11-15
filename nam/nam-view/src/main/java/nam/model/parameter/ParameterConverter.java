package nam.model.parameter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Parameter;
import nam.model.util.ParameterUtil;


@FacesConverter(value = "parameterConverter", forClass = Parameter.class)
public class ParameterConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ParameterListManager parameterListManager = BeanContext.getFromSession("parameterListManager");
		Parameter parameter = parameterListManager.getRecordByName(value);
		return parameter;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Parameter parameter = (Parameter) value;
		return ParameterUtil.toString(parameter);
	}
	
}
