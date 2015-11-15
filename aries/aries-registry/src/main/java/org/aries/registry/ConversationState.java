package org.aries.registry;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;


public class ConversationState implements Serializable {

	private String correlationId;

	private String originatingChannel;
	
	private String originatingTransport;

	//private List<String> commands = new ArrayList<String>();
	
	private AtomicBoolean shutdown = new AtomicBoolean(false);
	
	private boolean prepared;
	
	private int preparedResult;

	
	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
//	public void addCommand(String operation) {
//		checkShutdown();
//		commands.add(operation);
//	}

//	public List<String> getCommands() {
//		return commands;
//	}

	public String getOriginatingChannel() {
		return originatingChannel;
	}

	public void setOriginatingChannel(String originatingChannel) {
		this.originatingChannel = originatingChannel;
	}

	public String getOriginatingTransport() {
		return originatingTransport;
	}

	public void setOriginatingTransport(String originatingTransport) {
		this.originatingTransport = originatingTransport;
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
		return correlationId.hashCode();
	}

	public boolean equals(Object object) {
		if (object instanceof ConversationState) {
			ConversationState other = (ConversationState) object;
			return other.getCorrelationId().equals(correlationId);
		}
		return false;
	}
	
	public String toString() {
		return correlationId.toString();
	}

}
