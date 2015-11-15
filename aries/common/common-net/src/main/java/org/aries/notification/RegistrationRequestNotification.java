/*
 * RegistrationRequestNotification.java
 * Created on Apr 19, 2005
 * 
 * 
 */
package org.aries.notification;

import java.net.InetAddress;


/**
 * <code>RegistrationRequestNotification</code> - 
 * @author tfisher
 * Apr 19, 2005
 */
public class RegistrationRequestNotification extends AbstractControlNotification {

    private InetAddress _host;
    
    private int _port;

    public RegistrationRequestNotification(InetAddress host, int port) {
        super("RegistrationRequest", host);
        _host = host;
        _port = port;
    }
    
    public InetAddress getHost() {
        return _host;
    }
    
    public int getPort() {
        return _port;
    }
    
}
