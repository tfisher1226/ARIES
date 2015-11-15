package common.tx;

import javax.transaction.SystemException;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.context.TransactionContext;
import common.tx.context.TransactionContextImpl;
import common.tx.context.TransactionContextManager;
import common.tx.model.Fault;
import common.tx.model.context.CoordinationContextType;
import common.tx.model.participant.Participant;


public class TransactionManagerImpl extends TransactionManager {
	
	protected static Log log = LogFactory.getLog(TransactionManager.class);

	private final TransactionContextManager transactionContextManager = new TransactionContextManager();

	
	public TransactionManagerImpl() {
		//nothing for now
	}

	public TransactionContext currentTransaction() throws SystemException {
		return transactionContextManager.currentTransaction();
	}

	public TransactionContext suspend() throws SystemException {
		return transactionContextManager.suspend();
	}

	// resume overwrites. Should we check first a la JTA?
	public void resume(TransactionContext tx) throws SystemException {
		transactionContextManager.resume(tx);
	}

	public int replay() throws SystemException {
		throw new SystemException("Not implemented");
	}

    private boolean isCurrentContextSecure() throws SystemException {
        TransactionContextImpl currentContext = (TransactionContextImpl) transactionContextManager.currentTransaction();
        if (currentContext != null)
            return currentContext.isSecure();
        return false;
    }

    W3CEndpointReference getParticipant(String id, boolean isSecure) {
        //QName serviceName = AriesConstants.PARTICIPANT_SERVICE_QNAME;
        //QName endpointName = AriesConstants.PARTICIPANT_PORT_QNAME;
        //String address = ServiceRegistry.getRegistry().getServiceURI(AriesConstants.PARTICIPANT_SERVICE_NAME, isSecure);
        //W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
        //builder.serviceName(serviceName);
        //builder.endpointName(endpointName);
        //builder.address(address);
        //InstanceIdentifier.setEndpointInstanceIdentifier(builder, id);
        //return builder.build();
    	return null;
    }

    public void registerParticipant(String protocolId) throws SystemException {
		TransactionContextImpl currentContext = null;

//		try {
//			currentContext = (TransactionContextImpl) contextManager.suspend();
//			if (currentContext == null)
//				throw new SystemException("Cannot register");
//            CoordinationContextType coordinationContext = currentContext.getCoordinationContext();
//            String messageId = MessageId.getMessageId();
//            W3CEndpointReference registerationEndpoint = RegistrationCoordinator.register(coordinationContext, messageId, participantEndpoint, protocolId);
//			return registerationEndpoint;
//        } catch (Fault f) {
//			throw new SystemException(f.getMessage());
//        } catch (Exception e) {
//			throw new SystemException(e.toString());
//		} finally {
//			try {
//				if (currentContext != null)
//					contextManager.resume(currentContext);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	public void enlistForDurableTwoPhase(Participant participant, String id) throws SystemException {
//		try {
//            W3CEndpointReference participantEndpoint = getParticipant(id, isCurrentContextSecure());
//            W3CEndpointReference coordinatorEndpoint = registerParticipant(participantEndpoint, AriesConstants.WSAT_SUB_PROTOCOL_DURABLE_2PC);
//			ParticipantEngine participantEngine = new ParticipantEngineImpl(participant, id, coordinatorEndpoint);
//			ParticipantProcessor.getInstance().activateParticipant(participantEngine, id);
//		} catch (InvalidProtocolException e) {
//			throw new SystemException(e.toString());
//		} catch (InvalidStateException e) {
//			throw new WrongStateException();
//		} catch (CannotRegisterException e) {
//            // cause could actually be no activity or already registered
//			throw new UnknownTransactionException();
//		}
	}

}
