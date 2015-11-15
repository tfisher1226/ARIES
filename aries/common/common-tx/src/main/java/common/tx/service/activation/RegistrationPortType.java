package common.tx.service.activation;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import common.tx.model.context.RegisterResponseType;
import common.tx.model.context.RegisterType;


@WebService(name = "RegistrationPortType", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface RegistrationPortType {

    @WebMethod(operationName = "RegisterOperation", action = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06/Register")
    @WebResult(name = "RegisterResponse", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "parameters")
    public RegisterResponseType registerOperation(
        @WebParam(name = "Register", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "parameters")
        RegisterType parameters);

}
