package nam.ui.conversation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.Assert;

import nam.ui.Conversation;
import nam.ui.design.SelectionContext;
import nam.ui.util.ConversationUtil;


@SessionScoped
@Named("conversationDataManager")
public class ConversationDataManager implements Serializable {
	
	@Inject
	private ConversationEventManager conversationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Conversation> getConversationList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Conversation> getDefaultList() {
		return null;
	}
	
	public void saveConversation(Conversation conversation) {
		if (scope != null) {
		}
	}
	
	public boolean removeConversation(Conversation conversation) {
		if (scope != null) {
		}
		return false;
	}
	
}
