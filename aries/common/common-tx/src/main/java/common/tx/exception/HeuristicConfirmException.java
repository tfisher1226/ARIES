package common.tx.exception;


/**
 * There is no coordinator associated with the target.
 */
public class HeuristicConfirmException extends SystemException {

	public HeuristicConfirmException() {
	}

	public HeuristicConfirmException(String message) {
		super(message);
	}

	public HeuristicConfirmException(String message, int errorcode) {
		super(message, errorcode);
	}

}


