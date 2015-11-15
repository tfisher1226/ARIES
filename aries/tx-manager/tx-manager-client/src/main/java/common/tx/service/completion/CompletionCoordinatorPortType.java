package common.tx.service.completion;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import common.tx.model.Notification;


@WebService(name = "CompletionCoordinatorPortType", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CompletionCoordinatorPortType {

    @Oneway
    @WebMethod(operationName = "CommitOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Commit")
    public void commitOperation(
    		@WebParam(name = "Commit", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
    		Notification notification);

    @Oneway
    @WebMethod(operationName = "RollbackOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Rollback")
    public void rollbackOperation(
    		@WebParam(name = "Rollback", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
    		Notification notification);

}
