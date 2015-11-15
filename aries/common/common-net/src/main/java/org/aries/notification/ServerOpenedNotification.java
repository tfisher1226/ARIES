/*
 * ServerOpenedNotification.java
 * Created on Aug 24, 2005
 * 
 * 
 */
package org.aries.notification;

/**
 * <code>ServerOpenedNotification</code> - 
 * @author tfisher
 * Aug 24, 2005
 */
public class ServerOpenedNotification extends AbstractControlNotification {

    private String _host;
    
    private int _port;
    
    public ServerOpenedNotification(Object source, String host, int port) {
        super("ServerOpened", source);
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