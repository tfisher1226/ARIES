package org.aries.process;


public class ProcessStateUtil {

	public static ProcessKey createProcessKey(ProcessState processState) {
		ProcessKey key = createProcessKey(
				processState.getName(),
				processState.getVersion(),
				processState.getCorrelationId());
		return key;
	}

	public static ProcessKey createProcessKey(String name, String version, Object correlationId) {
		ProcessKey key = new ProcessKey(name, version, correlationId);
		return key;
	}
	
}
