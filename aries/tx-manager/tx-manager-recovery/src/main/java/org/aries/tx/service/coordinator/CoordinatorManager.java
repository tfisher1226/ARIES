package org.aries.tx.service.coordinator;

import java.util.HashMap;
import java.util.Map;

import org.aries.tx.ParticipantInternal;
import org.aries.tx.Synchronization;
import org.aries.tx.service.coordinator.ATCoordinator;

import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;


public class CoordinatorManager {

	private static Map<String, ATCoordinator> coordinators = new HashMap<String, ATCoordinator>();



	protected ATCoordinator getParentCoordinator() {
		return null;
	}

	protected ATCoordinator getCurrentCoordinator(String transactionId) {
		return coordinators.get(transactionId);
	}


	public int getStatus(String transactionId) {
		ATCoordinator coordinator = getCurrentCoordinator(transactionId);
		return coordinator.status();
	}

	/**
	 * An activity has begun and is active on the current thread.
	 */
	public String begin() throws SystemException {		
		try {
			ATCoordinator coordinator = new ATCoordinator();
			int status = coordinator.start(getParentCoordinator());

			if (status != ActionStatus.RUNNING)
				throw new WrongStateException(ActionStatus.stringForm(status));
			
			String transactionId = coordinator.get_uid().stringForm();
			coordinators.put(transactionId, coordinator);
			return transactionId;
			
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}

	public void commit(String transactionId) {
		ATCoordinator coordinator = getCurrentCoordinator(transactionId);
		coordinator.end(true);
	}

	public void cancel(String transactionId) {
		ATCoordinator coordinator = getCurrentCoordinator(transactionId);
		coordinator.cancel();
	}

	
	public void enlistParticipant(String transactionId, ParticipantInternal participant) throws SystemException {
		try {
			ATCoordinator coordinator = getCurrentCoordinator(transactionId);
			coordinator.enlistParticipant(participant);
		} catch (javax.transaction.SystemException e) {
			throw new SystemException(e.getMessage());
		}
	}

	public void delistParticipant(String transactionId, ParticipantInternal participant) throws SystemException {
		try {
			ATCoordinator coordinator = getCurrentCoordinator(transactionId);
			coordinator.delistParticipant(participant);
		} catch (javax.transaction.SystemException e) {
			throw new SystemException(e.getMessage());
		}
	}
	
	public void enlistSynchronization(String transactionId, Synchronization synchronization) throws SystemException {
		try {
			ATCoordinator coordinator = getCurrentCoordinator(transactionId);
			coordinator.enlistSynchronization(synchronization);
		} catch (javax.transaction.SystemException e) {
			throw new SystemException(e.getMessage());
		}
	}

	public void delistSynchronization(String transactionId, Synchronization synchronization) throws SystemException {
		try {
			ATCoordinator coordinator = getCurrentCoordinator(transactionId);
			coordinator.delistSynchronization(synchronization);
		} catch (javax.transaction.SystemException e) {
			throw new SystemException(e.getMessage());
		}
	}

}
