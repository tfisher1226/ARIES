package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.Map;


public interface ChannelGroupDescripter extends Serializable {

	public String getDomain();

	public String getType();

	public String getTransferMode();

	public String getProviderName();

	public String getConnectionFactory();

	public Map<String, ChannelDescripter> getChannelDescripters();

	public ChannelDescripter getChannelDescripter(String name);

	public void addChannelDescripter(ChannelDescripter channeldescripter);

}
