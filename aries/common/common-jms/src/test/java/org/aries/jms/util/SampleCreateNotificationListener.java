package org.aries.jms.util;

import javax.management.Notification;
import javax.management.NotificationListener;


public class SampleCreateNotificationListener implements NotificationListener {

	public void handleNotification(Notification notification, Object handback) { 
//		if (notification instanceof ServiceNotification) { 
//			ServiceNotification n = (ServiceNotification) notification;
//			System.out.println( "\nReceived service notification: " );
//			System.out.println( "\tNotification type: " + n.getType() );
//			System.out.println( "\tService name: " + n.getServiceName() );
//		} else { 
//			System.err.println( "Wrong type of notification for listener" );
//			return;
//		}
	}

}
