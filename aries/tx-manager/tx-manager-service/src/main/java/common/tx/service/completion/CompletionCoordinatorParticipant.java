package common.tx.service.completion;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import common.tx.exception.SystemException;
import common.tx.exception.TransactionRolledBackException;
import common.tx.exception.UnknownTransactionException;


public interface CompletionCoordinatorParticipant {

    public W3CEndpointReference getParticipant();

	public void commit(String transactionId) throws TransactionRolledBackException, UnknownTransactionException, SystemException;

	public void rollback(String transactionId) throws UnknownTransactionException, SystemException;

}
