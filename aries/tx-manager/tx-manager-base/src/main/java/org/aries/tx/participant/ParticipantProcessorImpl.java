package org.aries.tx.participant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.tx.coordinator.CoordinatorClient;
import org.aries.tx.util.ActivatedObjectProcessor;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Header;
import common.tx.model.Notification;


public class ParticipantProcessorImpl extends ParticipantProcessor {

	private static Log log = LogFactory.getLog(ParticipantProcessor.class);

	private ActivatedObjectProcessor activatedObjectProcessor = new ActivatedObjectProcessor();

	
	public void activateParticipant(ParticipantEngine participantEngine, String identifier) {
		activatedObjectProcessor.activateObject(participantEngine, identifier);
	}

	public void deactivateParticipant(ParticipantEngine participantEngine) {
		activatedObjectProcessor.deactivateObject(participantEngine);
	}

	public boolean isActive(String identifier) {
		// if there is an entry in the table then it is active or completed and pending delete
		return (activatedObjectProcessor.getObject(identifier) != null);
	}

	private ParticipantEngine getParticipant(String participantId) {
		//String identifier = (instanceIdentifier != null ? instanceIdentifier.getInstanceIdentifier() : null);
		Object object = activatedObjectProcessor.getObject(participantId);
		if (object == null)
			System.out.println();
		return (ParticipantEngine) object;
	}

	public void commit(Header header, Notification notification) {
		//InstanceIdentifier instanceIdentifier = (InstanceIdentifier) header.getInstanceIdentifier();
		String participantId = header.getParticipantId();
		String coordinatorId = header.getCoordinatorId();
		Assert.notNull(participantId, "ParticipantId must be specified");
		Assert.notNull(coordinatorId, "CoordinatorId must be specified");

//		/* ensure the AT participant recovery manager is running */
//		XTSATRecoveryManager recoveryManager = XTSATRecoveryManager.getRecoveryManager();
//		if (recoveryManager == null) {
//			// log warning and drop this message -- it will be resent
//			log.warn("Commit request dropped pending participant recovery manager initialization for participant: "+instanceIdentifier.toString());
//			return;
//		}

		ParticipantEngine participant = getParticipant(participantId);
		if (participant != null) {
			try {
				participant.commit(notification);
			} catch (Throwable e) {
				log.error("Error: "+e);
			}
//		} else if (!recoveryManager.isParticipantRecoveryStarted()) {
//			log.warn("Commit request dropped pending WS-AT participant recovery manager scan for unknown participant: "+instanceIdentifier.toString());
//		} else if (recoveryManager.findParticipantRecoveryRecord(instanceIdentifier.getInstanceIdentifier()) != null) {
//			log.warn("Commit request dropped pending registration of application-specific recovery module for WS-AT participant: "+instanceIdentifier.toString());
		} else {
			log.warn("Unknown participant: "+participantId);
			sendCommitted(participantId, coordinatorId);
		}
	}

	public void prepare(Header header, Notification notification) {
		//InstanceIdentifier instanceIdentifier = (InstanceIdentifier) header.getInstanceIdentifier();
		String participantId = header.getParticipantId();
		String coordinatorId = header.getCoordinatorId();
		Assert.notNull(participantId, "ParticipantId must be specified");
		Assert.notNull(coordinatorId, "CoordinatorId must be specified");

		ParticipantEngineImpl participant = (ParticipantEngineImpl) getParticipant(participantId);
		if (participant != null) {
			try {
				participant.setCoordinatorId(coordinatorId);
				participant.prepare(notification);
			} catch (Throwable e) {
				log.error("Error", e);
			}
			
		} else {
			log.warn("Unknown participant: "+participantId);
			sendAborted(participantId, coordinatorId);
		}
	}

	public void rollback(Header header, Notification notification) {
		//InstanceIdentifier instanceIdentifier = (InstanceIdentifier) header.getInstanceIdentifier();
		String participantId = header.getParticipantId();
		String coordinatorId = header.getCoordinatorId();
		Assert.notNull(participantId, "ParticipantId must be specified");
		Assert.notNull(coordinatorId, "CoordinatorId must be specified");
		
//		/* ensure the AT participant recovery manager is running */
//		XTSATRecoveryManager recoveryManager = XTSATRecoveryManager.getRecoveryManager();
//		if (recoveryManager == null) {
//			// log warning and drop this message -- it will be resent
//			log.warn("Rollback request dropped pending WS-AT participant recovery manager initialization for participant: "+instanceIdentifier.toString());
//		}

		ParticipantEngineImpl participant = (ParticipantEngineImpl) getParticipant(participantId);
		if (participant != null) {
			try {
				participant.setCoordinatorId(coordinatorId);
				participant.rollback(notification);
			} catch (Throwable e) {
				log.error("Error", e);
			}
//		} else if (!recoveryManager.isParticipantRecoveryStarted()) {
//			log.warn("Rollback request dropped pending participant recovery scan for unknown participant: "+instanceIdentifier.toString());
//		} else if (recoveryManager.findParticipantRecoveryRecord(instanceIdentifier.getInstanceIdentifier()) != null) {
//			log.warn("Rollback request dropped pending registration of application-specific recovery module for participant: "+instanceIdentifier.toString());
		} else {
			log.warn("Unknown participant: "+participantId);
			sendAborted(participantId, coordinatorId);
		}
	}

	public void fault(Header header, Fault fault) {
		//InstanceIdentifier instanceIdentifier = (InstanceIdentifier) header.getInstanceIdentifier();
		String participantId = header.getParticipantId();
		String coordinatorId = header.getCoordinatorId();
		Assert.notNull(participantId, "ParticipantId must be specified");
		Assert.notNull(coordinatorId, "CoordinatorId must be specified");

		ParticipantEngine participant = getParticipant(participantId);
		if (participant != null) {
			try {
				participant.fault(fault);
			} catch (Throwable e) {
				log.error("Error", e);
			}
		} else {
			log.error("Unknown participant: "+participantId);
		}
	}

	private void sendCommitted(String participantId, String coordinatorId) {
		// KEV add check for recovery
		try {
			CoordinatorClient.getInstance().sendCommitted(null, participantId, coordinatorId);
		} catch (Throwable e) {
			log.debug("Error", e);
		}
	}

	private void sendAborted(String participantId, String coordinatorId) {
		// KEV add check for recovery
		try {
			CoordinatorClient.getInstance().sendAborted(null, participantId, coordinatorId);
		} catch (Throwable e) {
			log.debug("Error", e);
		}
	}

}
