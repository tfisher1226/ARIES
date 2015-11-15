package bookshop2.seller.outgoing.purchaseAccepted;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class PurchaseAcceptedReplyProxyForJAXWS extends JAXWSProxy implements Proxy<PurchaseAcceptedReply> {

	private PurchaseAcceptedReplyService service;

	private Object mutex = new Object();

	
	public PurchaseAcceptedReplyProxyForJAXWS(String host, int port) {
		super(host, port);
	}


	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-seller-service/purchaseAcceptedReplyService/purchaseAcceptedReply?wsdl";
	}
	
	@Override
	public PurchaseAcceptedReply getDelegate() {
		return getPort();
	}

	protected PurchaseAcceptedReply getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			PurchaseAcceptedReply port = service.getPort();
			initializePort(port);
			return port;
		}
	}

	protected PurchaseAcceptedReplyService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			PurchaseAcceptedReplyService service = new PurchaseAcceptedReplyService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

}
