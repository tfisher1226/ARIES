package org.aries.notification;

import java.io.Serializable;

import javax.management.Notification;


public class ServiceEventNotification extends Notification implements Serializable {

	private static final long serialVersionUID = -8975305006175714826L;

	public static String NOTIFICATION_ID = "org.aries.ServiceEvent";

	public static String DESCRIPTION = "A service event has been fired";

	private static long sequenceNumber = 0L;
	
	private String eventId;

	private String correlationId;

	
	public ServiceEventNotification(Object source) {
		super(NOTIFICATION_ID, source, sequenceNumber++);
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String serviceId) {
		this.eventId = serviceId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}
