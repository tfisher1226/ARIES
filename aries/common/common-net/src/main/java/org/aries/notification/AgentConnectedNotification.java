/*
 * AgentConnectedNotification.java
 * Created on Aug 21, 2005
 * 
 * 
 */
package org.aries.notification;

import java.net.InetAddress;



/**
 * <code>AgentConnectedNotification</code> - 
 * @author tfisher
 * Apr 21, 2005
 */
public class AgentConnectedNotification extends AbstractAgentNotification {

    public AgentConnectedNotification(InetAddress localAddress, int localPort, InetAddress remoteAddress, int remotePort) {
        super("AgentConnected", localAddress, localPort, remoteAddress, remotePort);
    }

}
