package org.aries.notification;

import javax.management.Notification;


public class ClientInvokedNotification extends Notification {

	private static final long serialVersionUID = -8975305006175714826L;

	public static String NOTIFICATION_ID = "org.aries.ClientInvoked";

	public static String DESCRIPTION = "A client method requesting a service has been invoked";

	private static long sequenceNumber = 0L;
	

	public ClientInvokedNotification(Object source) {
		super(NOTIFICATION_ID, source, sequenceNumber++);
	}

}
