package sample.theatre;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(
		name = "TheatreService", 
		wsdlLocation = "/wsdl/sample-theatre-service.wsdl",
		targetNamespace = "http://www.aries.com/demo/Theatre")
public class TheatreService extends Service {

	private final static URL WSDL_LOCATION;

	static {
		URL url = TheatreService.class.getResource("/wsdl/sample-theatre-service.wsdl");
		WSDL_LOCATION = url;
	}

	public TheatreService() {
		this(WSDL_LOCATION);
	}

	public TheatreService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://www.aries.com/demo/Theatre", "TheatreService"));
	}

	public TheatreService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	@WebEndpoint(name = "TheatrePortType")
	public TheatrePortType getTheatrePortType() {
		return super.getPort(new QName("http://www.aries.com/demo/Theatre", "TheatrePortType"), TheatrePortType.class);
	}

	@WebEndpoint(name = "TheatrePortType")
	public TheatrePortType getTheatrePortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.aries.com/demo/Theatre", "TheatrePortType"), TheatrePortType.class, features);
	}

}
