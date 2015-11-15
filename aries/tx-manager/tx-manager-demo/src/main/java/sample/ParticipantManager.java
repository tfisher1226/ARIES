package sample;


public interface ParticipantManager {

	public boolean prepare(Object transactionId);
	
	public void commit(Object transactionId);

	public void rollback(Object transactionId);

}
