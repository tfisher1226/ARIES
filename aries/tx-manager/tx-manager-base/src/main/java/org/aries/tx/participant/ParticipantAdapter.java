package org.aries.tx.participant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.Durable2PCParticipant;
import org.aries.tx.Transactional;

import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;
import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;
import common.tx.vote.Aborted;
import common.tx.vote.Prepared;
import common.tx.vote.Vote;


/**
 * An adapter class that exposes the RestaurantManager as a WS-T Atomic Transaction participant.
 * Also logs events to a RestaurantView object.
 */
public class ParticipantAdapter implements Durable2PCParticipant {

	private static Log log = LogFactory.getLog(ParticipantAdapter.class);
	
	private String serviceName;

	/**
	 * Id for the transaction which this participant instance relates to.
	 * Set by the service (via contrtuctor) at enrolment time, this value
	 * is passed to the backend business logic methods.
	 */
	protected String transactionId;

	protected String participantId;

	/** We invalidate the participant if the client makes invalid requests. */
	protected boolean valid = true;

//	private ParticipantManager serviceManager;

	private Transactional serviceProcessor;
	

	public ParticipantAdapter(String serviceName, String transactionId, String participantId) {
		this.serviceName = serviceName;
		this.transactionId = transactionId;
		this.participantId = participantId;
		this.valid = true;
	}

	public String getTxID() {
		return transactionId;
	}

	public void invalidate() {
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public String getParticipantId() {
		return participantId;
	}

	public String getCoordinatorId() {
		return null;
	}

//	public ParticipantManager getServiceManager() {
//		return serviceManager;
//	}
//
//	public void setServiceManager(ParticipantManager participantManager) {
//		this.serviceManager = participantManager;
//	}

	public Transactional getServiceProcessor() {
		return serviceProcessor;
	}

	public void setServiceProcessor(Transactional serviceProcessor) {
		this.serviceProcessor = serviceProcessor;
	}

	
	/************************************************************************/
	/* Durable2PCParticipant methods                                        */
	/************************************************************************/

	/**
	 * Invokes the prepare step of the business logic,
	 * reporting activity and outcome.
	 *
	 * @return Prepared where possible, Aborted where necessary.
	 * @throws WrongStateException
	 * @throws SystemException
	 */
	//TODO need exception handling here...
	public Vote prepare() throws WrongStateException, SystemException {
		log.info(serviceName+" prepare starting");

		//TODO getRestaurantView().addPrepareMessage("id:" + transactionId + ". Prepare called on participant: " + this.getClass().toString());
		boolean success = serviceProcessor.prepare(transactionId);

		// Log the outcome and map the return value from
		// the business logic to the appropriate Vote type.

		if (success) {
			//TODO getRestaurantView().addMessage("Seats prepared successfully. Returning 'Prepared'\n");
			//getRestaurantView().updateFields();
			log.info(serviceName+" result: Prepared");
			return new Prepared();

		} else {
			//TODO getRestaurantView().addMessage("Prepare failed (not enough seats?) Returning 'Aborted'\n");
			//getRestaurantView().updateFields();
			// forget about the participant
			log.info(serviceName+" result: Aborted");
			removeParticipant();
			return new Aborted();
		}
	}

	/**
	 * Invokes the commit step of the business logic,
	 * reporting activity and outcome.
	 *
	 * @throws WrongStateException
	 * @throws SystemException
	 */
	public void commit() throws WrongStateException, SystemException {
		log.info(serviceName+" commit starting");
		//TODO getRestaurantView().addMessage("id:" + transactionId + ". Commit called on participant: " + this.getClass().toString());
		
		try {
			serviceProcessor.commit(transactionId);
			log.info(serviceName+" result: Committed");

			// Log the outcome
			//TODO getRestaurantView().addMessage("Seats committed\n");
			//TODO getRestaurantView().updateFields();
			
		} catch (Exception e) {
			//TODO what more to do here?
			log.error(serviceName+" result: Unexpected error", e);
			
		} finally {
			// forget about the participant
			removeParticipant();
		}
	}
	
	/**
	 * Invokes the rollback operation on the business logic,
	 * reporting activity and outcome.
	 *
	 * @throws WrongStateException
	 * @throws SystemException
	 */
	public void rollback() throws WrongStateException, SystemException {
		log.info(serviceName+" Rollback starting");
		//TODO getRestaurantView().addMessage("id:" + transactionId + ". Rollback called on participant: " + this.getClass().toString());
		
		try {
			serviceProcessor.rollback(transactionId);
			log.info(serviceName+" result: Rolledback");

			// Log the outcome
			//TODO getRestaurantView().addMessage("Seats booking cancelled\n");
			//TODO getRestaurantView().updateFields();

		} catch (Exception e) {
			//TODO what more to do here?
			log.error(serviceName+" result: Unexpected error", e);
			
		} finally {
			// forget about the participant
			removeParticipant();
		}
	}

	public void unknown() throws SystemException {
		forgetParticipant();
	}

	public void error() throws SystemException {
		forgetParticipant();
	}

	protected void removeParticipant() {
		ParticipantCache.getInstance().removeParticipant(serviceName, transactionId);
	}

	protected void forgetParticipant() {
		ParticipantCache.getInstance().forgetParticipant(serviceName, transactionId);
	}

	
	public boolean saveState(OutputObjectState objectState) {
		// TODO NOT YET
		return false;
	}

	public boolean restoreState(InputObjectState objectState) {
		// TODO NOT YET
		return false;
	}

}
