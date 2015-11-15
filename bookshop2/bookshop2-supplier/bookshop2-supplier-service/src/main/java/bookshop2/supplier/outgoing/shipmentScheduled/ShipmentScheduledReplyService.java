package bookshop2.supplier.outgoing.shipmentScheduled;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "shipmentScheduledReply", targetNamespace = "http://bookshop2/supplier")
public class ShipmentScheduledReplyService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ShipmentScheduledReplyService service;
	
	
	public ShipmentScheduledReplyService() {
		this(WSDL_LOCATION);
	}
	
	public ShipmentScheduledReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "shipmentScheduledReplyService"));
	}
	
	public ShipmentScheduledReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "shipmentScheduledReply")
	public ShipmentScheduledReply getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "shipmentScheduledReply"), ShipmentScheduledReply.class);
	}
	
	@WebEndpoint(name = "shipmentScheduledReply")
	public ShipmentScheduledReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "shipmentScheduledReply"), ShipmentScheduledReply.class, features);
	}
	
}
