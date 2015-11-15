package nam.model.variable;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Variable;
import nam.model.util.VariableUtil;


@FacesConverter(value = "variableConverter", forClass = Variable.class)
public class VariableConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		VariableListManager variableListManager = BeanContext.getFromSession("variableListManager");
		Variable variable = variableListManager.getRecordByName(value);
		return variable;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Variable variable = (Variable) value;
		return VariableUtil.toString(variable);
	}
	
}
