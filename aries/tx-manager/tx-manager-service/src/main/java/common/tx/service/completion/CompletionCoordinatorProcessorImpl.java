package common.tx.service.completion;

import org.aries.tx.util.ActivatedObjectProcessor;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.InstanceIdentifier;
import common.tx.exception.SystemException;
import common.tx.exception.TransactionRolledBackException;
import common.tx.exception.UnknownTransactionException;
import common.tx.model.Header;
import common.tx.model.Notification;


public class CompletionCoordinatorProcessorImpl extends CompletionCoordinatorProcessor {

	//private static Log log = LogFactory.getLog(CompletionCoordinatorProcessor.class);

	private ActivatedObjectProcessor activatedObjectProcessor = new ActivatedObjectProcessor();

	public void activateParticipant(CompletionCoordinatorParticipant participant, String identifier) {
		activatedObjectProcessor.activateObject(participant, identifier);
	}

	public void deactivateParticipant(CompletionCoordinatorParticipant participant) {
		activatedObjectProcessor.deactivateObject(participant);
	}

	private CompletionCoordinatorParticipant getParticipant(InstanceIdentifier instanceIdentifier) {
		if (instanceIdentifier == null)
			System.out.println();
		String identifier = (instanceIdentifier != null ? instanceIdentifier.getInstanceIdentifier() : null);
		return (CompletionCoordinatorParticipant) activatedObjectProcessor.getObject(identifier);
	}

	public void commit(Header header, Notification notification) {
		InstanceIdentifier instanceIdentifier = header.getInstanceIdentifier();
		String transactionId = instanceIdentifier.getInstanceIdentifier();
		CompletionCoordinatorParticipant participant = getParticipant(instanceIdentifier);

		if (participant == null) {
			//TODO does this make sense here?
			Fault fault = new Fault();
			fault.setFaultstring("Unknown Participant: "+transactionId);
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_UNKNOWN_PARTICIPANT_QNAME);
			CompletionInitiatorClient.getClient().sendFault(fault, transactionId);
			return;
		}

		try {
			participant.commit(transactionId);
			
		} catch (TransactionRolledBackException trbe) {
			CompletionInitiatorClient.getClient().sendAborted(participant.getParticipant(), transactionId);
			return;
			
		} catch (UnknownTransactionException e) {
			Fault fault = new Fault();
			fault.setFaultstring("Unknown Transaction");
			fault.setFaultcode(CoordinationConstants.WSAT_ERROR_CODE_UNKNOWN_TRANSACTION_QNAME);
			CompletionInitiatorClient.getClient().sendFault(participant.getParticipant(), fault, transactionId);
			return;
			
		} catch (SystemException e) {
			Fault fault = new Fault();
			fault.setFaultstring("System Exception: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_SYSTEM_EXCEPTION_QNAME);
			CompletionInitiatorClient.getClient().sendFault(participant.getParticipant(), fault, transactionId);
			return;
			
		} catch (Throwable e) {
			Fault fault = new Fault();
			fault.setFaultstring("Unexpected Error: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_UNEXPECTED_ERROR_QNAME);
			CompletionInitiatorClient.getClient().sendFault(participant.getParticipant(), fault, transactionId);
			return;
		}
		
		CompletionInitiatorClient.getClient().sendCommitted(participant.getParticipant(), transactionId);
	}

	public void rollback(Header header, Notification notification) {
		InstanceIdentifier instanceIdentifier = header.getInstanceIdentifier();
		String transactionId = instanceIdentifier.getInstanceIdentifier();
		CompletionCoordinatorParticipant participant = getParticipant(instanceIdentifier);

		if (participant == null) {
			//TODO does this make sense here?
			Fault fault = new Fault();
			fault.setFaultstring("Unknown Participant: "+instanceIdentifier);
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_UNKNOWN_PARTICIPANT_QNAME);
			CompletionInitiatorClient.getClient().sendFault(fault, transactionId);
			return;
		}

		try {
			participant.rollback(transactionId);
			
		} catch (UnknownTransactionException e) {
			Fault fault = new Fault();
			fault.setFaultstring("Unknown Transaction");
			fault.setFaultcode(CoordinationConstants.WSAT_ERROR_CODE_UNKNOWN_TRANSACTION_QNAME);
			CompletionInitiatorClient.getClient().sendFault(participant.getParticipant(), fault, transactionId);
			return;
			
		} catch (SystemException e) {
			Fault fault = new Fault();
			fault.setFaultstring("System Exception: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_SYSTEM_EXCEPTION_QNAME);
			CompletionInitiatorClient.getClient().sendFault(participant.getParticipant(), fault, transactionId);
			return;
			
		} catch (Throwable e) {
			Fault fault = new Fault();
			fault.setFaultstring("Unexpected Error: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_UNEXPECTED_ERROR_QNAME);
			CompletionInitiatorClient.getClient().sendFault(participant.getParticipant(), fault, transactionId);
			return;
		}

		CompletionInitiatorClient.getClient().sendAborted(participant.getParticipant(), transactionId);

	}

}
