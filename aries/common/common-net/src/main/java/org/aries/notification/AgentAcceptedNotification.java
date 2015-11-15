/*
 * AgentAcceptedNotification.java
 * Created on Aug 12, 2005
 * 
 * 
 */
package org.aries.notification;

import java.net.InetAddress;

/**
 * <code>AgentAcceptedNotification</code> - 
 * @author tfisher
 * Aug 12, 2005
 */
public class AgentAcceptedNotification extends AbstractAgentNotification {

    public AgentAcceptedNotification(InetAddress localHost, int localPort, InetAddress remoteHost, int remotePort) {
        super("AgentAccepted", localHost, localPort, remoteHost, remotePort);
    }

}
