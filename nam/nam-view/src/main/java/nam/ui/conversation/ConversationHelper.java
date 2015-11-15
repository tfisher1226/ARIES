package nam.ui.conversation;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.ui.Conversation;
import nam.ui.util.ConversationUtil;


@SessionScoped
@Named("conversationHelper")
public class ConversationHelper extends AbstractElementHelper<Conversation> implements Serializable {
	
	@Override
	public boolean isEmpty(Conversation conversation) {
		return ConversationUtil.isEmpty(conversation);
	}
	
	@Override
	public String toString(Conversation conversation) {
		return ConversationUtil.toString(conversation);
	}
	
	@Override
	public String toString(Collection<Conversation> conversationList) {
		return ConversationUtil.toString(conversationList);
	}
	
	@Override
	public boolean validate(Conversation conversation) {
		return ConversationUtil.validate(conversation);
	}
	
	@Override
	public boolean validate(Collection<Conversation> conversationList) {
		return ConversationUtil.validate(conversationList);
	}
	
}
