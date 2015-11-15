package common.tx.exception;


@SuppressWarnings("serial")
public class InvalidStateException extends Exception {

	public InvalidStateException() {
	}

	public InvalidStateException(String message) {
		super(message);
	}

}
