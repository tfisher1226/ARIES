package common.tx.service.completion;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.exception.SystemException;
import common.tx.exception.TransactionRolledBackException;
import common.tx.exception.UnknownTransactionException;
import common.tx.model.Event;


public class CompletionStub {

	private W3CEndpointReference completionCoordinator;

	private String transactionId;


	public CompletionStub(W3CEndpointReference completionCoordinator, String id) throws Exception {
		this.completionCoordinator = completionCoordinator;
		this.transactionId = id;
	}

	public void commit() throws TransactionRolledBackException, UnknownTransactionException, SystemException {
		CompletionInitiatorCallbackImpl callback = new CompletionInitiatorCallbackImpl();
		CompletionInitiatorProcessor completionInitiator = CompletionInitiatorProcessorImpl.getProcessor();
		completionInitiator.registerCallback(transactionId, callback);

		try {
			//InstanceIdentifier identifier = new InstanceIdentifier(transactionId);
			CompletionCoordinatorClient.getClient().sendCommit(completionCoordinator, transactionId);
			callback.waitUntilTriggered();
		} catch (Event e) {
		} catch (Throwable e) {
			e.printStackTrace();
			throw new SystemException(e.getMessage());
		} finally {
			completionInitiator.removeCallback(transactionId);
		}

		if (callback.hasTriggered()) {
			if (callback.receivedCommitted()) {
				return;
			} else if (callback.receivedAborted()) {
				throw new TransactionRolledBackException();
			}
			Fault fault = callback.getFault();
			if (fault != null && CoordinationConstants.WSAT_ERROR_CODE_UNKNOWN_TRANSACTION.equals(fault.getFaultcode())) {
				throw new UnknownTransactionException();
			}
		}

		throw new SystemException();
	}

	public void rollback() throws UnknownTransactionException, SystemException {
		CompletionInitiatorCallbackImpl callback = new CompletionInitiatorCallbackImpl();
		CompletionInitiatorProcessor completionInitiator = CompletionInitiatorProcessorImpl.getProcessor();
		completionInitiator.registerCallback(transactionId, callback);
		
		try {
			//InstanceIdentifier identifier = new InstanceIdentifier(transactionId);
			CompletionCoordinatorClient.getClient().sendRollback(completionCoordinator, transactionId);
			callback.waitUntilTriggered();
		} catch (final Throwable e) {
			e.printStackTrace();
			throw new SystemException(e.getMessage());
		} finally {
			completionInitiator.removeCallback(transactionId);
		}

		if (callback.hasTriggered()) {
			if (callback.receivedAborted()){
				return;
			}
			Fault fault = callback.getFault();
			if (fault != null && CoordinationConstants.WSAT_ERROR_CODE_UNKNOWN_TRANSACTION.equals(fault.getFaultcode())) {
				throw new UnknownTransactionException();
			}
		}

		throw new SystemException();
	}
    
}
