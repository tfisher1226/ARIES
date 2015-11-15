package bookshop2.supplier.outgoing.booksUnavailable;

import java.net.MalformedURLException;
import java.net.URL;

import org.aries.bean.Proxy;
import org.aries.tx.service.jaxws.JAXWSProxy;
import org.aries.util.ExceptionUtil;


public class BooksUnavailableReplyProxyForJAXWS extends JAXWSProxy implements Proxy<BooksUnavailableReply> {

	private BooksUnavailableReplyService service;

	private Object mutex = new Object();

	
	public BooksUnavailableReplyProxyForJAXWS(String host, int port) {
		super(host, port);
	}


	protected String getServiceURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/bookshop2-supplier-service/booksUnavailableReplyService/booksUnavailableReply?wsdl";
	}

	@Override
	public BooksUnavailableReply getDelegate() {
		return getPort();
	}

	protected BooksUnavailableReply getPort() {
		synchronized (mutex) {
			if (service == null)
				service = createService();
			BooksUnavailableReply port = service.getPort();
			initializePort(port);
			return port;
		}
	}

	protected BooksUnavailableReplyService createService() {
		try {
			URL wsdlLocation = new URL(getServiceURI(host, port));
			BooksUnavailableReplyService service = new BooksUnavailableReplyService(wsdlLocation);
			return service;
		} catch (Exception e) {
			//log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

}
