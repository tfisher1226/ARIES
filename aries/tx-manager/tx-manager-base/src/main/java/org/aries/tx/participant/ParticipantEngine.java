package org.aries.tx.participant;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Notification;


public interface ParticipantEngine {

    public String getCoordinatorId();

    public W3CEndpointReference getCoordinatorEPR();

    public void commit(Notification commit);

    public void prepare(Notification prepare);

    public void rollback(Notification rollback);

    public void fault(Fault fault);

    public void recovery();
    
}
