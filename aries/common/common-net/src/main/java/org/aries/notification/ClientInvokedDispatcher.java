package org.aries.notification;

import java.util.Map;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationEmitter;

import org.aries.util.ExceptionUtil;


public class ClientInvokedDispatcher extends NotificationBroadcasterSupport implements ClientInvokedDispatcherMBean, NotificationEmitter {

	public static final String MBEAN_NAME = "ApplicationManagment:name=ClientInvokedDispatcher";
	
	
	@Override
	public void sendNotification(Notification notification) {
		super.sendNotification(notification);
	}
	
	@Override
	public void sendNotification(Object source, Map<String, Object> data) {
		try {
			ClientInvokedNotification notification = new ClientInvokedNotification(source);
			notification.setUserData(data);
			sendNotification(notification);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		String[] notificationTypes = new String[] { ClientInvokedNotification.NOTIFICATION_ID };
		String className = AttributeChangeNotification.class.getName();
		String description = ClientInvokedNotification.DESCRIPTION;
		MBeanNotificationInfo notificationInfo = new MBeanNotificationInfo(notificationTypes, className, description);
		MBeanNotificationInfo[] notificationInfoArray = new MBeanNotificationInfo[] { notificationInfo };
		return notificationInfoArray;
	}

}

