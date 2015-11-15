package bookshop2.buyer.incoming.purchaseBooks;

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

import bookshop2.PurchaseAcceptedMessage;
import bookshop2.buyer.BuyerContext;


@Remote(PurchaseAccepted.class)
@Stateless(name = "PurchaseAccepted")
@WebService(name = "purchaseAccepted", serviceName = "purchaseAcceptedService", portName = "purchaseAccepted", targetNamespace = "http://bookshop2/buyer")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class PurchaseAcceptedListenerForJAXWS implements PurchaseAccepted {
	
	private static final Log log = LogFactory.getLog(PurchaseAcceptedListenerForJAXWS.class);
	
	@Inject
	private BuyerContext buyerContext;
	
	@Inject
	private PurchaseAcceptedHandler purchaseAcceptedHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage) {
		if (!Bootstrapper.isInitialized("bookshop2-buyer-service"))
			return;
		
		try {
			buyerContext.validate(purchaseAcceptedMessage);
			purchaseAcceptedHandler.purchaseAccepted(purchaseAcceptedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
