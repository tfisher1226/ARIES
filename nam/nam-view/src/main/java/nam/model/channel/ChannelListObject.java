package nam.model.channel;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Channel;
import nam.model.util.ChannelUtil;


public class ChannelListObject extends AbstractListObject<Channel> implements Comparable<ChannelListObject>, Serializable {
	
	private Channel channel;
	
	
	public ChannelListObject(Channel channel) {
		this.channel = channel;
	}
	
	
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public Object getKey() {
		return getKey(channel);
	}
	
	public Object getKey(Channel channel) {
		return ChannelUtil.getKey(channel);
	}
	
	@Override
	public String getLabel() {
		return getLabel(channel);
	}
	
	public String getLabel(Channel channel) {
		return ChannelUtil.getLabel(channel);
	}
	
	@Override
	public String toString() {
		return toString(channel);
	}
	
	@Override
	public String toString(Channel channel) {
		return ChannelUtil.toString(channel);
	}
	
	@Override
	public int compareTo(ChannelListObject other) {
		Object thisKey = getKey(this.channel);
		Object otherKey = getKey(other.channel);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ChannelListObject other = (ChannelListObject) object;
		Object thisKey = ChannelUtil.getKey(this.channel);
		Object otherKey = ChannelUtil.getKey(other.channel);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
