package common.tx.exception;


/**
 * There is no coordinator associated with the target.
 */
public class SystemCommunicationException extends SystemException {

	public SystemCommunicationException() {
	}

	public SystemCommunicationException(String message) {
		super(message);
	}

	public SystemCommunicationException(String message, int errorcode) {
		super(message, errorcode);
	}
    
}


