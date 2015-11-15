package nam.model.timeout;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Timeout;
import nam.model.util.TimeoutUtil;


@FacesConverter(value = "timeoutConverter", forClass = Timeout.class)
public class TimeoutConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		TimeoutListManager timeoutListManager = BeanContext.getFromSession("timeoutListManager");
		Timeout timeout = timeoutListManager.getRecordByName(value);
		return timeout;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Timeout timeout = (Timeout) value;
		return TimeoutUtil.toString(timeout);
	}
	
}
