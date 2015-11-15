package common.tx.service.coordinator;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Notification;


public interface CoordinatorEngine {

    public W3CEndpointReference getParticipant();

    public void aborted(Notification notification);

    public void committed(Notification notification);

    public void prepared(Notification notification);

    public void readOnly(Notification notification);

    public void fault(Fault fault);

}
