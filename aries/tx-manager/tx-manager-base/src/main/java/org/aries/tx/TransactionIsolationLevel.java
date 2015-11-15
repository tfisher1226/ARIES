package org.aries.tx;


public enum TransactionIsolationLevel {

	TRANSACTION_READ_UNCOMMITTED("transaction_read_uncommitted"),
	
	TRANSACTION_READ_COMMITTED("transaction_read_committed"),
	
	TRANSACTION_REPEATABLE_READ("transaction_repeatable_read"), 
	
	TRANSACTION_SERIALIZABLE("transaction_repeatable_read");
	
	
    private final String value;

    TransactionIsolationLevel(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransactionIsolationLevel fromValue(String v) {
        for (TransactionIsolationLevel c: TransactionIsolationLevel.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
