package common.tx.exception;


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


