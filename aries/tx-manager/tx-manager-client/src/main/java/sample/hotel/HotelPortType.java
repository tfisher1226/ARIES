package sample.hotel;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(
		name = "HotelPortType", 
		targetNamespace = "http://www.aries.com/demo/Hotel")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface HotelPortType {

    @WebMethod
    public void bookRoom(
        @WebParam(name = "numberOfPeople", partName = "numberOfPeople")
        int numberOfPeople);

}
