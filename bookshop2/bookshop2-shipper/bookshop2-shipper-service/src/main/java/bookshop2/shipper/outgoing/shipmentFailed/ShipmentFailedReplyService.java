package bookshop2.shipper.outgoing.shipmentFailed;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "shipmentFailedReply", targetNamespace = "http://bookshop2/shipper")
public class ShipmentFailedReplyService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ShipmentFailedReplyService service;
	
	
	public ShipmentFailedReplyService() {
		this(WSDL_LOCATION);
	}
	
	public ShipmentFailedReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/shipper", "shipmentFailedReplyService"));
	}
	
	public ShipmentFailedReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "shipmentFailedReply")
	public ShipmentFailedReply getPort() {
		return super.getPort(new QName("http://bookshop2/shipper", "shipmentFailedReply"), ShipmentFailedReply.class);
	}
	
	@WebEndpoint(name = "shipmentFailedReply")
	public ShipmentFailedReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/shipper", "shipmentFailedReply"), ShipmentFailedReply.class, features);
	}
	
}
