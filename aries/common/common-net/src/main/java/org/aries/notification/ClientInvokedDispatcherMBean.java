package org.aries.notification;

import java.util.Map;

import javax.management.Notification;


public interface ClientInvokedDispatcherMBean {

	public void sendNotification(Notification notification);
	
	public void sendNotification(Object source, Map<String, Object> data);

//	public void addNotificationListener(NotificationListener notificationListener, NotificationFilter filter, Object handback);
//
//	public void removeNotificationListener(NotificationListener notificationListener, NotificationFilter filter, Object handback) throws ListenerNotFoundException;

}
