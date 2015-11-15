package bookshop2.supplier.outgoing.shipmentFailed;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "shipmentFailedReply", targetNamespace = "http://bookshop2/supplier")
public class ShipmentFailedReplyService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ShipmentFailedReplyService service;
	
	
	public ShipmentFailedReplyService() {
		this(WSDL_LOCATION);
	}
	
	public ShipmentFailedReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "shipmentFailedReplyService"));
	}
	
	public ShipmentFailedReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "shipmentFailedReply")
	public ShipmentFailedReply getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "shipmentFailedReply"), ShipmentFailedReply.class);
	}
	
	@WebEndpoint(name = "shipmentFailedReply")
	public ShipmentFailedReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "shipmentFailedReply"), ShipmentFailedReply.class, features);
	}
	
}
