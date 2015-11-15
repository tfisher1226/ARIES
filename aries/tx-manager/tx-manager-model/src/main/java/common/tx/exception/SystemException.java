package common.tx.exception;


/**
 * Thrown if a general exception is encountered (one not supported by
 * any other exception.)
 */
@SuppressWarnings("serial")
public class SystemException extends TransactionException {

	public SystemException() {
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(String reason, int errorcode) {
		super(reason, errorcode);
	}

	public SystemException(String reason, Exception cause) {
		super(reason, cause);
	}

	public SystemException(Exception cause) {
		super(cause);
	}

}

