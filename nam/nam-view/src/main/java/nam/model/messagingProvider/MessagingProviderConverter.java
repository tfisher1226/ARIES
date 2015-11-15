package nam.model.messagingProvider;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.MessagingProvider;
import nam.model.util.MessagingProviderUtil;


@FacesConverter(value = "messagingProviderConverter", forClass = MessagingProvider.class)
public class MessagingProviderConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		MessagingProviderListManager messagingProviderListManager = BeanContext.getFromSession("messagingProviderListManager");
		MessagingProvider messagingProvider = messagingProviderListManager.getRecordByName(value);
		return messagingProvider;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		MessagingProvider messagingProvider = (MessagingProvider) value;
		return MessagingProviderUtil.toString(messagingProvider);
	}
	
}
