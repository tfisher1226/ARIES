/*
 * AbstractAgentNotification.java
 * Created on Aug 12, 2005
 * 
 * 
 */
package org.aries.notification;

import java.net.InetAddress;

/**
 * <code>AbstractAgentNotification</code> - 
 * @author tfisher
 * Aug 12, 2005
 */
public abstract class AbstractAgentNotification extends ApplicationNotification {

    protected InetAddress _localAddress;

    protected int _localPort;

    protected InetAddress _remoteAddress;

    protected int _remotePort;

    private String _processName;

    
    public AbstractAgentNotification(String type, InetAddress localAddress, int localPort, InetAddress remoteAddress, int remotePort) {
        super(type, localAddress);
        _localAddress = localAddress;
        _localPort = localPort;
        _remoteAddress = remoteAddress;
        _remotePort = remotePort;
    }

    public InetAddress getLocalHost() {
        return _localAddress;
    }

    public int getLocalPort() {
        return _localPort;
    }

    public InetAddress getRemoteHost() {
        return _remoteAddress;
    }

    public int getRemotePort() {
        return _remotePort;
    }

    public String getProcessName() {
        return _processName;
    }

    public void setProcessName(String name) {
        _processName = name;
    }
}
