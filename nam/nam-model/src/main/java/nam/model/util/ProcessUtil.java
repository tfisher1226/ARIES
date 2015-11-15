package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Cache;
import nam.model.Operation;
import nam.model.Process;
import nam.model.Processes;
import nam.model.Unit;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class ProcessUtil extends BaseUtil {
	
	public static String getKey(Process process) {
		return process.getType();
	}
	
	public static String getLabel(Process process) {
		return process.getName();
	}
	
	public static boolean getLabel(Collection<Process> processList) {
		if (processList == null  || processList.size() == 0)
			return true;
		Iterator<Process> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			if (!isEmpty(process))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Process process) {
		if (process == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Process> processList) {
		if (processList == null  || processList.size() == 0)
			return true;
		Iterator<Process> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			if (!isEmpty(process))
				return false;
		}
		return true;
	}
	
	public static String toString(Process process) {
		if (isEmpty(process))
			return "Process: [uninitialized] "+process.toString();
		String text = process.toString();
		return text;
	}
	
	public static String toString(Collection<Process> processList) {
		if (isEmpty(processList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Process> iterator = processList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Process process = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(process);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Process create() {
		Process process = new Process();
		initialize(process);
		return process;
	}
	
	public static void initialize(Process process) {
		if (process.getStateful() == null)
			process.setStateful(false);
	}
	
	public static boolean validate(Process process) {
		if (process == null)
			return false;
		Validator validator = Validator.getValidator();
		CacheUtil.validate(process.getCacheUnits());
		UnitUtil.validate(process.getDataUnits());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Process> processList) {
		Validator validator = Validator.getValidator();
		Iterator<Process> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			//TODO break or accumulate?
			validate(process);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Process> processList) {
		Collections.sort(processList, createProcessComparator());
	}
	
	public static Collection<Process> sortRecords(Collection<Process> processCollection) {
		List<Process> list = new ArrayList<Process>(processCollection);
		Collections.sort(list, createProcessComparator());
		return list;
	}
	
	public static Comparator<Process> createProcessComparator() {
		return new Comparator<Process>() {
			public int compare(Process process1, Process process2) {
				Object key1 = getKey(process1);
				Object key2 = getKey(process2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Process clone(Process process) {
		if (process == null)
			return null;
		Process clone = create();
		clone.setStateful(ObjectUtil.clone(process.getStateful()));
		//TODO clone.setObject(ObjectUtil.clone(process.getObject()));
		clone.setDataUnits(UnitUtil.clone(process.getDataUnits()));
		clone.setCacheUnits(CacheUtil.clone(process.getCacheUnits()));
		return clone;
	}


	public static boolean isStateful(Process process) {
		if (process == null)
			return false;
		Boolean stateful = process.getStateful();
		if (stateful != null && stateful)
			return true;
		if (process.getCacheUnits().size() > 0)
			return true;
		return false;
	}
	
	public static String getNamespaceAlias(Process process) {
		//String identifier = NameUtil.getLastSegment(service.getDomain(), ".");
		String identifier = NameUtil.getLastSegment(process.getNamespace(), "/");
		return NameUtil.capName(identifier);
	}
	
	public static String getNamespace(Process process) {
		org.eclipse.bpel.model.Process bpelProcess = (org.eclipse.bpel.model.Process) process.getObject();
		if (bpelProcess != null) {
			String namespace = bpelProcess.getTargetNamespace();
			return namespace;
		}
		String namespace = process.getNamespace();
		return namespace;
	}

	public static Operation getOperation(Process process, String operationName) {
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			if (operation.getName().equalsIgnoreCase(operationName))
				return operation;
		}
		return null;
	}
	
	public static void addOperation(Process process, Operation operation) {
		List<Operation> operations = process.getOperations();
		operations.add(operation);
	}

	public static boolean hasEvents(Process process) {
		return hasOutgoingEvents(process) || hasIncomingEvents(process);
	}
	
	public static Set<String> getAllEvents(Process process) {
		Set<String> events = new HashSet<String>();
		events.addAll(getOutgoingEvents(process));
		events.addAll(getIncomingEvents(process));
		return events;
	}
	
	public static boolean hasOutgoingEvents(Process process) {
		return process != null && getOutgoingEvents(process).size() > 0;
	}
	
	public static boolean hasIncomingEvents(Process process) {
		return process != null && getIncomingEvents(process).size() > 0;
	}
	
	public static Set<String> getOutgoingEvents(Process process) {
		Set<String> events = new HashSet<String>();
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			events.addAll(OperationUtil.getOutgoingEvents(operation));
		}
		return events;
	}
	
	public static Set<String> getIncomingEvents(Process process) {
		Set<String> events = new HashSet<String>();
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			events.addAll(OperationUtil.getIncomingEvents(operation));
		}
		return events;
	}
	
	/*
	 * Cache unit related
	 */

	public static List<Cache> getCacheUnits(Processes processes) {
		return getCacheUnits(processes.getProcesses());
	}

	public static List<Cache> getCacheUnits(List<Process> processList) {
		List<Cache> cacheUnits = new ArrayList<Cache>();
		Iterator<Process> iterator = processList.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			cacheUnits.addAll(process.getCacheUnits());
		}
		return cacheUnits;
	}

	public static Cache getCacheUnit(Process process, String cacheName) {
		List<Cache> cacheUnits = process.getCacheUnits();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			if (cacheUnit.getName().equalsIgnoreCase(cacheName)) {
				return cacheUnit;
			}
		}
		return null;
	}

	public static void addCacheUnits(Process process, List<Cache> cacheUnits) {
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			if (!containsCacheUnit(process, cacheUnit)) {
				addCacheUnit(process, cacheUnit);
			}
		}
	}
	
	public static void addCacheUnit(Process process, Cache cacheUnit) {
		if (!containsCacheUnit(process, cacheUnit)) {
			process.getCacheUnits().add(cacheUnit);
		}
	}

	public static boolean containsCacheUnit(Process process, Cache cacheUnit) {
		Iterator<Cache> iterator = process.getCacheUnits().iterator();
		while (iterator.hasNext()) {
			Cache existingCacheUnit = iterator.next();
			if (existingCacheUnit.getName().equalsIgnoreCase(cacheUnit.getName())) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Data unit related
	 */
	
	public static Unit getDataUnit(Process process, String unitName) {
		List<Unit> dataUnits = process.getDataUnits();
		Iterator<Unit> iterator = dataUnits.iterator();
		while (iterator.hasNext()) {
			Unit dataUnit = iterator.next();
			if (dataUnit.getName().equalsIgnoreCase(unitName)) {
				return dataUnit;
			}
		}
		return null;
	}
	
	public static void addDataUnits(Process process, List<Unit> dataUnits) {
		Iterator<Unit> iterator = dataUnits.iterator();
		while (iterator.hasNext()) {
			Unit dataUnit = iterator.next();
			if (!containsDataUnit(process, dataUnit)) {
				addDataUnit(process, dataUnit);
			}
		}
	}
	
	public static void addDataUnit(Process process, Unit dataUnit) {
		if (!containsDataUnit(process, dataUnit)) {
			process.getDataUnits().add(dataUnit);
		}
	}

	public static boolean containsDataUnit(Process process, Unit dataUnit) {
		Iterator<Unit> iterator = process.getDataUnits().iterator();
		while (iterator.hasNext()) {
			Unit existingDataUnit = iterator.next();
			if (existingDataUnit.getName().equalsIgnoreCase(dataUnit.getName())) {
				return true;
			}
		}
		return false;
	}

}
