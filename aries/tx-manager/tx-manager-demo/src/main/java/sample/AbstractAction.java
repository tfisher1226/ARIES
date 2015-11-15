package sample;



public abstract class AbstractAction {

//	private Log log = LogFactory.getLog(getClass());
//
//	public abstract String getActionName();
//	
//	public abstract Participant createParticipant(String transactionId);
//
//	
//    String getParticipantKey(String transactionId) {
//		return getActionName()+"/"+transactionId;
//	}
//
//	public void enlistParticipant(String transactionId) {
//		String participantKey = getParticipantKey(transactionId);
//		Participant participant = ParticipantManager.getInstance().getParticipant(participantKey);
//		if (participant != null) {
//			handleParticipantAlreadyEnrolled(transactionId, participant);
//		} else {
//			handleParticipantEnrollment(transactionId);
//		}
//	}
//
//	protected void handleParticipantEnrollment(String transactionId) {
//        try {
//	        // enlist the Participant for this service:
//	        log.debug("Enrolling participant...");
//	        Participant participant = createParticipant(transactionId);
//	        String participantKey = getParticipantKey(transactionId);
//			TransactionManagerFactory.getTransactionManager().enlistForDurableTwoPhase(transactionId, participant);
//			ParticipantManager.getInstance().recordParticipant(participantKey, participant);
//			
//        } catch (Exception e) {
//            String message = "Participant enrollment failed: "+e.getMessage();
//			log.error(message, e);
//            throw new RuntimeException(message, e);
//        }
//	}
//
//	protected void handleParticipantAlreadyEnrolled(String transactionId, Participant participant) {
//        // this service does not support repeated bookings in the same transaction
//        // so mark the participant as invalid
//        //restaurantView.addMessage("id:" + transactionId + ". Participant already enrolled!");
//        //restaurantView.updateFields();
//        log.error("Participant already enrolled");
//        // this ensures we do not try later to prepare the participant
//        participant.invalidate();
//        // throw away any local changes previously made on behalf of the participant
//        RestaurantManager.getInstance().rollback(transactionId);
//	}
	
}
