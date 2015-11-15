package org.aries.util.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.aries.event.tracker.EventSequence;
import org.aries.event.tracker.EventTracker;
import org.aries.event.tracker.EventUtil;
import org.junit.After;
import org.junit.Before;


public abstract class AbstractEventTrackedIT extends AbstractMultiThreadedIT {

	protected EventTracker eventTracker;


	@Before
	public void setUp() throws Exception {
		super.setUp();
		EventTracker.reset();
		eventTracker = new EventTracker();
		Logger.getRootLogger().addAppender(eventTracker);
	}

	@After
	public void tearDown() throws Exception {
		Logger.getRootLogger().removeAppender(eventTracker);
		eventTracker = null;
		EventTracker.reset();
		super.tearDown();
	}

	protected EventTracker getEventTracker() {
		return eventTracker;
	}

	protected void stopProcesses() throws Exception {
	}

	protected void verifyResults(String testName, String serverKey, String clientKey) throws Exception {
		String resourcePath = getTestResourcePath(testName);
		String actualServiceEventsFile = resourcePath + "service-events-actual.txt";
		String actualClientEventsFile = resourcePath + "client-events-actual.txt";
		String expectedServiceEventsFile = resourcePath + "service-events-expected.txt";
		String expectedClientEventsFile = resourcePath + "client-events-expected.txt";
		String missingServiceEventsFile = resourcePath + "service-events-missing.txt";
		String missingClientEventsFile = resourcePath + "client-events-missing.txt";
		String unexpectedServiceEventsFile = resourcePath + "service-events-unexpected.txt";
		String unexpectedClientEventsFile = resourcePath + "client-events-unexpected.txt";
		EventSequence serviceEvents = eventTracker.getEventList(serverKey);
		EventSequence clientEvents = eventTracker.getEventList(clientKey);
		EventUtil.writeOutEventSequence(serviceEvents, actualServiceEventsFile);
		EventUtil.writeOutEventSequence(clientEvents, actualClientEventsFile);
		compareAndReportResults(actualServiceEventsFile, expectedServiceEventsFile, missingServiceEventsFile, unexpectedServiceEventsFile);
		compareAndReportResults(actualClientEventsFile, expectedClientEventsFile, missingClientEventsFile, unexpectedClientEventsFile);
	}

	protected void compareAndReportResults(String actualEventsFile, String expectedEventsFile, String missingEventsFile, String unexpectedEventsFile) throws Exception {
		List<String> actualEvents = EventUtil.readInEventSequence(actualEventsFile);
		List<String> expectedEvents = EventUtil.readInEventSequence(expectedEventsFile);
		List<String> missingEvents = compareResults(expectedEvents, actualEvents);
		List<String> unexpectedEvents = compareResults(actualEvents, expectedEvents);
		EventUtil.writeOutEventSequence(missingEvents, missingEventsFile);
		EventUtil.writeOutEventSequence(unexpectedEvents, unexpectedEventsFile);
	}

	protected List<String> compareResults(List<String> actualEvents, List<String> expectedEvents) {
		List<String> events = new ArrayList<String>();
		Iterator<String> outerIterator = actualEvents.iterator();
		while (outerIterator.hasNext()) {
			String outerString = outerIterator.next();
			int outerIndexOf = outerString.indexOf("]");
			if (outerIndexOf != -1)
				outerString = outerString.substring(outerIndexOf+2);
			boolean found = false;

			Iterator<String> innerIterator = expectedEvents.iterator();
			while (innerIterator.hasNext()) {
				String innerString = innerIterator.next();
				int innerIndexOf2 = innerString.indexOf("]");
				if (innerIndexOf2 != -1)
					innerString = innerString.substring(innerIndexOf2+2);
				if (innerString.equals(outerString)) {
					found = true;
					break;
				}
			}
			if (!found)
				events.add(outerString);
		}
		return events;
	}

}
