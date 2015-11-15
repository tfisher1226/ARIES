package bookshop2.seller.outgoing.purchaseRejected;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class PurchaseRejectedReplyProxyForJAXWS extends JAXWSProxy implements Proxy<PurchaseRejectedReply> {

	private PurchaseRejectedReplyService service;

	private Object mutex = new Object();

	
	public PurchaseRejectedReplyProxyForJAXWS(String host, int port) {
		super(host, port);
	}


	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-seller-service/purchaseRejectedReplyService/purchaseRejectedReply?wsdl";
	}
	
	@Override
	public PurchaseRejectedReply getDelegate() {
		return getPort();
	}

	protected PurchaseRejectedReply getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			PurchaseRejectedReply port = service.getPort();
			initializePort(port);
			return port;
		}
	}

	protected PurchaseRejectedReplyService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			PurchaseRejectedReplyService service = new PurchaseRejectedReplyService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

}
