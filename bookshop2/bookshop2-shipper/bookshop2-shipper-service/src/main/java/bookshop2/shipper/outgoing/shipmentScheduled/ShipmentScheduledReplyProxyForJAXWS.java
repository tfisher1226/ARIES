package bookshop2.shipper.outgoing.shipmentScheduled;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class ShipmentScheduledReplyProxyForJAXWS extends JAXWSProxy implements Proxy<ShipmentScheduledReply> {
	
	private ShipmentScheduledReplyService service;
	
	private Object mutex = new Object();
	
	
	public ShipmentScheduledReplyProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-shipper-service/shipmentScheduledReplyService/shipmentScheduledReply?wsdl";
	}
	
	@Override
	public ShipmentScheduledReply getDelegate() {
		return getPort();
	}
	
	protected ShipmentScheduledReply getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			ShipmentScheduledReply port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected ShipmentScheduledReplyService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			ShipmentScheduledReplyService service = new ShipmentScheduledReplyService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
