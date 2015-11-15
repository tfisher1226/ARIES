package org.aries.util;


public class IdGenerator {

	private static int _number = 0;

	public static synchronized String createId() {
		long requestId = System.currentTimeMillis() + _number++;
		return Long.toString(requestId);
	}

	public static synchronized Long createRequestId() {
		long requestId = System.currentTimeMillis() + _number++;
		return requestId;
	}
	
	public static synchronized Long createRequestId(Object data) {
		long requestId = data.hashCode() + _number++;
		return requestId;
	}

}
