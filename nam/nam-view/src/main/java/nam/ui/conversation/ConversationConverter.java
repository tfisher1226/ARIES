package nam.ui.conversation;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.ui.Conversation;
import nam.ui.util.ConversationUtil;


@FacesConverter(value = "conversationConverter", forClass = Conversation.class)
public class ConversationConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		ConversationListManager conversationListManager = BeanContext.getFromSession("conversationListManager");
		Conversation conversation = conversationListManager.getRecordByName(value);
		return conversation;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Conversation conversation = (Conversation) value;
		return ConversationUtil.toString(conversation);
	}
	
}
