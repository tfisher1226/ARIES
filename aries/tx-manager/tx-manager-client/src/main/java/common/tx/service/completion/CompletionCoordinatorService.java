package common.tx.service.completion;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.wsaddressing.W3CEndpointReference;


@WebServiceClient(
		name = "CompletionCoordinatorService", 
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", 
		wsdlLocation = "/wsdl/wsat-completion-coordinator-binding.wsdl")
public class CompletionCoordinatorService extends Service {

	private final static URL COMPLETIONCOORDINATORSERVICE_WSDL_LOCATION;
	//private final static Logger logger = Logger.getLogger(CompletionCoordinatorService.class.getName());

	static {
		URL url = CompletionCoordinatorService.class.getResource("/wsdl/wsat-completion-coordinator-binding.wsdl");
		COMPLETIONCOORDINATORSERVICE_WSDL_LOCATION = url;
	}

	public CompletionCoordinatorService() {
		this(COMPLETIONCOORDINATORSERVICE_WSDL_LOCATION);
	}

	public CompletionCoordinatorService(URL wsdlLocation) {
		super(wsdlLocation, new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CompletionCoordinatorService"));
	}

	public CompletionCoordinatorService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	@WebEndpoint(name = "CompletionCoordinatorPortType")
	public CompletionCoordinatorPortType getCompletionCoordinatorPortType(W3CEndpointReference endpoint) {
		return super.getPort(endpoint, CompletionCoordinatorPortType.class);
	}
	
	@WebEndpoint(name = "CompletionCoordinatorPortType")
	public CompletionCoordinatorPortType getCompletionCoordinatorPortType() {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CompletionCoordinatorPortType"), CompletionCoordinatorPortType.class);
	}

	@WebEndpoint(name = "CompletionCoordinatorPortType")
	public CompletionCoordinatorPortType getCompletionCoordinatorPortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CompletionCoordinatorPortType"), CompletionCoordinatorPortType.class, features);
	}

}
