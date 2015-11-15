package org.aries.util;


public class IdGenerator {

	private static int _number = 0;

	//TODO get a better one of these from JGroups source
	public static synchronized String createId() {
		long requestId = System.currentTimeMillis() + _number++;
		return Long.toString(requestId);
	}

	//TODO get a better one of these from JGroups source
	public static synchronized Long createRequestId() {
		long requestId = System.currentTimeMillis() + _number++;
		return requestId;
	}
	
	//TODO get a better one of these from JGroups source
	public static synchronized Long createRequestId(Object data) {
		long requestId = _number++;
		if (data != null)
			requestId += data.hashCode();
		return requestId;
	}

}
