package common.tx.service.activation;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(
		name = "ActivationService", 
		wsdlLocation = "/wsdl/wscoor-activation-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06")
public class ActivationService extends Service {

	private final static URL ACTIVATIONSERVICE_WSDL_LOCATION;
	//private final static Logger logger = Logger.getLogger(ActivationService.class.getName());

	static {
		URL url = ActivationService.class.getResource("/wsdl/wscoor-activation-binding.wsdl");
		ACTIVATIONSERVICE_WSDL_LOCATION = url;
	}

	public ActivationService() {
		this(ACTIVATIONSERVICE_WSDL_LOCATION);
	}

	public ActivationService(URL wsdlLocation) {
		super(wsdlLocation, new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "ActivationService"));
	}

	public ActivationService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	@WebEndpoint(name = "ActivationPortType")
	public ActivationPortType getActivationPortType() {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "ActivationPortType"), ActivationPortType.class);
	}

	@WebEndpoint(name = "ActivationPortType")
	public ActivationPortType getActivationPortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "ActivationPortType"), ActivationPortType.class, features);
	}

}
