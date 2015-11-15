package nam.model.transport;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Transport;
import nam.model.util.TransportUtil;


@FacesConverter(value = "transportConverter", forClass = Transport.class)
public class TransportConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		TransportListManager transportListManager = BeanContext.getFromSession("transportListManager");
		Transport transport = transportListManager.getRecordByName(value);
		return transport;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Transport transport = (Transport) value;
		return TransportUtil.getLabel(transport);
	}
	
}
