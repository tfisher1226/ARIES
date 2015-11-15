package nam.model.messaging;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Messaging;
import nam.model.util.MessagingUtil;


@FacesConverter(value = "messagingConverter", forClass = Messaging.class)
public class MessagingConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		MessagingListManager messagingListManager = BeanContext.getFromSession("messagingListManager");
		Messaging messaging = messagingListManager.getRecordByName(value);
		return messaging;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Messaging messaging = (Messaging) value;
		return MessagingUtil.getLabel(messaging);
	}
	
}
