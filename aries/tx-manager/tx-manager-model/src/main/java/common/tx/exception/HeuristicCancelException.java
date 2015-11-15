package common.tx.exception;


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


