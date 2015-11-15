package org.aries.nam.model.old;

import java.io.Serializable;


public interface ChannelDescriptor extends Serializable {

    public String getType();

    public String getName();

    public String getHost();

    public String getPort();

    public String getProviderName();

    public String getInboundQueue();

    public String getOutboundQueue();

    public String getExceptionQueue();

    public String getConnectionFactory();

}

