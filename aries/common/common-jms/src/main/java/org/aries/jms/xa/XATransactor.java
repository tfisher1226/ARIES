package org.aries.jms.xa;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;


public class XATransactor {

	private static final Log log = LogFactory.getLog(XATransactor.class);

	private static final int TRANSACTION_TIMEOUT = 360;
	
	private TransactionManager transactionManager;

	private Transaction transaction;

	private AtomicBoolean started;
	
	private int timeout;

	protected Object mutex;

    
	public XATransactor() {
		mutex = new Object();
		started = new AtomicBoolean(false);
		timeout = TRANSACTION_TIMEOUT;
	}
	
	public Transaction getTransaction() {
		Assert.isTrue(started, "Transaction Manager not started");
		return transaction;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}
    
	public void setTransactionManager(TransactionManager value) {
		transactionManager = value;
	}
	
	public int getTransactionTimeout() {
		return timeout;
	}
	
	public void setTransactionTimeout(int value) {
		timeout = value;
	}
	

	public void reset() {
		synchronized (mutex) {
			transaction = null;
		}
	}
	
	public void begin() {
		synchronized (mutex) {
			Assert.isFalse(started);
			try {
				transactionManager.begin();
				transactionManager.setTransactionTimeout(timeout);
				transaction = transactionManager.getTransaction();
				started.set(true);
			} catch (NotSupportedException e) {
				log.error(e);
			} catch (SystemException e) {
				log.error(e);
			}
		}
	}

	public boolean isStarted() {
		return started.get();
	}

	
	public void enlist(XAResource resource) {
		Assert.isTrue(started);
		enlist(transaction, resource);
	}
	
//    public void enlist(XAResource resource) throws IllegalStateException, RollbackException, SystemException {
//    	transaction.enlistResource(resource);
//    }
//    
//    public void delist(XAResource resource) throws IllegalStateException, RollbackException, SystemException {
//    	transaction.delistResource(resource, 0);
//    }

	protected void enlist(Transaction transaction, XAResource resource) {
		synchronized (mutex) {
			Assert.isTrue(started);
			Assert.notNull(transaction);
			Assert.notNull(resource);
			try {
				transaction.enlistResource(resource);
			} catch (IllegalStateException e) {
				log.error(e);
			} catch (RollbackException e) {
				log.error(e);
			} catch (SystemException e) {
				log.error(e);
			}
		}
	}
	
	public void delistSuccess(XAResource resource) {
		delist(resource, XAResource.TMSUCCESS);
	}

	public void delistFail(XAResource resource) {
		delist(resource, XAResource.TMFAIL);
	}

	public void delist(XAResource resource, int flag) {
		delist(transaction, resource, flag);
	}
	
	protected void delist(Transaction transaction, XAResource resource, int flag) {
		synchronized (mutex) {
			Assert.isTrue(started);
			Assert.notNull(transaction);
			Assert.notNull(resource);
			try {
				transaction.delistResource(resource, flag);
			} catch (IllegalStateException e) {
				log.error(e);
			} catch (SystemException e) {
				log.error(e);
			}
		}
	}
	
	public void rollback() {
		synchronized (mutex) {
			Assert.isTrue(started);
			try {
				transactionManager.rollback();
			} catch (IllegalStateException e) {
				log.error(e);
			} catch (SecurityException e) {
				log.error(e);
			} catch (SystemException e) {
				log.error(e);
			}
		}
	}
	
	public void commit() {
		synchronized (mutex) {
			Assert.isTrue(started);
			try {
				transactionManager.commit();
			} catch (SecurityException e) {
				log.error(e);
			} catch (IllegalStateException e) {
				log.error(e);
			} catch (RollbackException e) {
				log.error(e);
			} catch (HeuristicMixedException e) {
				log.error(e);
			} catch (HeuristicRollbackException e) {
				log.error(e);
			} catch (SystemException e) {
				log.error(e);
			}
		}
	}

}
