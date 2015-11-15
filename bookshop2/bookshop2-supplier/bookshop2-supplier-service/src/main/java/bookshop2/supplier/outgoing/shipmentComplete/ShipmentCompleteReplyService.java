package bookshop2.supplier.outgoing.shipmentComplete;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "shipmentCompleteReply", targetNamespace = "http://bookshop2/supplier")
public class ShipmentCompleteReplyService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ShipmentCompleteReplyService service;
	
	
	public ShipmentCompleteReplyService() {
		this(WSDL_LOCATION);
	}
	
	public ShipmentCompleteReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "shipmentCompleteReplyService"));
	}
	
	public ShipmentCompleteReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "shipmentCompleteReply")
	public ShipmentCompleteReply getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "shipmentCompleteReply"), ShipmentCompleteReply.class);
	}
	
	@WebEndpoint(name = "shipmentCompleteReply")
	public ShipmentCompleteReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "shipmentCompleteReply"), ShipmentCompleteReply.class, features);
	}
	
}
