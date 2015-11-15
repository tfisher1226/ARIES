package bookshop2.supplier.incoming.shipBooks;

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
import bookshop2.supplier.SupplierContext;


@Remote(ShipmentFailed.class)
@Stateless(name = "ShipmentFailed")
@WebService(name = "shipmentFailed", serviceName = "shipmentFailedService", portName = "shipmentFailed", targetNamespace = "http://bookshop2/supplier")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class ShipmentFailedListenerForJAXWS implements ShipmentFailed {
	
	private static final Log log = LogFactory.getLog(ShipmentFailedListenerForJAXWS.class);
	
	@Inject
	private SupplierContext supplierContext;
	
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
		if (!Bootstrapper.isInitialized("bookshop2-supplier-service"))
			return;
		
		try {
			supplierContext.validate(shipmentFailedMessage);
			shipmentFailedHandler.shipmentFailed(shipmentFailedMessage);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
