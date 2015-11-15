package nam.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Conversations", namespace = "http://nam/ui", propOrder = {
	"conversation"
})
@XmlRootElement(name = "conversations", namespace = "http://nam/ui")
public class Conversations implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "conversation", namespace = "http://nam/ui")
	private List<Conversation> conversation;
	
	
	public Conversations() {
		conversation = new ArrayList<Conversation>();
	}
	
	
	public List<Conversation> getConversation() {
		synchronized (conversation) {
			return conversation;
		}
	}
	
	public void setConversation(Collection<Conversation> conversation) {
		if (conversation == null) {
			this.conversation = null;
		} else {
		synchronized (this.conversation) {
				this.conversation = new ArrayList<Conversation>();
				addToConversation(conversation);
			}
		}
	}

	public void addToConversation(Conversation conversation) {
		if (conversation != null ) {
			synchronized (this.conversation) {
				this.conversation.add(conversation);
			}
		}
	}

	public void addToConversation(Collection<Conversation> conversationCollection) {
		if (conversationCollection != null && !conversationCollection.isEmpty()) {
			synchronized (this.conversation) {
				this.conversation.addAll(conversationCollection);
			}
		}
	}

	public void removeFromConversation(Conversation conversation) {
		if (conversation != null ) {
			synchronized (this.conversation) {
				this.conversation.remove(conversation);
			}
		}
	}

	public void removeFromConversation(Collection<Conversation> conversationCollection) {
		if (conversationCollection != null ) {
			synchronized (this.conversation) {
				this.conversation.removeAll(conversationCollection);
			}
		}
	}

	public void clearConversation() {
		synchronized (conversation) {
			conversation.clear();
		}
	}
}
