package bookshop2.supplier.incoming.reserveBooks;

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

import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;
import bookshop2.supplier.SupplierContext;


@Remote(ReserveBooks.class)
@Stateless(name = "ReserveBooks")
@WebService(name = "reserveBooks", serviceName = "reserveBooksService", portName = "reserveBooks", targetNamespace = "http://bookshop2/supplier")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class ReserveBooksListenerForJAXWS implements ReserveBooks {
	
	private static final Log log = LogFactory.getLog(ReserveBooksListenerForJAXWS.class);
	
	@Inject
	private SupplierContext supplierContext;
	
	@Inject
	private ReserveBooksHandler reserveBooksHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void reserveBooks(ReservationRequestMessage reservationRequestMessage) throws ReservationAbortedException {
		if (!Bootstrapper.isInitialized("bookshop2-supplier-service"))
			return;
		
		try {
			supplierContext.validate(reservationRequestMessage);
			reserveBooksHandler.reserveBooks(reservationRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			//throw ExceptionUtil.rewrap(e);
			Exception cause = ExceptionUtil.getRootCause(e);
			throw new ReservationAbortedException(cause.getMessage(), cause);
		}
	}
	
}
