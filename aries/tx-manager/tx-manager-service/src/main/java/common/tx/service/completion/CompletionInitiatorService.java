package common.tx.service.completion;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.wsaddressing.W3CEndpointReference;


@WebServiceClient(
		name = "CompletionInitiatorService", 
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", 
		wsdlLocation = "/wsdl/wsat-completion-initiator-binding.wsdl")
public class CompletionInitiatorService extends Service {

	private final static URL COMPLETIONINITIATORSERVICE_WSDL_LOCATION;
	//private final static Logger logger = Logger.getLogger(CompletionInitiatorService.class.getName());

	static {
		URL url = CompletionInitiatorService.class.getResource("/wsdl/wsat-completion-initiator-binding.wsdl");
		COMPLETIONINITIATORSERVICE_WSDL_LOCATION = url;
	}

	public CompletionInitiatorService() {
		this(COMPLETIONINITIATORSERVICE_WSDL_LOCATION);
	}

	public CompletionInitiatorService(URL wsdlLocation) {
		super(wsdlLocation, new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CompletionInitiatorService"));
	}

	public CompletionInitiatorService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	@WebEndpoint(name = "CompletionInitiatorPortType")
	public CompletionInitiatorPortType getCompletionInitiatorPortType(W3CEndpointReference endpoint) {
		return super.getPort(endpoint, CompletionInitiatorPortType.class);
	}

	@WebEndpoint(name = "CompletionInitiatorPortType")
	public CompletionInitiatorPortType getCompletionInitiatorPortType() {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CompletionInitiatorPortType"), CompletionInitiatorPortType.class);
	}

	@WebEndpoint(name = "CompletionInitiatorPortType")
	public CompletionInitiatorPortType getCompletionInitiatorPortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CompletionInitiatorPortType"), CompletionInitiatorPortType.class, features);
	}

}
