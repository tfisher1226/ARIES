package org.aries.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractManager {
	
	private Log log = LogFactory.getLog(getClass());
	

	public abstract void initialize();
	
	public abstract void reset();

	public abstract void close();

	
//	protected String beginTransaction() {
//		String transactionId = null;
//		try {
//			//if (Transport.valueOf(transport) == Transport.WS) {
//			UserTransaction userTransaction = UserTransactionFactory.userTransaction();
//				userTransaction.begin();
//				transactionId = userTransaction.toString();
//			//}
//		} catch (WrongStateException e) {
//			processWrongStateException(e);
//		} catch (SystemException e) {
//			processException(e);
//		}
//		return transactionId;
//	}
//
//	protected void commitTransaction() {
//		try {
//			//if (Transport.valueOf(transport) == Transport.WS) {
//			UserTransaction userTransaction = UserTransactionFactory.userTransaction();
//				userTransaction.commit();
//			//}
//		} catch (UnknownTransactionException e) {
//			processUnknownTransactionException(e);
//		} catch (TransactionRolledBackException e) {
//			processTransactionRolledBackException(e);
//		} catch (WrongStateException e) {
//			processWrongStateException(e);
//		} catch (SecurityException e) {
//			processSecurityException(e);
//		} catch (SystemException e) {
//			processSystemException(e);
//		}
//	}
//
//	protected void rollbackTransaction() {
//		try {
//			//if (Transport.valueOf(transport) == Transport.WS) {
//			UserTransaction userTransaction = UserTransactionFactory.userTransaction();
//			userTransaction.rollback();
//			//}
//		} catch (UnknownTransactionException e) {
//			processUnknownTransactionException(e);
//		} catch (WrongStateException e) {
//			processWrongStateException(e);
//		} catch (SecurityException e) {
//			processSecurityException(e);
//		} catch (SystemException e) {
//			processSystemException(e);
//		}
//	}
//
//	protected void processUnknownTransactionException(UnknownTransactionException exception) {
//		processException("No transaction context", exception);
//	}
//
//	private void processTransactionRolledBackException(TransactionRolledBackException exception) {
//		processException("Transaction termination requested", exception);
//	}
//
//	protected void processWrongStateException(WrongStateException exception) {
//		processException("Unexpected transaction context state", exception);
//	}
//
//	protected void processSecurityException(SecurityException exception) {
//		processException("Transaction permission denied", exception);
//	}
//
//	protected void processSystemException(SystemException exception) {
//		processException("Unexpected system exception", exception);
//	}
//
//	protected void processException(Exception exception) {
//		processException("Unexpected exception", exception);
//	}
//
//	protected void processException(String message, Exception exception) {
//		log.error(message, exception);
//		throw new RuntimeException(message, exception);
//	}

}
