package common.tx.service.activation;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import common.tx.model.context.CreateCoordinationContextResponseType;
import common.tx.model.context.CreateCoordinationContextType;


@WebService(name = "ActivationPortType", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ActivationPortType {

    @WebMethod(operationName = "CreateCoordinationContextOperation")
    @WebResult(name = "CreateCoordinationContextResponse", partName = "parameters")
    public CreateCoordinationContextResponseType createCoordinationContextOperation(
        @WebParam(name = "CreateCoordinationContext", partName = "parameters") 
        CreateCoordinationContextType parameters);

}
