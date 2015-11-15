package bookshop2.buyer.incoming.purchaseBooks;

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
import bookshop2.buyer.BuyerContext;
import bookshop2.buyer.BuyerProcess;


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
	private BuyerProcess buyerProcess;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "PurchaseBooks";
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
	public void purchaseBooks(PurchaseRequestMessage purchaseRequestMessage) {
		String correlationId = getCorrelationId(purchaseRequestMessage);
		log.info("#### [buyer]: PurchaseBooks request received: "+correlationId);
		TimeoutHandler timeoutHandler = null;
		
		try {
			BuyerContext buyerContext = getContext(correlationId);
			buyerContext.validate(purchaseRequestMessage);
			buyerContext.fire_PurchaseBooks_request_received();

			if (buyerContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", buyerContext);

			switch (getAction(purchaseRequestMessage)) {
			case CANCEL:
				buyerProcess.handle_PurchaseBooks_request_cancel(purchaseRequestMessage);
				break;
			case UNDO:
				buyerProcess.handle_PurchaseBooks_request_undo(purchaseRequestMessage);
				break;
			default:
				timeoutHandler = startTimeoutHandler(createTimeoutHandler(correlationId));
				buyerProcess.handle_PurchaseBooks_request(purchaseRequestMessage);
				timeoutHandler.reset();
				break;
			}
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			buyerProcess.handle_PurchaseBooks_request_exception(correlationId, e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	protected Runnable createTimeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				String reason = "PurchaseBooks timed-out";
				buyerProcess.handle_PurchaseBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
}
