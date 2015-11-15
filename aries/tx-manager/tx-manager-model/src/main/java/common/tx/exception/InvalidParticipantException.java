package common.tx.exception;


/**
 * The specified participant is invalid in the context used.
 */
@SuppressWarnings("serial")
public class InvalidParticipantException extends Exception {

	public InvalidParticipantException() {
	}

	public InvalidParticipantException(String message) {
		super(message);
	}

}
