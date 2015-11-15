package sample.restaurant;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.service.jaxws.AbstractClientJAXWSImpl;


public class RestaurantClient extends AbstractClientJAXWSImpl {

	private static Log log = LogFactory.getLog(RestaurantClient.class);
	
	private RestaurantService service;
	
	
	public RestaurantClient(String host, int port) {
    	try {
    		URL wsdlLocation = new URL(getURI(host, port));
    		service = new RestaurantService(wsdlLocation);
    	} catch (Exception e) {
    		log.error("Error", e);
    	}
	}
	
    private String getURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/tx-service/RestaurantService/RestaurantPortType?wsdl";
	}

	public void bookSeats(int howMany) {
    	try {
	    	RestaurantPortType port = service.getRestaurantPortType();
	    	initializePort(port);
	    	port.bookSeats(howMany);
    	} catch (Exception e) {
    		log.error("Error", e);
    	}
    }
	
}
