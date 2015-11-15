package common.tx.exception;


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


