package sample.theatre;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@WebService(
		name = "TheatrePortType", 
		targetNamespace = "http://www.aries.com/demo/Theatre")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TheatrePortType {

    @WebMethod
    public void bookSeats(
        @WebParam(name = "howMany", partName = "howMany")
        int howMany,
        @WebParam(name = "which_area", partName = "which_area")
        int whichArea);

}
