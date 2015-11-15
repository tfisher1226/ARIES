package common.tx.exception;


public class NoTransactionException extends Exception {
	
    public NoTransactionException() {
    }

    public NoTransactionException(String message) {
        super(message);
    }
    
}