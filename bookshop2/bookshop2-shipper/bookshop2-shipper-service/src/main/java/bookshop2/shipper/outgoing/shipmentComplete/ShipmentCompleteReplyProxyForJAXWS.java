package bookshop2.shipper.outgoing.shipmentComplete;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ShipmentCompleteReplyProxyForJAXWS extends JAXWSProxy implements Proxy<ShipmentCompleteReply> {
	
	private ShipmentCompleteReplyService service;

	private Object mutex = new Object();
	
	
	public ShipmentCompleteReplyProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-shipper-service/shipmentCompleteReplyService/shipmentCompleteReply?wsdl";
	}
	
	@Override
	public ShipmentCompleteReply getDelegate() {
		return getPort();
	}
	
	protected ShipmentCompleteReply getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ShipmentCompleteReply port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ShipmentCompleteReplyService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ShipmentCompleteReplyService service = new ShipmentCompleteReplyService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
