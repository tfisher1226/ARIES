package sample.hotel;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.service.jaxws.AbstractClientJAXWSImpl;


public class HotelClient extends AbstractClientJAXWSImpl {

	private static Log log = LogFactory.getLog(HotelClient.class);
	
	private HotelService service;
	
	
	public HotelClient(String host, int port) {
    	try {
    		URL wsdlLocation = new URL(getURI(host, port));
        	service = new HotelService(wsdlLocation);
    	} catch (Exception e) {
    		log.error("Error", e);
    	}
	}
	
    private String getURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/tx-service/HotelService/HotelPortType?wsdl";
	}

	public void bookRoom(int numberOfPeople) {
    	try {
	    	HotelPortType port = service.getHotelPortType();
	    	initializePort(port);
	    	port.bookRoom(numberOfPeople);
    	} catch (Exception e) {
    		log.error("Error", e);
    	}
    }

}
