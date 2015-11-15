package bookshop2.seller.incoming.orderBooks;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;

import bookshop2.BooksUnavailableMessage;
import bookshop2.seller.SellerContext;


@Remote(BooksUnavailable.class)
@Stateless(name = "BooksUnavailable")
@WebService(name = "booksUnavailable", serviceName = "booksUnavailableService", portName = "booksUnavailable", targetNamespace = "http://bookshop2/seller")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class BooksUnavailableListenerForJAXWS implements BooksUnavailable {
	
	private static final Log log = LogFactory.getLog(BooksUnavailableListenerForJAXWS.class);
	
	@Inject
	private SellerContext sellerContext;
	
	@Inject
	private BooksUnavailableHandler booksUnavailableHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void booksUnavailable(BooksUnavailableMessage booksUnavailableMessage) {
		if (!Bootstrapper.isInitialized("bookshop2-seller-service"))
			return;
		
		try {
			sellerContext.validate(booksUnavailableMessage);
			booksUnavailableHandler.booksUnavailable(booksUnavailableMessage);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
