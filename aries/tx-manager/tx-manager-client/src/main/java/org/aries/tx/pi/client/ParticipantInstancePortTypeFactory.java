package org.aries.tx.pi.client;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.service.AbstractPortTypeFactory;


public class ParticipantInstancePortTypeFactory extends AbstractPortTypeFactory {

    private static ThreadLocal<ParticipantInstanceService> participantInstanceService = new ThreadLocal<ParticipantInstanceService>();

    private static synchronized ParticipantInstanceService getParticipantInstanceService() {
        if (participantInstanceService.get() == null)
        	participantInstanceService.set(new ParticipantInstanceService());
        return participantInstanceService.get();
    }

    public static ParticipantInstancePortType getParticipantPort() {
        ParticipantInstancePortType port = getParticipantInstanceService().getPort(ParticipantInstancePortType.class);
        initializePort(port);
        return port;
    }

    public static ParticipantInstancePortType getParticipantPort(W3CEndpointReference endpointReference) {
        ParticipantInstancePortType port = getParticipantInstanceService().getPort(endpointReference, ParticipantInstancePortType.class);
        initializePort(port);
        return port;
    }

    public static ParticipantInstancePortType getParticipantPort(W3CEndpointReference endpointReference, String participantId, String coordinatorId) {
        ParticipantInstancePortType port = getParticipantInstanceService().getPort(endpointReference, ParticipantInstancePortType.class);
        initializePort(port, null, participantId, coordinatorId);
        return port;
    }

}
