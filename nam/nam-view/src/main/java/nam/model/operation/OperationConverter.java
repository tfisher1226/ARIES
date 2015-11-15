package nam.model.operation;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Operation;
import nam.model.util.OperationUtil;


@FacesConverter(value = "operationConverter", forClass = Operation.class)
public class OperationConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		OperationListManager operationListManager = BeanContext.getFromSession("operationListManager");
		Operation operation = operationListManager.getRecordByName(value);
		return operation;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Operation operation = (Operation) value;
		return OperationUtil.toString(operation);
	}
	
}
