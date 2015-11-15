package org.aries.notification;

import java.lang.management.ManagementFactory;

import javax.management.Notification;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.ExceptionUtil;

import common.jmx.client.MBeanClient;


public class NotificationDispatcher {

	private static Log log = LogFactory.getLog(NotificationDispatcher.class);

	private MBeanClient mBeanClient;
	
	
	public NotificationDispatcher() {
		mBeanClient = new MBeanClient();
		mBeanClient.setMBeanServerConnection(ManagementFactory.getPlatformMBeanServer());
	}

	protected Notification createClientInvokedNotification(Object sourceObject, Object userData) {
		ClientInvokedNotification notification = new ClientInvokedNotification(sourceObject);
		notification.setUserData(userData);
		return notification;
	}
	
//	protected Notification createServiceInvokedNotification(String serviceId, Object sourceObject, Object userData) {
//		ServiceInvokedNotification notification = new ServiceInvokedNotification(sourceObject);
//		notification.setServiceId(serviceId);
//		notification.setUserData(userData);
//		return notification;
//	}
//	
//	protected Notification createServiceCompletedNotification(String serviceId, Object sourceObject, Object userData) {
//		ServiceCompletedNotification notification = new ServiceCompletedNotification(sourceObject);
//		notification.setServiceId(serviceId);
//		notification.setUserData(userData);
//		return notification;
//	}
//	
//	protected Notification createServiceAbortedNotification(String serviceId, Object sourceObject, Object userData) {
//		ServiceAbortedNotification notification = new ServiceAbortedNotification(sourceObject);
//		notification.setServiceId(serviceId);
//		notification.setUserData(userData);
//		return notification;
//	}
	
	protected Notification createServiceEventNotification(String eventId, String correlationId, Object sourceObject, Object userData) {
		ServiceEventNotification notification = new ServiceEventNotification(sourceObject);
		notification.setEventId(eventId);
		notification.setCorrelationId(correlationId);
		notification.setUserData(userData);
		return notification;
	}

	public void fireClientInvokedNotification(Object sourceObject, Object userData) {
		Notification notification = createClientInvokedNotification(sourceObject, userData);
		fireNotification(ClientInvokedDispatcher.MBEAN_NAME, notification);
	}
	
//	public void fireServiceInvokedNotification(String serviceId, Object sourceObject, Object userData) {
//		Notification notification = createServiceInvokedNotification(serviceId, sourceObject, userData);
//		fireNotification(ServiceInvokedDispatcher.MBEAN_NAME, notification);
//	}
//	
//	public void fireServiceCompletedNotification(String serviceId, Object sourceObject, Object userData) {
//		Notification notification = createServiceCompletedNotification(serviceId, sourceObject, userData);
//		fireNotification(ServiceCompletedDispatcher.MBEAN_NAME, notification);
//	}
//
//	public void fireServiceAbortedNotification(String serviceId, Object sourceObject, Object userData) {
//		Notification notification = createServiceAbortedNotification(serviceId, sourceObject, userData);
//		fireNotification(ServiceAbortedDispatcher.MBEAN_NAME, notification);
//	}

	public void fireEventNotification(String eventId, String correlationId, Object sourceObject, Object userData) {
		Notification notification = createServiceEventNotification(eventId, correlationId, sourceObject, userData);
		fireNotification(ServiceEventDispatcher.MBEAN_NAME, notification);
	}
	
	public void fireNotification(String mbeanName, Notification notification) {
		ServiceEventNotification eventNotification = (ServiceEventNotification) notification;
		Object sourceName = notification.getSource();
		String eventId = eventNotification.getEventId();
		try {
			log.info("["+sourceName+"]: Firing notification: "+eventId);
			mBeanClient.sendNotification(mbeanName, notification);
			log.info("["+sourceName+"]: Fired notification: "+eventId);
		} catch (Exception e) {
			log.info("["+sourceName+"]: Problem firing notification: "+eventId, e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}

