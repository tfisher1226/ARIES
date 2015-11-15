package sample;

import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;

import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;
import common.tx.service.participant.Durable2PCParticipant;
import common.tx.state.ParticipantCache;
import common.tx.state.ParticipantManager;
import common.tx.vote.Aborted;
import common.tx.vote.Prepared;
import common.tx.vote.Vote;

/**
 * An adapter class that exposes the RestaurantManager as a WS-T Atomic Transaction participant.
 * Also logs events to a RestaurantView object.
 */
public class ParticipantAdapter implements Durable2PCParticipant {

	private String service = "RestaurantService";

	/**
	 * Id for the transaction which this participant instance relates to.
	 * Set by the service (via contrtuctor) at enrolment time, this value
	 * is passed to the backend business logic methods.
	 */
	protected String transactionId;

	protected String participantId;

	/** We invalidate the participant if the client makes invalid requests. */
	protected boolean valid = true;

	private ParticipantManager participantManager;
	

	public ParticipantAdapter(String transactionId, String participantId) {
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

	protected ParticipantManager getParticipantManager() {
		return participantManager;
	}

	protected void setParticipantManager(ParticipantManager participantManager) {
		this.participantManager = participantManager;
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
	public Vote prepare() throws WrongStateException, SystemException {
		System.out.println("RestaurantParticipantAT.prepare");

		//TODO getRestaurantView().addPrepareMessage("id:" + transactionId + ". Prepare called on participant: " + this.getClass().toString());
		boolean success = participantManager.prepare(transactionId);

		// Log the outcome and map the return value from
		// the business logic to the appropriate Vote type.

		if (success) {
			//TODO getRestaurantView().addMessage("Seats prepared successfully. Returning 'Prepared'\n");
			//getRestaurantView().updateFields();
			return new Prepared();

		} else {
			//TODO getRestaurantView().addMessage("Prepare failed (not enough seats?) Returning 'Aborted'\n");
			//getRestaurantView().updateFields();
			// forget about the participant
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
		System.out.println("RestaurantParticipantAT.commit");
		//TODO getRestaurantView().addMessage("id:" + transactionId + ". Commit called on participant: " + this.getClass().toString());
		participantManager.commit(transactionId);

		// Log the outcome
		//TODO getRestaurantView().addMessage("Seats committed\n");
		//TODO getRestaurantView().updateFields();

		// forget about the participant
		removeParticipant();
	}

	/**
	 * Invokes the rollback operation on the business logic,
	 * reporting activity and outcome.
	 *
	 * @throws WrongStateException
	 * @throws SystemException
	 */
	public void rollback() throws WrongStateException, SystemException {
		System.out.println("RestaurantParticipantAT.rollback");
		//TODO getRestaurantView().addMessage("id:" + transactionId + ". Rollback called on participant: " + this.getClass().toString());
		participantManager.rollback(transactionId);

		// Log the outcome
		//TODO getRestaurantView().addMessage("Seats booking cancelled\n");
		//TODO getRestaurantView().updateFields();

		// forget about the participant
		removeParticipant();
	}

	public void unknown() throws SystemException {
		forgetParticipant();
	}

	public void error() throws SystemException {
		forgetParticipant();
	}

	protected void removeParticipant() {
		ParticipantCache.getInstance().removeParticipant(service, transactionId);
	}

	protected void forgetParticipant() {
		ParticipantCache.getInstance().forgetParticipant(service, transactionId);
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
