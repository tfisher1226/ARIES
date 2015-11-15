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

import bookshop2.PurchaseRejectedMessage;
import bookshop2.buyer.BuyerContext;


@Remote(PurchaseRejected.class)
@Stateless(name = "PurchaseRejected")
@WebService(name = "purchaseRejected", serviceName = "purchaseRejectedService", portName = "purchaseRejected", targetNamespace = "http://bookshop2/buyer")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class PurchaseRejectedListenerForJAXWS implements PurchaseRejected {
	
	private static final Log log = LogFactory.getLog(PurchaseRejectedListenerForJAXWS.class);
	
	@Inject
	private BuyerContext buyerContext;
	
	@Inject
	private PurchaseRejectedHandler purchaseRejectedHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void purchaseRejected(PurchaseRejectedMessage purchaseRejectedMessage) {
		if (!Bootstrapper.isInitialized("bookshop2-buyer-service"))
			return;
		
		try {
			buyerContext.validate(purchaseRejectedMessage);
			purchaseRejectedHandler.purchaseRejected(purchaseRejectedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
