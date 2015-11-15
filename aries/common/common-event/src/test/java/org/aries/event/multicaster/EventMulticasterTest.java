package org.aries.event.multicaster;

import org.aries.Assert;
import org.aries.common.AbstractEvent;
import org.aries.event.EventListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EventMulticasterTest {

	private TestEventMulticaster eventMulticaster;
	
	private EventListener<Event> eventListener1;
	
	private EventListener<Event> eventListener2;
	
	private boolean eventProcessed1;

	private boolean eventProcessed2;
	
	
	class TestEventMulticaster extends AbstractEventMulticaster {	
	}
	
	@Before
	public void setUp() throws Exception {
		eventMulticaster = new TestEventMulticaster();
		eventListener1 = createEventListener(1);
		eventListener2 = createEventListener(2);
	}
	
	@After
	public void tearDown() throws Exception {
		eventMulticaster = null;
		eventProcessed1 = false;
		eventProcessed2 = false;
	}
	
	@Test
	public void testFireEvent_OneListener() throws Exception {
		eventMulticaster.addEventListener(Event.class, eventListener1);
		Event event = new Event();
		eventMulticaster.fireEvent(event);
		Assert.isTrue(eventProcessed1, "Event was not processed");
	}

	@Test
	public void testFireEvent_TwoListeners() throws Exception {
		eventMulticaster.addEventListener(Event.class, eventListener1);
		eventMulticaster.addEventListener(Event.class, eventListener2);
		Event event = new Event();
		eventMulticaster.fireEvent(event);
		Assert.isTrue(eventProcessed1, "Event was not processed by listener 1");
		Assert.isTrue(eventProcessed2, "Event was not processed by listener 2");
	}
	
	@Test
	public void testFireEvent_RemovedOneListener() throws Exception {
		eventMulticaster.addEventListener(Event.class, eventListener1);
		eventMulticaster.addEventListener(Event.class, eventListener2);
		eventMulticaster.removeEventListener(Event.class, eventListener1);
		Event event = new Event();
		eventMulticaster.fireEvent(event);
		Assert.isFalse(eventProcessed1, "Event was not processed by listener 1");
		Assert.isTrue(eventProcessed2, "Event was not processed by listener 2");
	}

	@Test
	public void testFireEvent_RemovedTwoListeners() throws Exception {
		eventMulticaster.addEventListener(Event.class, eventListener1);
		eventMulticaster.addEventListener(Event.class, eventListener2);
		eventMulticaster.removeEventListener(Event.class, eventListener1);
		eventMulticaster.removeEventListener(Event.class, eventListener2);
		Event event = new Event();
		eventMulticaster.fireEvent(event);
		Assert.isFalse(eventProcessed1, "Event was not processed by listener 1");
		Assert.isFalse(eventProcessed2, "Event was not processed by listener 2");
	}

	protected EventListener<Event> createEventListener(final int index) {
		return new EventListener<Event>() {
			public void process(Event event) {
				switch (index) {
				case 1: 
					eventProcessed1 = true; 
					break;
				case 2: 
					eventProcessed2 = true; 
					break;
				}
			}
		};
	}


	class Event extends AbstractEvent {
		
	}
	
}
