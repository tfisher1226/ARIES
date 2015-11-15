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
import org.aries.process.TimeoutHandler;
import org.aries.runtime.RequestContext;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.PurchaseRequestMessage;
import bookshop2.seller.SellerContext;
import bookshop2.seller.SellerProcess;
import bookshop2.seller.data.bookCache.BookCacheManager;


@Stateful
@Local(PurchaseBooksHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class PurchaseBooksHandlerImpl extends AbstractServiceHandler implements PurchaseBooksHandler {
	
	private static final Log log = LogFactory.getLog(PurchaseBooksHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	private SellerProcess sellerProcess;
	
	@Inject
	private BookCacheManager bookCacheManager;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "PurchaseBooks";
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
	public void purchaseBooks(PurchaseRequestMessage purchaseRequestMessage) {
		String correlationId = getCorrelationId(purchaseRequestMessage);
		log.info("#### [seller]: PurchaseBooks request received: "+correlationId);
		TimeoutHandler timeoutHandler = null;
		
		try {
			SellerContext sellerContext = getContext(correlationId);
			sellerContext.validate(purchaseRequestMessage);
			sellerContext.fire_PurchaseBooks_request_received();
			
			if (sellerContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", sellerContext);

			switch (getAction(purchaseRequestMessage)) {
			case CANCEL:
				sellerProcess.handle_PurchaseBooks_request_cancel(purchaseRequestMessage);
				break;
			case UNDO:
				sellerProcess.handle_PurchaseBooks_request_undo(purchaseRequestMessage);
				break;
			default:
				timeoutHandler = startTimeoutHandler(createTimeoutHandler(correlationId));
				sellerProcess.handle_PurchaseBooks_request(purchaseRequestMessage);
				timeoutHandler.reset();
				break;
			}
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			sellerProcess.handle_PurchaseBooks_request_exception(correlationId, e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	protected Runnable createTimeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				String reason = "PurchaseBooks timed-out";
				sellerProcess.handle_PurchaseBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
}
