package nam.ui.conversation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Conversation;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("conversationEventManager")
public class ConversationEventManager extends AbstractEventManager<Conversation> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Conversation getInstance() {
		return selectionContext.getSelection("conversation");
	}
	
	public void removeConversation() {
		Conversation conversation = getInstance();
		fireRemoveEvent(conversation);
	}
	
}
