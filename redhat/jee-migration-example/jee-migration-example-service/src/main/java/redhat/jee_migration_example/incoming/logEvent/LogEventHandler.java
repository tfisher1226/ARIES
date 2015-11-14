package redhat.jee_migration_example.incoming.logEvent;

import org.aries.tx.Transactional;

import redhat.jee_migration_example.Event;


public interface LogEventHandler extends Transactional {
	
	public void logEvent(Event event);
	
}
