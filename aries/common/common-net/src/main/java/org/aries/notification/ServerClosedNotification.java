/*
 * ServerClosedNotification.java
 * Created on Aug 24, 2005
 * 
 * 
 */
package org.aries.notification;

/**
 * <code>ServerClosedNotification</code> - 
 * @author tfisher
 * Aug 24, 2005
 */
public class ServerClosedNotification extends AbstractControlNotification {

    private String _host;
    
    private int _port;
    
    public ServerClosedNotification(Object source, String host, int port) {
        super("ServerClosed", source);
        _host = host;
        _port = port;
    }
    
    public String getHost() {
        return _host;
    }
    
    public int getPort() {
        return _port;
    }
    
}