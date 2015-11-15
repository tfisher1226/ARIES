package common.tx.service.completion;

import org.aries.tx.util.Callback;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Header;
import common.tx.model.Notification;
import common.tx.service.BaseNotificationProcessor;


public class CompletionInitiatorProcessorImpl extends BaseNotificationProcessor implements CompletionInitiatorProcessor {

    private static CompletionInitiatorProcessor INSTANCE;

    public static synchronized CompletionInitiatorProcessor getProcessor() {
    	//System.out.println(">>>"+CompletionInitiatorProcessorImpl.class.hashCode());
        return INSTANCE;
    }

    public static synchronized CompletionInitiatorProcessor setInstance(CompletionInitiatorProcessor processor) {
    	//System.out.println(">>>"+CompletionInitiatorProcessorImpl.class.hashCode());
    	CompletionInitiatorProcessor origProcessor = INSTANCE;
        INSTANCE = processor;
        return origProcessor;
    }

    
    public void handleAborted(final Header header, final Notification notification) {
        handleCallbacks(new CallbackExecutorAdapter() {
            public void execute(Callback callback) {
                ((CompletionInitiatorCallback) callback).aborted(header, notification);
            }
        }, getIDs(header));
    }

    /**
     * Handle a committed response.
     * @param committed The committed notification.
     * @param map The addressing context.
     * @param arjunaContext The arjuna context.
     */
    public void handleCommitted(final Header header, final Notification notification) {
        handleCallbacks(new CallbackExecutorAdapter() {
            public void execute(final Callback callback) {
                ((CompletionInitiatorCallback) callback).committed(header, notification);
            }
        }, getIDs(header));
    }

    /**
     * Handle a fault response.
     */
    public void handleFault(final Header header, final Fault fault) {
        handleCallbacks(new CallbackExecutorAdapter() {
            public void execute(Callback callback) {
                ((CompletionInitiatorCallback) callback).fault(header, fault);
            }
        }, getIDs(header));
    }

    /**
     * Register a callback for the specific instance identifier.
     * @param instanceIdentifier The instance identifier.
     * @param callback The callback for the response.
     */
    public void registerCallback(String instanceIdentifier, CompletionInitiatorCallback callback) {
        register(instanceIdentifier, callback);
    }
    
}
