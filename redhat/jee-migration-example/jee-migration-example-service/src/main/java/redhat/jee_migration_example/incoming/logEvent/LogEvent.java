package redhat.jee_migration_example.incoming.logEvent;

import redhat.jee_migration_example.Event;


public interface LogEvent {
	
	public String ID = "redhat.jee-migration-example.logEvent";
	
	public void logEvent(Event event);
	
}
