package bookshop2.buyer.incoming.orderBooks;

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
import bookshop2.buyer.BuyerContext;
import bookshop2.buyer.BuyerProcess;


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
	private BuyerProcess buyerProcess;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "OrderBooks";
	}
	
	public String getDomain() {
		return "bookshop2.buyer";
	}
	
	public boolean prepare(String transactionId) {
		return true;
	}
	
	public void commit(String transactionId) {
		//nothing for now
	}
	
	public void rollback(String transactionId) {
		//nothing for now
	}
	
	protected BuyerContext getContext(AbstractMessage message) {
		return getContext(getCorrelationId(message));
	}

	protected BuyerContext getContext(String correlationId) {
		requestContext.setCorrelationId(correlationId);
		return BuyerContext.getInstance(correlationId);
	}
	
	@Override
	@Asynchronous
	@AccessTimeout(value = 60000)
	@TransactionAttribute(REQUIRED)
	public void orderBooks(OrderRequestMessage orderRequestMessage) {
		String correlationId = getCorrelationId(orderRequestMessage);
		log.info("#### [buyer]: OrderBooks request received: "+correlationId);
		TimeoutHandler timeoutHandler = null;

		try {
			BuyerContext buyerContext = getContext(correlationId);
			buyerContext.validate(orderRequestMessage);
			buyerContext.fire_OrderBooks_request_received();

			if (buyerContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", buyerContext);

			switch (getAction(orderRequestMessage)) {
			case CANCEL:
				buyerProcess.handle_OrderBooks_request_cancel(orderRequestMessage);
				break;
			case UNDO:
				buyerProcess.handle_OrderBooks_request_undo(orderRequestMessage);
				break;
			default:
				timeoutHandler = startTimeoutHandler(createTimeoutHandler(correlationId));
				buyerProcess.handle_OrderBooks_request(orderRequestMessage);
				timeoutHandler.reset();
				break;
			}
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			buyerProcess.handle_OrderBooks_request_exception(correlationId, e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	protected Runnable createTimeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				String reason = "OrderBooks timed-out";
				buyerProcess.handle_OrderBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
}
