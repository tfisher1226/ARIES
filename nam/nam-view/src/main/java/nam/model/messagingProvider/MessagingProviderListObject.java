package nam.model.messagingProvider;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.MessagingProvider;
import nam.model.util.MessagingProviderUtil;


public class MessagingProviderListObject extends AbstractListObject<MessagingProvider> implements Comparable<MessagingProviderListObject>, Serializable {
	
	private MessagingProvider messagingProvider;
	
	
	public MessagingProviderListObject(MessagingProvider messagingProvider) {
		this.messagingProvider = messagingProvider;
	}
	
	
	public MessagingProvider getMessagingProvider() {
		return messagingProvider;
	}
	
	@Override
	public Object getKey() {
		return getKey(messagingProvider);
	}
	
	public Object getKey(MessagingProvider messagingProvider) {
		return MessagingProviderUtil.getKey(messagingProvider);
	}
	
	@Override
	public String getLabel() {
		return getLabel(messagingProvider);
	}
	
	public String getLabel(MessagingProvider messagingProvider) {
		return MessagingProviderUtil.getLabel(messagingProvider);
	}
	
	@Override
	public String toString() {
		return toString(messagingProvider);
	}
	
	@Override
	public String toString(MessagingProvider messagingProvider) {
		return MessagingProviderUtil.toString(messagingProvider);
	}
	
	@Override
	public int compareTo(MessagingProviderListObject other) {
		Object thisKey = getKey(this.messagingProvider);
		Object otherKey = getKey(other.messagingProvider);
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
		MessagingProviderListObject other = (MessagingProviderListObject) object;
		Object thisKey = MessagingProviderUtil.getKey(this.messagingProvider);
		Object otherKey = MessagingProviderUtil.getKey(other.messagingProvider);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
