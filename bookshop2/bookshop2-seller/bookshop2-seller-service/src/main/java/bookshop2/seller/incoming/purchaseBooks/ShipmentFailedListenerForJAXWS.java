package bookshop2.seller.incoming.purchaseBooks;

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

import bookshop2.ShipmentFailedMessage;
import bookshop2.seller.SellerContext;


@Remote(ShipmentFailed.class)
@Stateless(name = "ShipmentFailed")
@WebService(name = "shipmentFailed", serviceName = "shipmentFailedService", portName = "shipmentFailed", targetNamespace = "http://bookshop2/seller")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class ShipmentFailedListenerForJAXWS implements ShipmentFailed {
	
	private static final Log log = LogFactory.getLog(ShipmentFailedListenerForJAXWS.class);
	
	@Inject
	private SellerContext sellerContext;
	
	@Inject
	private ShipmentFailedHandler shipmentFailedHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void shipmentFailed(ShipmentFailedMessage shipmentFailedMessage) {
		if (!Bootstrapper.isInitialized("bookshop2-seller-service"))
			return;
		
		try {
			sellerContext.validate(shipmentFailedMessage);
			shipmentFailedHandler.shipmentFailed(shipmentFailedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
