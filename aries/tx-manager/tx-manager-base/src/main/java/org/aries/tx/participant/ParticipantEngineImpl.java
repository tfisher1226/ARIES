package org.aries.tx.participant;

import java.util.TimerTask;

import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.Durable2PCParticipant;
import org.aries.tx.Participant;
import org.aries.tx.State;
import org.aries.tx.coordinator.CoordinatorClient;
import org.aries.tx.util.TransportTimer;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.exception.SystemException;
import common.tx.model.Notification;
import common.tx.vote.Aborted;
import common.tx.vote.Prepared;
import common.tx.vote.ReadOnly;
import common.tx.vote.Vote;


public class ParticipantEngineImpl implements ParticipantEngine {

	private static Log log = LogFactory.getLog(ParticipantEngine.class);

	private Participant participant;

	private String participantId;

	private String coordinatorId;

	private W3CEndpointReference coordinatorReference;

	private State state;

	private TimerTask timerTask;

	/**
	 * the time which will elapse before the next message resend. this is incrementally increased
	 * until it reaches RESEND_PERIOD_MAX
	 */
	private long resendPeriod;

	/**
	 * the initial period we will allow between resends.
	 */
	private long initialResendPeriod;

	/**
	 * the maximum period we will allow between resends. n.b. the coordinator uses the value returned
	 * by getTransportTimeout as the limit for how long it waits for a response. however, we can still
	 * employ a max resend period in excess of this value. if a message comes in after the coordinator
	 * has given up it will catch it on the next retry.
	 */
	private long maxResendPeriod;

	/**
	 * true if this participant has been recovered otherwise false
	 */
	private boolean recovered;

	/**
	 * true if this participant's recovery details have been logged to disk otherwise false
	 */
	private boolean persisted;

	private Object mutex = new Object();


	public ParticipantEngineImpl(Participant participant, String participantId, W3CEndpointReference coordinator){
		this(participant, participantId, State.STATE_ACTIVE, coordinator, false);
	}

	public ParticipantEngineImpl(Participant participant, String participantId, State state, W3CEndpointReference coordinatorReference, boolean recovered) {
		this.participant = participant;
		this.participantId = participantId;
		this.coordinatorId = participant.getCoordinatorId();
		this.state = state;
		this.coordinatorReference = coordinatorReference;
		this.recovered = recovered;
		this.persisted = recovered;
		this.initialResendPeriod = TransportTimer.getTransportPeriod();
		this.maxResendPeriod = TransportTimer.getMaximumTransportPeriod();
		this.resendPeriod = this.initialResendPeriod;
	}

	public String getId() {
		return participantId;
	}

	public String getCoordinatorId() {
		return coordinatorId;
	}

	public void setCoordinatorId(String coordinatorId) {
		this.coordinatorId = coordinatorId;
	}
	
	public W3CEndpointReference getCoordinatorEPR() {
		return coordinatorReference;
	}

	public boolean isPersisted() {
		return persisted;
	}

	public boolean isRecovered() {
		return recovered;
	}


	/*
	 * None -> None (send committed)
	 * Active -> Aborting (do nothing)
	 * Preparing -> Aborting (do nothing)
	 * PreparedSuccess -> Committing (initiate commit)
	 * Committing -> Committing (do nothing)
	 * Aborting -> Aborting (do nothing)
	 */
	public void commit(Notification commit) {
		State current;
		synchronized (mutex) {
			current = state;
			if (current == State.STATE_PREPARED_SUCCESS) {
				state = State.STATE_COMMITTING;
				if (timerTask != null) {
					timerTask.cancel();
				}
			} else if ((current == State.STATE_ACTIVE) || (current == State.STATE_PREPARING)) {
				state = State.STATE_ABORTING;
			}
		}

		if (current == State.STATE_PREPARED_SUCCESS) {
			executeCommit();
		} else if (current == null) {
			sendCommitted();
		}
	}

	/*
	 * None -> None (send aborted)
	 * Active -> Preparing (execute prepare)
	 * Preparing -> Preparing (do nothing)
	 * PreparedSuccess -> PreparedSuccess (resend prepared)
	 * Committing -> Committing (ignore)
	 * Aborting -> Aborting (send aborted and forget)
	 */
	public void prepare(Notification prepare) {
		State current;
		synchronized (mutex) {
			current = state;
			if (current == State.STATE_ACTIVE) {
				state = State.STATE_PREPARING;
			}
		}

		if (current == State.STATE_ACTIVE) {
			executePrepare();
		} else if (current == State.STATE_PREPARED_SUCCESS) {
			sendPrepared();
		} else if ((current == State.STATE_ABORTING) || (current == null)) {
			sendAborted();
			forget();
		}
	}

	/*
	 * None -> None (send aborted)
	 * Active -> Aborting (execute rollback, send aborted and forget)
	 * Preparing -> Aborting (execute rollback, send aborted and forget)
	 * PreparedSuccess -> Aborting (execute rollback, send aborted and forget)
	 * Committing -> Committing (ignore)
	 * Aborting -> Aborting (send aborted and forget)
	 */
	public void rollback(Notification rollback) {
		State current;
		synchronized (mutex) {
			current = state;
			if ((current == State.STATE_ACTIVE) || 
					(current == State.STATE_PREPARING) ||
					(current == State.STATE_PREPARED_SUCCESS)) {
				state = State.STATE_ABORTING;
			}
		}

		if (current != State.STATE_COMMITTING) {
			if ((current == State.STATE_ACTIVE) || 
					(current == State.STATE_PREPARING) ||
					(current == State.STATE_PREPARED_SUCCESS)) {
				// n.b. if state is PREPARING the participant may still be in the middle
				// of prepare or may even be told to prepare after this is called. according
				// to the spec that is not our lookout. however, rollback should only get
				// called once here.
				if (!executeRollback()) {
					return;
				}
			}

			// if the participant managed to persist the log record then we should try
			// to delete it. note that persisted can only be set to true by the PREPARING
			// thread. if it detects a transtiion to ABORTING while it is doing the log write
			// it will clear up itself.

			if (persisted && participant instanceof Durable2PCParticipant) {
				// if we cannot delete the participant we effectively drop the rollback message
				// here in the hope that we have better luck next time..
				//				if (!XTSATRecoveryManager.getRecoveryManager().deleteParticipantRecoveryRecord(participantId)) {
				//					// hmm, could not delete entry -- leave it so we can maybe retry later
				//					log.warn("Could not delete recovery record for participant: "+participantId);
				//					return;
				//				}
			}

			sendAborted();
			if (current != null) {
				forget();
			}
		}
	}

	/**
	 * None -> None
	 * Active -> Aborting (execute rollback, send aborted and forget)
	 * Preparing -> Aborting (execute rollback, send aborted and forget)
	 * PreparedSuccess -> PreparedSuccess
	 * Committing -> Committing
	 * Aborting -> Aborting
	 */
	public void earlyRollback() {
		rollbackDecision();
	}

	/**
	 * None -> None
	 * Active -> None (send ReadOnly)
	 * Preparing -> None (send ReadOnly)
	 * PreparedSuccess -> PreparedSuccess
	 * Committing -> Committing
	 * Aborting -> Aborting
	 */
	public void earlyReadonly() {
		readOnlyDecision();
	}

	/**
	 * None -> None
	 * Active -> Active
	 * Preparing -> Preparing
	 * Committing -> Committing
	 * PreparedSuccess -> PreparedSuccess (resend Prepared)
	 * Aborting -> Aborting
	 */
	public void recovery() {
		State current;
		synchronized (mutex) {
			current = state;
		}
		if (current == State.STATE_PREPARED_SUCCESS) {
			sendPrepared();
		}
	}

	public void fault(Fault fault) {
		log.debug("Unexpected fault for participant: "+participantId);
		QName faultCode = fault.getFaultcode();
		String faultString = fault.getFaultstring();
		
		if (CoordinationConstants.WSCOOR_ERROR_CODE_INVALID_STATE_QNAME.equals(faultCode) ||
			CoordinationConstants.WSAT_ERROR_CODE_INCONSISTENT_INTERNAL_STATE_QNAME.equals(faultCode) ||
			CoordinationConstants.WSAT_ERROR_CODE_UNKNOWN_TRANSACTION_QNAME.equals(faultCode)) {

			log.error("Unrecoverable error for participant: "+participantId+", "+faultCode+", "+faultString);
			// unrecoverable error -- forget this participant and delete any persistent record of it
			State current;

			synchronized (mutex) {
				current = state;
				state = null;
			}

			if (current == State.STATE_PREPARED_SUCCESS &&
				CoordinationConstants.WSAT_ERROR_CODE_UNKNOWN_TRANSACTION_QNAME.equals(faultCode)) {
				// we need to tell this participant to roll back
				executeRollback();
			}

			if (persisted && participant instanceof Durable2PCParticipant) {
				// remove any durable participant recovery record from the persistent store
				Durable2PCParticipant durableParticipant =(Durable2PCParticipant) participant;

				// if we cannot delete the participant we record an error here
//				if (!XTSATRecoveryManager.getRecoveryManager().deleteParticipantRecoveryRecord(participantId)) {
//					log.warn("Unable to delete recovery record at commit for participant: "+participantId);
//				}
			}

			forget();
		}
	}

	/**
	 * Preparing -> PreparedSuccess (send Prepared)
	 * Committing -> Committing (send committed and forget)
	 */
	private void commitDecision() {
		State current;
		boolean rollbackRequired  = false;
		boolean deleteRequired  = false;

		synchronized (mutex) {
			current = state;
		}

		if (current == State.STATE_PREPARING) {
			// ok, we need to write the recovery details to log and send prepared.
			// if we cannot write the log then we have to rollback the participant
			//  and send aborted.
			if (participant instanceof Durable2PCParticipant) {
				// write a durable participant recovery record to the persistent store
//				Durable2PCParticipant durableParticipant = (Durable2PCParticipant) participant;
//				ATParticipantRecoveryRecord recoveryRecord = new ATParticipantRecoveryRecord(participantId, durableParticipant, coordinator);
//				if (!XTSATRecoveryManager.getRecoveryManager().writeParticipantRecoveryRecord(recoveryRecord)) {
//					// we need to rollback and send aborted unless some other thread
//					//gets there first
//					rollbackRequired = true;
//				}
			}

			// recheck state in case a rollback or readonly came in while we were writing the
			// log record
			synchronized (mutex) {
				current = state;

				if (current == State.STATE_PREPARING) {
					if (rollbackRequired) {
						// if we change state to aborting then we are responsible for
						// calling rollback and sending aborted but we have no log record
						// to delete
						state = State.STATE_ABORTING;
					} else {
						state = State.STATE_PREPARED_SUCCESS;
						// this ensures any subsequent commit or rollback deletes the log record
						// so we still have no log record to delete here
						persisted = true;
					}
				} else if (!rollbackRequired) {
					// an incoming rollback or readonly changed the state to aborted or null so
					// it will already have performed a rollback if required but we need to
					// delete the log record since the rollback/readonly thread did not know
					// about it
					deleteRequired = true;
				}
			}

			if (rollbackRequired) {
				// we need to do the rollback and send aborted
				executeRollback();
				sendAborted();
				forget();

			} else if (deleteRequired) {
				// just try to delete the log entry -- any required aborted has already been sent
//				if (!XTSATRecoveryManager.getRecoveryManager().deleteParticipantRecoveryRecord(participantId)) {
//					log.warn("Unable to delete recovery record during prepare for participant: "+participantId);
//				}

			} else {
				// whew got through -- send a prepared
				sendPrepared();
			}

		} else if (current == State.STATE_COMMITTING) {
			if (persisted && participant instanceof Durable2PCParticipant) {
				// remove any durable participant recovery record from the persistent store
				Durable2PCParticipant durableParticipant =(Durable2PCParticipant) participant;

				// if we cannot delete the participant we effectively drop the commit message
				// here in the hope that we have better luck next time.
//				if (!XTSATRecoveryManager.getRecoveryManager().deleteParticipantRecoveryRecord(participantId)) {
//					// hmm, could not delete entry -- log a warning
//					log.error("Unable to delete recovery record at commit for participant: "+participantId);
//
//					// now revert back to PREPARED_SUCCESS and drop message awaiting a retry
//					synchronized(mutex) {
//						state = State.STATE_PREPARED_SUCCESS;
//					}
//					return;
//				}
			}

			sendCommitted();
			forget();
		}
	}

	/**
	 * Active -> None (send ReadOnly)
	 * Preparing -> None (send ReadOnly)
	 */
	private void readOnlyDecision() {
		State current;
		synchronized (mutex) {
			current = state;
			if ((current == State.STATE_ACTIVE) || (current == State.STATE_PREPARING)) {
				state = null;
			}
		}

		if ((current == State.STATE_ACTIVE) || (current == State.STATE_PREPARING)) {
			sendReadOnly();
			forget();
		}
	}

	/**
	 * Active -> Aborting (send aborted)
	 * Preparing -> Aborting (send aborted)
	 */
	private void rollbackDecision() {
		State current;
		synchronized (mutex) {
			current = state;
			if ((current == State.STATE_PREPARING) || (current == State.STATE_ACTIVE)) {
				state = State.STATE_ABORTING;
			}
		}

		if ((current == State.STATE_PREPARING) || (current == State.STATE_ACTIVE)) {
			sendAborted();
			forget();
		}
	}

	/**
	 * PreparedSuccess -> PreparedSuccess (resend Prepared)
	 */
	private void commsTimeout(TimerTask caller) {
		State current;
		synchronized (mutex) {
			if (timerTask != caller) {
				// the timer was cancelled but it went off before it could be cancelled
				return;
			}

			// double the resend period up to our maximum limit
			if (resendPeriod < maxResendPeriod) {
				resendPeriod = resendPeriod * 14 / 10; // approximately doubles every two resends

				if (resendPeriod > maxResendPeriod) {
					resendPeriod = maxResendPeriod;
				}
			}
			current = state;
		}

		if (current == State.STATE_PREPARED_SUCCESS) {
			sendPrepared();
		}
	}

	/**
	 * Execute the commit transition.
	 */
	private void executeCommit() {
		try {
			participant.commit();
			commitDecision();
		} catch (Throwable th) {
			synchronized (mutex) {
				if (state == State.STATE_COMMITTING) {
					state = State.STATE_PREPARED_SUCCESS;
				}
			}
			log.error("Error", th);
		}
	}

	/**
	 * Execute the rollback transition.
	 */
	private boolean executeRollback() {
		try {
			participant.rollback();
		} catch (SystemException e) {
			return false;
		} catch (Throwable e) {
			log.error("Error", e);
		}
		return true;
	}

	/**
	 * Execute the prepare transition.
	 */
	private void executePrepare() {
		Vote vote;
		try {
			vote = participant.prepare();
		} catch (SystemException e) {
			log.error("Error", e);
			return;
		} catch (Throwable e) {
			log.error("Error", e);
			rollbackDecision();
			return;
		}

		if (vote instanceof Prepared) {
			commitDecision();
		} else if (vote instanceof ReadOnly) {
			readOnlyDecision();
		} else if (vote instanceof Aborted) {
			rollbackDecision();
		} else {
			log.error("Unexpected result from participant prepare: "+(vote == null ? "null" : vote.getClass().getName()));
			rollbackDecision();
		}
	}

	/**
	 * Forget the current participant.
	 */
	private void forget() {
		synchronized (mutex) {
			state = null;
		}
		ParticipantProcessor.getProcessor().deactivateParticipant(this);
	}

	private void sendCommitted() {
		try {
			//InstanceIdentifier instanceIdentifier = new InstanceIdentifier(participantId);
			CoordinatorClient.getInstance().sendCommitted(coordinatorReference, participantId, coordinatorId);
		} catch (Throwable e) {
			log.error("Error", e);
		}
	}

	private void sendPrepared() {
		sendPrepared(false);
	}

	private void sendPrepared(boolean timedOut) {
		try {
			//InstanceIdentifier instanceIdentifier = new InstanceIdentifier(participantId);
			CoordinatorClient.getInstance().sendPrepared(coordinatorReference, participantId, coordinatorId);
		} catch (Throwable e) {
			log.error("Error", e);
		}

		updateResendPeriod(timedOut);
		initiateTimer();
	}

	private void updateResendPeriod(boolean timedOut) {
		synchronized (mutex) {
			// if we timed out then we multiply the resend period by ~= sqrt(2) up to the maximum
			// if not we make sure it is reset to the initial period
	
			if (timedOut) {
				if (resendPeriod < maxResendPeriod) {
					long newPeriod  = resendPeriod * 14 / 10;  // approximately doubles every two resends
	
					if (newPeriod > maxResendPeriod) {
						newPeriod = maxResendPeriod;
					}
					resendPeriod = newPeriod;
				}
			} else {
				if (resendPeriod > initialResendPeriod) {
					resendPeriod = initialResendPeriod;
				}
			}
		}
	}
	
	private void sendAborted() {
		try {
			//InstanceIdentifier instanceIdentifier = new InstanceIdentifier(participantId);
			CoordinatorClient.getInstance().sendAborted(coordinatorReference, participantId, coordinatorId);
		} catch (Throwable e) {
			log.error("Error", e);
		}
	}

	private void sendReadOnly() {
		try {
			//InstanceIdentifier instanceIdentifier = new InstanceIdentifier(participantId);
			CoordinatorClient.getInstance().sendReadOnly(coordinatorReference, participantId, coordinatorId);
		} catch (Throwable e) {
			log.error("Error", e);
		}
	}

	private void initiateTimer() {
		synchronized (mutex) {
			if (timerTask != null)
				timerTask.cancel();
	
			if (state == State.STATE_PREPARED_SUCCESS) {
				timerTask = new TimerTask() {
					public void run() {
						commsTimeout(this);
					}
				};
				//TODO XXX TransportTimer.getTimer().schedule(timerTask, resendPeriod);
	
			} else {
				timerTask = null;
			}
		}
	}

}
