package common.tx.exception;


/**
 * There is no coordinator associated with the target.
 */
public class ParticipantCancelledException extends SystemException {

	public ParticipantCancelledException() {
		super();
	}

	public ParticipantCancelledException(String message) {
		super(message);
	}

	public ParticipantCancelledException(String message, int errorcode) {
		super(message, errorcode);
	}
    
}


