package org.aries.nam.model.old;

import java.io.Serializable;


public interface ChannelDescripter extends Serializable {

	public String getType();

	public String getName();

	public String getHost();

	public String getPort();

	public String getTransferMode();

	public String getProviderName();

	public String getDestinationName();

	public String getConnectionFactory();

}
