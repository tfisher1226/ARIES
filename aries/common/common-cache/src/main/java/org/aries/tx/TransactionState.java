package org.aries.tx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class TransactionState {

	private String transactionId;
	
	private String correlationId;
	
	private List<String> commands = new ArrayList<String>();
	
	private AtomicBoolean shutdown = new AtomicBoolean(false);
	
	private boolean prepared;
	
	private int preparedResult;

	
	public TransactionState(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void addCommand(String operation) {
		checkShutdown();
		commands.add(operation);
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setPrepared(boolean prepared) {
		this.prepared = prepared;
	}

	public boolean isPrepared() {
		return prepared;
	}

	public void setPreparedResult(int preparedResult) {
		this.preparedResult = preparedResult;
	}

	public int getPreparedResult() {
		return preparedResult;
	}

	private void checkShutdown() {
		if (shutdown.get()) {
			throw new IllegalStateException("Disposed");
		}
	}

	public void shutdown() {
		shutdown.set(false);
	}
	
	public int hashCode() {
		return transactionId.hashCode();
	}

	public boolean equals(Object object) {
		if (object instanceof TransactionState) {
			TransactionState other = (TransactionState) object;
			return other.getTransactionId().equals(getTransactionId());
		}
		return false;
	}
	
	public String toString() {
		return transactionId.toString();
	}

}
