package common.tx.exception;


/**
 * There is no coordinator associated with the target.
 */
public class HeuristicCancelException extends SystemException {

	public HeuristicCancelException() {
	}

	public HeuristicCancelException(String message) {
		super(message);
	}

	public HeuristicCancelException(String message, int errorcode) {
		super(message, errorcode);
	}

}


