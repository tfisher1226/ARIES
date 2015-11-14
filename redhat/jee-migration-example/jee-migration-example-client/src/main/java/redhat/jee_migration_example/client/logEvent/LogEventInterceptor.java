package redhat.jee_migration_example.client.logEvent;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.Event;


@SuppressWarnings("serial")
public class LogEventInterceptor extends MessageInterceptor<LogEvent> implements LogEvent {
	
	@Override
	public void logEvent(Event event) {
		try {
			log.info("#### [eventLogger-client]: logEvent() sending");
			Message message = createMessage("logEvent");
			message.addPart("event", event);
			getProxy().send(message);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
