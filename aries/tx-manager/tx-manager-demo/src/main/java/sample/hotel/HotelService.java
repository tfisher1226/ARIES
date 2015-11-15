package sample.hotel;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(
		name = "HotelService", 
		wsdlLocation = "/wsdl/sample-hotel-service.wsdl",
		targetNamespace = "http://www.aries.com/demo/Hotel")
public class HotelService extends Service {

	private final static URL WSDL_LOCATION;

	static {
		URL url = HotelService.class.getResource("/wsdl/sample-hotel-service.wsdl");
		WSDL_LOCATION = url;
	}

	public HotelService() {
		this(WSDL_LOCATION);
	}

	public HotelService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://www.aries.com/demo/Hotel", "HotelService"));
	}

	public HotelService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	@WebEndpoint(name = "HotelPortType")
	public HotelPortType getHotelPortType() {
		return super.getPort(new QName("http://www.aries.com/demo/Hotel", "HotelPortType"), HotelPortType.class);
	}

	@WebEndpoint(name = "HotelPortType")
	public HotelPortType getHotelPortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.aries.com/demo/Hotel", "HotelPortType"), HotelPortType.class, features);
	}

}
