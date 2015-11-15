package org.aries.tx;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import org.aries.Assert;
import org.aries.tx.coordinator.CoordinatorClient;

import tx.manager.registry.ServiceRegistry;

import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import common.tx.CoordinationConstants;
import common.tx.InstanceIdentifier;
import common.tx.exception.CannotRegisterException;
import common.tx.exception.HeuristicMixedException;
import common.tx.exception.HeuristicRollbackException;
import common.tx.exception.NotSupportedException;
import common.tx.exception.RollbackException;
import common.tx.exception.SystemException;
import common.tx.exception.UnknownTransactionException;
import common.tx.exception.WrongStateException;
import common.tx.model.context.CoordinationContext;
import common.tx.model.context.CoordinationContextType;
import common.tx.service.activation.ActivationCoordinator;
import common.tx.service.completion.CompletionStub;


public class UserTransactionImpl implements UserTransaction {

	protected final TransactionContextManager transactionContextManager = TransactionContextManager.INSTANCE;

	private Map<String, W3CEndpointReference> completionCoordinators = new HashMap<String, W3CEndpointReference>();

	protected String activationCoordinatorService;

	private UserSubordinateTransactionImpl userSubordinateTransaction;

	private CoordinationContextType coordinationContext;

	private String transactionId;


	@Override
	public int getStatus() {
		//if (transactionId == null)
		//	transactionId = getTransactionId();
		if (transactionId == null)
			//TODO throw new NoTransactionException();
			return ActionStatus.NO_ACTION;
		//W3CEndpointReference coordinatorReference = getCoordinator(false);
		//int status = CoordinatorClient.getInstance().getStatus(coordinatorReference, transactionId);
		int status = CoordinatorClient.getInstance().getStatus(transactionId);
//		try {
//			Transaction transaction = TransactionRegistry.getInstance().getTransaction(transactionId);
//			if (transaction != null) {
//				int status2 = transaction.getStatus();
//				return status2;
//			}
//		} catch (javax.transaction.SystemException e) {
//			e.printStackTrace();
//		}
		return status;
	}

	@Override
	public String getState() {
		String state = ActionStatus.stringForm(getStatus());
		return state;
	}
	
	public String getTransactionId() {
		TransactionContext transactionContext = transactionContextManager.getTransactionContext();
		if (transactionContext != null)
			return transactionContext.getTransactionId();
		return null;
	}

	@Override
	public int getTransactionTimeout() {
		//throw new NotSupportedException();
		return 0;
	}

	@Override
	public void setTransactionTimeout(int seconds) {
		//throw new NotSupportedException();
	}

	protected W3CEndpointReference getCoordinator(boolean isSecure) {
		W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
		//String address = ServiceRegistry.getRegistry().getServiceURI(CoordinationConstants.COORDINATOR_SERVICE_NAME, isSecure);
		String address = "http://127.0.0.1:8080/tx-service/CoordinatorService/CoordinatorPortType";
		builder.serviceName(CoordinationConstants.COORDINATOR_SERVICE_QNAME);
		builder.endpointName(CoordinationConstants.COORDINATOR_PORT_QNAME);
		builder.address(address);
		return builder.build();
	}
	
	/**
	 * fetch the coordination context type stashed in the current AT context implememtation
	 * and use it to construct an instance of the coordination context extension type we need to
	 * send down the wire to the activation coordinator
	 * @param current the current AT context implememtation
	 * @return an instance of the coordination context extension type
	 */
	private CoordinationContext getContext(TransactionContext current) {
		CoordinationContext coordinationContext = new CoordinationContext();
		CoordinationContextType coordinationContextType = ((TransactionContextImpl) current).getCoordinationContextType();
		coordinationContext.setCoordinationType(coordinationContextType.getCoordinationType());
		coordinationContext.setExpires(coordinationContextType.getExpires());
		coordinationContext.setIdentifier(coordinationContextType.getIdentifier());
		coordinationContext.setRegistrationService(coordinationContextType.getRegistrationService());
		return coordinationContext;
	}

	@Override
	public String begin() throws NotSupportedException, SystemException {
		return begin(0);
	}

	public String begin(long timeout) throws NotSupportedException, SystemException {
		try {
			if (transactionContextManager.getTransactionContext() != null)
				throw new SystemException("Already started");
			
			TransactionContext transactionContext = startTransaction(timeout, null);
			
			transactionContextManager.start(transactionContext);

			enlistCompletionParticipants();

//			javax.transaction.UserTransaction delegate = getUserTransaction();
//			if (delegate != null)
//				delegate.begin();

			transactionId = transactionContext.getTransactionId();
			return transactionId;
			
		} catch (SystemException e) {
			suspend();
			throw e;

		} catch (Exception e) {
			suspend();
			throw new SystemException(e.getMessage());
		}
	}

	protected TransactionContext startTransaction(long timeout, TransactionContext current) throws SystemException {
		try {
			// TODO: tricks for per app _activationCoordinatorService config, perhaps:
			//InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/foo.properties");

			Long expires = (timeout > 0 ? new Long(timeout) : null) ;
			CoordinationContext currentContext = (current != null ? getContext(current) : null);
			coordinationContext = ActivationCoordinator.createCoordinationContext(CoordinationConstants.WSAT_PROTOCOL, currentContext, expires);
			if (coordinationContext == null)
				throw new SystemException("Received context is null");
			TransactionContextImpl transactionContext = new TransactionContextImpl(coordinationContext);
			return transactionContext;

		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}


	private void enlistCompletionParticipants() throws UnknownTransactionException, CannotRegisterException, SystemException {
		TransactionManagerImpl transactionManager = (TransactionManagerImpl) TransactionManager.getTransactionManager();
		TransactionContext transactionContext = transactionManager.currentTransaction();
		if (transactionContext == null)
			throw new UnknownTransactionException();
		String transactionId = transactionContext.getTransactionId();
		String participantId = transactionContext.getTransactionId(); //TODO make this unique
		W3CEndpointReference completionParticipant = getCompletionParticipant(transactionId, transactionContext.isSecure());
		W3CEndpointReference completionCoordinator = transactionManager.registerParticipant(transactionId, participantId, CoordinationConstants.WSAT_SUB_PROTOCOL_COMPLETION, completionParticipant);
		completionCoordinators.put(transactionId, completionCoordinator);
	}

//	protected TransactionContext getTransactionContext() throws UnknownTransactionException {
//		TransactionManagerImpl transactionManager = (TransactionManagerImpl) TransactionManager.getTransactionManager();
//		TransactionContext transactionContext = (TransactionContext) transactionManager.currentTransaction();
//		if (transactionContext == null)
//			throw new UnknownTransactionException();
//		return transactionContext;
//	}

	/**
	 * method provided for the benefit of UserSubordinateTransactionImple to allow it
	 * to begin a subordinate transaction which requires an existing context to be
	 * installed on the thread before it will start and instal la new transaction
	 *
	 * @param timeout
	 * @throws WrongStateException
	 * @throws SystemException
	 */
	public String beginSubordinate(int timeout) throws SystemException {
		try {
			TransactionContext current = transactionContextManager.getTransactionContext();
			if (current == null || current instanceof TransactionContextImpl == false)
				throw new SystemException("Wrong state");

			//TransactionContextImpl currentImple = (TransactionContextImpl) current;
			TransactionContext transactionContext = startTransaction(timeout, current);
			transactionContextManager.start(transactionContext);

			// n.b. we don't enlist the subordinate transaction for completion
			// that ensures that any attempt to commit or rollback will fail

			transactionId = transactionContext.getTransactionId();
			return transactionId;
			
		} catch (SystemException e) {
			suspend();
			throw e;
		}
	}

	@Override
	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
		try {
			commitWithoutAck();

			//javax.transaction.UserTransaction delegate = getUserTransaction();
			//if (delegate != null)
			//	delegate.commit();

		} catch (SystemException e) {
			throw e;

		} finally {
			suspend();
		}
	}

	private final void commitWithoutAck() throws SecurityException, SystemException {
		TransactionContextImpl transactionContext = null;

		try {
			transactionContext = (TransactionContextImpl) transactionContextManager.suspend();
			if (transactionContext == null)
				throw new SystemException("No transactional context");
			String transactionId = transactionContext.getTransactionId();
			Assert.equals(transactionId, this.transactionId);

			/*
			 * By default the completionParticipantURL won't be set for an interposed (imported)
			 * bridged transaction. This is fine, because you shouldn't be able to commit that
			 * transaction from a node in the tree, only from the root. So, we can prevent commit
			 * or rollback at this stage. The alternative would be to setup the completionParticipantURL
			 * and throw the exception from the remote coordinator side (see enlistCompletionParticipants
			 * for how to do this).
			 *
			 * The same applies for an interposed subordinate transaction created via beginSubordinate.
			 */

			W3CEndpointReference completionCoordinator = (W3CEndpointReference) completionCoordinators.get(transactionId);
			if (completionCoordinator == null)
				throw new SystemException("Wrong state");

			CompletionStub completionStub = new CompletionStub(completionCoordinator, transactionId);
			completionStub.commit();

		} catch (SystemException e) {
			throw e;

		} catch (SecurityException e) {
			throw e;

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e.getMessage());

		} finally {
			try {
				if (transactionContext != null)
					transactionContextManager.resume(transactionContext);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (transactionId != null)
				completionCoordinators.remove(transactionId);
		}
	}


	@Override
	public void rollback() throws IllegalStateException, SecurityException, SystemException {
		try {
			abortWithoutAck();
			
		} catch (SystemException e) {
			throw e;
			
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
			
		} finally {
			suspend();
		}
	}

	private final void abortWithoutAck() throws UnknownTransactionException, SecurityException, SystemException {
		TransactionContextImpl transactionContext = null;

		try {
			transactionContext = (TransactionContextImpl) transactionContextManager.suspend();
			if (transactionContext == null)
				throw new SystemException("No transactional context");
			String transactionId = transactionContext.getTransactionId();
			Assert.equals(transactionId, this.transactionId);

			/*
			 * By default the completionParticipantURL won't be set for an interposed (imported)
			 * bridged transaction. This is fine, because you shouldn't be able to commit that
			 * transaction from a node in the tree, only from the root. So, we can prevent commit
			 * or rollback at this stage. The alternative would be to setup the completionParticipantURL
			 * and throw the exception from the remote coordinator side (see enlistCompletionParticipants
			 * for how to do this).
			 *
			 * The same applies for an interposed subordinate transaction created via beginSubordinate.
			 */

			W3CEndpointReference completionCoordinator = (W3CEndpointReference) completionCoordinators.get(transactionId);

			if (completionCoordinator == null)
				throw new WrongStateException();

			CompletionStub completionStub = new CompletionStub(completionCoordinator, transactionId);
			completionStub.rollback();
			
		} catch (SystemException e) {
			throw e;
			
		} catch (UnknownTransactionException e) {
			throw e;
			
		} catch (SecurityException e) {
			throw e;
			
		} catch (Exception e) {
			throw new SystemException(e.toString());
			
		} finally {
			try {
				if (transactionContext != null)
					transactionContextManager.resume(transactionContext);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (transactionId != null)
				completionCoordinators.remove(transactionId);
		}
	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		//getUserTransaction().setRollbackOnly();
	}

	
	public void suspend() {
		try {
			transactionContextManager.suspend();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		transactionId = null;
	}



	
	/**
	 * Create an endpoint for the local participant service labeled with the current context id which can be passed
	 * to the registration service and handed on to the registered coordinator to call back to this transaction
	 * @param id the current transaction context identifier
	 * @return
	 */
	private W3CEndpointReference getCompletionParticipant(final String id, final boolean isSecure) {
		QName serviceName = CoordinationConstants.COMPLETION_INITIATOR_SERVICE_QNAME;
		QName endpointName = CoordinationConstants.COMPLETION_INITIATOR_ENDPOINT_QNAME;
		String address = ServiceRegistry.getInstance().getServiceURI(CoordinationConstants.COMPLETION_INITIATOR_SERVICE_NAME);
		W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
		builder.serviceName(serviceName);
		builder.endpointName(endpointName);
		builder.address(address);
		InstanceIdentifier.setEndpointInstanceIdentifier(builder, id);
		return builder.build();
	}

}
