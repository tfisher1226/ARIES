package common.tx.service.completion;

import javax.transaction.SystemException;


public class CompletionStub {
	
    private String _id;

    
	public CompletionStub(String id) throws Exception {
		this._id = id;
	}

	public void commit() throws SystemException {
//        try {
//            CompletionCoordinatorClient.getClient().sendCommit(new InstanceIdentifier(_id)) ;
//            callback.waitUntilTriggered() ;
//        } catch (Fault e) {
//        } catch (Throwable e) {
//            e.printStackTrace();
//            throw new SystemException(e.getMessage());
//        }
//
//        if (callback.hasTriggered()) {
//            if (callback.receivedCommitted()) {
//                return;
//            } else if (callback.receivedAborted()) {
//                throw new SystemException("Aborted");
//            }
//            final SoapFault soapFault = callback.getSoapFault() ;
//            if ((soapFault != null) && ArjunaTXConstants.UNKNOWNTRANSACTION_ERROR_CODE_QNAME.equals(soapFault.getSubcode())) {
//                throw new UnknownTransactionException();
//            }
//        }
//
//        throw new SystemException();
	}

	public void rollback() throws SystemException {
//        final MAP map = AddressingHelper.createNotificationContext(MessageId.getMessageId()) ;
//        final CompletionStub.RequestCallback callback = new CompletionStub.RequestCallback() ;
//        final CompletionInitiatorProcessor completionInitiator = CompletionInitiatorProcessor.getProcessor() ;
//        completionInitiator.registerCallback(_id, callback) ;
//        try {
//            CompletionCoordinatorClient.getClient().sendRollback(_completionCoordinator, map, new InstanceIdentifier(_id)) ;
//            callback.waitUntilTriggered() ;
//        } catch (final Throwable e) {
//            e.printStackTrace();
//            throw new SystemException(e.getMessage());
//        } finally {
//            completionInitiator.removeCallback(_id) ;
//        }
//
//        if (callback.hasTriggered()) {
//            if (callback.receivedAborted()){
//                return;
//            }
//            final SoapFault soapFault = callback.getSoapFault() ;
//            if ((soapFault != null) && ArjunaTXConstants.UNKNOWNTRANSACTION_ERROR_CODE_QNAME.equals(soapFault.getSubcode())) {
//                throw new UnknownTransactionException();
//            }
//        }
//
//        throw new SystemException() ;
	}

}
