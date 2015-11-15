package nam.model.network;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Network;
import nam.model.util.NetworkUtil;


@FacesConverter(value = "networkConverter", forClass = Network.class)
public class NetworkConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		NetworkListManager networkListManager = BeanContext.getFromSession("networkListManager");
		Network network = networkListManager.getRecordByName(value);
		return network;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Network network = (Network) value;
		return NetworkUtil.toString(network);
	}
	
}
