package bookshop2.supplier.outgoing.shipmentFailed;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ShipmentFailedReplyProxyForJAXWS extends JAXWSProxy implements Proxy<ShipmentFailedReply> {
	
	private ShipmentFailedReplyService service;
	
	private Object mutex = new Object();
	
	
	public ShipmentFailedReplyProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-supplier-service/shipmentFailedReplyService/shipmentFailedReply?wsdl";
	}
	
	@Override
	public ShipmentFailedReply getDelegate() {
		return getPort();
	}
	
	protected ShipmentFailedReply getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ShipmentFailedReply port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ShipmentFailedReplyService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ShipmentFailedReplyService service = new ShipmentFailedReplyService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
