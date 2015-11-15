package nam.ui.conversation;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.ui.Conversation;
import nam.ui.util.ConversationUtil;


public class ConversationListObject extends AbstractListObject<Conversation> implements Comparable<ConversationListObject>, Serializable {
	
	private Conversation conversation;
	
	
	public ConversationListObject(Conversation conversation) {
		this.conversation = conversation;
	}
	
	
	public Conversation getConversation() {
		return conversation;
	}
	
	@Override
	public Object getKey() {
		return getKey(conversation);
	}
	
	public Object getKey(Conversation conversation) {
		return ConversationUtil.getKey(conversation);
	}
	
	@Override
	public String getLabel() {
		return getLabel(conversation);
	}
	
	public String getLabel(Conversation conversation) {
		return ConversationUtil.getLabel(conversation);
	}
	
	@Override
	public String toString() {
		return toString(conversation);
	}
	
	@Override
	public String toString(Conversation conversation) {
		return ConversationUtil.toString(conversation);
	}
	
	@Override
	public int compareTo(ConversationListObject other) {
		Object thisKey = getKey(this.conversation);
		Object otherKey = getKey(other.conversation);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ConversationListObject other = (ConversationListObject) object;
		Object thisKey = ConversationUtil.getKey(this.conversation);
		Object otherKey = ConversationUtil.getKey(other.conversation);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
