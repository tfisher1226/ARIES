package nam.model.service;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Service;
import nam.model.util.ServiceUtil;


@FacesConverter(value = "serviceConverter", forClass = Service.class)
public class ServiceConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ServiceListManager serviceListManager = BeanContext.getFromSession("serviceListManager");
		Service service = serviceListManager.getRecordByName(value);
		return service;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Service service = (Service) value;
		return ServiceUtil.getLabel(service);
	}
	
}
