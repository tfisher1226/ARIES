package bookshop2.seller.incoming.orderBooks;

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

import bookshop2.OrderRequestMessage;
import bookshop2.seller.SellerContext;
import bookshop2.seller.SellerProcess;
import bookshop2.seller.data.bookCache.BookCacheManager;


@Stateful
@Local(OrderBooksHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class OrderBooksHandlerImpl extends AbstractServiceHandler implements OrderBooksHandler {
	
	private static final Log log = LogFactory.getLog(OrderBooksHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	private SellerProcess sellerProcess;
	
	@Inject
	private BookCacheManager bookCacheManager;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "OrderBooks";
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
	public void orderBooks(OrderRequestMessage orderRequestMessage) {
		String correlationId = getCorrelationId(orderRequestMessage);
		log.info("#### [seller]: OrderBooks request received: "+correlationId);
		TimeoutHandler timeoutHandler = null;
		
		try {
			SellerContext sellerContext = getContext(correlationId);
			sellerContext.validate(orderRequestMessage);
			sellerContext.fire_OrderBooks_request_received();
			
			if (sellerContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", sellerContext);

			switch (getAction(orderRequestMessage)) {
			case CANCEL:
				sellerProcess.handle_OrderBooks_request_cancel(orderRequestMessage);
				break;
			case UNDO:
				sellerProcess.handle_OrderBooks_request_undo(orderRequestMessage);
				break;
			default:
				timeoutHandler = startTimeoutHandler(createTimeoutHandler(correlationId));
				sellerProcess.handle_OrderBooks_request(orderRequestMessage);
				timeoutHandler.reset();
				break;
			}
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			sellerProcess.handle_OrderBooks_request_exception(correlationId, e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	protected Runnable createTimeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				String reason = "OrderBooks timed-out";
				sellerProcess.handle_OrderBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
//	protected SellerProcess getProcess() {
//		String jndiName = getProcessJndiName();
//		return BeanLocator.lookup(SellerProcess.class, jndiName);
//	}
//	
//	protected String getProcessJndiName() {
//		JndiName jndiName = new JndiName();
//		jndiName.setApplicationName("bookshop2-seller");
//		jndiName.setModuleName("bookshop2-seller-service-0.0.1-SNAPSHOT");
//		jndiName.setBeanName("SellerProcess");
//		return jndiName.toString();
//	}
	
}
