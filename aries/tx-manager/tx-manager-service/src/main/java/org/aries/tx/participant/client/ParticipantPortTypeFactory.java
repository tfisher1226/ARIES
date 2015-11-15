package org.aries.tx.participant.client;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.service.AbstractPortTypeFactory;


public class ParticipantPortTypeFactory extends AbstractPortTypeFactory {

    private static ThreadLocal<ParticipantService> participantService = new ThreadLocal<ParticipantService>();

    private static synchronized ParticipantService getParticipantService() {
        if (participantService.get() == null)
        	participantService.set(new ParticipantService());
        return participantService.get();
    }

    public static ParticipantPortType getParticipantPort() {
        ParticipantPortType port = getParticipantService().getPort(ParticipantPortType.class);
        initializePort(port);
        return port;
    }

    public static ParticipantPortType getParticipantPort(W3CEndpointReference endpointReference) {
        ParticipantPortType port = getParticipantService().getPort(endpointReference, ParticipantPortType.class);
        initializePort(port);
        return port;
    }

    public static ParticipantPortType getParticipantPort(W3CEndpointReference endpointReference, String participantId, String coordinatorId) {
        ParticipantPortType port = getParticipantService().getPort(endpointReference, ParticipantPortType.class);
        initializePort(port, null, participantId, coordinatorId);
        return port;
    }

}
