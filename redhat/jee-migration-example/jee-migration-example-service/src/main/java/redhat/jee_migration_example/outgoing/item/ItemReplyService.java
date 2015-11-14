package redhat.jee_migration_example.outgoing.item;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "item", targetNamespace = "http://www.redhat.com/jee-migration-example")
public class ItemReplyService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ItemReplyService service;
	
	
	public ItemReplyService() {
		this(WSDL_LOCATION);
	}
	
	public ItemReplyService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://www.redhat.com/jee-migration-example", "itemReplyService"));
	}
	
	public ItemReplyService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "item")
	public ItemReply getPort() {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "itemReply"), ItemReply.class);
	}
	
	@WebEndpoint(name = "item")
	public ItemReply getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "itemReply"), ItemReply.class, features);
	}
	
}
