/*
 * ApplicationNotification.java - 
 * Created on Sep 12, 2005
 *  
 *  
 */
package org.aries.notification;

import javax.management.Notification;

/**
 * <code>ApplicationNotification</code> - 
 * @author tfisher
 * Sep 12, 2005
 */
public class ApplicationNotification extends Notification {

    private Object _handback;
    
    private String _clientId;
    
    private static int _sequenceNumber;
    
    
    public ApplicationNotification(String type) {
        super(type, new String(type), _sequenceNumber++);
    }
    
    public ApplicationNotification(String type, Object userData) {
        super(type, new String(type), _sequenceNumber++);
        setUserData(userData);
    }
    

    public Object getHandback() {
        return _handback;
    }

    public void setHandback(Object handback) {
        _handback = handback;
    }
    
    public String getClientId() {
        return _clientId;
    }
   
    public void setClientId(String clientId) {
        _clientId = clientId;
    }
}