package org.aries.registry;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aries.Assert;
import org.aries.process.Process;
import org.aries.process.ProcessFactory;
import org.aries.process.ProcessState;
import org.aries.runtime.BeanContext;


//@BypassInterceptors
public class ProcessLocator implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> processCache = new ConcurrentHashMap<String, Object>();

	private Object mutex = new Object();


//	public <P extends Process> P lookup(String transactionId, Class<P> classObject) {
//		P process = lookup(classObject);
//		process.setTransactionId(transactionId);
//		return process;
//	}
	
	public <P extends Process> P lookup(Class<P> classObject) {
    	synchronized (mutex) {
			String name = classObject.getName();
			String version = "0.0.1-SNAPSHOT";
			//Assert.notNull(correlationId, "CorrelationId must be specified");
			P instance = lookup(classObject, name, version, null);
			if (instance != null)
				return instance;
	    	ProcessRegistry processRegistry = BeanContext.get("org.aries.processRegistry");
	   		ProcessState processState = processRegistry.createProcessState(name, version);
	   		instance = ProcessFactory.createProcessInstance(classObject, processState);
	   		addProcessInstance(instance);
	   		return instance;
    	}
	}

	public <P extends Process> P lookup(Class<P> classObject, Object correlationId) {
    	synchronized (mutex) {
			String name = classObject.getName();
			String version = "0.0.1-SNAPSHOT";
			P instance = lookup(classObject, name, version, correlationId);
			return instance;
		}
	}

    public <P extends Process> P lookup(Class<P> classObject, String name, String version, Object correlationId) {
    	synchronized (mutex) {
			Assert.notNull(name, "Name must be specified");
			Assert.notNull(version, "Version must be specified");
			//Assert.notNull(correlationId, "CorrelationId must be specified");
	    	ProcessRegistry processRegistry = BeanContext.get("org.aries.processRegistry");
	   		ProcessState processState = processRegistry.getProcessState(name, version, correlationId);
	   		
	   		//First check cache for any previously existing process instance
	   		P instance = getProcessInstance(classObject, correlationId);
	   		
	   		//create fresh istance? or throw exception?
	   		if (instance == null) {
	   			instance = ProcessFactory.createProcessInstance(classObject, processState);
	   			//if (correlationId != null)
	   			//	instance.setCorrelationId(correlationId);
	   			addProcessInstance(instance);
	   		}
	   		
	   		return instance;
    	}
    }

//	//assuming processStates exist
//	protected <P extends Process> P getProcessInstance(List<ProcessState> processStates, Class<P> classObject) {
//    	synchronized (mutex) {
//			ProcessState processState = new ProcessState();
//			P processInstance = getProcessInstance(processState, classObject);
//			return processInstance;
//		}
//	}

	public <P extends Process> void addProcessInstance(P instance) {
    	synchronized (mutex) {
    		Assert.notNull(instance, "ProcessInstance must be specified");
    		//Assert.notNull(instance.getCorrelationId(), "CorrelationId must be specified");
    		String key = createProcessKey(instance.getClass(), instance.getName());
			processCache.put(key, instance);
		}
	}

	public <P extends Process> P getProcessInstance(Class<P> classObject, Object correlationId) {
    	synchronized (mutex) {
    		Assert.notNull(classObject, "ProcessClass must be specified");
    		Assert.notNull(correlationId, "CorrelationId must be specified");
    		String key = createProcessKey(classObject, correlationId);
			@SuppressWarnings("unchecked") P instance = (P) processCache.get(key);
			return instance;
		}
	}
	
	protected String createProcessKey(Class<?> classObject, Object correlationId) {
		return classObject.getName() + "/" + correlationId;
	}
	
	//assuming processStates exist
	protected <P extends Process> P getProcessInstance(Class<P> classObject, List<ProcessState> processStates, String correlationId) {
    	synchronized (mutex) {
			ProcessState processState = selectProcessState(processStates, correlationId);
			Assert.notNull(processState, "ProcessState not found correlationId: "+correlationId);
			P instance = getProcessInstance(classObject, correlationId);
			return instance;
		}
	}
	
	//assuming processState exists
	protected ProcessState selectProcessState(List<ProcessState> processStates, String correlationId) {
		//if (processBalancer != null)
		//	processState = processBalancer.selectProcess(processStates);
		Iterator<ProcessState> iterator = processStates.iterator();
		while (iterator.hasNext()) {
			ProcessState processState = (ProcessState) iterator.next();
			if (processState.getCorrelationId().equals(correlationId))
				return processState; 
		}
		return null;
	}
	

}

