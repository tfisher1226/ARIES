package org.aries.event;

import org.aries.common.AbstractEvent;


public interface EventHandler<T extends AbstractEvent> extends EventListener<T> {
	
	public String getEventId();
	
	public void initialize();
	
	public void register(String correlationId);
	
	public void cancel(String correlationId);

	public void reset();

	public void close();

}
