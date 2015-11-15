/*
 * AbstractControlNotification.java
 * Created on Apr 22, 2005
 * 
 * 
 */
package org.aries.notification;

import javax.management.Notification;


/**
 * <code>AbstractControlNotification</code> - 
 * @author tfisher
 * Apr 22, 2005
 */
public class AbstractControlNotification extends Notification {

    private static long _sequenceNumber;

//    private transient JMXClient _connector;

//    private transient JMXControlClient _control;

    
    public AbstractControlNotification(String type, Object object) {
        super(type, object, _sequenceNumber++);
    }

//    public JMXClient getConnector() {
//        return _connector;
//    }
//
//    public void setConnector(JMXClient connector) {
//        _connector = connector;
//    }
//    
//    public JMXControlClient getControl() {
//        return _control;
//    }
//
//    public void setControl(JMXControlClient control) {
//        _control = control;
//    }
    
}
