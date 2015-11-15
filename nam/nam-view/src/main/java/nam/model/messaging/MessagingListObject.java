package nam.model.messaging;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Messaging;
import nam.model.util.MessagingUtil;


public class MessagingListObject extends AbstractListObject<Messaging> implements Comparable<MessagingListObject>, Serializable {
	
	private Messaging messaging;
	
	
	public MessagingListObject(Messaging messaging) {
		this.messaging = messaging;
	}
	
	
	public Messaging getMessaging() {
		return messaging;
	}
	
	@Override
	public Object getKey() {
		return getKey(messaging);
	}
	
	public Object getKey(Messaging messaging) {
		return MessagingUtil.getKey(messaging);
	}
	
	@Override
	public String getLabel() {
		return getLabel(messaging);
	}
	
	public String getLabel(Messaging messaging) {
		return MessagingUtil.getLabel(messaging);
	}
	
	@Override
	public String toString() {
		return toString(messaging);
	}
	
	@Override
	public String toString(Messaging messaging) {
		return MessagingUtil.toString(messaging);
	}
	
	@Override
	public int compareTo(MessagingListObject other) {
		Object thisKey = getKey(this.messaging);
		Object otherKey = getKey(other.messaging);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		MessagingListObject other = (MessagingListObject) object;
		Object thisKey = MessagingUtil.getKey(this.messaging);
		Object otherKey = MessagingUtil.getKey(other.messaging);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
