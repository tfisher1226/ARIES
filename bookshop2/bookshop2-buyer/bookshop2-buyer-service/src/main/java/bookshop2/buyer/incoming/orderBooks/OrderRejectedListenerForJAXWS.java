package bookshop2.buyer.incoming.orderBooks;

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

import bookshop2.OrderRejectedMessage;
import bookshop2.buyer.BuyerContext;


@Remote(OrderRejected.class)
@Stateless(name = "OrderRejected")
@WebService(name = "orderRejected", serviceName = "orderRejectedService", portName = "orderRejected", targetNamespace = "http://bookshop2/buyer")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class OrderRejectedListenerForJAXWS implements OrderRejected {
	
	private static final Log log = LogFactory.getLog(OrderRejectedListenerForJAXWS.class);
	
	@Inject
	private BuyerContext buyerContext;
	
	@Inject
	private OrderRejectedHandler orderRejectedHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void orderRejected(OrderRejectedMessage orderRejectedMessage) {
		if (!Bootstrapper.isInitialized("bookshop2-buyer-service"))
			return;
		
		try {
			buyerContext.validate(orderRejectedMessage);
			orderRejectedHandler.orderRejected(orderRejectedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
