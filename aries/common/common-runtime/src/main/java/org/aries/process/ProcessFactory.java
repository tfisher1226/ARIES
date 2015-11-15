package org.aries.process;

import java.util.Iterator;
import java.util.List;

import org.aries.util.ReflectionUtil;


public class ProcessFactory {

	public static <P extends Process> P createProcessInstance(Class<P> classObject, ProcessState processState) {
		return createProcessInstance(classObject, processState, null);
	}
	
	public static <P extends Process> P createProcessInstance(Class<P> classObject, ProcessState processState, Object correlationId) {
		P instance = (P) ReflectionUtil.newInstance(classObject);
		//if (correlationId == null)
		//	correlationId = UUIDGenerator.generateRandomUUIDString();
		//instance.setCorrelationId(correlationId);
		refreshState(instance, processState);
		return instance;
	}
	
	public static <P extends Process> void refreshState(P instance, ProcessState state) {
		org.aries.common.Map valuesMap = (org.aries.common.Map) state.getValues();
		if (valuesMap == null)
			valuesMap = new org.aries.common.Map();
		List<org.aries.common.MapEntry> mapEntries = valuesMap.getEntries();
		Iterator<org.aries.common.MapEntry> iterator = mapEntries.iterator();
		while (iterator.hasNext()) {
			org.aries.common.MapEntry mapEntry = iterator.next();
			instance.setValue((String) mapEntry.getKey(), mapEntry.getValue());
		}
	}
}
