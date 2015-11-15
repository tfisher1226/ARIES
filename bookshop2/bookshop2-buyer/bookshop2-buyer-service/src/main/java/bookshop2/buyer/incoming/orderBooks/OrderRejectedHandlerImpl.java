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
import org.aries.runtime.RequestContext;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderRejectedMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.buyer.BuyerProcess;


@Stateful
@Local(OrderRejectedHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class OrderRejectedHandlerImpl extends AbstractServiceHandler implements OrderRejectedHandler {
	
	private static final Log log = LogFactory.getLog(OrderRejectedHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	private BuyerProcess buyerProcess;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "OrderRejected";
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
	public void orderRejected(OrderRejectedMessage orderRejectedMessage) {
		String correlationId = getCorrelationId(orderRejectedMessage);
		log.info("#### [buyer]: OrderRejected response received: "+correlationId);
		BuyerContext buyerContext = getContext(correlationId);
		
		try {
			buyerContext.validate(orderRejectedMessage);
			buyerContext.fire_OrderRejected_response_received();

			if (buyerContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", buyerContext);

			buyerProcess.handle_OrderRejected_response(orderRejectedMessage);
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			buyerContext.fire_OrderRejected_incoming_response_aborted(e);
		}
	}
	
}
