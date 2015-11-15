package sample.restaurant;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(
		name = "RestaurantService", 
		wsdlLocation = "/wsdl/sample-restaurant-service.wsdl",
		targetNamespace = "http://www.aries.com/demo/Restaurant")
public class RestaurantService extends Service {

	private final static URL WSDL_LOCATION;

	static {
		URL url = RestaurantService.class.getResource("/wsdl/sample-restaurant-service.wsdl");
		WSDL_LOCATION = url;
	}

	public RestaurantService() {
		this(WSDL_LOCATION);
	}

	public RestaurantService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://www.aries.com/demo/Restaurant", "RestaurantService"));
	}

	public RestaurantService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	@WebEndpoint(name = "RestaurantPortType")
	public RestaurantPortType getRestaurantPortType() {
		return super.getPort(new QName("http://www.aries.com/demo/Restaurant", "RestaurantPortType"), RestaurantPortType.class);
	}

	@WebEndpoint(name = "RestaurantPortType")
	public RestaurantPortType getRestaurantPortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.aries.com/demo/Restaurant", "RestaurantPortType"), RestaurantPortType.class, features);
	}

}
