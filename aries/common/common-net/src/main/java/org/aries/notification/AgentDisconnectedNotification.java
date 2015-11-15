/*
 * AgentDisconnectedNotification.java
 * Created on Aug 4, 2005
 * 
 * 
 */
package org.aries.notification;

import java.net.InetAddress;

/**
 * <code>AgentDisconnectedNotification</code> - 
 * @author tfisher
 * Aug 4, 2005
 */
public class AgentDisconnectedNotification extends AbstractAgentNotification {

    public AgentDisconnectedNotification(InetAddress localAddress, int localPort, InetAddress remoteAddress, int remotePort) {
        super("AgentDisconnected", localAddress, localPort, remoteAddress, remotePort);
    }
    
    public void setRemoteHost(InetAddress address) {
        _remoteAddress = address;
    }

    public void setRemotePort(int port) {
        _remotePort = port;
    }
    
}
