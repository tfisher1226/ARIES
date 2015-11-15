package org.aries.notification;

import java.util.Map;

import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationEmitter;

import org.aries.util.ExceptionUtil;


public class ServiceEventDispatcher extends NotificationBroadcasterSupport implements ServiceEventDispatcherMBean, NotificationEmitter {

	public static final String MBEAN_NAME = "ApplicationManagment:name=ServiceEventDispatcher";
	
	
	@Override
	public void sendNotification(Notification notification) {
		super.sendNotification(notification);
	}
	
	@Override
	public void sendNotification(Object source, Map<String, Object> data) {
		try {
			ServiceEventNotification notification = new ServiceEventNotification(source);
			notification.setUserData(data);
			sendNotification(notification);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		String[] notificationTypes = new String[] { ServiceEventNotification.NOTIFICATION_ID };
		String className = ServiceEventNotification.class.getName();
		String description = ServiceEventNotification.DESCRIPTION;
		MBeanNotificationInfo notificationInfo = new MBeanNotificationInfo(notificationTypes, className, description);
		MBeanNotificationInfo[] notificationInfoArray = new MBeanNotificationInfo[] { notificationInfo };
		return notificationInfoArray;
	}

}

