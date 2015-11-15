/*
 * ClientDisconnectedNotification.java
 * Created on Aug 24, 2005
 * 
 * 
 */
package org.aries.notification;

/**
 * <code>ClientDisconnectedNotification</code> - 
 * @author tfisher
 * Aug 24, 2005
 */
public class ClientDisconnectedNotification extends AbstractControlNotification {

    private String _clientId;
    
    public ClientDisconnectedNotification(Object object, String clientId) {
        super("ClientDisconnected", object);
        _clientId = clientId;
    }
    
    public String getClientId() {
        return _clientId;
    }
    
}