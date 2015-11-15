package bookshop2.client.shipmentService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "shipmentService", targetNamespace = "http://bookshop2")
public class ShipmentServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ShipmentServiceService service;
	
	
	public ShipmentServiceService() {
		this(WSDL_LOCATION);
	}
	
	public ShipmentServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2", "shipmentServiceService"));
	}
	
	public ShipmentServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "shipmentService")
	public ShipmentService getPort() {
		return super.getPort(new QName("http://bookshop2", "shipmentService"), ShipmentService.class);
	}
	
	@WebEndpoint(name = "shipmentService")
	public ShipmentService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2", "shipmentService"), ShipmentService.class, features);
	}
	
}
