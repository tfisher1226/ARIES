package org.aries.tx;

import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.participant.ParticipantEngine;
import org.aries.tx.participant.ParticipantEngineImpl;
import org.aries.tx.participant.ParticipantProcessor;
import org.aries.tx.registration.RegistrationClient;

import tx.manager.registry.ServiceRegistry;

import common.tx.CoordinationConstants;
import common.tx.InstanceIdentifier;
import common.tx.exception.CannotRegisterException;
import common.tx.exception.SystemException;
import common.tx.model.context.CoordinationContextType;


public class TransactionManagerImpl extends TransactionManager /*implements javax.transaction.TransactionManager*/ {
	
	private static Log log = LogFactory.getLog(TransactionManager.class);

	
	public TransactionManagerImpl() {
		//nothing for now
	}

	public TransactionContext currentTransaction() {
		return TransactionContextManager.INSTANCE.getTransactionContext();
	}

	public TransactionContext suspend() throws SystemException {
		return TransactionContextManager.INSTANCE.suspend();
	}

	// resume overwrites. Should we check first a la JTA?
	public void resume(TransactionContext tx) throws SystemException {
		TransactionContextManager.INSTANCE.resume(tx);
	}

	public int replay() throws SystemException {
		throw new SystemException("Not implemented");
	}

    private boolean isCurrentContextSecure() throws SystemException {
        TransactionContextImpl currentContext = (TransactionContextImpl) TransactionContextManager.INSTANCE.getTransactionContext();
        if (currentContext != null)
            return currentContext.isSecure();
        return false;
    }

    W3CEndpointReference getParticipant(String id, boolean isSecure) {
        QName serviceName = CoordinationConstants.PARTICIPANT_SERVICE_QNAME;
        QName endpointName = CoordinationConstants.PARTICIPANT_ENDPOINT_QNAME;
        String address = ServiceRegistry.getInstance().getServiceURI(CoordinationConstants.PARTICIPANT_SERVICE_NAME);
        W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
        builder.serviceName(serviceName);
        builder.endpointName(endpointName);
        builder.address(address);
        InstanceIdentifier.setEndpointInstanceIdentifier(builder, id);
        return builder.build();
    }

	public void enlistForDurableTwoPhase(String transactionId, Participant participant) throws CannotRegisterException, SystemException {
		enlistForDurableTwoPhase(transactionId, participant.getParticipantId(), participant);
	}

	public void enlistForDurableTwoPhase(String transactionId, String participantId, Participant participant) throws CannotRegisterException, SystemException {
		W3CEndpointReference participantEndpoint = getParticipant(participantId, isCurrentContextSecure());
		W3CEndpointReference coordinatorEndpoint = registerParticipant(transactionId, participantId, CoordinationConstants.WSAT_SUB_PROTOCOL_DURABLE_2PC, participantEndpoint);
		//System.out.println();
		
		ParticipantEngine participantEngine = new ParticipantEngineImpl(participant, participantId, coordinatorEndpoint);
		ParticipantProcessor.getProcessor().activateParticipant(participantEngine, participantId);
	}

	public void enlistForVolatileTwoPhase(String transactionId, Participant participant) throws CannotRegisterException, SystemException {
		enlistForVolatileTwoPhase(transactionId, participant.getParticipantId(), participant);
	}

	public void enlistForVolatileTwoPhase(String transactionId, String participantId, Participant participant) throws CannotRegisterException, SystemException {
		W3CEndpointReference participantEndpoint = getParticipant(participantId, isCurrentContextSecure());
		W3CEndpointReference coordinatorEndpoint = registerParticipant(transactionId, participantId, CoordinationConstants.WSAT_SUB_PROTOCOL_VOLATILE_2PC, participantEndpoint);
		//System.out.println();
		
		ParticipantEngine participantEngine = new ParticipantEngineImpl(participant, participantId, coordinatorEndpoint);
		ParticipantProcessor.getProcessor().activateParticipant(participantEngine, participantId) ;
	}

    public W3CEndpointReference registerParticipant(String transactionId, String participantId, String protocolId, W3CEndpointReference participantEndpoint) throws CannotRegisterException, SystemException {
		TransactionContextImpl transactionContext = null;
    	if (transactionId != null)
    		transactionContext = (TransactionContextImpl) TransactionContextManager.INSTANCE.getTransactionContext(transactionId);
    	if (transactionContext == null)
    		transactionContext = (TransactionContextImpl) TransactionContextManager.INSTANCE.getTransactionContext();
    	if (transactionContext == null)
    		transactionContext = (TransactionContextImpl) TransactionContextManager.INSTANCE.suspend();
		if (transactionContext == null)
			throw new CannotRegisterException("participant: "+participantId);

		try {
            CoordinationContextType coordinationContext = transactionContext.getCoordinationContextType();
            W3CEndpointReference coordinatorEndpoint = RegistrationClient.getInstance().register(coordinationContext, participantEndpoint, participantId, protocolId);
			return coordinatorEndpoint;
			
        } catch (Exception e) {
			throw new SystemException(e.toString());
			
		} finally {
			try {
				if (transactionContext != null)
					TransactionContextManager.INSTANCE.resume(transactionContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	@Override
//	public void begin() throws NotSupportedException, SystemException {
//	}
//
//	@Override
//	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
//	}
//
//	@Override
//	public int getStatus() throws SystemException {
//		return 0;
//	}
//
//	@Override
//	public Transaction getTransaction() throws SystemException {
//		return null;
//	}
//
//	@Override
//	public void resume(Transaction tobj) throws InvalidTransactionException, IllegalStateException, SystemException {
//	}
//
//	@Override
//	public void rollback() throws IllegalStateException, SecurityException, SystemException {
//	}
//
//	@Override
//	public void setRollbackOnly() throws IllegalStateException, SystemException {
//	}
//
//	@Override
//	public void setTransactionTimeout(int seconds) throws SystemException {
//	}
//
//	@Override
//	public Transaction suspend() throws SystemException {
//		return null;
//	}
    
}
