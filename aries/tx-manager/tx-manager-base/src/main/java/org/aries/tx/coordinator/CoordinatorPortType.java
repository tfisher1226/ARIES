package org.aries.tx.coordinator;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Notification;


@WebService(name = "CoordinatorPortType", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CoordinatorPortType {

	@WebMethod(operationName = "GetStatus", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/GetStatus")
	@WebResult(name = "Status", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "status")
	public int getStatus(
		@WebParam(name = "TransactionId", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "transactionId") 
		String transactionId);

    @Oneway
    @WebMethod(operationName = "PreparedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Prepared")
    public void preparedOperation(
    		@WebParam(name = "Prepared", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
    		Notification notification);

    @Oneway
    @WebMethod(operationName = "ReadOnlyOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/ReadOnly")
    public void readOnlyOperation(
    		@WebParam(name = "ReadOnly", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
    		Notification notification);

    @Oneway
    @WebMethod(operationName = "CommittedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Committed")
    public void committedOperation(
    		@WebParam(name = "Committed", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
    		Notification notification);

    @Oneway
    @WebMethod(operationName = "AbortedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Aborted")
    public void abortedOperation(
    		@WebParam(name = "Aborted", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
    		Notification notification);

    @Oneway
    @WebMethod(operationName = "Fault", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
    public void fault(
    		@WebParam(name = "Fault", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope/", partName = "parameters") 
    		Fault fault);

}
