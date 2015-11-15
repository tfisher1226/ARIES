package common.tx.exception;

import org.apache.tools.ant.taskdefs.SQLExec.Transaction;


/**
 *  The RollbackException exception indicates that either the transaction
 *  has been rolled back or an operation cannot complete because the
 *  transaction has been marked as rollback only.
 *  <p>
 *  It is thrown under two circumstances:
 *  <ul>
 *    <li>
 *    At transaction commit time, if the transaction has been marked for
 *    rollback only. In this case, the <code>commit</code> method will roll
 *    back the transaction and throw this exception to indicate that the
 *    transaction could not be committed.
 *    </li>
 *    <li>
 *    At other times, if an operation cannot be completed because the
 *    transaction is marked for rollback only.
 *    The {@link Transaction#enlistResource(javax.transaction.xa.XAResource) enlistResource}
 *    and {@link Transaction#registerSynchronization(Synchronization) registerSynchronization}
 *    methods in the {@link Transaction} interface throw this exception to
 *    indicate that the operation cannot be completed because the transaction
 *    is marked for rollback only. In this case, the state of the transaction
 *    remains unchanged.
 *    </li>
 *  </ul>
 */
@SuppressWarnings("serial")
public class RollbackException extends TransactionException {

	public RollbackException() {
	}

	public RollbackException(String message) {
		super(message);
	}

	public RollbackException(String reason, int errorcode) {
		super(reason, errorcode);
	}

	public RollbackException(String reason, Exception cause) {
		super(reason, cause);
	}

	public RollbackException(Exception cause) {
		super(cause);
	}

}

