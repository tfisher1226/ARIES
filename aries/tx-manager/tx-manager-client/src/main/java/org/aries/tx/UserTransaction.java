package org.aries.tx;

import common.tx.exception.HeuristicMixedException;
import common.tx.exception.HeuristicRollbackException;
import common.tx.exception.NotSupportedException;
import common.tx.exception.RollbackException;
import common.tx.exception.SystemException;


public interface UserTransaction /*extends javax.transaction.UserTransaction*/ {

	public int getStatus();

	public String getState();

	public String getTransactionId();

	public int getTransactionTimeout();

	public void setTransactionTimeout(int seconds);

	public String begin() throws NotSupportedException, SystemException;

	public String begin(long timeout) throws NotSupportedException, SystemException;

	public String beginSubordinate(int timeout) throws SystemException;

	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException;

	public void rollback() throws IllegalStateException, SecurityException, SystemException;

	public void setRollbackOnly() throws IllegalStateException, SystemException;

}
