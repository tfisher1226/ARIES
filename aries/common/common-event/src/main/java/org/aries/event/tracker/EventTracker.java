package org.aries.event.tracker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.aries.util.io.StreamListener;


public class EventTracker extends AppenderSkeleton implements StreamListener {

	//private static Map<String, EventSequence> map = new ConcurrentHashMap<String, EventSequence>();

	private static Map<String, EventSequence> map = new HashMap<String, EventSequence>();

	public static void reset() {
		map.clear();
	}
	
//	protected static class EventList extends ThreadLocal<List<String>> {
//		public List<String> initialValue() {
//			return new ArrayList<String>();
//		}
//
//		public List<String> getContext() { 
//			return super.get();
//		}
//	}

	private PatternLayout layout;
	
	
	public EventTracker() {
		layout = new PatternLayout("%d{MM/dd/yy HH:mm:ss,SSS} %-6p [%t] %c{1}:%L - %m%n");
	}
	
    @Override
    public boolean requiresLayout() {
        return false;
    }

    public void addCompletionToken(String threadGroupName, String completionToken) {
    	EventSequence eventList = getEventList(threadGroupName);
    	if (eventList == null)
    		eventList = createNewEventSequence(threadGroupName);
    	eventList.addCompletionToken(completionToken);
    }
    
    public void removeCompletionToken(String threadGroupName, String completionToken) {
    	EventSequence eventList = getEventList(threadGroupName);
    	eventList.removeCompletionToken(completionToken);
    }

    @Override
	public void append(LoggingEvent event) {	
        String message = event.getRenderedMessage();
    	EventSequence eventList = getEventList();
		eventList.add(layout.format(event));
   		String[] trace = event.getThrowableStrRep();
   		if (trace != null) {
    		for (String text : trace) {
    			if (text.charAt(0) == '\t')
    				text = "\t"+text;
    			eventList.add(text+"\n");
    		}
    	}
		checkForCompletionToken(message);
    }

	public void waitForToken(String threadGroupName) throws Exception {
		waitForToken(threadGroupName, 0);
	}
	
	public void waitForToken(String threadGroupName, long timeout) throws Exception {
		EventSequence eventList = getEventList(threadGroupName);
		if (timeout > 0)
			eventList.getFutureResult().get(500);
		else eventList.getFutureResult().get();
	}

	protected EventSequence createNewEventSequence(String threadGroupName) {
		EventSequence eventList = new EventSequence(threadGroupName);
		map.put(threadGroupName, eventList);
		return eventList;
	}

	public EventSequence getEventList() {
		EventSequence eventList = getEventList(Thread.currentThread());
    	return eventList;
	}
	
	public EventSequence getEventList(Thread thread) {
		EventSequence eventList = getEventList(thread.getThreadGroup());
    	return eventList;
	}

	public EventSequence getEventList(ThreadGroup threadGroup) {
		String threadGroupName = threadGroup.getName();
		EventSequence eventList = getEventList(threadGroupName);
		if (eventList != null)
			return eventList;
		ThreadGroup parentThreadGroup = threadGroup.getParent();
		if (parentThreadGroup != null) {
			eventList = getEventList(parentThreadGroup);
			if (eventList != null)
				return eventList;
		}
		//Not found in group-hierarchy; create new one
		eventList = createNewEventSequence(threadGroupName);
		return eventList;
	}

	public EventSequence getEventList(String threadGroupName) {
		EventSequence eventList = map.get(threadGroupName);
		return eventList;
	}

	@Override
	public void writeLine(String text) {
    	EventSequence eventList = getEventList();
		eventList.add(text+"\n");
		checkForCompletionToken(text);
	}

	//triggers upon first TOKEN found
    protected void checkForCompletionToken(String text) {
    	EventSequence eventList = getEventList();
   		List<String> tokens = eventList.getCompletionTokens();
   		Iterator<String> iterator = tokens.iterator();
   		while (iterator.hasNext()) {
			String token = iterator.next();
			if (token != null && text.contains(token)) {
	        	eventList.getFutureResult().set(true);
	        }
		}
	}

	@Override
    public void close() {
    	//not yet
    }

}
