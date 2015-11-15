package bookshop2.buyer.outgoing.orderAccepted;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class OrderAcceptedReplyProxyForJAXWS extends JAXWSProxy implements Proxy<OrderAcceptedReply> {
	
	private OrderAcceptedReplyService service;
	
	private Object mutex = new Object();
	
	
	public OrderAcceptedReplyProxyForJAXWS(String host, int port) {
		super(host, port);
	}
	
	
	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-buyer-service/orderAcceptedReplyService/orderAcceptedReply?wsdl";
	}
	
	@Override
	public OrderAcceptedReply getDelegate() {
		return getPort();
	}
	
	protected OrderAcceptedReply getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			OrderAcceptedReply port = service.getPort();
			initializePort(port);
			return port;
		}
	}
	
	protected OrderAcceptedReplyService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			OrderAcceptedReplyService service = new OrderAcceptedReplyService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
