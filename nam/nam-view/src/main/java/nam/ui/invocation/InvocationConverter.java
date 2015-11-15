package nam.ui.invocation;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.ui.Invocation;
import nam.ui.util.InvocationUtil;


@FacesConverter(value = "invocationConverter", forClass = Invocation.class)
public class InvocationConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		InvocationListManager invocationListManager = BeanContext.getFromSession("invocationListManager");
		Invocation invocation = invocationListManager.getRecordByName(value);
		return invocation;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Invocation invocation = (Invocation) value;
		return InvocationUtil.toString(invocation);
	}
	
}
