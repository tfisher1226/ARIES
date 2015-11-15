package org.aries.event.tracker;

import java.util.ArrayList;
import java.util.List;

import org.aries.util.concurrent.FutureResult;


public class EventSequence extends ArrayList<String> {

	private static final long serialVersionUID = 1L;

	
	private String threadGroup;

	private List<String> completionTokens = new ArrayList<String>();

	private FutureResult<Boolean> futureResult = new FutureResult<Boolean>();
	

	public EventSequence(String threadGroup) {
		this.threadGroup = threadGroup;
	}
	
	public String getThreadGroup() {
		return threadGroup;
	}

	public List<String> getCompletionTokens() {
    	return completionTokens;
    }

    public void addCompletionToken(String completionToken) {
    	completionTokens.add(completionToken);
    }

    public void removeCompletionToken(String completionToken) {
    	completionTokens.remove(completionToken);
    }

    public FutureResult<Boolean> getFutureResult() {
		return futureResult;
	}
}
