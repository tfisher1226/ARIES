package org.aries.nam.model.old;


public class ListenerDefinition implements ListenerDescripter {

	private static final long serialVersionUID = 1L;
	
	public String listenerType;

	public String listenerName;
	
	private ChannelDescripter channelDescripter;


	public String getListenerType() {
		return listenerType;
	}

	public void setListenerType(String listenerType) {
		this.listenerType = listenerType;
	}

	public String getListenerName() {
		return listenerName;
	}

	public void setListenerName(String listenerName) {
		this.listenerName = listenerName;
	}

	public ChannelDescripter getChannelDescripter() {
		return channelDescripter;
	}

	public void setChannelDescripter(ChannelDescripter channelDescripter) {
		this.channelDescripter = channelDescripter;
	}
}
