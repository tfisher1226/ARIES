package common.tx.service.coordinator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.tx.participant.client.ParticipantClient;
import org.aries.tx.util.ActivatedObjectProcessor;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.InstanceIdentifier;
import common.tx.model.Header;
import common.tx.model.Notification;


public class CoordinatorProcessorImpl extends CoordinatorProcessor {
	
	private static Log log = LogFactory.getLog(CoordinatorProcessor.class);

    private final ActivatedObjectProcessor activatedObjectProcessor = new ActivatedObjectProcessor();

    
    public void activateCoordinator(CoordinatorEngine coordinator, String coordinatorId) {
        activatedObjectProcessor.activateObject(coordinator, coordinatorId);
    }

    public void deactivateCoordinator(CoordinatorEngine coordinator) {
        activatedObjectProcessor.deactivateObject(coordinator);
    }

    public CoordinatorEngine getCoordinator(String coordinatorId) {
        return (CoordinatorEngine) activatedObjectProcessor.getObject(coordinatorId);
    }
    
    private CoordinatorEngine getCoordinator(InstanceIdentifier instanceIdentifier) {
        final String coordinatorId = (instanceIdentifier != null ? instanceIdentifier.getInstanceIdentifier() : null);
        return getCoordinator(coordinatorId);
    }


    public void prepared(Header header, Notification notification) {
    	String coordinatorId = header.getCoordinatorId();
        CoordinatorEngine coordinator = getCoordinator(coordinatorId);
        if (coordinator != null) {
	        try {
	            coordinator.prepared(notification);
	        } catch (Throwable e) {
	        	log.error("Error", e);
	        }

//        } else if (areRecoveryLogEntriesAccountedFor()) {
//            log.warn("Prepared called on unknown coordinator: "+instanceIdentifier.toString());
//
//            String coordinatorId = instanceIdentifier.getInstanceIdentifier();
//            if (coordinatorId != null && coordinatorId.length() > 0 && coordinatorId.charAt(0) == 'D') {
//                sendRollback(instanceIdentifier);
//            } else {
//                sendUnknownTransaction(instanceIdentifier);
//            }
            
        } else {
            // there may be a participant stub waiting to be recovered from the log so drop the
            // message, forcing the caller to retry
            log.warn("Ignoring prepared called on unidentified coordinator until recovery pass is complete: "+coordinatorId);
        }
    }

    public void readOnly(Header header, Notification notification) {
    	String coordinatorId = header.getCoordinatorId();
        CoordinatorEngine coordinator = getCoordinator(coordinatorId);
        Assert.notNull(coordinator, "Unknown coordinator: "+coordinatorId);
        try {
            coordinator.readOnly(notification);
        } catch (Throwable e) {
        	log.error("Error", e);
        }
    }

    public void committed(Header header, Notification notification) {
    	String coordinatorId = header.getCoordinatorId();
        CoordinatorEngine coordinator = getCoordinator(coordinatorId);
        Assert.notNull(coordinator, "Unknown coordinator: "+coordinatorId);
        try {
            coordinator.committed(notification);
        } catch (Throwable e) {
        	log.error("Error", e);
        }
    }

    public void aborted(Header header, Notification notification) {
    	String coordinatorId = header.getCoordinatorId();
        CoordinatorEngine coordinator = getCoordinator(coordinatorId);
        Assert.notNull(coordinator, "Unknown coordinator: "+coordinatorId);
        try {
            coordinator.aborted(notification);
        } catch (Throwable e) {
        	log.error("Error", e);
        }
    }

    public void fault(Header header, Fault fault) {
    	String coordinatorId = header.getCoordinatorId();
        CoordinatorEngine coordinator = getCoordinator(coordinatorId);
        Assert.notNull(coordinator, "Unknown coordinator: "+coordinatorId);
        try {
            coordinator.fault(fault);
        } catch (Throwable e) {
        	log.error("Error", e);
        }
    }

    /**
     * Send an unknown transaction fault.
     */
    private void sendUnknownTransaction(InstanceIdentifier instanceIdentifier) {
        String transactionId = instanceIdentifier.getInstanceIdentifier();
        // KEV add check for recovery
        try {
            Fault fault = new Fault();
            fault.setFaultcode(CoordinationConstants.WSAT_ERROR_CODE_UNKNOWN_TRANSACTION_QNAME);
            fault.setFaultstring("Unknown Transaction");
			ParticipantClient.getInstance().sendFault(transactionId, transactionId, null, fault);
        } catch (Throwable e) {
        	log.error("Error", e);
        }
    }

    private void sendRollback(InstanceIdentifier instanceIdentifier) {
    	String transactionId = instanceIdentifier.getInstanceIdentifier();
        // KEV add check for recovery
        try {
            ParticipantClient.getInstance().sendRollback(transactionId, null, transactionId, null);
        } catch (Throwable e) {
        	log.error("Error", e);
        }
    }

//    /**
//     * Tests if there may be unknown coordinator entries in the recovery log.
//     */
//    private static boolean areRecoveryLogEntriesAccountedFor() {
//        return XTSATRecoveryManagerImpl.getRecoveryManager().isCoordinatorRecoveryStarted();
//    }
    
}
