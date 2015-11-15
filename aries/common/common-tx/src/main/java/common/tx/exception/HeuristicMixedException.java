package common.tx.exception;


/**
 * There is no coordinator associated with the target.
 */
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


