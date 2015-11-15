package common.tx.exception;


public class HeuristicMixedException extends SystemException {

	public HeuristicMixedException() {
	}

	public HeuristicMixedException(String message) {
		super(message);
	}

	public HeuristicMixedException(String message, int errorcode) {
		super(message, errorcode);
	}
    
}


