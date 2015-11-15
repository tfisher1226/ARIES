package common.tx.state;


public interface ParticipantManager {

	public boolean prepare(String transactionId);
	
	public void commit(String transactionId);

	public void rollback(String transactionId);

}
