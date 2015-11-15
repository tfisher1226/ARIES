package common.tx.service.registration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.service.coordinator.ATCoordinator;
import org.aries.tx.service.coordinator.CoordinatorManager;
import org.aries.tx.service.subordinate.SubordinateATCoordinator;

import tx.manager.registry.ServiceRegistry;

import com.arjuna.ats.arjuna.common.Uid;
import common.tx.CoordinationConstants;
import common.tx.Durable2PCStub;
import common.tx.InstanceIdentifier;
import common.tx.Volatile2PCStub;
import common.tx.exception.AlreadyRegisteredException;
import common.tx.exception.InvalidProtocolException;
import common.tx.exception.InvalidStateException;
import common.tx.exception.NoActivityException;
import common.tx.exception.SystemException;
import common.tx.service.completion.CompletionCoordinatorParticipant;
import common.tx.service.completion.CompletionCoordinatorParticipantImpl;
import common.tx.service.completion.CompletionCoordinatorProcessor;
import common.tx.service.participant.DurableTwoPhaseCommitParticipant;
import common.tx.service.participant.VolatileTwoPhaseCommitParticipant;


public class RegistrarImple implements Registrar {

	private static Log log = LogFactory.getLog(RegistrarImple.class);

	private CoordinatorManager coordinatorManager;

	private Map<String, ATCoordinator> _hierarchies = new ConcurrentHashMap<String, ATCoordinator>();


	public RegistrarImple() throws SystemException {
		//_coordManager = CoordinatorManagerFactory.coordinatorManager();

		// register with mapper using tx id as protocol identifier.
		RegistrarMapper mapper = RegistrarMapper.getInstance();
		mapper.addRegistrar(CoordinationConstants.WSAT_SUB_PROTOCOL_DURABLE_2PC, this);
		mapper.addRegistrar(CoordinationConstants.WSAT_SUB_PROTOCOL_VOLATILE_2PC, this);
		mapper.addRegistrar(CoordinationConstants.WSAT_SUB_PROTOCOL_COMPLETION, this);
	}

	
	public CoordinatorManager getCoordinatorManager() {
		return coordinatorManager;
	}

	public void setCoordinator(CoordinatorManager coordinatorManager) {
		this.coordinatorManager = coordinatorManager;
	}


	/**
	 * Called when a registrar is added to a register mapper. This method will
	 * be called multiple times if the registrar is added to multiple register
	 * mappers or to the same register mapper with different protocol
	 * identifiers.
	 */
	public void install (String protocolIdentifier) {
	}

	/**
	 * Called when a registrar is removed from a register mapper. This method
	 * will be called multiple times if the registrar is removed from multiple
	 * register mappers or from the same register mapper with different protocol
	 * identifiers.
	 */
	public void uninstall(String protocolIdentifier) {
	}
	
	/**
	 * Registers the interest of participant in a particular protocol.
	 *
	 * @param participantProtocolService
	 *            the address of the participant protocol service
	 * @param protocolIdentifier
	 *            the protocol identifier
	 *
	 * @return the PortReference of the coordinator protocol service
	 *
	 * @throws com.arjuna.wsc.AlreadyRegisteredException
	 *             if the participant is already registered for this
	 *             coordination protocol under this activity identifier
	 * @throws com.arjuna.wsc.InvalidProtocolException
	 *             if the coordination protocol is not supported
	 * @throws com.arjuna.wsc.InvalidStateException
	 *             if the state of the coordinator no longer allows registration
	 *             for this coordination protocol
	 * @throws com.arjuna.wsc.NoActivityException
	 *             if the activity does not exist.
	 *
	 */

	/*
	 * TODO
	 *
	 * See comment at head of class definition. We shouldn't have to rely on
	 * thread-to-activity association to register a participant. We currently do
	 * because the code is based on old WS-CAF models that are no longer
	 * applicable. This needs updating!
	 */
	public W3CEndpointReference register(W3CEndpointReference participantProtocolService, String protocolIdentifier, InstanceIdentifier instanceIdentifier, String participantId, boolean isSecure) throws AlreadyRegisteredException, InvalidProtocolException, InvalidStateException, NoActivityException {
		Object tx = _hierarchies.get(instanceIdentifier.getInstanceIdentifier());
		String transactionId = instanceIdentifier.toString();

		if (tx instanceof SubordinateATCoordinator) {
			W3CEndpointReference epr = registerWithSubordinate((SubordinateATCoordinator) tx, participantProtocolService, protocolIdentifier, participantId, isSecure);
			return epr;
		}
		
		// TODO check for AlreadyRegisteredException

		if (CoordinationConstants.WSAT_SUB_PROTOCOL_DURABLE_2PC.equals(protocolIdentifier)) {
			// enlist participant that wraps the requester URI.
			String coordinatorId = "D" + new Uid().stringForm();
			
			try {
				Durable2PCStub participantStub = new Durable2PCStub(transactionId, coordinatorId, participantId, participantProtocolService);
				DurableTwoPhaseCommitParticipant participant = new DurableTwoPhaseCommitParticipant(participantStub, coordinatorId);
				coordinatorManager.enlistParticipant(transactionId, participant);
				//TODO XXXXX _coordManager.suspend();
				
				W3CEndpointReference coordinatorEndpoint = getCoordinator(coordinatorId, isSecure);

//		        ParticipantAdapter participant = null; //createParticipant(transactional, transactionId);
//				ParticipantEngine participantEngine = new ParticipantEngineImpl(participant, participantId, coordinatorEndpoint);
//				ParticipantProcessor.getProcessor().activateParticipant(participantEngine, participantId);

				return coordinatorEndpoint;
				
			} catch (Exception e) {
				throw new InvalidStateException(e.getMessage());
			}

		} else if (CoordinationConstants.WSAT_SUB_PROTOCOL_VOLATILE_2PC.equals(protocolIdentifier)) {
			// enlist participant that wraps the requester URI.
			String coordinatorId = "V" + new Uid().stringForm();

			try {
				Volatile2PCStub participantStub = new Volatile2PCStub(transactionId, coordinatorId, participantId, participantProtocolService);
				VolatileTwoPhaseCommitParticipant participant = new VolatileTwoPhaseCommitParticipant(participantStub);
				coordinatorManager.enlistSynchronization(transactionId, participant);
				//TODO XXXXX _coordManager.suspend();
				
				W3CEndpointReference coordinatorEndpoint = getCoordinator(coordinatorId, isSecure);
				return coordinatorEndpoint;
				
			} catch (Exception e) {
				throw new InvalidStateException(e.getMessage());
			}

		} else if (CoordinationConstants.WSAT_SUB_PROTOCOL_COMPLETION.equals(protocolIdentifier)) {
			try {
				CompletionCoordinatorParticipant participant = new CompletionCoordinatorParticipantImpl(coordinatorManager, true, participantProtocolService);
				CompletionCoordinatorProcessor.getProcessor().activateParticipant(participant, instanceIdentifier.getInstanceIdentifier());
				W3CEndpointReference completionCoordinatorEndpoint = getCompletionCoordinator(instanceIdentifier, isSecure);
				//TODO XXXXX _coordManager.suspend();
				return completionCoordinatorEndpoint;

			} catch (Exception e) {
				throw new InvalidStateException(e.getMessage());
			}

		} else {
			String message = "Invalid type URI: "+CoordinationConstants.WSAT_PROTOCOL+", "+protocolIdentifier;
			log.error(message);
			throw new InvalidProtocolException(message);
		}
	}

	public void associate () throws Exception {
		//String txIdentifier = _coordManager.identifier().toString();
		//ActivityHierarchy hier = _coordManager.suspend();
		//_hierarchies.put(txIdentifier, hier);
	}

	public void associate (ATCoordinator transaction) throws Exception {
		String transactionId = transaction.get_uid().stringForm();
		_hierarchies.put(transactionId, transaction);
	}

	public void disassociate(String transactionId) throws Exception {
		_hierarchies.remove(transactionId);
	}

	private W3CEndpointReference registerWithSubordinate(SubordinateATCoordinator theTx, W3CEndpointReference participantProtocolService, String protocolIdentifier, String participantId, boolean isSecure) throws AlreadyRegisteredException, InvalidProtocolException, InvalidStateException, NoActivityException {
		if (CoordinationConstants.WSAT_SUB_PROTOCOL_DURABLE_2PC.equals(protocolIdentifier)) {
			// enlist participant that wraps the requester URI.
			String transactionId = theTx.get_uid().stringForm();
			String coordinatorId = "D" + new Uid().stringForm();

			try {
				Durable2PCStub participantStub = new Durable2PCStub(transactionId, coordinatorId, participantId, participantProtocolService);
				theTx.enlistParticipant(new DurableTwoPhaseCommitParticipant(participantStub, participantId));
				W3CEndpointReference coordinator = getCoordinator(participantId, isSecure);
				return coordinator;
				
			} catch (Exception e) {
				throw new InvalidStateException(e.getMessage());
			}
			
		} else if (CoordinationConstants.WSAT_SUB_PROTOCOL_VOLATILE_2PC.equals(protocolIdentifier)) {
			// enlist participant that wraps the requester URI.
			String transactionId = theTx.get_uid().stringForm();
			String coordinatorId = "V" + new Uid().stringForm();

			try {
				Volatile2PCStub participantStub = new Volatile2PCStub(transactionId, coordinatorId, participantId, participantProtocolService);
				theTx.enlistSynchronization(new VolatileTwoPhaseCommitParticipant(participantStub));
				W3CEndpointReference coordinator = getCoordinator(participantId, isSecure);
				return coordinator;
				
			} catch (Exception e) {
				throw new InvalidStateException(e.getMessage());
			}
			
		} else if (CoordinationConstants.WSAT_SUB_PROTOCOL_COMPLETION.equals(protocolIdentifier)) {
			// not allowed for subordinate transactions!
			throw new InvalidStateException();
			
		} else {
			String message = "Invalid type URI: "+CoordinationConstants.WSAT_PROTOCOL+", "+protocolIdentifier;
			log.error(message);
			throw new InvalidProtocolException(message);
		}
	}

	private W3CEndpointReference getCompletionCoordinator(InstanceIdentifier instanceIdentifier, boolean isSecure) {
		W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
		String address = ServiceRegistry.getInstance().getServiceURI(CoordinationConstants.COMPLETION_COORDINATOR_SERVICE_NAME);
		builder.serviceName(CoordinationConstants.COMPLETION_COORDINATOR_SERVICE_QNAME);
		builder.endpointName(CoordinationConstants.COMPLETION_COORDINATOR_ENDPOINT_QNAME);
		builder.address(address);
		InstanceIdentifier.setEndpointInstanceIdentifier(builder, instanceIdentifier);
		return builder.build();
	}

	private W3CEndpointReference getCoordinator(String coordinatorId, boolean isSecure) {
		W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
		String address = ServiceRegistry.getInstance().getServiceURI(CoordinationConstants.COORDINATOR_SERVICE_NAME);
		builder.serviceName(CoordinationConstants.COORDINATOR_SERVICE_QNAME);
		builder.endpointName(CoordinationConstants.COORDINATOR_PORT_QNAME);
		builder.address(address);
		InstanceIdentifier.setEndpointInstanceIdentifier(builder, coordinatorId);
		return builder.build();
	}

}
