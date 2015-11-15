package org.aries.tx.service.coordinator;

import javax.transaction.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.ParticipantInternal;
import org.aries.tx.ParticipantRecord;
import org.aries.tx.Synchronization;
import org.aries.tx.SynchronizationRecord;
import org.aries.tx.coordinator.CoordinatorId;
import org.aries.tx.coordinator.CoordinatorIdImple;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.arjuna.coordinator.AbstractRecord;
import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import com.arjuna.ats.arjuna.coordinator.AddOutcome;
import com.arjuna.ats.arjuna.coordinator.RecordListIterator;
import com.arjuna.ats.arjuna.coordinator.TwoPhaseCoordinator;


public class ATCoordinator extends TwoPhaseCoordinator {

	private static Log log = LogFactory.getLog(ATCoordinator.class);
	
	private final static int ROLLEDBACK = 0;
	
	private final static int READONLY = 1;

	private CoordinatorIdImple coordinatorId;


	public ATCoordinator() {
		this.coordinatorId = new CoordinatorIdImple(get_uid());
	}

	public ATCoordinator(Uid recovery) {
		super(recovery);
		this.coordinatorId = new CoordinatorIdImple(get_uid());
	}

	public void enlistParticipant(ParticipantInternal act) throws SystemException {		
		if (act == null)
			throw new SystemException("Invalid participant");
		AbstractRecord rec = new ParticipantRecord(act, new Uid());
		if (add(rec) != AddOutcome.AR_ADDED)
			throw new SystemException("Wrong state");
	}

	public void delistParticipant(ParticipantInternal act) throws SystemException {
		if (act == null)
			throw new SystemException("InvalidParticipant");
		throw new SystemException("No support for removal of participants");
	}

	public void enlistSynchronization(Synchronization act) throws SystemException {
		if (act == null)
			throw new SystemException("InvalidSynchronization");
		SynchronizationRecord rec = new SynchronizationRecord(act, new Uid());
		if (addSynchronization(rec) != AddOutcome.AR_ADDED)
			throw new SystemException("Wrong state");
	}

	public void delistSynchronization(Synchronization act) throws SystemException {
		if (act == null)
			throw new SystemException("Invalid synchronization");
		throw new SystemException("No support for removal of synchronizations");
	}

//	public Qualifier[] qualifiers () throws SystemException	{
//		return null;
//	}

	public CoordinatorId identifier() throws SystemException {
		return coordinatorId;
	}

	public synchronized void participantRolledBack(String participantId) throws SystemException {
		if (participantId == null)
			throw new SystemException("ParticipantId must be specified");
		if (status() == ActionStatus.RUNNING)
			changeParticipantStatus(participantId, ROLLEDBACK);
		throw new SystemException("Wrong state");
	}

	public synchronized void participantReadOnly(String participantId) throws SystemException {
		if (participantId == null)
			throw new SystemException("ParticipantId must be specified");
		if (status() == ActionStatus.RUNNING)
			changeParticipantStatus(participantId, READONLY);
		throw new SystemException("Wrong state");
	}

    @Override
    public String type() {
		return "/StateManager/BasicAction/AtomicAction/TwoPhaseCoordinator/TwoPhase/ATCoordinator";
	}
	
	private final void changeParticipantStatus(String participantId, int status) throws SystemException {
		/*
		 * Transaction is active, so we can look at the pendingList only.
		 */

		// TODO allow transaction status to be changed during commit - exit
		// could come in late
		boolean found = false;

		if (pendingList != null) {
			RecordListIterator iter = new RecordListIterator(pendingList);
			AbstractRecord absRec = iter.iterate();

			try {
				while ((absRec != null) && !found) {
					if (absRec instanceof ParticipantRecord) {
						ParticipantRecord pr = (ParticipantRecord) absRec;
						ParticipantInternal participant = (ParticipantInternal) pr.value();

						if (participantId.equals(participant.id())) {
							found = true;

							if (status == READONLY)
								pr.readonly();
							else
								pr.rolledback();
						}
					}

					absRec = iter.iterate();
				}
			} catch (Exception e) {
				throw new SystemException(e.getMessage());
			}
		}

		if (!found)
			throw new SystemException("Participant not found");
	}

}
