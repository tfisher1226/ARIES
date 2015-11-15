package common.tx.state;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.TransactionManagerFactory;
import org.aries.tx.participant.ParticipantAdapter;
import org.aries.tx.participant.ParticipantCache;



public abstract class AbstractProcessor<T extends ServiceState> {

	protected Log log = LogFactory.getLog(getClass());
	
	protected boolean transactionContextReentrant;

	
	public boolean validateState(T state) {
		return true;
	}

	public abstract void updateState(T state);
	
	public void resetState(T state) {
		//nothing by default
	}
	
	
	public abstract String getActionName();
	
	protected abstract ParticipantManager getParticipantManager();
	
	
    String getParticipantKey(String transactionId) {
		return getActionName()+"/"+transactionId;
	}

	protected ParticipantAdapter createParticipant(String transactionId) {
        String participantId = getParticipantKey(transactionId);
        //String participantId = getActionName() + ":" + new UID().toString();
        ParticipantAdapter participant = new ParticipantAdapter(getActionName(), transactionId, participantId);
        //participant.setServiceManager(getParticipantManager());
        return participant;
	}
	
	public void ensureParticipantEnrollment(String transactionId) {
		String participantKey = getParticipantKey(transactionId);
		ParticipantAdapter participant = ParticipantCache.getInstance().getParticipant(participantKey);

		if (participant == null) {
			participant = handleParticipantEnrollment(transactionId);
			ParticipantCache.getInstance().recordParticipant(participantKey, participant);
		
		} else {
			if (transactionContextReentrant) {
				//TODO check validity 
				//TODO if (participant.isValid())
				//if (transactionContextReentrantOnceOnly) {
				//	
				//}
				
			} else {
				handleParticipantAlreadyEnrolled(transactionId, participant);
			}
		}
	}

	protected ParticipantAdapter handleParticipantEnrollment(String transactionId) {
        try {
	        // enlist the Participant for this service:
	        log.debug("Enrolling participant...");
	        ParticipantAdapter participant = createParticipant(transactionId);
	        String participantKey = getParticipantKey(transactionId);
			TransactionManagerFactory.getTransactionManager().enlistForDurableTwoPhase(transactionId, participant);
			return participant;
			
        } catch (Exception e) {
            String message = "Participant enrollment failed: "+e.getMessage();
			log.error(message, e);
            throw new RuntimeException(message, e);
        }
	}
	
	protected void handleParticipantAlreadyEnrolled(String transactionId, ParticipantAdapter participant) {
        // this service does not support repeated bookings in the same transaction
        // so mark the participant as invalid
        //restaurantView.addMessage("id:" + transactionId + ". Participant already enrolled!");
        //restaurantView.updateFields();
        log.error("Participant already enrolled");
        // this ensures we do not try later to prepare the participant
        ((ParticipantAdapter) participant).invalidate(); //TODO cleanup
        // throw away any local changes previously made on behalf of the participant
        getParticipantManager().rollback(transactionId);
	}

}
