package common.tx;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import common.tx.context.TransactionContext;
import common.tx.context.TransactionContextImpl;
import common.tx.context.TransactionContextManager;
import common.tx.service.completion.CompletionStub;



public class UserTransaction implements javax.transaction.UserTransaction {

	private javax.transaction.UserTransaction transaction;

	protected final TransactionContextManager transactionContextManager = new TransactionContextManager();
	
	
	
	public javax.transaction.UserTransaction getUserTransaction() {
		return com.arjuna.ats.jta.UserTransaction.userTransaction();
	}

	@Override
	public int getStatus() throws SystemException {
		return getUserTransaction().getStatus();
	}

	@Override
	public void setTransactionTimeout(int seconds) throws SystemException {
		getUserTransaction().setTransactionTimeout(seconds);
	}

	@Override
	public void begin() throws NotSupportedException, SystemException {
		begin(0);
	}
	
	public void begin(long timeout) throws NotSupportedException, SystemException {
		try {
			if (transactionContextManager.currentTransaction() != null)
				throw new SystemException("Already started");
			TransactionContext transactionContext = startTransaction(timeout, null);
			transactionContextManager.resume(transactionContext);
			enlistCompletionParticipants();

			javax.transaction.UserTransaction delegate = getUserTransaction();
			if (delegate != null)
				delegate.begin();
		
		} catch (SystemException e) {
			suspendContext();
			throw e;

		} catch (Exception e) {
			suspendContext();
			throw new SystemException(e.getMessage());
		}
	}

	protected void suspendContext() {
		try {
			transactionContextManager.suspend();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    protected TransactionContext startTransaction(long timeout, TransactionContextImpl current) throws SystemException {
		try {
            // TODO: tricks for per app _activationCoordinatorService config, perhaps:
            //InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/foo.properties");

//            final Long expires = (timeout > 0 ? new Long(timeout) : null) ;
//            final String messageId = MessageId.getMessageId() ;
//            final CoordinationContext currentContext = (current != null ? getContext(current) : null);
//            final CoordinationContextType coordinationContext = ActivationCoordinator.createCoordinationContext(
//                    activationCoordinatorService, messageId, AriesConstants.WSAT_PROTOCOL, expires, currentContext) ;
//            if (coordinationContext == null)
//                throw new SystemException("Received context is null");
//            return new TransactionContextImpl(coordinationContext);
			
            return new TransactionContextImpl(null);
//		} catch (Fault e) {
//			throw new SystemException(e.getMessage()) ;
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}
    
	
	private void enlistCompletionParticipants() throws SystemException {
        TransactionManagerImpl transactionManager = (TransactionManagerImpl) TransactionManager.getTransactionManager();

        //TransactionContextImpl currentTx = (TransactionContextImpl) transactionManager.currentTransaction();
        //String id = currentTx.identifier();
        //W3CEndpointReference completionParticipant = getCompletionParticipant(id, currentTx.isSecure());
        //completionCoordinator = transactionManager.registerParticipant(completionParticipant, AriesConstants.WSAT_SUB_PROTOCOL_COMPLETION);

//        TransactionContextImpl currentTx = (TransactionContextImpl) transactionManager.currentTransaction();
//        if (currentTx == null)
//            throw new UnknownTransactionException();
//
//        String id = currentTx.identifier();
//        W3CEndpointReference completionParticipant = getCompletionParticipant(id, currentTx.isSecure());
//        W3CEndpointReference completionCoordinator = null;
//
//        try {
//            completionCoordinator = transactionManager.registerParticipant(completionParticipant, AriesConstants.WSAT_SUB_PROTOCOL_COMPLETION);
//        } catch (InvalidProtocolException e) {
//            e.printStackTrace();
//            throw new SystemException(e.getMessage());
//        } catch (InvalidStateException e) {
//            throw new SystemException("Wrong state");
//        } catch (CannotRegisterException e) {
//            // cause could actually be no activity or already registered
//            throw new UnknownTransactionException();
//        }
//
//        _completionCoordinators.put(id, completionCoordinator);
	}
	
	@Override
	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
		try {
			commitWithoutAck();
			
			javax.transaction.UserTransaction delegate = getUserTransaction();
			if (delegate != null)
				delegate.commit();
			
		} catch (SystemException e) {
			throw e;
			
		} finally {
			suspendContext();
		}
	}
	
	private final void commitWithoutAck () throws SecurityException, SystemException {
		TransactionContextImpl ctx = null;
		String id = null;

		try {
			ctx = (TransactionContextImpl) transactionContextManager.suspend();
            if (ctx == null)
                throw new SystemException("Wrong state");
			id = ctx.identifier();

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

			//W3CEndpointReference completionCoordinator = (W3CEndpointReference) _completionCoordinators.get(id);
			//if (completionCoordinator == null)
			//	throw new SystemException("Wrong state");

			CompletionStub completionStub = new CompletionStub(id);
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
				if (ctx != null)
					transactionContextManager.resume(ctx);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//if (id != null)
			//	_completionCoordinators.remove(id);
		}
	}
	

	@Override
	public void rollback() throws IllegalStateException, SecurityException, SystemException {
		getUserTransaction().rollback();
	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		getUserTransaction().setRollbackOnly();
	}

}
