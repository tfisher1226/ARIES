package org.aries.event;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EventLog implements Serializable {

	protected static final Log log = LogFactory.getLog(EventLog.class);

	private static EventLog instance = new EventLog();

	
	public static EventLog getInstance() {
		return instance;
	}


	public String logEvent(String message) {
		Event event = new Event();
		event.setUserId("tfisher");
		event.setMessage(message);
		event.setTimestamp(new Date());
		event.setSequenceNumber(1L);
		event.setEventType(EventType.APPLICATION);
		event.setEventAction(EventAction.UPDATED);
		event.setEventSeverity(EventSeverity.EVENT);
		event.setSource("Internal");
		event.setObject("Internal");
		//event.setTransactionId(getTransactionId());
		String result = logEvent(event);
		return result;
	}


	public void trace(String message) {
		log.info(message);
	}


	public String logEvent(Event event) {
		return null;
	}



//	public String logEvent(Event event) {
//		Transaction transaction = TransactionCache.getTransaction();
//		String result = null;
//		try {
//			String transactionId = transaction.beginSubordinate();
//			event.setTransactionId(transactionId);
//
//			Message request = new Message();
//			request.addPart("event", event);
//			ServiceLocator serviceLocator = BeanContext.get("org.aries.serviceLocator");
//			ServiceProxy proxy = serviceLocator.lookup("/event-service", "0.0.1-SNAPSHOT", "org.aries.event.eventService", "logEvent", Transport.WS);
//			proxy.invoke(request);
//			result = "success";
//			return result;
//		} catch (Exception e) {
//			log.error("Error", e);
//			transaction.rollbackSubordinate();
//			result = e.getMessage();
//			return result;
//		}
//	}
	
}
