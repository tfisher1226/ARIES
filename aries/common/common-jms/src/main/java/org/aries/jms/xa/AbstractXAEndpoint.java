package org.aries.jms.xa;

import javax.jms.JMSException;
import javax.jms.XASession;
import javax.transaction.Status;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

import org.aries.Assert;
import org.aries.jms.AbstractEndpoint;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsEndpointDescripter;


public abstract class AbstractXAEndpoint extends AbstractEndpoint {

	protected JmsXASessionAdapter xaSessionAdapter;

	protected TransactionManager transactionManager;


	public AbstractXAEndpoint(JmsXASessionAdapter sessionAdapter, JmsEndpointDescripter specification) {
		super(sessionAdapter, specification);
		xaSessionAdapter = sessionAdapter;
	}

	public XAResource getXAResource() {
		return xaSessionAdapter.getXAResource();
	}

    public XASession getXASession() {
        return xaSessionAdapter.getXASession();
    }

    public JmsSessionAdapter getXASessionAdapter() {
        return xaSessionAdapter;
    }

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
    protected void assureSession() throws JMSException {
		xaSessionAdapter.addAdapterListener(sessionListener);
    	if (!xaSessionAdapter.isInitialized()) {
    		xaSessionAdapter.initialize();
    	}
	}
	

	protected boolean assureTransactionStarted() throws Exception {
		boolean localTransactionStarted = false;
		Transaction transaction = getTransactionManager().getTransaction();
		if (transaction == null) {
			getTransactionManager().begin();
			transaction = getTransactionManager().getTransaction();
			localTransactionStarted = true;
		}
		
		transaction = getTransactionManager().getTransaction();
		assertTransactionActive(transaction);
		getTransactionManager().setTransactionTimeout(/*timeout*/ 60000);
		transaction.enlistResource(getXAResource());
		return localTransactionStarted;
	}

	protected void assertTransactionActive(Transaction transaction) throws Exception {
		int status = transaction.getStatus();
		Assert.isTrue(status == Status.STATUS_ACTIVE, "Invalid transaction state: "+status);
	}

	protected void assureTransactionComplete(boolean localTransactionStarted) throws Exception {
		Transaction transaction = getTransactionManager().getTransaction();
		transaction.delistResource(getXAResource(), XAResource.TMSUCCESS);
		if (localTransactionStarted) {
			/* conclude with success */
			getTransactionManager().commit();
		}
	}

	protected void processTransactionFailure(boolean localTransactionStarted) throws Exception {
		Transaction transaction = getTransactionManager().getTransaction();
		transaction.delistResource(getXAResource(), XAResource.TMFAIL);
		if (localTransactionStarted) {
			getTransactionManager().setRollbackOnly();
		} else {
			if (transaction.getStatus() == Status.STATUS_ACTIVE) {
				log.info("Rolling back message");
				getTransactionManager().rollback();
			}
		}
	}
	
}
