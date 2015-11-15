package aries;

import java.util.concurrent.TimeoutException;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.omg.Security.Initiator;

import common.tx.service.activation.ActivationPortType;
import common.tx.service.completion.CompletionCoordinatorClient;
import common.tx.service.registration.RegistrationPortType;


public class CommitFlow {

	Initiator initiator;
	ActivationParticipant activation;
	RegistrationParticipant registration;
	CompletionInitiatorParticipant completionInitiator;
	CompletionCoordinatorParticipant completionCoordinator;
	
	
	public void begin(int timeout) {
		try {
			CoordinationContextRequest request;
			CoordinationContextResponse response;
			response = sendAndWait(initiator)->activation.createCoordinationContext(request);
			saveContext(response);
			
			RegistrationRequest registrationRequest;
			RegistrationResponse registrationResponse;
			registrationResponse = sendAndWait(initiator)->registration.registerParticipant(registrationRequest);

		} catch (TimeoutException e) {
			//TODO
			
		} catch (Exception e) {
			//TODO
		}
	}
	
	public void commit(int timeout) {
		try {
			CoordinationContextRequest request;
			CoordinationContextResponse response;
			
			Future future = completionCoordinator.sendCommit(identifier);
			future.get(timeout);

		} catch (TimeoutException e) {
			//TODO
			
		} catch (Exception e) {
			//TODO
		}
	}
	
}
