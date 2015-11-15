package common.tx.exception;


public class HeuristicRollbackException extends SystemException {

	public HeuristicRollbackException() {
	}

	public HeuristicRollbackException(String message) {
		super(message);
	}

	public HeuristicRollbackException(String message, int errorcode) {
		super(message, errorcode);
	}
    
}


