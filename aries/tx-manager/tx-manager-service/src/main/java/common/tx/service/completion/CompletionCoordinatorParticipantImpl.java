package common.tx.service.completion;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.service.coordinator.CoordinatorManager;

import common.tx.exception.SystemException;
import common.tx.exception.TransactionRolledBackException;
import common.tx.exception.UnknownTransactionException;


public class CompletionCoordinatorParticipantImpl implements CompletionCoordinatorParticipant {

	private CoordinatorManager coordinatorManager;

	//private ActivityHierarchy _hier;

	private final boolean deactivate;

	private W3CEndpointReference participant;


	public CompletionCoordinatorParticipantImpl(CoordinatorManager coordinatorManager, boolean deactivate, W3CEndpointReference participant) {
		this.coordinatorManager = coordinatorManager;
		//_hier = hier;
		this.deactivate = deactivate;
		this.participant = participant;
	}


	public W3CEndpointReference getParticipant() {
		return participant;
	}

	public void commit(String transactionId) throws TransactionRolledBackException, UnknownTransactionException, SystemException {
		try {
			//if (_hier != null)
			//	_cm.resume(_hier);
			//_cm.confirm();
			coordinatorManager.commit(transactionId);
		} finally {
			if (deactivate)
				CompletionCoordinatorProcessor.getProcessor().deactivateParticipant(this);
		}
	}

	public void rollback(String transactionId) throws UnknownTransactionException, SystemException {
		try {
			//if (_hier != null)
			//	_cm.resume(_hier);
			coordinatorManager.cancel(transactionId);
//		} catch (InvalidActivityException ex) {
//			throw new UnknownTransactionException();
//		} catch (WrongStateException ex) {
//			throw new SystemException(ex.toString());
//		} catch (ProtocolViolationException ex) {
//			throw new SystemException();
//		} catch (NoCoordinatorException ex) {
//			throw new UnknownTransactionException();
//		} catch (CoordinatorConfirmedException ex) {
//			throw new SystemException();
//		} catch (HeuristicMixedException ex) {
//			throw new SystemException(ex.toString());
//		} catch (HeuristicHazardException ex) {
//			throw new SystemException(ex.toString());
//		} catch (NoPermissionException ex) {
//			throw new SystemException(ex.toString());
//		} catch (SystemException ex) {
//			throw new SystemException(ex.toString());
		} finally {
//			if (deactivate)
//				CompletionCoordinatorProcessor.getProcessor().deactivateParticipant(this);
		}
	}

}
