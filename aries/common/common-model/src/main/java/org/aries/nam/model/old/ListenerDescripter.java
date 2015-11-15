package org.aries.nam.model.old;

import java.io.Serializable;


public interface ListenerDescripter extends Serializable {

	public String getListenerType();

	public String getListenerName();

	public ChannelDescripter getChannelDescripter();

}
