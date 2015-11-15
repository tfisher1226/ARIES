package org.aries.tx;

import org.aries.Assert;

import common.tx.state.ServiceStateManager;


public class MockTransactional implements Transactional {

	private ServiceStateManager<?> stateManager;
	
	private String instanceName;

	private String correlationId;

	private String transactionId;
	
	private boolean prepared;

	private boolean committed;

	private boolean rollbackOnly;

	private boolean rolledBack;

	
	public MockTransactional(String instanceName) {
		this.instanceName = instanceName;
	}
	
	@Override
	public String getName() {
		return instanceName;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setTransactonId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
	public boolean isActive() {
		return transactionId != null && !committed && !rolledBack;
	}

	public boolean isPrepared() {
		return prepared;
	}

	public boolean isCommitted() {
		return committed;
	}

	public boolean isRollbackOnly() {
		return rollbackOnly;
	}
	
	public boolean isRolledBack() {
		return rolledBack;
	}


	public void setServiceStateManager(ServiceStateManager<?> stateManager) {
		this.stateManager = stateManager;
	}

	
	@Override
	public boolean prepare(String transactionId) {
		Assert.equals(this.transactionId, transactionId);
		boolean status = stateManager.prepare(transactionId);
		prepared = true;
		return status;
	}

	@Override
	public void commit(String transactionId) {
		Assert.equals(this.transactionId, transactionId);
		stateManager.commit(transactionId);
		committed = true;
	}

	@Override
	public void rollback(String transactionId) {
		Assert.equals(this.transactionId, transactionId);
		stateManager.rollback(transactionId);
		rolledBack = true;
	}
	
}
