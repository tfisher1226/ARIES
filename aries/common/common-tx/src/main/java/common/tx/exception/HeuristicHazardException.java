package common.tx.exception;


/**
 * There is no coordinator associated with the target.
 */
public class HeuristicHazardException extends SystemException {

	public HeuristicHazardException() {
	}

	public HeuristicHazardException(String message) {
		super(message);
	}

	public HeuristicHazardException(String message, int errorcode) {
		super(message, errorcode);
	}
    
}


