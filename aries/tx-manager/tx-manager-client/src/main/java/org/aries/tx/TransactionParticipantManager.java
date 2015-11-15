package org.aries.tx;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.participant.ParticipantAdapter;
import org.aries.tx.participant.ParticipantCache;

import common.tx.state.ServiceState;


@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(BEAN)
public class TransactionParticipantManager {

	private static TransactionParticipantManager instance = new TransactionParticipantManager();
	
	public static TransactionParticipantManager getInstance() {
		return instance;
	}
	
	protected Log log = LogFactory.getLog(getClass());
	
	protected boolean transactionContextReentrant = true;
	
	private String actionName;
	
//	private Runnable runnable;

//	private ParticipantManager serviceManager;
	
//	private Transactional serviceProcessor;

	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	

	public TransactionParticipantManager() {
	}

//	public ParticipantHelper(ServiceStateProcessor<T> processor) {
//		this.processor = processor;
//	}
	
	public boolean inGlobalTransaction() {
		return TransactionManager.getTransactionId() != null;
	}
	
	public boolean isTransactionContextReentrant() {
		return transactionContextReentrant;
	}

	public void setTransactionContextReentrant(boolean transactionContextReentrant) {
		this.transactionContextReentrant = transactionContextReentrant;
	}

//	public String getActionName() {
//		return actionName;
//	}
//
//	public void setActionName(String actionName) {
//		this.actionName = actionName;
//	}

//	public Runnable getRunnable() {
//		return runnable;
//	}
//
//	public void setRunnable(Runnable runnable) {
//		this.runnable = runnable;
//	}

//	public ParticipantManager getServiceManager() {
//		return serviceManager;
//	}
//
//	public void setServiceManager(ParticipantManager participantManager) {
//		this.serviceManager = participantManager;
//	}

//	public Transactional getServiceProcessor() {
//		return serviceProcessor;
//	}
//
//	public void setServiceProcessor(Transactional serviceProcessor) {
//		this.serviceProcessor = serviceProcessor;
//	}

	
	public boolean validateRequest(ServiceState state) {
		return true;
	}

	//public abstract void execute(T state);
	
	public void resetState(ServiceState state) {
		//nothing by default
	}
	
	
    String getParticipantKey(String name, String transactionId) {
		return name+"/"+transactionId;
	}

	protected ParticipantAdapter createParticipant(Transactional transactional, String transactionId) {
        String participantId = getParticipantKey(transactional.getName(), transactionId);
        ParticipantAdapter participant = new ParticipantAdapter(transactional.getName(), transactionId, participantId);
        //participant.setServiceManager(getServiceManager());
        participant.setServiceProcessor(transactional);
        return participant;
	}
	
	public void enrollTransaction(String transactionName, String transactionId, Transactional transactional) {
		String participantKey = getParticipantKey(transactional.getName(), transactionId);
		ParticipantAdapter participant = ParticipantCache.getInstance().getParticipant(participantKey);

		if (participant == null) {
			participant = handleParticipantEnrollment(transactional, transactionId);
			ParticipantCache.getInstance().recordParticipant(participantKey, participant);
		
		} else {
			if (transactionContextReentrant) {
				//TODO check validity 
				//TODO if (participant.isValid())
				//if (transactionContextReentrantOnceOnly) {
				//	
				//}
				
			} else {
				handleParticipantAlreadyEnrolled(transactional, participant, transactionId);
			}
		}
	}

	public void enroll(Transactional transactional, String transactionId) {
//		String transactionId = null;
//		try {
//			javax.transaction.TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
//			TransactionImple transaction = (TransactionImple) transactionManager.getTransaction();
//			if (transaction == null)
//				return;
//			
//			int status = transaction.getStatus();
//			transactionId = transaction.get_uid().stringForm();
//			//transactionId = TransactionManager.getTransactionId();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return;
//		}

//		BasicAction currentAction = ThreadActionData.currentAction();
//		if (currentAction == null)
//			return;
//		String transactionId = currentAction.get_uid().toString();
//		if (transactionId == null)
//			return;
		
		//String transactionId = transactional.getTransactionId();
		String participantKey = getParticipantKey(transactional.getName(), transactionId);
		ParticipantAdapter participant = ParticipantCache.getInstance().getParticipant(participantKey);

		if (participant == null) {
			participant = handleParticipantEnrollment(transactional, transactionId);
			ParticipantCache.getInstance().recordParticipant(participantKey, participant);
		
		} else {
			if (transactionContextReentrant) {
				//TODO check validity 
				//TODO if (participant.isValid())
				//if (transactionContextReentrantOnceOnly) {
				//	
				//}
				
			} else {
				handleParticipantAlreadyEnrolled(transactional, participant, transactionId);
			}
		}
	}

	protected ParticipantAdapter handleParticipantEnrollment(Transactional transactional, String transactionId) {
        try {
	        // enlist the Participant for this service:
	        String participantKey = getParticipantKey(transactional.getName(), transactionId);
	        ParticipantAdapter participant = createParticipant(transactional, transactionId);
			TransactionManagerFactory.getTransactionManager().enlistForDurableTwoPhase(transactionId, participant);
	        log.debug("Enrolled participant for: "+participantKey);
			return participant;
			
        } catch (Exception e) {
            String message = "Participant enrollment failed: "+e.getMessage();
			log.error(message, e);
            throw new RuntimeException(message, e);
        }
	}
	
	protected void handleParticipantAlreadyEnrolled(Transactional transactional, ParticipantAdapter participant, String transactionId) {
        // this service does not support repeated bookings in the same transaction
        // so mark the participant as invalid
        //restaurantView.addMessage("id:" + transactionId + ". Participant already enrolled!");
        //restaurantView.updateFields();
        log.error("Participant already enrolled");
        // this ensures we do not try later to prepare the participant
        participant.invalidate(); //TODO cleanup
        // throw away any local changes previously made on behalf of the participant
        //TODO serviceManager.rollback(transactionId);
	}

}
