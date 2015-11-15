package org.aries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Allows for management of parts of runtime lifecycle by allowing Runnables
 * (classes that implement {@link Runnable}) to be executed (or scheduled at) 
 * at various steps in the runtime lifecycle of the application.
 */
public class RuntimeManager {
	
    private static final Log log = LogFactory.getLog(RuntimeManager.class);

    private RuntimeContext runtimeContext;
    
    private List<Runnable> initActions;

    private List<Runnable> startActions;

    private List<Runnable> stopActions;

    
    public RuntimeManager() {
    	//nothing for now
    }

    public void setRuntimeContext(RuntimeContext runtimeContext) {
        this.runtimeContext = runtimeContext;
    }

    public void setInitActions(List<Runnable> initActions) {
        this.initActions = new ArrayList<Runnable>(initActions);
    }

    public void setStartActions(List<Runnable> startActions) {
        this.startActions = new ArrayList<Runnable>(startActions);
    }

    public void setStopActions(List<Runnable> stopActions) {
        this.stopActions = new ArrayList<Runnable>(stopActions);
    }

    
    //SEAM @Create
    @PostConstruct
    public void initialize() throws Exception {
        if (initActions != null) {
        	log.debug("Initialization actions starting...");
            execute(initActions);
            log.debug("Initialization actions complete");
           //addServerEventSubscriber();
        }
    }
    
    /*
     * This is for running under Tomcat only:
    protected void addServerEventSubscriber() {
    	TomcatEventQueue.getInstance().addEventSubscriber(new TomcatEventSubscriber() {
			public void serverInitialized() throws Exception {
				start();
			}
		});
    }
     */

    
    public void start() throws Exception {
        if (startActions != null) {
	        ServletContext servletContext = runtimeContext.getServletContext();
	        //SEAM ServletLifecycle.beginApplication(servletContext);
	        //SEAM Lifecycle.beginApplication(Lifecycle.getApplication());
	        //SEAM Lifecycle.beginCall();
	        
	        log.debug("Startup actions starting...");
			execute(startActions);
	        log.debug("Startup actions complete");
	    }
    }
    
    
    //SEAM @Destroy
    @PreDestroy
    public void stop() throws Exception {
        if (stopActions != null) {
	        log.debug("Shutdown actions starting...");
	        execute(stopActions);
	        log.debug("Shutdown actions complete");
	    }
    }

    
    protected void execute(List<Runnable> actions) throws Exception {
        for (Runnable action : actions) {
            execute(action);
        }
    }
    
    protected void execute(Runnable action) throws Exception {
    	Assert.notNull(action, "Action must be specified");
    	try {
            log.debug(String.format("Executing action %s", action));
            
			//Registry registry = LocateRegistry.getRegistry("localhost", 9350);
			//SimpleRMIService runnable = (SimpleRMIService) registry.lookup("initializePersonCacheProxy");
			//runnable.run();
            action.run();
            
            log.debug(String.format("Completed action %s", action));
            
    	} catch (Throwable e) {
            log.debug(String.format("Aborted action %s: %s", action, e));
            throw getRootCause(e);
            
    	} finally {
    		//nothing for now
    	}
    }
    
    /*
     * Returns the first non-RuntimeException in the cause chain.
     * We assume here that the main causing Exception that is at the head 
     * of the cause chain is simply the first non wrapping-exception which 
     * in our case it almost always the first non-RuntimeException (i.e. 
     * we use try to always use only RuntimeException as wrapping-exceptions). 
     * This method won't return a RuntimeException unless only RuntimeExceptions 
     * exist on the chain, if true then the specified itself is returned.
     * 
     * Can we assume if the Exception is not an instance of RuntimeException
     * then it is an instance of Exception?
     */
    protected Exception getRootCause(Throwable e) {
    	List<?> throwableList = ExceptionUtils.getThrowableList(e);
    	Iterator<?> iterator = throwableList.iterator();
    	while (iterator.hasNext()) {
    		Throwable throwable = (Throwable) iterator.next();
			if (throwable instanceof RuntimeException == false && throwable instanceof Exception)
				return (Exception) throwable;
		}
    	if (e instanceof Exception == false)
    		return new Exception(e);
    	return (Exception) e;
    }

}
