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

import bookshop2.ShipmentCompleteMessage;
import bookshop2.seller.SellerContext;


@Remote(ShipmentComplete.class)
@Stateless(name = "ShipmentComplete")
@WebService(name = "shipmentComplete", serviceName = "shipmentCompleteService", portName = "shipmentComplete", targetNamespace = "http://bookshop2/seller")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class ShipmentCompleteListenerForJAXWS implements ShipmentComplete {
	
	private static final Log log = LogFactory.getLog(ShipmentCompleteListenerForJAXWS.class);
	
	@Inject
	private SellerContext sellerContext;
	
	@Inject
	private ShipmentCompleteHandler shipmentCompleteHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage) {
		if (!Bootstrapper.isInitialized("bookshop2-seller-service"))
			return;
		
		try {
			sellerContext.validate(shipmentCompleteMessage);
			shipmentCompleteHandler.shipmentComplete(shipmentCompleteMessage);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
