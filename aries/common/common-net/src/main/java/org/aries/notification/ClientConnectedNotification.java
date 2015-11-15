/*
 * ClientConnectedNotification.java
 * Created on Aug 24, 2005
 * 
 * 
 */
package org.aries.notification;

/**
 * <code>ClientConnectedNotification</code> - 
 * @author tfisher
 * Aug 24, 2005
 */
public class ClientConnectedNotification extends AbstractControlNotification {

    private String _clientId;
    
    public ClientConnectedNotification(Object object, String clientId) {
        super("ClientConnected", object);
        _clientId = clientId;
    }
    
    public String getClientId() {
        return _clientId;
    }
   
}