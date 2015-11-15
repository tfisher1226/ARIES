package nam.model.fault;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Fault;
import nam.model.util.FaultUtil;


@FacesConverter(value = "faultConverter", forClass = Fault.class)
public class FaultConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		FaultListManager faultListManager = BeanContext.getFromSession("faultListManager");
		Fault fault = faultListManager.getRecordByName(value);
		return fault;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Fault fault = (Fault) value;
		return FaultUtil.toString(fault);
	}
	
}
