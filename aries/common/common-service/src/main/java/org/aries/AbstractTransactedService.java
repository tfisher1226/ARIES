package org.aries;


public abstract class AbstractTransactedService<T /*extends AbstractServiceState*/> extends AbstractService {

	public AbstractTransactedService() {
		initialize(getServiceId());
	}

	public AbstractTransactedService(String serviceId) {
		initialize(serviceId);
	}

//	protected ExecutionManager<T> createExecutionManager() {
//		ServiceRepositoryOLD serviceRepository = BeanContext.get("org.aries.serviceRepository");
//		ServiceDescripter serviceDescripter = serviceRepository.getServiceDescripter(serviceId);
//		ExecutionManager<T> executionManager = new ExecutionManager<T>(serviceDescripter, webServiceContext);
//		return executionManager;
//	}
	

//	private String transactionId;
//
//	private String subTransactionId;
//
//	
//	protected String getTransactionId() {
//		return transactionId;
//	}
//
//	protected void setTransactionId(String transactionId) {
//		this.transactionId = transactionId;
//	}
//
//	protected String beginTransaction() {
//		try {
//			//if (Transport.valueOf(transport) == Transport.WS) {
//			UserTransaction userTransaction = UserTransactionFactory.userTransaction();
//				userTransaction.begin();
//				this.transactionId = userTransaction.toString();
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
//	
//	protected String beginSubordinateTransaction() {
//		try {
//			//if (Transport.valueOf(transport) == Transport.WS) {
//			UserTransaction userTransaction = UserTransactionFactory.userSubordinateTransaction();
//			userTransaction.begin();
//			this.subTransactionId = userTransaction.toString();
//			//}
//		} catch (WrongStateException e) {
//			processWrongStateException(e);
//		} catch (SystemException e) {
//			processException(e);
//		}
//		return subTransactionId;
//	}
//
//
////	protected void markRollbackOnly(String transactionId) {
////		try {
////			//if (Transport.valueOf(transport) == Transport.WS) {
////			UserTransaction userTransaction = UserTransactionFactory.userSubordinateTransaction();
////			userTransaction.
////			this.subTransactionId = userTransaction.toString();
////			//}
////		} catch (WrongStateException e) {
////			processWrongStateException(e);
////		} catch (SystemException e) {
////			processException(e);
////		}
////		return subTransactionId;
////	}
//
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
