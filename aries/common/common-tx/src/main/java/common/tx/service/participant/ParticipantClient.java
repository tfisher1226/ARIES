package common.tx.service.participant;



public class ParticipantClient {

    private String prepareAction;
    
    private String commitAction;
    
    private String rollbackAction;
    
    private String faultAction;

    
//    public void sendPrepare(W3CEndpointReference endpoint, MAP map, InstanceIdentifier identifier) throws Fault, IOException {
////        MAPEndpoint coordinator = getCoordinator(endpoint, map);
////        AddressingHelper.installFromFaultTo(map, coordinator, identifier);
////        ParticipantPortType port = getPort(endpoint, map, prepareAction);
////        Notification prepare = new Notification();
////        port.prepareOperation(prepare);
//    }
//
//    public void sendCommit(W3CEndpointReference endpoint, MAP map, InstanceIdentifier identifier) throws Fault, IOException {
////        MAPEndpoint coordinator = getCoordinator(endpoint, map);
////        AddressingHelper.installFromFaultTo(map, coordinator, identifier);
////        ParticipantPortType port = getPort(endpoint, map, commitAction);
////        Notification commit = new Notification();
////        port.commitOperation(commit);
//    }
//
//    public void sendRollback(W3CEndpointReference endpoint, MAP map, InstanceIdentifier identifier) throws Fault, IOException {
////        MAPEndpoint coordinator = getCoordinator(endpoint, map);
////        AddressingHelper.installFromFaultTo(map, coordinator, identifier);
////        ParticipantPortType port = getPort(endpoint, map, rollbackAction);
////        Notification rollback = new Notification();
////        port.rollbackOperation(rollback);
//    }
//
//    public void sendFault(MAP map, Fault fault, InstanceIdentifier identifier) throws Fault, IOException {
////        ParticipantPortType port = getPort(null, map, faultAction);
////        // convert fault to the wire format and dispatch it to the initiator
////        org.xmlsoap.schemas.soap.envelope.Fault xmlFault = FaultUtil.convertToXmlFault(fault);
////        port.fault(xmlFault);
//    }
    
}
