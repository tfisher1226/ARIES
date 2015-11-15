package common.tx.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.util.Callback;


public abstract class BaseProcessor {
	
	protected Log log = LogFactory.getLog(getClass());
	
    private final Map callbackMap = new HashMap();
    

    protected void register(String messageID, Callback callback) {
        synchronized (callbackMap) {
            callbackMap.put(messageID, callback);
        }
    }
    
    /**
     * Remove the callback for the specified message ID.
     */
    public void removeCallback(final String messageID) {
        synchronized (callbackMap) {
            callbackMap.remove(messageID);
        }
    }

    /**
     * Handle the callbacks for the specified addressing context.
     */
    protected void handleCallbacks(CallbackExecutor executor, String[] ids) {
        Callback[] callbacks = getCallbacks(ids);
        if (callbacks != null) {
            boolean executed = false;
            int numCallbacks = callbacks.length;
            for (int count = 0; count < numCallbacks; count++) {
                Callback callback = callbacks[count];
                if (callback != null) {
                    executed = true;
                    try {
                        executor.execute(callback);
                        callback.setTriggered();
                    } catch (final Throwable e) {
                    	log.error("Error", e);
                        callback.setFailed();
                    }
                }
            }
            if (!executed) {
                executor.executeUnknownIds(ids);
            }
        }
    }
    
    /**
     * Get the callbacks associated with the message ids.
     */
    private Callback[] getCallbacks(final String[] ids) {
        if (ids == null)
            return null;
        int numIDs = ids.length;
        Callback[] callbacks = new Callback[numIDs];
        synchronized (callbackMap) {
            for (int count = 0; count < numIDs; count++) {
                callbacks[count] = (Callback) callbackMap.get(ids[count]);
            }
        }
        return callbacks;
    }
    
    /**
     * Interface for executing a specific callback.
     */
    protected static interface CallbackExecutor {

    	public void execute(Callback callback);

    	public void executeUnknownIds(String[] ids);
    }
    
    /**
     * Adapter for the callback executor.
     */
    public abstract static class CallbackExecutorAdapter implements CallbackExecutor {

    	private static Log log = LogFactory.getLog(CallbackExecutorAdapter.class);
    	
    	public void executeUnknownIds(final String[] ids) {
            log.debug("Received a response for non existent message IDs: "+toString(ids));
        }
        
        /**
         * Convert an array of IDs to a comma separated string representation.
         */
        private String toString(final String[] ids) {
            int numIDs = (ids == null ? 0 : ids.length);
            if (numIDs == 0) {
                return "";
            } else if (numIDs == 1) {
                return ids[0];
            } else {
                StringBuffer buffer = new StringBuffer(ids[0]);
                for (int count = 1; count < numIDs; count++) {
                    buffer.append(", ");
                    buffer.append(ids[count]);
                }
                return buffer.toString();
            }
        }
    }

}
