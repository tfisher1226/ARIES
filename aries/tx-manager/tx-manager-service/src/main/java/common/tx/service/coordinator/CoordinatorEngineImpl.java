package common.tx.service.coordinator;

import java.util.TimerTask;

import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.State;
import org.aries.tx.participant.client.ParticipantClient;
import org.aries.tx.util.TransportTimer;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.model.Notification;


public class CoordinatorEngineImpl implements CoordinatorEngine {

	private static Log log = LogFactory.getLog(CoordinatorEngine.class);

	/** Indicates this is a coordinator for a durable participant. */
	private boolean durable;

	/** Indicates that this coordinator has been recovered from the log. */
	private boolean recovered;

	private String transactionId;

	private String coordinatorId;

	private String participantId;

	private W3CEndpointReference participant;

	private State state;

	private boolean readOnly;

	private TimerTask timerTask;
	
	private Object mutex = new Object();


	public CoordinatorEngineImpl(String transactionId, String coordinatorId, String participantId, boolean durable, W3CEndpointReference participant) {
		this(transactionId, coordinatorId, participantId, durable, participant, false, State.STATE_ACTIVE);
	}

	public CoordinatorEngineImpl(String transactionId, String coordinatorId, String participantId, boolean durable, W3CEndpointReference participant, boolean recovered, State state) {
		this.transactionId = transactionId;
		this.coordinatorId = coordinatorId;
		this.participantId = participantId;
		this.durable = durable;
		this.participant = participant;
		this.state = state;
		this.recovered = recovered;

		// unrecovered participants are always activated
		// we only need to reactivate recovered participants which were successfully prepared
		// any others will only have been saved because of a heuristic outcome e.g. a comms
		// timeout at prepare will write a heuristic record for an ABORTED TX including a
		// participant in state PREPARING. we can safely drop it since we implement presumed abort.

		if (!recovered || state == State.STATE_PREPARED_SUCCESS) {
			CoordinatorProcessor.getProcessor().activateCoordinator(this, coordinatorId);
		}
	}

	public String getCoordinatorId() {
		return coordinatorId;
	}

	public W3CEndpointReference getParticipant() {
		return participant;
	}

	public boolean isDurable() {
		return durable;
	}

	public boolean isRecovered() {
		return recovered;
	}

	public boolean isReadOnly() {
		synchronized (mutex) {
			return readOnly;
		}
	}

	public State getState() {
		synchronized (mutex) {
			return state;
		}
	}

	private void changeState(State state) {
		synchronized (mutex) {
			if (this.state != state) {
				this.state = state;
				mutex.notifyAll();
			}
		}
	}
	
	private State waitForState(State origState, long delay) {
		long end = System.currentTimeMillis() + delay;
		synchronized (mutex) {
			while (state == origState) {
				long remaining = end - System.currentTimeMillis();
				if (remaining <= 0) {
					break;
				}
				try {
					mutex.wait(remaining);
				} catch (InterruptedException ie) {
					// ignore
				} 
			}
			return state;
		}
	}

	/**
	 * None -> None (ignore)
	 * Active -> Aborting (forget)
	 * Preparing -> Aborting (forget)
	 * PreparedSuccess -> PreparedSuccess (invalid state)
	 * Committing -> Committing (invalid state)
	 * Aborting -> Aborting (forget)
	 */
	public void aborted(Notification notification) {
		synchronized (mutex) {
			State current = state;
			if (current == State.STATE_ACTIVE) {
				changeState(State.STATE_ABORTING);
			} else if (current == State.STATE_PREPARING || current == State.STATE_ABORTING) {
				forget();
			}
		}
	}

	/**
	 * None -> None (ignore)
	 * Active -> Aborting (invalid state)
	 * Preparing -> Aborting (invalid state)
	 * PreparedSuccess -> PreparedSuccess (invalid state)
	 * Committing -> Committing (forget)
	 * Aborting -> Aborting (invalid state)
	 */
	public void committed(Notification notification) {
		synchronized (mutex) {
			State current = state;
			if (current == State.STATE_ACTIVE) {
				changeState(State.STATE_ABORTING);
			} else if (current == State.STATE_PREPARING || current == State.STATE_COMMITTING) {
				forget();
			}
		}
	}
	
	/**
	 * None -> Durable: (send rollback), Volatile: Invalid state: none
	 * Active -> Aborting (invalid state)
	 * Preparing -> PreparedSuccess (Record Vote)
	 * PreparedSuccess -> PreparedSuccess (ignore)
	 * Committing -> Committing (resend Commit)
	 * Aborting -> Aborting (resend Rollback and forget)
	 */
	public void prepared(Notification notification) {
		State current;
		synchronized (mutex) {
			current = state;
			if (current == State.STATE_ACTIVE) {
				changeState(State.STATE_ABORTING);
			} else if (current == State.STATE_PREPARING) {
				changeState(State.STATE_PREPARED_SUCCESS);
			}
		}
		if (current == State.STATE_COMMITTING) {
			sendCommit();
		} else if (current == State.STATE_ABORTING) {
			if (durable) {
				sendRollback();
			} else {
				sendUnknownTransaction();
			}
			forget();
		} else if ((current == null) && !readOnly) {
			if (durable) {
				sendRollback();
			} else {
				sendUnknownTransaction();
			}
		}
	}

	/**
	 * None -> None (ignore)
	 * Active -> Active (forget)
	 * Preparing -> Preparing (forget)
	 * PreparedSuccess -> PreparedSuccess (invalid state)
	 * Committing -> Committing (invalid state)
	 * Aborting -> Aborting (forget)
	 */
	public synchronized void readOnly(Notification readOnly) {
		synchronized (mutex) {
			State current = state;
			if (current == State.STATE_ACTIVE || 
				current == State.STATE_PREPARING ||
				current == State.STATE_ABORTING) {
				if (current != State.STATE_ABORTING)
					this.readOnly = true;
				forget();
			}
		}
	}
	
	public void fault(Fault fault) {
		QName faultcode = fault.getFaultcode();
		String faultstring = fault.getFaultstring();
		log.error("Unexpected fault for coordinator: "+coordinatorId+", "+faultcode+", "+faultstring);
	}

	/**
	 * None -> None (invalid state)
	 * Active -> Preparing (send prepare)
	 * Preparing -> Preparing (resend prepare)
	 * PreparedSuccess -> PreparedSuccess (do nothing)
	 * Committing -> Committing (invalid state)
	 * Aborting -> Aborting (invalid state)
	 */
	public State prepare() {
		State current;
		synchronized (mutex) {
			current = state;
			if (current == State.STATE_ACTIVE) {
				changeState(State.STATE_PREPARING);
			}
		}

		if (current == State.STATE_ACTIVE || current == State.STATE_PREPARING) {
			sendPrepare();
		}

		waitForState(State.STATE_PREPARING, TransportTimer.getTransportTimeout());

		synchronized (mutex) {
			if (state != State.STATE_PREPARING) {
				return state;
			}

			if (timerTask != null) {
				timerTask.cancel();
				timerTask = null;
			}

			// ok, we leave the participant stub active because the coordinator will attempt
			// to roll it back when it notices that this has failed
			return state;
		}
	}

	/**
	 * None -> None (invalid state)
	 * Active -> Active (invalid state)
	 * Preparing -> Preparing (invalid state)
	 * PreparedSuccess -> Committing (send commit)
	 * Committing -> Committing (resend commit)
	 * Aborting -> Aborting (invalid state)
	 */
	public State commit() {
		State current;
		synchronized (mutex) {
			current = state;
			if (current == State.STATE_PREPARED_SUCCESS) {
				changeState(State.STATE_COMMITTING);
			}
		}

		if (current == State.STATE_PREPARED_SUCCESS || current == State.STATE_COMMITTING) {
			sendCommit();
		}

		waitForState(State.STATE_COMMITTING, TransportTimer.getTransportTimeout());

		synchronized (mutex) {
			if (state != State.STATE_COMMITTING) {
				// if this is a recovered participant then forget will not have
				// deactivated the entry so that this (recovery) thread can
				// detect it and update its log entry. so we need to deactivate
				// the entry here.

				if (recovered) {
					CoordinatorProcessor.getProcessor().deactivateCoordinator(this);
				}

				return state;
			}

			// the participant is still uncommitted so it will be rewritten to the log.
			// it remains activated in case a committed message comes in between now and
			// the next scan. the recovery code will detect this active participant when
			// rescanning the log and use it instead of recreating a new one.
			// we need to mark this one as recovered so it does not get deleted until
			// the next scan

			recovered = true;

			return State.STATE_COMMITTING;
		}
	}

	/**
	 * None -> None (invalid state)
	 * Active -> Aborting (send rollback)
	 * Preparing -> Aborting (send rollback)
	 * PreparedSuccess -> Aborting (send rollback)
	 * Committing -> Committing (invalid state)
	 * Aborting -> Aborting (do nothing)
	 */
	public State rollback() {
		State current;
		synchronized (mutex) {
			current = state;
			if (current == State.STATE_ACTIVE || 
				current == State.STATE_PREPARING ||
				current == State.STATE_PREPARED_SUCCESS) {
				changeState(State.STATE_ABORTING);
			}
		}

		if (current == State.STATE_ACTIVE || 
			current == State.STATE_PREPARING ||
			current == State.STATE_PREPARED_SUCCESS) {
			sendRollback();
			
		} else if (current == State.STATE_ABORTING) {
			forget();
		}

		waitForState(State.STATE_ABORTING, TransportTimer.getTransportTimeout());

		synchronized (mutex) {
			if (state != State.STATE_ABORTING) {
				// means state must be null and the participant has already been deactivated
				return state;
			}

			// the participant has not confirmed that it is aborted so it will be written to the
			// log in the transaction's heuristic list. it needs to be deactivated here
			// so that subsequent ABORTED messages are handled correctly, either by sending
			// an UnknownTransaction fault or a rollback depending upon whether it is
			// volatile or durable, respectively

			forget();
			return State.STATE_ABORTING;
		}
	}

	/**
	 * Preparing -> Preparing (resend Prepare)
	 * Committing -> Committing (resend Commit)
	 */
	private void commsTimeout(TimerTask caller) {
		State current;
		synchronized (mutex) {
			if (timerTask != caller) {
				// the timer was cancelled but it went off before it could be cancelled
				return;
			}

			current = state;
		}

		if (current == State.STATE_PREPARING) {
			sendPrepare();
			
		} else if (current == State.STATE_COMMITTING) {
			sendCommit();
		}
	}


	private void forget() {
		// first, change state to null to indicate that the participant has completed.
		changeState(null);

		// participants which have not been recovered from the log can be deactivated now.

		// participants which have been recovered are left for the recovery thread to deactivate.
		// this is because the recovery thread may have timed out waiting for a response to
		// the commit message and gone on to complete its scan and suspend. the next scan
		// will detect this activated participant and note that it has completed. if a crash
		// happens in between the recovery thread can safely recreate and reactivate the
		// participant and resend the commit since the commit/committed exchange is idempotent.

		if (!recovered) {
			CoordinatorProcessor.getProcessor().deactivateCoordinator(this);
		}
	}

	private void sendPrepare() {
		TimerTask newTimerTask = createTimerTask();
		synchronized (mutex) {
			// cancel any existing timer task
			if (timerTask != null) {
				timerTask.cancel();
			}

			// install the new timer task. this signals our intention to post a prepare which may need
			// rescheduling later but allows us to drop the lock on this while we are in the comms layer.
			// our intention can be revised by another thread by reassigning the field to a new task
			// or null

			timerTask = newTimerTask;
		}

		// ok now try the prepare

		try {
			ParticipantClient.getInstance().sendPrepare(transactionId, coordinatorId, participantId, participant);
		} catch (Throwable e) {
			log.error("Error", e);
		}

		// re-obtain the lock before deciding whether to schedule the timer
		synchronized (mutex) {
			if (timerTask == newTimerTask) {
				// the timer task has not been canceled so schedule it if appropriate
				if (state == State.STATE_PREPARING) {
					//************* tfisher
					//TODO scheduleTimer(newTimerTask);
					//************* tfisher
				} else {
					// no need to schedule it so get rid of it
					timerTask = null;
				}
			}
		}
	}

	private void sendCommit() {
		TimerTask newTimerTask = createTimerTask();
		synchronized (mutex) {
			// cancel any existing timer task
			if (timerTask != null) {
				timerTask.cancel();
			}

			// install the new timer task. this signals our intention to post a commit which may need
			// rescheduling later but allows us to drop the lock on this while we are in the comms layer.
			// our intention can be revised by another thread by reassigning the field to a new task
			// or null

			timerTask = newTimerTask;
		}

		// ok now try the commit
		try {
			ParticipantClient.getInstance().sendCommit(transactionId, coordinatorId, participantId, participant);
		} catch (Throwable e) {
			log.error("error", e);
		}

		// reobtain the lock before deciding whether to schedule the timer

		synchronized (mutex) {
			if (timerTask == newTimerTask) {
				// the timer task has not been cancelled so schedule it if appropriate
				if (state == State.STATE_COMMITTING) {
					//************* tfisher
					//TODO scheduleTimer(newTimerTask);
					//************* tfisher
				} else {
					// no need to schedule it so get rid of it
					timerTask = null;
				}
			}
		}
	}

	private void sendRollback() {
		try {
			ParticipantClient.getInstance().sendRollback(transactionId, coordinatorId, participantId, participant);
		} catch (Throwable e) {
			log.error("Error", e);
		}
	}

	private void sendUnknownTransaction() {
		try {
			Fault fault = new Fault();
			fault.setFaultstring("Unknown transaction");
			fault.setFaultcode(CoordinationConstants.WSAT_ERROR_CODE_UNKNOWN_TRANSACTION_QNAME);
			ParticipantClient.getInstance().sendFault(transactionId, coordinatorId, participantId, fault);
		} catch (Throwable e) {
			log.error("Error", e);
		}
	}

	private TimerTask createTimerTask() {
		return new TimerTask() {
			public void run() {
				commsTimeout(this);
			}
		};
	}

	private void scheduleTimer(TimerTask timerTask) {
		TransportTimer.getTimer().schedule(timerTask, TransportTimer.getTransportPeriod());
	}

	private void initiateTimer() {
		synchronized (mutex) {
			if (timerTask != null) {
				timerTask.cancel();
			}
			if (state == State.STATE_PREPARING || state == State.STATE_COMMITTING) {
				timerTask = new TimerTask() {
					public void run() {
						commsTimeout(this);
					}
				};
				TransportTimer.getTimer().schedule(timerTask, TransportTimer.getTransportPeriod());
				
			} else {
				timerTask = null;
			}
		}
	}
	
}
