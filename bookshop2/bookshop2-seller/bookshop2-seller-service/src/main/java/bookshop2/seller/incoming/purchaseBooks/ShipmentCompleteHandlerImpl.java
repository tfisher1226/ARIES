package bookshop2.seller.incoming.purchaseBooks;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.common.AbstractMessage;
import org.aries.runtime.RequestContext;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentCompleteMessage;
import bookshop2.seller.SellerContext;
import bookshop2.seller.SellerProcess;
import bookshop2.seller.data.bookCache.BookCacheManager;


@Stateful
@Local(ShipmentCompleteHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class ShipmentCompleteHandlerImpl extends AbstractServiceHandler implements ShipmentCompleteHandler {
	
	private static final Log log = LogFactory.getLog(ShipmentCompleteHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	private SellerProcess sellerProcess;
	
	@Inject
	private BookCacheManager bookCacheManager;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "ShipmentComplete";
	}
	
	public String getDomain() {
		return "bookshop2.seller";
	}
	
	public boolean prepare(String transactionId) {
		return bookCacheManager.prepare(transactionId);
	}
	
	public void commit(String transactionId) {
		bookCacheManager.commit(transactionId);
	}
	
	public void rollback(String transactionId) {
		bookCacheManager.rollback(transactionId);
	}
	
	protected SellerContext getContext(AbstractMessage message) {
		return getContext(getCorrelationId(message));
	}

	protected SellerContext getContext(String correlationId) {
		requestContext.setCorrelationId(correlationId);
		return SellerContext.getInstance(correlationId);
	}
	
	@Override
	@Asynchronous
	@AccessTimeout(value = 60000)
	@TransactionAttribute(REQUIRED)
	public void shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage) {
		String correlationId = getCorrelationId(shipmentCompleteMessage);
		log.info("#### [seller]: ShipmentComplete response received: "+correlationId);
		SellerContext sellerContext = getContext(correlationId);
		
		try {
			sellerContext.validate(shipmentCompleteMessage);
			sellerContext.fire_ShipmentComplete_response_received();
			
			if (sellerContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", sellerContext);

			sellerProcess.handle_ShipmentComplete_response(shipmentCompleteMessage);
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			sellerContext.fire_ShipmentComplete_incoming_response_aborted(e);
		}
	}
	
}
