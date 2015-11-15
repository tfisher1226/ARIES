package sample.restaurant;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(
		name = "RestaurantPortType", 
		targetNamespace = "http://www.aries.com/demo/Restaurant")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RestaurantPortType {

    @WebMethod
    public void bookSeats(
        @WebParam(name = "howMany", partName = "howMany")
        int howMany);

}
