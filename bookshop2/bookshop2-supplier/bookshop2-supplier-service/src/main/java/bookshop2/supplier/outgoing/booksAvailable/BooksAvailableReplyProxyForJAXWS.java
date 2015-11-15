package bookshop2.supplier.outgoing.booksAvailable;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class BooksAvailableReplyProxyForJAXWS extends JAXWSProxy implements Proxy<BooksAvailableReply> {

	private BooksAvailableReplyService service;

	private Object mutex = new Object();

	
	public BooksAvailableReplyProxyForJAXWS(String host, int port) {
		super(host, port);
	}


	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-supplier-service/booksAvailableReplyService/booksAvailableReply?wsdl";
	}

	@Override
	public BooksAvailableReply getDelegate() {
		return getPort();
	}

	protected BooksAvailableReply getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			BooksAvailableReply port = service.getPort();
			initializePort(port);
			return port;
		}
	}

	protected BooksAvailableReplyService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			BooksAvailableReplyService service = new BooksAvailableReplyService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

}
