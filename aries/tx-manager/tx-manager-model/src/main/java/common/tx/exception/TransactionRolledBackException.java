package common.tx.exception;

/**
 * Thrown if a transaction rolls back rather than commits.
 */
@SuppressWarnings("serial")
public class TransactionRolledBackException extends Exception {

	public TransactionRolledBackException() {
	}

	public TransactionRolledBackException(String message) {
		super(message);
	}

}

