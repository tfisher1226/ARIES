package bookshop2.shipper.incoming.shipBooks;

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

import bookshop2.ShipmentRequestMessage;
import bookshop2.shipper.ShipperContext;


@Remote(ShipBooks.class)
@Stateless(name = "ShipBooks")
@WebService(name = "shipBooks", serviceName = "shipBooksService", portName = "shipBooks", targetNamespace = "http://bookshop2/shipper")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class ShipBooksListenerForJAXWS implements ShipBooks {
	
	private static final Log log = LogFactory.getLog(ShipBooksListenerForJAXWS.class);
	
	@Inject
	private ShipperContext shipperContext;
	
	@Inject
	private ShipBooksHandler shipBooksHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void shipBooks(ShipmentRequestMessage shipmentRequestMessage) {
		if (!Bootstrapper.isInitialized("bookshop2-shipper-service"))
			return;
		
		try {
			shipperContext.validate(shipmentRequestMessage);
			shipBooksHandler.shipBooks(shipmentRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}