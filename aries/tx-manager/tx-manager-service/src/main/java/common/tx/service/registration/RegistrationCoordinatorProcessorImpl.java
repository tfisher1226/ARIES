package common.tx.service.registration;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.CoordinationConstants;
import common.tx.InstanceIdentifier;
import common.tx.exception.AlreadyRegisteredException;
import common.tx.exception.InvalidProtocolException;
import common.tx.exception.InvalidStateException;
import common.tx.exception.NoActivityException;
import common.tx.model.Event;
import common.tx.model.Header;
import common.tx.model.context.RegisterResponseType;
import common.tx.model.context.RegisterType;


public class RegistrationCoordinatorProcessorImpl extends RegistrationCoordinatorProcessor {

	private static Log log = LogFactory.getLog(RegistrationCoordinatorProcessor.class);

	
	/**
	 * Register the participant in the protocol.
	 */
	public RegisterResponseType register(Header header, RegisterType request, boolean isSecure) throws Event {
		RegistrarMapper registrarMapper = RegistrarMapper.getInstance();
		String protocolIdentifier = request.getProtocolIdentifier();
		Registrar registrar = registrarMapper.getRegistrar(protocolIdentifier);

		if (registrar == null) {
			Event fault = new Event();
			fault.setFaultstring("Unknown protocol identifier: "+protocolIdentifier);
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_UNKNOWN_PROTOCOL_ID_QNAME);
			//log.error(fault.getFaultstring());
			throw fault;
		}
		
		try {
			W3CEndpointReference participantProtocolService = request.getParticipantProtocolService();
			InstanceIdentifier instanceIdentifier = header.getInstanceIdentifier();
			String participantId = header.getParticipantId();
			W3CEndpointReference coordinationProtocolService = registrar.register(participantProtocolService, protocolIdentifier, instanceIdentifier, participantId, isSecure);
			RegisterResponseType response = new RegisterResponseType();
			response.setCoordinatorProtocolService(coordinationProtocolService);
			return response;

		} catch (AlreadyRegisteredException e) {
			Event fault = new Event();
			fault.setFaultstring("Already Registered: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.WSCOOR_ERROR_CODE_ALREADY_REGISTERED_QNAME);
			throw fault;

		} catch (InvalidProtocolException e) {
			Event fault = new Event();
			fault.setFaultstring("Invalid Protocol: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.WSCOOR_ERROR_CODE_INVALID_PROTOCOL_QNAME);
			throw fault;

		} catch (InvalidStateException e) {
			Event fault = new Event();
			fault.setFaultstring("Invalid State: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.WSCOOR_ERROR_CODE_INVALID_STATE_QNAME);
			throw fault;

		} catch (NoActivityException e) {
			Event fault = new Event();
			fault.setFaultstring("No Activity: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_NO_ACTIVITY_QNAME);
			throw fault;

		} catch (Throwable e) {
			Event fault = new Event();
			fault.setFaultstring("Unexpected Error: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_UNEXPECTED_ERROR_QNAME);
			log.error(fault.getFaultstring());
			throw fault;
		}
	}

}
