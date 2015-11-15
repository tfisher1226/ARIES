package org.aries.event;

import java.io.Serializable;
import java.util.Date;

import org.aries.message.Message;


public class EventFactory implements Serializable {

    public Event createEvent(Message message) {
    	Event event = createEvent();
		//TODO add this as a new field called "payload"
		//TODO event.setMessage(Transformer.toXML(message));
		//Person user = message.getPart("user");
		EventAction action = message.getPart("action");
		String source = message.getPart("source");
		//String function = message.getPart("function");
		String object = message.getPart("object");
		event.setEventAction(action);
		//event.setUserId(user.getBadgeNumber());
		event.setSource(source);
		//event.setFunction(function);
		event.setObject(object);
		return event;
	}

    public Event createEvent() {
    	Event event = new Event();
		event.setTimestamp(new Date());
		event.setEventSeverity(EventSeverity.INFO);
		event.setEventType(EventType.APPLICATION);
		//just do this for now till we discuss
		event.setSequenceNumber(1L);
		return event;
	}

}
