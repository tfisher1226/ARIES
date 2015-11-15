package common.tx.state;

import java.io.IOException;

import javax.xml.ws.WebServiceException;

import com.arjuna.ats.arjuna.coordinator.BasicAction;
import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;



public abstract class AbstractStateManager<T extends ServiceState> extends ServiceStateManager<T> {

	protected abstract T createState();

	protected abstract T resetState();

	
	private boolean autoCommitMode;

	/** The object used for wait/notify in manual commit mode. */
	private Object preparationMutex = new Object();

	/** The waiting status, when in manual commit mode. */
	private boolean isPreparationWaiting;

	/** The user specified outcome when in manual commit mode. */
	private boolean isCommit;
	

	/**
	 * Create and initialize a new RestaurantManager instance. If the super constructor does
	 * not restore a previously persisted current state then create and persist an initial state
	 * using appropriate default values.
	 */
	protected AbstractStateManager() {
		if (currentState == null) {
			// we need to create a new initial state and persist it to disk
			currentState = createState();
			Object txId = "initialisation-transaction-" + System.currentTimeMillis();
			try {
				writeShadowState(txId, currentState);
				commitShadowState(txId);
			} catch (IOException e) {
				clearShadowState(txId);
				System.out.println("error : unable to initialise restaurant manager state " + e);
			}
		}

		isCommit = true;
		autoCommitMode = true;
		isPreparationWaiting = false;
	}

	public boolean isAutoCommitMode() {
		return autoCommitMode;
	}

	public void setAutoCommitMode(boolean autoCommit) {
		autoCommitMode = autoCommit;
	}

	public Object getPreparation() {
		return preparationMutex;
	}

	public boolean getIsPreparationWaiting() {
		return isPreparationWaiting;
	}

	public void setIsPreparationWaiting(boolean isWaiting) {
		isPreparationWaiting = isWaiting;
	}

	public void manualCommit() {
		if (autoCommitMode)
			throw new RuntimeException("Auto commit is enabled");
		forceCommit();
	}

	public void forceCommit() {
		synchronized (preparationMutex) {
    		isCommit = true;
            preparationMutex.notify();
        }
	}

	public void manualAbort() {
		if (autoCommitMode)
			throw new RuntimeException("Auto commit is enabled");
		forceAbort();
	}

	public void forceAbort() {
		synchronized (preparationMutex) {
    		isCommit = false;
            preparationMutex.notify();
        }
	}

	
	/*****************************************************************************/
	/* Implementation of execution entry method                                  */
	/*****************************************************************************/

	public synchronized void updateState(ServiceStateProcessor<T> processor) {
//		String transactionId = null;
//		try {
//			javax.transaction.TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
//			TransactionImple transaction = (TransactionImple) transactionManager.getTransaction();
//			if (transaction == null)
//				return;
//			
//			int status = transaction.getStatus();
//			transactionId = transaction.get_uid().stringForm();
//			//transactionId = TransactionManager.getTransactionId();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return;
//		}

		BasicAction currentAction = ThreadActionData.currentAction();
		if (currentAction == null)
			return;
		String transactionId = ThreadActionData.currentAction().get_uid().toString();
		if (transactionId == null)
			//TODO follow specified TX policy
			return;

		// we cannot proceed while a (another) prepare is in progress
		while (isLocked()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// ignore
			}
		}

		T derivedState = getDerivedState(transactionId);
		
		if (derivedState != null) {
			if (!processor.validateState(derivedState)) {
				//TODO get error message from processor
				throw new WebServiceException("Invalid request");
			}

			// update the number of booked and free seats in the derived state
			processor.updateState(derivedState);

		} else {
			if (!processor.validateState(currentState)) {
				//TODO get error message from processor
				throw new WebServiceException("Invalid request");
			}

			// create a state derived from the current state which holds the new value(s)
			T childState = currentState.getDerivedState();

			// updates current state using values from the specified derived state
			processor.updateState(childState);

			// install the specified derived state as the current transaction state
			putDerivedState(transactionId, childState);
			//currentState = childState;
		}
	}
	
	
	/*****************************************************************************/
	/* Implementation of execution entry method                                  */
	/*****************************************************************************/

	public synchronized void execute(ServiceStateProcessor<T> processor, String transactionId) {
		// we cannot proceed while a (another) prepare is in progress
		while (isLocked()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// ignore
			}
		}

		T derivedState = getDerivedState(transactionId);
		
		if (derivedState != null) {
			if (!processor.validateState(derivedState)) {
				//TODO get error message from processor
				throw new WebServiceException("Invalid request");
			}

			// update the number of booked and free seats in the derived state
			processor.updateState(derivedState);

		} else {
			if (!processor.validateState(currentState)) {
				//TODO get error message from processor
				throw new WebServiceException("Invalid request");
			}

			// create a state derived from the current state which holds the new value(s)
			T childState = currentState.getDerivedState();

			// updates the current state using values from the specified derived state
			processor.updateState(childState);

			// install the specified derived state as the current transaction state
			putDerivedState(transactionId, childState);
		}
	}

	/*****************************************************************************/
	/* Implementation of inherited abstract state management API                 */
	/*****************************************************************************/

//	//@Override
//	// undo all existing bookings
//	protected T updateState(AbstractProcessor<T> processor) {
//
//		if (!processor.validateRequest(currentState)) {
//			//TODO get error message from processor
//			throw new WebServiceException("Invalid request");
//		}
//
//		// create a state derived from the current state which reflects the new booking count
//		T derivedState = currentState.getDerivedState();
//
//		// update the number of booked and free seats in the derived state
//		processor.updateState(derivedState);
//		return derivedState;
//	}
	
	//@Override
	// undo all existing bookings
	protected T resetState(AbstractProcessor<T> processor) {
		T freshState = currentState.getDerivedState();

		processor.resetState(freshState);
		
		Object txId = "reset-transaction";
		try {
			writeShadowState(txId, freshState);
			commitShadowState(txId);
			
		} catch (IOException e) {
			clearShadowState(txId);
			System.out.println("error : unable to reset restaurant manager state " + e);
		}

		return freshState;
	}

	/**
	 * method called during prepare of local state changes allowing the user to force a prepare failure
	 * @return true if the prepare should succeed and false if it should fail
	 */
	public boolean confirmPrepare() {
		if (autoCommitMode) {
			return true;
		} else {
			// need to wait for the user to decide whether to go ahead or not with this participant
			isPreparationWaiting = true;
			synchronized (preparationMutex) {
				try {
					preparationMutex.wait();
				} catch (InterruptedException e) {
					// ignore
				}
			}
			isPreparationWaiting = false;
			return isCommit;
		}
	}


	public synchronized void reset() {
		// we cannot proceed while a prepare is in progress
		while (isLocked()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// ignore
			}
		}

		currentState = resetState();

		// remove any in-progress transactions
		clearTransactions();
	}

	
	
	/*
	 * Temporary
	 */
	
	protected boolean transacted = false;

	protected boolean isolated = true;

	protected String correlationId;
	
	protected String transactionId;
	
	
	public boolean isTransacted() {
		return transacted;
	}
	
	public void setTransacted(boolean transacted) {
		this.transacted = transacted;
	}
	
	public boolean isIsolated() {
		return isolated;
	}
	
	public void setIsolated(boolean isolated) {
		this.isolated = isolated;
	}
	
//	@Override
//	public String getCorrelationId() {
//		return correlationId;
//	}
//
//	@Override
//	public String getTransactionId() {
//		return transactionId;
//	}
//
//	public void setTransactionId(String transactionId) {
//		this.transactionId = transactionId;
//		setTransacted(true);
//	}
	
}
